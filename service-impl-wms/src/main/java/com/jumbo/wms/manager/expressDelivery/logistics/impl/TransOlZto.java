package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.dao.warehouse.ZtoConfirmOrderQueueDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
import com.jumbo.wms.model.warehouse.ZtoConfirmOrderQueue;

@Service("transOlZto")
@Transactional
public class TransOlZto extends BaseManagerImpl implements TransOlInterface {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1945844516803065653L;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private ZtoConfirmOrderQueueDao ztoConfirmOrderQueueDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        StaDeliveryInfo info = new StaDeliveryInfo();
        info.setExtTransOrderId("ZTO" + new Date().getTime());
        transProvideNoDao.flush();
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.ZTO, new SingleColumnRowMapper<String>(String.class));
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
        StaDeliveryInfo sd = stab.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        sd.setExtTransOrderId(stab.getCode());
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.ZTO, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
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
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        sd.setExtTransOrderId(stab.getCode());
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.ZTO, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(transNo);
        sd.setLastModifyTime(new Date());
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(staId);
        transProvideNoDao.flush();
        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

        List<PackageInfo> piList = packageInfoDao.findByStaId(sta.getId());
        if (piList == null || piList.size() == 0) {
            return;
        }
        List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(sta.getId());
        if (list == null) {
            list = new ArrayList<StaAdditionalLine>();
        }
        for (PackageInfo pi : piList) {
            // 查询当前作业单的所属组织Id，用于查询zto用户名和zto接口密码
            ZtoConfirmOrderQueue q = new ZtoConfirmOrderQueue();
            // 查询zto用户名，及接口密码
            q.setUserName(ZTO_USERNAME);
            q.setPassWord(ZTO_PASSWORD);
            q.setCreateTime(new Date());
            q.setExeCount(0L);
            q.setMailno(pi.getTrackingNo());
            q.setStaCode(sta.getCode());
            BigDecimal weight = pi.getWeight();
            DecimalFormat df = new DecimalFormat("0.00");
            q.setWeight(df.format(weight));
            boolean isNotInfo = true;
            if (list.size() > 0) {
                for (StaAdditionalLine l : list) {
                    if (l.getTrackingNo().equals(pi.getTrackingNo())) {
                        if (l.getSku() != null && l.getSku().getLength() != null) {
                            q.setHeight(df.format(l.getSku().getHeight().divide(new BigDecimal(10))));
                            q.setLength(df.format(l.getSku().getLength().divide(new BigDecimal(10))));
                            q.setWhight(df.format(l.getSku().getWidth().divide(new BigDecimal(10))));
                            isNotInfo = false;
                            break;
                        }
                    }
                }
            }
            if (isNotInfo) {
                q.setHeight("0");
                q.setLength("0");
                q.setWhight("0");
            }
            ztoConfirmOrderQueueDao.save(q);
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
        // 需要处理
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.ZTO, new SingleColumnRowMapper<String>(String.class));
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
