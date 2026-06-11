package gov.dolr.wdcpmksy3.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
@Entity
@Table(name = "iwmp_state")
public class IwmpState {

    @Id
    @Column(name = "st_code")
    private Integer stCode;

    @Column(name = "st_name", nullable = false)
    private String stName;

    private BigDecimal ddp;
    private BigDecimal dpap;
    private BigDecimal iwdp;

    @Column(name = "north_east")
    private String northEast;

    private String payee;
    private String payeeofficer;
    private String payeecode;

    @Column(name = "st_capital")
    private String stCapital;

    private BigDecimal iwmp;

    @Column(name = "import_type")
    private Character importType;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "isvillagewise")
    private Character isvillagewise;

    @Column(name = "state_code2001")
    private String stateCode2001;

    @Column(name = "state_code2011")
    private String stateCode2011;

    @Column(name = "census_code_ported_data")
    private String censusCodePortedData;

    @Column(name = "approval_req")
    private Character approvalReq;

    @Column(name = "state_codelgd")
    private String stateCodelgd;

    @Column(name = "lgd_code_ported_data")
    private String lgdCodePortedData;

    @Column(name = "jal_shakati")
    private BigDecimal jalShakati;

    private Integer wdcpmksy;

    @OneToMany(mappedBy = "state")
    private List<IwmpDistrict> districts;
    

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

	public BigDecimal getDdp() {
		return ddp;
	}

	public void setDdp(BigDecimal ddp) {
		this.ddp = ddp;
	}

	public BigDecimal getDpap() {
		return dpap;
	}

	public void setDpap(BigDecimal dpap) {
		this.dpap = dpap;
	}

	public BigDecimal getIwdp() {
		return iwdp;
	}

	public void setIwdp(BigDecimal iwdp) {
		this.iwdp = iwdp;
	}

	public String getNorthEast() {
		return northEast;
	}

	public void setNorthEast(String northEast) {
		this.northEast = northEast;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayeeofficer() {
		return payeeofficer;
	}

	public void setPayeeofficer(String payeeofficer) {
		this.payeeofficer = payeeofficer;
	}

	public String getPayeecode() {
		return payeecode;
	}

	public void setPayeecode(String payeecode) {
		this.payeecode = payeecode;
	}

	public String getStCapital() {
		return stCapital;
	}

	public void setStCapital(String stCapital) {
		this.stCapital = stCapital;
	}

	public BigDecimal getIwmp() {
		return iwmp;
	}

	public void setIwmp(BigDecimal iwmp) {
		this.iwmp = iwmp;
	}

	public Character getImportType() {
		return importType;
	}

	public void setImportType(Character importType) {
		this.importType = importType;
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

	public Character getIsvillagewise() {
		return isvillagewise;
	}

	public void setIsvillagewise(Character isvillagewise) {
		this.isvillagewise = isvillagewise;
	}

	public String getStateCode2001() {
		return stateCode2001;
	}

	public void setStateCode2001(String stateCode2001) {
		this.stateCode2001 = stateCode2001;
	}

	public String getStateCode2011() {
		return stateCode2011;
	}

	public void setStateCode2011(String stateCode2011) {
		this.stateCode2011 = stateCode2011;
	}

	public String getCensusCodePortedData() {
		return censusCodePortedData;
	}

	public void setCensusCodePortedData(String censusCodePortedData) {
		this.censusCodePortedData = censusCodePortedData;
	}

	public Character getApprovalReq() {
		return approvalReq;
	}

	public void setApprovalReq(Character approvalReq) {
		this.approvalReq = approvalReq;
	}

	public String getStateCodelgd() {
		return stateCodelgd;
	}

	public void setStateCodelgd(String stateCodelgd) {
		this.stateCodelgd = stateCodelgd;
	}

	public String getLgdCodePortedData() {
		return lgdCodePortedData;
	}

	public void setLgdCodePortedData(String lgdCodePortedData) {
		this.lgdCodePortedData = lgdCodePortedData;
	}

	public BigDecimal getJalShakati() {
		return jalShakati;
	}

	public void setJalShakati(BigDecimal jalShakati) {
		this.jalShakati = jalShakati;
	}

	public Integer getWdcpmksy() {
		return wdcpmksy;
	}

	public void setWdcpmksy(Integer wdcpmksy) {
		this.wdcpmksy = wdcpmksy;
	}

	public List<IwmpDistrict> getDistricts() {
		return districts;
	}

	public void setDistricts(List<IwmpDistrict> districts) {
		this.districts = districts;
	}
    
    
    
    
    
    
    
    
    
    
    
    
}