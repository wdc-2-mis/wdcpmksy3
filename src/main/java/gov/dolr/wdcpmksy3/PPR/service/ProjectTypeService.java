package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.ProjectType;
import gov.dolr.wdcpmksy3.PPR.repository.ProjectTypeRepository;

@Service
public class ProjectTypeService {
	
	@Autowired
	ProjectTypeRepository projectTypeRepo;
	
	public List<ProjectType> getProjectType(){
		List<ProjectType> list = projectTypeRepo.findAll();
		return list;
	}

}
