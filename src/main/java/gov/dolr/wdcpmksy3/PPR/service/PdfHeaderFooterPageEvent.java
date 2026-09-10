package gov.dolr.wdcpmksy3.PPR.service;

import java.awt.Color;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Draws:
 *   - Ashoka Stambh + "Government of India - Department of Land Resources" +
 *     "Preliminary Project Report (PPR)" at the top of EVERY page
 *   - "Page X of Y" at the bottom of every page
 *
 * Registered on the PdfWriter via writer.setPageEvent(...) BEFORE
 * document.open() is called (see PdfService.generatePprReportPdf).
 *
 * "Page X of Y" needs a 2-pass trick because the total page count isn't
 * known while a page is being written: we reserve a small PdfTemplate
 * placeholder for "Y" on every page in onEndPage(), and only fill it in
 * once, in onCloseDocument(), when the final page number is known.
 */
public class PdfHeaderFooterPageEvent extends PdfPageEventHelper {

    private final Image logo;
    private final Font headerTitleFont;
    private final Font headerSubFont;
    private final Font footerFont;

    private PdfTemplate totalPagesTemplate;
    private BaseFont baseFont;

    public PdfHeaderFooterPageEvent(Image logo, Font headerTitleFont, Font headerSubFont, Font footerFont) {
        this.logo = logo;
        this.headerTitleFont = headerTitleFont;
        this.headerSubFont = headerSubFont;
        this.footerFont = footerFont;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            // Small template reserved for the "total pages" number, filled in at the very end.
            totalPagesTemplate = writer.getDirectContent().createTemplate(30, 16);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            float usableWidth = pageWidth - document.leftMargin() - document.rightMargin();

            // ---------------- Header ----------------
            PdfPTable header = new PdfPTable(logo != null ? new float[] { 1f, 7f } : new float[] { 1f });
            header.setTotalWidth(usableWidth);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            if (logo != null) {
                Image logoCopy = Image.getInstance(logo);
                logoCopy.scaleToFit(40, 40);
                PdfPCell logoCell = new PdfPCell(logoCopy, false);
                logoCell.setBorder(Rectangle.NO_BORDER);
                logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.addCell(logoCell);
            }

            PdfPCell textCell = new PdfPCell();
            textCell.setBorder(Rectangle.NO_BORDER);
            textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            Paragraph p1 = new Paragraph("Government of India - Department of Land Resources", headerTitleFont);
            p1.setAlignment(Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph("Preliminary Project Report (PPR)", headerSubFont);
            p2.setAlignment(Element.ALIGN_CENTER);
            textCell.addElement(p1);
            textCell.addElement(p2);
            header.addCell(textCell);

            header.writeSelectedRows(0, -1, document.leftMargin(), pageHeight - 14, cb);

            cb.saveState();
            cb.setLineWidth(0.75f);
            cb.setColorStroke(new Color(13, 110, 253));
            cb.moveTo(document.leftMargin(), pageHeight - 62);
            cb.lineTo(pageWidth - document.rightMargin(), pageHeight - 62);
            cb.stroke();
            cb.restoreState();

            // ---------------- Footer ("Page X of Y") ----------------
            float footerY = document.bottomMargin() - 14;
            String prefix = "Page " + writer.getPageNumber() + " of ";
            float prefixWidth = baseFont.getWidthPoint(prefix, 8f);
            float totalBlockWidth = prefixWidth + 20f; // 20pt reserved for the total-pages template
            float startX = (pageWidth - totalBlockWidth) / 2f;

            cb.beginText();
            cb.setFontAndSize(baseFont, 8f);
            cb.setTextMatrix(startX, footerY);
            cb.showText(prefix);
            cb.endText();

            cb.addTemplate(totalPagesTemplate, startX + prefixWidth, footerY - 2f);

            cb.saveState();
            cb.setLineWidth(0.5f);
            cb.setColorStroke(Color.GRAY);
            cb.moveTo(document.leftMargin(), document.bottomMargin() - 4);
            cb.lineTo(pageWidth - document.rightMargin(), document.bottomMargin() - 4);
            cb.stroke();
            cb.restoreState();

        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        totalPagesTemplate.beginText();
        totalPagesTemplate.setFontAndSize(baseFont, 8f);
        totalPagesTemplate.setTextMatrix(0, 2);
        totalPagesTemplate.showText(String.valueOf(writer.getPageNumber()));
        totalPagesTemplate.endText();
    }
}