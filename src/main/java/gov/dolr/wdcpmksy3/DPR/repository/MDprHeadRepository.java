package gov.dolr.wdcpmksy3.DPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.dolr.wdcpmksy3.DPR.entity.MDprHead;

@Repository
public interface MDprHeadRepository extends JpaRepository<MDprHead, Integer> {

    List<MDprHead> findAllByOrderByHeadTypeAsc();
}