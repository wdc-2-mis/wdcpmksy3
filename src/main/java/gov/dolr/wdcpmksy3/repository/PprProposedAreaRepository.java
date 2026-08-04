package gov.dolr.wdcpmksy3.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.PprProposedArea;

@Repository
public interface PprProposedAreaRepository extends JpaRepository<PprProposedArea, Long> {

    List<PprProposedArea> findByPprPprId(Integer pprId);

}