package se.sundsvall.managerresponsibility.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.managerresponsibility.Application;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;

@ActiveProfiles("junit")
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
class LoginsResourceTest {

	private static final String MUNICIPALITY_ID = "2281";

	// TODO: Uncomment and make this work
	// @MockitoBean
	// private ManagerResponsibilityService managerResponsibilityServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getManagerResponsibilitiesByLoginName() {

		// Arrange
		final var login = "login";
		final var expectedResult = List.of(ManagerResponsibility.create().withId("id").withLoginName("loginName").withOrgList(List.of("org1")));

		// when(managerResponsibilityServiceMock.get()).thenReturn(result);

		// Act
		final var result = webTestClient.get()
			.uri("/{municipalityId}/logins/{loginName}/manager-responsibilities", MUNICIPALITY_ID, login)
			.exchange()
			.expectStatus()
			.isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBodyList(ManagerResponsibility.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(result).isEqualTo(expectedResult);
		// TODO: Uncomment and make this work
		// verify(managerResponsibilityServiceMock).get();
	}
}
