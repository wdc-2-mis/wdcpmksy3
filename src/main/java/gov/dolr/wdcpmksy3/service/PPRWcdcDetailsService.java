package gov.dolr.wdcpmksy3.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.PPRWcdcDetails;

public interface PPRWcdcDetailsService {

    List<Object[]> getPPR4List(Integer stcode);

    boolean completeRecordPPR4(Integer id);

    public PPRWcdcDetails getById(Integer id);
       
    public void delete(Integer id) ;
    
    void saveWCDCFunctionary(Integer district, String fname, String lname, Integer designation, Integer qualification, String workallocation,
            BigDecimal slr, BigDecimal slnr,BigDecimal dlr, BigDecimal dlnr,String[] officename, String[] address,
            Integer[] yr, Integer[] day, String[] workdetail, String status, String userid, String ip);
    
    void UpdateWCDCFunctionary(Integer pprwcdcFunId, Integer district, String fname, String lname, Integer designation, Integer qualification, String workallocation,
            BigDecimal slr, BigDecimal slnr,BigDecimal dlr, BigDecimal dlnr,String[] officename, String[] address,
            Integer[] yr, Integer[] day, String[] workdetail, String status, String userid, String ip);
    
   
}