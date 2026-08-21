package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PPREmploymentGeneration;

@Repository
public interface PPREmploymentGenerationRepository extends JpaRepository<PPREmploymentGeneration, Integer> {

	boolean existsByPprIdPprIdAndVillageVcodeAndMicroWatershedMwIdAndEmploymentTypeEmploymentTypeId(
            Integer pprId, Integer vcode, Integer mwId, Integer employmentTypeId);


    @Query(value = """
        SELECT
            e.ppr_employment_id,
            d.dist_name,
            p.project_name,
            mw.mw_name,
            v.village_name,
            et.employment_type_id,
            et.employment_type_name,
            e.sc,
            e.st,
            e.others,
            e.women,
            (COALESCE(e.sc,0) + COALESCE(e.st,0) + COALESCE(e.others,0)) AS total,
            e.status
        FROM ppr_employment_generation e
        JOIN m_ppr p ON e.ppr_id = p.ppr_id
        JOIN m_district d ON p.dcode = d.dcode
        JOIN m_micro_watershed mw ON e.mw_id = mw.mw_id
        JOIN m_village v ON e.vcode = v.vcode
        JOIN m_employment_type et ON e.employment_type_id = et.employment_type_id
        WHERE p.dcode = :dcode
        ORDER BY
        CASE WHEN e.status = 'D' THEN 0 ELSE 1 END,
        d.dist_name, p.project_name, mw.mw_name, v.village_name, et.employment_type_id
        """, nativeQuery = true)
    List<Map<String,Object>> getEmploymentGenerationByDistrict(@Param("dcode") Integer dcode);


    @Query("""
        SELECT e FROM PPREmploymentGeneration e WHERE e.pprId.pprId = :pprId AND e.village.vcode = :vcode
        AND e.microWatershed.mwId = :mwId ORDER BY e.employmentType.employmentTypeId""")
    List<PPREmploymentGeneration> findExistingRecords(@Param("pprId") Integer pprId, @Param("vcode") Integer vcode, @Param("mwId") Integer mwId);
	
}
