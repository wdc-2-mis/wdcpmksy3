package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.CropType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MSeason;
import gov.dolr.wdcpmksy3.PPR.entity.PprCropOutcome;
import gov.dolr.wdcpmksy3.PPR.entity.PprWaterOutcome;
import gov.dolr.wdcpmksy3.PPR.repository.CropTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MSeasonRepo;
import gov.dolr.wdcpmksy3.repository.CropOutcomeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PprCropOutcomeService {
	
	 @Autowired
	 private CropOutcomeRepository pprCropOutcomeRepo;
	 
	 @Autowired
	 private MPprService pprService;
	 
	 @Autowired
	 private MSeasonRepo mseasonRepo;
	 
	 @Autowired
	 private CropTypeRepository cropTypeRepo;

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
	 
	@Transactional
	public void saveCropRelatedOutcome(Integer dcode, Integer project, Integer seasonTypeId, Integer[] cropTypeIds,
			String[] currentAreas, String[] currentProds, String[] expectedAreas, String[] expectedProds,
			HttpServletRequest request, String userId) {
		  MPpr ppr = pprService.getById(project);

	    MSeason season = mseasonRepo.findById(seasonTypeId)
	            .orElseThrow(() ->
	                    new RuntimeException("Season not found: " + seasonTypeId));

	   
	    for (int i = 0; i < cropTypeIds.length; i++) {

	        if (cropTypeIds[i] == null) {
	            continue;
	        }

	        Integer cropTypeId = cropTypeIds[i];

	        CropType cropType = cropTypeRepo.findById(cropTypeId)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Crop not found: " + cropTypeId
	                        )
	                );

	        PprCropOutcome outcome = new PprCropOutcome();

	        outcome.setPpr(ppr);
	        outcome.setCropType(cropType);
	        outcome.setSeason(season);

	        if (currentAreas[i] != null &&
	                !currentAreas[i].trim().isEmpty()) {

	            outcome.setCurrentArea(
	                    new BigDecimal(currentAreas[i].trim())
	            );
	        }

	        if (currentProds[i] != null &&
	                !currentProds[i].trim().isEmpty()) {

	            outcome.setCurrentProd(
	                    new BigDecimal(currentProds[i].trim())
	            );
	        }

	        if (expectedAreas[i] != null &&
	                !expectedAreas[i].trim().isEmpty()) {

	            outcome.setExpectedArea(
	                    new BigDecimal(expectedAreas[i].trim())
	            );
	        }

	        if (expectedProds[i] != null &&
	                !expectedProds[i].trim().isEmpty()) {

	            outcome.setExpectedProd(
	                    new BigDecimal(expectedProds[i].trim())
	            );
	        }

	        outcome.setStatus("D");
	        outcome.setRequestIp(getClientIpAddr(request));
	        outcome.setCreatedBy(userId);
	        outcome.setCreatedDate(LocalDateTime.now());

	        pprCropOutcomeRepo.save(outcome);
	    }
	}

	@Transactional
	public void updateCropOutcome(Integer id, Integer project, Integer seasonId, Integer cropTypeId, String currentArea,
			String currentProd, String expectedArea, String expectedProd, HttpServletRequest request, String userId) {
		PprCropOutcome outcome =
	            pprCropOutcomeRepo.findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Crop outcome not found: " + id
	                            ));

	    CropType cropType =
	            cropTypeRepo.findById(cropTypeId)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Crop not found: " + cropTypeId
	                            ));

	    MSeason season =
	            mseasonRepo.findById(seasonId)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Season not found: " + seasonId
	                            ));

        outcome.setCropType(cropType);
        outcome.setSeason(season);
         if (currentArea != null &&
	            !currentArea.trim().isEmpty()) {

	        outcome.setCurrentArea(
	                new BigDecimal(currentArea.trim())
	        );

	    } else {

	        outcome.setCurrentArea(null);
	    }
       if (currentProd != null &&
	            !currentProd.trim().isEmpty()) {

	        outcome.setCurrentProd(
	                new BigDecimal(currentProd.trim())
	        );

	    } else {

	        outcome.setCurrentProd(null);
	    }

       if (expectedArea != null &&
	            !expectedArea.trim().isEmpty()) {

	        outcome.setExpectedArea(
	                new BigDecimal(expectedArea.trim())
	        );

	    } else {

	        outcome.setExpectedArea(null);
	    }
     if (expectedProd != null &&
	            !expectedProd.trim().isEmpty()) {

	        outcome.setExpectedProd(
	                new BigDecimal(expectedProd.trim())
	        );

	    } else {

	        outcome.setExpectedProd(null);
	    }
        outcome.setUpdatedBy(userId);
        outcome.setUpdatedDate(LocalDate.now());
        pprCropOutcomeRepo.save(outcome);
	}

	@Transactional
	public void completeWaterOutcome(Integer id, String updatedBy) {
		PprCropOutcome outcome =
				pprCropOutcomeRepo.findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Crop outcome record not found."
	                            ));

	    outcome.setStatus("C");
	    pprCropOutcomeRepo.save(outcome);
		
	}

	@Transactional
	public void deleteWaterOutcome(Integer id) {
		PprCropOutcome outcome =
				pprCropOutcomeRepo.findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Crop outcome record not found."
	                            ));

		pprCropOutcomeRepo.delete(outcome);
		
	}

	
}
