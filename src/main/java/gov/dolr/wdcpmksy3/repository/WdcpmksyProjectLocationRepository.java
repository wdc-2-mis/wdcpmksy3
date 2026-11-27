package gov.dolr.wdcpmksy3.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyProjectLocation;


@Repository
public interface WdcpmksyProjectLocationRepository
        extends JpaRepository<WdcpmksyProjectLocation, Integer> {

    List<WdcpmksyProjectLocation> findByProject_PprId(Integer pprId);

    List<WdcpmksyProjectLocation> findByProject_PprIdOrderByLocationIdAsc(
            Integer pprId);

    boolean existsByProject_PprIdAndStatus(
            Integer pprId, Character status);

    boolean existsByProject_PprIdAndVillage_Vcode(
            Integer pprId, Integer vcode);

    Optional<WdcpmksyProjectLocation> findByLocationIdAndProject_PprId(
            Integer locationId, Integer pprId);

    @Modifying
    @Query("""
        UPDATE WdcpmksyProjectLocation l
           SET l.status = 'C',
               l.lastUpdatedBy = :userid,
               l.lastUpdatedDate = :updatedDate,
               l.requestIp = :requestIp
         WHERE l.project.pprId = :pprId
           AND l.status = 'D'
    """)
    int completeDraftLocations(
            @Param("pprId") Integer pprId,
            @Param("userid") String userid,
            @Param("updatedDate") LocalDate updatedDate,
            @Param("requestIp") String requestIp);
    
    
    long countByProject_PprId(Integer pprId);

    long countByProject_PprIdAndStatus(
            Integer pprId,
            Character status
    );
}


