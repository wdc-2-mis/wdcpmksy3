package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MScheme;
import gov.dolr.wdcpmksy3.PPR.repository.PprAreaCoveredRepository;

@Service
public class PprAreaCoverService {
	
	@Autowired
	private PprAreaCoveredRepository repo;

	public List<MScheme> getAllSchemes() {
        return repo.findAll();
    }

}
