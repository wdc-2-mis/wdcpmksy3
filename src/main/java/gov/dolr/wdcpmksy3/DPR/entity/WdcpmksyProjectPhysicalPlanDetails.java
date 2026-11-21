package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "wdcpmksy_project_physical_plan_details", schema = "public")
public class WdcpmksyProjectPhysicalPlanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_dtl_id")
    private Integer planDtlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private WdcpmksyProjectPhysicalPlan plan;

    @Column(name = "phy_activity_code")
    private Integer phyActivityCode;

    @Column(name = "level", length = 1)
    private String level;

    @Column(name = "qty_planned", precision = 19, scale = 2)
    private BigDecimal qtyPlanned;

    @Column(name = "work_created", length = 1)
    private String workCreated;

    @Column(name = "createdby", length = 20)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdon")
    private Date createdOn;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "updatedby", length = 20)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedon")
    private Date updatedOn;

    // Getters and Setters

    public Integer getPlanDtlId() {
        return planDtlId;
    }

    public void setPlanDtlId(Integer planDtlId) {
        this.planDtlId = planDtlId;
    }

    public WdcpmksyProjectPhysicalPlan getPlan() {
        return plan;
    }

    public void setPlan(WdcpmksyProjectPhysicalPlan plan) {
        this.plan = plan;
    }

    public Integer getPhyActivityCode() {
        return phyActivityCode;
    }

    public void setPhyActivityCode(Integer phyActivityCode) {
        this.phyActivityCode = phyActivityCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getQtyPlanned() {
        return qtyPlanned;
    }

    public void setQtyPlanned(BigDecimal qtyPlanned) {
        this.qtyPlanned = qtyPlanned;
    }

    public String getWorkCreated() {
        return workCreated;
    }

    public void setWorkCreated(String workCreated) {
        this.workCreated = workCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}