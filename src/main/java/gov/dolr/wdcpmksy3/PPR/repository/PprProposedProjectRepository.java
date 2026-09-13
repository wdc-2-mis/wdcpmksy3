package gov.dolr.wdcpmksy3.PPR.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.dto.PprRequestDolrApprovalDto;
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
	
	
	@Query("select new gov.dolr.wdcpmksy3.PPR.dto.PprRequestDolrApprovalDto(" 
			+ "ppp.ppr.pprId, "
			+ "ppp.ppr.projectName, "
			+ "ppp.ppr.district.distName, "
			+ "ppp.ppr.finYear.finYrDesc, "
			+ "sum(ppp.treatedArea), "
			+ "sum(ppp.proposedCost)) "
			+ "from PprProposedProject ppp "
			+ "where ppp.ppr = :ppr and ppp.status = 'C' "
			+ "group by ppp.ppr.pprId, ppp.ppr.projectName, "
			+ "ppp.ppr.district.distName, ppp.ppr.finYear.finYrDesc "
			+ "order by ppp.ppr.projectName, ppp.ppr.district.distName")
	PprRequestDolrApprovalDto getPprRequestDolrApprovalData(@Param("ppr") MPpr ppr);

}
