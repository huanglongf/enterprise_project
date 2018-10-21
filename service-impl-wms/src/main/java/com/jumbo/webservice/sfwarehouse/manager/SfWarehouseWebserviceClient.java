package com.jumbo.webservice.sfwarehouse.manager;

import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.sfwarehouse.IWarehouseService;
import com.jumbo.webservice.sfwarehouse.WarehouseServiceService;
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

/**
 * 顺丰接口仓库部分客户端
 * 
 * @author jinlong.ke
 * 
 */
public class SfWarehouseWebserviceClient {
    protected static final Logger logger = LoggerFactory.getLogger(SfWarehouseWebserviceClient.class);
    private static final QName SERVICE_NAME = new QName("http://service.warehouse.integration.sf.com/", "WarehouseServiceService");

    private static IWarehouseService getPord() {
        URL wsdlUrl = WarehouseServiceService.WSDL_LOCATION;
        WarehouseServiceService wss = new WarehouseServiceService(wsdlUrl, SERVICE_NAME);
        logger.error("SfWarehouse wsdl init WarehouseServiceService");
        return wss.getWarehouseServicePort();
    }

    /**
     * SF:入库单(采购订单)接口 ==WMS:顺丰接口-采购入库单接口
     * 
     * @param request
     * @return
     */
    public static WmsPurchaseOrderResponse wmsPurchaseOrder(WmsPurchaseOrderRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        String responseXml = getPord().wmsPurchaseOrderService(requestXml);
        logger.error(responseXml);
        WmsPurchaseOrderResponse response = null;
        try{
            response = (WmsPurchaseOrderResponse) MarshallerUtil.buildJaxb(WmsPurchaseOrderResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:出库单（销售订单）接口==WMS:顺丰接口-销售订单接口
     * 
     * @param request
     * @return
     */
    public static WmsSailOrderResponse wmsSailOrder(WmsSailOrderRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        String responseXml = getPord().wmsSailOrderService(requestXml);
        logger.error(responseXml);
        WmsSailOrderResponse response = null;
        try{
            response = (WmsSailOrderResponse) MarshallerUtil.buildJaxb(WmsSailOrderResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:取消订单(销售订单)接口==WMS:顺丰接口-取消订单接口
     * 
     * @param request
     * @return
     */
    public static WmsCancelSailOrderResponse wmsCanclSailOrder(WmsCancelSailOrderRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        String responseXml = getPord().wmsCancelSailOrderService(requestXml);
        logger.error(responseXml);
        WmsCancelSailOrderResponse response = null;
        try{
            response = (WmsCancelSailOrderResponse) MarshallerUtil.buildJaxb(WmsCancelSailOrderResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:商品目录接口==WMS:顺丰接口-商品目录接口
     * 
     * @param request
     * @return
     */
    public static WmsMerchantCatalogResponse wmsMerchantCatalog(WmsMerchantCatalogRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        String responseXml = getPord().wmsMerchantCatalogService(requestXml);
        logger.error("Call sf interface end------------------------------------->");
        logger.error(responseXml);
        WmsMerchantCatalogResponse response = null;
        try{
            response = (WmsMerchantCatalogResponse) MarshallerUtil.buildJaxb(WmsMerchantCatalogResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:商品目录接口(批量)==WMS:顺丰接口-商品目录接口(批量)
     * 
     * @param request
     * @return
     */
    public static WmsMerchantCatalogBatchResponse wmsMerchantCatalogBatch(WmsMerchantCatalogBatchRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        String responseXml = getPord().wmsMerchantCatalogBatchService(requestXml);
        logger.error(responseXml);
        WmsMerchantCatalogBatchResponse response = null;
        try{
            response = (WmsMerchantCatalogBatchResponse) MarshallerUtil.buildJaxb(WmsMerchantCatalogBatchResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:供应商接口==WMS:顺丰接口-供应商接口
     * 
     * @param request
     * @return
     */
    public static WmsVendorResponse wmsVendor(WmsVendorRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        logger.error("Call sf wmsVendorService begin------------------------------------->");
        String responseXml = getPord().wmsVendorService(requestXml);
        logger.error("Call sf wmsVendorService end------------------------------------->");
        logger.error(responseXml);
        WmsVendorResponse response = null;
        try{
            response = (WmsVendorResponse) MarshallerUtil.buildJaxb(WmsVendorResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }

    /**
     * SF:出库单（销售订单）状态查询接口==WMS:顺丰接口-出库订单（销售订单）状态查询接口
     * 
     * @param request
     * @return
     */
    public static WmsSailOrderStatusQueryResponse wmsSailOrderStatusQuery(WmsSailOrderStatusQueryRequest request) {
        String requestXml = MarshallerUtil.buildJaxb(request);
        logger.error(requestXml);
        String responseXml = getPord().wmsSailOrderStatusQueryService(requestXml);
        logger.error(responseXml);
        WmsSailOrderStatusQueryResponse response = null;
        try{
            response = (WmsSailOrderStatusQueryResponse) MarshallerUtil.buildJaxb(WmsSailOrderStatusQueryResponse.class, responseXml);
        }catch(Exception e){
            FailResponse fr = (FailResponse) MarshallerUtil.buildJaxb(FailResponse.class, responseXml);
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR,new Object[]{fr.getReasoncode(),fr.getRemark()});
        }
        return response;
    }
}
