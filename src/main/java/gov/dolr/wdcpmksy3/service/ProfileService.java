package gov.dolr.wdcpmksy3.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.dolr.wdcpmksy3.dto.ProfileBean;
import gov.dolr.wdcpmksy3.entity.IwmpUserReg;
import gov.dolr.wdcpmksy3.repository.ProfileProjection;
import gov.dolr.wdcpmksy3.repository.UserMapRepository;
import gov.dolr.wdcpmksy3.repository.UserRepository;

@Service
public class ProfileService {
	
	 	@Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private UserMapRepository repository;

	    public List<IwmpUserReg> getUserDetail(Integer regid) {
	        return userRepository.getUserDetail(regid);
	    }

	    public List<ProfileBean> getMapState(Integer regid, String usertype) {

	        List<ProfileProjection> result;

	        switch (usertype) {

	            case "ADMIN":
	            case "DL":
	                result = repository.getMapAdmin(regid);
	                break;

	            case "SL":
	                result = repository.getMapState(regid);
	                break;

	            case "DI":
	                result = repository.getMapDistrict(regid);
	                break;

	            case "PI":
	                result = repository.getMapProject(regid);
	                break;

	            default:
	                return Collections.emptyList();
	        }

	        return result.stream().map(this::convertToBean).toList();
	    }

	    private ProfileBean convertToBean(ProfileProjection p) {

	        ProfileBean bean = new ProfileBean();

	        bean.setStatecode(p.getStateCode());
	        bean.setStatename(p.getStateName());

	        bean.setDistrictcode(p.getDistrictCode());
	        bean.setDistrictname(p.getDistrictName());

	        bean.setProjectcode(p.getProjectCode());
	        bean.setProjectname(p.getProjectName());

	        bean.setSelected(p.getSelected());

	        bean.setStatecodelgd(p.getStateCodelgd());
	        bean.setDistrictcodelgd(p.getDistrictCodelgd());

	        return bean;
	    }

}
