package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "iwmp_m_menu")
public class IwmpMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "hseq_no", unique = true, precision = 6, scale = 2)
    private BigDecimal hseqNo;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "menu_name", nullable = false, length = 400)
    private String menuName;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "menu_hindi_name", length = 400)
    private String menuHindiName;

    @OneToMany(mappedBy = "menu",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<IwmpSubmenu> submenus;

    // Getters and Setters

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public BigDecimal getHseqNo() {
        return hseqNo;
    }

    public void setHseqNo(BigDecimal hseqNo) {
        this.hseqNo = hseqNo;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getMenuHindiName() {
        return menuHindiName;
    }

    public void setMenuHindiName(String menuHindiName) {
        this.menuHindiName = menuHindiName;
    }

    public List<IwmpSubmenu> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(List<IwmpSubmenu> submenus) {
        this.submenus = submenus;
    }
}