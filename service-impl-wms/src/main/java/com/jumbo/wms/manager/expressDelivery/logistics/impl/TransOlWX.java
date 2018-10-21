package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlWX")
@Transactional
public class TransOlWX extends BaseManagerImpl implements TransOlInterface {

    /**
     * 
     */
    private static final long serialVersionUID = 7747318449397241744L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;


    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        StaDeliveryInfo info = new StaDeliveryInfo();
        info.setExtTransOrderId("WX" + new Date().getTime());
        transProvideNoDao.flush();
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.WX, new SingleColumnRowMapper<String>(String.class));
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
    public synchronized StaDeliveryInfo matchingTransNo(Long staId) {
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
        // 需要处理
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.WX, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        return sd;
    }

    @Override
    public synchronized StaDeliveryInfo matchingTransNoEs(Long staId) {
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
        // 需要处理
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.WX, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

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
        // 需要处理
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.WX, new SingleColumnRowMapper<String>(String.class));
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
