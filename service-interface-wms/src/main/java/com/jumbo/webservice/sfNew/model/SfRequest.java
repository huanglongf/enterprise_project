package com.jumbo.webservice.sfNew.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * SF请求报文
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Request")
public class SfRequest extends BaseModel {

    private static final long serialVersionUID = 7784852701689454375L;

    @XmlAttribute(required = true, name = "service")
    private String service;
    @XmlAttribute(required = true, name = "lang")
    private String lang = "zh-CN";
    @XmlElement(required = true, name = "Head")
    private String head;
    @XmlElement(required = true, name = "Body")
    private String body;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
