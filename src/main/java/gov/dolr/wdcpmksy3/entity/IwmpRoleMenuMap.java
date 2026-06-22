package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*; 
import java.time.LocalDate; 

@Entity 
@Table(name = "iwmp_role_menu_map") 
public class IwmpRoleMenuMap 
{ 
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "rolemenu_id") 
	private Integer rolemenuId; 
	
	@Column(name = "last_updated_by", length = 20) 
	private String lastUpdatedBy; 
	
	@Column(name = "last_updated_date") 
	private LocalDate lastUpdatedDate; 
	
	@Column(name = "request_ip", length = 20) 
	private String requestIp; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "role_id", nullable = false) 
	private IwmpAppRoleMap role; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "submenu_id", nullable = false) 
	private IwmpSubmenu submenu; 
	
	
	// Getters and Setters 
	public Integer getRolemenuId() 
	{ 
		return rolemenuId; 
	} 
	public void setRolemenuId(Integer rolemenuId) 
	{ 
		this.rolemenuId = rolemenuId; 
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
	public IwmpAppRoleMap getRole() 
	{ 
		return role; 
	}
	public void setRole(IwmpAppRoleMap role) 
	{ 
		this.role = role; 
	} 
	public IwmpSubmenu getSubmenu() 
	{ 
		return submenu; 
	} 
	public void setSubmenu(IwmpSubmenu submenu) 
	{ 
		this.submenu = submenu; 
	} 
}