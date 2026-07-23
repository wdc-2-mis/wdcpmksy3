package gov.dolr.wdcpmksy3.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public interface SlnaFunctionaryService {
	
	 void saveFunctionary(Integer pprInstStrId, String level, String fname, String lname, Integer designation, Integer qualification, String workallocation,
	            BigDecimal slr, BigDecimal slnr,BigDecimal dlr, BigDecimal dlnr,String[] officename, String[] address,
	            Integer[] yr,Integer[] day,String[] workdetail, String status,String user, String ip);


}
