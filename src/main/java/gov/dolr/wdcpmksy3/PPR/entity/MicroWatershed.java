package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "m_micro_watershed", schema = "public")
public class MicroWatershed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mw_id", nullable = false)
    private Integer mwId;

    @Column(name = "mw_name", length = 200)
    private String mwName;

    @Column(name = "mw_code", length = 20)
    private String mwCode;

    @Column(name = "mw_area", precision = 20, scale = 4)
    private BigDecimal  mwArea;

    @Column(name = "gis_reference")
    private String gisReference;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal  latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal  longitude;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;
    
    @OneToMany(mappedBy = "microWatershed")
    private List<PprMicroWatershed> pprs;

    // --- Getters and Setters ---
    public Integer getMwId() {
        return mwId;
    }

    public void setMwId(Integer mwId) {
        this.mwId = mwId;
    }

    public String getMwName() {
        return mwName;
    }

    public void setMwName(String mwName) {
        this.mwName = mwName;
    }

    public String getMwCode() {
        return mwCode;
    }

    public void setMwCode(String mwCode) {
        this.mwCode = mwCode;
    }

    public BigDecimal  getMwArea() {
        return mwArea;
    }

    public void setMwArea(BigDecimal  mwArea) {
        this.mwArea = mwArea;
    }

    public String getGisReference() {
        return gisReference;
    }

    public void setGisReference(String gisReference) {
        this.gisReference = gisReference;
    }

    public BigDecimal  getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal  latitude) {
        this.latitude = latitude;
    }

    public BigDecimal  getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal  longitude) {
        this.longitude = longitude;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

	public List<PprMicroWatershed> getPprs() {
		return pprs;
	}

	public void setPprs(List<PprMicroWatershed> pprs) {
		this.pprs = pprs;
	}
    
}

