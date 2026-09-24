package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MState;

@Repository
public interface MStateRepository extends JpaRepository<MState, Integer> {

    List<MState> findAllByWdcpmksyOrderByStNameAsc(int i);

	List<MState> findAllByOrderByStNameAsc();
	
	@Query(value =
            "select * from m_state " +
            "where st_code in " +
            "(select st_code from wdcpmksy_user_map where reg_id=:regid) " +
            "order by st_code",
            nativeQuery = true)
	List<MState> findAllByRegId(Integer regid);
}
