package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;

@Repository
public interface PprWcdcUnspentBalanceRepository extends JpaRepository<PprWcdcUnspentBalance, Integer> {
	
	List<PprWcdcUnspentBalance> findByPpr_District_State_StCode(Integer stCode);
	
	List<PprWcdcUnspentBalance> findByPprPprIdAndStatus(Integer pprId, Character status);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprWcdcUnspentBalance b SET b.status = 'D' WHERE b.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	

}