package gov.dolr.wdcpmksy3.PPR.dto;

public class MicroWatershedDTO {
    private Integer mwId;
    private String mwName;

    public MicroWatershedDTO(Integer mwId, String mwName) {
        this.mwId = mwId;
        this.mwName = mwName;
    }

    // getters
    public Integer getMwId() { return mwId; }
    public String getMwName() { return mwName; }
}

