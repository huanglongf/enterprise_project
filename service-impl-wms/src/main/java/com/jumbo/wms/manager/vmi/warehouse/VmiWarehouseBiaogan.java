package com.jumbo.wms.manager.vmi.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.outwh.biaogan.BiaoGanManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.webservice.biaogan.clientNew.PushExpressInfoPortTypeClientNew;
import com.jumbo.webservice.biaogan.clientNew2.PushExpressInfoPortTypeClientNew2;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.webservice.biaogan.command.OrderCancelResponse;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;

public class VmiWarehouseBiaogan extends AbstractVmiWarehouse {

    private static final long serialVersionUID = -708345449395153015L;
    @Autowired
    private InOutBoundManager inOutBoundManager;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private BiaoGanManagerProxy biaoGanManagerProxy;

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        String rtnStr = "";
        try {
            OrderCancelResponse sts = PushExpressInfoPortTypeClientNew.orderQuery(staCode);
            if ("true".endsWith(sts.getSuccess())) {
                // 订单存在
                if ("90".equals(sts.getCode())) {
                    // 已经取消
                    msg.setMsg(rtnStr);
                    msgOutboundOrderCancelDao.save(msg);
                    return true;
                } else {
                    InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.cancelOrderRX(staCode);
                    if ("true".equals(resp.getSuccess())) {
                        msg.setMsg(rtnStr);
                        msgOutboundOrderCancelDao.save(msg);
                        return true;
                    } else {
                        // 不能取消
                    	log.error("LF CancelError..."+staCode+";"+rtnStr);
                        throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        } catch (BusinessException e) {
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
        }
    }

    /*
	 * 
	 */
    public boolean cancelInBoundSta(String staCode, MsgOutboundOrderCancel msg) {
        String rtnStr = "";
        try {
            OrderCancelResponse sts = PushExpressInfoPortTypeClientNew.orderQuery(staCode);
            if ("true".endsWith(sts.getSuccess())) {
                // 订单存在
                if ("90".equals(sts.getCode())) {
                    // 已经取消
                    msg.setMsg(rtnStr);
                    msgOutboundOrderCancelDao.save(msg);
                    return true;
                } else {
                    InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.cancelOrderRX(staCode);
                    if ("true".equals(resp.getSuccess())) {
                        msg.setMsg(rtnStr);
                        msgOutboundOrderCancelDao.save(msg);
                        return true;
                    } else {
                        // 不能取消
                        throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
        }

    }

    @Transactional
    public void inboundNotice(MsgInboundOrder msgInorder) {
        // 同步SKU
        biaoGanManagerProxy.singleSkuToWmsProxy();
        inOutBoundManager.ansToWms(msgInorder);
    }

    @Transactional
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {
        inOutBoundManager.ansToWms(msg);
    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    @Override
    @Transactional
    public boolean cancelReturnRequest(Long msgLog) {
        MsgRaCancel msg = msgRaCancelDao.getByPrimaryKey(msgLog);
        if (msg == null) {
            return false;
        }
        try {
            InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.cancelAsnRX(msg.getStaCode());
            if ("true".endsWith(resp.getSuccess())) {
                // 订单存在
                if ("000".equals(resp.getCode())) {
                    // 已经取消
                    msg.setStatus(DefaultStatus.FINISHED);
                    msgRaCancelDao.save(msg);
                    return true;
                } else if ("002".equals(resp.getCode())) {
                    msg.setStatus(DefaultStatus.CANCELED);
                    msgRaCancelDao.save(msg);
                    return false;
                } else if ("005".equals(resp.getCode())) {
                    // 订单不存在或者已经取消
                    msg.setStatus(DefaultStatus.FINISHED);
                    msgRaCancelDao.save(msg);
                    return true;
                } else if ("009".equals(resp.getCode())) {
                    msg.setStatus(DefaultStatus.CANCELED);
                    msgRaCancelDao.save(msg);
                    return false;
                }
            } else {
                msg.setStatus(DefaultStatus.CANCELED);
                msgRaCancelDao.save(msg);
                return false;
            }
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_RO_WH);
        }
        return true;
    }
}
