package gov.dolr.wdcpmksy3.PPR.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.PPR.dto.CoveredAreaDTO;
import gov.dolr.wdcpmksy3.PPR.dto.WatershedAreaBean;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PPREmploymentGeneration;
import gov.dolr.wdcpmksy3.PPR.entity.PPRLandPatternArea;
import gov.dolr.wdcpmksy3.PPR.entity.PPRSoilErosion;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprCropOutcome;
import gov.dolr.wdcpmksy3.PPR.entity.PprDisasterDetails;
import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;
import gov.dolr.wdcpmksy3.PPR.entity.PprPendingUc;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.PPR.entity.PprWaterOutcome;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPREmploymentGenerationRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPRLandPatternAreaRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPRMigrationDetailsRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPRSoilErosionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAgroClimateRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAreaCoveredRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprDisasterDetailsRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprLivelihoodRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprPendingUcRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProjectGlanceRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProposedProjectRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWaterOutcomeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWatershedCoveredAreaRepo;
import gov.dolr.wdcpmksy3.PPR.repository.PprWcdcUnspentBalanceRepository;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.PPR.service.PPRDistrictService;
import gov.dolr.wdcpmksy3.PPR.service.PdfService;
import gov.dolr.wdcpmksy3.PPR.service.PprAreaCoverService;
import gov.dolr.wdcpmksy3.PPR.service.PprProposedProjectService;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.CropOutcomeRepository;
import gov.dolr.wdcpmksy3.repository.PprDrinkingWaterRepository;
import gov.dolr.wdcpmksy3.repository.PprProposedAreaRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import gov.dolr.wdcpmksy3.service.DistrictService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private PprProposedAreaRepository pprProposedAreaRepo;

    @Autowired
    private PPRLandPatternAreaRepository landPatternAreaRepo;

    @Autowired
    private PprAgroClimateRepository agroClimateRepo;

    @Autowired
    private PprDisasterDetailsRepository pprDisasterDetailsRepo;

    @Autowired
    private PPRSoilErosionRepository soilErosionRepo;

    @Autowired
    private PprLivelihoodRepository pprLivelihoodRepo;

    @Autowired
    private PPREmploymentGenerationRepository pprEmploymentRepo;

    @Autowired
    private PPRMigrationDetailsRepository pprMigrationDetailsRepo;

    @Autowired
    private PprWaterOutcomeRepository pprWaterOutcomeRepo;

    @Autowired
    private PprDrinkingWaterRepository pprDrinkingWaterRepo;

    @Autowired
    private CropOutcomeRepository cropOutcomeRepo;

    @Autowired
    private PprPendingUcRepository pprPendingUcRepo;

    @Autowired
    private PprWcdcUnspentBalanceRepository pprUnspentBalanceRepo;

    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private UserRepository ur;
    
    @Autowired
	private PPRDistrictService pprService;
    
    @Autowired
    private PprTransactionRepository trans;


    @GetMapping("/viewPPR")
    public String viewPPR(HttpSession session, Model model) {

        Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
        Object userid = session.getAttribute("userid");
        if (userid == null) {
            return "redirect:/login";
        }

        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
        model.addAttribute("searched", false);

        return "viewPPR";
    }

    @PostMapping("/viewPPR")
    public String viewPPR1(HttpSession session, Model model, @RequestParam("fyear") Integer finYrCd,
            @RequestParam("district") Integer dcode, @RequestParam("project") Integer project) {

        Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
        Object userid = session.getAttribute("userid");
        if (userid == null) {
            return "redirect:/login";
        }

        Map<String, Object> data = fetchReportData(dcode, project, finYrCd);
        model.addAllAttributes(data);

        model.addAttribute("distList", districtService.getPPRDistrictsByState(stcode));
        model.addAttribute("finYearList", finService.getFinYearCdAndDesc());
        model.addAttribute("searched", true);

        model.addAttribute("selectedDcode", dcode);
        model.addAttribute("selectedProject", project);
        model.addAttribute("selectedFinYrCd", finYrCd);
        model.addAttribute("selectedProjectName", resolveProjectName(data, project));

        boolean allSectionsFilled = isAllSectionsFilled(data);
     //   System.out.println("kdy1"+allSectionsFilled);
        model.addAttribute("allSectionsFilled", allSectionsFilled);

        List<WdcpmksyUserReg> users = ur.findDLUsersByStateAndRole(stcode);
        model.addAttribute("userList", users);
        model.addAttribute("transactionHistory", buildTransactionHistory(project));
        model.addAttribute("alreadyForwarded", isAlreadyForwarded(project));
        model.addAttribute("latestBackForwarded", isLatestBackForwarded(project));

        return "viewPPR";
    }

    private boolean isAllSectionsFilled1(Map<String, Object> data) {

        String[] keysToCheck = {
            "records",
            "watershedList",
            "detailsOfListOfProposedProject",
            "pprProjectAtGlanceList",
            "ppr8List",
            "landPatternAreaList",
            "ppr10List",
            "ppr11List",
            "soilErosionList",
            "pprLivelihoodSummaryList",
            "pprEmploymentList",
            "ppr15List",
            "pprWaterOutcomesList",
            "pprDrinkingWaterList",
            "cropOutcomes",
            "pendingUCList",
            "draftList"
        };

        for (String key : keysToCheck) {

            Object value = data.get(key);
          //  System.out.println("kdy1"+value);

            if (!(value instanceof List<?> list) || list.isEmpty()) {
                return false;
            }
        }

        return true;
    }
    
    private boolean isAllSectionsFilled(Map<String, Object> data) {

        for (Map.Entry<String, Object> entry : data.entrySet()) {

            if ("watershedList".equals(entry.getKey())) continue;

            if (entry.getValue() instanceof List<?> list) {
                if (list.isEmpty()) return false;
                for (Object o : list) {
                    if ("D".equals(statusOf(o))) return false;   // draft pending
                }
            }
        }
        return !Boolean.TRUE.equals(data.get("hasD"));           // PPR-2 draft
    }

    private String statusOf(Object o) {
        try {
            if (o instanceof Map<?, ?> m) {
                Object s = m.get("status");
                return s == null ? null : s.toString();
            }
            Object s = o.getClass().getMethod("getStatus").invoke(o);
            return s == null ? null : s.toString();
        } catch (Exception e) {
            return null;
        }
    }
    

    @PostMapping("/downloadPPRPdf")
    public ResponseEntity<byte[]> downloadPPRPdf(HttpSession session, Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "fyear", required = false) Integer finYrCd,
            @RequestParam(value = "district", required = false) Integer dcode,
            @RequestParam(value = "project", required = false) Integer project) throws Exception {

        Object userid = session.getAttribute("userid");
        if (userid == null) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        if (dcode == null || project == null || finYrCd == null) {
            redirectAttributes.addFlashAttribute("validationError",
                    "Please select District, Project and Financial Year, then click Get Data before downloading the PDF.");
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/viewPPR").build();
        }

        Map<String, Object> data = fetchReportData(dcode, project, finYrCd, false); 
        
        ByteArrayOutputStream baos = pdfService.generatePprReportPdf(data);
        byte[] pdfBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", buildDownloadFileName(data));
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, org.springframework.http.HttpStatus.OK);
    }

    private String buildDownloadFileName(Map<String, Object> data) {
        String projectName = firstRecordProjectName(data);
        if (projectName == null || projectName.isBlank()) {
            return "Preliminary_Project_Report.pdf";
        }
        String safeName = projectName.trim().replaceAll("[^a-zA-Z0-9._-]+", "_");
        return "Preliminary_Project_Report_" + safeName + ".pdf";
    }

    private String resolveProjectName(Map<String, Object> data, Integer project) {
        String projectName = firstRecordProjectName(data);
        return projectName != null ? projectName : ("Project #" + project);
    }

    @SuppressWarnings("unchecked")
    private String firstRecordProjectName(Map<String, Object> data) {
        Object recordsObj = data.get("records");
        if (recordsObj instanceof List<?> recordsList && !recordsList.isEmpty()) {
            Object first = recordsList.get(0);
            if (first instanceof MPpr ppr) {
                return ppr.getProjectName();
            }
        }
        return null;
    }

    private Map<String, Object> fetchReportData(Integer dcode, Integer project, Integer finYrCd) {
        return fetchReportData(dcode, project, finYrCd, true);
    }

    private Map<String, Object> fetchReportData(Integer dcode, Integer project, Integer finYrCd, boolean includeDraft) {

        Map<String, Object> data = new LinkedHashMap<>();

        List<String> statuses   = includeDraft ? List.of("C", "S", "D") : List.of("C", "S");
        List<Character> statuse = includeDraft ? List.of('C', 'S', 'D') : List.of('C', 'S');
        List<String> statusppr  = List.of("C", "A", "B");   // PPR-1 unchanged
        
        // ---- PPR-1 ----
    
        List<MPpr> records = pprRepo.findByDistrict_DcodeAndPprIdAndFinYear_FinYrCdAndStatusIn(dcode, project, finYrCd, statusppr);
        data.put("records", records);

        List<PprWatershedCoveredArea> areaRecords =
                area.findByPprPprIdAndStatusIn(project, statuses);


        // Check whether C and S records exist
        boolean hasC = areaRecords.stream()
                .anyMatch(row -> "C".equals(row.getStatus()));

        boolean hasS = areaRecords.stream()
                .anyMatch(row -> "S".equals(row.getStatus()));


        // Send these flags to Thymeleaf
        data.put("hasC", hasC);
        data.put("hasS", hasS);
        
        boolean hasD = areaRecords.stream().anyMatch(row -> "D".equals(row.getStatus()));
        data.put("hasD", hasD);


        Map<Integer, WatershedAreaBean> map = new LinkedHashMap<>();


        for (PprWatershedCoveredArea row : areaRecords) {

            // Only C records should be converted to watershedList
            if (!"C".equals(row.getStatus())) {
                continue;
            }

            // Check Micro Watershed
            if (row.getMicroWatershed() == null) {
                continue;
            }

            Integer mwId = row.getMicroWatershed().getMwId();

            if (mwId == null) {
                continue;
            }


            WatershedAreaBean bean = map.getOrDefault(
                    mwId,
                    new WatershedAreaBean()
            );


            bean.setMwId(mwId);

            bean.setMwName(
                    row.getMicroWatershed().getMwName()
            );


            // Check Scheme
            if (row.getScheme() == null) {
                map.put(mwId, bean);
                continue;
            }


            Integer scheme = row.getScheme().getSchemeId();

            if (scheme == null) {
                map.put(mwId, bean);
                continue;
            }


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

                default:
                    break;
            }


            map.put(mwId, bean);
        }


        data.put("watershedList", new ArrayList<>(map.values()) );

        // ---- PPR-3 ----
        List<Character> status = List.of('C', 'S');
        
        List<PprProposedProject> detailsOfListOfProposedProject = repository.findByPprPprIdAndStatusIn(project, statuse);
        data.put("detailsOfListOfProposedProject", detailsOfListOfProposedProject);

        // ---- PPR-4 ----
        List<PprProjectGlance> pprProjectAtGlanceList = pprProjectGlanceRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("pprProjectAtGlanceList", pprProjectAtGlanceList);
       
        // ---- PPR-8 ----
        List<PprProposedArea> ppr8List = pprProposedAreaRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("ppr8List", ppr8List);
       
        // ---- PPR-9 ----
        List<PPRLandPatternArea> landPatternAreaList = landPatternAreaRepo.findByPprIdPprIdAndStatusIn(project, statuse);
        data.put("landPatternAreaList", landPatternAreaList);

        // ---- PPR-10 ----
        List<PprAgroClimate> ppr10List = agroClimateRepo.findByPprPprIdAndStatusIn(project, statuse );
        data.put("ppr10List", ppr10List);

        // ---- PPR-11 ----
        List<PprDisasterDetails> ppr11List = pprDisasterDetailsRepo.findByPprPprIdAndStatusIn(project, statuses);
        data.put("ppr11List", ppr11List);

        // ---- PPR-12 ----
        List<PPRSoilErosion> soilErosionList = soilErosionRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("soilErosionList", soilErosionList);

        // ---- PPR-13 ----
        List<PprLivelihood> pprLivelihoodSummaryList = pprLivelihoodRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("pprLivelihoodSummaryList", pprLivelihoodSummaryList);

        // ---- PPR-14 ----
        List<PPREmploymentGeneration> pprEmploymentList = pprEmploymentRepo.findByPprIdPprIdAndStatusIn(project, statuse);
        data.put("pprEmploymentList", pprEmploymentList);

        // ---- PPR-15 ----
        List<Map<String, Object>> ppr15List = pprMigrationDetailsRepo.getMigrationDetailsByProjectAndStatus(project, statuse);
        data.put("ppr15List", ppr15List);

        // ---- PPR-16 ----
        List<PprWaterOutcome> pprWaterOutcomesList =pprWaterOutcomeRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("pprWaterOutcomesList", pprWaterOutcomesList);
     //   System.out.println("kdy"+pprWaterOutcomesList.size());
        // ---- PPR-17 ----
        List<PprDrinkingWater> pprDrinkingWaterList = pprDrinkingWaterRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("pprDrinkingWaterList", pprDrinkingWaterList);

        // ---- PPR-18 ----
        List<PprCropOutcome> cropOutcomes = cropOutcomeRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("cropOutcomes", cropOutcomes);

        // ---- PPR-19 ----
        List<PprPendingUc> pendingUCList = pprPendingUcRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("pendingUCList", pendingUCList);

        // ---- PPR-20 ----
        List<PprWcdcUnspentBalance> draftList = pprUnspentBalanceRepo.findByPprPprIdAndStatusIn(project, statuse);
        data.put("draftList", draftList);

        return data;
    }
    // ---------- Transaction history (read-only display) ----------

    private List<Map<String, String>> buildTransactionHistory(Integer pprId) {

        List<Map<String, String>> rows = new ArrayList<>();

        if (pprId == null) {
            return rows;
        }

        List<PprTransaction> list = trans.findByPprPprIdOrderBySentonDesc(pprId);

        if (list == null) {
            return rows;
        }

        for (PprTransaction t : list) {

            Map<String, String> row = new LinkedHashMap<>();

            row.put("sentFrom", displayUser(t.getSentFrom() != null ? t.getSentFrom().getUserId() : null));
            row.put("sentTo",   displayUser(t.getSentTo()   != null ? t.getSentTo().getUserId()   : null));
            row.put("action",   displayAction(t.getAction()));
            row.put("remarks",  (t.getRemarks() == null || t.getRemarks().toString().trim().isEmpty())
                                    ? "-" : t.getRemarks().toString());
            row.put("sentOn",   formatSentOn(t.getSenton()));

            rows.add(row);
        }

        return rows;
    }
    
    // true when the newest transaction is Back Forwarded (B)
    private boolean isLatestBackForwarded(Integer pprId) {
        if (pprId == null) {
            return false;
        }
        List<PprTransaction> list = trans.findByPprPprIdOrderBySentonDesc(pprId);
        if (list == null || list.isEmpty()) {
            return false;
        }
        Object action = list.get(0).getAction();
        return action != null && "B".equals(action.toString());
    }
    
    // true when the newest transaction is Forwarded (F) or Approved (A)
    private boolean isAlreadyForwarded(Integer pprId) {
        if (pprId == null) {
            return false;
        }
        List<PprTransaction> list = trans.findByPprPprIdOrderBySentonDesc(pprId);
        if (list == null || list.isEmpty()) {
            return false;
        }
        Object action = list.get(0).getAction();
        if (action == null) {
            return false;
        }
        String a = action.toString();
        return "F".equals(a) || "A".equals(a);
    }

    private String displayUser(String userId) {
        if (userId == null || userId.isEmpty()) {
            return "-";
        }
        return userId.startsWith("DL") ? "DoLR" : userId;
    }

    private String displayAction(Object action) {
        if (action == null || action.toString().isEmpty()) {
            return "-";
        }
        switch (action.toString()) {
            case "F": return "Forwarded";
            case "R": return "Reject";
            case "A": return "Approved";
            case "B": return "Back Forwarded";
            default:  return action.toString();
        }
    }

    private String formatSentOn(Object value) {
        if (value == null) {
            return "-";
        }
        String out;
        if (value instanceof java.time.LocalDateTime ldt) {
            out = ldt.format(java.time.format.DateTimeFormatter
                    .ofPattern("dd/MM/yyyy, hh:mm a", java.util.Locale.ENGLISH));
        } else if (value instanceof java.util.Date d) {   // also covers java.sql.Timestamp
            out = new java.text.SimpleDateFormat("dd/MM/yyyy, hh:mm a", java.util.Locale.ENGLISH).format(d);
        } else {
            return value.toString();
        }
        return out.toLowerCase();   // "26/10/2026, 10:41 am", same as the modal
    }
    
    
    
    @PostMapping("/forwardViewPPR")
    public String forwardViewPPR(HttpSession session, Model model, HttpServletRequest request,
            @RequestParam("pprdcode") Integer pprdcode,
            @RequestParam("pprid") Integer pprid,
            @RequestParam("userid") Integer regid,
            RedirectAttributes redirectAttributes) {

    	Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		Integer loginregid = Integer.parseInt(session.getAttribute("regid").toString());
		try {
			
			 if(userid==null){

		            return "redirect:/login";
		     }
			 

				if (isAlreadyForwarded(pprid)) {
					redirectAttributes.addFlashAttribute("error",
							"This PPR has already been forwarded/approved and cannot be forwarded again.");
					return "redirect:/viewPPR";
				}
			boolean save=false;
	    	
			save=pprService.forwardViewPPR(loginregid, pprdcode, pprid, regid, CommonFunctions.getClientIpAddr(request));
			
			if(save)
				redirectAttributes.addFlashAttribute( "success", "PPR Forward to DoLR successfully.");
			else
				redirectAttributes.addFlashAttribute("error", "Unable to Forward PPR");
			}
			catch (Exception e) {

				e.printStackTrace();
		        redirectAttributes.addFlashAttribute("error", "Unable to saved Details of Unspent balance as on date District-wise (PPR20)");
			}

        return "redirect:/viewPPR";
    }
    
    @GetMapping("/checkTransPPRExists")
	@ResponseBody
	public String checkTransPPRExists(@RequestParam Integer pprid) {

    	List<PprTransaction> records = trans.findLatestTransactionsByPprPprId(pprid);
	    
	    if (records != null && !records.isEmpty()) {
	        return "EXISTS";
	    }

	    return "NOT_EXISTS";
	}
    
    
    
}