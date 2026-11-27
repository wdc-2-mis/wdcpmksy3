package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gov.dolr.wdcpmksy3.PPR.entity.MPpr;
import jakarta.persistence.*;

@Entity
@Table(name = "wdcpmksy_project_wc", schema = "public")
public class WdcpmksyProjectWc {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "project_wc_seq"
    )
    @SequenceGenerator(
        name = "project_wc_seq",
        sequenceName = "wdcpmksy_project_wc_wc_id_seq",
        allocationSize = 1
    )
    @Column(name = "wc_id")
    private Integer wcId;

    @Column(name = "wcname", length = 70)
    private String wcname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_id")
    private MPpr project;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "last_updated_by", length = 25)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    // Getters and Setters

    public Integer getWcId() {
        return wcId;
    }

    public void setWcId(Integer wcId) {
        this.wcId = wcId;
    }

    public String getWcname() {
        return wcname;
    }

    public void setWcname(String wcname) {
        this.wcname = wcname;
    }

    public MPpr getProject() {
        return project;
    }

    public void setProject(MPpr project) {
        this.project = project;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
