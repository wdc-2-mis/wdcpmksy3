package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MSanction;

@Repository
public interface MSanctionRepository extends JpaRepository<MSanction, Integer> {

    List<MSanction> findByPprPprId(Integer pprId);

}