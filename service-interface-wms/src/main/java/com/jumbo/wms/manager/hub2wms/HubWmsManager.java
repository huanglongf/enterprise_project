package com.jumbo.wms.manager.hub2wms;

import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.compensation.ClaimHead;
import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.WmsClaimResult;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderCancel;
import com.jumbo.wms.model.hub2wms.WmsOrderCancelResult;
import com.jumbo.wms.model.hub2wms.WmsOrderStatus;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand;

public interface HubWmsManager extends BaseManager {

    void wmsSalesOrder(String systemKey, WmsSalesOrder order) throws Exception;

    public void validateOrderInfo(String system, WmsRtnOrder order);

    void updateWmsOutBound(List<Long> id);

    Pagination<MsgRtnOutboundCommand> findStaByStaCode(int start, int pagesize, String codeList, Sort[] sorts);

    void createStaByOrder(String systemKey, WmsRtnInOrder order);

    /**
     * 订单取消确认
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsOrderCancelResult> wmsCancelOrderConfirm(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * 订单执行反馈
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsOrderStatus> wmsOrderFinish(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    boolean cancelSalesStaByshippingCode1(String shipping);


    public WmsOrderCancel cancelSalesStaByLineNo(List<WmsSalesOrderLine> lines, String orderCode);

    public WmsOrderCancelResult cancelSalesSta(String systemKey, String orderCode, boolean platCancel);

    /**
     * 索赔数据同步接口
     * 
     * @param contactCode
     * @param systemKey
     * @param chList
     * @return
     */
    public List<WmsClaimResult> tomsClaimSync(String contactCode, String systemKey, List<ClaimHead> chList);

    /**
     * 理赔处理反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimProcess> receiveWmsClaimProcess(int start, int pagesize, String systemKey, Date startTime, Date endTime);


    /**
     * 索赔结果反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimResult> receiveWmsClaimResult(int start, int pagesize, String systemKey, Date startTime, Date endTime);

    /**
     * 查询直连出库数据接口
     * 
     * @param sorts
     * @param shoppingCode
     * @param endTime
     * @param startTime
     * @param pagesize
     * @param start
     * @return
     */
    Pagination<WmsOrderStatusOms> queryWmsOrder(int start, int pagesize, Date startTime, Date endTime, String shoppingCode, Sort[] sorts);

    void saveAndUpdateOrderStatus(List<Long> ids, String loginName);

    public Pagination<WmsOrderStatus> wmsOrderFinishStatus(int start, int pagesize, String systemKey, Date starteTime, Date endTime, Integer type);

    public Pagination<WmsOrderStatus> wmsOrderCancel(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    public Pagination<WmsOrderStatus> wmsOrderFinishByType(int start, int pagesize, String systemKey, Date starteTime, Date endTime, List<Integer> type);

    /**
     * 查询直连创单数据
     * 
     * @param sorts
     * @param shoppingCode
     * @param endTime
     * @param startTime
     * @param pagesize
     * @param start
     * @return
     */
    Pagination<WmsConfirmOrderQueue> queryWmsConfirmOrderQueue(int start, int pageSize, Date date, Date date2, String shoppingCode, Sort[] sorts);

    void insertAgvSku(Long createOrUpdate,Long skuId);
    
    // 直连创单推送
    void updateWmsConfirmOrderQueue(List<Long> ids, String loginName);
    
    // AGV推送hub中间表
    void insertAgvInBoundToHub(Long stvId);
    
    void insertAgvTransitInnerToHub(Long stvId);

}
