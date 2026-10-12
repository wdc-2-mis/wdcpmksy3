package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.DPR.entity.MOtherHeads;

public interface MOtherHeadsRepository extends JpaRepository<MOtherHeads, Integer>{
	
	List<MOtherHeads> findAllByOrderBySeqNoAsc();

}
