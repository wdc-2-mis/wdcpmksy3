package gov.dolr.wdcpmksy3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {

   
    @GetMapping("/extendSession")
    @ResponseBody
    public String extendSession(HttpSession session) {

        if (session.getAttribute("userid") == null) {
            return "expired";
        }

        session.setMaxInactiveInterval(30 * 60);

        System.out.println(
            "Session Extended = " + session.getId()
        );

        return "extended";
    }

}
