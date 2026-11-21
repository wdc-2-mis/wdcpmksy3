package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlan;


@Repository
public interface WdcpmksyProjectPhysicalPlanRepository  extends JpaRepository<WdcpmksyProjectPhysicalPlan, Integer> {

    List<WdcpmksyProjectPhysicalPlan> findByProject_PprId(Integer projId);

    Optional<WdcpmksyProjectPhysicalPlan>
        findByProject_PprIdAndFinYear_FinYrCd(
                Integer projId, Integer finYrCd);

    List<WdcpmksyProjectPhysicalPlan> findByStatus(String status);
}