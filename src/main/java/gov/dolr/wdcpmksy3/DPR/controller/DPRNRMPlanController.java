package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.entity.MPhyActivity;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyActivityRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyHeadsRepository;
import gov.dolr.wdcpmksy3.DPR.service.DPRNRMPlanService;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWcdcUnspentBalanceRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DPRNRMPlanController {
	
	@Autowired
    private PprTransactionRepository tranrepo;
	
	@Autowired
    private MPhyHeadsRepository head;
	
	@Autowired
    private MPhyActivityRepository actrepo;
	
	@Autowired
    private DPRNRMPlanService dprserv;
	
	@GetMapping("/createDPRNRMPlan")
    public String createDPRNRMPlan(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
        if(userid==null){

            return "redirect:/login";
        }
		
        model.addAttribute("headList", head.findAllByOrderBySeqNoAsc());
        model.addAttribute("distList", tranrepo.findDistrictsByStCodeAndActionA(stcode));
        
        return "dpr/addDPRNRMTarget";
    }
	
	@GetMapping("/getProjectsByDistrictDPRNRM")
	@ResponseBody
	public List<Map<String, Object>> getProjectsByDistrictDPRNRM(@RequestParam Integer dcode, Model model) {

	    List<MPpr> projects = tranrepo.findPprByDcodeAndActionA(dcode);

	    return projects.stream().map(p -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", p.getPprId());
	        map.put("name", p.getProjectName());
	        map.put("selectedDistrict", dcode);
	        model.addAttribute("selectedDcode", dcode);
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getActivitiesByHeadCodeDPRNRM")
	@ResponseBody
	public List<Map<String, Object>> getActivitiesByHeadCodeDPRNRM(@RequestParam Integer headId, Model model) {

	    List<MPhyActivity> act = actrepo.findByHead_HeadCodeOrderBySeqNoAsc(headId);

	    return act.stream().map(a -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", a.getActivityCode());
	        map.put("name", a.getActivityDesc());
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getUnitDescByActivity")
	@ResponseBody
	public ResponseEntity<String> getUnitDescByActivity(@RequestParam("actId") Integer actId) {

	    String unitDesc = actrepo.findUnitDescByActivityCode(actId);
	    System.out.println("kdy"+unitDesc);
	    if (unitDesc == null) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(unitDesc);
	}
	
	

}
