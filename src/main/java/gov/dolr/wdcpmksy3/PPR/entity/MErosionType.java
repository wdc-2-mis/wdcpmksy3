package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "m_erosion_type", schema = "public")
public class MErosionType {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "erosion_type_id")
    private Integer erosionTypeId;

    @Column(name = "erosion_type", length = 50)
    private String erosionType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "erosion_id", nullable = false)
    private MErosion erosion;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;
    
    
    
    
	public Integer getErosionTypeId() {
		return erosionTypeId;
	}

	public void setErosionTypeId(Integer erosionTypeId) {
		this.erosionTypeId = erosionTypeId;
	}

	public String getErosionType() {
		return erosionType;
	}

	public void setErosionType(String erosionType) {
		this.erosionType = erosionType;
	}
	
	public MErosion getErosion() {
		return erosion;
	}

	public void setErosion(MErosion erosion) {
		this.erosion = erosion;
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

	
    
	
}
