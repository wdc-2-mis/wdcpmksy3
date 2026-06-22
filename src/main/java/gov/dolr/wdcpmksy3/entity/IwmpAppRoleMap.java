package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*; 
import java.time.LocalDate; 
import java.util.List;

@Entity 
@Table(name = "iwmp_app_role_map") 
public class IwmpAppRoleMap {

	@Id @Column(name = "role_id") 
	private Integer roleId;
	
	@Column(name = "role_name", length = 255) 
	private String roleName;
	
	@Column(name = "home_page", length = 255) 
	private String homePage;
	
	@Column(name = "last_updated_by", length = 255) 
	private String lastUpdatedBy;
	
	@Column(name = "last_updated_date") 
	private LocalDate lastUpdatedDate;
	
	@Column(name = "request_ip", length = 255) 
	private String requestIp;
	
	@OneToMany(mappedBy = "role",cascade = CascadeType.ALL,
             fetch = FetchType.LAZY)
    private List<IwmpUserAppRoleMap> userRoleMappings;

	@OneToMany(mappedBy = "role",cascade = CascadeType.ALL,
             fetch = FetchType.LAZY)
	private List<IwmpRoleMenuMap> roleMenuMappings;
	
	// Getters and Setters 
	public Integer getRoleId() 
	{ 
		return roleId; 
	} 
	public void setRoleId(Integer roleId) 
	{ 
		this.roleId = roleId; 
	} 
	public String getRoleName() 
	{ 
		return roleName; 
	} 
	public void setRoleName(String roleName) 
	{ 
		this.roleName = roleName; 
	} 
	public String getHomePage() 
	{ 
		return homePage; } 
	public void setHomePage(String homePage) 
	{ 
		this.homePage = homePage; 
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
	public List<IwmpUserAppRoleMap> getUserRoleMappings() {
		return userRoleMappings;
	}
	public void setUserRoleMappings(List<IwmpUserAppRoleMap> userRoleMappings) {
		this.userRoleMappings = userRoleMappings;
	}
	public List<IwmpRoleMenuMap> getRoleMenuMappings() {
		return roleMenuMappings;
	}
	public void setRoleMenuMappings(List<IwmpRoleMenuMap> roleMenuMappings) {
		this.roleMenuMappings = roleMenuMappings;
	} 
	
	
	
	
	
	
}