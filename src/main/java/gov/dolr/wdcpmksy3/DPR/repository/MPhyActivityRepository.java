package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import gov.dolr.wdcpmksy3.DPR.entity.MPhyActivity; 

@Repository 
public interface MPhyActivityRepository extends JpaRepository<MPhyActivity, Integer> { 
	
	List<MPhyActivity> findByHead_HeadCodeOrderBySeqNoAsc( Integer headCode); 
	List<MPhyActivity> findByHead_HeadCodeAndStatusOrderBySeqNoAsc( Integer headCode, String status); 
	List<MPhyActivity> findByStatusOrderBySeqNoAsc(String status); 
	
}