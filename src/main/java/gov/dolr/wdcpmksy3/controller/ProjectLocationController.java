package gov.dolr.wdcpmksy3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gov.dolr.wdcpmksy3.dto.LoginDTO;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectLocationController {

	
	@GetMapping("/piaPjtNotLocatiaon")
	public String piaPjtNotLocatiaon(HttpSession session,
	                                 Model model) {
		System.out.println("PIA Session = " + session.getId());
	    model.addAttribute("stateName",
	            session.getAttribute("statename"));

	    return "pialocation";
	}
}
