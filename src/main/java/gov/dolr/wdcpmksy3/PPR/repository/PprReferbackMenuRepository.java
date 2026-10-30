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
    
    @Query("SELECT m FROM PprReferbackMenu m WHERE m.ppr.pprId = :pprId AND m.referbackRound= (select max(m1.referbackRound) "
    		+ "from PprReferbackMenu m1  where m1.ppr.pprId = m.ppr.pprId) order by referbackId")
    List<PprReferbackMenu> getListPprReferbackMenu(@Param("pprId") Integer pprId);
    
    
    
}
