package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
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
	
	public void approveSlnaReqFromDolr(Integer tranId, Integer regId) {
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

}
