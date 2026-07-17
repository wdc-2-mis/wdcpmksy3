package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.entity.MemberDetails;
import gov.dolr.wdcpmksy3.repository.MemberDetailsRepository;

@Service
public class MemberDetailsService {
	
	@Autowired
	MemberDetailsRepository memberDetailsRepo;
	
	public List<MemberDetails> getAllMemberDetails(){
		List<MemberDetails> list = memberDetailsRepo.findAll();
		return list;
	}

	@SuppressWarnings("deprecation")
	public MemberDetails getMemberDetailsById(Integer memberType) {
		// TODO Auto-generated method stub
		return memberDetailsRepo.getById(memberType);
	}

}
