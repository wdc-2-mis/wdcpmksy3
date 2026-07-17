package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.Designation;
import gov.dolr.wdcpmksy3.repository.DesignationRepository;

@Service
public class DesignationService {
	
	@Autowired
	DesignationRepository designationRepo;
	
	public List<Designation> getAllDesignationDetails(){
		List<Designation> list = designationRepo.findAll();
		return list;
	}

	@SuppressWarnings("deprecation")
	public Designation getDesignationById(Integer designation) {
		// TODO Auto-generated method stub
		return designationRepo.getById(designation);
	}

}
