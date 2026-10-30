
package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "ppr_referback_menu")
public class PprReferbackMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referback_id")
    private Integer referbackId;

    @Column(name = "ppr_id", nullable = false)
    private Integer pprId;

    @Column(name = "submenu_id")
    private Integer submenuId;

    @Column(name = "submenu_name", nullable = false, length = 400)
    private String submenuName;

    @Column(name = "target", nullable = false, length = 250)
    private String target;

    @Column(name = "remark", length = 1000)
    private String remark;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    // Getters and Setters

    public Integer getReferbackId() {
        return referbackId;
    }

    public void setReferbackId(Integer referbackId) {
        this.referbackId = referbackId;
    }

    public Integer getPprId() {
        return pprId;
    }

    public void setPprId(Integer pprId) {
        this.pprId = pprId;
    }

    public Integer getSubmenuId() {
        return submenuId;
    }

    public void setSubmenuId(Integer submenuId) {
        this.submenuId = submenuId;
    }

    public String getSubmenuName() {
        return submenuName;
    }

    public void setSubmenuName(String submenuName) {
        this.submenuName = submenuName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
