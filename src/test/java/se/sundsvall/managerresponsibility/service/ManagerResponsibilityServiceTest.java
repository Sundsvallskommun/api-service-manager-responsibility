package se.sundsvall.managerresponsibility.service;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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
import se.sundsvall.managerresponsibility.integration.employee.EmployeeIntegration;

@ExtendWith(MockitoExtension.class)
class ManagerResponsibilityServiceTest {

	private static final String MUNICIPALITY_ID = "2281";

	@Mock
	private ManagerResponsibilityRepository repositoryMock;

	@Mock
	private EmployeeIntegration employeeIntegrationMock;

	@InjectMocks
	private ManagerResponsibilityService service;

	@Test
	void findByOrgId() {

		// Arrange
		final var orgId = "1234";
		final var loginName = "user01";
		final var entity = ManagerResponsibilityEntity.create().withLoginName(loginName);

		when(repositoryMock.findByOrgId(orgId)).thenReturn(List.of(entity));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, loginName)).thenReturn(true);

		// Act
		final var result = service.findByOrgId(MUNICIPALITY_ID, orgId);

		// Assert
		assertThat(result).isNotNull().hasSize(1);
		verify(repositoryMock).findByOrgId(orgId);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, loginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByOrgIdFiltersNonExistingEmployees() {

		// Arrange
		final var orgId = "1234";
		final var existingLoginName = "existing_user";
		final var nonExistingLoginName = "non_existing_user";
		final var entity1 = ManagerResponsibilityEntity.create().withLoginName(existingLoginName);
		final var entity2 = ManagerResponsibilityEntity.create().withLoginName(nonExistingLoginName);

		when(repositoryMock.findByOrgId(orgId)).thenReturn(List.of(entity1, entity2));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, existingLoginName)).thenReturn(true);
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, nonExistingLoginName)).thenReturn(false);

		// Act
		final var result = service.findByOrgId(MUNICIPALITY_ID, orgId);

		// Assert
		assertThat(result).isNotNull().hasSize(1);
		assertThat(result.getFirst().getLoginName()).isEqualTo(existingLoginName);
		verify(repositoryMock).findByOrgId(orgId);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, existingLoginName);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, nonExistingLoginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = " ")
	void findByOrgIdWithNullOrBlank(final String orgId) {

		// Act
		final var result = service.findByOrgId(MUNICIPALITY_ID, orgId);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verifyNoInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByPersonId() {

		// Arrange
		final var personId = "57B17CE9-F1EE-49C4-ABCD-1234567890AB";
		final var loginName = "user01";
		final var entity = ManagerResponsibilityEntity.create().withLoginName(loginName);

		when(repositoryMock.findByPersonId(personId)).thenReturn(List.of(entity));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, loginName)).thenReturn(true);

		// Act
		final var result = service.findByPersonId(MUNICIPALITY_ID, personId);

		// Assert
		assertThat(result).isNotNull().hasSize(1);
		verify(repositoryMock).findByPersonId(personId);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, loginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByPersonIdFiltersNonExistingEmployees() {

		// Arrange
		final var personId = "57B17CE9-F1EE-49C4-ABCD-1234567890AB";
		final var existingLoginName = "existing_user";
		final var nonExistingLoginName = "non_existing_user";
		final var entity1 = ManagerResponsibilityEntity.create().withLoginName(existingLoginName);
		final var entity2 = ManagerResponsibilityEntity.create().withLoginName(nonExistingLoginName);

		when(repositoryMock.findByPersonId(personId)).thenReturn(List.of(entity1, entity2));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, existingLoginName)).thenReturn(true);
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, nonExistingLoginName)).thenReturn(false);

		// Act
		final var result = service.findByPersonId(MUNICIPALITY_ID, personId);

		// Assert
		assertThat(result).isNotNull().hasSize(1);
		assertThat(result.getFirst().getLoginName()).isEqualTo(existingLoginName);
		verify(repositoryMock).findByPersonId(personId);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, existingLoginName);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, nonExistingLoginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = " ")
	void findByPersonIdWithNullOrBlank(final String personId) {

		// Act
		final var result = service.findByPersonId(MUNICIPALITY_ID, personId);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verifyNoInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByLoginName() {

		// Arrange
		final var loginName = "user01";
		final var entity = ManagerResponsibilityEntity.create().withLoginName(loginName);

		when(repositoryMock.findByLoginName(loginName)).thenReturn(List.of(entity));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, loginName)).thenReturn(true);

		// Act
		final var result = service.findByLoginName(MUNICIPALITY_ID, loginName);

		// Assert
		assertThat(result).isNotNull().hasSize(1);
		verify(repositoryMock).findByLoginName(loginName);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, loginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByLoginNameFiltersNonExistingEmployees() {

		// Arrange
		final var loginName = "user01";
		final var entity = ManagerResponsibilityEntity.create().withLoginName(loginName);

		when(repositoryMock.findByLoginName(loginName)).thenReturn(List.of(entity));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, loginName)).thenReturn(false);

		// Act
		final var result = service.findByLoginName(MUNICIPALITY_ID, loginName);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verify(repositoryMock).findByLoginName(loginName);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, loginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = " ")
	void findByLoginNameWithNullOrBlank(final String loginName) {

		// Act
		final var result = service.findByLoginName(MUNICIPALITY_ID, loginName);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verifyNoInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByOrgIdWithEmptyResult() {

		// Arrange
		final var orgId = "1234";

		when(repositoryMock.findByOrgId(orgId)).thenReturn(emptyList());

		// Act
		final var result = service.findByOrgId(MUNICIPALITY_ID, orgId);

		// Assert
		assertThat(result).isNotNull().isEmpty();
		verify(repositoryMock).findByOrgId(orgId);
		verify(employeeIntegrationMock, never()).employeeExists(anyString(), anyString());
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}

	@Test
	void findByOrgIdFiltersEntitiesWithBlankLoginName() {

		// Arrange
		final var orgId = "1234";
		final var validLoginName = "valid_user";
		final var entity1 = ManagerResponsibilityEntity.create().withLoginName(validLoginName);
		final var entity2 = ManagerResponsibilityEntity.create().withLoginName(null);
		final var entity3 = ManagerResponsibilityEntity.create().withLoginName("");
		final var entity4 = ManagerResponsibilityEntity.create().withLoginName("   ");

		when(repositoryMock.findByOrgId(orgId)).thenReturn(List.of(entity1, entity2, entity3, entity4));
		when(employeeIntegrationMock.employeeExists(MUNICIPALITY_ID, validLoginName)).thenReturn(true);

		// Act
		final var result = service.findByOrgId(MUNICIPALITY_ID, orgId);

		// Assert
		assertThat(result).isNotNull().hasSize(1);
		assertThat(result.getFirst().getLoginName()).isEqualTo(validLoginName);
		verify(repositoryMock).findByOrgId(orgId);
		verify(employeeIntegrationMock).employeeExists(MUNICIPALITY_ID, validLoginName);
		verifyNoMoreInteractions(repositoryMock, employeeIntegrationMock);
	}
}
