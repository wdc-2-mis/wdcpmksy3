package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlanDetails;



@Repository
public interface WdcpmksyProjectPhysicalPlanDetailsRepository extends JpaRepository<WdcpmksyProjectPhysicalPlanDetails, Integer> {

    List<WdcpmksyProjectPhysicalPlanDetails> findByPlan_PlanId(Integer planId);

    void deleteByPlan_PlanId(Integer planId);
    
    @Query(value = "SELECT pd.plan_dtl_id, pd.plan_id, project_name, pd.phy_activity_code,  CASE WHEN pd.level = 'N' THEN mh.head_desc WHEN pd.level = 'E' THEN 'EPA'  WHEN pd.level = 'P' THEN 'Production System' "
    		+ "WHEN pd.level = 'L' THEN 'Livelihood' END AS head_desc, pd.level, CASE WHEN pd.level = 'E' THEN epa.epa_desc WHEN pd.level = 'P' THEN pa.productivity_coreactivity_desc "
    		+ "WHEN pd.level = 'L' THEN la.livelihood_activity_name WHEN pd.level = 'N' THEN ma.activity_desc END AS activity_name, pd.qty_planned, "
    		+ "CASE  WHEN pd.level = 'E' THEN mu_epa.unit_desc WHEN pd.level = 'P' THEN mu_prod.unit_desc WHEN pd.level = 'L' THEN mu_liv.unit_desc WHEN pd.level = 'N' THEN mu_nrm.unit_desc "
    		+ "END AS unit_desc FROM m_ppr m JOIN m_district d ON d.dcode = m.dcode JOIN wdcpmksy_project_physical_plan p ON m.ppr_id = p.proj_id "
    		+ "JOIN wdcpmksy_project_physical_plan_details pd ON p.plan_id = pd.plan_id LEFT JOIN m_phy_activity ma ON pd.level = 'N' AND ma.activity_code = pd.phy_activity_code "
    		+ "LEFT JOIN m_phy_heads mh ON ma.head_code = mh.head_code LEFT JOIN m_unit mu_nrm ON ma.unit_code = mu_nrm.unit_code "
    		+ "LEFT JOIN m_epa_activity epa ON pd.level = 'E' AND pd.phy_activity_code = epa.epa_activity_id "
    		+ "LEFT JOIN m_unit mu_epa ON epa.unit_code = mu_epa.unit_code LEFT JOIN m_productivity_activity pa ON pd.level = 'P' AND pd.phy_activity_code = pa.productivity_activity_id "
    		+ "LEFT JOIN m_unit mu_prod ON pa.unit_code = mu_prod.unit_code LEFT JOIN m_livelihood_activities la ON pd.level = 'L' AND pd.phy_activity_code = la.livelihood_activity_id "
    		+ "LEFT JOIN m_unit mu_liv ON la.unit_code = mu_liv.unit_code WHERE p.plan_id =:planId "
    		+ "ORDER BY  CASE WHEN pd.level = 'N' THEN 1 WHEN pd.level = 'E' THEN 2 WHEN pd.level = 'P' THEN 3 WHEN pd.level = 'L' THEN 4 END, "
    		+ " mh.seq_no NULLS LAST, ma.seq_no NULLS LAST", nativeQuery = true)
        List<PhysicalActionPlanViewProjection> getPhysicalActionPlanViewProjection( @Param("planId") Integer planId );
}