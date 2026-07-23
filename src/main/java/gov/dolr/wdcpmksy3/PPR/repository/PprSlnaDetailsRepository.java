package gov.dolr.wdcpmksy3.PPR.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprSlnaDetails;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;

@Repository
public interface PprSlnaDetailsRepository extends JpaRepository<PprSlnaDetails, Integer> {
	
	// Find all details for a given InstitutionalStructure
    List<PprSlnaDetails> findByInstitutionalStructure(InstitutionalStructure institutionalStructure);

}