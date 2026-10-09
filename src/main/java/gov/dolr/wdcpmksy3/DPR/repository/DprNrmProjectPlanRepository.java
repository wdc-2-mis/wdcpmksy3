package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List; 
import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.DprNrmProjectPlan; 

@Repository 
public interface DprNrmProjectPlanRepository extends JpaRepository<DprNrmProjectPlan, Integer> { 
	
	List<DprNrmProjectPlan> findByPpr_PprId(Integer pprId); 
	Optional<DprNrmProjectPlan> findByPpr_PprIdAndStatus( Integer pprId, String status); 
	List<DprNrmProjectPlan> findByStatus(String status); }