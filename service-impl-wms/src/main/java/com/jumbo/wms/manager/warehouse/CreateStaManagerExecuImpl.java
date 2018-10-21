package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.QueueGiftLineDao;
import com.jumbo.dao.warehouse.QueueLogGiftLineDao;
import com.jumbo.dao.warehouse.QueueLogStaDao;
import com.jumbo.dao.warehouse.QueueLogStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueLogStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueLogStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueLogStaLineDao;
import com.jumbo.dao.warehouse.QueueLogStaPaymentDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.QueueStaPaymentDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaPaymentDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhOrderSpecialExecuteDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.TransUpgradeType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.SkuWarrantyCardType;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.PackingType;
import com.jumbo.wms.model.warehouse.QueueGiftLine;
import com.jumbo.wms.model.warehouse.QueueLogGiftLine;
import com.jumbo.wms.model.warehouse.QueueLogSta;
import com.jumbo.wms.model.warehouse.QueueLogStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueLogStaInvoice;
import com.jumbo.wms.model.warehouse.QueueLogStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueLogStaLine;
import com.jumbo.wms.model.warehouse.QueueLogStaPayment;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaPayment;
import com.jumbo.wms.model.warehouse.SpecialSkuType;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaPayment;
import com.jumbo.wms.model.warehouse.StaSpecialExecute;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhOrderSpecialExecute;

@Transactional
@Service("createStaManagerExecu")
public class CreateStaManagerExecuImpl implements CreateStaManagerExecu {
    protected static final Logger log = LoggerFactory.getLogger(CreateStaManagerExecuImpl.class);
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaPaymentDao staPaymentDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private QueueGiftLineDao queueGiftLineDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StaSpecialExecutedDao staSpecialExecutedDao;
    @Autowired
    private QueueStaInvoiceLineDao queuestaInvoiceLineDao;
    @Autowired
    private StaInvoiceLineDao staInvoiceLineDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueStaPaymentDao queueStaPaymentDao;
    @Autowired
    private QueueLogStaPaymentDao queueLogStaPaymentDao;
    @Autowired
    private QueueStaDeliveryInfoDao queueStaDeliveryInfoDao;
    @Autowired
    private QueueStaInvoiceDao queueStaInvoiceDao;
    @Autowired
    private QueueLogStaDao queueLogStaDao;
    @Autowired
    private QueueLogStaDeliveryInfoDao queueLogStaDeliveryInfoDao;
    @Autowired
    private QueueLogStaLineDao queueLogStaLineDao;
    @Autowired
    private QueueLogGiftLineDao logGiftLineDao;
    @Autowired
    private QueueLogStaInvoiceDao queueLogStaInvoiceDao;
    @Autowired
    private QueueStaInvoiceLineDao queueStaInvoiceLineDao;
    @Autowired
    private QueueLogStaInvoiceLineDao queueLogStaInvoiceLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WhOrderSpecialExecuteDao whOrderSpecialExecuteDao;

    @Override
    public void createStaForSales(QueueSta qsta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> queueStaLines, List<QueueStaInvoice> invoices) {
        if (log.isInfoEnabled()) {
            log.info("check createStaForSales and create sta by batchCode start,ouId is:{}, batchCode is:{}", qsta.getMainwhouid(), qsta.getBatchcode());
        }
        // 创建STA
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(qsta.getMainwhouid());
        sta.setRefSlipCode(qsta.getOrdercode());
        sta.setSlipCode1(qsta.getSlipcode1());
        sta.setSlipCode2(qsta.getSlipcode2());
        sta.setSlipCode3(qsta.getSlipCode3());
        sta.setOwner(qsta.getOwner());
        sta.setAddiOwner(qsta.getAddOwner());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.valueOf(qsta.getType()));
        sta.setRefSlipType(SlipType.SALES_ORDER);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setChannelList(qsta.getChannelList());
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(ou1);
        sta.setTotalActual(qsta.getTotalactual());
        sta.setOrderCreateTime(qsta.getOrdercreatetime());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setActivitySource(qsta.getActivitysource());
        sta.setIsSpecialPackaging(qsta.getIsspecialpackaging());
        sta.setSpecialType(qsta.getSpecialType());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setOrderTotalActual(qsta.getOrdertaotalactual());
        sta.setOrderTransferFree(qsta.getOrdertransferfree());
        sta.setToLocation(qsta.getToLocation());
        sta.setPlanOutboundTime(qsta.getPlanOutboundTime());
        sta.setStorecode(qsta.getStorecode());
        sta.setStorePlanArriveTime(qsta.getPlanArriveTime());
        sta.setHkArriveTime(qsta.getArriveTime());
        sta.setOrderSourcePlatform(qsta.getOrderSourcePlatform());
        sta.setIsMacaoOrder(qsta.getIsMacaoOrder());// 是否澳门件
        // 平日送
        if (sta.getHkArriveTime() != null) {
            Date date = new Date();
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            String datetime = dateFormater.format(date);
            if (!datetime.equals(dateFormater.format(sta.getHkArriveTime()))) {
                if (date.getTime() < qsta.getArriveTime().getTime()) {
                    sta.setIsLocked(true);
                }
            }
        }
        // WMS预计送达时间
        BiChannel bichannel = companyShopDao.getByCode(sta.getOwner());
        bichannel.setIsTransUpgrade(bichannel.getIsTransUpgrade() == null ? false : bichannel.getIsTransUpgrade());
        bichannel.setIsLockRransUpgrade(bichannel.getIsLockRransUpgrade() == null ? false : bichannel.getIsTransUpgrade());
        // 判断是渠道是否升单
        if (log.isDebugEnabled()) {
            log.debug("qstaId:{}, planOutboundTime:{}", qsta.getId(), qsta.getPlanOutboundTime());
        }
        if (qsta.getPlanOutboundTime() != null) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{},bichannel is:{}, bichannel isTransUpgrade:{}", new Object[] {qsta.getId(), bichannel.getCode(), bichannel.getIsTransUpgrade()});
            }
            if (bichannel.getIsTransUpgrade()) {
                // 判断平台是否需要升单
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{},qsta isTransUpgrade:{}, bichannel isLockRransUpgrade:{}", new Object[] {qsta.getId(), qsta.getIsTransUpgrade(), bichannel.getIsLockRransUpgrade()});
                }
                if (qsta.getIsTransUpgrade()) {

                    // 是否需要升单锁定
                    if (bichannel.getIsLockRransUpgrade()) {
                        sta.setIsLocked(true);
                        sta.setIsTransUpgrade(true);
                        sta.setTransUpgradeType(TransUpgradeType.STORE_REQUIREMENET);
                    } else {
                        sta.setIsTransUpgrade(true);
                        sta.setIsLocked(false);
                        sta.setTransUpgradeType(TransUpgradeType.STORE_REQUIREMENET);
                    }
                    // 根据时间判断是否升单
                } else {
                    // 获取当前时间
                    Calendar calendar = Calendar.getInstance();
                    String dateTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                    // 获取预计发货日期
                    Calendar date = Calendar.getInstance();
                    date.setTime(qsta.getPlanArriveTime());
                    String arriveTime = date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, qsta arriveTime:{}, current dateTime:{}, bichannel isLockRransUpgrade:{}", new Object[] {qsta.getId(), arriveTime, dateTime, bichannel.getIsLockRransUpgrade()});
                    }
                    // 同天逻辑
                    if (arriveTime.equals(dateTime)) {
                        if (bichannel.getIsLockRransUpgrade()) {
                            // 当前时间大于16点
                            if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                // 预计发货时间大于16
                                if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 不进行升单
                                    sta.setIsTransUpgrade(false);
                                    sta.setIsLocked(false);
                                } else {
                                    // 预计发货时间小于16点需要进行升单
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(true);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                }
                            } else {
                                sta.setIsTransUpgrade(false);
                                sta.setIsLocked(false);
                            }
                        } else {
                            // 当前时间大于16点
                            if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                // 预计发货时间大于16
                                if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 不进行升单
                                    sta.setIsTransUpgrade(false);
                                    sta.setIsLocked(false);
                                } else {
                                    // 预计发货时间小于16点需要进行升单
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(false);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                }
                            } else {
                                sta.setIsTransUpgrade(false);
                                sta.setIsLocked(false);
                            }
                        }
                        // 隔天逻辑
                    } else {
                        if (bichannel.getIsLockRransUpgrade()) {
                            // 获取相隔天数
                            int month = calendar.get(Calendar.DAY_OF_MONTH) - date.get(Calendar.DAY_OF_MONTH);
                            // 等于一天
                            if (month == 1) {
                                // 当前时间大于16点
                                if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 预计发货时间大于16点
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(true);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);

                                } else {
                                    // 判断预计发货时间大于16点
                                    if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                        // 无需升单
                                        sta.setIsTransUpgrade(false);
                                        sta.setIsLocked(false);
                                    } else {
                                        // 预计发货时间小于16点需要进行升单
                                        sta.setIsTransUpgrade(true);
                                        sta.setIsLocked(true);
                                        sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                    }
                                }
                            } else if (month > 1) {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(true);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            } else {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(true);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            }
                        } else {
                            // 获取相隔天数
                            int month = calendar.get(Calendar.MONTH) - date.get(Calendar.MONDAY);
                            // 等于一天
                            if (month == 1) {
                                // 当前时间大于16点
                                if (calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
                                    // 预计发货时间大于16点
                                    sta.setIsTransUpgrade(true);
                                    sta.setIsLocked(false);
                                    sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);

                                } else {
                                    // 判断预计发货时间大于16点
                                    if (date.get(Calendar.HOUR_OF_DAY) >= 16) {
                                        // 无需升单
                                        sta.setIsTransUpgrade(false);
                                        sta.setIsLocked(false);
                                    } else {
                                        // 预计发货时间小于16点需要进行升单
                                        sta.setIsTransUpgrade(true);
                                        sta.setIsLocked(false);
                                        sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                                    }
                                }
                            } else if (month > 1) {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(false);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            } else {
                                // 相隔超过一天升单
                                sta.setIsTransUpgrade(true);
                                sta.setIsLocked(false);
                                sta.setTransUpgradeType(TransUpgradeType.WMS_DELAY);
                            }
                        }
                    }
                }
            }
        }
        // 澳门件判断是否需要打印海关单
        if (qsta != null && qsta.getIsMacaoOrder() != null && qsta.getIsMacaoOrder()) {
            if (bichannel.getIsPrintMaCaoHGD() != null && bichannel.getIsPrintMaCaoHGD()) {
                // 勾选了需要打印海关单开关并且金额高于设定值
                long totalactual = qsta.getTotalactual() == null ? 0l : qsta.getTotalactual().longValue();
                long setMoneyLmit = bichannel.getMoneyLmit() == null ? 0l : bichannel.getMoneyLmit().longValue();
                if (totalactual >= setMoneyLmit) {
                    sta.setIsPrintMaCaoHGD(true);
                } else {
                    sta.setIsPrintMaCaoHGD(false);
                }
            }
        }
        staDao.save(sta);
        // 创建特殊类型，删除特殊类型中间表,记录日志表
        List<WhOrderSpecialExecute> spExe = whOrderSpecialExecuteDao.getByQueueId(qsta.getId());
        if (spExe != null&&spExe.size()>0) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, qsta order special execute is not null,save special execute", qsta.getId());
            }
            for(WhOrderSpecialExecute whOrderSpecialExecute:spExe){
                StaSpecialExecute staSpExe = new StaSpecialExecute();
                staSpExe.setSta(sta);
                staSpExe.setType(whOrderSpecialExecute.getType());
                staSpExe.setMemo(whOrderSpecialExecute.getMemo());
                staSpecialExecutedDao.save(staSpExe);
            }
           
            // 删除特殊类型中间表,记录日志表
            // whOrderSpecialExecuteDao.deleteByPrimaryKey(spExe.getId());
            // whOrderSpecialExecuteDao.inserSpecialExecuteLog(qsta.getId(),
            // spExe.getType().getValue(), spExe.getMemo());
        }
        // 创建物流信息
        StaDeliveryInfo di = null;
        if (deliveryInfo != null) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, qsta delivery info is not null,save sta delivery info", qsta.getId());
            }
            di = new StaDeliveryInfo();
            di.setCountry(deliveryInfo.getCountry());
            di.setTrackingNo(deliveryInfo.getTrackingno());
            di.setProvince(deliveryInfo.getProvince());
            di.setCity(deliveryInfo.getCity());
            di.setDistrict(deliveryInfo.getDistrict());
            di.setAddress(deliveryInfo.getAddress());
            di.setTelephone(deliveryInfo.getTelephone());
            di.setIsCodPos(deliveryInfo.getIsCodPos());
            di.setConvenienceStore(deliveryInfo.getConvenienceStore());
            di.setMobile(deliveryInfo.getMobile());
            di.setReceiver(deliveryInfo.getReceiver());
            di.setZipcode(deliveryInfo.getZipcode());
            di.setIsCod(deliveryInfo.getIscode());
            di.setLpCode(deliveryInfo.getLpcode());
            if ("SFCOD".equals(deliveryInfo.getLpcode())) {
                di.setLpCode("SF");
            }
            if ("EMSCOD".equals(deliveryInfo.getLpcode())) {
                di.setLpCode("EMS");
            }
            if ("JDCOD".equals(deliveryInfo.getLpcode())) {
                di.setLpCode("JD");
            }

            di.setStoreComIsNeedInvoice(qsta.getIsInvoice());
            di.setAddressEn(deliveryInfo.getAddressEn());
            di.setCityEn(deliveryInfo.getCityEn());
            di.setCountryEn(deliveryInfo.getCountryEn());
            di.setDistrictEn(deliveryInfo.getDistrictEn());
            di.setProvinceEn(deliveryInfo.getProvinceEn());
            di.setReceiverEn(deliveryInfo.getReceiverEn());
            di.setRemarkEn(deliveryInfo.getRemarkEn());
            di.setRemark(deliveryInfo.getRemark());
            di.setTransferFee(qsta.getTransferfee());
            di.setTransType(TransType.valueOf(deliveryInfo.getTranstype()));
            di.setTransTimeType(TransTimeType.valueOf(deliveryInfo.getTranstimetype()));
            di.setTransMemo(deliveryInfo.getTransmemo());
            // BiChannel bichannel = companyShopDao.getByCode(sta.getOwner());
            List<BiChannelSpecialAction> bsa = biChannelSpecialActionDao.getChannelByAndType(bichannel.getId(), BiChannelSpecialActionType.PRINT_EXPRESS_BILL);
            if (bsa != null && bsa.size() > 0) {
                for (BiChannelSpecialAction bcsa : bsa) {
                    if (StringUtils.hasText(bcsa.getTransAddMemo())) {
                        di.setTransMemo(bcsa.getTransAddMemo());
                    }
                }
            }
            di.setId(sta.getId());
            di.setOrderUserCode(deliveryInfo.getOrderusercode());
            di.setOrderUserMail(deliveryInfo.getOrderusermail());
            di.setInsuranceAmount(deliveryInfo.getInsuranceAmount());
            di.setLastModifyTime(new Date());
            sta.setDeliveryType((deliveryInfo.getTranstype() == 6 ? TransDeliveryType.AVIATION : TransDeliveryType.ORDINARY));
            sta.setMemo(deliveryInfo.getRemark());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        }
        for (QueueStaLine staLine : queueStaLines) {
            if (staLine.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                Sku sku = skuDao.getByCode(staLine.getSkucode());
                if (sku.getSnType() != null) {
                    if (sku.getSnType() == SkuSnType.NO_BARCODE_SKU) {
                        sta.setIsBkCheck(true);
                    }
                }
                StaLine sl = new StaLine();
                if (staLine.getLineStatus() == QueueStaLineStatus.LINE_STATUS_TURE) {
                    sl.setSta(sta);
                    sl.setSku(sku);
                    sl.setOwner(staLine.getOwner());
                    sl.setQuantity(staLine.getQty());
                    sl.setVersion(1);
                    sl.setSkuName(staLine.getSkuName());
                    sl.setTotalActual(staLine.getTotalactual());
                    sl.setOrderTotalActual(staLine.getOrdertotalactual());
                    sl.setOrderTotalBfDiscount(staLine.getOrdertotalbfdiscount());
                    sl.setListPrice(staLine.getListprice());
                    sl.setUnitPrice(staLine.getUnitprice());
                    staLineDao.save(sl);
                }

                List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(staLine.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                if (giftLines != null && giftLines.size() > 0) {
                    // 判断是否是保修卡信息
                    boolean isWarrantyCard = (sku.getWarrantyCardType() != null && SkuWarrantyCardType.NO_CHECK.equals(sku.getWarrantyCardType()));
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, is no check warrantyCard:{}", qsta.getId(), isWarrantyCard);
                    }
                    for (QueueGiftLine giftLine : giftLines) {
                        GiftLine line = new GiftLine();
                        int temp = 0; // 用于计算非礼盒的类型
                        if (GiftType.CK_GIFT_BOX.getValue() != giftLine.getType()) {
                            temp++;
                        }
                        line.setType(GiftType.valueOf(giftLine.getType()));
                        if (GiftType.COACH_CARD.getValue() == giftLine.getType()) {
                            if (log.isDebugEnabled()) {
                                log.debug("qstaId:{},coach gift type:{}, is no check warrantyCard:{}", new Object[] {qsta.getId(), giftLine.getType(), isWarrantyCard});
                            }
                            if (!isWarrantyCard) continue; // 如果 是无需保修商品 那么OMS 过来的保修卡信息 将不做记录
                            try {
                                int month = Integer.valueOf(giftLine.getMemo());
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new Date());
                                cal.add(Calendar.MONTH, month);
                                line.setMemo(FormatUtil.formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
                            } catch (Exception e) {
                                throw new BusinessException(ErrorCode.GIFT_LINE_IS_NULL, new Object[] {sku.getCode(), 4});
                            }
                        } else if (GiftType.CK_GIFT_BOX.getValue() == giftLine.getType()) {
                            // 新增CK礼盒类型，判断如包含礼盒类型，则往作业单头上打标 by FXL
                            sta.setPackingType(PackingType.GIFT_BOX);
                            // 头特殊处理如果为null： 如果特殊包装明细里只包含礼盒包装，则不进入特殊处理逻辑
                            if (temp == 0 && spExe == null) {
                                sta.setSpecialType(null);
                            }
                            line.setMemo(giftLine.getMemo());
                        } else {
                            line.setMemo(giftLine.getMemo());
                        }
                        line.setStaLine(sl);
                        giftLineDao.save(line);
                    }
                }
            }
        }
        staLineDao.flush();
      /*  if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) {
            Boolean b = staLineDao.checkIsSpecailSku(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
            if (b) {
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{}, check staLine has specail sku, set sta isSpecialPackaging", qsta.getId(), true);
                }
                sta.setIsSpecialPackaging(true);
            }
        }*/
        // 特殊处理
        BiChannel cha = companyShopDao.getByCode(sta.getOwner());
        List<BiChannelSpecialAction> csaList = biChannelSpecialActionDao.getChannelByAndType(cha.getId(), BiChannelSpecialActionType.SPECIAL_PACKING);
        if (csaList != null && csaList.size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, bichannel special action is not null,save staSpecialExecute and sta specialType as:{}", qsta.getId(), SpecialSkuType.NORMAL);
            }
            for (BiChannelSpecialAction csa : csaList) {
                StaSpecialExecuteType type = null;
                try {
                    type = StaSpecialExecuteType.valueOf(Integer.valueOf(csa.getTemplateType()));
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.ERROR_SPECIAL_TEMPLATE_TYPE_ERROR);
                }
                StaSpecialExecute sse = new StaSpecialExecute();
                sse.setType(type);
                sse.setSta(sta);
                staSpecialExecutedDao.save(sse);
            }
            sta.setSpecialType(SpecialSkuType.NORMAL);
            // sta.setIsSpecialPackaging(true);
        }
        // 如果是Burberry 又是陆运 那么在备注里面存放 汽运
        if (Constants.BURBERRY_OWNER1.equals(sta.getOwner()) && (TransDeliveryType.LAND.equals(sta.getDeliveryType()) || TransDeliveryType.AVIATION.equals(sta.getDeliveryType()))) {
            di.setRemark("汽运");
        }
        if (qsta.getIsInvoice()) {
            if (log.isDebugEnabled()) {
                log.debug("qstaId:{}, qsta invoice is not null,save sta invoice", qsta.getId());
            }
            for (QueueStaInvoice queueStaInvoice : invoices) {
                StaInvoice invoice = new StaInvoice();
                invoice.setSta(sta);
                invoice.setPayer(queueStaInvoice.getPayer());
                invoice.setAmt(queueStaInvoice.getAmt());
                invoice.setDrawer(queueStaInvoice.getDrawer());
                invoice.setInvoiceDate(queueStaInvoice.getInvoiceDate());
                invoice.setItem(queueStaInvoice.getItem());
                invoice.setMemo(queueStaInvoice.getMemo());
                invoice.setPayee(queueStaInvoice.getPayee());
                invoice.setQty(queueStaInvoice.getQty());
                invoice.setUnitPrice(queueStaInvoice.getUnitPrice());
                invoice.setTelephone(queueStaInvoice.getTelephone());
                invoice.setIdentificationNumber(queueStaInvoice.getIdentificationNumber());
                invoice.setAddress(queueStaInvoice.getAddress());
                staInvoiceDao.save(invoice);
                List<QueueStaInvoiceLine> queueStaInvoiceLines = queuestaInvoiceLineDao.findByInvoiceId(queueStaInvoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                    StaInvoiceLine staInvoiceLine = new StaInvoiceLine();
                    staInvoiceLine.setAmt(queueStaInvoiceLine.getAmt());
                    staInvoiceLine.setItem(queueStaInvoiceLine.getItem());
                    staInvoiceLine.setQty(queueStaInvoiceLine.getQty());
                    staInvoiceLine.setUnitPrice(queueStaInvoiceLine.getUnitPrice());
                    staInvoiceLine.setStaInvoice(invoice);
                    staInvoiceLineDao.save(staInvoiceLine);
                }
            }
        }
        // 老的费用列表
        List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(qsta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
        for (QueueStaPayment wp : pays) {
            StaPayment sp = new StaPayment();
            sp.setAmt(wp.getAmt());
            sp.setType(wp.getType());
            sp.setMemo(wp.getMemo());
            sp.setSta(sta);
            staPaymentDao.save(sp);
        }

        staDao.save(sta);
        staDao.flush();
        if (log.isInfoEnabled()) {
            log.info("check TransactionalEvent and create sta by batchCode start,ouId is:{}, batchCode is:{}", qsta.getMainwhouid(), qsta.getBatchcode());
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        if (log.isInfoEnabled()) {
            log.info("check inventory and create sta by batchCode start,ouId is:{}, batchCode is:{}", qsta.getMainwhouid(), qsta.getBatchcode());
        }

    }

    /**
     * 调用OMS接口
     * 
     * 1.OMS成功，无需转仓，删除队列数据，记录日志
     * 
     * 2.OMS成功，需转仓，修改队列仓库，记录日志
     * 
     * 3.OMS接口无法连接或系统 throw BusinessException ErrorCode=SYSTEM_ERROR 回滚事务
     * 
     * 4.OMS反馈失败或单据取消 throw BusinessException ErrorCode=OMS_SYSTEM_ERROR 回滚事务
     */
    public void sendCreateResultToOms(StaCreatedResponse createdResponse, Long qstaId) {
        if (log.isInfoEnabled()) {
            log.info("sendCreateResultToOms start, qstaId:{}", qstaId);
        }
        if (createdResponse != null) {
            // 调用OMS接口
            BaseResult result = null;
            try {
                // 记录日志表信息
                createLogSta(qstaId, createdResponse);
                if (log.isInfoEnabled()) {
                    log.info("rmi sendCreateResultToOms wmsCreateStaFeedback start, qstaId:{}", qstaId);
                }
                result = rmi4Wms.wmsCreateStaFeedback(createdResponse);
                if (log.isInfoEnabled()) {
                    log.info("rmi sendCreateResultToOms wmsCreateStaFeedback end, qstaId:{}, slipCode:{}, status:{}", new Object[] {qstaId, result.getSlipCode(), result.getStatus()});
                }
            } catch (Exception e) {
                log.error("error when connect oms to wmsCreateStaFeedback");
                // 接口异常
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
            if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_SUCCESS) {
                // ===========反馈成功,判断OMS是否需要修改仓库================
                if (result.getWhCode() != null) {
                    // ==============判断是否需要修改仓库ID========
                    // 修改仓库ID
                    queueSta.setMainWhOuCode(result.getWhCode());
                    OperationUnit mainWhOu = operationUnitDao.getByCode(result.getWhCode());
                    if (mainWhOu == null || !OperationUnitType.OUTYPE_WAREHOUSE.equals(mainWhOu.getOuType().getName())) {
                        throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {result.getWhCode()});
                    }
                    queueSta.setMainwhouid(mainWhOu.getId());
                } else {
                    // ============= 删除队列数据===============
                    // 删除中间表信息
                    List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                    for (QueueStaInvoice invoice : invoices) {
                        List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                        for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                            queueStaInvoiceLineDao.deleteByPrimaryKey(queueStaInvoiceLine.getId());
                        }
                        queueStaInvoiceDao.deleteByPrimaryKey(invoice.getId());
                    }

                    List<QueueStaLine> ql = queueStaLineDao.findByStaId(qstaId);
                    for (QueueStaLine line : ql) {
                        queueStaLineDao.delete(line);
                    }
                    queueStaLineDao.flush();
                    // 旧 删除 费用列表
                    List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
                    for (QueueStaPayment queueStaPayment : pays) {
                        queueStaPaymentDao.deleteStaPayment(queueStaPayment.getId());
                    }
                    int status = queueStaDao.deleteQueuesta(qstaId);
                    if (status == 1) {} else {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {qstaId});
                    }
                    if (queueSta.getQueueStaDeliveryInfo() != null) {
                        queueStaDeliveryInfoDao.delete(queueSta.getQueueStaDeliveryInfo());
                    }
                }
            } else if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_CANCEL) {
                // // OMS单据取消抛出异常
                // List<QueueStaLine> ql = queueStaLineDao.findByStaId(qstaId);
                // for (QueueStaLine line : ql) {
                // queueStaLineDao.delete(line);
                // }
                // queueStaDao.delete(queueSta);
                // if (queueSta.getQueueStaDeliveryInfo() != null) {
                // queueStaDeliveryInfoDao.delete(queueSta.getQueueStaDeliveryInfo());
                // }
                throw new BusinessException(ErrorCode.OMS_ORDER_CANACEL);
            } else if (result.getStatus() == BaseResult.BASE_RESULT_STATUS_ERROR) {
                // OMS系统异常
                throw new BusinessException(ErrorCode.OMS_OUT_ERROR);
            } else {
                // 系统异常
                log.error("when send message to oms,the message is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("sendCreateResultToOms end, qstaId:{}", qstaId);
        }
    }

    public void createLogSta(Long qStaId, StaCreatedResponse error) {
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        QueueStaDeliveryInfo deliveryInfo = queueSta.getQueueStaDeliveryInfo();
        List<QueueStaLine> staLines = queueStaLineDao.queryStaId(qStaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(qStaId, new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
        QueueLogSta logsta = new QueueLogSta();
        logsta.setOrdercode(queueSta.getOrdercode());
        logsta.setSlipcode1(queueSta.getSlipcode1());
        logsta.setSlipcode2(queueSta.getSlipcode2());
        logsta.setSlipCode3(queueSta.getSlipCode3());
        logsta.setStorecode(queueSta.getStorecode());
        logsta.setMainwhouid(queueSta.getMainwhouid());
        logsta.setAddwhouid(queueSta.getAddwhouid() == null ? 0 : queueSta.getAddwhouid());
        logsta.setIscreatedlocked(queueSta.getIscreatedlocked());
        logsta.setOwner(queueSta.getOwner());
        logsta.setQstaId(queueSta.getId());
        logsta.setCreatetime(queueSta.getCreatetime());
        logsta.setAddOwner(queueSta.getAddOwner());
        logsta.setBatchCode(queueSta.getBatchcode());
        logsta.setArriveTime(queueSta.getArriveTime());
        logsta.setArriveTimeType(queueSta.getArriveTimeType());
        logsta.setType(queueSta.getType());
        logsta.setIsspecialpackaging(queueSta.getIsspecialpackaging());
        logsta.setTotalactual(queueSta.getTotalactual());
        logsta.setOrdertaotalactual(queueSta.getOrdertaotalactual());
        logsta.setTransferfee(queueSta.getTransferfee());
        logsta.setOrdertransferfree(queueSta.getOrdertransferfree());
        logsta.setOrdertotalbfdiscount(queueSta.getOrdertotalbfdiscount());
        logsta.setOrdercreatetime(queueSta.getOrdercreatetime());
        logsta.setIsInvoice(queueSta.getIsInvoice());
        logsta.setChannelList(queueSta.getChannelList());
        logsta.setCustomerId(queueSta.getCustomerId());
        logsta.setCodAmount(queueSta.getCodAmount());
        logsta.setMainWhOuCode(queueSta.getMainWhOuCode());
        logsta.setAddWhOuCode(queueSta.getAddWhOuCode());
        logsta.setLogtime(new Date());
        logsta.setToLocation(queueSta.getToLocation());
        logsta.setIsPreSale(queueSta.getIsPreSale());// 是否预售
        logsta.setIsOnlineInvoice(queueSta.getIsOnlineInvoice());
        logsta.setIsMacaoOrder(queueSta.getIsMacaoOrder());// 是否澳门件
        logsta.setExtMemo2(queueSta.getExtMemo2());
        if (queueSta.getIsTransUpgrade() != null && queueSta.getIsTransUpgrade()) {
            logsta.setTransUpgrade(true);
        } else {
            logsta.setTransUpgrade(false);
        }
        logsta.setPlanArriveTime(queueSta.getPlanArriveTime());
        logsta.setPlanOutboundTime(queueSta.getPlanOutboundTime());
        logsta.setSpecialType(queueSta.getSpecialType());
        if (error != null) {
            if (StringUtils.hasText(error.getMsg())) {
                if (error != null && error.getSoLineResponses() != null) {
                    for (int i = 0; i < error.getSoLineResponses().size(); i++) {
                        if (logsta.getErrormsg() == null) {
                            logsta.setErrormsg("");
                        }
                        logsta.setErrormsg(logsta.getErrormsg() + "[" + error.getSoLineResponses().get(i).getJmSkuCode() + "缺少" + error.getSoLineResponses().get(i).getQty() + "]");
                    }

                }
                if (logsta.getErrormsg().length() > 500) {
                    logsta.setErrormsg(logsta.getErrormsg().substring(0, 400));
                }
            }
        } else {
            logsta.setErrormsg("OMS取消订单");
        }

        logsta.setStatus(1);
        logsta.setActivitysource(queueSta.getActivitysource());
        queueLogStaDao.save(logsta);
        queueLogStaDao.flush();
        if (deliveryInfo != null) {
            QueueLogStaDeliveryInfo di = new QueueLogStaDeliveryInfo();
            di.setId(logsta.getId());
            di.setCountry(deliveryInfo.getCountry());
            di.setProvince(deliveryInfo.getProvince());
            di.setCity(deliveryInfo.getCity());
            di.setDistrict(deliveryInfo.getDistrict());
            di.setAddress(deliveryInfo.getAddress());
            di.setTelephone(deliveryInfo.getTelephone());
            di.setMobile(deliveryInfo.getMobile());
            di.setReceiver(deliveryInfo.getReceiver());
            di.setZipcode(deliveryInfo.getZipcode());
            di.setIscode(deliveryInfo.getIscode());
            di.setLpcode(deliveryInfo.getLpcode());
            di.setIsCodPos(deliveryInfo.getIsCodPos());
            di.setConvenienceStore(deliveryInfo.getConvenienceStore());
            di.setTrackingno(deliveryInfo.getTrackingno());
            di.setRemark(deliveryInfo.getRemark());
            di.setTranstype(deliveryInfo.getTranstimetype());
            di.setAddressEn(deliveryInfo.getAddressEn());
            di.setCityEn(deliveryInfo.getCityEn());
            di.setCountryEn(deliveryInfo.getCountryEn());
            di.setDistrictEn(deliveryInfo.getDistrictEn());
            di.setProvinceEn(deliveryInfo.getProvinceEn());
            di.setReceiverEn(deliveryInfo.getReceiverEn());
            di.setRemarkEn(deliveryInfo.getRemarkEn());
            di.setTranstimetype(deliveryInfo.getTranstimetype());
            di.setSender(deliveryInfo.getSender());
            di.setSendLpcode(deliveryInfo.getSendLpcode());
            di.setSendMobile(deliveryInfo.getSendMobile());
            di.setSendTransNo(deliveryInfo.getSendTransNo());
            di.setTransmemo(deliveryInfo.getTransmemo());
            di.setOrderusermail(deliveryInfo.getOrderusermail());
            di.setOrderusercode(deliveryInfo.getOrderusercode());
            di.setInsuranceAmount(deliveryInfo.getInsuranceAmount());
            queueLogStaDeliveryInfoDao.save(di);
        }
        for (int i = 0; i < staLines.size(); i++) {
            QueueLogStaLine logStaLine = new QueueLogStaLine();
            logStaLine.setSkucode(staLines.get(i).getSkucode());
            logStaLine.setQty(staLines.get(i).getQty());
            logStaLine.setTotalactual(staLines.get(i).getTotalactual());
            logStaLine.setOrdertotalactual(staLines.get(i).getOrdertotalactual());
            logStaLine.setListprice(staLines.get(i).getListprice());
            logStaLine.setUnitprice(staLines.get(i).getUnitprice());
            logStaLine.setDirection(staLines.get(i).getDirection());
            logStaLine.setOwner(staLines.get(i).getOwner());
            logStaLine.setInvstatusid(staLines.get(i).getInvstatusid() == null ? 1 : staLines.get(i).getInvstatusid());
            logStaLine.setQueueLogSta(logsta);
            logStaLine.setSkuName(staLines.get(i).getSkuName());
            logStaLine.setLineStatus(staLines.get(i).getLineStatus());
            logStaLine.setLineType(staLines.get(i).getLineType());
            logStaLine.setActivitysource(staLines.get(i).getActivitysource());
            logStaLine.setOrdertotalbfdiscount(staLines.get(i).getOrdertotalbfdiscount());
            queueLogStaLineDao.save(logStaLine);
            List<QueueGiftLine> giftLines = queueGiftLineDao.getByfindQstaline(staLines.get(i).getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
            if (giftLines != null) {
                for (QueueGiftLine giftLine : giftLines) {
                    QueueLogGiftLine logGiftLine = new QueueLogGiftLine();
                    logGiftLine.setMemo(giftLine.getMemo());
                    logGiftLine.setQueueLogStaLine(logStaLine);
                    logGiftLine.setType(giftLine.getType());
                    logGiftLineDao.save(logGiftLine);
                    queueGiftLineDao.delete(giftLine);
                }
            }
        }
        if (queueSta.getIsInvoice()) {
            for (QueueStaInvoice queueStaInvoice : invoices) {
                QueueLogStaInvoice logStaInvoice = new QueueLogStaInvoice();
                logStaInvoice.setAmt(queueStaInvoice.getAmt());
                logStaInvoice.setDrawer(queueStaInvoice.getDrawer());
                logStaInvoice.setInvoiceDate(queueStaInvoice.getInvoiceDate());
                logStaInvoice.setItem(queueStaInvoice.getItem());
                logStaInvoice.setMemo(queueStaInvoice.getMemo());
                logStaInvoice.setPayee(queueStaInvoice.getPayee());
                logStaInvoice.setPayer(queueStaInvoice.getPayer());
                logStaInvoice.setqlgStaId(logsta);
                logStaInvoice.setQty(queueStaInvoice.getQty());
                logStaInvoice.setUnitPrice(queueStaInvoice.getUnitPrice());
                queueLogStaInvoiceDao.save(logStaInvoice);
                List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(queueStaInvoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                    QueueLogStaInvoiceLine queueLogStaInvoiceLine = new QueueLogStaInvoiceLine();
                    queueLogStaInvoiceLine.setAmt(queueStaInvoiceLine.getAmt());
                    queueLogStaInvoiceLine.setItem(queueStaInvoiceLine.getItem());
                    queueLogStaInvoiceLine.setQty(queueStaInvoiceLine.getQty());
                    queueLogStaInvoiceLine.setUnitPrice(queueStaInvoiceLine.getUnitPrice());
                    queueLogStaInvoiceLine.setQueueLogStaInvoice(logStaInvoice);
                    queueLogStaInvoiceLineDao.save(queueLogStaInvoiceLine);
                }

            }
        }
        List<WhOrderSpecialExecute> spExe = whOrderSpecialExecuteDao.getByQueueId(queueSta.getId());
        if (spExe != null) {
            for(WhOrderSpecialExecute whOrderSpecialExecute:spExe){
                // 删除特殊类型中间表,记录日志表
                whOrderSpecialExecuteDao.deleteByPrimaryKey(whOrderSpecialExecute.getId());
                whOrderSpecialExecuteDao.inserSpecialExecuteLog(logsta.getId(), whOrderSpecialExecute.getType().getValue(), whOrderSpecialExecute.getMemo()); 
            }
        }
        // 旧 费用列表log
        List<QueueStaPayment> pays = queueStaPaymentDao.findByStaPaymentId(queueSta.getId(), new BeanPropertyRowMapperExt<QueueStaPayment>(QueueStaPayment.class));
        for (QueueStaPayment queueStaPayment : pays) {
            QueueLogStaPayment logStaPayment = new QueueLogStaPayment();
            logStaPayment.setAmt(queueStaPayment.getAmt());
            logStaPayment.setMemo(queueStaPayment.getMemo());
            logStaPayment.setqStaId(logsta);
            queueLogStaPaymentDao.save(logStaPayment);
        }

    }

    @Override
    public void createSta(QueueSta queueSta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> staLines, List<QueueStaInvoice> invoices) {
        if (log.isInfoEnabled()) {
            log.info("createSta sendCreateResultToOms start, qstaId:{}", queueSta.getId());
        }
        createStaForSales(queueSta, deliveryInfo, staLines, invoices);
        StaCreatedResponse createdResponse = new StaCreatedResponse();
        if (queueSta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
            createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
        } else {
            createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
        }
        createdResponse.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_SUCCESS);
        createdResponse.setSlipCode(queueSta.getOrdercode());
        // 反馈OMS成功
        sendCreateResultToOms(createdResponse, queueSta.getId());
        if (log.isInfoEnabled()) {
            log.info("createSta sendCreateResultToOms end, qstaId:{}", queueSta.getId());
        }
    }

}
