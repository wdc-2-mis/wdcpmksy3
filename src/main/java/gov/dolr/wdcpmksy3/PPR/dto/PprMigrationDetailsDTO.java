package gov.dolr.wdcpmksy3.PPR.dto;

public class PprMigrationDetailsDTO {

    private Integer pprMigrationId;
    private Integer pprId;
    private Integer vcode;
    private Integer mwId;
    private Integer migratingPeopleCount;
    private Integer migrationDaysPerYear;
    private String migrationReason;
    private Integer expectedReductionMigratingPeople;
    private String status;

    private String projectName;

    private Integer dcode;
    private String districtName;

    private Integer villageId;
    private String villageName;

    private Integer microWatershedId;
    private String microWatershedName;
    
    public PprMigrationDetailsDTO() {
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDcode() {
        return dcode;
    }

    public void setDcode(Integer dcode) {
        this.dcode = dcode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Integer getMicroWatershedId() {
        return microWatershedId;
    }

    public void setMicroWatershedId(Integer microWatershedId) {
        this.microWatershedId = microWatershedId;
    }

    public String getMicroWatershedName() {
        return microWatershedName;
    }

    public void setMicroWatershedName(String microWatershedName) {
        this.microWatershedName = microWatershedName;
    }
}