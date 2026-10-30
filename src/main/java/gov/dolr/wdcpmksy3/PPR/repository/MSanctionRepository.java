package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.dolr.wdcpmksy3.PPR.entity.MSanction;

@Repository
public interface MSanctionRepository extends JpaRepository<MSanction, Integer> {

    List<MSanction> findByPprPprId(Integer pprId);
    
    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM public.m_sanction_project WHERE ppr_id = :pprId",nativeQuery = true)
    int deleteByPprId(@Param("pprId") Integer pprId);
}