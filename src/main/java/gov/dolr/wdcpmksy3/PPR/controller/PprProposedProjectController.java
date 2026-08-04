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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.service.MicroWatershedService;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.PPR.service.ProjectTypeService;
import gov.dolr.wdcpmksy3.service.DistrictService;
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
	
	@GetMapping("/pprProposedProjectDetails")
    public String pprProposedProjectDetails(HttpSession session, Model model) {
		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    
		String userid=(String)session.getAttribute("userid");
		if(userid==null){
            return "redirect:/login";
        }
		model.addAttribute("districtList", districtService.getDistrictsByState(stcode));
		model.addAttribute("projectTypeList", projectTypeServ.getProjectType());
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
            response.put("project", ppr.getProjectName());
            response.put("microWatershedList", pprService.getMicroWatershedsByDistrict(dcode));
        } else {
            response.put("project", "");
            response.put("microWatershedList", Collections.emptyList());
        }
        return response;
    }
	
	@GetMapping("/getMicroWatershedCodeByMwId")
	@ResponseBody
	public String getMicroWatershedCodeByMwId(@RequestParam Integer mwId) {
	    MicroWatershed mw = microWatershedServ.getMicroWatershedById(mwId);
	    return mw == null ? "" : mw.getMwCode();
	}

	
}
