package gov.dolr.wdcpmksy3.DPR.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.Rectangle;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherDtl;
import gov.dolr.wdcpmksy3.DPR.repository.DprDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprOtherDtlRepository;
import gov.dolr.wdcpmksy3.DPR.service.DPRNRMPlanService;
import gov.dolr.wdcpmksy3.PPR.repository.PprTransactionRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ViewConfirmDPRPlan {
	
	@Value("${dpr.path}")
    private String uploadPath;
	
	@Autowired
    private PprTransactionRepository tranrepo;
	
	@Autowired
    private DprDetailsRepository plan;
	
	@Autowired
    private DprOtherDtlRepository  other;
	
	@Autowired
    private DPRNRMPlanService dprserv;
	
	@GetMapping("/getViewConfirmDPRPlan")
    public String getViewConfirmDPRPlan(HttpSession session, Model model, @RequestParam(required = false) Integer district, 
    		@RequestParam(required = false) Integer project) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
		List<Object[]> finalList = new ArrayList<>();
        if(userid==null){

            return "redirect:/login";
        }
        
        boolean allnrm =false;
        boolean allelp =false;
        boolean alloth =false;
        boolean exists=false;
        if(project!=null) {	
		List<Object[]> list =plan.getDprNrmProjectPlanComplitedList(project);
		allnrm = list.stream().anyMatch(row -> "D".equals(String.valueOf(row[5])));
		//System.out.println("kdy1"+allnrm);
		List<Object[]> listelp =plan.getDprELPProjectPlanCompletedList(project);
		allelp = listelp.stream().anyMatch(row -> "D".equals(String.valueOf(row[9])));
		//System.out.println("kdy2"+allelp);
		List<DprOtherDtl> otheract=other.findCompletedOtherDtlByPprId(project);
		alloth= otheract.stream().anyMatch(row -> "D".equals(String.valueOf(row.getStatus())));
		//System.out.println("kdy3"+alloth);
		if(list!=null && !list.isEmpty()) {
	        int srNo = 1;
	        Integer previousId = null;

	        for (Object[] row : list) {

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
	    }
		
	    exists=plan.existsApprovedDprByPprId(project);
	 //   System.out.println("kdy4"+exists);
	    model.addAttribute("existssl", exists);
	    model.addAttribute("dprNrmProjectPlanList", finalList);
        model.addAttribute("dprELPList", listelp);
        model.addAttribute("dprOtherTargetList", otheract);
        model.addAttribute("projList", tranrepo.findPprByDcodeAndActionA(district));
        }
        
        boolean complete = false;
        if (!allnrm && !allelp && !alloth && exists) {
            complete = true;
           // System.out.println("kdy5"+complete);
        }

        model.addAttribute("complete", complete);
        model.addAttribute("distList", tranrepo.findDistrictsByStCodeAndActionA(stcode));
        model.addAttribute("selectedDcode", district);
        model.addAttribute("selectedProject", project);
        
        return "dpr/viewConfirmDPRPlan";
    }
	
	@GetMapping("/checkDPRProjectDataApproved")
	@ResponseBody
	public String checkDPRProjectDataApproved(@RequestParam Integer pprid) {

	    	List<DprDetails> records = plan.findByPprPprIdandStatus(pprid);
		    
		    if (records != null && !records.isEmpty()) {
		        return "EXISTS";
		    }

		    return "NOT_EXISTS";
	}
	
	 @PostMapping("/confirmDPR")
	 public String confirmDPR(HttpSession session, Model model, HttpServletRequest request,
	    		@RequestParam Integer pprdcode,
	    		@RequestParam Integer pprid,
	    		@RequestParam MultipartFile dprFile,
	            RedirectAttributes redirectAttributes) {
	   
				Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
				String userid=(String)session.getAttribute("userid");
				Integer block=0;
				try {
					
					if(userid==null){
	
				        return "redirect:/login";
				    }
					boolean save=false;
					
					File dir = new File(uploadPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
			    	
					save=dprserv.updateDPRNRMELPOtherTarget(pprid, dprFile, userid, CommonFunctions.getClientIpAddr(request));
					
					if(save)
						redirectAttributes.addFlashAttribute( "success", "DPR Approved.");
					else
						redirectAttributes.addFlashAttribute("error", "Unable to DPR Approved.");
				}
				catch (Exception e) {
	
						e.printStackTrace();
				        redirectAttributes.addFlashAttribute("error", "Unable to DPR Approved.");
				}
				return "redirect:/getViewConfirmDPRPlan";	
	    }
	 
	 @GetMapping("/downloadDPRActivityPdf")
	 public ResponseEntity<byte[]> downloadPPRPdf(
	         HttpSession session,
	         @RequestParam(required = false) Integer project) {

	     if (session.getAttribute("userid") == null) {
	         return ResponseEntity.status(HttpStatus.FOUND)
	                 .header(HttpHeaders.LOCATION, "/login")
	                 .build();
	     }

	     if (project == null) {
	         return ResponseEntity.badRequest().build();
	     }

	     try {
	         // ---------- Fetch data ----------
	         List<Object[]> nrm =
	                 plan.getDprNrmProjectPlanComplitedList(project);

	         List<Object[]> elp =
	                 plan.getDprELPProjectPlanCompletedList(project);

	         List<DprOtherDtl> oth =
	                 other.findCompletedOtherDtlByPprId(project);

	         String districtName = "";
	         String projectName = "";

	         // ---------- Get district and project name ----------
	         if (nrm != null && !nrm.isEmpty()) {
	             districtName = str(nrm.get(0)[3]);
	             projectName = str(nrm.get(0)[2]);

	         } else if (elp != null && !elp.isEmpty()) {
	             districtName = str(elp.get(0)[3]);
	             projectName = str(elp.get(0)[2]);

	         } else if (oth != null && !oth.isEmpty()) {
	             districtName = str(
	                     oth.get(0).getDprDetails()
	                             .getPpr().getDistrict().getDistName());

	             projectName = str(
	                     oth.get(0).getDprDetails()
	                             .getPpr().getProjectName());
	         }

	         // =====================================================
	         // DPR-1 : NRM
	         // Status = row[5]
	         // =====================================================
	         List<String[]> r1 = new ArrayList<>();

	         Object prevId = null;
	         String prevHead = null;

	         if (nrm != null) {
	             for (Object[] row : nrm) {

	                 boolean firstOfProject =
	                         prevId == null || !prevId.equals(row[0]);

	                 String status = str(row[5]);

	                 if ("S".equals(status)) {

	                     // Skip message after Project, merged across 4 columns
	                     if (firstOfProject) {
	                         r1.add(new String[]{
	                                 str(row[3]),  // District
	                                 str(row[2]),  // Project
	                                 "This DPR NRM has been skipped.",
	                                 "This DPR NRM has been skipped.",
	                                 "This DPR NRM has been skipped.",
	                                 "This DPR NRM has been skipped."
	                         });
	                     }

	                 } else if ("C".equals(status)) {

	                     String head = str(row[8]);

	                     boolean sameHead =
	                             !firstOfProject && head.equals(prevHead);

	                     String unit = str(row[11]);

	                     r1.add(new String[]{
	                             firstOfProject ? str(row[3]) : "",
	                             firstOfProject ? str(row[2]) : "",
	                             sameHead ? "" : head,
	                             str(row[10]),
	                             unit,
	                             fmtTarget(row[13], unit)
	                     });

	                     prevHead = head;
	                 }

	                 prevId = row[0];
	             }
	         }

	         // =====================================================
	         // DPR-2 : EPA, Livelihood and Production
	         // Status = row[9]
	         // =====================================================
	         List<String[]> r2 = new ArrayList<>();

	         if (elp != null) {
	             for (Object[] row : elp) {

	                 String status = str(row[9]);

	                 if ("S".equals(status)) {

	                     r2.add(new String[]{
	                             str(row[3]),  // District
	                             str(row[2]),  // Project
	                             "This DPR EPA, Livelihood and Production has been skipped.",
	                             "This DPR EPA, Livelihood and Production has been skipped.",
	                             "This DPR EPA, Livelihood and Production has been skipped.",
	                             "This DPR EPA, Livelihood and Production has been skipped."
	                     });

	                 } else if ("C".equals(status)) {

	                     String code = str(row[6]);

	                     String head = "L".equals(code)
	                             ? "Livelihood"
	                             : "P".equals(code)
	                                     ? "Production"
	                                     : "E".equals(code)
	                                             ? "EPA"
	                                             : code;

	                     r2.add(new String[]{
	                             str(row[3]),
	                             str(row[2]),
	                             head,
	                             str(row[7]),
	                             str(row[10]),
	                             str(row[8])
	                     });
	                 }
	             }
	         }

	         // =====================================================
	         // DPR-3 : Other Activity
	         // Status = d.getStatus()
	         // =====================================================
	         List<String[]> r3 = new ArrayList<>();

	         if (oth != null) {
	             for (DprOtherDtl d : oth) {

	                 String status = d.getStatus() == null
	                         ? ""
	                         : d.getStatus().toString();

	                 String dist = str(
	                         d.getDprDetails().getPpr()
	                                 .getDistrict().getDistName());

	                 String proj = str(
	                         d.getDprDetails().getPpr().getProjectName());

	                 if ("S".equals(status)) {

	                     r3.add(new String[]{
	                             dist,
	                             proj,
	                             "This DPR Other Activity has been skipped.",
	                             "This DPR Other Activity has been skipped.",
	                             "This DPR Other Activity has been skipped.",
	                             "This DPR Other Activity has been skipped."
	                     });

	                 } else if ("C".equals(status)) {

	                     String head = "";
	                     String act = "";
	                     String unit = "";

	                     if (d.getOtherActivity() != null) {

	                         if (d.getOtherActivity().getOtherHeads() != null) {
	                             head = str(
	                                     d.getOtherActivity()
	                                             .getOtherHeads()
	                                             .getOtherHeadDesc());
	                         }

	                         act = str(
	                                 d.getOtherActivity()
	                                         .getOtherActivityDesc());

	                         if (d.getOtherActivity().getUnit() != null) {
	                             unit = str(
	                                     d.getOtherActivity()
	                                             .getUnit().getUnitDesc());
	                         }
	                     }

	                     r3.add(new String[]{
	                             dist,
	                             proj,
	                             head,
	                             act,
	                             unit,
	                             str(d.getQtyPlanned())
	                     });
	                 }
	             }
	         }

	         // =====================================================
	         // Build PDF
	         // =====================================================
	         ByteArrayOutputStream out = new ByteArrayOutputStream();

	         Document doc = new Document(
	                 PageSize.A4.rotate(), 30, 30, 30, 30);

	         PdfWriter.getInstance(doc, out);
	         doc.open();

	         // ---------- PDF title ----------
	         Font titleFont = FontFactory.getFont(
	                 FontFactory.HELVETICA_BOLD, 15);

	         Paragraph title = new Paragraph(
	                 "Details of Project wise DPR Target", titleFont);

	         title.setAlignment(Element.ALIGN_CENTER);
	         title.setSpacingAfter(12);

	         doc.add(title);

	         // ---------- District and Project information ----------
	         Font infoFont = FontFactory.getFont(
	                 FontFactory.HELVETICA_BOLD, 11, Color.WHITE);

	         PdfPTable info = new PdfPTable(1);
	         info.setWidthPercentage(100);
	         info.setSpacingAfter(0);

	         PdfPCell infoCell = new PdfPCell(new Phrase(
	                 "District : " + districtName
	                         + "          Project : " + projectName,
	                 infoFont));

	         infoCell.setBackgroundColor(new Color(25, 135, 84));
	         infoCell.setPadding(6);
	         infoCell.setBorder(Rectangle.NO_BORDER);

	         info.addCell(infoCell);
	         doc.add(info);

	         // ---------- Table headings ----------
	         String[] heads = {
	                 "District", "Project", "Head",
	                 "Activity", "Unit", "Target"
	         };

	         float[] widths = {
	                 1.2f, 2.2f, 3f, 2.6f, 0.8f, 1.2f
	         };

	         // ---------- Add sections ----------
	         addSection(
	                 doc,
	                 "DPR-1 : Details of DPR NRM Target",
	                 heads, widths, r1, 0);

	         addSection(
	                 doc,
	                 "DPR-2 : Details of DPR EPA, Livelihood and Production Target",
	                 heads, widths, r2, 10);

	         addSection(
	                 doc,
	                 "DPR-3 : Details of DPR Other Activity Target",
	                 heads, widths, r3, 10);

	         doc.close();

	         // ---------- Return PDF ----------
	         return ResponseEntity.ok()
	                 .contentType(MediaType.APPLICATION_PDF)
	                 .header(
	                         HttpHeaders.CONTENT_DISPOSITION,
	                         "attachment; filename=\"DPR_Targets_"
	                                 + project + ".pdf\"")
	                 .body(out.toByteArray());

	     } catch (Exception e) {
	         e.printStackTrace();

	         return ResponseEntity
	                 .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                 .build();
	     }
	 }
	
	@GetMapping("/downloadDPRActivityPdf1")
	public ResponseEntity<byte[]> downloadPPRPdf1(HttpSession session,
	        @RequestParam(required = false) Integer project) {

	    if (session.getAttribute("userid") == null) {
	        return ResponseEntity.status(HttpStatus.FOUND)
	                .header(HttpHeaders.LOCATION, "/login").build();
	    }
	    if (project == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    try {
	        List<Object[]> nrm = plan.getDprNrmProjectPlanComplitedList(project);
	        List<Object[]> elp = plan.getDprELPProjectPlanCompletedList(project);
	        List<DprOtherDtl> oth = other.findCompletedOtherDtlByPprId(project);
	        
	        String districtName = "";
	        String projectName = "";

	        if (nrm != null && !nrm.isEmpty()) 
	        {
	            districtName = str(nrm.get(0)[3]);
	            projectName  = str(nrm.get(0)[2]);
	        } 
	        else if (elp != null && !elp.isEmpty()) 
	        {
	            districtName = str(elp.get(0)[3]);
	            projectName  = str(elp.get(0)[2]);
	        } 
	        else if (oth != null && !oth.isEmpty()) 
	        {
	            districtName = str(oth.get(0).getDprDetails().getPpr().getDistrict().getDistName());
	            projectName  = str(oth.get(0).getDprDetails().getPpr().getProjectName());
	        }

	        // ---------- DPR-1 rows ----------
	        List<String[]> r1 = new ArrayList<>();
	        Object prevId = null;
	        String prevHead = null;
	        if (nrm != null) 
	        {
	            for (Object[] row : nrm) 
	            {
	                boolean firstOfProject = prevId == null || !prevId.equals(row[0]);
	                String head = str(row[8]);
	                boolean sameHead = !firstOfProject && head.equals(prevHead);
	                String unit = str(row[11]);
	                r1.add(new String[] {
	                        firstOfProject ? str(row[3]) : "",
	                        firstOfProject ? str(row[2]) : "",
	                        sameHead ? "" : head,
	                        str(row[10]),
	                        unit,
	                        fmtTarget(row[13], unit) });
	                prevId = row[0];
	                prevHead = head;
	            }
	        }
	        // ---------- DPR-2 rows ----------
	        List<String[]> r2 = new ArrayList<>();
	        if (elp != null) 
	        {
	            for (Object[] row : elp) 
	            {
	                String code = str(row[6]);
	                String head = "L".equals(code) ? "Livelihood"
	                        : "P".equals(code) ? "Production"
	                        : "E".equals(code) ? "EPA" : code;
	                r2.add(new String[] { str(row[3]), str(row[2]), head,
	                        str(row[7]), str(row[10]), str(row[8]) });
	            }
	        }

	        // ---------- DPR-3 rows ----------
	        List<String[]> r3 = new ArrayList<>();
	        if (oth != null) {
	            for (DprOtherDtl d : oth) 
	            {
	                String head = "", act = "", unit = "";
	                if (d.getOtherActivity() != null) 
	                {
	                    head = str(d.getOtherActivity().getOtherHeads().getOtherHeadDesc());
	                    act = str(d.getOtherActivity().getOtherActivityDesc());
	                    unit = str(d.getOtherActivity().getUnit().getUnitDesc());
	                }
	                r3.add(new String[] {str(d.getDprDetails().getPpr().getDistrict().getDistName()),
	                        str(d.getDprDetails().getPpr().getProjectName()),
	                        head, act, unit, str(d.getQtyPlanned()) });
	            }
	        }

	        // ---------- Build PDF ----------
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Document doc = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
	        PdfWriter.getInstance(doc, out);
	        doc.open();

	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
	        Paragraph title = new Paragraph("Details of Project wise DPR Target", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        title.setSpacingAfter(12);
	        doc.add(title);
	        
	        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);

	        PdfPTable info = new PdfPTable(1);
	        info.setWidthPercentage(100);
	        info.setSpacingAfter(0);

	        PdfPCell infoCell = new PdfPCell(new Phrase(
	                "District : " + districtName + "          Project : " + projectName,
	                infoFont));
	        infoCell.setBackgroundColor(new Color(25, 135, 84));   // green
	        infoCell.setPadding(6);
	        infoCell.setBorder(0);

	        info.addCell(infoCell);
	        doc.add(info);

	        String[] heads = { "District", "Project", "Head", "Activity", "Unit", "Target" };
	        float[] widths = { 1.2f, 2.2f, 3f, 2.6f, 0.8f, 1.2f };

	        addSection(doc, "DPR-1 : Details of DPR NRM Target", heads, widths, r1, 0);
	        addSection(doc, "DPR-2 : Details of DPR EPA, Livelihood and Production Target", heads, widths, r2, 10);
	        addSection(doc, "DPR-3 : Details of DPR Other Activity Target", heads, widths, r3, 10);
	        doc.close();

	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=\"DPR_Targets_" + project + ".pdf\"")
	                .body(out.toByteArray());

	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	private void addSection(Document doc, String heading, String[] heads,
	        float[] widths, List<String[]> rows, float spaceBefore) throws Exception {

	    Font hFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
	    Font thFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
	    Font tdFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

	    PdfPTable bar = new PdfPTable(1);
	    bar.setWidthPercentage(100);
	    bar.setSpacingBefore(spaceBefore);
	    PdfPCell barCell = new PdfPCell(new Phrase(heading, hFont));
	    barCell.setBackgroundColor(new Color(13, 110, 253));
	    barCell.setPadding(5);
	    barCell.setBorder(0);
	    bar.addCell(barCell);
	    doc.add(bar);

	    PdfPTable t = new PdfPTable(widths);
	    t.setWidthPercentage(100);
	    t.setHeaderRows(1);

	    for (String h : heads) {
	        PdfPCell c = new PdfPCell(new Phrase(h, thFont));
	        c.setBackgroundColor(new Color(207, 226, 255));
	        c.setPadding(4);
	        t.addCell(c);
	    }

	    if (rows.isEmpty()) {
	        PdfPCell c = new PdfPCell(new Phrase("No records found.", tdFont));
	        c.setColspan(heads.length);
	        c.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c.setPadding(5);
	        t.addCell(c);
	    } 
	    else {
	        for (String[] r : rows) {
	            for (int i = 0; i < r.length; i++) {
	                PdfPCell c = new PdfPCell(new Phrase(r[i], tdFont));
	                c.setPadding(4);
	                if (i == r.length - 1) {
	                    c.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                }
	                t.addCell(c);
	            }
	        }
	    }
	    doc.add(t);
	}

	private String str(Object o) {
	    return o == null ? "" : o.toString().trim();
	}

	private String fmtTarget(Object o, String unit) {
	    if (o == null) return "";
	    if ("nos".equalsIgnoreCase(unit)) {
	        return new BigDecimal(o.toString()).setScale(0, RoundingMode.HALF_UP).toPlainString();
	    }
	    return o.toString();
	}

}
