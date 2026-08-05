package gov.dolr.wdcpmksy3.PPR.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_agro_soil")
public class PprAgroSoil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_soil_id")
    private Integer pprSoilId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_agro_id", nullable = false)
    private PprAgroClimate agroClimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soil_type_id", nullable = false)
    private SoilType soilType;

    @Column(name = "area", precision = 20, scale = 4)
    private BigDecimal area;

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

	public Integer getPprSoilId() {
		return pprSoilId;
	}

	public void setPprSoilId(Integer pprSoilId) {
		this.pprSoilId = pprSoilId;
	}

	public PprAgroClimate getAgroClimate() {
		return agroClimate;
	}

	public void setAgroClimate(PprAgroClimate agroClimate) {
		this.agroClimate = agroClimate;
	}

	public SoilType getSoilType() {
		return soilType;
	}

	public void setSoilType(SoilType soilType) {
		this.soilType = soilType;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
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