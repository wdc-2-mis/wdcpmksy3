package gov.dolr.wdcpmksy3.DPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.DPR.entity.MOtherActivity;

import java.util.List;


public interface MOtherActivityRepository extends JpaRepository<MOtherActivity, Integer> {
	
	List<MOtherActivity> findByOtherHeadsIdOrderBySeqNoAsc(Integer otherHeadsId);
	
	String findUnitDescByOtherActivityId(Integer actId);

}
