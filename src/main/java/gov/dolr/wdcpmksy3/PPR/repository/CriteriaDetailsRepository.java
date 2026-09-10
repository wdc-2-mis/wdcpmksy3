package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.CriteriaDetails;

@Repository
public interface CriteriaDetailsRepository extends JpaRepository<CriteriaDetails, Integer> {

    List<CriteriaDetails> findByCriteria_CriteriaId(Integer criteriaId);

    List<CriteriaDetails> findByStatus(String status);

    List<CriteriaDetails> findByCriteria_CriteriaIdAndStatus(Integer criteriaId, String status);
    
    List<CriteriaDetails> findByProposedProjectPprProposedProjectId(Integer pprProposedProjectId);
    
    void deleteByProposedProjectPprProposedProjectId(Integer projectId);
    
    @Modifying
    @Transactional
    @Query(" UPDATE CriteriaDetails c  SET c.status = 'D' WHERE c.proposedProject.ppr.pprId = :pprId")
    int changeStatusByPprId(@Param("pprId") Integer pprId);

}
