package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "iwmp_m_submenu")
public class IwmpSubmenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submenu_id")
    private Integer submenuId;

    @Column(name = "seq_no", nullable = false, precision = 6, scale = 2)
    private BigDecimal seqNo;

    @Column(name = "submenu_name", nullable = false, length = 400)
    private String submenuName;

    @Column(name = "target", nullable = false, length = 250)
    private String target;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "submenu_hindi_name", length = 500)
    private String submenuHindiName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private IwmpMenu menu;
    
    
    @OneToMany(mappedBy = "submenu", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<IwmpRoleMenuMap> roleMenuMappings;

    // Getters and Setters

    public Integer getSubmenuId() {
        return submenuId;
    }

    public void setSubmenuId(Integer submenuId) {
        this.submenuId = submenuId;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public String getSubmenuHindiName() {
        return submenuHindiName;
    }

    public void setSubmenuHindiName(String submenuHindiName) {
        this.submenuHindiName = submenuHindiName;
    }

	public IwmpMenu getMenu() {
		return menu;
	}

	public void setMenu(IwmpMenu menu) {
		this.menu = menu;
	}

	public List<IwmpRoleMenuMap> getRoleMenuMappings() {
		return roleMenuMappings;
	}

	public void setRoleMenuMappings(List<IwmpRoleMenuMap> roleMenuMappings) {
		this.roleMenuMappings = roleMenuMappings;
	}

   
    
    
    
    
    
    
}