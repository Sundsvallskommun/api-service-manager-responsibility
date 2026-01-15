package se.sundsvall.managerresponsibility.service.mapper;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.lowerCase;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import se.sundsvall.managerresponsibility.api.model.ManagerResponsibility;
import se.sundsvall.managerresponsibility.integration.db.model.ManagerResponsibilityEntity;

public final class ManagerResponsibilityMapper {

	private ManagerResponsibilityMapper() {}

	public static List<ManagerResponsibility> toManagerResponsibilityList(List<ManagerResponsibilityEntity> managerResponsibilityEntityList) {
		return Optional.ofNullable(managerResponsibilityEntityList).orElse(emptyList()).stream()
			.map(ManagerResponsibilityMapper::toManagerResponsibility)
			.filter(Objects::nonNull)
			.toList();
	}

	public static ManagerResponsibility toManagerResponsibility(ManagerResponsibilityEntity managerResponsibilityEntity) {
		return Optional.ofNullable(managerResponsibilityEntity)
			.map(entity -> ManagerResponsibility.create()
				.withLoginName(lowerCase(entity.getLoginName()))
				.withOrgList(toOrgIdList(entity))
				.withPersonId(lowerCase(entity.getPersonId())))
			.orElse(null);
	}

	public static List<String> toOrgIdList(ManagerResponsibilityEntity managerResponsibilityEntity) {
		return Optional.ofNullable(managerResponsibilityEntity.getOrgList())
			.filter(StringUtils::isNotBlank)
			.stream()
			.flatMap(s -> Arrays.stream(s.split("\\|")))
			.map(String::trim)
			.filter(StringUtils::isNotEmpty)
			.toList();
	}
}
