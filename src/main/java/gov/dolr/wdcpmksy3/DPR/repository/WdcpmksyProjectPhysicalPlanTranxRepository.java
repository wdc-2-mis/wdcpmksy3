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
    
    @Query(value = "select planid, projectdesc, case when a.status='R' then 'Rejected' when a.status='F' then 'Pending' else 'Completed' end as status, "
    		+ "case  when reg.user_type='PI' then 'PIA' when reg.user_type='DI' then 'District' else 'SLNA' end as currentlevel, "
    		+ "remarks, finyr, usertype from (select DISTINCT ON (tranx.plan_id) tranx.plan_id as planid, proj.project_name as projectdesc, "
    		+ "LAST_VALUE(action) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) status, "
    		+ "LAST_VALUE(sent_to) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) regid, "
    		+ "LAST_VALUE(tranx.remarks) OVER(PARTITION BY tranx.plan_id ORDER BY tranx.plan_id, tranx.sent_on RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) remarks, "
    		+ "yr.fin_yr_desc as finyr, (select case when user_type='PI' then 'PIA' when user_type='DI' then 'District' else 'SLNA' end  "
    		+ " from wdcpmksy_user_reg where reg_id=:regId) as userType from wdcpmksy_project_physical_plan_tranx tranx, wdcpmksy_project_physical_plan plan, "
    		+ "m_ppr proj, m_fin_year yr where tranx.plan_id=plan.plan_id and plan.proj_id = proj.ppr_id and plan.status != 'C' "
    		+ "and plan.fin_yr_cd=yr.fin_yr_cd and tranx.plan_id in (select distinct plan_id from wdcpmksy_project_physical_plan_tranx "
    		+ "where sent_from=:regId or sent_to=:regId) order by tranx.plan_id, tranx.sent_on desc) a, wdcpmksy_user_reg reg where "
    		+ "regid=reg.reg_id order by projectdesc, finyr", nativeQuery = true)
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