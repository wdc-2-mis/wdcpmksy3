package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "iwmp_m_fin_year", schema = "public")
public class IwmpMFinYear {

    @Id
    @Column(name = "fin_yr_cd", nullable = false)
    private Integer finYrCd;

    @Column(name = "act_flag", length = 1)
    private String actFlag;


    @Column(name = "created_by", length = 25)
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_to")
    private Date endTo;

    @Column(name = "fin_yr_desc", length = 9)
    private String finYrDesc;

    @Column(name = "last_updated_by", length = 25)
    private String lastUpdatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_from")
    private Date startFrom;

    @Column(name = "achiev_status", length = 1)
    private String achievStatus;


    @OneToMany(mappedBy = "finYear")
    private List<MPpr> pprs;
    
    // --- Getters and Setters ---
    public Integer getFinYrCd() {
        return finYrCd;
    }

    public void setFinYrCd(Integer finYrCd) {
        this.finYrCd = finYrCd;
    }

    public String getActFlag() {
        return actFlag;
    }

    public void setActFlag(String actFlag) {
        this.actFlag = actFlag;
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

    public Date getEndTo() {
        return endTo;
    }

    public void setEndTo(Date endTo) {
        this.endTo = endTo;
    }

    public String getFinYrDesc() {
        return finYrDesc;
    }

    public void setFinYrDesc(String finYrDesc) {
        this.finYrDesc = finYrDesc;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Date getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(Date startFrom) {
        this.startFrom = startFrom;
    }

    public String getAchievStatus() {
        return achievStatus;
    }

    public void setAchievStatus(String achievStatus) {
        this.achievStatus = achievStatus;
    }

	public List<MPpr> getPprs() {
		return pprs;
	}

	public void setPprs(List<MPpr> pprs) {
		this.pprs = pprs;
	}

    
}

