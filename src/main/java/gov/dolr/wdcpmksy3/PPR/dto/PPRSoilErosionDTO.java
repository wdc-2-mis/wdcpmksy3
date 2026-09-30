package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PPRSoilErosionDTO {
	
	private Integer dcode;
	private String distName;
	private Integer pprId;
	private String projectName;
	private String erosion;
	private Integer erosionTypeId;
	private String erosionType;
    private BigDecimal affectedArea;
    private BigDecimal runoff;
    private BigDecimal avgSoilLoss;
    private Integer monthId;
    private Integer yearId;
    private String month;
    private String year;

	public Integer getErosionTypeId() {
		return erosionTypeId;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
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
	public String getErosion() {
		return erosion;
	}
	public void setErosion(String erosion) {
		this.erosion = erosion;
	}
	public void setErosionTypeId(Integer erosionTypeId) {
		this.erosionTypeId = erosionTypeId;
	}
	public String getErosionType() {
		return erosionType;
	}
	public void setErosionType(String erosionType) {
		this.erosionType = erosionType;
	}
	public BigDecimal getAffectedArea() {
		return affectedArea;
	}
	public void setAffectedArea(BigDecimal affectedArea) {
		this.affectedArea = affectedArea;
	}
	public BigDecimal getRunoff() {
		return runoff;
	}
	public void setRunoff(BigDecimal runoff) {
		this.runoff = runoff;
	}
	public BigDecimal getAvgSoilLoss() {
		return avgSoilLoss;
	}
	public void setAvgSoilLoss(BigDecimal avgSoilLoss) {
		this.avgSoilLoss = avgSoilLoss;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	public Integer getYearId() {
		return yearId;
	}
	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
    
}
