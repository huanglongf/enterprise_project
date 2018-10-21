package com.jumbo.pms.manager.app;


/**
 * 
 * @author ShengGe
 * 
 */
public interface ParcelManagerTask {

    /**
     * 顾客已签收需要通知PAC更新订单状态,PAC订单必须允许SF刷完成
     */
    void parcelChangeStatusNotifyPAC();
    
    /**
     * 通知物流商上门取件
     */
    void parcelPickUpNotify();
}
