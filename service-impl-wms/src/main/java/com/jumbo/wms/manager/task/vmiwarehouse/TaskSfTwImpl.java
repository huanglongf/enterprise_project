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
package com.jumbo.wms.manager.task.vmiwarehouse;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskSfTw;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.sf.tw.TaskSfTwManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;

/**
 * @author lichuan
 * 
 */
public class TaskSfTwImpl extends BaseManagerImpl implements TaskSfTw {

    private static final long serialVersionUID = 1L;
    @Autowired
    private TaskSfTwManager taskSfTwManager;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;


    /**
     * 同步商品接口
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendSkuService() {
        log.info("SFTW sendSkuItems exe begin...");
        // 获取所有能同步给顺分台湾商品的货主编码
        List<String> sfFlagList = msgSKUSyncDao.findSfFlagSKUSync(Constants.VIM_WH_SOURCE_SF_TW, new SingleColumnRowMapper<String>(String.class));
        for (String sfFlag : sfFlagList) {
            List<MsgSKUSync> skus = msgSKUSyncDao.findVmiMsgSKUSyncForSfFlag(Constants.VIM_WH_SOURCE_SF_TW, sfFlag, new BeanPropertyRowMapper<MsgSKUSync>(MsgSKUSync.class));
            int skusCount = skus.size();
            List<MsgSKUSync> msgSkuList = new ArrayList<MsgSKUSync>();
            for (int i = 0; i < skus.size(); i++) {
                // 每次批量同步10条
                msgSkuList.add(skus.get(i));
                if (skusCount >= 10) {
                    if (msgSkuList.size() % 10 == 0) {
                        try {
                            taskSfTwManager.sendSkuItemsService(msgSkuList, sfFlag);
                        } catch (Exception e) {
                            log.error("TaskSFTW====>sendSkuItemsService throw exception", e);
                            log.error("", e);
                        }
                        skusCount = skusCount - msgSkuList.size();
                        msgSkuList = new ArrayList<MsgSKUSync>();
                    }
                } else {
                    if (msgSkuList.size() % skusCount == 0) {
                        try {
                            taskSfTwManager.sendSkuItemsService(msgSkuList, sfFlag);
                        } catch (Exception e) {
                            log.error("TaskSFTW====>sendSkuItemsService throw exception", e);
                            log.error("", e);
                        }
                        msgSkuList = new ArrayList<MsgSKUSync>();
                    }
                }
            }
        }
        log.info("SFTW sendSkuItems exe end");
    }

    /**
     * 入库通知台湾顺丰
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendInboundOrderService() {
        log.info("SFTW sendInboundOrderService exe begin...");
        sendSkuService();// 同步商品信息
        List<MsgInboundOrder> list = msgInboundOrderDao.findVmiInboundNeedSend(Constants.VIM_WH_SOURCE_SF_TW);
        for (MsgInboundOrder inOrder : list) {
            try {
                taskSfTwManager.sendInboundOrderService(inOrder);
            } catch (Exception e) {
                log.error("TaskSFTW====>sendInboundOrderService throw exception", e);
            }
        }
        log.info("SFTW sendInboundOrderService exe end");
    }

    /**
     * 出库通知台湾顺丰
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendOutboundOrderService() {
        log.info("SFTW sendOutboundOrderService exe begin...");
        List<MsgOutboundOrder> list = msgOutboundOrderDao.findVmiOutboundNeedSend(Constants.VIM_WH_SOURCE_SF_TW);
        for (MsgOutboundOrder outOrder : list) {
            try {
                taskSfTwManager.sendOutboundOrderService(outOrder);
            } catch (BusinessException e) {
                log.warn("TaskSFTW====>sendOutboundOrderService throw exception,{}", e.getErrorCode());
            } catch (Exception e) {
                log.error("TaskSFTW====>sendOutboundOrderService throw exception", e);
            }
        }
        log.info("SFTW sendOutboundOrderService exe end");
    }

    /**
     * 台湾顺丰入库反馈
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inboundOrderRtnService() {
        log.info("SFTW inboundOrderRtnService exe begin...");
        List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrderDao.findInboundByStatus(Constants.VIM_WH_SOURCE_SF_TW, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (msgOrderNewInfo.size() > 0) {
            for (MsgRtnInboundOrder msgNewInorder : msgOrderNewInfo) {
                try {
                    wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        log.warn("TaskSFTW====>inboundOrderRtnService error, errorCode is :" + ((BusinessException) e).getErrorCode(), e);
                    } else {
                        log.error("TaskSFTW====>inboundOrderRtnService throw exception", e);
                    }
                }
            }
        }
        log.info("SFTW inboundOrderRtnService exe end");
    }

    /**
     * 台湾顺丰出库反馈
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void outboundOrderRtnService() {
        log.info("SFTW outboundOrderRtnService exe begin...");
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VIM_WH_SOURCE_SF_TW);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                wareHouseManagerProxy.callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("TaskSFTW====>outboundOrderRtnService error, errorCode is :" + ((BusinessException) e).getErrorCode(), e);
            } catch (Exception e) {
                log.error("TaskSFTW====>outboundOrderRtnService throw exception", e);
            }
        }
        log.info("SFTW outboundOrderRtnService exe end");
    }

}
