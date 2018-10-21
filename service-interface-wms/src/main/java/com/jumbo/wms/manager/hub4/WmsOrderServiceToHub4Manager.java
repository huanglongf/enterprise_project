package com.jumbo.wms.manager.hub4;

import java.util.List;

import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.wmsInterface.WmsInBound;
import com.jumbo.wms.model.wmsInterface.WmsOutBound;

/**
 * 
 * @author HUB4.0 标准对接【非销售类】jinggang.chen
 * 
 */
public interface WmsOrderServiceToHub4Manager extends BaseManager {

    /**
     * 创入库单
     * 
     * @param wmsInBound
     * @return
     */
    public WmsResponse createOrderInbound(List<WmsInBound> wmsInBound);

    /**
     * 创出库单
     * 
     * @param wmsInBound
     * @return
     */
    public WmsResponse createOrderOutbound(List<WmsOutBound> wmsOutBound);

    /**
     * 创建反馈信息给HUB4
     * 
     * @param staId
     */
    public void createResponseInfo(Long staId, Integer stvId);

    /**
     * 更新IntfcCfm状态
     * 
     * @param icList
     * @param status
     */
    public void modifyIntfcCfmStatusById(Long icId, Integer status, String errorMsg);

    /**
     * 前海出入库单报关状态更改
     */
    WmsResponse qianHaiOrderStatusNotify(String orderCode, Integer status);

    /**
     * 前海商品报关状态修改
     */
    WmsResponse qianHaiSkuInfoStatusNotify(String skuUpc, Integer status, Integer ciqStatus);

}
