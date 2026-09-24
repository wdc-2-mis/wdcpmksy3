package gov.dolr.wdcpmksy3.PPR.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.PPR.dto.PprRequestDolrApprovalDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprProposedProjectRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.service.StateService;
import jakarta.servlet.http.HttpSession;

@Controller
public class StWisePprTransactionStatusController {
	
	@Autowired
	private PprTransactionRepository pprTransactionRepo;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private PprProposedProjectRepository pprProposedProjectRepo;
	
	@GetMapping("/stWisePprTransactionStatus")
	public String stWisePprTransactionStatus(@RequestParam(required = false) Integer state,
			HttpSession session, Model model) {
		String userid=(String)session.getAttribute("userid");
		if(userid==null){
			model.addAttribute("stateList",stateService.getAllStates(1));
        }else {
        	String userType=(String)session.getAttribute("usertype");
        	if(userType.equals("DL")) {
        		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
        		model.addAttribute("stateList",stateService.findAllByRegId(regid));
        	}else {
        		String statename=session.getAttribute("statename").toString();
        		state = Integer.parseInt(session.getAttribute("stcode").toString());
        		model.addAttribute("stateOrList",true);
        		model.addAttribute("statename",statename);
        	}
    		if(state!=null)
    			model.addAttribute("selectedState", state);
    		else
    			state = 0;
    		List<PprTransaction> pprTranList = pprTransactionRepo.findPprTransactionByStCode(state);
    		if(!pprTranList.isEmpty()) {
    			List<PprRequestDolrApprovalDto> pprRequestDolrApprovalList = new ArrayList<>();
    			for(PprTransaction tran : pprTranList) {
    				PprRequestDolrApprovalDto pprProposedProject = pprProposedProjectRepo.getPprRequestDolrApprovalData(tran.getPpr());
    				pprProposedProject.setTranStatus(tran.getAction());
    				pprProposedProject.setTranId(tran.getTranxId());
    				pprRequestDolrApprovalList.add(pprProposedProject);
    			}
    			model.addAttribute("stWisePprTransactionList", pprRequestDolrApprovalList);
    		}
        }
		
		return "ppr/stWisePprTransactionStatus";
	}

}
