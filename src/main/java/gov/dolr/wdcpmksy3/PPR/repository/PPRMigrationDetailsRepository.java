package gov.dolr.wdcpmksy3.PPR.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PPRMigrationDetails;




@Repository
public interface PPRMigrationDetailsRepository extends JpaRepository<PPRMigrationDetails, Integer>  {
	
	List<PPRMigrationDetails> findByPprIdAndStatus(
            Integer pprId,
            Character status);
	
	
	@Query(value = """
			SELECT
			    pm.ppr_migration_id,
			    d.dist_name,
			    p.project_name,
			    mw.mw_name,
			    v.village_name,
			    pm.migrating_people_count,
			    pm.migration_days_per_year,
			    pm.migration_reason,
			    pm.expected_reduction_migrating_people,
			    pm.status,
			    pm.ppr_id,
			    pm.mw_id,
			    pm.vcode
			FROM ppr_migration_details pm
			JOIN m_ppr p ON pm.ppr_id = p.ppr_id
			JOIN m_district d ON p.dcode = d.dcode
			LEFT JOIN m_micro_watershed mw ON pm.mw_id = mw.mw_id
			LEFT JOIN m_village v ON pm.vcode = v.vcode
			WHERE pm.ppr_id = :pprId
			ORDER BY CASE WHEN pm.status = 'D' THEN 0 ELSE 1 END,
			         pm.ppr_migration_id
			""", nativeQuery = true)
			List<Map<String, Object>> getMigrationDetailsByProject(@Param("pprId") Integer pprId);
}
