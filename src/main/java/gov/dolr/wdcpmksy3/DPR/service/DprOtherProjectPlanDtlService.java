package gov.dolr.wdcpmksy3.DPR.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.DPR.entity.DprOtherProjectPlan;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherProjectPlanDtl;
import gov.dolr.wdcpmksy3.DPR.entity.MOtherActivity;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherProjectPlanDtlRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherProjectPlanRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MOtherActivityRepository;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import jakarta.transaction.Transactional;

@Service
public class DprOtherProjectPlanDtlService {
	
	@Autowired
	private DprOtherProjectPlanRepository planRepository;
	@Autowired
    private DprOtherProjectPlanDtlRepository detailRepository;
	@Autowired
    private MPprRepository mPprRepo;
	@Autowired
    private MOtherActivityRepository mOtherActivityRepo;

    public DprOtherProjectPlanDtlService(DprOtherProjectPlanRepository planRepository, DprOtherProjectPlanDtlRepository detailRepository,
            MPprRepository mPprRepo, MOtherActivityRepository mOtherActivityRepo) {
        this.planRepository = planRepository;
        this.detailRepository = detailRepository;
        this.mPprRepo = mPprRepo;
        this.mOtherActivityRepo = mOtherActivityRepo;
    }

    @Transactional
    public void saveOtherProjectPlan(Integer pprId, Integer activityId,
            BigDecimal target, String userid, String requestIp, String action) {

        MPpr ppr = mPprRepo.findById(pprId).orElseThrow(() -> new RuntimeException("Project not found"));
        MOtherActivity activity = mOtherActivityRepo.findById(activityId).orElseThrow(() -> new RuntimeException("Activity not found"));
        LocalDateTime now = LocalDateTime.now();
        DprOtherProjectPlan plan = new DprOtherProjectPlan();
        plan.setPpr(ppr);
        plan.setStatus(action);
        plan.setCreatedby(userid);
        plan.setCreatedon(now);
        plan.setRequestIp(requestIp);

        plan = planRepository.save(plan);

        DprOtherProjectPlanDtl detail = new DprOtherProjectPlanDtl();
        detail.setOtherPlan(plan);
        detail.setOtherActivity(activity);
        detail.setQtyPlanned(target);
        detail.setCreatedby(userid);
        detail.setCreatedon(now);
        detail.setRequestIp(requestIp);

        detailRepository.save(detail);
    }

}
