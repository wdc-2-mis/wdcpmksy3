package gov.dolr.wdcpmksy3.DPR.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import gov.dolr.wdcpmksy3.DPR.entity.MUnit; 

@Repository 
public interface MUnitRepository extends JpaRepository<MUnit, Integer> { 
	
	
}