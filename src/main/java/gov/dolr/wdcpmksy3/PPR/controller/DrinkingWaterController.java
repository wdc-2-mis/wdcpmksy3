package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.MWaterQuality;
import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;
import gov.dolr.wdcpmksy3.PPR.repository.PprLivelihoodRepository;
import gov.dolr.wdcpmksy3.PPR.service.DrinkingWaterServices;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.repository.MWaterQualityRepository;
import gov.dolr.wdcpmksy3.repository.PprDrinkingWaterRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DrinkingWaterController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private MWaterQualityRepository wtrqua;
	
	@Autowired
    private PprLivelihoodRepository livrepo;
	
	@Autowired
    private DrinkingWaterServices serv;
	
	@Autowired
    private PprDrinkingWaterRepository dwrepo;
	
	@GetMapping("/drinkingWaterStatus")
    public String drinkingWaterStatus(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
        if(userid==null){

            return "redirect:/login";
        }
        List<MWaterQuality> waterQualityList =wtrqua.findAll();
        model.addAttribute("waterQualityList", waterQualityList);
        List<PprDrinkingWater> records = dwrepo.findByPpr_District_State_StCode(stcode);
        model.addAttribute("records", records);
        model.addAttribute("distList", districtService.findCompletedDistrictsByState(stcode));
	
        return "ppr/drinkingWaterStatus";
    }
	
	@PostMapping("/saveDrinkingWaterStatus")
	 public String saveDrinkingWaterStatus(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer district,
	    		@RequestParam Integer project,
	    		@RequestParam Integer village,
	            @RequestParam Integer watershed,
	            @RequestParam Integer preQualityWater,
	            @RequestParam Integer preQualityId,
	            @RequestParam Integer postQualityWater,
	            @RequestParam Integer postQualityId,
	            @RequestParam String action,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=serv.saveDrinkingWaterStatus(project, village, watershed, preQualityWater, preQualityId,
							postQualityWater, postQualityId, action, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Drinking Water Status saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to saved Drinking Water Status.");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to saved Drinking Water Status.");
					}
				return "redirect:/drinkingWaterStatus";	
	    }
	
	@GetMapping("/deleteDrinkingWaterStatus")
    public String deleteDrinkingWaterStatus(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
	        PprDrinkingWater data = dwrepo.findById(id).orElse(null);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/drinkingWaterStatus";
            }
            
            if (dwrepo.existsById(id)) {
            	dwrepo.deleteById(id);
            	redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
            }
            else {
            	redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            }
            
        } 
        catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            e.printStackTrace();
        }
		return "redirect:/drinkingWaterStatus";
    }
	
	@GetMapping("/completeDrinkingWaterStatus")
    public String completeDrinkingWaterStatus(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
			String userid=(String)session.getAttribute("userid");
		 	try {
		 		
		 		if(userid==null){

		            return "redirect:/login";
		        }
		 		serv.completeRecord(id);
		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
		    } 
		 	catch (Exception e) {
		 		e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
		    }
       
        return "redirect:/drinkingWaterStatus";
    }
	
	 @PostMapping("/editDrinkingWaterStatus")
	 public String editDrinkingWaterStatus(HttpSession session, Model model, HttpServletRequest request,
	    		
	    		@RequestParam Integer pprWaterId,
	    		@RequestParam Integer village1,
	            @RequestParam Integer watershed1,
	            @RequestParam Integer preQualityWater1,
	            @RequestParam Integer preQualityId1,
	            @RequestParam Integer postQualityWater1,
	            @RequestParam Integer postQualityId1,
	            @RequestParam String updateAction,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=serv.editDrinkingWaterStatus(pprWaterId, village1, watershed1, preQualityWater1, preQualityId1,
							postQualityWater1, postQualityId1, updateAction, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Drinking Water Status Update successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to Update Drinking Water Status.");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to Update Drinking Water Status.");
					}
				return "redirect:/drinkingWaterStatus";	
	    }

}
