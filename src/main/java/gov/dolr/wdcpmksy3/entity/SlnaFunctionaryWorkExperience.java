package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_slna_functionary_work_experience")
public class SlnaFunctionaryWorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_slna_fun_exp_id")
    private Integer pprSlnaFunExpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_slna_fun_id")
    private SlnaFunctionary functionary;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "address")
    private String address;

    @Column(name = "work_exp_yrs")
    private Integer workExpYrs;

    @Column(name = "work_exp_days")
    private Integer workExpDays;

    @Column(name = "work_details", length = 1000)
    private String workDetails;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;
    

	public Integer getPprSlnaFunExpId() {
		return pprSlnaFunExpId;
	}

	public void setPprSlnaFunExpId(Integer pprSlnaFunExpId) {
		this.pprSlnaFunExpId = pprSlnaFunExpId;
	}

	public SlnaFunctionary getFunctionary() {
		return functionary;
	}

	public void setFunctionary(SlnaFunctionary functionary) {
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

    
    
}