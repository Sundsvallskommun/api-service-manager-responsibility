package se.sundsvall.managerresponsibility.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.sundsvall.managerresponsibility.integration.db.ManagerResponsibilityRepository;
import se.sundsvall.managerresponsibility.integration.db.model.ManagerResponsibilityEntity;

@ExtendWith(MockitoExtension.class)
class ManagerResponsibilityServiceTest {

	@Mock
	private ManagerResponsibilityRepository repositoryMock;

	@InjectMocks
	private ManagerResponsibilityService service;

	@Test
	void findByOrgId() {

		// Arrange
		final var orgId = "1234";

		when(repositoryMock.findByOrgId(orgId)).thenReturn(List.of(ManagerResponsibilityEntity.create()));

		// Act
		final var result = service.findByOrgId(orgId);

		// Assert
		assertThat(result).isNotNull();
		verify(repositoryMock).findByOrgId(orgId);
		verifyNoMoreInteractions(repositoryMock);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = " ")
	void findByOrgIdWithNullOrBlank(final String orgId) {

		// Act
		final var result = service.findByOrgId(orgId);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verifyNoInteractions(repositoryMock);
	}

	@Test
	void findByPersonId() {

		// Arrange
		final var personId = "57B17CE9-F1EE-49C4-ABCD-1234567890AB";

		when(repositoryMock.findByPersonId(personId)).thenReturn(List.of(ManagerResponsibilityEntity.create()));

		// Act
		final var result = service.findByPersonId(personId);

		// Assert
		assertThat(result).isNotNull();
		verify(repositoryMock).findByPersonId(personId);
		verifyNoMoreInteractions(repositoryMock);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = " ")
	void findByPersonIdWithNullOrBlank(final String personId) {

		// Act
		final var result = service.findByPersonId(personId);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verifyNoInteractions(repositoryMock);
	}

	@Test
	void findByLoginName() {

		// Arrange
		final var loginName = "user01";

		when(repositoryMock.findByLoginName(loginName)).thenReturn(List.of(ManagerResponsibilityEntity.create()));

		// Act
		final var result = service.findByLoginName(loginName);

		// Assert
		assertThat(result).isNotNull();
		verify(repositoryMock).findByLoginName(loginName);
		verifyNoMoreInteractions(repositoryMock);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = " ")
	void findByLoginNameWithNullOrBlank(final String loginName) {

		// Act
		final var result = service.findByLoginName(loginName);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verifyNoInteractions(repositoryMock);
	}
}
