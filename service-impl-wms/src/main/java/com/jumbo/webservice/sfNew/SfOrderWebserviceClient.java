package com.jumbo.webservice.sfNew;

import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.webservice.sfNew.model.MongoSfOrder;
import com.jumbo.webservice.sfNew.model.MongoSfOrderConfirm;
import com.jumbo.webservice.sfNew.model.MongoSfOrderConfirmReturn;
import com.jumbo.webservice.sfNew.model.MongoSfOrderReturn;
import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.webservice.sfNew.model.SfOrderConfirm;
import com.jumbo.webservice.sfNew.model.SfOrderConfirmResponse;
import com.jumbo.webservice.sfNew.model.SfOrderResponse;
import com.jumbo.webservice.sfNew.model.SfRequest;
import com.jumbo.webservice.sfNew.model.SfResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.system.ChooseOption;

@SuppressWarnings("serial")
@Service("sfOrderWebserviceClient")
public class SfOrderWebserviceClient extends BaseManagerImpl implements SfOrderWebserviceClientInter {
    public static final String SF_CACHE = "sfCache";
    public static final String SF_CACHE2 = "sfCache2";

    protected static final Logger logger = LoggerFactory.getLogger(SfOrderWebserviceClient.class);
    static TimeHashMap<String, String> sfCache = new TimeHashMap<String, String>();

    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    private static IService getPord() {
        URL wsdlURL = CommonServiceService.WSDL_LOCATION;
        CommonServiceService ss = new CommonServiceService(wsdlURL, CommonServiceService.SERVICE);
        logger.info("SF WSDL init CommonServiceService");
        IService isc = ss.getCommonServicePort();
        Client client = ClientProxy.getClient(isc);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(8000);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(8000);
        http.setClient(httpClientPolicy);
        return isc;
    }

    private static IService2 getPord2() {// https请求
        URL wsdlURL = CommonServiceService2.WSDL_LOCATION;
        CommonServiceService2 ss = new CommonServiceService2(wsdlURL, CommonServiceService2.SERVICE);
        logger.info("SF WSDL init CommonServiceService");
        IService2 isc = ss.getCommonServicePort();
        Client client = ClientProxy.getClient(isc);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(8000);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(8000);
        http.setClient(httpClientPolicy);
        return isc;
    }


    public String GetSfCache() {// 获取缓存
        String result = sfCache.get(SF_CACHE2);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(SF_CACHE2, SF_CACHE2);
            sfCache.put(SF_CACHE2, op.getOptionValue(), 8 * 60 * 1000);// 8min
            return sfCache.get(SF_CACHE2);
        }
        return result;
    }

    /**
     * 下单
     * 
     * @param order
     * @return
     */
    public SfResponse creSfOrder(SfOrder order, String sfClientCode, String checkword, int i) {

        Date date = new Date();
        MongoSfOrder mongoSfOrder = new MongoSfOrder();
        MongoSfOrderReturn mongoSfOrderReturn = new MongoSfOrderReturn();
        // createSfOrder改为creSfOrder 避免CodeGenerationAspect切入报错
        try {
            String responseXML = null;
            String putXml = getRequestXml(SfOrder.SERVICE_NAME, MarshallerUtil.buildJaxb(order), sfClientCode, checkword);
            logger.debug("Call the SF interface! request:" + putXml);
            // sf下单日志
            if (GetSfCache() != null) {
                try {
                    mongoSfOrder.setId(order.getOrderId());
                    mongoSfOrder.setContent(putXml);
                    mongoSfOrder.setCreateTime(date);
                    mongoSfOrder.setdAddress(order.getdAddress());
                    mongoSfOrder.setdCity(order.getdCity());
                    mongoSfOrder.setdContact(order.getdContact());
                    mongoSfOrder.setdProvince(order.getdProvince());
                    mongoSfOrder.setdTel(order.getdTel());
                    mongoOperation.save(mongoSfOrder);
                } catch (Exception e) {
                    logger.error("creSfOrder01:" + putXml);
                }
                // end
            }
            responseXML = dealSfCache(putXml);
            logger.debug(responseXML);
            // sf 反馈日志
            if (GetSfCache() != null) {
                try {
                    mongoSfOrderReturn.setId(order.getOrderId());
                    mongoSfOrderReturn.setContent(responseXML);
                    mongoSfOrderReturn.setCreateTime(date);
                    mongoSfOrderReturn.setdAddress(order.getdAddress());
                    mongoSfOrderReturn.setdCity(order.getdCity());
                    mongoSfOrderReturn.setdContact(order.getdContact());
                    mongoSfOrderReturn.setdProvince(order.getdProvince());
                    mongoSfOrderReturn.setdTel(order.getdTel());
                    mongoOperation.save(mongoSfOrderReturn);
                } catch (Exception e) {
                    logger.error("creSfOrder02:" + responseXML);
                }
                // end
            }

            return getResponse(responseXML, SfOrderResponse.class);
        } catch (Exception e) {
            logger.error("", e);
            i++;
            if (i <= 3) {
                return creSfOrder(order, sfClientCode, checkword, i);
            } else {
                throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR);
            }
        }
    }


    public String dealSfCache(String putXml) {
        String responseXML = null;
        String result = sfCache.get(SF_CACHE);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(SF_CACHE, SF_CACHE);
            sfCache.put("sfCache", op.getOptionValue(), 8 * 60 * 1000);// 8min
        }
        if ("1".equals(sfCache.get(SF_CACHE)) || sfCache.get(SF_CACHE) == null) {// https下单接口
            responseXML = getPord2().sfexpressService(putXml);
            logger.debug("https");
        } else {// http下单接口
            responseXML = getPord().sfexpressService(putXml);
            logger.debug("http");
        }
        return responseXML;
    }


    /**
     * 确认订单
     * 
     * @param order
     * @return
     */
    public SfResponse comfirmOrder(SfOrderConfirm order, String sfClientCode, String checkword) {
        Date date = new Date();
        MongoSfOrderConfirm mongoSfOrderConfirm = new MongoSfOrderConfirm();
        MongoSfOrderConfirmReturn mongoSfOrderConfirmReturn = new MongoSfOrderConfirmReturn();
        String putXml = getRequestXml(SfOrderConfirm.SERVICE_NAME, MarshallerUtil.buildJaxb(order), sfClientCode, checkword);
        logger.debug(putXml);
        if (GetSfCache() != null) {
            try {
                mongoSfOrderConfirm.setOrderId(order.getOrderId());
                mongoSfOrderConfirm.setContent(putXml);
                mongoSfOrderConfirm.setDealtype(order.getDealtype());
                mongoSfOrderConfirm.setMailno(order.getMailno());
                mongoSfOrderConfirm.setCreateTime(date);
                mongoOperation.save(mongoSfOrderConfirm);
            } catch (Exception e) {
                logger.error("comfirmOrder01:" + putXml);
            }
        }

        // String responseXML = getPord().sfexpressService(putXml);
        String responseXML = dealSfCache(putXml);
        logger.debug(responseXML);
        if (GetSfCache() != null) {
            try {
                mongoSfOrderConfirmReturn.setOrderId(order.getOrderId());
                mongoSfOrderConfirmReturn.setContent(responseXML);
                mongoSfOrderConfirmReturn.setDealtype(order.getDealtype());
                mongoSfOrderConfirmReturn.setMailno(order.getMailno());
                mongoSfOrderConfirmReturn.setCreateTime(date);
                mongoOperation.save(mongoSfOrderConfirmReturn);
            } catch (Exception e) {
                logger.error("comfirmOrder02:" + responseXML);
            }
        }


        return getResponse(responseXML, SfOrderConfirmResponse.class);
    }

    /**
     * 获取响应报文
     * 
     * @param responseXML
     * @param bodyClass
     * @return
     */
    private static SfResponse getResponse(String responseXML, @SuppressWarnings("rawtypes") Class bodyClass) {
        SfResponse rs = null;
        if (responseXML != null) {
            rs = (SfResponse) MarshallerUtil.buildJaxb(SfResponse.class, responseXML);
            if (rs != null && SfResponse.STATUS_OK.equals(rs.getHead())) {
                String body = responseXML.substring(responseXML.indexOf("Body") + 5, responseXML.lastIndexOf("Body") - 2);
                rs.setBody(body);
                rs.setBodyObj(MarshallerUtil.buildJaxb(bodyClass, body));
            }
        }
        return rs;
    }

    /**
     * 获取请求报文
     * 
     * @param service
     * @param requestDate
     * @return
     */
    private static String getRequestXml(String service, String body, String sfClientCode, String checkword) {
        SfRequest request = new SfRequest();
        request.setService(service);
        // request.setHead("SHBZ,]Lrdp7YibHnD-_KQ[SNq");
        request.setHead(sfClientCode + "," + checkword);
        request.setBody("BodyDate");
        String regEx = "<\\?.*\\?>"; // 去除<?xml ...... ?>
        Pattern pat = Pattern.compile(regEx);
        String requesTstr = MarshallerUtil.buildJaxb(request);
        try {
            requesTstr = requesTstr.replaceAll("BodyDate", body);
        } catch (Exception e) {
            requesTstr = java.util.regex.Matcher.quoteReplacement(requesTstr);
            requesTstr = requesTstr.replaceAll("BodyDate", body);
        }
        Matcher mat = pat.matcher(requesTstr);
        requesTstr = mat.replaceAll("");
        return requesTstr;
    }
}
