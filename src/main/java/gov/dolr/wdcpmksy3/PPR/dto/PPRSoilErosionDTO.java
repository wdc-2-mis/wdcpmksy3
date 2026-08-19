package gov.dolr.wdcpmksy3.PPR.dto;

import java.math.BigDecimal;

public class PPRSoilErosionDTO {
	
	
	private Integer dcode;
	
	private Integer erosionTypeId;

    private BigDecimal affectedArea;

    private BigDecimal runoff;

    private BigDecimal avgSoilLoss;
    
    private Integer monthId;
    
    private Integer yearId;
    

	public Integer getErosionTypeId() {
		return erosionTypeId;
	}

	public Integer getDcode() {
		return dcode;
	}

	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}

	public void setErosionTypeId(Integer erosionTypeId) {
		this.erosionTypeId = erosionTypeId;
	}

	public BigDecimal getAffectedArea() {
		return affectedArea;
	}

	public void setAffectedArea(BigDecimal affectedArea) {
		this.affectedArea = affectedArea;
	}

	public BigDecimal getRunoff() {
		return runoff;
	}

	public void setRunoff(BigDecimal runoff) {
		this.runoff = runoff;
	}

	public BigDecimal getAvgSoilLoss() {
		return avgSoilLoss;
	}

	public void setAvgSoilLoss(BigDecimal avgSoilLoss) {
		this.avgSoilLoss = avgSoilLoss;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	public Integer getYearId() {
		return yearId;
	}

	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}
	
    
}
