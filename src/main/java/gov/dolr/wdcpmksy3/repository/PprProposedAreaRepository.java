package gov.dolr.wdcpmksy3.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.PprProposedArea;

@Repository
public interface PprProposedAreaRepository extends JpaRepository<PprProposedArea, Long> {

    List<PprProposedArea> findByPprPprIdAndStatus(Integer pprId,Character status);
    List<PprProposedArea> findByBlockBcode(Integer bcode);
    List<PprProposedArea> findBycreatedBy(String userid);
    
    @Modifying
    @Transactional
    @Query(" UPDATE PprProposedArea p SET p.status = 'D' WHERE p.ppr.pprId = :pprId")
    int changeStatusByPprId(@Param("pprId") Integer pprId);
}