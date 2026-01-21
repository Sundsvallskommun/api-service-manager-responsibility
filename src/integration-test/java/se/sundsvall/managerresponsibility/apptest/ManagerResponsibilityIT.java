package se.sundsvall.managerresponsibility.apptest;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.managerresponsibility.Application;

@WireMockAppTestSuite(files = "classpath:/ManagerResponsibilityIT/", classes = Application.class)
class ManagerResponsibilityIT extends AbstractAppTest {

	private static final String RESPONSE_FILE = "response.json";
	private static final String MUNICIPALITY_ID = "2281";

	// ========================================
	// Happy Path - Organizations
	// ========================================

	@Test
	void test01_findByOrgIdSingleResult() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/organizations/9936/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_findByOrgIdMultipleResults() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/organizations/1401/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_findByOrgIdNoResults() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/organizations/9999/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	// ========================================
	// Happy Path - Persons
	// ========================================

	@Test
	void test04_findByPersonIdSuccess() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/persons/57B17CE9-F1EE-49C4-ABCD-1234567890AB/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test05_findByPersonIdNoResults() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/persons/00000000-0000-0000-0000-000000000000/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	// ========================================
	// Happy Path - Logins
	// ========================================

	@Test
	void test06_findByLoginNameSuccess() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/logins/user01/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test07_findByLoginNameNoResults() {
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/logins/nonexistent/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	// ========================================
	// Employee Filtering
	// ========================================

	@Test
	void test08_filterOutNonExistingEmployee() {
		// user02 exists in DB for org 9936, but Employee API returns 404
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/organizations/9936/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test09_partialFilterMultipleManagers() {
		// org 1401 has user03 (exists) and user04 (not exists), should return only user03
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/organizations/1401/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	// ========================================
	// Edge Cases
	// ========================================

	@Test
	void test10_orgListParsingMultipleOrgs() {
		// user01 has orgList "9937|1281|1302" - verify it's parsed correctly
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/persons/57B17CE9-F1EE-49C4-ABCD-1234567890AB/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test11_responseValuesAreLowercase() {
		// Verify personId and loginName are returned in lowercase
		setupCall()
			.withServicePath("/" + MUNICIPALITY_ID + "/logins/user01/manager-responsibilities")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}
