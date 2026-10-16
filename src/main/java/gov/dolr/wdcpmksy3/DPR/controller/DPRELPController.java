package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.util.ArrayList;
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

import gov.dolr.wdcpmksy3.DPR.repository.EpaActivityRepo;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyHeadsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.ProductionActivityRepo;
import gov.dolr.wdcpmksy3.DPR.service.DPRELPService;
import gov.dolr.wdcpmksy3.PPR.repository.LivelihoodActivityRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DPRELPController {
	
	@Autowired
	private DPRELPService dprservice;
	
	@Autowired
    private PprTransactionRepository tranrepo;
	
	@Autowired
    private MPhyHeadsRepository head;
	
	@Autowired
	LivelihoodActivityRepository livRepo;
	
	@Autowired
	EpaActivityRepo epaRepo;
	
	@Autowired
	ProductionActivityRepo prodRepo;
	
	@GetMapping("/epalivproddetails")
	public String epalivproddetails(
	        HttpSession session, Model model) {

		
	    Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    Object userid = session.getAttribute("userid");

	    if (userid == null) {
	        return "redirect:/login";
	    }

	  
        model.addAttribute("distList", tranrepo.findDistrictsByStCodeAndActionA(stcode));
	    
	    return "dpr/epalivproddtls";
	}
	
	@GetMapping("/getActivitiesByHeadCodeDPRELP")
	@ResponseBody
	public List<Map<String, Object>> getActivitiesByHeadCodeDPRELP(@RequestParam String head) {
	    List<Map<String, Object>> result = new ArrayList<>();

	    if ("E".equalsIgnoreCase(head)) {
	        result = epaRepo.findAll().stream().map(e -> {
	            Map<String, Object> map = new HashMap<>();
	            map.put("id", e.getId());
	            map.put("name", e.getDescription());
	            return map;
	        }).toList();
	    } else if ("L".equalsIgnoreCase(head)) {
	        result = livRepo.findAll().stream().map(l -> {
	            Map<String, Object> map = new HashMap<>();
	            map.put("id", l.getLivelihoodActivityId());
	            map.put("name", l.getLivelihoodActivityName());
	            return map;
	        }).toList();
	    } else if ("P".equalsIgnoreCase(head)) {
	        result = prodRepo.findAll().stream().map(p -> {
	            Map<String, Object> map = new HashMap<>();
	            map.put("id", p.getId());
	            map.put("name", p.getDescription());
	            return map;
	        }).toList();
	    }

	    return result;
	}


	@GetMapping("/getUnitDescByelpActivity")
	@ResponseBody
	public ResponseEntity<String> getUnitDescByelpActivity(@RequestParam("head") String head, @RequestParam("actId") Integer actId) {

	    String unitDesc = null;

	    switch (head) {
	        case "E":
	            unitDesc = epaRepo.findById(actId)
	                              .map(e -> e.getUnit().getUnitDesc())
	                              .orElse(null);
	            break;

	        case "L":
	            unitDesc = livRepo.findById(actId)
	                                     .map(l -> l.getUnit().getUnitDesc())
	                                     .orElse(null);
	            break;

	        case "P":
	            unitDesc = prodRepo.findById(actId)
	                                       .map(p -> p.getUnit().getUnitDesc())
	                                       .orElse(null);
	            break;
	    }

	    if (unitDesc == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(unitDesc);
	}
	
	
	@PostMapping("/saveDPRELPTarget")
	public String saveDPRELPTarget(@RequestParam("district") Integer district, @RequestParam("project") Integer project, @RequestParam("head") Character head, @RequestParam("activity") Integer activity, @RequestParam("target") BigDecimal target,
	        HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

	    Object userid = session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }

	    boolean saved = dprservice.saveDprELP(district, project, head, activity, target, userid.toString(), session, request);

	    if (saved) {
	    	 if (Character.toUpperCase(head) == 'E') {
	        redirectAttributes.addFlashAttribute("success", "DPR EPA Target Records saved successfully.");
	    	 }
	    	 else if (Character.toUpperCase(head) == 'L') {
	 	        redirectAttributes.addFlashAttribute("success", "DPR Livelihood Target Records saved successfully.");
	 	    	 }
	    	 if (Character.toUpperCase(head) == 'P') {
	 	        redirectAttributes.addFlashAttribute("success", "DPR Production Target Records saved successfully.");
	 	    	 }
	    	 
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Unable to save DPR Target.");
	    }

	    return "redirect:/epalivproddetails";
	}


}
