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
		assertThat(result)
			.isNotEmpty()
			.extracting(ManagerResponsibilityEntity::toOrgIds)
			.allSatisfy(orgIds -> assertThat(orgIds).containsExactlyInAnyOrder("9937", "1281", "1302"));
	}

	void findByOrgIdNotFound() {

		// Act
		final var result = managerResponsibilityRepository.findByOrgId("non-existing");

		// Assert
		assertThat(result)
			.isNotNull()
			.isEmpty();
	}
}
