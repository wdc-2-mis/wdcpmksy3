package gov.dolr.wdcpmksy3.DPR.service;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.DPR.entity.DprDetails;
import gov.dolr.wdcpmksy3.DPR.entity.DprEpaLivProdDtl;
import gov.dolr.wdcpmksy3.DPR.entity.DprNrmDtl;
import gov.dolr.wdcpmksy3.DPR.entity.MDprHead;
import gov.dolr.wdcpmksy3.DPR.repository.DprDetailsRepository;
import gov.dolr.wdcpmksy3.DPR.repository.DprEpaLivProdDtlRepo;
import gov.dolr.wdcpmksy3.DPR.repository.MDprHeadRepository;
import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import gov.dolr.wdcpmksy3.PPR.repository.MPprRepository;
import gov.dolr.wdcpmksy3.entity.MDistrict;
import gov.dolr.wdcpmksy3.repository.MDistrictRepository;
import gov.dolr.wdcpmksy3.service.IPService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class DPRELPService {
	
	@Autowired
    private MDistrictRepository districtRepo;
	
	@Autowired
	private DprDetailsRepository dprRepo;
	
	@Autowired
	private DprEpaLivProdDtlRepo dprEpaLivProdDtlRepo;
	
	@Autowired
    private MPprRepository mprep;
	
	@Autowired
	private IPService ipservice;
	
	@Autowired
    private MDprHeadRepository mdprheadrepo;
	
	public List<MDistrict> getDPRDistrictsByState(Integer stcode) {
		return districtRepo.getDPRDistrictsByState(stcode);
	}

	public boolean saveDprELP(Integer district, Integer project, Character head, Integer activity, BigDecimal target,
			String userid, HttpSession session, HttpServletRequest request) {
		
		try {
			MPpr mp= mprep.getReferenceById(project);
			MDprHead dh=mdprheadrepo.getReferenceById(1);
			DprDetails dtl = dprRepo.findByPprPprId(project).orElse(null);
			
			
			
			if (dtl == null) {

				dtl = new DprDetails();
				dtl.setPpr(mp);
				dtl.setStatus('D');
				dtl.setCreatedby(userid);
				dtl.setCreatedon(LocalDateTime.now());
				dtl.setHeadType(dh);
				dtl.setRequestIp(IPService.getClientIpAddr(request));
				dprRepo.save(dtl);
			}
			
            

            DprEpaLivProdDtl entity = new DprEpaLivProdDtl();
            entity.setDpr(dtl);
            entity.setActivityId(activity);
            entity.setLevelHead(head);
            entity.setQtyPlanned(target);
            entity.setStatus('D'); 
            entity.setCreatedBy(userid);
            entity.setCreatedOn(LocalDateTime.now());
            entity.setRequestIp(IPService.getClientIpAddr(request));

            dprEpaLivProdDtlRepo.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	
	}

	public List<Object[]> getDprELPProjectPlanList(Integer stcode) {
		
		return dprEpaLivProdDtlRepo.getDprELPProjectPlanList(stcode);
	}

	public boolean updateDprTarget(Integer elpId, BigDecimal target, String userId, HttpServletRequest request) {
        try {
            DprEpaLivProdDtl entity = dprEpaLivProdDtlRepo.findById(elpId)
                    .orElseThrow(() -> new RuntimeException("Record not found"));

            entity.setQtyPlanned(target);
            entity.setUpdatedBy(userId);
            entity.setUpdatedOn(LocalDateTime.now());
            entity.setRequestIp(request.getRemoteAddr());

            dprEpaLivProdDtlRepo.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public boolean deleteDprTarget(Integer elpId) {
        try {
            dprEpaLivProdDtlRepo.deleteById(elpId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    

    public boolean completeDprTarget(Integer elpId, String userId, HttpServletRequest request) {
        try {
            DprEpaLivProdDtl entity = dprEpaLivProdDtlRepo.findById(elpId)
                    .orElseThrow(() -> new RuntimeException("Record not found"));

            entity.setStatus('C'); 
            entity.setUpdatedBy(userId);
            entity.setUpdatedOn(LocalDateTime.now());
            entity.setRequestIp(request.getRemoteAddr());

            dprEpaLivProdDtlRepo.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean completeMultipleDprTargets(List<Integer> ids, String userId, HttpServletRequest request) {
        try {
            List<DprEpaLivProdDtl> records = dprEpaLivProdDtlRepo.findAllById(ids);
            for (DprEpaLivProdDtl entity : records) {
                entity.setStatus('C');
                entity.setUpdatedBy(userId);
                entity.setUpdatedOn(LocalDateTime.now());
                entity.setRequestIp(request.getRemoteAddr());
            }
            dprEpaLivProdDtlRepo.saveAll(records);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public String findStatusByProjectAndActivityAndHead(Integer projectId, Integer actId, Character headChar) {
		// TODO Auto-generated method stub
		return dprEpaLivProdDtlRepo.findStatusByProjectAndActivityAndHead(projectId, actId, headChar);
	}

	public List<Object[]> getDprELPProjectPlanListByDistrict(Integer dcode) {
		// TODO Auto-generated method stub
		return dprEpaLivProdDtlRepo.getDprELPProjectPlanListByDistrict(dcode);
	}

	public List<Object[]> getDprELPProjectPlanListByProject(Integer pprId) {
		// TODO Auto-generated method stub
		return dprEpaLivProdDtlRepo.getDprELPProjectPlanListByProject(pprId);
	}
	
	public void skipDPRELPTarget(Integer pprid, String userId, HttpServletRequest servletRequest) {
        
 		MPpr mp= mprep.getReferenceById(pprid);
		MDprHead dh=mdprheadrepo.getReferenceById(1);
 		DprDetails pln = dprRepo.findByPprPprId(pprid).orElse(null);
		
		if (pln == null) {

		    pln = new DprDetails();
		    pln.setHeadType(dh);
		    pln.setPpr(mp);
		    pln.setStatus('D');
		    pln.setCreatedby(userId);
		    pln.setCreatedon(LocalDateTime.now());
		  //  pln.setRequestIp(ip);
		    dprRepo.save(pln);
		}
		
		int deletedCount = dprEpaLivProdDtlRepo.deleteByPprId(pprid);
		
		DprEpaLivProdDtl plndtl = new DprEpaLivProdDtl();
		plndtl.setDpr(pln);
		plndtl.setStatus('S');
		plndtl.setCreatedBy(userId);
		plndtl.setCreatedOn(LocalDateTime.now());
		//plndtl.setRequestIp(ip);
		dprEpaLivProdDtlRepo.save(plndtl);
    }

}
