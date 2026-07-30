package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;

public interface InstitutionalStructureRepository extends JpaRepository<InstitutionalStructure, Long>{
	
	
	 	@Query(value = "select ppr_inst_str_id, st_code, (select st_name from iwmp_state where st_code=ins.st_code) as statename, slna_type, "
	 		+ "TO_CHAR(ins.notification_date, 'DD/MM/YYYY') as notification_date, notification_file, TO_CHAR(ins.mou_date, 'DD/MM/YYYY') as mou_date, "
	 		+ "mou_file, status from ppr_slna_institutional_structure ins where st_code=:stcode",nativeQuery = true)
	    List<Object[]> getPPR1List(@Param("stcode") int stcode);
	    
	    
	    @Modifying
	    @Transactional
	    @Query("UPDATE InstitutionalStructure i SET i.status = 'C' WHERE i.id = :id")
	    int completeRecordPPR1(@Param("id") Long id);
	    
	    boolean existsByStCode(Integer stCode);


		InstitutionalStructure findByStCode(Integer stCode);
	    
	    

}