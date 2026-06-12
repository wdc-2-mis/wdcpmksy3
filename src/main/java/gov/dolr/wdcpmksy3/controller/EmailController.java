package gov.dolr.wdcpmksy3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class EmailController {
	
	HttpSession session;
	
	@Autowired
    private OtpService otpService;

    @GetMapping("/login")
    public String loginPage() {
    	
        return "login";
       
    }
    
    @GetMapping("/checkemail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = otpService.checkEmailExists(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam String email,
                          Model model) {
    	
    	 if (email == null || email.trim().isEmpty()) {
    		 model.addAttribute("email", "Enter Correct Email-Id");
    	        return "login";
    	 }

        otpService.sendOtp(email);

        model.addAttribute("email", email);

        return "verifyOtp";
    }

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
        		session.setAttribute("regid", regid);
        	    String address = (String) row[1];
        	    String department = (String) row[2];
        	    String mobile = (String) row[5];
        	    String statename = (String) row[13];
        	    String usertype = (String) row[9];
        	    
        	    session.setAttribute("usertype", usertype);
        	    session.setAttribute("stcode", regid);
        	    session.setAttribute("statename", statename);
        	    session.setAttribute("mobile", mobile);

        	  //  System.out.println("ID: " + regid + ", address: " + address + ", department: " + department);
        	}
        	
        	
        	
            return "success";
        }
        
        else {
        	model.addAttribute("error", "Invalid OTP");
        	return "login";
        }

        
       

        
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(true);

        if (session != null) {
            session.invalidate(); // destroy session
        }

        return "redirect:/login";
    }

}
