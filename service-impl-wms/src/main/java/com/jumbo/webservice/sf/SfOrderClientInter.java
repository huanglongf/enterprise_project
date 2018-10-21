package com.jumbo.webservice.sf;

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
import com.jumbo.wms.manager.BaseManager;


public interface SfOrderClientInter extends BaseManager {

    /**
     * SF:入库单(采购订单)接口 ==WMS:顺丰接口-采购入库单接口 1
     * 
     * @param request
     * @return
     */
    WmsPurchaseOrderResponse wmsPurchaseOrder(WmsPurchaseOrderRequest request);

    /**
     * SF:出库单（销售订单）接口==WMS:顺丰接口-销售订单接口 2
     * 
     * @param request
     * @return
     */
    WmsSailOrderResponse wmsSailOrder(WmsSailOrderRequest request);

    /**
     * SF:取消订单(销售订单)接口==WMS:顺丰接口-取消订单接口 3
     * 
     * @param request
     * @return
     */
    WmsCancelSailOrderResponse wmsCanclSailOrder(WmsCancelSailOrderRequest request);


    /**
     * SF:商品目录接口==WMS:顺丰接口-商品目录接口 4
     * 
     * @param request
     * @return
     */
    WmsMerchantCatalogResponse wmsMerchantCatalog(WmsMerchantCatalogRequest request);

    /**
     * SF:商品目录接口(批量)==WMS:顺丰接口-商品目录接口(批量) 5
     * 
     * @param request
     * @return
     */
    WmsMerchantCatalogBatchResponse wmsMerchantCatalogBatch(WmsMerchantCatalogBatchRequest request);

    /**
     * SF:供应商接口==WMS:顺丰接口-供应商接口 6
     * 
     * @param request
     * @return
     */
    WmsVendorResponse wmsVendor(WmsVendorRequest request);

    /**
     * SF:出库单（销售订单）状态查询接口==WMS:顺丰接口-出库订单（销售订单）状态查询接口 7
     * 
     * @param request
     * @return
     */
    WmsSailOrderStatusQueryResponse wmsSailOrderStatusQuery(WmsSailOrderStatusQueryRequest request);

}
