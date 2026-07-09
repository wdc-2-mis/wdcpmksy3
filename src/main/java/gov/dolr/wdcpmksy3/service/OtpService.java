package gov.dolr.wdcpmksy3.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.IwmpUserReg;
import gov.dolr.wdcpmksy3.repository.UserRepository;



@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public String generateOtp() {
        return String.valueOf(
                (int)(Math.random() * 900000) + 100000
        );
    }

    public void sendOtp(String email) {

        String otp = generateOtp();

        IwmpUserReg user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

    //    user.setEmail(email);
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(30));

        userRepository.save(user);
        
    /*    UserReg user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        userRepository.save(user);   */

        emailService.sendOtp(email, otp);
    }

    public boolean verifyOtp(String userId, String otp) {

        Optional<IwmpUserReg> optionalUser =
                userRepository.findByUserId(userId);

        if(optionalUser.isPresent()) {

        	IwmpUserReg user = optionalUser.get();

            return otp.equals(user.getOtp())
                    && user.getOtpExpiry().isAfter(LocalDateTime.now());
        }

        return false;
    }
    public boolean checkEmailExists(String email) {
    	
        return userRepository.existsByEmail(email);
    }
    
    public List<Object[]> getUserList(String userId) {
    	
        return userRepository.getUserList(userId);
    }
    
    public boolean verifyUserOtp(String userid, String otp) {

        Optional<IwmpUserReg> optionalUser =userRepository.findByUserId(userid);

        if(optionalUser.isPresent()) {

        	IwmpUserReg user = optionalUser.get();

            return otp.equals(user.getOtp())
                    && user.getOtpExpiry().isAfter(LocalDateTime.now());
        }

        return false;
    }
    
    public List<Object[]> getUserVerify(String userId) {
    	
        return userRepository.getUserVerify(userId);
    }
    
    
}
