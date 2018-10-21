package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ASNs")
public class Asns implements Serializable {

    private static final long serialVersionUID = 1135845901523678930L;

    /**
     * 仓库入库单号
     */
    private String ASNNo;

    /**
     * 客户ID
     */
    private String CustmorOrderNo;

    /**
     * 收货时间
     */
    private String ExpectedArriveTime;

    @XmlElements({@XmlElement(name = "SkuCode", type = Detail.class), @XmlElement(name = "ReceivedTime", type = Detail.class), @XmlElement(name = "ExpectedQty", type = Detail.class), @XmlElement(name = "ReceivedQty", type = Detail.class),
            @XmlElement(name = "Lotatt01", type = Detail.class), @XmlElement(name = "Lotatt02", type = Detail.class), @XmlElement(name = "Lotatt03", type = Detail.class), @XmlElement(name = "Lotatt04", type = Detail.class),
            @XmlElement(name = "Lotatt05", type = Detail.class), @XmlElement(name = "Lotatt06", type = Detail.class), @XmlElement(name = "Lotatt07", type = Detail.class), @XmlElement(name = "Lotatt08", type = Detail.class),
            @XmlElement(name = "Lotatt09", type = Detail.class), @XmlElement(name = "Lotatt10", type = Detail.class), @XmlElement(name = "Lotatt11", type = Detail.class), @XmlElement(name = "Lotatt12", type = Detail.class),
            @XmlElement(name = "Detail", type = Detail.class)})
    private List<Object> obj;

    public String getASNNo() {
        return ASNNo;
    }

    public void setASNNo(String aSNNo) {
        ASNNo = aSNNo;
    }

    public String getCustmorOrderNo() {
        return CustmorOrderNo;
    }

    public void setCustmorOrderNo(String custmorOrderNo) {
        CustmorOrderNo = custmorOrderNo;
    }

    public String getExpectedArriveTime() {
        return ExpectedArriveTime;
    }

    public void setExpectedArriveTime(String expectedArriveTime) {
        ExpectedArriveTime = expectedArriveTime;
    }

    public List<Object> getObj() {
        return obj;
    }

    public void setObj(List<Object> obj) {
        this.obj = obj;
    }



}
