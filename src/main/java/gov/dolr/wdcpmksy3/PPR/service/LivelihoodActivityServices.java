package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodActivity;
import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprExistingLivelihoodActivity;
import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectLivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.repository.LivelihoodActivityRepository;
import gov.dolr.wdcpmksy3.PPR.repository.LivelihoodInterventionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprExistingLivelihoodActivityRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprLivelihoodRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProjectLivelihoodInterventionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.MVillage;

@Service
public class LivelihoodActivityServices {
	
	@Autowired
	private LivelihoodActivityRepository repo;
	
	@Autowired
	private LivelihoodInterventionRepository repoinv;
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
    private VillageRepository villrepo;
	
	@Autowired
    private MicroWatershedRepository mipero;
	
	@Autowired
    private PprLivelihoodRepository livrepo;
	
	@Autowired
    private PprExistingLivelihoodActivityRepository pprexist;
	
	@Autowired
    private PprProjectLivelihoodInterventionRepository pprinvrepo;
	
	public List<LivelihoodActivity> getAllLivelihoodActivity(){
		List<LivelihoodActivity> list = repo.findAll();
		return list;
	}
	
	
	public boolean saveLivelihoodSummaryPPR13(Integer block, Integer project, Integer village, List<Integer> livact, List<Integer> livinv, 
			Integer watershed, Integer migrat, String reason, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MicroWatershed micro=mipero.getReferenceById(watershed);
			MPpr mp= mprep.getReferenceById(project);
			MVillage v= villrepo.getReferenceById(village);
			
			PprLivelihood li=new PprLivelihood();
			
			li.setPpr(mp);
			li.setMicroWatershed(micro);
			li.setVillage(v);
			li.setMigratedPeople(migrat);
			li.setMigrationReason(reason);
			li.setStatus(action.charAt(0));
			li.setCreatedBy(userid);
			li.setCreatedDate(LocalDateTime.now());
			li.setRequestIp(ip);
			livrepo.save(li);
			
			for (Integer livactid : livact) 
			{
				LivelihoodActivity lia = repo.findById(livactid).orElseThrow();
                PprExistingLivelihoodActivity pela = new PprExistingLivelihoodActivity();
                
                pela.setPprLivelihood(li);
                pela.setLivelihoodActivity(lia);
                pela.setCreatedBy(userid);
                pela.setCreatedDate(LocalDateTime.now());
                pela.setRequestIp(ip);
                
                pprexist.save(pela);
                
            }
			
			for(Integer livinvid : livinv) 
			{
				LivelihoodIntervention linv= repoinv.findById(livinvid).orElseThrow();
				PprProjectLivelihoodIntervention plivinv= new PprProjectLivelihoodIntervention();
				
				plivinv.setPprLivelihood(li);
				plivinv.setLivelihoodIntervention(linv);
				plivinv.setCreatedBy(userid);
				plivinv.setCreatedDate(LocalDateTime.now());
				plivinv.setRequestIp(ip);
				
				pprinvrepo.save(plivinv);
			}
			
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;	
		
	}
	
	@Transactional
    public void completeRecord(Integer id) {

		PprLivelihood fun = livrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        fun.setStatus('C');

        livrepo.save(fun);
    }
	
	
	public boolean editLivelihoodSummaryPPR13(Integer livsamid, Integer village, List<Integer> livact, List<Integer> livinv, 
			Integer watershed, Integer migrat, String reason, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MicroWatershed micro=mipero.getReferenceById(watershed);
			MVillage v= villrepo.getReferenceById(village);
			
			PprLivelihood li=livrepo.findById(livsamid).get();
			
			li.setMicroWatershed(micro);
			li.setVillage(v);
			li.setMigratedPeople(migrat);
			li.setMigrationReason(reason);
			li.setUpdatedBy(userid);
			li.setUpdatedDate(LocalDate.now());
			li.setRequestIp(ip);
			
			livrepo.save(li);
			
			pprexist.deleteByPprLivelihoodPprLivelihoodId(livsamid);
			for (Integer livactid : livact) 
			{
				LivelihoodActivity lia = repo.findById(livactid).orElseThrow();
                PprExistingLivelihoodActivity pela = new PprExistingLivelihoodActivity();
                
                pela.setPprLivelihood(li);
                pela.setLivelihoodActivity(lia);
                pela.setCreatedBy(userid);
                pela.setCreatedDate(LocalDateTime.now());
                pela.setUpdatedBy(userid);
                pela.setUpdatedDate(LocalDate.now());
                pela.setRequestIp(ip);
                
                pprexist.save(pela);
                
            }
			pprinvrepo.deleteByPprLivelihoodPprLivelihoodId(livsamid);
			for(Integer livinvid : livinv) 
			{
				LivelihoodIntervention linv= repoinv.findById(livinvid).orElseThrow();
				PprProjectLivelihoodIntervention plivinv= new PprProjectLivelihoodIntervention();
				
				plivinv.setPprLivelihood(li);
				plivinv.setLivelihoodIntervention(linv);
				plivinv.setCreatedBy(userid);
				plivinv.setCreatedDate(LocalDateTime.now());
				plivinv.setUpdatedBy(userid);
				plivinv.setUpdatedDate(LocalDate.now());
				plivinv.setRequestIp(ip);
				
				pprinvrepo.save(plivinv);
			}
			
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;	
		
	}


}
