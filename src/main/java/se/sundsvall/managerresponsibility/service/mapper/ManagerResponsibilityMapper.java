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

/**
 * Mapper class for converting between {@link ManagerResponsibilityEntity} and {@link ManagerResponsibility} DTOs.
 */
public final class ManagerResponsibilityMapper {

	private ManagerResponsibilityMapper() {}

	/**
	 * Converts a list of entities to a list of DTOs.
	 * Null entities are filtered out from the result.
	 *
	 * @param  managerResponsibilityEntityList the list of entities to convert, may be null
	 * @return                                 list of DTOs, never null (empty list if input is null or empty)
	 */
	public static List<ManagerResponsibility> toManagerResponsibilityList(final List<ManagerResponsibilityEntity> managerResponsibilityEntityList) {
		return Optional.ofNullable(managerResponsibilityEntityList).orElse(emptyList()).stream()
			.map(ManagerResponsibilityMapper::toManagerResponsibility)
			.filter(Objects::nonNull)
			.toList();
	}

	/**
	 * Converts a single entity to a DTO.
	 * Login name and person ID are converted to lowercase.
	 *
	 * @param  managerResponsibilityEntity the entity to convert, may be null
	 * @return                             the converted DTO, or null if input is null
	 */
	public static ManagerResponsibility toManagerResponsibility(final ManagerResponsibilityEntity managerResponsibilityEntity) {
		return Optional.ofNullable(managerResponsibilityEntity)
			.map(entity -> ManagerResponsibility.create()
				.withLoginName(lowerCase(entity.getLoginName()))
				.withOrgList(toOrgIdList(entity))
				.withPersonId(lowerCase(entity.getPersonId())))
			.orElse(null);
	}

	/**
	 * Parses the pipe-delimited orgList string from an entity into a list of organization IDs.
	 * Example: "123|456|789" becomes ["123", "456", "789"].
	 *
	 * @param  managerResponsibilityEntity the entity containing the orgList
	 * @return                             list of organization IDs, empty list if orgList is null or blank
	 */
	public static List<String> toOrgIdList(final ManagerResponsibilityEntity managerResponsibilityEntity) {
		return Optional.ofNullable(managerResponsibilityEntity.getOrgList())
			.filter(StringUtils::isNotBlank)
			.stream()
			.flatMap(s -> Arrays.stream(s.split("\\|")))
			.map(String::trim)
			.filter(StringUtils::isNotEmpty)
			.toList();
	}
}
