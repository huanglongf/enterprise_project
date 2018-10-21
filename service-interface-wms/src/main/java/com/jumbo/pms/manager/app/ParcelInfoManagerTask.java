package com.jumbo.pms.manager.app;

import java.util.List;

import com.jumbo.pms.model.command.cond.PgPackageCreateCommand;


/**
 * 
 * @author ShengGe
 * 
 */
public interface ParcelInfoManagerTask {

    /**
     * 顾客已签收需要通知PAC更新订单状态,PAC订单必须允许SF刷完成
     */
    void parcelInfoChangeStatusNotifyPAC();
    
    /**
     * 通知物流商上门取件
     */
    void parcelPickUpNotify();
    
    /**
     * 推送包裹出库信息至SD
     */
    List<PgPackageCreateCommand> parcelInfoQuery();
    
}
