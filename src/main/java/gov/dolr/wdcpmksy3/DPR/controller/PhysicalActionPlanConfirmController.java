package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.service.PhysicalActionPlanService;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PhysicalActionPlanConfirmController {
	
	
	@Autowired
	private PhysicalActionPlanService  physicalActionPlanService;
	
	
	@GetMapping("/wcdcslnaPhyActPlan")
    public String wcdcslnaPhyActPlan(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		String usertype=(String)session.getAttribute("usertype");
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		List<Object[]> finalList = new ArrayList<>();
        if(userid==null){

            return "redirect:/login";
        }
        	
        model.addAttribute("planList", physicalActionPlanService.viewARPlan(regid, usertype)); 
        model.addAttribute("comPlanList", physicalActionPlanService.viewCompletePlan(regid, usertype));
        model.addAttribute("movementPlanList", physicalActionPlanService.viewAAPMovement(regid, usertype));
        
        return "dpr/physicalActionPlanForWCDCSLNA";
    }
	
	 @PostMapping("/approveRejectAAP")
	 public String approveRejectAAP(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer planid,
	            @RequestParam Character action,
	            @RequestParam String remark,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				String usertype=(String)session.getAttribute("usertype");
				Integer sentfrom = Integer.parseInt(session.getAttribute("regid").toString());
				String value=null;
				String res =null;
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom, action, planid, usertype);
					if (sentto > 0 && sentfrom > 0)
						value = physicalActionPlanService.forwardByWCDC(sentto, sentfrom, planid, action, remark, usertype);
					if (value.equals("success") && usertype.equals("SL")) {
						res = "state";
					}
					if (value.equals("success") && usertype.equals("DI")) {
						res = "district";
					}
					
					
					
					
					if(save)
						redirectAttributes.addFlashAttribute( "success1", "DPR NRM Target saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error1", "Unable to saved DPR NRM Target.");
				}
				catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error1", "Unable to DPR NRM Target.");
				}
				return "redirect:/wcdcslnaPhyActPlan";	
	    }

}
