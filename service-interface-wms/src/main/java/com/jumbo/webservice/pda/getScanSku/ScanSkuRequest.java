package com.jumbo.webservice.pda.getScanSku;


import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getScanSkuRequest", propOrder = {"getScanSkuRequestBody"})
public class ScanSkuRequest implements Serializable{
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = 8237996883404694062L;
	
	    @XmlElement(required = true)
	    protected List<GetScanSkuRequestBody> getScanSkuRequestBody;
	    
		public List<GetScanSkuRequestBody> getGetScanSkuRequestBody() {
			return getScanSkuRequestBody;
		}
		public void setGetScanSkuRequestBody(List<GetScanSkuRequestBody> getScanSkuRequestBody) {
			this.getScanSkuRequestBody = getScanSkuRequestBody;
		}
		
	    
	
	
	


}
