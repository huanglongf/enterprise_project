package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.QueueGiftLineDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhOrderSpecialExecuteDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.TransUpgradeType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.SkuWarrantyCardType;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackingType;
import com.jumbo.wms.model.warehouse.QueueGiftLine;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineCommand;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.SpecialSkuType;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaSpecialExecute;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhOrderSpecialExecute;


@Transactional
@Service("createStaManager")
public class CreateStaManagerImpl extends BaseManagerImpl implements CreateStaManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = -6186238879059460822L;


    final Logger logger = LoggerFactory.getLogger(CreateStaManagerImpl.class);


    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private ChannelWhRefRefDao warehouseShopRefDao;
    @Autowired
    private VmiFactory vmiFactory;
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
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WhOrderSpecialExecuteDao whOrderSpecialExecuteDao;

    @Override
    public boolean createStaForSales(QueueSta qsta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> queueStaLines, List<QueueStaInvoice> invoices) {
        // 创建STA
        if (log.isDebugEnabled()) {
            log.debug("create sta for sales start, qstaId:{}, sta type:{}", new Object[] {qsta.getId(), qsta.getType()});
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(qsta.getMainwhouid());
        sta.setRefSlipCode(qsta.getOrdercode());
        sta.setSlipCode1(qsta.getSlipcode1());
        sta.setSlipCode2(qsta.getSlipcode2());
        sta.setSlipCode3(qsta.getSlipCode3());
        sta.setOwner(qsta.getOwner());
        sta.setStorecode(qsta.getStorecode());
        sta.setAddiOwner(qsta.getAddOwner());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.valueOf(qsta.getType()));
        sta.setHkArriveTime(qsta.getArriveTime());
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
        sta.setToLocation(qsta.getToLocation());
        if (StockTransApplicationType.OUTBOUND_SALES.getValue() == qsta.getType()) {
            sta.setRefSlipType(SlipType.SALES_ORDER);
        } else {
            sta.setRefSlipType(SlipType.RETURN_REQUEST);
            sta.setIsLocked(true);
        }
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setChannelList(qsta.getChannelList());
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(ou1);
        InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(qsta.getInvstatusid(), ou1.getParentUnit().getParentUnit().getId());
        sta.setMainStatus(invStatus);
        sta.setTotalActual(qsta.getTotalactual());
        sta.setOrderCreateTime(qsta.getOrdercreatetime());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setActivitySource(qsta.getActivitysource());
        sta.setIsSpecialPackaging(qsta.getIsspecialpackaging());
        sta.setSpecialType(qsta.getSpecialType());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setOrderTotalActual(qsta.getOrdertaotalactual());
        sta.setOrderTransferFree(qsta.getOrdertransferfree());
        sta.setPlanOutboundTime(qsta.getPlanOutboundTime());
        sta.setStorePlanArriveTime(qsta.getPlanArriveTime());
        sta.setIsMacaoOrder(qsta.getIsMacaoOrder());// 是否澳门件
        // 预计发货时间
        // WMS预计送达时间
        BiChannel bichannel = companyShopDao.getByCode(sta.getOwner());
        bichannel.setIsTransUpgrade(bichannel.getIsTransUpgrade() == null ? false : bichannel.getIsTransUpgrade());
        bichannel.setIsLockRransUpgrade(bichannel.getIsLockRransUpgrade() == null ? false : bichannel.getIsTransUpgrade());
        // 判断是渠道是否升单
        if (qsta.getPlanOutboundTime() != null) {

            if (bichannel.getIsTransUpgrade()) {
                // 判断平台是否需要升单
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
                                sta.setIsLocked(false);
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
        // 是否判断是不是陆运
        // boolean isIfLand = di == null ? false : (di.getTransType() == null ? true :
        // (!TransType.LAND.equals(di.getTransType())));
        // 是否陆运
        // boolean isLand = false;
        for (QueueStaLine staLine : queueStaLines) {
            if (staLine.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                Sku sku = skuDao.getByCode(staLine.getSkucode());
                if (sku.getSnType() != null) {
                    if (sku.getSnType() == SkuSnType.NO_BARCODE_SKU) {
                        sta.setIsBkCheck(true);
                    }
                }
                // if (isIfLand && !isLand) {
                // isLand = sku.getIsRailway() == null ? false : sku.getIsRailway();
                // }
                StaLine sl = new StaLine();
                if (staLine.getLineStatus() == QueueStaLineStatus.LINE_STATUS_TURE) {
                    sl.setSta(sta);
                    sl.setSku(sku);
                    if (!StringUtils.hasText(staLine.getOwner())) {
                        sl.setOwner(qsta.getOwner());
                    } else {
                        sl.setOwner(staLine.getOwner());
                    }
                    sl.setQuantity(staLine.getQty());
                    sl.setInvStatus(invStatus);
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

                    for (QueueGiftLine giftLine : giftLines) {
                        GiftLine line = new GiftLine();
                        line.setType(GiftType.valueOf(giftLine.getType()));
                        if (GiftType.COACH_CARD.getValue() == giftLine.getType()) {
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
                            // 如果特殊包装明细里只包含礼盒包装，则不进入特殊处理逻辑
                            if (giftLines.size() == 1) {
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
       /* if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) {
            Boolean b = staLineDao.checkIsSpecailSku(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
            if (b) {
                sta.setIsSpecialPackaging(true);
            }
        }*/
        // 特殊处理
        BiChannel cha = companyShopDao.getByCode(sta.getOwner());
        List<BiChannelSpecialAction> csaList = biChannelSpecialActionDao.getChannelByAndType(cha.getId(), BiChannelSpecialActionType.SPECIAL_PACKING);
        if (csaList != null && csaList.size() > 0) {
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
        }

        // Coach 特殊处理（小票打印）
        // if (isIfLand && isLand) {
        // di.setTransType(TransType.LAND);
        // staDeliveryInfoDao.save(di);
        // }
        // 如果是Burberry 又是陆运 那么在备注里面存放 汽运
        if (Constants.BURBERRY_OWNER1.equals(sta.getOwner()) && (TransDeliveryType.LAND.equals(sta.getDeliveryType()) || TransDeliveryType.AVIATION.equals(sta.getDeliveryType()))) {
            di.setRemark("汽运");
        }
        if (qsta.getIsInvoice()) {
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
                invoice.setAddress(queueStaInvoice.getAddress());
                invoice.setIdentificationNumber(queueStaInvoice.getIdentificationNumber());
                invoice.setTelephone(queueStaInvoice.getTelephone());
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
        staDao.save(sta);
        staDao.flush();
        if (log.isInfoEnabled()) {
            log.info("create sta for sales end, qstaId:{}, sta type:{}", new Object[] {qsta.getId(), qsta.getType()});
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        if (log.isInfoEnabled()) {
            log.info("create sta for sales end, qstaId:{}, sta type:{}", new Object[] {qsta.getId(), qsta.getType()});
        }

        return true;
    }

    @Override
    public boolean createStaForSalesAF(QueueSta qsta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLineCommand> commands, List<QueueStaInvoice> invoices, List<QueueStaLine> stalines) {
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
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        sta.setType(StockTransApplicationType.valueOf(qsta.getType()));
        if (StockTransApplicationType.OUTBOUND_SALES.getValue() == qsta.getType()) {
            sta.setRefSlipType(SlipType.SALES_ORDER);
        } else {
            sta.setRefSlipType(SlipType.RETURN_REQUEST);
            sta.setIsLocked(true);
        }
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setChannelList(qsta.getChannelList());
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(ou1);
        InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(qsta.getInvstatusid(), ou1.getParentUnit().getParentUnit().getId());
        sta.setMainStatus(invStatus);
        sta.setTotalActual(qsta.getTotalactual());
        sta.setOrderCreateTime(qsta.getOrdercreatetime());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setActivitySource(qsta.getActivitysource());
        sta.setIsSpecialPackaging(qsta.getIsspecialpackaging());
        sta.setOrderTotalBfDiscount(qsta.getOrdertotalbfdiscount());
        sta.setOrderTotalActual(qsta.getOrdertaotalactual());
        sta.setOrderTransferFree(qsta.getOrdertransferfree());
        staDao.save(sta);
        // 创建物流信息
        StaDeliveryInfo di = null;
        if (deliveryInfo != null) {
            di = new StaDeliveryInfo();
            di.setCountry(deliveryInfo.getCountry());
            di.setProvince(deliveryInfo.getProvince());
            di.setTrackingNo(deliveryInfo.getTrackingno());
            di.setCity(deliveryInfo.getCity());
            di.setDistrict(deliveryInfo.getDistrict());
            di.setIsCodPos(deliveryInfo.getIsCodPos());
            di.setConvenienceStore(deliveryInfo.getConvenienceStore());
            di.setAddress(deliveryInfo.getAddress());
            di.setTelephone(deliveryInfo.getTelephone());
            di.setMobile(deliveryInfo.getMobile());
            di.setReceiver(deliveryInfo.getReceiver());
            di.setZipcode(deliveryInfo.getZipcode());
            di.setIsCod(deliveryInfo.getIscode());
            di.setLpCode(deliveryInfo.getLpcode());
            di.setAddressEn(deliveryInfo.getAddressEn());
            di.setCityEn(deliveryInfo.getCityEn());
            di.setCountryEn(deliveryInfo.getCountryEn());
            di.setDistrictEn(deliveryInfo.getDistrictEn());
            di.setProvinceEn(deliveryInfo.getProvinceEn());
            di.setReceiverEn(deliveryInfo.getReceiverEn());
            di.setRemarkEn(deliveryInfo.getRemarkEn());
            di.setStoreComIsNeedInvoice(qsta.getIsInvoice());
            di.setRemark(deliveryInfo.getRemark());
            di.setTransferFee(qsta.getOrdertransferfree());
            di.setTransType(TransType.valueOf(deliveryInfo.getTranstype()));
            di.setTransTimeType(TransTimeType.valueOf(deliveryInfo.getTranstimetype()));
            di.setTransMemo(deliveryInfo.getTransmemo());
            BiChannel bichannel = companyShopDao.getByCode(sta.getOwner());
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
            sta.setMemo(deliveryInfo.getRemark());
            staDeliveryInfoDao.save(di);
            sta.setStaDeliveryInfo(di);
        }
        // 是否判断是不是陆运
        // boolean isIfLand = di == null ? false : (di.getTransType() == null ? true :
        // (!TransType.LAND.equals(di.getTransType())));
        // 是否陆运
        // boolean isLand = false;
        for (QueueStaLineCommand staLine : commands) {
            if (staLine.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                Sku sku = skuDao.getByCode(staLine.getSkucode());
                if (sku == null) {
                    throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
                }
                // if (isIfLand && !isLand) {
                // isLand = sku.getIsRailway() == null ? false : sku.getIsRailway();
                // }
                StaLine sl = new StaLine();
                if (staLine.getLineStatus() == QueueStaLineStatus.LINE_STATUS_TURE) {

                    sl.setSta(sta);
                    sl.setSku(sku);
                    sl.setOwner(staLine.getOwner());
                    sl.setQuantity(staLine.getQty());
                    sl.setInvStatus(invStatus);
                    sl.setVersion(1);
                    for (QueueStaLine line : stalines) {
                        if (line.getSkucode().equals(staLine.getSkucode())) {
                            sl.setSkuName(line.getSkuName());
                        }
                    }
                    sl.setTotalActual(staLine.getTotalactual());
                    sl.setOrderTotalActual(staLine.getOrdertotalactual());
                    sl.setOrderTotalBfDiscount(staLine.getOrdertotalbfdiscount());
                    sl.setListPrice(staLine.getListprice());
                    sl.setUnitPrice(staLine.getUnitprice());
                    staLineDao.save(sl);
                }
            }
        }

        staLineDao.flush();
        if (qsta.getIsInvoice()) {
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
                invoice.setAddress(queueStaInvoice.getAddress());
                invoice.setIdentificationNumber(queueStaInvoice.getIdentificationNumber());
                invoice.setTelephone(queueStaInvoice.getTelephone());
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
        staDao.save(sta);
        staDao.flush();
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        return true;
    }

    @Override
    public int getQueueErrorCount(String soCode) {
        return 0;
    }

    @Override
    public void updateQueueErrorCount(String soCode) {

    }

    /**
     * VMI转店
     * 
     * @param order
     */
    @Override
    public String createOwnerTransForSta(QueueSta qsta) {
        StockTransApplication sta = new StockTransApplication();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(qsta.getMainwhouid());
        if (!StringUtils.hasLength(qsta.getAddOwner())) {
            // 接口数据中附加店铺为空!
            throw new BusinessException(ErrorCode.EI_ADD_OWNER_IS_NULL, new Object[] {qsta.getAddOwner()});
        }
        BiChannel cs = companyShopDao.getByCode(qsta.getAddOwner());
        if (cs == null) {
            // 接口数据对应的附加店铺不存在!
            throw new BusinessException(ErrorCode.EI_ADD_OWNER_NOT_EXISTS);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, cs.getCode());
        String owner1 = warehouseShopRefDao.getShopInfoByWarehouseAndShopCode(ou1.getId(), qsta.getAddOwner(), new SingleColumnRowMapper<String>(String.class));
        if (owner1 == null) {
            // 仓库和附加店铺没有绑定!
            throw new BusinessException(ErrorCode.EI_TARGET_WH_SHOP_NOREF);
        }
        if (qsta.getOwner().equals(qsta.getAddOwner())) {
            // 店铺和附加店铺一致!
            throw new BusinessException(ErrorCode.EI_TARGET_ORG_SHOP_ERROR);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, qsta.getOwner());
        BiChannel shop = companyShopDao.getByCode(qsta.getOwner());
        if (!((shop.getVmiCode() != null && cs.getVmiCode() != null) || (shop.getVmiCode() == null && cs.getVmiCode() == null))) {
            throw new BusinessException(ErrorCode.EI_TWO_SHOP_MUST_VMI_SAME);
        }
        InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(qsta.getInvstatusid(), ou1.getParentUnit().getParentUnit().getId());
        sta.setRefSlipCode(qsta.getOrdercode());
        // 转店设置
        if (StringUtils.hasText(qsta.getAddOwner())) {
            sta.setType(StockTransApplicationType.VMI_OWNER_TRANSFER);
            sta.setMainStatus(invStatus);
        } else {
            sta.setType(StockTransApplicationType.valueOf(qsta.getType()));
        }
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setMainWarehouse(ou1);
        sta.setOwner(qsta.getOwner());
        sta.setAddiOwner(qsta.getAddOwner());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setIsNeedOccupied(true);
        // sta.setMemo(qsta.getMemo());
        if (shop != null && shop.getVmiCode() != null) {
            VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
            if (vf != null) {
                sta.setRefSlipCode(vf.generateRtnStaSlipCode(shop.getVmiCode(), sta.getType()));
            }
        }
        if (!StringUtil.isEmpty(sta.getRefSlipCode())) {
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        } else {
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou1.getId());
        }

        // 合并明细行记录同商品(库存状态一致)
        List<QueueStaLine> staLines = queueStaLineDao.findByStaId(qsta.getId());
        Map<String, QueueStaLine> map = new HashMap<String, QueueStaLine>();
        for (QueueStaLine line : staLines) {
            if (line.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                if (map.get(line.getSkucode()) == null) {
                    map.put(line.getSkucode(), line);
                } else {
                    QueueStaLine newLine = map.get(line.getSkucode());
                    newLine.setQty(newLine.getQty() + line.getQty());
                    map.put(line.getSkucode(), newLine);
                }
            }
        }
        staDao.save(sta);
        staDao.flush();
        List<QueueStaLine> lineList = new ArrayList<QueueStaLine>();
        lineList.addAll(map.values());
        for (QueueStaLine line1 : lineList) {
            if (line1.getDirection() == TransactionDirection.OUTBOUND.getValue()) {
                Sku sku = skuDao.getByCode(line1.getSkucode());
                invStatus = inventoryStatusDao.findSalesAviliableStatus(ou1.getId());
                StaLine sl = new StaLine();
                if (line1.getLineStatus() == QueueStaLineStatus.LINE_STATUS_TURE) {
                    sl.setSta(sta);
                    sl.setSku(sku);
                    sl.setOwner(qsta.getOwner());
                    sl.setQuantity(line1.getQty());
                    sl.setInvStatus(invStatus);
                    staLineDao.save(sl);
                }
            }
        }
        staLineDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        staDao.updateIsSnSta(sta.getId());
        staDao.flush();
        occupyInvntoryForSta(sta);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        return sta.getRefSlipCode();
    }

    /**
     * 库存占用生成STV
     * 
     * @param sta
     */
    private void occupyInvntoryForSta(StockTransApplication sta) {
        StockTransApplication sta1 = staDao.getByPrimaryKey(sta.getId());
        if (!StockTransApplicationStatus.CREATED.equals(sta1.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta1.getCode()});
        }
        TransactionType t = null;
        t = transactionTypeDao.findByCode(TransactionType.returnTypeOutbound(sta1.getType()));
        if (t != null) {
            wareHouseManager.occupyInventoryCommonMethod(sta1.getId());
            sta1.setLastModifyTime(new Date());
            sta1.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta1.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta1.getMainWarehouse().getId());
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(sta1.getId(), new SingleColumnRowMapper<String>());
            stvDao.createStv(code, sta1.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), null, tdType, sta.getMainWarehouse().getId(), t.getId());
            stvLineDao.createForCrossByStaId(sta1.getId());
        } else {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta1.getType()});
        }

    }

    /**
     * 通用库存占用方法
     * 
     * @param staId
     */
    // private void occupyInventoryCommonMethod(Long staId) {
    // StockTransApplication sta = staDao.getByPrimaryKey(staId);
    // List<InventoryOccupyCommand> list = inventoryDao.findForOccupyInventory(null, staId,
    // WarehouseDistrictType.RECEIVING.getValue(), new
    // BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
    // Long skuId = null;
    // Long tqty = null;
    // Long statusId = null;
    // for (InventoryOccupyCommand cmd : list) {
    // if (skuId == null || !(skuId.equals(cmd.getSkuId()) && statusId.equals(cmd.getStatusId()))) {
    // skuId = cmd.getSkuId();
    // statusId = cmd.getStatusId();
    // tqty = cmd.getPlanOccupyQty() == null ? 0L : cmd.getPlanOccupyQty();
    // }
    // // 待占用量小于等于0表示占用完毕，继续后续商品占用
    // if (tqty.longValue() <= 0L) {
    // continue;
    // }
    // if (cmd.getQuantity().longValue() > tqty.longValue()) {
    // // 库存数量大于待占用量拆分库存份
    // inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), tqty);
    // // 插入新库存份
    // wareHouseManager.saveInventoryForOccupy(cmd, cmd.getQuantity().longValue() -
    // tqty.longValue());
    // // 重置待占用量
    // tqty = 0L;
    // } else {
    // // 库存数量小于等于待占用量,直接占用库存份
    // inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), cmd.getQuantity().longValue());
    // tqty = tqty - cmd.getQuantity().longValue();
    // }
    // }
    // inventoryDao.flush();
    // // 验证占用量
    // validateOccupy(sta.getId());
    // }
    //
    // /**
    // * 库存占用验证占用量
    // *
    // * @param id
    // */
    // private void validateOccupy(Long id) {
    // List<InventoryCommand> ivtList = inventoryDao.validateOccupyByStaId(id, new
    // BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class));
    // if (ivtList != null && ivtList.size() > 0) {
    // BusinessException root = new
    // BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
    // for (InventoryCommand cmd : ivtList) {
    // setLinkedBusinessException(root, new BusinessException(ErrorCode.SKU_NO_INVENTORY_QTY, new
    // Object[] {cmd.getSkuName(), cmd.getSkuCode(), cmd.getBarCode(), cmd.getQuantity()}));
    // }
    // throw root;
    // }
    // }


}
