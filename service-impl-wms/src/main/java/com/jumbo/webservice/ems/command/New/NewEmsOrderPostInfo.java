package com.jumbo.webservice.ems.command.New;

import java.util.ArrayList;
import java.util.List;


public class NewEmsOrderPostInfo {

    private List<NewWaybill> waybills = new ArrayList<NewWaybill>();

    public List<NewWaybill> getWaybills() {
        return waybills;
    }

    public void setWaybills(List<NewWaybill> waybills) {
        this.waybills = waybills;
    }



}
