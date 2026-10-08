package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PPRLandPatternArea;

@Repository
public interface PPRLandPatternAreaRepository extends JpaRepository<PPRLandPatternArea,Integer> {
	
	@Query(value = """
		    SELECT
		        l.ppr_land_pattern_area_id,
		        d.dist_name,
		        m.project_name,
		        mw.mw_name,
		        mv.village_name,
		        l.village_area,
		        l.forest_area,
		        l.argiculture_land,
		        l.rainfed_area,
		        l.pastures,
		        l.cultivable_wasteland_area,
		        l.non_cultivable_wasteland_area,
		        l.status
		    FROM ppr_land_pattern_area l
		    JOIN m_ppr m
		        ON l.ppr_id = m.ppr_id
		    JOIN m_district d
		        ON m.dcode = d.dcode
		    LEFT JOIN m_micro_watershed mw
		        ON l.mw_id = mw.mw_id
		    LEFT JOIN m_village mv
		        ON l.vcode = mv.vcode
		    WHERE m.dcode = :dcode
		    ORDER BY
		        CASE
		            WHEN l.status = 'D' THEN 0
		            WHEN l.status = 'S' THEN 1
		            ELSE 2
		        END,
		        d.dist_name,
		        m.project_name,
		        mw.mw_name,
		        mv.village_name
		    """, nativeQuery = true)
		List<Map<String, Object>> getLandPatternAreaByDistrict(
		        @Param("dcode") Integer dcode);
	
	@Query("""
		    SELECT status FROM PPRLandPatternArea WHERE village.vcode = :vcode""")
		Character getStatusByVillage(@Param("vcode") Integer vcode);
	
	
	@Query("""
		    SELECT COUNT(p) FROM PPRLandPatternArea p WHERE p.village.vcode = :vcode""")
		long countByVillage(@Param("vcode") Integer vcode);
	
	
	List<PPRLandPatternArea> findByPprIdPprIdAndStatus(Integer pprId, Character status);
	
	List<PPRLandPatternArea> findByPprIdPprIdAndStatusIn(Integer pprId, List<Character> statuses);
	
	@Modifying
	@Transactional
	@Query("UPDATE PPRLandPatternArea l SET l.status = 'D' WHERE l.pprId.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	
	
	boolean existsByPprId_InstitutionalStructure_StCodeAndStatus(Integer stCode, Character status);

	@Query(value = """
		    SELECT
		        l.ppr_land_pattern_area_id,
		        d.dist_name,
		        m.project_name,
		        mw.mw_name,
		        mv.village_name,
		        l.village_area,
		        l.forest_area,
		        l.argiculture_land,
		        l.rainfed_area,
		        l.pastures,
		        l.cultivable_wasteland_area,
		        l.non_cultivable_wasteland_area,
		        l.status

		    FROM ppr_land_pattern_area l

		    JOIN m_ppr m
		        ON l.ppr_id = m.ppr_id

		    JOIN m_district d
		        ON m.dcode = d.dcode

		    LEFT JOIN m_micro_watershed mw
		        ON l.mw_id = mw.mw_id

		    LEFT JOIN m_village mv
		        ON l.vcode = mv.vcode

		    WHERE d.st_code = :stcode

		    ORDER BY
		        CASE
		            WHEN l.status = 'D' THEN 0
		            WHEN l.status = 'S' THEN 1
		            WHEN l.status = 'C' THEN 2
		            ELSE 3
		        END,
		        d.dist_name,
		        m.project_name,
		        mw.mw_name,
		        mv.village_name
		    """,
		    nativeQuery = true)
		List<Map<String, Object>> getLandPatternAreaByState(
		        @Param("stcode") Integer stcode);
}
