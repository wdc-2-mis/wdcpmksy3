package gov.dolr.wdcpmksy3.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.dto.LoginDTO;
import gov.dolr.wdcpmksy3.entity.MBlock;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserProjectMap;
import gov.dolr.wdcpmksy3.repository.MBlockRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyUserProjectMapRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectLocationController {

	@Autowired
	private WdcpmksyUserProjectMapRepository userProjectMapRepository;
	
	@Autowired
	private MBlockRepository blockRepository;
	
	@GetMapping("/piaPjtNotLocatiaon")
	public ModelAndView projectLocation(HttpServletRequest request) {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("regid") == null) {
	        return new ModelAndView("redirect:/login");
	    }

	    Integer regId = Integer.parseInt(session.getAttribute("regid").toString());

	    List<WdcpmksyUserProjectMap> projectMappings =
	            userProjectMapRepository.findByUser_RegId(regId);

	    List<MPpr> projectList = projectMappings.stream()
	            .map(WdcpmksyUserProjectMap::getProject)
	            .filter(Objects::nonNull)
	            .sorted(Comparator.comparing(
	                    MPpr::getProjectName,
	                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
	            .toList();

	    ModelAndView mav = new ModelAndView("project/projectLocation");

	    mav.addObject("projectList", projectList);
	    mav.addObject("username", session.getAttribute("username"));

	    return mav;
}
	
	
	@GetMapping("/getProjectBlocks")
	@ResponseBody
	public ResponseEntity<?> getProjectBlocks(
	        @RequestParam("pprId") Integer pprId,
	        HttpServletRequest request) {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("regid") == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    Integer regId = Integer.parseInt(
	            session.getAttribute("regid").toString());

	    // Check project assignment for logged-in PIA
	    boolean assigned = userProjectMapRepository
	            .existsByUser_RegIdAndProject_PprId(regId, pprId);

	    if (!assigned) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }

	    List<MBlock> blocks = blockRepository.findBlocksByProjectId(pprId);

	    List<Map<String, Object>> result = blocks.stream()
	            .map(b -> {
	                Map<String, Object> map = new HashMap<>();
	                map.put("bcode", b.getBcode());
	                map.put("blockName", b.getBlockName());
	                return map;
	            })
	            .toList();

	    return ResponseEntity.ok(result);
	}
	
}
