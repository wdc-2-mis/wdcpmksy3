package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "wdcpmksy_project_location",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uniq_wdcpmksy_project_location_vcode",
            columnNames = {"proj_id", "vcode"}
        )
    }
)
public class WdcpmksyProjectLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_id")
    private MPpr project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vcode")
    private MVillage village;

    @Column(name = "status", length = 1)
    private Character status;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "last_updated_by", length = 25)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public MPpr getProject() {
		return project;
	}

	public void setProject(MPpr project) {
		this.project = project;
	}

	public MVillage getVillage() {
		return village;
	}

	public void setVillage(MVillage village) {
		this.village = village;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(LocalDateTime createdDt) {
		this.createdDt = createdDt;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public LocalDate getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

    
}

