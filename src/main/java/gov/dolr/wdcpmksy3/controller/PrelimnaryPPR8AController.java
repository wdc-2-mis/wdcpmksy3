package gov.dolr.wdcpmksy3.controller;

import java.util.ArrayList;
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
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.entity.MBlock;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.repository.PprProposedAreaRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;


@Controller
public class PrelimnaryPPR8AController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private PprProposedAreaRepository pprprep;
	
	@Autowired
	private MPprRepository mpprRepository;

	@Autowired
	private MBlockRepository blockRepository;
	
	@GetMapping("/preliminaryPPR8")
    public String preliminaryPPR8(HttpSession session, Model model){

		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");

        if(userid==null){

            return "redirect:/login";
        }
        model.addAttribute("stateName",statename);
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        
        model.addAttribute("projectList",new ArrayList<>());
        model.addAttribute("blockList",new ArrayList<>());
        model.addAttribute("ppr8List",new ArrayList<>());

        return "ppr8";
    }
	
	
	
	@GetMapping("/getBlockByProjectPPR8")
	@ResponseBody
	public List<Map<String,Object>> getBlocksByProject(@RequestParam Integer pprId){

	    List<MBlock> block=blockRepository.getBlocksByProject(pprId);

	    return block.stream().map(b->{

	        Map<String,Object> map=new HashMap<>();

	        map.put("id",b.getBcode());
	        map.put("name",b.getBlockName());

	        return map;

	    }).toList();

	}
	 
	
	
}
