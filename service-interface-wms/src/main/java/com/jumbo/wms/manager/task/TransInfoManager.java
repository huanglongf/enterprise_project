package com.jumbo.wms.manager.task;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.trans.TransInfo;
import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;

/**
 * 
 * @author kai.zhu
 * 
 */
public interface TransInfoManager extends BaseManager {

    /**
     * 获取所有可用的物流信息
     * 
     * @return
     */
    List<TransInfo> getAvailableTransInfo();

    /**
     * 获取10000条没有运单号的工作单Id
     * 
     * @return
     */
    void setMailNos();

    List<Long> findExtOrderIdSeo();

    String getConfirmOrderJson(ExpressConfirmOrderQueue q);

    /**
     * 获取物流商的可用运单号数量
     * 
     * @param lpCode
     * @param regionCode
     */
    Long getTranNoNumberByLpCodeAndRegionCode(String lpCode, String regionCode);

    <T> String getObjectToJson(T t);

    <T> T getJsonToObject(String json, Class<T> t);

    void saveMailNos(WhTransProvideNo mailNo);

    ExpressConfirmOrderQueue getExpressConfirmOrder(Long id);

    void saveLogAndDeleteQueue(ExpressConfirmOrderQueue q);

    void addErrorCount(Long id);

    List<Long> findStaByTransInfoLpCode();

    Boolean orderConfrimToLogistics(ExpressConfirmOrderQueue queue);

    /**
     * 退仓发送物流商出库确认信息
     * 
     * @param staId
     */
    void vmiRtoOrderConfrimToLogistics(Long staId);
}
