package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.PprDisasterDetails;

public interface PprDisasterDetailsRepository extends JpaRepository<PprDisasterDetails, Integer>{
	
	List<PprDisasterDetails> findByPprPprIdAndStatus(Integer pprId, String status);
	

}
