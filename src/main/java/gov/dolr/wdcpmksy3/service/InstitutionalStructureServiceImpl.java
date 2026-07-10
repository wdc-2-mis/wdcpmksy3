package gov.dolr.wdcpmksy3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;

@Service
public class InstitutionalStructureServiceImpl implements InstitutionalStructureService{

    @Autowired
    InstitutionalStructureRepository repository;

    @Override
    public void save(InstitutionalStructure structure) {

        repository.save(structure);

    }

}