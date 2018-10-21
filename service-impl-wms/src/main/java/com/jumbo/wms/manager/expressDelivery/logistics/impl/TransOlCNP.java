package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.model.top.TopCainiaoCntmsMailnoGetResponse;
import cn.baozun.model.top.TopCainiaoCntmsMailnoGetResquest;
import cn.baozun.model.top.TopCainiaoCntmsMailnoGetResquest.CnTmsMailnoItem;
import cn.baozun.model.top.TopCainiaoCntmsMailnoGetResquest.CnTmsMailnoReceiverinfo;
import cn.baozun.model.top.TopCainiaoCntmsMailnoGetResquest.CnTmsMailnoSenderinfo;
import cn.baozun.rmi.top.TopRmiService;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CNPConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.CNPConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlCNP")
@Transactional
public class TransOlCNP extends BaseManagerImpl implements TransOlInterface {

    /**
     * 
     */
    private static final long serialVersionUID = 7747318449397241744L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private TopRmiService topRmiService;
    @Autowired
    private CNPConfirmOrderQueueDao cNPConfirmOrderQueueDao;
    @Autowired
    private BiChannelDao biChannelDao;

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public synchronized StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        BiChannel l = biChannelDao.getByCode(sta.getOwner());
        TopCainiaoCntmsMailnoGetResquest request = new TopCainiaoCntmsMailnoGetResquest();
        String tradeId = sta.getSlipCode1().replaceAll("-", "");
        request.setOrderCode(sta.getCode());
        request.setWmsShopId(l.getId());
        request.setTradeId(tradeId);// 平台订单号
        request.setOrderSource(sta.getOrderSourcePlatform());// 来源渠道
        request.setSolutionsCode("5000000011425"); // 菜鸟提供
        request.setPackageNo(1l); // 包裹序号
        CnTmsMailnoReceiverinfo recInfo = new CnTmsMailnoReceiverinfo();
        recInfo.setReceiverProvince(sd.getProvince());
        recInfo.setReceiverCity(sd.getCity());
        recInfo.setReceiverArea(sd.getDistrict());
        recInfo.setReceiverAddress(sd.getAddress());
        recInfo.setReceiverName(sd.getReceiver());
        if (StringUtils.hasLength(sd.getTelephone())) {
            recInfo.setReceiverPhone(sd.getTelephone());
        } else {
            recInfo.setReceiverMobile((sd.getMobile()));
        }
        request.setReceiverInfo(recInfo); // 收货人信息
        CnTmsMailnoSenderinfo sendInfo = new CnTmsMailnoSenderinfo();
        sendInfo.setSenderProvince(wh.getProvince());
        sendInfo.setSenderCity(wh.getCity());
        sendInfo.setSenderArea(wh.getDistrict());
        sendInfo.setSenderAddress(wh.getAddress());
        sendInfo.setSenderName(wh.getPic());
        if (StringUtils.hasLength(wh.getPicContact())) {
            sendInfo.setSenderPhone((wh.getPicContact()));
        } else {
            sendInfo.setSenderPhone((wh.getPhone()));
        }
        request.setSenderInfo(sendInfo); // 发货人信息
        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
        List<CnTmsMailnoItem> itemList = new ArrayList<CnTmsMailnoItem>();
        for (StaLine line : staLines) {
            CnTmsMailnoItem itme = new CnTmsMailnoItem();
            itme.setItemName(line.getSku().getName());
            itme.setItemQty(line.getQuantity());
            itemList.add(itme);
        }
        request.setItems(itemList); // 商品信息
        try {
            TopCainiaoCntmsMailnoGetResponse response = topRmiService.cainiaoCntmsMailnoGet(request);
            if (response != null) {
                if (!StringUtils.hasLength(response.getMailno())) {
                    log.error(sta.getId() + " getCnpTrackNo is error :" + response.getErrorCode() + "," + response.getErrorMsg());
                    throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
                } else {
                    sd.setExtTransOrderId(response.getMailno());
                    sd.setTrackingNo(response.getMailno());
                    sd.setTransBigWord(response.getShortAddress());
                    sd.setTmsCode(response.getTmsCode()); // 二级配送公司编码
                    sd.setLogisticsCode(response.getLogisticsCode());// 物流公司编码
                    sd.setPackageCenterCode(response.getPackageCenterCode()); // 集包地编码
                    sd.setPackageCenterName(response.getPackageCenterName()); // 集包地名称
                    log.info(staId + ":" + response.getMailno() + "," + response.getShortAddress() + "," + response.getLogisticsCode() + "," + response.getLogisticsName());
                }
            } else {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(staId + "matchingTransNo error: ", e);
            }
            throw e;
        }
        return sd;

    }


    @Override
    public synchronized StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        BiChannel l = biChannelDao.getByCode(sta.getOwner());
        TopCainiaoCntmsMailnoGetResquest request = new TopCainiaoCntmsMailnoGetResquest();
        String tradeId = sta.getSlipCode1().replaceAll("-", "");
        request.setOrderCode(sta.getCode());
        request.setWmsShopId(l.getId());
        request.setTradeId(tradeId);// 平台订单号
        request.setOrderSource(sta.getOrderSourcePlatform());// 来源渠道
        request.setSolutionsCode("5000000011425"); // 菜鸟提供
        request.setPackageNo(1l); // 包裹序号
        CnTmsMailnoReceiverinfo recInfo = new CnTmsMailnoReceiverinfo();
        recInfo.setReceiverProvince(sd.getProvince());
        recInfo.setReceiverCity(sd.getCity());
        recInfo.setReceiverArea(sd.getDistrict());
        recInfo.setReceiverAddress(sd.getAddress());
        recInfo.setReceiverName(sd.getReceiver());
        if (StringUtils.hasLength(sd.getTelephone())) {
            recInfo.setReceiverPhone(sd.getTelephone());
        } else {
            recInfo.setReceiverMobile((sd.getMobile()));
        }
        request.setReceiverInfo(recInfo); // 收货人信息
        CnTmsMailnoSenderinfo sendInfo = new CnTmsMailnoSenderinfo();
        sendInfo.setSenderProvince(wh.getProvince());
        sendInfo.setSenderCity(wh.getCity());
        sendInfo.setSenderArea(wh.getDistrict());
        sendInfo.setSenderAddress(wh.getAddress());
        sendInfo.setSenderName(wh.getPic());
        if (StringUtils.hasLength(wh.getPicContact())) {
            sendInfo.setSenderPhone((wh.getPicContact()));
        } else {
            sendInfo.setSenderPhone((wh.getPhone()));
        }
        request.setSenderInfo(sendInfo); // 发货人信息
        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
        List<CnTmsMailnoItem> itemList = new ArrayList<CnTmsMailnoItem>();
        for (StaLine line : staLines) {
            CnTmsMailnoItem itme = new CnTmsMailnoItem();
            itme.setItemName(line.getSku().getName());
            itme.setItemQty(line.getQuantity());
            itemList.add(itme);
        }
        request.setItems(itemList); // 商品信息
        try {
            TopCainiaoCntmsMailnoGetResponse response = topRmiService.cainiaoCntmsMailnoGet(request);
            if (response != null) {
                if (!StringUtils.hasLength(response.getMailno())) {
                    log.error(sta.getId() + " getCnpTrackNo is error :" + response.getErrorCode() + "," + response.getErrorMsg());
                    throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
                } else {
                    sd.setExtTransOrderId(response.getMailno());
                    sd.setTrackingNo(response.getMailno());
                    sd.setTransBigWord(response.getShortAddress());
                    sd.setTmsCode(response.getTmsCode()); // 二级配送公司编码
                    sd.setLogisticsCode(response.getLogisticsCode());// 物流公司编码
                    sd.setPackageCenterCode(response.getPackageCenterCode()); // 集包地编码
                    sd.setPackageCenterName(response.getPackageCenterName()); // 集包地名称
                    log.info(staId + ":" + response.getMailno() + "," + response.getShortAddress() + "," + response.getLogisticsCode() + "," + response.getLogisticsName());
                }
            } else {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(staId + "matchingTransNo error: ", e);
            }
            throw e;
        }
        return sd;

    }



    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        try {
            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            List<PackageInfo> piList = packageInfoDao.findByStaId(sta.getId());
            if (piList == null || piList.size() == 0) {
                return;
            }
            int no = 0;
            for (PackageInfo pi : piList) {
                no++;
                CNPConfirmOrderQueue q = new CNPConfirmOrderQueue();
                q.setOrderCode(sta.getCode());
                String tradeId = sta.getSlipCode1().replaceAll("-", "");
                q.setTradeId(tradeId);
                q.setOrderSource(sta.getOrderSourcePlatform());
                q.setTmsCode(di.getTmsCode());
                q.setStaId(sta.getId());
                q.setOuId(sta.getMainWarehouse().getId());
                q.setPackageNo(no);
                q.setPackageCount(piList.size());
                q.setExeCount(0l);
                q.setMailNo(pi.getTrackingNo());
                q.setCreateTime(new Date());
                BigDecimal b1 = new BigDecimal(100);
                long b2 = pi.getWeight().multiply(b1).longValue();
                q.setWeight(b2 + "");
                cNPConfirmOrderQueueDao.save(q);
            }
        } catch (Exception e) {
            log.error(sta.getId() + ": saveConfirmOrderQueue is error!", e);
        }
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        StaDeliveryInfo sd = new StaDeliveryInfo();
        PackageInfo info = packageInfoDao.getByPrimaryKey(packId);
        sd = null;
        if (info != null) {
            sd = info.getStaDeliveryInfo();// 根据包裹获取物流信息
            if (sd != null) {
                // stab = staDao.getByPrimaryKey(sd.getId());// 根据物流信息获取作业单
            } else {
                throw new BusinessException(ErrorCode.ERROR_DELIINFO_ISNULL);
            }
        }

        StockTransApplication sta = staDao.getByPrimaryKey(sd.getId());
        List<PackageInfo> piList = packageInfoDao.findByStaId(sta.getId());
        Integer pgNo = piList.size() + 1;
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        BiChannel l = biChannelDao.getByCode(sta.getOwner());
        TopCainiaoCntmsMailnoGetResquest request = new TopCainiaoCntmsMailnoGetResquest();
        String tradeId = sta.getSlipCode1().replaceAll("-", "");
        request.setOrderCode(sta.getCode());
        request.setWmsShopId(l.getId());
        request.setTradeId(tradeId);// 平台订单号
        request.setOrderSource(sta.getOrderSourcePlatform());// 来源渠道
        request.setSolutionsCode("5000000011425"); // 菜鸟提供
        request.setPackageNo(pgNo.longValue()); // 包裹序号
        CnTmsMailnoReceiverinfo recInfo = new CnTmsMailnoReceiverinfo();
        recInfo.setReceiverProvince(sd.getProvince());
        recInfo.setReceiverCity(sd.getCity());
        recInfo.setReceiverArea(sd.getDistrict());
        recInfo.setReceiverAddress(sd.getAddress());
        recInfo.setReceiverName(sd.getReceiver());
        if (StringUtils.hasLength(sd.getTelephone())) {
            recInfo.setReceiverPhone(sd.getTelephone());
        } else {
            recInfo.setReceiverMobile((sd.getMobile()));
        }
        request.setReceiverInfo(recInfo); // 收货人信息
        CnTmsMailnoSenderinfo sendInfo = new CnTmsMailnoSenderinfo();
        sendInfo.setSenderProvince(wh.getProvince());
        sendInfo.setSenderCity(wh.getCity());
        sendInfo.setSenderArea(wh.getDistrict());
        sendInfo.setSenderAddress(wh.getAddress());
        sendInfo.setSenderName(wh.getPic());
        if (StringUtils.hasLength(wh.getPicContact())) {
            sendInfo.setSenderPhone((wh.getPicContact()));
        } else {
            sendInfo.setSenderPhone((wh.getPhone()));
        }
        request.setSenderInfo(sendInfo); // 发货人信息
        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
        List<CnTmsMailnoItem> itemList = new ArrayList<CnTmsMailnoItem>();
        for (StaLine line : staLines) {
            CnTmsMailnoItem itme = new CnTmsMailnoItem();
            itme.setItemName(line.getSku().getName());
            itme.setItemQty(line.getQuantity());
            itemList.add(itme);
        }
        request.setItems(itemList); // 商品信息
        try {
            TopCainiaoCntmsMailnoGetResponse response = topRmiService.cainiaoCntmsMailnoGet(request);
            if (response != null) {
                if (!StringUtils.hasLength(response.getMailno())) {
                    log.error(sta.getId() + " getCnpTrackNoAgain is error :" + response.getErrorCode() + "," + response.getErrorMsg());
                    throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
                } else {
                    info.setTrackingNo(response.getMailno());// 运单号更新到包裹信息
                    log.info(sta.getId() + ":" + response.getMailno() + "," + response.getShortAddress() + "," + response.getLogisticsCode() + "," + response.getLogisticsName());
                }
            } else {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
        } catch (Exception e) {
            log.error(sta.getId() + "getCnpTrackNoAgain error: ", e);
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        return sd;
    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        return null;
    }

    @Override
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {

    }
}
