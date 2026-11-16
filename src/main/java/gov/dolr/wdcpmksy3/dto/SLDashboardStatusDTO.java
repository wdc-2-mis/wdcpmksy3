package gov.dolr.wdcpmksy3.dto;

public class SLDashboardStatusDTO {
	
	private Long pprPending;
    private Long pprApproved;

    private Long dprPending;
    private Long dprApproved;

    public SLDashboardStatusDTO() {
    }

    public SLDashboardStatusDTO(
            Long pprPending,
            Long pprApproved,
            Long dprPending,
            Long dprApproved) {

        this.pprPending = pprPending;
        this.pprApproved = pprApproved;
        this.dprPending = dprPending;
        this.dprApproved = dprApproved;
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
