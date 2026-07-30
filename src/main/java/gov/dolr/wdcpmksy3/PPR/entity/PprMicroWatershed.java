package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ppr_micro_watershed", schema = "public")
public class PprMicroWatershed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_mw_id", nullable = false)
    private Integer pprMwId;

    // --- Relationships ---
    @ManyToOne
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    // --- Other Columns ---
    @Column(name = "status", length = 1)
    private String status;

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
    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;

    // --- Getters and Setters ---
    public Integer getPprMwId() {
        return pprMwId;
    }

    public void setPprMwId(Integer pprMwId) {
        this.pprMwId = pprMwId;
    }

    public MPpr getPpr() {
        return ppr;
    }

    public void setPpr(MPpr ppr) {
        this.ppr = ppr;
    }

    public MicroWatershed getMicroWatershed() {
        return microWatershed;
    }

    public void setMicroWatershed(MicroWatershed microWatershed) {
        this.microWatershed = microWatershed;
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

