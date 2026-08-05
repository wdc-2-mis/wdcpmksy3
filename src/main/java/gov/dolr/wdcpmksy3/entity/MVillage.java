package gov.dolr.wdcpmksy3.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import gov.dolr.wdcpmksy3.PPR.entity.PprAgroClimate;
import gov.dolr.wdcpmksy3.PPR.entity.PprVillage;

@Entity
@Table(name = "m_village")
public class MVillage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vcode")
    private Integer vcode;

    @Column(name = "village_codelgd", nullable = false)
    private Integer villageCodelgd;

    @Column(name = "village_name", nullable = false, length = 200)
    private String villageName;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gcode")
    private MGramPanchayat gramPanchayat;

    @OneToMany(mappedBy = "village")
    private List<PprVillage> pprVillages;
    
    @OneToMany(mappedBy = "village")
    private List<PprAgroClimate> agroClimateList = new ArrayList<PprAgroClimate>();

    // Getters and Setters

    public Integer getVcode() {
        return vcode;
    }

    public void setVcode(Integer vcode) {
        this.vcode = vcode;
    }

    public Integer getVillageCodelgd() {
        return villageCodelgd;
    }

    public void setVillageCodelgd(Integer villageCodelgd) {
        this.villageCodelgd = villageCodelgd;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

	 public MGramPanchayat getGramPanchayat() 
	 { 
		 return gramPanchayat; 
	 }
	 
	 public void setGramPanchayat(MGramPanchayat gramPanchayat) 
	 {
		 this.gramPanchayat = gramPanchayat; 
	 }
    public List<PprVillage> getPprVillages() {
        return pprVillages;
    }

    public void setPprVillages(List<PprVillage> pprVillages) {
        this.pprVillages = pprVillages;
    }

	public List<PprAgroClimate> getAgroClimateList() {
		return agroClimateList;
	}

	public void setAgroClimateList(List<PprAgroClimate> agroClimateList) {
		this.agroClimateList = agroClimateList;
	}
    
    
    
    
    
    
    
}