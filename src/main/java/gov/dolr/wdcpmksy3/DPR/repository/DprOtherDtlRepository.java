package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprOtherDtl;


public interface DprOtherDtlRepository extends JpaRepository<DprOtherDtl, Integer>{

	@Query(" select count(d) > 0 from DprOtherDtl d  WHERE d.dprDetails.ppr.pprId = :pprId AND d.otherActivity.otherActivityId = :actId")
	boolean existsByPprIdAndOtherActivityId(Integer pprId, Integer actId);
	
	@Query("select dp from DprOtherDtl dp WHERE dp.dprDetails.ppr.district.dcode = :dcode order by dp.status desc")
	List<DprOtherDtl> getListOfDprOtherDtlByDcode(Integer dcode);
	
	List<DprOtherDtl> findByDprDetailsOrderByStatusDesc(DprDetails dpr);
	
	@Query("select dp from DprOtherDtl dp WHERE dp.dprDetails.ppr.pprId = :pprId order by dp.status desc")
	List<DprOtherDtl> getListOfDprOtherDtlByPprIdOrderByStatusDesc(Integer pprId);

	@Query("select dp from DprOtherDtl dp WHERE dp.dprDetails.ppr.district.state.stCode = :stCode order by dp.status desc")
	List<DprOtherDtl> findDprOtherDtlDataBystCode(Integer stCode);
	
	@Query("select dp from DprOtherDtl dp WHERE dp.dprDetails.ppr.pprId = :pprId and dp.status in('C','S') ")
	List<DprOtherDtl> findCompletedOtherDtlByPprId(Integer pprId);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM DprOtherDtl n WHERE n.dprDetails.dprId IN ( SELECT d.dprId FROM DprDetails d  WHERE d.ppr.pprId = :pprId)")
    int deleteByPprId(@Param("pprId") Integer pprId);

	
}
