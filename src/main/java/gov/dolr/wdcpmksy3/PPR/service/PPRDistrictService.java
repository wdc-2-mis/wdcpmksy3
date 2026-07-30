package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.IwmpMFinYear;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.PPR.repository.IwmpMFinYearRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprMicroWatershedRepository;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.IwmpDistrict;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;
import gov.dolr.wdcpmksy3.repository.IwmpDistrictRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PPRDistrictService {

	@Autowired private MPprRepository pprRepo;
    @Autowired private IwmpMFinYearRepository finYearRepo;
    @Autowired private IwmpDistrictRepository districtRepo;
    @Autowired private MicroWatershedRepository microRepo;
    @Autowired private InstitutionalStructureRepository instRepo;
    @Autowired private PprMicroWatershedRepository pmwRepo;
    
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
    
	public String savePreliminaryPPR(Integer finYrCd, Integer dcode, String projectName, List<Integer> mwIds, String userId,  Integer stCode, HttpServletRequest servletRequest) {
        try {
            IwmpMFinYear finYear = finYearRepo.findById(finYrCd).orElseThrow();
            IwmpDistrict district = districtRepo.findById(dcode).orElseThrow();
            InstitutionalStructure inst = instRepo.findByStCode(stCode);

            MPpr ppr = new MPpr();
            ppr.setFinYear(finYear);
            ppr.setDistrict(district);
            ppr.setInstitutionalStructure(inst);
            ppr.setProjectName(projectName);
            ppr.setStatus("D");
            ppr.setCreatedBy(userId);
            ppr.setRequestIp(getClientIpAddr(servletRequest));

            ppr = pprRepo.save(ppr);

            for (Integer mwId : mwIds) {
                MicroWatershed micro = microRepo.findById(mwId).orElseThrow();

                if (!pmwRepo.existsByPprPprIdAndMicroWatershedMwId(ppr.getPprId(), mwId)) {
                    PprMicroWatershed pmw = new PprMicroWatershed();
                    pmw.setPpr(ppr);
                    pmw.setMicroWatershed(micro);
                    pmw.setStatus("D");
                    pmw.setCreatedBy(userId);
                    pmw.setRequestIp(getClientIpAddr(servletRequest));

                    pmwRepo.save(pmw);
                }
            }

            return "Record saved successfully!";
        } catch (Exception e) {
            return "Error saving record: " + e.getMessage();
        }
    }

	public String updatePreliminaryPPR(Integer pprId, List<Integer> microIds, String userId, HttpServletRequest request) {
		 try {
	        MPpr ppr = pprRepo.findById(pprId).orElseThrow();

	        pmwRepo.deleteAll(ppr.getMicroWatersheds());

	        for (Integer mwId : microIds) {
	            MicroWatershed micro = microRepo.findById(mwId).orElseThrow();

	            PprMicroWatershed pmw = new PprMicroWatershed();
	            pmw.setPpr(ppr);
	            pmw.setMicroWatershed(micro);
	            pmw.setStatus("D");
	            pmw.setCreatedBy(userId);
	            pmw.setRequestIp(getClientIpAddr(request));

	            pmwRepo.save(pmw);
	        }

	        return "Record updated successfully!";
	    } catch (Exception e) {
	        return "Error updating record: " + e.getMessage();
	    }
	}

	public String completePPRDist(Integer id, String userId) {
        try {
            MPpr ppr = pprRepo.findById(id).orElseThrow();

            ppr.setStatus("C");
            ppr.setUpdatedBy(userId);
            
            pprRepo.save(ppr);

            List<PprMicroWatershed> linkedMW = pmwRepo.findByPprPprId(id);
            for (PprMicroWatershed pmw : linkedMW) {
                pmw.setStatus("C");
                pmw.setUpdatedBy(userId);
                pmwRepo.save(pmw);
            }

            return "Record Completed Successfully!";
        } catch (Exception e) {
            return "Error completing record: " + e.getMessage();
        }
    }

	public String deletePPRDist(Integer id) {
		try {
            MPpr ppr = pprRepo.findById(id).orElseThrow();

            List<PprMicroWatershed> linkedMW = pmwRepo.findByPprPprId(id);
            pmwRepo.deleteAll(linkedMW);

            pprRepo.delete(ppr);

            return "Record deleted successfully!";
        } catch (Exception e) {
            return "Error deleting record: " + e.getMessage();
        }
    
	}
}
