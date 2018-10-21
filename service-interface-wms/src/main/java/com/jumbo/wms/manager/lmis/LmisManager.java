package com.jumbo.wms.manager.lmis;

import java.io.File;

/**
 * 提供给LMIS系统的接口服务
 */
public interface LmisManager {

    /**
     * 1、操作费接口服务
     * 
     * @param requestParam
     * @return
     */
    public String getOperatingCost(String requestParam);

    /**
     * 2、耗材费-采购明细接口服务
     * 
     * @param requestParam
     * @return
     */
    public String getMaterialFeePurchaseDetails(String requestParam);

    /**
     * 3、耗材费-实际使用量接口服务
     * 
     * @param requestParam
     * @return
     */
    public String getMaterialFeeActualUsage(String requestParam);

    /**
     * 4、快递运单接口服务
     * 
     * @param requestParam
     * @return
     */
    public String getExpressWaybill(String requestParam);

    /**
     * 5、仓储费接口服务
     * 
     * @param requestParam
     * @return
     */
    public String getWarehouseCharges(String requestParam);
    
    /**
     * 6、退货入库接口服务
     * 
     * @param requestParam
     * @return
     */
    public String getReturnStorageData(String requestParam);
    
    /**
     * 7、出库单信息同步接口服务
     */
    String sendOrderOutBoundData(File file,Long ouId);

    /**
     * 8、发送纸箱数据给LMIS
     */
    void sendStaCartonInfo();

}
