package gov.dolr.wdcpmksy3.PPR.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.PPRSoilErosionDTO;
import gov.dolr.wdcpmksy3.PPR.dto.PPRSoilErosionFormDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MErosion;
import gov.dolr.wdcpmksy3.PPR.entity.MErosionType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PPRSoilErosion;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPRSoilErosionRepository;
import gov.dolr.wdcpmksy3.PPR.service.PPRSoilErosionService;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRSoilErosionController {
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
    private PPRSoilErosionService soilErosionService;
	
	@Autowired
	private MPprRepository mPprRepo;
	
	@Autowired
	private PPRSoilErosionRepository pprSoilErosionRepo;
	
	@GetMapping("/pprSoilErosion")
    public String soilErosionForm(HttpSession session, Model model, @RequestParam(required = false) Integer dcode,
    		@RequestParam(required = false) Integer pprid, @RequestParam(required = false) Integer project) {
		
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){
            return "redirect:/login";
        }
        boolean pprCompleted = mPprRepo.existsByDistrict_State_StCodeAndStatus(stcode, "C");
        model.addAttribute("pprCompleted", pprCompleted);
	    if (!pprCompleted) 
	        model.addAttribute("error", "Please complete the Preliminary Project Report First before entering PPR Soil Erosion Details.");
        if (pprid != null) {
        	soilErosionService.changeStatusByPprId(pprid);
        }
        if(dcode != null){
        	List<MPpr> pprList = mPprRepo.findByDistrictDcode(dcode);
        	if(!pprList.isEmpty()){
	        	model.addAttribute("pprList", pprList);
	        	if(project != null) {
	        		MPpr ppr = pprList.stream().filter(s-> s.getPprId().equals(project)).findFirst().orElse(null);
	        		model.addAttribute("pprId", project);
	        	}
        	}
			model.addAttribute("selectedDistrict", dcode);
        }
        List<MErosion> erosionList = soilErosionService.getErosionList();
        
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("erosionList", erosionList);
        model.addAttribute("monthList", soilErosionService.getAllMonths());
        model.addAttribute("yearList", soilErosionService.getAllYears());
        model.addAttribute("soilErosionList",pprSoilErosionRepo.findPPRSoilErosionBystCode(stcode));

        return "ppr/pprSoilErosion";
    }
	
	@GetMapping("/getErosionTypes")
    @ResponseBody
    public List<MErosionType> getErosionTypes(@RequestParam Integer erosionId) {

        return soilErosionService.getErosionTypes(erosionId);

    }
	
	@GetMapping("/getSoilErosionById")
    @ResponseBody
    public PPRSoilErosionDTO getSoilErosionById(@RequestParam Integer id) {

        return soilErosionService.getSoilErosionById(id);
    }
	
	@GetMapping("/getSoilErosionByDistrict")
    @ResponseBody
    public List<Map<String, Object>> getSoilErosionByDistrict(@RequestParam Integer dcode) {

        return soilErosionService.getSoilErosionByDistrict(dcode);
    }
	
	@PostMapping("/savePPRSoilErosion")
	public String saveSoilErosion(@RequestParam Integer project, @ModelAttribute PPRSoilErosionFormDTO form,
	        HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    List<PPRSoilErosionDTO> erosionList = form.getErosionList();
	    try {
	    	String userId = session.getAttribute("userid").toString();
	        if (project == null) 
	            throw new RuntimeException("Project is missing from form.");
	        if (erosionList == null || erosionList.isEmpty()) 
	            throw new RuntimeException("No soil erosion data received.");
	        //Check duplication
	        List<Integer> duplicateTypes = soilErosionService.findDuplicateErosionTypes(project, erosionList);
	        if (!duplicateTypes.isEmpty()) {
	            String typeNames = soilErosionService.getErosionTypeNames(duplicateTypes);
	            redirectAttributes.addFlashAttribute("error", "Duplicate entries found for: " + typeNames + ". Each erosion type can only be entered once.");
	            return "redirect:/pprSoilErosion";
	        }
	        soilErosionService.saveSoilErosion(project, erosionList, userId, request);
	        redirectAttributes.addFlashAttribute("success", "Record saved successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Record not saved.");
	    }
	    return "redirect:/pprSoilErosion";
	}
	
	@PostMapping("/updatePPRSoilErosion")
	public String updateSoilErosion(@RequestParam Integer ppr_soil_erosion_id, @RequestParam(required = false) String affected_area, 
	        @RequestParam(required = false) String runoff, @RequestParam(required = false) String avg_soil_loss, 
	        @RequestParam(required = false) Integer monthId, @RequestParam(required = false) Integer yearId,HttpSession session, 
	        RedirectAttributes redirectAttributes) {
	    try {
	        String userId = session.getAttribute("userid").toString();
	        BigDecimal affected = (affected_area != null && !affected_area.isEmpty()) ?  new BigDecimal(affected_area) : null;
	        BigDecimal runoffVal = (runoff != null && !runoff.isEmpty()) ?  new BigDecimal(runoff) : null;
	        BigDecimal soilLoss = (avg_soil_loss != null && !avg_soil_loss.isEmpty()) ?  new BigDecimal(avg_soil_loss) : null;
	        soilErosionService.updateSoilErosion(ppr_soil_erosion_id, affected, runoffVal, soilLoss, monthId, yearId, userId);
	        redirectAttributes.addFlashAttribute("success", "Record updated successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Unable to update record: " + e.getMessage());
	    }
	    return "redirect:/pprSoilErosion";
	}
	
	@GetMapping("/deletePPRSoilErosion")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
	    try {
	        soilErosionService.delete(id);
	        redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to delete.");
	    }
	    return "redirect:/pprSoilErosion";
	}
	
	@GetMapping("/completePPRSoilErosion")
	public String complete(@RequestParam Integer id, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    try {
	        PPRSoilErosion entity = soilErosionService.getById(id);
	        if (entity == null) {
	            redirectAttributes.addFlashAttribute("error", "Record not found.");
	            return "redirect:/pprSoilErosion";
	        }
	        entity.setStatus('C');
	        entity.setUpdatedBy(session.getAttribute("userid").toString());
	        entity.setUpdatedDate(java.time.LocalDate.now());
	        entity.setRequestIp(request.getRemoteAddr());
	        soilErosionService.save(entity);
	        redirectAttributes.addFlashAttribute("success", "Record completed.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to complete.");
	    }

	    return "redirect:/pprSoilErosion";
	}
	
	@GetMapping("/skipPprSoilErosion")
    public String skipPprSoilErosion(@RequestParam Integer pprid, Model model, HttpServletRequest servletRequest, 
    		HttpSession session, RedirectAttributes redirectAttributes) {
	  String userId = (String) session.getAttribute("userid");
	  try {
		  soilErosionService.skipPprSoilErosion(pprid, userId, servletRequest);
           // redirectAttributes.addFlashAttribute("success", "Records saved successfully!");
	    } 
	  	catch (Exception ex) {
	        redirectAttributes.addFlashAttribute("error", "Failed to viewPPR records: " + ex.getMessage());
	    }
         return "redirect:/viewPPR";
    }
}
