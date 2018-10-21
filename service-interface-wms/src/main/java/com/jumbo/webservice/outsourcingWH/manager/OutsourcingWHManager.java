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
package com.jumbo.webservice.outsourcingWH.manager;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.webservice.outsourcingWH.command.OrderListConfirm;
import com.jumbo.webservice.outsourcingWH.command.SkuListConfirm;

public interface OutsourcingWHManager extends BaseManager {
    /**
     * 获取销售单据
     * 
     * @param source
     * @return
     */
    public String getPullSo(String source);


    void serviceCfOrder(OrderListConfirm comfirm);

    /**
     * 获取销售出库单据
     * 
     * @param resultXml
     * @return
     */
    List<MsgRtnOutbound> findoutboundOrder(String resultXml, String source) throws Exception;

    /**
     * 获取取消单据
     * 
     * @param source
     * @return
     */
    String getCancelOrderBound(String source);

    /**
     * 确认取消
     * 
     * @param resultXml
     * @return
     */
    void vimExecuteCreateCancelOrder(String resultXml, String source);

    /**
     * 执行取消
     * 
     * @param resultXml
     * @return
     */
    void vimExecuteCancelOrder(Long id);

    /**
     * 获取退货单据
     * 
     * @return
     * @throws Exception
     */
    String findinboundReturnRequest(String source) throws Exception;

    /**
     * 退货入库通知
     * 
     * @param resultXml
     * @return
     * @throws Exception
     */
    List<MsgRtnInboundOrder> transactionRtnInbound(String resultXml, String source) throws Exception;

    /**
     * 获取入库单据
     * 
     * @return
     */
    String pullInBound(String source);

    /**
     * 获取入库单据
     */
    List<MsgRtnInboundOrder> ibInbound(String resultXml, String source);

    /**
     * 获取出库单据
     * 
     * @return
     */
    String pullOutBound(String source);

    /**
     * 获取商品信息
     * 
     * @return
     */
    String pullSku(String source);

    /**
     * 确认接收商品信息
     * 
     * @param confirm
     * @param filename
     */
    void serviceCfSku(SkuListConfirm confirm);

}
