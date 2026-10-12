package gov.dolr.wdcpmksy3.DPR.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.DPR.entity.DprNrmProjectPlan;
import gov.dolr.wdcpmksy3.DPR.entity.DprNrmProjectPlanDtl;
import gov.dolr.wdcpmksy3.DPR.entity.MPhyActivity;
import gov.dolr.wdcpmksy3.DPR.repository.DprNrmProjectPlanDtlRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprNrmProjectPlanRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyActivityRepository;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;


@Service
public class DPRNRMPlanService {
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
    private DprNrmProjectPlanRepository planrepo;
	
	@Autowired
    private MPhyActivityRepository mactrepo;
	
	@Autowired
    private DprNrmProjectPlanDtlRepository plndtlrepo;
	
	public boolean saveDPRNRMTarget(Integer district, Integer project, Integer head, Integer activity, BigDecimal target, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MPpr mp= mprep.getReferenceById(project);
			
			DprNrmProjectPlan pln = planrepo.findByPprPprId(project).orElse(null);
			
			if (pln == null) {

			    pln = new DprNrmProjectPlan();
			    pln.setPpr(mp);
			    pln.setStatus('D');
			    pln.setCreatedBy(userid);
			    pln.setCreatedOn(LocalDateTime.now());
			    pln.setRequestIp(ip);
			    planrepo.save(pln);
			}
			
			MPhyActivity act = mactrepo.findById(activity).orElseThrow();
			DprNrmProjectPlanDtl plndtl = new DprNrmProjectPlanDtl();
                
			plndtl.setPlan(pln);;
			plndtl.setActivity(act);
			plndtl.setQtyPlanned(target);
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

}
