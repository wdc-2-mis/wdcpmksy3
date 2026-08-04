package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.entity.MFinYear;
import gov.dolr.wdcpmksy3.PPR.repository.WdcpmksyMFinYearRepository;

@Service
public class FinYearService {

    @Autowired
    private WdcpmksyMFinYearRepository finYearRepository;

    public List<MFinYear> getFinYearCdAndDesc() {
        return finYearRepository.findAll(); 
    }
    
   
}
