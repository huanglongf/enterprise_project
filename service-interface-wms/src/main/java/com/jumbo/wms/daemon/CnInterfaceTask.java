package com.jumbo.wms.daemon;

import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.CnSkuInfo;
import com.jumbo.wms.model.warehouse.InventoryCheck;

public interface CnInterfaceTask {
    /**
     * 添加/修改 菜鸟商品
     */
    public void createOrUpdateCnSku();

    /**
     * 根据中间表创建VMI入库单据
     */
    public void generateInbountSta();

    /**
     * 菜鸟大宝创建商品
     * 
     * @param csi
     */
    public void createSkuByCnSku(CnSkuInfo csi);

    /**
     * 单据状态回传
     */
    public void uploadStatus();

    /**
     * 入库订单确认
     */
    public void inOrderMsgToCaiNiao();

    /**
     * 生成菜鸟VMI退仓
     */
    public void createCaiNiaoVmiRtn();

    /**
     * 生成菜鸟VMI退仓
     * 
     * @param oon
     */
    public void createVmiRtn(CnOutOrderNotify oon);

    /**
     * 菜鸟出库通知
     */
    public void outOrderMsgToCaiNiao();

    /**
     * VMI库存调整通知菜鸟（盘点数据生成）
     * 
     * @param ic
     */
    public void makeVMIInvAdjNoticeToCaiNiao(InventoryCheck ic);

    /**
     * 库存状态修改通知菜鸟（盘点数据生成）
     */
    public void createInvStatusUpdateNoticeToCaiNiao(Long staId);

    /**
     * VMI库存调整通知菜鸟（盘点数据发送）
     * 
     * @param ic
     */
    public void uploadVMIInvAdjustmentToCaiNiao();

}
