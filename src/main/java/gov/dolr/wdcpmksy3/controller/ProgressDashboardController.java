package gov.dolr.wdcpmksy3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProgressDashboardController {

	@GetMapping("/progressdashboard")
    public String dashboard(HttpSession session,
                            Model model) {

        Integer regid = (Integer) session.getAttribute("regid");
        String userType = (String) session.getAttribute("usertype");
        Integer stateCode = (Integer) session.getAttribute("stcode");

        if(regid == null){
            return "redirect:/login";
        }

        switch (userType) {

            case "ADMIN":
                return "redirect:/admin/dashboard";

            case "DL":
                return "redirect:/dl/dashboard";

            case "SL":
                return "redirect:/sl/dashboard";

            case "DI":
                return "redirect:/di/dashboard";

            case "PI":
                return "redirect:/pi/dashboard";

            default:
                return "login";
        }
    }
}
