package se.sundsvall.managerresponsibility.service;

import static se.sundsvall.managerresponsibility.service.mapper.ManagerResponsibilityMapper.toManagerResponsibilityList;

import java.util.List;
import org.springframework.stereotype.Service;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;
import se.sundsvall.managerresponsibility.integration.db.ManagerResponsibilityRepository;

@Service
public class ManagerResponsibilityService {

	private ManagerResponsibilityRepository managerResponsibilityRepository;

	public ManagerResponsibilityService(final ManagerResponsibilityRepository managerResponsibilityRepository) {
		this.managerResponsibilityRepository = managerResponsibilityRepository;
	}

	public List<ManagerResponsibility> findByOrgId(String orgId) {
		return toManagerResponsibilityList(managerResponsibilityRepository.findByOrgId(orgId));
	}

	public List<ManagerResponsibility> findByPersonId(String personId) {
		return toManagerResponsibilityList(managerResponsibilityRepository.findByPersonId(personId));
	}

	public List<ManagerResponsibility> findByLoginName(String loginName) {
		return toManagerResponsibilityList(managerResponsibilityRepository.findByLoginName(loginName));
	}
}
