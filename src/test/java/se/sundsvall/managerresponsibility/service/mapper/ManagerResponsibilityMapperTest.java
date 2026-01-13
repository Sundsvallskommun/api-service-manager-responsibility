package se.sundsvall.managerresponsibility.service.mapper;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.sundsvall.managerresponsibility.integration.db.model.ManagerResponsibilityEntity;

class ManagerResponsibilityMapperTest {

	private static final Long ID = 1L;
	private static final String LOGIN_NAME = "loginName";
	private static final String PERSON_ID = randomUUID().toString();
	private static final String ORG_IDS = "org1|org2|org3";

	@Test
	void toManagerResponsibility() {

		// Arrange
		final var bean = ManagerResponsibilityEntity.create()
			.withId(ID)
			.withLoginName(LOGIN_NAME)
			.withOrgList(ORG_IDS)
			.withPersonId(PERSON_ID);

		// Act
		final var result = ManagerResponsibilityMapper.toManagerResponsibility(bean);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getLoginName())
			.isLowerCase()
			.isEqualToIgnoringCase(LOGIN_NAME);
		assertThat(result.getPersonId())
			.isLowerCase()
			.isEqualToIgnoringCase(PERSON_ID);
		assertThat(result.getOrgList()).isEqualTo(List.of("org1", "org2", "org3"));
	}

	@Test
	void toManagerResponsibilityWhenNull() {

		// Act
		final var result = ManagerResponsibilityMapper.toManagerResponsibility(null);

		// Assert
		assertThat(result).isNull();
	}

	@Test
	void toManagerResponsibilityList() {

		// Arrange
		final var bean = ManagerResponsibilityEntity.create()
			.withId(ID)
			.withLoginName(LOGIN_NAME)
			.withOrgList(ORG_IDS)
			.withPersonId(PERSON_ID);

		// Act
		final var result = ManagerResponsibilityMapper.toManagerResponsibilityList(List.of(bean));

		// Assert
		assertThat(result)
			.isNotNull()
			.hasSize(1);
		final var resultElement = result.getFirst();
		assertThat(resultElement.getLoginName())
			.isLowerCase()
			.isEqualToIgnoringCase(LOGIN_NAME);
		assertThat(resultElement.getPersonId())
			.isLowerCase()
			.isEqualToIgnoringCase(PERSON_ID);
		assertThat(resultElement.getOrgList()).isEqualTo(List.of("org1", "org2", "org3"));
	}

	@Test
	void toManagerResponsibilityListWhenNull() {

		// Act
		final var result = ManagerResponsibilityMapper.toManagerResponsibilityList(null);

		// Assert
		assertThat(result)
			.isNotNull()
			.isEmpty();
	}

	@Test
	void toManagerResponsibilityListWhenEmpty() {

		// Act
		final var result = ManagerResponsibilityMapper.toManagerResponsibilityList(emptyList());

		// Assert
		assertThat(result)
			.isNotNull()
			.isEmpty();
	}

	@ParameterizedTest
	@MethodSource("toOrgIdListProvider")
	void testToOrgList(String orgListString, List<String> expectedList) {

		// Arrange
		final var bean = ManagerResponsibilityEntity.create()
			.withId(ID)
			.withLoginName(LOGIN_NAME)
			.withOrgList(orgListString)
			.withPersonId(PERSON_ID);

		// Act
		final var result = ManagerResponsibilityMapper.toOrgIdList(bean);

		// Assert
		assertThat(result).isEqualTo(expectedList);
	}

	private static Stream<Arguments> toOrgIdListProvider() {
		return Stream.of(
			Arguments.of("org1|org2|org3", List.of("org1", "org2", "org3")),
			Arguments.of("|org1|org2|org3|", List.of("org1", "org2", "org3")),
			Arguments.of(" | org1 | org2 | org3 | ", List.of("org1", "org2", "org3")),
			Arguments.of("", emptyList()),
			Arguments.of(" ", emptyList()),
			Arguments.of("||", emptyList()),
			Arguments.of(null, emptyList()));
	}
}
