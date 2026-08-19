package gov.dolr.wdcpmksy3.PPR.dto;

import java.util.ArrayList;
import java.util.List;

public class PPRSoilErosionFormDTO {
	
	private List<PPRSoilErosionDTO> erosionList = new ArrayList<>();

	public List<PPRSoilErosionDTO> getErosionList() {
		return erosionList;
	}

	public void setErosionList(List<PPRSoilErosionDTO> erosionList) {
		this.erosionList = erosionList;
	}
	

}
