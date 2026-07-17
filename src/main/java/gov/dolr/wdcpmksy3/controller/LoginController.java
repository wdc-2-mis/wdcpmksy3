package gov.dolr.wdcpmksy3.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.dolr.wdcpmksy3.dto.LoginDTO;
import gov.dolr.wdcpmksy3.dto.MenuMap;
import gov.dolr.wdcpmksy3.dto.ProfileBean;
import gov.dolr.wdcpmksy3.entity.IwmpState;
import gov.dolr.wdcpmksy3.entity.IwmpUserReg;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import gov.dolr.wdcpmksy3.service.LoginServices;
import gov.dolr.wdcpmksy3.service.MenuService;
import gov.dolr.wdcpmksy3.service.OtpService;
import gov.dolr.wdcpmksy3.service.PasswordGenerator;
import gov.dolr.wdcpmksy3.service.ProfileService;
import gov.dolr.wdcpmksy3.service.StateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class LoginController {

    private final HomeController homeController;
	
	@Autowired
    private OtpService otpService;
	
	@Autowired
    private  LoginServices loginserv;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
    private StateService stateService;
	
	@Autowired
    private ProfileService profileService;
	
	@Autowired
    private PasswordGenerator pwdgen;
	
	private LinkedHashMap<Integer, String> stateList;


    LoginController(HomeController homeController) {
        this.homeController = homeController;
    }
	
	
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {

        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    @GetMapping("/success")
    public String success(HttpSession session, Model model) {

        Integer regid = (Integer) session.getAttribute("regid");

        if(regid == null){
            return "redirect:/login";
        }

        String userid = (String) session.getAttribute("userid");
        String usertype = (String) session.getAttribute("usertype");

        model.addAttribute("userList",
                otpService.getUserVerify(userid));

        model.addAttribute("listm",
                profileService.getMapState(regid, usertype));

        model.addAttribute("userType", usertype);

        return "success";
    }
    
    @PostMapping("/loginSuccess")
    public String authenticate(@ModelAttribute("login") LoginDTO login,  HttpSession session, HttpServletRequest request,
    		@RequestParam String userId, @RequestParam String encrypted_pass, Model model) {

    	
    	boolean valid =false; 
    	String logintype=login.getLoginMethod();
    	String otp=login.getOtp();
    	String email=login.getEmailid();
    	Integer regid =0;
    	String usertype =null;
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    	int i =1;

    	System.out.println("Login Session = " + session.getId());
    	
    	if ("otp".equalsIgnoreCase(logintype))
    	{
    		if (otpService.verifyOtp(userId, otp)) {
            	
            	session.setAttribute("useremail", email);
                session.setMaxInactiveInterval(30 * 60); 
            	model.addAttribute("email", email);
            	session.setAttribute("user", email);
            	model.addAttribute("timeoutSeconds", session.getMaxInactiveInterval());
            	
            	model.addAttribute("userList", otpService.getUserList(userId));
            	
            	List<Object[]> rows = otpService.getUserList(userId);

            	for (Object[] row : rows) {
            	    
            		regid = (Integer) row[12];
            	    String address = (String) row[2];
            	    String department = (String) row[3];
            	    String mobile = (String) row[6];
            	    String statename = (String) row[13];
            	    usertype = (String) row[1];
            	    String username = (String) row[0];
            	    String user_id = (String) row[8];
            	    Integer stCode = (Integer) row[9];
            	    model.addAttribute("username", username);
            	    session.setAttribute("regid", regid);
            	    session.setAttribute("username", username);
            	    session.setAttribute("usertype", usertype);
            	    session.setAttribute("stcode", stCode);
            	    session.setAttribute("statename", statename);
            	    session.setAttribute("mobile", mobile);
            	    session.setAttribute("userid", user_id);
            	    loginserv.insertloginlog(userId, "success", request);
            	    model.addAttribute("menus", menuService.getMenuUserId(regid));
            	  
            	}
            	//String userType = session.getAttribute("userType").toString();
            	LinkedHashMap<Integer, List<ProfileBean>> map = new LinkedHashMap<Integer, List<ProfileBean>>();
    			
            	List<IwmpState> lists = stateService.getAllStates(i);
            	LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();

            	for (IwmpState state : stateService.getAllStates(i)) {
            	    stateList.put(state.getStCode(), state.getStName());
            	}

				
				List<IwmpUserReg> list=new  ArrayList<IwmpUserReg>();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				
				list=profileService.getUserDetail(regid);
				listm=profileService.getMapState(regid, usertype);
				
				List<ProfileBean> sublist = new ArrayList<ProfileBean>();
				if(usertype.equals("ADMIN") || usertype.equals("DL") )
				{
					sublist = new ArrayList<ProfileBean>();
					ProfileBean profileBean = new ProfileBean();
					if ((listm != null) && (listm.size() > 0)) 
					{
						for (Map.Entry<Integer, String> entry : stateList.entrySet()) 
						{
							for (ProfileBean row : listm) 
							{
								profileBean = new ProfileBean();
					        	if(!map.containsKey(row.getStatecode()) && stateList.containsKey(row.getStatecode().toString())) 
					        	{
					        		profileBean.setSelected("selected");
					        		profileBean.setStatecode(row.getStatecode());
					        		
					        		profileBean.setStatename(stateList.get(row.getStatecode().toString()));
									sublist = new ArrayList<ProfileBean>();
									sublist.add(profileBean);
									map.put(row.getStatecode(), sublist);
									 //break second ;
								}
					        	else if(!map.containsKey(entry.getKey()) )
					        	{ 
										 sublist = new ArrayList<ProfileBean>();
										 profileBean.setSelected(" ");
										 profileBean.setStatecode(entry.getKey());
										 profileBean.setStatename(stateList.get(entry.getKey()));
										 sublist.add(profileBean);
										 map.put(entry.getKey(), sublist);
										// break second ;
								}
								else {
									  
								}				        	
					        }
					    }
					}
				}
				else if(usertype.equals("SL") || usertype.equals("DI") ) 
				{
					sublist = new ArrayList<ProfileBean>();
					for (ProfileBean row : listm) 
					{
						sublist.add(row);
						map.put(row.getStatecode(), sublist);
					}
				}
				else if(usertype.equalsIgnoreCase("PI")  ) 
				{
					sublist = new ArrayList<ProfileBean>();
					for (ProfileBean row : listm) 
					{
						sublist.add(row);
						map.put(row.getStatecode(), sublist);
					}
				}
            	
				model.addAttribute("listm", map);
				model.addAttribute("loginId", userId);
				model.addAttribute("stateList", stateList);
				model.addAttribute("regId",regid);
				model.addAttribute("userType",usertype);
				model.addAttribute("userId",userId);
				//model.addAttribute("sessionTimeout", 1800);
				model.addAttribute("sessionTimeout",session.getMaxInactiveInterval());
            	
                return "success";
            }
            
            else {
            	
            	
            	 loginserv.insertloginlog(userId, "Fail", request);
            	
            	model.addAttribute("error", "Invalid OTP / User is inActive");
            	return "login";
            }

    	}	
    	if ("pass".equalsIgnoreCase(logintype)) {
    		String userid=login.getUserId();
    		String planpwd=login.getEncrypted_pass();
    		boolean isValid =false;
    		boolean isValid1 =false;
			
    	//	String encrpwd=pwdgen.passwdgen(planpwd);
    	//	System.out.println("kdy = "+encrpwd);
    		
    		List<Object[]> rows = otpService.getUserVerify(userid);
    		if(rows.size()>0) {
    			for (Object[] row : rows) {
    				String encrpt = (String) row[14];
    				String encrpt1 = (String) row[15];
    				
    				 isValid = encoder.matches(planpwd, encrpt);
    				 isValid1 = encoder.matches(planpwd, encrpt1);
    				 
    				 System.out.println(isValid+" - "+isValid1);
    				
    			}
    		}	
    	
    	
    		if(isValid || isValid1) {
    			
    			loginserv.insertloginlog(userId, "success", request);
    			session.setMaxInactiveInterval(30 * 60); // 30 minutes
             	model.addAttribute("timeoutSeconds", session.getMaxInactiveInterval());
             	
     			for (Object[] row : rows) {
             	    
             		regid = (Integer) row[12];
             	    String address = (String) row[2];
             	    String department = (String) row[3];
             	    String mobile = (String) row[6];
             	    String statename = (String) row[13];
             	    usertype = (String) row[1];
             	    String username = (String) row[0];
             	    String user_id = (String) row[8];
             	    Integer stCode = (Integer) row[9];
             	    
             	    model.addAttribute("username", username);
             	    session.setAttribute("regid", regid);
             	    session.setAttribute("username", username);
             	    session.setAttribute("usertype", usertype);
             	    session.setAttribute("stcode", stCode);
             	    session.setAttribute("statename", statename);
             	    session.setAttribute("mobile", mobile);
             	    session.setAttribute("userid", user_id);
             	    
             	    loginserv.insertloginlog(userId, "success", request);
             	    model.addAttribute("menus", menuService.getMenuUserId(regid));
             	  
             	}
             	//String userType = session.getAttribute("userType").toString();
             	LinkedHashMap<Integer, List<ProfileBean>> map = new LinkedHashMap<Integer, List<ProfileBean>>();
     			
             	List<IwmpState> lists = stateService.getAllStates(i);
             	LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();

             	for (IwmpState state : stateService.getAllStates(i)) {
             	    stateList.put(state.getStCode(), state.getStName());
             	}

 				
 				List<IwmpUserReg> list=new  ArrayList<IwmpUserReg>();
 				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
 				
 				list=profileService.getUserDetail(regid);
 				listm=profileService.getMapState(regid, usertype);
 				
 				List<ProfileBean> sublist = new ArrayList<ProfileBean>();
 				if(usertype.equals("ADMIN") || usertype.equals("DL") )
 				{
 					sublist = new ArrayList<ProfileBean>();
 					ProfileBean profileBean = new ProfileBean();
 					if ((listm != null) && (listm.size() > 0)) 
 					{
 						for (Map.Entry<Integer, String> entry : stateList.entrySet()) 
 						{
 							for (ProfileBean row : listm) 
 							{
 								profileBean = new ProfileBean();
 					        	if(!map.containsKey(row.getStatecode()) && stateList.containsKey(row.getStatecode().toString())) 
 					        	{
 					        		profileBean.setSelected("selected");
 					        		profileBean.setStatecode(row.getStatecode());
 					        		
 					        		profileBean.setStatename(stateList.get(row.getStatecode().toString()));
 									sublist = new ArrayList<ProfileBean>();
 									sublist.add(profileBean);
 									map.put(row.getStatecode(), sublist);
 									 //break second ;
 								}
 					        	else if(!map.containsKey(entry.getKey()) )
 					        	{ 
 										 sublist = new ArrayList<ProfileBean>();
 										 profileBean.setSelected(" ");
 										 profileBean.setStatecode(entry.getKey());
 										 profileBean.setStatename(stateList.get(entry.getKey()));
 										 sublist.add(profileBean);
 										 map.put(entry.getKey(), sublist);
 										// break second ;
 								}
 								else {
 									  
 								}				        	
 					        }
 					    }
 					}
 				}
 				else if(usertype.equals("SL") || usertype.equals("DI") ) 
 				{
 					sublist = new ArrayList<ProfileBean>();
 					for (ProfileBean row : listm) 
 					{
 						sublist.add(row);
 						map.put(row.getStatecode(), sublist);
 					}
 				}
 				else if(usertype.equalsIgnoreCase("PI")  ) 
 				{
 					sublist = new ArrayList<ProfileBean>();
 					for (ProfileBean row : listm) 
 					{
 						sublist.add(row);
 						map.put(row.getStatecode(), sublist);
 					}
 				}
             	
 				model.addAttribute("listm", map);
 				model.addAttribute("loginId", userId);
 				model.addAttribute("stateList", stateList);
 				model.addAttribute("regId",regid);
 				model.addAttribute("userType",usertype);
 				model.addAttribute("userId",userId);
 				//model.addAttribute("sessionTimeout", 1800);
 				model.addAttribute("sessionTimeout",session.getMaxInactiveInterval());
     			model.addAttribute("userList", otpService.getUserVerify(userid));
     			
     			
     			
     			return "success";
    			
    			
    		}
    		else {
    			
    			 model.addAttribute("error", "Invalid Credential");
    			 loginserv.insertloginlog(userId, "Fail", request);
  	            return "login";
    		}
        	

            
        }
        else {
        	loginserv.insertloginlog(userId, "Fail", request);
	        model.addAttribute("error", "Invalid UserId or Password");
	        return "login";
        }
    }
	
    @GetMapping("/getEmailandGenerateotp")
    @ResponseBody
    public ResponseEntity<String> getEmailandGenerateotp(
            @RequestParam String value, HttpServletRequest request) {

        String email = loginserv.getEmailandGenerateotp(value);

        if(email == null || email.isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body("USER_NOT_FOUND");

        }

        otpService.sendOtp(email, request);

        return ResponseEntity.ok(email);

    }
    
    @GetMapping("/customLogout")
    public String logout(HttpServletRequest request) {
    	System.out.println("session value start");
        HttpSession session = request.getSession(false);
System.out.println("session value" +session);
        if (session != null) {
            System.out.println("Logout Session = " + session.getId());
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return "redirect:/login";
    }
    

    @PostMapping("/fetchMenu")
    public LinkedHashMap<String, List<MenuMap>> fetchMenu(
            HttpSession session) {

        String userId =(String) session.getAttribute("regid");

        if (userId == null) {
            return new LinkedHashMap<>();
        }

        return menuService.getMenuUserId(Integer.parseInt(userId));
    }
	
	

}