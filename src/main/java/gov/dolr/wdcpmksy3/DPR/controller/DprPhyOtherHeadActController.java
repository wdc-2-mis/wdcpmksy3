package gov.dolr.wdcpmksy3.DPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.DPR.repository.MOtherActivityRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MOtherHeadsRepository;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
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
	        	if(project != null) 
	        		model.addAttribute("pprId", project);
	        }
	    }
		if(head != null) {
			model.addAttribute("selectedHead", head);
			model.addAttribute("activityList", mOtherActivityRepo.findByOtherHeadsIdOrderBySeqNoAsc(head));
		}
			
		model.addAttribute("headList", mOtherHeadsRepo.findAllByOrderBySeqNoAsc());
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

}
