package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.entity.DprOtherDtl;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherDtlRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MOtherActivityRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MOtherHeadsRepository;
import gov.dolr.wdcpmksy3.DPR.service.DprOtherDtlService;
import gov.dolr.wdcpmksy3.PPR.dto.PprProposedProjectDto;
import gov.dolr.wdcpmksy3.PPR.entity.CriteriaDetails;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
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
	private DprOtherDtlService dprOtherDtlService;
	
	@Autowired
	private DprOtherDtlRepository dprOtherDtlRepo;
	
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
		model.addAttribute("dprOtherTargetList", dprOtherDtlRepo.findDprOtherDtlDataBystCode(stcode));
		
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
	
	@GetMapping("/checkProjectActivityExistsOther")
	@ResponseBody
	public String checkProjectActivityExistsOther(@RequestParam("pprid") Integer pprid, @RequestParam("actId") Integer actId) {

	    boolean exists =dprOtherDtlRepo.existsByPprIdAndOtherActivityId(pprid,actId);
	    if (exists) {
	        return "EXISTS";
	    }

	    return "NOT_EXISTS";
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
	    	dprOtherDtlService.saveOtherProjectPlan(pprId, activityId, target, userid, request.getRemoteAddr(), action);
	        redirectAttributes.addFlashAttribute("success", "Other project plan saved successfully.");
	    } catch (RuntimeException e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to save the other project plan.");
	    }

	    return "redirect:/dprPhyOtherHeadActivities";
	}
	
	@GetMapping("/getOtherDtlEditById")
	@ResponseBody
	public Map<String,Object> getOtherDtlEditById(
	        @RequestParam Integer id) {
	    DprOtherDtl data = dprOtherDtlRepo.getById(id);
	    Map<String,Object> map = new HashMap<>();
	    map.put("id", data.getOtherId());
	    map.put("district", data.getDprDetails().getPpr().getDistrict().getDistName());
	    map.put("project", data.getDprDetails().getPpr().getProjectName());
	    map.put("head", data.getOtherActivity().getOtherHeads().getOtherHeadDesc());
	    map.put("activity", data.getOtherActivity().getOtherActivityDesc());
	    map.put("unit", data.getOtherActivity().getUnit().getUnitDesc());
	    map.put("target", data.getQtyPlanned());
	    return map;
	}
	
	@PostMapping("/updateDPROtherTargetRecord")
	public String updateDPROtherTargetRecord(@RequestParam Integer otherId, @RequestParam BigDecimal target, 
			HttpSession session, RedirectAttributes redirectAttributes) {
		String userId = (String) session.getAttribute("userid");
//	    proposedProjectService.updateProposedProject(dto, userId);
	    redirectAttributes.addFlashAttribute("success",
	            "Record updated successfully.");
	    return "redirect:/pprProposedProjectDetails";
	}
	
	@GetMapping("/completeDprOtherTargetData")
	public String completeDprOtherTargetData(@RequestParam(value = "id", required = false) Integer id,
	        @RequestParam(value = "ids", required = false) List<Integer> ids, RedirectAttributes redirectAttributes) {
	    try {
	        if (id != null) {
	        	dprOtherDtlService.completeDprOtherTargetData(id);
	        	redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
	        }
	        else if (ids != null && !ids.isEmpty()) {
	            for (Integer selectedId : ids) {
	            	dprOtherDtlService.completeDprOtherTargetData(selectedId);
	            }
	            redirectAttributes.addFlashAttribute("success", "All Records completed successfully.");
	        } else 
	            redirectAttributes.addFlashAttribute("error", "No record selected.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Failed to complete records.");
	    }

	    return "redirect:/dprPhyOtherHeadActivities";
	}

}
