package com.jumbo.wms.manager.expressDelivery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import loxia.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.DeliveryChangeConfigure;
import com.jumbo.wms.model.warehouse.DeliveryChanngeLog;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.QueueMqSta;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

@Transactional
@Service("transOlManager")
public class TransOlManagerImpl extends BaseManagerImpl implements TransOlManager {

    private static final long serialVersionUID = -6942605421335356787L;

    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private TransAliWaybill transAliWaybill;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Autowired
    private PackageInfoDao packageInfoDao;
    
    @Value("${mq.mq.wms3.test.order}")
    public String WMS3_MQ_TEST_ORDER;

    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;

    /**
     * 作业单匹配快递运单号
     * 
     * @throws Exception
     */
    public void matchingTransNo(Long staId, String lpcode, Long whouId) throws Exception {
        Warehouse wh = warehouseDao.getByOuId(whouId);
        // 匹配运单号
        TransOlInterface transOlInterface = transOnLineFactory.getTransOnLine(lpcode, whouId);
        if (transOlInterface != null) {
            StaDeliveryInfo sd = null;
            /*
             * ChooseOption option =
             * chooseOptionManager.findChooseOptionByCategoryCodeAndKey(Constants.ALI_WAYBILL,
             * lpcode); if (option != null &&
             * Constants.ALI_WAYBILL_SWITCH_ON.equals(option.getOptionValue())) { sd =
             * transAliWaybill.getTransNoByStaId(staId); if (sd == null ||
             * StringUtil.isEmpty(sd.getTrackingNo())) { sd = null; } } if (sd == null) {
             */
                sd = transOlInterface.matchingTransNo(staId);
            // }
            // 外包仓解锁
            if (StringUtils.hasText(wh.getVmiSource())) {
                StockTransApplication sta = staDao.getByPrimaryKey(staId);
                MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta.getCode());
                if (mo != null && mo.getIsLocked() != null && mo.getIsLocked()) {
                    // 解锁的同时设置msgOutBoundOrder的TransNo，SfCityCode
                    mo.setTransNo(sd.getTrackingNo());
                    mo.setSfCityCode(sd.getTransCityCode());
                    // 解锁
                    mo.setIsLocked(false);
                    if ("1".equals(MSG_OUTBOUNT_CREATETIMELOG)) {
                        mo.setCreateTime(new Date());// 创建时间
                    }
                    msgOutboundOrderDao.save(mo);
                }
            }
        }
    }

    /**
     * 作业单匹配快递运单号Es
     */
    public void matchingTransNoEs(Long staId, String lpcode, Long whouId) throws Exception {
        Warehouse wh = warehouseDao.getByOuId(whouId);
        // 匹配运单号
        TransOlInterface transOlInterface = transOnLineFactory.getTransOnLine(lpcode, whouId);
        if (transOlInterface != null) {
            StaDeliveryInfo sd = null;
            /*
             * ChooseOption option =
             * chooseOptionManager.findChooseOptionByCategoryCodeAndKey(Constants.ALI_WAYBILL,
             * lpcode); if (option != null &&
             * Constants.ALI_WAYBILL_SWITCH_ON.equals(option.getOptionValue())) { sd =
             * transAliWaybill.getTransNoByStaId(staId); if (sd == null ||
             * StringUtil.isEmpty(sd.getTrackingNo())) { sd = null; } } if (sd == null) {
             */
                sd = transOlInterface.matchingTransNoEs(staId);
            // }
            // StaDeliveryInfo sd = transOlInterface.matchingTransNoEs(staId);
            // 外包仓解锁
            if (StringUtils.hasText(wh.getVmiSource())) {
                StockTransApplication sta = staDao.getByPrimaryKey(staId);
                MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta.getCode());
                if (mo != null && mo.getIsLocked() != null && mo.getIsLocked()) {
                    // 解锁的同时设置msgOutBoundOrder的TransNo，SfCityCode
                    mo.setTransNo(sd.getTrackingNo());
                    mo.setSfCityCode(sd.getTransCityCode());
                    // 解锁
                    mo.setIsLocked(false);
                    msgOutboundOrderDao.save(mo);
                }
            }
        }
    }

    /**
     * 外包仓解锁任务
     * 
     * @throws Exception
     */
    public void msgUnLocked(Long staId) throws Exception {
        /* try { */
        matchingTransNo(staId);
        /*
         * } catch (BusinessException e) { // 记录获取运单失败的单据
         * wareHouseManagerExecute.failedToGetTransno(staId); StockTransApplication sta1 =
         * staDao.getByPrimaryKey(staId); log.error(e.getErrorCode() + "-----"); //
         * 判断顺丰可否送达，否则修改快递供应商为EMS/EMSCOD MsgOutboundOrder mo =
         * msgOutboundOrderDao.getByStaCode(sta1.getCode()); Warehouse wh =
         * warehouseDao.getByOuId(sta1.getMainWarehouse().getId()); StaDeliveryInfo sinfo =
         * sta1.getStaDeliveryInfo(); if (!Constants.VIM_WH_SOURCE_GUESS.equals(mo.getSource())) {
         * if (sinfo.getIsCod() != null && sinfo.getIsCod()) {
         * sinfo.setLpCode(Transportator.EMS_COD); mo.setLpCode(Transportator.EMS_COD); } else {
         * sinfo.setLpCode(Transportator.EMS); mo.setLpCode(Transportator.EMS); } if
         * (wh.getIsEmsOlOrder()) { mo.setIsLocked(true); } else { mo.setIsLocked(false); } }
         * msgOutboundOrderDao.save(mo); staDeliveryInfoDao.save(sinfo); log.error("", e); } catch
         * (Exception e) { log.error("msgUnLocked", e); }
         */
    }


    @Override
    public List<Long> findLockedOrder(int rownum) {
        return staDao.findLockedOrder(rownum, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public Integer getSystemThreadValueByKey(String key) {
        return chooseOptionManager.getSystemThreadValueByKey(key);
    }

    /**
     * 作业单匹配快递运单号
     */
    public void matchingTransNo(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo d = sta.getStaDeliveryInfo();
        matchingTransNo(staId, d.getLpCode(), sta.getMainWarehouse().getId());
    }

    @Override
    public void matchingTransNoByPackage(Long plId, String lpcode, Long whouId) {
        // 匹配运单号
        TransOlInterface transOlInterface = transOnLineFactory.getTransOnLine(lpcode, whouId);
        if (transOlInterface != null) {
            transOlInterface.matchingTransNoByPackage(plId);// O2O+QS
        }
    }

    @Override
    public void invoiceOrderMatchingTransNo(Long wioId, String lpcode, Long whouId) {
        // 匹配运单号
        TransOlInterface transOlInterface = transOnLineFactory.getTransOnLine(lpcode, whouId);
        if (transOlInterface != null) {
            transOlInterface.invoiceOrderMatchingTransNo(wioId);
        }
    }

    @Override
    public List<MsgRtnOutbound> findAllVmiMsgOutboundByRowNum(String source, int rowNum) {
        return msgRtnOutboundDao.findAllVmiMsgOutboundByRowNum(source, rowNum, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
    }


    public void findStaByStatus(int rowNum) {
        List<StockTransApplication> stockTransApplicationList = staDao.findStaByStatus(rowNum, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
        if (null != stockTransApplicationList && stockTransApplicationList.size() > 0) {
            int j = 0;
            List<Long> idList = new ArrayList<Long>();
            List<QueueMqSta> QueueMqStaList = new ArrayList<QueueMqSta>();
            for (int i = 0; i < stockTransApplicationList.size(); i++) {
                MessageCommond mc = new MessageCommond();
                QueueMqSta queueMqSta = new QueueMqSta();
                queueMqSta.setId(stockTransApplicationList.get(i).getId());
                QueueMqStaList.add(queueMqSta);
                String msg = JsonUtil.writeValue(QueueMqStaList);
                mc.setMsgId(stockTransApplicationList.get(i).getId() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                mc.setMsgType("com.jumbo.wms.manager.expressDelivery.TransOlManagerImpl.findStaByStatus");
                mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                mc.setMsgBody(msg);
                mc.setIsMsgBodySend(false);
                try {
                    producerServer.sendDataMsgConcurrently(WMS3_MQ_TEST_ORDER, mc);
                    QueueMqStaList.clear();
                } catch (Exception e) {
                    log.debug("findStaByStatus:" + stockTransApplicationList.get(i).getCode(), e.toString());
                    QueueMqStaList.clear();
                }
                idList.add(stockTransApplicationList.get(i).getId());
                if (j == 990) {
                    staDao.updateStaById(idList);
                    j = 0;
                    idList.clear();
                } else {
                    if (j == stockTransApplicationList.size() - 1) {
                        staDao.updateStaById(idList);
                        idList.clear();
                    }
                }
                j++;
            }
        }
    }

    /**
     * 根据包裹匹配新运单号 出库转物流专业
     * 
     * @param plId
     * @param lpcode
     * @param whouId
     * @return
     * @throws Exception
     */
    @Override
    public TransOlInterface matchingTransNoByPackage1(Long packId, String lpcode, Long whouId) {
        // StaDeliveryInfo sd = null;
        // 匹配运单号
        TransOlInterface transOlInterface = transOnLineFactory.getTransOnLine1(lpcode, whouId);
        if (transOlInterface != null) {
            transOlInterface.matchingTransNoByPackId(packId, "1");
        }

        return transOlInterface;
    }



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
    public JSONObject changeLpCode(String trackingNo, List<Long> whidList, Long whId, String userName) throws JSONException {
        JSONObject ob = new JSONObject();

        BigDecimal volume = null;
        List<StockTransApplicationCommand> stas;
        DeliveryChanngeLog channgeLog = new DeliveryChanngeLog();
        Sku skuCommand = null;
        String newTrackingNo = null;
        DeliveryChangeConfigure configure = null;
        StockTransApplicationCommand st = null;
        PackageInfo packinfo = null;
        StaDeliveryInfo infoCommand = wareHouseManager.findStaDeliveryInfoByTrackingNo(trackingNo);
        if (infoCommand == null) {
            packinfo = wareHouseManager.findPackByTrackingNo(trackingNo);
        }
        // 先去变更日志表里查，如果不为空，侧说明该快递单已经执行了变更，直接返回结果
        channgeLog = wareHouseManager.findDeliveryChanngeLogByTrNo(trackingNo);
        if (channgeLog != null) {
            ob.put("lpCode", channgeLog.getLpcode());
            ob.put("trackingNo", channgeLog.getTrackingNo());
            ob.put("weight", channgeLog.getWeight());
            ob.put("volume", channgeLog.getVolume());
            ob.put("newLpCode", channgeLog.getNewLpcode());
            ob.put("newTrackingNo", channgeLog.getNewTrackingNo());
            // 关联原始快递单
            ob.put("relevance", "isTrue");
            return ob;
        }
        PackageInfo packageInfo = wareHouseManager.findPackByTrackingNo(trackingNo);
        // 根据trackingNo获取作业单信息
        if (whidList != null && whidList.size() > 0) {
            stas = wareHouseManager.findStaListByTrackingNo1(null, whidList, trackingNo, null);
        } else {
            stas = wareHouseManager.findStaListByTrackingNo1(whId, null, trackingNo, null);
        }
        skuCommand = wareHouseManager.findSkuConsumptiveByTrNo(trackingNo);
        // 获取变更配置信息
        if (packinfo != null) {
            configure = wareHouseManager.findDCCByLpcode1(packinfo.getLpCode());
        } else {
            configure = wareHouseManager.findDCCByLpcode1(packageInfo.getLpCode());
        }
        // 更新物流信息面单号
        wareHouseManager.updateStaDeliveryById(packageInfo.getStaDeliveryInfo().getId(), newTrackingNo, configure.getNewLpcode());
        packageInfoDao.flush();
        matchingTransNoByPackage1(packageInfo.getId(), configure.getNewLpcode(), whId);
        // 获取新的物流单号
        if (infoCommand != null) {
            newTrackingNo = wareHouseManager.getDeliveryTrackingNo(infoCommand.getId());
        } else {
            newTrackingNo = wareHouseManager.getPackInfoById(packinfo.getId()).getTrackingNo();
        }

        // 记录变更信息到物流变更日志表里
        DeliveryChanngeLog channgeLog1 = new DeliveryChanngeLog();
        channgeLog1.setCreateUser(userName);
        channgeLog1.setCreateTime(new Date());
        channgeLog1.setTrackingNo(trackingNo);
        channgeLog1.setNewTrackingNo(newTrackingNo);
        channgeLog1.setLpcode(configure.getLpcode());
        channgeLog1.setNewLpcode(configure.getNewLpcode());
        channgeLog1.setStaId(stas.get(0).getId());
        if (infoCommand != null) {
            channgeLog1.setWeight(infoCommand.getWeight());
        } else {
            channgeLog1.setWeight(packinfo.getWeight());
        }
        wareHouseManager.insertDeliverChangeLog(channgeLog1);
        st = stas.get(0);
        // 同步物流商发货接口
        try {
            wareHouseManager.synchroDeliveryInfo(configure.getNewLpcode(), newTrackingNo, whId, st);
        } catch (Exception e) {}
        // 获取包裹体积
        if (skuCommand != null) {
            try {
                volume = skuCommand.getHeight().multiply(skuCommand.getLength()).multiply(skuCommand.getWidth());
                // 把立方毫米转为立方米，保留两位数
                volume = volume.divide(BigDecimal.valueOf(1000000.00), 2);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (infoCommand != null) {
            ob.put("lpCode", infoCommand.getLpCode());
            ob.put("weight", infoCommand.getWeight());
        } else {
            ob.put("lpCode", packinfo.getLpCode());
            ob.put("weight", packinfo.getWeight());
        }
        ob.put("newLpCode", configure.getNewLpcode());
        ob.put("packId", packageInfo.getId());
        ob.put("trackingNo", trackingNo);
        ob.put("volume", volume);
        ob.put("newTrackingNo", newTrackingNo);
        // 没有关联原始订单
        ob.put("relevance", "isFalse");
        if (st != null) {
            ob.put("staId", st.getId());
        } else {
            ob.put("staId", packinfo.getStaDeliveryInfo().getId());
        }
        return ob;
    }

    /**
     * 作业单匹配快递运单号 by mq
     * 
     * @throws Exception
     */
    public void matchingTransNoByMq(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if(sta != null){
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            // 匹配运单号
            TransOlInterface transOlInterface = transOnLineFactory.getTransOnLine(sta.getStaDeliveryInfo().getLpCode(), sta.getMainWarehouse().getId());
            if (transOlInterface != null) {
                StaDeliveryInfo sd = null;
                sd = transOlInterface.matchingTransNo(staId);
                // 外包仓解锁
                if (StringUtils.hasText(wh.getVmiSource())) {
                    MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta.getCode());
                    if (mo != null && mo.getIsLocked() != null && mo.getIsLocked()) {
                        // 解锁的同时设置msgOutBoundOrder的TransNo，SfCityCode
                        mo.setTransNo(sd.getTrackingNo());
                        mo.setSfCityCode(sd.getTransCityCode());
                        // 解锁
                        mo.setIsLocked(false);
                        mo.setCreateTime(new Date());// 创建时间
                        msgOutboundOrderDao.save(mo);
                    }
                }
            } else {
                // 不是电子运单, 修改Next_Get_Trans_Time
                wareHouseManagerExecute.updateNextGetTransTime(staId);
            }
        }
    }

    /**
     * 根据ID获取物流单号 MQ
     * 
     * @param staId
     * @throws Exception
     */
    public void matchingTransNoMqByStaId(Long staId) throws Exception {
        
        matchingTransNoByMq(staId);
        // resetMqGetTransNoFlag(staId);
    }

    /**
     * 重置MQ状态
     * 
     * @param staId
     */
    public void resetMqGetTransNoFlag(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (StringUtil.isEmpty(sta.getStaDeliveryInfo().getTrackingNo())) {
            sta.getStaDeliveryInfo().setMqGetTransNo(null);
            wareHouseManagerExecute.failedToGetTransno(staId);
        }
    }
}
