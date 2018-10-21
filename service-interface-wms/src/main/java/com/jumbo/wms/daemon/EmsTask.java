package com.jumbo.wms.daemon;

import java.text.ParseException;
import java.util.List;

public interface EmsTask {

    /**
     * 获取EMS 单号
     */
    public void toEmsGetOrderCode();

    /**
     * 设置EMS快点单号
     */
    public void setEmsStaTrasnNo();

    /**
     * EMS 出库通知
     */
    // public void exeEmsConfirmOrderQueue();

    public Long getTransNoNumber();

    public List<Long> getAllEmsWarehouse();

    public List<Long> findStaByOuIdAndStatus(Long id, List<String> lpList);

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 根据authorization来刷新flashToken 或根据flashToken来刷authorization 最终获取authorization
    public void EmsAuthorizationOrflashToken() throws ParseException;

}
