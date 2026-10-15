package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.repository.MOtherActivityRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MOtherHeadsRepository;
import gov.dolr.wdcpmksy3.DPR.service.DprOtherDtlService;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DprPhyOtherHeadActController {
	
	@Autowired
	private MPprRepository mPprRepo;
	
	@Autowired
	private PprTransactionRepository pprTransactionRepo;
	
	@Autowired
	private MOtherHeadsRepository mOtherHeadsRepo;
	
	@Autowired 
	private MOtherActivityRepository mOtherActivityRepo;
	
	@Autowired
	private DprOtherDtlService otherProjectPlanDtlService;
	
	@GetMapping("/dprPhyOtherHeadActivities")
    public String dprPhyOtherHeadActivities(@RequestParam(required = false) Integer dcode, HttpSession session, 
    		Model model, @RequestParam(required = false) Integer project, @RequestParam(required = false) Integer head) {
		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    
		String userid=(String)session.getAttribute("userid");
		if(userid==null){
            return "redirect:/login";
        }
		if(dcode != null){
			model.addAttribute("selectedDistrict", dcode);
	        List<MPpr> pprList = pprTransactionRepo.findPprByDcodeAndActionA(dcode);
	        if(!pprList.isEmpty()){
	        	model.addAttribute("pprList", pprList);
	        	if(project != null) {
	        		model.addAttribute("pprId", project);
	        		model.addAttribute("headList", mOtherHeadsRepo.findAllByOrderBySeqNoAsc());
	        	}
	        }
	    }
		if(head != null) {
			model.addAttribute("selectedHead", head);
			model.addAttribute("activityList", mOtherActivityRepo.findByOtherHeads_OtherHeadsIdOrderBySeqNoAsc(head));
		}
			
		model.addAttribute("districtList", pprTransactionRepo.findDistrictsByStCodeAndActionA(stcode));
		model.addAttribute("state", statename);
//		model.addAttribute("pprProjectAtGlanceList", pprProjectGlanceRepo.findPprProjectGlanceBystCode(stcode));
		
		return "dpr/dprPhyOtherHeadActivities";
	}
	
	@GetMapping("/getUnitDescByOtherActivity")
	@ResponseBody
	public ResponseEntity<String> getUnitDescByOtherActivity(@RequestParam("actId") Integer actId) {
	    String unitDesc = mOtherActivityRepo.findUnitDescByOtherActivityId(actId);
	    if (unitDesc == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(unitDesc);
	}

	@PostMapping("/saveDPROtherHeadActivities")
	public String saveDprOtherHeadActivities(@RequestParam("project") Integer pprId,
	        @RequestParam("head") Integer headId,
	        @RequestParam("activity") Integer activityId,
	        @RequestParam("target") BigDecimal target,
	        @RequestParam("action") String action,
	        HttpSession session,
	        HttpServletRequest request,
	        RedirectAttributes redirectAttributes) {
	    String userid = (String) session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }
	    if (pprId == null || headId == null || activityId == null || target == null || target.compareTo(BigDecimal.ZERO) < 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Please enter valid project, head, activity and target details.");
	        return "redirect:/dprPhyOtherHeadActivities";
	    }
	    try {
	    	otherProjectPlanDtlService.saveOtherProjectPlan(pprId, activityId, target, userid, request.getRemoteAddr(), action);
	        redirectAttributes.addFlashAttribute("success", "Other project plan saved successfully.");
	    } catch (RuntimeException e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to save the other project plan.");
	    }

	    return "redirect:/dprPhyOtherHeadActivities";
	}

}
