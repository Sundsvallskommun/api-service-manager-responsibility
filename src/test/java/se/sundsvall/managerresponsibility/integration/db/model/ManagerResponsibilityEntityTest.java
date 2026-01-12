package se.sundsvall.managerresponsibility.integration.db.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

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

	@Test
	void testToOrgIds() {

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
		assertThat(bean.toOrgIds()).isEqualTo(List.of("org1", "org2", "org3"));
	}
}
