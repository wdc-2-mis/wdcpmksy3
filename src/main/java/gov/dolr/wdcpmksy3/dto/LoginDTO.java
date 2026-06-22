package gov.dolr.wdcpmksy3.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
	
	@NotBlank(message = "User Id is required")
	private String userId;
	
	//@NotBlank(message = "Password is required")
    private String encrypted_pass;
    private String loginMethod;
    private String emailid;
    private String otp;
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEncrypted_pass() {
		return encrypted_pass;
	}
	public void setEncrypted_pass(String encrypted_pass) {
		this.encrypted_pass = encrypted_pass;
	}
	public String getLoginMethod() {
		return loginMethod;
	}
	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
    
    
    
    

}
