package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "m_epa_activity")
public class EpaActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "epa_activity_id")
    private Integer id;

    @Column(name = "epa_desc", length = 250)
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "unit_code", referencedColumnName = "unit_code")
    private MUnit unit;

    @Column(name = "created_by", length = 20)
    private String createdBy;

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

	public MUnit getUnit() {
		return unit;
	}

	public void setUnit(MUnit unit) {
		this.unit = unit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

