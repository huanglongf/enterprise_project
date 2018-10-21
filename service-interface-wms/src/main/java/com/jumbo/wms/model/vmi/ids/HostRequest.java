package com.jumbo.wms.model.vmi.ids;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"Key", "RequestTime", "Sign", "Version", "ServiceType", "Data"})
@XmlRootElement(name = "bhSyncResponse")
public class HostRequest implements Serializable {

    private static final long serialVersionUID = -98746788439656661L;
    @XmlElement(required = true)
    protected String Key;
    @XmlElement(required = true)
    protected String RequestTime;
    @XmlElement(required = true)
    protected String Sign;
    @XmlElement(required = true)
    protected String Version;
    @XmlElement(required = true)
    protected String ServiceType;
    @XmlElement(required = true)
    protected String Data;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

}
