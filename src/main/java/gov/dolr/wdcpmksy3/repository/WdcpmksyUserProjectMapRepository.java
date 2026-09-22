package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyUserProjectMap;

@Repository
public interface WdcpmksyUserProjectMapRepository
        extends JpaRepository<WdcpmksyUserProjectMap, Integer> {

    List<WdcpmksyUserProjectMap> findByUser_RegId(Integer regId);

    boolean existsByUser_RegIdAndProject_ProjId(
            Integer regId,
            Integer projId
    );

    void deleteByUser_RegIdAndProject_ProjId(
            Integer regId,
            Integer projId
    );
}