package gov.dolr.wdcpmksy3.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_slna_functionary")
public class SlnaFunctionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_slna_fun_id")
    private Integer pprSlnaFunId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_inst_str_id")
    private InstitutionalStructure institutionalStructure;

    @Column(name = "designation_id")
    private Integer designationId;

    @Column(name = "qualification_id")
    private Integer qualificationId;

    @Column(name = "slna_fun_name", length = 50)
    private String slnaFunName;

    @Column(name = "work_allocation", length = 1000)
    private String workAllocation;

    @Column(name = "tot_budget_slna_recurring", precision =15, scale = 2)
    private BigDecimal totBudgetSlnaRecurring;

    @Column(name = "tot_budget_slna_non_recurring" , precision =15, scale = 2)
    private BigDecimal totBudgetSlnaNonRecurring;

    @Column(name = "dolr_fund_recurring" , precision =15, scale = 2)
    private BigDecimal dolrFundRecurring;

    @Column(name = "dolr_fund_non_recurring" , precision =15, scale = 2)
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

    @OneToMany(mappedBy = "functionary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SlnaFunctionaryWorkExperience> experiences = new ArrayList<SlnaFunctionaryWorkExperience>();

	public Integer getPprSlnaFunId() {
		return pprSlnaFunId;
	}

	public void setPprSlnaFunId(Integer pprSlnaFunId) {
		this.pprSlnaFunId = pprSlnaFunId;
	}

	public InstitutionalStructure getInstitutionalStructure() {
		return institutionalStructure;
	}

	public void setInstitutionalStructure(InstitutionalStructure institutionalStructure) {
		this.institutionalStructure = institutionalStructure;
	}

	public Integer getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getSlnaFunName() {
		return slnaFunName;
	}

	public void setSlnaFunName(String slnaFunName) {
		this.slnaFunName = slnaFunName;
	}

	public String getWorkAllocation() {
		return workAllocation;
	}

	public void setWorkAllocation(String workAllocation) {
		this.workAllocation = workAllocation;
	}

	public BigDecimal getTotBudgetSlnaRecurring() {
		return totBudgetSlnaRecurring;
	}

	public void setTotBudgetSlnaRecurring(BigDecimal totBudgetSlnaRecurring) {
		this.totBudgetSlnaRecurring = totBudgetSlnaRecurring;
	}

	public BigDecimal getTotBudgetSlnaNonRecurring() {
		return totBudgetSlnaNonRecurring;
	}

	public void setTotBudgetSlnaNonRecurring(BigDecimal totBudgetSlnaNonRecurring) {
		this.totBudgetSlnaNonRecurring = totBudgetSlnaNonRecurring;
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

	public List<SlnaFunctionaryWorkExperience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<SlnaFunctionaryWorkExperience> experiences) {
		this.experiences = experiences;
	}

    // Getters & Setters
    
    
    
    
    
    
    
    
    
}