package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.repository.LivelihoodInterventionRepository;

@Service
public class LivelihoodInterventionServices {

	@Autowired
	LivelihoodInterventionRepository repo;
	
	public List<LivelihoodIntervention> getAllLivelihoodIntervention(){
		List<LivelihoodIntervention> list = repo.findAll();
		return list;
	}
}
