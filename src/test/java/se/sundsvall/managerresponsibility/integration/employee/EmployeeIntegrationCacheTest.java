package se.sundsvall.managerresponsibility.integration.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static se.sundsvall.managerresponsibility.integration.employee.EmployeeIntegration.DOMAIN;
import static se.sundsvall.managerresponsibility.integration.employee.EmployeeIntegration.EMPLOYEE_EXISTS_CACHE;

import generated.se.sundsvall.employee.PortalPersonData;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.sundsvall.managerresponsibility.Application;

@ActiveProfiles("junit")
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
class EmployeeIntegrationCacheTest {

	private static final String MUNICIPALITY_ID = "2281";
	private static final String LOGIN_NAME = "user01";

	@MockitoBean
	private EmployeeClient employeeClientMock;

	@Autowired
	private EmployeeIntegration employeeIntegration;

	@Autowired
	private CacheManager cacheManager;

	@BeforeEach
	void clearCache() {
		final var cache = cacheManager.getCache(EMPLOYEE_EXISTS_CACHE);
		if (cache != null) {
			cache.clear();
		}
	}

	@Test
	void employeeExistsCachesResult() {

		// Arrange
		when(employeeClientMock.getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, LOGIN_NAME))
			.thenReturn(Optional.of(new PortalPersonData()));

		// Act - call multiple times
		final var result1 = employeeIntegration.employeeExists(MUNICIPALITY_ID, LOGIN_NAME);
		final var result2 = employeeIntegration.employeeExists(MUNICIPALITY_ID, LOGIN_NAME);
		final var result3 = employeeIntegration.employeeExists(MUNICIPALITY_ID, LOGIN_NAME);

		// Assert - client should only be called once, rest from cache
		assertThat(result1).isTrue();
		assertThat(result2).isTrue();
		assertThat(result3).isTrue();
		verify(employeeClientMock, times(1)).getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, LOGIN_NAME);
		verifyNoMoreInteractions(employeeClientMock);
	}

	@Test
	void employeeExistsCachesDifferentKeysIndependently() {

		// Arrange
		final var loginName1 = "user01";
		final var loginName2 = "user02";

		when(employeeClientMock.getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, loginName1))
			.thenReturn(Optional.of(new PortalPersonData()));
		when(employeeClientMock.getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, loginName2))
			.thenReturn(Optional.empty());

		// Act - call with different keys
		final var result1a = employeeIntegration.employeeExists(MUNICIPALITY_ID, loginName1);
		final var result1b = employeeIntegration.employeeExists(MUNICIPALITY_ID, loginName1);
		final var result2a = employeeIntegration.employeeExists(MUNICIPALITY_ID, loginName2);
		final var result2b = employeeIntegration.employeeExists(MUNICIPALITY_ID, loginName2);

		// Assert - each key should only trigger one call
		assertThat(result1a).isTrue();
		assertThat(result1b).isTrue();
		assertThat(result2a).isFalse();
		assertThat(result2b).isFalse();

		verify(employeeClientMock, times(1)).getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, loginName1);
		verify(employeeClientMock, times(1)).getEmployeeByDomainAndLoginName(MUNICIPALITY_ID, DOMAIN, loginName2);
		verifyNoMoreInteractions(employeeClientMock);
	}

	@Test
	void employeeExistsCacheKeyIncludesMunicipalityId() {

		// Arrange
		final var municipalityId1 = "2281";
		final var municipalityId2 = "2282";

		when(employeeClientMock.getEmployeeByDomainAndLoginName(municipalityId1, DOMAIN, LOGIN_NAME))
			.thenReturn(Optional.of(new PortalPersonData()));
		when(employeeClientMock.getEmployeeByDomainAndLoginName(municipalityId2, DOMAIN, LOGIN_NAME))
			.thenReturn(Optional.empty());

		// Act - call with same login name but different municipality IDs
		final var result1 = employeeIntegration.employeeExists(municipalityId1, LOGIN_NAME);
		final var result2 = employeeIntegration.employeeExists(municipalityId2, LOGIN_NAME);

		// Assert - different municipality IDs should trigger separate calls
		assertThat(result1).isTrue();
		assertThat(result2).isFalse();

		verify(employeeClientMock, times(1)).getEmployeeByDomainAndLoginName(municipalityId1, DOMAIN, LOGIN_NAME);
		verify(employeeClientMock, times(1)).getEmployeeByDomainAndLoginName(municipalityId2, DOMAIN, LOGIN_NAME);
		verifyNoMoreInteractions(employeeClientMock);
	}
}
