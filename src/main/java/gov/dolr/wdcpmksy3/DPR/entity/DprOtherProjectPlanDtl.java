package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal;
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

@Entity
@Table(name = "dpr_other_project_plan_dtl", schema = "public")
public class DprOtherProjectPlanDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "other_plan_dtl_id")
    private Integer otherPlanDtlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_plan_id")
    private DprOtherProjectPlan otherPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_activity_id")
    private MOtherActivity otherActivity;

    @Column(name = "qty_planned", precision = 19, scale = 2)
    private BigDecimal qtyPlanned;

    @Column(name = "createdby", length = 20)
    private String createdby;

    @Column(name = "createdon")
    private LocalDateTime createdon;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "updatedby", length = 20)
    private String updatedby;

    @Column(name = "updatedon")
    private LocalDateTime updatedon;

    public DprOtherProjectPlanDtl() {
    }

    public Integer getOtherPlanDtlId() {
        return otherPlanDtlId;
    }

    public void setOtherPlanDtlId(Integer otherPlanDtlId) {
        this.otherPlanDtlId = otherPlanDtlId;
    }

    public DprOtherProjectPlan getOtherPlan() {
        return otherPlan;
    }

    public void setOtherPlan(DprOtherProjectPlan otherPlan) {
        this.otherPlan = otherPlan;
    }

    public MOtherActivity getOtherActivity() {
        return otherActivity;
    }

    public void setOtherActivity(MOtherActivity otherActivity) {
        this.otherActivity = otherActivity;
    }

    public BigDecimal getQtyPlanned() {
        return qtyPlanned;
    }

    public void setQtyPlanned(BigDecimal qtyPlanned) {
        this.qtyPlanned = qtyPlanned;
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
}