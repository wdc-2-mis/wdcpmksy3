package gov.dolr.wdcpmksy3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MWaterQuality;

@Repository
public interface MWaterQualityRepository extends JpaRepository<MWaterQuality, Integer> {

}
