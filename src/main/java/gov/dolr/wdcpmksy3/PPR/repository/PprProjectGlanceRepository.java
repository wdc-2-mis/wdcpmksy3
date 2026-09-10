package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;

public interface PprProjectGlanceRepository extends JpaRepository<PprProjectGlance, Integer>{
	
	List<PprProjectGlance> getListOfPprProjectGlanceByPpr(MPpr ppr);
	List<PprProjectGlance> findByPprPprIdAndStatus(Integer pprId, Character status);
	
	@Modifying
	@Transactional
	@Query(" UPDATE PprProjectGlance p SET p.status = 'D' WHERE p.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);

}
