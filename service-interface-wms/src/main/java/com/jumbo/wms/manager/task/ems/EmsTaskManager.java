package com.jumbo.wms.manager.task.ems;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.EMSConfirmOrderQueue;

public interface EmsTaskManager extends BaseManager {

    /**
     * EMS执行单据更新
     * 
     * @param q
     */
    void exeEmsConfirmOrder(Long qId);

    /**
     * EMS执行单据更新New
     * 
     * @param q
     */
    void exeEmsConfirmOrderNew(Long qId);

    /**
     * EMS获取单号
     * 
     * @param isCod
     */
    void saveEmsNo(String transNo, boolean isCod, String account);


    /**
     * EMS获取待执行的数量
     * 
     * @param count
     * @return
     */
    List<EMSConfirmOrderQueue> findExtOrder();

    /**
     * EMS获取待执行的数量 优化
     * 
     * @return
     */
    List<Long> findExtOrderIdSeo();

    /**
     * CNP 出库查询
     * 
     * @return
     */
    List<Long> findCnpOrderIdSeo();

    /**
     * 标示ems新旧接口切换
     */
    Boolean getChooseEmsHttps();

    /**
     * CNP 出库执行
     * 
     * @param qId
     */
    void exeCnpConfirmOrder(Long qId);

}
