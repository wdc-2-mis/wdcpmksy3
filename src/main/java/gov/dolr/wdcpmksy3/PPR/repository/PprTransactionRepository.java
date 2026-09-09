package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;

public interface PprTransactionRepository extends JpaRepository<PprTransaction, Integer> {

    List<PprTransaction> findByPprPprId(Integer pprId);

    List<PprTransaction> findBySentToRegId(Integer regId);

    List<PprTransaction> findBySentFromRegId(Integer regId);
    
    List<PprTransaction> findByPpr_District_State_StCode(Integer stCode);
    
    @Query("select t from PprTransaction t where t.ppr.district.state.stCode=:stCode and t.action='R' and t.senton=(select max(t2.senton) "
    		+ "from PprTransaction t2  where t2.ppr.pprId = t.ppr.pprId and t2.action ='R') order by t.senton desc")
    List<PprTransaction> findLatestTransactionsByState(@Param("stCode") Integer stCode);
}