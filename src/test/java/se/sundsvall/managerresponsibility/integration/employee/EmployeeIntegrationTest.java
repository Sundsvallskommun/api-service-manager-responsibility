package se.sundsvall.managerresponsibility.integration.employee;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static se.sundsvall.managerresponsibility.integration.employee.EmployeeIntegration.DOMAIN;

import generated.se.sundsvall.employee.PortalPersonData;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeIntegrationTest {

	private static final String MUNICIPALITY_ID = "2281";
	private static final String LOGIN_NAME = "user01";

	@Mock
	private EmployeeClient employeeClientMock;

	@InjectMocks
	private EmployeeIntegration employeeIntegration;

	@Test
	void employeeExistsReturnsTrue() {

		// Arrange
		when(employeeClientMock.getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, LOGIN_NAME))
			.thenReturn(Optional.of(new PortalPersonData()));

		// Act
		final var result = employeeIntegration.employeeExists(MUNICIPALITY_ID, LOGIN_NAME);

		// Assert
		assertThat(result).isTrue();
		verify(employeeClientMock).getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, LOGIN_NAME);
		verifyNoMoreInteractions(employeeClientMock);
	}

	@Test
	void employeeExistsReturnsFalse() {

		// Arrange
		when(employeeClientMock.getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, LOGIN_NAME))
			.thenReturn(empty());

		// Act
		final var result = employeeIntegration.employeeExists(MUNICIPALITY_ID, LOGIN_NAME);

		// Assert
		assertThat(result).isFalse();
		verify(employeeClientMock).getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, LOGIN_NAME);
		verifyNoMoreInteractions(employeeClientMock);
	}
}
