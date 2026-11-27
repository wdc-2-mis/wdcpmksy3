package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyProjectWc;

@Repository
public interface WdcpmksyProjectWcRepository
        extends JpaRepository<WdcpmksyProjectWc, Integer> {

    List<WdcpmksyProjectWc> findByProject_PprIdOrderByWcIdAsc(
            Integer pprId
    );

    boolean existsByProject_PprId(Integer pprId);

    void deleteByWcIdAndProject_PprId(
            Integer wcId,
            Integer pprId
    );
    
    long countByProject_PprId(Integer pprId);
}
