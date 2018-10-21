package com.jumbo.webservice.sku.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.jumbo.webservice.base.AuthenticationFailedException;
import com.jumbo.webservice.pda.getScanSku.ScanSkuRequest;

@WebService(targetNamespace = "http://webservice.jumbo.com/sku/", name = "SkuService")
//@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ScanSkuService {
//	 @WebResult(name = "scanSkuResponse", targetNamespace = "", partName = "scanSkuResponse")
	 @WebMethod
	 public void saveScanRecords(@WebParam(partName = "getScanSkuRequest", name = "getScanSkuRequest", targetNamespace = "http://webservice.jumbo.com/sku/") ScanSkuRequest xml)
	            throws AuthenticationFailedException;

}
