package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.SoilType;
import gov.dolr.wdcpmksy3.PPR.repository.SoilTypeRepository;

@Service
public class SoilTypeServices {
	
	@Autowired
	SoilTypeRepository   soilrepo;
	
	public List<SoilType> getAllSoilTypeDetails(){
		List<SoilType> list = soilrepo.findAll();
		return list;
	}

}
