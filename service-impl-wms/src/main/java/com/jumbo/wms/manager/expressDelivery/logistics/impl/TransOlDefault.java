package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.primservice.logistics.command.MailnoGetContentCommand;
import com.baozun.scm.primservice.logistics.command.MailnoTransInfoCommand;
import com.baozun.scm.primservice.logistics.manager.TransServiceManager;
import com.baozun.scm.primservice.logistics.model.MailnoGetResponse;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.ExpressConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransInfoDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.trans.TransInfo;
import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlDefault")
@Transactional
public class TransOlDefault extends BaseManagerImpl implements TransOlInterface {

    private static final long serialVersionUID = 7747318449397241744L;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private ExpressConfirmOrderQueueDao expressConfirmOrderQueueDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private TransInfoDao transInfoDao;
    @Autowired
    private TransServiceManager transServiceManager;

    private TimeHashMap<String, TransInfo> transInfoCache = new TimeHashMap<String, TransInfo>();

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        sd.setExtTransOrderId(stab.getCode());
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        Warehouse wh = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
        String key = sd.getLpCode() + (wh.getRegionCode() == null ? "" : wh.getRegionCode());
        TransInfo info = transInfoCache.get(key);
        if (info == null) {
            info = transInfoDao.getTransInfoByLpCodeAndRegionCode(sd.getLpCode(), wh.getRegionCode(), new BeanPropertyRowMapper<TransInfo>(TransInfo.class));
            transInfoCache.put(key, info);
        }
        String transNo = null;
        // 需要处理
        if (info.getIsOltransno() != null && info.getIsOltransno()) {
            // transNo = transProvideNoDao.getTranNoByLpcodeAndRegionCode(sd.getLpCode(),
            // wh.getRegionCode(), new SingleColumnRowMapper<String>(String.class));
            // if (transNo == null) {
            // throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            // }
            // WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            // whTransProvideNo.setStaid(staId);
            // transProvideNoDao.flush();
            MailnoGetContentCommand content = salseMailnoGetContent(stab, sd, wh);
            List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(content, "WMS3");
            if (salseList != null && salseList.size() > 0) {
                if (salseList.get(0).getStatus() == 1) {
                    if (StringUtils.hasText(salseList.get(0).getMailno())) {
                        sd.setTrackingNo(salseList.get(0).getMailno());// 运到号更新到包裹信息
                        sd.setTransBigWord(salseList.get(0).getTransBigWord()); // 大头笔信息
                        sd.setTransCityCode(salseList.get(0).getTransBigWord());// 大头笔信息
                        sd.setExtTransOrderId(salseList.get(0).getExtId());
                        return sd;
                    } else {
                        throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                    }
                } else {
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), ""});
            }
        } else {
            transNo = "BZ" + stab.getCode();
            sd.setTrackingNo(transNo);
        }
        return sd;
    }


    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        sd.setExtTransOrderId(stab.getCode());
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        Warehouse wh = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
        String key = sd.getLpCode() + (wh.getRegionCode() == null ? "" : wh.getRegionCode());
        TransInfo info = transInfoCache.get(key);
        if (info == null) {
            info = transInfoDao.getTransInfoByLpCodeAndRegionCode(sd.getLpCode(), wh.getRegionCode(), new BeanPropertyRowMapper<TransInfo>(TransInfo.class));
            transInfoCache.put(key, info);
        }

        String transNo = null; // 需要处理
        if (info.getIsOltransno() != null && info.getIsOltransno()) {
            // transNo = transProvideNoDao.getTranNoByLpcodeAndRegionCode(sd.getLpCode(),
            // wh.getRegionCode(), new SingleColumnRowMapper<String>(String.class));
            // if (transNo == null) {
            // throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            // }
            // WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            // whTransProvideNo.setStaid(staId);
            // transProvideNoDao.flush();
            // 启用物流服务新接口，by fxl
            MailnoGetContentCommand content = salseMailnoGetContent(stab, sd, wh);
            List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(content, "WMS3");
            if (salseList != null && salseList.size() > 0) {
                if (salseList.get(0).getStatus() == 1) {
                    if (StringUtils.hasText(salseList.get(0).getMailno())) {
                        sd.setTrackingNo(salseList.get(0).getMailno());// 运到号更新到包裹信息
                        sd.setTransBigWord(salseList.get(0).getTransBigWord()); // 大头笔信息
                        sd.setTransCityCode(salseList.get(0).getTransBigWord());// 大头笔信息
                        sd.setExtTransOrderId(salseList.get(0).getExtId());
                        return sd;
                    } else {
                        throw new BusinessException(ErrorCode.PROVIDER_NO_TRANS_OR_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                    }
                } else {
                    throw new BusinessException(ErrorCode.PROVIDER_NO_TRANS_OR_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                throw new BusinessException(ErrorCode.PROVIDER_NO_TRANS_OR_ERROR, new Object[] {stab.getCode(), ""});
            }
        } else {
            transNo = "BZ" + stab.getCode();
            sd.setTrackingNo(transNo);
        }
        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        // 合并订单传主订单
        if (null != sta.getGroupSta() && (null == sta.getIsMerge() || !sta.getIsMerge())) {
            sta = staDao.getByPrimaryKey(sta.getGroupSta().getId());
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
            ExpressConfirmOrderQueue order = expressConfirmOrderQueueDao.findExOrderByStaCodeAndTransNo(pi.getLpCode(), sta.getCode(), pi.getTrackingNo(), new BeanPropertyRowMapper<ExpressConfirmOrderQueue>(ExpressConfirmOrderQueue.class));
            if (order == null) {
                ExpressConfirmOrderQueue q = new ExpressConfirmOrderQueue();
                q.setLpCode(pi.getLpCode());
                q.setTransNo(pi.getTrackingNo());
                q.setStaCode(sta.getCode());
                q.setCreateTime(new Date());
                q.setExeCount(0L);
                DecimalFormat df = new DecimalFormat("0.00");
                q.setWeight(df.format(pi.getWeight()));
                boolean isNotInfo = true;
                if (list.size() > 0) {
                    for (StaAdditionalLine l : list) {
                        Sku sku = l.getSku();
                        if (l.getSku() != null && l.getSku().getLength() != null && l.getSku().getHeight() != null && l.getSku().getWidth() != null) {
                            BigDecimal height = sku.getHeight().divide(new BigDecimal(10));
                            BigDecimal length = sku.getLength().divide(new BigDecimal(10));
                            BigDecimal width = sku.getWidth().divide(new BigDecimal(10));
                            BigDecimal volume = height.multiply(length).multiply(width);
                            q.setHeight(df.format(height));
                            q.setLength(df.format(length));
                            q.setWidth(df.format(width));
                            q.setVolume(df.format(volume));
                            isNotInfo = false;
                            break;
                        }
                    }
                }
                if (isNotInfo) {
                    q.setHeight("0");
                    q.setLength("0");
                    q.setWidth("0");
                    q.setVolume("0");
                }
                expressConfirmOrderQueueDao.save(q);
            }
        }
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        // 匹配时基于订单判断获取不同类型的运单号
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
        Warehouse wh = warehouseDao.getByOuId(stab.getMainWarehouse().getId());
        String key = sd.getLpCode() + (wh.getRegionCode() == null ? "" : wh.getRegionCode());
        TransInfo transInfo = transInfoCache.get(key);
        if (transInfo == null) {
            transInfo = transInfoDao.getTransInfoByLpCodeAndRegionCode(sd.getLpCode(), wh.getRegionCode(), new BeanPropertyRowMapper<TransInfo>(TransInfo.class));
            transInfoCache.put(key, transInfo);
        }
        String transNo = "";
        // 需要处理
        if (transInfo.getIsOltransno() != null && transInfo.getIsOltransno()) {

            // transNo = transProvideNoDao.getTranNoByLpcodeAndRegionCode(sd.getLpCode(),
            // wh.getRegionCode(), new SingleColumnRowMapper<String>(String.class));
            // if (transNo == null) {
            // throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            // }
            // WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            // whTransProvideNo.setStaid(staId);
            // transProvideNoDao.flush();
            MailnoGetContentCommand content = salseMailnoGetContent(stab, sd, wh);
            List<MailnoGetResponse> salseList = transServiceManager.matchingTransNo(content, "WMS3");
            if (salseList != null && salseList.size() > 0) {
                if (salseList.get(0).getStatus() == 1) {
                    if (StringUtils.hasText(salseList.get(0).getMailno())) {
                        sd.setTrackingNo(salseList.get(0).getMailno());// 运到号更新到包裹信息
                        sd.setTransBigWord(salseList.get(0).getTransBigWord()); // 大头笔信息
                        sd.setTransCityCode(salseList.get(0).getTransBigWord());// 大头笔信息
                        sd.setExtTransOrderId(salseList.get(0).getExtId());
                        return sd;
                    } else {
                        throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                    }
                } else {
                    throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), salseList.get(0).getErrorMsg()});
                }
            } else {
                throw new BusinessException(ErrorCode.LOGISTICS_INTERFACE_ERROR, new Object[] {stab.getCode(), ""});
            }
        
        } else {
            String count = packageInfoDao.findPgCountByStaId(stab.getId(), new SingleColumnRowMapper<String>(String.class));
            transNo = "BZ" + stab.getCode() + count;
            info.setTrackingNo(transNo);
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


    public MailnoGetContentCommand salseMailnoGetContent(StockTransApplication sta, StaDeliveryInfo sd, Warehouse wh) {
        MailnoGetContentCommand mailnoGetContent = new MailnoGetContentCommand();
        mailnoGetContent.setTotalActual(sta.getTotalActual());
        mailnoGetContent.setOrderCode(sta.getCode());
        mailnoGetContent.setTradeId(sta.getRefSlipCode());
        mailnoGetContent.setOrderSource(sta.getSystemKey());
        mailnoGetContent.setExpressType("1"); // 默认标准快递
        mailnoGetContent.setTransFree(sd.getTransferFee()); // 运费
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
        returnTransInfo.setAddress(wh.getAddress());
        returnTransInfo.setCity(wh.getCity());
        returnTransInfo.setDistrict(wh.getDistrict());
        returnTransInfo.setMobile(wh.getPhone());
        returnTransInfo.setProvince(wh.getProvince());
        returnTransInfo.setReceiver(wh.getPic());
        returnTransInfo.setTelephone(wh.getPicContact());
        returnTransInfo.setZipCode(wh.getZipcode());
        mailnoGetContent.setSenderTransInfo(returnTransInfo);
        return mailnoGetContent;
    }
}
