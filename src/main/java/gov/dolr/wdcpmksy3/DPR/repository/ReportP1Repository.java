package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.dto.CodeName;
import gov.dolr.wdcpmksy3.dto.ReportP1Row;
import gov.dolr.wdcpmksy3.dto.ReportP1StateSummary;

/**
 * m_ppr.dcode -> m_district.dcode ; m_district.st_code -> m_state.st_code
 * m_ppr.fin_yr_cd -> m_fin_year.fin_yr_cd (label fin_yr_desc)
 * Approved = latest ppr_transaction row of the project has action = 'A'.
 */
public interface ReportP1Repository extends JpaRepository<MPpr, Integer> {

    @Query(value = """
        SELECT st.st_name           AS "stateName",
               d.dist_name          AS "districtName",
               fy.fin_yr_desc       AS "finYear",
               p.project_name       AS "projectName",
               s.sanction_date      AS "sanctionDate",
               s.area_proposed      AS "areaSanctioned",
               s.tot_cost           AS "totCost",
               s.central_share_amt  AS "centralShare",
               s.state_share_amt    AS "stateShare"
        FROM public.m_ppr p
        JOIN public.m_sanction s
              ON s.sanction_id = (SELECT MAX(s2.sanction_id) FROM public.m_sanction s2
                                  WHERE s2.ppr_id = p.ppr_id)
        JOIN public.m_district d ON d.dcode = p.dcode
        JOIN public.m_state st   ON st.st_code = d.st_code
        LEFT JOIN public.m_fin_year fy ON fy.fin_yr_cd = p.fin_yr_cd
        WHERE (SELECT t.action FROM public.ppr_transaction t
               WHERE t.ppr_id = p.ppr_id
               ORDER BY t.senton DESC, t.tranx_id DESC LIMIT 1) = 'A'
          AND (CAST(:stateCode AS integer)    IS NULL OR st.st_code  = CAST(:stateCode AS integer))
          AND (CAST(:districtCode AS integer) IS NULL OR d.dcode     = CAST(:districtCode AS integer))
          AND (CAST(:finYrCd AS integer)      IS NULL OR p.fin_yr_cd = CAST(:finYrCd AS integer))
        ORDER BY st.st_name, d.dist_name, fy.fin_yr_desc, p.project_name
        """, nativeQuery = true)
    List<ReportP1Row> findSanctionedProjects(@Param("stateCode") Integer stateCode,
                                             @Param("districtCode") Integer districtCode,
                                             @Param("finYrCd") Integer finYrCd);

    @Query(value = """
            SELECT st_code AS "code", st_name AS "name"
            FROM public.m_state
            ORDER BY st_name
            """, nativeQuery = true)
        List<CodeName> findAllStates();

    @Query(value = """
        SELECT dcode AS "code", dist_name AS "name" FROM public.m_district
        WHERE (CAST(:stateCode AS integer) IS NULL OR st_code = CAST(:stateCode AS integer))
        ORDER BY dist_name
        """, nativeQuery = true)
    List<CodeName> findDistricts(@Param("stateCode") Integer stateCode);

    @Query(value = """
            SELECT fin_yr_cd AS "code", fin_yr_desc AS "name"
            FROM public.m_fin_year
            ORDER BY fin_yr_desc
            """, nativeQuery = true)
        List<CodeName> findFinYears();
    
    @Query(value = """
            SELECT st.st_code                    AS "stateCode",
                   st.st_name                    AS "stateName",
                   COUNT(DISTINCT d.dcode)       AS "districtCount",
                   COUNT(*)                      AS "projectCount",
                   SUM(s.area_proposed)          AS "areaSanctioned",
                   SUM(s.tot_cost)               AS "totCost",
                   SUM(s.central_share_amt)      AS "centralShare",
                   SUM(s.state_share_amt)        AS "stateShare"
            FROM public.m_ppr p
            JOIN public.m_sanction s
                  ON s.sanction_id = (SELECT MAX(s2.sanction_id) FROM public.m_sanction s2
                                      WHERE s2.ppr_id = p.ppr_id)
            JOIN public.m_district d ON d.dcode = p.dcode
            JOIN public.m_state st   ON st.st_code = d.st_code
            WHERE (SELECT t.action FROM public.ppr_transaction t
                   WHERE t.ppr_id = p.ppr_id
                   ORDER BY t.senton DESC, t.tranx_id DESC LIMIT 1) = 'A'
            GROUP BY st.st_code, st.st_name
            ORDER BY st.st_name
            """, nativeQuery = true)
        List<ReportP1StateSummary> findStateSummary();
}
