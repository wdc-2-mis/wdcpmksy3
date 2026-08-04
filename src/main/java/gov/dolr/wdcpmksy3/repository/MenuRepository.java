package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;

@Repository
public interface MenuRepository extends JpaRepository<WdcpmksySubmenu, Integer> {

    @Query(value = "select reg_id , b.submenu_id, submenu_name,  parent_id,(select MENU_NAME from m_menu where MENU_ID= sm.PARENT_ID) as parentname, "
    		+ "target from wdcpmksy_user_app_role_map a,wdcpmksy_role_menu_map b,m_menu c,m_submenu sm where  a.ROLE_ID=b.ROLE_ID and "
    		+ "b.submenu_id=sm.submenu_id and a.reg_id=:reg_id  and c.menu_id=sm.parent_id and c.isactive = TRUE and sm.isactive = TRUE"
    		+ " order by hseq_no, seq_no",
        nativeQuery = true)
    List<Object[]> getUserMenus(@Param("reg_id") int reg_id);
}