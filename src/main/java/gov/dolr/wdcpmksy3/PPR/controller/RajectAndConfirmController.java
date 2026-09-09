package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;
import gov.dolr.wdcpmksy3.repository.MenuRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpSession;

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

}
