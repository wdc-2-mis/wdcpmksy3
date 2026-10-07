package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprWaterOutcome;

@Repository
public interface PprWaterOutcomeRepository extends JpaRepository<PprWaterOutcome, Integer>{

	@Query("""
		    SELECT o
		    FROM PprWaterOutcome o
		    JOIN FETCH o.ppr p
		    JOIN FETCH p.district d
		    JOIN FETCH o.microWatershed mw
		    JOIN FETCH o.village v
		    JOIN FETCH o.waterSource ws
		    WHERE d.dcode = :dcode
		    ORDER BY CASE
            WHEN o.status = 'D' THEN 0
            WHEN o.status = 'C' THEN 1
            ELSE 2
            END
		""")
		List<PprWaterOutcome> findByDistrict(@Param("dcode") Integer dcode);
	
	@Query("""
	        SELECT o
	        FROM PprWaterOutcome o
	        JOIN FETCH o.ppr p
	        JOIN FETCH p.district d
	        JOIN FETCH o.microWatershed mw
	        JOIN FETCH o.village v
	        JOIN FETCH o.waterSource ws
	        WHERE p.pprId = :pprId
	          AND o.status IN :statuses
	        ORDER BY o.pprWaterOutcomeId
	        """)
	List<PprWaterOutcome> findByPprIdAndStatusIn(
	        @Param("pprId") Integer pprId,
	        @Param("statuses") List<Character> statuses);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprWaterOutcome w SET w.status = 'D' WHERE w.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	   
	
	 boolean existsByPpr_InstitutionalStructure_StCodeAndStatus(Integer stCode, String status);

	 Optional<PprWaterOutcome>
	    findTopByPpr_PprIdAndMicroWatershed_MwIdAndVillage_VcodeOrderByPprWaterOutcomeIdDesc(
	            Integer projectId,
	            Integer watershedId,
	            Integer vcode
	    );

	 @Query("""
			    SELECT o
			    FROM PprWaterOutcome o
			    JOIN FETCH o.ppr p
			    JOIN FETCH p.district d
			    JOIN FETCH o.microWatershed mw
			    JOIN FETCH o.village v
			    JOIN FETCH o.waterSource ws
			    WHERE d.state.stCode = :stcode
			    ORDER BY CASE
			        WHEN o.status = 'D' THEN 0
			        WHEN o.status = 'C' THEN 1
			        ELSE 2
			    END
			""")
			List<PprWaterOutcome> findByState(Integer stcode);
	
	

}
