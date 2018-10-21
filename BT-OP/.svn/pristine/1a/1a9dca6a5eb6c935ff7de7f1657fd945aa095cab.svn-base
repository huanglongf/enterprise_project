package com.bt.orderPlatform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bt.common.util.CommonUtil;

@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlAttribute(name = "service")
	private String service = "OrderService";
	@XmlAttribute(name = "lang")
	private String lang = "zh-CN";
	@XmlElement(name = "Head")
	private String Head = CommonUtil.getConfig("access_code_sf");
	@XmlElement(name = "Body")
	private SfexpressServiceRequestBodyBean Body;
	
	
	
	
	
	
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getHead() {
		return Head;
	}
	public void setHead(String head) {
		Head = head;
	}
	public SfexpressServiceRequestBodyBean getBody() {
		return Body;
	}
	public void setBody(SfexpressServiceRequestBodyBean body) {
		Body = body;
	}
	
	public static SfexpressServiceRequestBean buildRequest(WaybillMaster master){

		SfexpressServiceRequestBean sfexpressServiceRequestBean = new SfexpressServiceRequestBean();
		
		SfexpressServiceRequestBodyBean body = new SfexpressServiceRequestBodyBean();
		
		body.setRequestOrder(master);
		
		sfexpressServiceRequestBean.setBody(body);
		
		return sfexpressServiceRequestBean;
		
	}
	
	public static SfexpressServiceRequestBean buildRequest(WaybillMaster master,String key){

		SfexpressServiceRequestBean sfexpressServiceRequestBean = new SfexpressServiceRequestBean();
		
		SfexpressServiceRequestBodyBean body = new SfexpressServiceRequestBodyBean();
		
		body.setRequestOrder(master);
		
		sfexpressServiceRequestBean.setBody(body);
		sfexpressServiceRequestBean.setHead(key);
		
		return sfexpressServiceRequestBean;
		
	}
	
   public static SfexpressServiceRequestBean initRequest(String key){

        SfexpressServiceRequestBean sfexpressServiceRequestBean = new SfexpressServiceRequestBean();	        
        SfexpressServiceRequestBodyBean body = new SfexpressServiceRequestBodyBean();
        
        sfexpressServiceRequestBean.setBody(body);
        sfexpressServiceRequestBean.setHead(key);
        
        return sfexpressServiceRequestBean;
    }
	
	
}
