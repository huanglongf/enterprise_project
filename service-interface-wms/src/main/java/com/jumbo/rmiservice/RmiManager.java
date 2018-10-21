package com.jumbo.rmiservice;

import java.util.List;

import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmi.warehouse.OrderResult;
import com.jumbo.rmi.warehouse.WarehousePriority;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiAsn;
import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.wms.manager.BaseManager;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface RmiManager extends BaseManager {

    String createStaByOrder(Order order);

    /**
     * 验证单据信息
     * 
     * @param order
     */
    void validateOrderInfo(Order order);

    /**
     * 更新盘点批为可执行状态
     * 
     * @param code
     */
    void confirmUpdateStatus(String code);

    /**
     * 取消作业单
     * 
     * @param slipCode
     * @return
     */
    boolean cancelOrder(String slipCode);

    /**
     * 取消费用化单据
     * 
     * @param slipCode
     * @return
     */
    boolean cancelBatchCode(String slipCode);

    /**
     * 负向采购取消作业单
     * 
     * @param slipCode
     * @return
     */
    boolean procurementOrder(String slipCode);

    /**
     * 盘点审核不通过
     */
    boolean rejectInvCheck(String slipCode);

    /**
     * 获取订单商品批次
     * 
     * @param order
     * @return
     */
    OrderResult orderSkuBatchCode(Order order);

    WmsResponse vmiAsn(VmiAsn VmiAsn, WmsResponse wr);

    /**
     * VMI退仓指令
     * 
     * @param VmiRto
     * @param wr
     * @return WmsResponse
     * @throws
     */
    WmsResponse vmiRto(VmiRto vmiRto, WmsResponse wr);

    /**
     * 更具 省市 获取配送范围
     * 
     * @param province
     * @param city
     * @param district
     * @param owner
     * @return
     */
    List<WarehousePriority> getPriorityWHByCity(String province, String city, Long cusId, Long cId);

    /**
     * 采购订单取消
     * 
     * @param code
     * @param ouId 仓库ID
     * @return
     */
    Boolean cancelProcurement(String code);

    /**
     * 采购订单关闭
     * 
     * @param code
     * @param ouId 仓库ID
     * @return
     */
    Boolean closeProcurement(String code);

    /**
      * 根据渠道ID查询绑定发货仓库优先级
     * 
     * @param province
     * @param city
     * @param cusId
     * @param channleId
     * @return
     */
    List<WarehousePriority> getPriorityWHByCityChannleId(String province, String city, Long cusId, Long channleId);
    
    /**
     * 增加或删除店铺和品牌关联关系
     * @param shopCode
     * @param brandCode
     * @param type
     */
    void updateStoreBrand(String shopCode, List<String> brandCodeList);
    
}
