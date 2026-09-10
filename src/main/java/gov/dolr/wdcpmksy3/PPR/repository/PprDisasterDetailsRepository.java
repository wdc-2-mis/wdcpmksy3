package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprDisasterDetails;

public interface PprDisasterDetailsRepository extends JpaRepository<PprDisasterDetails, Integer>{
	
	List<PprDisasterDetails> findByPprPprIdAndStatus(Integer pprId, String status);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprDisasterDetails w SET w.status = 'D' WHERE w.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	
	

}
