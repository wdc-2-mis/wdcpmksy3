package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlanDetails;



@Repository
public interface WdcpmksyProjectPhysicalPlanDetailsRepository extends JpaRepository<WdcpmksyProjectPhysicalPlanDetails, Integer> {

    List<WdcpmksyProjectPhysicalPlanDetails> findByPlan_PlanId(Integer planId);

    void deleteByPlan_PlanId(Integer planId);
}