package gov.dolr.wdcpmksy3.DPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.DPR.service.DPRELPService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DPRELPController {
	
	@Autowired
	private DPRELPService dprservice;
	
	@GetMapping("/epalivproddetails")
	public String epalivproddetails(
	        HttpSession session, Model model) {

		
	    Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    Object userid = session.getAttribute("userid");

	    if (userid == null) {
	        return "redirect:/login";
	    }

	    model.addAttribute("distList",
	    		dprservice.getDPRDistrictsByState(stcode));
	    
	    return "dpr/epalivproddtls";
	}

}
