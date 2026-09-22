package gov.dolr.wdcpmksy3.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import gov.dolr.wdcpmksy3.PPR.entity.MFinYear;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_project", schema = "public")
public class MProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_id")
    private Integer projId;

    @Column(name = "act_flag", length = 1)
    private String actFlag;

    @Column(name = "area_proposed", precision = 15, scale = 2, nullable = false)
    private BigDecimal areaProposed;

    @Column(name = "central_share_amt", precision = 15, scale = 2, nullable = false)
    private BigDecimal centralShareAmt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "last_updated_by", length = 25)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "proj_name", length = 100)
    private String projName;

    @Column(name = "project_cd", length = 50, nullable = false, unique = true)
    private String projectCd;

    @Column(name = "project_cost", precision = 15, scale = 2, nullable = false)
    private BigDecimal projectCost;

    @Column(name = "project_end_dt")
    private LocalDate projectEndDt;

    @Column(name = "project_seq_no", nullable = false)
    private Integer projectSeqNo;

    @Column(name = "project_start_dt", nullable = false)
    private LocalDate projectStartDt;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "state_share_amt", precision = 15, scale = 2, nullable = false)
    private BigDecimal stateShareAmt;

    @Column(name = "status", length = 1)
    private String status;

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode", nullable = false)
    private MDistrict district;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fin_yr_cd")
    private MFinYear finYear;


    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<WdcpmksyUserProjectMap> userProjectMappings;
    
    // =========================
    // Getters and Setters
    // =========================

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getActFlag() {
        return actFlag;
    }

    public void setActFlag(String actFlag) {
        this.actFlag = actFlag;
    }

    public BigDecimal getAreaProposed() {
        return areaProposed;
    }

    public void setAreaProposed(BigDecimal areaProposed) {
        this.areaProposed = areaProposed;
    }

    public BigDecimal getCentralShareAmt() {
        return centralShareAmt;
    }

    public void setCentralShareAmt(BigDecimal centralShareAmt) {
        this.centralShareAmt = centralShareAmt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
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

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjectCd() {
        return projectCd;
    }

    public void setProjectCd(String projectCd) {
        this.projectCd = projectCd;
    }

    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }

    public LocalDate getProjectEndDt() {
        return projectEndDt;
    }

    public void setProjectEndDt(LocalDate projectEndDt) {
        this.projectEndDt = projectEndDt;
    }

    public Integer getProjectSeqNo() {
        return projectSeqNo;
    }

    public void setProjectSeqNo(Integer projectSeqNo) {
        this.projectSeqNo = projectSeqNo;
    }

    public LocalDate getProjectStartDt() {
        return projectStartDt;
    }

    public void setProjectStartDt(LocalDate projectStartDt) {
        this.projectStartDt = projectStartDt;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public BigDecimal getStateShareAmt() {
        return stateShareAmt;
    }

    public void setStateShareAmt(BigDecimal stateShareAmt) {
        this.stateShareAmt = stateShareAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MDistrict getDistrict() {
        return district;
    }

    public void setDistrict(MDistrict district) {
        this.district = district;
    }

    public MFinYear getFinYear() {
        return finYear;
    }

    public void setFinYear(MFinYear finYear) {
        this.finYear = finYear;
    }

	public List<WdcpmksyUserProjectMap> getUserProjectMappings() {
		return userProjectMappings;
	}

	public void setUserProjectMappings(List<WdcpmksyUserProjectMap> userProjectMappings) {
		this.userProjectMappings = userProjectMappings;
	}
    
    
}