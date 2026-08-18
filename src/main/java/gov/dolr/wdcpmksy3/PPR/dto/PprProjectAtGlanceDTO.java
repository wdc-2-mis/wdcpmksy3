package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PprProjectAtGlanceDTO {
	
	private Integer pprProjectGlanceId;
    private Integer pprId;
    private Integer microWatershedCode;
    private Integer projectType;
    private String villages;
    private String selectionReason;
    private BigDecimal projectArea;

    private BigDecimal proposedArea;
    private BigDecimal projectCost;
    private String piaName;
    private String address;
    private String comments;
    
	public Integer getPprProjectGlanceId() {
		return pprProjectGlanceId;
	}
	public void setPprProjectGlanceId(Integer pprProjectGlanceId) {
		this.pprProjectGlanceId = pprProjectGlanceId;
	}
	public Integer getPprId() {
		return pprId;
	}
	public void setPprId(Integer pprId) {
		this.pprId = pprId;
	}
	public Integer getMicroWatershedCode() {
		return microWatershedCode;
	}
	public void setMicroWatershedCode(Integer microWatershedCode) {
		this.microWatershedCode = microWatershedCode;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getVillages() {
		return villages;
	}
	public void setVillages(String villages) {
		this.villages = villages;
	}
	public String getSelectionReason() {
		return selectionReason;
	}
	public void setSelectionReason(String selectionReason) {
		this.selectionReason = selectionReason;
	}
	public BigDecimal getProjectArea() {
		return projectArea;
	}
	public void setProjectArea(BigDecimal projectArea) {
		this.projectArea = projectArea;
	}
	public BigDecimal getProposedArea() {
		return proposedArea;
	}
	public void setProposedArea(BigDecimal proposedArea) {
		this.proposedArea = proposedArea;
	}
	public BigDecimal getProjectCost() {
		return projectCost;
	}
	public void setProjectCost(BigDecimal projectCost) {
		this.projectCost = projectCost;
	}
	public String getPiaName() {
		return piaName;
	}
	public void setPiaName(String piaName) {
		this.piaName = piaName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

}
