package gov.dolr.wdcpmksy3.PPR.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;

@Service
public class PdfService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.WHITE);
    private final Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
    private final Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.WHITE);
    private final Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.BLACK);
    private final Color headerBg = new Color(13, 110, 253);   // matches table-primary blue used in the UI
    private final Color sectionBg = new Color(13, 110, 253);

    public ByteArrayOutputStream generatePprReportPdf(Map<String, Object> data) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate(), 20, 20, 30, 30);
        PdfWriter.getInstance(document, baos);
        document.open();

        addTitle(document);

        // ---- PPR-1 ----
        addSectionHeader(document, "PPR-1 : Preliminary Project Report");
        List<MPpr> records = castList(data.get("records"));
        String[] h1 = { "Financial Year", "District", "Project Name" };
        PdfPTable t1 = newTable(h1.length);
        addHeaderRow(t1, h1);
        if (isEmpty(records)) {
            addEmptyRow(t1, h1.length);
        } else {
            for (MPpr r : records) {
                addRow(t1,
                        safe(r.getFinYear() != null ? r.getFinYear().getFinYrDesc() : null),
                        safe(r.getDistrict() != null ? r.getDistrict().getDistName() : null),
                        safe(r.getProjectName()));
            }
        }
        document.add(t1);

        // ---- PPR-3 ----
        addSectionHeader(document, "PPR-3 : Prioritized List of Proposed Projects");
        List<PprProposedProject> ppr3 = castList(data.get("detailsOfListOfProposedProject"));
        String[] h3 = { "District", "Project Name", "Micro-Watershed", "MW Code", "Proposed Area (Ha.)", "Type", "Proposed Cost" };
        PdfPTable t3 = newTable(h3.length);
        addHeaderRow(t3, h3);
        if (isEmpty(ppr3)) {
            addEmptyRow(t3, h3.length);
        } else {
            for (PprProposedProject d : ppr3) {
                addRow(t3,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getMicroWatershed().getMwCode()),
                        safe(d.getTreatedArea()),
                        safe(d.getProjectType().getProjectType()),
                        safe(d.getProposedCost()));
            }
        }
        document.add(t3);

        // ---- PPR-4 ----
        addSectionHeader(document, "PPR-4 : Project at a Glance");
        List<PprProjectGlance> ppr4 = castList(data.get("pprProjectAtGlanceList"));
        String[] h4 = { "District", "Project", "Type", "MW Code", "Reason", "Proj Area", "Proposed Area", "Cost", "PIA" };
        PdfPTable t4 = newTable(h4.length);
        addHeaderRow(t4, h4);
        if (isEmpty(ppr4)) {
            addEmptyRow(t4, h4.length);
        } else {
            for (PprProjectGlance d : ppr4) {
                addRow(t4,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getProjectType().getProjectType()),
                        safe(d.getMicroWatershed().getMwCode()),
                        safe(d.getSelectionReason()),
                        safe(d.getProjectArea()),
                        safe(d.getProposedArea()),
                        safe(d.getProjectCost()),
                        safe(d.getPia() != null ? d.getPia().getPiaName() : null));
            }
        }
        document.add(t4);

        // ---- PPR-8 ----
        addSectionHeader(document, "PPR-8 : Area Proposed");
        List<PprProposedArea> ppr8 = castList(data.get("ppr8List"));
        String[] h8 = { "District", "Project", "Block", "Scheme", "Sanctioned No.", "Sanctioned Area", "Net Area", "Proposed Area", "Other DoLR", "Balance" };
        PdfPTable t8 = newTable(h8.length);
        addHeaderRow(t8, h8);
        if (isEmpty(ppr8)) {
            addEmptyRow(t8, h8.length);
        } else {
            for (PprProposedArea d : ppr8) {
                addRow(t8,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getBlock().getBlockName()),
                        safe(d.getScheme().getSchemeName()),
                        safe(d.getProjSanctionedNo()),
                        safe(d.getProjSanctionedArea()),
                        safe(d.getNetArea()),
                        safe(d.getProposedArea()),
                        safe(d.getProposedAreaOthers()),
                        safe(d.getNetBalArea()));
            }
        }
        document.add(t8);

        // ---- PPR-9 ----
        addSectionHeader(document, "PPR-9 : Land Pattern Area");
        List<PPRLandPatternArea> ppr9 = castList(data.get("landPatternAreaList"));
        String[] h9 = { "District", "Project", "Micro-Watershed", "Village", "Geo Area", "Forest", "Agriculture", "Rainfed", "Pastures", "Cultivable Waste", "Non-Cultivable Waste" };
        PdfPTable t9 = newTable(h9.length);
        addHeaderRow(t9, h9);
        if (isEmpty(ppr9)) {
            addEmptyRow(t9, h9.length);
        } else {
            for (PPRLandPatternArea d : ppr9) {
                addRow(t9,
                        safe(d.getPprId().getDistrict().getDistName()),
                        safe(d.getPprId().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getVillageArea()),
                        safe(d.getForestArea()),
                        safe(d.getAgricultureLand()),
                        safe(d.getRainfedArea()),
                        safe(d.getPastures()),
                        safe(d.getCultivableWastelandArea()),
                        safe(d.getNonCultivableWastelandArea()));
            }
        }
        document.add(t9);

        // ---- PPR-10 ----
        addSectionHeader(document, "PPR-10 : Details of Agro-Climatic Condition");
        List<PprAgroClimate> ppr10 = castList(data.get("ppr10List"));
        String[] h10 = { "District", "Project", "Village", "Zone", "Topography", "Rainfall", "Area", "Forest Area" };
        PdfPTable t10 = newTable(h10.length);
        addHeaderRow(t10, h10);
        if (isEmpty(ppr10)) {
            addEmptyRow(t10, h10.length);
        } else {
            for (PprAgroClimate d : ppr10) {
                addRow(t10,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getZoneName()),
                        safe(d.getTopography()),
                        safe(d.getAvgRainfall()),
                        safe(d.getArea()),
                        safe(d.getForestArea()));
            }
        }
        document.add(t10);

        // ---- PPR-11 ----
        addSectionHeader(document, "PPR-11 : Details of Flood and Drought in Project Area");
        List<PprDisasterDetails> ppr11 = castList(data.get("ppr11List"));
        String[] h11 = { "Village", "Year", "Month", "Particular", "Periodicity", "Affected" };
        PdfPTable t11 = newTable(h11.length);
        addHeaderRow(t11, h11);
        if (isEmpty(ppr11)) {
            addEmptyRow(t11, h11.length);
        } else {
            for (PprDisasterDetails d : ppr11) {
                addRow(t11,
                        safe(d.getVcode().getVillageName()),
                        safe(d.getYear().getYear()),
                        safe(d.getMonth().getMonthName()),
                        safe(d.getDtype().getDisasterName()),
                        "Q".equals(d.getPeriodicity()) ? "Quarterly" : "Annual",
                        Boolean.TRUE.equals(d.getAffected()) ? "Yes" : "No");
            }
        }
        document.add(t11);

        // ---- PPR-12 ----
        addSectionHeader(document, "PPR-12 : Soil Erosion Details");
        List<PPRSoilErosion> ppr12 = castList(data.get("soilErosionList"));
        String[] h12 = { "District", "Month", "Year", "Erosion Type", "Sub-Type", "Affected Area", "Runoff", "Avg Soil Loss" };
        PdfPTable t12 = newTable(h12.length);
        addHeaderRow(t12, h12);
        if (isEmpty(ppr12)) {
            addEmptyRow(t12, h12.length);
        } else {
            for (PPRSoilErosion d : ppr12) {
                addRow(t12,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getMonth() != null ? d.getMonth().getMonthName() : null),
                        safe(d.getYear() != null ? d.getYear().getYear() : null),
                        safe(d.getErosionType().getErosion().getCategoryType()),
                        safe(d.getErosionType().getErosionType()),
                        safe(d.getAffectedArea()),
                        safe(d.getRunoff()),
                        safe(d.getAvgSoilLoss()));
            }
        }
        document.add(t12);

        // ---- PPR-13 ----
        addSectionHeader(document, "PPR-13 : Livelihood Summary");
        List<PprLivelihood> ppr13 = castList(data.get("pprLivelihoodSummaryList"));
        String[] h13 = { "District", "Block", "Project", "Micro-Watershed", "Village", "Migrated People", "Migration Reason" };
        PdfPTable t13 = newTable(h13.length);
        addHeaderRow(t13, h13);
        if (isEmpty(ppr13)) {
            addEmptyRow(t13, h13.length);
        } else {
            for (PprLivelihood d : ppr13) {
                addRow(t13,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getVillage().getGramPanchayat().getBlock().getBlockName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getMigratedPeople()),
                        safe(d.getMigrationReason()));
            }
        }
        document.add(t13);

        // ---- PPR-14 ----
        addSectionHeader(document, "PPR-14 : Employment Generation Details");
        List<PPREmploymentGeneration> ppr14 = castList(data.get("pprEmploymentList"));
        String[] h14 = { "District", "Project", "Micro-Watershed", "Village", "Type", "SC", "ST", "Others", "Women", "Total" };
        PdfPTable t14 = newTable(h14.length);
        addHeaderRow(t14, h14);
        if (isEmpty(ppr14)) {
            addEmptyRow(t14, h14.length);
        } else {
            for (PPREmploymentGeneration d : ppr14) {
                int total = nz(d.getSc()) + nz(d.getSt()) + nz(d.getOthers()) + nz(d.getWomen());
                addRow(t14,
                        safe(d.getPprId().getDistrict().getDistName()),
                        safe(d.getPprId().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getEmploymentType().getEmploymentTypeName()),
                        safe(d.getSc()), safe(d.getSt()), safe(d.getOthers()), safe(d.getWomen()),
                        String.valueOf(total));
            }
        }
        document.add(t14);

        // ---- PPR-15 (List<Map<String,Object>>) ----
        addSectionHeader(document, "PPR-15 : Migration Details");
        List<Map<String, Object>> ppr15 = castList(data.get("ppr15List"));
        String[] h15 = { "District", "Project", "Watershed", "Village", "People Migrating", "Days/Year", "Reason", "Expected Reduction" };
        PdfPTable t15 = newTable(h15.length);
        addHeaderRow(t15, h15);
        if (isEmpty(ppr15)) {
            addEmptyRow(t15, h15.length);
        } else {
            for (Map<String, Object> m : ppr15) {
                addRow(t15,
                        safe(m.get("dist_name")), safe(m.get("project_name")), safe(m.get("mw_name")),
                        safe(m.get("village_name")), safe(m.get("migrating_people_count")),
                        safe(m.get("migration_days_per_year")), safe(m.get("migration_reason")),
                        safe(m.get("expected_reduction_migrating_people")));
            }
        }
        document.add(t15);

        // ---- PPR-16 ----
        addSectionHeader(document, "PPR-16 : Average Ground Water Table Depth in Project Area");
        List<PprWaterOutcome> ppr16 = castList(data.get("pprWaterOutcomesList"));
        String[] h16 = { "District", "Project", "Micro-Watershed", "Village", "Source", "Pre-Project", "Post-Project", "Remarks" };
        PdfPTable t16 = newTable(h16.length);
        addHeaderRow(t16, h16);
        if (isEmpty(ppr16)) {
            addEmptyRow(t16, h16.length);
        } else {
            for (PprWaterOutcome d : ppr16) {
                addRow(t16,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getWaterSource().getSourceName()),
                        safe(d.getPreProjectLevel()),
                        safe(d.getPostProjectLevel()),
                        safe(d.getRemarks()));
            }
        }
        document.add(t16);

        // ---- PPR-17 ----
        addSectionHeader(document, "PPR-17 : Drinking Water Status");
        List<PprDrinkingWater> ppr17 = castList(data.get("pprDrinkingWaterList"));
        String[] h17 = { "District", "Project", "Watershed", "Village", "Pre-Avail(Mo)", "Pre-Quality", "Post-Avail(Mo)", "Post-Quality" };
        PdfPTable t17 = newTable(h17.length);
        addHeaderRow(t17, h17);
        if (isEmpty(ppr17)) {
            addEmptyRow(t17, h17.length);
        } else {
            for (PprDrinkingWater d : ppr17) {
                addRow(t17,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getPreWaterAvailabilityMonths()),
                        safe(d.getPreWaterQuality() != null ? d.getPreWaterQuality().getWaterQualityType() : null),
                        safe(d.getPostWaterAvailabilityMonths()),
                        safe(d.getPostWaterQuality() != null ? d.getPostWaterQuality().getWaterQualityType() : null));
            }
        }
        document.add(t17);

        // ---- PPR-18 ----
        addSectionHeader(document, "PPR-18 : Major Crops Grown and Their Productivity");
        List<PprCropOutcome> ppr18 = castList(data.get("cropOutcomes"));
        String[] h18 = { "Season", "Crop", "Current Area", "Current Prod", "Expected Area", "Expected Prod" };
        PdfPTable t18 = newTable(h18.length);
        addHeaderRow(t18, h18);
        if (isEmpty(ppr18)) {
            addEmptyRow(t18, h18.length);
        } else {
            for (PprCropOutcome d : ppr18) {
                addRow(t18,
                        safe(d.getSeason().getSeasonName()),
                        safe(d.getCropType().getCropName()),
                        safe(d.getCurrentArea()), safe(d.getCurrentProd()),
                        safe(d.getExpectedArea()), safe(d.getExpectedProd()));
            }
        }
        document.add(t18);

        // ---- PPR-19 ----
        addSectionHeader(document, "PPR-19 : List of Details of Pending UC's");
        List<PprPendingUc> ppr19 = castList(data.get("pendingUCList"));
        String[] h19 = { "Project", "Fin. Year", "Installment", "Released", "Utilized", "Due Date", "UC Amt", "Submission Date", "Submitted Amt", "Reason", "Period", "Pending Amt" };
        PdfPTable t19 = newTable(h19.length);
        addHeaderRow(t19, h19);
        if (isEmpty(ppr19)) {
            addEmptyRow(t19, h19.length);
        } else {
            for (PprPendingUc d : ppr19) {
                String period = (d.getPendingStart() != null ? d.getPendingStart().format(DATE_FMT) : "") + " - "
                        + (d.getPendingEnd() != null ? d.getPendingEnd().format(DATE_FMT) : "");
                addRow(t19,
                        safe(d.getPpr().getProjectName()),
                        safe(d.getFinYear() != null ? d.getFinYear().getFinYrDesc() : null),
                        safe(d.getInstallmentNo()),
                        safe(d.getReleasedAmount()), safe(d.getUtilizedAmount()),
                        d.getDueDate() != null ? d.getDueDate().format(DATE_FMT) : "",
                        safe(d.getUcAmount()),
                        d.getUcSubmissionDate() != null ? d.getUcSubmissionDate().format(DATE_FMT) : "",
                        safe(d.getUcSubmissionAmt()), safe(d.getReasonNotSubmitted()),
                        period, safe(d.getPendingAmount()));
            }
        }
        document.add(t19);

        // ---- PPR-20 ----
        addSectionHeader(document, "PPR-20 : Draft Details (Unspent Balance)");
        List<PprWcdcUnspentBalance> ppr20 = castList(data.get("draftList"));
        String[] h20 = { "District", "Project", "Total Cost", "State Fund", "DoLR Fund", "Interest", "Total", "Unspent Balance" };
        PdfPTable t20 = newTable(h20.length);
        addHeaderRow(t20, h20);
        if (isEmpty(ppr20)) {
            addEmptyRow(t20, h20.length);
        } else {
            for (PprWcdcUnspentBalance d : ppr20) {
                addRow(t20,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getTotCost()), safe(d.getStReleasedFund()), safe(d.getDolrReleasedFund()),
                        safe(d.getInterest()), safe(d.getTotal()), safe(d.getUnspendBalance()));
            }
        }
        document.add(t20);

        document.close();
        return baos;
    }

    // ---------- helpers ----------

    private void addTitle(Document document) throws Exception {
        PdfPTable title = new PdfPTable(1);
        title.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(25, 90, 150));
        cell.setBorder(0);
        cell.setPadding(8);
        Paragraph p1 = new Paragraph("Government of India - Department of Land Resources", titleFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        Paragraph p2 = new Paragraph("Preliminary Project Report (PPR)", sectionFont);
        p2.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p1);
        cell.addElement(p2);
        title.addCell(cell);
        document.add(title);
        document.add(new Paragraph(" "));
    }

    private void addSectionHeader(Document document, String text) throws Exception {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        PdfPCell cell = new PdfPCell(new Paragraph(text, sectionFont));
        cell.setBackgroundColor(sectionBg);
        cell.setPadding(5);
        cell.setBorder(0);
        table.addCell(cell);
        document.add(table);
    }

    private PdfPTable newTable(int cols) {
        PdfPTable table = new PdfPTable(cols);
        table.setWidthPercentage(100);
        table.setSpacingAfter(5);
        return table;
    }

    private void addHeaderRow(PdfPTable table, String[] headers) {
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(h, headerFont));
            cell.setBackgroundColor(headerBg);
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void addRow(PdfPTable table, String... values) {
        for (String v : values) {
            PdfPCell cell = new PdfPCell(new Paragraph(v == null ? "" : v, cellFont));
            cell.setPadding(3);
            table.addCell(cell);
        }
    }

    private void addEmptyRow(PdfPTable table, int colspan) {
        PdfPCell cell = new PdfPCell(new Paragraph("No records found.", cellFont));
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private int nz(Integer i) {
        return i == null ? 0 : i;
    }

    private String safe(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> castList(Object obj) {
        return obj == null ? List.of() : (List<T>) obj;
    }
}