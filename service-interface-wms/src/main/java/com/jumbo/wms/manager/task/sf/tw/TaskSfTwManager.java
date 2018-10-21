package com.jumbo.wms.manager.task.sf.tw;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;

/**
 * 
 * @author lichuan
 * 
 */
public interface TaskSfTwManager extends BaseManager {
    /**
     * 同步商品到台湾顺丰
     * 
     * @param skus
     * @param sfFlag
     * @throws Exception void
     * @throws
     */
    void sendSkuItemsService(List<MsgSKUSync> skus, String sfFlag) throws Exception;

    /**
     * 入库单推送到台湾顺丰
     * 
     * @param inOrder
     * @throws Exception void
     * @throws
     */
    void sendInboundOrderService(MsgInboundOrder inOrder) throws Exception;

    /**
     * 接收台湾顺丰入库反馈
     * 
     * @param logisticsInterface
     * @param dataDigest
     * @return String
     * @throws
     */
    String receiveInboundOrderPushService(String logisticsInterface, String dataDigest);

    /**
     * 下发供应商到台湾顺丰
     * 
     * @return String
     * @throws Exception
     */
    String sendVendorService();

    /**
     * 出库单通知到台湾顺丰
     * 
     * @param outOrder
     * @throws Exception void
     * @throws
     */
    void sendOutboundOrderService(MsgOutboundOrder outOrder) throws Exception;

    /**
     * 接收台湾顺丰出库反馈
     * 
     * @param logisticsInterface
     * @param dataDigest
     * @return String
     * @throws
     */
    String receiveOutboundOrderPushService(String logisticsInterface, String dataDigest);

    /**
     * 接收台湾顺丰库存变化
     * 
     * @param logisticsInterface
     * @param dataDigest
     * @return String
     * @throws
     */
    String receiveInventoryChangePushService(String logisticsInterface, String dataDigest);
}
