package gov.dolr.wdcpmksy3.PPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.dto.MicroWatershedDTO;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;

import java.util.List;

@Repository
public interface PprMicroWatershedRepository
        extends JpaRepository<PprMicroWatershed, Integer> {

    List<PprMicroWatershed> findByPprPprId(Integer pprId);

    List<PprMicroWatershed> findByMicroWatershedMwId(Integer mwId);

    boolean existsByPprPprIdAndMicroWatershedMwId(
            Integer pprId,
            Integer mwId
    );

    @Query("""
        SELECT COUNT(pm.microWatershed.mwId)
        FROM PprMicroWatershed pm
        WHERE pm.ppr.district.dcode = :dcode
        AND pm.ppr.status = 'C'
        AND pm.status = 'C'
    """)
    Long countByDistrictWithCompletedStatus(
            @Param("dcode") Integer dcode
    );


    // Total Micro-Watersheds for selected Project/PPR
    @Query("""
        SELECT COUNT(pm.microWatershed.mwId)
        FROM PprMicroWatershed pm
        WHERE pm.ppr.pprId = :pprId
    """)
    Integer countMicroWatershedsByPprId(
            @Param("pprId") Integer pprId
    );


    // Micro-Watersheds for selected Project/PPR
    @Query("""
        SELECT new gov.dolr.wdcpmksy3.PPR.dto.MicroWatershedDTO(
            pm.microWatershed.mwId,
            pm.microWatershed.mwName
        )
        FROM PprMicroWatershed pm
        WHERE pm.ppr.pprId = :pprId
    """)
    List<MicroWatershedDTO> getMicroWatershedsByPprId(
            @Param("pprId") Integer pprId
    );
}

