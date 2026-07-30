package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.PPR.entity.IwmpMFinYear;

@Repository
public interface IwmpMFinYearRepository extends JpaRepository<IwmpMFinYear, Integer> {

	List<IwmpMFinYear> findAll();
}

