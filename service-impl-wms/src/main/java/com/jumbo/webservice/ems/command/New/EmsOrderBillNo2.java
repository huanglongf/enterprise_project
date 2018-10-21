package com.jumbo.webservice.ems.command.New;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.webservice.ems.EmsServiceClient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLInfo")
public class EmsOrderBillNo2 {
    /**
     * 业务类型，非邮业务：05 标准快递：06 经济快递：07
     */
    @XmlElement(required = true, name = "bizcode")
    private String bizcode = EmsServiceClient.BUSINESS_TYPE_NORMAL;

    /**
     * 需要详情单数量，最多输入100个
     */
    @XmlElement(required = true, name = "count")
    private String count = "";

    /**
     * 对接授权码 ,接口调用方的身份凭据，由接口提供方提供
     */
    @XmlElement(required = false, name = "authorization")
    private String authorization = "";



    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

}
