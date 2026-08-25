package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprCropOutcome;

@Repository
public interface CropOutcomeRepository extends JpaRepository<PprCropOutcome, Integer>{

	@Query("SELECT c.cropType.cropTypeId FROM PprCropOutcome c WHERE c.ppr.pprId = :pprId")
	List<Integer> findCropIdsByPpr(Integer pprId);

	@Query(" SELECT p FROM PprCropOutcome p WHERE p.ppr.district.dcode = :dcode ORDER BY p.pprCropOutcomeId")
	List<PprCropOutcome> findByDistrict(Integer dcode);

}
