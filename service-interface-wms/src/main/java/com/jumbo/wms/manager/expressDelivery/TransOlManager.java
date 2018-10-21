package com.jumbo.wms.manager.expressDelivery;

import java.util.List;

import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public interface TransOlManager {
    /**
     * 作业单匹配快递运单号
     * 
     * @param staId 作业单ID
     * @param lpcode
     * @param whouId
     */
    void matchingTransNo(Long staId, String lpcode, Long whouId) throws Exception;

    /**
     * 作业单匹配快递运单号
     * 
     * @param staId 作业单ID
     * @param lpcode
     * @param whouId
     */
    void matchingTransNoEs(Long staId, String lpcode, Long whouId) throws Exception;

    /**
     * 作业单匹配快递运单号
     * 
     */
    void matchingTransNo(Long staId) throws Exception;

    List<Long> findLockedOrder(int rownum);

    Integer getSystemThreadValueByKey(String key);

    public void findStaByStatus(int rowNum);

    /**
     * 外包仓解锁任务
     * 
     */
    void msgUnLocked(Long staId) throws Exception;

    /**
     * O2O+QS配货单包裹匹配快递单号
     * 
     * @param plId
     * @param lpcode
     * @param whouId
     */
    void matchingTransNoByPackage(Long plId, String lpcode, Long whouId);

    List<MsgRtnOutbound> findAllVmiMsgOutboundByRowNum(String source, int rowNum);

    /**
     * 发票订单批量获取运单号
     * 
     * @param wioId
     * @param lpcode
     * @param whouId
     */
    void invoiceOrderMatchingTransNo(Long wioId, String lpcode, Long whouId);

    public TransOlInterface matchingTransNoByPackage1(Long packId, String lpcode, Long whouId) throws Exception;

    /**
     * 销售出库转物流
     * 
     * @param trackingNo
     * @param whidList
     * @param whId
     * @param userName
     * @return
     * @throws JSONException
     */
    public JSONObject changeLpCode(String trackingNo, List<Long> whidList, Long whId, String userName) throws JSONException;

    /**
     * 作业单匹配快递运单号 by mq
     * 
     * @throws Exception
     */
    public void matchingTransNoByMq(Long staId) throws Exception;

    /**
     * 根据ID获取物流单号 MQ
     * 
     * @param staId
     * @throws Exception
     */
    public void matchingTransNoMqByStaId(Long staId) throws Exception;

    /**
     * 重置MQ状态
     * 
     * @param staId
     */
    public void resetMqGetTransNoFlag(Long staId);

}
