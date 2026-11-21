package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MFinYear;

@Entity
@Table(name = "wdcpmksy_project_physical_plan", schema = "public")
public class WdcpmksyProjectPhysicalPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_id")
    private MPpr project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fin_yr_cd")
    private MFinYear finYear;

    @Column(name = "status", length = 1)
    private String status;

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

    @OneToMany(mappedBy = "plan",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<WdcpmksyProjectPhysicalPlanDetails> planDetails =
            new ArrayList<>();

    @OneToMany(mappedBy = "plan",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<WdcpmksyProjectPhysicalPlanTranx> transactions =
            new ArrayList<>();

    // Getters and Setters

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public MPpr getProject() {
        return project;
    }

    public void setProject(MPpr project) {
        this.project = project;
    }

    public MFinYear getFinYear() {
        return finYear;
    }

    public void setFinYear(MFinYear finYear) {
        this.finYear = finYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<WdcpmksyProjectPhysicalPlanDetails> getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(
            List<WdcpmksyProjectPhysicalPlanDetails> planDetails) {
        this.planDetails = planDetails;
    }

    public List<WdcpmksyProjectPhysicalPlanTranx> getTransactions() {
        return transactions;
    }

    public void setTransactions(
            List<WdcpmksyProjectPhysicalPlanTranx> transactions) {
        this.transactions = transactions;
    }
}