package gov.dolr.wdcpmksy3.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.dto.MenuMap;
import gov.dolr.wdcpmksy3.service.MenuService;
import gov.dolr.wdcpmksy3.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class EmailController {
	
	HttpSession session;
	
	@Autowired
    private OtpService otpService;
	
	@Autowired
	private MenuService menuService;

    @GetMapping("/emaillogin")
    public String loginPage() {
    	
        return "emaillogin";
       
    }
    
    @GetMapping("/checkemail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = otpService.checkEmailExists(email);
        return ResponseEntity.ok(exists);
    }

	/*
	 * @PostMapping("/sendOtp") public String sendOtp(@RequestParam String email,
	 * Model model) {
	 * 
	 * if (email == null || email.trim().isEmpty()) { model.addAttribute("email",
	 * "Enter Correct Email-Id"); return "emaillogin"; }
	 * 
	 * otpService.sendOtp(email);
	 * 
	 * model.addAttribute("email", email);
	 * 
	 * return "verifyOtp"; }
	 */

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp,
                            HttpSession session,
                            Model model) {

        if (otpService.verifyOtp(email, otp)) {
        	
        	session.setAttribute("useremail", email);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
        	model.addAttribute("email", email);
        	session.setAttribute("user", email);
        	model.addAttribute("timeoutSeconds", session.getMaxInactiveInterval());
        	
        	model.addAttribute("userList", otpService.getUserList(email));
        	
        	List<Object[]> rows = otpService.getUserList(email);

        	for (Object[] row : rows) {
        	    
        		Integer regid = (Integer) row[0];
        	    String address = (String) row[1];
        	    String department = (String) row[2];
        	    String mobile = (String) row[5];
        	    String statename = (String) row[13];
        	    String usertype = (String) row[9];
        	    String username = (String) row[8];
        	    
        	    model.addAttribute("username", username);
        	    session.setAttribute("regid", regid);
        	    session.setAttribute("username", username);
        	    session.setAttribute("usertype", usertype);
        	    session.setAttribute("stcode", regid);
        	    session.setAttribute("statename", statename);
        	    session.setAttribute("mobile", mobile);

        	    model.addAttribute("menus", menuService.getMenuUserId(regid));
        	  
        	}
        	
        	
        	
            return "success";
        }
        
        else {
        	model.addAttribute("error", "Invalid OTP");
        	return "emaillogin";
        }

        
       

        
    }
    
    

}
