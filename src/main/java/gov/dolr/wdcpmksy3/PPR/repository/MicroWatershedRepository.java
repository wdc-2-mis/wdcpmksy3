package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.dto.MicroWatershedDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;

@Repository
public interface MicroWatershedRepository extends JpaRepository<MicroWatershed, Integer>{

	List<MicroWatershed> findAll();

	@Query("SELECT SUM(mw.mwArea) FROM MicroWatershed mw WHERE mw.mwId IN (SELECT pm.microWatershed.mwId FROM PprMicroWatershed pm " +
	           "WHERE pm.ppr.district.dcode = :dcode AND pm.ppr.status = 'C' and pm.status = 'C')")
	    Double sumAreaByDistrictWithCompletedStatus(Integer dcode);

	
	@Query("SELECT new gov.dolr.wdcpmksy3.PPR.dto.MicroWatershedDTO(mw.mwId, mw.mwName) FROM MicroWatershed mw WHERE mw.mwId IN (SELECT pmw.microWatershed.mwId FROM PprMicroWatershed pmw " +
		       " WHERE pmw.ppr.district.dcode = :dcode)")
		List<MicroWatershedDTO> findByDistrict(@Param("dcode") Integer dcode);

	
	@Query("SELECT mwArea FROM MicroWatershed WHERE mwId = :mwId")
	Double microWatershedArea(Integer mwId);
	
	@Query("from MicroWatershed mw where mw.mwId IN (SELECT pm.microWatershed.mwId FROM PprMicroWatershed pm WHERE pm.ppr.pprId = :pprId)")
	List<MicroWatershed> getListOfMicroWatershedbyMwIds(Integer pprId);
}



