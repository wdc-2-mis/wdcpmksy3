package gov.dolr.wdcpmksy3.PPR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Public (no-login) MIS reports listing: /reports?roleId=1 */
@Controller
public class PublicReportsController {

    @GetMapping("/reports")
    public String reports(@RequestParam(required = false) Integer roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "reports/misReports";
    }
}

