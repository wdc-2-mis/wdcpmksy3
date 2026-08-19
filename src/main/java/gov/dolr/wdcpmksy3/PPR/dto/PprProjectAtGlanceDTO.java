package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;
import java.util.List;

public class PprProjectAtGlanceDTO {
	
	private Integer pprProjectGlanceId;
    private Integer pprId;
    private Integer mwId;
    private Integer projectType;
    private List<Integer> villages;
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
	
	public Integer getMwId() {
		return mwId;
	}
	public void setMwId(Integer mwId) {
		this.mwId = mwId;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public List<Integer> getVillages() {
		return villages;
	}
	public void setVillages(List<Integer> villages) {
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
