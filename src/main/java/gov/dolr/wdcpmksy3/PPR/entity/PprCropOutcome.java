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
@Table(name = "ppr_crop_outcome")
public class PprCropOutcome {

        @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ppr_crop_outcome_id")
	    private Integer pprCropOutcomeId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ppr_id", nullable = false)
	    private MPpr ppr;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "crop_type_id", nullable = false)
	    private CropType cropType;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "season_id", nullable = false)
	    private MSeason season;

	    @Column(name = "current_area", precision = 20, scale = 4)
	    private BigDecimal currentArea;
	    
	    @Column(name = "current_prod", precision = 20, scale = 4)
	    private BigDecimal currentProd;
	    
	    @Column(name = "expected_area", precision = 20, scale = 4)
	    private BigDecimal expectedArea;
	    
	    @Column(name = "expected_prod", precision = 20, scale = 4)
	    private BigDecimal expectedProd;
	    
	    @Column(name = "status", length = 1)
	    private String status;

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

		public Integer getPprCropOutcomeId() {
			return pprCropOutcomeId;
		}

		public void setPprCropOutcomeId(Integer pprCropOutcomeId) {
			this.pprCropOutcomeId = pprCropOutcomeId;
		}

		public MPpr getPpr() {
			return ppr;
		}

		public void setPpr(MPpr ppr) {
			this.ppr = ppr;
		}

		public CropType getCropType() {
			return cropType;
		}

		public void setCropType(CropType cropType) {
			this.cropType = cropType;
		}

		public MSeason getSeason() {
			return season;
		}

		public void setSeason(MSeason season) {
			this.season = season;
		}

		public BigDecimal getCurrentArea() {
			return currentArea;
		}

		public void setCurrentArea(BigDecimal currentArea) {
			this.currentArea = currentArea;
		}

		public BigDecimal getCurrentProd() {
			return currentProd;
		}

		public void setCurrentProd(BigDecimal currentProd) {
			this.currentProd = currentProd;
		}

		public BigDecimal getExpectedArea() {
			return expectedArea;
		}

		public void setExpectedArea(BigDecimal expectedArea) {
			this.expectedArea = expectedArea;
		}

		public BigDecimal getExpectedProd() {
			return expectedProd;
		}

		public void setExpectedProd(BigDecimal expectedProd) {
			this.expectedProd = expectedProd;
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
