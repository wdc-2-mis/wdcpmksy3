package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "m_productivity_activity")
public class ProductivityActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productivity_activity_id")
    private Integer id;

    @Column(name = "productivity_coreactivity_desc", length = 50)
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "cover_area", length = 1)
    private String coverArea;

    @ManyToOne
    @JoinColumn(name = "unit_code", referencedColumnName = "unit_code")
    private MUnit unit;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCoverArea() {
		return coverArea;
	}

	public void setCoverArea(String coverArea) {
		this.coverArea = coverArea;
	}

	public MUnit getUnit() {
		return unit;
	}

	public void setUnit(MUnit unit) {
		this.unit = unit;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public LocalDate getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDate updatedOn) {
		this.updatedOn = updatedOn;
	}

   
}

