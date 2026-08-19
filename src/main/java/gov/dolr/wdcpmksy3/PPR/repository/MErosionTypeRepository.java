package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.MErosionType;

@Repository
public interface MErosionTypeRepository extends JpaRepository<MErosionType, Integer> {
	
	List<MErosionType> findByErosionErosionId(Integer erosionId);
	
}
