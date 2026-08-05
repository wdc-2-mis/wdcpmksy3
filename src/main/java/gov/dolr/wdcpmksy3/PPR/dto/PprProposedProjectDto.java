package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PprProposedProjectDto {

    private Integer pprId;
    private Integer microWatershed;
    private Integer projectType;

    private BigDecimal treatedProjectArea;
    private BigDecimal proposedCost;

	public Integer getPprId() {
		return pprId;
	}

	public void setPprId(Integer pprId) {
		this.pprId = pprId;
	}

	public Integer getMicroWatershed() {
		return microWatershed;
	}

	public void setMicroWatershed(Integer microWatershed) {
		this.microWatershed = microWatershed;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public BigDecimal getTreatedProjectArea() {
		return treatedProjectArea;
	}

	public void setTreatedProjectArea(BigDecimal treatedProjectArea) {
		this.treatedProjectArea = treatedProjectArea;
	}

	public BigDecimal getProposedCost() {
		return proposedCost;
	}

	public void setProposedCost(BigDecimal proposedCost) {
		this.proposedCost = proposedCost;
	}

    
}

