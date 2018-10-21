package com.baozun.test.wms.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.vmi.espData.ESPInvoicePercentageDao;
import com.jumbo.dao.vmi.espData.ESPPoTypeDao;
import com.jumbo.dao.vmi.nikeDate.NikeReturnReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.EspritStoreDao;
import com.jumbo.dao.warehouse.ImportPrintDataDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceSnLineDao;
import com.jumbo.dao.warehouse.InventoryCheckLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgSkuUpdateDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.PickingReplenishCfgDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.rmiservice.RmiService;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.NikeTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.VmiDefault;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiOpType;
import com.jumbo.wms.model.vmi.nikeData.NikeReturnReceive;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;


/**
 * 测试样例类，测试manager定义
 * @author jingkai
 *
 */
@Service("testOutboundManager")
@Transactional
public class TestOutboundManager {
    
    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private ESPPoTypeDao potypeDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WareHouseManagerProxy whManagerProxy;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private ESPInvoicePercentageDao ipDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private InventoryCheckDifTotalLineDao vmiinvCheckLineDao;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private EspritStoreDao espritStoreDao;
    @Autowired
    private InventoryCheckLineDao inventoryCheckLineDao;
    @Autowired
    private PickingReplenishCfgDao pickingReplenishCfgDao;
    @Autowired
    private InventoryCheckDifferenceSnLineDao inventoryCheckDifferenceSnLineDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private ChannelWhRefRefDao warehouseShopRefDao;
    @Autowired
    private RmiService rmiService;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;
    @Autowired
    private ImportPrintDataDao importPrintDataDao;
    @Autowired
    private InventoryStatusDao isDao;
    @Autowired
    private MsgSkuUpdateDao msgSkuUpdateDao;
    @Autowired
    private HubWmsService hubWmsService;
    
    
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private NikeTask nikeTask;
    @Autowired
    private NikeReturnReceiveDao nikeReturnReceiveDao;



    /**
     * 创建测试作业单
     * 
     * @author jingkai
     * @param whouId 仓库
     * @param owner 店铺
     * @param skus 商品及数量
     */
    public StockTransApplication crateSalesOutboundSta(Long whouId, String owner, Map<String, Long> skus) {
        // 创建STA
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(whouId);
        String code = "ST" + System.currentTimeMillis();
        sta.setRefSlipCode(code);
        sta.setSlipCode1(code);
        sta.setSlipCode2(code);
        sta.setSlipCode3(code);
        sta.setOwner(owner);
        sta.setStorecode(owner);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.OUTBOUND_SALES);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setChannelList(owner);
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(ou1);
        sta.setTotalActual(new BigDecimal(1000));
        sta.setOrderCreateTime(new Date());
        sta.setIsSpecialPackaging(false);
        sta.setOrderTotalActual(new BigDecimal(1000));
        sta.setOrderTransferFree(new BigDecimal(0));
        staDao.save(sta);

        staDao.flush();
        // 配送信息
        StaDeliveryInfo di = new StaDeliveryInfo();
        di.setProvince("上海");
        di.setCity("上海市");
        di.setDistrict("静安区");
        di.setAddress("上海 上海市 静安区 XXXXXXXXXXX");
        di.setTelephone("66666666");
        di.setMobile("1300000000");
        di.setReceiver("张三");
        di.setZipcode("200000");
        di.setIsCod(false);
        di.setLpCode("SF");
        di.setTransferFee(new BigDecimal(0));
        di.setTransType(TransType.ORDINARY);
        di.setTransTimeType(TransTimeType.ORDINARY);
        di.setId(sta.getId());
        di.setLastModifyTime(new Date());
        sta.setDeliveryType(TransDeliveryType.ORDINARY);
        staDeliveryInfoDao.save(di);
        sta.setStaDeliveryInfo(di);
        staDao.flush();

        // 创建单据明细
        for (Entry<String, Long> skuData : skus.entrySet()) {
            Sku sku = skuDao.getByCode(skuData.getKey());
            StaLine sl = new StaLine();
            sl.setSta(sta);
            sl.setSku(sku);
            sl.setOwner(owner);
            sl.setQuantity(skuData.getValue());
            sl.setVersion(1);
            sl.setSkuName(sku.getName());
            sl.setTotalActual(new BigDecimal(100));
            sl.setUnitPrice(new BigDecimal(100));
            staLineDao.save(sl);
        }
        staDao.flush();
        return sta;
    }


    /**
     * 创建转店单
     * @param ownerCode
     * @param addiownerCode
     * @param ouid
     * @param cmpOuid
     * @param userid
     * @param invstatusId
     * @return
     * @throws Exception
     */
    public StockTransApplication createStaForVMITransfer(String ownerCode, String addiownerCode, Long ouid, Long cmpOuid, Long userid, Long invstatusId) throws Exception {
        StockTransApplication sta = new StockTransApplication();
        String code = "ST" + System.currentTimeMillis();
        sta.setCode(code);
        BiChannel originShop = companyShopDao.getByCode(ownerCode);
        BiChannel targetShop = companyShopDao.getByCode(addiownerCode);
        if (originShop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        if (targetShop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        wareHouseManagerExe.validateBiChannelSupport(null, originShop.getCode());
        wareHouseManagerExe.validateBiChannelSupport(null, targetShop.getCode());
        if (!StringUtil.isEmpty(originShop.getVmiCode())) {
            // 验证品牌操作权限
            if (!StringUtil.isEmpty(originShop.getOpType())) {
                // 如果有配置需要验证
                boolean b = VmiDefault.checkVmiOpType(originShop.getOpType(), VmiOpType.TFXRSN);
                if (b) {
                    // 无操作权限抛出异常
                    throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {"转出：" + originShop.getName(), "VMI转店"});
                }
            }
        }
        if (!StringUtil.isEmpty(targetShop.getVmiCode())) {
            // 验证品牌操作权限
            if (!StringUtil.isEmpty(targetShop.getOpType())) {
                // 如果有配置需要验证
                boolean b = VmiDefault.checkVmiOpType(targetShop.getOpType(), VmiOpType.TFXRSN);
                if (b) {
                    // 无操作权限抛出异常
                    throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {"转入：" + targetShop.getName(), "VMI转店"});
                }
            }
        }
        // 判断 店铺是否都存在品牌对接编码 或者 都不存在品牌对接编码
        if ((originShop.getVmiCode() == null ? true : originShop.getVmiCode().isEmpty()) != (targetShop.getVmiCode() == null ? true : targetShop.getVmiCode().isEmpty())) {
            throw new BusinessException(ErrorCode.SHOP_TYPE_IS_ERROR);
        }
        /*
         * Boolean msg = rmi4Wms.checkPaymentDistribution(originShop.getCode()); if (null != msg &&
         * msg) { throw new BusinessException(ErrorCode.SHOP_TYPE_IS_PAYMENT_ERROR, new Object[]
         * {originShop.getName()}); } Boolean msg1 =
         * rmi4Wms.checkPaymentDistribution(targetShop.getCode()); if (null != msg1 && msg1) { throw
         * new BusinessException(ErrorCode.SHOP_TYPE_IS_PAYMENT_ERROR, new Object[]
         * {targetShop.getName()}); }
         */
        List<BiChannel> shoplist = companyShopDao.getAllRefShopByWhOuId(ouid);
        boolean isRefOriginShop = false;
        boolean isRefTargetShop = false;
        for (BiChannel tmpShop : shoplist) {
            if (originShop.getId().equals(tmpShop.getId())) {
                isRefOriginShop = true;
            }
            if (targetShop.getId().equals(tmpShop.getId())) {
                isRefTargetShop = true;
            }
        }
        boolean isRelative = false;
        if (isRefOriginShop && isRefTargetShop) {
            isRelative = true;
        }
        if (!isRelative) {
            // 店铺未关联同一家仓库
            throw new BusinessException(ErrorCode.TWO_COMAPNY_SHOP_IS_NOT_RELATIVE, new Object[] {originShop.getName(), targetShop.getName()});
        }
        try {
            List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();// 封装
            StaLineCommand c1 = new StaLineCommand();
            c1.setQuantity(2L);
            c1.setSku(skuDao.getByCode("SX4801_160_M"));
            stalinecmds.add(c1);
            // save sta
            InventoryStatus inventorystatus = inventoryStatusDao.getByPrimaryKey(invstatusId);
            User user = userDao.getByPrimaryKey(userid);
            sta.setMainStatus(inventorystatus);
            sta.setType(StockTransApplicationType.VMI_OWNER_TRANSFER);
            sta.setMainWarehouse(operationUnitDao.getByPrimaryKey(ouid));
            sta.setCreateTime(new Date());
            sta.setOwner(originShop.getCode());
            sta.setAddiOwner(targetShop.getCode());
            sta.setToLocation(targetShop.getVmiCode());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setSystemKey(Constants.WMS);// 转店作业单来源于WMS系统自己创建
            sta.setCreator(user);
            sta.setOutboundOperator(user);
            sta.setIsNeedOccupied(true);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());

            // VMI转店反馈
            if (originShop != null && originShop.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(originShop.getVmiCode());
                if (vf != null) {
                    sta.setRefSlipCode(vf.generateRtnStaSlipCode(originShop.getVmiCode(), sta.getType()));
                }
            }
            sta.setIsNotPacsomsOrder(true);
            staDao.save(sta);
            Long skuQty = 0l;
            // save staline
            for (StaLineCommand cmd : stalinecmds) {
                if (cmd.getQuantity() != 0) {
                    StaLine staLine = new StaLine();
                    staLine.setInvStatus(inventorystatus);
                    staLine.setOwner(originShop.getCode());
                    staLine.setQuantity(cmd.getQuantity());
                    staLine.setSku(cmd.getSku());
                    staLine.setSta(sta);
                    skuQty += staLine.getQuantity();
                    staLineDao.save(staLine);
                }
            }
            sta.setSkuQty(skuQty);
            staDao.flush();
            staDao.save(sta);
            staDao.updateSkuQtyById(sta.getId());
            wareHouseManager.isInventoryNumber(sta.getId());
            /***** mongoDB库存变更添加逻辑 ******************************/
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
            // 占用库存 create stv, stvline
            wareHouseManager.createTransferByStaId(sta.getId(), userid);
            if (sta.getSystemKey() != null && Constants.WMS.equals(sta.getSystemKey())) {
                wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.VMI_OWNER_TRANSFER);
            }

            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sta;
    }

    /**
     * 执行转店
     * 
     * @param staId
     * @param userId
     * @param ouId
     * @throws Exception
     */
    
    public void executeVmiTransferOutBound(Long staId, Long userId, Long ouId) throws Exception
 {
        wareHouseManager.executeVmiTransferOutBound(staId, userId, ouId);
    }
    
    
    /**
     * 上传NIKE转店定制
     * 
     * @param staId
     * @param userId
     * @param ouId
     * @throws Exception
     */
    
    public void updateNikeFile() throws Exception
 {
        nikeTask.uploadNikeData();
    }

    /**
     * 验证nike转店定制是否OK
     */
    public boolean checkResult(Long staId) {
        // StockTransApplication sta = staDao.getByPrimaryKey(staId);
        List<NikeReturnReceive> list = nikeReturnReceiveDao.unitTestGetNikeReturnReceiveByStaId(staId, new BeanPropertyRowMapperExt<NikeReturnReceive>(NikeReturnReceive.class));
        if (list.size() > 0) {
            for (NikeReturnReceive nikeReturnReceive : list) {
                Integer type = nikeReturnReceive.getType();
                String fromLocation = nikeReturnReceive.getFromLocation();
                if (3 != type) {
                    return false;
                }
                if (!"1028".equals(fromLocation)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    



    /**
     * 清理测试单据
     * 
     * @author jingkai
     * @param staCode
     */
    public void removeTestData(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        //删除明细行
        staLineDao.deleteStaLineByStaId(sta.getId());
        staDeliveryInfoDao.deleteByPrimaryKey(sta.getId());
        staDao.deleteByPrimaryKey(sta.getId());
    }
}
