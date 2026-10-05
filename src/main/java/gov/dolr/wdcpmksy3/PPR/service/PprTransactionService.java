package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.CropType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MSanction;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroCrop;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroSoil;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.entity.SoilType;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MSanctionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import jakarta.transaction.Transactional;

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
	
	public void referBackSlnaReqFromDolr(Integer tranId, String remarks, Integer regId) {
		PprTransaction rejPprTran = pprTransactionRepo.getById(tranId);
		
		MPpr mp= mPprRepo.getReferenceById(rejPprTran.getPpr().getPprId());
		WdcpmksyUserReg regfrm=userRepo.getReferenceById(regId.longValue());
		WdcpmksyUserReg regto=userRepo.getReferenceById(rejPprTran.getSentFrom().getRegId().longValue());
		
		PprTransaction pprTran = new PprTransaction();
		
		pprTran.setPpr(mp);
		pprTran.setAction('B');
		pprTran.setRemarks(remarks);
		pprTran.setSentFrom(regfrm);
		pprTran.setSentTo(regto);
		pprTran.setSenton(LocalDateTime.now());
		
		pprTransactionRepo.save(pprTran);
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
	
	public boolean approveSlnaReqFromDolr(Integer pprId, BigDecimal areap, BigDecimal cost, BigDecimal central,
			BigDecimal state, Integer regid, String userid, String ip, LocalDate sanctiondt) {
		
		boolean status=false;
		
		try {
			
			PprTransaction rejPprTran = pprTransactionRepo.getById(pprId);
			MPpr mp= mPprRepo.getReferenceById(rejPprTran.getPpr().getPprId());
			WdcpmksyUserReg regfrm=userRepo.getReferenceById(regid.longValue());
			WdcpmksyUserReg regto=userRepo.getReferenceById(regid.longValue());
			
			
			MSanction san=new MSanction();
			
			san.setPpr(mp);
			san.setSanctionDate(sanctiondt);
			san.setAreaProposed(areap);
			san.setTotCost(cost);
			san.setCentralShareAmt(central);
			san.setStateShareAmt(state);
			san.setCreatedBy(userid);
			san.setRequestIp(ip);
			msanrepo.save(san);
			
			PprTransaction pprTran = new PprTransaction();
			
			pprTran.setPpr(mp);
			pprTran.setAction('A');
			pprTran.setSentFrom(regfrm);
			pprTran.setSentTo(regto);
			pprTran.setSenton(LocalDateTime.now());
			
			pprTransactionRepo.save(pprTran);
			
			status=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			status=false;
		}
		return status;
	}

}
