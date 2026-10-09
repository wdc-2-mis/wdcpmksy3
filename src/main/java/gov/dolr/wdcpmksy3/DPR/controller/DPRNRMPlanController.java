package gov.dolr.wdcpmksy3.DPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DPRNRMPlanController {
	
	@Autowired
    private DistrictService districtService;
	
	
	@GetMapping("/createDPRNRMPlan")
    public String createDPRNRMPlan(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
        if(userid==null){

            return "redirect:/login";
        }
        
        boolean exists=false;
      //  exists=pprAreaService.isPprCompleted(stcode);
        model.addAttribute("existssl", exists);
		if (!exists) {
	        model.addAttribute( "error1", "Please complete the Preliminary Project Report");
	    }
		
      //  List<PprWcdcUnspentBalance> records = unblance.findByPpr_District_State_StCode(stcode);
     //   model.addAttribute("records", records);
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        
        return "dpr/viewDPRNRMTarget";
    }

}
