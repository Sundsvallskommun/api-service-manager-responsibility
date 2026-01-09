package se.sundsvall.managerresponsibility.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.zalando.problem.Status.BAD_REQUEST;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;
import se.sundsvall.managerresponsibility.Application;

@ActiveProfiles("junit")
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
class OrganizationsResourceFailuresTest {

	private static final String ORG_ID = "orgId";

	// TODO: Uncomment and make this work
	// @MockitoBean
	// private ManagerResponsibilityService managerResponsibilityServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getManagerResponsibilitiesByOrgIdInvalidMunicipalityId() {

		// Act
		final var response = webTestClient.get()
			.uri("/{municipalityId}/organizations/{orgId}/manager-responsibilities", "invalid", ORG_ID)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getManagerResponsibilitiesByOrganizationId.municipalityId", "not a valid municipality ID"));

		// TODO: Uncomment and make this work
		// verifyNoInteractions(managerResponsibilityServiceMock);
	}
}
