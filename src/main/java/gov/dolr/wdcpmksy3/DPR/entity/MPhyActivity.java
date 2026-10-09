package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal; 
import java.time.LocalDate; 
import jakarta.persistence.*; 

@Entity 
@Table( name = "m_phy_activity", uniqueConstraints = { 
		@UniqueConstraint( name = "uk_m_phy_activity_composite", 
				columnNames = {"head_code", "seq_no"} ) } ) 
public class MPhyActivity { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "activity_code") 
	private Integer activityCode; 
	
	@Column(name = "activity_desc", length = 100) 
	private String activityDesc; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "head_code") 
	private MPhyHeads head; 
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "unit_code") 
	private MUnit unit; 
	
	@Column(name = "seq_no", precision = 5, scale = 2) 
	private BigDecimal seqNo; 
	
	@Column(name = "status", length = 1) 
	private String status; 
	
	@Column(name = "asset") 
	private Integer asset; 
	
	@Column(name = "created_by", length = 20) 
	private String createdBy; 
	
	@Column(name = "createdon") 
	private LocalDate createdOn; 
	
	@Column(name = "last_updated_by", length = 20) 
	private String lastUpdatedBy; 
	
	@Column(name = "last_updated_date") 
	private LocalDate lastUpdatedDate; 
	
	@Column(name = "request_ip", length = 20) 
	private String requestIp; 
	
	public Integer getActivityCode() { 
		return activityCode; 
	} 
	public void setActivityCode(Integer activityCode) { 
		this.activityCode = activityCode; 
	} 
	
	public String getActivityDesc() { 
		return activityDesc; 
	} 
	public void setActivityDesc(String activityDesc) { 
		this.activityDesc = activityDesc; 
	}
	
	public MPhyHeads getHead() { 
		return head; 
	} 
	public void setHead(MPhyHeads head) { 
		this.head = head; 
	} 
	
	public MUnit getUnit() { 
		return unit; 
	} 
	public void setUnit(MUnit unit) { 
		this.unit = unit; 
	}
	
	public BigDecimal getSeqNo() { 
		return seqNo; 
	} 
	public void setSeqNo(BigDecimal seqNo) { 
		this.seqNo = seqNo; 
	} 
	
	public String getStatus() { 
		return status; 
	} 
	public void setStatus(String status) { 
		this.status = status; 
	} 
	
	public Integer getAsset() { 
		return asset; 
	} 
	public void setAsset(Integer asset) { 
		this.asset = asset; 
	} 
	
	public String getCreatedBy() { 
		return createdBy; 
	} 
	public void setCreatedBy(String createdBy) { 
		this.createdBy = createdBy; 
	} 
	
	public LocalDate getCreatedOn() { 
		return createdOn; 
	} 
	public void setCreatedOn(LocalDate createdOn) { 
		this.createdOn = createdOn; 
	} 
	
	public String getLastUpdatedBy() { 
		return lastUpdatedBy; 
	} 
	public void setLastUpdatedBy(String lastUpdatedBy) { 
		this.lastUpdatedBy = lastUpdatedBy; 
	}
	
	public LocalDate getLastUpdatedDate() { 
		return lastUpdatedDate; 
	} 
	public void setLastUpdatedDate(LocalDate lastUpdatedDate) { 
		this.lastUpdatedDate = lastUpdatedDate; 
	} 
	
	public String getRequestIp() { 
		return requestIp; 
	} 
	public void setRequestIp(String requestIp) { 
		this.requestIp = requestIp; 
	} 
}