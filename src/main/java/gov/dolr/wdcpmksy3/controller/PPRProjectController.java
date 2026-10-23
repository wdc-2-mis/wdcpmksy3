package gov.dolr.wdcpmksy3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.PPR.service.MicroWatershedService;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRProjectController {

	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private FinYearService finService;
	
	@Autowired
	private MicroWatershedService microService;
	
	@Autowired
	private PPRDistrictService pprService;
	
	@Autowired
	private MPprRepository pprRepo;
	
	@Autowired
	private PprTransactionRepository pprTransactionRepository;
	
	@GetMapping("/pprDistrict")
	public String ppr1(HttpSession session,
	                    Model model,
	                    @RequestParam(required = false) Integer pprid) {

	    Object userid = session.getAttribute("userid");

	    if (userid == null) {
	        return "redirect:/login";
	    }

	    String statename = session.getAttribute("statename").toString();
	    Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());

	    if (pprid != null) {
	        pprService.OpenPPRDist(pprid);
	    }

	    // Check whether Institutional Structure is completed
	    boolean institutionalStructureCompleted =
	            pprService.isInstitutionalStructureCompleted(stcode);

	    model.addAttribute("institutionalStructureCompleted",
	                       institutionalStructureCompleted);

	    if (!institutionalStructureCompleted) {
	        model.addAttribute(
	            "error",
	            "Please complete the Institutional Structure before entering Preliminary Project Report details."
	        );
	    }

	    model.addAttribute("distList",
	            districtService.findCompletedDistrictsByState(stcode));

	    model.addAttribute("finYearList",
	            finService.getFinYearCdAndDesc());

	    model.addAttribute("microwatershedList",
	            microService.getMicroServiceIdandName());

	    model.addAttribute("statename", statename);
	    model.addAttribute("stcode", stcode);

	    List<MPpr> records = pprRepo.findByStateOrdered(stcode);
	    
	    Map<Integer, String> pprStatusMap = new HashMap<>();

	    for (MPpr rec : records) {
	        pprStatusMap.put(
	            rec.getPprId(),
	            getPprStatus(rec.getPprId())
	        );
	    }
	    
	    model.addAttribute("records", records);
	    model.addAttribute("pprStatusMap", pprStatusMap);

	    return "ppr/pprDistrict";
	}
	
	private String getPprStatus(Integer pprId) {

	    return pprTransactionRepository
	            .findTopByPpr_PprIdOrderBySentonDescTranxIdDesc(pprId)
	            .map(transaction -> {

	                Character action = transaction.getAction();

	                if (action == null) {
	                    return "PPR in Draft Mode";
	                }

	                switch (action) {
	                    case 'F':
	                        return "Forwarded to DoLR";

	                    case 'A':
	                        return "PPR Approved";

	                    case 'B':
	                        return "Refer Back";

	                    default:
	                        return "PPR in Draft Mode";
	                }
	            })
	            .orElse("PPR in Draft Mode");
	}
	
	@GetMapping("/getPPRDistrictRecords")
	@ResponseBody
	public List<Map<String, Object>> getPPRDistrictRecords(
	        @RequestParam Integer dcode,
	        HttpSession session) {

	    Object userid = session.getAttribute("userid");

	    if (userid == null) {
	        return List.of();
	    }

	    Integer stcode = Integer.parseInt(
	            session.getAttribute("stcode").toString());

	    // Verify that the selected district belongs to the user's state.
	    boolean validDistrict = districtService
	            .findCompletedDistrictsByState(stcode)
	            .stream()
	            .anyMatch(d -> d.getDcode().equals(dcode));

	    if (!validDistrict) {
	        return List.of();
	    }

	    List<MPpr> records = pprRepo.findByDistrictOrdered(dcode);

	    return records.stream().map(rec -> {
	        Map<String, Object> map = new HashMap<>();

	        map.put("pprId", rec.getPprId());
	        map.put("finYear",
	                rec.getFinYear() != null
	                        ? rec.getFinYear().getFinYrDesc() : "");
	        map.put("dcode", rec.getDistrict().getDcode());
	        map.put("district", rec.getDistrict().getDistName());
	        map.put("pprName", rec.getPprName());
	        map.put("projectName", rec.getProjectName());
	        map.put("status", rec.getStatus());
	        map.put("pprStatus", getPprStatus(rec.getPprId()));
	        map.put("microWatersheds",
	                rec.getMicroWatersheds() == null
	                        ? List.of()
	                        : rec.getMicroWatersheds().stream()
	                            .map(mw -> {
	                                Map<String, Object> micro = new HashMap<>();
	                                micro.put("mwId",
	                                        mw.getMicroWatershed().getMwId());
	                                micro.put("mwName",
	                                        mw.getMicroWatershed().getMwName());
	                                return micro;
	                            })
	                            .toList());

	        map.put("microIdsCsv", rec.getMicroIdsCsv());

	        return map;
	    }).toList();
	}
	
	@GetMapping("/checkDistrictCompleted")
	@ResponseBody
	public Map<String, Object> checkDistrictCompleted(
	        @RequestParam("dcode") Integer dcode) {
		boolean completed = pprService.isDistrictCompleted(dcode);

	    Map<String, Object> response = new HashMap<>();
	    response.put("completed", completed);

	    return response;
	}
	
	@GetMapping("/nextProjectName")
	@ResponseBody
	public Map<String, Object> getNextProjectName(
	        @RequestParam("dcode") Integer dcode,
	        @RequestParam("fyear") Integer fyear) {

	    return pprService.getNextProjectName(dcode, fyear);
	}
	
	@PostMapping("/savePPRDistrict")
	public String savePreliminaryPPR(@RequestParam("fyear") Integer finYrCd,
	                                 @RequestParam("district") Integer dcode,
	                                 @RequestParam("projectName") String projectName,
	                                 @RequestParam("micro") List<Integer> mwIds,
	                                 HttpSession session,
	                                 HttpServletRequest servletRequest,
	                                 RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userid");
	    Integer stCode = (Integer) session.getAttribute("stcode");

	    try {
	        pprService.savePreliminaryPPR(finYrCd, dcode, projectName, mwIds, userId, stCode, servletRequest);

	        redirectAttributes.addFlashAttribute("success", "Record saved successfully!");

	    } catch (Exception e) {
	       redirectAttributes.addFlashAttribute("error", "Error saving record: " + e.getMessage());
	    }

	    return "redirect:/pprDistrict";
	}

	
	@PostMapping("/updatePPRDistrict")
	public String updatePPRDistrict(@RequestParam("pprId") Integer pprId,
	                                @RequestParam("microIds") List<Integer> microIds,
	                                HttpSession session,
	                                HttpServletRequest request,
	                                RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userid");

	    try {
	        // Call service update method with projectName
	        pprService.updatePreliminaryPPR(pprId, microIds, userId, request);

	        redirectAttributes.addFlashAttribute("success", "Record updated successfully!");

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error updating record: " + e.getMessage());
	    }

	    return "redirect:/pprDistrict";
	}



 
	@GetMapping("/completePPRDistrict")
	public String completePPRDistrict(
	        @RequestParam("id") Integer id,
	        RedirectAttributes redirectAttributes,
	        HttpSession session) {

	    try {

	        System.out.println(
	                "UserId in session: " +
	                session.getAttribute("userId")
	        );

	        String updatedBy =
	                String.valueOf(session.getAttribute("userId"));

	        pprService.completePPRDist(id, updatedBy);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record completed successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage()
	        );
	    }

	    return "redirect:/pprDistrict";
	}
	
	@GetMapping("/deletePPRDistrict")
	public String deletePPRDistrict(
	        @RequestParam("id") Integer id,
	        RedirectAttributes redirectAttributes,
	        HttpSession session) {

	    try {

	        System.out.println(
	                "UserId in session: " +
	                session.getAttribute("userId")
	        );

	        String updatedBy =
	                String.valueOf(session.getAttribute("userId"));

	        pprService.deletePPRDist(id);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record deleted successfully."
	        );

	    } catch (Exception e) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage()
	        );
	    }

	    return "redirect:/pprDistrict";
	}

}
