package se.sundsvall.managerresponsibility.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;

@Schema(description = "ManagerResponsibility model", accessMode = READ_ONLY)
public class ManagerResponsibility {

	@Schema(description = "The ID", examples = "123456", accessMode = READ_ONLY)
	private String id;

	@Schema(description = "The manager person ID", examples = "35532a17-26a0-4438-970c-375465ff1aff", accessMode = READ_ONLY)
	private String personId;

	@Schema(description = "The manager login name", examples = "joe01doe", accessMode = READ_ONLY)
	private String loginName;

	@Schema(description = "The list of organisations that the manager is responsible of", accessMode = READ_ONLY)
	private List<String> orgList;

	public static ManagerResponsibility create() {
		return new ManagerResponsibility();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ManagerResponsibility withId(String id) {
		this.id = id;
		return this;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public ManagerResponsibility withPersonId(String personId) {
		this.personId = personId;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public ManagerResponsibility withLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	public ManagerResponsibility withOrgList(List<String> orgList) {
		this.orgList = orgList;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, loginName, orgList, personId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ManagerResponsibility other = (ManagerResponsibility) obj;
		return Objects.equals(id, other.id) && Objects.equals(loginName, other.loginName) && Objects.equals(orgList, other.orgList) && Objects.equals(personId, other.personId);
	}

	@Override
	public String toString() {
		return "ManagerResponsibility [id=" + id + ", personId=" + personId + ", loginName=" + loginName + ", orgList=" + orgList + "]";
	}
}
