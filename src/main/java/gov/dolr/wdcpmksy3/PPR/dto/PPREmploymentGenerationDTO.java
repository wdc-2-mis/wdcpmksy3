package gov.dolr.wdcpmksy3.PPR.dto;

public class PPREmploymentGenerationDTO {
	
	private Integer pprEmploymentId;
	
	private Integer employmentTypeId;

    private String employmentTypeName;

    private Integer sc;

    private Integer st;

    private Integer others;

    private Integer women;
    
    
	

	public Integer getPprEmploymentId() {
		return pprEmploymentId;
	}

	public void setPprEmploymentId(Integer pprEmploymentId) {
		this.pprEmploymentId = pprEmploymentId;
	}
	
	public Integer getEmploymentTypeId() {
		return employmentTypeId;
	}

	public void setEmploymentTypeId(Integer employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public String getEmploymentTypeName() {
		return employmentTypeName;
	}

	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
	}

	public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public Integer getSt() {
		return st;
	}

	public void setSt(Integer st) {
		this.st = st;
	}

	public Integer getOthers() {
		return others;
	}

	public void setOthers(Integer others) {
		this.others = others;
	}

	public Integer getWomen() {
		return women;
	}

	public void setWomen(Integer women) {
		this.women = women;
	}


}
