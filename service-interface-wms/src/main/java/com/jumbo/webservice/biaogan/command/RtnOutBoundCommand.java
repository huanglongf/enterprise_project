package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "outputBack")
public class RtnOutBoundCommand implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5066935977382958625L;
    public String orderNo = "";
    public String shipNo = "";
    public String weight = "";

    public String shipTime = "";
    public String carrierID = "";
    public String carrierName = "";
    public String customerId = "";
    public String bgNo = "";

    public RtnOutBoundLineList send;



    public RtnOutBoundLineList getSend() {
        return send;
    }

    public void setSend(RtnOutBoundLineList send) {
        this.send = send;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getCarrierID() {
        return carrierID;
    }

    public void setCarrierID(String carrierID) {
        this.carrierID = carrierID;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBgNo() {
        return bgNo;
    }

    public void setBgNo(String bgNo) {
        this.bgNo = bgNo;
    }



}
