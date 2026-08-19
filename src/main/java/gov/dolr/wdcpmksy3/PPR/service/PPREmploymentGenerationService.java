package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.PPREmploymentGenerationDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MEmploymentType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PPREmploymentGeneration;
import gov.dolr.wdcpmksy3.PPR.repository.MEmploymentTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPREmploymentGenerationRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PPREmploymentGenerationService {

    @Autowired
    private PPREmploymentGenerationRepository repository;

    @Autowired
    private MEmploymentTypeRepository employmentTypeRepository;

    @Autowired
    private MPprRepository pprRepository;

    @Autowired
    private MicroWatershedRepository microWatershedRepository;

    @Autowired
    private VillageRepository villageRepository;




    public List<MicroWatershed> getMicroWatershedsByProject(Integer pprId) {

        return microWatershedRepository.getListOfMicroWatershedbyMwIds(pprId);

    }


    public List<MEmploymentType> getAllEmploymentTypes() {

        return employmentTypeRepository.findAllByOrderByEmploymentTypeId();

    }


    public List<MEmploymentType> getEmploymentTypes() {

        return employmentTypeRepository.findAllByOrderByEmploymentTypeId();

    }


    public List<MVillage> getVillagesByProject(Integer pprId) {

        return villageRepository.getVillagesByProject(pprId);

    }


    public List<Integer> findDuplicateEmploymentTypes(Integer pprId, Integer vcode, Integer mwId,
            List<PPREmploymentGenerationDTO> employmentList) {

        List<Integer> duplicates = new ArrayList<>();

        if (employmentList == null || employmentList.isEmpty()) {
            return duplicates;
        }

        for (PPREmploymentGenerationDTO dto : employmentList) {

            if (dto.getEmploymentTypeId() == null) {
                continue;
            }

            if (dto.getSc() == null && dto.getSt() == null && dto.getOthers() == null && dto.getWomen() == null) {
                continue;
            }

            boolean exists =
                    repository.existsByPprIdPprIdAndVillageVcodeAndMicroWatershedMwIdAndEmploymentTypeEmploymentTypeId(pprId,vcode,mwId,
                            dto.getEmploymentTypeId());

            if (exists) {
                duplicates.add(dto.getEmploymentTypeId());
            }
        }

        return duplicates;
    }


    public String getEmploymentTypeNames(List<Integer> typeIds) {

        if (typeIds == null || typeIds.isEmpty()) {
            return "";
        }

        List<MEmploymentType> types = employmentTypeRepository.findAllById(typeIds);

        StringBuilder names = new StringBuilder();

        for (MEmploymentType type : types) {

            if (names.length() > 0) {
                names.append(", ");
            }

            names.append(type.getEmploymentTypeName());
        }

        return names.toString();
    }


    @Transactional
    public void saveEmploymentGeneration(Integer dcode, Integer pprId, Integer mwId, Integer vcode,
            List<PPREmploymentGenerationDTO> employmentList, String userId, HttpServletRequest request) {


        if (dcode == null) {
            throw new RuntimeException("District code is missing.");
        }

        if (pprId == null) {
            throw new RuntimeException("Project is required.");
        }

        if (mwId == null) {
            throw new RuntimeException("Watershed is required.");
        }

        if (vcode == null) {
            throw new RuntimeException("Village is required.");
        }

        if (employmentList == null || employmentList.isEmpty()) {
            throw new RuntimeException("No employment data received.");
        }

        MPpr ppr = pprRepository.findById(pprId)
                .orElseThrow(() -> new RuntimeException("PPR not found for project ID: " + pprId));


        MVillage village = villageRepository.findById(vcode)
                .orElseThrow(() -> new RuntimeException("Village not found: " + vcode));


        MicroWatershed microWatershed = microWatershedRepository.findById(mwId)
        		.orElseThrow(() -> new RuntimeException("Micro watershed not found: " + mwId));


        for (PPREmploymentGenerationDTO dto : employmentList) {

            if (dto.getEmploymentTypeId() == null) {
                continue;
            }


            if (dto.getSc() == null && dto.getSt() == null && dto.getOthers() == null && dto.getWomen() == null) {
                continue;
            }


            MEmploymentType employmentType = employmentTypeRepository
                    .findById(dto.getEmploymentTypeId())
                    .orElseThrow(() -> new RuntimeException("Employment type not found: " + dto.getEmploymentTypeId()));


            PPREmploymentGeneration entity = new PPREmploymentGeneration();


            entity.setPprId(ppr);

            entity.setVillage(village);

            entity.setMicroWatershed(microWatershed);

            entity.setEmploymentType(employmentType);

            entity.setSc(dto.getSc());

            entity.setSt(dto.getSt());

            entity.setOthers(dto.getOthers());

            entity.setWomen(dto.getWomen());

            entity.setStatus('D');

            entity.setCreatedBy(userId);

            entity.setCreatedDate(LocalDateTime.now());

            entity.setRequestIp(request.getRemoteAddr());

            repository.save(entity);
        }
    }


    @Transactional
    public void updateEmploymentGeneration(Integer id, Integer sc, Integer st, Integer others, Integer women, String userId, 
    		HttpServletRequest request) {

        PPREmploymentGeneration entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employment generation record not found: " + id));

        entity.setSc(sc);

        entity.setSt(st);

        entity.setOthers(others);

        entity.setWomen(women);

        entity.setUpdatedBy(userId);

        entity.setUpdatedDate(LocalDate.now());

        entity.setRequestIp(request.getRemoteAddr());

        repository.save(entity);
    }


    public List<Map<String, Object>> getEmploymentGenerationByDistrict(Integer dcode) {

        return repository.getEmploymentGenerationByDistrict(dcode);

    }


    public PPREmploymentGeneration getById(Integer id) {

        return repository.findById(id).orElse(null);

    }

    public List<PPREmploymentGeneration> findExistingRecords(Integer pprId, Integer vcode, Integer mwId) {

        return repository.findExistingRecords(pprId, vcode, mwId);

    }



    public boolean exists(Integer pprId, Integer vcode, Integer mwId, Integer employmentTypeId) {

        return repository
                .existsByPprIdPprIdAndVillageVcodeAndMicroWatershedMwIdAndEmploymentTypeEmploymentTypeId(pprId, vcode, mwId, employmentTypeId);
        
    }


    public void delete(Integer id) {

        repository.deleteById(id);

    }


    @Transactional
    public void complete(Integer id, String userId, HttpServletRequest request) {


        PPREmploymentGeneration entity =
                repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employment generation record not found: " + id));


        entity.setStatus('C');

        entity.setUpdatedBy(userId);

        entity.setUpdatedDate(LocalDate.now());

        entity.setRequestIp(request.getRemoteAddr());


        repository.save(entity);
    }

    public PPREmploymentGeneration save(PPREmploymentGeneration entity) {

        return repository.save(entity);

    }

}