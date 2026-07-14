package gov.dolr.wdcpmksy3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.repository.InstitutionalStructureRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstitutionalStructureServiceImpl implements InstitutionalStructureService{

    @Autowired
    InstitutionalStructureRepository repository;

    @Override
    public void save(InstitutionalStructure structure) {

        repository.save(structure);

    }
    
    public List<Object[]> getPPR1List(int stcode) {
    	
        return repository.getPPR1List(stcode);
    }
    
    public InstitutionalStructure getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    @Transactional
    public boolean completeRecordPPR1(Long id) {
        return repository.completeRecordPPR1(id) > 0;
    }
    
    @Transactional
    public boolean completeRecord1(Long id) {

        Optional<InstitutionalStructure> optional = repository.findById(id);

        if (optional.isPresent()) {

            InstitutionalStructure data = optional.get();
            data.setStatus("C");
            repository.save(data);

            return true;
        }

        return false;
    }
    
    
    
    
    
    

}