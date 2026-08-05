package gov.dolr.wdcpmksy3.entity;

import java.util.List;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "m_district")
public class MDistrict {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dcode")
    private Integer dcode;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code")
    private MState state;
	
    @Column(name = "dist_name")
    private String distName;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "request_ip")
    private String requestIp;
    
    @Column(name = "district_codelgd")
    private Integer districtCodeLgd;
    
    @Column(name = "wdcpmksy")
    private Boolean wdcpmksy;
    
    @Column(name = "wdcpmksy2")
    private Boolean wdcpmksy2;
    
    @Column(name = "wdcpmksy3")
    private Boolean wdcpmksy3;
    
    @OneToMany(mappedBy = "district")
    private List<MPpr> pprs;
    
	public Integer getDcode() {
		return dcode;
	}

	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}

	public MState getState() {
		return state;
	}

	public void setState(MState state) {
		this.state = state;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
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

	public Integer getDistrictCodeLgd() {
		return districtCodeLgd;
	}

	public void setDistrictCodeLgd(Integer districtCodeLgd) {
		this.districtCodeLgd = districtCodeLgd;
	}
	public Boolean getWdcpmksy() {
		return wdcpmksy;
	}

	public void setWdcpmksy(Boolean wdcpmksy) {
		this.wdcpmksy = wdcpmksy;
	}

	public Boolean getWdcpmksy2() {
		return wdcpmksy2;
	}

	public void setWdcpmksy2(Boolean wdcpmksy2) {
		this.wdcpmksy2 = wdcpmksy2;
	}

	public Boolean getWdcpmksy3() {
		return wdcpmksy3;
	}

	public void setWdcpmksy3(Boolean wdcpmksy3) {
		this.wdcpmksy3 = wdcpmksy3;
	}

	public List<MPpr> getPprs() {
		return pprs;
	}

	public void setPprs(List<MPpr> pprs) {
		this.pprs = pprs;
	}
    
    
}
