package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.PPRSoilErosionDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MErosion;
import gov.dolr.wdcpmksy3.PPR.entity.MErosionType;
import gov.dolr.wdcpmksy3.PPR.entity.MFinYear;
import gov.dolr.wdcpmksy3.PPR.entity.MMonth;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PPRSoilErosion;
import gov.dolr.wdcpmksy3.PPR.repository.MErosionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MErosionTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MonthRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PPRSoilErosionRepository;
import gov.dolr.wdcpmksy3.PPR.repository.WdcpmksyMFinYearRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PPRSoilErosionService {
	
	@Autowired
    private MPprRepository pprRepository;
	
	@Autowired
    private MErosionRepository erosionRepository;

    @Autowired
    private MErosionTypeRepository erosionTypeRepository;

    @Autowired
    private PPRSoilErosionRepository soilErosionRepository;
    
    @Autowired
    private MonthRepository monthRepository;
    
    @Autowired
    private WdcpmksyMFinYearRepository finYearRepository;
    
    
    
    public List<MErosion> getErosionList() {

        return erosionRepository.findAll();

    }

    public List<MErosionType> getErosionTypes(Integer erosionId) {

        return erosionTypeRepository.findByErosionErosionId(erosionId);

    }
    
    public String getErosionTypeNames(List<Integer> typeIds) {
        if (typeIds == null || typeIds.isEmpty()) {
            return "";
        }
        
        List<MErosionType> types = erosionTypeRepository.findAllById(typeIds);
        return types.stream()
            .map(MErosionType::getErosionType)
            .collect(Collectors.joining(", "));
    }
    
    public List<Integer> findDuplicateErosionTypes(Integer dcode, List<PPRSoilErosionDTO> erosionList) {

        MPpr ppr = pprRepository.findByDistrict_Dcode(dcode);

        if (ppr == null) {
            throw new RuntimeException("PPR not found for district code: " + dcode);
        }

        List<Integer> duplicates = new ArrayList<>();

        for (PPRSoilErosionDTO dto : erosionList) {

            if (dto.getErosionTypeId() == null) {
                continue;
            }

            if (dto.getAffectedArea() == null &&
                dto.getRunoff() == null &&
                dto.getAvgSoilLoss() == null) {
                continue;
            }
            
            if (dto.getMonthId() == null) {
                throw new RuntimeException("Month is required.");
            }

            if (dto.getYearId() == null) {
                throw new RuntimeException("Financial year is required.");
            }

            boolean exists = soilErosionRepository.existsByPprPprIdAndErosionTypeErosionTypeIdAndMonthMonthIdAndYearFinYrCd(
                        ppr.getPprId(), dto.getErosionTypeId(), dto.getMonthId(), dto.getYearId());

            if (exists) {
                duplicates.add(dto.getErosionTypeId());
            }
        }

        return duplicates;
    }
    
    public List<MMonth> getAllMonths() {
        return monthRepository.findAllByOrderByMonthIdAsc();
    }
    
    public List<MFinYear> getAllYears() {
        return finYearRepository.findAllByOrderByYearDesc();
    }
    
    @Transactional
    public void saveSoilErosion(Integer dcode, List<PPRSoilErosionDTO> erosionList, String userId, HttpServletRequest request) {

        MPpr ppr = pprRepository.findByDistrict_Dcode(dcode);

        if (ppr == null) {

            throw new RuntimeException("PPR not found for district code: " + dcode);
        }

        Integer pprId = ppr.getPprId();

        System.out.println("District Code = " + dcode);
        System.out.println("PPR ID = " + pprId);

        if (erosionList == null || erosionList.isEmpty()) {

            throw new RuntimeException("No soil erosion data received.");
        }


        for (PPRSoilErosionDTO dto : erosionList) {

            if (dto.getErosionTypeId() == null) {
                continue;
            }

            if (dto.getAffectedArea() == null && dto.getRunoff() == null && dto.getAvgSoilLoss() == null) {

                continue;
            }

            MErosionType erosionType = erosionTypeRepository.findById(dto.getErosionTypeId()).orElseThrow(() ->
                                    new RuntimeException("Erosion type not found: " + dto.getErosionTypeId()));
            
            MMonth month = monthRepository.findById(dto.getMonthId()).orElseThrow(() -> new RuntimeException("Month not found"));
            
            MFinYear year = finYearRepository.findById(dto.getYearId()).orElseThrow(() -> new RuntimeException("Financial year not found"));


            PPRSoilErosion entity = new PPRSoilErosion();


            entity.setPpr(ppr);

            entity.setErosionType(erosionType);

            entity.setAffectedArea(dto.getAffectedArea());

            entity.setRunoff(dto.getRunoff());

            entity.setAvgSoilLoss(dto.getAvgSoilLoss());
            
            entity.setMonth(month);
            
            entity.setYear(year);
            
            entity.setStatus('D');
            
            entity.setCreatedBy(userId);
            
            entity.setCreatedDate(LocalDateTime.now());

            entity.setRequestIp(request.getRemoteAddr());

            soilErosionRepository.save(entity);
        }
    }
    
    @Transactional
    public void updateSoilErosion(Integer id, BigDecimal affectedArea, BigDecimal runoff, BigDecimal avgSoilLoss, Integer monthId, 
    		Integer yearId, String userId) {
        
    	PPRSoilErosion entity = getById(id);
        
    	if (entity == null) {
            throw new RuntimeException("Record not found");
        }
    	
    	if (monthId == null) {
            throw new RuntimeException("Month is required.");
        }

        if (yearId == null) {
            throw new RuntimeException("Financial year is required.");
        }
        
    	
        
        MMonth month = monthRepository.findById(monthId).orElseThrow(() -> new RuntimeException("Month not found"));

        MFinYear year = finYearRepository.findById(yearId).orElseThrow(() -> new RuntimeException("Financial year not found"));

        
        entity.setAffectedArea(affectedArea);
        entity.setRunoff(runoff);
        entity.setAvgSoilLoss(avgSoilLoss);
        entity.setMonth(month);
        entity.setYear(year);
        entity.setUpdatedBy(userId);
        entity.setUpdatedDate(LocalDate.now());
        
        soilErosionRepository.save(entity);
    }
    
    public PPRSoilErosion save(PPRSoilErosion entity) {
    	
        return soilErosionRepository.save(entity);
        
    }

    public List<Map<String, Object>> getSoilErosionByDistrict(Integer dcode) {
    	
        return soilErosionRepository.getSoilErosionByDistrict(dcode);
        
    }
    
    public List<PPRSoilErosion> getByPprId(Integer pprId) {
    	
        return soilErosionRepository.findByPprPprId(pprId);
        
    }

    public PPRSoilErosion getById(Integer id) {
    	
        return soilErosionRepository.findById(id).orElse(null);
        
    }

    public void delete(Integer id) {
    	
        soilErosionRepository.deleteById(id);
        
    }
    
	
}
