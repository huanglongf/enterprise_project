package com.jumbo.wms.manager.warehouse;


import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;

/**
 * 退仓装箱业务逻辑
 * 
 * @author jinlong.ke
 * 
 */
public interface WarehouseReturnManager extends BaseManager {
    /**
     * 装箱扫描时校验商品是否存在，是否为计划商品，是否超出计划量
     * 
     * @param staCode 单据号
     * @param barCode 扫描的条码号
     * @param qty 数量
     */
    void validateSkuIsPlanOrNot(String staCode, String barCode, Long qty,Long whouid);
    /**
     * 查询明细是否只有单个库存状态
     * @param staCode
     * @return
     */
    StaLineCommand staLineStatus(String staCode);
    /**
     * 查询在库残次商品
     * @param defectCode
     * @param barCode
     * @return
     */
    SkuImperfectCommand validateSkuImperfect(String defectCode,String barCode);
    
   

}
