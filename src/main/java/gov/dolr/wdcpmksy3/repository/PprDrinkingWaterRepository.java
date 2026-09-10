package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;

public interface PprDrinkingWaterRepository extends JpaRepository<PprDrinkingWater, Integer> {
	
	List<PprDrinkingWater> findByPpr_District_State_StCode(Integer stCode);
	
	List<PprDrinkingWater> findByPprPprIdAndStatus(Integer pprId, Character status);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprDrinkingWater w SET w.status = 'D' WHERE w.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	

}
