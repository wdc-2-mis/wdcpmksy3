package gov.dolr.wdcpmksy3.PPR.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;
import gov.dolr.wdcpmksy3.PPR.repository.PprLivelihoodRepository;
import gov.dolr.wdcpmksy3.PPR.service.LivelihoodActivityServices;
import gov.dolr.wdcpmksy3.PPR.service.LivelihoodInterventionServices;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.MBlock;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LivelihoodSummaryController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private MBlockRepository blkrepo;
	
	@Autowired
    private LivelihoodActivityServices laser;
	
	@Autowired
    private LivelihoodInterventionServices liser;
	
	@Autowired
    private PprLivelihoodRepository livrepo;
	
	@GetMapping("/livelihoodSummaryPPR13")
    public String livelihoodSummaryPPR13(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
	
        if(userid==null){

            return "redirect:/login";
        }
        List<PprLivelihood> records = livrepo.findByPpr_District_State_StCode(stcode);
        model.addAttribute("records", records);
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("LivelihoodActList", laser.getAllLivelihoodActivity());
        model.addAttribute("LivelihoodInvList", liser.getAllLivelihoodIntervention());
	
        return "ppr/livelihoodSummary";
    }
	
	@GetMapping("/getBlocksByDistrictPPR13")
	@ResponseBody
	public List<Map<String, Object>> getProjectsByDistrict(@RequestParam Integer dcode) {

	    List<MBlock> block = blkrepo.findByDistrict_DcodeOrderByBlockNameAsc(dcode);
	    return block.stream().map(p -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", p.getBcode());
	        map.put("name", p.getBlockName());
	        return map;
	    }).toList();
	}
	
	 @PostMapping("/saveLivelihoodSummaryPPR13")
	 public String saveLivelihoodSummaryPPR13(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer district,
	    		@RequestParam Integer block,
	    		@RequestParam Integer project,
	    		@RequestParam Integer village,
	    		@RequestParam List<Integer> livact,
	    		@RequestParam List<Integer> livinv,
	            @RequestParam Integer watershed,
	            @RequestParam Integer migrat,
	            @RequestParam String reason,
	            @RequestParam String action,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=laser.saveLivelihoodSummaryPPR13(block, project, village, livact, livinv, watershed, migrat,
							reason, action, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Livelihood Summary saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to saved Livelihood Summary.");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to saved Livelihood Summary.");
					}
				return "redirect:/livelihoodSummaryPPR13";	
	    }
	 
	 	@GetMapping("/deleteLivelihoodSummaryPPR13")
	    public String deleteLivelihoodSummaryPPR13(HttpSession session, Model model, @RequestParam("id") Integer id,  
	    		RedirectAttributes redirectAttributes) {

			
			String userid=(String)session.getAttribute("userid");
			try {
				
		        if(userid==null){
		
		            return "redirect:/login";
		        }
		        PprLivelihood data = livrepo.findById(id).orElse(null);
	            if (data == null) {
	                redirectAttributes.addFlashAttribute("error", "Record not found.");
	                return "redirect:/livelihoodSummaryPPR13";
	            }
	            
	            if (livrepo.existsById(id)) {
	            	livrepo.deleteById(id);
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
			return "redirect:/livelihoodSummaryPPR13";
	    }
	 	
	 	@GetMapping("/completeLivelihoodSummaryPPR13")
	     public String completeLivelihoodSummaryPPR13(HttpSession session, Model model, @RequestParam("id") Integer id,  
	     		RedirectAttributes redirectAttributes) {

	 		
	 			String userid=(String)session.getAttribute("userid");
	 		 	try {
	 		 		
	 		 		if(userid==null){

	 		            return "redirect:/login";
	 		        }
	 		 		laser.completeRecord(id);
	 		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
	 		    } 
	 		 	catch (Exception e) {
	 		 		e.printStackTrace();
	 		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
	 		    }
	        
	         return "redirect:/livelihoodSummaryPPR13";
	     }
	 	
	 	 @PostMapping("/editLivelihoodSummaryPPR13")
		 public String editLivelihoodSummaryPPR13(HttpSession session, Model model, HttpServletRequest request,
		    		
		    		@RequestParam Integer livsamid,
		    		@RequestParam Integer village1,
		    		@RequestParam List<Integer> livact1,
		    		@RequestParam List<Integer> livinv1,
		            @RequestParam Integer watershed1,
		            @RequestParam Integer migrat1,
		            @RequestParam String reason1,
		            @RequestParam String updateAction,
		            RedirectAttributes redirectAttributes) {
		   
					Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
					String userid=(String)session.getAttribute("userid");
					try {
						
						 if(userid==null){
		
					            return "redirect:/login";
					     }
						boolean save=false;
				    	
						save=laser.editLivelihoodSummaryPPR13(livsamid, village1, livact1, livinv1, watershed1, migrat1,
								reason1, updateAction, userid, CommonFunctions.getClientIpAddr(request));
						
						if(save)
							redirectAttributes.addFlashAttribute( "success", "Livelihood Summary Update successfully.");
						else
							redirectAttributes.addFlashAttribute("error", "Unable to Update Livelihood Summary.");
						}
						catch (Exception e) {
		
							e.printStackTrace();
					        redirectAttributes.addFlashAttribute("error", "Unable to Update Livelihood Summary.");
						}
					return "redirect:/livelihoodSummaryPPR13";	
		    }

}
