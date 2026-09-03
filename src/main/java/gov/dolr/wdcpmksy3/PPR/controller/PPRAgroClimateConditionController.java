package gov.dolr.wdcpmksy3.PPR.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.PprAgroClimateDTO;
import gov.dolr.wdcpmksy3.PPR.dto.PprSlnaDetailsDto;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprSlnaDetails;
import gov.dolr.wdcpmksy3.PPR.repository.PprAgroClimateRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.PPR.service.CropTypeServices;
import gov.dolr.wdcpmksy3.PPR.service.MPprService;
import gov.dolr.wdcpmksy3.PPR.service.PPRAgroClimateConditionServices;
import gov.dolr.wdcpmksy3.PPR.service.SoilTypeServices;
import gov.dolr.wdcpmksy3.PPR.service.VillageService;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionary;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
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
    
    @Autowired
    private PPRAgroClimateConditionServices agroser;
    
    @Autowired
    private PprAgroClimateRepository agcrepo;
    
    @Autowired
    private VillageRepository villrepo;
    
	@GetMapping("/agroClimateConditionPPR10")
    public String agroClimateConditionPPR10(HttpSession session, Model model) 
	{
		
		//String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		
		List<Object[]> agroList = agcrepo.getPprAgroClimateList(stcode);
        if(userid==null){

            return "redirect:/login";
        }
        List<Object[]> finalList = new ArrayList<>();
        int srNo = 1;
        Integer previousId = null;
        for (Object[] row : agroList) {

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

        model.addAttribute("agroClimateList", finalList);
        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("villageList", villrepo.getVillagesByState(stcode));
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
	
	 @PostMapping("/saveAgroClimateConditionPPR10")
	 public String saveAgroClimateConditionPPR10(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer district,
	    		@RequestParam Integer project,
	    		@RequestParam Integer village,
	            @RequestParam String zone,
	            @RequestParam String graphy,
	            @RequestParam BigDecimal rainfall,
	            @RequestParam BigDecimal area,
	            @RequestParam BigDecimal farea,
	            @RequestParam Integer soilType,
	            @RequestParam BigDecimal soilarea,
	            @RequestParam Integer croptype,
	            @RequestParam BigDecimal croparea,
	            @RequestParam String action,
	            RedirectAttributes redirectAttributes) {
	   
		    
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				try {
					
					 if(userid==null){
	
				            return "redirect:/login";
				     }
					boolean save=false;
			    	
					save=agroser.saveAgroClimateCondition(project, village, zone, graphy, rainfall, area, farea,
							soilType, soilarea, croptype, croparea, action, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "Agro-Climatic Condition saved successfully.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to saved Agro-Climatic Condition.");
					}
					catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to saved Agro-Climatic Condition.");
					}
				return "redirect:/agroClimateConditionPPR10";	
	    }
	 
	 	@GetMapping("/deleteAgroClimateConditionPPR10")
	    public String deleteAgroClimateConditionPPR10(HttpSession session, Model model, @RequestParam("id") Integer id,  
	    		RedirectAttributes redirectAttributes) {

			
			String userid=(String)session.getAttribute("userid");
			try {
				
		        if(userid==null){
		
		            return "redirect:/login";
		        }
		        PprAgroClimate data = agcrepo.findById(id).orElse(null);
	            if (data == null) {
	                redirectAttributes.addFlashAttribute("error", "Record not found.");
	                return "redirect:/agroClimateConditionPPR10";
	            }
	            
	            if (agcrepo.existsById(id)) {
	            	agcrepo.deleteById(id);
	            	redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");
	            }
	            else {
	            	redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
	            }
	            

	            
	        } 
	        catch (Exception e) {

	            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
	            e.printStackTrace();
	        }
			return "redirect:/agroClimateConditionPPR10";
	    }
	 	
	 	 @GetMapping("/completeAgroClimateConditionPPR10")
	     public String completeWCDCFunctionariesPPR4B(HttpSession session, Model model, @RequestParam("id") Integer id,  
	     		RedirectAttributes redirectAttributes) {

	 		String statename=session.getAttribute("statename").toString();
	 		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	 		String userid=(String)session.getAttribute("userid");
	 		 	try {
	 		 		int updated =0;
	 		 		if(userid==null){

	 		            return "redirect:/login";
	 		        }
	 		 		agroser.completeRecord(id);
	 		        
	 		        redirectAttributes.addFlashAttribute("success", "Record completed successfully.");
	 		       
	 		    } 
	 		 	catch (Exception e) {
	 		 		e.printStackTrace();
	 		        redirectAttributes.addFlashAttribute("error", "Unable to complete record.");
	 		    }
	        
	         return "redirect:/agroClimateConditionPPR10";
	     }
	 	 
	 	 @GetMapping("/getAgroClimateConditionPPR10Id")
		 @ResponseBody
		 public PprAgroClimateDTO AgroClimateConditionPPR10(@RequestParam Integer id){

	 		List<Object[]> agroList = agcrepo.getPprAgroClimateListById(id);
	 		PprAgroClimateDTO dto = new PprAgroClimateDTO();
	 		for (Object[] row : agroList) 
	 		{
			    dto.setAgroid((Integer) row[0]);
			    dto.setArea(new BigDecimal(row[6].toString()));
			    dto.setDistname(row[1].toString());
			    dto.setProjname(row[2].toString());
			    dto.setVillage((Integer) row[3]);
			    dto.setVillname(row[4].toString());
			    dto.setZone(row[5].toString());
			    dto.setGraphy(row[8].toString());
			    dto.setRainfall(new BigDecimal(row[7].toString()));
			    dto.setFarea(new BigDecimal(row[9].toString()));
			    dto.setCroptype((Integer) row[11]);
			    dto.setCroparea(new BigDecimal(row[13].toString()));
			    dto.setSoilType((Integer) row[14]);
			    dto.setSoilarea(new BigDecimal(row[16].toString()));
	 		}
	 		return dto;
		 }
	 	 
	 	 @PostMapping("/editAgroClimateConditionPPR10")
		 public String editAgroClimateConditionPPR10(HttpSession session, Model model, HttpServletRequest request,
		    		
		    		@RequestParam Integer agroid,
		            @RequestParam String zone1,
		            @RequestParam String graphy1,
		            @RequestParam BigDecimal rainfall1,
		            @RequestParam BigDecimal area1,
		            @RequestParam BigDecimal farea1,
		            @RequestParam Integer soilType1,
		            @RequestParam BigDecimal soilarea1,
		            @RequestParam Integer croptype1,
		            @RequestParam BigDecimal croparea1,
		            @RequestParam String updateAction,
		            RedirectAttributes redirectAttributes) {
		   
			    
					Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
					String userid=(String)session.getAttribute("userid");
					try {
						
						 if(userid==null){
		
					            return "redirect:/login";
					     }
						boolean save=false;
				    	
						save=agroser.editAgroClimateConditionPPR10( agroid, zone1, graphy1, rainfall1, area1, farea1,
								soilType1, soilarea1, croptype1, croparea1, updateAction, userid, CommonFunctions.getClientIpAddr(request));
						
						if(save)
							redirectAttributes.addFlashAttribute( "success", "Agro-Climatic Condition Update successfully.");
						else
							redirectAttributes.addFlashAttribute("error", "Unable to Update Agro-Climatic Condition.");
						}
						catch (Exception e) {
		
							e.printStackTrace();
					        redirectAttributes.addFlashAttribute("error", "Unable to Update Agro-Climatic Condition.");
						}
					return "redirect:/agroClimateConditionPPR10";	
		    }

}
