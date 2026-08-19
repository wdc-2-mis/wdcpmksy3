package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.MMonth;

public interface MonthRepository extends JpaRepository<MMonth, Integer> {

	List<MMonth> findAllByOrderByMonthIdAsc();
	
}
