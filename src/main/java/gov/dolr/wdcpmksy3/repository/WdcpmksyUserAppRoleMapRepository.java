package gov.dolr.wdcpmksy3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyUserAppRoleMap;

@Repository
public interface WdcpmksyUserAppRoleMapRepository
        extends JpaRepository<WdcpmksyUserAppRoleMap, Integer> {

    Optional<WdcpmksyUserAppRoleMap> findByRegId(Integer regId);

    boolean existsByRegId(Integer regId);

    void deleteByRegId(Integer regId);

}