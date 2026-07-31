package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.IwmpDistrict;

@Repository
public interface IwmpDistrictRepository extends JpaRepository<IwmpDistrict, Integer> {

    List<IwmpDistrict> findByState_StCodeOrderByDistNameAsc(Integer stCode);
    
    @Query(" SELECT d FROM IwmpDistrict d  WHERE d.state.stCode = :stCode AND d.dcode "
    		+ "IN ( SELECT w.dcode FROM PPRWcdcDetails w WHERE w.status = 'C' ) ORDER BY d.distName")
        List<IwmpDistrict> findCompletedDistrictsByState(@Param("stCode") Integer stCode);

    
    @Query("SELECT DISTINCT p.district FROM MPpr p WHERE p.district.state.stCode = :stcode AND p.status = 'C' ORDER BY p.district.distName")
	List<IwmpDistrict> getPPRDistrictsByState(Integer stcode);
}
