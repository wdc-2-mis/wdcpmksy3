package gov.dolr.wdcpmksy3.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "m_state")
public class MState {
	
	@Id
    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "st_name", nullable = false)
    private String stName;
    
    @Column(name = "st_capital")
    private String stCapital;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    
    @Column(name = "request_ip")
    private String requestIp;
    
    @Column(name = "state_codelgd")
    private String stateCodelgd;
    
    private Integer wdcpmksy;
    
    @OneToMany(mappedBy = "state")
    private List<MDistrict> districts;
    

	public Integer getStCode() {
		return stCode;
	}

	public void setStCode(Integer stCode) {
		this.stCode = stCode;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	public String getStCapital() {
		return stCapital;
	}

	public void setStCapital(String stCapital) {
		this.stCapital = stCapital;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getStateCodelgd() {
		return stateCodelgd;
	}

	public void setStateCodelgd(String stateCodelgd) {
		this.stateCodelgd = stateCodelgd;
	}

	public Integer getWdcpmksy() {
		return wdcpmksy;
	}

	public void setWdcpmksy(Integer wdcpmksy) {
		this.wdcpmksy = wdcpmksy;
	}

	public List<MDistrict> getDistricts() {
		return districts;
	}

	public void setDistricts(List<MDistrict> districts) {
		this.districts = districts;
	}
    
    
    

}
