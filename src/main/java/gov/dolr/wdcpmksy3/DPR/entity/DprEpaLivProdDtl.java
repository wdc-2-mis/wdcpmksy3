package gov.dolr.wdcpmksy3.DPR.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "dpr_epa_liv_prod_dtl",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_dpr_epa_liv_prod_dtl_composite",
            columnNames = {"dpr_id", "activity_id", "level_head"}
        )
    }
)
public class DprEpaLivProdDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "elp_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dpr_id", referencedColumnName = "dpr_id")
    private DprDetails dpr;

    @Column(name = "activity_id")
    private Integer activityId;

    @Column(name = "level_head", length = 1)
    private Character levelHead;   

    @Column(name = "qty_planned", precision = 19, scale = 2)
    private BigDecimal qtyPlanned;

    @Column(name = "status", length = 1)
    private Character status;     

    @Column(name = "createdby", length = 20)
    private String createdBy;

    @Column(name = "createdon")
    private LocalDateTime createdOn;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "updatedby", length = 20)
    private String updatedBy;

    @Column(name = "updatedon")
    private LocalDateTime updatedOn;
    
    // getters and setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DprDetails getDpr() {
		return dpr;
	}

	public void setDpr(DprDetails dpr) {
		this.dpr = dpr;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Character getLevelHead() {
		return levelHead;
	}

	public void setLevelHead(Character levelHead) {
		this.levelHead = levelHead;
	}

	public BigDecimal getQtyPlanned() {
		return qtyPlanned;
	}

	public void setQtyPlanned(BigDecimal qtyPlanned) {
		this.qtyPlanned = qtyPlanned;
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

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

   
    
    
}
