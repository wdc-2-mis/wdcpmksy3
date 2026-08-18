package gov.dolr.wdcpmksy3.PPR.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodActivity;
import gov.dolr.wdcpmksy3.PPR.entity.LivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.entity.MWaterQuality;
import gov.dolr.wdcpmksy3.PPR.entity.MicroWatershed;
import gov.dolr.wdcpmksy3.PPR.entity.PprDrinkingWater;
import gov.dolr.wdcpmksy3.PPR.entity.PprExistingLivelihoodActivity;
import gov.dolr.wdcpmksy3.PPR.entity.PprLivelihood;
import gov.dolr.wdcpmksy3.PPR.entity.PprProjectLivelihoodIntervention;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.PPR.repository.MicroWatershedRepository;
import gov.dolr.wdcpmksy3.PPR.repository.VillageRepository;
import gov.dolr.wdcpmksy3.common.CommonFunctions;
import gov.dolr.wdcpmksy3.entity.MVillage;
import gov.dolr.wdcpmksy3.repository.MWaterQualityRepository;
import gov.dolr.wdcpmksy3.repository.PprDrinkingWaterRepository;

@Service
public class DrinkingWaterServices {
	
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
    private VillageRepository villrepo;
	
	@Autowired
    private MicroWatershedRepository mipero;
	
	@Autowired
    private MWaterQualityRepository wtrrepo;
	
	@Autowired
    private PprDrinkingWaterRepository dwrepo;
	
	
	public boolean saveDrinkingWaterStatus(Integer project, Integer village, Integer watershed, Integer preQualityWater, Integer preQualityId,
			Integer postQualityWater, Integer postQualityId, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MicroWatershed micro=mipero.getReferenceById(watershed);
			MPpr mp= mprep.getReferenceById(project);
			MVillage v= villrepo.getReferenceById(village);
			MWaterQuality pre= wtrrepo.getReferenceById(preQualityId);
			MWaterQuality post=wtrrepo.getReferenceById(postQualityId);
			
			PprDrinkingWater li=new PprDrinkingWater();
			
			li.setPpr(mp);
			li.setMicroWatershed(micro);
			li.setVillage(v);
			li.setPostWaterAvailabilityMonths(postQualityWater);
			li.setPostWaterQuality(post);
			li.setPreWaterAvailabilityMonths(preQualityWater);
			li.setPreWaterQuality(pre);
			li.setStatus(action.charAt(0));
			li.setCreatedBy(userid);
			li.setCreatedDate(LocalDateTime.now());
			li.setRequestIp(ip);
			dwrepo.save(li);
			
			
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

		PprDrinkingWater fun = dwrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        fun.setStatus('C');

        dwrepo.save(fun);
    }
	
	public boolean editDrinkingWaterStatus(Integer pprWaterId, Integer village1, Integer watershed1, Integer preQualityWater1, Integer preQualityId1,
			Integer postQualityWater1, Integer postQualityId1, String action, String userid, String ip) {
		
		boolean state=false;
		try {
			
			MicroWatershed micro=mipero.getReferenceById(watershed1);
			MVillage v= villrepo.getReferenceById(village1);
			MWaterQuality pre= wtrrepo.getReferenceById(preQualityId1);
			MWaterQuality post=wtrrepo.getReferenceById(postQualityId1);
			
			PprDrinkingWater li=dwrepo.findById(pprWaterId).get();
			
			li.setMicroWatershed(micro);
			li.setVillage(v);
			li.setPostWaterAvailabilityMonths(postQualityWater1);
			li.setPostWaterQuality(post);
			li.setPreWaterAvailabilityMonths(preQualityWater1);
			li.setPreWaterQuality(pre);
			li.setUpdatedBy(userid);
			li.setUpdatedDate(LocalDate.now());
			li.setRequestIp(ip);
			dwrepo.save(li);
			state=true;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			state=false;
		}
		return state;	
	}

}
