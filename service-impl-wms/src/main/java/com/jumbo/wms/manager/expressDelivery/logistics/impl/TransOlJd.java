package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.JdConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.JdConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlJd")
@Transactional
public class TransOlJd implements TransOlInterface {
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private JdConfirmOrderQueueDao jdConfirmOrderQueueDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    
    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {// 京东获取运单号为什么要传店铺的code
        StaDeliveryInfo info = new StaDeliveryInfo();
        // info.setExtTransOrderId("JD" + new Date().getTime());
        // transProvideNoDao.flush();
        // String transNo = null;
        // if ("2".equals(order.getCostCenterType())) {// 店铺
        // transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.JD,
        // order.getCostCenterDetail(), null, new SingleColumnRowMapper<String>(String.class));
        // } else {
        // transNo = transProvideNoDao.getEmsTranNoByLpcode(Transportator.JD, null, null, new
        // SingleColumnRowMapper<String>(String.class));
        // }
        // if (transNo == null) {
        // throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        // }
        // WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        // whTransProvideNo.setStaid(order.getId());
        // transProvideNoDao.save(whTransProvideNo);
        // info.setTrackingNo(transNo);
        return info;

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
        String lpCode = Transportator.JD;
        String transNo = transProvideNoDao.getJdTranNoByLpcode(lpCode, stab.getOwner(), null, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        transProvideNoDao.flush();
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
        String lpCode = Transportator.JD;
        String transNo = transProvideNoDao.getJdTranNoByLpcode(lpCode, stab.getOwner(), null, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        transProvideNoDao.flush();
        return sd;
    }


    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        if (sta.getStaDeliveryInfo().getExtTransOrderId() == null) {
            return;
        }
        JdConfirmOrderQueue jd = new JdConfirmOrderQueue();
        jd.setCreateTime(sta.getCreateTime());
        jd.setExeCount(0L);
        jd.setTransno(sta.getStaDeliveryInfo().getTrackingNo());
        jd.setStaCode(sta.getCode());
        BigDecimal weight = sta.getStaDeliveryInfo().getWeight();
        DecimalFormat df = new DecimalFormat("0.00");
        jd.setWeight(df.format(weight));
        List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(sta.getId());
        if (list != null) {
            for (StaAdditionalLine l : list) {
                if (l.getSku() != null && l.getSku().getLength() != null) {
                    jd.setHeight(df.format(l.getSku().getHeight().divide(new BigDecimal(10))));
                    jd.setLength(df.format(l.getSku().getLength().divide(new BigDecimal(10))));
                    jd.setWhight(df.format(l.getSku().getWidth().divide(new BigDecimal(10))));
                    break;
                }
            }
        } else {
            jd.setHeight("0");
            jd.setLength("0");
            jd.setWhight("0");
        }
        jdConfirmOrderQueueDao.save(jd);
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        return null;
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
