package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.PPRLandPatternArea;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.PPR.repository.PPRLandPatternAreaRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprMicroWatershedRepository;

@Service
public class PPRLandPatternAreaService {
	
	@Autowired
    private PprMicroWatershedRepository pprMicroWatershedRepository;
	
	@Autowired
    private PPRLandPatternAreaRepository landPatternAreaRepository;

    public List<PprMicroWatershed> getMicroWatershedsByProject(Integer pprId){

        return pprMicroWatershedRepository.findByPprPprId(pprId);

    }
    
    public PPRLandPatternArea savePPRLandPatternArea(PPRLandPatternArea entity){

        return landPatternAreaRepository.save(entity);

    }
    
    public List<Map<String,Object>> getLandPatternAreaByDistrict(Integer dcode){

        return landPatternAreaRepository.getLandPatternAreaByDistrict(dcode);

    }
    
    public PPRLandPatternArea getById(Integer id){

        return landPatternAreaRepository.findById(id).orElse(null);

    }
    
    public void delete(Integer id){

        landPatternAreaRepository.deleteById(id);

    }
    
    public Character getVillageStatus(Integer vcode) {
        return landPatternAreaRepository.getStatusByVillage(vcode);
    }
    
    public boolean existsByVillage(Integer vcode) {

        return landPatternAreaRepository.countByVillage(vcode) > 0;

    }
	
}
