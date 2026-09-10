package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gov.dolr.wdcpmksy3.PPR.dto.PprRequestDolrApprovalDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprProposedProjectRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.PPR.service.PprProposedProjectService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprDolrApprovalController {
	
	@Autowired
	PprTransactionRepository pprTransactionRepo;
	
	@Autowired
	private PprProposedProjectRepository pprProposedProjectRepo;
	
	@GetMapping("/pprRequestDolrApproval")
	public String pprRequestDolrApproval(HttpSession session, Model model) {
		String userid=(String)session.getAttribute("userid");
		Integer regid = Integer.parseInt(session.getAttribute("reg_id").toString());
		if(userid==null){
            return "redirect:/login";
        }
		
		List<PprTransaction> pprTranList = pprTransactionRepo.findBySentToRegId(regid);
		if(!pprTranList.isEmpty()) {
			for(PprTransaction tran : pprTranList) {
				PprRequestDolrApprovalDto pprProposedProject = pprProposedProjectRepo.getPprRequestDolrApprovalData(tran.getPpr());
				model.addAttribute("userid", userid);
				model.addAttribute("pprId", tran.getPpr().getPprId());
				model.addAttribute("pprRequestDolrApprovalList", pprProposedProject);
			}
		}
		
		
		return "ppr/pprRequestDolrApproval";
	}

}
