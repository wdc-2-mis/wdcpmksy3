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
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.PPR.repository.PprWcdcUnspentBalanceRepository;
import gov.dolr.wdcpmksy3.PPR.service.UnspentBalanceServices;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UnspentBalanceController {

	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private PprWcdcUnspentBalanceRepository unblance;
	
	@Autowired
    private UnspentBalanceServices serv;
	
	@GetMapping("/unspentBalancePPR20")
    public String pendingUCPPR19(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
        if(userid==null){

            return "redirect:/login";
        }
        List<PprWcdcUnspentBalance> records = unblance.findByPpr_District_State_StCode(stcode);
        model.addAttribute("records", records);
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        
        return "ppr/unspentBalancePPR20";
    }
	
	 @PostMapping("/saveUnspentBalancePPR20")
	 public String saveUnspentBalancePPR20(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer district,
	    		@RequestParam Integer project,
	            @RequestParam BigDecimal totcost,
	            @RequestParam BigDecimal state,
	            @RequestParam BigDecimal dolr,
	            @RequestParam BigDecimal interest,
	            @RequestParam BigDecimal total,
	            @RequestParam BigDecimal balance,
	            @RequestParam String action,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=serv.saveUnspentBalancePPR20(project, totcost, state, dolr, interest, 
							total, balance, action, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Details of Unspent balance as on date District-wise (PPR20) saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to saved Details of Unspent balance as on date District-wise (PPR20)");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to saved Details of Unspent balance as on date District-wise (PPR20)");
					}
				return "redirect:/unspentBalancePPR20";	
	    }
	 
	 	@GetMapping("/deleteUnspentBalancePPR20")
	    public String deletePendingUCPPR19(HttpSession session, Model model, @RequestParam("id") Integer id,  
	    		RedirectAttributes redirectAttributes) {

			
			String userid=(String)session.getAttribute("userid");
			try {
				
		        if(userid==null){
		
		            return "redirect:/login";
		        }
		        PprWcdcUnspentBalance data = unblance.findById(id).orElse(null);
	            if (data == null) {
	                redirectAttributes.addFlashAttribute("error", "Record not found.");
	                return "redirect:/pendingUCPPR19";
	            }
	            
	            if (unblance.existsById(id)) {
	            	unblance.deleteById(id);
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
			return "redirect:/unspentBalancePPR20";
	    }
	 
	 	@GetMapping("/completeUnspentBalancePPR20")
	    public String completeUnspentBalancePPR20(HttpSession session, Model model, @RequestParam("id") Integer id,  
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
	       
	        return "redirect:/unspentBalancePPR20";
	    }
	 
	 @PostMapping("/editUnspentBalancePPR20")
	 public String editUnspentBalancePPR20(HttpSession session, Model model, HttpServletRequest request,
	    		
	    		@RequestParam Integer pprUnspentBalanceId,
	    		@RequestParam BigDecimal totcost1,
	            @RequestParam BigDecimal state1,
	            @RequestParam BigDecimal dolr1,
	            @RequestParam BigDecimal interest1,
	            @RequestParam BigDecimal total1,
	            @RequestParam BigDecimal balance1,
	            @RequestParam String updateAction,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=serv.editUnspentBalancePPR20(pprUnspentBalanceId, totcost1, state1, dolr1, interest1, 
							total1, balance1, updateAction, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Unspent balance as on date District-wise (PPR20) Update successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to Update Unspent balance as on date District-wise (PPR20)");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to Update Unspent balance as on date District-wise (PPR20)");
					}
				return "redirect:/unspentBalancePPR20";	
	    }
}
