package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wdcpmksy_user_project_map", schema = "public")
public class WdcpmksyUserProjectMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_map_id")
    private Integer upMapId;


    /*
     * Foreign Key:
     * proj_id -> m_project.proj_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_id", nullable = false)
    private MProject project;


    /*
     * Foreign Key:
     * reg_id -> wdcpmksy_user_reg.reg_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", nullable = false)
    private WdcpmksyUserReg user;


    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;


    @Column(name = "create_by", length = 50, nullable = false)
    private String createBy;


    @Column(name = "ip_address", length = 20)
    private String ipAddress;


    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;


    @Column(name = "updated_by", length = 50, nullable = false)
    private String updatedBy;


    // =========================
    // Getters and Setters
    // =========================

    public Integer getUpMapId() {
        return upMapId;
    }

    public void setUpMapId(Integer upMapId) {
        this.upMapId = upMapId;
    }

    public MProject getProject() {
        return project;
    }

    public void setProject(MProject project) {
        this.project = project;
    }

    public WdcpmksyUserReg getUser() {
        return user;
    }

    public void setUser(WdcpmksyUserReg user) {
        this.user = user;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}