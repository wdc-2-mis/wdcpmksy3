package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.entity.MGramPanchayat;
import gov.dolr.wdcpmksy3.entity.MBlock;


public interface MGramPanchayatRepository extends JpaRepository<MGramPanchayat, Integer>{
	
	@Query("SELECT gp FROM MGramPanchayat gp WHERE gp.block.bcode = :blockcode")
	public List<MGramPanchayat> getListMGramPanchayatByBlock(@Param("blockcode") Integer blockcode);

}
