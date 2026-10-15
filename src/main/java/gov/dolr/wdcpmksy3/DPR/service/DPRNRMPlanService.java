package gov.dolr.wdcpmksy3.DPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprNrmDtl;
import gov.dolr.wdcpmksy3.DPR.entity.MDprHead;
import gov.dolr.wdcpmksy3.DPR.entity.MPhyActivity;
import gov.dolr.wdcpmksy3.DPR.repository.DprDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprNrmDtlRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MDprHeadRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyActivityRepository;
import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodActivity;
import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprExistingLivelihoodActivity;
import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectLivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;


@Service
public class DPRNRMPlanService {
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
    private DprDetailsRepository planrepo;
	
	@Autowired
    private MPhyActivityRepository mactrepo;
	
	@Autowired
    private DprNrmDtlRepository plndtlrepo;
	
	@Autowired
    private MDprHeadRepository mdprheadrepo;
	
	public boolean saveDPRNRMTarget(Integer district, Integer project, Integer head, Integer activity, BigDecimal target, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MPpr mp= mprep.getReferenceById(project);
			MDprHead dh=mdprheadrepo.getReferenceById(2);
			DprDetails pln = planrepo.findByPprPprId(project).orElse(null);
			
			if (pln == null) {

			    pln = new DprDetails();
			    pln.setHeadType(dh);
			    pln.setPpr(mp);
			    pln.setStatus('D');
			    pln.setCreatedby(userid);
			    pln.setCreatedon(LocalDateTime.now());
			    pln.setRequestIp(ip);
			    planrepo.save(pln);
			}
			
			MPhyActivity act = mactrepo.findById(activity).orElseThrow();
			DprNrmDtl plndtl = new DprNrmDtl();
                
			plndtl.setDpr(pln);
			plndtl.setActivity(act);
			plndtl.setQtyPlanned(target);
			plndtl.setStatus('D');
			plndtl.setCreatedBy(userid);
			plndtl.setCreatedOn(LocalDateTime.now());
			plndtl.setRequestIp(ip);
            plndtlrepo.save(plndtl);
			
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

		DprNrmDtl fun = plndtlrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        fun.setStatus('C');

        plndtlrepo.save(fun);
    }
	
	public boolean editDPRNRMTarget(Integer nrmid, BigDecimal target1, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			DprNrmDtl fun = plndtlrepo.findById(nrmid)
	                .orElseThrow(() -> new RuntimeException("Record not found"));

	        fun.setQtyPlanned(target1);
	        fun.setUpdatedBy(userid);
	        fun.setUpdatedOn(LocalDateTime.now());
	        plndtlrepo.save(fun);
			
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;	
		
	}

}
