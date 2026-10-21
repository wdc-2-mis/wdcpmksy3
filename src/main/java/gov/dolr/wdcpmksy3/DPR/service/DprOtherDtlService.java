package gov.dolr.wdcpmksy3.DPR.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprNrmDtl;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherDtl;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherProjectPlan;
import gov.dolr.wdcpmksy3.DPR.entity.MDprHead;
import gov.dolr.wdcpmksy3.DPR.entity.MOtherActivity;
import gov.dolr.wdcpmksy3.DPR.repository.DprDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherDtlRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherProjectPlanRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MDprHeadRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MOtherActivityRepository;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import jakarta.transaction.Transactional;

@Service
public class DprOtherDtlService {
	
	@Autowired
	private DprOtherProjectPlanRepository planRepository;
	@Autowired
    private DprOtherDtlRepository detailRepository;
	@Autowired
    private MPprRepository mPprRepo;
	@Autowired
    private MOtherActivityRepository mOtherActivityRepo;
	
	@Autowired
	private DprDetailsRepository dprDetailsRepo;
	@Autowired
	private MDprHeadRepository mDprHeadRepo;

    public DprOtherDtlService(DprOtherProjectPlanRepository planRepository, DprOtherDtlRepository detailRepository,
            MPprRepository mPprRepo, MOtherActivityRepository mOtherActivityRepo) {
        this.planRepository = planRepository;
        this.detailRepository = detailRepository;
        this.mPprRepo = mPprRepo;
        this.mOtherActivityRepo = mOtherActivityRepo;
    }

    @SuppressWarnings("null")
	@Transactional
    public void saveOtherProjectPlan(Integer pprId, Integer activityId,
            BigDecimal target, String userid, String requestIp, String action) {
    	Integer headTypeId = 3;
    	LocalDateTime now = LocalDateTime.now();
        MPpr ppr = mPprRepo.findById(pprId).orElse(null);
        DprDetails dpr = dprDetailsRepo.findByPprPprId(pprId).orElse(null);
        if(dpr==null) {
        	dpr.setPpr(ppr);
        	dpr.setStatus('D');
        	MDprHead headType = mDprHeadRepo.getById(headTypeId);
        	dpr.setHeadType(headType);
        	dpr.setCreatedby(userid);
        	dpr.setCreatedon(now);
        	dpr.setRequestIp(requestIp);
        	dpr = dprDetailsRepo.save(dpr);
        }
        MOtherActivity activity = mOtherActivityRepo.findById(activityId).orElseThrow(() -> new RuntimeException("Activity not found"));
        DprOtherDtl detail = new DprOtherDtl();
        detail.setDprDetails(dpr);
        detail.setOtherActivity(activity);
        detail.setQtyPlanned(target);
        detail.setStatus('D');
        detail.setCreatedby(userid);
        detail.setCreatedon(now);
        detail.setRequestIp(requestIp);

        detailRepository.save(detail);
    }

	public void completeDprOtherTargetData(Integer id) {
		DprOtherDtl dtl = detailRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
		dtl.setStatus('C');
		detailRepository.save(dtl);
	}

	public boolean updateDPROtherTargetRecord(Integer otherId, BigDecimal target, String userId, String clientIpAddr) {
		boolean state=false;
		try {
			DprOtherDtl dtl = detailRepository.findById(otherId).orElseThrow(() -> new RuntimeException("Record not found"));
			dtl.setQtyPlanned(target);
			dtl.setUpdatedby(userId);
			dtl.setUpdatedon(LocalDateTime.now());
			detailRepository.save(dtl);
			state=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;	
	}

	public void deleteDPROtherTarget(Integer id) {
		DprOtherDtl dtl = detailRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
		detailRepository.delete(dtl);
	}

}
