package gov.dolr.wdcpmksy3.DPR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.EpaActivity;

@Repository
public interface EpaActivityRepo extends JpaRepository<EpaActivity, Integer>{

}
