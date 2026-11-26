package gov.dolr.wdcpmksy3.DPR.repository;

import java.math.BigDecimal;

public interface PhysicalActionPlanViewProjection {
	
	Integer getPlan_dtl_id();
	Integer getPlan_id();
	String getProject_name();
	Integer getPhy_activity_code();
	String getHead_desc();
	String getLevel();
	String getActivity_name();
	BigDecimal getQty_planned();
	String getUnit_desc();

}
