package gov.dolr.wdcpmksy3.PPR.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.PprReferbackMenuStatusDTO;
import gov.dolr.wdcpmksy3.PPR.repository.PprReferbackMenuRepository;

@Service
public class PprReferbackMenuService {
	
	@Autowired
	private PprReferbackMenuRepository pprReferbackMenuRepository;

    public PprReferbackMenuService(PprReferbackMenuRepository pprReferbackMenuRepository) {

        this.pprReferbackMenuRepository = pprReferbackMenuRepository;
    }
	
	
	public List<PprReferbackMenuStatusDTO> getPprReferbackMenuStatus(
            Integer pprid) {

        return pprReferbackMenuRepository.getPprReferbackMenuStatus(pprid);
    }

}
