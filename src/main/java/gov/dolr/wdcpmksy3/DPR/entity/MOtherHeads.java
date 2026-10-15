package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_other_heads", schema = "public")
public class MOtherHeads {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "other_heads_id")
	private Integer otherHeadsId;

    @Column(name = "other_head_desc", length = 100)
    private String otherHeadDesc;

    @Column(name = "seq_no", precision = 5, scale = 2, unique = true)
    private BigDecimal seqNo;

    @Column(name = "status", length = 1)
    private Character status;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    public MOtherHeads() {
    }

    public Integer getOtherHeadsId() {
        return otherHeadsId;
    }

    public void setOtherHeadsId(Integer otherHeadsId) {
        this.otherHeadsId = otherHeadsId;
    }

    public String getOtherHeadDesc() {
        return otherHeadDesc;
    }

    public void setOtherHeadDesc(String otherHeadDesc) {
        this.otherHeadDesc = otherHeadDesc;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
