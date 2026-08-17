package gov.dolr.wdcpmksy3.PPR.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ppr_existing_livelihood_activities")
public class PprExistingLivelihoodActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_existing_livelihood_activity_id")
    private Integer pprExistingLivelihoodActivityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_livelihood_id", nullable = false)
    private PprLivelihood pprLivelihood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livelihood_activity_id", nullable = false)
    private LivelihoodActivity livelihoodActivity;

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

    // Getters and Setters

    public Integer getPprExistingLivelihoodActivityId() {
        return pprExistingLivelihoodActivityId;
    }

    public void setPprExistingLivelihoodActivityId(Integer id) {
        this.pprExistingLivelihoodActivityId = id;
    }

    public PprLivelihood getPprLivelihood() {
        return pprLivelihood;
    }

    public void setPprLivelihood(PprLivelihood pprLivelihood) {
        this.pprLivelihood = pprLivelihood;
    }

    public LivelihoodActivity getLivelihoodActivity() {
        return livelihoodActivity;
    }

    public void setLivelihoodActivity(LivelihoodActivity livelihoodActivity) {
        this.livelihoodActivity = livelihoodActivity;
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