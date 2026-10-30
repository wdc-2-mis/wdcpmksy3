package gov.dolr.wdcpmksy3.PPR.dto;

import java.util.ArrayList;
import java.util.List;

public class ReferBackRequest {

    private Integer tranId;

    private List<ReferBackMenuItemRequest> items = new ArrayList<>();

    public Integer getTranId() {
        return tranId;
    }

    public void setTranId(Integer tranId) {
        this.tranId = tranId;
    }

    public List<ReferBackMenuItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ReferBackMenuItemRequest> items) {
        this.items = items;
    }
}
