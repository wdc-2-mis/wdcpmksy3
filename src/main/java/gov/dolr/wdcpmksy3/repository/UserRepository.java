package gov.dolr.wdcpmksy3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.IwmpUserReg;




@Repository
public interface UserRepository extends JpaRepository<IwmpUserReg, Long> {

    Optional<IwmpUserReg> findByEmail(String email);
    
    @Query("SELECT u FROM IwmpUserReg u WHERE u.userId = upper(:userId) AND u.status = 'Active'")
    Optional<IwmpUserReg> findByUserId(@Param("userId") String userId);
    
   // Optional<IwmpUserReg> findByUserId(String userId);
    
    boolean existsByEmail(String email);
    
    @Query(value = "select user_name, user_type, cur_address, department, designation, email, mobile_no, status, user_id, "
    		+ "case when user_type = 'DL' OR user_type = 'ADMIN' then 0 else (select distinct st_code from iwmp_user_map where reg_id = ureg.reg_id) end as statecd,"
    		+ " rolemap.home_page as home_page, role_name, ureg.reg_id, case when user_type = 'DL' OR user_type = 'ADMIN' then null else "
    		+ "(select distinct st_name from iwmp_state where st_code in (select distinct st_code from iwmp_user_map where reg_id = ureg.reg_id)) end as stname "
    		+ "from iwmp_user_reg ureg,  iwmp_user_app_role_map, iwmp_app_role_map rolemap where upper(ureg.user_id) = upper(:userId) and Lower(status)=Lower('ACTIVE')  "
    		+ "and (iwmp_user_app_role_map.role_id=rolemap.role_id) and iwmp_user_app_role_map.reg_id=ureg.reg_id",nativeQuery = true)
    List<Object[]> getUserList(@Param("userId") String userId);
    
    @Query(value = "select email from iwmp_user_reg where user_id=upper(:value)",nativeQuery = true)
    String getEmail(String value);
    
    
    @Query("SELECT u FROM IwmpUserReg u WHERE u.regId = :regid AND u.status = 'Active'")
    List<IwmpUserReg> getUserDetail(@Param("regid") Integer regid);
    
    @Query(value = "select user_name, user_type, cur_address, department, designation, email, mobile_no, status, user_id,"
    		+ "case when user_type = 'DL' OR user_type = 'ADMIN' then 0 else (select distinct st_code from iwmp_user_map "
    		+ "where reg_id = ureg.reg_id) end as statecd, rolemap.home_page as home_page, role_name, ureg.reg_id, "
    		+ "case when user_type = 'DL' OR user_type = 'ADMIN' then null else "
    		+ "(select distinct st_name from iwmp_state where st_code in (select distinct st_code from iwmp_user_map where "
    		+ "reg_id = ureg.reg_id)) end as stname, encrypted_pass, encrypted_pass_second from iwmp_user_reg ureg,  iwmp_user_app_role_map, iwmp_app_role_map rolemap "
    		+ "where upper(ureg.user_id) = upper(:userId) and Lower(status)=Lower('ACTIVE') "
    		+ "and (iwmp_user_app_role_map.role_id=rolemap.role_id) and iwmp_user_app_role_map.reg_id=ureg.reg_id",nativeQuery = true)
    List<Object[]> getUserVerify(@Param("userId") String userId);
    
   

    
   
    
}
