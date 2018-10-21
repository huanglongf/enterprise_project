package com.jumbo.webservice.sf;

import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.model.warehouse.SfOrderCancelQueue;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.sf.model.SfOrderCancelResponse;
import com.jumbo.webservice.sf.model.SfOrderResponse;

public class SfWebserviceClient {
    protected static final Logger logger = LoggerFactory.getLogger(SfWebserviceClient.class);
    private static final QName SERVICE_NAME = new QName("http://service.serviceprovide.module.sf.com/", "CustomerServiceService");

    private static ICustomerService getPord() {
        URL wsdlURL = CustomerServiceService.WSDL_LOCATION;
        CustomerServiceService ss = new CustomerServiceService(wsdlURL, SERVICE_NAME);
        return ss.getCustomerServicePort();
    }

    public static SfOrderResponse createSfOrder(String putXml) {
        String str = getPord().orderService(putXml);
        return (SfOrderResponse) MarshallerUtil.buildJaxb(SfOrderResponse.class, str);
    }

    public static SfOrderCancelResponse cancelSfOrder(SfOrderCancelQueue q) {
        String putXml = MarshallerUtil.buildJaxb(q);
        logger.error(putXml);
        String str = getPord().cancelOrderService(putXml);
        logger.error(str);
        return (SfOrderCancelResponse) MarshallerUtil.buildJaxb(SfOrderCancelResponse.class, str);
    }
}
