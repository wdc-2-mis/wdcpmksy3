package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.PprRequestDolrApprovalDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprProposedProjectRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.PPR.service.PprTransactionService;
import gov.dolr.wdcpmksy3.repository.ProfileProjection;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprDolrApprovalController {
	
	@Autowired
	private PprTransactionRepository pprTransactionRepo;
	
	@Autowired
	private PprProposedProjectRepository pprProposedProjectRepo;
	
	@Autowired 
	private UserMapRepository userMapRepo; 
	
	@Autowired
	private PprTransactionService pprTransactionServ;
	
	@GetMapping("/pprRequestDolrApproval")
	public String pprRequestDolrApproval(@RequestParam(required = false) Integer state,
			HttpSession session, Model model) {
		String userid=(String)session.getAttribute("userid");
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		
		if(userid==null){
            return "redirect:/login";
        }
		List<ProfileProjection> stateList = userMapRepo.getMapState(regid);
		model.addAttribute("stateList",stateList);
		if(state!=null)
			model.addAttribute("selectedState", state);
		else
			state = 0;
		
		List<PprTransaction> pprTranList = pprTransactionRepo.findBySentToRegIdandStcode(regid,state);
		if(!pprTranList.isEmpty()) {
			List<PprRequestDolrApprovalDto> pprRequestDolrApprovalList = new ArrayList<>();
			for(PprTransaction tran : pprTranList) {
				PprRequestDolrApprovalDto pprProposedProject = pprProposedProjectRepo.getPprRequestDolrApprovalData(tran.getPpr());
				pprProposedProject.setTranStatus(tran.getAction());
				pprProposedProject.setTranId(tran.getTranxId());
				pprRequestDolrApprovalList.add(pprProposedProject);
			}
			model.addAttribute("userid", userid);
			model.addAttribute("pprRequestDolrApprovalList", pprRequestDolrApprovalList);
		}
		return "ppr/pprRequestDolrApproval";
	}
	
	@GetMapping("/rejPprReqForAprov")
	public String rejPprReqForAprov(@RequestParam("id") Integer tranId, @RequestParam("remarks") String remarks, 
			HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		try {
			Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
			pprTransactionServ.rejectSlnaReqFromDolr(tranId, remarks, regid);
			redirectAttributes.addFlashAttribute("success", " PPR Request Rejected Successfully.");
		}catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to Reject PPR Request.");
	    }

		return "redirect:/pprRequestDolrApproval";
	}
	
	@GetMapping("/referBackPprReqForAprov")
	public String referBackPprReqForAprov(@RequestParam("id") Integer tranId, @RequestParam("remarks") String remarks, 
			HttpSession session, Model model, RedirectAttributes redirectAttributes) {

		try {
			Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
			pprTransactionServ.referBackSlnaReqFromDolr(tranId, remarks, regid);
			redirectAttributes.addFlashAttribute("success", "PPR Request Refered Back to SLNA Successfully.");
		}catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to Refer Back PPR Request to SLNA.");
	    }

		return "redirect:/pprRequestDolrApproval";
	}
	
	@GetMapping("/approvePprReqForAprov")
	public String approvePprReqForAprov(@RequestParam("id") Integer tranId,
			HttpSession session, Model model, RedirectAttributes redirectAttributes) {

		try {
			Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
			pprTransactionServ.approveSlnaReqFromDolr(tranId, regid);
			redirectAttributes.addFlashAttribute("success", "PPR Request Approved Successfully.");
		}catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to Approve PPR Request.");
	    }

		return "redirect:/pprRequestDolrApproval";
	}

}
