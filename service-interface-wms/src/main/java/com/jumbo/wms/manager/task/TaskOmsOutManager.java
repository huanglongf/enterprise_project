package com.jumbo.wms.manager.task;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.MsgOmsSkuLog;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.msg.MessageProducerError;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronous;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;
import com.jumbo.wms.model.warehouse.AgvSku;
import com.jumbo.wms.model.warehouse.InboundAgvToHub;
import com.jumbo.wms.model.warehouse.InvetoryChangeCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOms;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOmsCommand;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;

import loxia.dao.Pagination;

/**
 * 
 * @author xiaolong.fei
 *
 */
public interface TaskOmsOutManager extends BaseManager {

    void threadOutBound();

    void createStaByOdOId(Long id);

    List<InboundAgvToHub> findAllByErrorCount();

    void sendEmail(List<WmsIntransitNoticeOms> list);

    void sendEmailIdsInventory(IdsInventorySynchronous en);

    void emailNoticeOms(List<MsgOmsSkuLog> list);

    void AgvInBoundToHub(InboundAgvToHub inboundAgvToHub);

    void invChangeLogNoticePac(Long staId, Long stvId, Long id);

    /**
     * 查询1W单出库通知订单pac
     * 
     * @return
     */
    List<WmsIntransitNoticeOms> findToNoticeOrder();


    List<InvetoryChangeCommand> findInvAllByErrorCount();

    /**
     * 查询1W单出库通知订单oms
     * 
     * @return
     */
    List<WmsOrderStatusOms> findToNoticeOrderOms();

    /**
     * 查询1W单 直连创单 通知
     * 
     * @return
     */
    List<WmsConfirmOrderQueue> findWmsOrderFinishList();

    /**
     * 查询1W单 外包仓出库反馈 反馈执行失败或丢mq 通知
     * 
     * @return
     */
    List<MsgRtnOutbound> wmsRtnOutBountMq();

    List<MessageProducerError> wmsCommonMessageProducerErrorMq();



    /**
     * 通知PAC订单出库
     * 
     * @param staid
     * @param orderid
     */
    void outboudNoticePacById(Long staid, Long orderid);



    /**
     * 通知PAC订单出库
     * 
     * @param staid
     * @param orderid
     */
    void outboudNoticePacId(WmsOrderStatusOms wmsOrderStatusOms);



    /**
     * 通知OMS订单出库
     * 
     * @param staid
     * @param orderid
     */
    void outboudNoticeOmsById(Long orderid);

    /**
     * 通知OMS直连 创单
     */
    void wmsOrderFinishOms(Long orderid);

    List<Odo> findCreateInBoundStaList();

    /**
     * 
     */
    void wmsRtnOutBountMq(Long orderid);

    void wmsCommonMessageProducerErrorMq(Long orderid);


    /**
     * 直连通知PAC订单出库
     * 
     * @param staid
     * @param orderid
     */
    void outboudNoticePacSystenKey(String systemKey);

    List<Long> getWmsOrderCreateTimeIsNullIds();

    void updateWmsOrderStatusOmsCreateTime(List<Long> ids);

    List<AgvSku> agvSkuToHub();

    void AgvSkuToHub(AgvSku agvSku);

    /**
     * 
     * 方法说明：查询(或根据参数) WmsIntransitNoticeOmsCommand
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午12:49:53
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<WmsIntransitNoticeOmsCommand> findOutPacsByParams(int start, int pageSize, Map<String, Object> params);

    /**
     * 
     * 方法说明：重置为0
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午5:32:16
     * @param idList
     */
    public void resetZero(List<Long> idList);

    /**
     * 
     * 方法说明：重置为100
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午5:32:30
     * @param idList
     */
    public void resetHundred(List<Long> idList);

    List<WmsOrderStatusOms> wmsOrderConfirmPac2();

    List<WmsConfirmOrderQueue> getListByQueryPac2();

    void wmsConfirmOrderService3(WmsConfirmOrderQueue wmsConfirmOrderQueue);

    List<StockTransApplication> getListWmsZhanYong();

    void zhanYongToMq(StockTransApplication stockTransApplication);

    public void staNotCheckInvToMQByPac(Long id);

    List<AgvOutBoundDto> agvOutBoundDaemon();
    
    List<AdPackageLineDealDto> adPackageUpdate();


    void agvOutBoundDaemon(AgvOutBoundDto agvOutBoundDto);

    void adPackageUpdate(AdPackageLineDealDto adPackageLineDealDto);

}
