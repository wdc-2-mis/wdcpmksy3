package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.PprSlnaDetails;
import gov.dolr.wdcpmksy3.PPR.repository.PprSlnaDetailsRepository;
import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;

@Service
public class PprSlnaDetailsService {
	
	@Autowired
	PprSlnaDetailsRepository pprSlnaDetailsRepo;
	
	public List<PprSlnaDetails> getDraftdataOfSlnaDetails(){
		List<PprSlnaDetails> list = pprSlnaDetailsRepo.findAll();
		return list;
	}
	
	public List<PprSlnaDetails> getComdataOfSlnaDetails(){
		List<PprSlnaDetails> comSlnaDetails = new ArrayList<>();
		List<PprSlnaDetails> list = pprSlnaDetailsRepo.findAll();
		comSlnaDetails = list.stream().filter(s-> s.getStatus().equals('C')).toList();
		return comSlnaDetails;
	}

	public void save(PprSlnaDetails details) {
		pprSlnaDetailsRepo.save(details);
	}
	
	public PprSlnaDetails getSlnaDetailsById(Integer Id) {
		return pprSlnaDetailsRepo.getById(Id);
	}
	
	public void delete(Integer Id) {
		pprSlnaDetailsRepo.deleteById(Id);
	}
	
	public void completeRecord(Integer id, String updatedBy) {
		PprSlnaDetails data = pprSlnaDetailsRepo.findById(id).orElse(null);
        if (data != null) {
            data.setStatus('C');
            data.setUpdatedBy(updatedBy);
            data.setUpdatedDate(LocalDate.now());
            pprSlnaDetailsRepo.save(data);
        }
    }
	
	public List<PprSlnaDetails> getSlnaDetailsByInstStruc(InstitutionalStructure instStr){
		List<PprSlnaDetails> list = pprSlnaDetailsRepo.findByInstitutionalStructure(instStr);
		return list;
	}

}
