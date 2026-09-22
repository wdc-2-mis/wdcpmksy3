package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MProject;

@Repository
public interface MProjectRepository extends JpaRepository<MProject, Integer> {

    List<MProject> findByDistrict_DcodeOrderByProjNameAsc(Integer dcode);

}
