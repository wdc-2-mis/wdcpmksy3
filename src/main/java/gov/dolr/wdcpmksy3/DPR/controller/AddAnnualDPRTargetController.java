package gov.dolr.wdcpmksy3.DPR.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherDtl;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserProjectMap;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserProjectMapRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class AddAnnualDPRTargetController {
	
	@Autowired
    private FinYearService finService;
	
	@Autowired
	private WdcpmksyUserProjectMapRepository userProjectMapRepository;
	
	@GetMapping("/getDPRAnnualTarget")
    public String getDPRAnnualTarget(@RequestParam(required = false) Integer superHead, HttpSession session, 
    		Model model, @RequestParam(required = false) Integer project, @RequestParam(required = false) Integer fyear, @RequestParam(required = false) Integer head) {
		
		String userid=(String)session.getAttribute("userid");
		if(userid==null){
            return "redirect:/login";
        }
		Integer regId = Integer.parseInt(session.getAttribute("regid").toString());
		List<WdcpmksyUserProjectMap> projectMappings = userProjectMapRepository.findByUser_RegId(regId);
		List<MPpr> projectList = projectMappings.stream()
	            .map(WdcpmksyUserProjectMap::getProject)
	            .filter(Objects::nonNull).sorted(Comparator.comparing(MPpr::getProjectName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))).toList();
		if(superHead != null) {
			if(project != null) {
				
			}
			model.addAttribute("superHead",superHead);
			model.addAttribute("pprList", projectList);
		}
			
		Map<String, String> superHeadMap = new LinkedHashMap<>();
		superHeadMap.put("EPA", "EPA/Livlihood/Production");
		superHeadMap.put("NRM", "NRM");
		superHeadMap.put("Other", "Other");
		List<DprOtherDtl> dprOtherDtlList = new ArrayList<>();
		
		if(head != null) {
			model.addAttribute("selectedHead", head);
		}
		model.addAttribute("superHeadMap",superHeadMap);
		model.addAttribute("dprOtherTargetList", dprOtherDtlList);
		model.addAttribute("statusCheck", dprOtherDtlList != null && dprOtherDtlList.stream().anyMatch(dtl -> dtl.getStatus() != null && dtl.getStatus() == 'D'));
		return "dpr/addAnnualDPRTarget";
	}

}
