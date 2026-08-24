package gov.dolr.wdcpmksy3.PPR.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.PprSlnaDetailsDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprSlnaDetails;
import gov.dolr.wdcpmksy3.PPR.service.PprSlnaDetailsService;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.Designation;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.MemberDetails;
import gov.dolr.wdcpmksy3.entity.Qualification;
import gov.dolr.wdcpmksy3.service.DesignationService;
import gov.dolr.wdcpmksy3.service.InstitutionalStructureServiceImpl;
import gov.dolr.wdcpmksy3.service.MemberDetailsService;
import gov.dolr.wdcpmksy3.service.QualificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PprDetailsOfSlnaController {
	
	@Autowired
    private InstitutionalStructureServiceImpl isserv;
	
	@Autowired
	PprSlnaDetailsService pprSlnaDetailsService;
	
	@Autowired
	DesignationService designationService;
	
	@Autowired
	MemberDetailsService memberDetailsService;
	
	@Autowired
	QualificationService qualificationService;

	@GetMapping("/detailsOfSLNA")
    public String detailsOfSLNA(HttpSession session, Model model) {
		
		String statename=session.getAttribute("statename").toString();
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
	    
		String userid=(String)session.getAttribute("userid");
		List<MemberDetails> memberDetailsList = new ArrayList<>();
		memberDetailsList = memberDetailsService.getAllMemberDetails();
		List<Designation> designationList = new ArrayList<>();
		designationList = designationService.getAllDesignationDetails();
		List<Qualification> qualificationList = new ArrayList<>();
		qualificationList = qualificationService.getAllQualification();
		List<PprSlnaDetails> detailsOfSLNAList = new ArrayList<>();
		detailsOfSLNAList = pprSlnaDetailsService.getDraftdataOfSlnaDetails();
//		List<PprSlnaDetails> detailsOfSLNAComList = new ArrayList<>();
//		detailsOfSLNAComList = pprSlnaDetailsService.getComdataOfSlnaDetails();
		
		List<Object[]> object = isserv.getPPR1List(stcode);
		Integer instId = object.stream().mapToInt(arr -> (Integer) arr[0]).findFirst().orElse(0);
		InstitutionalStructure instStructobj = isserv.getById((long)instId);
		List<PprSlnaDetails> allList = pprSlnaDetailsService.getSlnaDetailsByInstStruc(instStructobj);

		boolean chairpersonExists = allList.stream().anyMatch(s -> s.getMember() != null && s.getMember().getMemberId() != null
				&& s.getMember().getMemberId().equals(1));
		boolean ceoExists = allList.stream().anyMatch(s -> s.getMember() != null && s.getMember().getMemberId() != null
				&& s.getMember().getMemberId().equals(2));
		
		Map<Integer, String> phoneMap = allList.stream().filter(p -> p.getPhoneNo() != null).collect(Collectors.toMap(PprSlnaDetails::getPprSlnaId,PprSlnaDetails::getPhoneNo));
		Map<Integer, String> emailMap = allList.stream().filter(p -> p.getEmailId() != null).collect(Collectors.toMap(PprSlnaDetails::getPprSlnaId,PprSlnaDetails::getEmailId));

        if(userid==null){

            return "redirect:/login";
        }
		model.addAttribute("statename", statename);
		model.addAttribute("stcode", stcode);
		model.addAttribute("memberDetailsList", memberDetailsList);
		model.addAttribute("designationList",designationList);
//		model.addAttribute("detailsOfSLNAComList",detailsOfSLNAComList);
		model.addAttribute("qualificationList",qualificationList);
		model.addAttribute("detailsOfSLNAList",detailsOfSLNAList);
		model.addAttribute("chairpersonExists", chairpersonExists);
		model.addAttribute("ceoExists", ceoExists);
		model.addAttribute("phoneMap", phoneMap);
		model.addAttribute("emailMap", emailMap);
        return "detailsOfSLNA";
    }
	
	@PostMapping("/saveDetailsOfSLNA")
	public String saveDetailsOfSLNA(HttpServletRequest request,
	        @RequestParam Integer memberType,
	        @RequestParam String firstName,
	        @RequestParam String lastName,
	        @RequestParam Integer designation,
	        @RequestParam Integer qualification,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
	        @RequestParam String joiningType,
	        @RequestParam Integer tenure,
	        @RequestParam String phone,
	        @RequestParam String email,
	        @RequestParam(required = false) String fax,
	        @RequestParam Character action,
	        RedirectAttributes redirectAttributes,
	        HttpSession session) throws IOException {
		String userid=(String)session.getAttribute("userid");
		if (userid != null) {

			Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());

			List<Object[]> object = isserv.getPPR1List(stcode);
			Integer instId = object.stream().mapToInt(arr -> (Integer) arr[0]).findFirst().orElse(0);
			
			InstitutionalStructure instStructobj = isserv.getById((long)instId);

			MemberDetails member = memberDetailsService.getMemberDetailsById(memberType);

			Designation des = designationService.getDesignationById(designation);

			Qualification qual = qualificationService.getQualificationById(qualification);

			PprSlnaDetails details = new PprSlnaDetails();
			details.setInstitutionalStructure(instStructobj);
			details.setMember(member);
			details.setFirstName(firstName);
			details.setLastName(lastName);
			details.setDesignation(des);
			details.setQualification(qual);
			details.setAppointmentDate(appointmentDate);
			details.setJoiningType(joiningType);
			details.setTenurePeriodYr(tenure);
			details.setPhoneNo(phone);
			details.setEmailId(email);
			details.setFax(fax);
			details.setCreatedBy(userid);
			details.setCreatedDate(LocalDateTime.now());
			details.setRequestIp(CommonFunctions.getClientIpAddr(request));

			// Example if your table has status
			details.setStatus(action);

			pprSlnaDetailsService.save(details);

			redirectAttributes.addFlashAttribute("success", "Details saved successfully.");

			return "redirect:/detailsOfSLNA";
		}
		else {
			return "redirect:/login";
		}
	}
	
	 @GetMapping("/deleteDetailsOfSLNA")
	    public String DetailsOfSLNA(HttpSession session, @RequestParam Integer id,  
	    		RedirectAttributes redirectAttributes) {

			
			String statename=session.getAttribute("statename").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
			String userid=(String)session.getAttribute("userid");
			try {
				
		        if(userid==null){
		
		            return "redirect:/login";
		        }
		        PprSlnaDetails data = pprSlnaDetailsService.getSlnaDetailsById(id);
	            if (data == null) {
	                redirectAttributes.addFlashAttribute("error", "Record not found.");
	                return "redirect:/detailsOfSLNA";
	            }
	            
	            pprSlnaDetailsService.delete(id);

	            redirectAttributes.addFlashAttribute("success", "Record deleted successfully.");

	        } 
	        catch (Exception e) {

	            redirectAttributes.addFlashAttribute("error", "Unable to delete record.");
	            e.printStackTrace();
	        }
	        return "redirect:/detailsOfSLNA";
	    }
	 
	 @GetMapping("/completeDetailsOfSLNA")
	 public String completeDetailsOfSLNA(@RequestParam Integer id, RedirectAttributes redirectAttributes, HttpSession session) {
		 String userid=(String)session.getAttribute("userid");
			try {
				if(userid==null){
		            return "redirect:/login";
		        }
				pprSlnaDetailsService.completeRecord(id, userid);
				redirectAttributes.addFlashAttribute("success", "Selected Records Completed successfully.");
			}catch (Exception e) {

	            redirectAttributes.addFlashAttribute("error", "Unable to Complete records.");
	            e.printStackTrace();
	       }
	     return "redirect:/detailsOfSLNA";
	 }
	 
	 @GetMapping("/getDetailsOfSLNAById")
	 @ResponseBody
	 public PprSlnaDetailsDto getDetailsOfSLNAById(@RequestParam Integer id){

		 PprSlnaDetails data = pprSlnaDetailsService.getSlnaDetailsById(id);

		 PprSlnaDetailsDto dto = new PprSlnaDetailsDto();

		    dto.setPprSlnaId(data.getPprSlnaId());
		    dto.setMemberId(data.getMember().getMemberId());
		    dto.setDesignationId(data.getDesignation().getDesignationId());
		    dto.setQualificationId(data.getQualification().getQualificationId());
		    dto.setFirstName(data.getFirstName());
		    dto.setLastName(data.getLastName());
		    dto.setAppointmentDate(data.getAppointmentDate());
		    dto.setJoiningType(data.getJoiningType());
		    dto.setTenurePeriodYr(data.getTenurePeriodYr());
		    dto.setPhoneNo(data.getPhoneNo());
		    dto.setEmailId(data.getEmailId());
		    dto.setFax(data.getFax());

		    return dto;
	 }
	 
	 @PostMapping("/updateDetailsOfSLNA")
		public String updateDetailsOfSLNA(HttpServletRequest request,
				@RequestParam Integer pprSlna,
		        @RequestParam Integer memberType,
		        @RequestParam String firstName,
		        @RequestParam String lastName,
		        @RequestParam Integer designation,
		        @RequestParam Integer qualification,
		        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
		        @RequestParam String joiningType,
		        @RequestParam Integer tenure,
		        @RequestParam String phone,
		        @RequestParam String email,
		        @RequestParam(required = false) String fax,
		        RedirectAttributes redirectAttributes,
		        HttpSession session) throws IOException {
			String userid=(String)session.getAttribute("userid");
			if (userid != null) {

				MemberDetails member = memberDetailsService.getMemberDetailsById(memberType);

				Designation des = designationService.getDesignationById(designation);

				Qualification qual = qualificationService.getQualificationById(qualification);
				
				PprSlnaDetails details = pprSlnaDetailsService.getSlnaDetailsById(pprSlna);
				details.setMember(member);
				details.setFirstName(firstName);
				details.setLastName(lastName);
				details.setDesignation(des);
				details.setQualification(qual);
				details.setAppointmentDate(appointmentDate);
				details.setJoiningType(joiningType);
				details.setTenurePeriodYr(tenure);
				details.setPhoneNo(phone);
				details.setEmailId(email);
				details.setFax(fax);
				details.setCreatedBy(userid);
				details.setCreatedDate(LocalDateTime.now());
				details.setRequestIp(CommonFunctions.getClientIpAddr(request));

				pprSlnaDetailsService.save(details);

				redirectAttributes.addFlashAttribute("success", "Details Updated successfully.");

				return "redirect:/detailsOfSLNA";
			}
			else {
				return "redirect:/login";
			}
		}


}
