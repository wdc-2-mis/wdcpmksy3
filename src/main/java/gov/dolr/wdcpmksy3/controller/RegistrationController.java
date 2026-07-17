package gov.dolr.wdcpmksy3.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.dto.DistrictDTO;
import gov.dolr.wdcpmksy3.dto.RegistrationDTO;
import gov.dolr.wdcpmksy3.dto.VerifyOtpDTO;

import gov.dolr.wdcpmksy3.service.DistrictService;
import gov.dolr.wdcpmksy3.service.RegistrationService;
import gov.dolr.wdcpmksy3.service.StateService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class RegistrationController {

	@Autowired
    private StateService stateService;
	
	@Autowired
    private DistrictService districtService;
	
		
	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
       // model.addAttribute("user", new User());
		int i = 1;
		model.addAttribute("stateList", stateService.getAllStates(i));
        return "registration"; 
    }
	
	
	@GetMapping("/register/districts/{stateCode}")
	@ResponseBody
	public List<DistrictDTO> getDistrictsByState(@PathVariable Integer stateCode) {

	    return districtService.getDistrictsByState(stateCode)
	            .stream()
	            .map(d -> {
	                DistrictDTO dto = new DistrictDTO();
	                dto.setDistCode(d.getDistCode());
	                dto.setDistName(d.getDistName());
	                return dto;
	            })
	            .toList();
	}
	
	
	@PostMapping("/register/sendOtp")
	@ResponseBody
	public ResponseEntity<String> sendOtp(
	        @RequestBody RegistrationDTO dto,
	        HttpServletRequest request) {

	    return ResponseEntity.ok(
	            registrationService.sendOtp(dto, request));
	}
	
	@PostMapping("/register/resendOtp")
	@ResponseBody
	public ResponseEntity<String> resendOtp(@RequestParam String email) {

	    return ResponseEntity.ok(
	            registrationService.resendOtp(email));
	}
	
	@PostMapping("/register/verifyOtp")
	@ResponseBody
	public ResponseEntity<String> verifyOtp(
	        @RequestBody VerifyOtpDTO dto, HttpServletRequest request){

	    return ResponseEntity.ok(
	            registrationService.verifyOtp(
	                    dto.getEmail(),
	                    dto.getOtp(), request));
	}


	
}
