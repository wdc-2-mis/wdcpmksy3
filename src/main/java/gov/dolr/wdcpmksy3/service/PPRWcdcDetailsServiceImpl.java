package gov.dolr.wdcpmksy3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.entity.PPRWcdcDetails;
import gov.dolr.wdcpmksy3.repository.PPRWcdcDetailsRepository;

@Service
public class PPRWcdcDetailsServiceImpl implements PPRWcdcDetailsService {

    @Autowired
    private PPRWcdcDetailsRepository repository;

    @Override
	public List<Object[]> getPPR4List(Integer stcode) {
		// TODO Auto-generated method stub
		return repository.getPPR4List(stcode);
	}
	
	@Override
	@Transactional
	public boolean completeRecordPPR4(Integer id) {

		 return repository.completeRecordPPR4(id) > 0;
	}

	@Override
	public PPRWcdcDetails getById(Integer id) {
		
		        return repository.findById(id).orElse(null);
		    
	}

	@Override
	public void delete(Integer id) {
        repository.deleteById(id);
    }

	
	
   
}