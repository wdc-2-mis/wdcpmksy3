package gov.dolr.wdcpmksy3.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "m_gram_panchayat")
public class MGramPanchayat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gcode")
    private Integer gcode;

    @Column(name = "gram_panchayat_codelgd", nullable = false)
    private Integer gramPanchayatCodeLgd;

    @Column(name = "gram_panchayat_name", length = 100)
    private String gramPanchayatName;

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
    @JoinColumn(name = "bcode")
    private MBlock block;

    @OneToMany(mappedBy = "gramPanchayat")
    private List<MVillage> villages;

    // Getters and Setters

    public Integer getGcode() {
        return gcode;
    }

    public void setGcode(Integer gcode) {
        this.gcode = gcode;
    }

    public Integer getGramPanchayatCodeLgd() {
        return gramPanchayatCodeLgd;
    }

    public void setGramPanchayatCodeLgd(Integer gramPanchayatCodeLgd) {
        this.gramPanchayatCodeLgd = gramPanchayatCodeLgd;
    }

    public String getGramPanchayatName() {
        return gramPanchayatName;
    }

    public void setGramPanchayatName(String gramPanchayatName) {
        this.gramPanchayatName = gramPanchayatName;
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

    public MBlock getBlock() {
        return block;
    }

    public void setBlock(MBlock block) {
        this.block = block;
    }

    public List<MVillage> getVillages() {
        return villages;
    }

    public void setVillages(List<MVillage> villages) {
        this.villages = villages;
    }
}