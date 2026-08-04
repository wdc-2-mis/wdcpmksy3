package gov.dolr.wdcpmksy3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.entity.MBlock;
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
    private MPprRepository mpprrep;
	
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
	
	
	@ResponseBody
	@GetMapping("/getProjectByDistrictPPR8")
	public List<MPpr> getProjectByDistrictPPR8(Integer dcode){
	    return mpprRepository.findByDistrictDcode(dcode);
	}
	
	
	
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("/getBlockByProjectPPR8") public List<MBlock>
	 * getBlockByProjectPPR8(Integer pprId){
	 * 
	 * List<PprProposedArea> areas =
	 * PprProposedAreaRepository.findByPprPprId(pprId);
	 * 
	 * List<Integer> blockCodes = areas.stream() .map(area ->
	 * area.getBlock().getBcode()) .toList();
	 * 
	 * return blockRepository.findByBcodeIn(blockCodes); }
	 */
	
	
}
