package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.PPR.service.PprAreaCoverService;
import gov.dolr.wdcpmksy3.service.DistrictService;
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
	

	@GetMapping("/districtStats/{dcode}")
	@ResponseBody
	public Map<String, Object> getDistrictStats(@PathVariable Integer dcode) {
	    Map<String, Object> result = new HashMap<>();
	    result.put("totalMw", pprService.getTotalMicroWatersheds(dcode));
	    result.put("microWatersheds", pprService.getMicroWatershedsByDistrict(dcode));
	    return result;
	}

	@GetMapping("/microWatershedArea/{mwId}")
	@ResponseBody
	public Map<String, Object> getMicroWatershedArea(@PathVariable Integer mwId) {
	    Map<String, Object> result = new HashMap<>();
	    result.put("area", pprService.getMicroWatershedArea(mwId));
	    return result;
	}

	
}
