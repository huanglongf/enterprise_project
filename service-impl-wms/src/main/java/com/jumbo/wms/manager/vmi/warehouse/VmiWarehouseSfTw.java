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
 */
package com.jumbo.wms.manager.vmi.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.webservice.sf.tw.SfTwOrderClient;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelReqBody;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelReqHead;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelReqSaleOrder;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelReqSaleOrders;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelRequest;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelRespSaleOrder;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelResponse;
import com.jumbo.webservice.sf.tw.outbound.command.OutboundCancelSaleOrderRequest;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

/**
 * @author lichuan
 * 
 */
@Transactional
public class VmiWarehouseSfTw extends AbstractVmiWarehouse {

    private static final long serialVersionUID = 1L;
    protected static final Logger log = LoggerFactory.getLogger(VmiWarehouseSfTw.class);
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    /**
     * 台湾顺丰取消出库单
     */
    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        msgOutboundOrderDao.flush();
        MsgOutboundOrder order = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        // 如果未给外包仓直接标记为取消,取消单据
        if (order != null && order.getStatus().equals(DefaultStatus.CREATED)) {
            msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), staCode);
            return true;
        }
        OutboundCancelRequest req = new OutboundCancelRequest();
        OutboundCancelReqHead head = new OutboundCancelReqHead();
        head.setAccessCode(SF_TW_ACCESS_CODE);
        head.setCheckword(SF_TW_CHECKWORD);
        req.setHead(head);
        OutboundCancelReqBody body = new OutboundCancelReqBody();
        req.setBody(body);
        OutboundCancelSaleOrderRequest orderReq = new OutboundCancelSaleOrderRequest();
        orderReq.setCompanyCode(SF_TW_COMPANY_CODE);
        body.setCancelSaleOrderRequest(orderReq);
        OutboundCancelReqSaleOrders orders = new OutboundCancelReqSaleOrders();
        OutboundCancelReqSaleOrder o = new OutboundCancelReqSaleOrder();
        o.setErpOrder(order.getStaCode());
        orders.getSaleOrder().add(o);
        orderReq.setSaleOrders(orders);
        try {
            OutboundCancelResponse resp = SfTwOrderClient.cancelSaleOrderService(req);
            if (null != resp) {
                OutboundCancelRespSaleOrder cancelOrder =
                        (null != resp.getBody() ? (null != resp.getBody().getCancelSaleOrderResponse() ? (null != resp.getBody().getCancelSaleOrderResponse().getSaleOrders() ? (null != resp.getBody().getCancelSaleOrderResponse().getSaleOrders()
                                .getSaleOrder() ? resp.getBody().getCancelSaleOrderResponse().getSaleOrders().getSaleOrder().get(0) : null) : null) : null) : null);
                if (null == cancelOrder) {
                    if (log.isDebugEnabled()) {
                        log.debug("SFTW====> cancelSalesOutboundSta response xml structure is error");
                    }
                    throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
                }
                if ("OK".equals(resp.getHead())) {
                    if ("1".equals(cancelOrder.getResult())) {
                        msg.setMsg(cancelOrder.getNote());
                        if (log.isDebugEnabled()) {
                            log.debug("SFTW====> cancelSalesOutboundSta order cancel success!");
                        }
                        return true;
                    } else {
                        msg.setMsg(cancelOrder.getNote());
                        if (log.isDebugEnabled()) {
                            log.debug("SFTW====> cancelSalesOutboundSta order cancel fail!");
                        }
                        throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
                    }
                } else {
                    if ("1".equals(cancelOrder.getResult()) || ("2".equals(cancelOrder.getResult()) && cancelOrder.getNote().contains("订单已作废不允许取消"))) {
                        msg.setMsg(cancelOrder.getNote());
                        if (log.isDebugEnabled()) {
                            log.debug("SFTW====> cancelSalesOutboundSta order cancel success!");
                        }
                        return true;
                    } else {
                        msg.setMsg(cancelOrder.getNote());
                        if (log.isDebugEnabled()) {
                            log.debug("SFTW====> cancelSalesOutboundSta order cancel fail!");
                        }
                        throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("SFTW====> cancelSalesOutboundSta response xml structure is error");
                }
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
            }
        } catch (BusinessException e) {
            log.debug(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("cancelSalesOutboundSta Exception:" + staCode, e);
            }
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
        }
    }

    /** 
     *
     */
    @Override
    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    /** 
     *
     */
    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    /** 
     *
     */
    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return true;
    }

    /** 
     *
     */
    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

}
