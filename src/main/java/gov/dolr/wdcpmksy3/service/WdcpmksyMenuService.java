package gov.dolr.wdcpmksy3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.dto.RoleMenuList;
import gov.dolr.wdcpmksy3.dto.RoleMenuProjection;
import gov.dolr.wdcpmksy3.entity.MRole;
import gov.dolr.wdcpmksy3.entity.WdcpmksyMenu;
import gov.dolr.wdcpmksy3.entity.WdcpmksyRoleMenuMap;
import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;
import gov.dolr.wdcpmksy3.repository.MRoleRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyMenuRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksyRoleMenuMapRepository;
import gov.dolr.wdcpmksy3.repository.WdcpmksySubmenuRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class WdcpmksyMenuService {

	@Autowired
	private WdcpmksyMenuRepository menuRepository;
	@Autowired
	private WdcpmksySubmenuRepository submenuRepository;
	@Autowired
	private WdcpmksyRoleMenuMapRepository roleMenuMapRepository;
	@Autowired
	private MRoleRepository roleRepository;

    public WdcpmksyMenuService(
            WdcpmksyMenuRepository menuRepository,
            WdcpmksySubmenuRepository submenuRepository,
            WdcpmksyRoleMenuMapRepository roleMenuMapRepository,
            MRoleRepository roleRepository) {

        this.menuRepository = menuRepository;
        this.submenuRepository = submenuRepository;
        this.roleMenuMapRepository = roleMenuMapRepository;
        this.roleRepository = roleRepository;
    }


    //public List<WdcpmksyAppRoleMap> getAllRole() {
     //   return roleRepository.findAllByOrderByRoleNameAsc();
    //}


    //public List<WdcpmksyMenu> getParentMenu() {
      //  return menuRepository.findByIsActiveTrueOrderByHseqNoAsc();
    //}


    @Transactional
    public void saveMenu(WdcpmksySubmenu submenu, String userid, String requestIp) {

        LocalDate today = LocalDate.now();
        // Parent menu
        if (submenu.getIsParent() != null && submenu.getIsParent() == 1) 
        {
            submenu.setMenu(null);
            // Parent does not need roles
            submenu.setMapRoleId(null);
        }
        //Child menu
        else {

            if (submenu.getMenu() == null
                    || submenu.getMenu().getMenuId() == null
                    || submenu.getMenu().getMenuId() == 0) {

                throw new IllegalArgumentException("Please select Parent Menu.");
            }

            WdcpmksyMenu parent = menuRepository.findById(submenu.getMenu().getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Parent Menu."));
            submenu.setMenu(parent);
        }
        submenu.setLastUpdatedBy(userid);
        submenu.setLastUpdatedDate(today);
        submenu.setRequestIp(requestIp);

        WdcpmksySubmenu saved =submenuRepository.save(submenu);
        //Save role mappings only for Child menu
        if (submenu.getIsParent() != null && submenu.getIsParent() == 0 && submenu.getMapRoleId() != null) {

            List<WdcpmksyRoleMenuMap> mappings = new ArrayList<>();

            for (Integer roleId : submenu.getMapRoleId()) 
            {
                if (roleId == null) {
                    continue;
                }

                MRole role = roleRepository.findById(roleId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Invalid Role."));

                WdcpmksyRoleMenuMap map = new WdcpmksyRoleMenuMap();

                map.setRole(role);
                map.setSubmenu(saved);
                map.setLastUpdatedBy(userid);
                map.setLastUpdatedDate(today);
                map.setRequestIp(requestIp);

                mappings.add(map);
            }
            roleMenuMapRepository.saveAll(mappings);
        }
    }
    
    
    // ========== // Get Roles // =====================
    public List<MRole> getAllRole() 
    { 
    	return roleRepository.findAllByOrderByRoleNameAsc(); 
    } 
    // =========== // Get Parent Menus // =================
    public List<WdcpmksyMenu> getParentMenu() 
    { 
    	return menuRepository.findByIsActiveTrueOrderByHseqNoAsc(); 
    } 
    // ============= // ADD MENU // =======================
    
    @Transactional 
    public void addMenu(WdcpmksySubmenu menu) 
    { 
    	LocalDate today = LocalDate.now(); 
    	//  PARENT MENU 
    	if (menu.getIsParent() != null && menu.getIsParent() == 1) 
    	{ 
			if (menuRepository.existsByHseqNo(menu.getSeqNo())) {
				throw new IllegalArgumentException("Sequence No " + menu.getSeqNo() + " already exists.");
			}
    		WdcpmksyMenu parent = new WdcpmksyMenu(); 
    		parent.setMenuName( menu.getSubmenuName()); 
    		parent.setMenuHindiName( menu.getSubmenuHindiName()); 
    		parent.setHseqNo( menu.getSeqNo()); 
    		parent.setIsActive( menu.getIsActive()); 
    		parent.setLastUpdatedBy( menu.getLastUpdatedBy()); 
    		parent.setLastUpdatedDate( today); 
    		parent.setRequestIp( menu.getRequestIp()); 
    		menuRepository.save(parent); 
    		return; 
    	} 
    	// CHILD MENU 
    	/*if (submenuRepository.existsBySeqNoAndSubmenuIdNot(
    	        menu.getSeqNo(), menu.getSubmenuId())) {

    	    throw new IllegalArgumentException("Sequence No " + menu.getSeqNo() + " already exists."
    	    );
    	} */
    	menu.setIsActive(true); 
    	menu.setLastUpdatedDate(today); 
    	// Get Parent Menu 
    	if (menu.getMenu() == null || menu.getMenu().getMenuId() == null || menu.getMenu().getMenuId() == 0) 
    	{ 
    		throw new IllegalArgumentException( "Please Select Parent for Child Menu"); 
    	} 
    	WdcpmksyMenu parent = menuRepository.findById( menu.getMenu().getMenuId())
    			.orElseThrow(() -> new IllegalArgumentException( "Invalid Parent Menu")); 
    	menu.setMenu(parent); 
    	/* * Save Child Menu */ 
    	WdcpmksySubmenu savedMenu = submenuRepository.save(menu); 
    	/* * Save Role Mapping */ 
    	int[] roleIds = menu.getMapRoleId(); 
    	if (roleIds != null) 
    	{ 
    		List<WdcpmksyRoleMenuMap> mappings = new ArrayList<>(); 
    		for (Integer roleId : roleIds) 
    		{ 
    			if (roleId == null) 
    			{ 
    				continue; 
    			} 
    			MRole role = roleRepository.findById(roleId)
    					.orElseThrow(() -> new IllegalArgumentException( "Invalid Role ID: " + roleId)); 
    			WdcpmksyRoleMenuMap roleMenuMap = new WdcpmksyRoleMenuMap(); 
    			roleMenuMap.setRole(role); 
    			roleMenuMap.setSubmenu(savedMenu); 
    			roleMenuMap.setLastUpdatedBy( menu.getLastUpdatedBy()); 
    			roleMenuMap.setLastUpdatedDate( today); 
    			roleMenuMap.setRequestIp( menu.getRequestIp()); 
    			mappings.add(roleMenuMap); 
    		} 
    		roleMenuMapRepository.saveAll(mappings); 
    	} 
    } 
    // ================== UPDATE MENU // ===================
    @Transactional 
    public void updateMenu(WdcpmksySubmenu menu) 
    { 
    	LocalDate today = LocalDate.now(); 
    	// UPDATE PARENT MENU  
    	if (menu.getIsParent() != null && menu.getIsParent() == 1) 
    	{ 
    		// For Parent Menu, submenuId is being used * as the parent menu ID when editing. 
    		Integer menuId = menu.getSubmenuId(); 
    		WdcpmksyMenu parent = menuRepository.findById(menuId) 
    				.orElseThrow(() -> new IllegalArgumentException( "Parent Menu not found")); 
    		parent.setMenuName( menu.getSubmenuName()); 
    		parent.setMenuHindiName( menu.getSubmenuHindiName()); 
    		parent.setHseqNo( menu.getSeqNo()); 
    		parent.setIsActive( menu.getIsActive()); 
    		parent.setLastUpdatedBy( menu.getLastUpdatedBy()); 
    		parent.setLastUpdatedDate( today); 
    		parent.setRequestIp( menu.getRequestIp()); 
    		menuRepository.save(parent);
    		return; 
    	} 
    	// UPDATE CHILD MENU  
    	Integer submenuId = menu.getSubmenuId(); 
    	WdcpmksySubmenu existing = submenuRepository.findById(submenuId) 
    			.orElseThrow(() -> new IllegalArgumentException( "Submenu not found")); 
    	// Delete old role mappings  
    	roleMenuMapRepository.deleteAll(existing.getRoleMenuMappings()); 
    	// Get new Parent 
    	if (menu.getMenu() == null || menu.getMenu().getMenuId() == null || menu.getMenu().getMenuId() == 0) 
    	{ 
    		throw new IllegalArgumentException( "Please Select Parent for Child Menu"); 
    	} 
    	WdcpmksyMenu parent = menuRepository.findById( menu.getMenu().getMenuId()) 
    			.orElseThrow(() -> new IllegalArgumentException( "Invalid Parent Menu")); 
    	// Update existing child  
    	existing.setMenu(parent); 
    	existing.setSubmenuName( menu.getSubmenuName()); 
    	existing.setSubmenuHindiName( menu.getSubmenuHindiName()); 
    	existing.setTarget( menu.getTarget()); 
    	existing.setSeqNo( menu.getSeqNo()); 
    	existing.setIsActive( menu.getIsActive()); 
    	existing.setLastUpdatedBy( menu.getLastUpdatedBy()); 
    	existing.setLastUpdatedDate( today); 
    	existing.setRequestIp( menu.getRequestIp()); 
    	WdcpmksySubmenu saved = submenuRepository.save(existing); 
    	
    	// SAVE NEW ROLE MAPPINGS  
    	int[] roleIds = menu.getMapRoleId(); 
    	if (roleIds != null) 
    	{ 
    		List<WdcpmksyRoleMenuMap> mappings = new ArrayList<>(); 
    		for (Integer roleId : roleIds) 
    		{ 
    			if (roleId == null) 
    			{ 
    				continue; 
    			} 
    			MRole role = roleRepository.findById(roleId) 
    					.orElseThrow(() -> new IllegalArgumentException( "Invalid Role ID: " + roleId)); 
    			WdcpmksyRoleMenuMap roleMenuMap = new WdcpmksyRoleMenuMap(); 
    			roleMenuMap.setRole(role); 
    			roleMenuMap.setSubmenu(saved); 
    			roleMenuMap.setLastUpdatedBy( menu.getLastUpdatedBy()); 
    			roleMenuMap.setLastUpdatedDate( today); 
    			roleMenuMap.setRequestIp( menu.getRequestIp()); 
    			mappings.add(roleMenuMap); 
    		} 
    		roleMenuMapRepository.saveAll(mappings); 
    	} 
    }
    
    @Transactional(readOnly = true)
    public LinkedHashMap<String, List<RoleMenuList>> getMenuAll() {

        LinkedHashMap<String, List<RoleMenuList>> userMenu =new LinkedHashMap<>();

        List<RoleMenuProjection> list =roleMenuMapRepository.getMenuAll();

        if (list != null && !list.isEmpty()) {

            for (RoleMenuProjection row : list) {

                RoleMenuList menu = new RoleMenuList();

                menu.setRolename(row.getRolename());
                menu.setMenuid(row.getMenuid());
                menu.setMenuname(row.getMenuname());
                menu.setParentid(row.getParentid());
                menu.setParentname(row.getParentname());
                menu.setTarget(row.getTarget());
                menu.setSequence(row.getSequence());
                menu.setCountsub(row.getCountsub());
                menu.setHseqno(row.getHseqno());
                menu.setPactive(row.getPactive());
                menu.setCactive(row.getCactive());

                String key =
                        row.getRolename()
                        + ","
                        + row.getParentname()
                        + ":::"
                        + row.getParentid()
                        + "$"
                        + row.getHseqno()
                        + "@"
                        + row.getPactive();

                userMenu
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(menu);
            }
        }

        return userMenu;
    }
    
    @Transactional(readOnly = true)
    public LinkedHashMap<String, List<RoleMenuList>> getMenuAllRole(String role) {

        Integer roleId = Integer.parseInt(role);

        List<RoleMenuProjection> result =roleMenuMapRepository.getMenuAllRole(roleId);

        LinkedHashMap<String, List<RoleMenuList>> userMenu =
                new LinkedHashMap<>();

        for (RoleMenuProjection row : result) {

            RoleMenuList menu = new RoleMenuList();

            menu.setRolename(row.getRolename());
            menu.setMenuid(row.getMenuid());
            menu.setMenuname(row.getMenuname());
            menu.setParentid(row.getParentid());
            menu.setParentname(row.getParentname());
            menu.setTarget(row.getTarget());
            menu.setSequence(row.getSequence());
            menu.setCountsub(row.getCountsub());
            menu.setHseqno(row.getHseqno());
            menu.setPactive(row.getPactive());
            menu.setCactive(row.getCactive());

            String key = row.getRolename()
                    + ","
                    + row.getParentname()
                    + ":::"
                    + row.getParentid()
                    + "$"
                    + row.getHseqno()
                    + "@"
                    + row.getPactive();

            userMenu
                .computeIfAbsent(key, k -> new ArrayList<>())
                .add(menu);
        }

        return userMenu;
    }
    
    @Transactional
    public String deleteSubMenu(int menuId) {

        try {

            if (submenuRepository.existsById(menuId)) {

            	submenuRepository.deleteById(menuId);

                return "success";
            }

            return "fail";

        } catch (Exception ex) {

            ex.printStackTrace();

            return "fail";
        }
    }
    
    public WdcpmksySubmenu getMenu(int menuId) {

        return submenuRepository.findById(menuId).orElse(null);
    }
    
    public WdcpmksyMenu getParentMenu(int menuId) {

        return menuRepository.findById(menuId).orElse(null);
    }
    
    public int[] getMapRoleMenu(int submenuId) {

        List<Integer> roleIds =
                roleMenuMapRepository.findRoleIdsBySubmenuId(submenuId);

        return roleIds.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

}