package gov.dolr.wdcpmksy3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;




@Repository
public interface UserRepository extends JpaRepository<WdcpmksyUserReg, Long> {

    Optional<WdcpmksyUserReg> findByEmail(String email);
    
    @Query("SELECT u FROM WdcpmksyUserReg u WHERE u.userId = upper(:userId) AND u.status = 'Active'")
    Optional<WdcpmksyUserReg> findByUserId(@Param("userId") String userId);
    
   // Optional<WdcpmksyUserReg> findByUserId(String userId);
    
    boolean existsByEmail(String email);
    
    @Query(value = "select user_name, user_type, cur_address, department, designation, email, mobile_no, status, user_id, "
    		+ "case when user_type = 'DL' OR user_type = 'ADMIN' then 0 else (select distinct st_code from wdcpmksy_user_map where reg_id = ureg.reg_id) end as statecd,"
    		+ " rolemap.home_page as home_page, role_name, ureg.reg_id, case when user_type = 'DL' OR user_type = 'ADMIN' then null else "
    		+ "(select distinct st_name from m_state where st_code in (select distinct st_code from wdcpmksy_user_map where reg_id = ureg.reg_id)) end as stname "
    		+ "from wdcpmksy_user_reg ureg,  wdcpmksy_user_app_role_map, m_role rolemap where upper(ureg.user_id) = upper(:userId) and Lower(status)=Lower('ACTIVE')  "
    		+ "and (wdcpmksy_user_app_role_map.role_id=rolemap.role_id) and wdcpmksy_user_app_role_map.reg_id=ureg.reg_id",nativeQuery = true)
    List<Object[]> getUserList(@Param("userId") String userId);
    
    @Query(value = "select email from wdcpmksy_user_reg where user_id=upper(:value)",nativeQuery = true)
    String getEmail(String value);
    
    
    @Query("SELECT u FROM WdcpmksyUserReg u WHERE u.regId = :regid AND u.status = 'Active'")
    List<WdcpmksyUserReg> getUserDetail(@Param("regid") Integer regid);
    
    @Query(value = "select user_name, user_type, cur_address, department, designation, email, mobile_no, status, user_id,"
    		+ "case when user_type = 'DL' OR user_type = 'ADMIN' then 0 else (select distinct st_code from wdcpmksy_user_map "
    		+ "where reg_id = ureg.reg_id) end as statecd, rolemap.home_page as home_page, role_name, ureg.reg_id, "
    		+ "case when user_type = 'DL' OR user_type = 'ADMIN' then null else "
    		+ "(select distinct st_name from m_state where st_code in (select distinct st_code from wdcpmksy_user_map where "
    		+ "reg_id = ureg.reg_id)) end as stname, encrypted_pass, encrypted_pass_second from wdcpmksy_user_reg ureg,  wdcpmksy_user_app_role_map, m_role rolemap "
    		+ "where upper(ureg.user_id) = upper(:userId) and Lower(status)=Lower('ACTIVE') "
    		+ "and (wdcpmksy_user_app_role_map.role_id=rolemap.role_id) and wdcpmksy_user_app_role_map.reg_id=ureg.reg_id",nativeQuery = true)
    List<Object[]> getUserVerify(@Param("userId") String userId);

    @Query("select max(u.regId) from WdcpmksyUserReg u")
    Integer findMaxRegId();
    
    
    @Query("""
            SELECT DISTINCT u
            FROM WdcpmksyUserReg u
            JOIN u.userMappings um
            JOIN WdcpmksyUserAppRoleMap ur
                ON ur.regId = u.regId
            JOIN ur.role r
            WHERE u.userType = 'DL'
              AND um.state.stCode = :stcode
              AND r.roleName = 'DOLR'
        """)
        List<WdcpmksyUserReg> findDLUsersByStateAndRole(
                @Param("stcode") Integer stcode);
    
    @Query("""
    	    SELECT DISTINCT u
    	    FROM WdcpmksyUserReg u
    	    JOIN u.userMappings um
    	    JOIN um.state state
    	    WHERE state.stCode = :stCode
    	      AND (:userId IS NULL OR :userId = ''
    	           OR UPPER(u.userId) LIKE UPPER(CONCAT(:userId, '%')))
    	      AND (:userName IS NULL OR :userName = ''
    	           OR UPPER(u.userName) LIKE UPPER(CONCAT(:userName, '%')))
    	      AND (:userType IS NULL OR :userType = ''
    	           OR UPPER(u.userType) = UPPER(:userType))
    	      AND (:status IS NULL OR :status = ''
    	           OR UPPER(u.status) = UPPER(:status))
    	    ORDER BY u.regId
    	    """)
    	List<WdcpmksyUserReg> searchPiaUsers(
    	        @Param("userId") String userId,
    	        @Param("userName") String userName,
    	        @Param("userType") String userType,
    	        @Param("status") String status,
    	        @Param("stCode") Integer stCode
    	);
    @Query("""
    	    SELECT DISTINCT u
    	    FROM WdcpmksyUserReg u
    	    JOIN u.userMappings um
    	    JOIN um.state state
    	    WHERE  (:userId IS NULL OR :userId = ''
    	           OR UPPER(u.userId) LIKE UPPER(CONCAT(:userId, '%')))
    	      AND (:userName IS NULL OR :userName = ''
    	           OR UPPER(u.userName) LIKE UPPER(CONCAT(:userName, '%')))
    	      AND (:userType IS NULL OR :userType = ''
    	           OR UPPER(u.userType) = UPPER(:userType))
    	      AND (:status IS NULL OR :status = ''
    	           OR UPPER(u.status) = UPPER(:status))
    	    ORDER BY u.regId
    	    """)
    List<WdcpmksyUserReg> searchUsersAdmin(@Param("userId") String userId, @Param("userName") String userName, @Param("userType") String userType, @Param("status") String status);

    boolean existsByUserId(String userId);
   
    @Query("""
    	    SELECT DISTINCT u
    	    FROM WdcpmksyUserReg u
    	    JOIN u.userMappings um
    	    WHERE um.state.stCode = :stCode
    	      AND um.district.dcode = :dcode
    	      AND UPPER(u.status) = 'ACTIVE'
    	      AND (
    	            :userType IS NULL
    	            OR :userType = ''
    	            OR UPPER(:userType) = 'ALL'
    	            OR UPPER(u.userType) = UPPER(:userType)
    	          )
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findMappedUsersForRoleMapping(
    	        @Param("stCode") Integer stCode,
    	        @Param("dcode") Integer dcode,
    	        @Param("userType") String userType
    	);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMobileNo(String mobileNo);
    
    @Query("""
    	    SELECT u
    	    FROM WdcpmksyUserReg u
    	    WHERE UPPER(u.status) = 'ACTIVE'
    	      AND UPPER(u.userType) = UPPER(:userType)
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findUsersByTypeForRoleMapping(
    	        @Param("userType") String userType
    	);
    
 // Users having a state + district mapping
    @Query("""
    	    SELECT u
    	    FROM WdcpmksyUserReg u
    	    WHERE EXISTS (
    	        SELECT 1
    	        FROM WdcpmksyUserMap um
    	        WHERE um.user.regId = u.regId
    	          AND um.state.stCode = :stCode
    	          AND um.district.dcode = :dcode
    	    )
    	      AND UPPER(u.status) = 'ACTIVE'
    	      AND UPPER(u.userType) = UPPER(:userType)
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findUsersByStateAndDistrict(
    	        @Param("stCode") Integer stCode,
    	        @Param("dcode") Integer dcode,
    	        @Param("userType") String userType
    	);
    
    @Query("""
    	    SELECT u
    	    FROM WdcpmksyUserReg u
    	    WHERE EXISTS (
    	        SELECT 1
    	        FROM WdcpmksyUserMap um
    	        WHERE um.user.regId = u.regId
    	          AND um.state.stCode = :stCode
    	    )
    	      AND UPPER(u.status) = 'ACTIVE'
    	      AND UPPER(u.userType) = UPPER(:userType)
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findUsersByState(
    	        @Param("stCode") Integer stCode,
    	        @Param("userType") String userType
    	);

	
    @Query("""
    	    SELECT u
    	    FROM WdcpmksyUserReg u
    	    WHERE EXISTS (
    	        SELECT 1
    	        FROM WdcpmksyUserMap um
    	        WHERE um.user.regId = u.regId
    	          AND um.state.stCode = :stCode
    	          AND um.district.dcode = :dcode
    	    )
    	      AND NOT EXISTS (
    	          SELECT 1
    	          FROM WdcpmksyUserAppRoleMap urm
    	          WHERE urm.regId = u.regId
    	      )
    	      AND NOT EXISTS (
    	          SELECT 1
    	          FROM WdcpmksyUserProjectMap upm
    	          WHERE upm.user.regId = u.regId
    	      )
    	      AND UPPER(u.status) = 'ACTIVE'
    	      AND UPPER(u.userType) = 'PI'
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findAvailablePiaUsers(
    	        @Param("stCode") Integer stCode,
    	        @Param("dcode") Integer dcode
    	);
    
    
    

    @Query("""
    	    SELECT u
    	    FROM WdcpmksyUserReg u
    	    WHERE UPPER(u.status) = 'ACTIVE'
    	      AND UPPER(u.userType) = 'DL'
    	      AND NOT EXISTS (
    	          SELECT 1
    	          FROM WdcpmksyUserAppRoleMap urm
    	          WHERE urm.regId = u.regId
    	      )
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findAvailableDoLRUsers();
    
    @Query("""
    	    SELECT u
    	    FROM WdcpmksyUserReg u
    	    WHERE EXISTS (
    	        SELECT 1
    	        FROM WdcpmksyUserMap um
    	        WHERE um.user.regId = u.regId
    	          AND um.state.stCode = :stCode
    	    )
    	      AND NOT EXISTS (
    	          SELECT 1
    	          FROM WdcpmksyUserAppRoleMap urm
    	          WHERE urm.regId = u.regId
    	      )
    	      AND UPPER(u.status) = 'ACTIVE'
    	      AND UPPER(u.userType) = 'SL'
    	    ORDER BY UPPER(u.userName)
    	    """)
    	List<WdcpmksyUserReg> findAvailableSlnaUsers(
    	        @Param("stCode") Integer stCode
    	);
    
   
    
}
