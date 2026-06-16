package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.IwmpDistrict;

@Repository
public interface IwmpDistrictRepository extends JpaRepository<IwmpDistrict, Integer> {

    List<IwmpDistrict> findByState_StCodeOrderByDistNameAsc(Integer stCode);
}
