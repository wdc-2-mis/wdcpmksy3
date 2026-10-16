package gov.dolr.wdcpmksy3.dto;


import java.math.BigDecimal;

/** One row of the "All State / All District / All Financial Year" summary. */
public interface ReportP1StateSummary {
    Integer getStateCode();
    String getStateName();
    Long getDistrictCount();
    Long getProjectCount();
    BigDecimal getAreaSanctioned();
    BigDecimal getTotCost();
    BigDecimal getCentralShare();
    BigDecimal getStateShare();
}
