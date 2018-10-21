package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiAdjDao;
import com.jumbo.dao.vmi.defaultData.VmiAdjLineDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiCfgOrderCodeDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRtwDao;
import com.jumbo.dao.vmi.defaultData.VmiRtwLineDao;
import com.jumbo.dao.vmi.defaultData.VmiTfxDao;
import com.jumbo.dao.vmi.defaultData.VmiTfxLineDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.ext.VmiExtFactory;
import com.jumbo.wms.manager.vmi.ext.VmiInterfaceExt;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.RsnType;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiAdjLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiCfgOrderCodeCommand;
import com.jumbo.wms.model.vmi.Default.VmiCfgOrderCodeType;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiOpType;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;

public class VmiEC extends VmiBaseBrand {

    /**
     * 
     */
    private static final long serialVersionUID = 2929519367957615368L;

    private static final String invName = "良品";

    private static final String invNames = "良品不可销售";

    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao biCannelDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private VmiRsnDao vmiRsnDao;
    @Autowired
    private VmiRsnLineDao vmiRsnLineDao;
    @Autowired
    private StockTransTxLogDao stvLogDao;
    @Autowired
    private VmiTfxDao vmiTfxDao;
    @Autowired
    private VmiTfxLineDao vmiTfxLineDao;
    @Autowired
    private VmiRtwDao vmiRtwDao;
    @Autowired
    private VmiRtwLineDao vmiRtwLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private VmiCfgOrderCodeDao vmiCfgOrderCodeDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private VmiAdjDao vmiAdjDao;
    @Autowired
    private VmiAdjLineDao vmiAdjLineDao;
    @Autowired
    private VmiExtFactory vmiExtFactory;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;

    @Override
    public void generateInboundSta() {}

    @Override
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {}

    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        if (sta != null && stv != null) {
            BiChannelCommand bi = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
            VmiRsnDefault vrd = new VmiRsnDefault();
            vrd.setStoreCode(bi.getVmiCode());
            vrd.setCreateTime(new Date());
            vrd.setFinishTime(new Date());
            vrd.setFromLocation(sta.getFromLocation());
            vrd.setToLoaction(sta.getToLocation());
            vrd.setReceiveDate(getFormateDateToData("yyyyMMddHHmmss", new Date()));
            vrd.setStatus(VmiGeneralStatus.NEW);
            vrd.setVersion(1);
            vrd.setVmiSource(bi.getVmiSource());
            vrd.setWhCode(sta.getMainWarehouse().getCode());
            vrd.setStaId(sta);
            boolean vmiCheck = false;
            VmiDefaultInterface vv = null;
            if (!StringUtil.isEmpty(bi.getDefaultCode())) {
                // 判断是否有品牌通用定制逻辑
                vv = vmiDefaultFactory.getVmiDefaultInterface(bi.getDefaultCode());
                vmiCheck = true;
            }
            VmiInterfaceExt vmiBrandExt = null;
            if (null != bi.getIsVmiExt() && true == bi.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(bi.getVmiCode());// 品牌逻辑定制
            }
            Map<Long, Long> detials = new HashMap<Long, Long>();
            if (bi.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
                vrd.setOrderCode(sta.getRefSlipCode());
                vrd.setExtMemo(sta.getCode());
                if (null != vmiBrandExt) {
                    ExtParam extParam = new ExtParam();
                    if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                        extParam.setSta(sta);
                    }
                    vmiBrandExt.generateReceivingWhenShelvRsnAspect(vrd, extParam);
                }
                vmiRsnDao.save(vrd);
                // 按单收货
                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPEONE.getValue()))) {
                    if (!sta.getVmiRCStatus()) {
                        List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                        // 按计划量反馈=如果收货数量为100,这次收5件,那反馈100件 按t_wh_sta_line.quantity生成
                        for (StaLine line : staline) {
                            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                            VmiRsnLineDefault v = new VmiRsnLineDefault();
                            v.setQty(line.getQuantity());
                            v.setUpc(sku.getExtensionCode2());
                            v.setRsnId(vrd);
                            if (null != vmiBrandExt) {
                                ExtParam extParam = new ExtParam();
                                if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                                    extParam.setStaLine(line);
                                    extParam.setRsnType(bi.getRsnTypeString());
                                    extParam.setSta(sta);// 新增
                                }
                                vmiBrandExt.generateReceivingWhenShelvRsnLineAspect(v, extParam);
                            }
                            vmiRsnLineDao.save(v);
                        }
                        nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
                    }
                }
                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
                    // 按实际收货反馈=如果收货数量为100,这次收5件,那反馈5件 t_wh_st_log.quantity
                    List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    for (StockTransTxLogCommand log : logList) {
                        // 合并相同商品数量
                        Long val = detials.get(Long.parseLong(log.getSkuCode()));
                        if (val == null) {
                            detials.put(Long.parseLong(log.getSkuCode()), log.getInQty());
                        } else {
                            detials.put(Long.parseLong(log.getSkuCode()), val + log.getInQty());
                        }
                    }
                    for (Entry<Long, Long> line : detials.entrySet()) {
                        Sku sku = skuDao.getByPrimaryKey(line.getKey());
                        VmiRsnLineDefault v = new VmiRsnLineDefault();
                        v.setQty(line.getValue());
                        v.setUpc(sku.getExtensionCode2());
                        v.setRsnId(vrd);
                        if (null != vmiBrandExt) {
                            ExtParam extParam = new ExtParam();
                            if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                                SkuCommand skuCmd = new SkuCommand();
                                skuCmd.setExtensionCode2(sku.getExtensionCode2());
                                extParam.setSkuCmd(skuCmd);
                                extParam.setStaLineList(staLineDao.findByStaId(sta.getId()));
                                extParam.setRsnType(bi.getRsnTypeString());
                                extParam.setSta(sta);// 新增
                            }
                            vmiBrandExt.generateReceivingWhenShelvRsnLineAspect(v, extParam);
                        }
                        vmiRsnLineDao.save(v);
                    }
                }
            }
            if (bi.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
                vrd.setOrderCode(sta.getSlipCode1());
                vrd.setCartonNo(sta.getRefSlipCode());
                vrd.setExtMemo(sta.getCode());
                if (null != vmiBrandExt) {
                    ExtParam extParam = new ExtParam();
                    if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                        extParam.setSta(sta);
                        extParam.setAsnOrderType(bi.getAsnTypeString());
                    }
                    vmiBrandExt.generateReceivingWhenShelvRsnAspect(vrd, extParam);
                }
                vmiRsnDao.save(vrd);
                // 按箱收货
                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPEONE.getValue()))) {
                    // 按计划量反馈=如果收货数量为100,这次收5件,那反馈100件 按t_wh_sta_line.quantity生成
                    if (!sta.getVmiRCStatus()) {
                        List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                        for (StaLine line : staline) {
                            Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                            VmiRsnLineDefault v = new VmiRsnLineDefault();
                            v.setQty(line.getQuantity());
                            v.setUpc(sku.getExtensionCode2());
                            v.setCartonNo(sta.getRefSlipCode());
                            v.setRsnId(vrd);
                            if (null != vmiBrandExt) {
                                ExtParam extParam = new ExtParam();
                                if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                                    extParam.setStaLine(line);
                                    extParam.setRsnType(bi.getRsnTypeString());
                                    extParam.setSta(sta);// 新增
                                    extParam.setAsnOrderType(bi.getAsnTypeString());
                                }
                                vmiBrandExt.generateReceivingWhenShelvRsnLineAspect(v, extParam);
                            }
                            vmiRsnLineDao.save(v);
                        }
                    }
                    nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
                }
                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
                    // 按实际收货反馈=如果收货数量为100,这次收5件,那反馈5件 t_wh_st_log.quantity
                    List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    for (StockTransTxLogCommand log : logList) {
                        // 合并相同商品数量
                        Long val = detials.get(Long.parseLong(log.getSkuCode()));
                        if (val == null) {
                            detials.put(Long.parseLong(log.getSkuCode()), log.getInQty());
                        } else {
                            detials.put(Long.parseLong(log.getSkuCode()), val + log.getInQty());
                        }
                    }
                    for (Entry<Long, Long> line : detials.entrySet()) {
                        Sku sku = skuDao.getByPrimaryKey(line.getKey());
                        VmiRsnLineDefault v = new VmiRsnLineDefault();
                        v.setQty(line.getValue());
                        v.setUpc(sku.getExtensionCode2());
                        v.setCartonNo(sta.getRefSlipCode());
                        v.setRsnId(vrd);
                        if (null != vmiBrandExt) {
                            ExtParam extParam = new ExtParam();
                            if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                                SkuCommand skuCmd = new SkuCommand();
                                skuCmd.setExtensionCode2(sku.getExtensionCode2());
                                extParam.setSkuCmd(skuCmd);
                                extParam.setStaLineList(staLineDao.findByStaId(sta.getId()));
                                extParam.setRsnType(bi.getRsnTypeString());
                                extParam.setSta(sta);// 新增
                                extParam.setAsnOrderType(bi.getAsnTypeString());
                            } else if (Constants.SPEEDO_VMI_CODE.equals(bi.getVmiCode())) {// SPEEDO品牌根据业务可传递不同的参数
                                SkuCommand skuCmd = new SkuCommand();
                                skuCmd.setExtensionCode2(sku.getExtensionCode2());
                                skuCmd.setId(sku.getId());// 设置skuId
                                extParam.setSkuCmd(skuCmd);
                                extParam.setStaLineList(staLineDao.findByStaId(sta.getId()));
                                extParam.setRsnType(bi.getRsnTypeString());
                                extParam.setSta(sta);// 新增
                                extParam.setAsnOrderType(bi.getAsnTypeString());
                            }
                            vmiBrandExt.generateReceivingWhenShelvRsnLineAspect(v, extParam);
                        }
                        if (vmiCheck) {
                            ExtParam ext = new ExtParam();
                            ext.setBi(bi);
                            ext.setSta(sta);
                            ext.setSku(sku);
                            vv.generateReceivingWhenShelv(ext, v);
                        }
                        vmiRsnLineDao.save(v);
                    }
                }
            }
        }
    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        if (inv == null) {
            return;
        }
        BiChannel bi = biCannelDao.getByPrimaryKey(inv.getShop().getId());
        if (bi == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (!StringUtil.isEmpty(bi.getOpType())) {
            boolean b = checkVmiOpTypeReturn(bi.getOpType(), VmiOpType.ADJ);
            if (b) {
                // 不需要生成VMI反馈数据直接return
                return;
            }
        }
        OperationUnit ou = operationUnitDao.getByPrimaryKey(inv.getOu().getId());
        if (ou == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        VmiAdjDefault vad = new VmiAdjDefault();// VMI调整反馈表
        if (StringUtil.isEmpty(inv.getSlipCode())) {
            vad.setOrderCode(inv.getCode());
        } else {
            vad.setOrderCode(inv.getSlipCode());
            StockTransApplication sta = staDao.findBySlipCodeOwner(inv.getSlipCode(), bi.getCode());
            if (sta != null) {
                vad.setStaId(sta);
            }
        }
        vad.setCreateTime(new Date());
        vad.setFinishTime(new Date());
        vad.setAdjDate(getFormateDateToData("yyyyMMddHHmmss", new Date()));
        vad.setWhCode(ou.getCode());
        vad.setStoreCode(bi.getVmiCode());
        vad.setVmiSource(bi.getVmiSource());
        vad.setInvId(inv);
        vad.setAdjReason(inv.getReasonCode());// 原因
        vad.setStatus(VmiGeneralStatus.NEW);
        vad.setVersion(1);
        VmiInterfaceExt vmiBrandExt = null;
        if (null != bi.getIsVmiExt() && true == bi.getIsVmiExt()) {
            vmiBrandExt = vmiExtFactory.getBrandVmi(bi.getVmiCode());// 品牌逻辑定制
        }
        if (null != vmiBrandExt) {
            ExtParam extParam = new ExtParam();
            if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

            }
            vmiBrandExt.generateVMIReceiveInfoByInvCkAdjAspect(vad, extParam);
        }
        vmiAdjDao.save(vad);
        List<StockTransTxLogCommand> stl = stvLogDao.findStLogByInventoryCheckId(inv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        for (StockTransTxLogCommand l : stl) {
            Sku sku = skuDao.getByPrimaryKey(l.getSkuId());
            if (sku == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            String upc = sku.getExtensionCode2();
            if (StringUtil.isEmpty(upc)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            InventoryStatus i = inventoryStatusDao.getByPrimaryKey(l.getInvStatusId());
            if (i == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            VmiAdjLineDefault vald = new VmiAdjLineDefault();
            vald.setUpc(upc);
            if (TransactionDirection.INBOUND.getValue() == l.getIntDirection()) {
                vald.setQty((null == l.getQuantity() ? 0L : l.getQuantity()));
            } else if (TransactionDirection.OUTBOUND.getValue() == l.getIntDirection()) {
                vald.setQty((null == l.getQuantity() ? 0L : -l.getQuantity()));
            }
            if (i.getName().equals(invName) || i.getName().equals(invNames)) {
                vald.setInvStatus("1");
            } else {
                vald.setInvStatus("0");
            }
            vald.setVersion(1);
            vald.setAdjId(vad);
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                }
                vmiBrandExt.generateVMIReceiveInfoByInvCkAdjLineAspect(vald, extParam);
            }
            vmiAdjLineDao.save(vald);
        }
    }

    /**
     * Vmi调拨（转仓）单据创建
     */
    @Override
    public void generateVMITranscationWH() {}

    /**
     * VMI转店反馈数据
     * 
     * @param sta
     */
    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        if (sta.getType() == StockTransApplicationType.VMI_OWNER_TRANSFER) {
            String whCode = operationUnitDao.selectWhCodeByStaId(sta.getId(), new SingleColumnRowMapper<String>(String.class));
            // 必须是88状态作业单
            BiChannelCommand shop = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
            if (!StringUtil.isEmpty(shop.getOpType())) {
                boolean b = checkVmiOpTypeReturn(shop.getOpType(), VmiOpType.TFXRSN);
                if (b) {
                    // 不需要生成VMI反馈数据直接return
                    return;
                }
            }
            VmiTfxDefault tfx = new VmiTfxDefault();// 转店退仓反馈表
            tfx.setCreateTime(new Date());
            tfx.setFinishTime(new Date());
            tfx.setStoreCode(shop.getVmiCode());
            tfx.setFromLocation(shop.getVmiCode());
            tfx.setToLoaction(sta.getToLocation());
            tfx.setOrderCode(sta.getRefSlipCode());
            tfx.setVmiSource(shop.getVmiSource());
            tfx.setReturnDate(getFormateDateToData("yyyyMMddHHmmss", sta.getFinishTime()));
            tfx.setWhCode(sta.getMainWarehouse().getCode());
            tfx.setStatus(VmiGeneralStatus.NEW);
            tfx.setWhCode(whCode);
            tfx.setVersion(1);
            tfx.setStaId(sta);
            VmiInterfaceExt vmiBrandExt = null;
            if (null != shop.getIsVmiExt() && true == shop.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                }
                vmiBrandExt.generateReceivingTransferTfxAspect(tfx, extParam);
            }
            vmiTfxDao.save(tfx);
            VmiRsnDefault vrd = new VmiRsnDefault();// 收货反馈表
            vrd.setCreateTime(new Date());
            vrd.setFinishTime(new Date());
            vrd.setOrderCode(sta.getRefSlipCode());
            vrd.setStoreCode(shop.getVmiCode());
            vrd.setFromLocation(shop.getVmiCode());
            vrd.setToLoaction(sta.getToLocation());
            // 转店生成收货反馈数据是被锁定状态 等待收货指令解锁
            vrd.setStatus(VmiGeneralStatus.LOCKED);
            vrd.setReceiveDate(getFormateDateToData("yyyyMMddHHmmss", new Date()));
            vrd.setVersion(1);
            vrd.setVmiSource(shop.getVmiSource());
            vrd.setWhCode(whCode);
            vrd.setStaId(sta);
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                }
                vmiBrandExt.generateReceivingTransferRsnAspect(vrd, extParam);
            }
            vmiRsnDao.save(vrd);
            // 获取出库对应数据生成转店反馈数据vmiTfx
            List<StvLineCommand> outList = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
            for (StvLineCommand out : outList) {
                Sku sku = skuDao.getByPrimaryKey(out.getSkuId());
                if (sku == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                String upc = sku.getExtensionCode2();
                if (StringUtil.isEmpty(upc)) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                InventoryStatus i = inventoryStatusDao.getByPrimaryKey(out.getIntInvstatus());
                if (i == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                VmiTfxLineDefault vtd = new VmiTfxLineDefault();
                vtd.setQty(out.getQuantity());
                vtd.setUpc(upc);
                if (i.getName().equals(invName) || i.getName().equals(invNames)) {
                    vtd.setInvStatus("1");
                } else {
                    vtd.setInvStatus("0");
                }
                vtd.setVersion(1);
                vtd.setTfxId(tfx);
                if (null != vmiBrandExt) {
                    ExtParam extParam = new ExtParam();
                    if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                    }
                    vmiBrandExt.generateReceivingTransferTfxLineAspect(vtd, extParam);
                }
                vmiTfxLineDao.save(vtd);
            }
            // 获取入库对应数据生成收货反馈数据vmiRsn
            List<StvLineCommand> inList = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
            for (StvLineCommand in : inList) {
                Sku sku = skuDao.getByPrimaryKey(in.getSkuId());
                if (sku == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                String upc = sku.getExtensionCode2();
                if (StringUtil.isEmpty(upc)) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                InventoryStatus i = inventoryStatusDao.getByPrimaryKey(in.getIntInvstatus());
                if (i == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                VmiRsnLineDefault vrl = new VmiRsnLineDefault();
                vrl.setUpc(upc);
                vrl.setQty(in.getQuantity());
                vrl.setVersion(1);
                vrl.setRsnId(vrd);
                if (null != vmiBrandExt) {
                    ExtParam extParam = new ExtParam();
                    if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                    }
                    vmiBrandExt.generateReceivingTransferRsnLineAspect(vrl, extParam);
                }
                vmiRsnLineDao.save(vrl);
            }
        }
    }

    /**
     * VMI转仓（调拨）反馈数据
     * 
     * @param sta
     */
    @Override
    public void generateReceivingFlitting(StockTransApplication sta) {}

    @Override
    public void generateRtnWh(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        sta = stockTransApplicationDao.getByPrimaryKey(sta.getId());
        if (sta.getType().equals(StockTransApplicationType.VMI_RETURN)) {
            // 如果是退大仓
            BiChannelCommand sh = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));

            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            VmiRtwDefault rtw = new VmiRtwDefault();// 退大仓反馈表
            rtw.setCreateTime(new Date());
            rtw.setFinishTime(new Date());
            rtw.setStoreCode(sh.getVmiCode());
            rtw.setOrderCode(sta.getRefSlipCode());
            rtw.setVmiSource(sh.getVmiSource());
            rtw.setReturnDate(getFormateDateToData("yyyyMMddHHmmss", sta.getFinishTime()));
            rtw.setWhCode(sta.getMainWarehouse().getCode());
            rtw.setStatus(VmiGeneralStatus.NEW);
            rtw.setVersion(1);
            rtw.setStaId(sta);
            VmiInterfaceExt vmiBrandExt = null;
            if (null != sh.getIsVmiExt() && true == sh.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(sh.getVmiCode());// 品牌逻辑定制
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(sh.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                    extParam.setShopCmd(sh);
                    extParam.setSta(sta);
                }
                vmiBrandExt.generateRtnWhRtwAspect(rtw, extParam);
            }
            vmiRtwDao.save(rtw);
            // 生成退仓反馈信息
            List<Carton> cList = cartonDao.findCartonByStaId(sta.getId());// 通过STA查询箱号数据
            if (cList.size() > 0) {
                // 如果有箱号数据反馈箱号数据
                for (Carton c : cList) {
                    List<CartonLine> clList = cartonDao.findCartonLineByCarId(c.getId());// 查询箱号下的明细
                    for (CartonLine cl : clList) {
                        Sku sku = skuDao.getByPrimaryKey(cl.getSku().getId());
                        if (sku == null) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        String upc = sku.getExtensionCode2();
                        if (StringUtil.isEmpty(upc)) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        // 通过stvid 和 skuid查询 对应stvline中商品的状态
                        Long invS = stvDao.getInvStatus(stv.getId(), sku.getId(), new SingleColumnRowMapper<Long>(Long.class));
                        if (invS == null) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        InventoryStatus i = inventoryStatusDao.getByPrimaryKey(invS);
                        if (i == null) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        VmiRtwLineDefault rtwL = new VmiRtwLineDefault();
                        rtwL.setUpc(upc);
                        rtwL.setQty(cl.getQty());
                        rtwL.setCartonNo(c.getCode());// 箱号
                        if (i.getName().equals(invName) || i.getName().equals(invNames)) {
                            rtwL.setInvStatus("1");
                        } else {
                            rtwL.setInvStatus("0");
                        }
                        rtwL.setRtwId(rtw);
                        rtwL.setVersion(1);
                        if (null != vmiBrandExt) {
                            ExtParam extParam = new ExtParam();
                            if (Constants.PUMA_VMI_CODE.equals(sh.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                                SkuCommand skuCmd = new SkuCommand();
                                skuCmd.setExtensionCode2(sku.getExtensionCode2());
                                extParam.setSkuCmd(skuCmd);
                                extParam.setShopCmd(sh);
                                extParam.setSta(sta);
                            }
                            vmiBrandExt.generateRtnWhRtwLineAspect(rtwL, extParam);
                        }
                        vmiRtwLineDao.save(rtwL);
                    }
                }
            } else {
                // 没有箱号数据反馈t_wh_st_log数据
                List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                for (StockTransTxLogCommand log : logList) {
                    Sku sku = skuDao.getByPrimaryKey(Long.parseLong(log.getSkuCode()));
                    if (sku == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    String upc = sku.getExtensionCode2();
                    if (StringUtil.isEmpty(upc)) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    InventoryStatus i = inventoryStatusDao.getByPrimaryKey(Long.parseLong(log.getInvStatus()));
                    if (i == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    VmiRtwLineDefault rtwL = new VmiRtwLineDefault();
                    rtwL.setUpc(upc);
                    rtwL.setQty(log.getInQty());
                    if (i.getName().equals(invName) || i.getName().equals(invNames)) {
                        rtwL.setInvStatus("1");
                    } else {
                        rtwL.setInvStatus("0");
                    }
                    rtwL.setRtwId(rtw);
                    rtwL.setVersion(1);
                    if (null != vmiBrandExt) {
                        ExtParam extParam = new ExtParam();
                        if (Constants.PUMA_VMI_CODE.equals(sh.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                            SkuCommand skuCmd = new SkuCommand();
                            skuCmd.setExtensionCode2(sku.getExtensionCode2());
                            extParam.setSkuCmd(skuCmd);
                            extParam.setShopCmd(sh);
                            extParam.setSta(sta);
                        }
                        vmiBrandExt.generateRtnWhRtwLineAspect(rtwL, extParam);
                    }
                    vmiRtwLineDao.save(rtwL);
                }
            }
        }
        if (sta.getType().equals(StockTransApplicationType.VMI_TRANSFER_RETURN)) {
            // 如果是转店退仓
            BiChannelCommand sh = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            List<Carton> cList = cartonDao.findCartonByStaId(sta.getId());// 通过STA查询箱号数据
            VmiTfxDefault tfx = new VmiTfxDefault();// 转店退仓反馈表
            tfx.setCreateTime(new Date());
            tfx.setFinishTime(new Date());
            tfx.setStoreCode(sh.getVmiCode());
            tfx.setFromLocation(sh.getVmiCode());
            tfx.setToLoaction(sta.getToLocation());
            tfx.setOrderCode(sta.getRefSlipCode());
            tfx.setVmiSource(sh.getVmiSource());
            tfx.setReturnDate(getFormateDateToData("yyyyMMddHHmmss", sta.getFinishTime()));
            tfx.setWhCode(sta.getMainWarehouse().getCode());
            tfx.setStatus(VmiGeneralStatus.NEW);
            tfx.setVersion(1);
            tfx.setStaId(sta);
            VmiInterfaceExt vmiBrandExt = null;
            if (null != sh.getIsVmiExt() && true == sh.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(sh.getVmiCode());// 品牌逻辑定制
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(sh.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                }
                vmiBrandExt.generateRtnWhTfxAspect(tfx, extParam);
            }
            vmiTfxDao.save(tfx);
            if (cList.size() > 0) {
                // 如果有箱号数据反馈箱号数据
                for (Carton c : cList) {
                    List<CartonLine> clList = cartonDao.findCartonLineByCarId(c.getId());// 查询箱号下的明细
                    for (CartonLine cl : clList) {
                        Sku sku = skuDao.getByPrimaryKey(cl.getSku().getId());
                        if (sku == null) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        String upc = sku.getExtensionCode2();
                        if (StringUtil.isEmpty(upc)) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        // 通过stvid 和 skuid查询 对应stvline中商品的状态
                        Long invS = stvDao.getInvStatus(stv.getId(), sku.getId(), new SingleColumnRowMapper<Long>(Long.class));
                        if (invS == null) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        InventoryStatus i = inventoryStatusDao.getByPrimaryKey(invS);
                        if (i == null) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        VmiTfxLineDefault txfL = new VmiTfxLineDefault();
                        txfL.setUpc(upc);
                        txfL.setQty(cl.getQty());
                        txfL.setCartonNo(c.getCode());// 箱号
                        if (i.getName().equals(invName) || i.getName().equals(invNames)) {
                            txfL.setInvStatus("1");
                        } else {
                            txfL.setInvStatus("0");
                        }
                        txfL.setTfxId(tfx);
                        txfL.setVersion(1);
                        if (null != vmiBrandExt) {
                            ExtParam extParam = new ExtParam();
                            if (Constants.PUMA_VMI_CODE.equals(sh.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                            }
                            vmiBrandExt.generateRtnWhTfxLineAspect(txfL, extParam);
                        }
                        vmiTfxLineDao.save(txfL);
                    }
                }
            } else {
                // 没有箱号数据反馈t_wh_st_log数据
                List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                for (StockTransTxLogCommand log : logList) {
                    Sku sku = skuDao.getByPrimaryKey(Long.parseLong(log.getSkuCode()));
                    if (sku == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    String upc = sku.getExtensionCode2();
                    if (StringUtil.isEmpty(upc)) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    InventoryStatus i = inventoryStatusDao.getByPrimaryKey(Long.parseLong(log.getInvStatus()));
                    if (i == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    VmiTfxLineDefault txfL = new VmiTfxLineDefault();
                    txfL.setUpc(upc);
                    txfL.setQty(log.getInQty());
                    if (i.getName().equals(invName) || i.getName().equals(invNames)) {
                        txfL.setInvStatus("1");
                    } else {
                        txfL.setInvStatus("0");
                    }
                    txfL.setTfxId(tfx);
                    txfL.setVersion(1);
                    if (null != vmiBrandExt) {
                        ExtParam extParam = new ExtParam();
                        if (Constants.PUMA_VMI_CODE.equals(sh.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数

                        }
                        vmiBrandExt.generateRtnWhTfxLineAspect(txfL, extParam);
                    }
                    vmiTfxLineDao.save(txfL);
                }
            }
        }
    }

    /**
     * 根据指令创商品
     * 
     * @param po
     */
    @Override
    public void generateSkuByOrder(String orderName, String vmiCode) {}

    /**
     * 生成退仓外部单据号 默认不生成，特殊需要品牌定制
     * 
     * @return
     */
    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        BiChannelCommand shop = biCannelDao.findVmiDefaultTbiChannel(vmiCode, null, null, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        String slipCode = null;
        if (!StringUtil.isEmpty(shop.getVmiSource())) {
            // 有配置vmiSource才有规则
            VmiCfgOrderCodeCommand vco = null;
            if (type == StockTransApplicationType.VMI_OWNER_TRANSFER || type == StockTransApplicationType.SAME_COMPANY_TRANSFER) {
                // 如果type是 88 || 90 = 转店
                // 查询对应slipCode规则
                vco = vmiCfgOrderCodeDao.findCfgOrderCodeByVmiSourceType(shop.getVmiSource(), VmiCfgOrderCodeType.TFX.getValue(), new BeanPropertyRowMapper<VmiCfgOrderCodeCommand>(VmiCfgOrderCodeCommand.class));
            }
            if (type == StockTransApplicationType.VMI_RETURN || type == StockTransApplicationType.VMI_TRANSFER_RETURN) {
                // 如果type是101 || 102 = 退仓
                // 查询对应slipCode规则
                vco = vmiCfgOrderCodeDao.findCfgOrderCodeByVmiSourceType(shop.getVmiSource(), VmiCfgOrderCodeType.RTW.getValue(), new BeanPropertyRowMapper<VmiCfgOrderCodeCommand>(VmiCfgOrderCodeCommand.class));
            }
            if (vco != null) {
                // 有规则 根据规则生成slipCode
                slipCode = sequenceManager.getCode(BiChannel.class.toString(), vco.getSeqCategory() == null ? "" : vco.getSeqCategory(), vco.getFillChar().toCharArray()[0], vco.getMinLength(), vco.getPxCode());
            }
            if (vco == null) {
                // 没有规则 返回WMS固定规则
                slipCode = String.valueOf(staDao.findVMIRDSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
            }
        } else {
            // 没有配置vmiSource WMS固定规则
            slipCode = String.valueOf(staDao.findVMIRDSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
        }
        return slipCode;
    }

    @Override
    public void generateInvStatusChange(StockTransApplication sta) {}

    @Override
    public void validateReturnManager(StockTransApplication sta) {}

    @Override
    public void generateInvStatusChangeByInboundSta(StockTransApplication sta) {}

    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return null;
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {

    }

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {

    }

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {

    }

    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        return null;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return null;
    }



    @Override
    public List<String> generateInboundStaGetAllOrderCodeDefault(String vmiCode, String vmisource, String asnType) {
        List<String> s = null;
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单创建
            s = vmiAsnDao.findVmiAsnByType1(vmiCode, vmisource, new SingleColumnRowMapper<String>(String.class));
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱
            s = vmiAsnDao.findVmiAsnByType2(vmiCode, vmisource, new SingleColumnRowMapper<String>(String.class));
        }
        return s;
    }

    @Override
    public Map<String, Long> generateInboundStaGetDetialDefault(String slipCode, String vmisource, String asnType) {
        // 分解slipCode 分辨按单按箱
        String[] code = slipCode.split(",");
        Map<String, Long> detials = new HashMap<String, Long>();
        List<VmiAsnLineCommand> vList = null;
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单创建
            vList = vmiAsnLineDao.findVmiAsnLineList(code[1], null, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            for (VmiAsnLineCommand v : vList) {
                Long val = detials.get(v.getUpc());
                if (val == null) {
                    detials.put(v.getUpc(), v.getQty());
                } else {
                    detials.put(v.getUpc(), val + v.getQty());
                }
            }
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱创建
            vList = vmiAsnLineDao.findVmiAsnLineList(code[1], code[0], new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            for (VmiAsnLineCommand v : vList) {
                Long val = detials.get(v.getUpc());
                if (val == null) {
                    detials.put(v.getUpc(), v.getQty());
                } else {
                    detials.put(v.getUpc(), val + v.getQty());
                }
            }
        }
        return detials;
    }

    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, String vmisource, StockTransApplication head) {
        // 分解slipCode 分辨按单按箱
        String[] code = slipCode.split(",");
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        List<VmiAsnCommand> list = vmiAsnDao.findVmiAsnList(code[1].trim(), vmisource, new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        if (list.size() > 0) {
            head.setFromLocation(list.get(0).getFromLocation());
            head.setToLocation(list.get(0).getToLoaction());
            if (!code[0].equals(code[1])) {
                // 按箱
                head.setSlipCode1(code[1]);
            }
        }
    }

    @Override
    public void generateInboundStaCallBackDefault(String slipCode, String orderCode, Long staId, BiChannelCommand shop) {
        /**
         * 判断按单按箱 如果是按单直接更改vmiasn和vmiasnline状态为10 并把sta关联vmiasn 如果是按箱只修改vmiasnLine状态为10
         * 并把sta关联vmiasnLine
         */
        String asnType = shop.getAsnTypeString();
        List<VmiAsnLineCommand> vList = null;
        StockTransApplication sta = stockTransApplicationDao.getByPrimaryKey(staId);
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单
            vList = vmiAsnLineDao.findVmiAsnLineList(orderCode, null, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            VmiAsnDefault v = vmiAsnDao.getByPrimaryKey(vList.get(0).getaId());
            v.setStaId(sta);
            v.setFinishTime(new Date());
            v.setStatus(VmiGeneralStatus.FINISHED);
            vmiAsnDao.save(v);
            for (VmiAsnLineCommand vl : vList) {
                VmiAsnLineDefault avd = vmiAsnLineDao.getByPrimaryKey(vl.getId());
                avd.setStatus(VmiGeneralStatus.FINISHED);
                vmiAsnLineDao.save(avd);
            }
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱
            vList = vmiAsnLineDao.findVmiAsnLineList(orderCode, slipCode, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            for (VmiAsnLineCommand v : vList) {
                VmiAsnLineDefault avd = vmiAsnLineDao.getByPrimaryKey(v.getId());
                avd.setStaId(sta);
                avd.setStatus(VmiGeneralStatus.FINISHED);
                vmiAsnLineDao.save(avd);
                vmiAsnLineDao.flush();
            }
        }
    }

    /**
     * VMI入库单关闭反馈
     */
    @Override
    public void generateReceivingWhenClose(StockTransApplication sta) {

    }

    /**
     * 格式化时间
     */
    private String getFormateDateToData(String s, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(s);
        String date = sdf.format(d);
        return date;
    }

    private boolean checkVmiOpTypeReturn(String opType, VmiOpType type) {
        boolean b = true;
        String[] ot = opType.split(",");
        for (String s : ot) {
            String[] ots = s.split("-");
            if (VmiOpType.valueOf(Integer.parseInt(ots[0])) == type) {
                // 如果权限有对应操作权限 判断是否支持生成VMI反馈信息
                if (ots[1].equals("1")) {
                    // 如果支持生成VMI反馈信息
                    b = false;
                    return b;
                }
            }
        }
        return b;
    }
}
