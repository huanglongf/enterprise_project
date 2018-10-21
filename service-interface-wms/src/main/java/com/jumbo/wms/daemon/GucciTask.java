package com.jumbo.wms.daemon;

import com.jumbo.wms.model.vmi.GucciData.DataResponse;
import com.jumbo.wms.model.vmi.GucciData.GucciRespose;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInBound;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIInFB;
import com.jumbo.wms.model.vmi.GucciData.GucciVMIRtnFeedback;

public interface GucciTask {
    /**
     * 保存gucci入库指令（不使用）
     */
    public GucciRespose saveGucciVMIInstructions(String systemKey, GucciVMIInBound gucciVMIInBound);

    /**
     * 生成VMI入库作业单（不使用）
     */
    public void generateStaByInstruction();

    /**
     * 提供给hub的gucci入库反馈信息/退货入库反馈信息
     * 
     * @param systemKey
     * @param requrest
     * 
     */
    public DataResponse<GucciVMIInFB> feedbackInboundInfo(String systemKey, String request);

    /**
     * 生成VMI退仓
     */
    public void generateVmiRtn();

    /**
     * 提供给hub的gucci退大仓反馈信息
     * 
     * @param systemKey
     * @param requrest
     * 
     */
    public DataResponse<GucciVMIRtnFeedback> feedbackRtnInfo(String systemKey, String request);

    /**
     * 每日库存数据生成（含占用量和各种库存状态）
     */
    public void salesInventoryGucci();

    /**
     * 超过6小时未收到商品主档信息发送预警邮件
     */
    public void sendEmailWhenInstructionHasNoSku();
}
