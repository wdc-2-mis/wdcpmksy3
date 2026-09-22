package gov.dolr.wdcpmksy3.service;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.dto.ActivatePiaUserResponse;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserMap;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.UserRepository;

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

	        // -------------------------------------------------
	        // 1. Find user
	        // -------------------------------------------------

	        WdcpmksyUserReg user =
	                userRegRepository.findById(regId.longValue())
	                        .orElseThrow(() ->
	                                new RuntimeException("User not found.")
	                        );


	        // -------------------------------------------------
	        // 2. Check current status
	        // -------------------------------------------------

	        if ("Active".equalsIgnoreCase(user.getStatus())) {

	            return new ActivatePiaUserResponse(
	                    "ERROR",
	                    "This user account is already active.",
	                    null,
	                    null,
	                    null
	            );
	        }


	        // -------------------------------------------------
	        // 3. Get user's mapping
	        // -------------------------------------------------

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


	        // -------------------------------------------------
	        // 4. Get State Code
	        // -------------------------------------------------

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


	        // -------------------------------------------------
	        // 5. Get User Type
	        // -------------------------------------------------

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


	        // -------------------------------------------------
	        // 6. Get District Code
	        //
	        // PI and DI require district.
	        // SL and DL do NOT require district.
	        // -------------------------------------------------

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


	        // -------------------------------------------------
	        // 7. Generate User ID
	        // -------------------------------------------------

	        String newUserId;

	        switch (userType) {

	            case "PI":

	                // PI + 2 digit state + 3 digit district
	                // + 3 random alphabets

	                newUserId =
	                        generateUserId(
	                                "PI",
	                                stCode,
	                                dcode,
	                                3
	                        );

	                break;


	            case "SL":

	                // SL + 2 digit state + 00
	                // + 2 random alphabets

	                newUserId =
	                        generateUserId(
	                                "SL",
	                                stCode,
	                                0,
	                                2
	                        );

	                break;


	            case "DL":

	                // DL + 2 digit state + 00
	                // + 3 random alphabets

	                newUserId =
	                        generateUserId(
	                                "DL",
	                                stCode,
	                                0,
	                                3
	                        );

	                break;


	            case "DI":

	                // DI + 2 digit state + 3 digit district
	                // + 2 random alphabets

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


	        // -------------------------------------------------
	        // 8. Generate Password
	        // -------------------------------------------------

	        String rawPassword =
	                generatePassword();


	        // -------------------------------------------------
	        // 9. Encrypt Password
	        // -------------------------------------------------

	        String encryptedPassword =
	                encoder.encode(rawPassword);


	        // -------------------------------------------------
	        // 10. Update User
	        // -------------------------------------------------

	        user.setUserId(newUserId);
	        user.setEncryptedPass(encryptedPassword);
	        user.setStatus("Active");

	        userRegRepository.save(user);


	        // -------------------------------------------------
	        // 11. Send Credentials by Email
	        // -------------------------------------------------

	        boolean emailSent =
	                piaMailService.sendCredentialMail(
	                        user.getEmail(),
	                        user.getUserName(),
	                        newUserId,
	                        rawPassword
	                );


	        // -------------------------------------------------
	        // 12. Prepare Response
	        // -------------------------------------------------

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

	    // State code must always be 2 digits
	    String stateCode =
	            String.format("%02d", stCode);


	    // District code
	    // For SL and DL, dcode will be 0
	    // and therefore becomes "000".
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

}
