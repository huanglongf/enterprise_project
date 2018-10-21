package com.jumbo.wms.manager.lmis;

import com.jumbo.wms.model.lmis.ExpressWaybill;

/**
 * lmis接口线程任务接口
 * 
 */
public interface LmisThreadTaskManager {
    /**
     * 4.1 快递运单接口获取明细数据
     * 
     * @param ExpressWaybill 运单信息
     * @return
     */
    public void getExpressWaybillDetailData(ExpressWaybill p);
}
