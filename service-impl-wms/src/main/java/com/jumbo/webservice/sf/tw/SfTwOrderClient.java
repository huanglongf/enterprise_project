/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.webservice.sf.tw;

import java.net.URL;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.sf.tw.error.command.ErrorInfo;
import com.jumbo.webservice.sf.tw.error.command.ErrorResponse;
import com.jumbo.webservice.sf.tw.inbound.command.InboundRequest;
import com.jumbo.webservice.sf.tw.inbound.command.InboundResponse;
import com.jumbo.webservice.sf.tw.order.IOutsideToLscmService;
import com.jumbo.webservice.sf.tw.order.OutsideToLscmServiceService;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelRequest;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundRequest;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundResponse;
import com.jumbo.webservice.sf.tw.sku.command.SkuRequest;
import com.jumbo.webservice.sf.tw.sku.command.SkuResponse;
import com.jumbo.webservice.sf.tw.vendor.command.VendorsRequest;
import com.jumbo.webservice.sf.tw.vendor.command.VendorsResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;

/**
 * @author lichuan
 * 
 */
public class SfTwOrderClient {
    protected static final Logger log = LoggerFactory.getLogger(SfTwOrderClient.class);
    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.warehouse.integration.sf.com/", "OutsideToLscmServiceService");
    public final static QName OutsideToLscmServicePort = new QName("http://service.warehouse.integration.sf.com/", "OutsideToLscmServicePort");
    static {
        URL url = null;
        try {
            // url = new
            // URL("http://bsp.sit.sf-express.com:8080/bsp-wms/ws/OutsideToLscmServiceImpl?wsdl");
            url = OutsideToLscmServiceService.class.getResource("/wsdl/OutsideToLscmServiceImpl_tw.wsdl");
        } catch (Exception e) {
            log.error("Can not initialize the default wsdl from {0}", "/wsdl/OutsideToLscmServiceImpl_tw.wsdl");
        }
        WSDL_LOCATION = url;
    }

    private static IOutsideToLscmService getServicePort() {
        OutsideToLscmServiceService wss = new OutsideToLscmServiceService(WSDL_LOCATION, SERVICE);
        log.error("SfWarehouse wsdl init WarehouseServiceService");
        return wss.getOutsideToLscmServicePort();
    }

    /**
     * 商品同步到台湾顺丰
     * 
     * @param request
     * @return SkuResponse
     * @throws
     */
    public static SkuResponse sendSkuItemsService(SkuRequest request) {
        SkuResponse response = null;
        String requestXml = "";
        String responseXml = "";
        try {
            requestXml = MarshallerUtil.buildJaxb(request);
            log.debug("SFTW====> sendSkuItemsService ws start, sku request is:[{}]", requestXml);
            responseXml = getServicePort().outsideToLscmService(requestXml);
            log.debug("SFTW====> sendSkuItemsService ws end, sku response is:[{}]", responseXml);
            response = (SkuResponse) MarshallerUtil.buildJaxb(SkuResponse.class, responseXml);
        } catch (Exception e) {
            log.debug("SFTW====> sendSkuItemsService exception, requestXml is:[" + requestXml + "], responseXml is:[" + response + "]", e);
            String errorCode = "";
            String reasons = "";
            ErrorResponse er = (ErrorResponse) MarshallerUtil.buildJaxb(ErrorResponse.class, responseXml);
            if (null != er) {
                ErrorInfo error = er.getError();
                if (null != error) {
                    errorCode = error.getCode();
                    reasons = error.getValue();
                }
                log.debug("SFTW====> sendSkuItemsService error, errorCode is:[{0}], errorReason is:[{1}], request datagram is : {2}", new Object[] {errorCode, reasons, requestXml});
            }
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {errorCode, reasons});
        }
        return response;
    }

    /**
     * 下发入库单到台湾顺丰
     * 
     * @param request
     * @return WmsPurchaseOrderResponse
     * @throws
     */
    public static InboundResponse sendPurchaseOrderService(InboundRequest request) {
        InboundResponse response = null;
        String requestXml = "";
        String responseXml = "";
        try {
            requestXml = MarshallerUtil.buildJaxb(request);
            log.debug("SFTW====> sendPurchaseOrderService ws invoke start, request is:[{}]", requestXml);
            responseXml = getServicePort().outsideToLscmService(requestXml);
            log.debug("SFTW====> sendPurchaseOrderService ws invoke end, request is:[{}], response is:[{}]", requestXml, responseXml);
            response = (InboundResponse) MarshallerUtil.buildJaxb(InboundResponse.class, responseXml);
        } catch (Exception e) {
            log.debug("SFTW====> sendPurchaseOrderService exception, requestXml is:[" + requestXml + "]", e);
            String errorCode = "";
            String reasons = "";
            ErrorResponse er = (ErrorResponse) MarshallerUtil.buildJaxb(ErrorResponse.class, responseXml);
            if (null != er) {
                ErrorInfo error = er.getError();
                if (null != error) {
                    errorCode = error.getCode();
                    reasons = error.getValue();
                }
                log.debug("SFTW====> sendPurchaseOrderService error, errorCode is:[{0}], errorReason is:[{1}], request datagram is : {2}", new Object[] {errorCode, reasons, requestXml});
            }
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {errorCode, reasons});
        }
        return response;
    }

    /**
     * 供应商推给台湾顺丰
     * 
     * @param request
     * @return VendorsResponse
     * @throws
     */
    public static VendorsResponse sendVendorService(VendorsRequest request) {
        VendorsResponse response = null;
        String requestXml = "";
        String responseXml = "";
        try {
            requestXml = MarshallerUtil.buildJaxb(request);
            log.debug("SFTW====> sendVendorService ws invoke start, request is：[{}]", requestXml);
            responseXml = getServicePort().outsideToLscmService(requestXml);
            log.debug("SFTW====> sendVendorService ws invoke end, response is：[{}]", responseXml);
            response = (VendorsResponse) MarshallerUtil.buildJaxb(VendorsResponse.class, responseXml);
        } catch (Exception e) {
            log.debug("SFTW====> sendVendorService exception, requestXml is:[" + requestXml + "]", e);
            String errorCode = "";
            String reasons = "";
            ErrorResponse er = (ErrorResponse) MarshallerUtil.buildJaxb(ErrorResponse.class, responseXml);
            if (null != er) {
                ErrorInfo error = er.getError();
                if (null != error) {
                    errorCode = error.getCode();
                    reasons = error.getValue();
                }
                log.debug("SFTW====> sendVendorService error, errorCode is:[{0}], errorReason is:[{1}], request datagram is : {2}", new Object[] {errorCode, reasons, requestXml});
            }
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {errorCode, reasons});
        }
        return response;
    }

    /**
     * 下发出库单到台湾顺丰
     * 
     * @param request
     * @return OutboundResponse
     * @throws
     */
    public static OutboundResponse sendSaleOrderService(OutboundRequest request) {
        OutboundResponse response = null;
        String requestXml = "";
        String responseXml = "";
        try {
            requestXml = MarshallerUtil.buildJaxb(request);
            if(log.isDebugEnabled()){
                log.debug("SFTW====> sendSaleOrderService ws invoke start, request is:[{}]", requestXml);
            }
            responseXml = getServicePort().outsideToLscmService(requestXml);
            if(log.isDebugEnabled()){
                log.debug("SFTW====> sendSaleOrderService ws invoke end, request is:[{}], response is:[{}]", requestXml, responseXml);
            }
            response = (OutboundResponse) MarshallerUtil.buildJaxb(OutboundResponse.class, responseXml);
        } catch (Exception e) {
            if(log.isDebugEnabled()){
                log.debug("SFTW====> sendSaleOrderService exception, requestXml is:[" + requestXml + "]", e);
            }
            String errorCode = "";
            String reasons = "";
            ErrorResponse er = (ErrorResponse) MarshallerUtil.buildJaxb(ErrorResponse.class, responseXml);
            if (null != er) {
                ErrorInfo error = er.getError();
                if (null != error) {
                    errorCode = error.getCode();
                    reasons = error.getValue();
                }
                if(log.isDebugEnabled()){
                    log.error("SFTW====> sendSaleOrderService error, errorCode is:[{0}], errorReason is:[{1}], request datagram is : {2}", new Object[] {errorCode, reasons, requestXml});
                }
            }
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {errorCode, reasons});
        }
        return response;
    }

    /**
     * 取消出库单
     * 
     * @param request
     * @return OutboundCancelResponse
     * @throws
     */
    public static OutboundCancelResponse cancelSaleOrderService(OutboundCancelRequest request) {
        OutboundCancelResponse response = null;
        String requestXml = "";
        String responseXml = "";
        try {
            requestXml = MarshallerUtil.buildJaxb(request);
            log.debug("SFTW====> cancelSaleOrderService ws invoke start, request is:[{}]", requestXml);
            responseXml = getServicePort().outsideToLscmService(requestXml);
            log.debug("SFTW====> cancelSaleOrderService ws invoke end, request is:[{}], response is:[{}]", requestXml, responseXml);
            response = (OutboundCancelResponse) MarshallerUtil.buildJaxb(OutboundCancelResponse.class, responseXml);
        } catch (Exception e) {
            log.debug("SFTW====> cancelSaleOrderService exception, requestXml is:[" + requestXml + "]", e);
            String errorCode = "";
            String reasons = "";
            ErrorResponse er = (ErrorResponse) MarshallerUtil.buildJaxb(ErrorResponse.class, responseXml);
            if (null != er) {
                ErrorInfo error = er.getError();
                if (null != error) {
                    errorCode = error.getCode();
                    reasons = error.getValue();
                }
                log.debug("SFTW====> cancelSaleOrderService error, errorCode is:[{0}], errorReason is:[{1}], request datagram is : {2}", new Object[] {errorCode, reasons, requestXml});
            }
            throw new BusinessException(ErrorCode.SF_WAREHOUSE_INTERFACE_ERROR, new Object[] {errorCode, reasons});
        }
        return response;
    }
}
