package com.jumbo.wms.manager.task.kerry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.KerryConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.KerryConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.webservice.kerry.KerryOrderCancelItem;
import com.jumbo.webservice.kerry.KerryOrderCancelRequest;
import com.jumbo.webservice.kerry.KerryOrderClient;
import com.jumbo.webservice.kerry.KerryOrderItem;
import com.jumbo.webservice.kerry.KerryOrderRequest;
import com.jumbo.webservice.kerry.KerryOrderResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.KerryConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.KerryConfirmOrderQueueLog;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
@Service("kerryOrderTaskManager")
public class KerryOrderTaskManagerImpl extends BaseManagerImpl implements KerryOrderTaskManager {

    private static final long serialVersionUID = -3940592068467700781L;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private KerryConfirmOrderQueueDao kerryConfirmOrderQueueDao;
    @Autowired
    private KerryConfirmOrderQueueLogDao kerryConfirmOrderQueueLogDao;
    @Autowired
    private BiChannelDao companyShopDao;

    @Override
    public void exeKerryConfirmOrder(KerryConfirmOrderQueue q) {
        StockTransApplication sta = staDao.getByCode(q.getStaCode());
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        }
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        // 推送订单传输实体
        KerryOrderRequest order = new KerryOrderRequest();
        KerryOrderItem item = new KerryOrderItem();
        item.setOrderNo(sta.getCode());
        item.setDeliveryNo(sd.getTrackingNo());
        item.setQuantity(new BigDecimal(1));
        item.setCargoExpl("服饰"); // 货物描述 默认是服饰
        item.setVolume(new BigDecimal(q.getVolume())); // 体积
        item.setGrossWeight(sd.getWeight());
        item.setMemo(sta.getMemo());
        item.setSenderName(wh.getPic());
        item.setSenderPostcode(wh.getZipcode());
        item.setSenderProvince(wh.getProvince());
        item.setSenderCity(wh.getCity());
        item.setSenderDistrict(wh.getDistrict());
        item.setSenderAddress(wh.getAddress());
        item.setSenderPhone(shop.getTelephone());
        item.setSenderMobile(shop.getTelephone());
        item.setReceiverName(sd.getReceiver());
        item.setReceiverProvince(sd.getProvince());
        item.setReceiverCity(sd.getCity());
        item.setReceiverDistrict(sd.getDistrict());
        item.setReceiverAddress(sd.getAddress());
        if (StringUtils.hasText(sd.getMobile())) {
            item.setReceiverPhone(sd.getMobile());
        } else if (StringUtils.hasText(sd.getTelephone())) {
            item.setReceiverPhone(sd.getTelephone());
        }
        if (StringUtils.hasText(sd.getTelephone())) {
            item.setReceiverTel(sd.getTelephone());
        } else if (StringUtils.hasText(sd.getMobile())) {
            item.setReceiverTel(sd.getMobile());
        }
        item.setReceiverPostcode(sd.getZipcode());
        item.setInsurePrice(sd.getInsuranceAmount());
        item.setTranprice(String.valueOf(sta.getTotalActual()));
        item.setPayType("1"); // 预付
        order.setRequest(item);
        String returnJson = KerryOrderClient.sendOrderToKerry(order, KERRY_HAWBCREATE_URL, KERRY_CLIENTID, KERRY_SECRET);
        KerryOrderResponse response = JSONUtil.jsonToBean(returnJson, KerryOrderResponse.class);
        if (response == null || response.getIsSuccess() == null) {
            throw new BusinessException(ErrorCode.KERRY_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        } else if (response.getIsSuccess()) {
            KerryConfirmOrderQueueLog log = getLogByQueue(q);
            kerryConfirmOrderQueueLogDao.save(log);
            kerryConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
        } else {
            q.setExeCount(q.getExeCount() + 1);
            kerryConfirmOrderQueueDao.save(q);
        }
    }

    @Override
    public void exeKerryCancelOrder(KerryConfirmOrderQueue q) {
        KerryOrderCancelRequest request = new KerryOrderCancelRequest();
        KerryOrderCancelItem item = new KerryOrderCancelItem();
        item.setOrderNo(q.getStaCode());
        item.setDeliveryNo(q.getTransNo());
        request.setRequest(item);
        String returnJson = KerryOrderClient.sendCancelToKerry(request, KERRY_CANCELORDER_URL, KERRY_CLIENTID, KERRY_SECRET);
        KerryOrderResponse response = JSONUtil.jsonToBean(returnJson, KerryOrderResponse.class);
        if (response == null || response.getIsSuccess() == null) {
            throw new BusinessException(ErrorCode.KERRY_INTERFACE_ERROR, new Object[] {q.getStaCode(), ""});
        } else if (response.getIsSuccess()) {
            KerryConfirmOrderQueueLog log = getLogByQueue(q);
            kerryConfirmOrderQueueLogDao.save(log);
            kerryConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
        } else {
            q.setExeCount(q.getExeCount() + 1);
            kerryConfirmOrderQueueDao.save(q);
        }
    }

    @Override
    public void saveKerryCancelOrder(List<Long> staList) {
        for (Long staId : staList) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            }
            KerryConfirmOrderQueue kerryInfo = new KerryConfirmOrderQueue();
            kerryInfo.setCreateTime(new Date());
            kerryInfo.setExeCount(0L);
            kerryInfo.setStaCode(sta.getCode());
            kerryInfo.setTransNo(sd.getTrackingNo());
            kerryInfo.setType(2);
            kerryConfirmOrderQueueDao.save(kerryInfo);
        }
    }

    private KerryConfirmOrderQueueLog getLogByQueue(KerryConfirmOrderQueue q) {
        KerryConfirmOrderQueueLog log = new KerryConfirmOrderQueueLog();
        log.setCreateTime(q.getCreateTime());
        log.setExeCount(q.getExeCount());
        log.setFinishTime(new Date());
        log.setHeight(q.getHeight());
        log.setLength(q.getLength());
        log.setStaCode(q.getStaCode());
        log.setTransNo(q.getTransNo());
        log.setType(q.getType());
        log.setWeight(q.getWeight());
        log.setWhight(q.getWhight());
        log.setVolume(q.getVolume());
        return log;
    }
}
