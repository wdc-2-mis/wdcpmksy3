package gov.dolr.wdcpmksy3.dto;

public interface RoleMenuProjection {
	
	
	String getRolename();

    Integer getMenuid();

    String getMenuname();

    Integer getParentid();

    String getParentname();

    String getTarget();

    Integer getSequence();

    Integer getCountsub();

    Integer getHseqno();

    Boolean getPactive();

    Boolean getCactive();

}
