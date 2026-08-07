package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PPRLandPatternArea;

@Repository
public interface PPRLandPatternAreaRepository extends JpaRepository<PPRLandPatternArea,Integer> {
	
	@Query(value = """
		    select
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
		    from ppr_land_pattern_area l
		    join m_ppr m on l.ppr_id = m.ppr_id
		    join m_district d on m.dcode = d.dcode
		    join m_micro_watershed mw on l.mw_id = mw.mw_id
		    join m_village mv on l.vcode = mv.vcode
		    where m.dcode = :dcode
		    order by case when l.status = 'D' then 0 else 1 end, 
		    m.project_name, mw.mw_name, mv.village_name
		    """, nativeQuery = true)
		List<Map<String,Object>> getLandPatternAreaByDistrict(@Param("dcode") Integer dcode);
	
	
	@Query("""
		    SELECT status FROM PPRLandPatternArea WHERE village.vcode = :vcode""")
		Character getStatusByVillage(@Param("vcode") Integer vcode);
	
	
	@Query("""
		    SELECT COUNT(p) FROM PPRLandPatternArea p WHERE p.village.vcode = :vcode""")
		long countByVillage(@Param("vcode") Integer vcode);
	
}
