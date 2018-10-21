package com.jumbo.webservice.ems.command.New;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.webservice.ems.command.EmsOrderBillNoResponseAssignIds;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "responseApply")
public class EmsOrderBillNoResponse2 {

    /**
     * //是否执行成功，1是成功，0是失败
     */
    @XmlElement(required = true, name = "result")
    private String result = "";

    @XmlElement(required = true, name = "errorDesc")
    private String errorDesc = "";

    @XmlElement(required = true, name = "assignIds")
    private EmsOrderBillNoResponseAssignIds assignIds;

}
