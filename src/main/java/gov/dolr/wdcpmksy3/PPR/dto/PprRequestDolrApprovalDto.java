package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PprRequestDolrApprovalDto {
	
	private Integer pprId;
	private String projectName;
	private String districtName;
	private String finYear;
	private BigDecimal treatedArea;
	private BigDecimal proposedCost;

	public PprRequestDolrApprovalDto(Integer pprId, String projectName, String districtName,
			String finYear, BigDecimal treatedArea, BigDecimal proposedCost) {
		this.pprId = pprId;
		this.projectName = projectName;
		this.districtName = districtName;
		this.finYear = finYear;
		this.treatedArea = treatedArea;
		this.proposedCost = proposedCost;
	}

	public Integer getPprId() {
		return pprId;
	}

	public void setPprId(Integer pprId) {
		this.pprId = pprId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public BigDecimal getTreatedArea() {
		return treatedArea;
	}

	public void setTreatedArea(BigDecimal treatedArea) {
		this.treatedArea = treatedArea;
	}

	public BigDecimal getProposedCost() {
		return proposedCost;
	}

	public void setProposedCost(BigDecimal proposedCost) {
		this.proposedCost = proposedCost;
	}
	
	
	

}
