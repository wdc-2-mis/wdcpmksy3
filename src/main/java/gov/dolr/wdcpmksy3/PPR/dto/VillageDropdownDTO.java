package gov.dolr.wdcpmksy3.PPR.dto;

public class VillageDropdownDTO {

    private Integer vcode;
    private String villageName;

    public VillageDropdownDTO(Integer vcode, String villageName) {
        this.vcode = vcode;
        this.villageName = villageName;
    }

    public Integer getVcode() {
        return vcode;
    }

    public String getVillageName() {
        return villageName;
    }
}
