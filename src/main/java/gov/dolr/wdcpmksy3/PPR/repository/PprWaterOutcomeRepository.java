package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
