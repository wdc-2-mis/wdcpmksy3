package gov.dolr.wdcpmksy3.PPR.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;

@Repository
public interface PprProposedProjectRepository extends JpaRepository<PprProposedProject, Integer> {
	
	List<PprProposedProject> getListOfPprProposedProjectsByPpr(MPpr ppr);
	
	Optional<PprProposedProject> findById(Integer id);

	boolean existsByPprDistrictDcodeAndMicroWatershedMwId(String district, Integer microWatershed);
	
	List<PprProposedProject> findByPprPprIdAndStatus(Integer pprId, Character status);
	
	@Modifying
	@Transactional
	@Query(" UPDATE PprProposedProject p SET p.status = 'D' WHERE p.ppr.pprId = :pprId")
	int changeStatusByPprId(@Param("pprId") Integer pprId);

}
