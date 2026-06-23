package gov.dolr.wdcpmksy3.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.dolr.wdcpmksy3.dto.ProjectPropose;

@Controller
public class ProjectProposeController {

    @GetMapping("/projectPropose")
    public String projectPropose(Model model) {

        model.addAttribute("project", new ProjectPropose());
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        String hash = encoder.encode("kdy123");
        System.out.println("kdy" +hash); // true
        
        boolean isValid = encoder.matches("kdy123", hash);
        System.out.println(isValid); // true


        return "projectPropose";
    }

    @PostMapping("/projectProposSave")
    public String saveProject(@ModelAttribute ProjectPropose project,
                              RedirectAttributes redirectAttributes) {

        // save draft

        redirectAttributes.addFlashAttribute("msg",
                "Draft Saved Successfully");

        return "redirect:/projectPropose";
    }

    @GetMapping("/pdf")
    public String pdfView() {

        return "projectPdf";
    }
    
    @GetMapping("/gisDetails")
    public String gisDetails() {
        return "gisDetails";
    }
}