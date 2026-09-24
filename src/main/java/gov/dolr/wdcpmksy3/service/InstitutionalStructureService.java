package gov.dolr.wdcpmksy3.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MFinYear;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InstitutionalStructureService {

	@Value("${upload.path}")
    private String uploadPath;
	
    @Autowired
    InstitutionalStructureRepository repository;

    
    public String saveInstitutionalStructure(Integer stcode, String stateName, String slnaType, java.time.LocalDate notificationDate,
            MultipartFile notificationFile, java.time.LocalDate mouDate, MultipartFile mouFile, String userid, String ip) {
        try {
            
        	File dir = new File(uploadPath);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
        	InstitutionalStructure obj = new InstitutionalStructure();
        		
 	        obj.setStCode(stcode);
 	        obj.setSlnaType(slnaType);
 	        obj.setNotificationDate(notificationDate);
 	        obj.setMouDate(mouDate);
 	        obj.setStatus('D');
 	        obj.setCreatedBy(userid);
 	        obj.setCreatedDate(LocalDateTime.now());
 	        obj.setRequestIp(ip);
 	        
 	        obj = repository.save(obj);

 	        Integer id = obj.getPprInstStrId();

 	        obj.setNotificationFile(uploadPath + id + "_"+ notificationFile.getOriginalFilename());
 	        obj.setMouFile(uploadPath + id + "_"+ mouFile.getOriginalFilename());

 	        repository.save(obj);
 	        
 	       notificationFile.transferTo(new File(uploadPath + id + "_"+ notificationFile.getOriginalFilename()));
	       mouFile.transferTo(new File(uploadPath + id + "_"+ mouFile.getOriginalFilename()));
            

            return "Record saved successfully!";
        } 
        catch (Exception e) {
            return "Error saving record: " + e.getMessage();
        }
    }
    
    
    public void updateInstitutionalStructure(InstitutionalStructure structure) {

        repository.save(structure);

    }
    
    public List<Object[]> getPPR1List(int stcode) {
    	
        return repository.getPPR1List(stcode);
    }
    
    public InstitutionalStructure getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    @Transactional
    public boolean completeRecordPPR1(Long id) {
        return repository.completeRecordPPR1(id) > 0;
    }
    
    @Transactional
    public boolean completeRecord1(Long id) {

        Optional<InstitutionalStructure> optional = repository.findById(id);

        if (optional.isPresent()) {

            InstitutionalStructure data = optional.get();
            data.setStatus('C');
            repository.save(data);

            return true;
        }

        return false;
    }
    
    
    
    
    
    

}