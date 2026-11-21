package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.Date;

public interface PhysicalActionPlanTranxProjection {
	
	 Integer getPlanid();

	    String getProjectdesc();

	    String getStatus();

	    String getCurrentlevel();

	    String getRemarks();

	    String getFinyr();

	    String getUsertype();

	    String getSentfrom();

	    String getSentto();

	    Date getSenton();

	    String getAction();

}
