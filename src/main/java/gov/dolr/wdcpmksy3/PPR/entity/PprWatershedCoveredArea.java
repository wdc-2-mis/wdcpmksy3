package gov.dolr.wdcpmksy3.PPR.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ppr_watershed_covered_area")
public class PprWatershedCoveredArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppr_watershed_covered_area_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ppr_id", nullable = false)
    private MPpr ppr;

    @ManyToOne
    @JoinColumn(name = "mw_id", nullable = false)
    private MicroWatershed microWatershed;

    @ManyToOne
    @JoinColumn(name = "scheme_id", nullable = false)
    private MScheme scheme;

    @Column(name = "no_mw")
    private Integer noMw;

    @Column(name = "area_mw", precision = 20, scale = 4)
    private BigDecimal areaMw;

    @Column(name = "status")
    private String status;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MPpr getPpr() {
		return ppr;
	}

	public void setPpr(MPpr ppr) {
		this.ppr = ppr;
	}

	public MicroWatershed getMicroWatershed() {
		return microWatershed;
	}

	public void setMicroWatershed(MicroWatershed microWatershed) {
		this.microWatershed = microWatershed;
	}

	public MScheme getScheme() {
		return scheme;
	}

	public void setScheme(MScheme scheme) {
		this.scheme = scheme;
	}

	public Integer getNoMw() {
		return noMw;
	}

	public void setNoMw(Integer noMw) {
		this.noMw = noMw;
	}

	public BigDecimal getAreaMw() {
		return areaMw;
	}

	public void setAreaMw(BigDecimal areaMw) {
		this.areaMw = areaMw;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
    
}
