package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutAdditionalLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.KerryConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.kerry.KerryOrderClient;
import com.jumbo.webservice.kerry.KerryOrderCreateResponse;
import com.jumbo.webservice.kerry.KerryOrderItem;
import com.jumbo.webservice.kerry.KerryOrderRequest;
import com.jumbo.webservice.kerry.KerryOrderResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutAdditionalLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.KerryConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlKerry")
@Transactional
public class TransOlKerry extends BaseManagerImpl implements TransOlInterface {
    
    private static final long serialVersionUID = 3903697379580280956L;
    
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private KerryConfirmOrderQueueDao kerryConfirmOrderQueueDao;
    @Autowired
    private MsgRtnOutAdditionalLineDao msgRtnOutAdditionalLineDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private SkuDao skuDao;
    
    /**
     * 线下包裹获取运单号
     */
    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        return null;
    }
    
    /**
     * 匹配作业单运单号
     */
    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId())) {
            return sd;
        }
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        // 推送订单传输实体
        KerryOrderRequest order = getOrderRequest(sta,sd, wh, shop);
        String returnJson = KerryOrderClient.sendOrderToKerry(order,  KERRY_CREATEORDER_URL, KERRY_CLIENTID, KERRY_SECRET);
        KerryOrderResponse response = JSONUtil.jsonToBean(returnJson, KerryOrderResponse.class);
        if (response == null || response.getIsSuccess() == null) {
            throw new BusinessException(ErrorCode.KERRY_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        } else if (response.getIsSuccess()) {
            KerryOrderCreateResponse res = response.getResponse();
            if (res != null && !StringUtil.isEmpty(res.getDeliveryNo())) {
                sd.setTrackingNo(res.getDeliveryNo());
                sd.setExtTransOrderId(res.getOrderNo());
                sd.setTransCityCode(res.getDestinationiStation());
            } else {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
            }
        } else {
            throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getMessage()});
        }
        return sd;
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        return null;
    }
    
    /**
     * 出库记录订单回传信息
     */
    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        MsgRtnOutbound rtn = msgRtnOutboundDao.findByStaCode(sta.getCode());
        List<MsgRtnOutAdditionalLine> list = msgRtnOutAdditionalLineDao.getLineListByMsgRtnOutbound(rtn.getId());
        if (list == null) {
            list = new ArrayList<MsgRtnOutAdditionalLine>();
        }
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        }
        KerryConfirmOrderQueue kerryInfo = new KerryConfirmOrderQueue();
        kerryInfo.setCreateTime(new Date());
        kerryInfo.setExeCount(0L);
        kerryInfo.setStaCode(sta.getCode());
        kerryInfo.setTransNo(sd.getTrackingNo());
        kerryInfo.setType(1);
        DecimalFormat df = new DecimalFormat("0.00");
        kerryInfo.setWeight(df.format(rtn.getWeight()));
        boolean isNotInfo = true;
        if (list.size() > 0) {
            for (MsgRtnOutAdditionalLine l : list) {
                Sku sku = skuDao.getSkuByBarcode(l.getBarCode());
                if (sku != null) {
                    BigDecimal height = sku.getHeight().divide(new BigDecimal(10));
                    BigDecimal length = sku.getLength().divide(new BigDecimal(10));
                    BigDecimal whight = sku.getWidth().divide(new BigDecimal(10));
                    BigDecimal volume = height.multiply(length).multiply(whight).divide(new BigDecimal(1000*1000));
                    kerryInfo.setHeight(df.format(height));
                    kerryInfo.setLength(df.format(length));
                    kerryInfo.setWhight(df.format(whight));
                    kerryInfo.setVolume(new DecimalFormat("0.000").format(volume));
                    isNotInfo = false;
                    break;
                }
            }
        }
        if (isNotInfo) {
            kerryInfo.setHeight("0");
            kerryInfo.setLength("0");
            kerryInfo.setWhight("0");
            kerryInfo.setVolume("0");
        }
        kerryConfirmOrderQueueDao.save(kerryInfo);
    }
    
    private KerryOrderRequest getOrderRequest(StockTransApplication sta, StaDeliveryInfo sd, Warehouse wh, BiChannel shop){
        KerryOrderRequest order = new KerryOrderRequest();
        KerryOrderItem item = new KerryOrderItem();
        item.setOrderNo(sta.getCode());
        item.setCargoExpl("服饰"); // 货物描述 默认为服饰
        // item.setVolume(null); // 体积
        item.setQuantity(new BigDecimal(1));
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
        // item.setCodAmt(sd.getTransferFee());
        order.setRequest(item);
        return order;
    }
    
    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }
    
    @Override
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {
        
    }

    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        return null;
    }

}
