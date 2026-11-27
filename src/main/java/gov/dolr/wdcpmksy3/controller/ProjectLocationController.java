package gov.dolr.wdcpmksy3.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.dto.LoginDTO;
import gov.dolr.wdcpmksy3.entity.MBlock;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.WdcpmksyProjectLocation;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserProjectMap;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyProjectLocationRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserProjectMapRepository;
import gov.dolr.wdcpmksy3.service.ProjectLocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectLocationController {

	@Autowired
	private WdcpmksyUserProjectMapRepository userProjectMapRepository;
	
	@Autowired
	private MBlockRepository blockRepository;
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Autowired
	private MPprRepository projectRepository;
	
	@Autowired
	private WdcpmksyProjectLocationRepository projectLocationRepository;
	
	@Autowired
	private  ProjectLocationService  projectLocationService;
	

@GetMapping("/piaPjtNotLocatiaon")
public ModelAndView projectLocation(
        @RequestParam(required = false) Integer pprId,
        HttpServletRequest request) {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("regid") == null) {
        return new ModelAndView("redirect:/login");
    }

    Integer regId = Integer.parseInt(
            session.getAttribute("regid").toString());

    List<WdcpmksyUserProjectMap> projectMappings =
            userProjectMapRepository.findByUser_RegId(regId);

    List<MPpr> projectList = projectMappings.stream()
            .map(WdcpmksyUserProjectMap::getProject)
            .filter(Objects::nonNull)
            .sorted(Comparator.comparing(
                    MPpr::getProjectName,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
            .toList();

    // Do not retain a project that is not assigned to this user.
    Integer selectedPprId = null;

    if (pprId != null && userProjectMapRepository
            .existsByUser_RegIdAndProject_PprId(regId, pprId)) {

        selectedPprId = pprId;
    }

    ModelAndView mav =
            new ModelAndView("project/projectLocation");

    mav.addObject("projectList", projectList);
    mav.addObject("selectedPprId", selectedPprId);
    mav.addObject("username", session.getAttribute("username"));

    return mav;
}

	
	
	@GetMapping("/getProjectBlocks")
	@ResponseBody
	public ResponseEntity<?> getProjectBlocks(
	        @RequestParam("pprId") Integer pprId,
	        HttpServletRequest request) {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("regid") == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    Integer regId = Integer.parseInt(
	            session.getAttribute("regid").toString());

	    // Check project assignment for logged-in PIA
	    boolean assigned = userProjectMapRepository
	            .existsByUser_RegIdAndProject_PprId(regId, pprId);

	    if (!assigned) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }

	    List<MBlock> blocks = blockRepository.findBlocksByProjectId(pprId);

	    List<Map<String, Object>> result = blocks.stream()
	            .map(b -> {
	                Map<String, Object> map = new HashMap<>();
	                map.put("bcode", b.getBcode());
	                map.put("blockName", b.getBlockName());
	                return map;
	            })
	            .toList();

	    return ResponseEntity.ok(result);
	}

	@GetMapping("/getBlockVillages")
	@ResponseBody
	public ResponseEntity<?> getBlockVillages(
	        @RequestParam Integer bcode,
	        @RequestParam Integer pprId,
	        HttpServletRequest request) {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("regid") == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    Integer regId = Integer.parseInt(
	            session.getAttribute("regid").toString());

	    String userType = String.valueOf(session.getAttribute("usertype"));

	    if (!"PI".equalsIgnoreCase(userType)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }

	    boolean assigned = userProjectMapRepository
	            .existsByUser_RegIdAndProject_PprId(regId, pprId);

	    if (!assigned) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }

	    // Verify that this block belongs to the selected project's district.
	    MPpr project = projectRepository.findById(pprId).orElse(null);

	    if (project == null || project.getDistrict() == null ||
	            !blockRepository.existsByBcodeAndDistrict_Dcode(
	                    bcode, project.getDistrict().getDcode())) {
	        return ResponseEntity.badRequest().build();
	    }

	    List<Map<String, Object>> result =
	            villageRepository.findAvailableVillagesByBlockAndProject(bcode, pprId)
	                    .stream()
	                    .map(v -> {
	                        Map<String, Object> map = new HashMap<>();
	                        map.put("vcode", v.getVcode());
	                        map.put("villageName", v.getVillageName());
	                        map.put("gcode", v.getGramPanchayat().getGcode());
	                        map.put("gramPanchayatName",
	                                v.getGramPanchayat().getGramPanchayatName());
	                        map.put("blockName",
	                                v.getGramPanchayat().getBlock().getBlockName());
	                        return map;
	                    })
	                    .toList();

	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getProjectLocationData")
	@ResponseBody
	public ResponseEntity<?> getProjectLocationData(
	        @RequestParam Integer pprId,
	        HttpServletRequest request) {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("regid") == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    Integer regId = Integer.parseInt(session.getAttribute("regid").toString());

	    if (!"PI".equalsIgnoreCase(
	            String.valueOf(session.getAttribute("usertype")))) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }

	    if (!userProjectMapRepository
	            .existsByUser_RegIdAndProject_PprId(regId, pprId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }

	    List<WdcpmksyProjectLocation> locations =
	            projectLocationRepository.findByProject_PprIdOrderByLocationIdAsc(pprId);
	    
	    

	    List<Map<String, Object>> result = locations.stream()
	            .map(location -> {
	                MVillage v = location.getVillage();

	                Map<String, Object> map = new HashMap<>();
	                map.put("vcode", v.getVcode());
	                map.put("villageName", v.getVillageName());
	                map.put("gcode", v.getGramPanchayat().getGcode());
	                map.put("gramPanchayatName",
	                        v.getGramPanchayat().getGramPanchayatName());
	                map.put("blockName",
	                        v.getGramPanchayat().getBlock().getBlockName());
	                map.put("status", location.getStatus());
	                map.put("locationId", location.getLocationId());
	                return map;
	            })
	            .toList();

	    return ResponseEntity.ok(result);
	}
	

	@PostMapping("/saveProjectLocationDraft")
	public String saveProjectLocationDraft(
	        @RequestParam Integer pprId,
	        @RequestParam(required = false) List<Integer> villageCodes,
	        HttpServletRequest request,
	        RedirectAttributes redirectAttributes) {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("regid") == null) {
	        return "redirect:/login";
	    }

	    try {

	        Integer regId = Integer.parseInt(
	                session.getAttribute("regid").toString());

	        if (!"PI".equalsIgnoreCase(
	                String.valueOf(session.getAttribute("usertype")))) {

	            redirectAttributes.addFlashAttribute(
	                    "error", "You are not authorized to save project locations.");

	            return "redirect:/piaPjtNotLocatiaon";
	        }

	        if (!userProjectMapRepository
	                .existsByUser_RegIdAndProject_PprId(regId, pprId)) {

	            redirectAttributes.addFlashAttribute(
	                    "error", "You are not authorized for this project.");

	            return "redirect:/piaPjtNotLocatiaon";
	        }

	        String userid = String.valueOf(session.getAttribute("userid"));

	        projectLocationService.saveProjectLocationDraft(
	                pprId,
	                villageCodes,
	                userid,
	                request.getRemoteAddr());

	        redirectAttributes.addFlashAttribute(
	                "success", "Data saved successfully.");

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error", e.getMessage());
	    }

	    return "redirect:/piaPjtNotLocatiaon?pprId=" + pprId;
	}


@PostMapping("/deleteProjectLocation")
public String deleteProjectLocation(
        @RequestParam Integer pprId,
        @RequestParam Integer locationId,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes) {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("regid") == null) {
        return "redirect:/login";
    }

    Integer regId = Integer.parseInt(
            session.getAttribute("regid").toString());

    String userType = String.valueOf(
            session.getAttribute("usertype"));

    if (!"PI".equalsIgnoreCase(userType) ||
            !userProjectMapRepository
                    .existsByUser_RegIdAndProject_PprId(regId, pprId)) {

        redirectAttributes.addFlashAttribute(
                "error", "You are not authorized to delete this record.");

        return "redirect:/piaPjtNotLocatiaon";
    }

    try {

        projectLocationService.deleteProjectLocation(
                pprId, locationId);

        redirectAttributes.addFlashAttribute(
                "success", "Project location deleted successfully.");

    } catch (IllegalArgumentException | IllegalStateException e) {

        redirectAttributes.addFlashAttribute(
                "error", e.getMessage());
    }

    return "redirect:/piaPjtNotLocatiaon?pprId=" + pprId;
}


@PostMapping("/completeProjectLocation")
public String completeProjectLocation(
        @RequestParam Integer pprId,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes) {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("regid") == null) {
        return "redirect:/login";
    }

    Integer regId = Integer.parseInt(
            session.getAttribute("regid").toString());

    String userType = String.valueOf(
            session.getAttribute("usertype"));

    if (!"PI".equalsIgnoreCase(userType) ||
            !userProjectMapRepository
                    .existsByUser_RegIdAndProject_PprId(regId, pprId)) {

        redirectAttributes.addFlashAttribute(
                "error", "You are not authorized to complete this project.");

        return "redirect:/piaPjtNotLocatiaon";
    }

    String userid = String.valueOf(session.getAttribute("userid"));

    try {

        projectLocationService.completeProjectLocation(
                pprId,
                userid,
                request.getRemoteAddr()
        );

        redirectAttributes.addFlashAttribute(
                "success", "Project location completed successfully.");
        
        

    } catch (IllegalArgumentException | IllegalStateException e) {

        redirectAttributes.addFlashAttribute(
                "error", e.getMessage());
    }

    return "redirect:/piaPjtNotLocatiaon?pprId=" + pprId;
}


}
