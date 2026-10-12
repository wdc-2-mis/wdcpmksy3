package gov.dolr.wdcpmksy3.DPR.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "m_other_activity", schema = "public")
public class MOtherActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "other_activity_id")
    private Integer otherActivityId;

    @Column(name = "other_activity_desc", length = 50)
    private String otherActivityDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_heads_id")
    private MOtherHeads otherHeads;
    
    @Column(name = "seq_no", precision = 5, scale = 2, unique = true)
    private BigDecimal seqNo;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_code")
    private MUnit unit;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    public MOtherActivity() {
    }

    public Integer getOtherActivityId() {
        return otherActivityId;
    }

    public void setOtherActivityId(Integer otherActivityId) {
        this.otherActivityId = otherActivityId;
    }

    public String getOtherActivityDesc() {
        return otherActivityDesc;
    }

    public void setOtherActivityDesc(String otherActivityDesc) {
        this.otherActivityDesc = otherActivityDesc;
    }

    public MOtherHeads getOtherHeads() {
        return otherHeads;
    }

    public void setOtherHeads(MOtherHeads otherHeads) {
        this.otherHeads = otherHeads;
    }

    public BigDecimal getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public MUnit getUnit() {
        return unit;
    }

    public void setUnit(MUnit unit) {
        this.unit = unit;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }
}