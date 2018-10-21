package com.jumbo.webservice.sf;

import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.webservice.sf.order.IOutsideToLscmService;
import com.jumbo.webservice.sf.order.OutsideToLscmServiceService;
import com.jumbo.webservice.sf.order.OutsideToLscmServiceService2;
import com.jumbo.webservice.sfwarehouse.command.FailResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsCancelSailOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsCancelSailOrderResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogBatchRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogBatchResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsMerchantCatalogResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsPurchaseOrderResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderStatusQueryRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsSailOrderStatusQueryResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsVendorRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsVendorResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.system.ChooseOption;

@SuppressWarnings("serial")
@Service("sfOrderClient")
public class SfOrderClient extends BaseManagerImpl implements SfOrderClientInter {
    protected static final Logger logger = LoggerFactory.getLogger(SfOrderClient.class);
    private static final QName SERVICE_NAME = new QName("http://service.warehouse.integration.sf.com/", "OutsideToLscmServiceService");

    public static final String SF_LENG_CACHE = "sfLengCache";
    TimeHashMap<String, String> sfLengCache = new TimeHashMap<String, String>();

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    private static IOutsideToLscmService getPord() {
        URL wsdlUrl = OutsideToLscmServiceService.WSDL_LOCATION;
        OutsideToLscmServiceService wss = new OutsideToLscmServiceService(wsdlUrl, SERVICE_NAME);
        return wss.getOutsideToLscmServicePort();
    }

    private static IOutsideToLscmService getPord2() {// 冷链接口升级 5.0
        URL wsdlUrl = OutsideToLscmServiceService2.WSDL_LOCATION;
        OutsideToLscmServiceService wss = new OutsideToLscmServiceService(wsdlUrl, SERVICE_NAME);
        return wss.getOutsideToLscmServicePort();
    }

    /**
     * SF:入库单(采购订单)接口 ==WMS:顺丰接口-采购入库单接口 1
     * 
     * @param request
     * @return
     */
    public WmsPurchaseOrderResponse wmsPurchaseOrder(WmsPurchaseOrderRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
        }
        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug(responseXml);
        }
        WmsPurchaseOrderResponse response = null;
        try {
            response = (WmsPurchaseOrderResponse) MarshallerUtil.buildJaxb(WmsPurchaseOrderResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:出库单（销售订单）接口==WMS:顺丰接口-销售订单接口 2
     * 
     * @param request
     * @return
     */
    public WmsSailOrderResponse wmsSailOrder(WmsSailOrderRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
        }
        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug(responseXml);
        }
        WmsSailOrderResponse response = null;
        try {
            response = (WmsSailOrderResponse) MarshallerUtil.buildJaxb(WmsSailOrderResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:取消订单(销售订单)接口==WMS:顺丰接口-取消订单接口 3
     * 
     * @param request
     * @return
     */
    public WmsCancelSailOrderResponse wmsCanclSailOrder(WmsCancelSailOrderRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
        }
        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug(responseXml);
        }
        WmsCancelSailOrderResponse response = null;
        try {
            response = (WmsCancelSailOrderResponse) MarshallerUtil.buildJaxb(WmsCancelSailOrderResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:商品目录接口==WMS:顺丰接口-商品目录接口 4
     * 
     * @param request
     * @return
     */
    public WmsMerchantCatalogResponse wmsMerchantCatalog(WmsMerchantCatalogRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
        }
        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug("Call sf interface end------------------------------------->");
            logger.debug(responseXml);
        }
        WmsMerchantCatalogResponse response = null;
        try {
            response = (WmsMerchantCatalogResponse) MarshallerUtil.buildJaxb(WmsMerchantCatalogResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:商品目录接口(批量)==WMS:顺丰接口-商品目录接口(批量) 5
     * 
     * @param request
     * @return
     */
    public WmsMerchantCatalogBatchResponse wmsMerchantCatalogBatch(WmsMerchantCatalogBatchRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
        }

        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug(responseXml);
        }
        WmsMerchantCatalogBatchResponse response = null;
        try {
            response = (WmsMerchantCatalogBatchResponse) MarshallerUtil.buildJaxb(WmsMerchantCatalogBatchResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:供应商接口==WMS:顺丰接口-供应商接口 6
     * 
     * @param request
     * @return
     */
    public WmsVendorResponse wmsVendor(WmsVendorRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
            logger.debug("Call sf wmsVendorService begin------------------------------------->");
        }
        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug("Call sf wmsVendorService end------------------------------------->");
            logger.debug(responseXml);
        }
        WmsVendorResponse response = null;
        try {
            response = (WmsVendorResponse) MarshallerUtil.buildJaxb(WmsVendorResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:出库单（销售订单）状态查询接口==WMS:顺丰接口-出库订单（销售订单）状态查询接口 7
     * 
     * @param request
     * @return
     */
    public WmsSailOrderStatusQueryResponse wmsSailOrderStatusQuery(WmsSailOrderStatusQueryRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        if (logger.isDebugEnabled()) {
            logger.debug(requestXml);
        }
        // String responseXml = getPord().outsideToLscmService(requestXml);
        String responseXml = dealSfCache(requestXml);
        if (logger.isDebugEnabled()) {
            logger.debug(responseXml);
        }
        WmsSailOrderStatusQueryResponse response = null;
        try {
            response = (WmsSailOrderStatusQueryResponse) MarshallerUtil.buildJaxb(WmsSailOrderStatusQueryResponse.class, responseXml);
        } catch (Exception e) {
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {fr.getReasoncode(), fr.getRemark()});
        }
        return response;
    }

    public String dealSfCache(String putXml) {
        String responseXML = null;
        String result = sfLengCache.get(SF_LENG_CACHE);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(SF_LENG_CACHE, SF_LENG_CACHE);
            sfLengCache.put("sfLengCache", op.getOptionValue(), 8 * 60 * 1000);// 8min
        }
        if ("1".equals(sfLengCache.get(SF_LENG_CACHE)) || sfLengCache.get(SF_LENG_CACHE) == null) {
            responseXML = getPord2().outsideToLscmService(putXml);
        } else {
            responseXML = getPord().outsideToLscmService(putXml);
        }
        return responseXML;
    }

}
