package gov.dolr.wdcpmksy3.PPR.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.dolr.wdcpmksy3.PPR.entity.MSanction;

@Repository
public interface MSanctionRepository extends JpaRepository<MSanction, Integer> {

    List<MSanction> findByPprPprId(Integer pprId);
    
    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM public.m_sanction_project WHERE ppr_id = :pprId",nativeQuery = true)
    int deleteByPprId(@Param("pprId") Integer pprId);
    
    @Query(value = """
    	    SELECT COALESCE(SUM(s.area_proposed), 0)
    	    FROM m_sanction_project s
    	    INNER JOIN m_ppr p
    	        ON p.ppr_id = s.ppr_id
    	    INNER JOIN m_district d
    	        ON d.dcode = p.dcode
    	    WHERE d.st_code = :stcode
    	      AND p.status = 'A'
    	    """, nativeQuery = true)
    	BigDecimal getTotalAreaCoveredByState(
    	        @Param("stcode") Integer stcode);
    
}