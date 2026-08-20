package gov.dolr.wdcpmksy3.PPR.dto;

public class GPDropdownDTO {

    private Integer gcode;
    private String gramPanchayatName;

    public GPDropdownDTO(Integer gcode, String gramPanchayatName) {
        this.gcode = gcode;
        this.gramPanchayatName = gramPanchayatName;
    }

    public Integer getGcode() {
        return gcode;
    }

    public String getGramPanchayatName() {
        return gramPanchayatName;
    }
}