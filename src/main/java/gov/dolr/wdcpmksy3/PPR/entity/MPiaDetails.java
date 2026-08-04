package gov.dolr.wdcpmksy3.PPR.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import gov.dolr.wdcpmksy3.PPR.entity.PprProjectGlance;

@Entity
@Table(name = "m_pia_details")
public class MPiaDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pia_id")
    private Integer piaId;

    @Column(name = "pia_name", length = 100)
    private String piaName;

    @Column(name = "address", length = 200)
    private String address;

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
    @Column(name = "updated_date")
    private Date updatedDate;

    @OneToMany(mappedBy = "pia")
    private List<PprProjectGlance> projectGlances;

    // Getters and Setters

    public Integer getPiaId() {
        return piaId;
    }

    public void setPiaId(Integer piaId) {
        this.piaId = piaId;
    }

    public String getPiaName() {
        return piaName;
    }

    public void setPiaName(String piaName) {
        this.piaName = piaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<PprProjectGlance> getProjectGlances() {
        return projectGlances;
    }

    public void setProjectGlances(List<PprProjectGlance> projectGlances) {
        this.projectGlances = projectGlances;
    }
}