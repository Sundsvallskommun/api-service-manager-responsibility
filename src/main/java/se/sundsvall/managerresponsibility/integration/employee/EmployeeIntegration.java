package se.sundsvall.managerresponsibility.integration.employee;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Integration component for verifying employee existence via the Employee service.
 *
 * <p>
 * Results are cached to reduce load on the Employee API. Cache key is composed of
 * municipalityId and loginName. The login name should be provided in lowercase to ensure
 * consistent cache hits.
 *
 * @see EmployeeClient
 */
@Component
public class EmployeeIntegration {

	static final String DOMAIN = "PERSONAL";
	static final String EMPLOYEE_EXISTS_CACHE = "employeeExists";

	private final EmployeeClient employeeClient;

	public EmployeeIntegration(final EmployeeClient employeeClient) {
		this.employeeClient = employeeClient;
	}

	/**
	 * Checks if an employee exists in the Employee service.
	 *
	 * <p>
	 * Results are cached with key format: "{municipalityId}:{loginName}".
	 * The loginName should be provided in lowercase for consistent cache behavior.
	 *
	 * @param  municipalityId the municipality ID
	 * @param  loginName      the login name to check (should be lowercase)
	 * @return                true if the employee exists, false otherwise
	 */
	@Cacheable(value = EMPLOYEE_EXISTS_CACHE, key = "#municipalityId + ':' + #loginName")
	public boolean employeeExists(final String municipalityId, final String loginName) {
		return employeeClient.getEmployeeByDomainAndLoginName(municipalityId, DOMAIN, loginName).isPresent();
	}
}
