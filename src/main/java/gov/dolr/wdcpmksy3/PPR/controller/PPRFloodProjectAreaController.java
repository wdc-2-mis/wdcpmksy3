package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.DisasterType;
import gov.dolr.wdcpmksy3.PPR.entity.PprDisasterDetails;
import gov.dolr.wdcpmksy3.PPR.repository.DisasterTypeRepository;
import gov.dolr.wdcpmksy3.PPR.service.PprDisasterDetailsService;
import gov.dolr.wdcpmksy3.PPR.service.VillageService;
import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRFloodProjectAreaController {
	
	@Autowired
	private DisasterTypeRepository DTrepo;

	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private VillageService villService;
	
	@Autowired
	private PprDisasterDetailsService pprAreaService;
	
	@GetMapping("/dtlFloodDroughtArea")
    public String dtlFloodDroughtArea(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        List<DisasterType> disasterTypes = DTrepo.findAll();
        model.addAttribute("disasterTypes", disasterTypes);
        List<MDistrict> districts = districtService.getPPRDistrictsByState(stcode);
        Integer dcode  = districts.get(0).getDcode();
        if (!districts.isEmpty()) {
        	 model.addAttribute("district", districts.get(0));  
        }
        
		
		  List<MVillage> villages = villService.findVillagesByDistrict(dcode);
		  model.addAttribute("villages", villages);
		  List<PprDisasterDetails> records = pprAreaService.findAll(); 
		  model.addAttribute("records", records);
		 
       return "ppr/floodDroughtArea";
	}
	
	@PostMapping("/saveFloodDrought")
	public String saveFloodDrought(HttpSession session, @RequestParam("dcode") Integer dcode, @RequestParam("village") Integer vcode, @RequestParam("disasterTypeId") Integer disasterTypeId, @RequestParam("periodicity") String periodicity, @RequestParam("affected") String affected,
	        RedirectAttributes redirectAttributes, HttpServletRequest request) {

		String userId = (String) session.getAttribute("userid");
		  try {
			    pprAreaService.saveRecords(dcode, vcode, disasterTypeId, periodicity, affected, userId, request);
	            redirectAttributes.addFlashAttribute("success", "Records saved successfully!");
		    } catch (Exception ex) {
		        redirectAttributes.addFlashAttribute("error", "Failed to save records: " + ex.getMessage());
		    }
	         return "redirect:/dtlFloodDroughtArea";
		
	
	}
	
	@PostMapping("/updateFloodDroughtArea")
	public String updateFloodDroughtArea(@RequestParam Integer pprDisasterId, @RequestParam Integer disasterTypeId, @RequestParam String periodicity, @RequestParam Boolean affected, RedirectAttributes redirectAttributes, HttpSession session) {

	    try {

	        String updatedBy = String.valueOf(session.getAttribute("userId"));

	        pprAreaService.updateFloodDroughtArea(pprDisasterId, disasterTypeId, periodicity, affected, updatedBy);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record updated successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage()
	        );
	    }

	    return "redirect:/dtlFloodDroughtArea";
	}
	
	
	
	@GetMapping("/completeFloodDrought")
	public String completeFloodDrought(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes, HttpSession session) {

	    try {
	    	System.out.println("UserId in session: " + session.getAttribute("userId"));

	        String updatedBy =
	                String.valueOf(session.getAttribute("userId"));

	        pprAreaService.completeFloodDrought(id, updatedBy);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record completed successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute("error", e.getMessage()
	        );
	    }

	    return "redirect:/dtlFloodDroughtArea";
	}
	
	
	@GetMapping("/deleteFloodDrought")
	public String deleteFloodDrought(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {

	    try {

	        pprAreaService.deleteFloodDrought(id);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record deleted successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute("error", e.getMessage()
	        );
	    }

	    return "redirect:/dtlFloodDroughtArea";
	}

}
