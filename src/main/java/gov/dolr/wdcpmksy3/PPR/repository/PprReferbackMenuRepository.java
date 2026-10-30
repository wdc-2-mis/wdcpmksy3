package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.PPR.entity.PprReferbackMenu;

public interface PprReferbackMenuRepository
        extends JpaRepository<PprReferbackMenu, Integer> {

    List<PprReferbackMenu> findBypprPprId(Integer pprId);
    
    @Query("""
    	    SELECT COALESCE(MAX(r.referbackRound), 0)
    	    FROM PprReferbackMenu r
    	    WHERE r.ppr.pprId = :pprId
    	""")
    	Integer getLastReferbackRound(@Param("pprId") Integer pprId);
    
    List<PprReferbackMenu> findByPpr_PprIdAndReferbackRoundOrderByReferbackIdAsc(
            Integer pprId, Integer referbackRound);
    
}
