package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MDisasterType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MWaterSource;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprDisasterDetails;
import gov.dolr.wdcpmksy3.PPR.entity.PprWaterOutcome;
import gov.dolr.wdcpmksy3.PPR.repository.PprWaterOutcomeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.WaterSourceRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PprWaterOutcomeService {

	@Autowired
    private PprWaterOutcomeRepository repository;

	@Autowired
	private MPprService pprService;
	
	@Autowired
	private WaterSourceRepository waterSourceRepository;
	
	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	public void saveOutcome(Integer dcode, Integer project, Integer watershed, Integer vcode, HttpServletRequest request, Integer sourceTypeId,
			String preProject, String expected_post, String remarks, String userId) {
		PprWaterOutcome details = new PprWaterOutcome();
		MPpr ppr = pprService.getById(project);
		
		MVillage vCode = new MVillage();
		vCode.setVcode(vcode);
		
		MicroWatershed mwtype = new MicroWatershed();
		mwtype.setMwId(watershed);
		
		MWaterSource msource = new MWaterSource();
		msource.setWaterSourceId(sourceTypeId);
		
		details.setPpr(ppr);
		details.setVillage(vCode);
		details.setMicroWatershed(mwtype);
		details.setWaterSource(msource);
		
		details.setPreProjectLevel(preProject);
		details.setPostProjectLevel(expected_post);
		details.setRemarks(remarks);
		details.setStatus("D");
		details.setRequestIp(getClientIpAddr(request));
	    details.setCreatedBy(userId); 
	    details.setCreatedDate(LocalDateTime.now());
		repository.save(details);
		
	}

	
	@Transactional()
	public List<PprWaterOutcome> findByDistrict(Integer dcode) {
		return repository.findByDistrict(dcode);
	}

	@Transactional
	public void updateWaterOutcome(Integer pprWaterOutcomeId, Integer sourceTypeId, String preProjectLevel,
			String postProjectLevel, String remarks, String updatedBy) {
		PprWaterOutcome outcome =
				repository.findById(pprWaterOutcomeId)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Water outcome record not found."
	                            ));

	    MWaterSource source =
	            waterSourceRepository.findById(sourceTypeId)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Water source not found."
	                            ));

	    outcome.setWaterSource(source);
	    outcome.setPreProjectLevel(preProjectLevel);
	    outcome.setPostProjectLevel(postProjectLevel);
	    outcome.setRemarks(remarks);

	   
	    repository.save(outcome);
		
	}

	@Transactional
	public void completeWaterOutcome(Integer id, String updatedBy) {

	    PprWaterOutcome outcome =
	    		repository.findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Water outcome record not found."
	                            ));

	    outcome.setStatus("C");
	    repository.save(outcome);
	}

	@Transactional
	public void deleteWaterOutcome(Integer id) {

	    PprWaterOutcome outcome =
	    		repository.findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Water outcome record not found."
	                            ));

	    repository.delete(outcome);
	}

	

	
}
