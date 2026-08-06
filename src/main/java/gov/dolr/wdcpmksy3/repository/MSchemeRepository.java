package gov.dolr.wdcpmksy3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MScheme;

@Repository
public interface MSchemeRepository  extends JpaRepository<MScheme, Integer> {
	
	MScheme findBySchemeName(String schemeName);

}
