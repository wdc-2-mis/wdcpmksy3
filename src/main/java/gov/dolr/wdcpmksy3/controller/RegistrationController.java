package gov.dolr.wdcpmksy3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.dto.DistrictDTO;
import gov.dolr.wdcpmksy3.service.DistrictService;
import gov.dolr.wdcpmksy3.service.StateService;

@Controller
public class RegistrationController {

	@Autowired
    private StateService stateService;
	
	@Autowired
    private DistrictService districtService;
	
	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
       // model.addAttribute("user", new User());
		model.addAttribute("stateList", stateService.getAllStates());
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
}
