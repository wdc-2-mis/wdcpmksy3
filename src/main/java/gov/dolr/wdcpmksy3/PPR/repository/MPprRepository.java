package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;

@Repository
public interface MPprRepository extends JpaRepository<MPpr, Integer> {

    MPpr findByProjectName(String projectName);

    List<MPpr> findByDistrictDcode(Integer dcode);

    List<MPpr> findByFinYearFinYrCd(Integer finYrCd);

    boolean existsByProjectName(String projectName);

	MPpr findByDistrict_Dcode(Integer district);
	
	List<MPpr> findByDistrict_DcodeAndStatusOrderByProjectNameAsc(Integer dcode, String status);
}

