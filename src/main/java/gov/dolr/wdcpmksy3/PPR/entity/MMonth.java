package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "m_month", schema = "public")
public class MMonth {
	
	@Id
    @Column(name = "month_id")
    private Integer monthId;

    @Column(name = "month_name", length = 10, nullable = false)
    private String monthName;

    @Column(name = "fin_month_id")
    private Integer finMonthId;

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
    
    
    

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public Integer getFinMonthId() {
		return finMonthId;
	}

	public void setFinMonthId(Integer finMonthId) {
		this.finMonthId = finMonthId;
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
