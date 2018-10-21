package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CnjhTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlCnjh")
@Transactional
public class TransOlCnjh implements TransOlInterface {
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private CnjhTask cnjhTask;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    protected static final Logger logger = LoggerFactory.getLogger(TransOlCnjh.class);

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        sd.setExtTransOrderId(sta.getCode());
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta.getCode());

        // String lpCode = Transportator.CNJH;
        // Transportator transportator = transportatorDao.findTransportatorByCode(lpCode);
        // OperationUnit op = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());

        // 请求hub的systemKey
        ChooseOption systemKeyHub = chooseOptionDao.findByCategoryCodeAndKey(Constants.CJ_SYSTEM_KEY_HUB, sta.getOwner());
        // 菜鸟的物流资源id
        ChooseOption opResourceId = chooseOptionDao.findByCategoryCodeAndKey(Constants.CJ_RESOURCE_ID, sta.getOwner());
        // 菜鸟的仓库编码
        ChooseOption opStoreCode = chooseOptionDao.findByCategoryCodeAndKey(Constants.CJ_STORE_CODE, sta.getOwner());

        if (systemKeyHub == null || opResourceId == null || opStoreCode == null) {
            logger.error("Cj=========>>systemKeyHub==null||opResourceId == null || opStoreCode == null");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // String lgorder_code = cnjhTask.getLgorderCode(staId,
        // Long.parseLong(mo.getOuterOrderCode()), Long.parseLong(transportator.getPlatformCode()),
        // op.getCode(), Constants.CJ_FIRSTLOGISTICS, Constants.CJ_FIRSTWAYBILLNO, 0);

        // 1、通过一般进口发货获取lgorder_code
        String lgorder_code =
                cnjhTask.getLgorderCode(systemKeyHub.getOptionValue(), staId, Long.parseLong(mo.getOuterOrderCode()), Long.parseLong(opResourceId.getOptionValue()), opStoreCode.getOptionValue(), Constants.CJ_FIRSTLOGISTICS, Constants.CJ_FIRSTWAYBILLNO, 0);
        if (lgorder_code == null) {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        // 2、通过获取运单信息接口查询面单url
        String waybillurl = null;
        if (lgorder_code != null) {
            waybillurl = cnjhTask.getExpressWayBillUrl(systemKeyHub.getOptionValue(), staId, lgorder_code, 0);
        }
        // 更新CJ外包仓订单创建时间
        if (waybillurl != null) {
            mo.setCreateTime(new Date());
            msgOutboundOrderDao.save(mo);
        } else {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(lgorder_code);
        return sd;
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        sd.setExtTransOrderId(sta.getCode());
        if (StringUtils.hasText(sd.getTrackingNo())) {
            return sd;
        }
        MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta.getCode());
        String lpCode = Transportator.CNJH;
        Transportator transportator = transportatorDao.findTransportatorByCode(lpCode);
        OperationUnit op = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
        // 请求hub的systemKey
        ChooseOption systemKeyHub = chooseOptionDao.findByCategoryCodeAndKey(Constants.CJ_SYSTEM_KEY_HUB, sta.getOwner());
        if (systemKeyHub == null) {
            logger.error("Cj=========>>systemKeyHub==null");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 1、通过一般进口发货获取lgorder_code
        String lgorder_code =
                cnjhTask.getLgorderCode(systemKeyHub.getOptionValue(), staId, Long.parseLong(mo.getOuterOrderCode()), Long.parseLong(transportator.getPlatformCode()), op.getCode(), Constants.CJ_FIRSTLOGISTICS, Constants.CJ_FIRSTWAYBILLNO, 0);
        // 2、通过获取运单信息接口查询面单url
        String waybillurl = null;
        if (lgorder_code != null) {
            waybillurl = cnjhTask.getExpressWayBillUrl(systemKeyHub.getOptionValue(), staId, lgorder_code, 0);
        }
        // 更新CJ外包仓订单创建时间
        if (waybillurl != null) {
            mo.setCreateTime(new Date());
            msgOutboundOrderDao.save(mo);
        } else {
            throw new BusinessException(ErrorCode.NOT_ENOUGHT_TRANS_NO);
        }
        sd.setTrackingNo(lgorder_code);
        return sd;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

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
