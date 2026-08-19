package gov.dolr.wdcpmksy3.PPR.dto;

import java.util.List;

public class PPREmploymentGenerationFormDTO {
	
	private Integer project;

    private Integer watershed;

    private Integer village;

    private List<PPREmploymentGenerationDTO> employmentList;

    private Character status;
    
    
    

	

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public Integer getWatershed() {
		return watershed;
	}

	public void setWatershed(Integer watershed) {
		this.watershed = watershed;
	}

	public Integer getVillage() {
		return village;
	}

	public void setVillage(Integer village) {
		this.village = village;
	}

	public List<PPREmploymentGenerationDTO> getEmploymentList() {
		return employmentList;
	}

	public void setEmploymentList(List<PPREmploymentGenerationDTO> employmentList) {
		this.employmentList = employmentList;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}
    
    
}
