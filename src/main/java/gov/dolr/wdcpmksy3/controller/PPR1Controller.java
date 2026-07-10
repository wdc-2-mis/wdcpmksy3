package gov.dolr.wdcpmksy3.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.service.InstitutionalStructureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPR1Controller {
	
	@Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private InstitutionalStructureService service;
	
	@GetMapping("/ppr1")
    public String ppr1(HttpSession session, Model model) {

		
		System.out.println("PPR1 Session ID = " + session.getId());
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

        if(userid==null){

            return "redirect:/login";
        }
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
        return "ppr1";
    }
	

    @PostMapping("/saveInstitutionalStructure")
    public String saveInstitutionalStructure(HttpSession session, Model model, HttpServletRequest request,

            @RequestParam Integer stcode,
            @RequestParam String stateName,
            @RequestParam String slnaType,
            @RequestParam java.time.LocalDate notificationDate,
            @RequestParam MultipartFile notificationFile,
            @RequestParam java.time.LocalDate mouDate,
            @RequestParam MultipartFile mouFile,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) throws IOException {
    	
		String userid=(String)session.getAttribute("userid");
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		if(userid!=null) {
			
				
	        File dir = new File(uploadPath);
	
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	
	        String notificationFileName = UUID.randomUUID() + "_"+ notificationFile.getOriginalFilename();
	
	        String mouFileName = UUID.randomUUID() + "_"+ mouFile.getOriginalFilename();
	
	        notificationFile.transferTo(new File(uploadPath + notificationFileName));
	
	        mouFile.transferTo(new File(uploadPath + mouFileName));
	
	        InstitutionalStructure obj = new InstitutionalStructure();
	
	        obj.setSt_code(stcode);
	        obj.setSlnaType(slnaType);
	        obj.setNotificationDate(notificationDate);
	        obj.setNotificationFile(notificationFileName);
	        obj.setMouDate(mouDate);
	        obj.setMouFile(mouFileName);
	        obj.setStatus(action);
	        obj.setCreatedBy(userid);
	        obj.setCreatedDate(LocalDateTime.now());
	        obj.setRequestIp(getClientIpAddr(request));
	        
	        service.save(obj);
	
	        redirectAttributes.addFlashAttribute("success","Record Saved Successfully.");
	
	        return "redirect:/ppr1";
		}
		else {
			return "redirect:/login";
		}
		
		
    }
    
    public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}

}
