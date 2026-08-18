package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprMicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.PPR.service.PprProjectGlanceService;
import gov.dolr.wdcpmksy3.PPR.service.ProjectTypeService;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprProjectAtGlanceController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private PPRDistrictService pprService;
	
	@Autowired
	private MPprRepository mPprRepo;
	
	@Autowired 
	private ProjectTypeService projectTypeServ;
	
	@Autowired
	private PprProjectGlanceService pprProjectGlanceServ;
	
	@Autowired
	private MBlockRepository mBlockRepo;
	
	@Autowired
	private MicroWatershedRepository microWatershedRepo;
	 
	@GetMapping("/pprProjectAtGlance")
    public String pprProjectAtGlance(@RequestParam(required = false) Integer dcode, HttpSession session, Model model) {
		
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
	            		pprProjectGlanceServ.getPprProjectGlanceList(ppr));

	            model.addAttribute("project", ppr.getProjectName());
	            model.addAttribute("blockList", mBlockRepo.findByDistrict_DcodeOrderByBlockNameAsc(dcode));
	            model.addAttribute("microWatershedList",
	            		microWatershedRepo.getListOfMicroWatershedbyMwIds(ppr.getPprId()));
	        }
	    }
		model.addAttribute("districtList", districtService.getDistrictsByState(stcode));
		model.addAttribute("projectTypeList", projectTypeServ.getProjectType());
		model.addAttribute("state", statename);
		
		return "prioritizedListOfProposedProject";
	}

}
