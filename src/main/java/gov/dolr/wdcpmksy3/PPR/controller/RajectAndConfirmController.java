package gov.dolr.wdcpmksy3.PPR.controller;

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

import gov.dolr.wdcpmksy3.PPR.dto.PprTransactionDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;
import gov.dolr.wdcpmksy3.repository.MenuRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class RajectAndConfirmController {
	
	@Autowired
    private DistrictService districtService;
	
	@Autowired
    private PprTransactionRepository trans;
	
	@Autowired
	private MenuRepository submenuRepository;
	
	@GetMapping("/rajectAndConfirm")
    public String rajectAndConfirm(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        List<PprTransaction> records = trans.findLatestTransactionsByState(stcode);
        model.addAttribute("records", records);
        
        return "ppr/rajectAndConfirm";
    }
	
	@GetMapping("/modifyPprTransaction")
    public String modifyPprTransaction(HttpSession session, Model model, @RequestParam("tranxId") Integer pprid) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        List<WdcpmksySubmenu> submenuList =submenuRepository.findByMenu_MenuNameAndIsActiveTrueOrderBySeqNoAsc("Preliminary Project Report Details");
        model.addAttribute("submenuList", submenuList);
        model.addAttribute("pprid", pprid);
        return "ppr/rajectAndConfirm1";
    }
	
	@GetMapping("/getPprTransaction")
	@ResponseBody
	public ResponseEntity<?> getPprTransaction(
	        @RequestParam("pprId") Integer pprId) {

	    try {

	        List<PprTransaction> list =
	                trans.findByPprPprIdOrderBySentonDesc(pprId);

	        List<PprTransactionDto> result = new ArrayList<>();

	        for (PprTransaction t : list) {

	            PprTransactionDto dto = new PprTransactionDto();

	            dto.setTranxId(t.getTranxId());

	            if (t.getPpr() != null) {
	                dto.setPprId(t.getPpr().getPprId());
	            }

	       /*     if (t.getSentFrom() != null) {
	                dto.setSentFrom(t.getSentFrom().getUserId());
	            }

	            if (t.getSentTo() != null) {
	                dto.setSentTo(t.getSentTo().getUserId());
	            }  */
	            
	            if (t.getSentFrom() != null) {
	                String userId = t.getSentFrom().getUserId();

	                if (userId != null && userId.startsWith("DL")) {
	                    dto.setSentFrom("DoLR");
	                } else {
	                    dto.setSentFrom(userId);
	                }
	            }

	            if (t.getSentTo() != null) {
	                String userId = t.getSentTo().getUserId();

	                if (userId != null && userId.startsWith("DL")) {
	                    dto.setSentTo("DoLR");
	                } else {
	                    dto.setSentTo(userId);
	                }
	            }

	            dto.setAction(t.getAction());
	            dto.setRemarks(t.getRemarks());
	            dto.setSenton(t.getSenton());

	            result.add(dto);
	        }

	        System.out.println("DTO records returned = " + result.size());

	        return ResponseEntity.ok(result);

	    } catch (Exception e) {

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
