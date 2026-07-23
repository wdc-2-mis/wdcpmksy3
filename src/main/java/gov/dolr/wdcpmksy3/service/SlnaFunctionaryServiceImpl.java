package gov.dolr.wdcpmksy3.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionary;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionaryWorkExperience;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;
import gov.dolr.wdcpmksy3.repository.SlnaFunctionaryRepository;
import gov.dolr.wdcpmksy3.repository.SlnaFunctionaryWorkExperienceRepository;

@Service
@Transactional
public class SlnaFunctionaryServiceImpl implements SlnaFunctionaryService {

    @Autowired
    private SlnaFunctionaryRepository functionaryRepo;

    @Autowired
    private SlnaFunctionaryWorkExperienceRepository experienceRepo;

    @Autowired
    private InstitutionalStructureRepository institutionalRepo;

	@Override
	public void saveFunctionary(Integer pprInstStrId, String name, Integer designation, Integer qualification,
			String workallocation, BigDecimal slr, BigDecimal slnr, BigDecimal dlr, BigDecimal dlnr,
			String[] officename, String[] address, Integer[] yr, Integer[] day, String[] workdetail, String status,
			String user, String ip) {
		
	//	InstitutionalStructure inst1 = new InstitutionalStructure();
	//	inst.setPprInstStrId(pprInstStrId);
		
		InstitutionalStructure inst = institutionalRepo.getReferenceById(pprInstStrId.longValue());
		
			
	        SlnaFunctionary fun = new SlnaFunctionary();
	        
	       // fun.setInstitutionalStructure(inst1);
	        fun.setInstitutionalStructure(inst);
	        fun.setSlnaFunFname(name);
	        fun.setDesignationId(designation);
	        fun.setQualificationId(qualification);
	        fun.setWorkAllocation(workallocation);

	        fun.setTotBudgetSlnaRecurring(slr);
	        fun.setTotBudgetSlnaNonRecurring(slnr);

	        fun.setDolrFundRecurring(dlr);
	        fun.setDolrFundNonRecurring(dlnr);

	        fun.setStatus(status.charAt(0));
	        fun.setCreatedBy(user);
	        fun.setRequestIp(ip);

	        functionaryRepo.save(fun);

	        for (int i = 0; i < officename.length; i++) 
	        {
	            if (officename[i] == null || officename[i].trim().isEmpty())
	                continue;

	            SlnaFunctionaryWorkExperience exp =new SlnaFunctionaryWorkExperience();

	            exp.setFunctionary(fun);
	            exp.setOfficeName(officename[i]);
	            exp.setAddress(address[i]);
	            exp.setWorkExpYrs(yr[i]);
	            exp.setWorkExpDays(day[i]);
	            exp.setWorkDetails(workdetail[i]);
	            exp.setCreatedBy(user);
	            exp.setRequestIp(ip);
	            experienceRepo.save(exp);
	        }
	}
	

	    public List<Object[]> getFunctionariesList(Integer stcode) {
	        return functionaryRepo.getFunctionariesList(stcode);
	    }
	    
	    @Transactional
	    public void completeRecord(Integer id) {

	        SlnaFunctionary fun = functionaryRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Record not found"));

	        fun.setStatus('C');

	        functionaryRepo.save(fun);
	    }

}