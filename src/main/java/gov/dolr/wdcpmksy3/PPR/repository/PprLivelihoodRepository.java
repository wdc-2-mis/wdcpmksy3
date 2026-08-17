package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;

public interface PprLivelihoodRepository extends JpaRepository<PprLivelihood, Integer> {
	
	List<PprLivelihood> findByPpr_District_State_StCode(Integer stCode);

}
