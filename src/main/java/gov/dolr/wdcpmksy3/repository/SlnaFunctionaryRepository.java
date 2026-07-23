package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import gov.dolr.wdcpmksy3.entity.SlnaFunctionary;

@Repository
public interface SlnaFunctionaryRepository extends JpaRepository<SlnaFunctionary, Integer>{
	
	
	@Query(value = "SELECT f.ppr_slna_fun_id, f.ppr_inst_str_id, f.slna_fun_fname, d.designation_name, q.qualification_name, f.work_allocation, "
			+ "f.tot_budget_slna_recurring, f.tot_budget_slna_non_recurring, f.dolr_fund_recurring, f.dolr_fund_non_recurring, f.status, e.ppr_slna_fun_exp_id, "
			+ "e.office_name, e.address, e.work_exp_yrs, e.work_exp_days, e.work_details, f.slna_fun_lname, f.level FROM ppr_slna_institutional_structure i inner join ppr_slna_functionary f "
			+ "on f.ppr_inst_str_id = i.ppr_inst_str_id INNER JOIN m_designation d  ON d.designation_id = f.designation_id INNER JOIN m_qualification q "
			+ "ON q.qualification_id = f.qualification_id LEFT JOIN ppr_slna_functionary_work_experience e ON e.ppr_slna_fun_id = f.ppr_slna_fun_id "
			+ "WHERE i.st_code =:stcode ORDER BY f.ppr_slna_fun_id DESC, e.ppr_slna_fun_exp_id ASC", nativeQuery = true)
	    List<Object[]> getFunctionariesList(@Param("stcode") int stcode);
	    
	    @Transactional
	    @Modifying
	    @Query("UPDATE SlnaFunctionary s SET s.status = 'C' WHERE s.pprSlnaFunId = :id")
	    int updateStatus(@Param("id") Integer id);

}