package com.jumbo.wms.manager.hub2wms;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderConfirm;
import com.jumbo.wms.model.hub2wms.threepl.WmsSkuInfo;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockInOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.WmsStockOutOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.WmsThreeplRespose;

public interface WmsThreePLManager extends BaseManager {
    /**************** DB-WMS **********************/
    /**
     * 3.3.2 商品信息通知
     * 
     * @param skuInfo
     */
    public WmsThreeplRespose wmsSkuInfoNotify(String systemKey, WmsSkuInfo skuInfo);

    /****************** WMS-DB *********************/
    /**
     * 3.3.3 WMS查询订单商品信息
     * 
     * @param source 来源 :DB
     * @param providerTpId 货主ID
     * @param itemList skuItemList
     */
    public void wmsSkuInfoQuery(String source, Long providerTpId, List<Long> itemList, String systemKey);

    /**
     * 3.3.6 入库通知单接口
     * 
     * @param wmsStockInOrderNotify
     */
    public WmsThreeplRespose wmsStockInOrderNotify(String systemKey, WmsStockInOrderNotify wmsStockInOrderNotify);

    /**
     * 3.3.12 入库订单确认
     * 
     * @param orderCode
     * @return
     */
    public Boolean stockInOrderConfirmResponse(CnWmsStockInOrderConfirm ci);

    /**
     * 3.3.7 出库单通知接口
     * 
     * @return
     */
    public WmsThreeplRespose wmsStockOutOrderNotify(String systemKey, WmsStockOutOrderNotify wmsStockOutOrderNotify);

    /**
     * 3.3.5 销售订单发货通知接口
     * 
     * @return
     */
    public String wmsConsignOrderConfirm(Long staId);

    /**
     * 3.3.14 出库单确认
     * 
     * @param source
     * @param orderCode
     * @return
     */
    public String stockOutOrderConfirm(Long staId);

    /**
     * 3.3.9 单据状态回传接口
     * 
     * @param source
     * @param orderCode
     */
    public void wmsOrderStatusUpload(String orderCode);


    /**
     * 创建菜鸟回传信息by staId
     * 
     * @param staId
     */
    public void createCnWmsOrderStatusUpload(Long staId);

    /**
     * 创建菜鸟缺货回传
     * 
     * @param orderCode
     */
    public void createCnInvQtyDeficiency(String orderCode);

    /**
     * 创建菜鸟回传信息by stvId
     * 
     * @param stvId
     */
    public void createCnWmsOrderStatusUploadByStv(Long stvId);

    /**
     * 3.3.10 盘点
     * 
     * @param storeCode
     * @return
     */
    public void wmsInventoryCount(String storeCode);
}
