package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PPRLandPatternAreaDTO {

	private Integer project;
    private Integer watershed;
    private Integer village;

    private BigDecimal village_area;
    private BigDecimal forest_area;
    private BigDecimal argiculture_land;
    private BigDecimal rainfed_area;
    private BigDecimal pastures;
    private BigDecimal cultivable_wasteland_area;
    private BigDecimal non_cultivable_wasteland_area;
    
    private Integer ppr_land_pattern_area_id;
    private String dist_name;
    private String project_name;
    private String mw_name;
    private String village_name;
    private Character status;
    
    
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public Integer getWatershed() {
		return watershed;
	}
	public void setWatershed(Integer watershed) {
		this.watershed = watershed;
	}
	public Integer getVillage() {
		return village;
	}
	public void setVillage(Integer village) {
		this.village = village;
	}
	
	public BigDecimal getVillage_area() {
		return village_area;
	}
	public void setVillage_area(BigDecimal village_area) {
		this.village_area = village_area;
	}
	public BigDecimal getForest_area() {
		return forest_area;
	}
	public void setForest_area(BigDecimal forest_area) {
		this.forest_area = forest_area;
	}
	public BigDecimal getArgiculture_land() {
		return argiculture_land;
	}
	public void setArgiculture_land(BigDecimal argiculture_land) {
		this.argiculture_land = argiculture_land;
	}
	public BigDecimal getRainfed_area() {
		return rainfed_area;
	}
	public void setRainfed_area(BigDecimal rainfed_area) {
		this.rainfed_area = rainfed_area;
	}
	public BigDecimal getPastures() {
		return pastures;
	}
	public void setPastures(BigDecimal pastures) {
		this.pastures = pastures;
	}
	public BigDecimal getCultivable_wasteland_area() {
		return cultivable_wasteland_area;
	}
	public void setCultivable_wasteland_area(BigDecimal cultivable_wasteland_area) {
		this.cultivable_wasteland_area = cultivable_wasteland_area;
	}
	public BigDecimal getNon_cultivable_wasteland_area() {
		return non_cultivable_wasteland_area;
	}
	public void setNon_cultivable_wasteland_area(BigDecimal non_cultivable_wasteland_area) {
		this.non_cultivable_wasteland_area = non_cultivable_wasteland_area;
	}
	public Integer getPpr_land_pattern_area_id() {
		return ppr_land_pattern_area_id;
	}
	public void setPpr_land_pattern_area_id(Integer ppr_land_pattern_area_id) {
		this.ppr_land_pattern_area_id = ppr_land_pattern_area_id;
	}
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getMw_name() {
		return mw_name;
	}
	public void setMw_name(String mw_name) {
		this.mw_name = mw_name;
	}
	public String getVillage_name() {
		return village_name;
	}
	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
    
}