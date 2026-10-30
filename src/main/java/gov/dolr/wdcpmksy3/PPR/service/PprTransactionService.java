package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.ReferBackMenuItemRequest;
import gov.dolr.wdcpmksy3.PPR.entity.CropType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MSanction;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroCrop;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroSoil;
import gov.dolr.wdcpmksy3.PPR.entity.PprReferbackMenu;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.entity.SoilType;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MSanctionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprReferbackMenuRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.MenuRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class PprTransactionService {
	
	@Autowired
	private PprTransactionRepository pprTransactionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MPprRepository mPprRepo;
	
	@Autowired
	private MSanctionRepository msanrepo;
	
    @Autowired
    private PprReferbackMenuRepository pprReferbackMenuRepo;

    @Autowired
    private MenuRepository submenuRepository;

	
	public void rejectSlnaReqFromDolr(Integer tranId, String remarks, Integer regId) {
		PprTransaction rejPprTran = pprTransactionRepo.getById(tranId);
		
		MPpr mp= mPprRepo.getReferenceById(rejPprTran.getPpr().getPprId());
		WdcpmksyUserReg regfrm=userRepo.getReferenceById(regId.longValue());
		WdcpmksyUserReg regto=userRepo.getReferenceById(regId.longValue());
		
		PprTransaction pprTran = new PprTransaction();
		
		pprTran.setPpr(mp);
		pprTran.setAction('R');
		pprTran.setRemarks(remarks);
		pprTran.setSentFrom(regfrm);
		pprTran.setSentTo(regto);
		pprTran.setSenton(LocalDateTime.now());
		
		pprTransactionRepo.save(pprTran);
	}
	

@Transactional
public void referBackSlnaReqFromDolr(
        Integer tranId,
        List<ReferBackMenuItemRequest> items,
        Integer regId,
        String userid,
        String requestIp) {

    if (items == null || items.isEmpty()) {
        throw new IllegalArgumentException(
                "Please select at least one submenu.");
    }

    PprTransaction originalTran = pprTransactionRepo.findById(tranId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Invalid transaction ID."));

    MPpr mp = mPprRepo.getReferenceById(
            originalTran.getPpr().getPprId());
    
    Integer lastRound =
            pprReferbackMenuRepo.getLastReferbackRound(mp.getPprId());

    Integer currentRound = lastRound + 1;

    WdcpmksyUserReg regfrm =
            userRepo.getReferenceById(regId.longValue());

    WdcpmksyUserReg regto =
            userRepo.getReferenceById(
                    originalTran.getSentFrom().getRegId().longValue());

    // Validate all selected items before saving anything.
    java.util.Set<Integer> selectedIds = new java.util.HashSet<>();

    for (ReferBackMenuItemRequest item : items) {

        if (item.getSubmenuId() == null ||
                item.getRemark() == null ||
                item.getRemark().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Every selected submenu must have remarks.");
        }

        if (item.getRemark().trim().length() > 1000) {
            throw new IllegalArgumentException(
                    "Remarks cannot exceed 1000 characters.");
        }

        if (!selectedIds.add(item.getSubmenuId())) {
            throw new IllegalArgumentException(
                    "Duplicate submenu selection is not allowed.");
        }
    }

    // Create the Refer Back transaction.
    PprTransaction pprTran = new PprTransaction();
    
    
    pprTran.setPpr(mp);
    pprTran.setAction('B');

    mp.setStatus("B");
    mp.setUpdatedBy(requestIp);
    mp.setUpdatedDate(new Date());
    mPprRepo.save(mp);
    
    // Option A: no common remarks in ppr_transaction.
    pprTran.setRemarks(null);

    pprTran.setSentFrom(regfrm);
    pprTran.setSentTo(regto);
    pprTran.setSenton(LocalDateTime.now());

    pprTransactionRepo.save(pprTran);

    // Save one row for each selected submenu.
    List<PprReferbackMenu> referBackList = new ArrayList<>();

    for (ReferBackMenuItemRequest item : items) {

        WdcpmksySubmenu submenu =
                submenuRepository.findById(item.getSubmenuId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid submenu selected."));

        if (!Boolean.TRUE.equals(submenu.getIsActive())) {
            throw new IllegalArgumentException(
                    "Selected submenu is inactive.");
        }

        PprReferbackMenu record = new PprReferbackMenu();

        record.setPpr(mp);
        record.setSubmenu(submenu);
        record.setReferbackRound(currentRound);
        record.setSubmenuName(submenu.getSubmenuName());
        record.setTarget(submenu.getTarget());
        record.setRemark(item.getRemark().trim());
        record.setCreatedBy(userid);
        record.setCreatedDate(LocalDate.now());
        record.setRequestIp(requestIp);

        referBackList.add(record);
    }

    pprReferbackMenuRepo.saveAll(referBackList);
}

	
	public void approveSlnaReqFromDolr1(Integer tranId, Integer regId) {
		PprTransaction rejPprTran = pprTransactionRepo.getById(tranId);
		
		MPpr mp= mPprRepo.getReferenceById(rejPprTran.getPpr().getPprId());
		WdcpmksyUserReg regfrm=userRepo.getReferenceById(regId.longValue());
		WdcpmksyUserReg regto=userRepo.getReferenceById(regId.longValue());
		
		PprTransaction pprTran = new PprTransaction();
		
		pprTran.setPpr(mp);
		pprTran.setAction('A');
		pprTran.setSentFrom(regfrm);
		pprTran.setSentTo(regto);
		pprTran.setSenton(LocalDateTime.now());
		
		pprTransactionRepo.save(pprTran);
		
		
	}

	@Transactional
	public boolean approveSlnaReqFromDolr(Integer pprId, BigDecimal areap, BigDecimal cost, BigDecimal central, BigDecimal state,
	        Integer regid, String userid, String ip, LocalDate sanctiondt) {

	    try {

	        PprTransaction rejPprTran = pprTransactionRepo.findById(pprId).orElseThrow(() -> new RuntimeException("Transaction not found"));
	        Integer actualPprId = rejPprTran.getPpr().getPprId();
	        MPpr mp = mPprRepo.findById(actualPprId).orElseThrow(() -> new RuntimeException("PPR not found"));
	        WdcpmksyUserReg regfrm = userRepo.findById(regid.longValue()).orElseThrow(() -> new RuntimeException("User not found"));
	        WdcpmksyUserReg regto = userRepo.findById(regid.longValue()).orElseThrow(() -> new RuntimeException("User not found"));

	        // 5. Delete existing sanction using actual PPR ID
	        int deleted = msanrepo.deleteByPprId(actualPprId);
	        MSanction san = new MSanction();

	        san.setPpr(mp);
	        san.setSanctionDate(sanctiondt);
	        san.setAreaProposed(areap);
	        san.setTotCost(cost);
	        san.setCentralShareAmt(central);
	        san.setStateShareAmt(state);
	        san.setCreatedBy(userid);
	        san.setRequestIp(ip);
	        msanrepo.saveAndFlush(san);

	        PprTransaction pprTran = new PprTransaction();
	        pprTran.setPpr(mp);
	        pprTran.setAction('A');
	        pprTran.setSentFrom(regfrm);
	        pprTran.setSentTo(regto);
	        pprTran.setSenton(LocalDateTime.now());
	        pprTransactionRepo.save(pprTran);

	        mp.setStatus("A");
	        mp.setUpdatedDate(new Date());
	        mPprRepo.save(mp);

	        return true;

	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        return false;
	    }
	}

}
