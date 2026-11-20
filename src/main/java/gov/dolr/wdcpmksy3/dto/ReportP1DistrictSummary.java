package gov.dolr.wdcpmksy3.dto;

import java.math.BigDecimal;

public interface ReportP1DistrictSummary {
	 Integer getDistrictCode();
	    String getDistrictName();
	    Long getProjectCount();
	    BigDecimal getAreaSanctioned();
	    BigDecimal getTotCost();
	    BigDecimal getCentralShare();
	    BigDecimal getStateShare();
}
