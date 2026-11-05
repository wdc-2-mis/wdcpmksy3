package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;
    
	public void rejectSlnaReqFromDolr(Integer tranId, String remarks, Integer regId, String requestIp) {
		PprTransaction rejPprTran = pprTransactionRepo.getById(tranId);
		
		MPpr mp= mPprRepo.getReferenceById(rejPprTran.getPpr().getPprId());
		WdcpmksyUserReg regfrm=userRepo.getReferenceById(regId.longValue());
		WdcpmksyUserReg regto=userRepo.getReferenceById(regId.longValue());
		
		PprTransaction pprTran = new PprTransaction();
		
		pprTran.setPpr(mp);
		pprTran.setAction('R');
		
		mp.setStatus("R");
	    mp.setUpdatedBy(requestIp);
	    mp.setUpdatedDate(new Date());
	    mPprRepo.save(mp);
		
		pprTran.setRemarks(remarks);
		pprTran.setSentFrom(regfrm);
		pprTran.setSentTo(regto);
		pprTran.setSenton(LocalDateTime.now());
		
		pprTransactionRepo.save(pprTran);
	}
	
	private void updateSelectedModuleStatus(
	        String tableName, Integer pprId) {

	    // Only these approved tables can be updated.
	    Set<String> allowedTables = Set.of(
	        "ppr_agro_climate",
	        "ppr_livelihood",
	        "ppr_proposed_area",
	        "ppr_drinking_water",
	        "ppr_pending_uc",
	        "ppr_wcdc_unspent_balance",
	        "ppr_proposed_project",
	        "ppr_project_glance",
	        "ppr_land_pattern_area",
	        "ppr_disaster_details",
	        "ppr_soil_erosion",
	        "ppr_employment_generation",
	        "ppr_migration_details",
	        "ppr_water_outcome",
	        "ppr_crop_outcome"
	    );

	    if (!allowedTables.contains(tableName)) {
	        throw new IllegalArgumentException(
	            "Invalid PPR module table: " + tableName);
	    }

	    String sql = "UPDATE public." + tableName
	            + " SET status = 'D'"
	            + " WHERE ppr_id = :pprId"
	            + " AND status = 'C'";

	    entityManager.createNativeQuery(sql)
	            .setParameter("pprId", pprId)
	            .executeUpdate();
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

	    Integer pprId = mp.getPprId();

	    Integer lastRound =
	            pprReferbackMenuRepo.getLastReferbackRound(pprId);

	    Integer currentRound = lastRound + 1;

	    WdcpmksyUserReg regfrm =
	            userRepo.getReferenceById(regId.longValue());

	    WdcpmksyUserReg regto =
	            userRepo.getReferenceById(
	                    originalTran.getSentFrom().getRegId().longValue());

	    // Store validated submenu records.
	    Map<Integer, WdcpmksySubmenu> selectedSubmenus =
	            new HashMap<>();

	    Set<Integer> selectedIds = new HashSet<>();

	    // Step 1: Validate all selected submenu records.
	    for (ReferBackMenuItemRequest item : items) {

	        if (item.getSubmenuId() == null
	                || item.getRemark() == null
	                || item.getRemark().trim().isEmpty()) {

	            throw new IllegalArgumentException(
	                    "Every selected submenu must have remarks.");
	        }

	        String remark = item.getRemark().trim();

	        if (remark.length() < 10) {
	            throw new IllegalArgumentException(
	                    "Remarks must contain at least 10 characters.");
	        }

	        if (remark.length() > 1000) {
	            throw new IllegalArgumentException(
	                    "Remarks cannot exceed 1000 characters.");
	        }

	        if (!selectedIds.add(item.getSubmenuId())) {
	            throw new IllegalArgumentException(
	                    "Duplicate submenu selection is not allowed.");
	        }

	        WdcpmksySubmenu submenu =
	                submenuRepository.findById(item.getSubmenuId())
	                        .orElseThrow(() ->
	                                new IllegalArgumentException(
	                                        "Invalid submenu selected."));

	        if (!Boolean.TRUE.equals(submenu.getIsActive())) {
	            throw new IllegalArgumentException(
	                    "Selected submenu is inactive.");
	        }

	        if (submenu.getPprTableName() == null
	                || submenu.getPprTableName().trim().isEmpty()) {

	            throw new IllegalArgumentException(
	                    "PPR table mapping is missing for submenu: "
	                            + submenu.getSubmenuName());
	        }

	        selectedSubmenus.put(item.getSubmenuId(), submenu);
	    }

	    // Step 2: Create Refer Back transaction.
	    PprTransaction pprTran = new PprTransaction();

	    pprTran.setPpr(mp);
	    pprTran.setAction('B');

	    // Main PPR workflow status.
	    mp.setStatus("B");
	    mp.setUpdatedBy(requestIp);
	    mp.setUpdatedDate(new Date());

	    mPprRepo.save(mp);

	    // No common remarks. Individual remarks are stored below.
	    pprTran.setRemarks(null);

	    pprTran.setSentFrom(regfrm);
	    pprTran.setSentTo(regto);
	    pprTran.setSenton(LocalDateTime.now());

	    pprTransactionRepo.save(pprTran);

	    // Step 3: Update status of selected PPR module tables.
	    // Avoid updating the same table more than once.
	    Set<String> updatedTables = new HashSet<>();

	    for (WdcpmksySubmenu submenu : selectedSubmenus.values()) {

	        String tableName = submenu.getPprTableName().trim();

	        // m_ppr has the main Refer Back workflow status 'B'.
	        // Do not overwrite it with 'D'.
	        if ("m_ppr".equals(tableName)) {
	            continue;
	        }

	        if (updatedTables.add(tableName)) {

	            updateSelectedModuleStatus(tableName, pprId);
	        }
	    }

	    // Step 4: Save individual submenu remarks.
	    List<PprReferbackMenu> referBackList = new ArrayList<>();

	    for (ReferBackMenuItemRequest item : items) {

	        WdcpmksySubmenu submenu =
	                selectedSubmenus.get(item.getSubmenuId());

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
