package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal; 
import java.time.LocalDate; 
import jakarta.persistence.*; 

@Entity 
@Table(name = "m_phy_heads") 
public class MPhyHeads { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "head_code") 
	private Integer headCode; 
	
	@Column(name = "head_desc", length = 100) 
	private String headDesc; @Column(name = "seq_no", precision = 5, scale = 2, unique = true) private BigDecimal seqNo; @Column(name = "status", length = 1) private String status; @Column(name = "last_updated_by", length = 20) private String lastUpdatedBy; @Column(name = "last_updated_date") private LocalDate lastUpdatedDate; @Column(name = "request_ip", length = 20) private String requestIp; public Integer getHeadCode() { return headCode; } public void setHeadCode(Integer headCode) { this.headCode = headCode; } public String getHeadDesc() { return headDesc; } public void setHeadDesc(String headDesc) { this.headDesc = headDesc; } public BigDecimal getSeqNo() { return seqNo; } public void setSeqNo(BigDecimal seqNo) { this.seqNo = seqNo; } public String getStatus() { return status; } public void setStatus(String status) { this.status = status; } public String getLastUpdatedBy() { return lastUpdatedBy; } public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; } public LocalDate getLastUpdatedDate() { return lastUpdatedDate; } public void setLastUpdatedDate(LocalDate lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; } public String getRequestIp() { return requestIp; } public void setRequestIp(String requestIp) { this.requestIp = requestIp; } }