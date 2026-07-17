package gov.dolr.wdcpmksy3.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gov.dolr.wdcpmksy3.entity.MemberDetails;

@Repository
public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Integer> {

}
