package com.jumbo.webservice.sfNew.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风响应报文
 * 
 * @author dly
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class SfResponse extends BaseModel {

    /**
     * 
     */
    
    public final static String STATUS_ERROR = "ERR";
    public final static String STATUS_OK = "OK";
    
    private static final long serialVersionUID = 2787998712955161596L;
    @XmlAttribute(name = "service")
    private String service = "";
    @XmlElement(required = true, name = "Head")
    private String head;
    @XmlElement(name = "Body")
    private String body;
    @XmlElement(required = true, name = "ERROR")
    private String error;
    private Object bodyObj;
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getError() {
        return error;
    }

    public void setErroy(String error) {
        this.error = error;
    }

    public Object getBodyObj() {
        return bodyObj;
    }

    public void setBodyObj(Object bodyObj) {
        this.bodyObj = bodyObj;
    }
}
