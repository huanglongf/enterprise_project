package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

public interface ThreePLTask extends BaseManager {

    /**
     * 外包仓入库反馈执行
     */
    void threePLInboundExecute();

    /**
     * 外包仓出库反馈执行
     */
    void threePLOutboundExecute();

    /**
     * 外包仓流程失败邮件通知
     */
    void threePLEmailNotice();

    /**
     * 物流宝商品推送
     */
    void sendSkuToHab();


    /**
     * 物流宝入库推送
     */
    void sendInboundHab();

    /**
     * 物流宝出库推送
     */
    void sendOutboundHab();

    /**
     * 物流宝出库查询
     */
    void wlbOutboundQuery();

    /**
     * 物流宝入库差异查询
     */
    // void wlbInboundNotifyPageGet();

    /**
     * 物流宝单据取消
     */
    void wlbOutboundCancel();

    /**
     * 物流宝单据取消结果查询
     */
    void wlbOutboundCancelQuery();

    /**
     * 物流宝新入库查询
     */
    void wlbNewInboundQuery();

    /**
     * WX发货接口
     */
    void wxOutBoundQueryMq();

    /**
     * 外包仓库存状态调整
     */
    void threePlInvStatusChange();

    /**
     * 外包仓库存状态修改执行
     */
    void threePlInvStatusEexcute();

    /**
     * 外包仓库存数量调整
     */
    void threePlInvQtyChange();

    /**
     * 外包仓库存数量调整执行
     */
    void threePlInvQtyExecute();
    
    /**
     * 外包仓SN管理
     */
    void threePlSNManager();
}
