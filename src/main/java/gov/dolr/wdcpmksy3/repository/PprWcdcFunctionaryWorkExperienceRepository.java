package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionaryWorkExperience;
import gov.dolr.wdcpmksy3.entity.SlnaFunctionaryWorkExperience;

public interface PprWcdcFunctionaryWorkExperienceRepository extends JpaRepository<PprWcdcFunctionaryWorkExperience, Integer>{
	
	List<PprWcdcFunctionaryWorkExperience> findByFunctionaryPprWcdcFunId(Integer id);
	
	@Transactional
    @Modifying
    void deleteByFunctionaryPprWcdcFunId(Integer id);
	

}
