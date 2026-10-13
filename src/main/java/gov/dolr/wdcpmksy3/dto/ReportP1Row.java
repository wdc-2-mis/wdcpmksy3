package gov.dolr.wdcpmksy3.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReportP1Row {
	 String getStateName();
	    String getDistrictName();
	    String getFinYear();
	    String getProjectName();
	    LocalDate getSanctionDate();
	    BigDecimal getAreaSanctioned();
	    BigDecimal getTotCost();
	    BigDecimal getCentralShare();
	    BigDecimal getStateShare();

}
