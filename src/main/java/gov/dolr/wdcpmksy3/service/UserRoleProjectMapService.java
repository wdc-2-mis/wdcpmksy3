package gov.dolr.wdcpmksy3.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.entity.MProject;
import gov.dolr.wdcpmksy3.entity.MState;
import gov.dolr.wdcpmksy3.entity.MRole;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserAppRoleMap;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserProjectMap;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.MDistrictRepository;
import gov.dolr.wdcpmksy3.repository.MProjectRepository;
import gov.dolr.wdcpmksy3.repository.MStateRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;
import gov.dolr.wdcpmksy3.repository.MRoleRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserAppRoleMapRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserProjectMapRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserRoleProjectMapService {

    private final UserRepository userRepository;
    private final MProjectRepository projectRepository;
    private final MRoleRepository roleRepository;
    private final WdcpmksyUserAppRoleMapRepository userRoleRepository;
    private final WdcpmksyUserProjectMapRepository userProjectRepository;
    private final MStateRepository stateRepository;
    private final MDistrictRepository districtRepository;
    private final PiaMailService piaMailService;
    


    public UserRoleProjectMapService(
            UserRepository userRepository,
            MProjectRepository projectRepository,
            MRoleRepository roleRepository,
            WdcpmksyUserAppRoleMapRepository userRoleRepository,
            WdcpmksyUserProjectMapRepository userProjectRepository,
            MStateRepository stateRepository,
            MDistrictRepository districtRepository,
            PiaMailService piaMailService) {

        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userProjectRepository = userProjectRepository;
        this.stateRepository = stateRepository;
        this.districtRepository = districtRepository;
        this.piaMailService = piaMailService;
    }



    public List<MState> getAllStates() {

        return stateRepository.findAllByOrderByStNameAsc();
    }


    public List<MDistrict> getDistrictsByState(Integer stCode) {

        if (stCode == null) {
            return List.of();
        }

        return districtRepository
                .findByState_StCodeOrderByDistNameAsc(stCode);
    }


    public List<MRole> getAllRoles() {

        return roleRepository.findAllByOrderByRoleNameAsc();
    }


    public List<MProject> getProjectsByDistrict(Integer dcode) {

        if (dcode == null) {
            return List.of();
        }

        return projectRepository
                .findByDistrict_DcodeOrderByProjNameAsc(dcode);
    }

    public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}

    @Transactional(readOnly = true)
    public List<WdcpmksyUserReg> getUsersForRoleMapping(
            Integer stCode,
            Integer dcode,
            String userType) {

        if (stCode == null) {
            return List.of();
        }

        if (userType == null || userType.trim().isEmpty()) {
            return List.of();
        }

        userType = userType.trim().toUpperCase();

        // PIA
        if ("PI".equals(userType)) {

            if (dcode == null) {
                return List.of();
            }

            return userRepository.findAvailablePiaUsers(
                    stCode,
                    dcode
            );
        }

        // DoLR
        if ("DL".equals(userType)) {
            return userRepository.findAvailableDoLRUsers();
        }

        // SLNA
        if ("SL".equals(userType)) {
            return userRepository.findAvailableSlnaUsers(stCode);
        }

        // WCDC - currently no WCDC user_type in your database
        if ("DI".equals(userType)) {
            return List.of();
        }

        // NGO - currently no NGO user_type in your database
        if ("NGO".equals(userType)) {
            return List.of();
        }

        // ALL
        if ("ALL".equals(userType)) {

            List<WdcpmksyUserReg> result = new ArrayList<>();

            result.addAll(
                    userRepository.findUsersByState(stCode, "DL")
            );

            result.addAll(
                    userRepository.findUsersByState(stCode, "SL")
            );

            if (dcode != null) {

                result.addAll(
                        userRepository.findAvailablePiaUsers(
                                stCode,
                                dcode
                        )
                );
            }

            return result;
        }

        return List.of();
    }


    @Transactional(readOnly = true)
    public Optional<WdcpmksyUserReg> getUserByRegId(
            Integer regId) {

        if (regId == null) {
            return Optional.empty();
        }

        return userRepository.findById(regId.longValue());
    }


    @Transactional(readOnly = true)
    public Optional<WdcpmksyUserAppRoleMap> getUserRole(
            Integer regId) {

        if (regId == null) {
            return Optional.empty();
        }

        return userRoleRepository.findByRegId(regId);
    }


    @Transactional(readOnly = true)
    public List<WdcpmksyUserProjectMap> getUserProjects(
            Integer regId) {

        if (regId == null) {
            return List.of();
        }

        return userProjectRepository.findByUser_RegId(regId);
    }


    @Transactional
    public String saveUserRole(
            Integer regId,
            Integer roleId,
            String updatedBy,
            String requestIp, HttpServletRequest servletRequest) {

        if (regId == null) {
            return "User is required.";
        }

        if (roleId == null) {
            return "Role is required.";
        }

        if (!userRepository.existsById(regId.longValue())) {
            return "User not found.";
        }

        Optional<MRole> roleOptional =
                roleRepository.findById(roleId);

        if (roleOptional.isEmpty()) {
            return "Selected role not found.";
        }

        WdcpmksyUserAppRoleMap userRole;

        Optional<WdcpmksyUserAppRoleMap> existing =
                userRoleRepository.findByRegId(regId);

        if (existing.isPresent()) {

            userRole = existing.get();

        } else {

            userRole = new WdcpmksyUserAppRoleMap();
            userRole.setRegId(regId);
        }

        userRole.setRole(roleOptional.get());
        userRole.setLastUpdatedBy(updatedBy);
        userRole.setLastUpdatedDate(LocalDate.now());
        userRole.setRequestIp(getClientIpAddr(servletRequest));

        userRoleRepository.save(userRole);

        return "Role assigned successfully.";
    }


    @Transactional
    public String saveUserProjects(
            Integer regId,
            List<Integer> projectIds,
            String updatedBy,
            String requestIp, HttpServletRequest servletRequest) {

        if (regId == null) {
            return "User is required.";
        }

        if (projectIds == null || projectIds.isEmpty()) {
            return "Please select at least one project.";
        }

        Optional<WdcpmksyUserReg> userOptional =
                userRepository.findById(regId.longValue());

        if (userOptional.isEmpty()) {
            return "User not found.";
        }

        WdcpmksyUserReg user = userOptional.get();

        for (Integer projectId : projectIds) {

            if (projectId == null) {
                continue;
            }

            Optional<MProject> projectOptional =
                    projectRepository.findById(projectId);

            if (projectOptional.isEmpty()) {
                return "Selected project not found.";
            }

            boolean alreadyAssigned =
                    userProjectRepository
                            .existsByUser_RegIdAndProject_ProjId(
                                    regId,
                                    projectId
                            );

            if (alreadyAssigned) {
                continue;
            }

            WdcpmksyUserProjectMap mapping =
                    new WdcpmksyUserProjectMap();

            mapping.setUser(user);
            mapping.setProject(projectOptional.get());

            mapping.setCreateDate(LocalDate.now());
            mapping.setCreateBy(updatedBy);
            mapping.setIpAddress(getClientIpAddr(servletRequest));

            mapping.setUpdateDate(LocalDate.now());
            mapping.setUpdatedBy(updatedBy);

            userProjectRepository.save(mapping);
        }

        return "Project(s) assigned successfully.";
    }



    @Transactional
    public String deleteUserProject(
            Integer regId,
            Integer projectId) {

        if (regId == null || projectId == null) {
            return "User and project are required.";
        }

        boolean exists =
                userProjectRepository
                        .existsByUser_RegIdAndProject_ProjId(
                                regId,
                                projectId
                        );

        if (!exists) {
            return "Project assignment not found.";
        }

        userProjectRepository
                .deleteByUser_RegIdAndProject_ProjId(
                        regId,
                        projectId
                );

        return "Project assignment deleted successfully.";
    }



    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDistrictsForRoleMapping(Integer stCode) {

        return districtRepository
                .findByState_StCodeOrderByDistNameAsc(stCode)
                .stream()
                .map(d -> {
                    Map<String, Object> map = new LinkedHashMap<>();

                    map.put("dcode", d.getDcode());
                    map.put("distName", d.getDistName());

                    return map;
                })
                .toList();
    }



    @Transactional
    public void saveUserRoleProjectMapping(
            Integer regId,
            Integer projId,
            Integer roleId,
            String userId,
            String loggedUserType,
            HttpServletRequest request) {

        if (regId == null) {

            throw new IllegalArgumentException(
                    "Please select a user."
            );
        }


       
        WdcpmksyUserReg user =
                userRepository.findById(regId.longValue())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User not found."
                                )
                        );


        MRole assignedRole = null;

        if (roleId != null) {

            assignedRole =
                    roleRepository.findById(roleId)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Role not found."
                                    )
                            );


            // SLNA can assign only PIA
            if ("SL".equalsIgnoreCase(loggedUserType)
                    && !"PIA".equalsIgnoreCase(
                            assignedRole.getRoleName().trim())) {

                throw new IllegalArgumentException(
                        "SLNA can assign only PIA role."
                );
            }


            Optional<WdcpmksyUserAppRoleMap> existing =
            		userRoleRepository
                            .findByRegId(regId);


            if (existing.isPresent()) {

                WdcpmksyUserAppRoleMap mapping =
                        existing.get();

                mapping.setRole(assignedRole);
                mapping.setLastUpdatedBy(userId);
                mapping.setLastUpdatedDate(
                        LocalDate.now()
                );
                mapping.setRequestIp(
                        request.getRemoteAddr()
                );

                userRoleRepository.save(mapping);

            } else {

                WdcpmksyUserAppRoleMap mapping =
                        new WdcpmksyUserAppRoleMap();

                mapping.setRegId(regId);
                mapping.setRole(assignedRole);
                mapping.setLastUpdatedBy(userId);
                mapping.setLastUpdatedDate(
                        LocalDate.now()
                );
                mapping.setRequestIp(
                        request.getRemoteAddr()
                );

                userRoleRepository.save(mapping);
            }
        }


        String projectName = null;

        if (projId != null) {

            MProject project =
                    projectRepository.findById(projId)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Project not found."
                                    )
                            );

            projectName =
                    project.getProjName();


            boolean alreadyMapped =
            		userProjectRepository
                            .existsByUser_RegIdAndProject_ProjId(
                                    regId,
                                    projId
                            );


            if (!alreadyMapped) {

                WdcpmksyUserProjectMap mapping =
                        new WdcpmksyUserProjectMap();

                mapping.setUser(user);
                mapping.setProject(project);

                LocalDate today =
                        LocalDate.now();

                mapping.setCreateDate(today);
                mapping.setCreateBy(userId);

                mapping.setUpdateDate(today);
                mapping.setUpdatedBy(userId);

                mapping.setIpAddress(
                        request.getRemoteAddr()
                );

                userProjectRepository.save(mapping);
            }
        }


        if (assignedRole != null) {

            String email =
                    user.getEmail();

            if (email != null
                    && !email.trim().isEmpty()) {

                boolean mailSent =
                        piaMailService
                                .sendRoleAssignmentMail(
                                        email,
                                        user.getUserName(),
                                        user.getUserId(),
                                        assignedRole
                                                .getRoleName(),
                                        projectName
                                );

                if (!mailSent) {

                    System.out.println(
                            "Role assigned successfully, "
                            + "but role assignment email "
                            + "could not be sent to "
                            + email
                    );
                }
            }
        }
    }
    
    
}