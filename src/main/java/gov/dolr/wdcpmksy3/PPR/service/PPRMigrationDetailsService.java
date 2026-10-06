package gov.dolr.wdcpmksy3.PPR.service;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import gov.dolr.wdcpmksy3.PPR.dto.PprMigrationDetailsDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PPRMigrationDetails;
import gov.dolr.wdcpmksy3.PPR.repository.PPRMigrationDetailsRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;

@Service
public class PPRMigrationDetailsService {
	
	@Autowired
	private PPRMigrationDetailsRepository repository;
	
	
	public PPRMigrationDetails save(PPRMigrationDetails entity) {
		
		

        return repository.save(entity);
    }	

	
	 public List<PPRMigrationDetails> getDraftsByPprId(Integer pprId) {
	        return repository.findByPprIdAndStatus(pprId, 'D');
	    }
	 
	 @Transactional
	 public void updatePPR15(
	         Integer pprMigrationId,
	         Integer peopleMigrating,
	         Integer daysMigrating,
	         String migrationReason,
	         Integer expectedReduction,
	         String userId,
	         HttpServletRequest request) {

	     PPRMigrationDetails data =repository.findById(pprMigrationId).orElse(null);

	     if (data == null) {
	         throw new RuntimeException("Record not found.");
	     }

	     data.setMigratingPeopleCount(peopleMigrating);
	     data.setMigrationDaysPerYear(daysMigrating);
	     data.setMigrationReason(migrationReason);
	     data.setExpectedReductionMigratingPeople(expectedReduction);

	     data.setCreatedBy(userId);
	     data.setRequestIp(CommonFunctions.getClientIpAddr(request));

	     repository.save(data);
	 }
	 
		@Transactional
		public int changeStatusByPprId(Integer pprId) {
			return repository.changeStatusByPprId(pprId);
		}
	
		public void skipPreliminaryPPR15(Integer pprid, String userId, HttpServletRequest servletRequest) {
	        
			PPRMigrationDetails entity = new PPRMigrationDetails(); 
	    	//MPpr ppr = pprRepository.getReferenceById(pprid);
	    	
	    	entity.setPprId(pprid);; 
	    	entity.setStatus('S'); 
	    	entity.setCreatedBy(userId); 
	    	entity.setRequestIp( CommonFunctions.getClientIpAddr(servletRequest) ); 
	    	entity.setCreatedDate( LocalDateTime.now() ); 
	    	repository.save(entity);
	    }
		
		public PprMigrationDetailsDTO getPprMigrationDetailsById(Integer id) {
		    PPRMigrationDetails data = repository.getById(id);
		   
		        PprMigrationDetailsDTO dto = new PprMigrationDetailsDTO();
		        dto.setPprMigrationId(data.getPprMigrationId());
		        dto.setPprId(data.getPprId());
		        dto.setVcode(data.getVcode());
		        dto.setMwId(data.getMwId());
		        dto.setMigratingPeopleCount(data.getMigratingPeopleCount());
		        dto.setMigrationDaysPerYear(data.getMigrationDaysPerYear());
		        dto.setMigrationReason(data.getMigrationReason());
		        dto.setExpectedReductionMigratingPeople(data.getExpectedReductionMigratingPeople());
		        dto.setStatus(data.getStatus() != null ? data.getStatus().toString() : null);
		        if (data.getPpr() != null) {
		            dto.setProjectName(data.getPpr().getProjectName());
		            if (data.getPpr().getDistrict() != null) {
		                dto.setDcode(data.getPpr().getDistrict().getDcode());
		                dto.setDistrictName(data.getPpr().getDistrict().getDistName());
		            }
		        }
		        if (data.getVillage() != null) {
		            dto.setVillageId(data.getVillage().getVcode());
		            dto.setVillageName(data.getVillage().getVillageName());
		        }
		        if (data.getMicroWatershed() != null) {
		            dto.setMicroWatershedId(data.getMicroWatershed().getMwId());
		            dto.setMicroWatershedName(data.getMicroWatershed().getMwName());
		        }
		    
		    return dto;
		}
		
	

}
