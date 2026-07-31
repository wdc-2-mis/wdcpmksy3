package gov.dolr.wdcpmksy3.PPR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprAreaCoveredController {
	
	@Autowired
    private DistrictService districtService;
	
	
	@GetMapping("/areaCoveredUnderWP")
    public String areaCoveredUnderWP(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        return "ppr/areaCovered";
	}
	

}
