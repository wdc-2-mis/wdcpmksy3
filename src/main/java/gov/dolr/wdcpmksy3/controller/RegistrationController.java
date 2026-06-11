package gov.dolr.wdcpmksy3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

	
	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
       // model.addAttribute("user", new User());
        return "registration"; 
    }
	
}
