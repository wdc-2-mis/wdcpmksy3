package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_soil_erosion", schema = "public")
public class PPRSoilErosion {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_soil_erosion_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "erosion_type_id", nullable = false)
    private MErosionType erosionType;

    @Column(name = "affected_area", precision = 20, scale = 4)
    private BigDecimal affectedArea;

    @Column(name = "runoff", precision = 6, scale = 4)
    private BigDecimal runoff;

    @Column(name = "avg_soil_loss", precision = 6, scale = 4)
    private BigDecimal avgSoilLoss;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "month_id")
    private MMonth month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id")
    private MFinYear year;
    
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public MErosionType getErosionType() {
		return erosionType;
	}

	public void setErosionType(MErosionType erosionType) {
		this.erosionType = erosionType;
	}

	public BigDecimal getAffectedArea() {
		return affectedArea;
	}

	public void setAffectedArea(BigDecimal affectedArea) {
		this.affectedArea = affectedArea;
	}

	public BigDecimal getRunoff() {
		return runoff;
	}

	public void setRunoff(BigDecimal runoff) {
		this.runoff = runoff;
	}

	public BigDecimal getAvgSoilLoss() {
		return avgSoilLoss;
	}

	public void setAvgSoilLoss(BigDecimal avgSoilLoss) {
		this.avgSoilLoss = avgSoilLoss;
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

	public MMonth getMonth() {
		return month;
	}

	public void setMonth(MMonth month) {
		this.month = month;
	}

	public MFinYear getYear() {
		return year;
	}

	public void setYear(MFinYear year) {
		this.year = year;
	}
    
    
}
