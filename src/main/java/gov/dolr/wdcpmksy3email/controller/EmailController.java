package gov.dolr.wdcpmksy3email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.service.OtpService;



@Controller
public class EmailController {
	
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
                            Model model) {

        if (otpService.verifyOtp(email, otp)) {
            return "success";
        }

        model.addAttribute("error", "Invalid OTP");
        model.addAttribute("email", email);

        return "verifyOtp";
    }

}
