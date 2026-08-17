package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ppr_livelihood")
public class PprLivelihood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_livelihood_id")
    private Integer pprLivelihoodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    @Column(name = "migrated_people")
    private Integer migratedPeople;

    @Column(name = "migration_reason", length = 500)
    private String migrationReason;

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

    @OneToMany( mappedBy = "pprLivelihood", cascade = CascadeType.ALL,
        orphanRemoval = true, fetch = FetchType.LAZY )
    private List<PprExistingLivelihoodActivity> existingLivelihoodActivities =new ArrayList<>();

    @OneToMany( mappedBy = "pprLivelihood", cascade = CascadeType.ALL,
        orphanRemoval = true, fetch = FetchType.LAZY )
    private List<PprProjectLivelihoodIntervention> projectLivelihoodInterventions = new ArrayList<>();

    // Getters and Setters

    public Integer getPprLivelihoodId() {
        return pprLivelihoodId;
    }

    public void setPprLivelihoodId(Integer pprLivelihoodId) {
        this.pprLivelihoodId = pprLivelihoodId;
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

    public Integer getMigratedPeople() {
        return migratedPeople;
    }

    public void setMigratedPeople(Integer migratedPeople) {
        this.migratedPeople = migratedPeople;
    }

    public String getMigrationReason() {
        return migrationReason;
    }

    public void setMigrationReason(String migrationReason) {
        this.migrationReason = migrationReason;
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

    public List<PprExistingLivelihoodActivity> getExistingLivelihoodActivities() {
        return existingLivelihoodActivities;
    }

    public void setExistingLivelihoodActivities(
            List<PprExistingLivelihoodActivity> existingLivelihoodActivities) {
        this.existingLivelihoodActivities = existingLivelihoodActivities;
    }

    public List<PprProjectLivelihoodIntervention> getProjectLivelihoodInterventions() {
        return projectLivelihoodInterventions;
    }

    public void setProjectLivelihoodInterventions(
            List<PprProjectLivelihoodIntervention> projectLivelihoodInterventions) {
        this.projectLivelihoodInterventions = projectLivelihoodInterventions;
    }
}