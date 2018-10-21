package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.cch.CchStockReturnInfoDao;
import com.jumbo.dao.vmi.cch.CchStockReturnLineDao;
import com.jumbo.dao.vmi.cch.CchStockTransConfirmDao;
import com.jumbo.dao.vmi.cch.CchStockTransConfirmLineDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.cacheData.CacheParseDataManager;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.cch.CchStockReturnInfo;
import com.jumbo.wms.model.vmi.cch.CchStockReturnLine;
import com.jumbo.wms.model.vmi.cch.CchStockTransConfirm;
import com.jumbo.wms.model.vmi.cch.CchStockTransConfirmLine;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;

/**
 * 
 * Cache处理
 * 
 */
public class VmiCache extends VmiBaseBrand {

    private static final long serialVersionUID = -4653609758583654521L;
    protected static final Logger log = LoggerFactory.getLogger(VmiCache.class);

    @Autowired
    private CacheParseDataManager cchManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private CchStockTransConfirmDao confirmDao;
    @Autowired
    private CchStockTransConfirmLineDao confirmLineDao;
    @Autowired
    private CchStockReturnInfoDao returnInfoDao;
    @Autowired
    private CchStockReturnLineDao returnLineDao;

    /**
     * 通过SKUCode取商品基础信息（不同品牌取商品基本信息的表不同）
     * 
     * @param skuCode
     * @return
     */
    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return skuDao.findSKUCommandFromCCHData(skuCode, new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
    }

    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {
        log.debug("CC Start");
        List<String> parcelCodeList = cchManager.getParcelCodeWithNoSta(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
        for (String parcelCode : parcelCodeList) {
            try {
                cchManager.generateInboundStaSql(parcelCode);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 入库作业当收货时创建反馈/VMI为收货上架时
     * 
     * @param sta
     */
    @Override
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {

    }

    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        if (sta == null) {
            log.debug("Converse Rtn ERROR");
            return;
        }
        sta = staDao.getByPrimaryKey(sta.getId());
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);

        if (!sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        stv = stvDao.getByPrimaryKey(stv.getId());
        List<StvLine> stvLineList = stvLineDao.findStvLineListByStvId(stv.getId());
        Date date = new Date();
        CchStockTransConfirm cofirm = new CchStockTransConfirm();
        cofirm.setShipmentNumber(sta.getRefSlipCode());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String time = sdf2.format(stv.getFinishTime());
        String[] my = time.split(":");
        int hour = Integer.parseInt(my[0]);
        int min = Integer.parseInt(my[1]);
        int sec = Integer.parseInt(my[2]);
        Integer ss = hour * 3600 + min * 60 + sec;
        cofirm.setReceptionDate(stv.getFinishTime());
        cofirm.setReceptionHour(ss.toString());
        cofirm.setReceptionNumber(generateSeq().toString());
        cofirm.setCreateDate(date);
        cofirm.setStatus(DataStatus.CREATED.getValue());
        cofirm.setVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
        confirmDao.save(cofirm);
        CchStockTransConfirmLine confirmLine = null;
        for (StvLine stvLine : stvLineList) {
            confirmLine = new CchStockTransConfirmLine();
            confirmLine.setCreateDate(date);
            Sku sku = skuDao.getByPrimaryKey(stvLine.getSku().getId());
            confirmLine.setSkuCode(sku.getExtensionCode2());
            confirmLine.setQuantity2(stvLine.getQuantity());
            confirmLine.setStockTransConfirm(cofirm);
            confirmLineDao.save(confirmLine);
        }
    }

    /**
     * 生成序列的一个值
     * 
     * @return
     */
    private BigDecimal generateSeq() {
        return returnInfoDao.findCCHSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
    }

    /***
     * 创建大仓反馈
     * 
     * @param sta
     */
    @Override
    public void generateRtnWh(StockTransApplication sta) {
        if (sta == null) {
            log.debug("Converse Rtn ERROR");
            return;
        }
        sta = staDao.getByPrimaryKey(sta.getId());
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        if (!sta.getType().equals(StockTransApplicationType.VMI_RETURN)) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        List<Carton> list = cartonDao.findCartonByStaId(sta.getId());
        for (Carton c : list) {
            Date date = new Date();
            CchStockReturnInfo returnInfo = new CchStockReturnInfo();
            returnInfo.setCreateTime(date);
            returnInfo.setStatus(DataStatus.CREATED.getValue());
            returnInfo.setReturnDate(date);
            returnInfo.setReturnNumber(Long.valueOf(c.getCode()));
            returnInfo.setVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
            returnInfo.setStaId(sta.getId());
            returnInfo.setWarehouseCode(sta.getActivitySource() == null ? null : Integer.parseInt(sta.getActivitySource()));
            returnInfo.setCartonId(c.getId());
            returnInfoDao.save(returnInfo);
            List<CartonLine> lines = cartonDao.findCartonLineByCarId(c.getId());
            Map<Long, CchStockReturnLine> map2 = new HashMap<Long, CchStockReturnLine>();
            if (lines.size() > 0) {
                for (CartonLine carLine : lines) {
                    if (map2.get(carLine.getSku().getId()) == null && map2.get(carLine.getCarton().getId()) == null) {
                        CchStockReturnLine returnLine = new CchStockReturnLine();
                        returnLine.setCreateTime(date);
                        Sku sku = skuDao.getByPrimaryKey(carLine.getSku().getId());
                        if (sku != null) {
                            returnLine.setSkuCode(sku.getExtensionCode2());
                        }
                        returnLine.setReturnInfo(returnInfo);
                        returnLine.setQuantityShipped(carLine.getQty());
                        map2.put(carLine.getSku().getId(), returnLine);
                    } else {
                        CchStockReturnLine returnLine = new CchStockReturnLine();
                        returnLine.setCreateTime(date);
                        Sku sku = skuDao.getByPrimaryKey(carLine.getSku().getId());
                        if (sku != null) {
                            returnLine.setSkuCode(sku.getExtensionCode2());
                        }
                        returnLine.setReturnInfo(returnInfo);
                        returnLine.setQuantityShipped(carLine.getQty() + map2.get(carLine.getSku().getId()).getQuantityShipped());
                        map2.put(carLine.getSku().getId(), returnLine);
                    }
                }
                for (CchStockReturnLine l : map2.values()) {
                    returnLineDao.save(l);
                }
            }
        }
    }

    /**
     * VMI入库收货反馈（作业单完成时反馈）
     * 
     * @param sta
     */
    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {
        // DO NOTHING
    }

    /**
     * 创建入库作业单-获取所有未创建单号
     * 
     * @return
     */
    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return cchManager.getParcelCodeWithNoSta(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
    }

    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {};
}
