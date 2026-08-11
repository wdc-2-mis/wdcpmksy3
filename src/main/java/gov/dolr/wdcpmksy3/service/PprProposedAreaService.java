package gov.dolr.wdcpmksy3.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.MScheme;
import gov.dolr.wdcpmksy3.entity.PprProposedArea;
import gov.dolr.wdcpmksy3.repository.MSchemeRepository;
import gov.dolr.wdcpmksy3.repository.PprProposedAreaRepository;
import java.util.List;
@Service
public class PprProposedAreaService {

    @Autowired
    private MSchemeRepository schemeRepository;

    @Autowired
    private PprProposedAreaRepository repository;

    @Transactional
    public void saveDraft(PprProposedArea area, String scheme) {

        MScheme mScheme = schemeRepository.findBySchemeName(scheme);

        area.setScheme(mScheme);

        area.setStatus('D');

        repository.save(area);
    }
    
    public List<PprProposedArea> getPPR8List(Integer pprId) {

        return repository.findByPprPprIdAndStatus(pprId, 'D');

    }
}