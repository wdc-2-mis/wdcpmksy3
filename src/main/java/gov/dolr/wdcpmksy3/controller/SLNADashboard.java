package gov.dolr.wdcpmksy3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sl")
public class SLNADashboard {

	@GetMapping("/dashboard")
    public String dashboard(HttpSession session,
                            Model model) {

        Integer regid = (Integer) session.getAttribute("regid");
        Integer stateCode = (Integer) session.getAttribute("stcode");
        String username = (String) session.getAttribute("username");
        String statename=session.getAttribute("statename").toString();
        if(regid == null){
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        model.addAttribute("stateCode", stateCode);
        model.addAttribute("statename", statename);
       

        return "dashboard/slDashboard";
    }
}
	

