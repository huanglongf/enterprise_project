package com.jumbo.wms.manager.vmi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeReceiveDataLineDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeRtnOutDataDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveDataLine;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeRtnOutData;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiGymboree extends VmiBaseBrand {
    private static final long serialVersionUID = 322710330108816589L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;

    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;

    @Autowired
    private GymboreeRtnOutDataDao gymboreeRtnOutDataDao;
    @Autowired
    private GymboreeReceiveDataLineDao gymboreeReceiveDataLineDao;
    @Autowired
    private BiChannelDao biChannelDao;

    @Override
    public void generateInboundSta() {}

    /***
     * 入库反馈
     */
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        if (sta != null && stv != null) {
            if (!sta.getVmiRCStatus()) {
                List<GymboreeReceiveDataLine> dl = gymboreeReceiveDataLineDao.getByStaId(sta.getId(), new BeanPropertyRowMapper<GymboreeReceiveDataLine>(GymboreeReceiveDataLine.class));
                if (dl.size() == 0) {
                    log.error("Gymboree return inbound detail has no result .....");
                    throw new BusinessException("");
                } else {
                    for (GymboreeReceiveDataLine dll : dl) {
                        GymboreeRtnOutData rd = new GymboreeRtnOutData();
                        rd.setCreateTime(new Date());
                        rd.setStatus(DefaultStatus.CREATED);
                        rd.setStaId(sta.getId());
                        rd.setFchrItemId(dll.getFchrItemID());
                        rd.setFlotQuantity(dll.getFlotQuantity());
                        rd.setFchrSourceCode(dll.getFchrSourceCode());
                        rd.setType(GymboreeRtnOutData.INBOUND);
                        rd.setFchrWarehouseID(dll.getFchrWarehouseID());
                        rd.setFchrBarcode(dll.getFchrBarCode());
                        rd.setFchrFree2(dll.getFchrFree2());
                        rd.setFchrInOutVouchID(dll.getFchrInOutVouchID());
                        rd.setFchrOutVouchDetailID(dll.getFchrOutVouchDetailID());
                        rd.setOwner(dll.getOwner());
                        gymboreeRtnOutDataDao.save(rd);
                    }
                }
            }
            nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
        }
    }

    public void createVMIReceiveInfoByInvCk(InventoryCheck inv) {}

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        // 调整类型
        if (!inv.getType().equals(InventoryCheckType.VMI)) {
            return;
        }
        List<InventoryCheckDifTotalLine> lines = icLineDao.findByInventoryCheck(inv.getId());
        String vmiCode = biChannelDao.getVmiCodeByInv(inv.getId(), new SingleColumnRowMapper<String>(String.class));
        for (int i = 0; i < lines.size(); i++) {
            InventoryCheckDifTotalLine line = lines.get(i);
            GymboreeRtnOutData rd = new GymboreeRtnOutData();
            rd.setFchrSourceCode(inv.getSlipCode());
            rd.setCreateTime(new Date());
            rd.setStatus(DefaultStatus.CREATED);
            rd.setFlotQuantity(line.getQuantity() > 0 ? line.getQuantity() : -line.getQuantity());
            rd.setType(line.getQuantity() > 0 ? GymboreeRtnOutData.INVIN : GymboreeRtnOutData.INVOUT);
            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
            if (StringUtil.isEmpty(sku.getExtensionCode2())) {
                throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
            }
            rd.setFchrItemId(line.getSku().getExtensionCode3());
            rd.setStaId(inv.getId());
            rd.setFchrBarcode(line.getSku().getBarCode());
            rd.setFchrFree2(line.getSku().getSkuSize());
            rd.setOwner(vmiCode);
            gymboreeRtnOutDataDao.save(rd);
        }
    }

    public void generateReceivingTransfer(StockTransApplication stockTransApplication) {}

    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {}

    public void createInboundSta() {}

    public void createReceivingTransfer(StockTransApplication sta) {}

    public void createReceivingWithReceive(StockTransApplication sta, StockTransVoucher stv) {}

    public void createVMITranscationWH() {}

    /**
     * 退大仓反馈
     */
    public void generateRtnWh(StockTransApplication stockTransApplication) {
        if (stockTransApplication == null) {
            return;
        }
        StockTransApplication sta = staDao.getByPrimaryKey(stockTransApplication.getId());
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        gymboreeRtnOutDataDao.insertRtnResultDataToDB(sta.getId());
    }

    public String generateRtnStaSlipCode() {
        return null;
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {}

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {}

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {}

    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        return null;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return null;
    }
}
