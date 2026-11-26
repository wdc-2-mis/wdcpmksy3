package gov.dolr.wdcpmksy3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.service.LoginDashboardService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sl")
public class SLNADashboard {

	@Autowired
	private LoginDashboardService dashboardService;
	
	@Autowired
	private FinYearService finService;
	
	 @GetMapping("/dashboard")
	    public String dashboard(
	            HttpSession session,
	            Model model) {

	        Integer regid =
	                (Integer) session.getAttribute("regid");

	        Integer stateCode =
	                (Integer) session.getAttribute("stcode");

	        String username =
	                (String) session.getAttribute("username");

	        String statename =
	                (String) session.getAttribute("statename");
	        
	        String finYear = finService.getfinYear();

	        if (regid == null || stateCode == null) {
	            return "redirect:/login";
	        }

	        // Fetch State-wise dashboard KPI data
	        model.addAttribute(
	                "dashboard",
	                dashboardService.getDashboardData(stateCode)
	        );

	        model.addAttribute(
	                "dashboardStatus",
	                dashboardService.getDashboardStatus(stateCode)
	        );
	        
	        model.addAttribute("username", username);
	        model.addAttribute("stateCode", stateCode);
	        model.addAttribute("statename", statename);
	        model.addAttribute("finYear", finYear);

	        return "dashboard/slDashboard";
	    }
}
	

