package gov.dolr.wdcpmksy3.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MScheme;
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.entity.MBlock;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.repository.MSchemeRepository;
import gov.dolr.wdcpmksy3.repository.PprProposedAreaRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import gov.dolr.wdcpmksy3.service.PprProposedAreaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.List;


import java.util.List;

@Controller
public class PrelimnaryPPR8AController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private PprProposedAreaRepository pprprep;
	
	@Autowired
	private MPprRepository mpprRepository;

	@Autowired
	private MBlockRepository blockRepository;
	
	@Autowired
	private PprProposedAreaService pprProposedAreaService;
	
	@Autowired
	private MSchemeRepository schemeRepository;
	
	@GetMapping("/preliminaryPPR8")
    public String preliminaryPPR8(HttpSession session, Model model){

		String userid = (String) session.getAttribute("userid");

		if (userid == null) {
		    return "redirect:/login";
		}

		String statename = (String) session.getAttribute("statename");
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());

       
        model.addAttribute("stateName",statename);
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        
        model.addAttribute("projectList",new ArrayList<>());
        model.addAttribute("blockList",new ArrayList<>());
        model.addAttribute("draftList", pprprep.findBycreatedBy(userid));
     //   System.out.println("kdy"+pprprep.findBycreatedBy(userid).size());
       

        return "ppr8";
    }
	
	
	
	@GetMapping("/getBlockByProjectPPR8")
	@ResponseBody
	public List<Map<String,Object>> getBlocksByProject(@RequestParam Integer pprId){

	    List<MBlock> block=blockRepository.getBlocksByProject(pprId);

	    return block.stream().map(b->{

	        Map<String,Object> map=new HashMap<>();

	        map.put("id",b.getBcode());
	        map.put("name",b.getBlockName());

	        return map;

	    }).toList();

	}
	 
	@PostMapping("/saveDraftPPR8")
	public String saveDraftPPR8(HttpSession session, Model model,
	                            @RequestParam Integer pprId,
	                            @RequestParam Integer bcode,
	                            @RequestParam String scheme,
	                            @RequestParam Integer projSanctionedNo,
	                            @RequestParam BigDecimal projSanctionedArea,
	                            @RequestParam BigDecimal netArea,
	                            @RequestParam BigDecimal proposedArea,
	                            @RequestParam BigDecimal proposedAreaOthers,
	                            @RequestParam BigDecimal netBalArea,
	                            @RequestParam Character action,

	                            RedirectAttributes redirectAttributes) {

	    String userid = (String) session.getAttribute("userid");
	    

	    if (userid != null) {

	        // Fetch Project
	        MPpr ppr = mpprRepository.getReferenceById(pprId);

	        // Fetch Block
	        MBlock block = blockRepository.getReferenceById(bcode);
	        
	        
	     // Store pprId in session
	        session.setAttribute("pprId", pprId);

			
	        // Create Entity
	        PprProposedArea obj = new PprProposedArea();

	        obj.setPpr(ppr);
	        obj.setBlock(block);
	       
	        obj.setProjSanctionedNo(projSanctionedNo);
	        obj.setProjSanctionedArea(projSanctionedArea);
	        obj.setNetArea(netArea);
	        obj.setProposedArea(proposedArea);
	        obj.setProposedAreaOthers(proposedAreaOthers);
	        obj.setNetBalArea(netBalArea);

	        obj.setStatus(action);
	        obj.setCreatedBy(userid);
	        obj.setCreatedDate(LocalDateTime.now());
	        // obj.setRequestIp(getClientIpAddr(request));

	        pprProposedAreaService.saveDraft(obj, scheme);
	        
	        redirectAttributes.addFlashAttribute("success", "Record Saved Successfully.");

	        return "redirect:/preliminaryPPR8";

	    } else {

	        return "redirect:/login";
	    }
	}
	
	
	@GetMapping("/getPPR8ById")
	@ResponseBody
	public String editPPR8(@RequestParam Long id,
	                       Model model,
	                       HttpSession session) {

	    PprProposedArea data = pprprep.findById(id).orElse(null);

	    model.addAttribute("editData", data);

	    String statename = session.getAttribute("statename").toString();
	    Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    String userid = (String) session.getAttribute("userid");

	    if (userid == null) {
	        return "redirect:/login";
	    }

	    model.addAttribute("distList",
	            districtService.getPPRDistrictsByState(stcode));

	    model.addAttribute("projectList", new ArrayList<>());
	    model.addAttribute("blockList", new ArrayList<>());

	    Integer pprId = (Integer) session.getAttribute("pprId");

	    if (pprId != null) {
	        model.addAttribute("draftList",
	        		pprprep.findByPprPprIdAndStatus(pprId, 'D'));
	    } else {
	        model.addAttribute("draftList", new ArrayList<>());
	    }

	    model.addAttribute("stateName", statename);
	    model.addAttribute("projectList",new ArrayList<>());
        model.addAttribute("blockList",new ArrayList<>());
	    return "editppr8";
	}
	
	@GetMapping("/deleteDraftPPR8")
    public String deleteDraftPPR8(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
		String userid=(String)session.getAttribute("userid");
		try {
			
	        if(userid==null){
	
	            return "redirect:/login";
	        }
	        PprProposedArea data = pprprep.findById(id.longValue()).orElse(null);
            if (data == null) {
                redirectAttributes.addFlashAttribute("error", "Record not found.");
                return "redirect:/pendingUCPPR19";
            }
            
            if (pprprep.existsById(id.longValue())) {
            	pprprep.deleteById(id.longValue());
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
		return "redirect:/preliminaryPPR8";
    }
	
	@GetMapping("/completeDraftPPR8")
    public String completeDraftPPR8(HttpSession session, Model model, @RequestParam("id") Integer id,  
    		RedirectAttributes redirectAttributes) {

		
			String userid=(String)session.getAttribute("userid");
		 	try {
		 		
		 		if(userid==null){

		            return "redirect:/login";
		        }
		 		pprProposedAreaService.completeRecord(id);
		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
		    } 
		 	catch (Exception e) {
		 		e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
		    }
       
        return "redirect:/preliminaryPPR8";
    }
}
