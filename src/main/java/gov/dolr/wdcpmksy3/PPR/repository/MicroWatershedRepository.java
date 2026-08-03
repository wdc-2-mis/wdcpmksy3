package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;

@Repository
public interface MicroWatershedRepository extends JpaRepository<MicroWatershed, Integer>{

	List<MicroWatershed> findAll();

	@Query("SELECT SUM(mw.mwArea) FROM MicroWatershed mw WHERE mw.mwId IN (SELECT pm.microWatershed.mwId FROM PprMicroWatershed pm " +
	           "WHERE pm.ppr.district.dcode = :dcode AND pm.ppr.status = 'C' and pm.status = 'C')")
	    Double sumAreaByDistrictWithCompletedStatus(Integer dcode);
}
