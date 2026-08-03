package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.PPRWcdcDetails;

public interface PPRWcdcDetailsRepository extends JpaRepository<PPRWcdcDetails, Integer>{
	
	@Query(value =
	        "SELECT w.ppr_wcdc_id, " +
	        "d.dist_name, " +
	        "w.executing_agency, " +
	        "w.chairman_status, " +
	        "TO_CHAR(w.mou_date,'DD/MM/YYYY') AS mou_date, " +
	        "w.mou_file, " +
	        "w.status " +
	        "FROM ppr_wcdc_details w " +
	        "JOIN m_district d ON d.dcode = w.dcode " +
	        "WHERE d.st_code = :stcode order by dist_name" ,
	        nativeQuery = true)
	    List<Object[]> getPPR4List(@Param("stcode") Integer stcode);
	    
	    @Modifying
	    @Transactional
	    @Query("UPDATE PPRWcdcDetails i SET i.status = 'C' WHERE i.id = :id")
	    int completeRecordPPR4(@Param("id") Integer id);
	    
	    boolean existsByDcode(Integer dcode);
	    
	    @Query(value ="select ppr_wcdc_id, dcode, status from ppr_wcdc_details where status='C' and dcode=:district", nativeQuery = true)
		List<Object[]> getPPR4BWCDCList( Integer district);

}
