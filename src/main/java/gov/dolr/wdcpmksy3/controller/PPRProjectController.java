package gov.dolr.wdcpmksy3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.PPR.service.MicroWatershedService;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
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
	
	@GetMapping("/pprDistrict")
    public String ppr1(HttpSession session, Model model) 
	{
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
           
        model.addAttribute("distList", districtService.getDistrictsByState(stcode));
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
        model.addAttribute("microwatershedList", microService.getMicroServiceIdandName());
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
		List<MPpr> records = pprRepo.findAllOrderByStatusAndId();
	    model.addAttribute("records", records);
        return "ppr/pprDistrict";
    }
	
	@PostMapping("/savePPRDistrict")
	public String savePreliminaryPPR(@RequestParam("fyear") Integer finYrCd,
	                                 @RequestParam("district") Integer dcode,
	                                 @RequestParam("agency") String projectName,
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
	                                @RequestParam("projectName") String projectName,
	                                HttpSession session,
	                                HttpServletRequest request,
	                                RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userid");

	    try {
	        // Call service update method with projectName
	        pprService.updatePreliminaryPPR(pprId, microIds, projectName, userId, request);

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
