package gov.dolr.wdcpmksy3.DPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gov.dolr.wdcpmksy3.DPR.entity.MOtherActivity;

import java.util.List;


public interface MOtherActivityRepository extends JpaRepository<MOtherActivity, Integer> {
	
	List<MOtherActivity> findByOtherHeads_OtherHeadsIdOrderBySeqNoAsc(Integer otherHeadsId);
	
	@Query("SELECT a.unit.unitDesc FROM MOtherActivity a WHERE a.otherActivityId = :actId")
	String findUnitDescByOtherActivityId(Integer actId);

}
