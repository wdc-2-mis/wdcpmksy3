package gov.dolr.wdcpmksy3.PPR.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.CropType;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroCrop;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroSoil;
import gov.dolr.wdcpmksy3.PPR.entity.SoilType;
import gov.dolr.wdcpmksy3.PPR.repository.CropTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAgroClimateRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAgroCropRepository;
import gov.dolr.wdcpmksy3.PPR.repository.PprAgroSoilRepository;
import gov.dolr.wdcpmksy3.PPR.repository.SoilTypeRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.entity.PprWcdcFunctionary;

@Service
@Transactional
public class PPRAgroClimateConditionServices {
	
	@Autowired
    private CropTypeRepository ctrep;
	
	@Autowired
    private SoilTypeRepository strep;
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
    private PprAgroClimateRepository agcrepo;
	
	@Autowired
    private PprAgroCropRepository acrepo;
	
	@Autowired
    private PprAgroSoilRepository asrepo;
	
	@Autowired
    private VillageRepository villrepo;
	
	
	
	public boolean saveAgroClimateCondition(Integer pprId, Integer village, String zone, String graphy, BigDecimal rainfall, BigDecimal area, BigDecimal farea,
			Integer soilType, BigDecimal soilarea, Integer croptype, BigDecimal croparea, String status, String userid, String ip) {
		
		boolean state=false;
		
		try {
			
			MPpr mp= mprep.getReferenceById(pprId);
			CropType ct= ctrep.getReferenceById(croptype);
			SoilType st= strep.getReferenceById(soilType);
			MVillage v= villrepo.getReferenceById(village);
			
			PprAgroClimate ac= new PprAgroClimate();
			
			ac.setPpr(mp);
			ac.setVillage(v);
			ac.setZoneName(zone);
			ac.setTopography(graphy);
			ac.setAvgRainfall(rainfall);
			ac.setArea(area);
			ac.setForestArea(farea);
			ac.setStatus(status.charAt(0));
			ac.setCreatedBy(userid);
			ac.setCreatedDate(LocalDateTime.now());
			ac.setRequestIp(ip);
			agcrepo.save(ac);
			
			PprAgroCrop acrop = new PprAgroCrop();
			
			acrop.setAgroClimate(ac);
			acrop.setCropType(ct);
			acrop.setArea(croparea);
			acrop.setCreatedBy(userid);
			acrop.setCreatedDate(LocalDateTime.now());
			acrop.setRequestIp(ip);
			acrepo.save(acrop);
			
			PprAgroSoil asoil = new PprAgroSoil();
			
			asoil.setAgroClimate(ac);
			asoil.setSoilType(st);
			asoil.setArea(soilarea);
			asoil.setCreatedBy(userid);
			asoil.setCreatedDate(LocalDateTime.now());
			asoil.setRequestIp(ip);
			asrepo.save(asoil);
			
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;
	}
	
	@Transactional
    public void completeRecord(Integer id) {

		PprAgroClimate fun = agcrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        fun.setStatus('C');

        agcrepo.save(fun);
    }
	
	public boolean editAgroClimateConditionPPR10(Integer agroid, String zone, String graphy, BigDecimal rainfall, BigDecimal area, BigDecimal farea,
			Integer soilType, BigDecimal soilarea, Integer croptype, BigDecimal croparea, String status, String userid, String ip) {
		
		boolean state=false;
		
		try {
			CropType ct= ctrep.getReferenceById(croptype);
			SoilType st= strep.getReferenceById(soilType);
			
			
			PprAgroClimate ac= agcrepo.findById(agroid).get();
			
			ac.setZoneName(zone);
			ac.setTopography(graphy);
			ac.setAvgRainfall(rainfall);
			ac.setArea(area);
			ac.setForestArea(farea);
			ac.setUpdatedBy(userid);
			ac.setUpdatedDate(LocalDate.now());
			ac.setRequestIp(ip);
			agcrepo.save(ac);
			
			acrepo.deleteByAgroClimatePprAgroId(agroid);
			PprAgroCrop acrop = new PprAgroCrop();
			acrop.setAgroClimate(ac);
			acrop.setCropType(ct);
			acrop.setArea(croparea);
			acrop.setCreatedBy(userid);
			acrop.setCreatedDate(LocalDateTime.now());
			acrop.setRequestIp(ip);
			acrepo.save(acrop);
			
			asrepo.deleteByAgroClimatePprAgroId(agroid);
			PprAgroSoil asoil = new PprAgroSoil();
			asoil.setAgroClimate(ac);
			asoil.setSoilType(st);
			asoil.setArea(soilarea);
			asoil.setCreatedBy(userid);
			asoil.setCreatedDate(LocalDateTime.now());
			asoil.setRequestIp(ip);
			asrepo.save(asoil);
			
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;
	}
	

}
