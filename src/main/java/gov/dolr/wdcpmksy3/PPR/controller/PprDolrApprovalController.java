package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.ArrayList;
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
	private PprTransactionRepository pprTransactionRepo;
	
	@Autowired
	private PprProposedProjectRepository pprProposedProjectRepo;
	
	@GetMapping("/pprRequestDolrApproval")
	public String pprRequestDolrApproval(HttpSession session, Model model) {
		String userid=(String)session.getAttribute("userid");
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		if(userid==null){
            return "redirect:/login";
        }
		Integer pprId = 0;
		List<PprTransaction> pprTranList = pprTransactionRepo.findBySentToRegId(regid);
		if(!pprTranList.isEmpty()) {
			List<PprRequestDolrApprovalDto> pprRequestDolrApprovalList = new ArrayList<>();
			for(PprTransaction tran : pprTranList) {
				PprRequestDolrApprovalDto pprProposedProject = pprProposedProjectRepo.getPprRequestDolrApprovalData(tran.getPpr());
				pprRequestDolrApprovalList.add(pprProposedProject);
				pprId = tran.getPpr().getPprId();
			}
			model.addAttribute("userid", userid);
			model.addAttribute("pprId", pprId);
			model.addAttribute("pprRequestDolrApprovalList", pprRequestDolrApprovalList);
		}
		return "ppr/pprRequestDolrApproval";
	}

}
