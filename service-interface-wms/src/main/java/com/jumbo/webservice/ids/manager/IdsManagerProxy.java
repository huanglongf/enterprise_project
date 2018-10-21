package com.jumbo.webservice.ids.manager;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;

public interface IdsManagerProxy extends BaseManager {
    void idsInventorySyn(String Source);

    void receiveAEOSkuMasterByMq(String message);

    // public void salesOrderToIds();
    // public void salesReturnOrderToIds();
    // public void salesOrderCancelToIds() ;
    public IdsServerInformation findServerInformationBySource(String source);

    public Long findBatchNo();

    public Long findBatchNoPre();
    
    public String queryBatchcodeByOrderQueue();



    void updateMsgOutboundOrderById(Long batchId, List<Long> id);

    List<MsgOutboundOrder> findOutboundOrderBySource(String source);

    public Integer updateMsgOutboundOrderBatchNo(Long batchNo, String source);

    public Integer updateMsgOutboundOrderBatchNo2(Long batchNo, String source, Integer num);

    public Integer updatePreOrderBatchNo(Long batchNo, String source, Integer num);
    
    
    public Integer updateQstaBatchCodeByOuid(String batchNo, Integer num);




    public List<Long> findMsgOutboundOrderBatchNoBySource(String source);
    
    public List<String> findBatchCodeByDetial();


    public List<MsgOutboundOrder> getOutBoundListByBatchNo(Long batchNo);

    void sendOrder(String source, String sourceWh, Long batchNo, List<MsgOutboundOrder> orderList, IdsServerInformation idsServerInformation);
    
    
    void  createRtnOrderBatchCode(String batchNo);
    

    // 预售
    void sendPreOrder(String source, String sourceWh, Long batchNo, List<AdvanceOrderAddInfo> orderList, IdsServerInformation idsServerInformation);

    /**
     * 订单状态更新接口
     * 
     * @param resultXml
     */
    void outOrderReceiveConfirm(String resultXml);


    void wmsOrderToIds(String source, String sourceWh);

    void returnNotifyRequesformLF(String source);

    void orderCancelResponseToLF(String source);

    /**
     * 接口配置信息查询
     * 
     * @return
     */
    Map<String, IdsServerInformation> findIdsServiceInfo();

    List<Long> findPreOrderBatchNoBySource(String source);

    List<AdvanceOrderAddInfo> getPreOrderListByBatchNo(Long batchNo);

}
