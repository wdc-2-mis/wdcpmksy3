package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;

public interface PprWatershedCoveredAreaRepo extends JpaRepository<PprWatershedCoveredArea, Integer>{

	List<PprWatershedCoveredArea> findByPpr(MPpr ppr);

	Optional<PprWatershedCoveredArea> findByPpr_PprIdAndMicroWatershed_MwIdAndScheme_SchemeId(
            Integer pprId,
            Integer mwId,
            Integer schemeId);

	List<PprWatershedCoveredArea> findByPpr_PprIdAndMicroWatershed_MwId(Integer pprId, Integer mwId);

	boolean existsByMicroWatershed_MwIdAndStatus(Integer mwId, String string);

	Optional<PprWatershedCoveredArea> findTopByMicroWatershed_MwIdOrderByIdDesc(Integer mwId);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprWatershedCoveredArea w SET w.status = 'D' WHERE w.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);
	
	List<PprWatershedCoveredArea> findByPprPprIdAndStatus(Integer pprId, String status);

}
