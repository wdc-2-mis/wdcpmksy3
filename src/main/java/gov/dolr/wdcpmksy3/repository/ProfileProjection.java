package gov.dolr.wdcpmksy3.repository;

public interface ProfileProjection {

    Integer getStateCode();
    String getStateName();

    Integer getDistrictCode();
    String getDistrictName();

    Integer getProjectCode();
    String getProjectName();

    String getSelected();

    Integer getStateCodelgd();
    Integer getDistrictCodelgd();
}