package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlSto")
@Transactional
public class TransOlSto implements TransOlInterface {

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private WhTransProvideNoDao whTransProvideNoDao;
    @Autowired
    private TransAliWaybill transAliWaybill;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        StaDeliveryInfo info = new StaDeliveryInfo();
        info.setExtTransOrderId("STO" + new Date().getTime());
        transProvideNoDao.flush();
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.STO, new SingleColumnRowMapper<String>(String.class));
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
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        // Map<String, String> option =
        // chooseOptionManager.getOptionByCategoryCode(Constants.ALI_WAYBILL);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }

        // BiChannel bc = biChannelDao.getByCode(stab.getOwner());
        // if (option != null && bc.getIsCaiNiaoYto() == true) {
        // String onOff = option.get(Transportator.YTO);
        // String source = option.get(stab.getOrderSourcePlatform());
        // if (onOff != null && Constants.ALI_WAYBILL_SWITCH_ON.equals(onOff) && source != null &&
        // Constants.ALI_WAYBILL_SWITCH_ON.equals(source)) {
        // StaDeliveryInfo s = transAliWaybill.getTransNoByStaId(staId);
        // if (s != null && !StringUtil.isEmpty(s.getTrackingNo())) {
        // return s;
        // }
        // }
        // }

        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.STO, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setExtTransOrderId(stab.getCode());
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
        whTransProvideNoDao.save(whTransProvideNo);
        whTransProvideNoDao.flush();
        return sd;
    }


    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        StockTransApplication stab = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.STO, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setExtTransOrderId(stab.getCode());
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
        whTransProvideNoDao.save(whTransProvideNo);
        whTransProvideNoDao.flush();
        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        // do nothing
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
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.STO, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        info.setTrackingNo(transNo);// 运到号更新到包裹信息
        info.setLastModifyTime(new Date());
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
        String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.STO, null, null, new SingleColumnRowMapper<String>(String.class));
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
        String transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.STO, null, null, new SingleColumnRowMapper<String>(String.class));
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

    }
}
