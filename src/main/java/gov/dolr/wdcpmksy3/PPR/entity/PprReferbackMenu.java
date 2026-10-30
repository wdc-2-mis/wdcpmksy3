
package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;

import gov.dolr.wdcpmksy3.entity.WdcpmksySubmenu;
import jakarta.persistence.*;

@Entity
@Table(name = "ppr_referback_menu", schema = "public")
public class PprReferbackMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referback_id")
    private Integer referbackId;

    // Relationship with m_ppr
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    // Relationship with m_submenu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submenu_id")
    private WdcpmksySubmenu submenu;

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
    
    
    @Column(name = "referback_round", nullable = false)
    private Integer referbackRound;

	public Integer getReferbackId() {
		return referbackId;
	}

	public void setReferbackId(Integer referbackId) {
		this.referbackId = referbackId;
	}

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public WdcpmksySubmenu getSubmenu() {
		return submenu;
	}

	public void setSubmenu(WdcpmksySubmenu submenu) {
		this.submenu = submenu;
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

	public Integer getReferbackRound() {
		return referbackRound;
	}

	public void setReferbackRound(Integer referbackRound) {
		this.referbackRound = referbackRound;
	}

    
}