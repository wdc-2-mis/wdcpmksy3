package gov.dolr.wdcpmksy3.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.IwmpLoginLog;
import gov.dolr.wdcpmksy3.entity.IwmpUserReg;
import gov.dolr.wdcpmksy3.repository.IwmpLoginLogRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginServices {
	
	@Autowired
    private UserRepository userRepository;

	/*   @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean userAuthenticate(String userId, String password) {

        Optional<IwmpUserReg> optional =
                userRepository.findByUserId(userId);

        if(optional.isEmpty()) {
            return false;
        }
        IwmpUserReg user = optional.get();

        return passwordEncoder.matches(password, user.getEncryptedPass());
    }
	*/
	
	@Autowired
    private IwmpLoginLogRepository loginLogRepository;

    public boolean insertloginlog(String userid,
                                  String success,
                                  HttpServletRequest request) {
    	HttpSession session;
    	session = request.getSession(false);

        try {

            IwmpLoginLog log = new IwmpLoginLog();

            log.setLoginid(userid);
            log.setLoginSts(success);

            // Client IP
            String ipAddress = request.getRemoteAddr();
            log.setIpAddress(ipAddress);

            // Browser/User Agent
            String referrer = request.getHeader("referer"); 
            String userAgent = request.getHeader("User-Agent");
            log.setUserAgent(userAgent);
            log.setReferrer(referrer);
            // Login Time
            Timestamp loginDt = new Timestamp(System.currentTimeMillis());
            log.setLoginDt(loginDt);
            String sessionId =session.getId(); 
            log.setSessionId(sessionId);
            loginLogRepository.save(log);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
	
	
	public String getEmailandGenerateotp(String value) {
		
		
		return userRepository.getEmail( value);
		
	}

}
