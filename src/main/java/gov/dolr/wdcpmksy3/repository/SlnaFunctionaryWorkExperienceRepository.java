package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.SlnaFunctionaryWorkExperience;

@Repository
public interface SlnaFunctionaryWorkExperienceRepository extends JpaRepository<SlnaFunctionaryWorkExperience, Integer>{
	
	List<SlnaFunctionaryWorkExperience> findByFunctionaryPprSlnaFunId(Integer id);

    @Transactional
    @Modifying
    void deleteByFunctionaryPprSlnaFunId(Integer id);

}