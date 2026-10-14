package gov.dolr.wdcpmksy3.DPR.entity;

import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
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
@Table(name = "dpr_details", schema = "public")
public class DprDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dpr_id")
    private Integer dprId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id")
    private MPpr ppr;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "dprupload_url", length = 500)
    private String dpruploadUrl;

    @Column(name = "updatedby", length = 20)
    private String updatedby;

    @Column(name = "updatedon")
    private LocalDateTime updatedon;

    @Column(name = "createdby", length = 20)
    private String createdby;

    @Column(name = "createdon")
    private LocalDateTime createdon;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_type_id")
    private MDprHead headType;

    public Integer getDprId() {
        return dprId;
    }

    public void setDprId(Integer dprId) {
        this.dprId = dprId;
    }

    public MPpr getPpr() {
        return ppr;
    }

    public void setPpr(MPpr ppr) {
        this.ppr = ppr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDpruploadUrl() {
        return dpruploadUrl;
    }

    public void setDpruploadUrl(String dpruploadUrl) {
        this.dpruploadUrl = dpruploadUrl;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public LocalDateTime getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(LocalDateTime updatedon) {
        this.updatedon = updatedon;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public MDprHead getHeadType() {
        return headType;
    }

    public void setHeadType(MDprHead headType) {
        this.headType = headType;
    }
}
