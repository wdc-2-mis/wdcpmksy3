package gov.dolr.wdcpmksy3.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MScheme;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "ppr_proposed_area")
public class PprProposedArea {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_proposed_area_id")
    private Long pprProposedAreaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id")
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bcode")
    private MBlock block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id")
    private MScheme scheme;

    @Column(name = "proj_sanctioned_no")
    private Integer projSanctionedNo;

    @Column(name = "proj_sanctioned_area")
    private BigDecimal projSanctionedArea;

    @Column(name = "net_area")
    private BigDecimal netArea;

    @Column(name = "proposed_area")
    private BigDecimal proposedArea;

    @Column(name = "proposed_area_others")
    private BigDecimal proposedAreaOthers;

    @Column(name = "net_bal_area")
    private BigDecimal netBalArea;

    @Column(name = "status")
    private Character status;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

	public Long getPprProposedAreaId() {
		return pprProposedAreaId;
	}

	public void setPprProposedAreaId(Long pprProposedAreaId) {
		this.pprProposedAreaId = pprProposedAreaId;
	}

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public MBlock getBlock() {
		return block;
	}

	public void setBlock(MBlock block) {
		this.block = block;
	}

	public MScheme getScheme() {
		return scheme;
	}

	public void setScheme(MScheme scheme) {
		this.scheme = scheme;
	}

	public Integer getProjSanctionedNo() {
		return projSanctionedNo;
	}

	public void setProjSanctionedNo(Integer projSanctionedNo) {
		this.projSanctionedNo = projSanctionedNo;
	}

	public BigDecimal getProjSanctionedArea() {
		return projSanctionedArea;
	}

	public void setProjSanctionedArea(BigDecimal projSanctionedArea) {
		this.projSanctionedArea = projSanctionedArea;
	}

	public BigDecimal getNetArea() {
		return netArea;
	}

	public void setNetArea(BigDecimal netArea) {
		this.netArea = netArea;
	}

	public BigDecimal getProposedArea() {
		return proposedArea;
	}

	public void setProposedArea(BigDecimal proposedArea) {
		this.proposedArea = proposedArea;
	}

	public BigDecimal getProposedAreaOthers() {
		return proposedAreaOthers;
	}

	public void setProposedAreaOthers(BigDecimal proposedAreaOthers) {
		this.proposedAreaOthers = proposedAreaOthers;
	}

	public BigDecimal getNetBalArea() {
		return netBalArea;
	}

	public void setNetBalArea(BigDecimal netBalArea) {
		this.netBalArea = netBalArea;
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

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
    
    

}
