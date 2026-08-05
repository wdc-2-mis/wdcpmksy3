package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;

@Repository
public interface PprAgroClimateRepository extends JpaRepository<PprAgroClimate, Integer> {
	
	
	@Query(value = "select ac.ppr_agro_id, d.dist_name, p.project_name, vcode, (select village_name from m_village where vcode= ac.vcode) as village, "
			+ "zone_name, ac.area, avg_rainfall, topography, forest_area, ac.status, crop_type_id, (select crop_name from m_crop_type where crop_type_id=cr.crop_type_id) as crop_name, "
			+ "cr.area as crop_area, soil_type_id, (select soil_name from m_soil_type where soil_type_id=s.soil_type_id) as soil_name, s.area as soil_area "
			+ "from m_district d join m_ppr p on p.dcode = d.dcode join ppr_agro_climate ac on ac.ppr_id = p.ppr_id join ppr_agro_crop cr on cr.ppr_agro_id = ac.ppr_agro_id "
			+ "join ppr_agro_soil s on s.ppr_agro_id = ac.ppr_agro_id where d.st_code=:stcode order by d.dist_name, p.project_name ", nativeQuery = true)
	List<Object[]> getPprAgroClimateList(@Param("stcode") int stcode);

}
