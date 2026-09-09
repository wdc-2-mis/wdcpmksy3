package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.PPR.dto.CoveredAreaDTO;
import gov.dolr.wdcpmksy3.PPR.dto.WatershedAreaBean;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAreaCoveredRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProjectGlanceRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProposedProjectRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWatershedCoveredAreaRepo;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.PPR.service.PprAreaCoverService;
import gov.dolr.wdcpmksy3.PPR.service.PprProposedProjectService;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PPRViewController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
	private FinYearService finService;
	
	@Autowired
	private MPprRepository pprRepo;
	
	@Autowired
	private PprProposedProjectService proposedProjectService;
	
	@Autowired
	private PprWatershedCoveredAreaRepo area;
	
	@Autowired
    private PprProposedProjectRepository repository;
	
	@Autowired
	private PprProjectGlanceRepository pprProjectGlanceRepo;
	
	
	@GetMapping("/viewPPR")
    public String viewPPR(HttpSession session, Model model) {
		
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
      //  model.addAttribute("erosionList", erosionList);
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());

        return "viewPPR";
    }
	
	@PostMapping("/viewPPR")
    public String viewPPR1(HttpSession session, Model model, @RequestParam("fyear") Integer finYrCd,
            @RequestParam("district") Integer dcode, @RequestParam("project") Integer project) {
		
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		Object userid = session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        
        List<MPpr> records = pprRepo.findByDistrict_DcodeAndPprIdAndFinYear_FinYrCdAndStatus( dcode,   project,  finYrCd, "C");
        model.addAttribute("records", records);
        
        List<PprWatershedCoveredArea> areaRecords =area.findByPprPprIdAndStatus(project, "C");
        
        Map<Integer, WatershedAreaBean> map = new LinkedHashMap<>();

        for (PprWatershedCoveredArea row : areaRecords) {

            Integer mwId = row.getMicroWatershed().getMwId();

            WatershedAreaBean bean = map.getOrDefault(mwId, new WatershedAreaBean());

            bean.setMwId(mwId);
            bean.setMwName(row.getMicroWatershed().getMwName());

            Integer scheme = row.getScheme().getSchemeId();

            switch (scheme) {
                case 1:
                    bean.setPreNo(row.getNoMw());
                    bean.setPreArea(row.getAreaMw());
                    break;

                case 2:
                    bean.setDpapNo(row.getNoMw());
                    bean.setDpapArea(row.getAreaMw());
                    break;

                case 3:
                    bean.setDdpNo(row.getNoMw());
                    bean.setDdpArea(row.getAreaMw());
                    break;

                case 4:
                    bean.setIwdpNo(row.getNoMw());
                    bean.setIwdpArea(row.getAreaMw());
                    break;

                case 5:
                    bean.setIwmpNo(row.getNoMw());
                    bean.setIwmpArea(row.getAreaMw());
                    break;

                case 6:
                    bean.setPmksyNo(row.getNoMw());
                    bean.setPmksyArea(row.getAreaMw());
                    break;

                case 7:
                    bean.setOtherNo(row.getNoMw());
                    bean.setOtherArea(row.getAreaMw());
                    break;
            }

            map.put(mwId, bean);
        }
        model.addAttribute("watershedList", new ArrayList<>(map.values()));
        // ---- PPR-3 : Prioritized List of Proposed Projects ----
        
        List<PprProposedProject> ListOfProposedProject = repository.findByPprPprIdAndStatus(project, 'C');
        model.addAttribute("detailsOfListOfProposedProject", ListOfProposedProject);
        

        
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
        
        
        // ---- PPR-4 : Project at a Glance ----
        List<PprProjectGlance> pprProjectAtGlanceList = pprProjectGlanceRepo.findByPprPprIdAndStatus(project, 'C');
        model.addAttribute("pprProjectAtGlanceList", pprProjectAtGlanceList);

        


        return "viewPPR";
    }

}
