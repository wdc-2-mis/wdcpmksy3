package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.entity.DprNrmDtl;
import gov.dolr.wdcpmksy3.DPR.entity.MPhyActivity;
import gov.dolr.wdcpmksy3.DPR.repository.DprDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprNrmDtlRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyActivityRepository;
import gov.dolr.wdcpmksy3.DPR.repository.MPhyHeadsRepository;
import gov.dolr.wdcpmksy3.DPR.service.DPRNRMPlanService;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;

import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionary;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DPRNRMPlanController {
	
	@Autowired
    private PprTransactionRepository tranrepo;
	
	@Autowired
    private MPhyHeadsRepository head;
	
	@Autowired
    private MPhyActivityRepository actrepo;
	
	@Autowired
    private DPRNRMPlanService dprserv;
	
	@Autowired
    private DprNrmDtlRepository plndtlrepo;
	
	@Autowired
    private DprDetailsRepository plan;
	
	@GetMapping("/createDPRNRMPlan")
    public String createDPRNRMPlan(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
        if(userid==null){

            return "redirect:/login";
        }
        List<Object[]> list=plan.getDprNrmProjectPlanList(stcode);
        
        List<Object[]> finalList = new ArrayList<>();

        int srNo = 1;
        Integer previousId = null;

        for (Object[] row : list) {

            Integer currentId = ((Number) row[0]).intValue();

            // Create a new array with one extra column for serial number
            Object[] newRow = Arrays.copyOf(row, row.length + 1);

            if (previousId == null || !previousId.equals(currentId)) {
                newRow[row.length] = srNo++;   // Serial No.
            } else {
                newRow[row.length] = "";       // Blank for duplicate rows
            }
            finalList.add(newRow);
            previousId = currentId;
        }
        model.addAttribute("dprNrmProjectPlanList", finalList);
        model.addAttribute("headList", head.findAllByOrderBySeqNoAsc());
        model.addAttribute("distList", tranrepo.findDistrictsByStCodeAndActionA(stcode));
        
        return "dpr/addDPRNRMTarget";
    }
	
	@GetMapping("/getProjectsByDistrictDPRNRM")
	@ResponseBody
	public List<Map<String, Object>> getProjectsByDistrictDPRNRM(@RequestParam Integer dcode, Model model) {

	    List<MPpr> projects = tranrepo.findPprByDcodeAndActionA(dcode);

	    return projects.stream().map(p -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", p.getPprId());
	        map.put("name", p.getProjectName());
	        map.put("selectedDistrict", dcode);
	        model.addAttribute("selectedDcode", dcode);
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getActivitiesByHeadCodeDPRNRM")
	@ResponseBody
	public List<Map<String, Object>> getActivitiesByHeadCodeDPRNRM(@RequestParam Integer headId, Model model) {

	    List<MPhyActivity> act = actrepo.findByHead_HeadCodeOrderBySeqNoAsc(headId);

	    return act.stream().map(a -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id", a.getActivityCode());
	        map.put("name", a.getActivityDesc());
	        return map;
	    }).toList();
	}
	
	@GetMapping("/getUnitDescByActivity")
	@ResponseBody
	public ResponseEntity<String> getUnitDescByActivity(@RequestParam("actId") Integer actId) {

	    String unitDesc = actrepo.findUnitDescByActivityCode(actId);
	    System.out.println("kdy"+unitDesc);
	    if (unitDesc == null) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(unitDesc);
	}
	
	@GetMapping("/checkProjectActivityExistsNRM")
	@ResponseBody
	public String checkProjectActivityExistsNRM(@RequestParam("pprid") Integer pprid, @RequestParam("actId") Integer actId) {

	    boolean exists =plndtlrepo.existsByPprIdAndActivityId(pprid,actId);
	   // System.out.println("kdy"+exists);
	    if (exists) {
	        return "EXISTS";
	    }

	    return "NOT_EXISTS";
	}
	
	 @PostMapping("/saveDPRNRMTarget")
	 public String saveDPRNRMTarget(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer district,
	    		@RequestParam Integer project,
	    		@RequestParam Integer head,
	            @RequestParam Integer activity,
	            @RequestParam BigDecimal target,
	            @RequestParam String action,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				Integer block=0;
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=dprserv.saveDPRNRMTarget(district, project, head, activity, target, action, 
							userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "DPR NRM Target saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to saved DPR NRM Target.");
				}
				catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to DPR NRM Target.");
				}
				return "redirect:/createDPRNRMPlan";	
	    }
	 
	 	@GetMapping("/deleteDPRNRMTarget")
	    public String deleteDPRNRMTarget(HttpSession session, Model model, @RequestParam("id") Integer id,  
	    		RedirectAttributes redirectAttributes) {

			String userid=(String)session.getAttribute("userid");
			try {
				
		        if(userid==null){
		
		            return "redirect:/login";
		        }
		        DprNrmDtl data = plndtlrepo.findById(id).orElse(null);
	            if (data == null) {
	                redirectAttributes.addFlashAttribute("error", "Record not found.");
	                return "redirect:/createDPRNRMPlan";
	            }
	            
	            if (plndtlrepo.existsById(id)) {
	            	plndtlrepo.deleteById(id);
	            	redirectAttributes.addFlashAttribute("success", "Activity Record deleted successfully.");
	            }
	            else {
	            	redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
	            }
	            
	        } 
	        catch (Exception e) {

	            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
	            e.printStackTrace();
	        }
			return "redirect:/createDPRNRMPlan";
	    }

}
