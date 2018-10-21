package com.jumbo.webservice.ems.command;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "printDatas")
public class EmsOrderInfoPrintDatas {

    @XmlElement(required = true, name = "printData")
    private List<EmsOrderInfoPrintData> printData = new ArrayList<EmsOrderInfoPrintData>();

    public List<EmsOrderInfoPrintData> getPrintData() {
        return printData;
    }

    public void setPrintData(List<EmsOrderInfoPrintData> printData) {
        this.printData = printData;
    }

}
