package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.PprProposedProjectDto;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
import gov.dolr.wdcpmksy3.PPR.repository.CriteriaRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.service.MicroWatershedService;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.PPR.service.PprProposedProjectService;
import gov.dolr.wdcpmksy3.PPR.service.ProjectTypeService;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprProposedProjectController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private PPRDistrictService pprService;
	
	@Autowired
	private MPprRepository mPprRepo;
	
	@Autowired 
	private ProjectTypeService projectTypeServ;
	
	@Autowired
	private MicroWatershedService microWatershedServ;
	
	@Autowired
	private PprProposedProjectService proposedProjectService;
	
	@Autowired
	private CriteriaRepository criteriaRepo;
	
	@GetMapping("/pprProposedProjectDetails")
    public String pprProposedProjectDetails(@RequestParam(required = false) Integer dcode, HttpSession session, Model model) {
		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    
		String userid=(String)session.getAttribute("userid");
		if(userid==null){
            return "redirect:/login";
        }
		if(dcode != null){

	        List<MPpr> pprList = mPprRepo.findByDistrictDcode(dcode);

	        if(!pprList.isEmpty()){

	            MPpr ppr = pprList.get(0);
	            model.addAttribute("pprId", ppr.getPprId());
	            model.addAttribute("selectedDistrict", dcode);
	            model.addAttribute("detailsOfListOfProposedProject",
	                    proposedProjectService.getPprProposedProjectList(ppr));

	            model.addAttribute("project", ppr.getProjectName());
	            model.addAttribute("microWatershedList",
	                    pprService.getMicroWatershedsByDistrict(dcode));
	        }
	    }
		model.addAttribute("districtList", districtService.getDistrictsByState(stcode));
		model.addAttribute("projectTypeList", projectTypeServ.getProjectType());
		model.addAttribute("criteriaList", criteriaRepo.findAll());
		model.addAttribute("statename", statename);
		
		return "prioritizedListOfProposedProject";
	}
	
	@GetMapping("/getProjAndMicroWaterDetailsByDcode")
    @ResponseBody
    public Map<String, Object> getProjAndMicroWaterDetailsByDcode(@RequestParam Integer dcode) {
        Map<String, Object> response = new HashMap<>();
        List<MPpr> pprList = mPprRepo.findByDistrictDcode(dcode);
        if (!pprList.isEmpty()) {
            MPpr ppr = pprList.get(0);
            response.put("pprId", ppr.getPprId());
            response.put("project", ppr.getProjectName());
            response.put("microWatershedList", pprService.getMicroWatershedsByDistrict(dcode));
            response.put("savedProjects", proposedProjectService.getPprProposedProjectList(ppr));
        } else {
        	response.put("pprId", null);
            response.put("project", "");
            response.put("microWatershedList", Collections.emptyList());
            response.put("savedProjects", Collections.emptyList());
        }
        return response;
    }
	
	@GetMapping("/getMicroWatershedCodeByMwId")
	@ResponseBody
	public String getMicroWatershedCodeByMwId(@RequestParam Integer mwId) {
	    MicroWatershed mw = microWatershedServ.getMicroWatershedById(mwId);
	    return mw == null ? "" : mw.getMwCode();
	}
	
	@PostMapping("/savePrioritizedListOfProposedProject")
	public String savePrioritizedListOfProposedProject(
	        @ModelAttribute PprProposedProjectDto form,
	        @RequestParam Character action,
	        HttpSession session,
	        HttpServletRequest request,
	        RedirectAttributes redirectAttributes) {
	    String userId = (String) session.getAttribute("userid");
	    if (userId == null) {
	        return "redirect:/login";
	    }
	    proposedProjectService.save(form, action, userId, CommonFunctions.getClientIpAddr(request));
	    redirectAttributes.addFlashAttribute("success",
	            "Record saved successfully.");
	    return "redirect:/pprProposedProjectDetails";
	}
	
	@GetMapping("/getPprProposedProjectForEdit")
	@ResponseBody
	public Map<String,Object> getPprProposedProjectForEdit(
	        @RequestParam Integer id) {
	    PprProposedProject data = proposedProjectService.findById(id);
	    Map<String,Object> map = new HashMap<>();
	    map.put("id", data.getPprProposedProjectId());
	    map.put("district", data.getPpr().getDistrict().getDistName());
	    map.put("project", data.getPpr().getProjectName());
	    map.put("microWatershed", data.getMicroWatershed().getMwName());
	    map.put("microWatershedCode", data.getMicroWatershed().getMwCode());
	    map.put("treatedArea", data.getTreatedArea());
	    map.put("projectType", data.getProjectType().getProjectTypeId());
	    map.put("proposedCost", data.getProposedCost());
	    return map;
	}
	
	@PostMapping("/updatePprProposedProject")
	public String updatePprProposedProject(@ModelAttribute PprProposedProjectDto dto, 
			HttpSession session, RedirectAttributes redirectAttributes) {
		String userId = (String) session.getAttribute("userid");
	    proposedProjectService.updateProposedProject(dto, userId);
	    redirectAttributes.addFlashAttribute("success",
	            "Record updated successfully.");
	    return "redirect:/pprProposedProjectDetails";
	}



	
}
