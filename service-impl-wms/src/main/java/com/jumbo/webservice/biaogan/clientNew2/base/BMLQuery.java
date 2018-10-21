/*
 * 
 */

package com.jumbo.webservice.biaogan.clientNew2.base;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

import com.jumbo.webservice.biaogan.client.base.PushExpressInfo;

/**
 * This class was generated by Apache CXF 2.2.9 Wed Feb 20 13:19:45 CST 2013 Generated source
 * version: 2.2.9
 * 
 */


@WebServiceClient(name = "BMLQuery", wsdlLocation = "http://58.210.118.230:9013/order/BMLservices/BMLQuery?wsdl", targetNamespace = "http://BML")
public class BMLQuery extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://BML", "BMLQuery");
    public final static QName BMLQueryHttpPort = new QName("http://BML", "BMLQueryHttpPort");
    
	static {
		URL url = PushExpressInfo.class.getResource("/wsdl/Biaogan_new2.wsdl");
		WSDL_LOCATION = url;
	}
    

    public BMLQuery(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public BMLQuery(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BMLQuery() {
        super(WSDL_LOCATION, SERVICE);
    }


    /**
     * 
     * @return returns BMLQueryPortType
     */
    @WebEndpoint(name = "BMLQueryHttpPort")
    public BMLQueryPortType getBMLQueryHttpPort() {
        return super.getPort(BMLQueryHttpPort, BMLQueryPortType.class);
    }

    /**
     * 
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.
     *        Supported features not in the <code>features</code> parameter will have their default
     *        values.
     * @return returns BMLQueryPortType
     */
    @WebEndpoint(name = "BMLQueryHttpPort")
    public BMLQueryPortType getBMLQueryHttpPort(WebServiceFeature... features) {
        return super.getPort(BMLQueryHttpPort, BMLQueryPortType.class, features);
    }

}