package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.*;

@Entity
@Table(name = "ppr_agro_climate")
public class PprAgroClimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_agro_id")
    private Integer pprAgroId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;

    @Column(name = "zone_name", length = 50)
    private String zoneName;

    @Column(name = "area", precision = 20, scale = 4)
    private BigDecimal area;

    @Column(name = "avg_rainfall", precision = 6, scale = 4)
    private BigDecimal avgRainfall;

    @Column(name = "topography", length = 50)
    private String topography;

    @Column(name = "forest_area", precision = 20, scale = 4)
    private BigDecimal forestArea;

    @Column(name = "status")
    private Character status;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @OneToMany(mappedBy = "agroClimate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PprAgroCrop> cropList = new ArrayList<>();

    @OneToMany(mappedBy = "agroClimate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PprAgroSoil> soilList = new ArrayList<>();

	public Integer getPprAgroId() {
		return pprAgroId;
	}

	public void setPprAgroId(Integer pprAgroId) {
		this.pprAgroId = pprAgroId;
	}

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public MVillage getVillage() {
		return village;
	}

	public void setVillage(MVillage village) {
		this.village = village;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getAvgRainfall() {
		return avgRainfall;
	}

	public void setAvgRainfall(BigDecimal avgRainfall) {
		this.avgRainfall = avgRainfall;
	}

	public String getTopography() {
		return topography;
	}

	public void setTopography(String topography) {
		this.topography = topography;
	}

	public BigDecimal getForestArea() {
		return forestArea;
	}

	public void setForestArea(BigDecimal forestArea) {
		this.forestArea = forestArea;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<PprAgroCrop> getCropList() {
		return cropList;
	}

	public void setCropList(List<PprAgroCrop> cropList) {
		this.cropList = cropList;
	}

	public List<PprAgroSoil> getSoilList() {
		return soilList;
	}

	public void setSoilList(List<PprAgroSoil> soilList) {
		this.soilList = soilList;
	}

   
}