package gov.dolr.wdcpmksy3.DPR.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.repository.MDistrictRepository;

@Service
public class DPRELPService {
	
	@Autowired
    private MDistrictRepository districtRepo;

	public List<MDistrict> getDPRDistrictsByState(Integer stcode) {
		return districtRepo.getDPRDistrictsByState(stcode);
	}

}
