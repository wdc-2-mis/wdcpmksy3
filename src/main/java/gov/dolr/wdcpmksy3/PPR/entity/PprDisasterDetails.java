package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.*;

@Entity
@Table(name = "ppr_disaster_details")
public class PprDisasterDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_disaster_id")
    private Integer pprDisasterId;

    @ManyToOne
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;
    
    @ManyToOne
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage vcode;

    @ManyToOne
    @JoinColumn(name = "disaster_type_id", nullable = false)
    private MDisasterType dtype;
    
    @ManyToOne
    @JoinColumn(name = "month_id", nullable = false)
    private MMonth month;
    
    @ManyToOne
    @JoinColumn(name = "year_id", nullable = false)
    private MFinYear year;
    
    @Column(name = "periodicity", length = 1)
    private String periodicity;

    @Column(name = "affected")
    private Boolean affected;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate = LocalDate.now();

    
	public Integer getPprDisasterId() {
		return pprDisasterId;
	}

	public void setPprDisasterId(Integer pprDisasterId) {
		this.pprDisasterId = pprDisasterId;
	}

	
	public MDisasterType getDtype() {
		return dtype;
	}

	public void setDtype(MDisasterType dtype) {
		this.dtype = dtype;
	}

	public String getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}

	public Boolean getAffected() {
		return affected;
	}

	public void setAffected(Boolean affected) {
		this.affected = affected;
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

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public MVillage getVcode() {
		return vcode;
	}

	public void setVcode(MVillage vcode) {
		this.vcode = vcode;
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
