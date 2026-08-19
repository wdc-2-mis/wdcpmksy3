package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PPRSoilErosion;

@Repository
public interface PPRSoilErosionRepository extends JpaRepository<PPRSoilErosion, Integer> {
	
	@Query(value = """
	        SELECT
	            se.ppr_soil_erosion_id,
	            p.ppr_id,
	            d.dcode,
	            d.dist_name,
	            e.erosion_id,
	            e.category_type,
	            et.erosion_type_id,
	            et.erosion_type,
	            se.affected_area,
	            se.runoff,
	            se.avg_soil_loss,
	            se.status,
	            m.month_id,
			    m.month_name,
			    f.fin_yr_cd as year_id,
			    f.year
	        FROM ppr_soil_erosion se
	        JOIN m_ppr p ON se.ppr_id = p.ppr_id
	        JOIN m_district d ON p.dcode = d.dcode
	        JOIN m_erosion_type et ON se.erosion_type_id = et.erosion_type_id
	        JOIN m_erosion e ON et.erosion_id = e.erosion_id
	        LEFT JOIN m_month m ON se.month_id = m.month_id
			LEFT JOIN m_fin_year f ON se.year_id = f.fin_yr_cd
	        WHERE p.dcode = :dcode
	        ORDER BY CASE WHEN se.status = 'D' THEN 0 ELSE 1 END,
	        p.project_name, e.category_type, et.erosion_type""",nativeQuery = true)
	    List<Map<String, Object>> getSoilErosionByDistrict(@Param("dcode") Integer dcode);


	boolean existsByPprPprIdAndErosionTypeErosionTypeIdAndMonthMonthIdAndYearFinYrCd(Integer pprId, Integer erosionTypeId, 
			Integer monthId, Integer yearId);


    List<PPRSoilErosion> findByPprPprId(Integer pprId);
    
    @Query("SELECT se.erosionType.erosionTypeId FROM PPRSoilErosion se WHERE se.ppr.pprId = :pprId")
	List<Integer> findErosionTypeIdsByPprId(@Param("pprId") Integer pprId);
	
	
}
