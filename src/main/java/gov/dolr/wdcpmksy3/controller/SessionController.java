package gov.dolr.wdcpmksy3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {

    @GetMapping("/extendSession")
    public ResponseEntity<String> extendSession(HttpSession session){

        session.setMaxInactiveInterval(30 * 60);

        return ResponseEntity.ok("extended");
    }

}
