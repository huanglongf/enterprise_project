package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.primservice.logistics.command.MailnoGetContentCommand;
import com.baozun.scm.primservice.logistics.command.MailnoTransInfoCommand;
import com.baozun.scm.primservice.logistics.command.TransSkuItemCommand;
import com.baozun.scm.primservice.logistics.manager.TransServiceManager;
import com.baozun.scm.primservice.logistics.model.MailnoGetResponse;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.EMSConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.LogisticsInfoDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PackageListLogDao;
import com.jumbo.dao.warehouse.PickingListPackageDao;
import com.jumbo.dao.warehouse.SfConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.SfExpressTypeConfigDao;
import com.jumbo.dao.warehouse.SfMailNoRemainRelationDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransEmsInfoDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.ems.EmsServiceClient2;
import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.LogisticsInfo;
import com.jumbo.wms.model.baseinfo.PackageListLog;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.EMSConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.SfConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.SfMailNoRemainRelation;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlLogistics")
@Transactional
public class TransOlLogistics implements TransOlInterface {
    protected static final Logger logger = LoggerFactory.getLogger(TransOlLogistics.class);

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private SfConfirmOrderQueueDao sfConfirmOrderQueueDao;
    @Autowired
    private PickingListPackageDao pickingListPackageDao;
    @Autowired
    private SfExpressTypeConfigDao sfConfigDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    BiChannelDao biChannelDao;
    @Autowired
    private SfMailNoRemainRelationDao sfMailNoRemainRelationDao;
    @Autowired
    private TransServiceManager transServiceManager;
    @Autowired
    private LogisticsInfoDao logisticsInfoDao;
    @Autowired
    private PackageListLogDao packageListLogDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    @Autowired
    private EMSConfirmOrderQueueDao emsConfirmOrderQueueDao;
    @Autowired
    private TransEmsInfoDao transEmsInfoDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;

    // 匹配运单号主方法
    public StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        BiChannel channel = biChannelDao.getByCode(sta.getOwner());
        StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        if (channel.getIsPackingList() != null && channel.getIsPackingList() && sd.getLpCode().equals("SF")) {
            // NIKE-SF官网定制， SF获取正向和负向面单
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
            if (StringUtils.hasText(sd.getTrackingNo())) {
                return sd;
            }
            return matchingTransNo(staId, 1);
        }
    }


    // 匹配运单号子方法
    public StaDeliveryInfo matchingTransNo(Long staId, int expceptionType) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        MailnoGetContentCommand salseOrder = salseMailnoGetContent(sta, sd, null, wh, shop, 1);
        List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(salseOrder, "WMS3");
        if (salseList != null && salseList.size() > 0) {
            if (salseList.get(0).getStatus() == 1) {
                if (StringUtils.hasText(salseList.get(0).getMailno())) {
                    sd.setTransBigWord(salseList.get(0).getTransBigWord());
                    if (sd.getLpCode().contains("SF")) {
                        sd.setTransCityCode(salseList.get(0).getTransBigWord());
                        if (salseOrder.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                            sd.setTransType(TransType.PREFERENTIAL);
                        }
                    } else {
                        sd.setTransCityCode(salseList.get(0).getPackageCenterCode());
                    }
                    sd.setExtTransOrderId(salseList.get(0).getExtId());
                    sd.setTrackingNo(salseList.get(0).getMailno());
                    sd.setLastModifyTime(new Date());
                } else {
                    logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                if ("SF_ERROR".equals(salseList.get(0).getErrorCode())) {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                } else {
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
                }
            }
        } else {
            logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        }
        return sd;
    }

    /**
     * 线下包裹获取SF运单号
     */
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        Warehouse wh = warehouseDao.getByOuId(whOuId);// 仓库id
        BiChannel shop = companyShopDao.getByCode(order.getCostCenterDetail());// 店铺code
        MailnoGetContentCommand command = offLineMailnoGetContent(order, wh, shop);// 封装请求参数
        StaDeliveryInfo sd = new StaDeliveryInfo();
        // 请求物流服务接口
        List<MailnoGetResponse> offLinePackingList = transServiceManager.matchingTransNo(command, "WMS3");
        if (offLinePackingList != null && offLinePackingList.size() > 0) {
            if (offLinePackingList.get(0).getStatus() == 1) {
                if (StringUtils.hasText(offLinePackingList.get(0).getMailno())) {
                    sd.setTransCityCode(offLinePackingList.get(0).getTransBigWord());
                    sd.setExtTransOrderId(offLinePackingList.get(0).getExtId());
                    sd.setTrackingNo(offLinePackingList.get(0).getMailno());
                    if (command.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                        sd.setTransType(TransType.PREFERENTIAL);
                    }
                    sd.setLastModifyTime(new Date());
                } else {
                    logger.info(offLinePackingList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {offLinePackingList.get(0).getErrorMsg()});
                }
            } else {
                logger.info(offLinePackingList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                if ("SF_ERROR".equals(offLinePackingList.get(0).getErrorCode())) {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {offLinePackingList.get(0).getErrorMsg()});
                } else {
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {""});
                }
            }
        } else {
            logger.info(offLinePackingList.get(0).getErrorMsg() + ">>>>>>>>>>>");
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {""});
        }
        return sd;
    }

    /**
     * 需求不明，vmi esprit 退仓 专用
     */
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        StaDeliveryInfo d = null;
        try {
            d = matchingTransNo(staId, 1);
        } catch (Exception e) {}
        return d;
    }



    /**
     * 拆包裹获取运单号
     */
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
        StockTransApplication sta = staDao.getByPrimaryKey(sd.getId());
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        MailnoGetContentCommand salseOrder = salseMailnoGetContent(sta, sd, null, wh, shop, 7);
        salseOrder.setMainTrackno(sd.getTrackingNo());
        // salseOrder.setIsChild(true);
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
                if ("SF_ERROR".equals(salseList.get(0).getErrorCode())) {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                } else {
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
                }
            }
        } else {
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        }
    }

    /**
     * 发货确认. 目前只有SF，EMS
     */
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        // 合并订单传主订单
        if (null != sta.getGroupSta() && (null == sta.getIsMerge() || !sta.getIsMerge())) {
            sta = staDao.getByPrimaryKey(sta.getGroupSta().getId());
        }
        if (sta.getStaDeliveryInfo().getExtTransOrderId() == null) {
            return;
        }
        if (sta.getStaDeliveryInfo().getLpCode().equals("EMS")) {
            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            // TransEmsInfo emsInfo = transEmsInfoDao.findByCmp(di.getIsCod() == null ? false :
            // di.getIsCod());
            TransEmsInfo emsInfo = null;
            BiChannel bi = biChannelDao.getByCode(sta.getOwner());
            String account = "";
            if (null != bi.getEmsAccount() && !"".equals(bi.getEmsAccount())) {
                account = bi.getEmsAccount();
            }
            if (null != account && !"".equals(account)) {
                Boolean bEms = true;// 用老的
                ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
                if (op != null && op.getOptionValue() != null) {
                    bEms = false;// 用新的
                }
                if (bEms) {// 老的
                    emsInfo = transEmsInfoDao.findByAccount(di.getIsCod() == null ? false : di.getIsCod(), account, 0);
                } else {// 新的
                    emsInfo = transEmsInfoDao.findByAccount(di.getIsCod() == null ? false : di.getIsCod(), account, 1);
                }

            } else {
                Boolean bEms = true;// 用老的
                ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
                if (op != null && op.getOptionValue() != null) {
                    bEms = false;// 用新的
                }
                if (bEms) {// 老的
                    emsInfo = transEmsInfoDao.findAccountByCmp(di.getIsCod() == null ? false : di.getIsCod(), true, 0);
                } else {// 新的
                    emsInfo = transEmsInfoDao.findAccountByCmp(di.getIsCod() == null ? false : di.getIsCod(), true, 1);
                }
            }
            if (emsInfo == null) {
                throw new BusinessException(ErrorCode.NO_EMS_ACCOUNT);
            }
            List<PackageInfo> piList = packageInfoDao.findByStaId(sta.getId());
            if (piList == null || piList.size() == 0) {
                return;
            }
            List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(sta.getId());
            if (list == null) {
                list = new ArrayList<StaAdditionalLine>();
            }
            for (PackageInfo pi : piList) {
                EMSConfirmOrderQueue q = new EMSConfirmOrderQueue();
                q.setBillNo(pi.getTrackingNo());
                q.setStaCode(sta.getCode());
                q.setCreateTime(new Date());
                q.setExeCount(0L);
                q.setPassWord(emsInfo.getPassword());
                if (null != account && !"".equals(account)) {
                    q.setSysAccount(account);
                } else {
                    q.setSysAccount(emsInfo.getAccount());
                }
                DecimalFormat w = new DecimalFormat("0.00");
                q.setWeight(w.format(pi.getWeight()));
                boolean isNotInfo = true;
                if (list.size() > 0) {
                    for (StaAdditionalLine l : list) {
                        if (l.getTrackingNo().equals(pi.getTrackingNo())) {
                            if (l.getSku() != null && l.getSku().getLength() != null) {
                                q.setLength(w.format(l.getSku().getLength().divide(new BigDecimal(10))));
                                isNotInfo = false;
                                break;
                            }
                        }
                    }
                }
                if (isNotInfo) {
                    q.setLength("0");
                }
                emsConfirmOrderQueueDao.save(q);
            }
        } else if (sta.getStaDeliveryInfo().getLpCode().equals("SF") || sta.getStaDeliveryInfo().getLpCode().equals("SFDSTH")) {
            SfConfirmOrderQueue q = new SfConfirmOrderQueue();
            q.setCreateTime(new Date());
            q.setExeCount(0L);
            List<String> sunNo = staDeliveryInfoDao.getParentAndSonMailNoById1(sta.getId(), new SingleColumnRowMapper<String>(String.class));
            StaDeliveryInfo stad = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            String headStr = stad.getTrackingNo();
            String mailNo = null;
            if (newTransNo == null) {
                mailNo = headStr;
                String handleStr = org.apache.commons.lang.StringUtils.join(sunNo.toArray(), ",");
                if (!StringUtil.isEmpty(handleStr)) {
                    mailNo += "," + handleStr;
                }
            } else {
                mailNo = newTransNo;
            }
            List<String> interceptList = interceptOverLengthString(mailNo);
            q.setMailno(interceptList.get(0));
            // q.setMailno(info.getTrackingNo());
            // q.setOrderId(sta.getStaDeliveryInfo().getExtTransOrderId());
            q.setStaCode(sta.getCode());
            BigDecimal weight = stad.getWeight();
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
        } else {
            SfConfirmOrderQueue q = new SfConfirmOrderQueue();
            q.setCreateTime(new Date());
            q.setExeCount(0L);
            StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            q.setMailno(info.getTrackingNo());
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
            sfConfirmOrderQueueDao.save(q);
        }


    }

    /**
     * O2O+QS匹配作业单运单号
     */
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;// O2O+QS
    }

    /**
     * 匹配发票单运单号
     */
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        return null; // 无业务逻辑使用
    }

    /**
     * 匹配发票单运单号
     */
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        WmsInvoiceOrder wio = wmsInvoiceOrderDao.getByPrimaryKey(id);
        // 如果之前调用成功，记录快递单号则直接返回
        if (StringUtils.hasText(wio.getTransNo()) && StringUtils.hasText(wio.getExtTransOrderId())) {
            return wio;
        }
        BiChannel shop = companyShopDao.getByCode(wio.getOwner());
        MailnoGetContentCommand command = invoiceOrderMailnoGetContent(wio, shop);
        List<MailnoGetResponse> offLinePackingList = transServiceManager.matchingTransNo(command, "WMS3");
        if (offLinePackingList != null && offLinePackingList.size() > 0) {
            if (offLinePackingList.get(0).getStatus() == 1) {
                if (StringUtils.hasText(offLinePackingList.get(0).getMailno())) {

                    wio.setTransNo(offLinePackingList.get(0).getMailno());
                } else {
                    logger.info(offLinePackingList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {offLinePackingList.get(0).getErrorMsg()});
                }
            } else {
                logger.info(offLinePackingList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {offLinePackingList.get(0).getErrorMsg()});
            }
        } else {
            logger.info(offLinePackingList.get(0).getErrorMsg() + ">>>>>>>>>>>");
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {""});
        }
        return wio; // TODO
    }

    /**
     * 发票确认订单
     */
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {
        TransEmsInfo emsInfo = null;
        Boolean bEms = true;// 用老的
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
        if (op != null && op.getOptionValue() != null) {
            bEms = false;// 用新的
        }
        if (bEms) {// 老的
            emsInfo = transEmsInfoDao.findAccountByCmp(false, true, 0);// 没有cod订单
        } else {// 新的
            emsInfo = transEmsInfoDao.findAccountByCmp(false, true, 1);// 没有cod订单
        }
        if (emsInfo == null) {
            throw new BusinessException(ErrorCode.NO_EMS_ACCOUNT);
        }
        EMSConfirmOrderQueue q = new EMSConfirmOrderQueue();
        q.setOrder_flag(1L);// 1标识是发票订单
        q.setBillNo(wio.getTransNo());
        q.setStaCode(wio.getOrderCode());
        q.setCreateTime(new Date());
        q.setExeCount(0L);
        q.setPassWord(emsInfo.getPassword());
        q.setSysAccount(emsInfo.getAccount());
        DecimalFormat w = new DecimalFormat("0.00");
        q.setWeight(w.format(0.1));// 重量统一使用 0.1kg
        q.setLength("200.00");// 体积使用200mm*100mm*5mm
        emsConfirmOrderQueueDao.save(q);
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

    /**
     * nike定制获取正负面单
     * 
     * @param sta
     * @param logisticsInfo
     * @param sd
     * @return
     */
    private StaDeliveryInfo matchingTransNoNike(StockTransApplication sta, LogisticsInfo logisticsInfo, StaDeliveryInfo sd) {
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        MailnoGetContentCommand salseOrder = salseMailnoGetContent(sta, sd, logisticsInfo, wh, shop, 3);
        MailnoGetContentCommand returnOrder = returnMailnoGetContent(sta, sd, logisticsInfo, wh);
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
                if ("SF_ERROR".equals(salseList.get(0).getErrorCode())) {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                } else {
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
                }
            }
        } else {
            logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
            throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
        }
        List<MailnoGetResponse> returnList = transServiceManager.matchingTransNo(returnOrder, "WMS3");
        if (returnList != null && returnList.size() > 0) {
            if (returnList.get(0).getStatus() == 1) {
                if (StringUtils.hasText(returnList.get(0).getMailno())) {
                    sd.setReturnTrackingNo(returnList.get(0).getUrl());
                    sd.setReturnTransCityCode(returnList.get(0).getTransBigWord());
                    sd.setReturnTransNo(returnList.get(0).getMailno());
                    sd.setRetrunExtTransOrderId(returnList.get(0).getExtId());
                    if (salseOrder.getExpressType().equals(SfOrder.ORDER_TYPE_ELECTRIC)) {
                        sd.setTransType(TransType.PREFERENTIAL);
                    }
                    sd.setLastModifyTime(new Date());
                } else {
                    logger.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                    if ("SF_ERROR".equals(salseList.get(0).getErrorCode())) {
                        throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {sta.getCode(), salseList.get(0).getErrorMsg()});
                    } else {
                        throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {sta.getCode(), ""});
                    }
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

    /**
     * 获取面单封装数据
     * 
     * @param sta
     * @param sd
     * @param logisticsInfo
     * @param wh
     * @param shop
     * @param type : 1正向，2负向，3正向+自提，4发票，5线下包裹，6退仓，7拆包裹
     * @return
     */
    public MailnoGetContentCommand salseMailnoGetContent(StockTransApplication sta, StaDeliveryInfo sd, LogisticsInfo logisticsInfo, Warehouse wh, BiChannel shop, Integer Mialtype) {
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
            if (!StringUtil.isEmpty(shop.getTransAccountsCode())) {
                mailnoGetContent.setTransAccountsCode(shop.getTransAccountsCode());
            }
        } else {
            // 保价
            if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
                mailnoGetContent.setInsuranceAmount(inSure == null ? new BigDecimal(0) : inSure);
            }
            mailnoGetContent.setTotalActual(sta.getTotalActual());
        }
        mailnoGetContent.setOrderTime(sta.getOrderCreateTime()); // 平台订单时间
        mailnoGetContent.setOrderCode(sta.getCode());
        mailnoGetContent.setTradeId(sta.getSlipCode1());
        mailnoGetContent.setOrderSource(sta.getSystemKey());
        mailnoGetContent.setWhCode(sta.getMainWarehouse().getCode());
        mailnoGetContent.setOwnerCode(sta.getOwner());
        mailnoGetContent.setLpCode(sd.getLpCode());
        mailnoGetContent.setType(Mialtype);
        if ("SF".equals(sd.getLpCode()) || "SFDSTH".equals(sd.getLpCode())) {
            Integer type = sfConfigDao.findSfExpressTypeByCondition(sd.getTransTimeType().getValue(), sd.getLpCode(), sta.getDeliveryType().getValue(), new SingleColumnRowMapper<Integer>(Integer.class));
            if (type != null) {
                mailnoGetContent.setExpressType(type.toString());
            } else {
                mailnoGetContent.setExpressType("1");
            }
        }
        mailnoGetContent.setIsCod(sd.getIsCod());
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
        if (logisticsInfo == null) {
            returnTransInfo.setAddress(wh.getAddress());
            returnTransInfo.setCity(wh.getCity());
            returnTransInfo.setDistrict(wh.getDistrict());
            returnTransInfo.setMobile(wh.getPicContact());
            returnTransInfo.setProvince(wh.getProvince());
            if (shop != null) {
                returnTransInfo.setReceiver(shop.getName());
                returnTransInfo.setTelephone(shop.getTelephone());
            } else {
                returnTransInfo.setReceiver(wh.getPic());
                returnTransInfo.setTelephone(wh.getPhone());
            }
            returnTransInfo.setZipCode(wh.getZipcode());
        } else {
            returnTransInfo.setAddress(logisticsInfo.getAddress());
            returnTransInfo.setCity(logisticsInfo.getCity());
            returnTransInfo.setDistrict(logisticsInfo.getDistrict());
            returnTransInfo.setMobile(logisticsInfo.getMobile());
            returnTransInfo.setProvince(logisticsInfo.getProvince());
            returnTransInfo.setReceiver(logisticsInfo.getReceiver());
            returnTransInfo.setTelephone(logisticsInfo.getTelephone());
            returnTransInfo.setZipCode(logisticsInfo.getZipCode());
        }
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        List<StaLine> lineList = staLineDao.findByStaId(sta.getId());
        List<TransSkuItemCommand> skuItem = new ArrayList<TransSkuItemCommand>();
        for (StaLine line : lineList) {
            TransSkuItemCommand item = new TransSkuItemCommand();
            item.setItemName("宝尊商品");
            if (line.getUnitPrice() != null) {
                item.setItemPrice(line.getUnitPrice());
            }
            item.setItemQty(line.getQuantity().intValue());
            skuItem.add(item);
        }
        mailnoGetContent.setSkuItem(skuItem);
        return mailnoGetContent;

    }

    /**
     * 负向面单封装数据
     * 
     * @param sta
     * @param sd
     * @param logisticsInfo
     * @param wh
     * @return
     */
    public MailnoGetContentCommand returnMailnoGetContent(StockTransApplication sta, StaDeliveryInfo sd, LogisticsInfo logisticsInfo, Warehouse wh) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        mailnoGetContent.setSfWhCode(wh.getSfWhCode());
        mailnoGetContent.setOrderCode(sta.getCode());
        mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderSource(sta.getSystemKey());
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
        transInfoCommand.setReceiver("NIKE官网退货组");
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

    /**
     * nike定制装箱清单
     * 
     * @param sta
     * @param logisticsInfo
     */
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

    /**
     * 封装线下包裹请求信息
     * 
     * @param order
     * @param wh
     * @param shop
     * @return
     */
    private MailnoGetContentCommand offLineMailnoGetContent(TransOrder order, Warehouse wh, BiChannel shop) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        BigDecimal inSure = channelInsuranceManager.getInsurance(order.getCostCenterDetail(), order.getInsuranceAmount());
        if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
            mailnoGetContent.setIsTpPaymentSf(true);
        }
        mailnoGetContent.setSfWhCode(wh.getSfWhCode());
        if (null != wh.getSfWhCode()) {
            mailnoGetContent.setSfWhCode(wh.getSfWhCode());
        }
        // 保价
        if (inSure != null && inSure.compareTo(new BigDecimal(0)) > 0) {
            mailnoGetContent.setInsuranceAmount(inSure == null ? new BigDecimal(0) : inSure);
        }
        mailnoGetContent.setTotalActual(order.getInsuranceAmount());
        // mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderCode(order.getCode());
        mailnoGetContent.setOrderSource("线下包裹"); // TODO
        mailnoGetContent.setWhCode(order.getOpUnit().getCode());
        mailnoGetContent.setOwnerCode(order.getCostCenterDetail());
        mailnoGetContent.setLpCode(order.getTransportatorCode());
        mailnoGetContent.setIsOneSelf(false);

        mailnoGetContent.setType(5);
        mailnoGetContent.setExpressType(order.getTimeType()+"");
        mailnoGetContent.setIsCod(false);
        MailnoTransInfoCommand transInfoCommand = new MailnoTransInfoCommand();
        transInfoCommand.setAddress(order.getReceiverAddress());
        transInfoCommand.setCity(order.getReceiverCity());
        transInfoCommand.setDistrict(order.getReceiverArea());
        transInfoCommand.setMobile(order.getReceiverTel());
        transInfoCommand.setProvince(order.getReceiverProvince());
        transInfoCommand.setReceiver(order.getReceiver());
        transInfoCommand.setTelephone(order.getReceiverTel2());
        transInfoCommand.setZipCode("215000");
        mailnoGetContent.setTransInfo(transInfoCommand);
        MailnoTransInfoCommand returnTransInfo = new MailnoTransInfoCommand();
        returnTransInfo.setAddress(order.getSenderAddress());
        returnTransInfo.setCity(wh.getCity());
        returnTransInfo.setDistrict(wh.getDistrict());
        returnTransInfo.setMobile(wh.getPhone());
        returnTransInfo.setProvince(wh.getProvince());
        returnTransInfo.setReceiver(wh.getPic());
        returnTransInfo.setTelephone(wh.getPicContact());
        returnTransInfo.setZipCode("215000");
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        return mailnoGetContent;

    }

    private MailnoGetContentCommand invoiceOrderMailnoGetContent(WmsInvoiceOrder invoiceOrder, BiChannel shop) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        // StockTransApplication sta =staDao.findStaByCode(invoiceOrder.getOrderCode());
        // StaDeliveryInfo sd=sta.getStaDeliveryInfo();
        // mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderCode(invoiceOrder.getOrderCode());
        mailnoGetContent.setOrderSource(invoiceOrder.getSystemKey());
        mailnoGetContent.setWhCode("SYS");
        mailnoGetContent.setOwnerCode(shop.getCode());
        mailnoGetContent.setLpCode(invoiceOrder.getLpCode());
        mailnoGetContent.setIsOneSelf(false);
        mailnoGetContent.setType(6);
        mailnoGetContent.setExpressType("1");
        mailnoGetContent.setIsCod(false);
        MailnoTransInfoCommand transInfoCommand = new MailnoTransInfoCommand();
        transInfoCommand.setAddress(invoiceOrder.getAddress());
        transInfoCommand.setCity(invoiceOrder.getCity());
        transInfoCommand.setDistrict(invoiceOrder.getDistrict());
        transInfoCommand.setMobile(invoiceOrder.getMobile());
        transInfoCommand.setProvince(invoiceOrder.getProvince());
        transInfoCommand.setReceiver(invoiceOrder.getReceiver());
        transInfoCommand.setTelephone(invoiceOrder.getTelephone());
        transInfoCommand.setZipCode("215000");
        mailnoGetContent.setTransInfo(transInfoCommand);
        MailnoTransInfoCommand returnTransInfo = new MailnoTransInfoCommand();
        returnTransInfo.setAddress(shop.getAddress());
        returnTransInfo.setMobile(shop.getTelephone());
        returnTransInfo.setProvince(shop.getProvince());
        returnTransInfo.setReceiver(shop.getName());
        returnTransInfo.setTelephone(shop.getTelephone());
        returnTransInfo.setZipCode("215000");
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        return mailnoGetContent;
    }
}
