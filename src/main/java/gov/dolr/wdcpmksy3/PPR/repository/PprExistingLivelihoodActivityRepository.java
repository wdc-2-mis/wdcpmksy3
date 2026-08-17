package gov.dolr.wdcpmksy3.PPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprExistingLivelihoodActivity;

public interface PprExistingLivelihoodActivityRepository extends JpaRepository<PprExistingLivelihoodActivity, Integer>{
	
	@Transactional
    @Modifying
    void deleteByPprLivelihoodPprLivelihoodId(Integer id);

}
