package gov.dolr.wdcpmksy3.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.dto.ActivatePiaUserResponse;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserMap;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserAppRoleMapRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserProjectMapRepository;

@Service
public class PIAUserActivationService {

	private final UserRepository userRegRepository;
	
	 private final SecureRandom secureRandom = new SecureRandom();
	 
	 

	 private final BCryptPasswordEncoder encoder =
	            new BCryptPasswordEncoder(12);
	 
	 private final PiaMailService piaMailService;
	 
	 public PIAUserActivationService(
		        UserRepository userRegRepository,
		        PiaMailService piaMailService) {

		    this.userRegRepository = userRegRepository;
		    this.piaMailService = piaMailService;
		}
   
	 
	 @Autowired
	 private UserMapRepository userMapRepository;

	 @Autowired
	 private WdcpmksyUserAppRoleMapRepository userAppRoleMapRepository;
	 

	 @Autowired
	 private WdcpmksyUserProjectMapRepository userProjectMapRepository;
	 
	public List<WdcpmksyUserReg> searchPiaUsers(String userId, String userName, String userType, String status, Integer stCode) {
		
		return userRegRepository.searchPiaUsers(
                userId,
                userName,
                userType,
                status, stCode
        );
	}
	
	public List<WdcpmksyUserReg> searchPiaUsers(
	        String userId,
	        String userName,
	        String userType,
	        String status,
	        Integer stCode,
	        String loggedUserType) {

	    if ("ADMIN".equalsIgnoreCase(loggedUserType)) {
	        // ignore stCode filter for admin
	        return userRegRepository.searchUsersAdmin(userId, userName, userType, status);
	    } else {
	        // normal SLNA/WCDC etc. restricted by state
	        return userRegRepository.searchPiaUsers(userId, userName, userType, status, stCode);
	    }
	}

	
	@Transactional
	public ActivatePiaUserResponse activateUser(Integer regId) {

	    try {

	       
	        WdcpmksyUserReg user =
	                userRegRepository.findById(regId.longValue())
	                        .orElseThrow(() ->
	                                new RuntimeException("User not found.")
	                        );

           if ("Active".equalsIgnoreCase(user.getStatus())) {

	            return new ActivatePiaUserResponse(
	                    "ERROR",
	                    "This user account is already active.",
	                    null,
	                    null,
	                    null
	            );
	        }


	        if (user.getUserMappings() == null
	                || user.getUserMappings().isEmpty()) {

	            return new ActivatePiaUserResponse(
	                    "ERROR",
	                    "State mapping not found for this user.",
	                    null,
	                    null,
	                    null
	            );
	        }

	        WdcpmksyUserMap userMap =
	                user.getUserMappings().get(0);


	        Integer stCode = null;

	        if (userMap.getState() != null) {

	            stCode =
	                    userMap.getState().getStCode();
	        }

	        if (stCode == null) {

	            return new ActivatePiaUserResponse(
	                    "ERROR",
	                    "State Code is missing.",
	                    null,
	                    null,
	                    null
	            );
	        }


	        String userType = user.getUserType();

	        if (userType == null
	                || userType.trim().isEmpty()) {

	            return new ActivatePiaUserResponse(
	                    "ERROR",
	                    "User Type is missing.",
	                    null,
	                    null,
	                    null
	            );
	        }

	        userType =
	                userType.trim().toUpperCase();


	        Integer dcode = null;

	        if ("PI".equals(userType)
	                || "DI".equals(userType)) {

	            if (userMap.getDistrict() == null) {

	                return new ActivatePiaUserResponse(
	                        "ERROR",
	                        "District mapping not found for this user.",
	                        null,
	                        null,
	                        null
	                );
	            }

	            dcode =
	                    userMap.getDistrict().getDcode();

	            if (dcode == null) {

	                return new ActivatePiaUserResponse(
	                        "ERROR",
	                        "District Code is missing for this user.",
	                        null,
	                        null,
	                        null
	                );
	            }
	        }


	        String newUserId;

	        switch (userType) {

	            case "PI":

	                newUserId =
	                        generateUserId(
	                                "PI",
	                                stCode,
	                                dcode,
	                                3
	                        );

	                break;


	            case "SL":

	                newUserId =
	                        generateUserId(
	                                "SL",
	                                stCode,
	                                0,
	                                2
	                        );

	                break;


	            case "DL":

	                newUserId =
	                        generateUserId(
	                                "DL",
	                                stCode,
	                                0,
	                                3
	                        );

	                break;


	            case "DI":
         newUserId =
	                        generateUserId(
	                                "DI",
	                                stCode,
	                                dcode,
	                                2
	                        );

	                break;


	            default:

	                return new ActivatePiaUserResponse(
	                        "ERROR",
	                        "Unsupported user type: " + userType,
	                        null,
	                        null,
	                        null
	                );
	        }

    String rawPassword =
	                generatePassword();

     String encryptedPassword =
	                encoder.encode(rawPassword);


	        user.setUserId(newUserId);
	        user.setEncryptedPass(encryptedPassword);
	        user.setStatus("Active");

	        userRegRepository.save(user);


	        boolean emailSent =
	                piaMailService.sendCredentialMail(
	                        user.getEmail(),
	                        user.getUserName(),
	                        newUserId,
	                        rawPassword
	                );


	        ActivatePiaUserResponse response =
	                new ActivatePiaUserResponse(
	                        "SUCCESS",
	                        "User account activated successfully.",
	                        user.getUserName(),
	                        newUserId,
	                        rawPassword
	                );


	        if (emailSent) {

	            response.setEmailStatus("SENT");

	            response.setEmailMessage(
	                    "Login credentials sent successfully to the registered email."
	            );

	        } else {

	            response.setEmailStatus("FAILED");

	            response.setEmailMessage(
	                    "User activated successfully, but the credential email could not be sent."
	            );
	        }


	        return response;


	    } catch (Exception e) {

	        e.printStackTrace();

	        return new ActivatePiaUserResponse(
	                "ERROR",
	                "Unable to activate user.",
	                null,
	                null,
	                null
	        );
	    }
	}

	private String generateUserId(
	        String prefix,
	        Integer stCode,
	        Integer dcode,
	        int randomLength) {

	    String stateCode =
	            String.format("%02d", stCode);

   String districtCode;

	    if ("SL".equalsIgnoreCase(prefix)
	            || "DL".equalsIgnoreCase(prefix)) {

	        districtCode = "00";

	    } else {

	        districtCode =
	                String.format("%03d", dcode);
	    }


	    String userId;

	    do {

	        String randomLetters =
	                randomLetters(randomLength);

	        userId =
	                prefix
	                + stateCode
	                + districtCode
	                + randomLetters;

	    } while (userRegRepository.existsByUserId(userId));


	    return userId;
	}


    private String randomLetters(int length) {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder result =
                new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int index =
                    secureRandom.nextInt(characters.length());

            result.append(
                    characters.charAt(index)
            );
        }

        return result.toString();
    }


    private String generatePassword() {

        String upper =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String lower =
                "abcdefghijklmnopqrstuvwxyz";

        String numbers =
                "0123456789";

        String special =
                "@#$%&*!";

        String all =
                upper + lower + numbers + special;


        StringBuilder password =
                new StringBuilder();


        // Guarantee required characters

        password.append(
                upper.charAt(
                        secureRandom.nextInt(upper.length())
                )
        );

        password.append(
                lower.charAt(
                        secureRandom.nextInt(lower.length())
                )
        );

        password.append(
                numbers.charAt(
                        secureRandom.nextInt(numbers.length())
                )
        );

        password.append(
                special.charAt(
                        secureRandom.nextInt(special.length())
                )
        );


        // Remaining 4 characters

        for (int i = 4; i < 8; i++) {

            password.append(
                    all.charAt(
                            secureRandom.nextInt(all.length())
                    )
            );
        }


        // Shuffle password characters

        char[] chars =
                password.toString().toCharArray();

        for (int i = chars.length - 1; i > 0; i--) {

            int j =
                    secureRandom.nextInt(i + 1);

            char temp = chars[i];

            chars[i] = chars[j];

            chars[j] = temp;
        }

        return new String(chars);
    }




    @Transactional
    public void deleteNewUser(Integer regId) {

        WdcpmksyUserReg user = userRegRepository.findById(regId.longValue())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        if (user.getUserId() != null
                && !user.getUserId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "User cannot be deleted because User ID has already been generated."
            );
        }

        if (userAppRoleMapRepository.existsByRegId(regId)) {

            throw new IllegalArgumentException(
                    "User cannot be deleted because a role is already assigned."
            );
        }

        if (userProjectMapRepository.existsByUser_RegId(regId)) {

            throw new IllegalArgumentException(
                    "User cannot be deleted because a project is already assigned."
            );
        }

        userMapRepository.deleteByUser_RegId(regId);

       
        userRegRepository.delete(user);
    }

    @Transactional
    public boolean deactivateUser(
            Integer regId,
            String updatedBy) {

        WdcpmksyUserReg user = userRegRepository.findById(regId.longValue())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        if (!"Active".equalsIgnoreCase(user.getStatus())) {

            throw new IllegalArgumentException(
                    "Only active users can be inactivated."
            );
        }

        user.setStatus("Inactive");
        user.setLastUpdatedBy(updatedBy);
        user.setLastUpdatedDate(new java.sql.Date(System.currentTimeMillis()));

        userRegRepository.save(user);

        boolean mailSent = piaMailService.sendInactiveMail(
                user.getEmail(),
                user.getUserName(),
                user.getUserId()
        );

        return mailSent;
    }

    @Transactional
    public boolean reactivateUser(
            Integer regId,
            String updatedBy) {

        WdcpmksyUserReg user = userRegRepository.findById(regId.longValue())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        if (!"Inactive".equalsIgnoreCase(user.getStatus())) {

            throw new IllegalArgumentException(
                    "Only inactive users can be activated."
            );
        }

        if (user.getUserId() == null
                || user.getUserId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "User ID is missing for this inactive account."
            );
        }

        if (user.getEncryptedPass() == null
                || user.getEncryptedPass().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Password is missing for this inactive account."
            );
        }

       
        user.setStatus("Active");
        user.setLastUpdatedBy(updatedBy);
        user.setLastUpdatedDate(new java.sql.Date(System.currentTimeMillis()));

        userRegRepository.save(user);

        boolean mailSent = piaMailService.sendReactivatedMail(
                user.getEmail(),
                user.getUserName(),
                user.getUserId()
        );

        return mailSent;
    }

}
