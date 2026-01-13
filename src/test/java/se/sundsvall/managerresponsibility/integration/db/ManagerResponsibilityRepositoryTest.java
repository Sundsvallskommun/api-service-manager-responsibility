package se.sundsvall.managerresponsibility.integration.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.sundsvall.managerresponsibility.integration.db.model.ManagerResponsibilityEntity;

/**
 * ManagerResponsibilityRepository tests.
 *
 * @see /src/test/resources/db/scripts/testdata.sql for data setup.
 */
@DataJpaTest
@Transactional(readOnly = true)
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("junit")
class ManagerResponsibilityRepositoryTest {

	private static final long ID = 1L;

	@Autowired
	private ManagerResponsibilityRepository managerResponsibilityRepository;

	@Test
	void findAll() {

		// Act
		final var result = managerResponsibilityRepository.findAll();

		// Assert
		assertThat(result)
			.isNotNull()
			.hasSize(4)
			.extracting(ManagerResponsibilityEntity::getLoginName)
			.containsExactlyInAnyOrder("user01", "user02", "user03", "user04");
	}

	@Test
	void findById() {

		// Act
		final var result = managerResponsibilityRepository.findById(ID);

		// Assert
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(ID);
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"9937", "1281", "1302"
	})
	void findByOrgId(String orgId) {

		// Act
		final var result = managerResponsibilityRepository.findByOrgId(orgId);

		// Assert
		assertThat(result).hasSize(1);
		final var entity = result.getFirst();
		assertThat(entity.getLoginName()).isEqualTo("user01");
		assertThat(entity.toOrgIds()).containsExactlyInAnyOrder("9937", "1281", "1302");
	}

	@Test
	void findByOrgIdOverlapping() {

		// Arrange
		final var orgId = "1401";

		// Act
		final var result = managerResponsibilityRepository.findByOrgId(orgId);

		// Assert
		assertThat(result)
			.isNotEmpty()
			.hasSize(2)
			.extracting(ManagerResponsibilityEntity::getLoginName)
			.containsExactlyInAnyOrder("user03", "user04");
	}

	void findByOrgIdNotFound() {

		// Act
		final var result = managerResponsibilityRepository.findByOrgId("non-existing");

		// Assert
		assertThat(result)
			.isNotNull()
			.isEmpty();
	}

	@Test
	void findByPersonId() {

		// Arrange
		final var personId = "a12f98c4-8b42-4f3a-9f12-abcdef123456";

		// Act
		final var result = managerResponsibilityRepository.findByPersonId(personId);

		// Assert
		assertThat(result).hasSize(1);
		final var entity = result.getFirst();
		assertThat(entity.toOrgIds()).containsExactlyInAnyOrder("9936");
		assertThat(entity.getLoginName()).isEqualTo("user02");
	}

	@Test
	void findByPersonIdNotFound() {

		// Arrange
		final var personId = "51d88368-0c7e-4b8b-b9e2-6bf1e6999a36"; // non existing

		// Act
		final var result = managerResponsibilityRepository.findByPersonId(personId);

		// Assert
		assertThat(result)
			.isNotNull()
			.isEmpty();
	}

	@Test
	void findByLoginName() {

		// Arrange
		final var loginName = "user02";

		// Act
		final var result = managerResponsibilityRepository.findByLoginName(loginName);

		// Assert
		assertThat(result).hasSize(1);
		final var entity = result.getFirst();
		assertThat(entity.toOrgIds()).containsExactlyInAnyOrder("9936");
		assertThat(entity.getPersonId()).isEqualToIgnoringCase("a12f98c4-8b42-4f3a-9f12-abcdef123456");
	}

	@Test
	void findByLoginNameNotFound() {

		// Arrange
		final var loginName = "non-existing";

		// Act
		final var result = managerResponsibilityRepository.findByLoginName(loginName);

		// Assert
		assertThat(result)
			.isNotNull()
			.isEmpty();
	}
}
