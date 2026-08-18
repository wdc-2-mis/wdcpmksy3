package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ppr_drinking_water")
public class PprDrinkingWater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_water_id")
    private Integer pprWaterId;

    // ppr_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    // vcode
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;

    // mw_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    @Column(name = "pre_water_availability_months")
    private Integer preWaterAvailabilityMonths;

    @Column(name = "post_water_availability_months")
    private Integer postWaterAvailabilityMonths;

    // pre_quality_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_quality_id")
    private MWaterQuality preWaterQuality;

    // post_quality_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_quality_id")
    private MWaterQuality postWaterQuality;

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

    // Getters and Setters

    public Integer getPprWaterId() {
        return pprWaterId;
    }

    public void setPprWaterId(Integer pprWaterId) {
        this.pprWaterId = pprWaterId;
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

    public Integer getPreWaterAvailabilityMonths() {
        return preWaterAvailabilityMonths;
    }

    public void setPreWaterAvailabilityMonths(Integer preWaterAvailabilityMonths) {
        this.preWaterAvailabilityMonths = preWaterAvailabilityMonths;
    }

    public Integer getPostWaterAvailabilityMonths() {
        return postWaterAvailabilityMonths;
    }

    public void setPostWaterAvailabilityMonths(Integer postWaterAvailabilityMonths) {
        this.postWaterAvailabilityMonths = postWaterAvailabilityMonths;
    }

    public MWaterQuality getPreWaterQuality() {
        return preWaterQuality;
    }

    public void setPreWaterQuality(MWaterQuality preWaterQuality) {
        this.preWaterQuality = preWaterQuality;
    }

    public MWaterQuality getPostWaterQuality() {
        return postWaterQuality;
    }

    public void setPostWaterQuality(MWaterQuality postWaterQuality) {
        this.postWaterQuality = postWaterQuality;
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