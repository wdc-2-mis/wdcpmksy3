package gov.dolr.wdcpmksy3.controller;

import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.DPR.service.ReportP1ExportService;
import gov.dolr.wdcpmksy3.DPR.service.ReportP1Service;
import gov.dolr.wdcpmksy3.dto.ReportP1Row;
import gov.dolr.wdcpmksy3.dto.ReportP1StateSummary;


@Controller
@RequestMapping("/reports/p1")
public class ReportP1Controller {

    private final ReportP1Service service;
    private final ReportP1ExportService export;

    public ReportP1Controller(ReportP1Service service, ReportP1ExportService export) {
        this.service = service;
        this.export = export;
    }

    @GetMapping
    public String page(@RequestParam(required = false) Integer stateCode,
                       @RequestParam(required = false) Integer districtCode,
                       @RequestParam(required = false) Integer finYrCd,
                       Model model) {

        // Drop a district that doesn't belong to the chosen state.
        final Integer selectedDistrict = districtCode;
        if (selectedDistrict != null && stateCode != null && stateCode > 0
                && service.districts(stateCode).stream()
                          .noneMatch(d -> d.getCode().equals(selectedDistrict))) {
            districtCode = null;
        }

        // All three filters = "All" -> state-wise summary
        boolean summaryMode = isAll(stateCode) && isAll(districtCode) && isAll(finYrCd);
        model.addAttribute("summaryMode", summaryMode);

        if (summaryMode) {
            List<ReportP1StateSummary> summary = service.getStateSummary();
            model.addAttribute("summaryRows", summary);
            model.addAttribute("rows", java.util.Collections.emptyList());
            model.addAttribute("totDistricts", summary.stream().mapToLong(ReportP1StateSummary::getDistrictCount).sum());
            model.addAttribute("totProjects",  summary.stream().mapToLong(ReportP1StateSummary::getProjectCount).sum());
            model.addAttribute("totArea",    service.sum(summary, ReportP1StateSummary::getAreaSanctioned));
            model.addAttribute("totCost",    service.sum(summary, ReportP1StateSummary::getTotCost));
            model.addAttribute("totCentral", service.sum(summary, ReportP1StateSummary::getCentralShare));
            model.addAttribute("totState",   service.sum(summary, ReportP1StateSummary::getStateShare));
        } else {
            List<ReportP1Row> rows = service.getReport(stateCode, districtCode, finYrCd);
            model.addAttribute("rows", rows);
            model.addAttribute("totArea",    service.sum(rows, ReportP1Row::getAreaSanctioned));
            model.addAttribute("totCost",    service.sum(rows, ReportP1Row::getTotCost));
            model.addAttribute("totCentral", service.sum(rows, ReportP1Row::getCentralShare));
            model.addAttribute("totState",   service.sum(rows, ReportP1Row::getStateShare));
        }

        model.addAttribute("states", service.states());
        model.addAttribute("districts", service.districts(stateCode));
        model.addAttribute("finYears", service.finYears());
        model.addAttribute("stateCode", stateCode);
        model.addAttribute("districtCode", districtCode);
        model.addAttribute("finYrCd", finYrCd);
        model.addAttribute("reportOn",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")));
        return "reports/report-p1";
    }

    private static boolean isAll(Integer v) { return v == null || v <= 0; }
    public record CodeNameView(Integer code, String name) {}

    @GetMapping("/districts")
    @ResponseBody
    public List<CodeNameView> districts(@RequestParam(required = false) Integer stateCode) {
        return service.districts(stateCode).stream()
                .map(d -> new CodeNameView(d.getCode(), d.getName())).toList();
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> excel(@RequestParam(required = false) Integer stateCode,
                                        @RequestParam(required = false) Integer districtCode,
                                        @RequestParam(required = false) Integer finYrCd) throws Exception {
        byte[] data = export.toExcel(service.getReport(stateCode, districtCode, finYrCd),
                export.filterText(stateCode, districtCode, finYrCd));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReportP1.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> pdf(@RequestParam(required = false) Integer stateCode,
                                      @RequestParam(required = false) Integer districtCode,
                                      @RequestParam(required = false) Integer finYrCd) throws Exception {
        byte[] data = export.toPdf(service.getReport(stateCode, districtCode, finYrCd),
                export.filterText(stateCode, districtCode, finYrCd));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReportP1.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
    }
}
