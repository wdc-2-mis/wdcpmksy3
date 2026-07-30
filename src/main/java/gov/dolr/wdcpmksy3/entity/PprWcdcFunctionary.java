package gov.dolr.wdcpmksy3.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_wcdc_functionary")
public class PprWcdcFunctionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_wcdc_fun_id")
    private Integer pprWcdcFunId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_wcdc_id", nullable = false)
    private PPRWcdcDetails wcdcDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

    @Column(name = "wcdc_fun_fname", length =50)
    private String firstName;

    @Column(name = "wcdc_fun_lname" , length =50)
    private String lastName;

    @Column(name = "work_allocation", length = 1000)
    private String workAllocation;

    @Column(name = "tot_budget_wcdc_recurring", precision =15, scale = 2)
    private BigDecimal totalBudgetRecurring;

    @Column(name = "tot_budget_wcdc_non_recurring", precision =15, scale = 2)
    private BigDecimal totalBudgetNonRecurring;

    @Column(name = "dolr_fund_recurring", precision =15, scale = 2)
    private BigDecimal dolrFundRecurring;

    @Column(name = "dolr_fund_non_recurring", precision =15, scale = 2)
    private BigDecimal dolrFundNonRecurring;

    @Column(name = "status")
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

    @OneToMany(mappedBy = "functionary",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PprWcdcFunctionaryWorkExperience> workExperiences = new ArrayList<PprWcdcFunctionaryWorkExperience>();
    
    
    // Getters and Setters

	public Integer getPprWcdcFunId() {
		return pprWcdcFunId;
	}

	public void setPprWcdcFunId(Integer pprWcdcFunId) {
		this.pprWcdcFunId = pprWcdcFunId;
	}

	public PPRWcdcDetails getWcdcDetails() {
		return wcdcDetails;
	}

	public void setWcdcDetails(PPRWcdcDetails wcdcDetails) {
		this.wcdcDetails = wcdcDetails;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWorkAllocation() {
		return workAllocation;
	}

	public void setWorkAllocation(String workAllocation) {
		this.workAllocation = workAllocation;
	}

	public BigDecimal getTotalBudgetRecurring() {
		return totalBudgetRecurring;
	}

	public void setTotalBudgetRecurring(BigDecimal totalBudgetRecurring) {
		this.totalBudgetRecurring = totalBudgetRecurring;
	}

	public BigDecimal getTotalBudgetNonRecurring() {
		return totalBudgetNonRecurring;
	}

	public void setTotalBudgetNonRecurring(BigDecimal totalBudgetNonRecurring) {
		this.totalBudgetNonRecurring = totalBudgetNonRecurring;
	}

	public BigDecimal getDolrFundRecurring() {
		return dolrFundRecurring;
	}

	public void setDolrFundRecurring(BigDecimal dolrFundRecurring) {
		this.dolrFundRecurring = dolrFundRecurring;
	}

	public BigDecimal getDolrFundNonRecurring() {
		return dolrFundNonRecurring;
	}

	public void setDolrFundNonRecurring(BigDecimal dolrFundNonRecurring) {
		this.dolrFundNonRecurring = dolrFundNonRecurring;
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

	public List<PprWcdcFunctionaryWorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(List<PprWcdcFunctionaryWorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

   
    
    
    
    
    
    
    
    
    
    
}