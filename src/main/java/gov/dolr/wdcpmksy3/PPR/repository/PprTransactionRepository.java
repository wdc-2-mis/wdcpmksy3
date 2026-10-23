package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;
import gov.dolr.wdcpmksy3.entity.MDistrict;

public interface PprTransactionRepository extends JpaRepository<PprTransaction, Integer> {

    List<PprTransaction> findByPprPprId(Integer pprId);

    @Query("select t from PprTransaction t where t.action = 'F' and t.sentTo.regId =:regId and t.ppr.district.state.stCode= "
    		+ "case when 0 = :stCode then t.ppr.district.state.stCode else :stCode end and t.senton =(select max(t1.senton) from PprTransaction t1 "
    		+ "where t.ppr = t1.ppr) order by t.senton desc")
    List<PprTransaction> findBySentToRegIdandStcode(@Param("regId") Integer regId, @Param("stCode") Integer stCode);

    List<PprTransaction> findBySentFromRegId(Integer regId);
    
    List<PprTransaction> findByPpr_District_State_StCode(Integer stCode);
    
    @Query("select t from PprTransaction t where t.ppr.district.state.stCode=:stCode and t.action='B' and t.senton=(select max(t2.senton) "
    		+ "from PprTransaction t2  where t2.ppr.pprId = t.ppr.pprId) order by t.senton desc")
    List<PprTransaction> findLatestTransactionsByState(@Param("stCode") Integer stCode);
    
    @Query("select t from PprTransaction t where t.ppr.pprId=:pprId and (t.action='F' or t.action='A' or t.action='R') and t.senton=(select max(t2.senton) "
    		+ "from PprTransaction t2  where t2.ppr.pprId = t.ppr.pprId) order by t.senton desc")
    List<PprTransaction> findLatestTransactionsByPprPprId(@Param("pprId") Integer pprId);
    
    @Query("select t from PprTransaction t where t.ppr.district.state.stCode= case when 0 = :stCode then t.ppr.district.state.stCode else :stCode end "
    		+ "and t.senton =(select max(t2.senton) from PprTransaction t2  where t2.ppr.pprId = t.ppr.pprId) order by t.senton desc")
    List<PprTransaction> findPprTransactionByStCode(Integer stCode);
    
    List<PprTransaction> findByPprPprIdOrderBySentonDesc(Integer pprId);
    
    @Query("select count(t) > 0 from PprTransaction t where t.ppr.pprId = :pprId and t.action in ('A', 'F') and t.senton=(select max(t2.senton)"
    		+ "from PprTransaction t2  where t2.ppr.pprId = t.ppr.pprId)  group by t.senton order by t.senton desc")
    boolean existsActionAOrF(@Param("pprId") Integer pprId);
    
    @Query("SELECT DISTINCT d  FROM PprTransaction t  JOIN t.ppr p JOIN p.district d  JOIN d.state s  WHERE s.stCode = :stCode  "
    		+ "AND t.action = 'A'  ORDER BY d.distName")
    List<MDistrict> findDistrictsByStCodeAndActionA(@Param("stCode") Integer stCode);
    
    @Query("SELECT DISTINCT p  FROM PprTransaction t JOIN t.ppr p  JOIN p.district d  WHERE d.dcode = :dcode  AND t.action = 'A'  "
    		+ "ORDER BY p.projectName")
    List<MPpr> findPprByDcodeAndActionA(@Param("dcode") Integer dcode);

    Optional<PprTransaction>
    findTopByPpr_PprIdOrderBySentonDescTranxIdDesc(Integer pprId);
    
}