package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionary;

public interface PprWcdcFunctionaryRepository extends JpaRepository<PprWcdcFunctionary, Integer>{
	
	
	@Query(value = "select f.ppr_wcdc_fun_id, f.designation_id, de.designation_name, f.qualification_id, q.qualification_name, wcdc_fun_fname, "
			+ "wcdc_fun_lname, work_allocation, tot_budget_wcdc_recurring, tot_budget_wcdc_non_recurring, dolr_fund_recurring, dolr_fund_non_recurring, f.status, "
			+ "ppr_wcdc_fun_exp_id, office_name, address, work_exp_yrs, work_exp_days, work_details, d.dist_name FROM ppr_wcdc_functionary_work_experience e "
			+ "join ppr_wcdc_functionary f on e.ppr_wcdc_fun_id=f.ppr_wcdc_fun_id join ppr_wcdc_details w  on f.ppr_wcdc_id=w.ppr_wcdc_id JOIN m_district d ON d.dcode = w.dcode "
			+ "join m_designation de on f.designation_id=de.designation_id join m_qualification q on f.qualification_id=q.qualification_id where d.st_code=:stcode order by d.dist_name", nativeQuery = true)
	List<Object[]> getWcdcFunctionariesList(@Param("stcode") int stcode);
}
