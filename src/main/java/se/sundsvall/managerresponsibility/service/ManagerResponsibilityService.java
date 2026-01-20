package se.sundsvall.managerresponsibility.service;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static se.sundsvall.managerresponsibility.service.mapper.ManagerResponsibilityMapper.toManagerResponsibilityList;

import java.util.List;
import org.springframework.stereotype.Service;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;
import se.sundsvall.managerresponsibility.integration.db.ManagerResponsibilityRepository;
import se.sundsvall.managerresponsibility.integration.db.model.ManagerResponsibilityEntity;
import se.sundsvall.managerresponsibility.integration.employee.EmployeeIntegration;

/**
 * Service for retrieving manager responsibility information.
 * Provides methods to look up managerial organizational responsibilities
 * by organization ID, person ID, or login name.
 *
 * <p>
 * All results are filtered to only include managers who exist in the Employee service.
 * Entities with null or blank login names are also excluded from results.
 */
@Service
public class ManagerResponsibilityService {

	private final ManagerResponsibilityRepository managerResponsibilityRepository;
	private final EmployeeIntegration employeeIntegration;

	public ManagerResponsibilityService(final ManagerResponsibilityRepository managerResponsibilityRepository, final EmployeeIntegration employeeIntegration) {
		this.managerResponsibilityRepository = managerResponsibilityRepository;
		this.employeeIntegration = employeeIntegration;
	}

	/**
	 * Finds manager responsibilities by organization ID.
	 *
	 * <p>
	 * Results are filtered to only include managers who exist in the Employee service.
	 *
	 * @param  municipalityId the municipality ID used for employee verification
	 * @param  orgId          the organization ID to search for (can be partial match in pipe-delimited list)
	 * @return                list of manager responsibilities filtered by existing employees, empty list if orgId is null
	 *                        or blank
	 */
	public List<ManagerResponsibility> findByOrgId(final String municipalityId, final String orgId) {
		if (isBlank(orgId)) {
			return emptyList();
		}

		return toManagerResponsibilityList(filterByExistingEmployees(municipalityId, managerResponsibilityRepository.findByOrgId(orgId)));
	}

	/**
	 * Finds manager responsibilities by person ID (UUID).
	 *
	 * <p>
	 * Results are filtered to only include managers who exist in the Employee service.
	 *
	 * @param  municipalityId the municipality ID used for employee verification
	 * @param  personId       the person UUID to search for
	 * @return                list of manager responsibilities filtered by existing employees, empty list if personId is
	 *                        null or blank
	 */
	public List<ManagerResponsibility> findByPersonId(final String municipalityId, final String personId) {
		if (isBlank(personId)) {
			return emptyList();
		}

		return toManagerResponsibilityList(filterByExistingEmployees(municipalityId, managerResponsibilityRepository.findByPersonId(personId)));
	}

	/**
	 * Finds manager responsibilities by login name.
	 *
	 * <p>
	 * Results are filtered to only include managers who exist in the Employee service.
	 *
	 * @param  municipalityId the municipality ID used for employee verification
	 * @param  loginName      the login name to search for
	 * @return                list of manager responsibilities filtered by existing employees, empty list if loginName is
	 *                        null or blank
	 */
	public List<ManagerResponsibility> findByLoginName(final String municipalityId, final String loginName) {
		if (isBlank(loginName)) {
			return emptyList();
		}

		return toManagerResponsibilityList(filterByExistingEmployees(municipalityId, managerResponsibilityRepository.findByLoginName(loginName)));
	}

	private List<ManagerResponsibilityEntity> filterByExistingEmployees(final String municipalityId, final List<ManagerResponsibilityEntity> entities) {
		return entities.stream()
			.filter(entity -> isNotBlank(entity.getLoginName()))
			.filter(entity -> employeeIntegration.employeeExists(municipalityId, entity.getLoginName().toLowerCase()))
			.toList();
	}
}
