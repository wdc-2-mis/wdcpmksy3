package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprWcdcUnspentBalance;

@Repository
public interface PprWcdcUnspentBalanceRepository extends JpaRepository<PprWcdcUnspentBalance, Integer> {
	
	List<PprWcdcUnspentBalance> findByPpr_District_State_StCode(Integer stCode);

}