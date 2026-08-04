package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*; 
import java.time.LocalDate; 

@Entity 
@Table(name = "wdcpmksy_user_app_role_map", 
uniqueConstraints = { @UniqueConstraint(name = "wdcpmksy_user_app_role_map_uniquekey", 
columnNames = "reg_id") }) 
public class WdcpmksyUserAppRoleMap { 
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "user_app_id") 
	private Integer userAppId; 
	
	@Column(name = "reg_id", nullable = false) 
	private Integer regId; 
	
	@Column(name = "last_updated_by", length = 20) 
	private String lastUpdatedBy; 
	
	@Column(name = "last_updated_date") 
	private LocalDate lastUpdatedDate; 
	
	@Column(name = "request_ip", length = 20) 
	private String requestIp; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "role_id", nullable = false) 
	private WdcpmksyAppRoleMap role; 
	
	// Getters and Setters 
	public Integer getUserAppId() 
	{ 
		return userAppId; 
	} 
	public void setUserAppId(Integer userAppId) 
	{ 
		this.userAppId = userAppId; 
	} 
	public Integer getRegId() 
	{ 
		return regId; 
	} 
	public void setRegId(Integer regId) 
	{ 
		this.regId = regId; 
	} 
	public String getLastUpdatedBy() 
	{ 
		return lastUpdatedBy; 
	} 
	public void setLastUpdatedBy(String lastUpdatedBy) 
	{ 
		this.lastUpdatedBy = lastUpdatedBy; 
	} 
	public LocalDate getLastUpdatedDate() 
	{ 
		return lastUpdatedDate; 
	} 
	public void setLastUpdatedDate(LocalDate lastUpdatedDate) 
	{ 
		this.lastUpdatedDate = lastUpdatedDate; 
	} 
	public String getRequestIp() 
	{ 
		return requestIp; 
	} 
	public void setRequestIp(String requestIp) 
	{ 
		this.requestIp = requestIp; 
	} 
	public WdcpmksyAppRoleMap getRole() 
	{ 
		return role; 
	} 
	public void setRole(WdcpmksyAppRoleMap role) 
	{ 
		this.role = role; 
	} 
}
