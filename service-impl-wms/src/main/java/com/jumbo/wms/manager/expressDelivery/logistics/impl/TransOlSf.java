package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.primservice.logistics.command.MailnoGetContentCommand;
import com.baozun.scm.primservice.logistics.command.MailnoTransInfoCommand;
import com.baozun.scm.primservice.logistics.manager.TransServiceManager;
import com.baozun.scm.primservice.logistics.model.MailnoGetResponse;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.TransSfInfoDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.LogisticsInfoDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PackageListLogDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.PickingListPackageDao;
import com.jumbo.dao.warehouse.SfConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.SfExpressTypeConfigDao;
import com.jumbo.dao.warehouse.SfMailNoRemainRelationDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.webservice.sfNew.SfOrderWebserviceClientInter;
import com.jumbo.webservice.sfNew.model.SfAddedService;
import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.webservice.sfNew.model.SfOrderOption;
import com.jumbo.webservice.sfNew.model.SfOrderResponse;
import com.jumbo.webservice.sfNew.model.SfResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.MongoDBSfLogisticsManager;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.MongoDBSfLogistics;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.LogisticsInfo;
import com.jumbo.wms.model.baseinfo.PackageListLog;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.SfConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.SfMailNoRemainRelation;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlSf")
@Transactional
public class TransOlSf implements TransOlInterface {

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private TransSfInfoDao transSfInfoDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private SfConfirmOrderQueueDao sfConfirmOrderQueueDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    @Autowired
    private TransportatorDao transDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private PickingListPackageDao pickingListPackageDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private SfExpressTypeConfigDao sfConfigDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    MongoDBSfLogisticsManager dbSfLogisticsManager;
    @Autowired
    private ChannelWhRefRefDao refDao;
    @Autowired
    BiChannelDao biChannelDao;
    @Autowired
    private SfMailNoRemainRelationDao sfMailNoRemainRelationDao;
    @Autowired
    private SfOrderWebserviceClientInter sfOrderWebserviceClient;
    @Autowired
    private TransServiceManager transServiceManager;
    @Autowired
    private LogisticsInfoDao logisticsInfoDao;
    @Autowired
    private PackageListLogDao packageListLogDao;
    protected static final Logger logger = LoggerFactory.getLogger(TransOlSf.class);
    /**
     * 物流商COD缓存
     */
    static TimeHashMap<String, Transportator> transRoleCache = new TimeHashMap<String, Transportator>();
    /**
     * 月结账号缓存
     */
    static TimeHashMap<String, TransSfInfo> TransSfInfomap = new TimeHashMap<String, TransSfInfo>();
    public static final String CARD_DEFAULT = "DEFAULT";
    public static final String CARE_INVOICE_SEND = "INVOICE_SEND";


    private Map<String, String> getOptionMap(String opcode) {
        Map<String, String> map = new HashMap<String, String>();
        List<ChooseOption> list = chooseOptionDao.findOptionListByCategoryCode(opcode);
        if (list == null || list.isEmpty()) return map;
        for (ChooseOption option : list) {
            map.put(option.getOptionKey(), option.getOptionValue());
        }
        return map;
    }

    public Transportator getTransRole(String lpcode) {
        if (StringUtil.isEmpty(lpcode)) return null;
        Transportator result = transRoleCache.get(lpcode);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            result = cacheTransRole(lpcode);
        }
        return result;
    }

    /**
     * 根据店铺获取一定时间内的缓存数据
     * 
     * @param owner
     * @return
     */
    private synchronized Transportator cacheTransRole(String lpcode) {
        Transportator result = transDao.findByCode(lpcode);
        transRoleCache.put(lpcode, result, 4 * 60 * 1000);
        return result;
    }

    /**
     * 线下包裹获取SF运单号
     */
    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        StaDeliveryInfo info = new StaDeliveryInfo();
        transProvideNoDao.flush();
        Transportator transportator = getTransRole(order.getTransportatorCode());
        transportator.setIsUseSf(transportator.getIsUseSf() == null ? false : transportator.getIsUseSf());
        // 根据开关判断是否使用预存单号
        if (transportator.getIsUseSf()) {
            MongoDBSfLogistics dbSfLogistics = dbSfLogisticsManager.getLogistics(order.getReceiverProvince(), order.getReceiverCity());
            if (dbSfLogistics != null) {
                info.setTransCityCode(dbSfLogistics.getCode());
            } else {
                throw new BusinessException(ErrorCode.NO_SUPPORT_TRANSPORTATOR);
            }
            if (StringUtils.hasText(info.getTrackingNo()) && StringUtils.hasText(info.getExtTransOrderId())) {
                return info;
            }
            String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
            if (transNo == null) {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(order.getId());
            transProvideNoDao.save(whTransProvideNo);
            info.setTrackingNo(transNo);
            info.setLastModifyTime(new Date());
            return info;
        } else {
            Warehouse wh = warehouseDao.getByOuId(whOuId);// 仓库id
            BiChannel shop = companyShopDao.getByCode(order.getCostCenterDetail());// 店铺code

            DecimalFormat df = new DecimalFormat("0.00");
            SfOrder order2 = new SfOrder();
            String orderId = sequenceManager.getCode(SfOrder.class.getName(), order2);
            order2.setOrderId(orderId);// 物流商平台订单号
            // order2.setExpressType("1");
            // 获取产品类型
            Integer type = sfConfigDao.findSfExpressTypeByCondition(order.getTimeType(), order.getTransportatorCode(), order.getIsnotLandTrans(), new SingleColumnRowMapper<Integer>(Integer.class));
            if (type == null) {
                order2.setExpressType("1");
            } else {
                order2.setExpressType(type.toString());
            }
            // /////////////////////////////////////////

            if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
                order2.setPayMethod(SfOrder.SF_PAYMETHOD_TYPE_THIRD_PARTY_PAYMETHOD);
            }
            order2.setdContact(order.getReceiver());

            if (!StringUtils.hasText(order.getReceiverTel())) {
                order2.setdTel(order.getReceiverTel2());
            } else {
                order2.setdTel(order.getReceiverTel());
            }
            order2.setjAddress(wh.getAddress());
            order2.setjCity(wh.getCity());
            order2.setjCompany(order.getSender());

            if (!StringUtils.hasText(order.getSenderTel())) {
                order2.setjTel(order.getSenderTel2());
            } else {
                order2.setjTel(order.getSenderTel());
            }

            order2.setjProvince(wh.getProvince());

            SfOrderOption option = new SfOrderOption();
            order2.setdProvince(order.getReceiverProvince());
            order2.setdCity(order.getReceiverCity());
            option.setdCounty(order.getReceiverArea());
            order2.setdAddress(order.getReceiverAddress());

            option.setCargoCount("");
            TransSfInfo sfInfo = new TransSfInfo();
            if (shop != null) {
                sfInfo = getClientCodeAndSetOption(whOuId, shop, null);
            } else {
                sfInfo = TransSfInfomap.get(TransOlSf.CARD_DEFAULT);
                if (sfInfo == null) {
                    sfInfo = getTransSfInfo(null, null);
                }
            }
            option.setCustid(sfInfo.getjCustid());
            // 附加服务设置
            List<SfAddedService> asList = new ArrayList<SfAddedService>();
            option.setList(asList);
            BigDecimal inSure = order.getInsuranceAmount();// 保价金额
            if (null != wh.getSfWhCode()) {
                option.setTemplate(wh.getSfWhCode());
            }
            option.setTemplate(wh.getSfWhCodeCod());
            option.setList(asList);
            // 保价
            if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
                SfAddedService insure = new SfAddedService();
                insure.setName(SfOrder.SF_SERVICE_ORDER_TEMPLATE_INSURE);
                insure.setValue(inSure == null ? df.format(new BigDecimal(0)) : df.format(inSure));
                asList.add(insure);
            }
            if (!StringUtils.hasText(order.getSenderTel())) {
                option.setjMobile(order.getSenderTel2());
            } else {
                option.setjMobile(order.getSenderTel());
            }
            if (!StringUtils.hasText(order.getReceiverTel())) {
                option.setdMobile(order.getReceiverTel2());
            } else {
                option.setdMobile(order.getReceiverTel());
            }
            order2.setOrderOption(option);
            SfResponse rs = sfOrderWebserviceClient.creSfOrder(order2, sfInfo.getjCusttag(), sfInfo.getCheckword(), 0);
            if (rs == null || rs.getHead() == null) {
                throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {order.getCode(), ""});
            } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
                SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
                if (response != null && !StringUtil.isEmpty(response.getMailno())) {
                    info.setTrackingNo(response.getMailno());
                    info.setExtTransOrderId(orderId);
                    info.setLastModifyTime(new Date());
                    return info;
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {order.getCode(), rs.getError()});
                }
            } else {
                throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {order.getCode(), rs.getError()});
            }
        }
    }

    public StaDeliveryInfo matchingTransNo(Long staId, int expceptionType) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        Transportator transportator = getTransRole(sd.getLpCode());
        transportator.setIsUseSf(transportator.getIsUseSf() == null ? false : transportator.getIsUseSf());
        // 根据开关判断是否使用预存单号
        if (transportator.getIsUseSf()) {
            MongoDBSfLogistics dbSfLogistics = dbSfLogisticsManager.getLogistics(sd.getProvince(), sd.getCity());
            if (dbSfLogistics != null) {
                sd.setTransCityCode(dbSfLogistics.getCode());
            } else {
                throw new BusinessException(ErrorCode.NO_SUPPORT_TRANSPORTATOR);
            }
            if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId())) {
                return sd;
            }
            String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
            if (transNo == null) {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
            sd.setTrackingNo(transNo);
            sd.setExtTransOrderId(sta.getCode());
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(staId);
            sd.setTrackingNo(transNo);
            sd.setLastModifyTime(new Date());
            return sd;
        } else {
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            // 如果之前调用成功，记录快递单号则直接返回
            if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId())) {
                return sd;
            }
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            DecimalFormat df = new DecimalFormat("0.00");
            SfOrder order = new SfOrder();
            String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
            sd.setExtTransOrderId(orderId);
            order.setOrderId(sd.getExtTransOrderId());

            // run
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey("SF_ADIDAS", "SF_ADIDAS");
            if (op == null) {//
                Integer type = sfConfigDao.findSfExpressTypeByCondition(sd.getTransTimeType().getValue(), sd.getLpCode(), sta.getDeliveryType().getValue(), new SingleColumnRowMapper<Integer>(Integer.class));
                order.setExpressType(type.toString());
            } else {
                if (sta.getSystemKey() != null && sta.getSystemKey().equals("adidas")) {
                    order.setExpressType("1");
                } else {
                    Integer type = sfConfigDao.findSfExpressTypeByCondition(sd.getTransTimeType().getValue(), sd.getLpCode(), sta.getDeliveryType().getValue(), new SingleColumnRowMapper<Integer>(Integer.class));
                    order.setExpressType(type.toString());
                }
            }

            // if (sta.getSystemKey() != null && sta.getSystemKey().equals("adidas")) {
            // order.setExpressType("1");
            // } else {
            // Integer type =
            // sfConfigDao.findSfExpressTypeByCondition(sd.getTransTimeType().getValue(),
            // sd.getLpCode(), sta.getDeliveryType().getValue(), new
            // SingleColumnRowMapper<Integer>(Integer.class));
            // order.setExpressType(type.toString());
            // }

            if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
                order.setPayMethod(SfOrder.SF_PAYMETHOD_TYPE_THIRD_PARTY_PAYMETHOD);
            }
            order.setdContact(sd.getReceiver());
            order.setdTel(sd.getMobile() == null ? sd.getTelephone() : sd.getMobile());
            order.setjAddress(wh.getAddress());
            order.setjCity(wh.getCity());
            order.setjCompany(shop.getName());
            order.setjContact(shop.getName());
            order.setjTel(shop.getTelephone());
            order.setjProvince(wh.getProvince());
            if("澳門".equals(sd.getCity()) || "澳門".equals(sd.getProvince())||  "澳门".equals(sd.getCity()) || "澳门".equals(sd.getProvince())){
                try {
                    //设置申报价值币种
                    order.setDeclaredValueCurrency("HKD");
                    //设置申报价值
                    order.setDeclaredValue(sta.getTotalActual());
                } catch (Exception e) {
                }
            }
          
            SfOrderOption option = new SfOrderOption();

            // 判断渠道上收货省份，为空 取订单收件原有省份 ，非空 取渠道上的收件省份取代订单收件省份，而订单收件省份填写至收件城市，以此类推至 收件区域+详细地址 =
            // 接口中的详细地址，
            if (StringUtil.isEmpty(shop.getProvince())) {
                order.setdProvince(sd.getProvince());
                order.setdCity(sd.getCity());
                option.setdCounty(sd.getDistrict());
                order.setdAddress(sd.getAddress());
            } else {
                order.setdProvince(shop.getProvince());
                order.setdCity(sd.getProvince());
                option.setdCounty(sd.getCity());
                order.setdAddress(sd.getDistrict() + " " + sd.getAddress());
            }
            option.setCargoCount(sta.getSkuQty() == null ? "" : sta.getSkuQty().toString());
            TransSfInfo sfInfo = getClientCodeAndSetOption(sta.getMainWarehouse().getId(), shop, null);
            option.setCustid(sfInfo.getjCustid());
            // 附加服务设置
            List<SfAddedService> asList = new ArrayList<SfAddedService>();
            option.setList(asList);
            BigDecimal inSure = channelInsuranceManager.getInsurance(sta.getOwner(), sd.getInsuranceAmount());
            if (null != wh.getSfWhCode()) {
                option.setTemplate(wh.getSfWhCode());
            }
            if (sd.getIsCod() != null && sd.getIsCod()) {
                option.setTemplate(wh.getSfWhCodeCod());
                option.setList(asList);
                // 保价
                BigDecimal total = sta.getTotalActual();
                if (sd.getTransferFee() != null) {
                    total = total.add(sd.getTransferFee());
                }
                if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
                    SfAddedService insure = new SfAddedService();
                    insure.setName(SfOrder.SF_SERVICE_ORDER_TEMPLATE_INSURE);
                    insure.setValue(inSure == null ? df.format(new BigDecimal(0)) : df.format(inSure));
                    asList.add(insure);
                }
                // COD
                SfAddedService cod = new SfAddedService();
                cod.setName(SfOrder.SF_SERVICE_ORDER_TEMPLATE_COD);
                cod.setValue(df.format(total));
                // 品牌 COD订单特定 月结编码
                if (StringUtil.isEmpty(shop.getTransAccountsCode())) {
                    cod.setValue1(option.getCustid());
                } else {
                    cod.setValue1(shop.getTransAccountsCode());
                }
                asList.add(cod);
            } else {
                // 保价
                if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
                    SfAddedService insure = new SfAddedService();
                    insure.setName(SfOrder.SF_SERVICE_ORDER_TEMPLATE_INSURE);
                    insure.setValue(inSure == null ? df.format(new BigDecimal(0)) : df.format(inSure));
                    asList.add(insure);
                }
            }
            option.setjMobile(shop.getTelephone());
            option.setdMobile(sd.getMobile());
            order.setOrderOption(option);
            SfResponse rs = sfOrderWebserviceClient.creSfOrder(order, sfInfo.getjCusttag(), sfInfo.getCheckword(), 0);
            if (rs == null || rs.getHead() == null) {
                throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
            } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
                SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
                if (response != null && !StringUtil.isEmpty(response.getMailno())) {
                    sd.setTransCityCode(response.getDestCode());
                    sd.setTrackingNo(response.getMailno());
                    if (order.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                        sd.setTransType(TransType.PREFERENTIAL);
                    }
                    sd.setLastModifyTime(new Date());
                } else {
                    if (wh.getIsTurnEMS() != null && wh.getIsTurnEMS() == true) {
                        throw new Exception(ErrorCode.EMS_ERROR);
                    } else {
                        throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), rs.getError()});
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {sta.getCode(), rs.getError()});
            }
            return sd;
        }
    }

    /**
     * @param id
     * @param id2
     * @return
     * 
     *         目前SF接口需要用到的客户编码，校验码统一配置到T_WH_TRANS_SF表，根据其中的客户编码查询<br/>
     *         因此该表结构中有四个字段 客户编码，校验码，月结账号，是否通用<br/>
     *         获取信息首先看仓库渠道关联表上面是否有配置,其次看店铺上配置，如果没有就去通用配置（即是否通用字段为1的配置）
     */
    private TransSfInfo getClientCodeAndSetOption(Long ouId, BiChannel shop, String specialUse) {
        TransSfInfo sfInfo = null;
        if (specialUse != null) {
            sfInfo = TransSfInfomap.get(specialUse);
            if (sfInfo == null) {
                sfInfo = getTransSfInfo(null, specialUse);
            }
        } else {
            String custId = null;
            ChannelWhRef channelWhRef = refDao.getChannelRef(ouId, shop.getId());
            if (StringUtils.hasText(channelWhRef.getSfJcustid())) {
                custId = channelWhRef.getSfJcustid();
            } else {
                if (StringUtils.hasText(shop.getSfJcustid())) {
                    custId = shop.getSfJcustid();
                }
            }
            sfInfo = TransSfInfomap.get(custId == null ? TransOlSf.CARD_DEFAULT : custId);
            if (sfInfo == null) {
                sfInfo = getTransSfInfo(custId, null);
            }
        }
        return sfInfo;
    }

    private synchronized TransSfInfo getTransSfInfo(String custId, String specialCode) {
        TransSfInfo result = null;
        String infoKey = null;
        if (custId == null && specialCode == null) {
            infoKey = TransOlSf.CARD_DEFAULT;
            result = transSfInfoDao.findTransSfInfoDefault(true);
        } else if (specialCode != null) {
            infoKey = specialCode;
            result = transSfInfoDao.findTransSfInfoSpecialUse(specialCode);
        } else {
            infoKey = custId;
            result = transSfInfoDao.findTransSfInfoJCustid(custId);
        }
        TransSfInfomap.put(infoKey, result, 4 * 60 * 1000);
        return result;
    }

    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        BiChannel channel = biChannelDao.getByCode(sta.getOwner());
        if (channel.getIsPackingList() != null && channel.getIsPackingList()) {
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            if (StringUtils.hasText(sd.getTrackingNo()) && StringUtils.hasText(sd.getExtTransOrderId())) {
                return sd;
            } else {
                LogisticsInfo logisticsInfo = logisticsInfoDao.findBySystemKeyDefault("NIKE", true);
                matchingTransNoNike(sta, logisticsInfo, sd);
                // 记录装箱清单信息
                createPackageListLog(sta, logisticsInfo);
                return sd;
            }
        } else {
            return matchingTransNo(staId, 1);
        }
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        StaDeliveryInfo d = null;
        try {
            d = matchingTransNo(staId, 1);
        } catch (Exception e) {

        }
        return d;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        // 合并订单传主订单
        if (null != sta.getGroupSta() && (null == sta.getIsMerge() || !sta.getIsMerge())) {
            sta = staDao.getByPrimaryKey(sta.getGroupSta().getId());
        }
        if (sta.getStaDeliveryInfo().getExtTransOrderId() == null) {
            return;
        }
        BiChannel bi = biChannelDao.getByCode(sta.getOwner());
        SfConfirmOrderQueue q = new SfConfirmOrderQueue();
        TransSfInfo sfInfo = getClientCodeAndSetOption(sta.getMainWarehouse().getId(), bi, null);
        q.setCheckword(sfInfo.getCheckword());
        q.setJcusttag(sfInfo.getjCusttag());
        q.setCreateTime(new Date());
        q.setExeCount(0L);
        List<String> sunNo = staDeliveryInfoDao.getParentAndSonMailNoById1(sta.getId(), new SingleColumnRowMapper<String>(String.class));
        StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        String headStr = info.getTrackingNo();
        String mailNo = null;
        if (newTransNo == null) {
             mailNo = headStr;
            String handleStr = org.apache.commons.lang.StringUtils.join(sunNo.toArray(), ",");
            if (!StringUtil.isEmpty(handleStr)) {
                mailNo += "," + handleStr;
            }
        }else{
            mailNo = newTransNo;
        }

        if (StringUtils.hasText(info.getReturnTransNo())) {
            SfConfirmOrderQueue rq = new SfConfirmOrderQueue();
            rq.setCheckword(sfInfo.getCheckword());
            rq.setJcusttag(sfInfo.getjCusttag());
            rq.setCreateTime(new Date());
            rq.setExeCount(0L);
            rq.setMailno(info.getReturnTransNo());
            rq.setOrderId(sta.getStaDeliveryInfo().getRetrunExtTransOrderId());
            rq.setStaCode(sta.getCode());
            rq.setWeight(null);
            rq.setFilter4(null);
            rq.setFilter2(null);
            rq.setFilter3(null);
            sfConfirmOrderQueueDao.save(rq);
        }

        List<String> interceptList = interceptOverLengthString(mailNo);
        q.setMailno(interceptList.get(0));
        q.setOrderId(sta.getStaDeliveryInfo().getExtTransOrderId());
        q.setStaCode(sta.getCode());
        BigDecimal weight = sta.getStaDeliveryInfo().getWeight();
        DecimalFormat df = new DecimalFormat("0.00");
        q.setWeight(df.format(weight));
        List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(sta.getId());
        if (list != null) {
            for (StaAdditionalLine l : list) {
                if (l.getSku().getLength() != null) {
                    q.setFilter2(df.format(l.getSku().getLength().divide(new BigDecimal(10))));
                    q.setFilter3(df.format(l.getSku().getWidth().divide(new BigDecimal(10))));
                    q.setFilter4(df.format(l.getSku().getHeight().divide(new BigDecimal(10))));
                    break;
                }
            }
        } else {
            q.setFilter4("0");
            q.setFilter2("0");
            q.setFilter3("0");
        }
        SfConfirmOrderQueue entity = sfConfirmOrderQueueDao.save(q);
        if (null != interceptList && interceptList.size() > 1) {
            for (int i = 1; i < interceptList.size(); i++) {
                SfMailNoRemainRelation sfmnrr = new SfMailNoRemainRelation();
                sfmnrr.setSplitMailno(interceptList.get(i));
                sfmnrr.setRefId(entity.getId());
                sfMailNoRemainRelationDao.save(sfmnrr);
            }
        }
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        PackageInfo info = packageInfoDao.getByPrimaryKey(packId);
        StockTransApplication stab = null;
        StaDeliveryInfo sd = null;
        if (info != null) {
            sd = info.getStaDeliveryInfo();// 根据包裹获取物流信息
            if (sd != null) {
                stab = staDao.getByPrimaryKey(sd.getId());// 根据物流信息获取作业单
            } else {
                throw new BusinessException(ErrorCode.ERROR_DELIINFO_ISNULL);
            }
        }
        sd.setExtTransOrderId(stab.getCode());
        BiChannel shop = companyShopDao.getByCode(stab.getOwner());
        if (shop.getIsPackingList() != null && shop.getIsPackingList()) {
            StockTransApplication sta = staDao.getByPrimaryKey(sd.getId());
            LogisticsInfo logisticsInfo = logisticsInfoDao.findBySystemKeyDefault("NIKE", true);
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            TransSfInfo sfInfo = getClientCodeAndSetOption(sta.getMainWarehouse().getId(), shop, null);
            MailnoGetContentCommand salseOrder = salseMailnoGetContent(sta, sd, logisticsInfo, sfInfo, wh, shop); // TODO
            salseOrder.setMainTrackno(sd.getTrackingNo());
            salseOrder.setIsChild(true);
            List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(salseOrder, "WMS3");
            if (salseList != null && salseList.size() > 0) {
                if (salseList.get(0).getStatus() == 1) {
                    if (StringUtils.hasText(salseList.get(0).getMailno())) {
                        info.setTrackingNo(salseList.get(0).getMailno());// 运到号更新到包裹信息
                        info.setLastModifyTime(new Date());
                        return sd;
                    } else {
                        // l.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                        throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                    }
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
            }

        } else {
            ChannelWhRef channelWhRef = refDao.getChannelRef(stab.getMainWarehouse().getId(), shop.getId());
            String transNo = null;
            if (type == null) {

                if (StringUtils.hasText(channelWhRef.getSfJcustid())) {
                    transNo = transProvideNoDao.getTranNoByLpcodeJcustidSf(Transportator.SF, channelWhRef.getSfJcustid(), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.hasText(transNo)) {
                        transNo = transProvideNoDao.getTranNoByLpcodeSf(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
                    }
                } else if (StringUtils.hasText(shop.getSfJcustid())) {
                    transNo = transProvideNoDao.getTranNoByLpcodeJcustidSf(Transportator.SF, shop.getSfJcustid(), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.hasText(transNo)) {
                        transNo = transProvideNoDao.getTranNoByLpcodeSf(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
                    }
                } else {
                    TransSfInfo sfInfo = TransSfInfomap.get(TransOlSf.CARD_DEFAULT);
                    if (sfInfo == null) {
                        sfInfo = getTransSfInfo(null, null);
                    }
                    transNo = transProvideNoDao.getTranNoByLpcodeJcustidSf(Transportator.SF, sfInfo.getjCustid(), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.hasText(transNo)) {
                        transNo = transProvideNoDao.getTranNoByLpcodeSf(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
                    }
                }
                if (transNo == null) {
                    throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
                }
            } else {// 销售出转物流 获取sf母单号
                if (StringUtils.hasText(channelWhRef.getSfJcustid())) {
                    transNo = transProvideNoDao.getTranNoByLpcodeJcustidSfMu(Transportator.SF, channelWhRef.getSfJcustid(), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.hasText(transNo)) {
                        transNo = transProvideNoDao.getTranNoByLpcodeSfMu(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
                    }
                } else if (StringUtils.hasText(shop.getSfJcustid())) {
                    transNo = transProvideNoDao.getTranNoByLpcodeJcustidSfMu(Transportator.SF, shop.getSfJcustid(), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.hasText(transNo)) {
                        transNo = transProvideNoDao.getTranNoByLpcodeSfMu(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
                    }
                } else {
                    TransSfInfo sfInfo = TransSfInfomap.get(TransOlSf.CARD_DEFAULT);
                    if (sfInfo == null) {
                        sfInfo = getTransSfInfo(null, null);
                    }
                    transNo = transProvideNoDao.getTranNoByLpcodeJcustidSfMu(Transportator.SF, sfInfo.getjCustid(), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.hasText(transNo)) {
                        transNo = transProvideNoDao.getTranNoByLpcodeSfMu(Transportator.SF, new SingleColumnRowMapper<String>(String.class));
                    }
                }
                if (transNo == null) {
                    throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
                }


            }
            info.setTrackingNo(transNo);// 运到号更新到包裹信息
            info.setLastModifyTime(new Date());
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(stab.getId());// 作业单号更新到运单

            return sd;
        }
    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        PickingListPackage plp = pickingListPackageDao.findByPickingListId(plId);
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(plp.getTrackingNo())) {
            return plp;
        }
        List<StockTransApplication> stas = wareHouseManager.findStaByPickingList(pl.getId(), pl.getWarehouse().getId());
        StockTransApplication sta = staDao.getByPrimaryKey(stas.get(0).getId());
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        Warehouse wh = warehouseDao.getByOuId(pl.getWarehouse().getId());
        // OperationUnit cmpou = sta.getMainWarehouse().getParentUnit().getParentUnit();
        // TransSfInfo sfInfo = null;// transSfInfoDao.findTransSfInfo(cmpou.getId());

        SfOrder order = new SfOrder();
        String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
        order.setOrderId(orderId);
        order.setExpressType(SfOrder.ORDER_TYPE_STANDARD);// 标准快递

        order.setdAddress(plp.getAddress());
        order.setdCity(plp.getCity());
        order.setdContact(plp.getReceiver());
        order.setdProvince(plp.getProvince());
        order.setdTel(plp.getTelephone());

        order.setjAddress(wh.getAddress());
        order.setjCity(wh.getCity());
        order.setjContact(wh.getPicContact());
        order.setjProvince(wh.getProvince());

        SfOrderOption option = new SfOrderOption();
        // option.setCustid(sfInfo.getjCustid());

        // 获取 SF顾客编码
        Map<String, String> mapInfo = getOptionMap(Constants.SF_WEB_SERVICE_INFO);
        // 附加服务设置
        List<SfAddedService> asList = new ArrayList<SfAddedService>();
        option.setList(asList);
        if ("BZ021003".equals(wh.getSfWhCode())) {
            option.setTemplate(mapInfo.get(Constants.SF_WEB_SERVICE_INFO_BZ021003));
        }
        option.setjMobile(wh.getPhone());
        option.setdCounty(plp.getDistrict());// district
        option.setdMobile(plp.getTelephone());
        order.setOrderOption(option);

        SfResponse rs = sfOrderWebserviceClient.creSfOrder(order, mapInfo.get(Constants.SF_WEB_SERVICE_INFO_CODE), mapInfo.get(Constants.SF_WEB_SERVICE_INFO_CHECKWORD), 0);
        if (rs == null || rs.getHead() == null) {
            throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
            SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
            if (response != null && !StringUtil.isEmpty(response.getMailno())) {
                plp.setTrackingNo(response.getMailno());
                pickingListPackageDao.save(plp);
                if (order.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                    sd.setTransType(TransType.PREFERENTIAL);
                }
            } else {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), rs.getError()});
            }
        } else {
            throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {sta.getCode(), rs.getError()});
        }

        return plp;
    }

    @Deprecated
    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        WmsInvoiceOrder wio = wmsInvoiceOrderDao.getByPrimaryKey(wioId);
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(wio.getTransNo())) {
            return wio;
        }
        StockTransApplication sta = staDao.getByCode(wio.getOrderCode());
        // StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // OperationUnit cmpou = sta.getMainWarehouse().getParentUnit().getParentUnit();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        /*
         * StaDeliveryInfo sd = sta.getStaDeliveryInfo(); if (sd == null) { sd =
         * staDeliveryInfoDao.getByPrimaryKey(staId); }
         */

        /*
         * if (StringUtils.hasText(sd.getTrackingNo()) &&
         * StringUtils.hasText(sd.getExtTransOrderId())) { return sd; }
         */
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        // TransSfInfo sfInfo = null;// transSfInfoDao.findTransSfInfo(cmpou.getId());
        // Transportator trans = transportatorDao.findByCode(sd.getLpCode());
        // DecimalFormat df = new DecimalFormat("0.00");
        SfOrder order = new SfOrder();
        String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
        // sd.setExtTransOrderId(orderId);
        order.setOrderId(orderId);

        // Integer type = sfConfigDao.findSfExpressTypeByCondition(sd.getTransTimeType().getValue(),
        // sd.getLpCode(), sta.getDeliveryType().getValue(), new
        // SingleColumnRowMapper<Integer>(Integer.class));
        order.setExpressType("1");
        /*
         * if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
         * order.setPayMethod(SfOrder.SF_PAYMETHOD_TYPE_THIRD_PARTY_PAYMETHOD); }
         */


        order.setjAddress(wh.getAddress());
        order.setjCity(wh.getCity());
        order.setjCompany(shop.getName());
        order.setjContact(shop.getName());
        order.setjTel(shop.getTelephone());
        order.setjProvince(wh.getProvince());

        order.setdContact(wio.getReceiver());
        order.setdTel(wio.getMobile() == null ? wio.getTelephone() : wio.getMobile());
        /*
         * order.setdProvince(wio.getProvince()); order.setdCity(wio.getCity());
         * order.setdAddress(wio.getAddress());
         */

        SfOrderOption option = new SfOrderOption();
        // option.setdCounty(wio.getDistrict());

        // 判断渠道上收货省份，为空 取订单收件原有省份 ，非空 取渠道上的收件省份取代订单收件省份，而订单收件省份填写至收件城市，以此类推至 收件区域+详细地址 = 接口中的详细地址，
        if (StringUtil.isEmpty(shop.getProvince())) {
            order.setdProvince(wio.getProvince());
            order.setdCity(wio.getCity());
            option.setdCounty(wio.getDistrict());
            order.setdAddress(wio.getAddress());
        } else {
            order.setdProvince(shop.getProvince());
            order.setdCity(wio.getProvince());
            option.setdCounty(wio.getCity());
            order.setdAddress(wio.getDistrict() + " " + wio.getAddress());
        }
        option.setCargoWeight("0.1");
        option.setCargoAmount("0");

        // option.setCustid(sfInfo.getjCustid());
        // option.setCargoCount(sta.getSkuQty() == null ? "" : sta.getSkuQty().toString());
        // 获取 SF顾客编码
        Map<String, String> mapInfo = getOptionMap(Constants.SF_WEB_SERVICE_INFO);
        // 附加服务设置
        List<SfAddedService> asList = new ArrayList<SfAddedService>();
        option.setList(asList);
        // BigDecimal inSure = channelInsuranceManager.getInsurance(sta.getOwner(),
        // sd.getInsuranceAmount());
        if (null != wh.getSfWhCode()) {
            option.setTemplate(wh.getSfWhCode());
        }
        option.setjMobile(shop.getTelephone());
        option.setdMobile(wio.getMobile() == null ? wio.getTelephone() : wio.getMobile());
        order.setOrderOption(option);
        SfResponse rs = sfOrderWebserviceClient.creSfOrder(order, mapInfo.get(Constants.SF_WEB_SERVICE_INFO_CODE), mapInfo.get(Constants.SF_WEB_SERVICE_INFO_CHECKWORD), 0);
        if (rs == null || rs.getHead() == null) {
            throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {wio.getCode(), ""});
        } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
            SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
            if (response != null && !StringUtil.isEmpty(response.getMailno())) {
                wio.setTransNo(response.getMailno());
            } else {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {wio.getCode(), rs.getError()});
            }
        } else {
            throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {wio.getCode(), rs.getError()});
        }
        return wio;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        WmsInvoiceOrder wio = wmsInvoiceOrderDao.getByPrimaryKey(id);
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(wio.getTransNo()) && StringUtils.hasText(wio.getExtTransOrderId())) {
            return wio;
        }
        BiChannel shop = companyShopDao.getByCode(wio.getOwner());
        SfOrder order = new SfOrder();
        String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
        wio.setExtTransOrderId(orderId);
        order.setOrderId(wio.getExtTransOrderId());

        order.setExpressType("1");
        order.setPayMethod(1);
        order.setdContact(wio.getReceiver());
        order.setdTel(wio.getMobile() == null ? wio.getTelephone() : wio.getMobile());
        order.setjAddress(shop.getAddress());
        // order.setjCity();
        order.setjCompany(shop.getName());
        order.setjContact(shop.getName());
        order.setjTel(shop.getTelephone());
        order.setjProvince(shop.getProvince());

        SfOrderOption option = new SfOrderOption();

        // 判断渠道上收货省份，为空 取订单收件原有省份 ，非空 取渠道上的收件省份取代订单收件省份，而订单收件省份填写至收件城市，以此类推至 收件区域+详细地址 =
        // 接口中的详细地址，
        order.setdProvince(wio.getProvince());
        order.setdCity(wio.getCity());
        option.setdCounty(wio.getDistrict());
        order.setdAddress(wio.getAddress());

        TransSfInfo sfInfo = getClientCodeAndSetOption(null, null, TransOlSf.CARE_INVOICE_SEND);
        option.setCustid(sfInfo.getjCustid());
        option.setCargoCount("1");
        // 获取 SF顾客编码
        // 附加服务设置
        List<SfAddedService> asList = new ArrayList<SfAddedService>();
        option.setList(asList);
        option.setTemplate(sfInfo.getTemplate());
        option.setjMobile(shop.getTelephone());
        option.setdMobile(wio.getMobile() == null ? wio.getTelephone() : wio.getMobile());
        order.setOrderOption(option);
        SfResponse rs = sfOrderWebserviceClient.creSfOrder(order, sfInfo.getjCusttag(), sfInfo.getCheckword(), 0);
        if (rs == null || rs.getHead() == null) {
            throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {wio.getOrderCode(), ""});
        } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {
            SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
            if (response != null && !StringUtil.isEmpty(response.getMailno())) {
                wio.setTransCityCode(response.getDestCode());
                wio.setTransNo(response.getMailno());
            } else {
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {wio.getOrderCode(), rs.getError()});
            }
        } else {
            throw new BusinessException(ErrorCode.SF_INTERFACE_ERROR, new Object[] {wio.getOrderCode(), rs.getError()});
        }
        return wio;
    }

    @Override
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {
        if (wio.getExtTransOrderId() == null) {
            return;
        }
        SfConfirmOrderQueue q = new SfConfirmOrderQueue();
        Map<String, String> mapInfo = getOptionMap(Constants.SF_WEB_SERVICE_INFO);
        List<String> list = interceptOverLengthString(wio.getTransNo());
        q.setCheckword(mapInfo.get(Constants.SF_WEB_SERVICE_CONFIRM_CHECKWORD));// chooseOption里面配置
        q.setCreateTime(new Date());
        q.setExeCount(0L);
        q.setMailno(list.get(0));
        q.setOrderId(wio.getExtTransOrderId());
        q.setStaCode(wio.getOrderCode());
        DecimalFormat df = new DecimalFormat("0.00");
        q.setWeight(df.format(0.1));// 重量统一使用 0.1kg
        q.setFilter4("200.00");// 体积使用200mm*100mm*5mm
        q.setFilter2("100.00");
        q.setFilter3("5.00");
        SfConfirmOrderQueue entity = sfConfirmOrderQueueDao.save(q);
        if (null != list && list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                SfMailNoRemainRelation sfmnrr = new SfMailNoRemainRelation();
                sfmnrr.setSplitMailno(list.get(i));
                sfmnrr.setRefId(entity.getId());
                sfMailNoRemainRelationDao.save(sfmnrr);
            }
        }
    }

    /**
     * 
     * 方法说明：(VMWS-1076) 截取大于3000以上的字符串 分存至关联表,防止超过数据库字段定义的最大保存限制
     * 
     * @author LuYingMing
     * @param str
     * @return
     */
    private List<String> interceptOverLengthString(String str) {
        List<String> handleList = new ArrayList<String>();
        String strTemp = null;
        if (str.length() > 3000) {
            strTemp = str.substring(0, 3000);
            handleList.add(strTemp);
            String remainStr = str.substring(3000);
            List<String> list = interceptOverLengthString(remainStr);
            handleList.addAll(list);
        } else {
            handleList.add(str);
        }
        return handleList;
    }

    private StaDeliveryInfo matchingTransNoNike(StockTransApplication sta, LogisticsInfo logisticsInfo, StaDeliveryInfo sd) {
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        TransSfInfo sfInfo = getClientCodeAndSetOption(sta.getMainWarehouse().getId(), shop, null);
        MailnoGetContentCommand salseOrder = salseMailnoGetContent(sta, sd, logisticsInfo, sfInfo, wh, shop);
        MailnoGetContentCommand returnOrder = returnMailnoGetContent(sta, sd, logisticsInfo, sfInfo, wh);
        List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(salseOrder, "WMS3");
        if (salseList != null && salseList.size() > 0) {
            if (salseList.get(0).getStatus() == 1) {
                if (StringUtils.hasText(salseList.get(0).getMailno())) {
                    sd.setTransCityCode(salseList.get(0).getTransBigWord());
                    sd.setExtTransOrderId(salseList.get(0).getExtId());
                    sd.setTrackingNo(salseList.get(0).getMailno());
                    if (salseOrder.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                        sd.setTransType(TransType.PREFERENTIAL);
                    }
                    sd.setLastModifyTime(new Date());
                } else {
                    logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
            }
        } else {
            logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        }
        List<MailnoGetResponse> returnList = transServiceManager.matchingTransNo(returnOrder, "WMS3");
        if (returnList != null && returnList.size() > 0) {
            if (returnList.get(0).getStatus() == 1) {
                if (StringUtils.hasText(returnList.get(0).getMailno())) {
                    sd.setReturnTransCityCode(returnList.get(0).getTransBigWord());
                    sd.setReturnTransNo(returnList.get(0).getMailno());
                    sd.setRetrunExtTransOrderId(returnList.get(0).getExtId());
                    if (salseOrder.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                        sd.setTransType(TransType.PREFERENTIAL);
                    }
                    sd.setLastModifyTime(new Date());
                } else {
                    logger.info(returnList.get(0).getErrorMsg() + ">>>>>>>>>>>>>>>>");
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), returnList.get(0).getErrorMsg()});
                }
            } else {
                logger.info(returnList.get(0).getErrorMsg() + ">>>>>>>>>>>>>>>>");
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), returnList.get(0).getErrorMsg()});
            }
        } else {
            logger.info("LOGISTICS_INTERFACE_ERROR===>>>>>>>>>>>>>>");
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        }
        return sd;
    }

    public MailnoGetContentCommand salseMailnoGetContent(StockTransApplication sta, StaDeliveryInfo sd, LogisticsInfo logisticsInfo, TransSfInfo sfInfo, Warehouse wh, BiChannel shop) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        BigDecimal inSure = channelInsuranceManager.getInsurance(sta.getOwner(), sd.getInsuranceAmount());
        if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
            mailnoGetContent.setIsTpPaymentSf(true);
        }
        mailnoGetContent.setSfWhCode(wh.getSfWhCode());
        if (null != wh.getSfWhCode()) {
            mailnoGetContent.setSfWhCode(wh.getSfWhCode());
        }
        if (sd.getIsCod() != null && sd.getIsCod()) {
            mailnoGetContent.setSfWhCode(wh.getSfWhCodeCod());
            // 保价
            BigDecimal total = sta.getTotalActual();
            if (sd.getTransferFee() != null) {
                total = total.add(sd.getTransferFee());
            }
            if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
                mailnoGetContent.setInsuranceAmount(inSure == null ? new BigDecimal(0) : inSure);
            }
            // COD
            mailnoGetContent.setTotalActual(total);


            // 品牌 COD订单特定 月结编码
            if (StringUtil.isEmpty(shop.getTransAccountsCode())) {
                mailnoGetContent.setTransAccountsCode(sfInfo.getjCustid());
            } else {
                // mailnoGetContent.setTransAccountsCode(shop.getTransAccountsCode());
            }
        } else {
            // 保价
            if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
                mailnoGetContent.setInsuranceAmount(inSure == null ? new BigDecimal(0) : inSure);
            }
            mailnoGetContent.setTotalActual(sta.getTotalActual());
        }
        mailnoGetContent.setCheckWord(sfInfo.getCheckword());
        mailnoGetContent.setCoustemCode(sfInfo.getjCusttag());
        mailnoGetContent.setJcustid(sfInfo.getjCustid());
        mailnoGetContent.setOrderCode(sta.getCode());
        mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderSource(""); // TODO
        mailnoGetContent.setWhCode(sta.getMainWarehouse().getCode());
        mailnoGetContent.setOwnerCode(sta.getOwner());
        mailnoGetContent.setLpCode(sd.getLpCode());
        if (sta.getIsNikePick() != null && sta.getIsNikePick()) {
            if (sta.getNikePickUpType() != null && sta.getNikePickUpType().equals("0004")) {
                mailnoGetContent.setIsOneSelf(sta.getIsNikePick());
            }
        }
        mailnoGetContent.setType(3);
        Integer type = sfConfigDao.findSfExpressTypeByCondition(sd.getTransTimeType().getValue(), sd.getLpCode(), sta.getDeliveryType().getValue(), new SingleColumnRowMapper<Integer>(Integer.class));
        mailnoGetContent.setExpressType(type.toString());
        mailnoGetContent.setIsCod(sd.getIsCod());
        //
        // mailnoGetContent.setInsuranceAmount(sd.getInsuranceAmount());
        MailnoTransInfoCommand transInfoCommand = new MailnoTransInfoCommand();
        transInfoCommand.setAddress(sd.getAddress());
        transInfoCommand.setCity(sd.getCity());
        transInfoCommand.setDistrict(sd.getDistrict());
        transInfoCommand.setMobile(sd.getMobile());
        transInfoCommand.setProvince(sd.getProvince());
        transInfoCommand.setReceiver(sd.getReceiver());
        transInfoCommand.setTelephone(sd.getTelephone());
        transInfoCommand.setZipCode(sd.getZipcode());
        mailnoGetContent.setTransInfo(transInfoCommand);
        MailnoTransInfoCommand returnTransInfo = new MailnoTransInfoCommand();
        returnTransInfo.setAddress(logisticsInfo.getAddress());
        returnTransInfo.setCity(logisticsInfo.getCity());
        returnTransInfo.setDistrict(logisticsInfo.getDistrict());
        returnTransInfo.setMobile(logisticsInfo.getMobile());
        returnTransInfo.setProvince(logisticsInfo.getProvince());
        returnTransInfo.setReceiver(logisticsInfo.getReceiver());
        returnTransInfo.setTelephone(logisticsInfo.getTelephone());
        returnTransInfo.setZipCode(logisticsInfo.getZipCode());
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        return mailnoGetContent;

    }

    public MailnoGetContentCommand returnMailnoGetContent(StockTransApplication sta, StaDeliveryInfo sd, LogisticsInfo logisticsInfo, TransSfInfo sfInfo, Warehouse wh) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        mailnoGetContent.setSfWhCode(wh.getSfWhCode());
        mailnoGetContent.setJcustid(sfInfo.getjCustid());
        mailnoGetContent.setCheckWord(sfInfo.getCheckword());
        mailnoGetContent.setCoustemCode(sfInfo.getjCusttag());
        mailnoGetContent.setOrderCode(sta.getCode());
        mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderSource(""); // TODO
        mailnoGetContent.setWhCode(sta.getMainWarehouse().getCode());
        mailnoGetContent.setOwnerCode(sta.getOwner());
        mailnoGetContent.setLpCode(sd.getLpCode());
        mailnoGetContent.setType(2);
        mailnoGetContent.setExpressType("2");
        // mailnoGetContent.setIsCod(sd.getIsCod()); 负向订单不需要cod
        mailnoGetContent.setTotalActual(sta.getTotalActual());
        // mailnoGetContent.setInsuranceAmount(sd.getInsuranceAmount());
        // 退回的负向 收件人为 仓库
        MailnoTransInfoCommand transInfoCommand = new MailnoTransInfoCommand();
        transInfoCommand.setAddress(logisticsInfo.getAddress());
        transInfoCommand.setCity(logisticsInfo.getCity());
        transInfoCommand.setDistrict(logisticsInfo.getDistrict());
        transInfoCommand.setMobile(logisticsInfo.getMobile());
        transInfoCommand.setProvince(logisticsInfo.getProvince());
        transInfoCommand.setReceiver(logisticsInfo.getReceiver());
        transInfoCommand.setTelephone(logisticsInfo.getTelephone());
        transInfoCommand.setZipCode(logisticsInfo.getZipCode());
        mailnoGetContent.setTransInfo(transInfoCommand);
        // 退回的负向 寄件人 为 顾客
        MailnoTransInfoCommand returnTransInfo = new MailnoTransInfoCommand();
        returnTransInfo.setAddress(sd.getAddress());
        returnTransInfo.setCity(sd.getCity());
        returnTransInfo.setDistrict(sd.getDistrict());
        returnTransInfo.setMobile(sd.getMobile());
        returnTransInfo.setProvince(sd.getProvince());
        returnTransInfo.setReceiver(sd.getReceiver());
        returnTransInfo.setTelephone(sd.getTelephone());
        returnTransInfo.setZipCode(sd.getZipcode());
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        return mailnoGetContent;
    }

    public void createPackageListLog(StockTransApplication sta, LogisticsInfo logisticsInfo) {
        PackageListLog listLog = new PackageListLog();
        listLog.setReceiver(logisticsInfo.getReceiver());
        listLog.setSender(sta.getStaDeliveryInfo().getReceiver());
        listLog.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
        listLog.setStaCode(sta.getRefSlipCode());
        listLog.setMailingAddress(sta.getStaDeliveryInfo().getAddress());
        listLog.setConsigneeAddress(logisticsInfo.getAddress());
        listLog.setReceivingPhone(logisticsInfo.getTelephone());
        listLog.setTelephone(sta.getStaDeliveryInfo().getTelephone());
        packageListLogDao.save(listLog);


    }
}
