package com.jumbo.wms.manager.vmi.gucciData;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public interface GucciManager extends BaseManager {

    /**
     * 根据入库指令JDADocumentNo生成入库单(已修改为使用通用创单逻辑)
     * 
     * @param refNo
     */
    public void generateStaByJDADocumentNo(String JDADocumentNo);

    /**
     * 生成入库反馈数据
     * 
     * @param refNo
     */
    public void generateInBoundFeedbackDate(StockTransApplication sta);

    /**
     * 创建退大仓作业单
     * 
     * @param vmiRtoCommand
     */
    public void generateVmiRtn(VmiRtoCommand vmiRtoCommand);

    /**
     * 生成退大仓反馈数据
     * 
     * @param sta
     */
    public void generateVmiRtnFeedbackDate(StockTransApplication sta);

    /**
     * 生成退货入库反馈数据
     * 
     * @param sta
     * @param stv
     */
    public void generateSaleReturnInboundData(StockTransApplication sta, StockTransVoucher stv);

    /**
     * 退大仓送货单定制
     * 
     * @param staId
     * @return
     */
    public String[] outboundJasper(Long staid);

    /**
     * 退大仓装箱单定制
     * 
     * @param cartonid
     * @return
     */
    public String[] outBoundPackageInfoJasper(Long cartonid);

    /**
     * 超过6小时未收到商品主档信息发送预警邮件
     * 
     */
    public void sendEmailWhenInstructionHasNoSku();

}
