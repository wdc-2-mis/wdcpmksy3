package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.CropType;
import gov.dolr.wdcpmksy3.PPR.repository.CropTypeRepository;

@Service
public class CropTypeServices {
	
	@Autowired
	CropTypeRepository crprepo;
	
	public List<CropType> getAllCropTypeDetails(){
		List<CropType> list = crprepo.findAll();
		return list;
	}

}
