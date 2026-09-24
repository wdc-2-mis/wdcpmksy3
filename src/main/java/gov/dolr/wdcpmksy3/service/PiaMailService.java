package gov.dolr.wdcpmksy3.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gov.dolr.wdcpmksy3.dto.SendCredentialMailRequest;

@Service
public class PiaMailService {

    private final RestTemplate restTemplate;

    private static final String MAIL_URL =
            "http://164.100.114.72/otp/sendCredentialMail";

    public PiaMailService() {
        this.restTemplate = new RestTemplate();
    }

    public boolean sendCredentialMail(
            String email,
            String userName,
            String userId,
            String password) {

        try {

            String message =
                    "Dear " + userName + ",\n\n"
                    + "Your User Account for WDC-PMKSY 3.0 "
                    + "has been activated successfully.\n\n"

                    + "User Name : " + userName + "\n"
                    + "User ID   : " + userId + "\n"
                    + "Password  : " + password + "\n\n"

                    + "Important:\n"
                    + "Your account will be available for login only "
                    + "after a role has been assigned to this User ID.\n\n"

                    + "Please keep your User ID and Password confidential.\n\n"

                    + "Regards,\n"
                    + "WDC-PMKSY 3.0\n"
                    + "Department of Land Resources\n"
                    + "Ministry of Rural Development\n"
                    + "Government of India";

            SendCredentialMailRequest request =
                    new SendCredentialMailRequest();

            request.setEmail(email);
            request.setFromEmail("support-wdcpmksy@nic.in");
            request.setFromName("WDC-PMKSY 3.0");
            request.setSubject(
                    "WDC-PMKSY 3.0 – PIA Account Activated"
            );
            request.setMessage(message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SendCredentialMailRequest> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            MAIL_URL,
                            entity,
                            Map.class
                    );

            if (response.getBody() != null) {

                Object status =
                        response.getBody().get("status");

                return "SENT".equalsIgnoreCase(
                        String.valueOf(status)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    
    
    public boolean sendRoleAssignmentMail(
            String email,
            String userName,
            String userId,
            String roleName,
            String projectName) {

        try {

            String message =
                    "Dear " + userName + ",\n\n"

                    + "A role has been assigned to your "
                    + "WDC-PMKSY 3.0 user account successfully.\n\n"

                    + "User Name : " + userName + "\n"
                    + "User ID   : " + userId + "\n"
                    + "Role      : " + roleName + "\n";

            if (projectName != null
                    && !projectName.trim().isEmpty()) {

                message +=
                        "Project   : " + projectName + "\n";
            }

            message +=
                    "\n"
                    + "You are requested to use the assigned role "
                    + "for the activities available to your account.\n\n"

                    + "Regards,\n"
                    + "WDC-PMKSY 3.0\n"
                    + "Department of Land Resources\n"
                    + "Ministry of Rural Development\n"
                    + "Government of India";


            SendCredentialMailRequest request =
                    new SendCredentialMailRequest();

            request.setEmail(email);

            request.setFromEmail(
                    "support-wdcpmksy@nic.in"
            );

            request.setFromName(
                    "WDC-PMKSY 3.0"
            );

            request.setSubject(
                    "WDC-PMKSY 3.0 – Role Assigned"
            );

            request.setMessage(message);


            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );


            HttpEntity<SendCredentialMailRequest> entity =
                    new HttpEntity<>(request, headers);


            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            MAIL_URL,
                            entity,
                            Map.class
                    );


            if (response.getBody() != null) {

                Object status =
                        response.getBody().get("status");

                return "SENT".equalsIgnoreCase(
                        String.valueOf(status)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    
    public boolean sendInactiveMail(
            String email,
            String userName,
            String userId) {

        try {

            String message =
                    "Dear " + userName + ",\n\n"
                    + "This is to inform you that your "
                    + "WDC-PMKSY 3.0 user account has been "
                    + "temporarily inactivated by the administration.\n\n"
                    + "User ID : " + userId + "\n\n"
                    + "You will not be able to log in while your "
                    + "account remains inactive.\n\n"
                    + "If you require further information, "
                    + "please contact your concerned administrator.\n\n"
                    + "Regards,\n"
                    + "WDC-PMKSY 3.0\n"
                    + "Department of Land Resources\n"
                    + "Ministry of Rural Development\n"
                    + "Government of India";

            SendCredentialMailRequest request =
                    new SendCredentialMailRequest();

            request.setEmail(email);
            request.setFromEmail("support-wdcpmksy@nic.in");
            request.setFromName("WDC-PMKSY 3.0");

            request.setSubject(
                    "WDC-PMKSY 3.0 – User Account Inactivated"
            );

            request.setMessage(message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SendCredentialMailRequest> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            MAIL_URL,
                            entity,
                            Map.class
                    );

            if (response.getBody() != null) {

                Object status =
                        response.getBody().get("status");

                return "SENT".equalsIgnoreCase(
                        String.valueOf(status)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    
    public boolean sendReactivatedMail(
            String email,
            String userName,
            String userId) {

        try {

            String message =
                    "Dear " + userName + ",\n\n"
                    + "This is to inform you that your "
                    + "WDC-PMKSY 3.0 user account has been "
                    + "reactivated by the administration.\n\n"
                    + "User ID : " + userId + "\n\n"
                    + "You may now log in using your existing "
                    + "User ID and Password.\n\n"
                    + "No change has been made to your existing "
                    + "login credentials.\n\n"
                    + "Regards,\n"
                    + "WDC-PMKSY 3.0\n"
                    + "Department of Land Resources\n"
                    + "Ministry of Rural Development\n"
                    + "Government of India";

            SendCredentialMailRequest request =
                    new SendCredentialMailRequest();

            request.setEmail(email);
            request.setFromEmail("support-wdcpmksy@nic.in");
            request.setFromName("WDC-PMKSY 3.0");

            request.setSubject(
                    "WDC-PMKSY 3.0 – User Account Reactivated"
            );

            request.setMessage(message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SendCredentialMailRequest> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            MAIL_URL,
                            entity,
                            Map.class
                    );

            if (response.getBody() != null) {

                Object status =
                        response.getBody().get("status");

                return "SENT".equalsIgnoreCase(
                        String.valueOf(status)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}