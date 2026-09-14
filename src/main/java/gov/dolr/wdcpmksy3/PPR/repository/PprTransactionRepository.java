package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;

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
    
    @Query("select t from PprTransaction t where t.ppr.pprId=:pprId and t.action='F' and t.senton=(select max(t2.senton) "
    		+ "from PprTransaction t2  where t2.ppr.pprId = t.ppr.pprId) order by t.senton desc")
    List<PprTransaction> findLatestTransactionsByPprPprId(@Param("pprId") Integer pprId);
}