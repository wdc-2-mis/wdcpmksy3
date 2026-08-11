package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PprAgroClimateDTO {
	
	private Integer agroid;

    private Integer district;

    private Integer project;

    private Integer village;
    
    private String distname;
    private String projname;
    private String villname;

    private String zone;
    
    private String graphy;
    
    private BigDecimal rainfall;
    
    private BigDecimal area;
    
    private BigDecimal farea;
    
    private Integer soilType;
    
    private BigDecimal soilarea;
    
    private Integer croptype;
    
    private BigDecimal croparea;

	public Integer getAgroid() {
		return agroid;
	}

	public void setAgroid(Integer agroid) {
		this.agroid = agroid;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public Integer getVillage() {
		return village;
	}

	public void setVillage(Integer village) {
		this.village = village;
	}

	public String getDistname() {
		return distname;
	}

	public void setDistname(String distname) {
		this.distname = distname;
	}

	public String getProjname() {
		return projname;
	}

	public void setProjname(String projname) {
		this.projname = projname;
	}

	public String getVillname() {
		return villname;
	}

	public void setVillname(String villname) {
		this.villname = villname;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getGraphy() {
		return graphy;
	}

	public void setGraphy(String graphy) {
		this.graphy = graphy;
	}

	public BigDecimal getRainfall() {
		return rainfall;
	}

	public void setRainfall(BigDecimal rainfall) {
		this.rainfall = rainfall;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getFarea() {
		return farea;
	}

	public void setFarea(BigDecimal farea) {
		this.farea = farea;
	}

	public Integer getSoilType() {
		return soilType;
	}

	public void setSoilType(Integer soilType) {
		this.soilType = soilType;
	}

	public BigDecimal getSoilarea() {
		return soilarea;
	}

	public void setSoilarea(BigDecimal soilarea) {
		this.soilarea = soilarea;
	}

	public Integer getCroptype() {
		return croptype;
	}

	public void setCroptype(Integer croptype) {
		this.croptype = croptype;
	}

	public BigDecimal getCroparea() {
		return croparea;
	}

	public void setCroparea(BigDecimal croparea) {
		this.croparea = croparea;
	}
    
    
    

}
