package gov.dolr.wdcpmksy3.DPR.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherDtl;
import gov.dolr.wdcpmksy3.DPR.repository.DprDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherDtlRepository;
import gov.dolr.wdcpmksy3.DPR.service.DPRNRMPlanService;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ViewConfirmDPRPlan {
	
	@Value("${dpr.path}")
    private String uploadPath;
	
	@Autowired
    private PprTransactionRepository tranrepo;
	
	@Autowired
    private DprDetailsRepository plan;
	
	@Autowired
    private DprOtherDtlRepository  other;
	
	@Autowired
    private DPRNRMPlanService dprserv;
	
	@GetMapping("/getViewConfirmDPRPlan")
    public String getViewConfirmDPRPlan(HttpSession session, Model model, @RequestParam(required = false) Integer district, 
    		@RequestParam(required = false) Integer project) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		List<Object[]> finalList = new ArrayList<>();
        if(userid==null){

            return "redirect:/login";
        }
        if(project!=null) {	
		List<Object[]> list =plan.getDprNrmProjectPlanComplitedList(project);
		
		List<Object[]> listelp =plan.getDprELPProjectPlanCompletedList(project);
		
		List<DprOtherDtl> otheract=other.findCompletedOtherDtlByPprId(project);

		if(list!=null && !list.isEmpty()) {
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
	    }
	    model.addAttribute("dprNrmProjectPlanList", finalList);
	    model.addAttribute("selectedDcode", district);
        model.addAttribute("selectedProject", project);
        model.addAttribute("dprELPList", listelp);
        model.addAttribute("dprOtherTargetList", otheract);
        model.addAttribute("projList", tranrepo.findPprByDcodeAndActionA(district));
        }
        model.addAttribute("distList", tranrepo.findDistrictsByStCodeAndActionA(stcode));
        
        return "dpr/viewConfirmDPRPlan";
    }
	
	 	@GetMapping("/checkDPRProjectDataApproved")
		@ResponseBody
		public String checkDPRProjectDataApproved(@RequestParam Integer pprid) {

	    	List<DprDetails> records = plan.findByPprPprIdandStatus(pprid);
		    
		    if (records != null && !records.isEmpty()) {
		        return "EXISTS";
		    }

		    return "NOT_EXISTS";
		}
	
	@PostMapping("/confirmDPR")
	 public String confirmDPR(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer pprdcode,
	    		@RequestParam Integer pprid,
	    		@RequestParam MultipartFile dprFile,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				Integer block=0;
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
					
					File dir = new File(uploadPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
			    	
					save=dprserv.updateDPRNRMELPOtherTarget(pprid, dprFile, 
							userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "DPR Approved.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to DPR Approved.");
				}
				catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to DPR Approved.");
				}
				return "redirect:/getViewConfirmDPRPlan";	
	    }

}
