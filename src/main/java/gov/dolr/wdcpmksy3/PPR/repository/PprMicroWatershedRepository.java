package gov.dolr.wdcpmksy3.PPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;

import java.util.List;

@Repository
public interface PprMicroWatershedRepository extends JpaRepository<PprMicroWatershed, Integer> {

    List<PprMicroWatershed> findByPprPprId(Integer pprId);

    List<PprMicroWatershed> findByMicroWatershedMwId(Integer mwId);

   boolean existsByPprPprIdAndMicroWatershedMwId(Integer pprId, Integer mwId);

    @Query("SELECT COUNT(pm.microWatershed.mwId) FROM PprMicroWatershed pm WHERE pm.ppr.district.dcode = :dcode AND pm.ppr.status = 'C' and pm.status = 'C'")
    Long countByDistrictWithCompletedStatus(Integer dcode);
}

