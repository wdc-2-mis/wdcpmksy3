package gov.dolr.wdcpmksy3.dto;

import java.util.Date;

public class PhysicalActionPlanTranxDTO {
	
	private Integer planid;
	private String projectdesc;
	private String status;
	private String currentlevel;
	private String remarks;
	private String finyr;
	private String usertype;
	private String sentfrom;
	private String sentto;
	private Date senton;
	private Character action;
	
	
	public Character getAction() {
		return action;
	}
	public void setAction(Character action) {
		this.action = action;
	}
	public String getSentfrom() {
		return sentfrom;
	}
	public void setSentfrom(String sentfrom) {
		this.sentfrom = sentfrom;
	}
	public String getSentto() {
		return sentto;
	}
	public void setSentto(String sentto) {
		this.sentto = sentto;
	}
	public Date getSenton() {
		return senton;
	}
	public void setSenton(Date senton) {
		this.senton = senton;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getFinyr() {
		return finyr;
	}
	public void setFinyr(String finyr) {
		this.finyr = finyr;
	}
	
	public Integer getPlanid() {
		return planid;
	}
	public void setPlanid(Integer planid) {
		this.planid = planid;
	}
	public String getProjectdesc() {
		return projectdesc;
	}
	public void setProjectdesc(String projectdesc) {
		this.projectdesc = projectdesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCurrentlevel() {
		return currentlevel;
	}
	public void setCurrentlevel(String currentlevel) {
		this.currentlevel = currentlevel;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
