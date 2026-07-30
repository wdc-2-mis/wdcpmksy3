package gov.dolr.wdcpmksy3.PPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;

import java.util.List;

@Repository
public interface PprMicroWatershedRepository extends JpaRepository<PprMicroWatershed, Integer> {

    // Find all micro-watersheds linked to a given PPR
    List<PprMicroWatershed> findByPprPprId(Integer pprId);

    // Find all PPRs linked to a given micro-watershed
    List<PprMicroWatershed> findByMicroWatershedMwId(Integer mwId);

    // Check if a specific PPR–MicroWatershed link already exists
    boolean existsByPprPprIdAndMicroWatershedMwId(Integer pprId, Integer mwId);
}

