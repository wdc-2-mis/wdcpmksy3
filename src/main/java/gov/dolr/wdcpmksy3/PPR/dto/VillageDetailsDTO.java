package gov.dolr.wdcpmksy3.PPR.dto;

public class VillageDetailsDTO {

    private Integer vcode;
    private Integer gcode;
    private Integer bcode;

    public VillageDetailsDTO(Integer vcode, Integer gcode, Integer bcode) {
        this.vcode = vcode;
        this.gcode = gcode;
        this.bcode = bcode;
    }

    public Integer getVcode() {
        return vcode;
    }

    public Integer getGcode() {
        return gcode;
    }

    public Integer getBcode() {
        return bcode;
    }
}
