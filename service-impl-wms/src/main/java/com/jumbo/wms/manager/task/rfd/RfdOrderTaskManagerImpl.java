package com.jumbo.wms.manager.task.rfd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.RfdConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.RfdConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.webservice.rfd.RfdCreateOrderDetails;
import com.jumbo.webservice.rfd.RfdCreateOrderRequest;
import com.jumbo.webservice.rfd.RfdCreateOrderResDetail;
import com.jumbo.webservice.rfd.RfdCreateOrderResponse;
import com.jumbo.webservice.rfd.RfdOrderClient;
import com.jumbo.webservice.rfd.RfdOutStoreRequest;
import com.jumbo.webservice.rfd.RfdOutStoreResponse;
import com.jumbo.webservice.rfd.RfdOutStoreResponse.RfdOutStoreResDetail;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.RfdConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.RfdConfirmOrderQueueLog;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
@Service("rfdOrderTaskManager")
public class RfdOrderTaskManagerImpl extends BaseManagerImpl implements RfdOrderTaskManager {

    private static final long serialVersionUID = 7013015851896014243L;

    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private RfdConfirmOrderQueueDao rfdConfirmOrderQueueDao;
    @Autowired
    private RfdConfirmOrderQueueLogDao rfdConfirmOrderQueueLogDao;
    @Autowired
    private BiChannelDao companyShopDao;

    @Override
    public void rfdInterfaceAllWarehouse() {
        List<Long> idList = warehouseDao.getAllRFDWarehouse(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : idList) {
            try {
                rfdIntefaceByWarehouse(id);
            } catch (Exception e) {
                log.error("rfdIntefaceByWarehouse error", e);
            }
        }
    }

    private void rfdIntefaceByWarehouse(Long whId) {
        log.debug("=======RFD match TransNo====" + whId + "=======");
        Warehouse wh = warehouseDao.getRFDWarehouseByOuId(whId, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        Boolean flag = true;
        while (flag) {
            List<String> lpList = new ArrayList<String>();
            lpList.add("RF");
            List<Long> staList = staDao.findStaByOuIdAndStatusForRFD(whId, lpList, new SingleColumnRowMapper<Long>(Long.class));
            if (staList.size() < 30) {
                flag = false;
            }
            List<RfdCreateOrderRequest> orders = new ArrayList<RfdCreateOrderRequest>();
            for (Long id : staList) {
                StockTransApplication sta = staDao.getByPrimaryKey(id);
                List<StaLine> line = staLineDao.findByStaId(id);
                StaDeliveryInfo sd = sta.getStaDeliveryInfo();
                Warehouse warehouse = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                if (sd == null) {
                    sd = staDeliveryInfoDao.getByPrimaryKey(id);
                }
                RfdCreateOrderRequest order = getRequest(sta, sd, warehouse, shop, line);
                orders.add(order);
            }
            if (!orders.isEmpty()) {
                // 获取运单号
                String returnJson = RfdOrderClient.sendOrderToRfd(orders, RFD_CREATEORDER_URL, RFD_IDENTITY, RFD_SECRET);
                RfdCreateOrderResponse response = JSONUtil.jsonToBean(returnJson, RfdCreateOrderResponse.class);
                if (response == null || response.getIsSuccess() == null) {
                    throw new BusinessException(ErrorCode.RFD_INTERFACE_ERROR);
                } else if (response.getIsSuccess()) {
                    List<RfdCreateOrderResDetail> res = response.getResultData();
                    for (RfdCreateOrderResDetail detail : res) {
                        StockTransApplication sta = staDao.findStaByCode(detail.getFormCode());
                        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
                        if (detail.getWaybillNo() != null && detail.getWaybillNo() != 0) {
                            sd.setTrackingNo(detail.getWaybillNo().toString());
                            sd.setExtTransOrderId(detail.getFormCode());
                            sd.setTransCityCode(detail.getStationId() == null ? null : detail.getStationId().toString()); // 站点编号
                            sd.setTransBigWord(detail.getStationName());
                        }
                        if ("03".equals(detail.getResultCode())) {
                            // 记录获取运单号失败的单据
                            wareHouseManagerExecute.failedToGetTransno(sta.getId());
                            log.error("rfdIntefaceByWarehouse error code is : " + detail.getResultMessage());
                        }
                    }
                } else {
                    throw new BusinessException(ErrorCode.RFD_INTERFACE_ERROR);
                }
            }
        }
    }

    @Override
    public void exeRfdConfirmOrder(List<RfdConfirmOrderQueue> qList) {
        // 推送订单传输实体
        List<RfdOutStoreRequest> orders = new ArrayList<RfdOutStoreRequest>();
        for (RfdConfirmOrderQueue q : qList) {
            StockTransApplication sta = staDao.getByCode(q.getStaCode());
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            }
            RfdOutStoreRequest out = new RfdOutStoreRequest();
            out.setFormCode(q.getStaCode());
            out.setWaybillNo(Long.valueOf(q.getTransNo()));
            out.setPackageCount(1);
            out.setWeight(new BigDecimal(q.getWeight()));
            orders.add(out);
        }
        if (!orders.isEmpty()) {
            String returnJson = RfdOrderClient.sendRfdConfirmOrder(orders, RFD_CONFIRMORDER_URL, RFD_IDENTITY, RFD_SECRET);
            RfdOutStoreResponse response = JSONUtil.jsonToBean(returnJson, RfdOutStoreResponse.class);
            if (response == null || response.getIsSuccess() == null) {
                throw new BusinessException(ErrorCode.RFD_INTERFACE_ERROR);
            } else if (response.getIsSuccess()) {
                List<RfdOutStoreResDetail> res = response.getResultData();
                for (RfdOutStoreResDetail d : res) {
                    RfdConfirmOrderQueue q = rfdConfirmOrderQueueDao.findRfdQueueByStaCodeAndTransNo(d.getFormCode(), d.getWaybillNo().toString(), new BeanPropertyRowMapper<RfdConfirmOrderQueue>(RfdConfirmOrderQueue.class));
                    if (d.getIsSuccess() != null && d.getIsSuccess()) {
                        RfdConfirmOrderQueueLog log = new RfdConfirmOrderQueueLog();
                        log.setCreateTime(q.getCreateTime());
                        log.setExeCount(q.getExeCount());
                        log.setFinishTime(new Date());
                        log.setHeight(q.getHeight());
                        log.setLength(q.getLength());
                        log.setStaCode(q.getStaCode());
                        log.setTransNo(d.getWaybillNo().toString());
                        log.setWeight(q.getWeight());
                        log.setWhight(q.getWhight());
                        rfdConfirmOrderQueueLogDao.save(log);
                        rfdConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
                    } else {
                        q.setExeCount(q.getExeCount() + 1);
                        rfdConfirmOrderQueueDao.save(q);
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.RFD_INTERFACE_ERROR);
            }
        }
    }

    private RfdCreateOrderRequest getRequest(StockTransApplication sta, StaDeliveryInfo sd, Warehouse wh, BiChannel shop, List<StaLine> line) {
        RfdCreateOrderRequest order = new RfdCreateOrderRequest();
        order.setMerchantCode("rfd"); // 商家编号 如风达提供固定值
        order.setFormCode(sta.getCode());
        order.setToName(sd.getReceiver());
        order.setToProvince(sd.getProvince());
        order.setToCity(sd.getCity());
        order.setToArea(sd.getDistrict());
        order.setToAddress(sd.getAddress());
        order.setToPostCode(sd.getZipcode());
        if (StringUtils.hasText(sd.getMobile())) {
            order.setToMobile(sd.getMobile());
        } else if (StringUtils.hasText(sd.getTelephone())) {
            order.setToMobile(sd.getTelephone());
        }
        order.setToPhone(sd.getTelephone());
        order.setOrderType(0);
        order.setTotalAmount(sta.getOrderTotalActual());
        order.setPaidAmount(sta.getTotalActual());
        order.setReceiveAmount(sta.getTotalActual());
        order.setRefundAmount(new BigDecimal(0));
        order.setInsureAmount(sd.getInsuranceAmount());
        order.setWeight(sd.getWeight());
        order.setFromName(wh.getPic());
        order.setFromAddress(wh.getAddress());
        order.setFromProvince(wh.getProvince());
        order.setFromCity(wh.getCity());
        order.setFromArea(wh.getDistrict());
        order.setFromPostCode(wh.getZipcode());
        order.setFromMobile(wh.getPhone());
        order.setFromPhone(shop.getTelephone());
        order.setGoodsProperty(0);
        order.setPackageCount(1);
        order.setDistributionCode("rfd");
        order.setCashType((sd.getIsCodPos() != null && sd.getIsCodPos()) ? 2 : 1);
        List<RfdCreateOrderDetails> orderDetails = new ArrayList<RfdCreateOrderDetails>();
        if (line != null && line.size() > 0) {
            for (StaLine staLine : line) {
                Sku sku = staLine.getSku();
                RfdCreateOrderDetails detail = new RfdCreateOrderDetails();
                detail.setProductName(sku.getName());
                detail.setCount(staLine.getQuantity().intValue());
                detail.setUnit("件");
                detail.setSellPrice(staLine.getTotalActual());
                detail.setProductCode(sku.getBarCode());
                orderDetails.add(detail);
            }
        }
        order.setOrderDetails(orderDetails);
        return order;
    }
}
