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
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.nikeDate.NikeCheckReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockInDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.nike.manager.NikeManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeReturnReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeStockReceiveData;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;

public class VmiNike extends VmiBaseBrand {
    private static final long serialVersionUID = 322710330108816589L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    @Autowired
    private NikeStockInDao nikeStockInDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private NikeCheckReceiveDao nikeCheckReceiveDao;
    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    private NikeManager nikeManager;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private BiChannelDao companyShopDao;

    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {
        List<String> refNoList = nikeStockInDao.findNikeVmiStockIn(new SingleColumnRowMapper<String>(String.class));
        for (String ref : refNoList) {
            try {
                nikeManager.generateStaByRefNo(ref, null);
            } catch (Exception e) {
                log.error("nike generate sta error : " + ref);
            }
        }
        // nike hub 收货
        List<String> refNoList2 = nikeStockInDao.findNikeVmiStockInBrand(new SingleColumnRowMapper<String>(String.class));
        for (String ref : refNoList2) {
            try {
                nikeManager.generateStaByRefNo(ref, "1");
            } catch (Exception e) {
                log.error("nike generate sta error2 : " + ref);
            }
        }

    }

    /***
     * 入库反馈
     */
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        if (sta != null && stv != null) {
            List<StockTransApplication> stas = staDao.findBySlipCode1AndStatus(sta.getSlipCode1());
            if (stas.size() == 1) {
                List<StockTransApplication> stas1 = staDao.findBySlipCode1Inbound(sta.getSlipCode1());
                for (StockTransApplication sta1 : stas1) {
                    List<StaLine> staline = staLineDao.findByStaId(sta1.getId());
                    for (StaLine line : staline) {
                        NikeStockReceive rec = new NikeStockReceive();
                        rec.setReferenceNo(sta1.getRefSlipCode());
                        rec.setItemEanUpcCode(line.getSku().getExtensionCode2());
                        rec.setReceiveDate(new Date());
                        rec.setQuantity(line.getQuantity());
                        rec.setFromLocation(sta1.getFromLocation());
                        rec.setToLocation(sta1.getToLocation());
                        rec.setTransferPrefix(sta1.getFromLocation());
                        rec.setSapCarton(sta1.getMemo());
                        rec.setStatus(NikeStockReceive.CREATE_STATUS);
                        rec.setCartonStatus(NikeStockReceiveData.CLOSED);
                        rec.setType(NikeStockReceive.INBOUND_RECEIVE_TYPE);
                        rec.setCreateDate(new Date());
                        if ("1".equals(sta1.getNikeVmiStockinSource())) {
                            rec.setBrand("1");
                            rec.setStaId(sta1.getId());

                        }
                        nikeStockReceiveDao.save(rec);
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
        if (!inv.getType().equals(InventoryCheckType.VMI) && !"HD".equals(inv.getRemork())) {
            return;
        }
        if (inv.getOu().getId() == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        BiChannel shop = inv.getShop();
        log.debug("find shop");
        if (shop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        List<InventoryCheckDifTotalLine> lines = icLineDao.findByInventoryCheck(inv.getId());
        Date date = new Date();
        NikeCheckReceive check = null;
        String fileCode = nikeManager.getFileCode();
        for (int i = 0; i < lines.size(); i++) {
            InventoryCheckDifTotalLine line = lines.get(i);
            check = new NikeCheckReceive();
            check.setCheckCode(inv.getCode());
            check.setStatus(NikeCheckReceive.CREATE_STATUS);
            check.setType(NikeCheckReceive.SYS_CHECK_TYPE);
            check.setCreateDate(date);
            check.setFileCode(fileCode);
            check.setOwnerCode(shop.getVmiCode());
            check.setQuantity(line.getQuantity());
            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
            if (StringUtil.isEmpty(sku.getExtensionCode2())) {
                throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
            }
            check.setUpc(line.getSku().getExtensionCode2());
            check.setReceiveDate(date);
            nikeCheckReceiveDao.save(check);
        }
    }

    /****
     * 转店出库反馈 = 转店退仓， 转店入库反馈
     */
    @Transactional
    public void generateReceivingTransfer(StockTransApplication stockTransApplication) {
        if (stockTransApplication == null) {
            return;
        }
        StockTransApplication sta = staDao.getByPrimaryKey(stockTransApplication.getId());
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        BiChannel shop2 = companyShopDao.getByCode(sta.getAddiOwner());
        if (shop2 != null && "1028".equals(shop2.getVmiCode())) {// 转店走退大仓接口
            String owner = sta.getOwner();
            BiChannel shop = companyShopDao.getByCode(owner);
            if (shop == null || shop2 == null) {
                throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
            }
            String fromLocation = shop2.getVmiCode();
            String receiveDate = FormatUtil.formatDate(sta.getFinishTime(), "yyyyMMdd");
            staDao.createRtnFeedbackForNike(sta.getId(), Constants.NIKE_RESOURCE, sta.getRefSlipCode(), receiveDate, fromLocation, NikeReturnReceive.CREATE_STATUS, NikeReturnReceive.RETURN_VMI_RETURN_TYPE);
        } else {
        // 转店退仓反馈
        String receiveDate = FormatUtil.formatDate(sta.getFinishTime(), "yyyyMMdd");
        String owner = sta.getOwner();
        BiChannel shop = companyShopDao.getByCode(owner);
        if (shop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String fromLocation$ = shop.getVmiCode();
        BiChannel toShop = companyShopDao.getByCode(sta.getAddiOwner());
        if (toShop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String toLocation$ = toShop.getVmiCode();

        if (fromLocation$.equals(toLocation$)) {
            return;
        }

        // 转店退仓
        staDao.createTransferOutFeedbackForNike(sta.getId(), Constants.NIKE_RESOURCE, sta.getRefSlipCode(), receiveDate, NikeReturnReceive.CREATE_STATUS, NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, toLocation$, fromLocation$);
        List<StockTransVoucher> inStvs = stvDao.findFinishByStaWithDirection(sta.getId(), TransactionDirection.INBOUND);
        StockTransVoucher inStv = null;
        if (inStvs != null && !inStvs.isEmpty()) {
            inStv = inStvs.get(0);
        }
        if (inStv == null) {
            log.error("error ,stv is null...........");
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        if (sta != null && inStv != null && !sta.getVmiRCStatus()) {
            List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
            for (StaLine staLine : staLines) {
                if (staLine.getSku().getIsGift() == null || !staLine.getSku().getIsGift()) {
                    NikeStockReceive rec = new NikeStockReceive();
                    rec.setReferenceNo(sta.getRefSlipCode());
                    rec.setItemEanUpcCode(staLine.getSku().getExtensionCode2());
                    rec.setReceiveDate(new Date());
                    rec.setQuantity(staLine.getQuantity());
                    rec.setFromLocation(fromLocation$);
                    rec.setToLocation(toLocation$);
                    rec.setTransferPrefix(fromLocation$);
                    rec.setStatus(NikeStockReceive.CREATE_STATUS);
                    // 转店入库反馈 type 2
                    rec.setType(NikeStockReceive.TRANSFER_INBOUND_RECEIVE_TYPE);
                    rec.setCreateDate(new Date());
                    rec.setPoStatus(NikeStockReceive.CLOSED);
                    rec.setCartonStatus(NikeStockReceive.CLOSED);
                    nikeStockReceiveDao.save(rec);
                }
            }
            nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
            inStv.setLastModifyTime(new Date());
            inStv.setStatus(StockTransVoucherStatus.FINISHED);
            stvDao.save(inStv);
        }
        }
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

    public void createInboundSta() {}

    public void createReceivingTransfer(StockTransApplication sta) {}

    public void createReceivingWithReceive(StockTransApplication sta, StockTransVoucher stv) {}

    public void createVMITranscationWH() {}

    /**
     * NIKE 退仓反馈 100为A品，120为B品，101为C品 100 CC 良品 120 DR 待处理 101 SS 残次品
     */
    public void generateRtnWh(StockTransApplication stockTransApplication) {
        if (stockTransApplication == null) {
            return;
        }
        StockTransApplication sta = staDao.getByPrimaryKey(stockTransApplication.getId());
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }

        boolean isVMIReturn = false;
        String owner = sta.getOwner();
        BiChannel shop = companyShopDao.getByCode(owner);
        if (shop == null) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String fromLocation = shop.getVmiCode();
        String toLocation = sta.getToLocation();

        StockTransApplicationType type = sta.getType();
        if (StockTransApplicationType.VMI_RETURN.equals(type)) {
            // VMI退大仓
            isVMIReturn = true;
        }
        String receiveDate = FormatUtil.formatDate(sta.getFinishTime(), "yyyyMMdd");
        if (isVMIReturn) {
            // vmi退大仓 - old
            staDao.createRtnFeedbackForNike(sta.getId(), Constants.NIKE_RESOURCE, sta.getRefSlipCode(), receiveDate, fromLocation, NikeReturnReceive.CREATE_STATUS, NikeReturnReceive.RETURN_VMI_RETURN_TYPE);
        } else {
            // 转店退仓
            staDao.createTransferOutFeedbackForNike(sta.getId(), Constants.NIKE_RESOURCE, sta.getRefSlipCode(), receiveDate, NikeReturnReceive.CREATE_STATUS, NikeReturnReceive.RETURN_TRANSFER_OUT_TYPE, toLocation, fromLocation);
        }
    }

    /***
     * 生成NIKE C00000000000 + 8位序列
     */
    public String generateRtnStaSlipCode() {
        return "C" + String.valueOf(staDao.findNikeSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
    }

    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return "C" + String.valueOf(staDao.findNikeSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {}

    /**
     * 创建入库作业单成功后回调方法,用于各个品牌特殊处理
     * 
     * @param slipCode
     * @param staId
     * @param lineDetial 明细行 ,key:extcode2,value:staline id
     */
    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        List<NikeVmiStockInCommand> list = nikeStockInDao.getByReferenceNo(slipCode);
        for (NikeVmiStockInCommand cmd : list) {
            cmd.setStaId(staId);
            cmd.setStatus(NikeVmiStockInCommand.FINISH_STATUS);
            cmd.setStaLineId(lineDetial.get(cmd.getItemEanUpcCode()));
        }
    }

    /**
     * 创建入库作业单-设置作业单头特殊信息
     * 
     * @param slipCode
     * @param head
     */
    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {
        NikeVmiStockInCommand cmd = nikeStockInDao.getOneByReferenceNo(slipCode);
        head.setFromLocation(cmd.getFromLocation());
        head.setToLocation(cmd.getToLocation());
        head.setSlipCode1(cmd.getSapDNNo());
        head.setMemo(cmd.getSapDNNo());
    }

    /**
     * 创建入库作业单-根据唯一编码获取一单的入库单数据
     * 
     * @param slipCode
     * @return key:sku extcode2,value:qty
     */
    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        List<NikeVmiStockInCommand> list = nikeStockInDao.getByReferenceNo(slipCode);
        Map<String, Long> detials = new HashMap<String, Long>();
        for (NikeVmiStockInCommand cmd : list) {
            Long val = detials.get(cmd.getItemEanUpcCode());
            if (val == null) {
                detials.put(cmd.getItemEanUpcCode(), cmd.getQuantity());
            } else {
                detials.put(cmd.getItemEanUpcCode(), val + cmd.getQuantity());
            }
        }
        return detials;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return nikeStockInDao.findNikeVmiStockIn(new SingleColumnRowMapper<String>(String.class));
    }


    @Override
    public void generateReceivingFlitting(StockTransApplication sta) {
        generateReceivingTransfer(sta);
    }
}
