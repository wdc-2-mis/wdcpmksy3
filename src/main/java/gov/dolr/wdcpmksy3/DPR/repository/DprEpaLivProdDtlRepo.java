package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.DprEpaLivProdDtl;


@Repository
public interface DprEpaLivProdDtlRepo extends JpaRepository<DprEpaLivProdDtl, Integer>{

	
	@Query(value = """
		    select dp.dpr_id, dp.ppr_id, project_name, dist_name, elp.elp_id, elp.activity_id, elp.level_head, (case  when elp.level_head = 'E' then epa.epa_desc  
when elp.level_head = 'P' then pa.productivity_coreactivity_desc when elp.level_head = 'L' then la.livelihood_activity_name end) as activity_name, elp.qty_planned,  elp.status AS statuselp, CASE 
         WHEN elp.level_head = 'E' THEN mu_epa.unit_desc
           WHEN elp.level_head = 'P' THEN mu_prod.unit_desc WHEN elp.level_head = 'L' THEN mu_liv.unit_desc END AS unit_desc
FROM m_ppr m
JOIN m_district d ON d.dcode = m.dcode
JOIN dpr_details dp ON m.ppr_id = dp.ppr_id
JOIN dpr_epa_liv_prod_dtl elp ON dp.dpr_id = elp.dpr_id
LEFT JOIN m_epa_activity epa 
       ON elp.level_head = 'E' AND elp.activity_id = epa.epa_activity_id
LEFT JOIN m_unit mu_epa 
       ON epa.unit_code = mu_epa.unit_code
LEFT JOIN m_productivity_activity pa 
       ON elp.level_head = 'P' AND elp.activity_id = pa.productivity_activity_id
LEFT JOIN m_unit mu_prod 
       ON pa.unit_code = mu_prod.unit_code
LEFT JOIN m_livelihood_activities la 
       ON elp.level_head = 'L' AND elp.activity_id = la.livelihood_activity_id
LEFT JOIN m_unit mu_liv 
       ON la.unit_code = mu_liv.unit_code
WHERE d.st_code = :stcode
ORDER BY d.dist_name, project_name, 
CASE WHEN elp.status = 'D' THEN 0 ELSE 1 END ASC;
		""", nativeQuery = true)
		List<Object[]> getDprELPProjectPlanList(@Param("stcode") Integer stcode);
		
		
		

		@Query("SELECT d.status FROM DprEpaLivProdDtl d WHERE d.dpr.ppr.pprId = :projectId AND d.activityId = :actId AND d.levelHead = :head")
		String findStatusByProjectAndActivityAndHead(@Param("projectId") Integer projectId,
		                                             @Param("actId") Integer actId,
		                                             @Param("head") Character headChar);




		@Query(value = """
			    SELECT dp.dpr_id, dp.ppr_id, project_name, dist_name, elp.elp_id, elp.activity_id, elp.level_head,
			           CASE WHEN elp.level_head = 'E' THEN epa.epa_desc
			                WHEN elp.level_head = 'P' THEN pa.productivity_coreactivity_desc
			                WHEN elp.level_head = 'L' THEN la.livelihood_activity_name END AS activity_name,
			           elp.qty_planned, elp.status, 
			           CASE WHEN elp.level_head = 'E' THEN mu_epa.unit_desc
			                WHEN elp.level_head = 'P' THEN mu_prod.unit_desc
			                WHEN elp.level_head = 'L' THEN mu_liv.unit_desc END AS unit_desc
			    FROM m_ppr m
			    JOIN m_district d ON d.dcode = m.dcode
			    JOIN dpr_details dp ON m.ppr_id = dp.ppr_id
			    JOIN dpr_epa_liv_prod_dtl elp ON dp.dpr_id = elp.dpr_id
			    LEFT JOIN m_epa_activity epa ON elp.level_head = 'E' AND elp.activity_id = epa.epa_activity_id
			    LEFT JOIN m_unit mu_epa ON epa.unit_code = mu_epa.unit_code
			    LEFT JOIN m_productivity_activity pa ON elp.level_head = 'P' AND elp.activity_id = pa.productivity_activity_id
			    LEFT JOIN m_unit mu_prod ON pa.unit_code = mu_prod.unit_code
			    LEFT JOIN m_livelihood_activities la ON elp.level_head = 'L' AND elp.activity_id = la.livelihood_activity_id
			    LEFT JOIN m_unit mu_liv ON la.unit_code = mu_liv.unit_code
			    WHERE d.dcode = :dcode
			    ORDER BY d.dist_name, project_name,
			             CASE WHEN elp.status = 'D' THEN 0 ELSE 1 END ASC
			""", nativeQuery = true)
			List<Object[]> getDprELPProjectPlanListByDistrict(@Param("dcode") Integer dcode);




			@Query(value = """
				    SELECT dp.dpr_id, dp.ppr_id, project_name, dist_name, elp.elp_id, elp.activity_id, elp.level_head,
				           CASE WHEN elp.level_head = 'E' THEN epa.epa_desc
				                WHEN elp.level_head = 'P' THEN pa.productivity_coreactivity_desc
				                WHEN elp.level_head = 'L' THEN la.livelihood_activity_name END AS activity_name,
				           elp.qty_planned, elp.status, 
				           CASE WHEN elp.level_head = 'E' THEN mu_epa.unit_desc
				                WHEN elp.level_head = 'P' THEN mu_prod.unit_desc
				                WHEN elp.level_head = 'L' THEN mu_liv.unit_desc END AS unit_desc
				    FROM m_ppr m
				    JOIN m_district d ON d.dcode = m.dcode
				    JOIN dpr_details dp ON m.ppr_id = dp.ppr_id
				    JOIN dpr_epa_liv_prod_dtl elp ON dp.dpr_id = elp.dpr_id
				    LEFT JOIN m_epa_activity epa ON elp.level_head = 'E' AND elp.activity_id = epa.epa_activity_id
				    LEFT JOIN m_unit mu_epa ON epa.unit_code = mu_epa.unit_code
				    LEFT JOIN m_productivity_activity pa ON elp.level_head = 'P' AND elp.activity_id = pa.productivity_activity_id
				    LEFT JOIN m_unit mu_prod ON pa.unit_code = mu_prod.unit_code
				    LEFT JOIN m_livelihood_activities la ON elp.level_head = 'L' AND elp.activity_id = la.livelihood_activity_id
				    LEFT JOIN m_unit mu_liv ON la.unit_code = mu_liv.unit_code
				    WHERE dp.ppr_id = :pprId
				    ORDER BY d.dist_name, project_name,
				             CASE WHEN elp.status = 'D' THEN 0 ELSE 1 END ASC
				""", nativeQuery = true)
				List<Object[]> getDprELPProjectPlanListByProject(@Param("pprId") Integer pprId);





	

	
}
