package com.jumbo.webservice.sfOrder;

import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.sfOrder.command.SfComfirmOrder;
import com.jumbo.webservice.sfOrder.command.SfComfirmOrderResponse;
import com.jumbo.webservice.sfOrder.command.SfOrder;
import com.jumbo.webservice.sfOrder.command.SfOrderResponse;

public class SfOrderWebserviceClient {
    protected static final Logger logger = LoggerFactory.getLogger(SfOrderWebserviceClient.class);
    private static final QName SERVICE_NAME = new QName("http://service.serviceprovide.module.sf.com/", "B2CCustomizeServiceService");

    private static IB2CCustomizeService getPord() {
        URL wsdlURL = B2CCustomizeServiceService.WSDL_LOCATION;
        B2CCustomizeServiceService ss = new B2CCustomizeServiceService(wsdlURL, SERVICE_NAME);
        logger.debug("SF WSDL init B2CCustomizeServiceService");
        return ss.getB2CCustomizeServicePort();
    }

    /**
     * 下单
     * 
     * @param order
     * @return
     */
    public static SfOrderResponse createSfOrder(SfOrder order, int i) {
        try {
            String putXml = MarshallerUtil.buildJaxb(order);
            logger.debug(putXml);
            String str = getPord().filterOrderServiceForB2C(putXml);
            logger.debug(str);
            return (SfOrderResponse) MarshallerUtil.buildJaxb(SfOrderResponse.class, str);
        } catch (Exception e) {
            logger.error("",e);
            i++;
            if (i <= 3) {
                return createSfOrder(order, i);
            } else {
                throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR);
            }
        }
    }

    /**
     * 确认订单
     * 
     * @param order
     * @return
     */
    public static SfComfirmOrderResponse comfirmOrder(SfComfirmOrder order) {
        String putXml = MarshallerUtil.buildJaxb(order);
        logger.debug(putXml);
        String str = getPord().confirmOrderService(putXml);
        logger.debug(str);
        return (SfComfirmOrderResponse) MarshallerUtil.buildJaxb(SfComfirmOrderResponse.class, str);
    }
}
