package se.sundsvall.managerresponsibility.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.managerresponsibility.Application;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;
import se.sundsvall.managerresponsibility.service.ManagerResponsibilityService;

@ActiveProfiles("junit")
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
class OrganizationsResourceTest {

	private static final String MUNICIPALITY_ID = "2281";

	@MockitoBean
	private ManagerResponsibilityService managerResponsibilityServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getManagerResponsibilitiesByOrgId() {

		// Arrange
		final var orgId = "orgId";
		final var expectedResult = List.of(ManagerResponsibility.create().withLoginName("loginName").withOrgList(List.of("org1")));

		when(managerResponsibilityServiceMock.findByOrgId(orgId)).thenReturn(expectedResult);

		// Act
		final var result = webTestClient.get()
			.uri("/{municipalityId}/organizations/{orgId}/manager-responsibilities", MUNICIPALITY_ID, orgId)
			.exchange()
			.expectStatus()
			.isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBodyList(ManagerResponsibility.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(result).isEqualTo(expectedResult);
		verify(managerResponsibilityServiceMock).findByOrgId(orgId);
		verifyNoMoreInteractions(managerResponsibilityServiceMock);
	}
}
