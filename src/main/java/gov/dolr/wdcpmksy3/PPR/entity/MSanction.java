package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "m_sanction", schema = "public")
public class MSanction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sanction_id", nullable = false)
    private Integer sanctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;
    
    @Column(name="sanction_date")
    private LocalDate sanctionDate;

    @Column(name = "area_proposed", precision = 20, scale = 5)
    private BigDecimal areaProposed;

    @Column(name = "tot_cost", precision = 20, scale = 5)
    private BigDecimal totCost;

    @Column(name = "central_share_amt", precision = 20, scale = 5)
    private BigDecimal centralShareAmt;

    @Column(name = "state_share_amt", precision = 20, scale = 5)
    private BigDecimal stateShareAmt;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;


    // Getters and Setters

    public Integer getSanctionId() {
        return sanctionId;
    }

    public void setSanctionId(Integer sanctionId) {
        this.sanctionId = sanctionId;
    }

    public MPpr getPpr() {
        return ppr;
    }

    public void setPpr(MPpr ppr) {
        this.ppr = ppr;
    }

    public LocalDate getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(LocalDate sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public BigDecimal getAreaProposed() {
        return areaProposed;
    }

    public void setAreaProposed(BigDecimal areaProposed) {
        this.areaProposed = areaProposed;
    }

    public BigDecimal getTotCost() {
        return totCost;
    }

    public void setTotCost(BigDecimal totCost) {
        this.totCost = totCost;
    }

    public BigDecimal getCentralShareAmt() {
        return centralShareAmt;
    }

    public void setCentralShareAmt(BigDecimal centralShareAmt) {
        this.centralShareAmt = centralShareAmt;
    }

    public BigDecimal getStateShareAmt() {
        return stateShareAmt;
    }

    public void setStateShareAmt(BigDecimal stateShareAmt) {
        this.stateShareAmt = stateShareAmt;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}