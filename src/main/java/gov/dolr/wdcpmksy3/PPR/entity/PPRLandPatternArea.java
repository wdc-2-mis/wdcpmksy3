package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.*;

@Entity
@Table(name = "ppr_land_pattern_area", schema = "public")
public class PPRLandPatternArea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ppr_land_pattern_area_id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr pprId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;
	
	@Column(name = "village_area", precision = 20, scale = 4)
    private BigDecimal villageArea;

    @Column(name = "forest_area", precision = 20, scale = 4)
    private BigDecimal forestArea;
	
    @Column(name = "argiculture_land", precision = 20, scale = 4)
    private BigDecimal agricultureLand;

    @Column(name = "rainfed_area", precision = 20, scale = 4)
    private BigDecimal rainfedArea;

    @Column(name = "pastures", precision = 20, scale = 4)
    private BigDecimal pastures;

    @Column(name = "cultivable_wasteland_area", precision = 20, scale = 4)
    private BigDecimal cultivableWastelandArea;

    @Column(name = "non_cultivable_wasteland_area", precision = 20, scale = 4)
    private BigDecimal nonCultivableWastelandArea;

    @Column(name = "status", length = 1)
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
    
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MPpr getPprId() {
		return pprId;
	}

	public void setPprId(MPpr pprId) {
		this.pprId = pprId;
	}

	public MicroWatershed getMicroWatershed() {
		return microWatershed;
	}

	public void setMicroWatershed(MicroWatershed microWatershed) {
		this.microWatershed = microWatershed;
	}

	public MVillage getVillage() {
		return village;
	}

	public void setVillage(MVillage village) {
		this.village = village;
	}

	public BigDecimal getVillageArea() {
		return villageArea;
	}

	public void setVillageArea(BigDecimal villageArea) {
		this.villageArea = villageArea;
	}

	public BigDecimal getForestArea() {
		return forestArea;
	}

	public void setForestArea(BigDecimal forestArea) {
		this.forestArea = forestArea;
	}

	public BigDecimal getAgricultureLand() {
		return agricultureLand;
	}

	public void setAgricultureLand(BigDecimal agricultureLand) {
		this.agricultureLand = agricultureLand;
	}

	public BigDecimal getRainfedArea() {
		return rainfedArea;
	}

	public void setRainfedArea(BigDecimal rainfedArea) {
		this.rainfedArea = rainfedArea;
	}

	public BigDecimal getPastures() {
		return pastures;
	}

	public void setPastures(BigDecimal pastures) {
		this.pastures = pastures;
	}

	public BigDecimal getCultivableWastelandArea() {
		return cultivableWastelandArea;
	}

	public void setCultivableWastelandArea(BigDecimal cultivableWastelandArea) {
		this.cultivableWastelandArea = cultivableWastelandArea;
	}

	public BigDecimal getNonCultivableWastelandArea() {
		return nonCultivableWastelandArea;
	}

	public void setNonCultivableWastelandArea(BigDecimal nonCultivableWastelandArea) {
		this.nonCultivableWastelandArea = nonCultivableWastelandArea;
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

	
}
