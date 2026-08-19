package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.entity.MVillage;

@Entity
@Table(name = "ppr_water_outcome")
public class PprWaterOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_water_outcome_id")
    private Integer pprWaterOutcomeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "water_source_id", nullable = false)
    private MWaterSource waterSource;

    @Column(name = "pre_project_level", length = 10)
    private String preProjectLevel;

    @Column(name = "post_project_level", length = 10)
    private String postProjectLevel;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate = LocalDate.now();

    // --- Getters and Setters ---
    public Integer getPprWaterOutcomeId() {
        return pprWaterOutcomeId;
    }

    public void setPprWaterOutcomeId(Integer pprWaterOutcomeId) {
        this.pprWaterOutcomeId = pprWaterOutcomeId;
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

    

    public MicroWatershed getMicroWatershed() {
		return microWatershed;
	}

	public void setMicroWatershed(MicroWatershed microWatershed) {
		this.microWatershed = microWatershed;
	}

	public MWaterSource getWaterSource() {
        return waterSource;
    }

    public void setWaterSource(MWaterSource waterSource) {
        this.waterSource = waterSource;
    }

    public String getPreProjectLevel() {
        return preProjectLevel;
    }

    public void setPreProjectLevel(String preProjectLevel) {
        this.preProjectLevel = preProjectLevel;
    }

    public String getPostProjectLevel() {
        return postProjectLevel;
    }

    public void setPostProjectLevel(String postProjectLevel) {
        this.postProjectLevel = postProjectLevel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
