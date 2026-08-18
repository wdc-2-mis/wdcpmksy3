package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;

public interface PprDrinkingWaterRepository extends JpaRepository<PprDrinkingWater, Integer> {
	
	List<PprDrinkingWater> findByPpr_District_State_StCode(Integer stCode);

}
