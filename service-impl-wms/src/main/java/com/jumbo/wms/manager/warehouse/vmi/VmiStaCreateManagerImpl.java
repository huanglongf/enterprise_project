package com.jumbo.wms.manager.warehouse.vmi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.TransferOwnerSourceDao;
import com.jumbo.dao.vmi.defaultData.TransferOwnerTargetDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.ext.ExtReturn;
import com.jumbo.wms.manager.vmi.ext.VmiExtFactory;
import com.jumbo.wms.manager.vmi.ext.VmiInterfaceExt;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.TransferOwnerSource;
import com.jumbo.wms.model.vmi.Default.TransferOwnerTargetCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("vmiStaCreateManager")
public class VmiStaCreateManagerImpl extends BaseManagerImpl implements VmiStaCreateManager {

    private static final long serialVersionUID = -7237486712891567003L;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private BaseinfoManager baseinfoManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManager wareHouseManager;
    private EventObserver eventObserver;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private VmiExtFactory vmiExtFactory;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private TransferOwnerTargetDao transferOwnerTargetDao;
    @Autowired
    private TransferOwnerSourceDao transferOwnerSourceDao;
    @Autowired
    private SkuDao skuDao;

    @Autowired
    private InventoryStatusDao inventoryStatusDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named eventObserver");
        }
    }

    /**
     * VMI 品牌根据slipcode 创建入库作业单
     * 
     * @param vmiCode
     * @param slipCode
     */
    public void generateVmiInboundStaBySlipCode(String vmiCode, String slipCode) {
        // 判断单据是否已经存在
        if (checkInboundStaIsExists(slipCode)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        BiChannel shop = companyShopDao.getByVmiCode(vmiCode);
        if (shop == null) {
            log.error("shop not found by vmiCode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        VmiInterface vmiBrand = vmiFactory.getBrandVmi(shop.getVmiCode());
        StockTransApplication head = new StockTransApplication();
        Map<String, Long> detials = null;// 明细行数据 key:skuextcode2:value:qty
        if (vmiBrand != null) {
            vmiBrand.generateInboundStaSetHead(slipCode, head);
            detials = vmiBrand.generateInboundStaGetDetial(slipCode);
        }
        generateVmiInboundSta(shop, vmiBrand, vmiCode, slipCode, head, detials);
    }

    /**
     * VMI 品牌根据slipcode 创建入库作业单(通用)
     * 
     * @param vmiCode
     * @param slipCode
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateVmiInboundStaBySlipCodeDefault(String vmiCode, String vmiSource, String slipCode, String asnType) {
        // 判断单据是否已经存在
        if (checkInboundStaIsExists(slipCode)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        BiChannelCommand shop = companyShopDao.findVmiDefaultTbiChannel(vmiCode, vmiSource, null, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        if (shop == null) {
            log.error("shop not found by vmiCode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        VmiInterface vmiBrand = vmiFactory.getBrandVmi(shop.getVmiCode());
        VmiInterfaceExt vmiBrandExt = null;
        if (null != shop.getIsVmiExt() && true == shop.getIsVmiExt()) {
            vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
        }
        if (null != vmiBrandExt) {
            ExtParam extParam = new ExtParam();
            extParam.setVmiCode(vmiCode);
            extParam.setVmiSource(vmiSource);
            ExtReturn extReturn = vmiBrandExt.findVmiDefaultTbiChannelExt(extParam);// 定制逻辑，获取创指令的默认创单渠道
            if (null != extReturn && null != extReturn.getShopCmd()) {
                shop = extReturn.getShopCmd();// 替换为默认渠道
            }
        }
        StockTransApplication head = new StockTransApplication();
        Map<String, Long> detials = null;// 明细行数据 key:skuextcode2:value:qty
        // 按单创建
        if (vmiBrand != null) {
            vmiBrand.generateInboundStaSetHeadDefault(slipCode, vmiSource, head);
            if (!StringUtil.isEmpty(shop.getDefaultCode())) {
                // 判断是否有品牌通用定制逻辑
                VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
                vv.generateInboundStaSetHeadDefault(slipCode, head);
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                    extParam.setVmiSource(vmiSource);
                    extParam.setOrderCode(StringUtil.isEmpty(slipCode) ? "" : (slipCode.split(",")[1].trim().toString()));
                }
                vmiBrandExt.generateInboundStaSetHeadAspect(head, extParam);
            }
            detials = vmiBrand.generateInboundStaGetDetialDefault(slipCode, vmiSource, asnType);
        }
        generateVmiInboundStaDefault(shop, vmiBrand, vmiCode, slipCode.split(",")[0].trim().toString(), slipCode.split(",")[1].trim().toString(), asnType, head, detials);
    }

    /**
     * VMI 品牌根据slipcode 创建入库作业单(通用)
     * 
     * @param vmiCode
     * @param slipCode
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateVmiInboundStaBySlipCodeDefault(String vmiCode, String vmiSource, String slipCode, BiChannelCommand shopCmd) {
        // 判断单据是否已经存在
        if (checkInboundStaIsExists(slipCode)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        BiChannelCommand shop = shopCmd;
        if (shop == null) {
            log.error("shop not found by vmiCode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        String asnType = shop.getAsnTypeString();
        VmiInterface vmiBrand = vmiFactory.getBrandVmi(shop.getVmiCode());
        VmiInterfaceExt vmiBrandExt = null;
        if (null != shop.getIsVmiExt() && true == shop.getIsVmiExt()) {
            vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
        }
        StockTransApplication head = new StockTransApplication();
        Map<String, Long> detials = null;// 明细行数据 key:skuextcode2:value:qty
        // 按单创建
        if (vmiBrand != null) {
            vmiBrand.generateInboundStaSetHeadDefault(slipCode, vmiSource, head);
            if (!StringUtil.isEmpty(shop.getDefaultCode())) {
                // 判断是否有品牌通用定制逻辑
                VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
                vv.generateInboundStaSetHeadDefault(slipCode, head);
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                    extParam.setVmiSource(vmiSource);
                    extParam.setOrderCode(StringUtil.isEmpty(slipCode) ? "" : (slipCode.split(",")[1].trim().toString()));
                }
                vmiBrandExt.generateInboundStaSetHeadAspect(head, extParam);
            }
            detials = vmiBrand.generateInboundStaGetDetialDefault(slipCode, vmiSource, asnType);
        }
        generateVmiInboundStaDefault(shop, vmiBrand, vmiCode, slipCode.split(",")[0].trim().toString(), slipCode.split(",")[1].trim().toString(), asnType, head, detials);
    }

    /**
     * 创建VMI入库作业单
     * 
     * @param vmiCode
     * @param slipCode
     * @param head sta头信息
     * @param detials 明细行 key:skuextcode2,value:qty
     */
    private void generateVmiInboundSta(BiChannel shop, VmiInterface vmiBrand, String vmiCode, String slipCode, StockTransApplication head, Map<String, Long> detials) {
        if (detials == null || detials.size() == 0) {
            log.error("generate vmi inbound sta detial is null for vmicode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        OperationUnit whou = operationUnitDao.getDefaultInboundWhByShopId(shop.getId());
        if (whou == null) {
            log.error("warehouse not found by vmicode [{}]", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        // 库存状态
        InventoryStatus sts = wareHouseManagerQuery.getSalesInvStatusByWhou(whou.getId());
        if (sts == null) {
            log.error("inventory status is null for vmicode [{}]", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        }
        // 创建STA
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        StockTransApplication sta = null;
        if (head == null) {
            sta = new StockTransApplication();
        } else {
            sta = head;
        }
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipCode(slipCode);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, whou.getId());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setOwner(shop.getCode());
        sta.setMainWarehouse(whou);
        sta.setMainStatus(sts);
        staDao.save(sta);
        // 创建 sta line
        Long totalSku = 0L;
        Map<String, Long> tmp = new HashMap<String, Long>();// 商品与明细行对应关系,key:skuextcode2,value:stalineId
        boolean isNoSkuErroe = false;
        for (Entry<String, Long> line : detials.entrySet()) {
            // 不同品牌获取SKU
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(line.getKey(), shop.getCustomer().getId(), shop.getId());
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(line.getKey(), shop.getVmiCode());
                isNoSkuErroe = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setSku(sku);
            staLine.setQuantity(line.getValue());
            staLine.setCompleteQuantity(0L);
            staLine.setInvStatus(sts);
            staLine.setOwner(shop.getCode());
            staLine.setSta(sta);
            staLineDao.save(staLine);
            totalSku += line.getValue();
            tmp.put(line.getKey(), staLine.getId());
        }
        if (isNoSkuErroe) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(totalSku);
        staDao.flush();
        // 创建成功回调品牌后续处理
        if (vmiBrand != null) {
            vmiBrand.generateInboundStaCallBack(slipCode, sta.getId(), tmp);
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 创建VMI入库作业单(通用)
     * 
     * @param vmiCode
     * @param slipCode
     * @param head sta头信息
     * @param detials 明细行 key:skuextcode2,value:qty
     */
    private void generateVmiInboundStaDefault(BiChannelCommand shop, VmiInterface vmiBrand, String vmiCode, String slipCode, String orderCode, String asnOrderType, StockTransApplication head, Map<String, Long> detials) {
        if (detials == null || detials.size() == 0) {
            log.error("generate vmi inbound sta detial is null for vmicode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        OperationUnit whou = operationUnitDao.getDefaultInboundWhByShopId(shop.getId());
        if (whou == null) {
            log.error("warehouse not found by vmicode [{}]", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        // 库存状态
        InventoryStatus sts = wareHouseManagerQuery.getSalesInvStatusByWhou(whou.getId());
        if (sts == null) {
            log.error("inventory status is null for vmicode [{}]", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        }
        // 验证此品牌是否已经有相同slipCode的单子了
        int count = staDao.findVmiAsnBySlipCode(slipCode, shop.getCode(), StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue(), new SingleColumnRowMapper<Integer>(Integer.class));
        if (count > 0) {
            log.error("sta slipCode is not null", new Object[] {slipCode});
            throw new BusinessException();
        }
        // 创建STA
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        StockTransApplication sta = null;
        if (head == null) {
            sta = new StockTransApplication();
        } else {
            sta = new StockTransApplication();
            sta.setFromLocation(head.getFromLocation());
            sta.setToLocation(head.getToLocation());
            sta.setSlipCode1(head.getSlipCode1());
            sta.setSlipCode2(head.getSlipCode2());
        }
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipCode(slipCode);
        sta.setPumaType(head.getPumaType());
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, whou.getId());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setOwner(shop.getCode());
        sta.setMainWarehouse(whou);
        sta.setMainStatus(sts);
        VmiInterfaceExt vmiBrandExt = null;
        List<VmiAsnLineCommand> vList = new ArrayList<VmiAsnLineCommand>();
        if (null != shop.getIsVmiExt() && true == shop.getIsVmiExt()) {
            vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
        }
        if (null != vmiBrandExt) {
            ExtParam extParam = new ExtParam();
            if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                extParam.setSta(head);
            }
            vmiBrandExt.generateVmiInboundStaAspect(sta, extParam);
            extParam = new ExtParam();
            if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                extParam.setSlipCode(slipCode);
                extParam.setOrderCode(orderCode);
                extParam.setAsnOrderType(asnOrderType);
            }
            if (Constants.SPEEDO_VMI_CODE.equals(shop.getVmiCode())) {// SPEEDO根据业务可传递不同的参数
                extParam.setSlipCode(slipCode);
                extParam.setOrderCode(orderCode);
                extParam.setAsnOrderType(asnOrderType);
            }
            ExtReturn extReturn = vmiBrandExt.generateInboundStaGetDetialExt(extParam);
            if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可获取不同的返回值
                if (null != extReturn) vList = extReturn.getValList();
            }
        }
        if (!StringUtil.isEmpty(shop.getDefaultCode())) {
            // 判断是否有品牌通用定制逻辑
            VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
            vv.generateInboundStaSetSlipCodeOwner(shop, sta);
        }
        staDao.save(sta);
        // 创建 sta line
        Long totalSku = 0L;
        Map<String, Long> tmp = new HashMap<String, Long>();// 商品与明细行对应关系,key:skuextcode2,value:stalineId
        boolean isNoSkuErroe = false;
        for (Entry<String, Long> line : detials.entrySet()) {
            // 不同品牌获取SKU
            Long cusId = null;
            // 老柯bug修复
            if (shop.getCustomer() == null) {
                cusId = Long.parseLong(shop.getCustomerId());
            } else {
                cusId = shop.getCustomer().getId();
            }
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(line.getKey(), cusId, shop.getId());
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(line.getKey(), shop.getVmiCode());
                isNoSkuErroe = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setSku(sku);
            staLine.setQuantity(line.getValue());
            staLine.setCompleteQuantity(0L);
            staLine.setInvStatus(sts);
            staLine.setOwner(shop.getCode());
            staLine.setSta(sta);
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                    extParam.setValList(vList);
                }
                // if (Constants.SPEEDO_VMI_CODE.equals(shop.getVmiCode())) {// SPEEDO品牌根据业务可传递不同的参数
                // extParam.setValList(vList);
                // }
                vmiBrandExt.generateVmiInboundStaLineAspect(staLine, extParam);
            }
            if (!StringUtil.isEmpty(shop.getDefaultCode())) {
                // 判断是否有品牌通用定制逻辑
                VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
                try {
                    vv.generateVmiInboundStaLine(sta, staLine, line.getKey());
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
            staLineDao.save(staLine);
            totalSku += line.getValue();
            tmp.put(line.getKey(), staLine.getId());
        }
        if (isNoSkuErroe) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(totalSku);
        staDao.flush();
        // 创建成功回调品牌后续处理
        if (vmiBrand != null) {
            vmiBrand.generateInboundStaCallBackDefault(slipCode, orderCode, sta.getId(), shop);
        }
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 判断单据是否已经存在
     * 
     * @param slipCode
     * @return 存在 true,不存在 false
     */
    private boolean checkInboundStaIsExists(String slipCode) {
        List<StockTransApplication> list = staDao.findBySlipCodeByType(slipCode, StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        for (StockTransApplication findSta : list) {
            if (!(StockTransApplicationStatus.CANCEL_UNDO.equals(findSta.getStatus()) || StockTransApplicationStatus.CANCELED.equals(findSta.getStatus()))) {
                // 不为取消状态则抛出异常
                log.debug("vmi generate inbound sta error:sta is exist[{}]", slipCode);
                return true;
            }
        }
        List<StockTransApplication> tList = staDao.findBySlipCodeByType(slipCode, StockTransApplicationType.VMI_OWNER_TRANSFER);
        for (StockTransApplication findSta : tList) {
            if (!(StockTransApplicationStatus.CANCEL_UNDO.equals(findSta.getStatus()) || StockTransApplicationStatus.CANCELED.equals(findSta.getStatus()))) {
                // 不为取消状态则抛出异常
                log.debug("vmi generate inbound sta error:sta is exist[{}]", slipCode);
                return true;
            }
        }
        return false;
    }

    /**
     * vmi入库后自动转店
     * 
     * @param sta
     */
    public void autoTransferByVmiInbound(StockTransApplication sta) {
        if (StockTransApplicationType.VMI_INBOUND_CONSIGNMENT != sta.getType()) {
            return;
        }
        // 获取需要转到的店铺
        List<String> TargetOwnerList = transferOwnerTargetDao.findTargetOwnerByStaId(sta.getId(), new SingleColumnRowMapper<String>(String.class));
        if (TargetOwnerList == null || TargetOwnerList.size() == 0) {
            return;
        }
        List<Long> invStatusids = transferOwnerTargetDao.findTransferInvStatusIdByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
        for (String tol : TargetOwnerList) {
            if (invStatusids == null) {
                generateTransferByVmiInbound(sta, tol, sta.getMainStatus());
            } else {
                for (Long invStatusid : invStatusids) {
                    InventoryStatus sts = inventoryStatusDao.getByPrimaryKey(invStatusid);
                    generateTransferByVmiInbound(sta, tol, sts);
                }

            }
        }

    }

    public void generateTransferByVmiInbound(StockTransApplication outSta, String targetOwner, InventoryStatus sts) {
        // InventoryStatus sts =
        // inventoryStatusDao.findXSInvStatusByCompany(outSta.getMainWarehouse().getParentUnit().getParentUnit().getId());
        // 生成作业单
        StockTransApplication sta = autoChangeOwnerCreateSta(sts, outSta.getOwner(), targetOwner, outSta.getMainWarehouse());
        List<TransferOwnerTargetCommand> totList = transferOwnerTargetDao.findTargetRatioByStaId(outSta.getId(), targetOwner, sts.getId(), new BeanPropertyRowMapper<TransferOwnerTargetCommand>(TransferOwnerTargetCommand.class));

        if (totList == null || totList.size() == 0) {
            return;
        }
        TransferOwnerSource tos = transferOwnerSourceDao.getByOwnerSourceAndTargetOwner(outSta.getOwner(), targetOwner, outSta.getMainWarehouse().getId());

        // 生成明细行
        Long skuQty = 0l;
        List<StaLine> slList = new ArrayList<StaLine>();
        // save staline
        for (TransferOwnerTargetCommand cmd : totList) {
            Float targetRatio = cmd.getTargetRatio().floatValue();
            Float q = cmd.getQty() * (targetRatio / 100);
            Float m = ((float) ((int) (q * 100)) % 100) / 100;
            Long qty = q.longValue();

            // 计算数量
            if (tos == null || outSta.getOwner().equals(tos.getPriorityOwner())) {
                if (qty == 0) {
                    continue;
                }
            } else {

                if (m == 0 && qty == 0) {
                    continue;
                }
                if (m > 0) {
                    qty = qty + 1;
                }
            }
            Sku sku = skuDao.getByPrimaryKey(cmd.getSkuId());
            if (sku == null) {
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setInvStatus(sts);
            staLine.setOwner(targetOwner);
            staLine.setQuantity(qty);
            staLine.setSku(sku);
            staLine.setSta(sta);
            skuQty += staLine.getQuantity();
            slList.add(staLine);
        }
        if (slList.size() == 0) {
            return;
        }

        sta.setSkuQty(skuQty);
        staDao.save(sta);
        staDao.flush();

        for (StaLine sl : slList) {
            staLineDao.save(sl);
        }
        staLineDao.flush();

        // staDao.updateSkuQtyById(sta.getId());
        // wareHouseManager.isInventoryNumber(sta.getId());
        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        // 占用库存 create stv, stvline
        wareHouseManager.createTransferByStaId(sta.getId(), null);
        if (sta.getSystemKey() != null && Constants.WMS.equals(sta.getSystemKey())) {
            wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.VMI_OWNER_TRANSFER);
        }

        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }

        try {
            wareHouseManager.executeVmiTransferOutBound(sta.getId(), null, sta.getMainWarehouse().getId());

        } catch (Exception e) {
            log.error("VMI AUTO TRANSFER error", e);
        }
    }

    public StockTransApplication autoChangeOwnerCreateSta(InventoryStatus sts, String owner, String addOwner, OperationUnit ou) {
        StockTransApplication sta = new StockTransApplication();
        sta.setAddiOwner(addOwner);
        sta.setOwner(owner);
        sta.setMainStatus(sts);
        sta.setType(StockTransApplicationType.VMI_OWNER_TRANSFER);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setCreateTime(new Date());
        sta.setMainWarehouse(ou);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setLastModifyTime(new Date());
        sta.setIsNotPacsomsOrder(true);
        sta.setIsNeedOccupied(true);
        sta.setSystemKey(Constants.WMS);// 转店作业单来源于WMS系统自己创建
        return sta;
    }

}
