package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;

public interface DprDetailsRepository extends JpaRepository<DprDetails, Integer>{
	
	Optional<DprDetails> findByPprPprId(Integer pprId);
	
	@Query(value = "select p.dpr_id, p.ppr_id, project_name, dist_name, nrm_id, dt.status, "
			+ "	(select h.head_code from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headcode, "
			+ "	(select h.seq_no from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as seq_no, "
			+ "	(select h.head_desc from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headdesc, "
			+ "	activity_code, (select activity_desc from m_phy_activity where activity_code=dt.activity_code) as activity, "
			+ "	(select unit_desc from m_phy_activity aa, m_unit u where aa.activity_code=dt.activity_code and aa.unit_code=u.unit_code) as unit, "
			+ "	(select seq_no from m_phy_activity where activity_code=dt.activity_code) as aseq_no, qty_planned from dpr_details p, "
			+ "	dpr_nrm_dtl dt, m_ppr m, m_district d where p.dpr_id=dt.dpr_id and m.ppr_id=p.ppr_id and m.dcode=d.dcode and d.st_code=:stcode "
			+ "	order by dist_name, project_name, seq_no, aseq_no", nativeQuery = true)
	List<Object[]> getDprNrmProjectPlanList(@Param("stcode") int stcode);
	
	@Query(value = "select p.dpr_id, p.ppr_id, project_name, dist_name, nrm_id, dt.status, "
			+ "	(select h.head_code from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headcode, "
			+ "	(select h.seq_no from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as seq_no, "
			+ "	(select h.head_desc from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headdesc, "
			+ "	activity_code, (select activity_desc from m_phy_activity where activity_code=dt.activity_code) as activity, "
			+ "	(select unit_desc from m_phy_activity aa, m_unit u where aa.activity_code=dt.activity_code and aa.unit_code=u.unit_code) as unit, "
			+ "	(select seq_no from m_phy_activity where activity_code=dt.activity_code) as aseq_no, qty_planned from dpr_details p, "
			+ "	dpr_nrm_dtl dt, m_ppr m, m_district d where p.dpr_id=dt.dpr_id and m.ppr_id=p.ppr_id and m.dcode=d.dcode and d.dcode=:dcode "
			+ "	order by project_name, seq_no, aseq_no", nativeQuery = true)
	List<Object[]> getDprNrmDistrictPlanList(@Param("dcode") int dcode);
	
	@Query(value = "select p.dpr_id, p.ppr_id, project_name, dist_name, nrm_id, dt.status, "
			+ "	(select h.head_code from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headcode, "
			+ "	(select h.seq_no from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as seq_no, "
			+ "	(select h.head_desc from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headdesc, "
			+ "	activity_code, (select activity_desc from m_phy_activity where activity_code=dt.activity_code) as activity, "
			+ "	(select unit_desc from m_phy_activity aa, m_unit u where aa.activity_code=dt.activity_code and aa.unit_code=u.unit_code) as unit, "
			+ "	(select seq_no from m_phy_activity where activity_code=dt.activity_code) as aseq_no, qty_planned from dpr_details p, "
			+ "	dpr_nrm_dtl dt, m_ppr m, m_district d where p.dpr_id=dt.dpr_id and m.ppr_id=p.ppr_id and m.dcode=d.dcode and p.ppr_id=:project "
			+ "	order by project_name, seq_no, aseq_no", nativeQuery = true)
	List<Object[]> getDprNrmProjectPlanListByProject(@Param("project") int project);
	
	@Query(value = "select p.dpr_id, p.ppr_id, project_name, dist_name, nrm_id, dt.status, "
			+ "	(select h.head_code from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headcode, "
			+ "	(select h.seq_no from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as seq_no, "
			+ "	(select h.head_desc from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headdesc, "
			+ "	activity_code, (select activity_desc from m_phy_activity where activity_code=dt.activity_code) as activity, "
			+ "	(select unit_desc from m_phy_activity aa, m_unit u where aa.activity_code=dt.activity_code and aa.unit_code=u.unit_code) as unit, "
			+ "	(select seq_no from m_phy_activity where activity_code=dt.activity_code) as aseq_no, qty_planned from dpr_details p, "
			+ "	dpr_nrm_dtl dt, m_ppr m, m_district d where p.dpr_id=dt.dpr_id and m.ppr_id=p.ppr_id and m.dcode=d.dcode and dt.status in('C','S') and p.ppr_id=:project "
			+ "	order by dist_name, project_name, seq_no, aseq_no", nativeQuery = true)
	List<Object[]> getDprNrmProjectPlanComplitedList(@Param("project") int project);
	
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
		    WHERE dp.ppr_id = :pprId and elp.status in('C','S')
		    ORDER BY d.dist_name, project_name
		""", nativeQuery = true)
		List<Object[]> getDprELPProjectPlanCompletedList(@Param("pprId") Integer pprId);
		
		@Query("select d from DprDetails d where d.ppr.pprId=:pprId and d.status='C'")
	    List<DprDetails> findByPprPprIdandStatus(@Param("pprId") Integer pprId);
		
		@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DprDetails d WHERE d.ppr.pprId = :pprId AND d.status = 'C'")
		boolean existsApprovedDprByPprId(@Param("pprId") Integer pprId);

}
