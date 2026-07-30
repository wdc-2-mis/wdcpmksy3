package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import gov.dolr.wdcpmksy3.entity.InstitutionalStructure;
import gov.dolr.wdcpmksy3.entity.IwmpDistrict;

@Entity
@Table(name = "m_ppr", schema = "public")
public class MPpr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_id", nullable = false)
    private Integer pprId;

    // --- Relationships ---
    @ManyToOne
    @JoinColumn(name = "ppr_inst_str_id", nullable = false)
    private InstitutionalStructure institutionalStructure;

    @ManyToOne
    @JoinColumn(name = "dcode", nullable = false)
    private IwmpDistrict district;

    @ManyToOne
    @JoinColumn(name = "fin_yr_cd")
    private IwmpMFinYear finYear;

    // --- Other Columns ---
    @Column(name = "project_name", length = 200, unique = true)
    private String projectName;

    @Column(name = "status", length = 1)
    private String status;

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

    @OneToMany(mappedBy = "ppr")
    private List<PprMicroWatershed> microWatersheds;
    
    // --- Getters and Setters ---
    public Integer getPprId() {
        return pprId;
    }

    public void setPprId(Integer pprId) {
        this.pprId = pprId;
    }

    public InstitutionalStructure getInstitutionalStructure() {
        return institutionalStructure;
    }

    public void setInstitutionalStructure(InstitutionalStructure institutionalStructure) {
        this.institutionalStructure = institutionalStructure;
    }

    public IwmpDistrict getDistrict() {
        return district;
    }

    public void setDistrict(IwmpDistrict district) {
        this.district = district;
    }

    public IwmpMFinYear getFinYear() {
        return finYear;
    }

    public void setFinYear(IwmpMFinYear finYear) {
        this.finYear = finYear;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

	public List<PprMicroWatershed> getMicroWatersheds() {
		return microWatersheds;
	}

	public void setMicroWatersheds(List<PprMicroWatershed> microWatersheds) {
		this.microWatersheds = microWatersheds;
	}
    
    
}

