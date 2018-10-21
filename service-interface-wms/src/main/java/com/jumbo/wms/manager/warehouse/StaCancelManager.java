package com.jumbo.wms.manager.warehouse;


import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;


/**
 * 新配货逻辑接口
 * 
 * @author jinlong.ke
 * 
 */
public interface StaCancelManager extends BaseManager {
    /**
     * OMS3.0取消方法
     * 
     * @param soCode 订单号
     * @return true 取消成功 false 等待取消 throw exception 取消失败
     */
    boolean cancelStaBySlipCode(String soCode);

    /**
     * 所有销售相关作业单取消，取消后修改单据状态
     * 
     * @param sta hibernate对象 * @return true 允许取消，false 不允许取消, throw BusinessException 取消异常不能取消
     */
    boolean cancelStaForSales(StockTransApplication sta);

    /**
     * 取消销售作业单 when cancel failed then throw exception
     * 
     * @param slipCode 相关单据号
     * @param type 单据类型 * @return true 允许取消，false 不允许取消, throw BusinessException 取消异常不能取消
     */
    boolean cancelSalesStaBySlipCode(String slipCode);

    /**
     * 取消销售作业单
     * 
     * @param slipCode
     * @param type
     * @return
     */
    boolean cancelSalesStaBySlipCode(String slipCode, StockTransApplicationType type);

    /**
     * 电子面单取消作业单
     * 
     * @param staId
     */
    void cancelStaForTransOlOrder(Long staId);

    public boolean checkCancelStaForSales(StockTransApplication sta);

    /**
     * 根据相关单据号 和 配货清单编号查询Sta
     * 
     * @param id pickingList ID
     * @param code slipCode
     * @param beanPropertyRowMapper
     * @return
     */
    public StockTransApplication findStaBySlipCodeAndPid(Long id, String code);

    /**
     * 物流不可达转EMS
     * 
     * @param staId
     */
    void transErrorToEms(Long staId, Long locId, Long ouId);

    void releaseInventory2(StockTransApplication sta);


    /**
     * 重置作业单状态为新建
     * 
     * @param staId
     */
    public String updateStaStatusToCreate(String staCode, Long ouId);

    public String updateStaRestore(String staCode, Long ouId);

}
