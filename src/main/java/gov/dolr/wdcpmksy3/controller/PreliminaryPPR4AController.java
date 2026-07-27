package gov.dolr.wdcpmksy3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.PPRWcdcDetails;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;
import gov.dolr.wdcpmksy3.repository.PPRWcdcDetailsRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import gov.dolr.wdcpmksy3.service.InstitutionalStructureServiceImpl;
import gov.dolr.wdcpmksy3.service.PPRWcdcDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PreliminaryPPR4AController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private InstitutionalStructureServiceImpl isserv;
	
	@Autowired
	private InstitutionalStructureRepository institutionalRepo;
	
	@Autowired
	private PPRWcdcDetailsRepository pprwdcddetail;
	
	@Autowired
	private PPRWcdcDetailsService pprWcdcDetailsService;
	
	@Value("${upload.path1}")
    private String uploadPath;
	
	@GetMapping("/preliminaryPPR4A")
    public String ppr1(HttpSession session, Model model) 
	{
		System.out.println("PPR4 Session ID = " + session.getId());
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

        if(userid==null){

            return "redirect:/login";
        }
        
        List<Object[]> list=pprWcdcDetailsService.getPPR4List(stcode);
        
        for (Object[] row : list) {

		    Integer id = (Integer) row[0];
		    System.out.println("Idkdy : " + id);
		}
        
        model.addAttribute("ppr4List", pprWcdcDetailsService.getPPR4List(stcode));
        model.addAttribute("distList", districtService.getDistrictsByState(stcode));
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
        return "ppr4";
    }
	
	@GetMapping("/checkDistrictExists")
	@ResponseBody
	public String checkDistrictExists(@RequestParam Integer dcode) {

		System.out.println("kdy" +dcode);
	    boolean exists = pprwdcddetail.existsByDcode(dcode);

	    if (exists) {
	        return "EXISTS";
	    }

	    return "NOT_EXISTS";
	}
	
	@PostMapping("/savePreliminaryPPR4A")
	public String savePreliminaryPPR4A(HttpSession session,
	                                   Model model,
	                                   HttpServletRequest request,
	                                   @RequestParam Integer district,
	                                   @RequestParam String agency,
	                                   @RequestParam String chairman,
	                                   @RequestParam java.time.LocalDate MoU,
	                                   @RequestParam MultipartFile MoUfile,
	                                   @RequestParam Character action,
	                                   RedirectAttributes redirectAttributes) throws IOException {

	    String userid = (String) session.getAttribute("userid");

	    if (userid != null) {

	        Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	        Integer regid = Integer.parseInt(session.getAttribute("regid").toString());

	        File dir = new File(uploadPath);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	        Integer id =0;
	        List<Object[]> list = isserv.getPPR1List(stcode);
			for (Object[] row : list) {

			    id = (Integer) row[0];
			    System.out.println("Id : " + id);
			}

	            String mouFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 6)+ "_" + MoUfile.getOriginalFilename();
	            MoUfile.transferTo(new File(uploadPath + mouFileName));

	            InstitutionalStructure inst = institutionalRepo.getReferenceById(id.longValue());
	            PPRWcdcDetails obj = new PPRWcdcDetails();
	            obj.setInstitutionalStructure(inst);
	            obj.setDcode(district);
	            obj.setExecutingAgency(agency);
	            obj.setChairmanStatus(chairman);
	            obj.setMouDate(MoU);
	            obj.setMouFile(uploadPath + mouFileName);
	            obj.setStatus(action);
	            obj.setCreatedBy(userid);
	            obj.setCreatedDate(LocalDateTime.now());
	           // obj.setRequestIp(getClientIpAddr(request));
	            pprwdcddetail.save(obj);

	            redirectAttributes.addFlashAttribute( "success", "Record Saved Successfully."
	            );

	        
	        return "redirect:/preliminaryPPR4A";

	    } else {

	        return "redirect:/login";
	    }
	}
	
    @GetMapping("/deletePreliminaryPPR4A")
    public String deletePreliminaryPPR4A(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
	        PPRWcdcDetails data = pprWcdcDetailsService.getById(id);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/preliminaryPPR4A";
            }
            
            deleteFile(data.getMouFile());
            pprWcdcDetailsService.delete(id);

            model.addAttribute("success", "Record deleted successfully.");

            model.addAttribute("ppr4List", pprWcdcDetailsService.getPPR4List(stcode));	
    		model.addAttribute("statename", statename);
    		model.addAttribute("stcode", stcode);

        } 
        catch (Exception e) {

        	 model.addAttribute("error", "Unable to delete record.");
            e.printStackTrace();
        }
        return "ppr4";
    }
    
    private void deleteFile(String filePath) {

        if (filePath != null && !filePath.isBlank()) 
        {
            try {
                Path path = Paths.get(filePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
    @GetMapping("/completePreliminaryPPR4A")
    public String completePreliminaryPPR4A(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		 	try {

		 		if(userid==null){

		            return "redirect:/login";
		        }
		 		boolean updated= pprWcdcDetailsService.completeRecordPPR4(id);
		        if (updated) {
		        	model.addAttribute("success", "Record completed successfully.");
		        } 
		        else {
		        	model.addAttribute("success", "Record not found.");
		        }
		        model.addAttribute("ppr4List",
		                pprWcdcDetailsService.getPPR4List(stcode));
	    		model.addAttribute("statename", statename);
	    		model.addAttribute("stcode", stcode);
		    } 
		 	catch (Exception e) {

		 		model.addAttribute("error", "Unable to complete record.");
		    }
       
        return "ppr4";
    }
    
    
    

}
