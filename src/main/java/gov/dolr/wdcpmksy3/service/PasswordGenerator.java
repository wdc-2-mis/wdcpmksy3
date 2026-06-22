package gov.dolr.wdcpmksy3.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {
	
	BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);

	public String passwdgen(String encrypted_pass) {
		String genetatedpwd= null;
		genetatedpwd= encoder.encode(encrypted_pass);
		return genetatedpwd;
	}
	
/*	@Autowired
	private PasswordEncoder passwordEncoder;

	public void createUser(String username, String rawPassword) {
	    String hashedPassword = passwordEncoder.encode(rawPassword);

	    User user = new User();
	    user.setUsername(username);
	    user.setPassword(hashedPassword);

	    userRepository.save(user);
	} */
	

}
