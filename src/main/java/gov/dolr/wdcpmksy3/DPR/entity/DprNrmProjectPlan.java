package gov.dolr.wdcpmksy3.DPR.entity;

import java.time.LocalDateTime; 
import gov.dolr.wdcpmksy3.PPR.entity.MPpr; 
import jakarta.persistence.*; 

@Entity 
@Table(name = "dpr_nrm_project_plan") 
public class DprNrmProjectPlan { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "plan_id") 
	private Integer planId; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "ppr_id") 
	private MPpr ppr; 
	
	@Column(name = "status", length = 1) 
	private String status; 
	
	@Column(name = "updatedby", length = 20) 
	private String updatedBy; 
	
	@Column(name = "updatedon") 
	private LocalDateTime updatedOn; 
	
	@Column(name = "createdby", length = 20) 
	private String createdBy; 
	
	@Column(name = "createdon") 
	private LocalDateTime createdOn; 
	
	@Column(name = "request_ip", length = 20) 
	private String requestIp; 
	
	public Integer getPlanId() { 
		return planId; 
	} 
	public void setPlanId(Integer planId) { 
		this.planId = planId; 
	} 
	
	public MPpr getPpr() { 
		return ppr; 
	} 
	public void setPpr(MPpr ppr) { 
		this.ppr = ppr; 
	} 
	
	public String getStatus() { 
		return status; 
	} 
	public void setStatus(String status) { 
		this.status = status; 
	} 
	public String getUpdatedBy() { 
		return updatedBy; 
	} public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; } public LocalDateTime getUpdatedOn() { return updatedOn; } public void setUpdatedOn(LocalDateTime updatedOn) { this.updatedOn = updatedOn; } public String getCreatedBy() { return createdBy; } public void setCreatedBy(String createdBy) { this.createdBy = createdBy; } public LocalDateTime getCreatedOn() { return createdOn; } public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; } public String getRequestIp() { return requestIp; } public void setRequestIp(String requestIp) { this.requestIp = requestIp; } }