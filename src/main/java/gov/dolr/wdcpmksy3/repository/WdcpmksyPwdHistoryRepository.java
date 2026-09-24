package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyPwdHistory;

@Repository
public interface WdcpmksyPwdHistoryRepository extends JpaRepository<WdcpmksyPwdHistory, Long>{
	
	List<WdcpmksyPwdHistory> findTop3ByRegIdOrderByChangedDateDesc(Integer regId);

}
