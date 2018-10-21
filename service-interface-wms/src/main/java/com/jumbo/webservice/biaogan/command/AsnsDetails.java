package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ASNDetails")
public class AsnsDetails implements Serializable {


    private static final long serialVersionUID = -2964419244842303304L;


    @XmlElements({@XmlElement(name = "ASNNo", type = Asns.class), @XmlElement(name = "CustmorOrderNo", type = Asns.class), @XmlElement(name = "ExpectedArriveTime", type = Asns.class), @XmlElement(name = "obj", type = Asns.class),
            @XmlElement(name = "ASNs", type = Asns.class)})
    private List<Object> asns;


    public List<Object> getAsns() {
        return asns;
    }


    public void setAsns(List<Object> asns) {
        this.asns = asns;
    }


}
