package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.IwmpMFinYear;
import gov.dolr.wdcpmksy3.PPR.repository.IwmpMFinYearRepository;

@Service
public class FinYearService {

    @Autowired
    private IwmpMFinYearRepository finYearRepository;

    public List<IwmpMFinYear> getFinYearCdAndDesc() {
        return finYearRepository.findAll(); 
    }
    
   
}
