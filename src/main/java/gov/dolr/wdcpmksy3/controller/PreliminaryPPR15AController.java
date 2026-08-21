package gov.dolr.wdcpmksy3.controller;


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

import gov.dolr.wdcpmksy3.PPR.entity.PPRMigrationDetails;
import gov.dolr.wdcpmksy3.PPR.entity.PprMicroWatershed;
import gov.dolr.wdcpmksy3.PPR.repository.PPRMigrationDetailsRepository;
import gov.dolr.wdcpmksy3.PPR.service.PPRLandPatternAreaService;
import gov.dolr.wdcpmksy3.PPR.service.PPRMigrationDetailsService;
import gov.dolr.wdcpmksy3.PPR.service.VillageService;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class PreliminaryPPR15AController {
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private PPRLandPatternAreaService landPatternAreaService;
	
	@Autowired
	private VillageService villageService;
	
	
	@Autowired
	private PPRMigrationDetailsService migrationDetailsService;

	
	@Autowired
	private PPRMigrationDetailsRepository pprMigrationDetailsRepository;
	
	
	@GetMapping("/preliminaryPPR15")
	public String preliminaryPPR15(HttpSession session, Model model) {

	    String statename = (String) session.getAttribute("statename");

	    Integer stcode = Integer.parseInt(
	            session.getAttribute("stcode").toString()
	    );

	    model.addAttribute("stateName", statename);

	    model.addAttribute(
	            "distList",
	            districtService.getPPRDistrictsByState(stcode)
	    );

	    model.addAttribute("projectList", new ArrayList<>());
	    model.addAttribute("watershedList", new ArrayList<>());
	    model.addAttribute("villageList", new ArrayList<>());
	    model.addAttribute("draftList", new ArrayList<>());

	    return "ppr15";
	}
	
	
	@GetMapping("/getMicroWatershedsByProject1")
	@ResponseBody
	public List<Map<String, Object>> getMicroWatershedsByProject1(
	        @RequestParam Integer pprId) {

	    List<PprMicroWatershed> list =
	            landPatternAreaService.getMicroWatershedsByProject(pprId);

	    return list.stream().map(m -> {

	        Map<String, Object> map = new HashMap<>();

	        map.put("id", m.getMicroWatershed().getMwId());
	        map.put("name", m.getMicroWatershed().getMwName());

	        return map;

	    }).toList();
	}
	
	
	
	@GetMapping("/getVillagesByProject1")
	@ResponseBody
	public List<Map<String, Object>> getVillagesByProject1(
	        @RequestParam Integer pprId) {

	    List<MVillage> villages = villageService.getVillagesByProject(pprId);

	    return villages.stream().map(v -> {

	        Map<String, Object> map = new HashMap<>();

	        map.put("id", v.getVcode());
	        map.put("name", v.getVillageName());

	        return map;

	    }).toList();
	}
	

/* ==========================================================
   LOAD PPR15 DRAFTS
   ========================================================== */

	@GetMapping("/getDraftsPPR15")
	@ResponseBody
	public List<Map<String, Object>> getDraftsPPR15(@RequestParam Integer pprId) {
	    return pprMigrationDetailsRepository.getMigrationDetailsByProject(pprId);
	}
	
	
	@PostMapping("/saveDraftPPR15")
	public String saveDraftPPR15(
	        HttpSession session,
	        HttpServletRequest request,
	        @RequestParam("pprId") Integer pprId,
	        @RequestParam("villageId") Integer vcode,
	        @RequestParam("watershedId") Integer mwId,
	        @RequestParam("peopleMigrating") Integer migratingPeopleCount,
	        @RequestParam("daysMigrating") Integer migrationDaysPerYear,
	        @RequestParam("migrationReason") String migrationReason,
	        @RequestParam("expectedReduction") Integer expectedReductionMigratingPeople) {
		System.out.println("========== PPR15 SAVE METHOD CALLED ==========");

	    System.out.println("pprId = " + pprId);
	    System.out.println("vcode = " + vcode);
	    System.out.println("mwId = " + mwId);
	    System.out.println("people = " + migratingPeopleCount);
	    System.out.println("days = " + migrationDaysPerYear);
	    System.out.println("reason = " + migrationReason);
	    System.out.println("expectedReduction = " + expectedReductionMigratingPeople);


	    PPRMigrationDetails entity = new PPRMigrationDetails();

	    entity.setPprId(pprId);
	    entity.setVcode(vcode);
	    entity.setMwId(mwId);

	    entity.setMigratingPeopleCount(migratingPeopleCount);
	    entity.setMigrationDaysPerYear(migrationDaysPerYear);
	    entity.setMigrationReason(migrationReason);
	    entity.setExpectedReductionMigratingPeople(
	            expectedReductionMigratingPeople);

	    entity.setStatus('D');

	    entity.setRequestIp(request.getRemoteAddr());

	    Object user = session.getAttribute("username");

	    if (user != null) {
	        entity.setCreatedBy(user.toString());
	    }

	    migrationDetailsService.save(entity);

	    return "redirect:/preliminaryPPR15";
	}
	
	@PostMapping("/updatePPR15")
	public String updatePPR15(
	        HttpSession session,
	        Model model,
	        HttpServletRequest request,

	        @RequestParam Integer editPpr15Id,
	        @RequestParam Integer editMigratingPeopleCount,
	        @RequestParam Integer editMigrationDaysPerYear,
	        @RequestParam String editMigrationReason,
	        @RequestParam Integer editExpectedReduction,

	        RedirectAttributes redirectAttributes) {

	    String userid = (String) session.getAttribute("userid");

	    if (userid != null) {

	        try {

	            migrationDetailsService.updatePPR15(
	                    editPpr15Id,
	                    editMigratingPeopleCount,
	                    editMigrationDaysPerYear,
	                    editMigrationReason,
	                    editExpectedReduction,
	                    userid,
	                    request);

	            redirectAttributes.addFlashAttribute(
	                    "success",
	                    "Record updated successfully.");

	        } catch (Exception e) {

	            redirectAttributes.addFlashAttribute(
	                    "error",
	                    e.getMessage());
	        }

	        return "redirect:/preliminaryPPR15";

	    } else {

	        return "redirect:/login";
	    }
	}
	
	@GetMapping("/deletePreliminaryPPR15")
	public String deletePreliminaryPPR15(@RequestParam Integer id,
	                                     RedirectAttributes redirectAttributes) {

	    pprMigrationDetailsRepository.deleteById(id);

	    redirectAttributes.addFlashAttribute("success",
	            "Record Deleted Successfully.");

	    return "redirect:/preliminaryPPR15";
	}

	
	@GetMapping("/completePreliminaryPPR15")
	public String completePreliminaryPPR15(@RequestParam Integer id,
	                                       RedirectAttributes redirectAttributes,
	                                       HttpSession session) {

	    String userid = (String) session.getAttribute("userid");

	    if (userid == null) {
	        return "redirect:/login";
	    }

	    PPRMigrationDetails data =
	            pprMigrationDetailsRepository.findById(id).orElse(null);

	    if (data != null) {
	        data.setStatus('C');
	        data.setUpdatedBy(userid);
	        pprMigrationDetailsRepository.save(data);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record Completed Successfully.");
	    }

	    return "redirect:/preliminaryPPR15";
	}

}
