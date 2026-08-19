package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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
@Table(name = "ppr_pending_uc")
public class PprPendingUc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_pending_uc_id")
    private Integer pprPendingUcId;

    // Relation with m_ppr
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    // Relation with m_fin_year
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_yr_cd", nullable = false)
    private MFinYear finYear;

    @Column(name = "installment_no")
    private BigInteger installmentNo;

    @Column(name = "released_amount", precision = 20, scale = 5)
    private BigDecimal releasedAmount;

    @Column(name = "utilized_amount", precision = 20, scale = 5)
    private BigDecimal utilizedAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "uc_amount", precision = 20, scale = 5)
    private BigDecimal ucAmount;

    @Column(name = "uc_submission_date")
    private LocalDate ucSubmissionDate;

    @Column(name = "uc_submision_amt", precision = 20, scale = 5)
    private BigDecimal ucSubmissionAmt;

    @Column(name = "reason_not_submitted", length = 500)
    private String reasonNotSubmitted;

    @Column(name = "pending_start")
    private LocalDate pendingStart;

    @Column(name = "pending_end")
    private LocalDate pendingEnd;

    @Column(name = "pending_amount", precision = 20, scale = 5)
    private BigDecimal pendingAmount;

    @Column(name = "status", length = 1)
    private Character status;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;


    // Getters and Setters

    public Integer getPprPendingUcId() {
        return pprPendingUcId;
    }

    public void setPprPendingUcId(Integer pprPendingUcId) {
        this.pprPendingUcId = pprPendingUcId;
    }

    public MPpr getPpr() {
        return ppr;
    }

    public void setPpr(MPpr ppr) {
        this.ppr = ppr;
    }

    public MFinYear getFinYear() {
        return finYear;
    }

    public void setFinYear(MFinYear finYear) {
        this.finYear = finYear;
    }

    public BigInteger getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(BigInteger installmentNo) {
        this.installmentNo = installmentNo;
    }

    public BigDecimal getReleasedAmount() {
        return releasedAmount;
    }

    public void setReleasedAmount(BigDecimal releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    public BigDecimal getUtilizedAmount() {
        return utilizedAmount;
    }

    public void setUtilizedAmount(BigDecimal utilizedAmount) {
        this.utilizedAmount = utilizedAmount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getUcAmount() {
        return ucAmount;
    }

    public void setUcAmount(BigDecimal ucAmount) {
        this.ucAmount = ucAmount;
    }

    public LocalDate getUcSubmissionDate() {
        return ucSubmissionDate;
    }

    public void setUcSubmissionDate(LocalDate ucSubmissionDate) {
        this.ucSubmissionDate = ucSubmissionDate;
    }

    public BigDecimal getUcSubmissionAmt() {
        return ucSubmissionAmt;
    }

    public void setUcSubmissionAmt(BigDecimal ucSubmissionAmt) {
        this.ucSubmissionAmt = ucSubmissionAmt;
    }

    public String getReasonNotSubmitted() {
        return reasonNotSubmitted;
    }

    public void setReasonNotSubmitted(String reasonNotSubmitted) {
        this.reasonNotSubmitted = reasonNotSubmitted;
    }

    public LocalDate getPendingStart() {
        return pendingStart;
    }

    public void setPendingStart(LocalDate pendingStart) {
        this.pendingStart = pendingStart;
    }

    public LocalDate getPendingEnd() {
        return pendingEnd;
    }

    public void setPendingEnd(LocalDate pendingEnd) {
        this.pendingEnd = pendingEnd;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}