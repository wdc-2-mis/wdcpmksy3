package gov.dolr.wdcpmksy3.PPR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.repository.ProfileProjection;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class StWisePprTransactionStatusController {
	
	@Autowired 
	private UserMapRepository userMapRepo; 
	
	@Autowired
	private PprTransactionRepository pprTransactionRepo;
	
	@GetMapping("/stWisePprTransactionStatus")
	public String stWisePprTransactionStatus(@RequestParam(required = false) Integer state,
			HttpSession session, Model model) {
		Integer regid = Integer.parseInt(session.getAttribute("regid").toString());
		List<ProfileProjection> stateList = userMapRepo.getMapState(regid);
		model.addAttribute("stateList",stateList);
		if(state!=null)
			model.addAttribute("selectedState", state);
		else
			state = 0;
		model.addAttribute("stWisePprTransactionList", pprTransactionRepo.findPprTransactionByStCode(state));
		
		return "ppr/stWisePprTransactionStatus";
	}

}
