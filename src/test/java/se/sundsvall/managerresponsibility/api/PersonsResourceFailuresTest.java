package se.sundsvall.managerresponsibility.api;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.dept44.problem.violations.ConstraintViolationProblem;
import se.sundsvall.dept44.problem.violations.Violation;
import se.sundsvall.managerresponsibility.Application;
import se.sundsvall.managerresponsibility.service.ManagerResponsibilityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ActiveProfiles("junit")
@AutoConfigureWebTestClient
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
class PersonsResourceFailuresTest {

	private static final String MUNICIPALITY_ID = "2281";
	private static final String PERSON_ID = UUID.randomUUID().toString();

	@MockitoBean
	private ManagerResponsibilityService managerResponsibilityServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getManagerResponsibilitiesByPersonIdInvalidMunicipalityId() {

		// Act
		final var response = webTestClient.get()
			.uri("/{municipalityId}/persons/{personId}/manager-responsibilities", "invalid", PERSON_ID)
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
			.extracting(Violation::field, Violation::message)
			.containsExactly(tuple("getManagerResponsibilitiesByPersonId.municipalityId", "not a valid municipality ID"));

		verifyNoInteractions(managerResponsibilityServiceMock);
	}

	@Test
	void getManagerResponsibilitiesByPersonIdInvalidPersonId() {

		// Act
		final var response = webTestClient.get()
			.uri("/{municipalityId}/persons/{personId}/manager-responsibilities", MUNICIPALITY_ID, "invalid")
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
			.extracting(Violation::field, Violation::message)
			.containsExactly(tuple("getManagerResponsibilitiesByPersonId.personId", "not a valid UUID"));

		verifyNoInteractions(managerResponsibilityServiceMock);
	}
}
