package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.service.CropTypeServices;
import gov.dolr.wdcpmksy3.PPR.service.MPprService;
import gov.dolr.wdcpmksy3.PPR.service.SoilTypeServices;
import gov.dolr.wdcpmksy3.PPR.service.VillageService;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRAgroClimateConditionController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private SoilTypeServices soilser;
	
    @Autowired
    private CropTypeServices cropser;
    
    @Autowired
    private MPprService pprService;
	
    @Autowired
    private VillageService villageService;
    
	@GetMapping("/agroClimateConditionPPR10")
    public String agroClimateConditionPPR10(HttpSession session, Model model) 
	{
		//System.out.println("PPR1 Session ID = " + session.getId());
		//String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

        if(userid==null){

            return "redirect:/login";
        }
       // model.addAttribute("ppr1List", isserv.getPPR1List(stcode));
        model.addAttribute("distList", districtService.findCompletedDistrictsByState(stcode));
        model.addAttribute("soilTypeList", soilser.getAllSoilTypeDetails());
        model.addAttribute("cropTypeList", cropser.getAllCropTypeDetails());
		model.addAttribute("stcode", stcode);
        return "ppr/agroClimateCondition";
    }
	
	@GetMapping("/getProjectsByDistrictPPR10")
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
	
	@GetMapping("/getVillagesByProjectPPR10")
	@ResponseBody
	public List<Map<String,Object>> getVillagesByProject(@RequestParam Integer pprId){

	    List<MVillage> villages=villageService.getVillagesByProject(pprId);

	    return villages.stream().map(v->{

	        Map<String,Object> map=new HashMap<>();

	        map.put("id",v.getVcode());
	        map.put("name",v.getVillageName());

	        return map;

	    }).toList();

	}

}
