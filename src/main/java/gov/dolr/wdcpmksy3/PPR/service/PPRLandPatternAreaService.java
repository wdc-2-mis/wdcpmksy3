package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PPRLandPatternArea;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPRLandPatternAreaRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprMicroWatershedRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PPRLandPatternAreaService {
	
	@Autowired
    private PprMicroWatershedRepository pprMicroWatershedRepository;
	
	@Autowired
    private MPprRepository pprRepository;
	
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
    @Transactional
	public int changeStatusByPprId(Integer pprId) {
	    return landPatternAreaRepository.changeStatusByPprId(pprId);
	}
    public void skippprLandPatternArea(Integer pprid, String userId, HttpServletRequest servletRequest) {
        
    	PPRLandPatternArea entity = new PPRLandPatternArea(); 
    	MPpr ppr = pprRepository.getReferenceById(pprid);
    	
    	entity.setPprId(ppr); 
    	entity.setStatus('S'); 
    	entity.setCreatedBy(userId); 
    	entity.setRequestIp(servletRequest.getRemoteAddr() ); 
    	entity.setCreatedDate( LocalDateTime.now() ); 
    	landPatternAreaRepository.save(entity);
    }

	public List<Map<String, Object>> getLandPatternAreaByState(Integer stcode) {
		
		return landPatternAreaRepository.getLandPatternAreaByState(stcode);
	}
}
