package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.GPDropdownDTO;
import gov.dolr.wdcpmksy3.PPR.dto.PprProjectAtGlanceDTO;
import gov.dolr.wdcpmksy3.PPR.dto.VillageDropdownDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.PPR.service.PprProjectGlanceService;
import gov.dolr.wdcpmksy3.PPR.service.ProjectTypeService;
import gov.dolr.wdcpmksy3.entity.MGramPanchayat;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.repository.MGramPanchayatRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprProjectAtGlanceController {
	
	@Autowired
    private DistrictService districtService;
	
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
	
	@Autowired
	private MGramPanchayatRepository mGPRepo;
	
	@Autowired
	private VillageRepository villageRepo;
	 
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
	            model.addAttribute("pprProjectAtGlanceList",
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
		
		return "ppr/pprProjectAtGlance";
	}
	
	@GetMapping("/getGPlistbyBlock")
	@ResponseBody
	public List<GPDropdownDTO> getGPlistbyBlock(@RequestParam Integer blockcode) {
	    List<MGramPanchayat> gpList = mGPRepo.getListMGramPanchayatByBlock(blockcode);
	    return gpList.stream().map(gp -> new GPDropdownDTO(
	                    gp.getGcode(),
	                    gp.getGramPanchayatName()
	            )).toList();
	}
	
	@GetMapping("/getVillageListByGcode")
	@ResponseBody
	public List<VillageDropdownDTO> getVillageListByGcode(
	        @RequestParam Integer gcode) {
	    List<MVillage> villageList = villageRepo.findByGramPanchayat_Gcode(gcode);
	    return villageList.stream().map(village -> new VillageDropdownDTO(
	                    village.getVcode(),
	                    village.getVillageName()
	            )).toList();
	}
	
	@PostMapping("/savePprProjectAtGlance")
	public String savePprProjectAtGlance(
	        @ModelAttribute PprProjectAtGlanceDTO dto,
	        HttpSession session,
	        HttpServletRequest request) {
	    String userid = (String) session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }
	    pprProjectGlanceServ.savePprProjectAtGlance(dto, userid, request.getRemoteAddr());
	    return "redirect:/pprProjectAtGlance";
	}
	
	@GetMapping("/getPprProjectGlanceById")
	@ResponseBody
	public PprProjectAtGlanceDTO getPprProjectGlanceById(@RequestParam Integer id) {
	    return pprProjectGlanceServ.getPprProjectGlanceById(id);
	}
	
	@PostMapping("/updatePprProjectAtGlance")
	public String updatePprProjectAtGlance(@ModelAttribute PprProjectAtGlanceDTO dto, HttpSession session, HttpServletRequest request) {
	    String userid =(String) session.getAttribute("userid");
	    if (userid == null) {
	        return "redirect:/login";
	    }
	    pprProjectGlanceServ.updatePprProjectAtGlance(dto, userid, request.getRemoteAddr());

	    return "redirect:/pprProjectAtGlance";
	}
	
	@GetMapping("/getVillageDetailsByVcode")
	@ResponseBody
	public MVillage getVillageDetailsByVcode(@RequestParam Integer vcode) {
	    return villageRepo.findByVcode(vcode);
	}
	
	@GetMapping("/deletePprProProjectGlance")
	public String deletePprProProjectGlance(@RequestParam Integer id, RedirectAttributes redirectAttributes) {

	    try {
	        pprProjectGlanceServ.deletePprProjectGlance(id);
	        redirectAttributes.addFlashAttribute("success", "Project Glance deleted successfully.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Unable to delete Project Glance.");
	    }
	    return "redirect:/pprProjectAtGlance";
	}
	
	@GetMapping("/completePprProProjectGlance")
	public String completePprProProjectGlance(@RequestParam("id") Integer id,
	        RedirectAttributes redirectAttributes) {
	    pprProjectGlanceServ.completePprProjectGlance(id);
	    redirectAttributes.addFlashAttribute("success", "Project Glance completed successfully.");

	    return "redirect:/pprProjectAtGlance";
	}

}
