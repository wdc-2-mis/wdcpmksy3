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
		List<MPpr> records = pprRepo.findAll();
	    model.addAttribute("records", records);
        return "ppr/pprDistrict";
    }
	
	@PostMapping("/savePPRDistrict")
	public String savePreliminaryPPR4A(@RequestParam("fyear") Integer finYrCd, @RequestParam("district") Integer dcode, @RequestParam("agency") String projectName, @RequestParam("micro") List<Integer> mwIds,
	        HttpSession session, Model model, HttpServletRequest servletRequest, RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userid");
	    Integer stCode = (Integer) session.getAttribute("stcode");
	    Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    String message = pprService.savePreliminaryPPR(finYrCd, dcode, projectName, mwIds, userId,  stCode, servletRequest);
	    model.addAttribute("distList", districtService.getDistrictsByState(stcode));
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
        model.addAttribute("microwatershedList", microService.getMicroServiceIdandName());
        List<MPpr> records = pprRepo.findAll();
	    model.addAttribute("records", records);
        model.addAttribute("saveMessage", message);
        redirectAttributes.addFlashAttribute("saveMessage", message);
	    return "redirect:/pprDistrict";
	}
	
	@PostMapping("/updatePPRDistrict")
	public String updatePPRDistrict(@RequestParam("pprId") Integer pprId, @RequestParam("microIds") List<Integer> microIds, HttpSession session, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {

	    String userId = (String) session.getAttribute("userid");
	    Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());

	    String message = pprService.updatePreliminaryPPR(pprId, microIds, userId, request);

	    // ✅ reload fresh data
	    model.addAttribute("distList", districtService.getDistrictsByState(stcode));
	    model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
	    model.addAttribute("microwatershedList", microService.getMicroServiceIdandName());
	    List<MPpr> records = pprRepo.findAll();  // fresh fetch
	    model.addAttribute("records", records);
	    model.addAttribute("saveMessage", message);

	    redirectAttributes.addFlashAttribute("saveMessage", message);
	    return "redirect:/pprDistrict";
	}

 
	@GetMapping("/completePPRDistrict/{id}")
	public String completePPRDistrict(@PathVariable Integer id, RedirectAttributes redirectAttributes, HttpSession session) {
		 String userId = (String) session.getAttribute("userid");
	    String message = pprService.completePPRDist(id, userId);
	    redirectAttributes.addFlashAttribute("saveMessage", message);
	    return "redirect:/pprDistrict";
	}
	
	@GetMapping("/deletePPRDistrict/{id}")
	public String deletePPRDistrict(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		 String message = pprService.deletePPRDist(id);
	    redirectAttributes.addFlashAttribute("saveMessage", message);
	    return "redirect:/pprDistrict";
	}

}
