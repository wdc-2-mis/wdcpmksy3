package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MBlock;


@Repository
public interface MBlockRepository extends JpaRepository<MBlock, Integer> {
	
	List<MBlock> findByBcodeIn(List<Integer> bcodes);
}

