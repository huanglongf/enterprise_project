package com.jumbo.wms.manager.task.express;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.util.JSONUtil;

import com.baozun.scm.primservice.logistics.manager.OrderConfirmContentManager;
import com.baozun.scm.primservice.logistics.model.OrderConfirmContent;
import com.baozun.scm.primservice.logistics.model.OrderConfirmResponse;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.ExpressConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.ExpressConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransInfoDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.util.TimeHashMap;
import com.jumbo.webservice.express.ExpressAddedService;
import com.jumbo.webservice.express.ExpressContactInformation;
import com.jumbo.webservice.express.ExpressOrderConfirmRequest;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.TransInfoManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.trans.TransInfo;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonStatus;
import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueueLog;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;

@Transactional
@Service("transInfoManager")
public class TransInfoManagerImpl extends BaseManagerImpl implements TransInfoManager {

    private static final long serialVersionUID = 7908375450549280597L;

    @Autowired
    private TransInfoDao transInfoDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private ExpressConfirmOrderQueueDao expressConfirmOrderQueueDao;
    @Autowired
    private ExpressConfirmOrderQueueLogDao expressConfirmOrderQueueLogDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private OrderConfirmContentManager OrderConfirmContentManager;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private SkuDao skuDao;

    private TimeHashMap<String, TransInfo> transInfoCache = new TimeHashMap<String, TransInfo>();

    @Override
    public List<TransInfo> getAvailableTransInfo() {
        return transInfoDao.getAvailableTransInfo();
    }

    @Override
    public void setMailNos() {
        Boolean flag = true;
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD, ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_EXPRESS_TRANS_NO);
        Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
        // 根据参数创建线程池
        while (flag) {
            ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
            ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            List<Long> staList = transInfoDao.findStaByTransInfoLpCode(new SingleColumnRowMapper<Long>(Long.class));
            if (staList.size() < 10000) {
                flag = false;
            }
            for (Long id : staList) {
                try {
                    Thread t = new ExpressGetNoThread(id);
                    tx.execute(t);
                } catch (Exception e) {
                    log.error("", e);
                }
                while (true) {
                    long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
                    if (todoTotal >= 100) {
                        try {
                            Thread.sleep(500L);
                            log.error("get trans no thread todoTotal is " + todoTotal);
                        } catch (InterruptedException e) {
                            log.error("sleep error");
                        }
                    } else {
                        break;
                    }
                }
            }
            tx.shutdown();
            boolean isFinish = false;
            while (!isFinish) {
                isFinish = pool.isTerminated();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    log.error("InterruptedException majorThread error");
                }
            }
        }
        log.error("get trans no end");
    }

    @Override
    public List<Long> findExtOrderIdSeo() {
        return expressConfirmOrderQueueDao.findExtOrderIdSeo(5L, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public String getConfirmOrderJson(ExpressConfirmOrderQueue q) {
        StockTransApplication sta = staDao.findStaByCode(q.getStaCode());
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        // 物流商配置信息
        TransInfo transInfo = transInfoCache.get(sd.getLpCode());
        if (transInfo == null) {
            transInfo = transInfoDao.getTransInfoByLpCodeAndRegionCode(sd.getLpCode(), wh.getRegionCode(), new BeanPropertyRowMapper<TransInfo>(TransInfo.class));
            transInfoCache.put(sd.getLpCode(), transInfo);
        }
        if (transInfo == null) {
            log.info(sd.getLpCode() + " transInfo is null!!!");
            throw new BusinessException(ErrorCode.NO_EXPRESS_ACCOUNT);
        }
        ExpressOrderConfirmRequest req = new ExpressOrderConfirmRequest();
        req.setAccount(transInfo.getAccount());
        req.setCheckword(transInfo.getPassword());
        req.setRegionCode(wh.getRegionCode());
        req.setIsFragile(false);// 是否易碎
        req.setIsDanger(false);// 是否危险品
        req.setIsLiquid(false);// 是否含液体
        req.setIsBattery(false); // 是否含有锂电池
        req.setIsWorkDay(true);// 是否工作日配送
        req.setIsNight(true); // 是否夜间配送
        req.setIsWeekend(true);// 是否周末配送
        req.setMailno(q.getTransNo());
        req.setLpCode(transInfo.getLpcode());
        req.setType("1"); // 普通订单
        if (sd.getTransTimeType() != null) {
            req.setProductCode(String.valueOf(sd.getTransTimeType().getValue()));
        } else {
            req.setProductCode("1");
        }
        if (sta.getDeliveryType() != null) {
            req.setTransportType(String.valueOf(sta.getDeliveryType().getValue()));
        } else {
            req.setTransportType("1");
        }
        req.setOrderId(sta.getCode());
        // 寄件人信息
        ExpressContactInformation sender = new ExpressContactInformation();
        sender.setName(wh.getPic());
        sender.setPostCode(wh.getZipcode());
        sender.setPhone(wh.getPhone());
        sender.setMobile(wh.getPhone());
        sender.setProvince(wh.getProvince());
        sender.setCity(wh.getCity());
        sender.setDistrict(wh.getDistrict());
        sender.setAddress(wh.getAddress());
        req.setSender(sender);
        // 收件人信息
        ExpressContactInformation receiver = new ExpressContactInformation();
        receiver.setName(sd.getReceiver());
        receiver.setPostCode(sd.getZipcode());
        if (StringUtils.hasText(sd.getMobile())) {
            receiver.setMobile(sd.getMobile());
            receiver.setPhone(sd.getTelephone());
        } else if (StringUtils.hasText(sd.getTelephone())) {
            receiver.setMobile(sd.getTelephone());
            receiver.setPhone(sd.getTelephone());
        }
        receiver.setProvince(sd.getProvince());
        receiver.setCity(sd.getCity());
        receiver.setDistrict(sd.getDistrict());
        receiver.setAddress(sd.getAddress());
        req.setReceiver(receiver);
        req.setItemQty(sta.getSkuQty() == null ? 0 : sta.getSkuQty().intValue());
        List<ExpressAddedService> serviceList = new ArrayList<ExpressAddedService>();
        if (sd.getIsCod() != null && sd.getIsCod()) { // 是否COD
            ExpressAddedService service = new ExpressAddedService();
            service.setName("COD");
            BigDecimal totalActual = sta.getTotalActual() == null ? new BigDecimal(0) : sta.getTotalActual();
            BigDecimal transferFee = sd.getTransferFee() == null ? new BigDecimal(0) : sd.getTransferFee();
            service.setValue(String.valueOf(totalActual.add(transferFee)));
            serviceList.add(service);
        }
        if (sd.getInsuranceAmount() != null) { // 是否有保价
            ExpressAddedService service = new ExpressAddedService();
            service.setName("INSURE");
            service.setValue(String.valueOf(sd.getInsuranceAmount()));
            serviceList.add(service);
        }
        if (serviceList.size() > 0) {
            req.setAddedService(serviceList);
        }
        req.setWeight(new BigDecimal(q.getWeight()));
        req.setLength(new BigDecimal(q.getLength()));
        req.setWidth(new BigDecimal(q.getWidth()));
        req.setHeight(new BigDecimal(q.getHeight()));
        req.setVolume(new BigDecimal(q.getVolume()));
        req.setPackageQty(1);
        String json = JSONUtil.beanToJson(req);
        return json;
    }

    @Override
    public Long getTranNoNumberByLpCodeAndRegionCode(String lpCode, String regionCode) {
        return transProvideNoDao.getTranNoNumberByLpCodeAndRegionCode(lpCode, regionCode, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public <T> String getObjectToJson(T t) {
        return JSONUtil.beanToJson(t);
    }

    @Override
    public <T> T getJsonToObject(String json, Class<T> t) {
        return JSONUtil.jsonToBean(json, t);
    }

    @Override
    public void saveMailNos(WhTransProvideNo mailNo) {
        transProvideNoDao.save(mailNo);
    }

    @Override
    public ExpressConfirmOrderQueue getExpressConfirmOrder(Long id) {
        return expressConfirmOrderQueueDao.getByPrimaryKey(id);
    }

    @Override
    public void saveLogAndDeleteQueue(ExpressConfirmOrderQueue q) {
        ExpressConfirmOrderQueueLog log = new ExpressConfirmOrderQueueLog();
        log.setCreateTime(q.getCreateTime());
        log.setExeCount(q.getExeCount());
        log.setFinishTime(new Date());
        log.setHeight(q.getHeight());
        log.setLength(q.getLength());
        log.setLpCode(q.getLpCode());
        log.setStaCode(q.getStaCode());
        log.setTransNo(q.getTransNo());
        log.setVolume(q.getVolume());
        log.setWeight(q.getWeight());
        log.setWidth(q.getWidth());
        expressConfirmOrderQueueLogDao.save(log);
        expressConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
    }

    @Override
    public void addErrorCount(Long id) {
        ExpressConfirmOrderQueue exOrder = expressConfirmOrderQueueDao.getByPrimaryKey(id);
        exOrder.setExeCount(exOrder.getExeCount() + 1);
    }

    @Override
    public List<Long> findStaByTransInfoLpCode() {
        return transInfoDao.findStaByTransInfoLpCode(new SingleColumnRowMapper<Long>(Long.class));
    }


    public Boolean orderConfrimToLogistics(ExpressConfirmOrderQueue queue) {
        if (queue != null) {
            boolean result = false;
            // 封装请求
            OrderConfirmContent confirmcontent = new OrderConfirmContent();
            confirmcontent.setCreatetime(queue.getCreateTime());
            confirmcontent.setHeight(new BigDecimal(queue.getHeight()));
            confirmcontent.setLeng(new BigDecimal(queue.getLength()));
            confirmcontent.setLpcode(queue.getLpCode());
            confirmcontent.setTrackingno(queue.getTransNo());
            confirmcontent.setWeight(new BigDecimal(queue.getWeight()));
            confirmcontent.setWidth(new BigDecimal(queue.getWidth()));
            confirmcontent.setOrdercode(queue.getStaCode());
            confirmcontent.setScsource("");
            confirmcontent.setType(1);
            OrderConfirmResponse response = null;
            try {
                response = OrderConfirmContentManager.mialOrderComfirm(confirmcontent, "WMS3");
                if (response != null && response.getStatus() == 1) {
                    // 出库确认同步物流服务成功，确认数据
                    /*
                     * ExpressConfirmOrderQueueLog queueLog = new ExpressConfirmOrderQueueLog();
                     * queueLog.setCreateTime(queue.getCreateTime()); queueLog.setFinishTime(new
                     * Date()); queueLog.setHeight(queue.getHeight());
                     * queueLog.setLength(queue.getLength()); queueLog.setLpCode(queue.getLpCode());
                     * queueLog.setStaCode(queue.getStaCode());
                     * queueLog.setTransNo(queue.getTransNo());
                     * queueLog.setVolume(queue.getVolume()); queueLog.setWeight(queue.getWeight());
                     * queueLog.setWidth(queue.getWidth());
                     * expressConfirmOrderQueueLogDao.save(queueLog);
                     * expressConfirmOrderQueueDao.deleteByPrimaryKey(queue.getId());
                     */
                    result = true;
                } else {
                    // 反馈失败，
                    result = false;
                }
            } catch (Exception e) {
                log.error("send expressConfirmOrderQueue is error" + queue.getStaCode());
                log.error("", e);
            }
            return result;
        }
        return false;
    }

    /**
     * 退仓发送物流商出库确认信息
     */
    @Override
    public void vmiRtoOrderConfrimToLogistics(Long staId) {
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            // 转店退仓
            if (sta != null && !sta.getType().equals(StockTransApplicationType.VMI_TRANSFER_RETURN)) {
                return;
            }
            StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(staId);
            if (sta == null && info == null) {
                log.error("vmiRtoOrderConfrimToLogistics========sta or StaDeliveryInfo is null:" + staId);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            // 如果有耗材信息
            List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(staId);
            Double length = 0D;
            Double width = 0D;
            Double higth = 0D;
            if (list != null && list.size() > 0) {
                for (StaAdditionalLine l : list) {
                    Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
                    if (sku != null && sku.getLength() != null) {
                        length = sku.getLength().divide(new BigDecimal(10)).doubleValue();
                        width = sku.getWidth().divide(new BigDecimal(10)).doubleValue();
                        higth = sku.getHeight().divide(new BigDecimal(10)).doubleValue();
                        break;
                    }
                }
            }
            List<Carton> cartons = cartonDao.findCartonByStaId(staId);
            if (cartons != null && cartons.size() > 0) {
                for (Carton carton : cartons) {
                    if (!(carton.getStatus().equals(CartonStatus.CREATED) && carton.getStatus().equals(CartonStatus.DOING))) {
                        if (info != null && (org.apache.commons.lang3.StringUtils.equals(info.getLpCode(), "SF") || org.apache.commons.lang3.StringUtils.equals(info.getLpCode(), "EMS")) && carton.getTrackingNo() != null) {
                            ExpressConfirmOrderQueue queue = new ExpressConfirmOrderQueue();
                            queue.setCreateTime(sta.getCreateTime());
                            queue.setLpCode(info.getLpCode());
                            queue.setTransNo(carton.getTrackingNo());
                            queue.setWeight(carton.getWeight() + "");
                            queue.setStaCode(sta.getCode());
                            queue.setLength(length + "");
                            queue.setWidth(width + "");
                            queue.setHeight(higth + "");
                            orderConfrimToLogistics(queue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("vmiRtoOrderConfrimToLogistics error!=========staId:" + staId, e);
        }
    }
}
