package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.PprVillage;

public interface PprVillageRepository extends JpaRepository<PprVillage, Integer>{
	
	void deleteByProjectGlance_PprProjectGlanceId(Integer pprProjectGlanceId);
	
	List<PprVillage> findByProjectGlance_PprProjectGlanceId(Integer id);

}
