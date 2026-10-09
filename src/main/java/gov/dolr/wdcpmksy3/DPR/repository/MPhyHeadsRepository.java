package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import gov.dolr.wdcpmksy3.DPR.entity.MPhyHeads; 

@Repository 
public interface MPhyHeadsRepository extends JpaRepository<MPhyHeads, Integer> { 
	
	List<MPhyHeads> findAllByOrderBySeqNoAsc(); 
	List<MPhyHeads> findByStatusOrderBySeqNoAsc(String status); 
}