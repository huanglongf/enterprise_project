package com.jumbo.wms.manager.task.shelfLife;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgSkuUpdateDao;
import com.jumbo.dao.warehouse.MsgSkuUpdateLogDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.RmiSku;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.MsgSkuUpdateLog;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.MsgSkuUpdateCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TimeTypeMode;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("shelfLifeTaskManager")
public class ShelfLifeManagerImpl extends BaseManagerImpl implements ShelfLifeManager {

    private static final long serialVersionUID = -5678212647804536939L;

    protected static final Logger logger = LoggerFactory.getLogger(ShelfLifeManagerImpl.class);

    private EventObserver eventObserver;
    @Autowired
    private BiChannelDao biDao;
    @Autowired
    private ChannelWhRefRefDao cwrrDao;
    @Autowired
    private InventoryDao iDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private InventoryStatusDao isDao;
    @Autowired
    private MsgSkuUpdateDao msgDao;
    @Autowired
    private MsgSkuUpdateLogDao msgLogDao;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    /**
     * MQ JmsTemplate
     */
    private JmsTemplate taxMqJmsTemplate;
    private JmsTemplate bhMqJmsTemplate;

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
            taxMqJmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:ShelfLifeManagerImpl");
        }
    }

    @Override
    public void updateShelfLifeStatus(BiChannel b) {
        try {
            InventoryStatus iStatus = null;
            if (log.isInfoEnabled()) {
                logger.info("bichannelstart" + b.getName());
            }
            // 获取渠道下绑定的仓库OU_ID
            List<ChannelWhRef> cwrList = cwrrDao.findChannelWhRefListByChannelIdR(b.getId(), new BeanPropertyRowMapperExt<ChannelWhRef>(ChannelWhRef.class));
            for (ChannelWhRef cwr : cwrList) {

                OperationUnit ou = operationUnitDao.getByPrimaryKey(cwr.getWh().getId());
                iStatus = isDao.getByName("临近保质期", ou.getParentUnit().getParentUnit().getId());
                // 判断是否有该状态
                if (iStatus == null) {
                    iStatus = getInventoryStatus(operationUnitDao.getByPrimaryKey(ou.getParentUnit().getParentUnit().getId()));
                }
                List<Inventory> iList = iDao.findInventoryByShelfLife(cwr.getWh().getId(), InboundStoreMode.SHELF_MANAGEMENT.getValue(), b.getCode(), new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
                StockTransApplication sta = creatOutStaStv(iList, cwr.getWh().getId(), b.getCode());
                if (sta == null) {
                    logger.info("bichannelcontinue" + b.getName() + ou.getName());
                    continue;
                }
                // 合并入库单
                List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                Map<String, List<StvLineCommand>> lMap = new HashMap<String, List<StvLineCommand>>();
                for (StvLineCommand cmd : outStvLine) {
                    String key = cmd.getSkuId() + "";
                    List<StvLineCommand> cl = lMap.get(key);
                    if (cl == null) {
                        cl = new ArrayList<StvLineCommand>();
                    }
                    cl.add(cmd);
                    log.debug("put key : {}", key);
                    lMap.put(key, cl);
                }
                List<StvLine> inlist = new ArrayList<StvLine>();
                for (Inventory cmd : iList) {
                    log.debug("key is : {}", cmd.getSku().getId());
                    List<StvLineCommand> cl = lMap.get(cmd.getSku().getId() + "");
                    Long qty = cmd.getQuantity();
                    for (int i = 0; i < cl.size(); i++) {
                        StvLineCommand mcmd = cl.get(i);
                        Long quantity = 0l;
                        if (qty >= mcmd.getQuantity()) {
                            quantity = mcmd.getQuantity();
                            cl.remove(i--);
                        } else {
                            quantity = qty;
                            mcmd.setQuantity(mcmd.getQuantity() - quantity);
                        }
                        StvLine l = new StvLine();
                        l.setLocation(cmd.getLocation());
                        l.setSku(cmd.getSku());
                        l.setInvStatus(cmd.getStatus());
                        l.setOwner(mcmd.getOwner());
                        l.setQuantity(quantity);
                        l.setSkuCost(mcmd.getSkuCost());
                        l.setBatchCode(mcmd.getBatchCode());
                        l.setValidDate(cmd.getValidDate());
                        l.setExpireDate(cmd.getExpireDate());
                        l.setProductionDate(cmd.getProductionDate());
                        StaLine stal = new StaLine();
                        stal.setId(mcmd.getStalineId());
                        l.setStaLine(stal);
                        inlist.add(l);
                        qty -= quantity;
                        if (qty < 1) break;
                    }
                }
                noExecuteInvStatusChangeForImpory(sta.getId(), inlist, iStatus);
                wareHouseManagerExe.executeInvStatusChangeForImpory(sta.getId(), null, false, true);
                if (log.isInfoEnabled()) {
                    logger.info("bichannelend" + b.getName());
                }
            }
        } catch (Exception e) {
            logger.error("bichannelerror" + b.getName());
            logger.error("errorEx", e.toString());
        }

    }

    /**
     * 添加临近保质期状态
     */
    public InventoryStatus getInventoryStatus(OperationUnit ou) {
        InventoryStatus is = new InventoryStatus();
        is.setName("临近保质期");
        is.setOu(ou);
        is.setIsAvailable(true);
        is.setLastModifyTime(new Date());
        is.setIsForSale(false);
        is.setIsInCost(false);
        is.setIsSystem(false);
        isDao.save(is);
        return is;
    }

    /**
     * 创建出库sta stv staLine stvLine
     */
    public StockTransApplication creatOutStaStv(List<Inventory> iList, Long ouId, String owner) {
        if (iList.size() > 0) {
            TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT);
            if (type == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT_NOT_FOUND);
            }
            // List<StvLine> lineList = new ArrayList<StvLine>();
            OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
            StockTransApplication sta = new StockTransApplication();
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setIsNeedOccupied(false);
            sta.setMainWarehouse(ou);
            sta.setOwner(owner);
            // 锁定作业单
            sta.setIsLocked(true);
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            sta.setRefSlipType(SlipType.WMS_SHELF_LIFE);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, ou.getId());
            sta.setType(StockTransApplicationType.INVENTORY_STATUS_CHANGE);
            whExe.validateBiChannelSupport(null, sta.getOwner());
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            staDao.save(sta);
            StockTransVoucher stv = new StockTransVoucher();
            stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
            stv.setCode(sta.getCode() + "01");
            stv.setCreateTime(new Date());
            stv.setDirection(TransactionDirection.OUTBOUND);
            stv.setSta(sta);
            stv.setLastModifyTime(new Date());
            stv.setStatus(StockTransVoucherStatus.CREATED);
            stv.setTransactionType(type);
            stv.setWarehouse(ou);
            stvDao.save(stv);
            List<StvLine> lineList = new ArrayList<StvLine>();
            // Map<String, Long> map = new HashMap<String, Long>();
            Map<String, StvLine> mapStvLine = new HashMap<String, StvLine>();
            for (Inventory i : iList) {
                // 创建stvLine
                StvLine l = new StvLine();
                l.setSku(i.getSku());
                l.setLocation(i.getLocation());
                l.setOwner(owner);
                l.setInvStatus(i.getStatus());
                l.setQuantity(i.getQuantity());
                l.setSku(i.getSku());
                l.setInvStatus(i.getStatus());
                l.setDistrict(i.getDistrict());
                l.setDirection(TransactionDirection.OUTBOUND);
                l.setStv(stv);
                l.setBatchCode(i.getBatchCode());
                l.setTransactionType(type);
                l.setWarehouse(ou);
                /** -------------新建保质期字段---------------- */
                l.setValidDate(i.getValidDate());
                l.setExpireDate(i.getExpireDate());
                l.setProductionDate(i.getProductionDate());
                /** -------------新建保质期字段---------------- */
                String key = l.getSku().getId() + ":" + l.getLocation().getId() + ":" + l.getInvStatus().getId();
                if (mapStvLine.containsKey(key)) {
                    l = mapStvLine.get(key);
                    l.setQuantity(l.getQuantity() + i.getQuantity());
                } else {
                    lineList.add(l);
                    mapStvLine.put(key, l);
                }
                stvLineDao.save(l);
            }
            for (StvLine line : lineList) {
                StaLine stal = new StaLine();
                stal.setInvStatus(line.getInvStatus());
                stal.setOwner(line.getOwner());
                stal.setQuantity(line.getQuantity());
                stal.setSku(line.getSku());
                stal.setOwner(line.getOwner());
                stal.setSta(sta);
                staLineDao.save(stal);
                line.setStaLine(stal);
            }
            stv.setStvLines(lineList);
            // stvDao.save(stv);
            staDao.flush();
            staDao.updateSkuQtyById(sta.getId());
            Map<String, Object> params = new HashMap<String, Object>();
            // 检验是否存在暂存区的商品
            // wmExecute.valdateOutBoundLocationIsGI(stv);
            params.put("in_sta_id", sta.getId());
            SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
            SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), s};
            Map<String, Object> result = staDao.executeSp("sp_occ_inv_for_transit_inner", sqlParameters, params);
            String errorSku = (String) result.get("error_sku_id");
            BusinessException root = null;
            if (StringUtils.hasText(errorSku)) {
                String[] skus = errorSku.split(",");
                for (String str : skus) {
                    String[] strs = str.split(Constants.STA_SKUS_SLIPT_STR);
                    Long skuId = Long.parseLong(strs[0]);
                    Long qty = Long.parseLong(strs[1]);
                    if (root == null) {
                        root = new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
                    }
                    BusinessException current = root;
                    while (current.getLinkedException() != null) {
                        current = current.getLinkedException();
                    }
                    Sku sku = skuDao.getByPrimaryKey(skuId);
                    BusinessException be = new BusinessException(ErrorCode.SKU_NO_INVENTORY_QTY, new Object[] {sku.getName(), sku.getCode(), sku.getBarCode(), qty});
                    current.setLinkedException(be);
                }
                throw root;
            }
            stv.getStvLines().clear();
            stvDao.save(stv);
            stvDao.flush();
            // 重建sta line
            staLineDao.deleteStaLineByStaId(sta.getId());
            staLineDao.createByStaId(sta.getId());
            // 重建stv line
            stvLineDao.createTIOutByStaId(sta.getId(), stv.getId());
            stvLineDao.flush();
            staDao.updateSkuQtyById(sta.getId());
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
            return sta;
        }
        return null;
    }

    /**
     * 创建入库stv stvLine
     */
    public void noExecuteInvStatusChangeForImpory(Long staId, List<StvLine> stvlineList, InventoryStatus is) throws Exception {
        if (stvlineList == null || stvlineList.size() == 0) {
            throw new BusinessException(ErrorCode.TRANIST_INNER_LINE_EMPTY);
        }
        TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN);
        if (type == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher outStv = stvList.get(0);
        List<StvLineCommand> outLineList = stvLineDao.findByStvIdGroupBySkuLocationOwner(outStv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        // 校验入库数量
        Map<String, Long> vmap = new HashMap<String, Long>();
        for (StvLineCommand l : outLineList) {
            String key = l.getSkuId() + ":" + l.getBarCode();
            if (vmap.get(key) == null) {
                vmap.put(key, l.getQuantity());
            } else {
                vmap.put(key, vmap.get(key) + l.getQuantity());
            }
        }
        for (StvLine cmd : stvlineList) {
            String key = cmd.getSku().getId() + ":" + cmd.getSku().getBarCode();
            if (vmap.get(key) == null) {
                vmap.put(key, -cmd.getQuantity());
            } else {
                if (vmap.get(key) - cmd.getQuantity() == 0L) {
                    vmap.remove(key);
                } else {
                    vmap.put(key, vmap.get(key) - cmd.getQuantity());
                }
            }
        }
        // 校验提交数据
        for (StvLine cmd : stvlineList) {
            if (!StringUtils.hasText(cmd.getOwner())) {
                throw new BusinessException(ErrorCode.OWNER_IS_NULL);
            }
            if (cmd.getLocation() == null) {
                WarehouseLocation loc = warehouseLocationDao.getByPrimaryKey(cmd.getLocation().getId());
                if (loc == null) {
                    throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND);
                } else {
                    cmd.setLocation(loc);
                }
            }
            if (cmd.getSku() == null) {
                Sku sku = skuDao.getByPrimaryKey(cmd.getSku().getId());
                if (sku == null) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {""});
                } else {
                    cmd.setSku(sku);
                }
            }
            if (cmd.getInvStatus() == null) {
                InventoryStatus iss = inventoryStatusDao.getByPrimaryKey(cmd.getInvStatus().getId());
                if (iss == null) {
                    throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
                } else {
                    cmd.setInvStatus(iss);
                }
            }
        }
        // 创建入库单
        StockTransVoucher stv = new StockTransVoucher();
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>()));
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
        stv.setCreateTime(new Date());
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setMode(InboundStoreMode.SHELF_MANAGEMENT);
        stv.setSta(sta);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setLastModifyTime(new Date());
        stv.setTransactionType(type);
        stv.setWarehouse(sta.getMainWarehouse());
        List<StvLine> list = new ArrayList<StvLine>();
        List<StvLine> outList = stvLineDao.findStvLineListByStvId(outStv.getId());
        for (StvLine cmd : stvlineList) {
            StvLine l = new StvLine();
            l.setDirection(TransactionDirection.INBOUND);
            l.setBatchCode(cmd.getBatchCode());
            l.setSkuCost(cmd.getSkuCost());
            l.setDistrict(cmd.getLocation().getDistrict());
            l.setInvStatus(is);
            l.setValidDate(cmd.getValidDate());
            l.setExpireDate(cmd.getExpireDate());
            l.setProductionDate(cmd.getProductionDate());
            l.setLocation(cmd.getLocation());
            l.setOwner(cmd.getOwner());
            l.setQuantity(cmd.getQuantity());
            l.setSku(cmd.getSku());
            l.setStv(stv);
            l.setTransactionType(type);
            l.setWarehouse(sta.getMainWarehouse());
            // 12.1
            // List<String> error = wareHouseManager.validateIsSameBatch(cmd);
            // if (error != null && error.size() > 0) throw new
            // BusinessException(ErrorCode.INVENTORY_SKU_LOCATION_IS_SINGLE_STOREMODE_ERROR,
            // new
            // Object[] {error.toString()});

            // 匹配sta line
            for (StaLine line : staLineDao.findByStaId(sta.getId())) {
                if (line.getSku().getId().equals(l.getSku().getId()) && line.getOwner().equals(l.getOwner())) {
                    l.setStaLine(line);
                    break;
                }
            }
            for (StvLine sl : outList) {
                if (sl.getSku().getId().equals(l.getSku().getId()) && sl.getBatchCode().equals(l.getBatchCode()) && sl.getOwner().equals(l.getOwner())) {
                    l.setInBoundTime(sl.getInBoundTime());
                    break;
                }
            }
            list.add(l);
        }
        stv.setStvLines(list);
        stvDao.save(stv);
    }

    @Override
    public void updateSkuShelfLifeTime() throws Exception {
        List<MsgSkuUpdateCommand> msg = msgDao.findMsgSkuUpdate(new BeanPropertyRowMapper<MsgSkuUpdateCommand>(MsgSkuUpdateCommand.class));
        Sku sku = null;
        for (MsgSkuUpdateCommand m : msg) {
            try {
                sku = skuDao.getByPrimaryKey(m.getSkuId());
                RmiSku rs = new RmiSku();// 通知OMS类
                rs.setCustomerSkuCode(sku.getCustomerSkuCode());
                rs.setValidDate(m.getValidDate());
                rs.setTimeType(m.getTimeType());
                rs.setCustomerCode(sku.getCustomer().getCode());
                BaseResult baseResult = rmi4Wms.updateSku(rs);// 同步数据给OMS
                if (baseResult.getStatus() == 0) {
                    // 有错误，跳过这条数据
                    log.debug("Call oms update shelfLife sku error: " + baseResult.getMsg() + " MsgSkuUpdate_ID:" + m.getId());
                    continue;
                }
                MsgSkuUpdateLog msgLog = new MsgSkuUpdateLog();// 发送成功记录LOG
                msgLog.setSku(sku);
                msgLog.setCustomer(sku.getCustomer());
                msgLog.setTranTime(new Date());
                msgLog.setTimeType(TimeTypeMode.valueOf(m.getTimeType()));
                msgLog.setValidDate(m.getValidDate());
                msgLog.setOriginalTime(m.getCreatTime());
                msgLogDao.save(msgLog);
                msgDao.deleteByPrimaryKey(m.getId());
            } catch (Exception e) {
                log.error("", e);
                throw e;
            }
        }
        try {
            msgDao.updateMsgSkuExeCount();
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }

    public JmsTemplate getTaxMqJmsTemplate() {
        return taxMqJmsTemplate;
    }

    public void setTaxMqJmsTemplate(JmsTemplate taxMqJmsTemplate) {
        this.taxMqJmsTemplate = taxMqJmsTemplate;
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

}
