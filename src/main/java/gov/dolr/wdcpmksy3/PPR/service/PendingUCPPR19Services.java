package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.MFinYear;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprPendingUc;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprPendingUcRepository;
import gov.dolr.wdcpmksy3.PPR.repository.WdcpmksyMFinYearRepository;

@Service
public class PendingUCPPR19Services {
	
	@Autowired
	private WdcpmksyMFinYearRepository finrepo;
	
	@Autowired
    private PprPendingUcRepository ucrepo;
	
	@Autowired
    private MPprRepository mprep;
	
	public boolean savePendingUCPPR19(Integer project, Integer fyear, BigInteger installment, BigDecimal released, BigDecimal utilized, LocalDate dueDate, BigDecimal ucamount, 
			LocalDate ucDate, BigDecimal ducamount, String reasion, LocalDate fromDate, LocalDate toDate, BigDecimal pucamount, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MPpr mp= mprep.getReferenceById(project);
			MFinYear fn=finrepo.getReferenceById(fyear);
			
			PprPendingUc uc=new PprPendingUc();
			
			uc.setFinYear(fn);
			uc.setPpr(mp);
			uc.setInstallmentNo(installment);
			uc.setReleasedAmount(released);
			uc.setUtilizedAmount(utilized);
			uc.setDueDate(dueDate);
			uc.setUcAmount(ucamount);
			uc.setUcSubmissionDate(ucDate);
			uc.setUcSubmissionAmt(ducamount);
			uc.setReasonNotSubmitted(reasion);
			uc.setPendingStart(fromDate);
			uc.setPendingEnd(toDate);
			uc.setPendingAmount(pucamount);
			uc.setStatus(action.charAt(0));
			uc.setCreatedBy(userid);
			uc.setCreatedDate(LocalDateTime.now());
			uc.setRequestIp(ip);
			ucrepo.save(uc);
			
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

		PprPendingUc fun = ucrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        fun.setStatus('C');

        ucrepo.save(fun);
    }
	
	public boolean editPendingUCPPR19(Integer pprPendingUcId, BigInteger installment, BigDecimal released, BigDecimal utilized, LocalDate dueDate, BigDecimal ucamount, 
			LocalDate ucDate, BigDecimal ducamount, String reasion, LocalDate fromDate, LocalDate toDate, BigDecimal pucamount, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			PprPendingUc uc=ucrepo.findById(pprPendingUcId).get();
			
			uc.setInstallmentNo(installment);
			uc.setReleasedAmount(released);
			uc.setUtilizedAmount(utilized);
			uc.setDueDate(dueDate);
			uc.setUcAmount(ucamount);
			uc.setUcSubmissionDate(ucDate);
			uc.setUcSubmissionAmt(ducamount);
			uc.setReasonNotSubmitted(reasion);
			uc.setPendingStart(fromDate);
			uc.setPendingEnd(toDate);
			uc.setPendingAmount(pucamount);
			uc.setStatus(action.charAt(0));
			uc.setUpdatedBy(userid);
			uc.setUpdatedDate(LocalDate.now());
			uc.setRequestIp(ip);
			ucrepo.save(uc);
			
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;
		
	}

}
