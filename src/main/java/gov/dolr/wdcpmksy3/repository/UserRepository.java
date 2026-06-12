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
    
    boolean existsByEmail(String email);
    
    @Query(value = "select r.reg_id, cur_address, department, designation, email, mobile_no, status, user_id, user_name, user_type, dcode,"
    		+ " (select dist_name from iwmp_district where m.dcode=dcode) as distname, st_code, (select st_name from iwmp_state where m.st_code=st_code) as stname "
    		+ "from iwmp_user_reg r, iwmp_user_map m where r.reg_id=m.reg_id and r.email=:emailid",nativeQuery = true)
    List<Object[]> getUserList(@Param("emailid") String emailid);
    
    
    
    
    
}
