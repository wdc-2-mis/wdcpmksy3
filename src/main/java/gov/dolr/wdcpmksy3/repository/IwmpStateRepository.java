package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.IwmpState;
import gov.dolr.wdcpmksy3.entity.MState;

@Repository
public interface IwmpStateRepository extends JpaRepository<MState, Integer> {

    List<MState> findAllByWdcpmksyOrderByStNameAsc(int i);
}
