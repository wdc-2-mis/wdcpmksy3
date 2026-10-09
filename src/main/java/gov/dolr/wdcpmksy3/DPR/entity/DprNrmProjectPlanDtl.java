package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal; 
import java.time.LocalDateTime; 
import jakarta.persistence.*; 

@Entity 
@Table(name = "dpr_nrm_project_plan_dtl") 
public class DprNrmProjectPlanDtl { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "plan_dtl_id") 
	private Integer planDtlId; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "plan_id") 
	private DprNrmProjectPlan plan; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "activity_code") 
	private MPhyActivity activity; 
	
	@Column(name = "qty_planned", precision = 19, scale = 2) private BigDecimal qtyPlanned; @Column(name = "createdby", length = 20) private String createdBy; @Column(name = "createdon") private LocalDateTime createdOn; @Column(name = "request_ip", length = 20) private String requestIp; @Column(name = "updatedby", length = 20) private String updatedBy; @Column(name = "updatedon") private LocalDateTime updatedOn; public Integer getPlanDtlId() { return planDtlId; } public void setPlanDtlId(Integer planDtlId) { this.planDtlId = planDtlId; } public DprNrmProjectPlan getPlan() { return plan; } public void setPlan(DprNrmProjectPlan plan) { this.plan = plan; } public MPhyActivity getActivity() { return activity; } public void setActivity(MPhyActivity activity) { this.activity = activity; } public BigDecimal getQtyPlanned() { return qtyPlanned; } public void setQtyPlanned(BigDecimal qtyPlanned) { this.qtyPlanned = qtyPlanned; } public String getCreatedBy() { return createdBy; } public void setCreatedBy(String createdBy) { this.createdBy = createdBy; } public LocalDateTime getCreatedOn() { return createdOn; } public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; } public String getRequestIp() { return requestIp; } public void setRequestIp(String requestIp) { this.requestIp = requestIp; } public String getUpdatedBy() { return updatedBy; } public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; } public LocalDateTime getUpdatedOn() { return updatedOn; } public void setUpdatedOn(LocalDateTime updatedOn) { this.updatedOn = updatedOn; } }