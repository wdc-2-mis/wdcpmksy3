package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import jakarta.persistence.*;

@Entity
@Table(name = "ppr_transaction")
public class PprTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tranx_id")
    private Integer tranxId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id")
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_to")
    private WdcpmksyUserReg sentTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_from")
    private WdcpmksyUserReg sentFrom;

    @Column(name = "action", length = 1)
    private String action;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "senton")
    private LocalDateTime senton;


    // Getters and Setters

    public Integer getTranxId() {
        return tranxId;
    }

    public void setTranxId(Integer tranxId) {
        this.tranxId = tranxId;
    }

    public MPpr getPpr() {
        return ppr;
    }

    public void setPpr(MPpr ppr) {
        this.ppr = ppr;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getSenton() {
        return senton;
    }

    public void setSenton(LocalDateTime senton) {
        this.senton = senton;
    }
}