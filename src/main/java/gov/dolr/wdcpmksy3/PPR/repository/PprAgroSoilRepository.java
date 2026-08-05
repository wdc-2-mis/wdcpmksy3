package gov.dolr.wdcpmksy3.PPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroSoil;

@Repository
public interface PprAgroSoilRepository extends JpaRepository<PprAgroSoil, Integer>{

}
