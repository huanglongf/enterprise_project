package com.jumbo.wms.manager.hub2wms;

import java.util.Date;
import java.util.List;

import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.MSRCustomCardRtnInfo;
import com.jumbo.wms.model.compensation.ClaimHead;
import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimProcessAffirm;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.WmsClaimResult;
import com.jumbo.wms.model.hub2wms.EspritAsn;
import com.jumbo.wms.model.hub2wms.OmsTmallOrder;
import com.jumbo.wms.model.hub2wms.OrderAddInfo;
import com.jumbo.wms.model.hub2wms.ReturnResult;
import com.jumbo.wms.model.hub2wms.RtnOrderByAD;
import com.jumbo.wms.model.hub2wms.WmsAdidasSalesInventory;
import com.jumbo.wms.model.hub2wms.WmsAdidasTotalInventory;
import com.jumbo.wms.model.hub2wms.WmsAdvanceOrderResult;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrder;
import com.jumbo.wms.model.hub2wms.WmsOrderCancelResult;
import com.jumbo.wms.model.hub2wms.WmsOrderStatus;
import com.jumbo.wms.model.hub2wms.WmsReebokSalesInventory;
import com.jumbo.wms.model.hub2wms.WmsReebokTotalInventory;
import com.jumbo.wms.model.hub2wms.WmsRtnOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderResult;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderResult;
import com.jumbo.wms.model.hub2wms.WmsSku;
import com.jumbo.wms.model.invflow.WmsCustomer;
import com.jumbo.wms.model.invflow.WmsOccupiedAndRelease;
import com.jumbo.wms.model.invflow.WmsSkuInventoryFlow;
import com.jumbo.wms.model.vmi.GucciData.GucciSalesInvHub;
import com.jumbo.wms.model.vmi.adidasData.InventoryBatch;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.O2oEsprit.EspDeliveryResponse;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;

import loxia.dao.Pagination;

public interface HubWmsService extends BaseManager {

    /**
     * 换货出订单取消
     * 
     * @param systemKey
     * @param orderCode
     * @return
     */
    WmsOrderCancelResult wmsCancelOrderExchange(String systemKey, String orderCode);


    /**
     * 订单取消
     * 
     * @param systemKey
     * @param shippingCode
     * @param orderCode
     * @return
     */
    WmsOrderCancelResult wmsCancelOrder(String systemKey, String shippingCode, String orderCode);


    /**
     * 非销售类订单取消
     * 
     * @param orderCode
     * @return
     */
    WmsOrderCancelResult wmsCancelOrderNotSales(String orderCode, String type);

    /**
     * AGV出库反馈
     */
    String agvRtnOutBound(AgvOutBoundDto agvOutBoundDto);


    /**
     * AGV出库反馈List
     */
    ReturnResult agvRtnOutBoundList(List<AgvOutBoundDto> ls);


    /**
     * 订单取消接口
     * 
     * @param systemKey
     * @param json
     * @return
     */
    String wmsCancelOrder(String systemKey, String json);

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
     * 退换货接口
     * 
     * @param systemKey
     * @param rntOrder
     * @return
     */
    WmsRtnOrderResult wmsRtnOrderService(String systemKey, WmsRtnOrder rntOrder);

    /**
     * 退换货接口
     * 
     * @param systemKey
     * @param rntOrder
     * @return
     */
    List<WmsRtnOrderResult> wmsRtnOrderService(String systemKey, List<WmsRtnOrder> rntOrder);

    
    
    List<WmsRtnOrderResult> rtnOrderModifyMessage(String systemKey,List<RtnOrderByAD> rtnOrderByADList);
    
    /**
     * 订单执行反馈
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param endTime
     * @return
     */
    Pagination<WmsOrderStatus> wmsOrderFinish(int start, int pagesize, String systemKey, Date starteTime, Date endTime);


    /**
     * 出库实际数据反馈接口 adidas
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @param type
     * @return
     */
    Pagination<WmsOrderStatus> wmsOrderFinish(int start, int pagesize, String systemKey, Date starteTime, Date endTime, List<Integer> type);


    /**
     * 退货入库执行反馈 adidas
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @param type
     * @return
     */
    Pagination<WmsOrderStatus> wmsOrderFinishStatus(int start, int pagesize, String systemKey, Date starteTime, Date endTime, int type);

    /**
     * 退货入库取消反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsOrderStatus> clientReturnCancel(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * 销售订单接口，单个单据
     * 
     * @param systemKey
     * @param order
     * @return
     */
    WmsSalesOrderResult wmsSalesOrderService(String systemKey, WmsSalesOrder order);

    /**
     * 销售订单接口，多个单据
     * 
     * @param systemKey
     * @param orders
     * @return
     */
    List<WmsSalesOrderResult> wmsSalesOrderService(String systemKey, List<WmsSalesOrder> orders);

    /**
     * 订单仓库确认接口 过仓 (ruchu)
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsConfirmOrder> wmsConfirmOrderService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * 订单仓库确认接口ADIDAS
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @param type
     * @return
     */
    Pagination<WmsConfirmOrder> wmsConfirmOrderService(int start, int pagesize, String systemKey, Date starteTime, Date endTime, Integer type);


    /**
     * 退货单创建确认反馈接口 过仓 adidas
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsConfirmOrder> wmsConfirmRtnOrderService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);


    public WmsOrderCancelResult cancelSalesStaByshippingCode(String shipping);

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
     * 索赔处理确认接口
     * 
     * @param contactCode
     * @param systemKey
     * @param cpList
     * @return
     */
    public List<WmsClaimResult> tomsClaimProcessAffirm(String contactCode, String systemKey, List<ClaimProcessAffirm> cpList);

    /**
     * 小家电3C商家强制入菜鸟仓接口s
     * 
     * @param OmsTmallOrder
     * @return BaseResult
     */
    public BaseResult saveOMSTmalloutboundOrder(OmsTmallOrder order);

    /**
     * AD 退货入查询销售单买家信息
     * 
     * @param String slipCode1
     * @return WmsSalesOrderResult
     */
    WmsSalesOrderResult findTelephoneAndNameBySlipCode1(String slipCode1);

    /**
     * adidas商品接口 hub 通用
     * 
     * @param systemKey
     * @param Sku
     * @return BaseResult
     */
    public BaseResult updateSku(String systemKey, WmsSku sku);


    /**
     * 出库取消
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @param type
     * @return
     */
    Pagination<WmsOrderStatus> wmsOrderCancel(int start, int pagesize, String systemKey, Date starteTime, Date endTime, List<Integer> type);

    /**
     * adidas全量库存查询接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsAdidasTotalInventory> wmsTotalInventoryAdidasService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);


    /**
     * adidas全量可销售销售库存查询接口 通用
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsAdidasSalesInventory> wmsSalesInventoryAdidasService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * 增量可销售库存 给hub 通用 比如gnc
     */
    Pagination<WmsAdidasSalesInventory> wmsSalesInventoryHubServiceAdd(int start, int pagesize, String systemKey, Date starteTime, Date endTime);


    /**
     * esprit 按箱收货 小指令
     */
    public ReturnResult EspritAsn(String po, List<EspritAsn> espritAsn);

    /**
     * reebok全量库存查询接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsReebokTotalInventory> wmsTotalInventoryReebokService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * esprit O2O 通过hub 来生成文件
     */

    public EspDeliveryResponse findDeliveries(int staType, String type, Date startTime);


    /**
     * reebok销售库存查询接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsReebokSalesInventory> wmsSalesInventoryReebokService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * gucci销售库存查询接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<GucciSalesInvHub> wmsSalesInventoryGucciService(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * WMS3.0通知上位系统库存流水信息
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsSkuInventoryFlow> wmsSkuInventoryFlow(int start, int pagesize, String systemKey, String customerCode, Date startTime, Date endTime);

    /**
     * WMS3.0所有客户信息推送hub
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    List<WmsCustomer> wmsCustomerList(String systemKey);

    /**
     * WMS3.0无前置单号的占用数据推送hub
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param customerCode
     * @param owner
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsOccupiedAndRelease> wmsNoPreOrderOccupiedData(int start, int pagesize, String systemKey, String customerCode, String owner, Date startTime, Date endTime);

    /**
     * WMS3.0无前置单号的取消释放数据推送hub
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param customerCode
     * @param owner
     * @param starteTime
     * @param endTime
     * @return
     */
    Pagination<WmsOccupiedAndRelease> wmsNoPreOrderReleasedData(int start, int pagesize, String systemKey, String customerCode, String owner, Date startTime, Date endTime);

    /**
     * IM的无前置单号的占用数据
     * 
     * @param staId
     */
    public void insertOccupiedAndRelease(Long staId);

    /**
     * IM的部分出库的释放占用数据
     * 
     * @param staId
     */
    public void insertOccupiedAndReleaseUnDeal(Long staId);

    /**
     * 导入Excel覆盖原始计划量插入取消占用表给IM
     * 
     * @param staCode
     * @param listSource 原始明细行
     * @param listLast 覆盖后明细行
     */
    public void insertOccupiedAndReleaseCover(Long staId, List<StaLine> listSource, List<StaLine> listLast);

    /**
     * 允许发货和修改运单信息接口
     * 
     * @param systemKey
     * @param addInfo
     * @return
     */
    public WmsAdvanceOrderResult wmsAdvanceOrderDeliverNotice(String systemKey, OrderAddInfo addInfo);

    /**
     * 星巴克MSR定制卡入库通知接口
     * 
     * @param systemKey
     * @param list
     * @return
     */
    public ReturnResult recieveMSRCustomCardRtnInfo(String systemKey, List<MSRCustomCardRtnInfo> list);

    /**
     * IM盘点的占用数据
     * 
     * @param staId
     */
    public void insertOccupiedAndReleaseByCheck(Long checkId);

    /**
     * 库存批次查询 通过时间段查询WMS生成的库存批次
     */
    InventoryBatch getWmsSalesInventoryBatch(Date startTime, Date endTime);

    /**
     * 可售库存明细查询 通过库存批次号分页查询对应库存明细
     */
    Pagination<WmsAdidasSalesInventory> getSalesInventoryDetailByBatchCode(int start, int pagesize, String batchCode);
}
