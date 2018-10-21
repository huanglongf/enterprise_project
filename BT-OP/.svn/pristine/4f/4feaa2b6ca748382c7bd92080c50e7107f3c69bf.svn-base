package com.bt.orderPlatform.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceResponseOrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	private String orderid;
	@XmlAttribute
	private String mailno;
	@XmlAttribute
	private String return_tracking_no;
	@XmlAttribute
	private String origincode;
	@XmlAttribute
	private String destcode;
	@XmlAttribute
	private String filter_result;
	@XmlAttribute
	private String remark;
	@XmlElement(name = "Route")
	private List<SfexpressServiceResponseRouteBean> route;
	
	public List<SfexpressServiceResponseRouteBean> getRoute() {
		return route;
	}
	public void setRoute(List<SfexpressServiceResponseRouteBean> route) {
		this.route = route;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getMailno() {
		return mailno;
	}
	public void setMailno(String mailno) {
		this.mailno = mailno;
	}
	public String getReturn_tracking_no() {
		return return_tracking_no;
	}
	public void setReturn_tracking_no(String return_tracking_no) {
		this.return_tracking_no = return_tracking_no;
	}
	public String getOrigincode() {
		return origincode;
	}
	public void setOrigincode(String origincode) {
		this.origincode = origincode;
	}
	public String getDestcode() {
		return destcode;
	}
	public void setDestcode(String destcode) {
		this.destcode = destcode;
	}
	public String getFilter_result() {
		return filter_result;
	}
	public void setFilter_result(String filter_result) {
		this.filter_result = filter_result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String[] getMailNos(){    
	    if(this.mailno!=null){
	        return mailno.split(",");
	    }
	    return null;   
	}
	
}
