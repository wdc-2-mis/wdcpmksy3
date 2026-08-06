package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
