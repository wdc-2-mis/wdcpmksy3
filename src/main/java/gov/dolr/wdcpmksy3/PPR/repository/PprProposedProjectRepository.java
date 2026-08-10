package gov.dolr.wdcpmksy3.PPR.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;

@Repository
public interface PprProposedProjectRepository extends JpaRepository<PprProposedProject, Integer> {
	
	List<PprProposedProject> getListOfPprProposedProjectsByPpr(MPpr ppr);
	
	Optional<PprProposedProject> findById(Integer id);

}
