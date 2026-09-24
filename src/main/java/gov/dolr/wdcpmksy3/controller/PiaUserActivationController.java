package gov.dolr.wdcpmksy3.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.dto.ActivatePiaUserRequest;
import gov.dolr.wdcpmksy3.dto.ActivatePiaUserResponse;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import gov.dolr.wdcpmksy3.service.PIAUserActivationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PiaUserActivationController {

	@Autowired
	private PIAUserActivationService piaUserActivationService; 
	
	public PiaUserActivationController(
            PIAUserActivationService piaUserActivationService) {

        this.piaUserActivationService =
                piaUserActivationService;
    }
	
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/newUserActiveDelete")
	public String slnaUserActive(HttpSession session, Model model) {
	    Object useridObj = session.getAttribute("userid");
	    if (useridObj == null) {
	        return "redirect:/login";
	    }

	    String userid = useridObj.toString();

	    Optional<WdcpmksyUserReg> userOpt = userRepository.findByUserId(userid);
	    if (userOpt.isEmpty()) {
	        return "redirect:/login";
	    }

	    WdcpmksyUserReg user = userOpt.get();
	    String loggedUserType = user.getUserType();

	    Map<String, String> userTypeList = new LinkedHashMap<>();

	    if ("SL".equalsIgnoreCase(loggedUserType)) {
	        // Only PI option for SL users
	        userTypeList.put("PI", "PIA");
	    } else if ("ADMIN".equalsIgnoreCase(loggedUserType)) {
	        // Full list for ADMIN
	        userTypeList.put("DL", "DOLR");
	        userTypeList.put("SL", "SLNA");
	        userTypeList.put("DI", "WCDC");
	        userTypeList.put("PI", "PIA");
	        userTypeList.put("NGO", "NGO");
	    }

	    Map<String, String> userStatusList = new LinkedHashMap<>();
	    userStatusList.put("Active", "Active");
	    userStatusList.put("InActive", "InActive");
	    userStatusList.put("New", "New");

	    model.addAttribute("userTypeList", userTypeList);
	    model.addAttribute("userStatusList", userStatusList);
	    model.addAttribute("loggedUserType", loggedUserType);

	    return "user/slnauseractivation";
	}

	
	@PostMapping("/deleteUser")
	@ResponseBody
	public Map<String, Object> deleteUser(
	        @RequestParam Integer regId,
	        HttpSession session) {

	    Map<String, Object> response = new HashMap<>();

	    try {

	        Object loggedUser = session.getAttribute("userid");

	        if (loggedUser == null) {

	            response.put("status", "ERROR");
	            response.put("message",
	                    "Session expired. Please login again.");

	            return response;
	        }

	        piaUserActivationService.deleteNewUser(regId);

	        response.put("status", "SUCCESS");
	        response.put("message",
	                "User deleted successfully.");

	    } catch (IllegalArgumentException e) {

	        response.put("status", "ERROR");
	        response.put("message", e.getMessage());

	    } catch (Exception e) {

	        e.printStackTrace();

	        response.put("status", "ERROR");
	        response.put("message",
	                "Unable to delete user.");
	    }

	    return response;
	}
	
	@PostMapping("/deactivateUser")
	@ResponseBody
	public Map<String, Object> deactivateUser(
	        @RequestParam Integer regId,
	        HttpSession session,
	        HttpServletRequest request) {

	    Map<String, Object> response = new HashMap<>();

	    try {

	        Object useridObj = session.getAttribute("userid");

	        if (useridObj == null) {

	            response.put("status", "ERROR");
	            response.put("message",
	                    "Session expired. Please login again.");

	            return response;
	        }

	        String updatedBy = useridObj.toString();

	        piaUserActivationService.deactivateUser(
	                regId,
	                updatedBy
	        );

	        response.put("status", "SUCCESS");
	        response.put("message",
	                "User has been inactivated successfully.");

	    } catch (IllegalArgumentException e) {

	        response.put("status", "ERROR");
	        response.put("message", e.getMessage());

	    } catch (Exception e) {

	        e.printStackTrace();

	        response.put("status", "ERROR");
	        response.put("message",
	                "Unable to inactivate user.");
	    }

	    return response;
	}
	
	@PostMapping("/activateExistingUser")
	@ResponseBody
	public Map<String, Object> activateExistingUser(
	        @RequestParam Integer regId,
	        HttpSession session) {

	    Map<String, Object> response = new HashMap<>();

	    try {

	        Object useridObj = session.getAttribute("userid");

	        if (useridObj == null) {

	            response.put("status", "ERROR");
	            response.put("message",
	                    "Session expired. Please login again.");

	            return response;
	        }

	        String updatedBy = useridObj.toString();

	        boolean mailSent =
	        		piaUserActivationService.reactivateUser(
	                        regId,
	                        updatedBy
	                );

	        response.put("status", "SUCCESS");

	        if (mailSent) {

	            response.put(
	                    "message",
	                    "User activated successfully. "
	                    + "Notification email has been sent."
	            );

	        } else {

	            response.put(
	                    "message",
	                    "User activated successfully, "
	                    + "but notification email could not be sent."
	            );
	        }

	    } catch (IllegalArgumentException e) {

	        response.put("status", "ERROR");
	        response.put("message", e.getMessage());

	    } catch (Exception e) {

	        e.printStackTrace();

	        response.put("status", "ERROR");
	        response.put(
	                "message",
	                "Unable to activate user."
	        );
	    }

	    return response;
	}
	
	@RequestMapping(value = "/slnausersrch", method = {RequestMethod.GET, RequestMethod.POST})
	public String searchUsers(
	        @RequestParam(required = false) String userId,
	        @RequestParam(required = false) String userName,
	        @RequestParam(required = false) String userType,
	        @RequestParam(required = false) String status,
	        HttpSession session,
	        Model model) {

	    Object useridObj = session.getAttribute("userid");
	    if (useridObj == null) {
	        return "redirect:/login";
	    }

	    String userid = useridObj.toString();

	    Optional<WdcpmksyUserReg> userOpt = userRepository.findByUserId(userid);
	    if (userOpt.isEmpty()) {
	        return "redirect:/login";
	    }

	    WdcpmksyUserReg loggedUser = userOpt.get();
	    String loggedUserType = loggedUser.getUserType();

	    Object stateCodeObject = session.getAttribute("stcode");
	    Integer stCode = stateCodeObject != null ? Integer.valueOf(stateCodeObject.toString()) : null;

	    userId = userId != null ? userId.trim() : "";
	    userName = userName != null ? userName.trim() : "";
	    userType = userType != null ? userType.trim() : "";
	    status = status != null ? status.trim() : "";

	    if (!userType.isEmpty() && status.isEmpty()) {
	        model.addAttribute("error", "Please select Status when User Type is selected.");
	        addDropdownData(model, loggedUserType);
	        return "user/slnauseractivation";
	    }

	    if (!status.isEmpty() && userType.isEmpty()) {
	        model.addAttribute("error", "Please select User Type when Status is selected.");
	        addDropdownData(model, loggedUserType);
	        return "user/slnauseractivation";
	    }

	    if (userId.isEmpty() && userName.isEmpty() && userType.isEmpty() && status.isEmpty()) {
	        model.addAttribute("error", "Please provide at least one search criteria.");
	        addDropdownData(model, loggedUserType);
	        return "user/slnauseractivation";
	    }

	    List<WdcpmksyUserReg> userList =
	            piaUserActivationService.searchPiaUsers(userId, userName, userType, status, stCode, loggedUserType);

	    model.addAttribute("userList", userList);
	    model.addAttribute("searchUserId", userId);
	    model.addAttribute("searchUserName", userName);
	    model.addAttribute("searchUserType", userType);
	    model.addAttribute("searchStatus", status);

	    addDropdownData(model, loggedUserType);

	    return "user/slnauseractivation";
	}

	private void addDropdownData(Model model, String loggedUserType) {
	    Map<String, String> userTypeList = new LinkedHashMap<>();

	    if ("SL".equalsIgnoreCase(loggedUserType)) {
	        userTypeList.put("PI", "PIA");
	    } else if ("ADMIN".equalsIgnoreCase(loggedUserType)) {
	        userTypeList.put("DL", "DOLR");
	        userTypeList.put("SL", "SLNA");
	        userTypeList.put("DI", "WCDC");
	        userTypeList.put("PI", "PIA");
	        userTypeList.put("NGO", "NGO");
	    }

	    Map<String, String> userStatusList = new LinkedHashMap<>();
	    userStatusList.put("Active", "Active");
	    userStatusList.put("InActive", "InActive");
	    userStatusList.put("New", "New");

	    model.addAttribute("userTypeList", userTypeList);
	    model.addAttribute("userStatusList", userStatusList);
	}

	
	@PostMapping("/activatePiaUser")
    @ResponseBody
    public ActivatePiaUserResponse activatePiaUser(
            @RequestBody ActivatePiaUserRequest request,
            HttpSession session) {

        // Login check
        Object userid =
                session.getAttribute("userid");

        if (userid == null) {

            return new ActivatePiaUserResponse(
                    "ERROR",
                    "Session expired. Please login again.",
                    null,
                    null,
                    null
            );
        }


        return piaUserActivationService
                .activateUser(request.getRegId());
    }
}	
