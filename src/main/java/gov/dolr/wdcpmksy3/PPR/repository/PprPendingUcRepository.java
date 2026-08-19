package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}