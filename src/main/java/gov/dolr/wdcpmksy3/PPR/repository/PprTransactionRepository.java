package gov.dolr.wdcpmksy3.PPR.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.dolr.wdcpmksy3.PPR.entity.PprTransaction;

public interface PprTransactionRepository extends JpaRepository<PprTransaction, Integer> {

    List<PprTransaction> findByPprPprId(Integer pprId);

    List<PprTransaction> findBySentToRegId(Integer regId);

    List<PprTransaction> findBySentFromRegId(Integer regId);
}