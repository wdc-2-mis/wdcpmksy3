package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.PprProposedProjectDto;
import gov.dolr.wdcpmksy3.PPR.entity.CriteriaDetails;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
import gov.dolr.wdcpmksy3.PPR.repository.CriteriaDetailsRepository;
import gov.dolr.wdcpmksy3.PPR.repository.CriteriaRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProposedProjectRepository;
import gov.dolr.wdcpmksy3.PPR.repository.ProjectTypeRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PprProposedProjectService {

    @Autowired
    private PprProposedProjectRepository repository;

    @Autowired
    private MPprRepository pprRepository;

    @Autowired
    private MicroWatershedRepository microWatershedRepository;

    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    
    @Autowired
    private CriteriaDetailsRepository criteriaDetailsRepo;
    
    @Autowired
    private CriteriaRepository criteriaRepo;

    public void save(PprProposedProjectDto form, Character status, String userId, String ipAddress) {
        PprProposedProject entity = new PprProposedProject();
        entity.setPpr(pprRepository.findById(form.getPprId()).orElseThrow(() -> new RuntimeException("Invalid PPR Id")));
        entity.setMicroWatershed(microWatershedRepository.findById(form.getMicroWatershed()).orElseThrow(() -> new RuntimeException("Invalid Micro Watershed")));
        entity.setProjectType(projectTypeRepository.findById(form.getProjectType()).orElseThrow(() -> new RuntimeException("Invalid Project Type")));
        // Replace with actual criteria id or logic
        entity.setTreatedArea(form.getTreatedProjectArea());
        entity.setProposedCost(form.getProposedCost());
        entity.setStatus(status);
        entity.setCreatedBy(userId);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRequestIp(ipAddress);
        repository.save(entity);
        String[] arr=form.getCriteriaData().split(",");
        
        for(String s:arr){
        	
        	if (s.isBlank()) {
                continue;
            }
            String[] value=s.split(":");
            if (value.length != 2) {
                continue;
            }
            Integer criteriaId=Integer.parseInt(value[0]);
            Integer marks=Integer.parseInt(value[1]);
            CriteriaDetails details=new CriteriaDetails();
            details.setCriteria(
                    criteriaRepo.findById(criteriaId).get());
            details.setScoredMarks(marks);
            details.setProposedProject(entity);
            details.setStatus("D");
            details.setCreatedBy(userId);
            details.setCreatedDate(LocalDateTime.now());
            criteriaDetailsRepo.save(details);

        }
    }
    
    public List<PprProposedProject> getPprProposedProjectList(MPpr ppr){
    	List<PprProposedProject> list = repository.getListOfPprProposedProjectsByPpr(ppr);
    	return list;
    }

	public PprProposedProject findById(Integer id) {
		Optional<PprProposedProject> data = repository.findById(id);
		return data.get();
	}
	
	@Transactional
	public void updateProposedProject(PprProposedProjectDto dto, String userId) {

	    PprProposedProject entity = repository.findById(dto.getPprProposedProjectId()).orElseThrow(() -> new RuntimeException("Invalid Id"));
	    // Only editable fields
	    entity.setTreatedArea(dto.getTreatedProjectArea());
	    entity.setProposedCost(dto.getProposedCost());
	    entity.setProjectType(projectTypeRepository.findById(dto.getProjectType()).orElseThrow(() -> new RuntimeException("Invalid Project Type")));
	    entity.setUpdatedBy(userId);
	    entity.setUpdatedDate(LocalDate.now());
	    repository.save(entity);
//	    String[] arr=dto.getCriteriaData().split(",");
//        
//        for(String s:arr){
//        	
//        	if (s.isBlank()) {
//                continue;
//            }
//            String[] value=s.split(":");
//            if (value.length != 2) {
//                continue;
//            }
//            Integer criteriaId=Integer.parseInt(value[0]);
//            Integer marks=Integer.parseInt(value[1]);
//            CriteriaDetails details=new CriteriaDetails();
//            details.setCriteria(
//                    criteriaRepo.findById(criteriaId).get());
//            details.setScoredMarks(marks);
//            details.setProposedProject(entity);
//            details.setStatus("D");
//            details.setCreatedBy(userId);
//            details.setCreatedDate(LocalDateTime.now());
//            criteriaDetailsRepo.save(details);
//
//        }
	}
	
	@Transactional
	public void deletePprProposedProject(Integer id, String userId) {
	    PprProposedProject project = repository.findById(id).orElseThrow(() -> new RuntimeException("Proposed project not found: " + id));
	    // Delete CriteriaDetails first
	    criteriaDetailsRepo.deleteByProposedProjectPprProposedProjectId(id);

	    // Delete proposed project
	    repository.delete(project);
	}
	
	@Transactional
	public void completePprProposedProject(Integer id, String userId) {

	    // Get proposed project
	    PprProposedProject project = repository.findById(id).orElseThrow(() -> new RuntimeException("Proposed project not found: " + id));

	    // Change PprProposedProject status
	    project.setStatus('C');
	    project.setUpdatedBy(userId);
	    project.setUpdatedDate(LocalDate.now());
	    repository.save(project);
		
	    // Get all CriteriaDetails belonging to this project
	    List<CriteriaDetails> criteriaDetails = criteriaDetailsRepo.findByProposedProjectPprProposedProjectId(id);

	    // Change CriteriaDetails status
	    for (CriteriaDetails detail : criteriaDetails) {
	        detail.setStatus("C");
	        detail.setUpdatedBy(userId);
	        detail.setUpdatedDate(LocalDate.now());
	    }
	    // Save all CriteriaDetails
	    criteriaDetailsRepo.saveAll(criteriaDetails);
	}
	
	public boolean existsByDistrictAndMicroWatershed(
	        String district,
	        Integer microWatershed) {

	    return repository.existsByPprDistrictDcodeAndMicroWatershedMwId(district,microWatershed);
	}

}
