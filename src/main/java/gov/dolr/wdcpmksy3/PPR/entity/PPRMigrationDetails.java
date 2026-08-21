package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ppr_migration_details")
public class PPRMigrationDetails {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_migration_id")
    private Integer pprMigrationId;

    @Column(name = "ppr_id")
    private Integer pprId;

    @Column(name = "vcode")
    private Integer vcode;

    @Column(name = "mw_id")
    private Integer mwId;

    @Column(name = "migrating_people_count")
    private Integer migratingPeopleCount;

    @Column(name = "migration_days_per_year")
    private Integer migrationDaysPerYear;

    @Column(name = "migration_reason")
    private String migrationReason;

    @Column(name = "expected_reduction_migrating_people")
    private Integer expectedReductionMigratingPeople;

    @Column(name = "status")
    private Character status;

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

    // Getters and Setters

	public Integer getPprMigrationId() {
		return pprMigrationId;
	}

	public void setPprMigrationId(Integer pprMigrationId) {
		this.pprMigrationId = pprMigrationId;
	}

	public Integer getPprId() {
		return pprId;
	}

	public void setPprId(Integer pprId) {
		this.pprId = pprId;
	}

	public Integer getVcode() {
		return vcode;
	}

	public void setVcode(Integer vcode) {
		this.vcode = vcode;
	}

	public Integer getMwId() {
		return mwId;
	}

	public void setMwId(Integer mwId) {
		this.mwId = mwId;
	}

	public Integer getMigratingPeopleCount() {
		return migratingPeopleCount;
	}

	public void setMigratingPeopleCount(Integer migratingPeopleCount) {
		this.migratingPeopleCount = migratingPeopleCount;
	}

	public Integer getMigrationDaysPerYear() {
		return migrationDaysPerYear;
	}

	public void setMigrationDaysPerYear(Integer migrationDaysPerYear) {
		this.migrationDaysPerYear = migrationDaysPerYear;
	}

	public String getMigrationReason() {
		return migrationReason;
	}

	public void setMigrationReason(String migrationReason) {
		this.migrationReason = migrationReason;
	}

	public Integer getExpectedReductionMigratingPeople() {
		return expectedReductionMigratingPeople;
	}

	public void setExpectedReductionMigratingPeople(Integer expectedReductionMigratingPeople) {
		this.expectedReductionMigratingPeople = expectedReductionMigratingPeople;
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
