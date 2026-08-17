package gov.dolr.wdcpmksy3.PPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodActivity;

@Repository
public interface LivelihoodActivityRepository extends JpaRepository<LivelihoodActivity, Integer> {

}