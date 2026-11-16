package gov.dolr.wdcpmksy3.dto;

import java.math.BigDecimal;

public class SLDashboardDTO {

    private Long totalDistricts;
    private Long totalProjects;
    private Long totalPiaAssigned;
    private BigDecimal totalAreaCovered;
    
    private Long pprPending;
    private Long pprApproved;

    private Long dprPending;
    private Long dprApproved;

    public SLDashboardDTO() {
    }

    public SLDashboardDTO(
            Long totalDistricts,
            Long totalProjects,
            Long totalPiaAssigned,
            BigDecimal totalAreaCovered) {

        this.totalDistricts = totalDistricts;
        this.totalProjects = totalProjects;
        this.totalPiaAssigned = totalPiaAssigned;
        this.totalAreaCovered = totalAreaCovered;
    }

    public Long getTotalDistricts() {
        return totalDistricts;
    }

    public void setTotalDistricts(Long totalDistricts) {
        this.totalDistricts = totalDistricts;
    }

    public Long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(Long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public Long getTotalPiaAssigned() {
        return totalPiaAssigned;
    }

    public void setTotalPiaAssigned(Long totalPiaAssigned) {
        this.totalPiaAssigned = totalPiaAssigned;
    }

    public BigDecimal getTotalAreaCovered() {
        return totalAreaCovered;
    }

    public void setTotalAreaCovered(BigDecimal totalAreaCovered) {
        this.totalAreaCovered = totalAreaCovered;
    }

	public Long getPprPending() {
		return pprPending;
	}

	public void setPprPending(Long pprPending) {
		this.pprPending = pprPending;
	}

	public Long getPprApproved() {
		return pprApproved;
	}

	public void setPprApproved(Long pprApproved) {
		this.pprApproved = pprApproved;
	}

	public Long getDprPending() {
		return dprPending;
	}

	public void setDprPending(Long dprPending) {
		this.dprPending = dprPending;
	}

	public Long getDprApproved() {
		return dprApproved;
	}

	public void setDprApproved(Long dprApproved) {
		this.dprApproved = dprApproved;
	}
    
    
}
