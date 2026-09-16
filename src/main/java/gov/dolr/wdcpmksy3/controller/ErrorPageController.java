package gov.dolr.wdcpmksy3.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
public class ErrorPageController implements ErrorController {

    // Manual test route — hits the same template directly, useful for design checks
    @GetMapping("/test404")
    public String test404(Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", "Page Not Found");
        model.addAttribute("path", "/test404");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error"; // -> templates/error.html
    }

    // Real error handler — Spring Boot forwards ALL uncaught errors/exceptions here
    // (401, 403, 404, 500, etc.), regardless of HTTP method (GET, POST, ...)
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object messageObj = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object pathObj = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object exceptionObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        int code = 500;
        if (statusObj != null) {
            code = Integer.parseInt(statusObj.toString());
        }

        String errorLabel = switch (code) {
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Access Denied";
            case 404 -> "Not Found";
            case 405 -> "Method Not Allowed";
            case 408 -> "Request Timeout";
            case 429 -> "Too Many Requests";
            default -> "Internal Server Error";
        };

        model.addAttribute("status", code);
        model.addAttribute("error", errorLabel);
        model.addAttribute("message", (messageObj != null && !messageObj.toString().isBlank())
                ? messageObj.toString() : null);
        model.addAttribute("path", pathObj != null ? pathObj.toString() : null);
        model.addAttribute("timestamp", LocalDateTime.now());

        // resolves to src/main/resources/templates/error.html
        return "error";
    }
}