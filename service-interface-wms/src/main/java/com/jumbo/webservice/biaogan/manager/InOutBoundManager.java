/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.webservice.biaogan.manager;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;

public interface InOutBoundManager extends BaseManager {

    /**
     * 更具作业单对应的外包仓获取库存状态
     * 
     * @param staCode
     * @return 返回Map集合
     */
    Map<String, InventoryStatus> findMsgInvStatusByStaCode(String staCode);

    InOutBoundResponse ansToWms(MsgInboundOrder msg);

    List<MsgRtnInboundOrder> createMsgRtnInboundLine(String xml) throws Exception;

    void updateInOrderSkuId(Long rtnOrderId) throws Exception;

    List<Long> findMsgOutboundOrderIds(String source);
    
    boolean vmiisCancelboundOrder(String soCode);
}
