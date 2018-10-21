package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.vmi.levis.LevisDeliveryOrderDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.levisData.LevisManager;
import com.jumbo.wms.model.vmi.levisData.LevisDeliveryOrder;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.util.StringUtil;

public class VmiLevis extends VmiBaseBrand {

    private static final long serialVersionUID = -1676991219242933113L;
    protected static final Logger log = LoggerFactory.getLogger(VmiLevis.class);
    @Autowired
    private LevisDeliveryOrderDao levisDeliveryOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private LevisManager levisManager;

    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {
        List<String> pocodes = levisDeliveryOrderDao.findUnDoPoCode(new SingleColumnRowMapper<String>(String.class));
        for (String poCode : pocodes) {
            try {
                levisManager.generateInboundStaByCode(poCode);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 转店
     */
    public void generateReceivingTransfer(StockTransApplication sta) {
        levisDeliveryOrderDao.createTransOut(sta.getId());
        levisDeliveryOrderDao.createTransIn(sta.getId());
    }

    /**
     * 收货反馈
     */
    @Transactional
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        levisDeliveryOrderDao.createDeliveryReceive(sta.getId());
        BigDecimal seq = levisDeliveryOrderDao.findAdjReceviceSeq(new SingleColumnRowMapper<BigDecimal>());
        levisDeliveryOrderDao.createAdjReceive(sta.getId(), seq.longValue() + "");
    }

    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return String.valueOf(staDao.findLevisSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
    }

    /***
     * 创建大仓反馈
     * 
     * @param sta
     */
    public void generateRtnWh(StockTransApplication sta) {
        if (StockTransApplicationType.VMI_RETURN.equals(sta.getType())) {
            levisDeliveryOrderDao.createRtnReceive(sta.getId());
        }
    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        // 60 入 61出
        levisDeliveryOrderDao.createInvCkReceive(inv.getId());
    }

    public void validateReturnManager(StockTransApplication sta) {
        if (StockTransApplicationType.VMI_TRANSFER_RETURN.equals((sta.getType()))) {
            if (StringUtil.isEmpty(sta.getToLocation())) {
                throw new BusinessException(ErrorCode.VMI_RETURN_TO_LOCATION_ERROR);
            }
        }
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {

    }

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        List<LevisDeliveryOrder> ldos = levisDeliveryOrderDao.findByPoCode(slipCode);
        for (LevisDeliveryOrder ldo : ldos) {
            ldo.setStaId(staId);
            String lvsSkuCode = ldo.getProductCode() + (StringUtils.hasText(ldo.getSizeCode()) ? ldo.getSizeCode() : "") + (StringUtils.hasText(ldo.getInseamCode()) ? ldo.getInseamCode() : "");
            String extcode2 = lvsSkuCode.replaceAll("-", "");
            ldo.setStaLineId(lineDetial.get(extcode2));
        }
    }

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {
        List<LevisDeliveryOrder> ldos = levisDeliveryOrderDao.findByPoCode(slipCode);
        for (LevisDeliveryOrder ld : ldos) {
            head.setMemo(ld.getOrderCode());
        }
    }

    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        List<LevisDeliveryOrder> ldos = levisDeliveryOrderDao.findByPoCode(slipCode);
        Map<String, Long> detials = new HashMap<String, Long>();
        for (LevisDeliveryOrder ldo : ldos) {
            String lvsSkuCode = ldo.getProductCode() + (StringUtils.hasText(ldo.getSizeCode()) ? ldo.getSizeCode() : "") + (StringUtils.hasText(ldo.getInseamCode()) ? ldo.getInseamCode() : "");
            String extcode2 = lvsSkuCode.replaceAll("-", "");
            Long val = detials.get(extcode2);
            if (val == null) {
                detials.put(extcode2, Long.parseLong(ldo.getQuantity()));
            } else {
                detials.put(extcode2, val + Long.parseLong(ldo.getQuantity()));
            }
        }
        return detials;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return levisDeliveryOrderDao.findUnDoPoCode(new SingleColumnRowMapper<String>(String.class));
    }
}
