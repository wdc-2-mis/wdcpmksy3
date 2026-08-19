package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
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
@Table(name = "ppr_wcdc_unspent_balance")
public class PprWcdcUnspentBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_unspent_balance_id")
    private Integer pprUnspentBalanceId;

    // Relation with m_ppr
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @Column(name = "tot_cost", precision = 20, scale = 5)
    private BigDecimal totCost;

    @Column(name = "st_released_fund", precision = 20, scale = 5)
    private BigDecimal stReleasedFund;

    @Column(name = "dolr_released_fund", precision = 20, scale = 5)
    private BigDecimal dolrReleasedFund;

    @Column(name = "interest", precision = 20, scale = 5)
    private BigDecimal interest;

    @Column(name = "total", precision = 20, scale = 5)
    private BigDecimal total;

    @Column(name = "unspend_balance", precision = 20, scale = 5)
    private BigDecimal unspendBalance;

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

    public Integer getPprUnspentBalanceId() {
        return pprUnspentBalanceId;
    }

    public void setPprUnspentBalanceId(Integer pprUnspentBalanceId) {
        this.pprUnspentBalanceId = pprUnspentBalanceId;
    }

    public MPpr getPpr() {
        return ppr;
    }

    public void setPpr(MPpr ppr) {
        this.ppr = ppr;
    }

    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }

    public BigDecimal getStReleasedFund() {
        return stReleasedFund;
    }

    public void setStReleasedFund(BigDecimal stReleasedFund) {
        this.stReleasedFund = stReleasedFund;
    }

    public BigDecimal getDolrReleasedFund() {
        return dolrReleasedFund;
    }

    public void setDolrReleasedFund(BigDecimal dolrReleasedFund) {
        this.dolrReleasedFund = dolrReleasedFund;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getUnspendBalance() {
        return unspendBalance;
    }

    public void setUnspendBalance(BigDecimal unspendBalance) {
        this.unspendBalance = unspendBalance;
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