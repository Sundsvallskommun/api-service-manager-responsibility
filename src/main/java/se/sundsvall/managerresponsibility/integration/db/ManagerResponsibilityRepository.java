package se.sundsvall.managerresponsibility.integration.db;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.sundsvall.managerresponsibility.integration.db.model.ManagerResponsibilityEntity;

@Transactional(readOnly = true)
@CircuitBreaker(name = "managerRepository")
public interface ManagerResponsibilityRepository extends JpaRepository<ManagerResponsibilityEntity, Long> {

	@Query(value = "SELECT * FROM org_edw.vChefOrganisationer t WHERE CONCAT('|', t.OrgList, '|') LIKE CONCAT('%|', :orgId, '|%')", nativeQuery = true)
	List<ManagerResponsibilityEntity> findByOrgId(@Param("orgId") String orgId);
}
