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

package com.jumbo.wms.manager.task;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.sdk.client.api.Client;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.system.SmsLogDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.StaCancelNoticeOmsDao;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.webservice.outsourcingWH.manager.OutsourcingWHManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskManagerProxy;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.system.SmsLog;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.StaCancelNoticeOms;

/**
 * @author wanghua
 * 
 */

@Service("taskManagerProxy")
public class TaskManagerProxyImpl extends BaseManagerImpl implements TaskManagerProxy {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3603796046827109509L;
    private static final Logger log = LoggerFactory.getLogger(TaskManagerProxyImpl.class);
    @Autowired
    private StaCancelNoticeOmsDao staCancelNoticeOmsDao;
    @Autowired
    private OutsourcingWHManager manager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private InOutBoundManager inoutManager;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private SmsLogDao smsLogDao;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void gqsTask() {
        log.debug("gqsTask begin");
        // 出库
        List<MsgRtnOutbound> msgOutList = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_GQS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        for (MsgRtnOutbound out : msgOutList) {
            try {
                wareHouseManagerProxy.callVmiSalesStaOutBound(out.getId());
            } catch (BusinessException e) {
                log.error("gqsTask error ! OUT STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
        // 入库
        List<MsgRtnInboundOrder> msgInList = msgRtnInboundOrderDao.findInboundByStatus(Constants.VIM_WH_SOURCE_GQS, new BeanPropertyRowMapper<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        for (MsgRtnInboundOrder in : msgInList) {
            try {
                inoutManager.updateInOrderSkuId(in.getId());
                wareHouseManagerProxy.msgInBoundWareHouse(in);
            } catch (BusinessException be) {
                log.warn("gqsTask error,error code is : {},id:{}", be.getErrorCode(),in.getId());
            } catch (UnexpectedRollbackException be) {
                log.error("gqsTask error,Transaction rolled back because it has been marked as rollback-only,id : {}", in.getId());
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.info("gqsTask end");
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendSms() {
        try {
            Client smsClient = new Client(Constants.SMS_SERIAL_NUMBER, Constants.SMS_KEY);
            List<SmsLog> list = smsLogDao.findUnSendMsg(new BeanPropertyRowMapperExt<SmsLog>(SmsLog.class));
            for (SmsLog lg : list) {
                try {
                    sendSmsMsg(lg.getId(), smsClient);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Transactional
    public void sendSmsMsg(Long msgId, Client smsClient) {
        SmsLog lg = smsLogDao.getByPrimaryKey(msgId);
        int rs = smsClient.sendSMS(new String[] {"13585847249"}, lg.getSmsContent(), 1);
        lg.setSendStatus(10);
        lg.setStatus(rs);
        smsLogDao.save(lg);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void execoutStaCancelNoticeOms() {
        List<StaCancelNoticeOms> list = staCancelNoticeOmsDao.getToExeList(StaCancelNoticeOms.MAX_ERROR_COUNT);
        for (StaCancelNoticeOms scno : list) {
            wareHouseManagerExecute.httpPostOmsOrderCancel(scno.getStaId());
        }
    }

    /**
     * 外包仓取消定时任务
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void msgCancelTask() {
        log.debug("msgCancelTask...");
        List<Long> ids = msgOutboundOrderCancelDao.findMsgOutboundOrderCancelIdList(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : ids) {
            try {
                manager.vimExecuteCancelOrder(id);
            } catch (Exception e) {
                log.error("", e);
                log.debug(e.getMessage());
                MsgOutboundOrderCancel cancel = msgOutboundOrderCancelDao.getByPrimaryKey(id);
                if (cancel.getErrorCount() == null || cancel.getErrorCount().equals("")) {
                    cancel.setErrorCount(0l);
                }
                cancel.setErrorCount(cancel.getErrorCount() + 1);
                // cancel.setStatus(MsgOutboundOrderCancelStatus.INEXECUTION);
                msgOutboundOrderCancelDao.save(cancel);
                // taskManager.updateUpdateCancleOrder(id,
                // MsgOutboundOrderCancelStatus.INEXECUTION);
            }
        }
    }
}
