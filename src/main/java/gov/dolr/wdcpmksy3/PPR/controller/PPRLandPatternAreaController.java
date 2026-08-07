package gov.dolr.wdcpmksy3.PPR.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.PPRLandPatternAreaDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PPRLandPatternArea;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.PPR.service.MPprService;
import gov.dolr.wdcpmksy3.PPR.service.MicroWatershedService;
import gov.dolr.wdcpmksy3.PPR.service.PPRLandPatternAreaService;
import gov.dolr.wdcpmksy3.PPR.service.VillageService;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRLandPatternAreaController {
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private MPprService pprService;
	
	@Autowired
	private VillageService villageService;
	
	@Autowired
	private MicroWatershedService microWatershedService;
	
	@Autowired
	private PPRLandPatternAreaService landPatternAreaService;
	

	

	
	@GetMapping("/pprLandPatternArea")
	public String landPatternArea(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));

        return "ppr/pprLandPatternArea";
	}
	
	@GetMapping("/getProjectsByDistrictLandPattern")
	@ResponseBody
	public List<Map<String, Object>> getProjectsByDistrict(@RequestParam Integer dcode) {

	    List<MPpr> projects = pprService.getProjectsByDistrict(dcode);
	    
	    return projects.stream().map(p -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", p.getPprId());
	        map.put("name", p.getProjectName());
	        return map;
	        
	    }).toList();
	    
	    
	}
	
	@GetMapping("/getMicroWatershedsByProject")
	@ResponseBody
	public List<Map<String, Object>> getMicroWatershedsByProject(@RequestParam Integer pprId) {

	    List<PprMicroWatershed> list = landPatternAreaService.getMicroWatershedsByProject(pprId);

	    return list.stream().map(m -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", m.getMicroWatershed().getMwId());
	        map.put("name", m.getMicroWatershed().getMwName());
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getVillagesByProject")
	@ResponseBody
	public List<Map<String, Object>> getVillagesByProject(@RequestParam Integer pprId) {

	    List<MVillage> villages = villageService.getVillagesByProject(pprId);

	    return villages.stream().map(v -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", v.getVcode());
	        map.put("name", v.getVillageName());
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getLandPatternVillageStatus")
	@ResponseBody
	public Map<String, Object> getLandPatternVillageStatus(
	        @RequestParam Integer vcode) {

	    Map<String, Object> result = new HashMap<>();

	    Character status =
	            landPatternAreaService.getVillageStatus(vcode);

	    result.put("status", status);

	    return result;
	}
	
	@PostMapping("/savePPRLandPatternArea")
	public String savePPRLandPatternArea(@ModelAttribute PPRLandPatternAreaDTO dto, HttpSession session, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) 
	{

	    try {

	        String userId = session.getAttribute("userid").toString();
	        
	        MPpr ppr = pprService.getById(dto.getProject());

	        if(ppr == null) {

	            redirectAttributes.addFlashAttribute("error", "Invalid Project selected.");

	            return "redirect:/pprLandPatternArea";
	        }
	        
	        MicroWatershed mw = microWatershedService.getMicroWatershedById(dto.getWatershed());

	        if(mw == null){

	            redirectAttributes.addFlashAttribute("error", "Invalid Micro Watershed selected.");

	            return "redirect:/pprLandPatternArea";
	        }
	        
	        MVillage vlg = villageService.getVillageById(dto.getVillage());


	        if(vlg == null){

	            redirectAttributes.addFlashAttribute("error", "Invalid Village selected.");

	            return "redirect:/pprLandPatternArea";
	        }
	        
	        
			if (landPatternAreaService.existsByVillage(dto.getVillage())) {

				redirectAttributes.addFlashAttribute("error", "A Land Pattern Area record already exists for the selected village.");

				return "redirect:/pprLandPatternArea";
			}
	        
	        PPRLandPatternArea area = new PPRLandPatternArea();

	        area.setPprId(ppr);
	        area.setMicroWatershed(mw);
	        area.setVillage(vlg);

	        area.setVillageArea(dto.getVillage_area());
            area.setForestArea(dto.getForest_area());
            area.setAgricultureLand(dto.getArgiculture_land());
            area.setRainfedArea(dto.getRainfed_area());
            area.setPastures(dto.getPastures());
            area.setCultivableWastelandArea(dto.getCultivable_wasteland_area());
            area.setNonCultivableWastelandArea(dto.getNon_cultivable_wasteland_area());

	        area.setStatus('D');

	        area.setCreatedBy(userId);

	        area.setCreatedDate(LocalDateTime.now());

	        area.setRequestIp(request.getRemoteAddr());

	        landPatternAreaService.savePPRLandPatternArea(area);

	        redirectAttributes.addFlashAttribute("success", "Record saved successfully.");

	    }
	    catch (Exception e) {

	        e.printStackTrace();

	        redirectAttributes.addFlashAttribute("error", "Record not saved.");

	    }

	    return "redirect:/pprLandPatternArea";

	}
	
	@GetMapping("/getLandPatternAreaByDistrict")
	@ResponseBody
	public List<Map<String,Object>> getLandPatternAreaByDistrict(@RequestParam Integer dcode){

	    return landPatternAreaService.getLandPatternAreaByDistrict(dcode);

	}
	
	@PostMapping("/updatePPRLandPatternArea")
	public String updatePPRLandPatternArea(
	        @ModelAttribute PPRLandPatternAreaDTO dto,
	        HttpServletRequest request,
	        HttpSession session,
	        RedirectAttributes redirectAttributes)
	{

	    try {

	        PPRLandPatternArea area =
	                landPatternAreaService.getById(dto.getPpr_land_pattern_area_id());

	        if(area==null){

	            redirectAttributes.addFlashAttribute("error","Record not found.");

	            return "redirect:/pprLandPatternArea";
	        }

	        area.setVillageArea(dto.getVillage_area());
	        area.setForestArea(dto.getForest_area());
	        area.setAgricultureLand(dto.getArgiculture_land());
	        area.setRainfedArea(dto.getRainfed_area());
	        area.setPastures(dto.getPastures());
	        area.setCultivableWastelandArea(dto.getCultivable_wasteland_area());
	        area.setNonCultivableWastelandArea(dto.getNon_cultivable_wasteland_area());

	        area.setUpdatedBy(session.getAttribute("userid").toString());
	        area.setUpdatedDate(LocalDate.now());
	        area.setRequestIp(request.getRemoteAddr());

	        landPatternAreaService.savePPRLandPatternArea(area);

	        redirectAttributes.addFlashAttribute("success",
	                "Record updated successfully.");

	    }
	    catch(Exception e){

	        e.printStackTrace();

	        redirectAttributes.addFlashAttribute("error",
	                "Unable to update record.");

	    }

	    return "redirect:/pprLandPatternArea";
	}
	
	@GetMapping("/deletePPRLandPatternArea")
	public String delete(
	        @RequestParam Integer id,
	        RedirectAttributes redirectAttributes)
	{

	    try{

	        landPatternAreaService.delete(id);

	        redirectAttributes.addFlashAttribute("success",
	                "Record deleted successfully.");

	    }
	    catch(Exception e){

	        redirectAttributes.addFlashAttribute("error",
	                "Unable to delete.");

	    }

	    return "redirect:/pprLandPatternArea";

	}
	
	@GetMapping("/completePPRLandPatternArea")
	public String complete(
	        @RequestParam Integer id,
	        HttpSession session,
	        HttpServletRequest request,
	        RedirectAttributes redirectAttributes)
	{

	    try{

	        PPRLandPatternArea area =
	                landPatternAreaService.getById(id);

	        if(area==null){

	            redirectAttributes.addFlashAttribute("error",
	                    "Record not found.");

	            return "redirect:/pprLandPatternArea";
	        }

	        area.setStatus('C');

	        area.setUpdatedBy(session.getAttribute("userid").toString());

	        area.setUpdatedDate(LocalDate.now());

	        area.setRequestIp(request.getRemoteAddr());

	        landPatternAreaService.savePPRLandPatternArea(area);

	        redirectAttributes.addFlashAttribute("success",
	                "Record completed.");

	    }
	    catch(Exception e){

	        redirectAttributes.addFlashAttribute("error",
	                "Unable to complete.");

	    }

	    return "redirect:/pprLandPatternArea";

	}
	
	
}
