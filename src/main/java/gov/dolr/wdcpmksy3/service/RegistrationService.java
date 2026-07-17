package gov.dolr.wdcpmksy3.service;

import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gov.dolr.wdcpmksy3.dto.OtpResponse;
import gov.dolr.wdcpmksy3.dto.RegistrationDTO;
import gov.dolr.wdcpmksy3.dto.SendOtpRequest;
import gov.dolr.wdcpmksy3.dto.VerifyOtpRequest;
import gov.dolr.wdcpmksy3.entity.IwmpUserMap;
import gov.dolr.wdcpmksy3.entity.IwmpUserReg;
import gov.dolr.wdcpmksy3.entity.IwmpUserRegistrationOtp;
import gov.dolr.wdcpmksy3.repository.IwmpDistrictRepository;
import gov.dolr.wdcpmksy3.repository.IwmpStateRepository;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;
import gov.dolr.wdcpmksy3.repository.UserRegistrationOtpRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
@Transactional
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

	
	  @Autowired private UserRegistrationOtpRepository otpRepository;
	  
	  
	  @Autowired private ObjectMapper objectMapper;
	 
    
    @Autowired
    private IwmpStateRepository stateRepository;

    @Autowired
    private IwmpDistrictRepository districtRepository;
    
    @Autowired
	private UserMapRepository userMapRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${otp.service.url}")
    private String otpServiceUrl;
    
    
    public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}

    public String sendOtp(RegistrationDTO dto, HttpServletRequest servletRequest) {

        try {

            // Email already registered
			/*
			 * if (userRepository.existsByEmail(dto.getUserEmailId())) { return
			 * "EMAIL_ALREADY_EXISTS"; }
			 */

            SendOtpRequest request = new SendOtpRequest();

            request.setEmail(dto.getUserEmailId());

            request.setMobileNo(dto.getUserMobileNo());

            request.setModuleName("WDC-PMKSY");

            request.setReferenceId(
                    UUID.randomUUID().toString());

            request.setRequestIp(getClientIpAddr(servletRequest));

            request.setFromEmail("support-wdcpmksy@nic.in");

            request.setFromName("WDC-PMKSY 3.0");

            request.setSubject("Email Verification");

            request.setMessage(
                    "Dear User,\n\n"
                  + "Your One-Time Password (OTP) for WDC-PMKSY 3.0 is {{OTP}}.\n"
                  + "It is valid for 5 minutes.\n\n"
                  + "Do not share this OTP with anyone."
                  + "\n\n"
                  + "Regards,"
                  + "\nWDC-PMKSY 3.0"
                  + "\nDepartment of Land Resources"
                  + "\nGovernment of India");

            ResponseEntity<OtpResponse> response =
                    restTemplate.postForEntity(

                            otpServiceUrl + "/sendWDCOTP",

                            request,

                            OtpResponse.class);

            if (response.getBody() == null) {
                return "ERROR";
            }

            if ("SUCCESS".equalsIgnoreCase(response.getBody().getStatus())) {

                Optional<IwmpUserRegistrationOtp> optional =
                        otpRepository.findTopByEmailOrderByOtpIdDesc(dto.getUserEmailId());

                IwmpUserRegistrationOtp otpEntity;

                if (optional.isPresent()) {

                    otpEntity = optional.get();

                } else {

                    otpEntity = new IwmpUserRegistrationOtp();

                    otpEntity.setCreatedDate(LocalDateTime.now());
                    otpEntity.setVerified("N");
                    otpEntity.setResendCount(0);
                }

                otpEntity.setEmail(dto.getUserEmailId());
                otpEntity.setMobileNo(dto.getUserMobileNo());

                // OTP is NOT stored locally anymore
                otpEntity.setOtp(null);
                otpEntity.setOtpExpiry(null);

                otpEntity.setUpdatedDate(LocalDateTime.now());

                otpEntity.setRegistrationData(
                        objectMapper.writeValueAsString(dto));

                otpEntity.setRequestIp(getClientIpAddr(servletRequest));

                otpRepository.save(otpEntity);

                return "OTP_SENT";
            }

            return "ERROR";

        }
        catch (Exception e) {

            e.printStackTrace();

            return "ERROR";
        }

    }
    
    @Transactional
    public String resendOtp(String email) {

        try {

            SendOtpRequest request = new SendOtpRequest();

            request.setEmail(email);

            request.setFromEmail("support-wdcpmksy@nic.in");

            request.setFromName("WDC-PMKSY 3.0");

            request.setSubject("Email Verification");

            request.setMessage(
                    "Dear User,\n\n"
                  + "Your One-Time Password (OTP) for WDC-PMKSY 3.0 is {{OTP}}.\n"
                  + "It is valid for 5 minutes.\n\n"
                  + "Do not share this OTP with anyone."
                  + "\n\n"
                  + "Regards,"
                  + "\nWDC-PMKSY 3.0"
                  + "\nDepartment of Land Resources"
                  + "\nGovernment of India");

            ResponseEntity<OtpResponse> response =
                    restTemplate.postForEntity(

                            otpServiceUrl + "/resendWDC",

                            request,

                            OtpResponse.class);

            if (response.getBody() == null) {
                return "ERROR";
            }

            switch (response.getBody().getStatus()) {

                case "SUCCESS":
                    return "OTP_RESENT";

                case "NOT_FOUND":
                    return "NOT_FOUND";

                case "FAILED":
                    return "ERROR";

                default:
                    return "ERROR";
            }

        } catch (Exception e) {

            e.printStackTrace();

            return "ERROR";
        }

    }
    
    @Transactional
    public String verifyOtp(String email,
                            String enteredOtp,
                            HttpServletRequest request) {

        try {

            // Step 1 : Call Pune OTP Server

            VerifyOtpRequest verifyRequest = new VerifyOtpRequest();
            verifyRequest.setEmail(email);
            verifyRequest.setOtp(enteredOtp);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<VerifyOtpRequest> entity =
                    new HttpEntity<>(verifyRequest, headers);

            ResponseEntity<OtpResponse> response =
                    restTemplate.postForEntity(
                            otpServiceUrl + "/verifyWDC",
                            entity,
                            OtpResponse.class);

            OtpResponse otpResponse = response.getBody();

            if (otpResponse == null) {
                return "ERROR";
            }

            // Step 2 : OTP failed
            if (!"VERIFIED".equalsIgnoreCase(otpResponse.getStatus())) {
                return otpResponse.getStatus();
            }

            // Step 3 : OTP verified by Pune
            // Fetch registration data from WDC database

            Optional<IwmpUserRegistrationOtp> optional =
                    otpRepository.findTopByEmailOrderByOtpIdDesc(email);

            if (optional.isEmpty()) {
                return "OTP_NOT_FOUND";
            }

            IwmpUserRegistrationOtp otpEntity = optional.get();

            RegistrationDTO dto =
                    objectMapper.readValue(
                            otpEntity.getRegistrationData(),
                            RegistrationDTO.class);

            // Step 4 : Save user
            
            if (userRepository.existsByEmail(email)) {
                return "ALREADY_REGISTERED";
            }

            saveUser(dto, request);

            // Step 5 : Mark local record verified

            otpEntity.setVerified("Y");
            otpEntity.setUpdatedDate(LocalDateTime.now());

            otpRepository.save(otpEntity);

            return "SUCCESS";

        }
        catch (Exception e) {

            e.printStackTrace();
            return "ERROR";
        }
    }
    
    @Transactional
    public void saveUser(RegistrationDTO dto, HttpServletRequest request) {

        IwmpUserReg user = new IwmpUserReg();

        user.setRegId(getNextRegId());

        user.setCreationDate(new Timestamp(System.currentTimeMillis()));

        user.setStatus("PENDING");

        user.setUserType(dto.getUserType());

        if ("NGO".equals(dto.getUserType())) {

            user.setUserName(dto.getUserNameNgo());
            user.setNgoId(dto.getUserNgoid());
            user.setRegisterWith(dto.getUserRegwith());

        } else {

            user.setUserName(dto.getUserName());
            user.setDepartment(dto.getUserDepartment());
            user.setDesignation(dto.getUserDesignation());

        }

        user.setEmail(dto.getUserEmailId());

        user.setMobileNo(dto.getUserMobileNo());

        user.setCurAddress(dto.getUserAddres());

        userRepository.save(user);

        saveUserMap(user, dto, request);
    }
    
    private Integer getNextRegId() {

	    Integer max = userRepository.findMaxRegId();

	    return max == null ? 1 : max + 1;

	}
    
        @Transactional
        private void saveUserMap(IwmpUserReg user, RegistrationDTO dto, HttpServletRequest request) {

            IwmpUserMap map = new IwmpUserMap();

			/* map.setMapId(getNextMapId()); */

           map.setUser(user);

            map.setState(
                    stateRepository.getReferenceById(dto.getUserState())
            );

            if (dto.getUserDistrict() != null) {

                map.setDistrict(
                        districtRepository.getReferenceById(dto.getUserDistrict())
                );
            }

            map.setCreatorDate(LocalDate.now());
         //   map.setCreatorId("SELF REGISTRATION");

            
            // Optional (replace with actual client IP if available)
            map.setRequestIp(getClientIpAddr(request));

            // Save Mapping
            userMapRepository.save(map);
        }

        private Integer getNextMapId() {

            Integer max = userMapRepository.findMaxMapId();

            return (max == null) ? 1 : max + 1;
        }
    }
