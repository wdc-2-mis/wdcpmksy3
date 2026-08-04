package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MVillage;

@Repository
public interface VillageRepository extends JpaRepository<MVillage,Integer>{

    @Query(value="""
        select distinct v.*
        from m_village v
        join ppr_village pv
             on pv.vcode=v.vcode
        join ppr_project_glance pg
             on pg.ppr_project_glance_id=pv.ppr_project_glance_id
        where pg.ppr_id=:pprId
        order by v.village_name
        """,nativeQuery=true)
    List<MVillage> getVillagesByProject(Integer pprId);

}