package gov.dolr.wdcpmksy3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.UserReg;



@Repository
public interface UserRepository extends JpaRepository<UserReg, Long> {

    Optional<UserReg> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
