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
import gov.dolr.wdcpmksy3.PPR.repository.PprWaterOutcomeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWatershedCoveredAreaRepo;
import gov.dolr.wdcpmksy3.PPR.repository.PprWcdcUnspentBalanceRepository;
import gov.dolr.wdcpmksy3.PPR.service.FinYearService;
import gov.dolr.wdcpmksy3.PPR.service.PdfService;
import gov.dolr.wdcpmksy3.PPR.service.PprAreaCoverService;
import gov.dolr.wdcpmksy3.PPR.service.PprProposedProjectService;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;
import gov.dolr.wdcpmksy3.repository.CropOutcomeRepository;
import gov.dolr.wdcpmksy3.repository.PprDrinkingWaterRepository;
import gov.dolr.wdcpmksy3.repository.PprProposedAreaRepository;
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

        return "viewPPR";
    }

    /**
     * Generates the same PPR-1..PPR-20 dataset as /viewPPR and streams it back as a
     * downloadable PDF. Reuses fetchReportData so there is zero duplicated fetch logic.
     */
    @PostMapping("/downloadPPRPdf")
    public ResponseEntity<byte[]> downloadPPRPdf(HttpSession session, @RequestParam("fyear") Integer finYrCd,
            @RequestParam("district") Integer dcode, @RequestParam("project") Integer project) throws Exception {

        Object userid = session.getAttribute("userid");
        if (userid == null) {
            return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();
        }

        Map<String, Object> data = fetchReportData(dcode, project, finYrCd);

        ByteArrayOutputStream baos = pdfService.generatePprReportPdf(data);
        byte[] pdfBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Preliminary_Project_Report.pdf");
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, org.springframework.http.HttpStatus.OK);
    }

    /**
     * Central place that fetches PPR-1 through PPR-20 data for a given
     * district/project/financial year. Used by BOTH the on-screen view and the PDF export,
     * so the two are always guaranteed to match.
     */
    private Map<String, Object> fetchReportData(Integer dcode, Integer project, Integer finYrCd) {

        Map<String, Object> data = new LinkedHashMap<>();

        // ---- PPR-1 ----
        List<MPpr> records = pprRepo.findByDistrict_DcodeAndPprIdAndFinYear_FinYrCdAndStatus(dcode, project, finYrCd, "C");
        data.put("records", records);

        // ---- PPR-2 ----
        List<PprWatershedCoveredArea> areaRecords = area.findByPprPprIdAndStatus(project, "C");

        Map<Integer, WatershedAreaBean> map = new LinkedHashMap<>();
        for (PprWatershedCoveredArea row : areaRecords) {

            Integer mwId = row.getMicroWatershed().getMwId();
            WatershedAreaBean bean = map.getOrDefault(mwId, new WatershedAreaBean());
            bean.setMwId(mwId);
            bean.setMwName(row.getMicroWatershed().getMwName());

            Integer scheme = row.getScheme().getSchemeId();
            switch (scheme) {
                case 1: bean.setPreNo(row.getNoMw()); bean.setPreArea(row.getAreaMw()); break;
                case 2: bean.setDpapNo(row.getNoMw()); bean.setDpapArea(row.getAreaMw()); break;
                case 3: bean.setDdpNo(row.getNoMw()); bean.setDdpArea(row.getAreaMw()); break;
                case 4: bean.setIwdpNo(row.getNoMw()); bean.setIwdpArea(row.getAreaMw()); break;
                case 5: bean.setIwmpNo(row.getNoMw()); bean.setIwmpArea(row.getAreaMw()); break;
                case 6: bean.setPmksyNo(row.getNoMw()); bean.setPmksyArea(row.getAreaMw()); break;
                case 7: bean.setOtherNo(row.getNoMw()); bean.setOtherArea(row.getAreaMw()); break;
            }
            map.put(mwId, bean);
        }
        data.put("watershedList", new ArrayList<>(map.values()));

        // ---- PPR-3 ----
        List<PprProposedProject> detailsOfListOfProposedProject = repository.findByPprPprIdAndStatus(project, 'C');
        data.put("detailsOfListOfProposedProject", detailsOfListOfProposedProject);

        // ---- PPR-4 ----
        List<PprProjectGlance> pprProjectAtGlanceList = pprProjectGlanceRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("pprProjectAtGlanceList", pprProjectAtGlanceList);

        // ---- PPR-8 ----
        List<PprProposedArea> ppr8List = pprProposedAreaRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("ppr8List", ppr8List);

        // ---- PPR-9 ----
        List<PPRLandPatternArea> landPatternAreaList = landPatternAreaRepo.findByPprIdPprIdAndStatus(project, 'C');
        data.put("landPatternAreaList", landPatternAreaList);

        // ---- PPR-10 ----
        List<PprAgroClimate> ppr10List = agroClimateRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("ppr10List", ppr10List);

        // ---- PPR-11 ----
        List<PprDisasterDetails> ppr11List = pprDisasterDetailsRepo.findByPprPprIdAndStatus(project, "C");
        data.put("ppr11List", ppr11List);

        // ---- PPR-12 ----
        List<PPRSoilErosion> soilErosionList = soilErosionRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("soilErosionList", soilErosionList);

        // ---- PPR-13 ----
        List<PprLivelihood> pprLivelihoodSummaryList = pprLivelihoodRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("pprLivelihoodSummaryList", pprLivelihoodSummaryList);

        // ---- PPR-14 ----
        List<PPREmploymentGeneration> pprEmploymentList = pprEmploymentRepo.findByPprIdPprIdAndStatus(project, 'C');
        data.put("pprEmploymentList", pprEmploymentList);

        // ---- PPR-15 ----
        List<Map<String, Object>> ppr15List = pprMigrationDetailsRepo.getMigrationDetailsByProjectAndStatus(project, "C");
        data.put("ppr15List", ppr15List);

        // ---- PPR-16 ----
        List<PprWaterOutcome> pprWaterOutcomesList = pprWaterOutcomeRepo.findByPprIdAndStatus(project, "C");
        data.put("pprWaterOutcomesList", pprWaterOutcomesList);

        // ---- PPR-17 ----
        List<PprDrinkingWater> pprDrinkingWaterList = pprDrinkingWaterRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("pprDrinkingWaterList", pprDrinkingWaterList);

        // ---- PPR-18 ----
        List<PprCropOutcome> cropOutcomes = cropOutcomeRepo.findByPprPprIdAndStatus(project, "C");
        data.put("cropOutcomes", cropOutcomes);

        // ---- PPR-19 ----
        List<PprPendingUc> pendingUCList = pprPendingUcRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("pendingUCList", pendingUCList);

        // ---- PPR-20 ----
        List<PprWcdcUnspentBalance> draftList = pprUnspentBalanceRepo.findByPprPprIdAndStatus(project, 'C');
        data.put("draftList", draftList);

        return data;
    }
}