package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;

@Repository
public interface MicroWatershedRepository extends JpaRepository<MicroWatershed, Integer>{

	List<MicroWatershed> findAll();
}
