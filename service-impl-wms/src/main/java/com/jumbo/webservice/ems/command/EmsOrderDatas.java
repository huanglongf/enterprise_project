package com.jumbo.webservice.ems.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "printDatas")
public class EmsOrderDatas {

    @XmlElement(required = true, name = "printData")
    private List<EmsOrderData> printData = new ArrayList<EmsOrderData>();

    public List<EmsOrderData> getPrintData() {
        return printData;
    }

    public void setPrintData(List<EmsOrderData> printData) {
        this.printData = printData;
    }
}
