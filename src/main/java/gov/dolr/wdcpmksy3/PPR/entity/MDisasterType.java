package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_disaster_type")
public class MDisasterType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disaster_type_id")
    private Integer disasterTypeId;

    @Column(name = "disaster_name", length = 50)
    private String disasterName;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate = LocalDate.now();

    // --- Getters and Setters ---
    public Integer getDisasterTypeId() {
        return disasterTypeId;
    }

    public void setDisasterTypeId(Integer disasterTypeId) {
        this.disasterTypeId = disasterTypeId;
    }

    public String getDisasterName() {
        return disasterName;
    }

    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
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

