package gov.dolr.wdcpmksy3.PPR.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.PprProjectAtGlanceDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MPiaDetails;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;
import gov.dolr.wdcpmksy3.PPR.entity.PprVillage;
import gov.dolr.wdcpmksy3.PPR.entity.ProjectType;
import gov.dolr.wdcpmksy3.PPR.repository.MPiaDetailsRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprProjectGlanceRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprVillageRepository;
import gov.dolr.wdcpmksy3.PPR.repository.ProjectTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PprProjectGlanceService {
	
	@Autowired
	private PprProjectGlanceRepository pprProjectGlanceRepo;
	
	@Autowired
	private MPprRepository mPprRepo;
	
	@Autowired
	private MicroWatershedRepository microWatershedRepo;
	
	@Autowired
	private ProjectTypeRepository projectTypeRepo;
	
	@Autowired
	private MPiaDetailsRepository piaRepository;
	
	@Autowired
	private VillageRepository villageRepo;
	
	@Autowired
	private PprVillageRepository pprVillageRepo;
	
	public List<PprProjectGlance> getPprProjectGlanceList(MPpr ppr){
		return pprProjectGlanceRepo.getListOfPprProjectGlanceByPpr(ppr);
	}
	
	@Transactional
	public void savePprProjectAtGlance(PprProjectAtGlanceDTO dto, String userid, String requestIp) {
	    // 1. Get PPR
	    MPpr ppr = mPprRepo.findById(dto.getPprId()).orElseThrow(() ->
	                new RuntimeException("PPR not found"));

	    // 2. Get Micro Watershed
	    MicroWatershed microWatershed = microWatershedRepo.findById(dto.getMwId()).orElseThrow(() ->
	                new RuntimeException("Micro Watershed not found"));

	    // 3. Get Project Type
	    ProjectType projectType = projectTypeRepo.findById(dto.getProjectType()).orElseThrow(() ->
	                new RuntimeException("Project Type not found"));

	    // 4. Create PIA
	    MPiaDetails pia = new MPiaDetails();
	    pia.setPiaName(dto.getPiaName());
	    pia.setAddress(dto.getAddress());
	    pia.setCreatedBy(userid);
	    pia.setRequestIp(requestIp);

	    piaRepository.save(pia);

	    // 5. Create Project Glance
	    PprProjectGlance projectGlance = new PprProjectGlance();

	    projectGlance.setPpr(ppr);
	    projectGlance.setMicroWatershed(microWatershed);
	    projectGlance.setProjectType(projectType);
	    projectGlance.setPia(pia);

	    projectGlance.setSelectionReason(dto.getSelectionReason());
	    projectGlance.setProjectArea(dto.getProjectArea());
	    projectGlance.setProposedArea(dto.getProposedArea());
	    projectGlance.setProjectCost(dto.getProjectCost());
	    projectGlance.setComments(dto.getComments());
	    projectGlance.setStatus('D');
	    // 6. Create PprVillage records
	    List<PprVillage> villageList = new ArrayList<>();
	    if (dto.getVillages() != null) {
	        for (Integer vcode : dto.getVillages()) {
	            MVillage village = villageRepo.findById(vcode).orElseThrow(() ->
	                        new RuntimeException("Village not found: " + vcode));

	            PprVillage pprVillage = new PprVillage();
	            pprVillage.setVillage(village);
	            pprVillage.setProjectGlance(projectGlance);
	            pprVillage.setStatus('D');
	            pprVillage.setCreatedBy(userid);
	            pprVillage.setRequestIp(requestIp);
				
	            villageList.add(pprVillage);
	        }
	    }
	    // 7. Set villages
	    projectGlance.setVillages(villageList);
	    // 8. Save everything
	    pprProjectGlanceRepo.save(projectGlance);
	}
	
	public PprProjectAtGlanceDTO getPprProjectGlanceById(Integer id) {

	    PprProjectGlance entity = pprProjectGlanceRepo.findById(id).orElseThrow(() ->
	                            new RuntimeException("Project Glance not found: " + id));
	    PprProjectAtGlanceDTO dto = new PprProjectAtGlanceDTO();
	    dto.setPprProjectGlanceId(entity.getPprProjectGlanceId());
	    dto.setPprId(entity.getPpr().getPprId());
	    dto.setMwId(entity.getMicroWatershed().getMwId());
	    dto.setProjectType(entity.getProjectType().getProjectTypeId());
	    dto.setSelectionReason(entity.getSelectionReason());
	    dto.setProjectArea(entity.getProjectArea());
	    dto.setProposedArea(entity.getProposedArea());
	    dto.setProjectCost(entity.getProjectCost());
	    dto.setPiaName(entity.getPia().getPiaName());
	    dto.setAddress(entity.getPia().getAddress());
	    dto.setComments(entity.getComments());

	    List<Integer> villageIds = entity.getVillages().stream().map(pv -> pv.getVillage().getVcode()).toList();
	    dto.setVillages(villageIds);

	    return dto;
	}
	
	@Transactional
	public void updatePprProjectAtGlance(PprProjectAtGlanceDTO dto, String userid, String ip) {
	    PprProjectGlance projectGlance = pprProjectGlanceRepo.findById(dto.getPprProjectGlanceId()).orElseThrow(() ->
	                    new RuntimeException("Project Glance not found"));

	    // Update basic fields
	    projectGlance.setSelectionReason(dto.getSelectionReason());
	    projectGlance.setProjectArea(dto.getProjectArea());
	    projectGlance.setProposedArea(dto.getProposedArea());
	    projectGlance.setProjectCost(dto.getProjectCost());
	    projectGlance.setComments(dto.getComments());

	    // Update project type
	    ProjectType projectType = projectTypeRepo.findById(dto.getProjectType()).orElseThrow(() ->
	                    new RuntimeException("Project Type not found"));
	    projectGlance.setProjectType(projectType);

	    // Update Micro Watershed
	    MicroWatershed mw = microWatershedRepo.findById(dto.getMwId()).orElseThrow(() ->
	                    new RuntimeException("Micro Watershed not found"));
	    projectGlance.setMicroWatershed(mw);
	    
	    MPiaDetails pia = piaRepository.findById(projectGlance.getPia().getPiaId()).orElseThrow(() ->
        				new RuntimeException("Pia not found"));
	    pia.setPiaName(dto.getPiaName());
	    pia.setAddress(dto.getAddress());
	    pia.setUpdatedBy(userid);
	    piaRepository.save(pia);
	    
	    projectGlance.getVillages().clear();
	    
	    if (dto.getVillages() != null) {
	        for (Integer vcode : dto.getVillages()) {
	            MVillage village = villageRepo.findById(vcode).orElseThrow(() ->
	                        new RuntimeException("Village not found: " + vcode));

	            PprVillage pprVillage = new PprVillage();
	            pprVillage.setVillage(village);
	            pprVillage.setProjectGlance(projectGlance);
	            pprVillage.setCreatedBy(userid);
	            projectGlance.getVillages().add(pprVillage);
	        }
	    }
	    pprProjectGlanceRepo.save(projectGlance);
	}
	
	@Transactional
	public void deletePprProjectGlance(Integer id) {
	    PprProjectGlance data = pprProjectGlanceRepo.findById(id).orElseThrow(() ->
					new RuntimeException("Project Glance not found"));
	    if (data != null) {
	    	MPiaDetails pia = piaRepository.findById(data.getPia().getPiaId()).orElseThrow(() -> new RuntimeException("Pia not found"));
	    	List<PprVillage> villList = pprVillageRepo.findByProjectGlance_PprProjectGlanceId(id);
		    for(PprVillage village : villList) {
		    	if(village != null)
		    		pprVillageRepo.delete(village);
		    }
	        pprProjectGlanceRepo.delete(data);
	        if(pia != null)
	        	piaRepository.delete(pia);
	    }
	}
	
	@Transactional
	public void completePprProjectGlance(Integer id) {
	    PprProjectGlance data = pprProjectGlanceRepo.findById(id).orElse(null);
	    if (data != null) {
	        data.setStatus('C');
	        pprProjectGlanceRepo.save(data);

	        List<PprVillage> villages = pprVillageRepo.findByProjectGlance_PprProjectGlanceId(id);
	        for (PprVillage village : villages) {
	            village.setStatus('C');
	        }

	        pprVillageRepo.saveAll(villages);
	    }
	}
	
	

}
