package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import gov.dolr.wdcpmksy3.DPR.entity.DprNrmProjectPlanDtl; 

@Repository 
public interface DprNrmProjectPlanDtlRepository extends JpaRepository<DprNrmProjectPlanDtl, Integer> { 
	
	List<DprNrmProjectPlanDtl> findByPlan_PlanId(Integer planId); 
	List<DprNrmProjectPlanDtl> findByPlan_PlanIdOrderByPlanDtlIdAsc(Integer planId); 
	List<DprNrmProjectPlanDtl> findByActivity_ActivityCode(Integer activityCode); 
}