package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="ppr_project_glance")
public class PprProjectGlance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ppr_project_glance_id")
    private Integer pprProjectGlanceId;

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
    @JoinColumn(name="pia_id", nullable = false)
    private MPiaDetails pia;

    @Column(name="selection_reason", length = 200)
    private String selectionReason;

    @Column(name="project_area", precision = 20, scale = 4)
    private BigDecimal projectArea;

    @Column(name="proposed_area", precision = 20, scale = 4)
    private BigDecimal proposedArea;

    @Column(name="project_cost", precision = 20, scale = 4)
    private BigDecimal projectCost;

    @Column(name="comments", length = 200)
    private String comments;

    @Column(name="status", length = 1)
    private Character status;

	public Integer getPprProjectGlanceId() {
		return pprProjectGlanceId;
	}

	public void setPprProjectGlanceId(Integer pprProjectGlanceId) {
		this.pprProjectGlanceId = pprProjectGlanceId;
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

	public MPiaDetails getPia() {
		return pia;
	}

	public void setPia(MPiaDetails pia) {
		this.pia = pia;
	}

	public String getSelectionReason() {
		return selectionReason;
	}

	public void setSelectionReason(String selectionReason) {
		this.selectionReason = selectionReason;
	}

	public BigDecimal getProjectArea() {
		return projectArea;
	}

	public void setProjectArea(BigDecimal projectArea) {
		this.projectArea = projectArea;
	}

	public BigDecimal getProposedArea() {
		return proposedArea;
	}

	public void setProposedArea(BigDecimal proposedArea) {
		this.proposedArea = proposedArea;
	}

	public BigDecimal getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(BigDecimal projectCost) {
		this.projectCost = projectCost;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}


}