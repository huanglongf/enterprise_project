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
import com.baozun.scm.primservice.logistics.manager.TransServiceManager;
import com.baozun.scm.primservice.logistics.model.MailnoGetResponse;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.EMSConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransEmsInfoDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.webservice.ems.EmsServiceClient2;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.EMSConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlEms")
@Transactional
public class TransOlEms implements TransOlInterface {
    protected static final Logger log = LoggerFactory.getLogger(TransOlEms.class);
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private TransEmsInfoDao transEmsInfoDao;
    @Autowired
    private EMSConfirmOrderQueueDao emsConfirmOrderQueueDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private WhTransProvideNoDao whTransProvideNoDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private TransServiceManager transServiceManager;

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        StaDeliveryInfo info = new StaDeliveryInfo();
        info.setExtTransOrderId("EMS" + new Date().getTime());
        transProvideNoDao.flush();
        String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.EMS, null, 0, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(order.getId());
        transProvideNoDao.save(whTransProvideNo);
        info.setTrackingNo(transNo);
        info.setLastModifyTime(new Date());
        return info;
    }


    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
        // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        sd.setExtTransOrderId(stab.getCode());
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        BiChannel bi = biChannelDao.getByCode(stab.getOwner());
        if (bi.getIsEms() != null && bi.getIsEms()) {
            Warehouse w = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
            MailnoGetContentCommand salseOrder = salseMailnoGetContent(stab, sd, w, bi);
            List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(salseOrder, "WMS3");
            if (salseList != null && salseList.size() > 0) {
                if (salseList.get(0).getStatus() == 1) {
                    if (StringUtils.hasText(salseList.get(0).getMailno())) {
                        sd.setTrackingNo(salseList.get(0).getMailno());
                        sd.setLastModifyTime(new Date());
                    } else {
                        log.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                        throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                    }
                } else {
                    log.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                log.info(salseList.get(0).getErrorMsg() + ">>>>>>>>>>>");
                throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), ""});
            }
            return sd;
        } else {
            String transNo = null;
            if (null != bi.getEmsAccount() && !"".equals(bi.getEmsAccount())) {
                transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), bi.getEmsAccount(), new SingleColumnRowMapper<String>(String.class));
                sd.setTransAccount(bi.getEmsAccount());
            } else {
                Warehouse w = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
                if (null != w.getEmsAccount() && !"".equals(w.getEmsAccount())) {
                    transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), w.getEmsAccount(), new SingleColumnRowMapper<String>(String.class));
                    sd.setTransAccount(w.getEmsAccount());
                } else {
                    TransEmsInfo transEmsInfo = null;
                    Boolean isCod = sd.getIsCod() == null ? false : sd.getIsCod();
                    Boolean bEms = true;// 用老的
                    ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
                    if (op != null && op.getOptionValue() != null) {
                        bEms = false;// 用新的
                    }
                    if (bEms) {// 老的
                        transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 0);
                    } else {// 新的
                        transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 1);
                    }
                    if (null != transEmsInfo) {
                        transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), transEmsInfo.getAccount(), new SingleColumnRowMapper<String>(String.class));
                        sd.setTransAccount(transEmsInfo.getAccount());
                    }
                }
            }

            // 需要处理
            // String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.EMS, null,
            // sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), new
            // SingleColumnRowMapper<String>(String.class));
            if (transNo == null) {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
            sd.setTrackingNo(transNo);
            sd.setLastModifyTime(new Date());
            staDeliveryInfoDao.save(sd);
            staDeliveryInfoDao.flush();
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(staId);
            whTransProvideNoDao.save(whTransProvideNo);
            whTransProvideNoDao.flush();
        }
        return sd;
    }


    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        sd.setExtTransOrderId(stab.getCode());
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        BiChannel bi = biChannelDao.getByCode(stab.getOwner());
        String transNo = null;
        if (null != bi.getEmsAccount() && !"".equals(bi.getEmsAccount())) {
            transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), bi.getEmsAccount(), new SingleColumnRowMapper<String>(String.class));
            sd.setTransAccount(bi.getEmsAccount());
        } else {
            // Warehouse w = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
            // if (null != w.getEmsAccount() && !"".equals(w.getEmsAccount())) {
            // transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null,
            // sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), w.getEmsAccount(), new
            // SingleColumnRowMapper<String>(String.class));
            // sd.setTransAccount(w.getEmsAccount());
            // } else {
            // Boolean isCod = sd.getIsCod() == null ? false : sd.getIsCod();
            // TransEmsInfo transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 0);
            // if (null != transEmsInfo) {
            // transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null,
            // sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), transEmsInfo.getAccount(), new
            // SingleColumnRowMapper<String>(String.class));
            // sd.setTransAccount(transEmsInfo.getAccount());
            // }
            // }
            TransEmsInfo transEmsInfo = null;
            Boolean isCod = sd.getIsCod() == null ? false : sd.getIsCod();
            Boolean bEms = true;// 用老的
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
            if (op != null && op.getOptionValue() != null) {
                bEms = false;// 用新的
            }
            if (bEms) {// 老的
                transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 0);
            } else {// 新的
                transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 1);
            }
            if (null != transEmsInfo) {
                transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), transEmsInfo.getAccount(), new SingleColumnRowMapper<String>(String.class));
                sd.setTransAccount(transEmsInfo.getAccount());
            }
        }

        // 需要处理
        // String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.EMS, null,
        // sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), new
        // SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
        staDeliveryInfoDao.save(sd);
        staDeliveryInfoDao.flush();
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        whTransProvideNoDao.save(whTransProvideNo);
        whTransProvideNoDao.flush();
        return sd;
    }


    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        StaDeliveryInfo di = sta.getStaDeliveryInfo();
        // TransEmsInfo emsInfo = transEmsInfoDao.findByCmp(di.getIsCod() == null ? false :
        // di.getIsCod());
        TransEmsInfo emsInfo = null;
        if (null != di.getTransAccount() && !"".equals(di.getTransAccount())) {
            Boolean bEms = true;// 用老的
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
            if (op != null && op.getOptionValue() != null) {
                bEms = false;// 用新的
            }
            if (bEms) {// 老的
                emsInfo = transEmsInfoDao.findByAccount(di.getIsCod() == null ? false : di.getIsCod(), di.getTransAccount(), 0);
            } else {// 新的
                emsInfo = transEmsInfoDao.findByAccount(di.getIsCod() == null ? false : di.getIsCod(), di.getTransAccount(), 1);
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
            if (null != di.getTransAccount() && !"".equals(di.getTransAccount())) {
                q.setSysAccount(di.getTransAccount());
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
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
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
        // 需要处理 获取 非COD的EMS运单号
        String transNo = null;
        BiChannel channel = biChannelDao.getByCode(stab.getOwner());
        if (channel.getIsEms() != null && channel.getIsEms()) {
            Warehouse w = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
            MailnoGetContentCommand salseOrder = salseMailnoGetContent(stab, sd, w, channel);
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
                        throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                    }
                } else {
                    throw new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), ""});
            }
        }
        if (null != sd.getTransAccount()) {
            transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), sd.getTransAccount(), new SingleColumnRowMapper<String>(String.class));
        } else {
            BiChannel bi = biChannelDao.getByCode(stab.getOwner());
            if (null != bi.getEmsAccount() && !"".equals(bi.getEmsAccount())) {
                transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), bi.getEmsAccount(), new SingleColumnRowMapper<String>(String.class));
                sd.setTransAccount(bi.getEmsAccount());
            } else {
                Warehouse w = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
                if (null != w.getEmsAccount() && !"".equals(w.getEmsAccount())) {
                    transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), w.getEmsAccount(), new SingleColumnRowMapper<String>(String.class));
                    sd.setTransAccount(w.getEmsAccount());
                } else {
                    TransEmsInfo transEmsInfo = null;
                    Boolean isCod = sd.getIsCod() == null ? false : sd.getIsCod();
                    Boolean bEms = true;// 用老的
                    ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
                    if (op != null && op.getOptionValue() != null) {
                        bEms = false;// 用新的
                    }
                    if (bEms) {// 老的
                        transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 0);
                    } else {// 新的
                        transEmsInfo = transEmsInfoDao.findAccountByCmp(isCod, true, 1);
                    }
                    if (null != transEmsInfo) {
                        transNo = transProvideNoDao.getEmsTranNoByLpcodeAndAccount(Transportator.EMS, null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), transEmsInfo.getAccount(), new SingleColumnRowMapper<String>(String.class));
                        sd.setTransAccount(transEmsInfo.getAccount());
                    }
                }
            }
        }
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        info.setLastModifyTime(new Date());
        info.setTrackingNo(transNo);// 运到号更新到包裹信息
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(stab.getId());// 作业单号更新到运单
        return sd;
    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        WmsInvoiceOrder wio = wmsInvoiceOrderDao.getByPrimaryKey(wioId);
        if (StringUtils.hasText(wio.getTransNo())) {
            return wio;
        }
        // 需要处理
        String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.EMS, null, null, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        wio.setTransNo(transNo);
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(wioId);
        whTransProvideNo.setType(5);// 发票单
        whTransProvideNoDao.save(whTransProvideNo);
        whTransProvideNoDao.flush();
        return wio;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        WmsInvoiceOrder wio = wmsInvoiceOrderDao.getByPrimaryKey(id);
        if (StringUtils.hasText(wio.getTransNo())) {
            return wio;
        }
        // 需要处理
        String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.EMS, null, null, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        wio.setTransNo(transNo);
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(id);
        whTransProvideNo.setType(5);// 发票单
        whTransProvideNoDao.save(whTransProvideNo);
        whTransProvideNoDao.flush();
        return wio;
    }

    @Override
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

    public MailnoGetContentCommand salseMailnoGetContent(StockTransApplication sta, StaDeliveryInfo sd, Warehouse w, BiChannel bi) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        mailnoGetContent.setOrderCode(sta.getCode());
        mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderSource(""); // TODO
        mailnoGetContent.setWhCode(sta.getMainWarehouse().getCode());
        mailnoGetContent.setOwnerCode(sta.getOwner());
        mailnoGetContent.setLpCode(sd.getLpCode());

        mailnoGetContent.setType(1);
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
        returnTransInfo.setAddress(w.getAddress());
        returnTransInfo.setCity(w.getCity());
        returnTransInfo.setDistrict(w.getDistrict());
        returnTransInfo.setMobile(w.getProvince());
        returnTransInfo.setProvince(w.getProvince());
        returnTransInfo.setReceiver(bi.getName());
        returnTransInfo.setTelephone(bi.getTelephone());
        returnTransInfo.setZipCode(w.getZipcode());
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        return mailnoGetContent;

    }

}
