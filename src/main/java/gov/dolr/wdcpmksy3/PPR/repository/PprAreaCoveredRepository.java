package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.PPR.entity.MScheme;

public interface PprAreaCoveredRepository extends JpaRepository<MScheme, Integer>{

	List<MScheme> findAll();

	@Query(value = "SELECT a.ppr_id AS pprId, d.dist_name, w.mw_id, w.mw_name, w.mw_area, " +
            "MAX(CASE WHEN s.scheme_name = 'Pre-WDC2.0' THEN s.scheme_name END) AS pre_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'Pre-WDC2.0' THEN a.no_mw END) AS pre_no, " +
            "MAX(CASE WHEN s.scheme_name = 'Pre-WDC2.0' THEN a.area_mw END) AS pre_area, " +
            "MAX(CASE WHEN s.scheme_name = 'DPAP' THEN s.scheme_name END) AS dpap_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'DPAP' THEN a.no_mw END) AS dpap_no, " +
            "MAX(CASE WHEN s.scheme_name = 'DPAP' THEN a.area_mw END) AS dpap_area, " +
            "MAX(CASE WHEN s.scheme_name = 'DDP' THEN s.scheme_name END) AS ddp_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'DDP' THEN a.no_mw END) AS ddp_no, " +
            "MAX(CASE WHEN s.scheme_name = 'DDP' THEN a.area_mw END) AS ddp_area, " +
            "MAX(CASE WHEN s.scheme_name = 'IWDP' THEN s.scheme_name END) AS iwdp_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'IWDP' THEN a.no_mw END) AS iwdp_no, " +
            "MAX(CASE WHEN s.scheme_name = 'IWDP' THEN a.area_mw END) AS iwdp_area, " +
            "MAX(CASE WHEN s.scheme_name = 'IWMP' THEN s.scheme_name END) AS iwmp_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'IWMP' THEN a.no_mw END) AS iwmp_no, " +
            "MAX(CASE WHEN s.scheme_name = 'IWMP' THEN a.area_mw END) AS iwmp_area, " +
            "MAX(CASE WHEN s.scheme_name = 'WDC-PMKSY 2.0' THEN s.scheme_name END) AS wdc_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'WDC-PMKSY 2.0' THEN a.no_mw END) AS wdc_no, " +
            "MAX(CASE WHEN s.scheme_name = 'WDC-PMKSY 2.0' THEN a.area_mw END) AS wdc_area, " +
            "MAX(CASE WHEN s.scheme_name = 'Others' THEN s.scheme_name END) AS other_scheme, " +
            "MAX(CASE WHEN s.scheme_name = 'Others' THEN a.no_mw END) AS other_no, " +
            "MAX(CASE WHEN s.scheme_name = 'Others' THEN a.area_mw END) AS other_area, " +
            "a.status " +
            "FROM m_scheme s " +
            "JOIN ppr_watershed_covered_area a ON s.scheme_id = a.scheme_id " +
            "JOIN m_micro_watershed w ON w.mw_id = a.mw_id " +
            "JOIN m_ppr p ON p.ppr_id = a.ppr_id " +
            "JOIN m_district d ON d.dcode = p.dcode " +
            "WHERE p.dcode = :dcode " +
            "GROUP BY a.ppr_id, d.dist_name, w.mw_id, w.mw_name, w.mw_area, a.status " +
            "ORDER BY CASE WHEN a.status = 'D' THEN 0 ELSE 1 END ",
    nativeQuery = true)
List<Object[]> findWatershedDataByDistrict(@Param("dcode") Integer dcode);


	


	
}
