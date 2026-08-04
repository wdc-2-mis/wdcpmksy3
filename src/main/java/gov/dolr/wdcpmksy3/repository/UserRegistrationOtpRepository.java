package gov.dolr.wdcpmksy3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyUserRegistrationOtp;

public interface UserRegistrationOtpRepository
        extends JpaRepository<WdcpmksyUserRegistrationOtp, Long> {

    Optional<WdcpmksyUserRegistrationOtp> findTopByEmailOrderByOtpIdDesc(String email);

	Optional<WdcpmksyUserRegistrationOtp> findTopByEmailAndVerifiedOrderByOtpIdDesc(String email, String verified);
    
    

}
