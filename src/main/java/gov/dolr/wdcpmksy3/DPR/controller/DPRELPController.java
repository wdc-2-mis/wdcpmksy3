package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
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
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
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
	    List<Object[]> list=dprservice.getDprELPProjectPlanList(stcode);
	  
        model.addAttribute("distList", tranrepo.findDistrictsByStCodeAndActionA(stcode));
        model.addAttribute("dprList", list);
        
        boolean allCompleted = list.stream()
                .allMatch(row -> "C".equals(row[9]));
         model.addAttribute("allCompleted", allCompleted);
        
	    return "dpr/epalivproddtls";
	}
	
	
	@GetMapping("/getProjectsByDistrictDPREPA")
	@ResponseBody
	public List<Map<String, Object>> getProjectsByDistrictDPREPA(@RequestParam Integer dcode, Model model) {

	    List<MPpr> projects = tranrepo.findPprByDcodeAndActionA(dcode);

	    return projects.stream().map(p -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", p.getPprId());
	        map.put("name", p.getProjectName());
	        map.put("selectedDistrict", dcode);
	        model.addAttribute("selectedDcode", dcode);
	        
	        List<Object[]> list=dprservice.getDprELPProjectPlanListByDistrict(dcode);
	        model.addAttribute("dprList", list);
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getDprELPByProject")
	@ResponseBody
	public List<Object[]> getDprELPByProject(@RequestParam("pprId") Integer pprId) {
	    return dprservice.getDprELPProjectPlanListByProject(pprId);
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
	public ResponseEntity<Map<String, String>> getUnitDescByelpActivity(@RequestParam("head") String head,
	                                                                    @RequestParam("actId") Integer actId,
	                                                                    @RequestParam("projectId") Integer projectId) {

	    String unitDesc = null;
	    String status = null;

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

	    // Check if this activity already exists for the given project + head
	    Character headChar = head.charAt(0);
	    status = dprservice.findStatusByProjectAndActivityAndHead(projectId, actId, headChar);


	    if (unitDesc == null) {
	        return ResponseEntity.notFound().build();
	    }

	    Map<String, String> response = new HashMap<>();
	    response.put("unitDesc", unitDesc);
	    response.put("status", status != null ? status : "");
	    return ResponseEntity.ok(response);
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

	@PostMapping("/editDPRELPTarget")
	public String editDPRELPTarget(@RequestParam("nrmid") Integer elpId,
	                               @RequestParam("target1") BigDecimal target,
	                               HttpSession session,
	                               RedirectAttributes redirectAttributes,
	                               HttpServletRequest request) {

	    Object userid = session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }

	    // Validation: target must not be null or blank
	    if (target == null) {
	        redirectAttributes.addFlashAttribute("error", "Please Enter Target");
	        return "redirect:/epalivproddetails";
	    }

	    boolean updated = dprservice.updateDprTarget(elpId, target, userid.toString(), request);

	    if (updated) {
	        redirectAttributes.addFlashAttribute("success", "DPR Target updated successfully.");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Unable to update DPR Target.");
	    }

	    return "redirect:/epalivproddetails";
	}

	// Delete single record
	@GetMapping("/deleteDprELPTarget")
	public String deleteDprELPTarget(@RequestParam("id") Integer elpId,
	                              HttpSession session,
	                              RedirectAttributes redirectAttributes) {
	    Object userid = session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }

	    boolean deleted = dprservice.deleteDprTarget(elpId);
	    if (deleted) {
	        redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
	    }
	    return "redirect:/epalivproddetails";
	}

	@PostMapping("/completeSelectedDprELPTargets")
	public String completeSelectedDprELPTargets(@RequestParam("selectedIds") List<Integer> ids,
	                                         HttpSession session,
	                                         RedirectAttributes redirectAttributes,
	                                         HttpServletRequest request) {
	    Object userid = session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }

	    boolean completed = dprservice.completeMultipleDprTargets(ids, userid.toString(), request);
	    if (completed) {
	        redirectAttributes.addFlashAttribute("success", "Selected records completed successfully.");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Unable to complete selected records.");
	    }
	    return "redirect:/epalivproddetails";
	}


	// Mark record as complete
	@GetMapping("/completeDprELPTarget")
	public String completeDprELPTarget(@RequestParam("id") Integer elpId,
	                                HttpSession session,
	                                RedirectAttributes redirectAttributes,
	                                HttpServletRequest request) {
	    Object userid = session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }

	    boolean completed = dprservice.completeDprTarget(elpId, userid.toString(), request);
	    if (completed) {
	        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Unable to mark record as completed.");
	    }
	    return "redirect:/epalivproddetails";
	}


}
