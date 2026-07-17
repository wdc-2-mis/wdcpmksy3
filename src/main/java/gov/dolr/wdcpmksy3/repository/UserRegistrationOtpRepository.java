package gov.dolr.wdcpmksy3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.entity.IwmpUserRegistrationOtp;

public interface UserRegistrationOtpRepository
        extends JpaRepository<IwmpUserRegistrationOtp, Long> {

    Optional<IwmpUserRegistrationOtp> findTopByEmailOrderByOtpIdDesc(String email);

	Optional<IwmpUserRegistrationOtp> findTopByEmailAndVerifiedOrderByOtpIdDesc(String email, String verified);
    
    

}
