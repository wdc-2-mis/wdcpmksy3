package gov.dolr.wdcpmksy3.PPR.entity;

import java.util.Date;

import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.*;

@Entity
@Table(name = "ppr_village")
public class PprVillage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_vlg_id")
    private Integer pprVillageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_project_glance_id", nullable = false)
    private PprProjectGlance projectGlance;

    @Column(name = "status", length = 1)
    private Character status;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date")
    private Date updatedDate;

    // Getters and Setters

    public Integer getPprVillageId() {
        return pprVillageId;
    }

    public void setPprVillageId(Integer pprVillageId) {
        this.pprVillageId = pprVillageId;
    }

    public MVillage getVillage() {
        return village;
    }

    public void setVillage(MVillage village) {
        this.village = village;
    }

    public PprProjectGlance getProjectGlance() {
        return projectGlance;
    }

    public void setProjectGlance(PprProjectGlance projectGlance) {
        this.projectGlance = projectGlance;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}