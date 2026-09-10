package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;

public interface PprLivelihoodRepository extends JpaRepository<PprLivelihood, Integer> {
	
	List<PprLivelihood> findByPpr_District_State_StCode(Integer stCode);
	
	List<PprLivelihood> findByPprPprIdAndStatus(Integer pprId, Character status);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprLivelihood l SET l.status = 'D' WHERE l.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	

}
