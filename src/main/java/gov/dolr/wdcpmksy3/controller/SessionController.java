package gov.dolr.wdcpmksy3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {

    @GetMapping("/extendSession")
    @ResponseBody
    public String extendSession(HttpSession session) {

        System.out.println("========== EXTEND SESSION REQUEST ==========");

        System.out.println("Session ID: " + session.getId());

        System.out.println("User ID: " +
                session.getAttribute("userid"));

        if (session.getAttribute("userid") == null) {

            System.out.println("SESSION EXPIRED");

            return "expired";
        }

        session.setMaxInactiveInterval(30 * 60);

        System.out.println("SESSION EXTENDED SUCCESSFULLY");

        return "extended";
    }
}