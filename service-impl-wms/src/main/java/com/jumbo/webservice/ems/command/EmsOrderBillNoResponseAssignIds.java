package com.jumbo.webservice.ems.command;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "assignIds")
public class EmsOrderBillNoResponseAssignIds {

    @XmlElement(name = "assignId", required = true)
    public List<EmsOrderBillNoResponseAssignId> assignId;

    public List<EmsOrderBillNoResponseAssignId> getAssignId() {
        return assignId;
    }

    public void setAssignId(List<EmsOrderBillNoResponseAssignId> assignId) {
        this.assignId = assignId;
    }

}
