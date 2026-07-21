package gov.dolr.wdcpmksy3.service;

import java.math.BigDecimal;

public interface SlnaFunctionaryService {
	
	 void saveFunctionary(Integer pprInstStrId, String name, Integer designation, Integer qualification, String workallocation,
	            BigDecimal slr, BigDecimal slnr,BigDecimal dlr, BigDecimal dlnr,String[] officename, String[] address,
	            Integer[] yr,Integer[] day,String[] workdetail, String status,String user, String ip);


}
