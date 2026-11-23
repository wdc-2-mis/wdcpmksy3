package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;

@Entity
@Table(name = "wdcpmksy_project_physical_plan_tranx", schema = "public")
public class WdcpmksyProjectPhysicalPlanTranx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tranx_id")
    private Integer tranxId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private WdcpmksyProjectPhysicalPlan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_to")
    private WdcpmksyUserReg sentTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_from")
    private WdcpmksyUserReg sentFrom;

    @Column(name = "action", length = 1)
    private Character action;

    @Column(name = "sent_on")
    private LocalDateTime sentOn;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    // Getters and Setters

    public Integer getTranxId() {
        return tranxId;
    }

    public void setTranxId(Integer tranxId) {
        this.tranxId = tranxId;
    }

    public WdcpmksyProjectPhysicalPlan getPlan() {
        return plan;
    }

    public void setPlan(WdcpmksyProjectPhysicalPlan plan) {
        this.plan = plan;
    }

    public WdcpmksyUserReg getSentTo() {
        return sentTo;
    }

    public void setSentTo(WdcpmksyUserReg sentTo) {
        this.sentTo = sentTo;
    }

    public WdcpmksyUserReg getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(WdcpmksyUserReg sentFrom) {
        this.sentFrom = sentFrom;
    }

    public Character getAction() {
        return action;
    }

    public void setAction(Character action) {
        this.action = action;
    }

    public LocalDateTime getSentOn() {
        return sentOn;
    }

    public void setSentOn(LocalDateTime sentOn) {
        this.sentOn = sentOn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}