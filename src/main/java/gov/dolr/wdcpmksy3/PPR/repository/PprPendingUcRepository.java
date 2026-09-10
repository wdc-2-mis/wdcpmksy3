package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprPendingUc;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;

@Repository
public interface PprPendingUcRepository extends JpaRepository<PprPendingUc, Integer> {

    List<PprPendingUc> findByPpr(MPpr ppr);
    
    List<PprPendingUc> findByPpr_District_State_StCode(Integer stCode);

    List<PprPendingUc> findByPprPprId(Integer pprId);

    List<PprPendingUc> findByPprPprIdAndStatus(Integer pprId, Character status);

    List<PprPendingUc> findByFinYearFinYrCd(Integer finYrCd);

    List<PprPendingUc> findByPprPprIdAndFinYearFinYrCd(
            Integer pprId,
            Integer finYrCd);
    
    @Modifying
	@Transactional
	@Query("UPDATE PprPendingUc u SET u.status = 'D' WHERE u.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	
}