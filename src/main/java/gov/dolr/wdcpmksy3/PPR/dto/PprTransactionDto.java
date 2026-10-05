package gov.dolr.wdcpmksy3.PPR.dto;

import java.time.LocalDateTime;

public class PprTransactionDto {

    private Integer tranxId;
    private Integer pprId;
    private String sentFrom;
    private String sentTo;
    private Character action;
    private String remarks;
    private LocalDateTime senton;

    public Integer getTranxId() {
        return tranxId;
    }

    public void setTranxId(Integer tranxId) {
        this.tranxId = tranxId;
    }

    public Integer getPprId() {
        return pprId;
    }

    public void setPprId(Integer pprId) {
        this.pprId = pprId;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public Character getAction() {
        return action;
    }

    public void setAction(Character action) {
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