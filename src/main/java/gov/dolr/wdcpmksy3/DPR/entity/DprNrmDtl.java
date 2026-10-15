package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table( name = "dpr_nrm_dtl",
    uniqueConstraints = { @UniqueConstraint(name = "uk_dpr_nrm_dtl_composite", columnNames = {"dpr_id", "activity_code"})}
)
public class DprNrmDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nrm_id")
    private Integer nrmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dpr_id")
    private DprDetails dpr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_code")
    private MPhyActivity activity;

    @Column(name = "qty_planned", precision = 19, scale = 2)
    private BigDecimal qtyPlanned;

    @Column(name = "status", length = 1)
    private Character status;

    @Column(name = "createdby", length = 20)
    private String createdBy;

    @Column(name = "createdon")
    private LocalDateTime createdOn;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "updatedby", length = 20)
    private String updatedBy;

    @Column(name = "updatedon")
    private LocalDateTime updatedOn;

    public DprNrmDtl() {
    }

    public Integer getNrmId() {
        return nrmId;
    }

    public void setNrmId(Integer nrmId) {
        this.nrmId = nrmId;
    }

    public DprDetails getDpr() {
        return dpr;
    }

    public void setDpr(DprDetails dpr) {
        this.dpr = dpr;
    }

    public MPhyActivity getActivity() {
        return activity;
    }

    public void setActivity(MPhyActivity activity) {
        this.activity = activity;
    }

    public BigDecimal getQtyPlanned() {
        return qtyPlanned;
    }

    public void setQtyPlanned(BigDecimal qtyPlanned) {
        this.qtyPlanned = qtyPlanned;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}