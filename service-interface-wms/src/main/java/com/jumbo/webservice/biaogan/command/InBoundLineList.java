package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "products")
public class InBoundLineList implements Serializable {


    private static final long serialVersionUID = -3677843842808582105L;
    @XmlElement(name = "productInfo", type = InBoundLineCommand.class)
    public List<InBoundLineCommand> inboundList;

    public List<InBoundLineCommand> getInboundList() {
        return inboundList;
    }

    public void setInboundList(List<InBoundLineCommand> inboundList) {
        this.inboundList = inboundList;
    }


}
