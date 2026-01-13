package se.sundsvall.managerresponsibility.integration.db.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ManagerResponsibilityEntityTest {

	@Test
	void testBean() {
		assertThat(ManagerResponsibilityEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var id = 1L;
		final var loginName = "loginName";
		final var orgList = "org1|org2|org3";
		final var personId = UUID.randomUUID().toString();

		final var bean = ManagerResponsibilityEntity.create()
			.withId(id)
			.withLoginName(loginName)
			.withOrgList(orgList)
			.withPersonId(personId);

		assertThat(bean).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(bean.getId()).isEqualTo(id);
		assertThat(bean.getLoginName()).isEqualTo(loginName);
		assertThat(bean.getOrgList()).isEqualTo(orgList);
		assertThat(bean.getPersonId()).isEqualTo(personId);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(ManagerResponsibilityEntity.create()).hasAllNullFieldsOrProperties();
		assertThat(new ManagerResponsibilityEntity()).hasAllNullFieldsOrProperties();
	}

	@ParameterizedTest
	@MethodSource("testToOrgIdsProvider")
	void testToOrgIds(String orgListString, List<String> expectedList) {

		final var id = 1L;
		final var loginName = "loginName";
		final var personId = UUID.randomUUID().toString();

		final var bean = ManagerResponsibilityEntity.create()
			.withId(id)
			.withLoginName(loginName)
			.withOrgList(orgListString)
			.withPersonId(personId);

		assertThat(bean).isNotNull();
		assertThat(bean.toOrgIds()).isEqualTo(expectedList);
	}

	private static Stream<Arguments> testToOrgIdsProvider() {
		return Stream.of(
			Arguments.of("org1|org2|org3", List.of("org1", "org2", "org3")),
			Arguments.of("|org1|org2|org3|", List.of("org1", "org2", "org3")),
			Arguments.of(" | org1 | org2 | org3 | ", List.of("org1", "org2", "org3")),
			Arguments.of("", emptyList()),
			Arguments.of("||", emptyList()),
			Arguments.of(null, emptyList()));
	}
}
