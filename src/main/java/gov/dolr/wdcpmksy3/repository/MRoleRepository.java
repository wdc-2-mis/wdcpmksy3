package gov.dolr.wdcpmksy3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MRole;

@Repository
public interface MRoleRepository   extends JpaRepository<MRole, Integer> {

    List<MRole> findAllByOrderByRoleNameAsc();

}