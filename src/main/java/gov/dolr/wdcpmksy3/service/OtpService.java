package gov.dolr.wdcpmksy3.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gov.dolr.wdcpmksy3.dto.OtpResponse;
import gov.dolr.wdcpmksy3.dto.SendOtpRequest;
import gov.dolr.wdcpmksy3.dto.VerifyOtpRequest;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;



@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${otp.service.url}")
    private String otpServiceUrl;

	/*
	 * @Autowired private EmailService emailService;
	 */
   

    public void sendOtp(String email,
            HttpServletRequest request) {

            SendOtpRequest otpRequest = new SendOtpRequest();

            otpRequest.setEmail(email);

            otpRequest.setModuleName("LOGIN");

            otpRequest.setReferenceId(UUID.randomUUID().toString());

            otpRequest.setRequestIp(RegistrationService.getClientIpAddr(request));

            otpRequest.setFromEmail("support-wdcpmksy@nic.in");

            otpRequest.setFromName("WDC-PMKSY 3.0");

            otpRequest.setSubject("Login OTP");

            otpRequest.setMessage(
             "Dear User,\n\n"
             + "Your Login OTP is {{OTP}}.\n"
             + "It is valid for 5 minutes.\n\n"
             + "Regards,\n"
             + "WDC-PMKSY 3.0");

            ResponseEntity<OtpResponse> response = restTemplate.postForEntity(otpServiceUrl + "/sendWDCOTP", otpRequest, OtpResponse.class);

            if (response.getBody() == null || !"SUCCESS".equalsIgnoreCase(response.getBody().getStatus())) {

           throw new RuntimeException("Unable to send OTP");
           }
         }

    public boolean verifyOtp(String userId, String otp) {

         Optional<WdcpmksyUserReg> optionalUser = userRepository.findByUserId(userId);
         if (optionalUser.isEmpty()) {
          return false;
          }

       VerifyOtpRequest request = new VerifyOtpRequest();
       request.setEmail(optionalUser.get().getEmail());
       request.setOtp(otp);
       ResponseEntity<OtpResponse> response = restTemplate.postForEntity(otpServiceUrl + "/verifyWDC", request, OtpResponse.class);
       OtpResponse otpResponse = response.getBody();
       return otpResponse != null && "VERIFIED".equalsIgnoreCase(otpResponse.getStatus());
     }
    
    public boolean checkEmailExists(String email) {
    	
        return userRepository.existsByEmail(email);
    }
    
    public List<Object[]> getUserList(String userId) {
    	
        return userRepository.getUserList(userId);
    }
    
    public boolean verifyUserOtp(String userid, String otp) {

        Optional<WdcpmksyUserReg> optionalUser =userRepository.findByUserId(userid);

        if(optionalUser.isPresent()) {

        	WdcpmksyUserReg user = optionalUser.get();

            return otp.equals(user.getOtp())
                    && user.getOtpExpiry().isAfter(LocalDateTime.now());
        }

        return false;
    }
    
    public List<Object[]> getUserVerify(String userId) {
    	
        return userRepository.getUserVerify(userId);
    }
    
    
}
