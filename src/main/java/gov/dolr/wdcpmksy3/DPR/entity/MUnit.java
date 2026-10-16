package gov.dolr.wdcpmksy3.DPR.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*; 

@Entity @Table(name = "m_unit") 
public class MUnit { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "unit_code") 
	private Integer unitCode; 
	
	@Column(name = "unit_desc", length = 30) 
	private String unitDesc; 
	
	@Column(name = "last_updated_by", length = 20) 
	private String lastUpdatedBy; 
	
	@Column(name = "last_updated_date") 
	private LocalDate lastUpdatedDate; 
	
	@Column(name = "request_ip", length = 20) 
	private String requestIp; 
	
	@OneToMany(mappedBy = "unit")
	private List<ProductivityActivity> productivityActivities;

	@OneToMany(mappedBy = "unit")
	private List<EpaActivity> epaActivities;

	
	public Integer getUnitCode() 
	{ 
		return unitCode; 
	} 
	public void setUnitCode(Integer unitCode) { 
		this.unitCode = unitCode; 
	} 
	
	public String getUnitDesc(){ 
		return unitDesc; 
	} 
	public void setUnitDesc(String unitDesc){ 
		this.unitDesc = unitDesc; 
	} 
	public String getLastUpdatedBy() { 
		return lastUpdatedBy; 
	} 
	public void setLastUpdatedBy(String lastUpdatedBy){ 
		this.lastUpdatedBy = lastUpdatedBy; 
	} 
	public LocalDate getLastUpdatedDate() { 
		return lastUpdatedDate;
	} 
	public void setLastUpdatedDate(LocalDate lastUpdatedDate){ 
		this.lastUpdatedDate = lastUpdatedDate; 
	} 
	public String getRequestIp(){ 
		return requestIp; 
	} 
	public void setRequestIp(String requestIp) { 
		this.requestIp = requestIp; 
	}
	public List<ProductivityActivity> getProductivityActivities() {
		return productivityActivities;
	}
	public void setProductivityActivities(List<ProductivityActivity> productivityActivities) {
		this.productivityActivities = productivityActivities;
	}
	public List<EpaActivity> getEpaActivities() {
		return epaActivities;
	}
	public void setEpaActivities(List<EpaActivity> epaActivities) {
		this.epaActivities = epaActivities;
	} 
	
	
}