package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;

@Service
public class VillageService {

    @Autowired
    private VillageRepository villageRepository;

    public List<MVillage> getVillagesByProject(Integer pprId){
        return villageRepository.getVillagesByProject(pprId);
    }
    
    public MVillage getVillageById(Integer id){

        return villageRepository.findById(id).orElse(null);

    }

}