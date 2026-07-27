package gov.dolr.wdcpmksy3.service;

import java.util.List;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.PPRWcdcDetails;

public interface PPRWcdcDetailsService {

    List<Object[]> getPPR4List(Integer stcode);

    boolean completeRecordPPR4(Integer id);

    public PPRWcdcDetails getById(Integer id);
       
    public void delete(Integer id) ;
}