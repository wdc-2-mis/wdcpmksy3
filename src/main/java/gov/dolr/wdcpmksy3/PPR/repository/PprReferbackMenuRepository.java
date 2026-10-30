package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.PprReferbackMenu;

public interface PprReferbackMenuRepository
        extends JpaRepository<PprReferbackMenu, Integer> {

    List<PprReferbackMenu> findByPprId(Integer pprId);
    
    
}
