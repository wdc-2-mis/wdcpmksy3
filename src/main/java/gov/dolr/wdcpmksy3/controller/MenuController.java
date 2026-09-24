package gov.dolr.wdcpmksy3.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.dto.RoleMenuList;
import gov.dolr.wdcpmksy3.entity.MRole;
import gov.dolr.wdcpmksy3.entity.WdcpmksyMenu;
import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;
import gov.dolr.wdcpmksy3.service.WdcpmksyMenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MenuController {
	
	@Autowired
	private WdcpmksyMenuService menuService;
	 
	 
	 
	@GetMapping("/newMenu")
    public String newMenu(HttpSession session, Model model) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
        WdcpmksySubmenu menu = new WdcpmksySubmenu();

        WdcpmksyMenu parent = new WdcpmksyMenu();

        menu.setMenu(parent);
        // Default = Parent
        menu.setIsParent(1);
        menu.setIsActive(true);

        model.addAttribute("menu", menu);

        List<MRole> listRole = menuService.getAllRole();
        model.addAttribute("listRole", listRole);

        List<WdcpmksyMenu> listMenu =menuService.getParentMenu();
        model.addAttribute("listMenu", listMenu);
        return "menuForm";
    }

	@PostMapping("/saveMenu") 
	public String saveMenu( @Valid @ModelAttribute("menu") WdcpmksySubmenu menu, BindingResult result, Model model, 
			HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) 
	{ 
		// Validation Errors  
		if (result.hasErrors()) 
		{ 
			model.addAttribute( "listRole", menuService.getAllRole()); 
			model.addAttribute( "listMenu", menuService.getParentMenu()); 
			return "menuForm"; 
		} 
		// Target Validation for Child 
		if (menu.getIsParent() != null && menu.getIsParent() == 0 && (menu.getTarget() == null || menu.getTarget().trim().isEmpty())) 
		{ 
			model.addAttribute( "listRole", menuService.getAllRole()); 
			model.addAttribute( "listMenu", menuService.getParentMenu()); 
			model.addAttribute( "error", "Target is mandatory for Child Menu"); 
			return "menuForm"; 
		} 
		// Parent Validation for Child  
		if (menu.getIsParent() != null && menu.getIsParent() == 0) 
		{ 
			if (menu.getMenu() == null || menu.getMenu().getMenuId() == null || menu.getMenu().getMenuId() == 0) 
			{ 
				model.addAttribute( "listRole", menuService.getAllRole()); 
				model.addAttribute( "listMenu", menuService.getParentMenu()); 
				model.addAttribute( "selectchild", "Please Select Parent for Child Menu"); 
				return "menuForm"; 
			} 
		} 
		// Session User  
		Object useridObj = session.getAttribute("userid"); 
		String userid = useridObj != null ? useridObj.toString() : "SUPERUSER"; 
		// Audit Information  
		menu.setLastUpdatedBy(userid); 
		menu.setRequestIp(request.getRemoteAddr()); 
		// ADD OR UPDATE  
		try { 
			if (menu.getSubmenuId() == null || menu.getSubmenuId() == 0) 
			{ 
				// New Menu  success
				menuService.addMenu(menu); 
				redirectAttributes.addFlashAttribute("success", "Menu Added Succefully.");
			} 
			else { 
				// Existing Menu  
				menuService.updateMenu(menu); 
				redirectAttributes.addFlashAttribute("success", "Menu Updated Succefully.");
			} 
			return "redirect:/newMenu"; 
			
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
			model.addAttribute( "error", e.getMessage()); 
			model.addAttribute( "listRole", menuService.getAllRole()); 
			model.addAttribute( "listMenu", menuService.getParentMenu()); 
			return "menuForm"; 
		} 
	}
	
	@GetMapping("/mainmenu")
    public String mainMenuPage(HttpSession session, Model model, @RequestParam(value = "msg", required = false) String msg) 
	{
		Integer stcode = Integer.parseInt(session.getAttribute("stcode").toString());
		String userid=(String)session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }

        List<MRole> listRole = menuService.getAllRole();

        LinkedHashMap<String, List<RoleMenuList>> listMenu = new LinkedHashMap<String, List<RoleMenuList>>();
		listMenu.putAll(menuService.getMenuAll());
		model.addAttribute("listMenu", listMenu);
        
        model.addAttribute("listRole", listRole);
        model.addAttribute("msg", msg);

        return "mainmenu";
    }
	
	@GetMapping("/fetchMenu")
	public String fetchallMenu(HttpSession session, Model model,
			@RequestParam(name = "role", defaultValue = "0") String role) {

		String userid=(String)session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
	    LinkedHashMap<String, List<RoleMenuList>> listMenu = new LinkedHashMap<>();

	    Integer roleId = Integer.parseInt(role);

	    if (roleId > 0 ) {
	        listMenu.putAll(menuService.getMenuAllRole(role));
	    } 
	    else {
	        listMenu.putAll(menuService.getMenuAll());
	    }

	    List<MRole> listRole = menuService.getAllRole();

	    model.addAttribute("listMenu", listMenu);
	    model.addAttribute("role", role);
	    model.addAttribute("listRole", listRole);

	    return "mainmenu";
	}
	
	@PostMapping("/deleteMenu")
	@ResponseBody
	public String deleteMenu(HttpSession session, Model model,
	        @RequestParam("menuId") Integer menuId) {
		
		String userid=(String)session.getAttribute("userid");
        if(userid==null){

            return "redirect:/login";
        }
		System.out.println("KDY"+menuId);
	    String msg = "";

	    msg = menuService.deleteSubMenu(menuId);

	   
	       // msg = menuService.deleteMenu(menuId);
	   

	    return msg;
	}
	
	@GetMapping("/editMenu")
	public String editMenu(HttpSession session, Model model,
	        @RequestParam(required = false) Integer menuId,
	        @RequestParam(required = false) Integer pmenuId) {

	    System.out.println("editMenu()");

	    WdcpmksySubmenu menu = new WdcpmksySubmenu();

	    // Edit Child Menu
	    if (menuId != null) {

	        System.out.println("editMenu() menuId = " + menuId);

	        menu = menuService.getMenu(menuId);

	        if (menu.getMenu() != null && menu.getMenu().getMenuId() == 0) {

	            menu.setIsParent(1);

	        } 
	        else {

	            menu.setIsParent(0);
	        }

	        System.out.println("editMenu() = " + menu.getSubmenuName());

	        menu.setMapRoleId(menuService.getMapRoleMenu(menu.getSubmenuId())
	        );
	    }

	    // Edit Parent Menu
	    else if (pmenuId != null) {

	        System.out.println("editMenu() pmenuId = " + pmenuId);

	        menu.setIsParent(1);

	        WdcpmksyMenu temp =
	                menuService.getParentMenu(pmenuId);

	        menu.setMenu(new WdcpmksyMenu());

	        menu.setSubmenuName(temp.getMenuName());

	        menu.setSubmenuHindiName(
	                temp.getMenuHindiName()
	        );
	        menu.setIsActive(temp.getIsActive());

	        menu.setSeqNo(temp.getHseqNo());

	        menu.setSubmenuId(pmenuId);

	        menu.setIsParent(1);
	    }

	    model.addAttribute("menu", menu);

	    List<MRole> listRole = menuService.getAllRole();
	    model.addAttribute("listRole", listRole);

	    List<WdcpmksyMenu> listMenu =menuService.getParentMenu();
	    model.addAttribute("listMenu", listMenu);

	    return "menuForm";
	}
	
}
