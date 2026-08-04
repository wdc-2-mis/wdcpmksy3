package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_criteria", schema = "public")
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_seq")
    @SequenceGenerator(
            name = "criteria_seq",
            sequenceName = "m_criteria_criteria_id_seq",
            allocationSize = 1
    )
    @Column(name = "criteria_id")
    private Integer criteriaId;

    @Column(name = "criteria_desc", length = 500)
    private String criteriaDesc;

    @Column(name = "maximum_score")
    private Integer maximumScore;

    @Column(name = "maximum_range")
    private Integer maximumRange;

    @Column(name = "minimum_range")
    private Integer minimumRange;

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

    public Criteria() {
    }

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

    public Integer getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Integer criteriaId) {
        this.criteriaId = criteriaId;
    }

    public String getCriteriaDesc() {
        return criteriaDesc;
    }

    public void setCriteriaDesc(String criteriaDesc) {
        this.criteriaDesc = criteriaDesc;
    }

    public Integer getMaximumScore() {
        return maximumScore;
    }

    public void setMaximumScore(Integer maximumScore) {
        this.maximumScore = maximumScore;
    }

    public Integer getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(Integer maximumRange) {
        this.maximumRange = maximumRange;
    }

    public Integer getMinimumRange() {
        return minimumRange;
    }

    public void setMinimumRange(Integer minimumRange) {
        this.minimumRange = minimumRange;
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

