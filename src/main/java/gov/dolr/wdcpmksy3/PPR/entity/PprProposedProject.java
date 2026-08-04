package gov.dolr.wdcpmksy3.PPR.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ppr_proposed_project", schema = "public")

public class PprProposedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_proposed_project_id")
    private Integer pprProposedProjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_type_id", nullable = false)
    private ProjectType projectType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_detail_id", nullable = false)
    private CriteriaDetails criteriaDetails;

    @Column(name = "treated_area", precision = 20, scale = 4)
    private BigDecimal treatedArea;

    @Column(name = "proposed_cost", precision = 20, scale = 5)
    private BigDecimal proposedCost;

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

	public Integer getPprProposedProjectId() {
		return pprProposedProjectId;
	}

	public void setPprProposedProjectId(Integer pprProposedProjectId) {
		this.pprProposedProjectId = pprProposedProjectId;
	}

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public MicroWatershed getMicroWatershed() {
		return microWatershed;
	}

	public void setMicroWatershed(MicroWatershed microWatershed) {
		this.microWatershed = microWatershed;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public CriteriaDetails getCriteriaDetails() {
		return criteriaDetails;
	}

	public void setCriteriaDetails(CriteriaDetails criteriaDetails) {
		this.criteriaDetails = criteriaDetails;
	}

	public BigDecimal getTreatedArea() {
		return treatedArea;
	}

	public void setTreatedArea(BigDecimal treatedArea) {
		this.treatedArea = treatedArea;
	}

	public BigDecimal getProposedCost() {
		return proposedCost;
	}

	public void setProposedCost(BigDecimal proposedCost) {
		this.proposedCost = proposedCost;
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

