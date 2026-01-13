package se.sundsvall.managerresponsibility.integration.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.StringUtils;

@Entity
@Table(schema = "org_edw", name = "vChefOrganisationer")
public class ManagerResponsibilityEntity {

	@Id
	@Column(name = "ChefOrganisationer_SK", insertable = false, updatable = false)
	private Long id;

	@Column(name = "PersonId", insertable = false, updatable = false)
	private String personId;

	@Column(name = "Login", insertable = false, updatable = false)
	private String loginName;

	@Column(name = "OrgList", insertable = false, updatable = false)
	private String orgList;

	@Transient
	public List<String> toOrgIds() {
		return Optional.ofNullable(this.orgList)
			.stream()
			.filter(StringUtils::hasText)
			.flatMap(s -> Arrays.stream(s.split("\\|")))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.toList();
	}

	public static ManagerResponsibilityEntity create() {
		return new ManagerResponsibilityEntity();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ManagerResponsibilityEntity withId(long id) {
		this.id = id;
		return this;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public ManagerResponsibilityEntity withPersonId(String personId) {
		this.personId = personId;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public ManagerResponsibilityEntity withLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public String getOrgList() {
		return orgList;
	}

	public void setOrgList(String orgList) {
		this.orgList = orgList;
	}

	public ManagerResponsibilityEntity withOrgList(String orgList) {
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
		ManagerResponsibilityEntity other = (ManagerResponsibilityEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(loginName, other.loginName) && Objects.equals(orgList, other.orgList) && Objects.equals(personId, other.personId);
	}

	@Override
	public String toString() {
		return "ManagerResponsibilityEntity [id=" + id + ", personId=" + personId + ", loginName=" + loginName + ", orgList=" + orgList + "]";
	}
}
