package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.CoveredAreaDTO;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.PPR.service.PprAreaCoverService;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprAreaCoveredController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private PPRDistrictService pprService;
	
	@Autowired
	private PprAreaCoverService pprAreaService;
	
	@GetMapping("/areaCoveredUnderWP")
    public String areaCoveredUnderWP(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("schemeList", pprAreaService.getAllSchemes().stream().limit(6));
        return "ppr/areaCovered";
	}
	

	@GetMapping("/areaCoveredUnderWP/{dcode}")
	public String areaCoveredUnderWPByDistrict(
	        @PathVariable Integer dcode,
	        HttpSession session,
	        Model model) {

	    Object userid = session.getAttribute("userid");

	    if (userid == null) {
	        return "redirect:/login";
	    }

	    Integer stcode = Integer.parseInt(
	            session.getAttribute("stcode").toString()
	    );

	    model.addAttribute(
	            "distList",
	            districtService.getPPRDistrictsByState(stcode)
	    );

	    model.addAttribute(
	            "schemeList",
	            pprAreaService.getAllSchemes().stream().limit(6)
	    );

	    // District received from Next button
	    model.addAttribute("selectedDcode", dcode);

	    return "ppr/areaCovered";
	}
	
	@GetMapping("/districtStats/{dcode}")
	@ResponseBody
	public Map<String, Object> getDistrictStats(@PathVariable Integer dcode, Model model) {
	    Map<String, Object> result = new HashMap<>();
	    result.put("totalMw", pprService.getTotalMicroWatersheds(dcode));
	    result.put("microWatersheds", pprService.getMicroWatershedsByDistrict(dcode));
	    List<CoveredAreaDTO> records = pprAreaService.getSchemeAreasByDistrict(dcode);
	    model.addAttribute("schemeList", pprAreaService.getAllSchemes().stream().limit(6));
	    result.put("records", records);

	    return result;
	}

	

	@GetMapping("/microWatershedArea/{mwId}")
	@ResponseBody
	public Map<String, Object> getMicroWatershedArea(@PathVariable Integer mwId) {
	    Map<String, Object> result = new HashMap<>();
	    result.put("area", pprService.getMicroWatershedArea(mwId));
	    result.put("status", pprAreaService.getMicroWatershedStatus(mwId));
	    return result;
	}

	
	
	@PostMapping("/saveAreaWP")
    public String saveAreaWP(@RequestParam Integer district,
                                  @RequestParam Integer mw,
                                  @RequestParam Map<String, String> params,
                                  Model model, HttpServletRequest servletRequest, HttpSession session, RedirectAttributes redirectAttributes) {
	  String userId = (String) session.getAttribute("userid");
	  try {
	        pprAreaService.saveRecords(district, mw, params, userId, servletRequest);
            redirectAttributes.addFlashAttribute("success", "Records saved successfully!");
	    } catch (Exception ex) {
	        redirectAttributes.addFlashAttribute("error", "Failed to save records: " + ex.getMessage());
	    }
         return "redirect:/areaCoveredUnderWP";
    }
	
	@PostMapping("/updateAreaWP")
	public String updateAreaWP(@RequestParam Integer editPprId, @RequestParam Integer editMWId,
	                           @RequestParam Map<String,String> params,
	                           HttpSession session,
	                           HttpServletRequest request,
	                           RedirectAttributes redirectAttributes){

	    String userId = (String) session.getAttribute("userid");

	    try{

	        pprAreaService.updateRecords(editPprId,editMWId,params,userId,request);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record updated successfully.");

	    }catch(Exception e){

	        redirectAttributes.addFlashAttribute(
	                "error",
	                e.getMessage());
	    }

	    return "redirect:/areaCoveredUnderWP";
	}

	@GetMapping("/completeAreaConveredWP")
	public String completeAreaConveredWP(@RequestParam("id") Integer pprId, @RequestParam("mwId") Integer mwId, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userid");

	    try {

	        pprAreaService.completeRecords(pprId, mwId, userId, request);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record completed successfully.");

	    } catch (Exception ex) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                ex.getMessage());
	    }

	    return "redirect:/areaCoveredUnderWP";
	}
	
	@GetMapping("/deleteAreaConveredWP")
	public String deleteAreaConveredWP(@RequestParam("id") Integer pprId, @RequestParam("mwId") Integer mwId, RedirectAttributes redirectAttributes) {

	    try {

	        pprAreaService.deleteRecords(pprId, mwId);

	        redirectAttributes.addFlashAttribute(
	                "success",
	                "Record deleted successfully.");

	    } catch (Exception ex) {

	        redirectAttributes.addFlashAttribute(
	                "error",
	                ex.getMessage());
	    }

	    return "redirect:/areaCoveredUnderWP";
	}
}
