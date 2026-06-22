package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "iwmp_user_reg", schema = "public")
public class IwmpUserReg {

    @Id
    @Column(name = "reg_id", nullable = false)
    private Integer regId;

    @Column(name = "authorization_date")
    private Timestamp authorizationDate;

    @Column(name = "authorizer_id", length = 150)
    private String authorizerId;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "cur_address", length = 500)
    private String curAddress;

    @Column(name = "department", length = 255)
    private String department;

    @Column(name = "designation", length = 255)
    private String designation;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "encrypted_pass", length = 200)
    private String encryptedPass;

    @Column(name = "encrypted_pass_second", length = 200)
    private String encryptedPassSecond;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "mobile_no", length = 40)
    private String mobileNo;

    @Column(name = "paswd_modify")
    private Date paswdModify;

    @Column(name = "per_address", length = 255)
    private String perAddress;

    @Column(name = "phone_no", length = 40)
    private String phoneNo;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "updation_date")
    private Timestamp updationDate;

    @Column(name = "updator_id", length = 150)
    private String updatorId;

    @Column(name = "user_id", length = 255)
    private String userId;

    @Column(name = "user_name", length = 255)
    private String userName;

    @Column(name = "ngo_id", length = 255)
    private String ngoId;

    @Column(name = "register_with", length = 255)
    private String registerWith;

    @Column(name = "user_type", length = 255)
    private String userType;

    @Column(name = "otp", length = 255)
    private String otp;

    @Column(name = "otp_expiry")
    private  LocalDateTime otpExpiry;
    
    @Column(name = "otp_mobile", length = 255)
    private String otpMobile;

    @Column(name = "otp_mobile_expiry")
    private  LocalDateTime otpMobileExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<IwmpUserMap> userMappings;
    
    
    // Getters and Setters
    public Integer getRegId() {
        return regId;
    }
    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public Timestamp getAuthorizationDate() {
        return authorizationDate;
    }
    public void setAuthorizationDate(Timestamp authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getAuthorizerId() {
        return authorizerId;
    }
    public void setAuthorizerId(String authorizerId) {
        this.authorizerId = authorizerId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getCurAddress() {
        return curAddress;
    }
    public void setCurAddress(String curAddress) {
        this.curAddress = curAddress;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPass() {
        return encryptedPass;
    }
    public void setEncryptedPass(String encryptedPass) {
        this.encryptedPass = encryptedPass;
    }

    public String getEncryptedPassSecond() {
        return encryptedPassSecond;
    }
    public void setEncryptedPassSecond(String encryptedPassSecond) {
        this.encryptedPassSecond = encryptedPassSecond;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Date getPaswdModify() {
        return paswdModify;
    }
    public void setPaswdModify(Date paswdModify) {
        this.paswdModify = paswdModify;
    }

    public String getPerAddress() {
        return perAddress;
    }
    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRequestIp() {
        return requestIp;
    }
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdationDate() {
        return updationDate;
    }
    public void setUpdationDate(Timestamp updationDate) {
        this.updationDate = updationDate;
    }

    public String getUpdatorId() {
        return updatorId;
    }
    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNgoId() {
        return ngoId;
    }
    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getRegisterWith() {
        return registerWith;
    }
    public void setRegisterWith(String registerWith) {
        this.registerWith = registerWith;
    }

    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}
	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}
	public List<IwmpUserMap> getUserMappings() {
		return userMappings;
	}
	public void setUserMappings(List<IwmpUserMap> userMappings) {
		this.userMappings = userMappings;
	}
	public String getOtpMobile() {
		return otpMobile;
	}
	public void setOtpMobile(String otpMobile) {
		this.otpMobile = otpMobile;
	}
	public LocalDateTime getOtpMobileExpiry() {
		return otpMobileExpiry;
	}
	public void setOtpMobileExpiry(LocalDateTime otpMobileExpiry) {
		this.otpMobileExpiry = otpMobileExpiry;
	}

	
   
}
