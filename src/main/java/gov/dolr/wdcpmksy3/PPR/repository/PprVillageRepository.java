package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprVillage;

public interface PprVillageRepository extends JpaRepository<PprVillage, Integer>{
	
	List<PprVillage> findByProjectGlance_PprProjectGlanceId(Integer id);
	
	@Modifying
	@Transactional
	@Query(" UPDATE PprVillage v SET v.status = 'D' WHERE v.projectGlance.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);

}
