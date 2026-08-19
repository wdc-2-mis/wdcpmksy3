package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MEmploymentType;
import gov.dolr.wdcpmksy3.PPR.repository.MEmploymentTypeRepository;

@Service
public class MEmploymentTypeService {

	@Autowired
    private MEmploymentTypeRepository repository;

    public List<MEmploymentType> getAllEmploymentTypes() {
        return repository.findAllByOrderByEmploymentTypeId();
    }

    public MEmploymentType getById(Integer id) {
        return repository.findById(id).orElse(null);
    }
	
}
