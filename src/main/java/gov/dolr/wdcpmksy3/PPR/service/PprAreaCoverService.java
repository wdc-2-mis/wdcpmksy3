package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MScheme;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAreaCoveredRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWatershedCoveredAreaRepo;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PprAreaCoverService {
	
	@Autowired
	private PprAreaCoveredRepository repo;

	@Autowired
	private PprWatershedCoveredAreaRepo wcarearepo;
	
	@Autowired 
	private MPprRepository pprRepo;
	
	public List<MScheme> getAllSchemes() {
        return repo.findAll();
    }


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
	
	public void saveRecords(Integer district, Integer mw, Map<String, String> params, String userId,
			HttpServletRequest servletRequest) {
        List<PprWatershedCoveredArea> entities = new ArrayList<>();

        params.forEach((key, value) -> {
            if (key.startsWith("scheme[") && key.endsWith("].no")) {
                String schemeIdStr = key.substring(7, key.indexOf("].no"));
                Integer schemeId = Integer.valueOf(schemeIdStr);

                String noStr = value;
                String areaStr = params.get("scheme[" + schemeId + "].area");

                PprWatershedCoveredArea entity = new PprWatershedCoveredArea();
                MPpr ppr = pprRepo.findByDistrict_Dcode(district);
                
                
                MicroWatershed mwEntity = new MicroWatershed();
                mwEntity.setMwId(mw);

                MScheme scheme = new MScheme();
                scheme.setSchemeId(schemeId);
                
                entity.setPpr(ppr); 
                entity.setMicroWatershed(mwEntity);
                entity.setScheme(scheme);
                entity.setNoMw(noStr != null && !noStr.isEmpty() ? Integer.valueOf(noStr) : null);
                entity.setAreaMw(areaStr != null && !areaStr.isEmpty() ? new BigDecimal(areaStr) : null);
                entity.setStatus("D"); 
                entity.setCreatedBy(userId); 
                entity.setRequestIp(getClientIpAddr(servletRequest)); 
                entities.add(entity);
            }
        });

        wcarearepo.saveAll(entities);
    }


}
