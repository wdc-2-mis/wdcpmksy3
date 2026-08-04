package gov.dolr.wdcpmksy3.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="wdcpmksy_login_log")
@NamedQuery(name="WdcpmksyLoginLog.findAll", query="SELECT i FROM WdcpmksyLoginLog i")
public class WdcpmksyLoginLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_log_id", nullable = false)
	private Integer login_log_id;

	@Column(name="failed_cause")
	private String failedCause;

	@Column(name="ip_address")
	private String ipAddress;

	@Column(name="last_updated_by")
	private String lastUpdatedBy;

	
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(name="login_dt")
	private Timestamp loginDt;

	@Column(name="login_sts")
	private String loginSts;

	private String loginid;

	private String referrer;

	@Column(name="request_ip")
	private String requestIp;

	@Column(name="session_id")
	private String sessionId;

	@Column(name="user_agent")
	private String userAgent;

	@Column(name="user_sts")
	private String userSts;

	@Column(name="user_type")
	private String userType;

	//bi-directional many-to-one association to IwmpUserReg
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", referencedColumnName = "reg_id")
    private WdcpmksyUserReg userReg;

	public WdcpmksyLoginLog() {
	}

	public Integer getLogin_log_id() {
		return login_log_id;
	}

	public void setLogin_log_id(Integer login_log_id) {
		this.login_log_id = login_log_id;
	}

	public String getFailedCause() {
		return failedCause;
	}

	public void setFailedCause(String failedCause) {
		this.failedCause = failedCause;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public Timestamp getLoginDt() {
		return loginDt;
	}

	public void setLoginDt(Timestamp loginDt) {
		this.loginDt = loginDt;
	}

	public String getLoginSts() {
		return loginSts;
	}

	public void setLoginSts(String loginSts) {
		this.loginSts = loginSts;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserSts() {
		return userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public WdcpmksyUserReg getUserReg() {
		return userReg;
	}

	public void setUserReg(WdcpmksyUserReg userReg) {
		this.userReg = userReg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
