package gov.dolr.wdcpmksy3.PPR.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.entity.Designation;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.MemberDetails;
import gov.dolr.wdcpmksy3.entity.Qualification;

@Entity
@Table(name = "ppr_slna_details", schema = "public")
public class PprSlnaDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_slna_id")
    private Integer pprSlnaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_inst_str_id", nullable = false)
    private InstitutionalStructure institutionalStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberDetails member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id", nullable = false)
    private Designation designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", nullable = false)
    private Qualification qualification;

    @Column(name = "first_name", length = 100)
    private String firstName;
    
    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "joining_type", length = 1)
    private String joiningType;

    @Column(name = "tenure_period_yr")
    private Integer tenurePeriodYr;

    @Column(name = "tenure_period_month")
    private Integer tenurePeriodMonth;

    @Column(name = "phone_no", length = 15)
    private String phoneNo;

    @Column(name = "email_id", length = 50)
    private String emailId;

    @Column(name = "fax", length = 50)
    private String fax;

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

	public Integer getPprSlnaId() {
		return pprSlnaId;
	}

	public void setPprSlnaId(Integer pprSlnaId) {
		this.pprSlnaId = pprSlnaId;
	}

	public InstitutionalStructure getInstitutionalStructure() {
		return institutionalStructure;
	}

	public void setInstitutionalStructure(InstitutionalStructure institutionalStructure) {
		this.institutionalStructure = institutionalStructure;
	}

	public MemberDetails getMember() {
		return member;
	}

	public void setMember(MemberDetails member) {
		this.member = member;
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

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getJoiningType() {
		return joiningType;
	}

	public void setJoiningType(String joiningType) {
		this.joiningType = joiningType;
	}

	public Integer getTenurePeriodYr() {
		return tenurePeriodYr;
	}

	public void setTenurePeriodYr(Integer tenurePeriodYr) {
		this.tenurePeriodYr = tenurePeriodYr;
	}

	public Integer getTenurePeriodMonth() {
		return tenurePeriodMonth;
	}

	public void setTenurePeriodMonth(Integer tenurePeriodMonth) {
		this.tenurePeriodMonth = tenurePeriodMonth;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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
    
}
