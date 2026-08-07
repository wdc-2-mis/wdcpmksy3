package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;

@Service
public class MPprService {

    @Autowired
    private MPprRepository pprRepository;

    public List<MPpr> getProjectsByDistrict(Integer dcode) {
        return pprRepository.findByDistrict_DcodeAndStatusOrderByProjectNameAsc(dcode, "C");
    }
    
    public MPpr getById(Integer id){

        return pprRepository.findById(id).orElse(null);

    }
}