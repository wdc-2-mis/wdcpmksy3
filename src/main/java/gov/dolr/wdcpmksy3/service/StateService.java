package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.IwmpState;
import gov.dolr.wdcpmksy3.repository.IwmpStateRepository;

@Service
public class StateService {

    @Autowired
    private IwmpStateRepository stateRepo;

    public List<IwmpState> getAllStates() {
        return stateRepo.findAllByOrderByStNameAsc();
    }
}
