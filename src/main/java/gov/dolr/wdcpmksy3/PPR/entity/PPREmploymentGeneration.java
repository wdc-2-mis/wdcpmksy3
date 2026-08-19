package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.persistence.*;


@Entity
@Table(name = "ppr_employment_generation", schema = "public")
public class PPREmploymentGeneration {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_employment_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr pprId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode", nullable = false)
    private MVillage village;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_type_id", nullable = false)
    private MEmploymentType employmentType;

    @Column(name = "sc")
    private Integer sc;

    @Column(name = "st")
    private Integer st;

    @Column(name = "others")
    private Integer others;

    @Column(name = "women")
    private Integer women;

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
    
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MPpr getPprId() {
		return pprId;
	}

	public void setPprId(MPpr pprId) {
		this.pprId = pprId;
	}

	public MVillage getVillage() {
		return village;
	}

	public void setVillage(MVillage village) {
		this.village = village;
	}

	public MicroWatershed getMicroWatershed() {
		return microWatershed;
	}

	public void setMicroWatershed(MicroWatershed microWatershed) {
		this.microWatershed = microWatershed;
	}

	public MEmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(MEmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public Integer getSt() {
		return st;
	}

	public void setSt(Integer st) {
		this.st = st;
	}

	public Integer getOthers() {
		return others;
	}

	public void setOthers(Integer others) {
		this.others = others;
	}

	public Integer getWomen() {
		return women;
	}

	public void setWomen(Integer women) {
		this.women = women;
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
