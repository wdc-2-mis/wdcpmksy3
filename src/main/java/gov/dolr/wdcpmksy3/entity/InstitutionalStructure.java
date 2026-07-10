package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="ppr_slna_institutional_structure")
public class InstitutionalStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ppr_inst_str_id;

    private Integer st_code;

    @Column(name="slna_type")
    private String slnaType;

    @Column(name="notification_date")
    private LocalDate notificationDate;

    @Column(name="notification_file")
    private String notificationFile;

    @Column(name="mou_date")
    private LocalDate mouDate;

    @Column(name="mou_file")
    private String mouFile;

    private String status;
    
    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate UpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    //Getters and Setters

    public Integer getPpr_inst_str_id() {
		return ppr_inst_str_id;
	}
	public void setPpr_inst_str_id(Integer ppr_inst_str_id) {
		this.ppr_inst_str_id = ppr_inst_str_id;
	}

	public Integer getSt_code() {
		return st_code;
	}
	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}

    public String getSlnaType() {
        return slnaType;
    }

    public void setSlnaType(String slnaType) {
        this.slnaType = slnaType;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationFile() {
        return notificationFile;
    }

    public void setNotificationFile(String notificationFile) {
        this.notificationFile = notificationFile;
    }

    public LocalDate getMouDate() {
        return mouDate;
    }

    public void setMouDate(LocalDate mouDate) {
        this.mouDate = mouDate;
    }

    public String getMouFile() {
        return mouFile;
    }

    public void setMouFile(String mouFile) {
        this.mouFile = mouFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
		return UpdatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

    

}