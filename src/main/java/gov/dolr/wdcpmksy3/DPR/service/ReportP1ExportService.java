package gov.dolr.wdcpmksy3.DPR.service;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


import gov.dolr.wdcpmksy3.dto.CodeName;
import gov.dolr.wdcpmksy3.dto.ReportP1Row;




@Service
public class ReportP1ExportService {

    static final String TITLE = "Report P1- State-wise,District-wise and Year-wise Details of Projects "
        + "Sanctioned including Area Sanctioned, Total Cost(Central/State Share)";
    static final String[] HEAD = {"S.No.", "State Name", "District Name", "Financial Year", "Project Name",
        "Project Sanction Date", "Area Sanctioned", "Total Cost", "Central Share", "State Share"};
    static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    private final ReportP1Service service;

    public ReportP1ExportService(ReportP1Service service) { this.service = service; }

    private static String nameOf(List<CodeName> list, Integer code, String all) {
        if (code == null || code <= 0) return all;
        return list.stream().filter(x -> x.getCode().equals(code)).map(CodeName::getName)
                   .findFirst().orElse(all);
    }

    public String filterText(Integer s, Integer d, Integer f) {
        return "State: " + nameOf(service.states(), s, "All State")
             + " | District: " + nameOf(service.districts(s), d, "All District")
             + " | Financial Year: " + nameOf(service.finYears(), f, "All Financial Year");
    }

    // ---------------- Excel ----------------
    public byte[] toExcel(List<ReportP1Row> rows, String filters) throws Exception {
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sh = wb.createSheet("Report P1");
            Font bold = wb.createFont(); bold.setBold(true);
            Font white = wb.createFont(); white.setBold(true); white.setColor(IndexedColors.WHITE.getIndex());

            CellStyle head = wb.createCellStyle();
            head.setFillForegroundColor(IndexedColors.TEAL.getIndex());
            head.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            head.setFont(white);
            head.setAlignment(HorizontalAlignment.CENTER);
            head.setVerticalAlignment(VerticalAlignment.CENTER);
            head.setWrapText(true);

            DataFormat df = wb.createDataFormat();
            CellStyle dec2 = wb.createCellStyle(); dec2.setDataFormat(df.getFormat("#,##0.00"));
            CellStyle dec4 = wb.createCellStyle(); dec4.setDataFormat(df.getFormat("#,##0.0000"));
            CellStyle b2 = wb.createCellStyle(); b2.cloneStyleFrom(dec2); b2.setFont(bold);
            CellStyle b4 = wb.createCellStyle(); b4.cloneStyleFrom(dec4); b4.setFont(bold);

            int r = 0;
            Row t = sh.createRow(r++);
            Cell tc = t.createCell(0); tc.setCellValue(TITLE); tc.setCellStyle(head);
            for (int i = 1; i < 10; i++) t.createCell(i).setCellStyle(head);
            sh.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            sh.createRow(r++).createCell(0).setCellValue(filters);
            sh.createRow(r++).createCell(0).setCellValue("Report as on: " + LocalDateTime.now().format(TS)
                + "    (All amounts in Rs lakhs, All Area in Ha.)");

            Row h = sh.createRow(r++);
            for (int i = 0; i < HEAD.length; i++) { Cell c = h.createCell(i); c.setCellValue(HEAD[i]); c.setCellStyle(head); }
            Row n = sh.createRow(r++);
            for (int i = 0; i < 10; i++) { Cell c = n.createCell(i); c.setCellValue(i + 1); c.setCellStyle(head); }
            sh.createFreezePane(0, r);

            int sno = 1;
            for (ReportP1Row x : rows) {
                Row row = sh.createRow(r++);
                row.createCell(0).setCellValue(sno++);
                row.createCell(1).setCellValue(x.getStateName());
                row.createCell(2).setCellValue(x.getDistrictName());
                row.createCell(3).setCellValue(x.getFinYear() == null ? "" : x.getFinYear());
                row.createCell(4).setCellValue(x.getProjectName());
                row.createCell(5).setCellValue(x.getSanctionDate() == null ? "" : x.getSanctionDate().format(DF));
                num(row, 6, x.getAreaSanctioned(), dec4);
                num(row, 7, x.getTotCost(), dec2);
                num(row, 8, x.getCentralShare(), dec2);
                num(row, 9, x.getStateShare(), dec2);
            }
            Row g = sh.createRow(r);
            Cell gl = g.createCell(5); gl.setCellValue("Grand Total"); gl.setCellStyle(b2);
            num(g, 6, service.sum(rows, ReportP1Row::getAreaSanctioned), b4);
            num(g, 7, service.sum(rows, ReportP1Row::getTotCost), b2);
            num(g, 8, service.sum(rows, ReportP1Row::getCentralShare), b2);
            num(g, 9, service.sum(rows, ReportP1Row::getStateShare), b2);

            int[] w = {8, 24, 26, 14, 42, 20, 18, 16, 16, 16};
            for (int i = 0; i < w.length; i++) sh.setColumnWidth(i, w[i] * 256);
            wb.write(out);
            return out.toByteArray();
        }
    }

    private static void num(Row row, int col, BigDecimal v, CellStyle st) {
        Cell c = row.createCell(col);
        if (v != null) c.setCellValue(v.doubleValue());
        c.setCellStyle(st);
    }

    // ---------------- PDF ----------------
    public byte[] toPdf(List<ReportP1Row> rows, String filters) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4.rotate(), 20, 20, 25, 25);
        PdfWriter.getInstance(doc, out);
        doc.open();

        com.lowagie.text.Font tf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        com.lowagie.text.Font nf = FontFactory.getFont(FontFactory.HELVETICA, 8);
        com.lowagie.text.Font hf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.WHITE);
        com.lowagie.text.Font bf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);

        doc.add(new Paragraph(TITLE, tf));
        doc.add(new Paragraph(filters, nf));
        doc.add(new Paragraph("Report as on: " + LocalDateTime.now().format(TS)
            + "     All amounts (in Rs lakhs), All Area in Ha.", nf));
        doc.add(new Paragraph(" "));

        PdfPTable t = new PdfPTable(new float[]{4, 12, 13, 8, 22, 10, 10, 8, 8, 8});
        t.setWidthPercentage(100);
        t.setHeaderRows(2);
        Color teal = new Color(0x4A, 0x7C, 0x82);
        for (String s : HEAD) t.addCell(cell(s, hf, teal, Element.ALIGN_CENTER));
        for (int i = 1; i <= 10; i++) t.addCell(cell(String.valueOf(i), hf, teal, Element.ALIGN_CENTER));

        DecimalFormat d2 = new DecimalFormat("#,##0.00"), d4 = new DecimalFormat("#,##0.0000");
        int sno = 1;
        for(ReportP1Row x : rows) {
            Color bg = (sno % 2 == 1) ? new Color(0xCF, 0xE2, 0xE6) : Color.WHITE;
            t.addCell(cell(String.valueOf(sno++), nf, bg, Element.ALIGN_CENTER));
            t.addCell(cell(x.getStateName(), nf, bg, Element.ALIGN_LEFT));
            t.addCell(cell(x.getDistrictName(), nf, bg, Element.ALIGN_LEFT));
            t.addCell(cell(x.getFinYear(), nf, bg, Element.ALIGN_RIGHT));
            t.addCell(cell(x.getProjectName(), nf, bg, Element.ALIGN_LEFT));
            t.addCell(cell(x.getSanctionDate() == null ? "" : x.getSanctionDate().format(DF), nf, bg, Element.ALIGN_RIGHT));
            t.addCell(cell(fmt(d4, x.getAreaSanctioned()), nf, bg, Element.ALIGN_RIGHT));
            t.addCell(cell(fmt(d2, x.getTotCost()), nf, bg, Element.ALIGN_RIGHT));
            t.addCell(cell(fmt(d2, x.getCentralShare()), nf, bg, Element.ALIGN_RIGHT));
            t.addCell(cell(fmt(d2, x.getStateShare()), nf, bg, Element.ALIGN_RIGHT));
        }
        Color gt = new Color(0xB8, 0xD9, 0xF8);
        PdfPCell lbl = cell("Grand Total", bf, gt, Element.ALIGN_RIGHT);
        lbl.setColspan(6);
        t.addCell(lbl);
        t.addCell(cell(fmt(d4, service.sum(rows,ReportP1Row::getAreaSanctioned)), bf, gt, Element.ALIGN_RIGHT));
        t.addCell(cell(fmt(d2, service.sum(rows,ReportP1Row::getTotCost)), bf, gt, Element.ALIGN_RIGHT));
        t.addCell(cell(fmt(d2, service.sum(rows,ReportP1Row::getCentralShare)), bf, gt, Element.ALIGN_RIGHT));
        t.addCell(cell(fmt(d2, service.sum(rows,ReportP1Row::getStateShare)), bf, gt, Element.ALIGN_RIGHT));
        doc.add(t);
        doc.close();
        return out.toByteArray();
    }

    private static String fmt(DecimalFormat f, BigDecimal v) { return v == null ? "" : f.format(v); }

    private static PdfPCell cell(String txt, com.lowagie.text.Font f, Color bg, int align) {
        PdfPCell c = new PdfPCell(new Phrase(txt == null ? "" : txt, f));
        c.setBackgroundColor(bg);
        c.setHorizontalAlignment(align);
        c.setPadding(4);
        return c;
    }
}
