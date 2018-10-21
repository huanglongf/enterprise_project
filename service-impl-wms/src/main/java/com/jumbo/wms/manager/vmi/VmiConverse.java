package com.jumbo.wms.manager.vmi;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiReceiveDao;
import com.jumbo.dao.vmi.converseData.ConverseVmiStockInDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.converseData.ConverseVmiReceiveManager;
import com.jumbo.wms.manager.vmi.converseData.ConverseVmiStockInManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiReceive;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiStockIn;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;


public class VmiConverse extends VmiBaseBrand {

    private static final long serialVersionUID = 8431804472557033371L;
    protected static final Logger log = LoggerFactory.getLogger(VmiConverse.class);

    @Autowired
    private ConverseVmiStockInManager converseManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private ConverseVmiReceiveManager converReceiveManager;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private ConverseVmiReceiveDao cReceiveDao;
    @Autowired
    private SkuBarcodeDao barCodeDao;
    @Autowired
    private ConverseVmiStockInDao converseStockInDao;

    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {
        List<String> cartonNoList = converseManager.findCartonNoList();
        for (String cartonNo : cartonNoList) {
            try {
                converseManager.generateInboundSta(cartonNo);
            } catch (BusinessException e) {
                log.error("error : converse create sta" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 创建VMI转仓（调拨）反馈数据
     * 
     * @param sta
     */
    public void generateReceivingFlitting(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        sta = staDao.getByPrimaryKey(sta.getId());
        log.debug("find sta");
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        String owner = sta.getOwner();
        BiChannel shop = companyShopDao.getByCode(owner);
        log.debug("find shop");
        if (shop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String fromLocation = shop.getVmiCode();
        BiChannel toShop = companyShopDao.getByCode(sta.getAddiOwner());
        log.debug("find toShop");
        if (toShop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String toLocation = toShop.getVmiCode();
        String receiveDate = FormatUtil.formatDate(sta.getFinishTime(), "yyyyMMdd");
        if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
            // 1入库， 7退大仓RTV， 5转店退仓
            int returnType = 5;
            // 出库
            String cartNumber = sta.getRefSlipCode();
            staDao.createRtnFeedbackForConverse(sta.getId(), fromLocation, toLocation, receiveDate, cartNumber, cartNumber, VMIReceiveInfoStatus.NEW.getValue(), returnType);
            // 入库
            List<StaLine> lines = staLineDao.findByStaId(sta.getId());
            Date date = new Date();
            ConverseVmiReceive receiveInfo = null;
            for (StaLine line : lines) {
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                receiveInfo = new ConverseVmiReceive();
                receiveInfo.setCartonNumber(cartNumber);
                receiveInfo.setFromLocation(fromLocation);
                receiveInfo.setItemEanUpcCode(sku.getExtensionCode2());
                receiveInfo.setQuantity(line.getQuantity());
                receiveInfo.setReceiveDate(date);
                receiveInfo.setSapCarton(cartNumber);
                receiveInfo.setTransferNO(cartNumber);
                receiveInfo.setToLocation(toLocation);
                receiveInfo.setTransferPrefix(fromLocation);
                receiveInfo.setStatus(VMIReceiveInfoStatus.NEW);
                receiveInfo.setType(1);// 1为收货反馈 ；5为退仓反馈 7为转店反馈
                cReceiveDao.save(receiveInfo);
            }
            sta.setVmiRCStatus(Boolean.TRUE);
        }
    }

    /**
     * 创建VMI转店反馈数据
     * 
     * @param sta
     */
    public void generateReceivingTransfer(StockTransApplication stocktransapplication) {
        if (stocktransapplication == null) {
            return;
        }
        StockTransApplication sta = staDao.getByPrimaryKey(stocktransapplication.getId());
        log.debug("find sta");
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        String owner = sta.getOwner();
        BiChannel shop = companyShopDao.getByCode(owner);
        log.debug("find shop");
        if (shop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String fromLocation = shop.getVmiCode();
        BiChannel toShop = companyShopDao.getByCode(sta.getAddiOwner());
        log.debug("find toShop");
        if (toShop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String toLocation = toShop.getVmiCode();
        String receiveDate = FormatUtil.formatDate(sta.getFinishTime(), "yyyyMMdd");
        if (sta.getType().equals(StockTransApplicationType.VMI_OWNER_TRANSFER)) {
            // 1入库， 7退大仓RTV， 5转店退仓
            int returnType = 5;
            // 出库
            String cartNumber = sta.getRefSlipCode();
            staDao.createRtnFeedbackForConverse(sta.getId(), fromLocation, toLocation, receiveDate, cartNumber, cartNumber, VMIReceiveInfoStatus.NEW.getValue(), returnType);
            // 入库
            List<StaLine> lines = staLineDao.findByStaId(sta.getId());
            Date date = new Date();
            ConverseVmiReceive receiveInfo = null;
            for (StaLine line : lines) {
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                receiveInfo = new ConverseVmiReceive();
                receiveInfo.setCartonNumber(cartNumber);
                receiveInfo.setFromLocation(fromLocation);
                receiveInfo.setItemEanUpcCode(sku.getExtensionCode2());
                receiveInfo.setQuantity(line.getQuantity());
                receiveInfo.setReceiveDate(date);
                receiveInfo.setSapCarton(cartNumber);
                receiveInfo.setTransferNO(cartNumber);
                receiveInfo.setToLocation(toLocation);
                receiveInfo.setTransferPrefix(fromLocation);
                receiveInfo.setStatus(VMIReceiveInfoStatus.NEW);
                receiveInfo.setType(1);// 1为收货反馈 ；5为退仓反馈 7为转店反馈
                cReceiveDao.save(receiveInfo);
            }
            sta.setVmiRCStatus(Boolean.TRUE);
        }
    }

    /**
     * 入库作业当收货时创建反馈/VMI为收货上架时
     * 
     * @param sta
     */
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        // converReceiveManager.generateVMIReceiveInfoBySta(sta);
    }

    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        converReceiveManager.generateVMIReceiveInfoBySta(sta);
        // DO NOTHING
    }

    /**
     * 生成退仓外部单据号 生成Converse 序列 8位 8000 0001
     * 
     */
    public String generateRtnStaSlipCode() {
        return String.valueOf(staDao.findConverseSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
    }

    /***
     * 创建大仓反馈
     * 
     * @param sta
     */
    public void generateRtnWh(StockTransApplication sta) {
        String slipCode = "";
        if (sta == null) {
            log.debug("Converse Rtn ERROR");
            return;
        }
        sta = staDao.getByPrimaryKey(sta.getId());
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        String receiveDate = FormatUtil.formatDate(sta.getFinishTime(), "yyyyMMdd");
        int returnType = 0; // 1入库， 7退大仓RTV， 5转店退仓
        String toLocation = "";
        if (sta.getType() != null) {
            if (sta.getType().equals(StockTransApplicationType.VMI_RETURN)) {
                returnType = 7;
                toLocation = Constants.CONVERSE_LOCATION_CODE;
                slipCode = sta.getSlipCode1();
            } else if (sta.getType().equals(StockTransApplicationType.VMI_TRANSFER_RETURN)) {
                returnType = 5;
                toLocation = sta.getToLocation();
                slipCode = sta.getRefSlipCode();
            }
        } else {
            slipCode = sta.getRefSlipCode();
        }
        String innerShopCode = sta.getOwner();
        String fromLocation = "";
        if (innerShopCode != null) {
            BiChannel shop = shopDao.getByCode(innerShopCode);
            if (shop != null) {
                fromLocation = shop.getVmiCode();
            }
        }
        staDao.createRtnFeedbackForConverse(sta.getId(), fromLocation, toLocation, receiveDate, slipCode, sta.getRefSlipCode(), VMIReceiveInfoStatus.NEW.getValue(), returnType);
    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        converReceiveManager.generateVMIReceiveInfoByInvCk(inv);
    }

    /**
     * 生成库存状态调整单反馈
     * 
     * @param sta
     */
    public void generateInvStatusChange(StockTransApplication sta) {
        converReceiveManager.generateInvStatusChange(sta);
    }

    /**
     * 根据入库单创库存状态调整反馈
     * 
     * @param sta
     */
    @Override
    public void generateInvStatusChangeByInboundSta(StockTransApplication sta) {
        if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus()) && StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            converReceiveManager.generateInvStatusChangeByInboundSta(sta);
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
     * 创建入库作业单-成功后回调方法,用于各个品牌特殊处理
     * 
     * @param slipCode
     * @param staId
     * @param lineDetial 明细行 ,key:extcode2,value:staline id
     */
    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        List<ConverseVmiStockIn> list = converseStockInDao.findConverseInstructListByCartonNo(slipCode);
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        for (ConverseVmiStockIn cmd : list) {
            Long stalId = lineDetial.get(cmd.getItemEanUpcCode());
            StaLine stal = staLineDao.getByPrimaryKey(stalId);
            cmd.setSta(sta);
            cmd.setStaLine(stal);
        }
        barCodeDao.createConverseBarCodes();
    }

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {
        // DO NOTHING
    }

    /**
     * 创建入库作业单-根据唯一编码获取一单的入库单数据
     * 
     * @param slipCode
     * @return key:sku extcode2,value:qty
     */
    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        List<ConverseVmiStockIn> list = converseStockInDao.findConverseInstructListByCartonNo(slipCode);
        Map<String, Long> detials = new HashMap<String, Long>();
        for (ConverseVmiStockIn cmd : list) {
            Long val = detials.get(cmd.getItemEanUpcCode());
            if (val == null) {
                detials.put(cmd.getItemEanUpcCode(), cmd.getQuantity().longValue());
            } else {
                detials.put(cmd.getItemEanUpcCode(), val + cmd.getQuantity().longValue());
            }
        }
        return detials;
    }

    /**
     * 创建入库作业单-获取所有未创建单号
     * 
     * @return
     */
    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return converseManager.findCartonNoList();
    }


    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return String.valueOf(staDao.findConverseSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
    }

}
