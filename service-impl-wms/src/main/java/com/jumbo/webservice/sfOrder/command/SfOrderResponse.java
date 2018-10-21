package com.jumbo.webservice.sfOrder.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.mq.MarshallerUtil;

/**
 * 顺风下单实体
 * 
 * @author sjk
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderResponse")
public class SfOrderResponse {
    public static void main(String[] args) {
        String str = "<orderResponse><result><status>4</status><remark>0200</remark></result></orderResponse>";
        SfOrderResponse s = (SfOrderResponse) MarshallerUtil.buildJaxb(SfOrderResponse.class, str);
        System.out.println(s.getResult().getStatus());
    }

    @XmlElement(name = "orderid")
    private String orderid;
    @XmlElement(name = "result")
    private SfOrderResultResponse result;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public SfOrderResultResponse getResult() {
        return result;
    }

    public void setResult(SfOrderResultResponse result) {
        this.result = result;
    }

}
