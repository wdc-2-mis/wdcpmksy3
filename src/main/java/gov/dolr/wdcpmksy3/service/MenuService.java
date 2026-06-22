package gov.dolr.wdcpmksy3.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.dto.MenuMap;
import gov.dolr.wdcpmksy3.repository.MenuRepository;

@Service
public class MenuService {
	
	@Autowired
	MenuRepository menuRepository;

	public LinkedHashMap<String, List<MenuMap>> getMenuUserId(int regId){
		
		List<Object[]> rows = menuRepository.getUserMenus(regId);

        LinkedHashMap<String, List<MenuMap>> menuMap = new LinkedHashMap<>();

        for (Object[] row : rows) {

            MenuMap dto = new MenuMap();

            dto.setReg_id((int) row[0]);
            dto.setSubmenu_id(((Number) row[1]).intValue());
            dto.setSubmenu_name((String) row[2]);
            dto.setParent_id(((Number) row[3]).intValue());
            dto.setParentname((String) row[4]);
            dto.setTarget((String) row[5]);

            menuMap.computeIfAbsent(dto.getParentname(),k -> new ArrayList<>()).add(dto);
        }

        return menuMap;
	}
}