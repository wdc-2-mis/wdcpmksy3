package gov.dolr.wdcpmksy3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {

	@GetMapping("/technicalsupport")
    public String technicalSupport() {
        return "technicalSupport";
    }
}
