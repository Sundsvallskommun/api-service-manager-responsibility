package se.sundsvall.managerresponsibility.integration.employee;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeIntegration {

	static final String DOMAIN = "PERSONAL";
	static final String EMPLOYEE_EXISTS_CACHE = "employeeExists";

	private final EmployeeClient employeeClient;

	public EmployeeIntegration(final EmployeeClient employeeClient) {
		this.employeeClient = employeeClient;
	}

	@Cacheable(value = EMPLOYEE_EXISTS_CACHE, key = "#municipalityId + ':' + #loginName")
	public boolean employeeExists(final String municipalityId, final String loginName) {
		return employeeClient.getEmployeeByDomainAndLoginName(municipalityId, DOMAIN, loginName).isPresent();
	}
}
