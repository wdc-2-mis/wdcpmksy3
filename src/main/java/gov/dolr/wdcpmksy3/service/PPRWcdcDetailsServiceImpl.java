package gov.dolr.wdcpmksy3.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.Designation;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.PPRWcdcDetails;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionary;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionaryWorkExperience;
import gov.dolr.wdcpmksy3.entity.Qualification;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionary;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionaryWorkExperience;
import gov.dolr.wdcpmksy3.repository.DesignationRepository;
import gov.dolr.wdcpmksy3.repository.PPRWcdcDetailsRepository;
import gov.dolr.wdcpmksy3.repository.PprWcdcFunctionaryRepository;
import gov.dolr.wdcpmksy3.repository.PprWcdcFunctionaryWorkExperienceRepository;
import gov.dolr.wdcpmksy3.repository.QualificationRepository;

@Service
public class PPRWcdcDetailsServiceImpl implements PPRWcdcDetailsService {

    @Autowired
    private PPRWcdcDetailsRepository repository;
    
    @Autowired
    private DesignationRepository desrep;
    
    @Autowired
    private QualificationRepository quarep;
    
    @Autowired
    private PprWcdcFunctionaryRepository wcfunrepo;
    
    @Autowired
    private PprWcdcFunctionaryWorkExperienceRepository wcExpRepo;

    @Override
	public List<Object[]> getPPR4List(Integer stcode) {
		// TODO Auto-generated method stub
		return repository.getPPR4List(stcode);
	}
	
	@Override
	@Transactional
	public boolean completeRecordPPR4(Integer id) {

		 return repository.completeRecordPPR4(id) > 0;
	}

	@Override
	public PPRWcdcDetails getById(Integer id) {
		
		        return repository.findById(id).orElse(null);
		    
	}

	@Override
	public void delete(Integer id) {
        repository.deleteById(id);
    }

	@Override
	public void saveWCDCFunctionary(Integer district, String fname, String lname, Integer designation,
			Integer qualification, String workallocation, BigDecimal slr, BigDecimal slnr, BigDecimal dlr,
			BigDecimal dlnr, String[] officename, String[] address, Integer[] yr, Integer[] day, String[] workdetail,
			String status, String userid, String ip) {
		
		
		Integer pprwcId =0;
    	List<Object[]> list = repository.getPPR4BWCDCList(district);
		for (Object[] row : list) {

			pprwcId = (Integer) row[0];
		}
		
		PPRWcdcDetails wcd = repository.getReferenceById(pprwcId);
		Designation des=desrep.getReferenceById(designation);
		Qualification qa=quarep.getReferenceById(qualification);
		
		PprWcdcFunctionary fun = new PprWcdcFunctionary();
        
        fun.setWcdcDetails(wcd);
        fun.setFirstName(fname);
        fun.setLastName(lname);
        fun.setDesignation(des);
        fun.setQualification(qa);
        fun.setWorkAllocation(workallocation);
        fun.setTotalBudgetRecurring(slr);
        fun.setTotalBudgetNonRecurring(slnr);
        fun.setDolrFundRecurring(dlr);
        fun.setDolrFundNonRecurring(dlnr);
        fun.setStatus(status.charAt(0));
        fun.setCreatedBy(userid);
        fun.setCreatedDate(LocalDateTime.now());
        fun.setRequestIp(ip);

        wcfunrepo.save(fun);

        for (int i = 0; i < officename.length; i++) 
        {
            if (officename[i] == null || officename[i].trim().isEmpty())
                continue;

            PprWcdcFunctionaryWorkExperience exp =new PprWcdcFunctionaryWorkExperience();

            exp.setFunctionary(fun);
            exp.setOfficeName(officename[i]);
            exp.setAddress(address[i]);
            exp.setWorkExpYrs(yr[i]);
            exp.setWorkExpDays(day[i]);
            exp.setWorkDetails(workdetail[i]);
            exp.setCreatedBy(userid);
            exp.setCreatedDate(LocalDateTime.now());
            exp.setRequestIp(ip);
            wcExpRepo.save(exp);
        }
	}
	
	@Transactional
    public void completeRecord(Integer id) {

		PprWcdcFunctionary fun = wcfunrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        fun.setStatus('C');

        wcfunrepo.save(fun);
    }

	@Override
	public void UpdateWCDCFunctionary(Integer pprwcdcFunId, Integer district, String fname, String lname,
			Integer designation, Integer qualification, String workallocation, BigDecimal slr, BigDecimal slnr,
			BigDecimal dlr, BigDecimal dlnr, String[] officename, String[] address, Integer[] yr, Integer[] day,
			String[] workdetail, String status, String userid, String ip) {
		
		
		try {
			
			PprWcdcFunctionary fun= wcfunrepo.findById(pprwcdcFunId).get();
			
			Integer pprwcId =0;
	    	List<Object[]> list = repository.getPPR4BWCDCList(district);
			for (Object[] row : list) {

				pprwcId = (Integer) row[0];
			}
			
			PPRWcdcDetails wcd = repository.getReferenceById(pprwcId);
			Designation des=desrep.getReferenceById(designation);
			Qualification qa=quarep.getReferenceById(qualification);
			
			fun.setWcdcDetails(wcd);
	        fun.setFirstName(fname);
	        fun.setLastName(lname);
	        fun.setDesignation(des);
	        fun.setQualification(qa);
	        fun.setWorkAllocation(workallocation);
	        fun.setTotalBudgetRecurring(slr);
	        fun.setTotalBudgetNonRecurring(slnr);
	        fun.setDolrFundRecurring(dlr);
	        fun.setDolrFundNonRecurring(dlnr);
	        fun.setStatus(status.charAt(0));
	        fun.setUpdatedBy(userid);
	        fun.setUpdatedDate(LocalDate.now());
	        fun.setRequestIp(ip);

	        wcfunrepo.save(fun);
	        
	        wcExpRepo.deleteByFunctionaryPprWcdcFunId(pprwcdcFunId);
	        
	        for (int i = 0; i < officename.length; i++) 
	        {
	            if (officename[i] == null || officename[i].trim().isEmpty())
	                continue;

	            PprWcdcFunctionaryWorkExperience exp =new PprWcdcFunctionaryWorkExperience();

	            exp.setFunctionary(fun);
	            exp.setOfficeName(officename[i]);
	            exp.setAddress(address[i]);
	            exp.setWorkExpYrs(yr[i]);
	            exp.setWorkExpDays(day[i]);
	            exp.setWorkDetails(workdetail[i]);
	            exp.setCreatedBy(userid);
	            exp.setCreatedDate(LocalDateTime.now());
	            exp.setUpdatedBy(userid);
	            exp.setUpdatedDate(LocalDate.now());
	            exp.setRequestIp(ip);
	            wcExpRepo.save(exp);
	        }
	        
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
   
}