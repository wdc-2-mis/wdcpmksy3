package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.MScheme;

public interface PprAreaCoveredRepository extends JpaRepository<MScheme, Integer>{

	List<MScheme> findAll();

}
