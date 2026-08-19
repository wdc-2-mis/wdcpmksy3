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
import gov.dolr.wdcpmksy3.PPR.entity.PprPendingUc;
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWcdcUnspentBalanceRepository;

@Service
public class UnspentBalanceServices {
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
    private PprWcdcUnspentBalanceRepository repo;
	
	public boolean saveUnspentBalancePPR20(Integer project, BigDecimal totcost, BigDecimal state, BigDecimal dolr, BigDecimal interest, 
			BigDecimal total, BigDecimal balance, String action, String userid, String ip) {
		
		boolean st=false;
		try {
			
			MPpr mp= mprep.getReferenceById(project);
			
			PprWcdcUnspentBalance ub=new PprWcdcUnspentBalance();
			
			ub.setPpr(mp);
			ub.setTotCost(totcost);
			ub.setStReleasedFund(state);
			ub.setDolrReleasedFund(dolr);
			ub.setInterest(interest);
			ub.setTotal(total);
			ub.setUnspendBalance(balance);
			ub.setStatus(action.charAt(0));
			ub.setCreatedBy(userid);
			ub.setCreatedDate(LocalDateTime.now());
			ub.setRequestIp(ip);
			repo.save(ub);
			
			st=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			st=false;
		}
		return st;
		
	}
	
	@Transactional
    public void completeRecord(Integer id) {

		PprWcdcUnspentBalance ub = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

		ub.setStatus('C');

        repo.save(ub);
    }
	
	public boolean editUnspentBalancePPR20(Integer pprUnspentBalanceId, BigDecimal totcost, BigDecimal state, BigDecimal dolr, BigDecimal interest, 
			BigDecimal total, BigDecimal balance, String action, String userid, String ip) {
		
		boolean st=false;
		try {
			
			PprWcdcUnspentBalance ub=repo.findById(pprUnspentBalanceId).get();
			
			ub.setTotCost(totcost);
			ub.setStReleasedFund(state);
			ub.setDolrReleasedFund(dolr);
			ub.setInterest(interest);
			ub.setTotal(total);
			ub.setUnspendBalance(balance);
			ub.setUpdatedBy(userid);
			ub.setUpdatedDate(LocalDate.now());
			ub.setRequestIp(ip);
			repo.save(ub);
			
			st=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			st=false;
		}
		return st;
		
	}

}
