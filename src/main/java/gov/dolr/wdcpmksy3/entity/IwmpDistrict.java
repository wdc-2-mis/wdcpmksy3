package gov.dolr.wdcpmksy3.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "iwmp_district")
public class IwmpDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dcode")
    private Integer dcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code")
    private IwmpState state;

    @Column(name = "dist_code")
    private Integer distCode;

    @Column(name = "dist_name")
    private String distName;

    @Column(name = "no_of_blocks_ddp")
    private Integer noOfBlocksDdp;

    @Column(name = "no_of_blocks_dpap")
    private Integer noOfBlocksDpap;

    @Column(name = "no_of_blocks_iwdp")
    private Integer noOfBlocksIwdp;

    private Integer iwdpCount;
    private Integer ddpCount;
    private Integer dpapCount;

    private Integer iwdp;
    private Integer ddp;
    private Integer dpap;

    private BigDecimal iwmp;

    @Column(name = "import_type")
    private Character importType;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "request_ip")
    private String requestIp;

    private Character mong;

    @Column(name = "state_code2001")
    private String stateCode2001;

    @Column(name = "state_code2011")
    private String stateCode2011;

    @Column(name = "district_code2001")
    private String districtCode2001;

    @Column(name = "district_code2011")
    private String districtCode2011;

    @Column(name = "census_code_ported_data")
    private String censusCodePortedData;

    @Column(name = "state_codelgd")
    private String stateCodeLgd;

    @Column(name = "district_codelgd")
    private Integer districtCodeLgd;

    @Column(name = "lgd_code_ported_data")
    private String lgdCodePortedData;

    @Column(name = "jal_shakati")
    private BigDecimal jalShakati;

    @Column(name = "dist_proj")
    private Boolean distProj;
    

	public Integer getDcode() {
		return dcode;
	}

	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}

	public IwmpState getState() {
		return state;
	}

	public void setState(IwmpState state) {
		this.state = state;
	}

	public Integer getDistCode() {
		return distCode;
	}

	public void setDistCode(Integer distCode) {
		this.distCode = distCode;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public Integer getNoOfBlocksDdp() {
		return noOfBlocksDdp;
	}

	public void setNoOfBlocksDdp(Integer noOfBlocksDdp) {
		this.noOfBlocksDdp = noOfBlocksDdp;
	}

	public Integer getNoOfBlocksDpap() {
		return noOfBlocksDpap;
	}

	public void setNoOfBlocksDpap(Integer noOfBlocksDpap) {
		this.noOfBlocksDpap = noOfBlocksDpap;
	}

	public Integer getNoOfBlocksIwdp() {
		return noOfBlocksIwdp;
	}

	public void setNoOfBlocksIwdp(Integer noOfBlocksIwdp) {
		this.noOfBlocksIwdp = noOfBlocksIwdp;
	}

	public Integer getIwdpCount() {
		return iwdpCount;
	}

	public void setIwdpCount(Integer iwdpCount) {
		this.iwdpCount = iwdpCount;
	}

	public Integer getDdpCount() {
		return ddpCount;
	}

	public void setDdpCount(Integer ddpCount) {
		this.ddpCount = ddpCount;
	}

	public Integer getDpapCount() {
		return dpapCount;
	}

	public void setDpapCount(Integer dpapCount) {
		this.dpapCount = dpapCount;
	}

	public Integer getIwdp() {
		return iwdp;
	}

	public void setIwdp(Integer iwdp) {
		this.iwdp = iwdp;
	}

	public Integer getDdp() {
		return ddp;
	}

	public void setDdp(Integer ddp) {
		this.ddp = ddp;
	}

	public Integer getDpap() {
		return dpap;
	}

	public void setDpap(Integer dpap) {
		this.dpap = dpap;
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

	public Character getMong() {
		return mong;
	}

	public void setMong(Character mong) {
		this.mong = mong;
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

	public String getDistrictCode2001() {
		return districtCode2001;
	}

	public void setDistrictCode2001(String districtCode2001) {
		this.districtCode2001 = districtCode2001;
	}

	public String getDistrictCode2011() {
		return districtCode2011;
	}

	public void setDistrictCode2011(String districtCode2011) {
		this.districtCode2011 = districtCode2011;
	}

	public String getCensusCodePortedData() {
		return censusCodePortedData;
	}

	public void setCensusCodePortedData(String censusCodePortedData) {
		this.censusCodePortedData = censusCodePortedData;
	}

	public String getStateCodeLgd() {
		return stateCodeLgd;
	}

	public void setStateCodeLgd(String stateCodeLgd) {
		this.stateCodeLgd = stateCodeLgd;
	}

	public Integer getDistrictCodeLgd() {
		return districtCodeLgd;
	}

	public void setDistrictCodeLgd(Integer districtCodeLgd) {
		this.districtCodeLgd = districtCodeLgd;
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

	public Boolean getDistProj() {
		return distProj;
	}

	public void setDistProj(Boolean distProj) {
		this.distProj = distProj;
	}
    
    
}