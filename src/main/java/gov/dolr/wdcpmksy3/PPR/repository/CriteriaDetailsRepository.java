package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.CriteriaDetails;

@Repository
public interface CriteriaDetailsRepository extends JpaRepository<CriteriaDetails, Integer> {

    List<CriteriaDetails> findByCriteria_CriteriaId(Integer criteriaId);

    List<CriteriaDetails> findByStatus(String status);

    List<CriteriaDetails> findByCriteria_CriteriaIdAndStatus(Integer criteriaId, String status);
    
    List<CriteriaDetails> findByProposedProjectPprProposedProjectId(Integer pprProposedProjectId);
    
    void deleteByProposedProjectPprProposedProjectId(Integer projectId);

}
