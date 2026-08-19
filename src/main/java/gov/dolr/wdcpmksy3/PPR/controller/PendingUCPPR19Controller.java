package gov.dolr.wdcpmksy3.PPR.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.PprPendingUc;
import gov.dolr.wdcpmksy3.PPR.repository.PprPendingUcRepository;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.PPR.service.PendingUCPPR19Services;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PendingUCPPR19Controller {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private FinYearService finService;
	
	@Autowired
    private PprPendingUcRepository ucrepo;
	
	@Autowired
    private PendingUCPPR19Services services;
	
	@GetMapping("/pendingUCPPR19")
    public String pendingUCPPR19(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
        if(userid==null){

            return "redirect:/login";
        }
        List<PprPendingUc> records = ucrepo.findByPpr_District_State_StCode(stcode);
        model.addAttribute("records", records);
        model.addAttribute("distList", districtService.findCompletedDistrictsByState(stcode));
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
        return "ppr/pendingUCPPR19";
    }
	
	@PostMapping("/savePendingUCPPR19")
	 public String savePendingUCPPR19(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer district,
	    		@RequestParam Integer project,
	    		@RequestParam Integer fyear,
	            @RequestParam BigInteger installment,
	            @RequestParam BigDecimal released,
	            @RequestParam BigDecimal utilized,
	            @RequestParam java.time.LocalDate dueDate,
	            @RequestParam BigDecimal ucamount,
	            @RequestParam java.time.LocalDate ucDate,
	            @RequestParam BigDecimal ducamount,
	            @RequestParam String reasion,
	            @RequestParam java.time.LocalDate fromDate,
	            @RequestParam java.time.LocalDate toDate,
	            @RequestParam BigDecimal pucamount,
	            @RequestParam String action,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=services.savePendingUCPPR19(project, fyear, installment, released, utilized, dueDate, ucamount, ucDate,
							ducamount, reasion, fromDate, toDate, pucamount, action, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Details of Pending UC`s saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to saved Details of Pending UC`s");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to saved Details of Pending UC`s");
					}
				return "redirect:/pendingUCPPR19";	
	    }
	
	@GetMapping("/deletePendingUCPPR19")
    public String deletePendingUCPPR19(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
	        PprPendingUc data = ucrepo.findById(id).orElse(null);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/pendingUCPPR19";
            }
            
            if (ucrepo.existsById(id)) {
            	ucrepo.deleteById(id);
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
		return "redirect:/pendingUCPPR19";
    }
	
	@GetMapping("/completePendingUCPPR19")
    public String completePendingUCPPR19(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
			String userid=(String)session.getAttribute("userid");
		 	try {
		 		
		 		if(userid==null){

		            return "redirect:/login";
		        }
		 		services.completeRecord(id);
		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
		    } 
		 	catch (Exception e) {
		 		e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
		    }
       
        return "redirect:/pendingUCPPR19";
    }
	@PostMapping("/editPendingUCPPR19")
	 public String editPendingUCPPR19(HttpSession session, Model model, HttpServletRequest request,
	    		
	    		@RequestParam Integer pprPendingUcId,
	    		@RequestParam BigInteger installment1,
	            @RequestParam BigDecimal released1,
	            @RequestParam BigDecimal utilized1,
	            @RequestParam java.time.LocalDate dueDate1,
	            @RequestParam BigDecimal ucamount1,
	            @RequestParam java.time.LocalDate ucDate1,
	            @RequestParam BigDecimal ducamount1,
	            @RequestParam String reasion1,
	            @RequestParam java.time.LocalDate fromDate1,
	            @RequestParam java.time.LocalDate toDate1,
	            @RequestParam BigDecimal pucamount1,
	            @RequestParam String updateAction,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=services.editPendingUCPPR19(pprPendingUcId, installment1, released1, utilized1, dueDate1, ucamount1, ucDate1,
							ducamount1, reasion1, fromDate1, toDate1, pucamount1, updateAction, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Details of Pending UC`s Update successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to Update Details of Pending UC`s");
				}
				catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to Update Details of Pending UC`s");
				}
				return "redirect:/pendingUCPPR19";	
	    }

}
