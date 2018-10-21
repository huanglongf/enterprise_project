package com.jumbo.wms.manager.outbound;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.web.commond.OrderCheckCommand;

/**
 * @author jinlong.ke
 * @date 2016年7月18日下午3:56:23
 * 
 */
public interface AdCheckManager extends BaseManager {

    /**
     * ad获取作业单是否是预售标示
     */
    boolean isAdPreSale(Long staId);

    /**
     * 二次分拣复核一体化-调用AD接口
     * 
     * @param pickingId
     * @param staLineId
     * @param userId
     */
    public void storeLogisticsSendByTwoPicking(Long pickingId, Long staLineId, Long userId);

    /**
     * @param checkOrder
     */
    String storeLogisticsSend(Long staId, boolean autoSend);

    /**
     * @param checkOrder
     * @param id
     * @param id2
     */
    void ifExistsLineCanncel(OrderCheckCommand checkOrder, Long id, Long id2);

    /**
     * @param staIdList
     */
    void reOccupationByIdList(List<Long> staIdList, Long ouId);

    /**
     * @param id
     */
    void confirmCheckOrderById(Long id);

    /**
     * @return
     */
    List<Long> getAllNeedConfirmCheckOrder();

}
