package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.repository.IwmpDistrictRepository;

@Service
public class DistrictService {

    @Autowired
    private IwmpDistrictRepository districtRepo;

    public List<MDistrict> getDistrictsByState(Integer stCode) {
        return districtRepo.findByState_StCodeOrderByDistNameAsc(stCode);
    }
    
    public List<MDistrict> findCompletedDistrictsByState(Integer stCode) {
        return districtRepo.findCompletedDistrictsByState(stCode);
    }

	public List<MDistrict> getPPRDistrictsByState(Integer stcode) {
		// TODO Auto-generated method stub
		return districtRepo.getPPRDistrictsByState(stcode);
	}
    
    
}
