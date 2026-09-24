package gov.dolr.wdcpmksy3.repository;

import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WdcpmksySubmenuRepository  extends JpaRepository<WdcpmksySubmenu, Integer> {
	
	
	 boolean existsBySeqNo(BigDecimal seqNo);

	 boolean existsBySeqNoAndSubmenuIdNot(BigDecimal seqNo, Integer submenuId);
}