package se.sundsvall.managerresponsibility.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;
import se.sundsvall.dept44.common.validators.annotation.ValidMunicipalityId;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;

@RestController
@Validated
@RequestMapping(value = "/{municipalityId}/organizations/{orgId}/manager-responsibilities")
@Tag(name = "Organization")
@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = {
	Problem.class, ConstraintViolationProblem.class
})))
@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
class OrganizationsResource {

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@Operation(operationId = "getManagerResponsibilitiesByOrganizationId", summary = "Get manager responsibilities", responses = @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true))
	ResponseEntity<List<ManagerResponsibility>> getManagerResponsibilitiesByOrganizationId(
		@Parameter(name = "municipalityId", description = "Municipality ID", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@Parameter(name = "orgId", description = "Organization ID", example = "123") @PathVariable final String orgId) {

		return ok(List.of(ManagerResponsibility.create().withLoginName("loginName").withOrgList(List.of("org1"))));
	}
}
