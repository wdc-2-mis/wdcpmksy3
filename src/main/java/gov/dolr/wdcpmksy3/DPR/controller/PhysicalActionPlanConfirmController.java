package gov.dolr.wdcpmksy3.DPR.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlanTranx;
import gov.dolr.wdcpmksy3.DPR.repository.PhysicalActionPlanViewProjection;
import gov.dolr.wdcpmksy3.DPR.repository.WdcpmksyProjectPhysicalPlanDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.WdcpmksyProjectPhysicalPlanTranxRepository;
import gov.dolr.wdcpmksy3.DPR.service.PhysicalActionPlanService;
import gov.dolr.wdcpmksy3.PPR.dto.PprTransactionDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PhysicalActionPlanConfirmController {
	
	
	@Autowired
	private PhysicalActionPlanService  physicalActionPlanService;
	
	@Autowired
	private WdcpmksyProjectPhysicalPlanTranxRepository tranxRepository;
	
	@Autowired
	private WdcpmksyProjectPhysicalPlanDetailsRepository plndtl;
	
	
	@GetMapping("/wcdcslnaPhyActPlan")
    public String wcdcslnaPhyActPlan(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		String usertype=(String)session.getAttribute("usertype");
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		List<Object[]> finalList = new ArrayList<>();
        if(userid==null){

            return "redirect:/login";
        }
        	
        model.addAttribute("planList", physicalActionPlanService.viewARPlan(regid, usertype)); 
        model.addAttribute("comPlanList", physicalActionPlanService.viewCompletePlan(regid, usertype));
        model.addAttribute("movementPlanList", physicalActionPlanService.viewAAPMovement(regid, usertype));
        
        return "dpr/physicalActionPlanForWCDCSLNA";
    }
	
	 @PostMapping("/approveRejectAAP")
	 public String approveRejectAAP(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer planid,
	            @RequestParam Character action,
	            @RequestParam String remark,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				String usertype=(String)session.getAttribute("usertype");
				Integer sentfrom = Integer.parseInt(session.getAttribute("regid").toString());
				String value=null;
				String res =null;
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom, action, planid, usertype);
					if (sentto > 0 && sentfrom > 0)
						value = physicalActionPlanService.forwardByWCDC(sentto, sentfrom, planid, action, remark, usertype);
					if (value.equals("success") && usertype.equals("SL")) {
						res = "state";
					}
					if (value.equals("success") && usertype.equals("DI")) {
						res = "district";
					}
					
					
					
					
					if(save)
						redirectAttributes.addFlashAttribute( "success1", "DPR NRM Target saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error1", "Unable to saved DPR NRM Target.");
				}
				catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error1", "Unable to DPR NRM Target.");
				}
				return "redirect:/wcdcslnaPhyActPlan";	
	    }
	 
	 	@GetMapping("/getAAPTransaction")
		@ResponseBody
		public ResponseEntity<?> getPprTransaction(
		        @RequestParam("planId") Integer planId) {

		    try {

		        List<WdcpmksyProjectPhysicalPlanTranx> list =tranxRepository.findByPlan_PlanIdOrderBySentOnDesc(planId);

		        List<PprTransactionDto> result = new ArrayList<>();

		        for (WdcpmksyProjectPhysicalPlanTranx t : list) {

		            PprTransactionDto dto = new PprTransactionDto();

		            dto.setTranxId(t.getTranxId());

		            if (t.getPlan() != null) {
		                dto.setPprId(t.getPlan().getPlanId());
		            }

		       /*     if (t.getSentFrom() != null) {
		                dto.setSentFrom(t.getSentFrom().getUserId());
		            }

		            if (t.getSentTo() != null) {
		                dto.setSentTo(t.getSentTo().getUserId());
		            }  */
		            
		            if (t.getSentFrom() != null) 
		            {
		                String userId = t.getSentFrom().getUserId();
		                if (userId != null && userId.startsWith("DL")) {
		                    dto.setSentFrom("DoLR");
		                } 
		                else {
		                    dto.setSentFrom(userId);
		                }
		            }
		            if (t.getSentTo() != null) 
		            {
		                String userId = t.getSentTo().getUserId();
		                if (userId != null && userId.startsWith("DL")) {
		                    dto.setSentTo("DoLR");
		                } else {
		                    dto.setSentTo(userId);
		                }
		            }

		            dto.setAction(t.getAction());
		            dto.setRemarks(t.getRemarks());
		            dto.setSenton(t.getSentOn());

		            result.add(dto);
		        }

		        System.out.println("DTO records returned = " + result.size());

		        return ResponseEntity.ok(result);

		    } 
		    catch (Exception e) {

		        e.printStackTrace();

		        Map<String, Object> error = new HashMap<>();

		        error.put("success", false);
		        error.put("message", e.getMessage());

		        return ResponseEntity
		                .status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body(error);
		    }
		}
	 	
	 	
	 	@GetMapping("/getPhysicalActionPlanViewProjection")
		@ResponseBody
		public ResponseEntity<?> getPhysicalActionPlanViewProjection(
		        @RequestParam("planId") Integer planId) {

		    try {

		        List<PhysicalActionPlanViewProjection> list =plndtl.getPhysicalActionPlanViewProjection(planId);
		        List<PprTransactionDto> result = new ArrayList<>();

		        for (PhysicalActionPlanViewProjection t : list) {

		            PprTransactionDto dto = new PprTransactionDto();

		            dto.setPlanid(t.getPlan_id());
		            dto.setProject_name(t.getProject_name());
		            dto.setHead_desc(t.getHead_desc());
		            dto.setActivity_name(t.getActivity_name());
		            dto.setUnit_desc(t.getUnit_desc());
		            dto.setQty_planned(t.getQty_planned());

		            result.add(dto);
		        }

		        System.out.println("DTO records returned = " + result.size());

		        return ResponseEntity.ok(result);

		    } 
		    catch (Exception e) {

		        e.printStackTrace();

		        Map<String, Object> error = new HashMap<>();

		        error.put("success", false);
		        error.put("message", e.getMessage());

		        return ResponseEntity
		                .status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body(error);
		    }
		}

}
