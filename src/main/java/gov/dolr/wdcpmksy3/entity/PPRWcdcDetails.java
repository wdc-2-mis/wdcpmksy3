package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="ppr_wcdc_details")

public class PPRWcdcDetails {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ppr_wcdc_id")
	    private Integer pprWcdcId;
	   
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ppr_inst_str_id")
	    private InstitutionalStructure institutionalStructure;

	    @Column(name = "dcode")
	    private Integer dcode;

	    @Column(name = "executing_agency", length=100)
	    private String executingAgency;

	    @Column(name = "chairman_status", length=100)
	    private String chairmanStatus;

	    @Column(name = "mou_date")
	    private LocalDate mouDate;

	    @Column(name = "mou_file")
	    private String mouFile;

	    @Column(name = "status")
	    private Character status;

	    @Column(name = "request_ip", length=20)
	    private String requestIp;

	    @Column(name = "created_by", length=20)
	    private String createdBy;

	    @Column(name = "created_date")
	    private LocalDateTime createdDate;

	    @Column(name = "updated_by", length=20)
	    private String updatedBy;

	    @Column(name = "updated_date")
	    private LocalDate updatedDate;

	    // Getters and Setters

	    public Integer getPprWcdcId() {
	        return pprWcdcId;
	    }

	    public void setPprWcdcId(Integer pprWcdcId) {
	        this.pprWcdcId = pprWcdcId;
	    }

	    public InstitutionalStructure getInstitutionalStructure() {
			return institutionalStructure;
		}

		public void setInstitutionalStructure(InstitutionalStructure institutionalStructure) {
			this.institutionalStructure = institutionalStructure;
		}

		public Integer getDcode() {
	        return dcode;
	    }

	    public void setDcode(Integer dcode) {
	        this.dcode = dcode;
	    }

	    public String getExecutingAgency() {
	        return executingAgency;
	    }

	    public void setExecutingAgency(String executingAgency) {
	        this.executingAgency = executingAgency;
	    }

	    public String getChairmanStatus() {
	        return chairmanStatus;
	    }

	    public void setChairmanStatus(String chairmanStatus) {
	        this.chairmanStatus = chairmanStatus;
	    }

	    public LocalDate getMouDate() {
	        return mouDate;
	    }

	    public void setMouDate(LocalDate mouDate) {
	        this.mouDate = mouDate;
	    }

	    public String getMouFile() {
	        return mouFile;
	    }

	    public void setMouFile(String mouFile) {
	        this.mouFile = mouFile;
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
