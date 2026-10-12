package gov.dolr.wdcpmksy3.DPR.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;

@Entity
@Table(name = "dpr_other_project_plan", schema = "public")
public class DprOtherProjectPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "other_plan_id")
    private Integer otherPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id")
    private MPpr ppr;

    @Column(name = "status", length = 1)
    private String status;

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

    public DprOtherProjectPlan() {
    }

    public Integer getOtherPlanId() {
        return otherPlanId;
    }

    public void setOtherPlanId(Integer otherPlanId) {
        this.otherPlanId = otherPlanId;
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
}