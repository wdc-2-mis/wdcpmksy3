package gov.dolr.wdcpmksy3.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.entity.MProject;
import gov.dolr.wdcpmksy3.entity.MState;
import gov.dolr.wdcpmksy3.entity.MRole;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserProjectMap;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserAppRoleMap;
import gov.dolr.wdcpmksy3.service.UserRoleProjectMapService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserRoleProjectMapController {

    private final UserRoleProjectMapService service;

    public UserRoleProjectMapController(
            UserRoleProjectMapService service) {
        this.service = service;
    }

    @GetMapping("/userRoleMap")
    public String userRoleMap(HttpSession session, Model model) {

        String loggedUserType = "";

        Object userTypeObj = session.getAttribute("usertype");

        if (userTypeObj != null) {
            loggedUserType = userTypeObj.toString();
        }

        Map<String, String> userTypeList = new LinkedHashMap<>();

        if ("SL".equalsIgnoreCase(loggedUserType)) {

            // SLNA can work only with PIA users
            userTypeList.put("PI", "PIA");

        } else if ("ADMIN".equalsIgnoreCase(loggedUserType)) {

            // Superuser can work with all user types
            userTypeList.put("ALL", "All");
            userTypeList.put("DL", "DoLR");
            userTypeList.put("SL", "SLNA");
            userTypeList.put("DI", "WCDC");
            userTypeList.put("PI", "PIA");
            userTypeList.put("NGO", "NGO");
        }

        Integer sessionStateCode = null;

        Object stCodeObj = session.getAttribute("stcode");

        if (stCodeObj != null) {
            try {
                sessionStateCode = Integer.valueOf(stCodeObj.toString());
            } catch (NumberFormatException ignored) {
            }
        }

        model.addAttribute("states", service.getAllStates());
        model.addAttribute("roles", service.getAllRoles());

        model.addAttribute("userTypeList", userTypeList);
        model.addAttribute("loggedInUserType", loggedUserType);
        model.addAttribute("sessionStateCode", sessionStateCode);

        return "user/userRoleMap";
    }

    @GetMapping("/userRoleMap/districts")
    @ResponseBody
    public ResponseEntity<?> getDistricts(
            @RequestParam Integer stCode) {

        List<Map<String, Object>> districts =
                service.getDistrictsForRoleMapping(stCode);

        return ResponseEntity.ok(districts);
    }

    @GetMapping("/userRoleMap/users")
    @ResponseBody
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) Integer stCode,
            @RequestParam(required = false) Integer dcode,
            @RequestParam String userType) {

        List<WdcpmksyUserReg> users =
                service.getUsersForRoleMapping(
                        stCode,
                        dcode,
                        userType
                );

        List<Map<String, Object>> result =
                users.stream()
                        .map(u -> {
                            Map<String, Object> map =
                                    new LinkedHashMap<>();

                            map.put("regId", u.getRegId());
                            map.put("userId", u.getUserId());
                            map.put("userName", u.getUserName());

                            return map;
                        })
                        .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/userRoleMap/projects")
    @ResponseBody
    public ResponseEntity<?> getProjects(@RequestParam Integer dcode) {

        List<MProject> projects =
                service.getProjectsByDistrict(dcode);

        List<Map<String, Object>> result = projects.stream()
                .map(project -> {

                    Map<String, Object> map =
                            new LinkedHashMap<>();

                    map.put("projId", project.getProjId());
                    map.put("projName", project.getProjName());
                    map.put("projectCd", project.getProjectCd());

                    return map;
                })
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/userRoleMap/roles")
    @ResponseBody
    public List<MRole> getRoles() {

        return service.getAllRoles();
    }

	/*
	 * @GetMapping("/userRoleMap/user/{regId}")
	 * 
	 * @ResponseBody public ResponseEntity<?> getUser(
	 * 
	 * @PathVariable Integer regId) {
	 * 
	 * return service.getUserByRegId(regId) .map(user -> ResponseEntity.ok(user))
	 * .orElseGet( () -> ResponseEntity.notFound().build() ); }
	 */

  @GetMapping("/userRoleMap/user/{regId}/role")
    @ResponseBody
    public ResponseEntity<?> getUserRole(
            @PathVariable Integer regId) {

        Optional<WdcpmksyUserAppRoleMap> mapping =
                service.getUserRole(regId);

        if (mapping.isPresent()) {
            return ResponseEntity.ok(mapping.get());
        }

        return ResponseEntity.ok(Map.of());
    }
  @GetMapping("/userRoleMap/user/{regId}/projects")
  @ResponseBody
  public ResponseEntity<?> getUserProjects(
          @PathVariable Integer regId) {

      List<WdcpmksyUserProjectMap> mappings =
              service.getUserProjects(regId);

      List<Map<String, Object>> result =
              mappings.stream()
                      .map(mapping -> {

                          Map<String, Object> map =
                                  new LinkedHashMap<>();

                          MProject project =
                                  mapping.getProject();

                          Map<String, Object> projectMap =
                                  new LinkedHashMap<>();

                          projectMap.put(
                                  "projId",
                                  project.getProjId()
                          );

                          projectMap.put(
                                  "projName",
                                  project.getProjName()
                          );

                          projectMap.put(
                                  "projectCd",
                                  project.getProjectCd()
                          );

                          map.put(
                                  "project",
                                  projectMap
                          );

                          map.put(
                                  "upMapId",
                                  mapping.getUpMapId()
                          );

                          return map;
                      })
                      .toList();

      return ResponseEntity.ok(result);
  }

  @PostMapping("/userRoleMap/saveRole")
    @ResponseBody
    public ResponseEntity<?> saveRole(
            @RequestParam Integer regId,
            @RequestParam Integer roleId,
            HttpServletRequest request,
            HttpSession session) {

        String updatedBy =
                session.getAttribute("userid") != null
                        ? session.getAttribute("userid").toString()
                        : "SYSTEM";

        String requestIp =
                request.getRemoteAddr();

        String message =
                service.saveUserRole(
                        regId,
                        roleId,
                        updatedBy,
                        requestIp, request
                );

        return ResponseEntity.ok(
                Map.of(
                        "success", message.contains("successfully"),
                        "message", message
                )
        );
    }
   @PostMapping("/userRoleMap/saveProjects")
    @ResponseBody
    public ResponseEntity<?> saveProjects(
            @RequestParam Integer regId,
            @RequestParam List<Integer> projectIds,
            HttpServletRequest request,
            HttpSession session) {

        String updatedBy =
                session.getAttribute("userid") != null
                        ? session.getAttribute("userid").toString()
                        : "SYSTEM";

        String requestIp =
                request.getRemoteAddr();

        String message =
                service.saveUserProjects(
                        regId,
                        projectIds,
                        updatedBy,
                        requestIp, request
                );

        return ResponseEntity.ok(
                Map.of(
                        "success", message.contains("successfully"),
                        "message", message
                )
        );
    }

  @PostMapping("/userRoleMap/deleteProject")
    @ResponseBody
    public ResponseEntity<?> deleteProject(
            @RequestParam Integer regId,
            @RequestParam Integer projectId) {

        String message =
                service.deleteUserProject(
                        regId,
                        projectId
                );

        return ResponseEntity.ok(
                Map.of(
                        "success",
                        message.contains("successfully"),

                        "message",
                        message
                )
        );
    }
  
  @PostMapping("/userRoleMap/save")
  @ResponseBody
  public ResponseEntity<?> saveUserRoleProjectMapping(
          @RequestParam Integer regId,
          @RequestParam(required = false) Integer projId,
          @RequestParam(required = false) Integer roleId,
          HttpSession session,
          HttpServletRequest request) {

      try {

          String userId =
                  String.valueOf(
                          session.getAttribute("userid")
                  );

          String loggedUserType =
                  String.valueOf(
                          session.getAttribute("usertype")
                  );


          service.saveUserRoleProjectMapping(
                  regId,
                  projId,
                  roleId,
                  userId,
                  loggedUserType,
                  request
          );


          return ResponseEntity.ok(
                  Map.of(
                          "success", true,
                          "message",
                          "Mapping saved successfully"
                  )
          );

      } catch (Exception e) {

          e.printStackTrace();

          return ResponseEntity
                  .badRequest()
                  .body(
                          Map.of(
                                  "success", false,
                                  "message",
                                  e.getMessage()
                          )
                  );
      }
  }
}