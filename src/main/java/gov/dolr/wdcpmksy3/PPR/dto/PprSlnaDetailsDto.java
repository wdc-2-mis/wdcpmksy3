package gov.dolr.wdcpmksy3.PPR.dto;

import java.time.LocalDate;

public class PprSlnaDetailsDto {

	private Integer pprSlnaId;

    private Integer memberId;

    private Integer designationId;

    private Integer qualificationId;

    private String firstName;

    private String lastName;

    private LocalDate appointmentDate;

    private String joiningType;

    private Integer tenurePeriodYr;

    private String phoneNo;

    private String emailId;

    private String fax;

	public Integer getPprSlnaId() {
		return pprSlnaId;
	}

	public void setPprSlnaId(Integer pprSlnaId) {
		this.pprSlnaId = pprSlnaId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
    
    
}
