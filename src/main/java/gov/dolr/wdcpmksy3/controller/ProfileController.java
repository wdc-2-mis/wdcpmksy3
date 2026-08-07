package gov.dolr.wdcpmksy3.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gov.dolr.wdcpmksy3.dto.ProfileBean;
import gov.dolr.wdcpmksy3.entity.MState;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.service.MenuService;
import gov.dolr.wdcpmksy3.service.OtpService;
import gov.dolr.wdcpmksy3.service.ProfileService;
import gov.dolr.wdcpmksy3.service.StateService;
import jakarta.servlet.http.HttpSession;


@Controller
public class ProfileController {

	@Autowired
    private OtpService otpService;
	
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
    private StateService stateService;
	
	@Autowired
    private ProfileService profileService;
	
	@GetMapping("/profile")
    public String profile(HttpSession session, Model model) {

        Integer regid = (Integer) session.getAttribute("regid");
        String usertype = (String) session.getAttribute("usertype");
        String userid = (String) session.getAttribute("userid");

        if(regid == null){
            return "redirect:/login";
        }

        loadProfileData(regid, userid, usertype, session, model);

        return "success";
    }
	
    private void loadProfileData(Integer regid,
            String userid,
            String usertype,
            HttpSession session,
            Model model) {

int i = 1;

// User Details
model.addAttribute("userList", otpService.getUserVerify(userid));

// Menus
model.addAttribute("menus", menuService.getMenuUserId(regid));

// States
LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();

for (MState state : stateService.getAllStates(i)) {
stateList.put(state.getStCode(), state.getStName());
}

List<WdcpmksyUserReg> list = profileService.getUserDetail(regid);
List<ProfileBean> listm = profileService.getMapState(regid, usertype);

LinkedHashMap<Integer, List<ProfileBean>> map = new LinkedHashMap<>();
List<ProfileBean> sublist = new ArrayList<>();

if ("ADMIN".equals(usertype) || "DL".equals(usertype)) {

ProfileBean profileBean;

if (listm != null && !listm.isEmpty()) {

for (Map.Entry<Integer, String> entry : stateList.entrySet()) {

for (ProfileBean row : listm) {

   profileBean = new ProfileBean();

   if (!map.containsKey(row.getStatecode())) {

       profileBean.setSelected("selected");
       profileBean.setStatecode(row.getStatecode());
       profileBean.setStatename(stateList.get(row.getStatecode()));

       sublist = new ArrayList<>();
       sublist.add(profileBean);

       map.put(row.getStatecode(), sublist);
   }
   else if (!map.containsKey(entry.getKey())) {

       profileBean.setSelected("");
       profileBean.setStatecode(entry.getKey());
       profileBean.setStatename(entry.getValue());

       sublist = new ArrayList<>();
       sublist.add(profileBean);

       map.put(entry.getKey(), sublist);
   }
}
}
}
}
else {

sublist = new ArrayList<>();

for (ProfileBean row : listm) {
sublist.add(row);
map.put(row.getStatecode(), sublist);
}
}

model.addAttribute("listm", map);
model.addAttribute("stateList", stateList);
model.addAttribute("regId", regid);
model.addAttribute("userType", usertype);
model.addAttribute("userId", userid);
model.addAttribute("sessionTimeout", session.getMaxInactiveInterval());
}
    
    @GetMapping("/changePassword")
    public String changePassword(HttpSession session, Model model) {

        String username = (String) session.getAttribute("username");

        if(username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);

        return "changePassword";
    }
}
