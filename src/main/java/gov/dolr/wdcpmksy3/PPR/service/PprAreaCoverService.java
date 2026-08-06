package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.PPR.dto.CoveredAreaDTO;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MScheme;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprWatershedCoveredArea;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAreaCoveredRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprWatershedCoveredAreaRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PprAreaCoverService {
	
	@Autowired
	private PprAreaCoveredRepository repo;

	@Autowired
	private PprWatershedCoveredAreaRepo wcarearepo;
	
	@Autowired 
	private MPprRepository pprRepo;
	
	public List<MScheme> getAllSchemes() {
        return repo.findAll();
    }


	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	public void saveRecords(Integer district, Integer mw, Map<String, String> params, String userId,
			HttpServletRequest servletRequest) {
        List<PprWatershedCoveredArea> entities = new ArrayList<>();

        params.forEach((key, value) -> {
            if (key.startsWith("scheme[") && key.endsWith("].no")) {
                String schemeIdStr = key.substring(7, key.indexOf("].no"));
                Integer schemeId = Integer.valueOf(schemeIdStr);

                String noStr = value;
                String areaStr = params.get("scheme[" + schemeId + "].area");

                PprWatershedCoveredArea entity = new PprWatershedCoveredArea();
                MPpr ppr = pprRepo.findByDistrict_Dcode(district);
                
                
                MicroWatershed mwEntity = new MicroWatershed();
                mwEntity.setMwId(mw);

                MScheme scheme = new MScheme();
                scheme.setSchemeId(schemeId);
                
                entity.setPpr(ppr); 
                entity.setMicroWatershed(mwEntity);
                entity.setScheme(scheme);
                entity.setNoMw(noStr != null && !noStr.isEmpty() ? Integer.valueOf(noStr) : null);
                entity.setAreaMw(areaStr != null && !areaStr.isEmpty() ? new BigDecimal(areaStr) : null);
                entity.setStatus("D"); 
                entity.setCreatedBy(userId); 
                entity.setRequestIp(getClientIpAddr(servletRequest)); 
                entity.setCreatedDate(LocalDateTime.now());
                entities.add(entity);
            }
        });

        wcarearepo.saveAll(entities);
    }


	/*
	 * public List<CoveredAreaDTO> getRecordsByDistrict(Integer dcode) { MPpr ppr =
	 * pprRepo.findByDistrict_Dcode(dcode);
	 * 
	 * List<PprWatershedCoveredArea> entities = wcarearepo.findByPpr(ppr);
	 * 
	 * return entities.stream().map(e -> { CoveredAreaDTO dto = new
	 * CoveredAreaDTO(); dto.setId(e.getId()); dto.setPprId(e.getPpr().getPprId());
	 * dto.setMwId(e.getMicroWatershed().getMwId());
	 * dto.setSchemeId(e.getScheme().getSchemeId()); dto.setNoMw(e.getNoMw());
	 * dto.setAreaMw(e.getAreaMw()); dto.setStatus(e.getStatus());
	 * dto.setCreatedBy(e.getCreatedBy()); return dto;
	 * }).collect(Collectors.toList()); }
	 */


	public List<CoveredAreaDTO> getSchemeAreasByDistrict(Integer dcode) {
	    List<Object[]> rows = repo.findWatershedDataByDistrict(dcode);

	    return rows.stream().map(r -> {
	        CoveredAreaDTO dto = new CoveredAreaDTO();
	        dto.setPprId(((Number) r[0]).intValue());
	        dto.setDist_name((String) r[1]);
	        dto.setMw_id(((Number) r[2]).intValue());
	        dto.setMw_name((String) r[3]);
	        dto.setMw_area(r[4] != null ? new BigDecimal(r[4].toString()) : null);
	        dto.setPre_scheme((String) r[5]);
	        dto.setPre_no(r[6] != null ? ((Number) r[6]).intValue() : null);
	        dto.setPre_area(r[7] != null ? new BigDecimal(r[7].toString()) : null);

	        dto.setDpap_scheme((String) r[8]);
	        dto.setDpap_no(r[9] != null ? ((Number) r[9]).intValue() : null);
	        dto.setDpap_area(r[10] != null ? new BigDecimal(r[10].toString()) : null);

	        dto.setDdp_scheme((String) r[11]);
	        dto.setDdp_no(r[12] != null ? ((Number) r[12]).intValue() : null);
	        dto.setDdp_area(r[13] != null ? new BigDecimal(r[13].toString()) : null);

	        dto.setIwdp_scheme((String) r[14]);
	        dto.setIwdp_no(r[15] != null ? ((Number) r[15]).intValue() : null);
	        dto.setIwdp_area(r[16] != null ? new BigDecimal(r[16].toString()) : null);

	        dto.setIwmp_scheme((String) r[17]);
	        dto.setIwmp_no(r[18] != null ? ((Number) r[18]).intValue() : null);
	        dto.setIwmp_area(r[19] != null ? new BigDecimal(r[19].toString()) : null);

	        dto.setWdc_scheme((String) r[20]);
	        dto.setWdc_no(r[21] != null ? ((Number) r[21]).intValue() : null);
	        dto.setWdc_area(r[22] != null ? new BigDecimal(r[22].toString()) : null);
	        
	         
	        dto.setOther_scheme((String) r[23]);
	        dto.setOther_no(r[24] != null ? ((Number) r[24]).intValue() : null);
	        dto.setOther_area(r[25] != null ? new BigDecimal(r[25].toString()) : null);

	        dto.setStatus((String) r[26]);
	        
	        
	        
	        return dto;
	    }).collect(Collectors.toList());
	}


	@Transactional
	public void updateRecords(Integer pprId,
	                          Integer mwId,
	                          Map<String, String> params,
	                          String userId,
	                          HttpServletRequest request) {

	    params.forEach((key, value) -> {

	        if (key.startsWith("scheme[") && key.endsWith("].no")) {

	            Integer schemeId = Integer.parseInt(
	                    key.substring(7, key.indexOf("].no"))
	            );

	            String noValue = value;
	            String areaValue = params.get("scheme[" + schemeId + "].area");

	            // Skip if Scheme 7 is blank
	            if (schemeId == 7 &&
	                    (noValue == null || noValue.isBlank()) &&
	                    (areaValue == null || areaValue.isBlank())) {
	                return;
	            }

	            Optional<PprWatershedCoveredArea> optional =
	                    wcarearepo.findByPpr_PprIdAndMicroWatershed_MwIdAndScheme_SchemeId(
	                            pprId,
	                            mwId,
	                            schemeId
	                    );

	            PprWatershedCoveredArea entity;

	            if (optional.isPresent()) {

	                // Existing record
	                entity = optional.get();

	            } else {

	                // Create new record (mainly for Scheme 7)
	                entity = new PprWatershedCoveredArea();

	                MPpr ppr = new MPpr();
	                ppr.setPprId(pprId);
	                entity.setPpr(ppr);

	                MicroWatershed watershed = new MicroWatershed();
	                watershed.setMwId(mwId);
	                entity.setMicroWatershed(watershed);

	                MScheme scheme = new MScheme();
	                scheme.setSchemeId(schemeId);
	                entity.setScheme(scheme);

	                entity.setStatus("D");

	                entity.setCreatedBy(userId);
	                entity.setCreatedDate(LocalDateTime.now());
	                entity.setRequestIp(getClientIpAddr(request));
	            }

	            entity.setNoMw(
	                    (noValue == null || noValue.isBlank())
	                            ? null
	                            : Integer.valueOf(noValue)
	            );

	            entity.setAreaMw(
	                    (areaValue == null || areaValue.isBlank())
	                            ? null
	                            : new BigDecimal(areaValue)
	            );

	            entity.setUpdatedBy(userId);
	            entity.setUpdatedDate(new Date());
	            entity.setRequestIp(getClientIpAddr(request));

	            wcarearepo.save(entity);
	        }

	    });

	}


	@Transactional
	public void completeRecords(Integer pprId, Integer mwId, String userId, HttpServletRequest request) {

	    List<PprWatershedCoveredArea> records = wcarearepo.findByPpr_PprIdAndMicroWatershed_MwId(pprId, mwId);

	    if(records.isEmpty()) {
	        throw new RuntimeException("Record not found.");
	    }

	    for(PprWatershedCoveredArea entity : records) {

	        entity.setStatus("C");

	        entity.setUpdatedBy(userId);

	        entity.setUpdatedDate(new Date());

	        entity.setRequestIp(getClientIpAddr(request));
	    }

	    wcarearepo.saveAll(records);
	}


	@Transactional
	public void deleteRecords(Integer pprId,
	                          Integer mwId) {

	    List<PprWatershedCoveredArea> records =
	            wcarearepo.findByPpr_PprIdAndMicroWatershed_MwId(
	                    pprId,
	                    mwId);

	    if(records.isEmpty()) {
	        throw new RuntimeException("Record not found.");
	    }

	    wcarearepo.deleteAll(records);
	}


	public boolean isCompleted(Integer mwId) {

	    return wcarearepo.existsByMicroWatershed_MwIdAndStatus(
	            mwId,
	            "C"
	    );
	}





	

}
