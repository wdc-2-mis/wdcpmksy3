package gov.dolr.wdcpmksy3.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "m_block")
public class MBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bcode")
    private Integer bcode;

    @Column(name = "block_name", nullable = false)
    private String blockName;

    @Column(name = "block_codelgd")
    private Integer blockCodeLGD;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "request_ip")
    private String requestIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode")
    private MDistrict district;

    @OneToMany(mappedBy = "block")
    private List<MGramPanchayat> gramPanchayats;

    // Getters and Setters

    public Integer getBcode() {
        return bcode;
    }

    public void setBcode(Integer bcode) {
        this.bcode = bcode;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Integer getBlockCodeLGD() {
        return blockCodeLGD;
    }

    public void setBlockCodeLGD(Integer blockCodeLGD) {
        this.blockCodeLGD = blockCodeLGD;
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

    public MDistrict getDistrict() {
        return district;
    }

    public void setDistrict(MDistrict district) {
        this.district = district;
    }

    public List<MGramPanchayat> getGramPanchayats() {
        return gramPanchayats;
    }

    public void setGramPanchayats(List<MGramPanchayat> gramPanchayats) {
        this.gramPanchayats = gramPanchayats;
    }
}