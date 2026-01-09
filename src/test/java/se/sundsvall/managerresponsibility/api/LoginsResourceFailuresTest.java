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
class LoginsResourceFailuresTest {

	private static final String LOGIN_NAME = "joe01doe";

	// TODO: Uncomment and make this work
	// @MockitoBean
	// private ManagerResponsibilityService managerResponsibilityServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getManagerResponsibilitiesByLoginNameInvalidMunicipalityId() {

		// Act
		final var response = webTestClient.get()
			.uri("/{municipalityId}/logins/{loginName}/manager-responsibilities", "invalid", LOGIN_NAME)
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
			.containsExactly(tuple("getManagerResponsibilitiesByLoginName.municipalityId", "not a valid municipality ID"));

		// TODO: Uncomment and make this work
		// verifyNoInteractions(managerResponsibilityServiceMock);
	}
}
