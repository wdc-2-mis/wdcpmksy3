package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MDisasterType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprDisasterDetails;
import gov.dolr.wdcpmksy3.PPR.repository.MDisasterTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprDisasterDetailsRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PprDisasterDetailsService {

	@Autowired
    private PprDisasterDetailsRepository repository;

	@Autowired 
	private MPprRepository pprRepo;
	
	@Autowired
	private MDisasterTypeRepository disasterRepo;
	
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
	
	public void saveRecords(Integer dcode, Integer vcode, Integer disasterTypeId, String periodicity, String affected, String userId,
			HttpServletRequest request) {
		PprDisasterDetails details = new PprDisasterDetails();
		MPpr ppr = pprRepo.findByDistrict_Dcode(dcode);
		MVillage vCode = new MVillage();
		vCode.setVcode(vcode);
		MDisasterType dtype = new MDisasterType();
		dtype.setDisasterTypeId(disasterTypeId);
		
	    details.setPpr(ppr);
	    details.setVcode(vCode);
	    details.setDtype(dtype);
	    details.setPeriodicity(periodicity);
	    details.setAffected("Y".equalsIgnoreCase(affected));
	    details.setStatus("D"); 
	    details.setRequestIp(getClientIpAddr(request));
	    details.setCreatedBy(userId); 
	    details.setCreatedDate(LocalDateTime.now());
	    repository.save(details);
	}

	
	public List<PprDisasterDetails> findAll() {
	    return repository.findAll(Sort.by(Sort.Direction.DESC, "status"));
	}

	
	@Transactional
	public void updateFloodDroughtArea(Integer pprDisasterId, Integer disasterTypeId, String periodicity,
			Boolean affected, String updatedBy) {
		
		PprDisasterDetails record =
				repository
                        .findById(pprDisasterId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Record not found."
                                )
                        );


        MDisasterType disasterType =
        		disasterRepo
                        .findById(disasterTypeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Disaster type not found."
                                )
                        );


        record.setDtype(disasterType);
        record.setPeriodicity(periodicity);
        record.setAffected(affected);

        record.setUpdatedBy(updatedBy);
        record.setUpdatedDate(LocalDate.now());


        repository.save(record);
    }

	@Transactional
	public void completeFloodDrought(Integer id, String updatedBy) {

	    PprDisasterDetails record =  repository
	                    .findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Record not found."
	                            )
	                    );

	    record.setStatus("C");
	    record.setUpdatedBy(updatedBy);
	    record.setUpdatedDate(LocalDate.now());

	    repository.save(record);
	}

	@Transactional
	public void deleteFloodDrought(Integer id) {

	    PprDisasterDetails record =
	    		repository
	                    .findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Record not found."
	                            )
	                    );

	    repository.delete(record);
	}
	
	
	
}
