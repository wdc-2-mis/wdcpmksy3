package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.Qualification;
import gov.dolr.wdcpmksy3.repository.QualificationRepository;

@Service
public class QualificationService {
	
	@Autowired
	QualificationRepository qualificationRepo;
	
	public List<Qualification> getAllQualification(){
		List<Qualification> list = qualificationRepo.findAll();
		return list;
	}

	@SuppressWarnings("deprecation")
	public Qualification getQualificationById(Integer qualification) {
		// TODO Auto-generated method stub
		return qualificationRepo.getById(qualification);
	}

}
