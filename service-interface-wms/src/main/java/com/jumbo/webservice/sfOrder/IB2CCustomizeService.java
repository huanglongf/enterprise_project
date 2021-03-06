package com.jumbo.webservice.sfOrder;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.9 Wed Jan 09 14:21:07 CST 2013 Generated source
 * version: 2.2.9
 * 
 */

@WebService(targetNamespace = "http://service.serviceprovide.module.sf.com/", name = "IB2CCustomizeService")
@XmlSeeAlso({ObjectFactory.class})
public interface IB2CCustomizeService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "confirmOrderService", targetNamespace = "http://service.serviceprovide.module.sf.com/", className = "com.jumbo.webservice.sfOrder.ConfirmOrderService")
    @WebMethod
    @ResponseWrapper(localName = "confirmOrderServiceResponse", targetNamespace = "http://service.serviceprovide.module.sf.com/", className = "com.jumbo.webservice.sfOrder.ConfirmOrderServiceResponse")
    public java.lang.String confirmOrderService(@WebParam(name = "xml", targetNamespace = "") java.lang.String xml);

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "filterOrderServiceForB2C", targetNamespace = "http://service.serviceprovide.module.sf.com/", className = "com.jumbo.webservice.sfOrder.FilterOrderServiceForB2C")
    @WebMethod
    @ResponseWrapper(localName = "filterOrderServiceForB2CResponse", targetNamespace = "http://service.serviceprovide.module.sf.com/", className = "com.jumbo.webservice.sfOrder.FilterOrderServiceForB2CResponse")
    public java.lang.String filterOrderServiceForB2C(@WebParam(name = "xml", targetNamespace = "") java.lang.String xml);
}
