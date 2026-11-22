package gov.dolr.wdcpmksy3.DPR.service;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlan;
import gov.dolr.wdcpmksy3.DPR.entity.WdcpmksyProjectPhysicalPlanTranx;
import gov.dolr.wdcpmksy3.DPR.repository.PhysicalActionPlanTranxProjection;
import gov.dolr.wdcpmksy3.DPR.repository.WdcpmksyProjectPhysicalPlanRepository;
import gov.dolr.wdcpmksy3.DPR.repository.WdcpmksyProjectPhysicalPlanTranxRepository;
import gov.dolr.wdcpmksy3.dto.PhysicalActionPlanTranxDTO;
import gov.dolr.wdcpmksy3.entity.WdcpmksyUserReg;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;

@Service
public class PhysicalActionPlanService {
	
	@Autowired
	private WdcpmksyProjectPhysicalPlanTranxRepository tranxRepository;
	
	@Autowired
	private UserMapRepository maprep;
	
	@Autowired
	private UserRepository userrepo;
	
	
	@Autowired
	private WdcpmksyProjectPhysicalPlanRepository planrepo;
	
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
	 	
	 	public Integer getSentToByRegId(Integer regId, Character action,Integer planid,String userType) {
			// TODO Auto-generated method stub
			String hql=null;
			Integer sentto =null;
			
			try {
				
				
				if(userType.equals("DI")) 
				{
					if(action=='F') 
					{
						//forward to slna    
						sentto=userrepo.getSentToByRegIdDIF(regId);
					}
					else if(action=='R') 
					{
					//backward to pia for correction
						sentto=tranxRepository.getSentToByRegIdDIR(regId, planid);
					}
				}
				if(userType.equals("SL")) 
				{
					if(action=='F') 
					{
						 sentto=regId;
					}
					else if(action=='R') 
					{
						//backward to pia/wcdc for correction
						sentto=tranxRepository.getSentToByRegIdSLR(regId, planid);
					}
				}
				if(userType.equals("PI")) 
				{
					if(action=='F') 
					{
						//forward to slna
						sentto=userrepo.getSentToByRegIdDIF(regId);
					}
					else if(action=='R') 
					{}
				}
				
			}
			catch(Exception ex) 
			{
			    System.out.print(ex.getStackTrace()[0].getLineNumber());
				ex.printStackTrace();
			}
			finally {}
			return sentto;
		}
	 	
	 	@Transactional
	 	public String forwardByWCDC(Integer sentto, Integer sentfrom, Integer planid, Character action, String remarks,String userType) {
			
			String res=null;
			try {
				
				Timestamp time= Timestamp.from(Instant.now());  
				
				WdcpmksyProjectPhysicalPlanTranx tranx = new WdcpmksyProjectPhysicalPlanTranx();
				
				WdcpmksyUserReg regfrom = userrepo.findById(sentfrom.longValue()).orElseThrow();
				WdcpmksyUserReg regto = userrepo.findById(sentto.longValue()).orElseThrow();
				WdcpmksyProjectPhysicalPlan plan=planrepo.findById(planid).orElseThrow();
				
				tranx.setPlan(plan);
				tranx.setSentFrom(regfrom);
				tranx.setSentTo(regto);
				
				if(action.toString().equals("F") && userType.equals("SL"))
					tranx.setAction('C');
				else
					tranx.setAction(action);
				
				tranx.setRemarks(remarks);
				tranx.setSentOn(time);
				tranxRepository.save(tranx);
				
				if(action.toString().equals("F") && userType.equals("SL")) {
				plan.setStatus('C');
				planrepo.save(plan);
				}
				res="success";
				
			}
			catch(Exception ex) {
				res="fail";
				ex.printStackTrace();
				
			}
			finally {
				
			}
			return res;
		}


}
