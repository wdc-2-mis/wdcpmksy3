package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;

@Repository
public interface MPprRepository extends JpaRepository<MPpr, Integer> {

    MPpr findByProjectName(String projectName);

    List<MPpr> findByDistrictDcode(Integer dcode);

    List<MPpr> findByFinYearFinYrCd(Integer finYrCd);

    boolean existsByProjectName(String projectName);

	MPpr findByDistrict_Dcode(Integer district);
	
	List<MPpr> findByDistrict_DcodeAndStatusOrderByProjectNameAsc(Integer dcode, String status);

	@Query("SELECT p FROM MPpr p ORDER BY CASE WHEN p.status = 'C' THEN 1 ELSE 0 END, p.pprId ASC")
	List<MPpr> findAllOrderByStatusAndId();

	
}

