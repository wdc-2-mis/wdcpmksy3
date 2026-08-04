package gov.dolr.wdcpmksy3.PPR.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ppr_criteria_details", schema = "public")
public class CriteriaDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_details_seq")
    @SequenceGenerator(
            name = "criteria_details_seq",
            sequenceName = "ppr_criteria_details_criteria_detail_id_seq",
            allocationSize = 1
    )
    @Column(name = "criteria_detail_id")
    private Integer criteriaDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_id", nullable = false)
    private Criteria criteria;

    @Column(name = "scored_marks")
    private Integer scoredMarks;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDate.now();
    }

    public Integer getCriteriaDetailId() {
        return criteriaDetailId;
    }

    public void setCriteriaDetailId(Integer criteriaDetailId) {
        this.criteriaDetailId = criteriaDetailId;
    }

    public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public Integer getScoredMarks() {
        return scoredMarks;
    }

    public void setScoredMarks(Integer scoredMarks) {
        this.scoredMarks = scoredMarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

