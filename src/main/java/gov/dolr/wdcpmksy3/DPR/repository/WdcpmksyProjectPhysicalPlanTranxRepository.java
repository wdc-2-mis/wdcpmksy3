package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlanTranx;

@Repository
public interface WdcpmksyProjectPhysicalPlanTranxRepository extends JpaRepository<WdcpmksyProjectPhysicalPlanTranx, Integer> {

    List<WdcpmksyProjectPhysicalPlanTranx> findByPlan_PlanIdOrderBySentOnDesc(Integer planId);

    List<WdcpmksyProjectPhysicalPlanTranx> findBySentTo_RegIdOrderBySentOnDesc(Integer regId);

    List<WdcpmksyProjectPhysicalPlanTranx> findBySentFrom_RegIdOrderBySentOnDesc(Integer regId);
    
    @Query(value = "SELECT a.plan_id AS planid, a.projectdesc AS projectdesc, (CASE WHEN a.status = 'R' THEN 'Rejected' WHEN a.status = 'F' THEN 'Pending' ELSE 'Completed' END) AS status, "
    		+ "( CASE  WHEN reg.user_type = 'PI' THEN 'PIA' WHEN reg.user_type = 'DI' THEN 'District' ELSE 'SLNA' END) AS currentlevel, a.remarks AS remarks, a.finyr AS finyr,"
    		+ " a.usertype AS usertype, a.sentfrom AS sentfrom, a.sentto AS sentto, a.sent_on AS senton, a.status AS action FROM  (SELECT t.plan_id, proj.project_name AS projectdesc, "
    		+ "t.action AS status, t.sent_to AS regid, t.sent_from AS sentfrom, t.sent_to AS sentto, t.sent_on, t.remarks, yr.fin_yr_desc AS finyr,"
    		+ " (CASE WHEN u.user_type = 'PI' THEN 'PIA' WHEN u.user_type = 'DI' THEN 'District' ELSE 'SLNA' END) AS usertype, ROW_NUMBER() OVER ( PARTITION BY t.plan_id ORDER BY t.sent_on DESC, t.tranx_id DESC ) AS rn"
    		+ " FROM wdcpmksy_project_physical_plan_tranx t INNER JOIN wdcpmksy_project_physical_plan p  ON t.plan_id = p.plan_id INNER JOIN m_ppr proj ON p.proj_id = proj.ppr_id INNER JOIN m_fin_year yr "
    		+ " ON p.fin_yr_cd = yr.fin_yr_cd LEFT JOIN wdcpmksy_user_reg u ON u.reg_id = t.sent_to WHERE p.status != 'C' AND ( t.sent_from = :regId OR t.sent_to = :regId ) ) a "
    		+ "INNER JOIN wdcpmksy_user_reg reg ON a.regid = reg.reg_id WHERE a.rn = 1 ORDER BY a.projectdesc, a.finyr", nativeQuery = true)
        List<PhysicalActionPlanTranxProjection> viewARMovement( @Param("regId") Integer regId );
    
    @Query(value = "select planid, projectdesc, case when a.status='R' then 'Rejected' when a.status='F' then 'Pending' else 'Completed' end as status, "
    		+ "case  when reg.user_type='PI' then 'PIA' when reg.user_type='DI' then 'District' else 'SLNA' end as currentlevel, remarks, finyr, usertype "
    		+ "from (select DISTINCT ON (tranx.plan_id) tranx.plan_id as planid, proj.project_name as projectdesc, LAST_VALUE(action) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, "
    		+ " tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) status, LAST_VALUE(sent_to) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, "
    		+ " tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) regid ,LAST_VALUE(tranx.remarks) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, "
    		+ " tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) remarks, yr.fin_yr_desc as finyr, "
    		+ "(select case when user_type='PI' then 'PIA' when user_type='DI' then 'District' else 'SLNA' end from wdcpmksy_user_reg where reg_id=:regId) as userType "
    		+ "from wdcpmksy_project_physical_plan_tranx tranx, wdcpmksy_project_physical_plan plan, m_ppr proj, m_fin_year yr where  "
    		+ "tranx.plan_id=plan.plan_id and plan.proj_id = proj.ppr_id and plan.status = 'C' and plan.fin_yr_cd=yr.fin_yr_cd "
    		+ "and tranx.plan_id in (select distinct plan_id from wdcpmksy_project_physical_plan_tranx where sent_from=:regId or sent_to=:regId) "
    		+ "order by tranx.plan_id, tranx.sent_on desc) a, wdcpmksy_user_reg reg where regid=reg.reg_id order by projectdesc, finyr", nativeQuery = true)
        List<PhysicalActionPlanTranxProjection> viewCompletePlan( @Param("regId") Integer regId );
    
    @Query(value = "select planid, projectdesc, case when a.status='R' then 'Rejected' when a.status='F' then 'Pending' else 'Completed' end as status, "
    		+ "case  when reg.user_type='PI' then 'PIA' when reg.user_type='DI' then 'District' else 'SLNA' end as currentlevel, remarks, finyr, usertype "
    		+ "from (select DISTINCT ON (tranx.plan_id) tranx.plan_id as planid, proj.project_name as projectdesc, LAST_VALUE(action) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, "
    		+ "tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) status, LAST_VALUE(sent_to) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, "
    		+ "tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) regid ,LAST_VALUE(tranx.remarks) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, "
    		+ "tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) remarks, yr.fin_yr_desc as finyr, \r\n"
    		+ "(select case when user_type='PI' then 'PIA' when user_type='DI' then 'District' else 'SLNA' end from wdcpmksy_user_reg where reg_id=:regId) as userType "
    		+ "from wdcpmksy_project_physical_plan_tranx tranx, wdcpmksy_project_physical_plan plan, m_ppr proj, m_fin_year yr \r\n"
    		+ "where tranx.plan_id=plan.plan_id and plan.proj_id = proj.ppr_id and plan.fin_yr_cd=yr.fin_yr_cd and tranx.plan_id in (select distinct plan_id "
    		+ "from wdcpmksy_project_physical_plan_tranx where sent_from=:regId or sent_to=:regId) order by tranx.plan_id, tranx.sent_on desc) a, "
    		+ "wdcpmksy_user_reg reg where regid=reg.reg_id order by  projectdesc, finyr", nativeQuery = true)
        List<PhysicalActionPlanTranxProjection> viewAAPMovement( @Param("regId") Integer regId );
    
    
    @Query(value ="select distinct on (sent_from) sent_from from wdcpmksy_project_physical_plan_tranx, wdcpmksy_user_reg where plan_id=:planid "
    		+ "and sent_to=:regId and sent_from=reg_id and user_type='PI'", nativeQuery = true)
    Integer getSentToByRegIdDIR(@Param("regId") Integer regId, @Param("planid") Integer planid);
    
    @Query(value ="select distinct on (sent_from) sent_from from wdcpmksy_project_physical_plan_tranx, wdcpmksy_user_reg where plan_id=:planid and sent_to=:regId and sent_from=reg_id and "
    		+ "user_type=(select distinct on (user_type) user_type from wdcpmksy_project_physical_plan_tranx, wdcpmksy_user_reg where plan_id=:planid and sent_to=:regId and sent_from=reg_id) ", nativeQuery = true)
    Integer getSentToByRegIdSLR(@Param("regId") Integer regId, @Param("planid") Integer planid);
    
    
    
    
    
    
    
    
    
}