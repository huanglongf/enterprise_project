/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TtkConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.task.ttk.TtkOrderTaskManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.TtkConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

/**
 * @author lichuan
 * 
 */
@Service("transOlTtk")
@Transactional
public class TransOlTtk extends BaseManagerImpl implements TransOlInterface {

    private static final long serialVersionUID = -3872288598760117204L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private TtkConfirmOrderQueueDao ttkConfirmOrderQueueDao;
    @Autowired
    private TtkOrderTaskManager ttkOrderTaskManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    
    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {// 设置大头笔信息？？？
        StaDeliveryInfo info = new StaDeliveryInfo();
        info.setExtTransOrderId("TTKDEX" + new Date().getTime());
        transProvideNoDao.flush();
        String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.TTKDEX, new SingleColumnRowMapper<String>(String.class));
        if (transNo == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
        whTransProvideNo.setStaid(order.getId());
        transProvideNoDao.save(whTransProvideNo);
        info.setTrackingNo(transNo);
        return info;
    }

    /**
     * 匹配运单号及设置大头笔信息
     */
    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
        StaDeliveryInfo sd = new StaDeliveryInfo();
        try {
            StockTransApplication stab = staDao.getByPrimaryKey(staId);
            sd = stab.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(staId);
            }
            if (StringUtils.hasText(sd.getTrackingNo())) {
                return sd;
            }
            sd.setExtTransOrderId(stab.getCode());
            String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.TTKDEX, new SingleColumnRowMapper<String>(String.class));
            if (transNo == null) {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
            sd.setTrackingNo(transNo);
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(staId);
            transProvideNoDao.flush();
            // 设置大头笔信息
            ttkOrderTaskManager.setTransBigWord(staId);
        } catch (Exception e) {
            log.error("====>matching ttk trans no or set trans big word throw exception:", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return sd;
    }


    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        StaDeliveryInfo sd = new StaDeliveryInfo();
        try {
            StockTransApplication stab = staDao.getByPrimaryKey(staId);
            sd = stab.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(staId);
            }
            if (StringUtils.hasText(sd.getTrackingNo())) {
                return sd;
            }
            sd.setExtTransOrderId(stab.getCode());
            String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.TTKDEX, new SingleColumnRowMapper<String>(String.class));
            if (transNo == null) {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
            sd.setTrackingNo(transNo);
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(staId);
            transProvideNoDao.flush();
            // 设置大头笔信息
            ttkOrderTaskManager.setTransBigWord(staId);
        } catch (Exception e) {
            log.error("====>matching ttk trans no or set trans big word throw exception:", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return sd;
    }

    /**
     * 根据包裹匹配运单号
     */
    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        StaDeliveryInfo sd = new StaDeliveryInfo();
        try {
            // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
            PackageInfo info = packageInfoDao.getByPrimaryKey(packId);
            StockTransApplication stab = null;
            sd = null;
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
            String transNo = transProvideNoDao.getTranNoByLpcode(Transportator.TTKDEX, new SingleColumnRowMapper<String>(String.class));
            if (transNo == null) {
                throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
            }
            info.setTrackingNo(transNo);// 运到号更新到包裹信息
            WhTransProvideNo whTransProvideNo = transProvideNoDao.getByTranNo(transNo);
            whTransProvideNo.setStaid(stab.getId());// 作业单号更新到运单

            ttkOrderTaskManager.setTransBigWord(stab.getId());
        } catch (Exception e) {
            log.error("====>matching ttk trans no by packId or set trans big word throw exception:", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return sd;
    }

    /**
     * 出库记录订单回传信息
     */
    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {
        StaDeliveryInfo di = sta.getStaDeliveryInfo();
        List<PackageInfo> piList = packageInfoDao.findByStaId(sta.getId());
        if (piList == null || piList.size() == 0) {
            return;
        }
        for (PackageInfo pi : piList) {
            TtkConfirmOrderQueue q = new TtkConfirmOrderQueue();
            q.setStaCode(sta.getCode());
            q.setExeCount(0L);
            q.setEcCompanyId("BAOZUN");
            q.setLogisticProviderID("TTKDEX");
            q.setCustomerId("BAOZUNCUS");
            q.setSiteName("");
            q.setTxLogisticID(sta.getCode());
            q.setTradeNo(sta.getRefSlipCode());
            q.setMailNo(pi.getTrackingNo());
            q.setOrderType((true == di.getIsCod() ? 1 : 0));
            q.setServiceType(1L);
            q.setName(di.getReceiver());
            q.setPostCode(di.getZipcode());
            q.setPhone(di.getTelephone());
            q.setMobile(di.getMobile());
            q.setProv(di.getProvince());
            q.setCity(di.getCity());
            q.setArea(di.getDistrict());
            q.setAddress(di.getAddress());
            q.setSendStartTime(new Date());
            q.setRemark("BAOZUN ORDER FEEDBACK");
            ttkConfirmOrderQueueDao.save(q);
        }

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
