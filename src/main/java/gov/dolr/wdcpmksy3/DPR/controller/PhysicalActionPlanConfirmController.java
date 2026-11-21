package gov.dolr.wdcpmksy3.DPR.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.DPR.service.PhysicalActionPlanService;
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

}
