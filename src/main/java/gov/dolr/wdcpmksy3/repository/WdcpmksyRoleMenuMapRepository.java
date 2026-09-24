package gov.dolr.wdcpmksy3.repository;

import gov.dolr.wdcpmksy3.dto.RoleMenuProjection;
import gov.dolr.wdcpmksy3.entity.WdcpmksyRoleMenuMap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WdcpmksyRoleMenuMapRepository extends JpaRepository<WdcpmksyRoleMenuMap, Integer> {
	
	 List<WdcpmksyRoleMenuMap> findAll();
	 
	 @Query(value = """
		        SELECT *
		        FROM (
		            SELECT
		                (
		                    SELECT role_name
		                    FROM wdcpmksy_app_role_map a
		                    WHERE a.role_id = b.role_id
		                ) AS rolename,

		                b.submenu_id AS menuid,
		                c.submenu_name AS menuname,
		                CAST(c.parent_id AS INTEGER) AS parentid,
		                m.menu_name AS parentname,
		                c.target AS target,

		                CAST(
		                    (
		                        SELECT COUNT(*)
		                        FROM wdcpmksy_user_app_role_map sa,
		                             wdcpmksy_role_menu_map sb,
		                             m_submenu sc
		                        WHERE sa.role_id = sb.role_id
		                          AND sb.submenu_id = sc.submenu_id
		                    ) AS INTEGER
		                ) AS countsub,

		                CAST(c.seq_no AS INTEGER) AS sequence,
		                CAST(m.hseq_no AS INTEGER) AS hseqno,

		                m.isactive AS pactive,
		                c.isactive AS cactive

		            FROM wdcpmksy_role_menu_map b,
		                 m_submenu c,
		                 m_menu m

		            WHERE m.menu_id = c.parent_id
		              AND b.submenu_id = c.submenu_id

		            UNION

		            SELECT
		                '' AS rolename,
		                NULL AS menuid,
		                NULL AS menuname,
		                m.menu_id AS parentid,
		                m.menu_name AS parentname,
		                '' AS target,
		                NULL AS sequence,
		                0 AS countsub,
		                CAST(hseq_no AS INTEGER) AS hseqno,
		                m.isactive AS pactive,
		                NULL AS cactive

		            FROM m_menu m

		            WHERE m.menu_id NOT IN (
		                SELECT parent_id
		                FROM m_submenu
		            )
		        ) AS a

		        ORDER BY rolename, parentname, sequence
		        """, nativeQuery = true)
		    List<RoleMenuProjection> getMenuAll();
	 
	 @Query(value = """
		        SELECT
		            r.role_name AS rolename,
		            b.submenu_id AS menuid,
		            c.submenu_name AS menuname,
		            CAST(c.parent_id AS INTEGER) AS parentid,
		            m.menu_name AS parentname,
		            c.target AS target,
		            CAST(c.seq_no AS INTEGER) AS sequence,

		            CAST(
		                (
		                    SELECT COUNT(*)
		                    FROM wdcpmksy_user_app_role_map sa,
		                         wdcpmksy_role_menu_map sb,
		                         m_submenu sc
		                    WHERE sa.role_id = sb.role_id
		                      AND sb.submenu_id = sc.submenu_id
		                ) AS INTEGER
		            ) AS countsub,

		            CAST(hseq_no AS INTEGER) AS hseqno,
		            m.isactive AS pactive,
		            c.isactive AS cactive

		        FROM wdcpmksy_role_menu_map b
		        JOIN m_submenu c
		            ON b.submenu_id = c.submenu_id
		        JOIN m_menu m
		            ON m.menu_id = c.parent_id
		        JOIN wdcpmksy_app_role_map r
		            ON r.role_id = b.role_id

		        WHERE b.role_id = :roleId

		        UNION

		        SELECT
		            '' AS rolename,
		            NULL AS menuid,
		            NULL AS menuname,
		            m.menu_id AS parentid,
		            m.menu_name AS parentname,
		            '' AS target,
		            0 AS sequence,
		            0 AS countsub,
		            CAST(hseq_no AS INTEGER) AS hseqno,
		            m.isactive AS pactive,
		            NULL AS cactive

		        FROM m_menu m

		        WHERE m.menu_id NOT IN (
		            SELECT parent_id
		            FROM m_submenu
		        )

		        ORDER BY rolename, parentname, sequence
		        """, nativeQuery = true)
		    List<RoleMenuProjection> getMenuAllRole(
		            @Param("roleId") Integer roleId);
	 
	 		@Query("""
		        SELECT r.role.roleId
		        FROM WdcpmksyRoleMenuMap r
		        WHERE r.submenu.submenuId = :submenuId
		        """)
		    List<Integer> findRoleIdsBySubmenuId(@Param("submenuId") Integer submenuId);
		
}