package gov.dolr.wdcpmksy3.DPR.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.DPR.repository.PhysicalActionPlanTranxProjection;
import gov.dolr.wdcpmksy3.DPR.repository.WdcpmksyProjectPhysicalPlanTranxRepository;
import gov.dolr.wdcpmksy3.dto.PhysicalActionPlanTranxDTO;

@Service
public class PhysicalActionPlanService {
	
	@Autowired
	private WdcpmksyProjectPhysicalPlanTranxRepository tranxRepository;
	
	 	@Transactional(readOnly = true)
	    public List<PhysicalActionPlanTranxDTO> viewARPlan( Integer regId, String userType) {

	        List<PhysicalActionPlanTranxDTO> list = new ArrayList<PhysicalActionPlanTranxDTO>();

	        if (regId == null || userType == null) {
	            return list;
	        }

	        if (!userType.equals("DI") && !userType.equals("SL") && !userType.equals("PI")) {
	            return list;
	        }

	        List<PhysicalActionPlanTranxProjection> result = tranxRepository.viewARMovement(regId);

	        for (PhysicalActionPlanTranxProjection row : result) {

	        	PhysicalActionPlanTranxDTO bean = new PhysicalActionPlanTranxDTO();

	            bean.setPlanid(row.getPlanid());
	            bean.setProjectdesc(row.getProjectdesc());
	            bean.setStatus(row.getStatus());
	            bean.setCurrentlevel(row.getCurrentlevel());
	            bean.setRemarks(row.getRemarks());
	            bean.setFinyr(row.getFinyr());
	            bean.setUsertype(row.getUsertype());
	            bean.setSentfrom(row.getSentfrom());
	            bean.setSentto(row.getSentto());
	            bean.setSenton(row.getSenton());

	            if (row.getAction() != null && !row.getAction().isEmpty()) {
	                bean.setAction(row.getAction().charAt(0));
	            }

	            list.add(bean);
	        }

	        return list;
	    }
	 	
	 	@Transactional(readOnly = true)
	    public List<PhysicalActionPlanTranxDTO> viewCompletePlan( Integer regId, String userType) {

	        List<PhysicalActionPlanTranxDTO> list = new ArrayList<PhysicalActionPlanTranxDTO>();

	        if (regId == null || userType == null) {
	            return list;
	        }

	        if (!userType.equals("DI") && !userType.equals("SL") && !userType.equals("PI")) {
	            return list;
	        }

	        List<PhysicalActionPlanTranxProjection> result = tranxRepository.viewCompletePlan(regId);

	        for (PhysicalActionPlanTranxProjection row : result) {

	        	PhysicalActionPlanTranxDTO bean = new PhysicalActionPlanTranxDTO();

	            bean.setPlanid(row.getPlanid());
	            bean.setProjectdesc(row.getProjectdesc());
	            bean.setStatus(row.getStatus());
	            bean.setCurrentlevel(row.getCurrentlevel());
	            bean.setRemarks(row.getRemarks());
	            bean.setFinyr(row.getFinyr());
	            bean.setUsertype(row.getUsertype());
	            bean.setSentfrom(row.getSentfrom());
	            bean.setSentto(row.getSentto());
	            bean.setSenton(row.getSenton());

	            if (row.getAction() != null && !row.getAction().isEmpty()) {
	                bean.setAction(row.getAction().charAt(0));
	            }

	            list.add(bean);
	        }

	        return list;
	    }
	 	
	 	@Transactional(readOnly = true)
	    public List<PhysicalActionPlanTranxDTO> viewAAPMovement( Integer regId, String userType) {

	        List<PhysicalActionPlanTranxDTO> list = new ArrayList<PhysicalActionPlanTranxDTO>();

	        if (regId == null || userType == null) {
	            return list;
	        }

	        if (!userType.equals("DI") && !userType.equals("SL") && !userType.equals("PI")) {
	            return list;
	        }

	        List<PhysicalActionPlanTranxProjection> result = tranxRepository.viewAAPMovement(regId);

	        for (PhysicalActionPlanTranxProjection row : result) {

	        	PhysicalActionPlanTranxDTO bean = new PhysicalActionPlanTranxDTO();

	            bean.setPlanid(row.getPlanid());
	            bean.setProjectdesc(row.getProjectdesc());
	            bean.setStatus(row.getStatus());
	            bean.setCurrentlevel(row.getCurrentlevel());
	            bean.setRemarks(row.getRemarks());
	            bean.setFinyr(row.getFinyr());
	            bean.setUsertype(row.getUsertype());
	            bean.setSentfrom(row.getSentfrom());
	            bean.setSentto(row.getSentto());
	            bean.setSenton(row.getSenton());

	            if (row.getAction() != null && !row.getAction().isEmpty()) {
	                bean.setAction(row.getAction().charAt(0));
	            }

	            list.add(bean);
	        }

	        return list;
	    }


}
