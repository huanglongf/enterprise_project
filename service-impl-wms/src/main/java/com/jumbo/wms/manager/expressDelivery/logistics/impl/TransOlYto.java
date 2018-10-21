package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.webservice.yto.YtoOrderClient;
import com.jumbo.webservice.yto.command.UploadOrdersItemRequest;
import com.jumbo.webservice.yto.command.UploadOrdersItemsRequest;
import com.jumbo.webservice.yto.command.UploadOrdersReceiverRequest;
import com.jumbo.webservice.yto.command.UploadOrdersRequest;
import com.jumbo.webservice.yto.command.UploadOrdersResponse;
import com.jumbo.webservice.yto.command.UploadOrdersSenderRequest;
import com.jumbo.webservice.yto.command.YtoOrder;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.mongodb.MongoDBManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WhTransOlConfigManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mongodb.MongoDBYtoBigWord;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransOlConfig;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlYto")
@Transactional
public class TransOlYto extends BaseManagerImpl implements TransOlInterface {
    private static final long serialVersionUID = -4986698110441076332L;
    private static final String CARGO = "宝尊商品";

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
    private WhTransOlConfigManager whTransOlConfigManager;
    @Autowired
    private MongoDBManager mongoDBManager;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private TransAliWaybill transAliWaybill;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    private TimeHashMap<String, WhTransOlConfig> ytoAccountInfo = new TimeHashMap<String, WhTransOlConfig>();


    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        StaDeliveryInfo info = new StaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(whOuId);
        WhTransOlConfig wtoc = ytoAccountInfo.get(Transportator.YTO + wh.getDeparture());
        if (wtoc == null) {
            wtoc = whTransOlConfigManager.findTransOlConfig(Transportator.YTO, wh.getDeparture());
            ytoAccountInfo.put(Transportator.YTO + wh.getDeparture(), wtoc);
        }
        UploadOrdersRequest request = new UploadOrdersRequest();
        request.setClientID(wtoc.getUsername());// 电商标识（K10101010）
        request.setLogisticProviderID(Transportator.YTO);
        request.setCustomerId(wtoc.getUsername());// 客户标识
        // clientID +数字+六位随机数字
        request.setTxLogisticID(wtoc.getUsername() + sequenceManager.getCode(YtoOrder.class.getName(), new YtoOrder()));// 物流订单号LP81132426447;
        request.setTotalServiceFee(0.0);
        request.setCodSplitFee(0.0);
        request.setOrderType(1);
        // 服务类型(1-上门揽收, 2-次日达 4-次晨达 8-当日达,0-自己联系)。默认为0
        request.setServiceType(0L);// 服务类型--待定
        // 此处等待确定是否需要发送发货人信息
        UploadOrdersSenderRequest sRequest = new UploadOrdersSenderRequest();
        sRequest.setName(wh.getPic());
        sRequest.setPostCode(wh.getZipcode());
        sRequest.setPhone(wh.getPhone());
        sRequest.setMobile(wh.getPhone());
        sRequest.setProv(wh.getProvince());
        sRequest.setCity(wh.getCity());
        sRequest.setAddress(wh.getAddress());
        request.setSender(sRequest);
        UploadOrdersReceiverRequest rRequest = new UploadOrdersReceiverRequest();
        rRequest.setName(order.getReceiver());
        rRequest.setPostCode("221000");// 用户邮编
        if (StringUtils.hasText(order.getReceiverTel())) {
            rRequest.setPhone(order.getReceiverTel());
        } else if (StringUtils.hasText(order.getReceiverTel())) {
            rRequest.setPhone(order.getReceiverTel());
        }
        rRequest.setProv(order.getReceiverProvince());
        if (StringUtils.hasText(order.getReceiverArea())) {
            rRequest.setCity(order.getReceiverCity() + "," + order.getReceiverArea());
        } else {
            rRequest.setCity(order.getReceiverCity());
        }
        rRequest.setAddress(order.getReceiverAddress());
        request.setReceiver(rRequest);
        request.setInsuranceValue(0.0);

        // List<StaLine> lineList = staLineDao.findByStaId(staId);
        UploadOrdersItemsRequest itemslist = new UploadOrdersItemsRequest();
        List<UploadOrdersItemRequest> itemList = new ArrayList<UploadOrdersItemRequest>();
        UploadOrdersItemRequest item = new UploadOrdersItemRequest();
        item.setItemName(CARGO);
        item.setNumber(1);
        itemList.add(item);
        // List<UploadOrdersItemRequest> itemList = new ArrayList<UploadOrdersItemRequest>();
        // for (StaLine line : lineList) {
        // UploadOrdersItemRequest item = new UploadOrdersItemRequest();
        // item.setItemName(CARGO);
        // if (line.getUnitPrice() != null) {
        // item.setItemValue(line.getUnitPrice().doubleValue());
        // }
        // item.setNumber(line.getQuantity().intValue());
        // itemList.add(item);
        // }
        itemslist.setItem(itemList);
        request.setItems(itemslist);
        request.setSpecial(0);
        request.setRemark("");

        try {
            UploadOrdersResponse response = YtoOrderClient.sendOutboundOrderToYto(request, YTO_UPLOAD_ORDERS_URL, wtoc.getPwd(), wtoc.getUsername());
            System.out.println(response);
            if (response == null || response.getSuccess() == null) {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {order.getCode(), ""});
            } else if (response.getSuccess() != null && "true".equalsIgnoreCase(response.getSuccess())) {
                UploadOrdersRequest uor = (UploadOrdersRequest) MarshallerUtil.buildJaxb(UploadOrdersRequest.class, MarshallerUtil.buildJaxb(response.getOrderMessage()));
                if (response != null && !StringUtil.isEmpty(uor.getMailNo())) {
                    info.setLastModifyTime(new Date());
                    info.setTrackingNo(uor.getMailNo());
                    info.setTransBigWord(uor.getBigPen());// 大头笔
                    info.setExtTransOrderId(uor.getTxLogisticID());
                    // sd.setTrackingNo(uor.getMailNo());
                    // sd.setTransBigWord(uor.getBigPen());// 大头笔
                    // sd.setExtTransOrderId(uor.getTxLogisticID());
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {order.getCode(), response.getReason()});
                }
            } else if (response.getSuccess() != null && "false".equalsIgnoreCase(response.getSuccess())) {
                String reason = StringUtils.trimWhitespace(response.getReason()) + "";
                if (reason.contains(YtoOrderClient.PROVIDER_NO_TRANS_REASON) || reason.contains(YtoOrderClient.ILLEGAL_DATA_DIGEST_RESON)) {
                    throw new BusinessException(ErrorCode.PROVIDER_NO_TRANS_OR_ERROR, new Object[] {order.getCode(), response.getReason()});
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {order.getCode(), response.getReason()});
                }
            } else {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {order.getCode(), response.getReason()});
            }
        } catch (BusinessException e) {
            throw e;
        }
        try {
            MongoDBYtoBigWord ytoBigWord = mongoDBManager.matchingPackNo(info);
            if (null != ytoBigWord) {
                info.setTransCityCode(ytoBigWord.getPackNo());// 打包编码
            }
        } catch (Exception e) {}
        return info;
    }

    /**
     * YTO匹配运单号
     */
    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // Map<String, String> option =
        // chooseOptionManager.getOptionByCategoryCode(Constants.ALI_WAYBILL);

        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getAliPackageNo())) {
            return sd;
        }
        if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId())) {
            return sd;
        }
        // BiChannel bc = biChannelDao.getByCode(sta.getOwner());
        // if (option != null && bc.getIsCaiNiaoYto() == true) {
        // String onOff = option.get(Transportator.YTO);
        // String source = option.get(sta.getOrderSourcePlatform());
        // if (onOff != null && Constants.ALI_WAYBILL_SWITCH_ON.equals(onOff) && source != null &&
        // Constants.ALI_WAYBILL_SWITCH_ON.equals(source)) {
        // StaDeliveryInfo s = transAliWaybill.getTransNoByStaId(staId);
        // if (s != null && !StringUtil.isEmpty(s.getTrackingNo())) {
        // return s;
        // }
        // }
        // }
        // BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        WhTransOlConfig wtoc = ytoAccountInfo.get(Transportator.YTO + wh.getDeparture());
        if (wtoc == null) {
            wtoc = whTransOlConfigManager.findTransOlConfig(Transportator.YTO, wh.getDeparture());
            ytoAccountInfo.put(Transportator.YTO + wh.getDeparture(), wtoc);
        }

        UploadOrdersRequest request = new UploadOrdersRequest();
        request.setClientID(wtoc.getUsername());// 电商标识（K10101010）
        request.setLogisticProviderID(Transportator.YTO);
        request.setCustomerId(wtoc.getUsername());// 客户标识
        // clientID +数字+六位随机数字
        request.setTxLogisticID(wtoc.getUsername() + sequenceManager.getCode(YtoOrder.class.getName(), new YtoOrder()));// 物流订单号LP81132426447;
        request.setTotalServiceFee(0.0);
        request.setCodSplitFee(0.0);
        // 订单类型(0-COD,1-普通订单,3-退货单)(退货单是入操作，此处是出库信息传给YTO)
        if (sd.getIsCod() != null && sd.getIsCod()) {
            request.setOrderType(0);
            request.setItemsValue(sta.getTotalActual().doubleValue());// 有代收金额的订单在以上基础上必须添加代收金额的实际数值
            request.setAgencyFund(sta.getTotalActual().doubleValue());// 有代收金额的订单在以上基础上必须添加代收金额的实际数值
        } else {
            request.setOrderType(1);
        }
        // 服务类型(1-上门揽收, 2-次日达 4-次晨达 8-当日达,0-自己联系)。默认为0
        request.setServiceType(0L);// 服务类型--待定
        // 此处等待确定是否需要发送发货人信息
        UploadOrdersSenderRequest sRequest = new UploadOrdersSenderRequest();
        sRequest.setName(wh.getPic());
        sRequest.setPostCode(wh.getZipcode());
        sRequest.setPhone(wh.getPhone());
        sRequest.setMobile(wh.getPhone());
        sRequest.setProv(wh.getProvince());
        sRequest.setCity(wh.getCity());
        sRequest.setAddress(wh.getAddress());
        request.setSender(sRequest);
        UploadOrdersReceiverRequest rRequest = new UploadOrdersReceiverRequest();
        rRequest.setName(sd.getReceiver());
        rRequest.setPostCode(sd.getZipcode());
        if (StringUtils.hasText(sd.getMobile())) {
            rRequest.setPhone(sd.getMobile());
        } else if (StringUtils.hasText(sd.getTelephone())) {
            rRequest.setPhone(sd.getTelephone());
        }
        rRequest.setProv(sd.getProvince());
        if (StringUtils.hasText(sd.getDistrict())) {
            rRequest.setCity(sd.getCity() + "," + sd.getDistrict());
        } else {
            rRequest.setCity(sd.getCity());
        }
        rRequest.setAddress(sd.getAddress());
        request.setReceiver(rRequest);
        request.setInsuranceValue(0.0);

        List<StaLine> lineList = staLineDao.findByStaId(staId);
        UploadOrdersItemsRequest itemslist = new UploadOrdersItemsRequest();
        List<UploadOrdersItemRequest> itemList = new ArrayList<UploadOrdersItemRequest>();
        for (StaLine line : lineList) {
            UploadOrdersItemRequest item = new UploadOrdersItemRequest();
            item.setItemName(CARGO);
            if (line.getUnitPrice() != null) {
                item.setItemValue(line.getUnitPrice().doubleValue());
            }
            item.setNumber(line.getQuantity().intValue());
            itemList.add(item);
        }
        itemslist.setItem(itemList);
        request.setItems(itemslist);
        request.setSpecial(0);
        request.setRemark(sta.getMemo());
        try {
            UploadOrdersResponse response = YtoOrderClient.sendOutboundOrderToYto(request, YTO_UPLOAD_ORDERS_URL, wtoc.getPwd(), wtoc.getUsername());
            if (response == null || response.getSuccess() == null) {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
            } else if (response.getSuccess() != null && "true".equalsIgnoreCase(response.getSuccess())) {
                UploadOrdersRequest uor = (UploadOrdersRequest) MarshallerUtil.buildJaxb(UploadOrdersRequest.class, MarshallerUtil.buildJaxb(response.getOrderMessage()));
                if (response != null && !StringUtil.isEmpty(uor.getMailNo())) {
                    sd.setTrackingNo(uor.getMailNo());
                    sd.setLastModifyTime(new Date());
                    sd.setTransBigWord(uor.getBigPen());// 大头笔
                    sd.setExtTransOrderId(uor.getTxLogisticID());
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getReason()});
                }
            } else if (response.getSuccess() != null && "false".equalsIgnoreCase(response.getSuccess())) {
                String reason = StringUtils.trimWhitespace(response.getReason()) + "";
                if (reason.contains(YtoOrderClient.PROVIDER_NO_TRANS_REASON) || reason.contains(YtoOrderClient.ILLEGAL_DATA_DIGEST_RESON)) {
                    throw new BusinessException(ErrorCode.PROVIDER_NO_TRANS_OR_ERROR, new Object[] {sta.getCode(), response.getReason()});
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getReason()});
                }
            } else {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {sta.getCode(), response.getReason()});
            }
        } catch (BusinessException e) {
            throw e;
        }
        try {
            MongoDBYtoBigWord ytoBigWord = mongoDBManager.matchingPackNo(sd);
            if (null != ytoBigWord) {
                sd.setTransCityCode(ytoBigWord.getPackNo());// 打包编码
            }
        } catch (Exception e) {}
        return sd;
    }


    /**
     * YTO匹配运单号
     */
    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // Map<String, String> option =
        // chooseOptionManager.getOptionByCategoryCode(Constants.ALI_WAYBILL);

        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getAliPackageNo())) {
            return sd;
        }
        if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId())) {
            return sd;
        }

        // if (option != null) {
        // String onOff = option.get(Transportator.YTO);
        // String source = option.get(sta.getOrderSourcePlatform());
        // if (onOff != null && Constants.ALI_WAYBILL_SWITCH_ON.equals(onOff) && source != null &&
        // Constants.ALI_WAYBILL_SWITCH_ON.equals(source)) {
        // StaDeliveryInfo s = transAliWaybill.getTransNoByStaId(staId);
        // if (s != null && !StringUtil.isEmpty(s.getTrackingNo())) {
        // return s;
        // }
        // }
        // }
        // BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        WhTransOlConfig wtoc = ytoAccountInfo.get(Transportator.YTO + wh.getDeparture());
        if (wtoc == null) {
            wtoc = whTransOlConfigManager.findTransOlConfig(Transportator.YTO, wh.getDeparture());
            ytoAccountInfo.put(Transportator.YTO + wh.getDeparture(), wtoc);
        }

        UploadOrdersRequest request = new UploadOrdersRequest();
        request.setClientID(wtoc.getUsername());// 电商标识（K10101010）
        request.setLogisticProviderID(Transportator.YTO);
        request.setCustomerId(wtoc.getUsername());// 客户标识
        // clientID +数字+六位随机数字
        request.setTxLogisticID(wtoc.getUsername() + sequenceManager.getCode(YtoOrder.class.getName(), new YtoOrder()));// 物流订单号LP81132426447;
        request.setTotalServiceFee(0.0);
        request.setCodSplitFee(0.0);
        // 订单类型(0-COD,1-普通订单,3-退货单)(退货单是入操作，此处是出库信息传给YTO)
        if (sd.getIsCod() != null && sd.getIsCod()) {
            request.setOrderType(0);
            request.setItemsValue(sta.getTotalActual().doubleValue());// 有代收金额的订单在以上基础上必须添加代收金额的实际数值
            request.setAgencyFund(sta.getTotalActual().doubleValue());// 有代收金额的订单在以上基础上必须添加代收金额的实际数值
        } else {
            request.setOrderType(1);
        }
        // 服务类型(1-上门揽收, 2-次日达 4-次晨达 8-当日达,0-自己联系)。默认为0
        request.setServiceType(0L);// 服务类型--待定
        // 此处等待确定是否需要发送发货人信息
        UploadOrdersSenderRequest sRequest = new UploadOrdersSenderRequest();
        sRequest.setName(wh.getPic());
        sRequest.setPostCode(wh.getZipcode());
        sRequest.setPhone(wh.getPhone());
        sRequest.setMobile(wh.getPhone());
        sRequest.setProv(wh.getProvince());
        sRequest.setCity(wh.getCity());
        sRequest.setAddress(wh.getAddress());
        request.setSender(sRequest);
        UploadOrdersReceiverRequest rRequest = new UploadOrdersReceiverRequest();
        rRequest.setName(sd.getReceiver());
        rRequest.setPostCode(sd.getZipcode());
        if (StringUtils.hasText(sd.getMobile())) {
            rRequest.setPhone(sd.getMobile());
        } else if (StringUtils.hasText(sd.getTelephone())) {
            rRequest.setPhone(sd.getTelephone());
        }
        rRequest.setProv(sd.getProvince());
        if (StringUtils.hasText(sd.getDistrict())) {
            rRequest.setCity(sd.getCity() + "," + sd.getDistrict());
        } else {
            rRequest.setCity(sd.getCity());
        }
        rRequest.setAddress(sd.getAddress());
        request.setReceiver(rRequest);
        request.setInsuranceValue(0.0);

        List<StaLine> lineList = staLineDao.findByStaId(staId);
        UploadOrdersItemsRequest itemslist = new UploadOrdersItemsRequest();
        List<UploadOrdersItemRequest> itemList = new ArrayList<UploadOrdersItemRequest>();
        for (StaLine line : lineList) {
            UploadOrdersItemRequest item = new UploadOrdersItemRequest();
            item.setItemName(CARGO);
            if (line.getUnitPrice() != null) {
                item.setItemValue(line.getUnitPrice().doubleValue());
            }
            item.setNumber(line.getQuantity().intValue());
            itemList.add(item);
        }
        itemslist.setItem(itemList);
        request.setItems(itemslist);
        request.setSpecial(0);
        request.setRemark(sta.getMemo());
        try {
            UploadOrdersResponse response = YtoOrderClient.sendOutboundOrderToYto(request, YTO_UPLOAD_ORDERS_URL, wtoc.getPwd(), wtoc.getUsername());
            if (response == null || response.getSuccess() == null) {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
            } else if (response.getSuccess() != null && "true".equalsIgnoreCase(response.getSuccess())) {
                UploadOrdersRequest uor = (UploadOrdersRequest) MarshallerUtil.buildJaxb(UploadOrdersRequest.class, MarshallerUtil.buildJaxb(response.getOrderMessage()));
                if (response != null && !StringUtil.isEmpty(uor.getMailNo())) {
                    sd.setTrackingNo(uor.getMailNo());
                    sd.setLastModifyTime(new Date());
                    sd.setTransBigWord(uor.getBigPen());// 大头笔
                    sd.setExtTransOrderId(uor.getTxLogisticID());
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getReason()});
                }
            } else if (response.getSuccess() != null && "false".equalsIgnoreCase(response.getSuccess())) {
                String reason = StringUtils.trimWhitespace(response.getReason()) + "";
                if (reason.contains(YtoOrderClient.PROVIDER_NO_TRANS_REASON) || reason.contains(YtoOrderClient.ILLEGAL_DATA_DIGEST_RESON)) {
                    throw new BusinessException(ErrorCode.PROVIDER_NO_TRANS_OR_ERROR, new Object[] {sta.getCode(), response.getReason()});
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getReason()});
                }
            } else {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {sta.getCode(), response.getReason()});
            }
        } catch (BusinessException e) {
            throw e;
        }
        try {
            MongoDBYtoBigWord ytoBigWord = mongoDBManager.matchingPackNo(sd);
            if (null != ytoBigWord) {
                sd.setTransCityCode(ytoBigWord.getPackNo());// 打包编码
            }
        } catch (Exception e) {}
        return sd;
    }



    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        StaDeliveryInfo sd = new StaDeliveryInfo();
        // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
        PackageInfo info = packageInfoDao.getByPrimaryKey(packId);
        // StockTransApplication stab = null;
        sd = null;
        if (info != null) {
            sd = info.getStaDeliveryInfo();// 根据包裹获取物流信息
            if (sd != null) {
                // stab = staDao.getByPrimaryKey(sd.getId());// 根据物流信息获取作业单
            } else {
                throw new BusinessException(ErrorCode.ERROR_DELIINFO_ISNULL);
            }
        }
        String transNo = matchingTransNoByStaId(sd.getId());
        if (StringUtils.hasText(transNo)) {
            info.setTrackingNo(transNo);// 运单号更新到包裹信息
        }
        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }

    // 快递拆包裹获取运单号
    public String matchingTransNoByStaId(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        WhTransOlConfig wtoc = ytoAccountInfo.get(Transportator.YTO + wh.getDeparture());
        if (wtoc == null) {
            wtoc = whTransOlConfigManager.findTransOlConfig(Transportator.YTO, wh.getDeparture());
            ytoAccountInfo.put(Transportator.YTO + wh.getDeparture(), wtoc);
        }
        UploadOrdersRequest request = new UploadOrdersRequest();
        request.setClientID(wtoc.getUsername());// 电商标识（K10101010）
        request.setLogisticProviderID(Transportator.YTO);
        request.setCustomerId(wtoc.getUsername());// 客户标识
        // clientID +数字
        request.setTxLogisticID(wtoc.getUsername() + new Date().getTime());// 物流订单号LP81132426447;
        request.setTotalServiceFee(0.0);
        request.setCodSplitFee(0.0);
        // 订单类型(0-COD,1-普通订单,3-退货单)(退货单是入操作，此处是出库信息传给YTO)
        if (sd.getIsCod() != null && sd.getIsCod()) {
            request.setOrderType(0);
            request.setItemsValue(sta.getTotalActual().doubleValue());// 有代收金额的订单在以上基础上必须添加代收金额的实际数值
            request.setAgencyFund(sta.getTotalActual().doubleValue());// 有代收金额的订单在以上基础上必须添加代收金额的实际数值
        } else {
            request.setOrderType(1);
        }
        // 服务类型(1-上门揽收, 2-次日达 4-次晨达 8-当日达,0-自己联系)。默认为0
        request.setServiceType(0L);// 服务类型--待定
        // 此处等待确定是否需要发送发货人信息
        UploadOrdersSenderRequest sRequest = new UploadOrdersSenderRequest();
        sRequest.setName(wh.getPic());
        sRequest.setPostCode(wh.getZipcode());
        sRequest.setPhone(wh.getPhone());
        sRequest.setMobile(wh.getPhone());
        sRequest.setProv(wh.getProvince());
        sRequest.setCity(wh.getCity());
        sRequest.setAddress(wh.getAddress());
        request.setSender(sRequest);
        UploadOrdersReceiverRequest rRequest = new UploadOrdersReceiverRequest();
        rRequest.setName(sd.getReceiver());
        rRequest.setPostCode(sd.getZipcode());
        if (StringUtils.hasText(sd.getMobile())) {
            rRequest.setPhone(sd.getMobile());
        } else if (StringUtils.hasText(sd.getTelephone())) {
            rRequest.setPhone(sd.getTelephone());
        }
        rRequest.setProv(sd.getProvince());
        if (StringUtils.hasText(sd.getDistrict())) {
            rRequest.setCity(sd.getCity() + "," + sd.getDistrict());
        } else {
            rRequest.setCity(sd.getCity());
        }
        rRequest.setAddress(sd.getAddress());
        request.setReceiver(rRequest);
        request.setInsuranceValue(0.0);

        List<StaLine> lineList = staLineDao.findByStaId(staId);
        UploadOrdersItemsRequest itemslist = new UploadOrdersItemsRequest();
        List<UploadOrdersItemRequest> itemList = new ArrayList<UploadOrdersItemRequest>();
        for (StaLine line : lineList) {
            UploadOrdersItemRequest item = new UploadOrdersItemRequest();
            item.setItemName(line.getSku().getName());
            if (line.getUnitPrice() != null) {
                item.setItemValue(line.getUnitPrice().doubleValue());
            }
            item.setNumber(line.getQuantity().intValue());
            itemList.add(item);
        }
        itemslist.setItem(itemList);
        request.setItems(itemslist);
        request.setSpecial(0);
        request.setRemark(sta.getMemo());
        String trackingNo = null;
        try {
            UploadOrdersResponse response = YtoOrderClient.sendOutboundOrderToYto(request, YTO_UPLOAD_ORDERS_URL, wtoc.getPwd(), wtoc.getUsername());
            if (response == null || response.getSuccess() == null) {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
            } else if (response.getSuccess() != null && "true".equalsIgnoreCase(response.getSuccess())) {
                UploadOrdersRequest uor = (UploadOrdersRequest) MarshallerUtil.buildJaxb(UploadOrdersRequest.class, MarshallerUtil.buildJaxb(response.getOrderMessage()));
                if (response != null && !StringUtil.isEmpty(uor.getMailNo())) {
                    trackingNo = uor.getMailNo();
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getReason()});
                }
            } else if (response.getSuccess() != null && "false".equalsIgnoreCase(response.getSuccess())) {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), response.getReason()});
            } else {
                throw new BusinessException(ErrorCode.YTO_INTERFACE_ERROR, new Object[] {sta.getCode(), response.getReason()});
            }
        } catch (BusinessException e) {
            throw e;
        }
        try {
            MongoDBYtoBigWord ytoBigWord = mongoDBManager.matchingPackNo(sd);
            if (null != ytoBigWord) {
                sd.setTransCityCode(ytoBigWord.getPackNo());// 打包编码
            }
        } catch (Exception e) {}
        return trackingNo;
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
