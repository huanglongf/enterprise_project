package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"obj"})
@XmlRootElement(name = "RequestOrderList")
public class RequestOrderListCommand implements Serializable {

    private static final long serialVersionUID = -3836140745600150741L;

    @XmlElements({@XmlElement(name = "source", type = RequestOrderInfo.class), @XmlElement(name = "sourceWh", type = RequestOrderInfo.class), @XmlElement(name = "staCode", type = RequestOrderInfo.class),
            @XmlElement(name = "lpCode", type = RequestOrderInfo.class), @XmlElement(name = "status", type = RequestOrderInfo.class), @XmlElement(name = "remark", type = RequestOrderInfo.class),
            @XmlElement(name = "transferFee", type = RequestOrderInfo.class), @XmlElement(name = "receiver", type = RequestOrderInfo.class), @XmlElement(name = "totalActual", type = RequestOrderInfo.class),
            @XmlElement(name = "items", type = RequestOrderInfo.class), @XmlElement(name = "RequestOrderInfo", type = RequestOrderInfo.class)})
    private List<Object> obj;

    public List<Object> getObj() {
        return obj;
    }

    public void setObj(List<Object> obj) {
        this.obj = obj;
    }

}
