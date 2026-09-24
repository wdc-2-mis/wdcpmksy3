package gov.dolr.wdcpmksy3.repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface WdcpmksyMenuRepository extends JpaRepository<WdcpmksyMenu, Integer> {

    List<WdcpmksyMenu> findAllByOrderByHseqNoAsc();
    
    List<WdcpmksyMenu> findByIsActiveTrueOrderByHseqNoAsc();
    
    boolean existsByHseqNo(BigDecimal hseqNo);

    boolean existsByHseqNoAndMenuIdNot(BigDecimal hseqNo, Integer menuId);
    
}