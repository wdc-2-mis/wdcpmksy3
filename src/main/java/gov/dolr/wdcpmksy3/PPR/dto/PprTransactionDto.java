package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PprTransactionDto {

    private Integer tranxId;
    private Integer pprId;
    private String sentFrom;
    private String sentTo;
    private Character action;
    private String remarks;
    private LocalDateTime senton;
    
    private Integer planid;
    private String project_name;
    private Integer phy_activity_code;
    private String head_desc;
    private String activity_name;
    private BigDecimal qty_planned;
    private String unit_desc;

    public Integer getTranxId() {
        return tranxId;
    }

    public void setTranxId(Integer tranxId) {
        this.tranxId = tranxId;
    }

    public Integer getPprId() {
        return pprId;
    }

    public void setPprId(Integer pprId) {
        this.pprId = pprId;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public Character getAction() {
        return action;
    }

    public void setAction(Character action) {
        this.action = action;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getSenton() {
        return senton;
    }

    public void setSenton(LocalDateTime senton) {
        this.senton = senton;
    }

	public Integer getPlanid() {
		return planid;
	}

	public void setPlanid(Integer planid) {
		this.planid = planid;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Integer getPhy_activity_code() {
		return phy_activity_code;
	}

	public void setPhy_activity_code(Integer phy_activity_code) {
		this.phy_activity_code = phy_activity_code;
	}

	public String getHead_desc() {
		return head_desc;
	}

	public void setHead_desc(String head_desc) {
		this.head_desc = head_desc;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public BigDecimal getQty_planned() {
		return qty_planned;
	}

	public void setQty_planned(BigDecimal qty_planned) {
		this.qty_planned = qty_planned;
	}

	public String getUnit_desc() {
		return unit_desc;
	}

	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
	}
    
    
    
    
    
}