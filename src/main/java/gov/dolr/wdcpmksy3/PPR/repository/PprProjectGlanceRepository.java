package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;

public interface PprProjectGlanceRepository extends JpaRepository<PprProjectGlance, Integer>{
	
	List<PprProjectGlance> getListOfPprProjectGlanceByPpr(MPpr ppr);

}
