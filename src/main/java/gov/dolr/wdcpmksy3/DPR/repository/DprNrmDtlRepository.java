package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.DprNrmDtl;

@Repository
public interface DprNrmDtlRepository extends JpaRepository<DprNrmDtl, Integer> {

    List<DprNrmDtl> findByDpr_DprId(Integer dprId);

    List<DprNrmDtl> findByDpr_DprIdAndStatus(Integer dprId, String status);

    Optional<DprNrmDtl> findByDpr_DprIdAndActivity_ActivityCode(
            Integer dprId, Integer activityCode);

    boolean existsByDpr_DprIdAndActivity_ActivityCode(
            Integer dprId, Integer activityCode);

    List<DprNrmDtl> findAllByOrderByNrmIdDesc();
    
    @Query(" select count(d) > 0 from DprNrmDtl d  WHERE d.dpr.ppr.pprId = :pprId AND d.activity.activityCode = :actId")
	boolean existsByPprIdAndActivityId( @Param("pprId") Integer pprId, @Param("actId") Integer actId);
}