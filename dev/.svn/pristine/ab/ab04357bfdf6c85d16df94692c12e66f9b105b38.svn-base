package com.bt.orderPlatform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	private String service;
	@XmlElement(name = "Head")
	private String head;
	@XmlElement(name = "Body")
	private SfexpressServiceResponseBodyBean responseBody;
	@XmlElement(name = "ERROR")
	private SfexpressServiceResponseErrorBean error;
	
	
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
	public SfexpressServiceResponseBodyBean getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(SfexpressServiceResponseBodyBean responseBody) {
		this.responseBody = responseBody;
	}
	public SfexpressServiceResponseErrorBean getError() {
		return error;
	}
	public void setError(SfexpressServiceResponseErrorBean error) {
		this.error = error;
	}
	
	public String toString(){
		return "{service = \"" + service + "\",head = \"" 
				+ head + "\",responseBody = \"" + responseBody 
				+ "\",error = \"" + error + "\"}";
	}
	
	/***
	 * 查看相应是否成功
	 * <b>方法名：</b>：isSuccess<br>
	 * <b>功能说明：</b>：TODO<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月22日 下午6:00:59
	 * @return
	 */
	public boolean isSuccess(){
	    
	      return this.head != null && this.head.equals("OK");
	}
	
	
}
