package se.sundsvall.managerresponsibility.integration.employee;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static se.sundsvall.managerresponsibility.integration.employee.configuration.EmployeeConfiguration.CLIENT_ID;

import generated.se.sundsvall.employee.PortalPersonData;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import se.sundsvall.managerresponsibility.integration.employee.configuration.EmployeeConfiguration;

/**
 * Feign client for the Employee service API.
 *
 * <p>
 * Used to verify employee existence. The client is configured with a circuit breaker
 * for resilience and automatically handles 404 responses as empty Optional.
 */
@FeignClient(
	name = CLIENT_ID,
	url = "${integration.employee.url}",
	configuration = EmployeeConfiguration.class,
	dismiss404 = true)
@CircuitBreaker(name = CLIENT_ID)
public interface EmployeeClient {

	/**
	 * Retrieves employee portal data by domain and login name.
	 *
	 * @param  municipalityId the municipality ID
	 * @param  domain         the employee domain (e.g., "PERSONAL")
	 * @param  loginName      the login name of the employee
	 * @return                Optional containing PortalPersonData if found, empty Optional if not found (404)
	 */
	@GetMapping(path = "/{municipalityId}/portalpersondata/{domain}/{loginName}", produces = APPLICATION_JSON_VALUE)
	Optional<PortalPersonData> getEmployeeByDomainAndLoginName(
		@PathVariable String municipalityId,
		@PathVariable String domain,
		@PathVariable String loginName);
}
