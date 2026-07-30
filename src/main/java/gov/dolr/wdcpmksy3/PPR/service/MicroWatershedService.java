package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;

@Service
public class MicroWatershedService {

	@Autowired
	private MicroWatershedRepository repository;
	
	public List<MicroWatershed> getMicroServiceIdandName() {
        return repository.findAll(); 
    }

	
}
