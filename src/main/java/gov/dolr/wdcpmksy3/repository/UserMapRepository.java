package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.IwmpUserMap;

@Repository
public interface UserMapRepository extends JpaRepository<IwmpUserMap, Integer> {

	@Query(value =
            "select cast(st_code as integer) as stateCode from iwmp_user_map " +
            "where reg_id=:regid order by stateCode",nativeQuery = true)
    List<ProfileProjection> getMapAdmin(@Param("regid") Integer regid);


    @Query(value =
            "select cast(st_code as integer) as stateCode, " +
            "st_name as stateName " +
            "from iwmp_state " +
            "where st_code in " +
            "(select st_code from iwmp_user_map where reg_id=:regid) " +
            "order by stateCode",
            nativeQuery = true)
    List<ProfileProjection> getMapState(@Param("regid") Integer regid);


    @Query(value =
            "select cast(st_code as integer) as stateCode, " +
            "(select st_name from iwmp_state s where s.st_code=d.st_code) as stateName, " +
            "cast(dcode as integer) as districtCode, " +
            "dist_name as districtName " +
            "from iwmp_district d " +
            "where dcode in " +
            "(select dcode from iwmp_user_map where reg_id=:regid) " +
            "and st_code in " +
            "(select st_code from iwmp_user_map where reg_id=:regid) " +
            "order by stateCode",
            nativeQuery = true)
    List<ProfileProjection> getMapDistrict(@Param("regid") Integer regid);


    @Query(value =
            "select distinct " +
            "cast(m.st_code as integer) as stateCode, " +
            "(select st_name from iwmp_state s where s.st_code=m.st_code) as stateName, " +
            "cast(m.dcode as integer) as districtCode, " +
            "(select dist_name from iwmp_district d " +
            " where d.st_code=m.st_code and d.dcode=m.dcode) as districtName, " +
            "pm.proj_id as projectCode, " +
            "(select proj_name from iwmp_m_project p " +
            " where p.dcode=m.dcode and p.proj_id=pm.proj_id) as projectName " +
            "from iwmp_user_map m " +
            "left join iwmp_user_project_map pm on m.reg_id=pm.reg_id " +
            "where m.reg_id=:regid " +
            "order by projectName",
            nativeQuery = true)
    List<ProfileProjection> getMapProject(@Param("regid") Integer regid);


    @Query("SELECT MAX(m.mapId) FROM IwmpUserMap m")
    Integer findMaxMapId();


	


 

}