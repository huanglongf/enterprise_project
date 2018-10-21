package com.bt.orderPlatform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceRequestBodyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Order")
	private WaybillMaster requestOrder;
	
	
	@XmlElement(name = "OrderZD")
    private SfOrderZDServiceRequestBean orderZDServiceRequestBean;
	
	 @XmlElement(name = "OrderZDResponse")
     private OrderZDServiceResponseInfo orderZDResponse;
	
	public WaybillMaster getRequestOrder() {
		return requestOrder;
	}
	public void setRequestOrder(WaybillMaster requestOrder) {
		this.requestOrder = requestOrder;
	}
    
    public SfOrderZDServiceRequestBean getOrderZDServiceRequestBean(){
        return orderZDServiceRequestBean;
    }
    
    public void setOrderZDServiceRequestBean(SfOrderZDServiceRequestBean orderZDServiceRequestBean){
        this.orderZDServiceRequestBean = orderZDServiceRequestBean;
    }
    
    public OrderZDServiceResponseInfo getOrderZDResponse(){
        return orderZDResponse;
    }
    
    public void setOrderZDResponse(OrderZDServiceResponseInfo orderZDResponse){
        this.orderZDResponse = orderZDResponse;
    }
	
}
