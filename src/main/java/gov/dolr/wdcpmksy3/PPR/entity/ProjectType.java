package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_project_type", schema = "public")
@AllArgsConstructor
public class ProjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_type_id")
    private Integer projectTypeId;

    @Column(name = "project_type", length = 50)
    private String projectType;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;
    
    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDate.now();
    }

	public Integer getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(Integer projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public ProjectType() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}

