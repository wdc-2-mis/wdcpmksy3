package gov.dolr.wdcpmksy3.PPR.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.PprCropOutcome;
import gov.dolr.wdcpmksy3.PPR.repository.MSeasonRepo;
import gov.dolr.wdcpmksy3.PPR.service.CropTypeServices;
import gov.dolr.wdcpmksy3.PPR.service.PprCropOutcomeService;
import gov.dolr.wdcpmksy3.repository.CropOutcomeRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRCropRelatedOutcomeController {

	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private MSeasonRepo mseasonRepo;
	
	@Autowired
    private CropTypeServices cropser;
	
	@Autowired
	private CropOutcomeRepository cropOutcomeRepo;
	
	@Autowired
	private PprCropOutcomeService outcomeService;
	
	@GetMapping("/pprCropOutcomes")
	public String pprCropOutcomes(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("mseason", mseasonRepo.findAll());
        model.addAttribute("cropTypeList", cropser.getAllCropTypeDetails());
        
        return "ppr/pprCropOutcomes";
	}
	
	@GetMapping("/getUsedCropsByPpr")
	@ResponseBody
	public String getUsedCropsByPpr(@RequestParam("pprId") Integer pprId) {

	    List<Integer> cropIds = cropOutcomeRepo.findCropIdsByPpr(pprId);

	    return cropIds.stream()
	            .map(String::valueOf)
	            .collect(Collectors.joining(","));
	}
	
	@PostMapping("/savecropRelatedOutcome")
	public String saveCropRelatedOutcome(@RequestParam("district") Integer dcode, @RequestParam("project") Integer project, @RequestParam("seasonTypeId") Integer seasonTypeId, @RequestParam("cropTypeId") Integer[] cropTypeIds, 
			@RequestParam("currentArea") String[] currentAreas, @RequestParam("currentProd") String[] currentProds, @RequestParam("expectedArea") String[] expectedAreas, @RequestParam("expectedProd") String[] expectedProds,
            HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {

	    try {

	        String userId = session.getAttribute("userid").toString();

	        outcomeService.saveCropRelatedOutcome(dcode, project, seasonTypeId, cropTypeIds, currentAreas, currentProds, expectedAreas, expectedProds, request, userId);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Records saved successfully!"
	        );

	    } catch (Exception ex) {

	        ex.printStackTrace();

	        redirectAttributes.addFlashAttribute(
	                "error",
	                "Failed to save records: " + ex.getMessage()
	        );
	    }

	    return "redirect:/pprCropOutcomes";
	}
	
	@GetMapping("/getCropOutcomesByDistrict")
	public String getCropOutcomesByDistrict(
	        @RequestParam("dcode") Integer dcode,
	        Model model) {

	    List<PprCropOutcome> cropOutcomes =
	            cropOutcomeRepo.findByDistrict(dcode);

	    model.addAttribute("cropOutcomes", cropOutcomes);

	    return "ppr/pprCropOutcomes :: cropOutcomeTable";
	}
	
	@PostMapping("/updateCropOutcome")
	public String updateCropOutcome(@RequestParam("pprCropOutcomeId") Integer id, @RequestParam("project") Integer project, @RequestParam("seasonTypeId") Integer seasonId,@RequestParam("cropTypeId") Integer cropTypeId,
	        @RequestParam("currentArea") String currentArea, @RequestParam("currentProd") String currentProd, @RequestParam("expectedArea") String expectedArea, @RequestParam("expectedProd") String expectedProd, 
	        HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {

	    try {

	        String userId =
	                session.getAttribute("userid").toString();

	        outcomeService.updateCropOutcome(id, project, seasonId, cropTypeId, currentArea, currentProd, expectedArea, expectedProd, request, userId);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record updated successfully!"
	        );

	    } catch (Exception ex) {

	        ex.printStackTrace();

	        redirectAttributes.addFlashAttribute(
	                "error",
	                "Failed to update record: "
	                        + ex.getMessage()
	        );
	    }

	    return "redirect:/pprCropOutcomes";
	}
	
	@GetMapping("/completeCropOutcome")
	public String completeCropOutcome(@RequestParam Integer id, RedirectAttributes redirectAttributes, HttpSession session) {

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

	    return "redirect:/pprCropOutcomes";
	}

	@GetMapping("/deleteCropOutcome")
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

	    return "redirect:/pprCropOutcomes";
	}
}
