package com.jumbo.wms.manager.vmi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.ext.VmiExtFactory;
import com.jumbo.wms.manager.vmi.ext.VmiInterfaceExt;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.RsnType;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiBaseBrand extends BaseManagerImpl implements VmiInterface {

    private static final long serialVersionUID = -5557698022626685044L;
    private List<String> vmiCode;

    @Autowired
    private VmiRsnDao vmiRsnDao;
    @Autowired
    private VmiRsnLineDao vmiRsnLineDao;
    @Autowired
    private BiChannelDao biCannelDao;
    @Autowired
    private StockTransTxLogDao stvLogDao;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private VmiExtFactory vmiExtFactory;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    public List<String> getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(List<String> vmiCode) {
        this.vmiCode = vmiCode;
    }

    /**
     * 日志记录日常信息，解决BusinessException无法获取信息
     * 
     * @param e
     */
    public void loggerErrorMsg(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            log.debug("throw BusinessException, ErrorCode : {}", be.getErrorCode());
        } else {
            log.error(e.getMessage());
        }
    }

    @Override
    public void generateInboundSta() {}

    @Override
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateReceivingWhenInbound");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateReceivingWhenShelv");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateVMIReceiveInfoByInvCk");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
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
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateReceivingTransfer");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    /**
     * VMI转仓（调拨）反馈数据
     * 
     * @param sta
     */
    @Override
    public void generateReceivingFlitting(StockTransApplication sta) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateReceivingFlitting");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public void generateRtnWh(StockTransApplication sta) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateRtnWh");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    /**
     * 根据指令创商品
     * 
     * @param po
     */
    @Override
    public void generateSkuByOrder(String orderName, String vmiCode) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateSkuByOrder");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    /**
     * 生成退仓外部单据号 默认不生成，特殊需要品牌定制
     * 
     * @return
     */
    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return "";
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
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateReceivingWhenFinished");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaCallBack");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaSetHead");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaGetDetial");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaGetAllOrderCode");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public boolean inBoundInsertIntoDB(List<String> results) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : inBoundInsertIntoDB");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCodeDefault(String vmiCode, String vmisource, String asnType) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaGetAllOrderCodeDefault");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public Map<String, Long> generateInboundStaGetDetialDefault(String slipCode, String vmisource, String asnType) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaGetDetialDefault");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, String vmisource, StockTransApplication head) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaSetHeadDefault");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    @Override
    public void generateInboundStaCallBackDefault(String slipCode, String orderCode, Long staId, BiChannelCommand b) {
        log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateInboundStaCallBackDefault");
        throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }
    
    /** 
     *VMI入库单关闭反馈
     */
    @Override
    public void generateReceivingWhenClose(StockTransApplication sta) {
        BiChannelCommand bi = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        if (bi.getIsPda() != null && bi.getIsPda()) {// ispda 为true
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
            if (bi.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
                VmiAsnLineDefault vmiAsnLineDefault = null;
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
                // 按箱收货////////////////////////////////////////////////////////////////////////////////////
                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
                    // 按实际收货反馈=如果收货数量为100,这次收5件,那反馈5件 t_wh_st_log.quantity
                    if (StockTransApplicationStatus.FINISHED == sta.getStatus()) {// 完成才会一次反馈
                        vmiRsnDao.save(vrd);
                    } else {
                        return;
                    }
                    // List<StockTransTxLogCommand> logList =
                    // stvLogDao.findStaLogByStvId(stv.getId(), new
                    // BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId2(sta.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
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
                        vmiAsnLineDefault = vmiAsnLineDao.findExtMemoByStaId(sta.getId(), sku.getExtensionCode2(), new BeanPropertyRowMapper<VmiAsnLineDefault>(VmiAsnLineDefault.class));
                        if (null != vmiAsnLineDefault) {
                            v.setExtMemo(vmiAsnLineDefault.getExtMemo());
                        }
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
                // vmiRsnDao.save(vrd);

                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
                    VmiAsnLineDefault vmiAsnLineDefault = null;
                    // 按实际收货反馈=如果收货数量为100,这次收5件,那反馈5件 t_wh_st_log.quantity
                    if (StockTransApplicationStatus.FINISHED == sta.getStatus()) {// 完成才会一次反馈
                        vmiRsnDao.save(vrd);
                    } else {
                        return;
                    }
                    // List<StockTransTxLogCommand> logList =
                    // stvLogDao.findStaLogByStvId(stv.getId(), new
                    // BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));

                    List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId2(sta.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));

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
                        vmiAsnLineDefault = vmiAsnLineDao.findExtMemoByStaId(sta.getId(), sku.getExtensionCode2(), new BeanPropertyRowMapper<VmiAsnLineDefault>(VmiAsnLineDefault.class));
                        if (null != vmiAsnLineDefault) {
                            v.setExtMemo(vmiAsnLineDefault.getExtMemo());
                        }
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
        }

        // log.error("VmiBaseBrand NO IMPLEMENT FUNCTION : generateReceivingWhenClose");
        // throw new BusinessException(ErrorCode.BRAND_LIMIT_THE_FUNCTION);
    }

    /**
     * 格式化时间
     */
    private String getFormateDateToData(String s, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(s);
        String date = sdf.format(d);
        return date;
    }
}
