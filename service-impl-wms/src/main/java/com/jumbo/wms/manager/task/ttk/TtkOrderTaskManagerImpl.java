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
package com.jumbo.wms.manager.task.ttk;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TtkConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.TtkConfirmOrderQueueLogDao;
import com.jumbo.webservice.ttk.Item;
import com.jumbo.webservice.ttk.Items;
import com.jumbo.webservice.ttk.Receiver;
import com.jumbo.webservice.ttk.RequestOrder;
import com.jumbo.webservice.ttk.Sender;
import com.jumbo.webservice.ttk.TransBigWordReceiver;
import com.jumbo.webservice.ttk.TransBigWordResponse;
import com.jumbo.webservice.ttk.TtkOrderClient;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TtkConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.TtkConfirmOrderQueueLog;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("ttkOrderTaskManager")
public class TtkOrderTaskManagerImpl extends BaseManagerImpl implements TtkOrderTaskManager {

    private static final long serialVersionUID = 5383935316411867236L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private TtkConfirmOrderQueueDao ttkConfirmOrderQueueDao;
    @Autowired
    private TtkConfirmOrderQueueLogDao ttkConfirmOrderQueueLogDao;

    /**
     * 设置大头笔信息
     * 
     * @throws Exception
     */
    @Override
    public void setTransBigWord(Long staId) throws Exception {
        if (null == staId) return;
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        try {
            StaDeliveryInfo sd = stab.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(staId);
            }
            if (StringUtils.hasText(sd.getTrackingNo())) {
                // 获取当前运单信息对应的大头笔
                List<StaDeliveryInfoCommand> deliveryInfos = new ArrayList<StaDeliveryInfoCommand>();
                StaDeliveryInfoCommand di = new StaDeliveryInfoCommand();
                di.setTrackingNo(sd.getTrackingNo());
                di.setProvince(sd.getProvince());
                di.setCity(sd.getCity());
                di.setDistrict(sd.getDistrict());
                deliveryInfos.add(di);
                TransBigWordResponse res = TtkOrderClient.postTransBigWordByDeliveryInfo(deliveryInfos, TTK_SITE, TTK_CUS, TTK_PASSWORD, TTK_POST_URL);
                if (null != res) {
                    List<TransBigWordReceiver> bigWords = res.getData();
                    if (0 < bigWords.size()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(bigWords.get(0).getResult()).append(" ").append(bigWords.get(0).getPack());
                        if (!StringUtils.hasText(sd.getTransBigWord())) {
                            sd.setTransBigWord(sb.toString());
                            sd.setLastModifyTime(new Date());
                            staDeliveryInfoDao.save(sd);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("====>TTK,set trans big word throw exception,staCode is:[{}], and staId is:[{}]", stab.getCode(), staId);
            log.error("====>TTK,set trans big word throw exception", e);
            throw e;
        }
    }

    /**
     * 
     * 匹配运单号与设置大头笔信息
     * 
     * @throws Exception
     */
    @Override
    public void setTransNoAndTransBigWord(Long staId) throws Exception {
        // 匹配TTK单据号
        transOlManager.matchingTransNo(staId);
        // 设置大头笔
        setTransBigWord(staId);
    }

    /**
     * 执行订单信息回传，出库通知TTK
     */
    @Override
    public void exeTtkConfirmOrder(TtkConfirmOrderQueue q) {
        RequestOrder req = new RequestOrder();
        req.setEcCompanyId(q.getEcCompanyId());
        req.setLogisticProviderID(q.getLogisticProviderID());
        req.setCustomerId(q.getCustomerId());
        req.setTxLogisticID(q.getTxLogisticID());
        req.setTradeNo(StringUtils.hasText(q.getTradeNo()) ? q.getTradeNo() : "");
        req.setMailNo(q.getMailNo());
        req.setTotalServiceFee(q.getTotalServiceFee());
        req.setCodSplitFee(q.getCodSplitFee());
        req.setBuyServiceFee(q.getBuyServiceFee());
        req.setOrderType(q.getOrderType());
        req.setServiceType(q.getServiceType());
        Sender sender = new Sender();
        sender.setName("");
        sender.setPostCode("");
        sender.setPhone("");
        sender.setMobile("");
        sender.setProv("");
        sender.setCity("");
        sender.setAddress("");
        req.setSender(sender);
        Receiver receiver = new Receiver();
        receiver.setName(q.getName());
        receiver.setPostCode(StringUtils.hasText(q.getPostCode()) ? q.getPostCode() : "");
        receiver.setPhone(StringUtils.hasText(q.getPhone()) ? q.getPhone() : "");
        receiver.setMobile(StringUtils.hasText(q.getMobile()) ? q.getMobile() : "");
        receiver.setProv(StringUtils.hasText(q.getProv()) ? q.getProv() : "");
        receiver.setCity(q.getCity() + " " + q.getArea());
        receiver.setAddress(StringUtils.hasText(q.getAddress()) ? q.getAddress() : "");
        req.setReceiver(receiver);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        req.setSendStartTime(df.format(new Date()));
        req.setSendEndTime("");
        Items items = new Items();
        Item item = new Item();
        item.setItemName("");
        item.setItemValue(new BigDecimal("0.0"));
        item.setNumber(0L);
        items.getItem().add(item);
        req.setItems(items);
        req.setSpecial(99);
        req.setRemark(q.getRemark());
        boolean flag = TtkOrderClient.comfirmOrder(req, TTK_ORDER_URL, TTK_DIGEST_PARTERNID);
        if (flag) {
            TtkConfirmOrderQueueLog qLog = new TtkConfirmOrderQueueLog();
            qLog.setStaCode(q.getStaCode());
            qLog.setExeCount(q.getExeCount());
            qLog.setEcCompanyId(q.getEcCompanyId());
            qLog.setLogisticProviderID(q.getLogisticProviderID());
            qLog.setCustomerId(q.getCustomerId());
            qLog.setSiteName(q.getSiteName());
            qLog.setTxLogisticID(q.getTxLogisticID());
            qLog.setTradeNo(q.getTradeNo());
            qLog.setMailNo(q.getMailNo());
            qLog.setOrderType(q.getOrderType());
            qLog.setServiceType(q.getServiceType());
            qLog.setName(q.getName());
            qLog.setPostCode(q.getPostCode());
            qLog.setPhone(q.getPhone());
            qLog.setMobile(q.getMobile());
            qLog.setProv(q.getProv());
            qLog.setCity(q.getCity());
            qLog.setArea(q.getArea());
            qLog.setAddress(q.getAddress());
            qLog.setSendStartTime(q.getSendStartTime());
            qLog.setSendEndTime(new Date());
            qLog.setGoodsValue(q.getGoodsValue());
            qLog.setItemsValue(q.getItemsValue());
            qLog.setItemName(q.getItemName());
            qLog.setNumber(q.getNumber());
            qLog.setItemValue(q.getItemsValue());
            qLog.setSpecial(q.getSpecial());
            qLog.setRemark(q.getRemark());
            qLog.setTotalServiceFee(q.getTotalServiceFee());
            qLog.setCodSplitFee(q.getCodSplitFee());
            qLog.setBuyServiceFee(q.getBuyServiceFee());
            qLog.setFinishTime(new Date());
            ttkConfirmOrderQueueLogDao.save(qLog);
            ttkConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
        } else {
            TtkConfirmOrderQueue order = ttkConfirmOrderQueueDao.getByPrimaryKey(q.getId());
            order.setExeCount(order.getExeCount() + 1L);
        }

    }

}
