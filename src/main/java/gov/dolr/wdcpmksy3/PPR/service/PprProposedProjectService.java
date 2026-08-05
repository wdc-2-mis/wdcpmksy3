package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.PprProposedProjectDto;
import gov.dolr.wdcpmksy3.PPR.entity.PprProposedProject;
import gov.dolr.wdcpmksy3.PPR.repository.CriteriaDetailsRepository;
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
    private CriteriaDetailsRepository criteriaDetailsRepository;

    public void save(PprProposedProjectDto form, Character status, String userId, String ipAddress) {
        PprProposedProject entity = new PprProposedProject();
        entity.setPpr(pprRepository.findById(form.getPprId()).orElseThrow(() -> new RuntimeException("Invalid PPR Id")));
        entity.setMicroWatershed(microWatershedRepository.findById(form.getMicroWatershed()).orElseThrow(() -> new RuntimeException("Invalid Micro Watershed")));

        entity.setProjectType(projectTypeRepository.findById(form.getProjectType()).orElseThrow(() -> new RuntimeException("Invalid Project Type")));
        // Replace with actual criteria id or logic
        entity.setCriteriaDetails(criteriaDetailsRepository.findById(1).orElseThrow(() -> new RuntimeException("Criteria not found")));
        entity.setTreatedArea(form.getTreatedProjectArea());
        entity.setProposedCost(form.getProposedCost());
        entity.setStatus(status);

        entity.setCreatedBy(userId);

        entity.setCreatedDate(LocalDateTime.now());

        entity.setRequestIp(ipAddress);

        repository.save(entity);
    }
}
