package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;
import gov.dolr.wdcpmksy3.PPR.repository.PprProjectGlanceRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PprProjectGlanceService {
	
	@Autowired
	private PprProjectGlanceRepository pprProjectGlanceRepo;
	
	public List<PprProjectGlance> getPprProjectGlanceList(MPpr ppr){
		return pprProjectGlanceRepo.getListOfPprProjectGlanceByPpr(ppr);
	}
	
	

}
