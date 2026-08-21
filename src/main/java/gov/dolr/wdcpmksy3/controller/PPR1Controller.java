package gov.dolr.wdcpmksy3.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionary;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionaryWorkExperience;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionary;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionaryWorkExperience;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;
import gov.dolr.wdcpmksy3.repository.PprWcdcFunctionaryRepository;
import gov.dolr.wdcpmksy3.repository.PprWcdcFunctionaryWorkExperienceRepository;
import gov.dolr.wdcpmksy3.repository.SlnaFunctionaryRepository;
import gov.dolr.wdcpmksy3.repository.SlnaFunctionaryWorkExperienceRepository;
import gov.dolr.wdcpmksy3.service.DesignationService;
import gov.dolr.wdcpmksy3.service.DistrictService;
import gov.dolr.wdcpmksy3.service.InstitutionalStructureService;
import gov.dolr.wdcpmksy3.service.InstitutionalStructureServiceImpl;
import gov.dolr.wdcpmksy3.service.PPRWcdcDetailsServiceImpl;
import gov.dolr.wdcpmksy3.service.QualificationService;
import gov.dolr.wdcpmksy3.service.SlnaFunctionaryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPR1Controller {
	
	@Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private InstitutionalStructureService service;
    
    @Autowired
    private InstitutionalStructureServiceImpl isserv;
    
    @Autowired
    InstitutionalStructureRepository repository;
    
    @Autowired
    DesignationService dserv;
    
    @Autowired
    QualificationService qserv;
    
    @Autowired
    SlnaFunctionaryServiceImpl  slnaFunctionaryService;
    
    @Autowired
    SlnaFunctionaryRepository slnafuncrep;
    
    @Autowired
    SlnaFunctionaryWorkExperienceRepository exprep;
    
    @Autowired
    private DistrictService districtService;
    
    @Autowired
    private PPRWcdcDetailsServiceImpl ppwd;
    
    @Autowired
    private PprWcdcFunctionaryRepository wdcrep;
    
    @Autowired
    private PprWcdcFunctionaryWorkExperienceRepository wdcexprep;
	
	@GetMapping("/institutionalStructurePPR1")
    public String ppr1(HttpSession session, Model model) 
	{
		System.out.println("PPR1 Session ID = " + session.getId());
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

        if(userid==null){

            return "redirect:/login";
        }
        model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
        return "ppr1";
    }

    @PostMapping("/saveInstitutionalStructurePPR1")
    public String saveInstitutionalStructurePPR1(HttpSession session, Model model, HttpServletRequest request,
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
	        boolean exists =false;
	        
	        exists = repository.existsByStCode(stcode);
	        
	        if (!exists) {
	
	        String notificationFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 6) + "_"+ notificationFile.getOriginalFilename();
	
	        String mouFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 6) + "_"+ mouFile.getOriginalFilename();
	
	        notificationFile.transferTo(new File(uploadPath + notificationFileName));
	
	        mouFile.transferTo(new File(uploadPath + mouFileName));
	
	        InstitutionalStructure obj = new InstitutionalStructure();
	
	        obj.setStCode(stcode);
	        obj.setSlnaType(slnaType);
	        obj.setNotificationDate(notificationDate);
	        obj.setNotificationFile(uploadPath+notificationFileName);
	        obj.setMouDate(mouDate);
	        obj.setMouFile(uploadPath+mouFileName);
	        obj.setStatus(action.charAt(0));
	        obj.setCreatedBy(userid);
	        obj.setCreatedDate(LocalDateTime.now());
	        obj.setRequestIp(getClientIpAddr(request));
	        
	        service.save(obj);
	        
	        redirectAttributes.addFlashAttribute("success","Record Saved Successfully.");
	        }
	        else {
	        	
	        	redirectAttributes.addFlashAttribute("error","Record already Exists, Only one entry allow.");
	        }
	
	        
	        
	        model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
	
	        return "redirect:/institutionalStructurePPR1";
		}
		else {
			return "redirect:/login";
		}
		
    }
    
    @GetMapping("/viewPdf1")
    public ResponseEntity<Resource> viewPdf1(@RequestParam String path) throws IOException {

        Path filePath = Paths.get(path);
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }
        // server 
      //  return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
             //   .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=/"" + resource.getFilename() + "/"").body(resource);
        // local
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
    
    @GetMapping("/viewPdf2")
    public ResponseEntity<Resource> viewPdf2(@RequestParam Long id,
            @RequestParam String type) throws IOException {

    		InstitutionalStructure data = isserv.getById(id);
    		if (data == null) {
    			return ResponseEntity.notFound().build();
    		}
    		String filePath = null;

    		if ("notification".equalsIgnoreCase(type)) 
    		{
    			filePath = data.getNotificationFile();
    		} 
    		else if ("mou".equalsIgnoreCase(type)) 
    		{
    			filePath = data.getMouFile();
    		} 
    		else {
    			return ResponseEntity.badRequest().build();
    		}

    		Path path = Paths.get(filePath);
    		Resource resource = new UrlResource(path.toUri());
    		if (!resource.exists() || !resource.isReadable()) {
    			return ResponseEntity.notFound().build();
    		}

    		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
    				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(resource);
    }
    
    @GetMapping("/viewPdfInstitutionalStructure")
    public ResponseEntity<Resource> viewPdf(@RequestParam Long id,
                        @RequestParam String type) throws IOException {

        InstitutionalStructure data = isserv.getById(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        String filePath = null;

        if ("notification".equalsIgnoreCase(type)) 
        {
            filePath = data.getNotificationFile();
        } 
        else if ("mou".equalsIgnoreCase(type)) 
        {
            filePath = data.getMouFile();
        } 
        else {
            return ResponseEntity.badRequest().build();
        }

        if (filePath == null || filePath.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String fileName = path.getFileName().toString();

        // Remove UUID/prefix before first underscore (_)
        int index = fileName.indexOf("_");
        if (index != -1 && index < fileName.length() - 1) {
            fileName = fileName.substring(index + 1);
        }
        // local
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
        // server
       // return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
        //        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=/"" + fileName + "/"").body(resource);
    }
    
    @GetMapping("/deleteInstitutionalStructurePPR1")
    public String deletePPR1(HttpSession session, Model model, @RequestParam("id") Long id,  
    		RedirectAttributes redirectAttributes) {

		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
            InstitutionalStructure data = isserv.getById(id);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/institutionalStructurePPR1";
            }
            
            deleteFile(data.getNotificationFile());
            deleteFile(data.getMouFile());
            isserv.delete(id);

            redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
            model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
    		model.addAttribute("statename", statename);
    		model.addAttribute("stcode", stcode);

        } 
        catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            e.printStackTrace();
        }
        return "ppr1";
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
    
    @GetMapping("/completeInstitutionalStructurePPR1")
    public String completePPR1(HttpSession session, Model model, @RequestParam("id") Long id,  
    		RedirectAttributes redirectAttributes) {

		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		 	try {

		 		if(userid==null){

		            return "redirect:/login";
		        }
		        boolean updated = isserv.completeRecordPPR1(id);
		        if (updated) {
		            redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
		        } 
		        else {
		            redirectAttributes.addFlashAttribute("success", "Record not found.");
		        }
		        model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
	    		model.addAttribute("statename", statename);
	    		model.addAttribute("stcode", stcode);
		    } 
		 	catch (Exception e) {

		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
		    }
       
        return "ppr1";
    }
    
    @GetMapping("/editInstitutionalStructurePPR1")
    public String editPPR1(@RequestParam Long id, Model model, HttpSession session) {

    	InstitutionalStructure data = isserv.getById(id);
        model.addAttribute("editData", data);
        String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

		if(userid==null){

		    return "redirect:/login";
		}
		model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
 		model.addAttribute("statename", statename);
 		model.addAttribute("stcode", stcode);
 	//	model.addAttribute("stcode", data.getPpr_inst_str_id());

        return "editppr1";
    }
    
    @PostMapping("/updateInstitutionalStructurePPR1")
    public String updateInstitutionalStructurePPR1(HttpSession session, Model model, HttpServletRequest request,

            @RequestParam Long pprid,
            @RequestParam String stateName1,
            @RequestParam String slnaType1,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate notificationDate1,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate mouDate1,
            @RequestParam(required = false) MultipartFile notificationFile1,
            @RequestParam(required = false) MultipartFile mouFile1,
            RedirectAttributes redirectAttributes) throws IOException {
    	
		String userid=(String)session.getAttribute("userid");
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String notificationFileName =null;
		String mouFileName = null;
		if(userid!=null) 
		{
	        File dir = new File(uploadPath);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	        
	        InstitutionalStructure data = isserv.getById(pprid);	
			
	        if (notificationFile1 != null && !notificationFile1.isEmpty()) {
	        	deleteFile(data.getNotificationFile());
	        	notificationFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 6) + "_"+ notificationFile1.getOriginalFilename();
	        	notificationFile1.transferTo(new File(uploadPath + notificationFileName));
	 	        data.setNotificationFile(uploadPath+notificationFileName);
	        }
	        if (mouFile1 != null && !mouFile1.isEmpty()) {
	        	deleteFile(data.getMouFile());
	        	mouFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 6) + "_"+ mouFile1.getOriginalFilename();
	        	data.setMouFile(uploadPath+mouFileName);
	        	mouFile1.transferTo(new File(uploadPath + mouFileName));
	        }
	        
	        if(slnaType1!=null)
	        data.setSlnaType(slnaType1);
	        if(notificationDate1!=null)
	        data.setNotificationDate(notificationDate1);
	        if(mouDate1!=null)
	        data.setMouDate(mouDate1);
	        
	        data.setUpdatedBy(userid);
	        data.setUpdatedDate(LocalDate.now());
	        data.setRequestIp(getClientIpAddr(request));
	        
	        service.save(data);
	
	        redirectAttributes.addFlashAttribute("success","Record update Successfully.");
	        
	        model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
	
	        return "redirect:/institutionalStructurePPR1";
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
    
    @GetMapping("/slnaFunctionariesPPR3")
    public String slnaFunctionariesPPR3(HttpSession session, Model model) 
	{
		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
		List<Object[]> list = isserv.getPPR1List(stcode);
		for (Object[] row : list) {

		    Integer id = (Integer) row[0];
		    System.out.println("Id : " + id);
		}
		
        if(userid==null){

            return "redirect:/login";
        }
        List<Object[]> functionariesList = slnaFunctionaryService.getFunctionariesList(stcode);

        List<Object[]> finalList = new ArrayList<>();

        int srNo = 1;
        Integer previousId = null;

        for (Object[] row : functionariesList) {

            Integer currentId = ((Number) row[0]).intValue();

            // Create a new array with one extra column for serial number
            Object[] newRow = Arrays.copyOf(row, row.length + 1);

            if (previousId == null || !previousId.equals(currentId)) {
                newRow[row.length] = srNo++;   // Serial No.
            } else {
                newRow[row.length] = "";       // Blank for duplicate rows
            }
            finalList.add(newRow);
            previousId = currentId;
        }

        model.addAttribute("functionariesList", finalList);
      //  model.addAttribute("functionariesList", slnaFunctionaryService.getFunctionariesList(stcode));
        model.addAttribute("designationList", dserv.getAllDesignationDetails());
        model.addAttribute("qualificationList", qserv.getAllQualification());
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
        return "slnaFunctionariesPPR3";
    } 
    
    @PostMapping("/saveSLNAFunctionariesPPR3")
    public String saveSLNAFunctionariesPPR3(HttpSession session, Model model, HttpServletRequest request,
    		@RequestParam String level,
    		@RequestParam String fname,
            @RequestParam String lname,
            @RequestParam Integer designation,
            @RequestParam Integer qualification,
            @RequestParam String workallocation,
            @RequestParam BigDecimal slr,
            @RequestParam BigDecimal slnr,
            @RequestParam BigDecimal dlr,
            @RequestParam BigDecimal dlnr,
            @RequestParam("officename[]") String[] officename,
            @RequestParam("address[]") String[] address,
            @RequestParam("yr[]") Integer[] yr,
            @RequestParam("day[]") Integer[] day,
            @RequestParam("workdetail[]") String[] workdetail,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {
   
    		
	    	String statename=session.getAttribute("statename").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
			String userid=(String)session.getAttribute("userid");
			try {
			 if(userid==null){

		            return "redirect:/login";
		     }
			Integer pprInstStrId =0;
	    	List<Object[]> list = isserv.getPPR1List(stcode);
			for (Object[] row : list) {
	
			    pprInstStrId = (Integer) row[0];
			   // System.out.println("Id : " + pprInstStrId);
			}

			slnaFunctionaryService.saveFunctionary(pprInstStrId, level, fname, lname, designation, qualification, workallocation, slr,
                slnr, dlr,dlnr,officename, address, yr, day, workdetail, action, userid, getClientIpAddr(request));

			redirectAttributes.addFlashAttribute( "success", "Functionary saved successfully.");
        
			}
			catch (Exception e) {

				e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to saved record.");
			}
			return "redirect:/slnaFunctionariesPPR3";	
    }
    
    @GetMapping("/deleteSLNAFunctionariesPPR3")
    public String deleteSLNAFunctionariesPPR3(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
            SlnaFunctionary data = slnafuncrep.findById(id).orElse(null);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/slnaFunctionariesPPR3";
            }
            
            if (slnafuncrep.existsById(id)) {
            	slnafuncrep.deleteById(id);
            	redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
            }
            else {
            	redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            }
            

            
        } 
        catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            e.printStackTrace();
        }
		return "redirect:/slnaFunctionariesPPR3";
    }
    
    @GetMapping("/completeSLNAFunctionariesPPR3")
    public String completeSLNAFunctionariesPPR3(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		 	try {
		 		int updated =0;
		 		if(userid==null){

		            return "redirect:/login";
		        }
		 		slnaFunctionaryService.completeRecord(id);
		        
		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
		       
		    } 
		 	catch (Exception e) {
		 		e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
		    }
       
        return "redirect:/slnaFunctionariesPPR3";
    }
    
    @GetMapping("/editSLNAFunctionariesPPR3")
    public String editSLNAFunctionariesPPR3(@RequestParam Integer id, Model model, HttpSession session) {

    	
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

		if(userid==null){

		    return "redirect:/login";
		}
		SlnaFunctionary functionary = slnafuncrep.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));

	    List<SlnaFunctionaryWorkExperience> experiences =exprep.findByFunctionaryPprSlnaFunId(id);
	    
	    List<Object[]> functionariesList = slnaFunctionaryService.getFunctionariesList(stcode);

        List<Object[]> finalList = new ArrayList<>();

        int srNo = 1;
        Integer previousId = null;

        for (Object[] row : functionariesList) {

            Integer currentId = ((Number) row[0]).intValue();

            // Create a new array with one extra column for serial number
            Object[] newRow = Arrays.copyOf(row, row.length + 1);

            if (previousId == null || !previousId.equals(currentId)) {
                newRow[row.length] = srNo++;   // Serial No.
            } else {
                newRow[row.length] = "";       // Blank for duplicate rows
            }
            finalList.add(newRow);
            previousId = currentId;
        }

        model.addAttribute("functionariesList", finalList);
	    model.addAttribute("functionary", functionary);
	    model.addAttribute("experiences", experiences);
	//	model.addAttribute("functionariesList", slnaFunctionaryService.getFunctionariesList(stcode));
        model.addAttribute("designationList", dserv.getAllDesignationDetails());
        model.addAttribute("qualificationList", qserv.getAllQualification());

        return "editSlnaFunctionary";
    }
    
    @PostMapping("/updateSLNAFunctionariesPPR3")
    public String updateFunctionary(HttpSession session, Model model, HttpServletRequest request,
            @RequestParam Integer pprSlnaFunId, @RequestParam String level, @RequestParam String fname, @RequestParam String lname,
            @RequestParam Integer designation, @RequestParam Integer qualification,  @RequestParam String workallocation, 
            @RequestParam BigDecimal slr, @RequestParam BigDecimal slnr,  @RequestParam BigDecimal dlr, @RequestParam BigDecimal dlnr,
            @RequestParam("officename[]") String[] office,
            @RequestParam("address[]") String[] address,
            @RequestParam("yr[]") Integer[] year,
            @RequestParam("day[]") Integer[] day,
            @RequestParam("workdetail[]") String[] workdetail,
            @RequestParam Character action,  
    		RedirectAttributes redirectAttributes ) {
    	
    
    	Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		try {
			
			 if(userid==null){
	
		            return "redirect:/login";
		     }
			 SlnaFunctionary functionary = slnafuncrep.findById(pprSlnaFunId).get();

			 	functionary.setLevel(level);
		        functionary.setSlnaFunFname(fname);
		        functionary.setSlnaFunLname(lname);
		        functionary.setDesignationId(designation);
		        functionary.setQualificationId(qualification);
		        functionary.setWorkAllocation(workallocation);
		
		        functionary.setTotBudgetSlnaRecurring(slr);
		        functionary.setTotBudgetSlnaNonRecurring(slnr);
		        functionary.setDolrFundRecurring(dlr);
		        functionary.setDolrFundNonRecurring(dlnr);
		        functionary.setStatus(action);
		        functionary.setUpdatedBy(userid);
		        functionary.setUpdatedDate(LocalDate.now());

		        slnafuncrep.save(functionary);

		        exprep.deleteByFunctionaryPprSlnaFunId(pprSlnaFunId);

		        for(int i=0;i<office.length;i++){
		
		            if(office[i]==null || office[i].trim().isEmpty())
		                continue;
		
		            SlnaFunctionaryWorkExperience exp = new SlnaFunctionaryWorkExperience();
		
		            exp.setFunctionary(functionary);
		            exp.setOfficeName(office[i]);
		            exp.setAddress(address[i]);
		            exp.setWorkExpYrs(year[i]);
		            exp.setWorkExpDays(day[i]);
		            exp.setWorkDetails(workdetail[i]);
		            exp.setUpdatedBy(userid);
		            exp.setRequestIp(getClientIpAddr(request));
		            exp.setUpdatedDate(LocalDate.now());
		
		            exprep.save(exp);
		            
		        }
		        redirectAttributes.addFlashAttribute("success", "Record update successfully.");
		}
		catch (Exception e) {

			e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Unable to Update record.");
		}

        return "redirect:/slnaFunctionariesPPR3";	
    }
    
    @GetMapping("/wcdcFunctionariesPPR4B")
    public String wcdcFunctionariesPPR4(HttpSession session, Model model) 
	{
		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
		/*
		 * List<Object[]> list = isserv.getPPR1List(stcode); for (Object[] row : list) {
		 * 
		 * Integer id = (Integer) row[0]; System.out.println("Id : " + id); }
		 */
		
        if(userid==null){

            return "redirect:/login";
        }
        List<Object[]> functionariesList = slnaFunctionaryService.getWcdcFunctionariesList(stcode);

        List<Object[]> finalList = new ArrayList<>();

        int srNo = 1;
        Integer previousId = null;

        for (Object[] row : functionariesList) {

            Integer currentId = ((Number) row[0]).intValue();

            // Create a new array with one extra column for serial number
            Object[] newRow = Arrays.copyOf(row, row.length + 1);

            if (previousId == null || !previousId.equals(currentId)) {
                newRow[row.length] = srNo++;   // Serial No.
            } else {
                newRow[row.length] = "";       // Blank for duplicate rows
            }
            finalList.add(newRow);
            previousId = currentId;
        }

        model.addAttribute("functionariesList", finalList);
        model.addAttribute("designationList", dserv.getAllDesignationDetails());
        model.addAttribute("qualificationList", qserv.getAllQualification());
        model.addAttribute("distList", districtService.findCompletedDistrictsByState(stcode));
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
        return "wcdcFunctionariesPPR4";
    } 
    
    @PostMapping("/saveWCDCFunctionariesPPR4B")
    public String saveWCDCFunctionariesPPR4B(HttpSession session, Model model, HttpServletRequest request,
    		@RequestParam Integer district,
    		@RequestParam String fname,
            @RequestParam String lname,
            @RequestParam Integer designation,
            @RequestParam Integer qualification,
            @RequestParam String workallocation,
            @RequestParam BigDecimal slr,
            @RequestParam BigDecimal slnr,
            @RequestParam BigDecimal dlr,
            @RequestParam BigDecimal dlnr,
            @RequestParam("officename[]") String[] officename,
            @RequestParam("address[]") String[] address,
            @RequestParam("yr[]") Integer[] yr,
            @RequestParam("day[]") Integer[] day,
            @RequestParam("workdetail[]") String[] workdetail,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {
   
    		
	    	String statename=session.getAttribute("statename").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
			String userid=(String)session.getAttribute("userid");
			try {
			 if(userid==null){

		            return "redirect:/login";
		     }

			ppwd.saveWCDCFunctionary(district, fname, lname, designation, qualification, workallocation, slr, slnr, dlr, 
					dlnr, officename, address, yr, day, workdetail, action, userid, getClientIpAddr(request));

			redirectAttributes.addFlashAttribute( "success", "District Functionary saved successfully.");
        
			}
			catch (Exception e) {

				e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to saved record.");
			}
			return "redirect:/wcdcFunctionariesPPR4B";	
    }
    
    @GetMapping("/deleteWCDCFunctionariesPPR4B")
    public String deleteWCDCFunctionariesPPR4B(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
	        PprWcdcFunctionary data = wdcrep.findById(id).orElse(null);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/wcdcFunctionariesPPR4B";
            }
            
            if (wdcrep.existsById(id)) {
            	wdcrep.deleteById(id);
            	redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
            }
            else {
            	redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            }
            

            
        } 
        catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
            e.printStackTrace();
        }
		return "redirect:/wcdcFunctionariesPPR4B";
    }
    
    @GetMapping("/completeWCDCFunctionariesPPR4B")
    public String completeWCDCFunctionariesPPR4B(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		 	try {
		 		int updated =0;
		 		if(userid==null){

		            return "redirect:/login";
		        }
		 		ppwd.completeRecord(id);
		        
		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
		       
		    } 
		 	catch (Exception e) {
		 		e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
		    }
       
        return "redirect:/wcdcFunctionariesPPR4B";
    }
    
    @GetMapping("/editWCDCFunctionariesPPR4B")
    public String editWCDCFunctionariesPPR4B(@RequestParam Integer id, Model model, HttpSession session) {

    	
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

		if(userid==null){

		    return "redirect:/login";
		}
		
		PprWcdcFunctionary functionary = wdcrep.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));

	    List<PprWcdcFunctionaryWorkExperience> experiences =wdcexprep.findByFunctionaryPprWcdcFunId(id);
	    
	    List<Object[]> functionariesList = slnaFunctionaryService.getWcdcFunctionariesList(stcode);

        List<Object[]> finalList = new ArrayList<>();

        int srNo = 1;
        Integer previousId = null;

        for (Object[] row : functionariesList) {

            Integer currentId = ((Number) row[0]).intValue();

            // Create a new array with one extra column for serial number
            Object[] newRow = Arrays.copyOf(row, row.length + 1);

            if (previousId == null || !previousId.equals(currentId)) {
                newRow[row.length] = srNo++;   // Serial No.
            } else {
                newRow[row.length] = "";       // Blank for duplicate rows
            }
            finalList.add(newRow);
            previousId = currentId;
        }

        model.addAttribute("functionariesList", finalList);
	    model.addAttribute("functionary", functionary);
	    model.addAttribute("experiences", experiences);
	    model.addAttribute("designationList", dserv.getAllDesignationDetails());
        model.addAttribute("qualificationList", qserv.getAllQualification());
        model.addAttribute("distList", districtService.findCompletedDistrictsByState(stcode));
		model.addAttribute("stcode", stcode);

        return "editWCDCFunctionaryPPR4";
    }
    
    @PostMapping("/UpdateWCDCFunctionariesPPR4B")
    public String UpdateWCDCFunctionariesPPR4B(HttpSession session, Model model, HttpServletRequest request,
            @RequestParam Integer pprwcdcFunId, @RequestParam Integer district,
    		@RequestParam String fname,
            @RequestParam String lname,
            @RequestParam Integer designation,
            @RequestParam Integer qualification,
            @RequestParam String workallocation,
            @RequestParam BigDecimal slr,
            @RequestParam BigDecimal slnr,
            @RequestParam BigDecimal dlr,
            @RequestParam BigDecimal dlnr,
            @RequestParam("officename[]") String[] officename,
            @RequestParam("address[]") String[] address,
            @RequestParam("yr[]") Integer[] yr,
            @RequestParam("day[]") Integer[] day,
            @RequestParam("workdetail[]") String[] workdetail,
            @RequestParam String action,
            RedirectAttributes redirectAttributes) {
    	
    
    	Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		try {
			
			 if(userid==null){
	
		            return "redirect:/login";
		     }
			 
			 
			 ppwd.UpdateWCDCFunctionary(pprwcdcFunId,district, fname, lname, designation, qualification, workallocation, slr, slnr, dlr, 
						dlnr, officename, address, yr, day, workdetail, action, userid, getClientIpAddr(request));
			 
		        redirectAttributes.addFlashAttribute("success", "Record update successfully.");
		}
		catch (Exception e) {

			e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Unable to Update record.");
		}

        return "redirect:/wcdcFunctionariesPPR4B";	
    }

}
