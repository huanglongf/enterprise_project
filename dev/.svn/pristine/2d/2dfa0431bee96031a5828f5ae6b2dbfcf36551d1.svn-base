package com.bt.orderPlatform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class SfOrderZDServiceRequestBean implements Serializable {

	
	/** serialVersionUID */
    
    private static final long serialVersionUID = 6603405500323026526L;
    
    /**母单订单号**/
    @XmlAttribute
    private String orderid;
    
    /**子单数量,最大20**/
    @XmlAttribute(name = "parcel_quantity")
    private int parcel_quantity;

    
    public String getOrderid(){
        return orderid;
    }

    
    public void setOrderid(String orderid){
        this.orderid = orderid;
    }

    
    public int getParcel_quantity(){
        return parcel_quantity;
    }

    public void setParcel_quantity(int parcel_quantity){
        this.parcel_quantity = parcel_quantity;
    }

	
}
