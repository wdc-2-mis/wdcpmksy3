package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.entity.MGramPanchayat;
import gov.dolr.wdcpmksy3.entity.MBlock;


public interface MGramPanchayatRepository extends JpaRepository<MGramPanchayat, Integer>{
	
	public List<MGramPanchayat> getListMGramPanchayatByBlock(Integer block);

}
