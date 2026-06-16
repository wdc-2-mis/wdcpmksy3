package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.IwmpDistrict;
import gov.dolr.wdcpmksy3.repository.IwmpDistrictRepository;

@Service
public class DistrictService {

    @Autowired
    private IwmpDistrictRepository districtRepo;

    public List<IwmpDistrict> getDistrictsByState(Integer stCode) {
        return districtRepo.findByState_StCodeOrderByDistNameAsc(stCode);
    }
}
