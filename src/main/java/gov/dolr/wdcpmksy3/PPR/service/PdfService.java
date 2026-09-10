package gov.dolr.wdcpmksy3.PPR.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;

/**
 * Builds the PPR-1..PPR-20 report PDF from the SAME data map that
 * PPRViewController.fetchReportData() produces for the on-screen view,
 * so the PDF and the web page can never drift apart.
 *
 * Layout approach
 * ----------------
 * - A4 LANDSCAPE for the whole document (the widest tables, e.g. PPR-2 and
 *   PPR-4, need it; switching orientation per-section is unreliable in
 *   OpenPDF so the whole report uses landscape, as you asked for as the
 *   fallback).
 * - Every page gets the Ashoka Stambh + Government of India header and a
 *   "Page X of Y" footer via PdfHeaderFooterPageEvent (a PdfPageEventHelper),
 *   registered on the PdfWriter BEFORE document.open().
 * - Each PPR section's title is embedded as the FIRST ROW of that section's
 *   own PdfPTable (colspan = number of columns), and table.setHeaderRows(n)
 *   marks the title row + column-header row(s) as "header rows". OpenPDF's
 *   PdfPTable will not split a table's header rows away from at least one
 *   body row: if there isn't room for the header + a first data row, the
 *   WHOLE table (title included) moves to the next page automatically. That
 *   gives you "keep heading with its table" and "don't orphan a heading at
 *   the bottom of a page" without manual space bookkeeping, and the column
 *   headers repeat automatically on every page the table spans.
 */
@Service
public class PdfService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String LOGO_CLASSPATH_LOCATION = "/static/images/ashok-stambh.png";

    private final Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    private final Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7.5f, Color.WHITE);
    private final Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7.5f, Color.WHITE);
    private final Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 7.5f, Color.BLACK);
    private final Font pageHeaderTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, Color.BLACK);
    private final Font pageHeaderSubFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new Color(60, 60, 60));
    private final Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.BLACK);

    private final Color headerBg = new Color(13, 110, 253);   // matches Bootstrap table-primary used on screen
    private final Color sectionBg = new Color(25, 90, 150);

    public ByteArrayOutputStream generatePprReportPdf(Map<String, Object> data) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Extra top margin (room for the header) and bottom margin (room for the footer).
        Document document = new Document(PageSize.A4.rotate(), 24, 24, 78, 42);
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        Image logo = loadLogo();
        writer.setPageEvent(new PdfHeaderFooterPageEvent(logo, pageHeaderTitleFont, pageHeaderSubFont, footerFont));

        document.open();

        // ---- PPR-1 ----
        List<MPpr> records = castList(data.get("records"));
        PdfPTable t1 = newSectionTable("PPR-1 : Preliminary Project Report",
                new String[] { "Financial Year", "District", "Project Name", "Micro-Watershed" },
                new float[] { 2, 2, 3, 4 });
        if (isEmpty(records)) {
            addEmptyRow(t1, 4);
        } else {
            for (MPpr r : records) {
                addRow(t1,
                        safe(r.getFinYear() != null ? r.getFinYear().getFinYrDesc() : null),
                        safe(r.getDistrict() != null ? r.getDistrict().getDistName() : null),
                        safe(r.getProjectName()),
                        joinMicroWatersheds(r));
            }
        }
        document.add(t1);

        // ---- PPR-2 : Area Covered under Watershed Programme (grouped, was MISSING) ----
        addWatershedCoveredAreaTable(document, castList(data.get("watershedList")));

        // ---- PPR-3 ----
        List<PprProposedProject> ppr3 = castList(data.get("detailsOfListOfProposedProject"));
        PdfPTable t3 = newSectionTable("PPR-3 : Prioritized List of Proposed Projects",
                new String[] { "District", "Project Name", "Micro-Watershed", "MW Code", "Proposed Area (Ha.)",
                        "Type of Project", "Proposed Cost" },
                new float[] { 2, 3, 3, 2, 2, 2, 2 });
        if (isEmpty(ppr3)) {
            addEmptyRow(t3, 7);
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

        // ---- PPR-4 (was missing Block / Gram Panchayat / Villages / PIA Address) ----
        List<PprProjectGlance> ppr4 = castList(data.get("pprProjectAtGlanceList"));
        PdfPTable t4 = newSectionTable("PPR-4 : Project at a Glance",
                new String[] { "District", "Project", "Type", "Block", "Gram Panchayat", "Villages", "MW Code",
                        "Reason", "Proj Area", "Proposed Area", "Cost (Rs. Lakh)", "PIA", "PIA Address" },
                new float[] { 2, 3, 2, 2, 2, 3, 2, 3, 2, 2, 2, 3, 4 });
        if (isEmpty(ppr4)) {
            addEmptyRow(t4, 13);
        } else {
            for (PprProjectGlance d : ppr4) {
                String block = "";
                String gp = "";
                if (d.getVillages() != null && !d.getVillages().isEmpty()) {
                    var firstVillage = d.getVillages().get(0).getVillage();
                    block = safe(firstVillage.getGramPanchayat().getBlock().getBlockName());
                    gp = safe(firstVillage.getGramPanchayat().getGramPanchayatName());
                }
                StringBuilder villages = new StringBuilder();
                if (d.getVillages() != null) {
                    for (var pv : d.getVillages()) {
                        if (villages.length() > 0) villages.append(", ");
                        villages.append(pv.getVillage().getVillageName());
                    }
                }
                addRow(t4,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getProjectType().getProjectType()),
                        block,
                        gp,
                        villages.toString(),
                        safe(d.getMicroWatershed().getMwCode()),
                        safe(d.getSelectionReason()),
                        safe(d.getProjectArea()),
                        safe(d.getProposedArea()),
                        safe(d.getProjectCost()),
                        safe(d.getPia() != null ? d.getPia().getPiaName() : null),
                        safe(d.getPia() != null ? d.getPia().getAddress() : null));
            }
        }
        document.add(t4);

        // ---- PPR-8 (was missing State) ----
        List<PprProposedArea> ppr8 = castList(data.get("ppr8List"));
        PdfPTable t8 = newSectionTable("PPR-8 : Area Proposed",
                new String[] { "State", "District", "Project", "Block", "Scheme", "No. of Pre WDC Projects",
                        "Sanctioned Area", "Net Area", "Proposed Area", "Other DoLR Area", "Balance Area" },
                new float[] { 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2 });
        if (isEmpty(ppr8)) {
            addEmptyRow(t8, 11);
        } else {
            for (PprProposedArea d : ppr8) {
                addRow(t8,
                        safe(d.getPpr().getDistrict().getState().getStName()),
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

        // ---- PPR-9 (was missing S.No.) ----
        List<PPRLandPatternArea> ppr9 = castList(data.get("landPatternAreaList"));
        PdfPTable t9 = newSectionTable("PPR-9 : Land Pattern Area",
                new String[] { "S.No.", "District", "Project", "Micro-Watershed", "Village", "Geo Area",
                        "Forest", "Agriculture", "Rainfed", "Pastures", "Cultivable Waste", "Non-Cultivable Waste" },
                new float[] { 1, 2, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 });
        if (isEmpty(ppr9)) {
            addEmptyRow(t9, 12);
        } else {
            int sno = 1;
            for (PPRLandPatternArea d : ppr9) {
                addRow(t9,
                        String.valueOf(sno++),
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

        // ---- PPR-10 (was missing Soil Type(s) / Crop(s)) ----
        List<PprAgroClimate> ppr10 = castList(data.get("ppr10List"));
        PdfPTable t10 = newSectionTable("PPR-10 : Details of Agro-Climatic Condition",
                new String[] { "District", "Project", "Village", "Agro-Climatic Zone", "Topography", "Rainfall (mm)",
                        "Area (Ha.)", "Forest Area (Ha.)", "Major Soil Type(s)", "Major Crop(s)" },
                new float[] { 2, 3, 2, 2, 2, 2, 2, 2, 3, 3 });
        if (isEmpty(ppr10)) {
            addEmptyRow(t10, 10);
        } else {
            for (PprAgroClimate d : ppr10) {
                StringBuilder soils = new StringBuilder();
                if (d.getSoilList() != null) {
                    for (var s : d.getSoilList()) {
                        if (soils.length() > 0) soils.append(", ");
                        soils.append(s.getSoilType().getSoilName()).append(" (").append(safe(s.getArea())).append(" ha)");
                    }
                }
                StringBuilder crops = new StringBuilder();
                if (d.getCropList() != null) {
                    for (var c : d.getCropList()) {
                        if (crops.length() > 0) crops.append(", ");
                        crops.append(c.getCropType().getCropName()).append(" (").append(safe(c.getArea())).append(" ha)");
                    }
                }
                addRow(t10,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getVillage().getVillageName()),
                        safe(d.getZoneName()),
                        safe(d.getTopography()),
                        safe(d.getAvgRainfall()),
                        safe(d.getArea()),
                        safe(d.getForestArea()),
                        soils.toString(),
                        crops.toString());
            }
        }
        document.add(t10);

        // ---- PPR-11 (was missing S No.) ----
        List<PprDisasterDetails> ppr11 = castList(data.get("ppr11List"));
        PdfPTable t11 = newSectionTable("PPR-11 : Details of Flood and Drought in Project Area",
                new String[] { "S No.", "Village", "Year", "Month", "Particular", "Periodicity", "Affected" },
                new float[] { 1, 3, 2, 2, 3, 2, 2 });
        if (isEmpty(ppr11)) {
            addEmptyRow(t11, 7);
        } else {
            int sno = 1;
            for (PprDisasterDetails d : ppr11) {
                addRow(t11,
                        String.valueOf(sno++),
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
        List<PPRSoilErosion> ppr12 = castList(data.get("soilErosionList"));
        PdfPTable t12 = newSectionTable("PPR-12 : Soil Erosion Details",
                new String[] { "District", "Month", "Year", "Type of Erosion", "Erosion Type", "Affected Area (ha)",
                        "Runoff (mm/year)", "Average Soil Loss (tonnes/year)" },
                new float[] { 2, 2, 2, 3, 3, 2, 2, 3 });
        if (isEmpty(ppr12)) {
            addEmptyRow(t12, 8);
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

        // ---- PPR-13 (was missing Livelihood Activities / Interventions) ----
        List<PprLivelihood> ppr13 = castList(data.get("pprLivelihoodSummaryList"));
        PdfPTable t13 = newSectionTable("PPR-13 : Livelihood Summary",
                new String[] { "District", "Block", "Project", "Micro-Watershed", "Village",
                        "Livelihood Activities", "Livelihood Interventions", "Migration (No. of People)",
                        "Main Reason of Migration" },
                new float[] { 2, 2, 3, 3, 2, 3, 3, 2, 3 });
        if (isEmpty(ppr13)) {
            addEmptyRow(t13, 9);
        } else {
            for (PprLivelihood d : ppr13) {
                StringBuilder activities = new StringBuilder();
                if (d.getExistingLivelihoodActivities() != null) {
                    for (var act : d.getExistingLivelihoodActivities()) {
                        if (activities.length() > 0) activities.append(", ");
                        activities.append(act.getLivelihoodActivity().getLivelihoodActivityName());
                    }
                }
                StringBuilder interventions = new StringBuilder();
                if (d.getProjectLivelihoodInterventions() != null) {
                    for (var intv : d.getProjectLivelihoodInterventions()) {
                        if (interventions.length() > 0) interventions.append(", ");
                        interventions.append(intv.getLivelihoodIntervention().getLivelihoodInterventionName());
                    }
                }
                addRow(t13,
                        safe(d.getPpr().getDistrict().getDistName()),
                        safe(d.getVillage().getGramPanchayat().getBlock().getBlockName()),
                        safe(d.getPpr().getProjectName()),
                        safe(d.getMicroWatershed().getMwName()),
                        safe(d.getVillage().getVillageName()),
                        activities.toString(),
                        interventions.toString(),
                        safe(d.getMigratedPeople()),
                        safe(d.getMigrationReason()));
            }
        }
        document.add(t13);

        // ---- PPR-14 ----
        List<PPREmploymentGeneration> ppr14 = castList(data.get("pprEmploymentList"));
        PdfPTable t14 = newSectionTable("PPR-14 : Employment Generation Details",
                new String[] { "District", "Project", "Micro-Watershed", "Village", "Employment Type", "SC", "ST",
                        "Others", "Women", "Total" },
                new float[] { 2, 3, 3, 2, 3, 1, 1, 1, 1, 1 });
        if (isEmpty(ppr14)) {
            addEmptyRow(t14, 10);
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
        List<Map<String, Object>> ppr15 = castList(data.get("ppr15List"));
        PdfPTable t15 = newSectionTable("PPR-15 : Migration Details",
                new String[] { "District", "Project", "Watershed", "Village", "People Migrating", "Days/Year",
                        "Major Reason(s)", "Expected Reduction" },
                new float[] { 2, 3, 3, 2, 2, 2, 3, 2 });
        if (isEmpty(ppr15)) {
            addEmptyRow(t15, 8);
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
        List<PprWaterOutcome> ppr16 = castList(data.get("pprWaterOutcomesList"));
        PdfPTable t16 = newSectionTable("PPR-16 : Average Ground Water Table Depth in Project Area",
                new String[] { "District", "Project", "Micro-Watershed", "Village", "Source", "Pre-Project Level",
                        "Expected Post Project Level", "Remarks" },
                new float[] { 2, 3, 3, 2, 2, 2, 2, 3 });
        if (isEmpty(ppr16)) {
            addEmptyRow(t16, 8);
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
        List<PprDrinkingWater> ppr17 = castList(data.get("pprDrinkingWaterList"));
        PdfPTable t17 = newSectionTable("PPR-17 : Drinking Water Status",
                new String[] { "District", "Project", "Watershed", "Village", "Pre-Project Availability (Months)",
                        "Pre-Project Water Quality", "Post-Project Availability (Months)", "Post-Project Water Quality" },
                new float[] { 2, 3, 3, 2, 2, 2, 2, 2 });
        if (isEmpty(ppr17)) {
            addEmptyRow(t17, 8);
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

        // ---- PPR-18 (was missing S.No.) ----
        List<PprCropOutcome> ppr18 = castList(data.get("cropOutcomes"));
        PdfPTable t18 = newSectionTable("PPR-18 : Major Crops Grown and Their Productivity in the Project",
                new String[] { "S.No", "Season", "Name of Crop", "Current Area (ha.)", "Current Productivity (kg/ha.)",
                        "Expected Area (ha.)", "Expected Productivity (kg/ha.)" },
                new float[] { 1, 2, 3, 2, 2, 2, 2 });
        if (isEmpty(ppr18)) {
            addEmptyRow(t18, 7);
        } else {
            int sno = 1;
            for (PprCropOutcome d : ppr18) {
                addRow(t18,
                        String.valueOf(sno++),
                        safe(d.getSeason().getSeasonName()),
                        safe(d.getCropType().getCropName()),
                        safe(d.getCurrentArea()), safe(d.getCurrentProd()),
                        safe(d.getExpectedArea()), safe(d.getExpectedProd()));
            }
        }
        document.add(t18);

        // ---- PPR-19 ----
        List<PprPendingUc> ppr19 = castList(data.get("pendingUCList"));
        PdfPTable t19 = newSectionTable("PPR-19 : List of Details of Pending UC's",
                new String[] { "Project", "Fin. Year", "Installment No.", "Amount Released (Rs. Lakh)",
                        "Amount Utilized (Rs. Lakh)", "Due Date", "UC Amount", "Submission Date", "Submitted Amount",
                        "Reason", "Pending Period", "Pending Amount" },
                new float[] { 3, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 2 });
        if (isEmpty(ppr19)) {
            addEmptyRow(t19, 12);
        } else {
            for (PprPendingUc d : ppr19) {
                String period = (d.getPendingStart() != null ? d.getPendingStart().format(DATE_FMT) : "") + " to "
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
        List<PprWcdcUnspentBalance> ppr20 = castList(data.get("draftList"));
        PdfPTable t20 = newSectionTable("PPR-20 : Draft Details (Unspent Balance)",
                new String[] { "District", "Project", "Total Cost (Rs. Lakh)", "State Fund Released",
                        "DoLR Fund Released", "Interest", "Total (Rs. Lakh)", "Unspent Balance" },
                new float[] { 2, 3, 2, 2, 2, 2, 2, 2 });
        if (isEmpty(ppr20)) {
            addEmptyRow(t20, 8);
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

    // ---------- PPR-2 : grouped table (this section was entirely missing before) ----------

    private void addWatershedCoveredAreaTable(Document document, List<WatershedAreaBean> watershedList) throws Exception {

        int cols = 16; // S No. + Micro-Watershed + 7 schemes x (No. + Area)
        PdfPTable table = new PdfPTable(cols);
        table.setWidthPercentage(100);
        table.setSpacingBefore(14);
        table.setSpacingAfter(6);
        table.setWidths(new float[] { 1, 3, 1, 1.4f, 1, 1.4f, 1, 1.4f, 1, 1.4f, 1, 1.4f, 1, 1.4f, 1, 1.4f });

        // Row 1: section title, spans every column
        PdfPCell title = new PdfPCell(new Paragraph("PPR-2 : Area Covered under Watershed Programme", sectionFont));
        title.setColspan(cols);
        title.setBackgroundColor(sectionBg);
        title.setPadding(6);
        table.addCell(title);

        // Row 2: scheme group headers (each spans No. + Area = 2 columns)
        addGroupHeaderCell(table, "S No.", 1, 2);
        addGroupHeaderCell(table, "Micro-Watershed", 1, 2);
        String[] schemeGroups = { "Pre-WDC2.0", "DPAP", "DDP", "IWDP", "IWMP", "WDC-PMKSY 2.0", "Others" };
        for (String g : schemeGroups) {
            addGroupHeaderCell(table, g, 2, 1);
        }

        // Row 3: No. / Area sub-headers under each scheme group
        for (int i = 0; i < schemeGroups.length; i++) {
            addPlainHeaderCell(table, "No.");
            addPlainHeaderCell(table, "Area");
        }

        table.setHeaderRows(3);

        if (isEmpty(watershedList)) {
            addEmptyRow(table, cols);
        } else {
            int sno = 1;
            for (WatershedAreaBean row : watershedList) {
                addRow(table,
                        String.valueOf(sno++),
                        safe(row.getMwName()),
                        safe(row.getPreNo()), safe(row.getPreArea()),
                        safe(row.getDpapNo()), safe(row.getDpapArea()),
                        safe(row.getDdpNo()), safe(row.getDdpArea()),
                        safe(row.getIwdpNo()), safe(row.getIwdpArea()),
                        safe(row.getIwmpNo()), safe(row.getIwmpArea()),
                        safe(row.getPmksyNo()), safe(row.getPmksyArea()),
                        safe(row.getOtherNo()), safe(row.getOtherArea()));
            }
        }

        document.add(table);
    }

    private void addGroupHeaderCell(PdfPTable table, String text, int colspan, int rowspan) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, subHeaderFont));
        cell.setBackgroundColor(headerBg);
        cell.setPadding(4);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private void addPlainHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, subHeaderFont));
        cell.setBackgroundColor(headerBg);
        cell.setPadding(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    // ---------- generic section-table helpers ----------

    /**
     * Builds a table whose row 0 is the section title (full-width, colored) and
     * row 1 is the column header row; both are marked as header rows via
     * setHeaderRows(2) so OpenPDF keeps them attached to the first body row
     * (forcing a page break before the section if it doesn't fit) and repeats
     * the column headers on every page the table spans.
     */
    private PdfPTable newSectionTable(String title, String[] headers, float[] relativeWidths) throws Exception {
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        table.setSpacingBefore(14);
        table.setSpacingAfter(6);
        if (relativeWidths != null && relativeWidths.length == headers.length) {
            table.setWidths(relativeWidths);
        }

        PdfPCell titleCell = new PdfPCell(new Paragraph(title, sectionFont));
        titleCell.setColspan(headers.length);
        titleCell.setBackgroundColor(sectionBg);
        titleCell.setPadding(6);
        table.addCell(titleCell);

        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(h, headerFont));
            cell.setBackgroundColor(headerBg);
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        table.setHeaderRows(2);
        return table;
    }

    private void addRow(PdfPTable table, String... values) {
        for (String v : values) {
            PdfPCell cell = new PdfPCell(new Paragraph(v == null ? "" : v, cellFont));
            cell.setPadding(3);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }
    }

    private void addEmptyRow(PdfPTable table, int colspan) {
        PdfPCell cell = new PdfPCell(new Paragraph("No records found.", cellFont));
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        table.addCell(cell);
    }

    // ---------- misc helpers ----------

    private String joinMicroWatersheds(MPpr r) {
        StringBuilder sb = new StringBuilder();
        if (r.getMicroWatersheds() != null) {
            for (var mw : r.getMicroWatersheds()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(mw.getMicroWatershed().getMwName());
            }
        }
        return sb.toString();
    }

    private Image loadLogo() {
        try (InputStream in = getClass().getResourceAsStream(LOGO_CLASSPATH_LOCATION)) {
            if (in == null) {
                return null; // header still renders (text only) if the image isn't on the classpath yet
            }
            byte[] bytes = in.readAllBytes();
            return Image.getInstance(bytes);
        } catch (Exception e) {
            return null;
        }
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