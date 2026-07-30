package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_wcdc_functionary_work_experience")
public class PprWcdcFunctionaryWorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_wcdc_fun_exp_id")
    private Integer pprWcdcFunExpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_wcdc_fun_id", nullable = false)
    private PprWcdcFunctionary functionary;

    @Column(name = "office_name", length = 200)
    private String officeName;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "work_exp_yrs")
    private Integer workExpYrs;

    @Column(name = "work_exp_days")
    private Integer workExpDays;

    @Column(name = "work_details", length = 1000)
    private String workDetails;

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

	public Integer getPprWcdcFunExpId() {
		return pprWcdcFunExpId;
	}

	public void setPprWcdcFunExpId(Integer pprWcdcFunExpId) {
		this.pprWcdcFunExpId = pprWcdcFunExpId;
	}

	public PprWcdcFunctionary getFunctionary() {
		return functionary;
	}

	public void setFunctionary(PprWcdcFunctionary functionary) {
		this.functionary = functionary;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getWorkExpYrs() {
		return workExpYrs;
	}

	public void setWorkExpYrs(Integer workExpYrs) {
		this.workExpYrs = workExpYrs;
	}

	public Integer getWorkExpDays() {
		return workExpDays;
	}

	public void setWorkExpDays(Integer workExpDays) {
		this.workExpDays = workExpDays;
	}

	public String getWorkDetails() {
		return workDetails;
	}

	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
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

    // Getters and Setters
    
    
    
    
    
    
    
}