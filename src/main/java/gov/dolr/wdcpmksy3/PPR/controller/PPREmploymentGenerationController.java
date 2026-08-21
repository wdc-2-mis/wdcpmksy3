package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.ArrayList;
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

import gov.dolr.wdcpmksy3.PPR.dto.PPREmploymentGenerationDTO;
import gov.dolr.wdcpmksy3.PPR.dto.PPREmploymentGenerationFormDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MEmploymentType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PPREmploymentGeneration;
import gov.dolr.wdcpmksy3.PPR.service.MEmploymentTypeService;
import gov.dolr.wdcpmksy3.PPR.service.MPprService;
import gov.dolr.wdcpmksy3.PPR.service.PPREmploymentGenerationService;
import gov.dolr.wdcpmksy3.PPR.service.VillageService;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPREmploymentGenerationController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private MPprService pprService;

    @Autowired
    private VillageService villageService;

    @Autowired
    private PPREmploymentGenerationService employmentService;

    @Autowired
    private MEmploymentTypeService employmentTypeService;
    
    
    

    @GetMapping("/pprEmploymentGeneration")
    public String employmentGenerationForm(HttpSession session, Model model) {

        Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
        Object userid = session.getAttribute("userid");
        if (userid == null) {
            return "redirect:/login";
        }

        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        
        List<MEmploymentType> employmentTypes = employmentTypeService.getAllEmploymentTypes();
        model.addAttribute("employmentTypeList", employmentTypes);

        return "ppr/pprEmploymentGeneration";
    }

    @GetMapping("/getProjectsByDistrictEmployment")
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

    @GetMapping("/getMicroWatershedsByProjectEmployment")
    @ResponseBody
    public List<Map<String, Object>> getMicroWatershedsByProject(@RequestParam Integer pprId) {
    	
        List<MicroWatershed> watersheds = employmentService.getMicroWatershedsByProject(pprId);
        
        return watersheds.stream().map(w -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", w.getMwId());
            map.put("name", w.getMwName());
            return map;
        }).toList();
    }

    @GetMapping("/getVillagesByProjectEmployment")
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

    @GetMapping("/getEmploymentVillageStatus")
    @ResponseBody
    public Map<String, Object> getEmploymentVillageStatus(@RequestParam Integer pprId, 
            @RequestParam Integer vcode, @RequestParam Integer mwId) {
        
        Map<String, Object> result = new HashMap<>();
        
        List<PPREmploymentGeneration> existingRecords = employmentService.findExistingRecords(pprId, vcode, mwId);
        
        Character status = null;
        
        List<Map<String, Object>> records = new ArrayList<>();
        
        if (!existingRecords.isEmpty()) {
            status = existingRecords.get(0).getStatus();
            
            for (PPREmploymentGeneration record : existingRecords) {
                Map<String, Object> recordMap = new HashMap<>();
                recordMap.put("employmentTypeId", record.getEmploymentType().getEmploymentTypeId());
                recordMap.put("employmentTypeName", record.getEmploymentType().getEmploymentTypeName());
                recordMap.put("status", record.getStatus());
                records.add(recordMap);
            }
            
        }
        
        result.put("status", status);
        result.put("exists", !existingRecords.isEmpty());
        result.put("records", records);
        
        return result;
    }

    @GetMapping("/getEmploymentGenerationByDistrict")
    @ResponseBody
    public List<Map<String, Object>> getEmploymentGenerationByDistrict(@RequestParam Integer dcode) {
        return employmentService.getEmploymentGenerationByDistrict(dcode);
    }

    @PostMapping("/savePPREmployment")
    public String saveEmployment(@RequestParam Integer dcode, @RequestParam Integer project, @RequestParam Integer watershed,
            @RequestParam Integer village, @ModelAttribute PPREmploymentGenerationFormDTO form, HttpSession session,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {

        try {
        	
            String userId = session.getAttribute("userid").toString();

            List<PPREmploymentGenerationDTO> employmentList = form.getEmploymentList();

            // Check for duplicates
            List<Integer> duplicateTypes = employmentService.findDuplicateEmploymentTypes(project, village, watershed, employmentList);

            if (!duplicateTypes.isEmpty()) {
                String typeNames = employmentService.getEmploymentTypeNames(duplicateTypes);
                redirectAttributes.addFlashAttribute("error",
                        "Duplicate entries found for: " + typeNames + ". Each employment type can only be entered once.");
                
                return "redirect:/pprEmploymentGeneration";
            }

            employmentService.saveEmploymentGeneration(dcode, project, watershed, village, employmentList, userId, request);

            redirectAttributes.addFlashAttribute("success", "Record saved successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Record not saved: " + e.getMessage());
        }

        return "redirect:/pprEmploymentGeneration";
    }

    @PostMapping("/updatePPREmployment")
    public String updateEmployment(@RequestParam Integer ppr_employment_id, @RequestParam(required = false) Integer sc,
            @RequestParam(required = false) Integer st, @RequestParam(required = false) Integer others,
            @RequestParam(required = false) Integer women, HttpSession session, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        try {
            String userId = session.getAttribute("userid").toString();

            employmentService.updateEmploymentGeneration(ppr_employment_id, sc, st, others, women, userId, request);

            redirectAttributes.addFlashAttribute("success", "Record updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Unable to update record: " + e.getMessage());
        }

        return "redirect:/pprEmploymentGeneration";
    }

    @GetMapping("/deletePPREmployment")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
    	
        try {
        	
            employmentService.delete(id);
            
            redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
            
        } catch (Exception e) {
        	
            redirectAttributes.addFlashAttribute("error", "Unable to delete.");
            
        }
        return "redirect:/pprEmploymentGeneration";
    }

    @GetMapping("/completePPREmployment")
    public String complete(@RequestParam Integer id, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        try {
        	
            employmentService.complete(id, session.getAttribute("userid").toString(), request);
            redirectAttributes.addFlashAttribute("success", "Record completed.");
            
        } catch (Exception e) {
        	
            redirectAttributes.addFlashAttribute("error", "Unable to complete.");
            
        }

        return "redirect:/pprEmploymentGeneration";
    }
}