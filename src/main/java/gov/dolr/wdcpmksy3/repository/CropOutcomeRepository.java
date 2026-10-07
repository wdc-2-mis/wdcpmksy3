package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.PprCropOutcome;

@Repository
public interface CropOutcomeRepository extends JpaRepository<PprCropOutcome, Integer>{

	@Query("SELECT c.cropType.cropTypeId FROM PprCropOutcome c WHERE c.ppr.pprId = :pprId")
	List<Integer> findCropIdsByPpr(Integer pprId);

	@Query(" SELECT p FROM PprCropOutcome p WHERE p.ppr.district.dcode = :dcode ORDER BY CASE WHEN p.status = 'D' THEN 0 ELSE 1 END, p.pprCropOutcomeId")
	List<PprCropOutcome> findByDistrictOrderByStatus(Integer dcode);
	
	@Query(" SELECT p FROM PprCropOutcome p WHERE p.ppr.district.state.stCode = :stcode ORDER BY CASE WHEN p.status = 'D' THEN 0 ELSE 1 END, p.pprCropOutcomeId")
	List<PprCropOutcome> findByStateOrderByStatus(Integer stcode);
	
	List<PprCropOutcome> findByPprPprIdAndStatus(Integer pprId, Character status);
	
	@Modifying
	@Transactional
	@Query("UPDATE PprCropOutcome c SET c.status = 'D' WHERE c.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);	
	
	boolean existsByPpr_InstitutionalStructure_StCodeAndStatus(Integer stCode, String status);
	
	List<PprCropOutcome> findByPprPprIdAndStatusIn(Integer pprId, List<Character> status);

	@Query("""
		    SELECT c.status
		    FROM PprCropOutcome c
		    WHERE c.ppr.pprId = :pprId
		      AND c.ppr.district.dcode = :dcode
		      AND c.season.seasonId = :seasonId
		    ORDER BY c.pprCropOutcomeId DESC
		""")
		List<Character> findStatusByDistrictProjectSeason(
		        @Param("dcode") Integer dcode,
		        @Param("pprId") Integer pprId,
		        @Param("seasonId") Integer seasonId);

}
