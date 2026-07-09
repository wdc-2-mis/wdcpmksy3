package gov.dolr.wdcpmksy3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import gov.dolr.wdcpmksy3.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private MenuService menuService;

    @ModelAttribute
    public void addCommonAttributes(HttpServletRequest request,
                                    HttpSession session,
                                    Model model) {

        Integer regid = (Integer) session.getAttribute("regid");

        model.addAttribute("currentUri", request.getRequestURI());

        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("stateName", session.getAttribute("statename"));
        model.addAttribute("timeoutSeconds", session.getMaxInactiveInterval());

        if (regid != null) {
            model.addAttribute("menus", menuService.getMenuUserId(regid));
        }
    }
}