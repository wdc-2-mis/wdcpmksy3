package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List; 
import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.DprNrmProjectPlan; 

@Repository 
public interface DprNrmProjectPlanRepository extends JpaRepository<DprNrmProjectPlan, Integer> { 
	
	List<DprNrmProjectPlan> findByPpr_PprId(Integer pprId); 
	Optional<DprNrmProjectPlan> findByPpr_PprIdAndStatus( Integer pprId, String status); 
	List<DprNrmProjectPlan> findByStatus(String status); 
	
	Optional<DprNrmProjectPlan> findByPprPprId(Integer pprId);
	
	@Query(value = "select p.plan_id, p.ppr_id, project_name, dist_name, p.status, dprupload_url, plan_dtl_id, "
			+ "(select h.head_code from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headcode, "
			+ "(select h.seq_no from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as seq_no, "
			+ "(select h.head_desc from m_phy_heads h, m_phy_activity a where a.head_code=h.head_code and dt.activity_code=a.activity_code) as headdesc, "
			+ "activity_code, (select activity_desc from m_phy_activity where activity_code=dt.activity_code) as activity, "
			+ "(select unit_desc from m_phy_activity aa, m_unit u where aa.activity_code=dt.activity_code and aa.unit_code=u.unit_code) as unit, "
			+ "(select seq_no from m_phy_activity where activity_code=dt.activity_code) as aseq_no, qty_planned from dpr_nrm_project_plan p, "
			+ "dpr_nrm_project_plan_dtl dt, m_ppr m, m_district d where p.plan_id=dt.plan_id and m.ppr_id=p.ppr_id and m.dcode=d.dcode and d.st_code=:stcode "
			+ "order by dist_name, project_name, seq_no, aseq_no", nativeQuery = true)
	List<Object[]> getDprNrmProjectPlanList(@Param("stcode") int stcode);
	
}