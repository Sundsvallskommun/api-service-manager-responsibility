package se.sundsvall.managerresponsibility.service;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static se.sundsvall.managerresponsibility.service.mapper.ManagerResponsibilityMapper.toManagerResponsibilityList;

import java.util.List;
import org.springframework.stereotype.Service;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;
import se.sundsvall.managerresponsibility.integration.db.ManagerResponsibilityRepository;

/**
 * Service for retrieving manager responsibility information.
 * Provides methods to look up managerial organizational responsibilities
 * by organization ID, person ID, or login name.
 */
@Service
public class ManagerResponsibilityService {

	private final ManagerResponsibilityRepository managerResponsibilityRepository;

	public ManagerResponsibilityService(final ManagerResponsibilityRepository managerResponsibilityRepository) {
		this.managerResponsibilityRepository = managerResponsibilityRepository;
	}

	/**
	 * Finds manager responsibilities by organization ID.
	 *
	 * @param  orgId the organization ID to search for (can be partial match in pipe-delimited list)
	 * @return       list of manager responsibilities, empty list if orgId is null or blank
	 */
	public List<ManagerResponsibility> findByOrgId(final String orgId) {
		if (isBlank(orgId)) {
			return emptyList();
		}

		return toManagerResponsibilityList(managerResponsibilityRepository.findByOrgId(orgId));
	}

	/**
	 * Finds manager responsibilities by person ID (UUID).
	 *
	 * @param  personId the person UUID to search for
	 * @return          list of manager responsibilities, empty list if personId is null or blank
	 */
	public List<ManagerResponsibility> findByPersonId(final String personId) {
		if (isBlank(personId)) {
			return emptyList();
		}

		return toManagerResponsibilityList(managerResponsibilityRepository.findByPersonId(personId));
	}

	/**
	 * Finds manager responsibilities by login name.
	 *
	 * @param  loginName the login name to search for
	 * @return           list of manager responsibilities, empty list if loginName is null or blank
	 */
	public List<ManagerResponsibility> findByLoginName(final String loginName) {
		if (isBlank(loginName)) {
			return emptyList();
		}

		return toManagerResponsibilityList(managerResponsibilityRepository.findByLoginName(loginName));
	}
}
