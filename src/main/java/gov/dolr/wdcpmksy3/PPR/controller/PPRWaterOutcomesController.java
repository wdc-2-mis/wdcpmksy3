package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.DisasterType;
import gov.dolr.wdcpmksy3.PPR.entity.MWaterSource;
import gov.dolr.wdcpmksy3.PPR.entity.PprWaterOutcome;
import gov.dolr.wdcpmksy3.PPR.repository.WaterSourceRepository;
import gov.dolr.wdcpmksy3.PPR.service.PprAreaCoverService;
import gov.dolr.wdcpmksy3.PPR.service.PprWaterOutcomeService;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRWaterOutcomesController {

	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private WaterSourceRepository waterRepo;
	
	@Autowired
	private PprWaterOutcomeService outcomeService;
	
	@Autowired
	private PprAreaCoverService pprAreaService;
	
	@GetMapping("/pprWaterOutcomes")
	public String pprWaterOutcomes(HttpSession session, Model model, @RequestParam(required = false) Integer pprid) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        if (pprid != null) {
        	outcomeService.changeStatusByPprId(pprid);
        }
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        List<MWaterSource> mWaterSource = waterRepo.findAll();
        model.addAttribute("mWaterSource", mWaterSource);
        
        boolean pprCompleted =
	            pprAreaService.isPprCompleted(stcode);
   
   model.addAttribute("pprCompleted",
		   pprCompleted);

	    if (!pprCompleted) {

	        model.addAttribute(
	                "error",
	                "Please complete the Preliminary Project Report First."
	        );

	    }
        return "ppr/pprWaterOutcomes";
	}
	
	@PostMapping("/savegroundWaterDepthArea")
    public String saveWaterDepthArea(@RequestParam("district") Integer dcode, @RequestParam("project") Integer project, @RequestParam("watershed") Integer watershed, @RequestParam("village") Integer vcode, HttpServletRequest request,
    		@RequestParam("sourceTypeId") Integer sourceTypeId, @RequestParam("pre_project") String preProject, @RequestParam("expected_post") String expected_post, @RequestParam("remarks") String remarks, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
        	String userId = session.getAttribute("userid").toString();
            outcomeService.saveOutcome(dcode, project, watershed, vcode, request, sourceTypeId, preProject, expected_post, remarks, userId);
            redirectAttributes.addFlashAttribute("success", "Records saved successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to save records: " + ex.getMessage());
        }
        return "redirect:/pprWaterOutcomes";
        
    }
	
	@GetMapping("/checkWaterOutcome")
	@ResponseBody
	public String checkWaterOutcome(
	        @RequestParam Integer project,
	        @RequestParam Integer watershed,
	        @RequestParam Integer village) {

	    return outcomeService.getWaterOutcomeStatus(
	            project,
	            watershed,
	            village
	    );
	}

	
	
	@GetMapping("/getWaterOutcomesByDistrict")
	@ResponseBody
	public List<Map<String, Object>> getWaterOutcomesByDistrict(
	        @RequestParam(required = false) Integer dcode,
	        HttpSession session) {

	    Object userid = session.getAttribute("userid");

	    if (userid == null) {
	        return List.of();
	    }

	    Integer stcode =
	            Integer.parseInt(session.getAttribute("stcode").toString());

	    List<PprWaterOutcome> outcomes;

	    if (dcode != null) {
	        // District selected
	        outcomes = outcomeService.findByDistrict(dcode);
	    } else {
	        // No district selected -> all records of logged-in state
	        outcomes = outcomeService.findByState(stcode);
	    }

	    return outcomes.stream().map(o -> {

	        Map<String, Object> map = new HashMap<>();

	        map.put("id", o.getPprWaterOutcomeId());

	        map.put("district",
	                o.getPpr().getDistrict().getDistName());

	        map.put("project",
	                o.getPpr().getProjectName());

	        map.put("watershed",
	                o.getMicroWatershed() != null
	                        ? o.getMicroWatershed().getMwName()
	                        : "");

	        map.put("village",
	                o.getVillage() != null
	                        ? o.getVillage().getVillageName()
	                        : "");

	        map.put("sourceId",
	                o.getWaterSource() != null
	                        ? String.valueOf(
	                                o.getWaterSource().getWaterSourceId())
	                        : "");

	        map.put("source",
	                o.getWaterSource() != null
	                        ? o.getWaterSource().getSourceName()
	                        : "");

	        map.put("preProject",
	                o.getPreProjectLevel());

	        map.put("postProject",
	                o.getPostProjectLevel());

	        map.put("remarks",
	                o.getRemarks());

	        map.put("status",
	                o.getStatus());

	        return map;

	    }).toList();
	}

	@PostMapping("/updateWaterOutcome")
	public String updateWaterOutcome(@RequestParam Integer pprWaterOutcomeId, @RequestParam Integer editSourceTypeId, @RequestParam String editPreProject, @RequestParam String editPostProject, @RequestParam(required = false) String editRemarks,
	        RedirectAttributes redirectAttributes, HttpSession session) {

	    try {

	        String updatedBy = session.getAttribute("userid").toString();

	        outcomeService.updateWaterOutcome(pprWaterOutcomeId, editSourceTypeId, editPreProject, editPostProject, editRemarks, updatedBy);

	        redirectAttributes.addFlashAttribute("success","Record updated successfully.");

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage()
	        );
	    }

	    return "redirect:/pprWaterOutcomes";
	}

	@GetMapping("/completeWaterOutcome")
	public String completeWaterOutcome(@RequestParam Integer id, RedirectAttributes redirectAttributes, HttpSession session) {

	    try {

	        String updatedBy = String.valueOf(session.getAttribute("userId"));

	        outcomeService.completeWaterOutcome(id, updatedBy);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record completed successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage()
	        );
	    }

	    return "redirect:/pprWaterOutcomes";
	}

	@GetMapping("/deleteWaterOutcome")
	public String deleteWaterOutcome(@RequestParam Integer id, RedirectAttributes redirectAttributes, HttpSession session) {

	    try {

	        outcomeService.deleteWaterOutcome(id);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record deleted successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage()
	        );
	    }

	    return "redirect:/pprWaterOutcomes";
	}
	@GetMapping("/skipPprWaterOutcomes")
    public String skipPprWaterOutcomes(@RequestParam Integer pprid, Model model, HttpServletRequest servletRequest, 
    		HttpSession session, RedirectAttributes redirectAttributes) {
	  String userId = (String) session.getAttribute("userid");
	  try {
		  outcomeService.skipPprWaterOutcomes(pprid, userId, servletRequest);
           // redirectAttributes.addFlashAttribute("success", "Records saved successfully!");
	    } 
	  	catch (Exception ex) {
	        redirectAttributes.addFlashAttribute("error", "Failed to viewPPR records: " + ex.getMessage());
	    }
         return "redirect:/viewPPR";
    }
	
	
	
	
}
