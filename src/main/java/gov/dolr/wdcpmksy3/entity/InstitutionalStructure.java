package gov.dolr.wdcpmksy3.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ppr_slna_institutional_structure")
public class InstitutionalStructure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ppr_inst_str_id")
	private Integer pprInstStrId;
	
    @Column(name = "st_code")
    private Integer stCode;

    @Column(name="slna_type", length = 500)
    private String slnaType;

    @Column(name="notification_date")
    private LocalDate notificationDate;

    @Column(name="notification_file")
    private String notificationFile;

    @Column(name="mou_date")
    private LocalDate mouDate;

    @Column(name="mou_file")
    private String mouFile;
    
    @Column(name="common_slna_sldc")
    private Boolean common_slna_sldc;

    @Column(name="status" , length = 1)
    private Character status;
    
    @Column(name="created_by", length = 20)
    private String createdBy;

    @Column(name="created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate UpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;
    
    @OneToMany(mappedBy = "institutionalStructure", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SlnaFunctionary> functionaries = new ArrayList<SlnaFunctionary>();


    //Getters and Setters
    
	public Integer getPprInstStrId() {
		return pprInstStrId;
	}
	public void setPprInstStrId(Integer pprInstStrId) {
		this.pprInstStrId = pprInstStrId;
	}
	
	public Integer getStCode() {
		return stCode;
	}
	public void setStCode(Integer stCode) {
		this.stCode = stCode;
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

    public Boolean getCommon_slna_sldc() {
		return common_slna_sldc;
	}
	public void setCommon_slna_sldc(Boolean common_slna_sldc) {
		this.common_slna_sldc = common_slna_sldc;
	}
	
	public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
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
	
	public List<SlnaFunctionary> getFunctionaries() {
		return functionaries;
	}
	public void setFunctionaries(List<SlnaFunctionary> functionaries) {
		this.functionaries = functionaries;
	}

    

}