package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.entity.MVillage;

@Repository
public interface VillageRepository extends JpaRepository<MVillage,Integer>{

    @Query(value="select distinct v.* from m_village v join ppr_village pv on pv.vcode=v.vcode join ppr_project_glance pg "
    		+ "on pg.ppr_project_glance_id=pv.ppr_project_glance_id where pg.ppr_id=:pprId and pg.status='C' order by v.village_name",nativeQuery=true)
    List<MVillage> getVillagesByProject(Integer pprId);
    
    @Query(value="select distinct v.* from m_village v join ppr_village pv on pv.vcode=v.vcode join ppr_project_glance pg on pg.ppr_project_glance_id=pv.ppr_project_glance_id "
    		+ "	join m_ppr m ON m.ppr_id = pg.ppr_id join m_district d ON d.dcode = m.dcode where d.st_code=:stcode and pg.status='C' order by v.village_name",nativeQuery=true)
    List<MVillage> getVillagesByState(Integer stcode);
    
    @Query("SELECT v FROM MVillage v WHERE v.gramPanchayat.block.district.dcode = :dcode")
    List<MVillage> findVillagesByDistrict(Integer dcode);
    
    List<MVillage> findByGramPanchayat_Gcode(Integer gcode);
    
    
    

}