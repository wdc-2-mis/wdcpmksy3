package gov.dolr.wdcpmksy3.PPR.service;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
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
	 

	
	

}
