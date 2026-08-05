package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MBlock;
import gov.dolr.wdcpmksy3.entity.MVillage;


@Repository
public interface MBlockRepository extends JpaRepository<MBlock, Integer> {
	
	List<MBlock> findByBcodeIn(List<Integer> bcodes);
	
	 @Query(value="select b.* from m_block b JOIN ppr_proposed_area pa on pa.bcode = b.bcode join m_ppr m on m.ppr_id = pa.ppr_id "
	 		+ "where m.ppr_id=:pprId order by b.block_name",nativeQuery=true)
		    List<MBlock> getBlocksByProject(Integer pprId);
}

