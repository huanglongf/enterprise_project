package com.jumbo.wms.manager.hub2wms;

import java.util.List;

import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.QueueSta;

public interface CreateStaTaskManager {
    void setFlagForStaData();


    void setCanCreateAndEndFlagForOrder();

    List<Long> getAllOrderInvCheckCreate();

    Boolean getAllNeedSetFlagOrder();

    /**
     * 查询本次执行需要的默认仓
     */

    List<Long> getNeedExecuteWarehouse();

    void setDefaultCanFlagIsFalse(Long id);

    List<Long> getAllOrderToCreate();

    /**
     * 根据默认发货仓，设置当前所有单子的可创标识
     * 
     * @param id
     */
    void setFlagByWarehouse(Long id);

    void createStaById(Long id);

    void setBeginFlagForOrder();

    void createTomsOrdersendToMq(Long id);

    public Integer getSystemThreadValueByKeyAndDes(String key, Boolean available);

    /**
     * 查询设置了开始标识，并且没有标识失败也没有设置默认仓的单据
     */
    List<WmsSalesOrderQueue> getAllOrderHaveFlag();

    void setDefaultWarehouseById(Long id);

    /**
     * 根据仓库查询需要设置标识的单据
     * 
     * @param id
     * @return
     */
    List<WmsSalesOrderQueue> getToSetFlagOrderByWarehouse(Long id);

    /**
     * 为当前单据设置是否可创单标识
     * 
     * @param id
     */

    void setFlagToOrder(Long id, Long ouId);

    /**
     * 根据optionKey 获取value
     * 
     * @param key
     * @return
     */
    Integer getSystemThreadValueByKey(String key);

    /**
     * 根据optionKey 获取value
     * 
     * @param key
     * @return
     */
    Integer getLFPiCiValueByKey(String key);

    /**
     * 根基code ，key 获取vaule
     */
    Integer getChooseOptionByCodeKey(String code,String key);

    
    /**
     * 直连每次初始化一批销售可用量
     */
    // void initInventoryForOnceUse();

    /**
     * 一次直连打标之后，删除之前初始化数据
     */
    void deleteInventoryForOnceUse();


    /**
     * 
     * @return
     */
    List<String> findInitInventoryOwnerList();


    /**
     * @param owner
     */
    void initInventoryForOnceUseByOwner(String owner);

    public Boolean getAllNeedSetFlagOrderPac();

    public void setBeginFlagForOrderPac(Long id);

    public List<Long> getAllOrderToCreatePac();

    public void initInventoryByOuId(String warehouseCode);

    public List<String> findInitInventoryWarehouseCodeList();

    public List<String> findInitInventoryWarehouseCodeListDelete();

    public List<QueueSta> getToSetBatchOrderByWarehouse(Long id);

    public void setFlagToOrderPac(Long id, Long ouId);

    public void deleteInventoryForOuId();

    public String createStaByIdPac(Long id);

    public List<Long> getNeedExecuteWarehousePac();

    public List<Long> getBeginFlagForOrderPac();

    void createOrdersendToMq(Long qstaId);

    /**
     * 发送MQ通知pac
     * 
     * @param qstaId
     */
    public void sendCreateOrderMQToPac(String qstaId);

    /**
     * 推送MQ失败，更新状态
     * 
     * @param mpMsg
     */
    public void updateCreateOrderMQToPacStatus(String mpMsg);

    /**
     * 非直连创单反馈补偿
     * 
     * @return
     */
    public List<Long> findOrderCreateOrderToPac();

    /**
     * 补偿 发送MQ通知pac
     * 
     * @param qstaId
     */
    public void sendCreateOrderMQToPacById(Long cotpId);

    /**
     * 通过beginFlag查询WmsSalesOrderQueue数据
     * 
     * @param beginFlag
     * @return
     */
    List<WmsSalesOrderQueue> getWmsSalesOrderQueueByBeginFlag(Integer beginFlag);

    List<AdvanceOrderAddInfo> findPreSalesOrder(String source);

    void setFlagToOrderNew(Long id);


    void createTomsOrdersendToMq2(Long id);


    List<Long> getAllOrderInvCheckCreateByPac();


    public String beflag5ToOme(Long id);

}
