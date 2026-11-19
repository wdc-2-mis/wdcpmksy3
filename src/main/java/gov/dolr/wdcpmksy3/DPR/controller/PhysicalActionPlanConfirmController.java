package gov.dolr.wdcpmksy3.DPR.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class PhysicalActionPlanConfirmController {
	
	
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
        	
        
        
        return "dpr/physicalActionPlanForWCDCSLNA";
    }

}
