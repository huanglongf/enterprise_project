package com.bt.orderPlatform.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceResponseBodyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "OrderResponse")
	private SfexpressServiceResponseOrderBean responseOrder;
	@XmlElement(name = "RouteResponse")
	private SfexpressServiceResponseOrderBean routeResponse;
    @XmlElement(name = "OrderZDResponse")
    private OrderZDServiceResponseInfo orderZDResponse;
	

	
	
    public OrderZDServiceResponseInfo getOrderZDResponse(){
        return orderZDResponse;
    }

    
    public void setOrderZDResponse(OrderZDServiceResponseInfo orderZDResponse){
        this.orderZDResponse = orderZDResponse;
    }

    public SfexpressServiceResponseOrderBean getRouteResponse() {
		return routeResponse;
	}

	public void setRouteResponse(SfexpressServiceResponseOrderBean routeResponse) {
		this.routeResponse = routeResponse;
	}


	public SfexpressServiceResponseOrderBean getResponseOrder() {
		return responseOrder;
	}

	public void setResponseOrder(SfexpressServiceResponseOrderBean responseOrder) {
		this.responseOrder = responseOrder;
	}


	
	
	
	
	
	
}
