package com.jumbo.webservice.pda.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.CartonLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PdaHandOverLineDao;
import com.jumbo.dao.warehouse.PdaOrderDao;
import com.jumbo.dao.warehouse.PdaOrderLineDao;
import com.jumbo.dao.warehouse.PdaOrderLineSnDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.pda.InboundReceiveDetail;
import com.jumbo.webservice.pda.Inventory;
import com.jumbo.webservice.pda.ShelvesSkuDetial;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListRequest;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesRequest;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundRequest;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckRequest;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementRequest;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderRequest;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.pda.PdaHandOverLine;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderCommand;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.wms.model.pda.PdaOrderLineSn;
import com.jumbo.wms.model.pda.PdaOrderType;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.CartonLineCommand;
import com.jumbo.wms.model.warehouse.CartonStatus;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("pdaOperationManager")
public class PdaOperationManagerImpl extends BaseManagerImpl implements PdaOperationManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = -6078743978854713461L;
    private static final Logger log = LoggerFactory.getLogger(PdaOperationManagerImpl.class);
    @Autowired
    private PdaOrderDao pdaOrderDao;
    @Autowired
    private PdaOrderLineDao pdaOrderLineDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private PdaHandOverLineDao pdaHandOverLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryDao invDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private PdaOrderLineSnDao pdaOrderLineSnDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private CartonLineDao cartonLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Resource
    private ApplicationContext applicationContext;
    private EventObserver eventObserver;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:PdaOperationManagerImpl");
        }
    }

    @Override
    public PdaOrder saveMovementUpload(UploadLibraryMovementRequest uploadLibraryMovementRequest) {
        PdaOrder po = new PdaOrder();
        po.setOrderCode(uploadLibraryMovementRequest.getUploadLibraryMovementRequestBody().getCode());
        po.setIsPDA(false);
        po.setOuId(uploadLibraryMovementRequest.getUploadLibraryMovementRequestBody().getUniqCode());
        po.setCreateTime(new Date());
        po.setStatus(DefaultStatus.CREATED);
        po.setType(PdaOrderType.InnerMove);
        // 保存头信息
        po = pdaOrderDao.save(po);
        // 保存库内移动入库信息
        List<Inventory> inBound = uploadLibraryMovementRequest.getUploadLibraryMovementRequestBody().getInbound().getLibraryMovementInOutDetails();
        if (inBound != null && inBound.size() > 0) {
            for (int i = 0; i < inBound.size(); i++) {
                Inventory inv = inBound.get(i);
                PdaOrderLine pl = new PdaOrderLine();
                pl.setDirection(TransactionDirection.INBOUND);
                pl.setSkuCode(inv.getSkuBarCode());
                pl.setQty(Long.parseLong(inv.getQty()));
                pl.setLocationCode(inv.getLocation());
                pl.setInvStatus(inv.getInvStatus());
                pl.setPdaOrder(po);
                pdaOrderLineDao.save(pl);
            }
        } else {
            throw new BusinessException(ErrorCode.PDA_DETAIL_NOT_FOUND);
        }
        return po;
    }

    @Override
    public void saveCheckUpload(UploadInventoryCheckRequest uploadInventoryCheckRequest) {
        PdaOrder po = new PdaOrder();
        po.setOrderCode(uploadInventoryCheckRequest.getUploadInventoryCheckRequestBody().getCode());
        po.setOuId(uploadInventoryCheckRequest.getUploadInventoryCheckRequestBody().getUniqCode().toString());
        po.setStatus(DefaultStatus.CREATED);
        po.setType(PdaOrderType.INVENTORYCHECK);
        po.setCreateTime(new Date());
        po = pdaOrderDao.save(po);
        List<Inventory> detail = uploadInventoryCheckRequest.getUploadInventoryCheckRequestBody().getInventoryCheckDetails();
        if (detail != null && detail.size() > 0) {
            for (int i = 0; i < detail.size(); i++) {
                Inventory inv = detail.get(i);
                PdaOrderLine pl = new PdaOrderLine();
                pl.setPdaOrder(po);
                pl.setSkuCode(inv.getSkuBarCode());
                pl.setQty(Long.parseLong(inv.getQty()));
                pl.setLocationCode(inv.getLocation());
                pdaOrderLineDao.save(pl);
            }
        } else {
            throw new BusinessException(ErrorCode.PDA_DETAIL_NOT_FOUND);
        }
    }

    @Override
    public void saveUploadInbound(UploadInboundReceiveRequest uploadInboundReceiveRequest) {
        PdaOrder po = new PdaOrder();
        po.setOrderCode(uploadInboundReceiveRequest.getUploadInboundReceiveRequestBody().getCode());
        po.setOuId(uploadInboundReceiveRequest.getUploadInboundReceiveRequestBody().getUniqCode());
        po.setCreateTime(new Date());
        po.setStatus(DefaultStatus.CREATED);
        po.setType(PdaOrderType.Inbound);
        PdaOrder spo = pdaOrderDao.save(po);
        List<InboundReceiveDetail> rl = uploadInboundReceiveRequest.getUploadInboundReceiveRequestBody().getInboundReceiveDetails();
        for (int i = 0; i < rl.size(); i++) {
            PdaOrderLine pol = new PdaOrderLine();
            pol.setSkuCode(rl.get(i).getSkuCode());
            pol.setQty(Long.parseLong(rl.get(i).getQty()));
            pol.setDirection(TransactionDirection.INBOUND);
            pol.setPdaOrder(spo);
            pdaOrderLineDao.save(pol);
        }
    }

    @Override
    public void saveOnShelvesUpload(UploadInboundOnShelvesRequest uploadInboundOnShelvesRequest) {
        PdaOrder po = new PdaOrder();
        po.setOrderCode(uploadInboundOnShelvesRequest.getUploadInboundOnShelvesRequestBody().getCode());
        po.setOuId(uploadInboundOnShelvesRequest.getUploadInboundOnShelvesRequestBody().getUniqCode());
        po.setUserName(uploadInboundOnShelvesRequest.getUploadInboundOnShelvesRequestBody().getUserName());
        po.setCreateTime(new Date());
        po.setStatus(DefaultStatus.CREATED);
        po.setType(PdaOrderType.INBOUND_SHELEVES);
        PdaOrder spo = pdaOrderDao.save(po);
        List<ShelvesSkuDetial> sl = uploadInboundOnShelvesRequest.getUploadInboundOnShelvesRequestBody().getInboundOnShelvesDetials();
        for (int i = 0; i < sl.size(); i++) {
            PdaOrderLine pol = new PdaOrderLine();
            pol.setSkuCode(sl.get(i).getSkuCode());
            pol.setLocationCode(sl.get(i).getLocations().get(0));
            pol.setmDate(sl.get(i).getMDate());
            pol.setQty(Long.parseLong(sl.get(i).getQty()));
            pol.setPdaOrder(spo);
            pol.setDirection(TransactionDirection.INBOUND);
            pdaOrderLineDao.save(pol);
            if (sl.get(i).getSns() != null && sl.get(i).getSns().size() > 0) {
                List<String> snDetail = sl.get(i).getSns();
                for (int j = 0; j < snDetail.size(); j++) {
                    if (snDetail.get(j) != null && !snDetail.get(j).equals("")) {
                        PdaOrderLineSn pls = new PdaOrderLineSn();
                        pls.setPdaOrderLine(pol);
                        pls.setSnCode(snDetail.get(j));
                        pdaOrderLineSnDao.save(pls);
                    }
                }
            }
        }
    }

    @Override
    public PdaOrder saveHandOverListUpload(UploadHandOverListRequest uploadHandOverListRequest) {
        // 上传订单头
        PdaOrder po = new PdaOrder();
        po.setOuId(uploadHandOverListRequest.getUploadHandOverListRequestBody().getUniqCode());
        po.setLpCode(uploadHandOverListRequest.getUploadHandOverListRequestBody().getLpcode());
        po.setStatus(DefaultStatus.CREATED);
        po.setCreateTime(new Date());
        po.setType(PdaOrderType.HANDOVER);
        po = pdaOrderDao.save(po);
        // 得到并上传订单明细
        List<String> detail = uploadHandOverListRequest.getUploadHandOverListRequestBody().getTransNos();
        if (detail != null && detail.size() > 0) {
            for (int i = 0; i < detail.size(); i++) {
                PdaHandOverLine pl = new PdaHandOverLine();
                pl.setPdaOrder(po);
                pl.setTransNo(detail.get(i));
                pdaHandOverLineDao.save(pl);
            }
        } else {
            // PDA上传明细为空
            throw new BusinessException(ErrorCode.PDA_DETAIL_NOT_FOUND);
        }
        return po;
    }

    @Override
    public void saveReturnOrder(UploadReturnOrderRequest uploadReturnOrderRequest) {
        PdaOrder po = new PdaOrder();
        po.setOrderCode(uploadReturnOrderRequest.getUploadReturnOrderRequestBody().getCode());
        po.setOuId(uploadReturnOrderRequest.getUploadReturnOrderRequestBody().getUniqCode());
        po.setStatus(DefaultStatus.CREATED);
        po.setCreateTime(new Date());
        po.setType(PdaOrderType.RETURN);
        po = pdaOrderDao.save(po);
        List<ShelvesSkuDetial> detail = uploadReturnOrderRequest.getUploadReturnOrderRequestBody().getReturnOrderDetails();
        if (detail != null && detail.size() > 0) {
            for (int i = 0; i < detail.size(); i++) {
                PdaOrderLine pl = new PdaOrderLine();
                pl.setDirection(TransactionDirection.OUTBOUND);
                pl.setSkuCode(detail.get(i).getSkuCode());
                pl.setQty(Long.parseLong(detail.get(i).getQty()));
                pl.setLocationCode(detail.get(i).getLocations().get(0));
                pl.setIndex(detail.get(i).getIndex());
                pl.setPdaOrder(po);
                pl.setInvStatus(detail.get(i).getInvStatus());
                pl = pdaOrderLineDao.save(pl);
                // 保存明细sn
                if (detail.get(i).getSns() != null && detail.get(i).getSns().size() > 0) {
                    List<String> snDetail = detail.get(i).getSns();
                    for (int j = 0; j < snDetail.size(); j++) {
                        PdaOrderLineSn pls = new PdaOrderLineSn();
                        pls.setPdaOrderLine(pl);
                        pls.setSnCode(snDetail.get(j));
                        pdaOrderLineSnDao.save(pls);
                    }
                }
            }
        } else {
            throw new BusinessException(ErrorCode.PDA_DETAIL_NOT_FOUND);
        }
    }

    /**
     * 执行主动移库
     * 
     * @param pdaOrder
     * @throws Exception
     */
    public void exeInnerMove(PdaOrder pdaOrder) throws Exception {
        if (pdaOrder == null) {
            throw new BusinessException("Inner move order not found！");
        }
        if (pdaOrder.getType() == null || !PdaOrderType.InnerMove.equals(pdaOrder.getType())) {
            throw new BusinessException("pda order type error！");
        }
        if (pdaOrder.getStatus() == null || (!DefaultStatus.CREATED.equals(pdaOrder.getStatus()) && !DefaultStatus.CANCELED.equals(pdaOrder.getStatus()))) {
            throw new BusinessException("pda order status error！");
        }
        List<PdaOrderLine> pdaLine = pdaOrderLineDao.findLineByPdaOrderId(pdaOrder.getId());
        if (pdaLine == null || pdaLine.size() < 1) {
            throw new BusinessException("pda order line info error！");
        }
        if (StringUtil.isEmpty(pdaOrder.getOuId())) {
            throw new BusinessException("pda order warehouse info error！");
        }
        Long ouId = null;
        try {
            ouId = Long.valueOf(pdaOrder.getOuId());
        } catch (Exception e) {
            throw new BusinessException("pda order warehouse info error！:" + pdaOrder.getOuId());
        }
        Warehouse wh = warehouseDao.getByOuId(ouId);
        if (wh == null) {
            throw new BusinessException("pda order warehouse info error！not found:" + pdaOrder.getOuId());
        }
        // 入库明细
        // 出库明细
        StringBuffer errorInfo = new StringBuffer();
        boolean isError = false;
        Map<String, StvLine> outMap = new HashMap<String, StvLine>();
        Map<String, StvLine> inMap = new HashMap<String, StvLine>();
        Map<String, Sku> skuMap = new HashMap<String, Sku>();
        Map<String, WarehouseLocation> locMap = new HashMap<String, WarehouseLocation>();
        Map<String, InventoryStatus> statusMap = new HashMap<String, InventoryStatus>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        for (PdaOrderLine line : pdaLine) {
            if (line.getQty() == null || line.getQty() == 0) {
                continue;
            }

            StvLine stvLine = new StvLine();
            Sku sku = skuMap.get(line.getSkuCode());
            if (sku == null) {
                sku = skuDao.getByBarcode(line.getSkuCode(), customerId);
                if (sku == null) {
                    SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(line.getSkuCode(), customerId);
                    if (addSkuCode != null) {
                        sku = addSkuCode.getSku();
                        log.debug("sku find by add sku barcode {}", line.getSkuCode());
                    }
                }
                if (sku == null) {
                    isError = true;
                    errorInfo.append("[SKU:" + line.getSkuCode() + " not found!]");
                    continue;
                } else {
                    skuMap.put(line.getSkuCode(), sku);
                }
            }
            stvLine.setSku(sku);
            WarehouseLocation loc = locMap.get(line.getLocationCode());
            if (loc == null) {
                loc = warehouseLocationDao.findLocationByCode(line.getLocationCode(), wh.getOu().getId());
                if (loc == null) {
                    isError = true;
                    errorInfo.append("[Location:" + line.getLocationCode() + " not found!]");
                    continue;
                } else {
                    locMap.put(line.getLocationCode(), loc);
                }
            }
            stvLine.setLocation(loc);
            InventoryStatus status = statusMap.get(line.getInvStatus());
            if (status == null) {
                status = inventoryStatusDao.findByNameUnionSystem(line.getInvStatus(), wh.getOu().getParentUnit().getParentUnit().getId());
                if (status == null) {
                    isError = true;
                    errorInfo.append("[Inentory status:" + line.getInvStatus() + " not found!]");
                    continue;
                } else {
                    statusMap.put(line.getInvStatus(), status);
                }
            }
            stvLine.setInvStatus(status);
            stvLine.setQuantity(line.getQty());
            String key = sku.getId() + "_" + status.getId();
            if (TransactionDirection.OUTBOUND.equals(line.getDirection())) {
                key += loc.getId();
                if (outMap.containsKey(key)) {
                    StvLine temp = outMap.get(key);
                    temp.setQuantity(temp.getQuantity() + line.getQty());
                } else {
                    // 出库
                    stvLine.setDirection(TransactionDirection.OUTBOUND);
                    outMap.put(key, stvLine);
                }
            } else {
                // 入库
                if (inMap.containsKey(key)) {
                    StvLine temp = outMap.get(key);
                    temp.setQuantity(temp.getQuantity() + line.getQty());
                } else {
                    // 出库
                    stvLine.setDirection(TransactionDirection.INBOUND);
                    inMap.put(key, stvLine);
                }
            }
        }
        if (isError) {
            pdaOrder.setStatus(DefaultStatus.ERROR);
            pdaOrder.setMemo(errorInfo.toString());
            pdaOrderDao.save(pdaOrder);
            return;
        }
        StockTransApplication sta = staDao.findStaByCode(pdaOrder.getOrderCode());
        // 查找不到 库内移动单 默认认为是主动移动
        if (sta != null && StockTransApplicationType.TRANSIT_INNER.equals(sta.getType()) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            // 被动移库逻辑
        } else {
            // 主动移库逻辑
            initiativeInnerMove(pdaOrder, inMap, outMap, wh);
        }
    }

    // 主动移库逻辑处理
    private void initiativeInnerMove(PdaOrder pdaOrder, Map<String, StvLine> inLine, Map<String, StvLine> outLine, Warehouse wh) throws Exception {
        List<StvLine> outList = new ArrayList<StvLine>();
        StringBuffer errorInfo = new StringBuffer();
        boolean isError = false;
        for (StvLine out : outLine.values()) {
            Long qty = out.getQuantity();
            List<com.jumbo.wms.model.warehouse.Inventory> invList =
                    invDao.findBySkuLocAndStatus(out.getSku().getId(), out.getLocation().getId(), out.getInvStatus().getId(), wh.getOu().getId(), new BeanPropertyRowMapperExt<com.jumbo.wms.model.warehouse.Inventory>(
                            com.jumbo.wms.model.warehouse.Inventory.class));
            for (com.jumbo.wms.model.warehouse.Inventory inv : invList) {
                StvLine temp = new StvLine();
                if (inv.getQuantity() >= qty) {
                    temp.setQuantity(qty);
                    qty = 0L;
                } else if (inv.getQuantity() > 0) {
                    temp.setQuantity(inv.getQuantity());
                    qty -= inv.getQuantity();
                }
                temp.setSku(out.getSku());
                temp.setLocation(out.getLocation());
                temp.setInvStatus(out.getInvStatus());
                temp.setOwner(inv.getOwner());
                outList.add(temp);
                if (qty < 1) break;
            }
            if (qty > 0) {
                errorInfo.append("[SKU:" + out.getSku().getBarCode() + " The lack of inventory! number:]" + qty);
                isError = true;
            }
        }
        // 判断是否有错
        if (isError) {
            pdaOrder.setStatus(DefaultStatus.ERROR);
            pdaOrder.setMemo(errorInfo.toString());
            pdaOrderDao.save(pdaOrder);
            return;
        }
        // 创建库间移动单 并且占用库存
        StockTransApplication sta = whManager.createTransitInnerSta(false, pdaOrder.getOrderCode(), "", null, wh.getOu().getId(), outList);
        List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        Map<String, List<StvLineCommand>> tmpMap = new HashMap<String, List<StvLineCommand>>();
        for (StvLineCommand cmd : outStvLine) {
            String key = cmd.getSkuId() + "_" + cmd.getIntInvstatus();
            List<StvLineCommand> val = tmpMap.get(key);
            if (val == null) {
                val = new ArrayList<StvLineCommand>();
            }
            val.add(cmd);
            tmpMap.put(key, val);
        }
        // 入库STVLINE
        List<StvLineCommand> inLineList = new ArrayList<StvLineCommand>();
        for (StvLine line : inLine.values()) {
            String key = line.getSku().getId() + "_" + line.getInvStatus().getId();
            List<StvLineCommand> val = tmpMap.get(key);
            if (val == null) {
                throw new BusinessException("[SKU:" + line.getSku().getBarCode() + " OUT is not equal to IN !]");
            }
            Long qty = line.getQuantity();
            for (int i = 0; i < val.size(); i++) {
                StvLineCommand stvl = val.get(i);
                StvLineCommand l = new StvLineCommand();
                l.setLocation(line.getLocation());
                l.setLocationId(line.getLocation().getId());
                l.setSku(line.getSku());
                l.setSkuId(line.getSku().getId());
                l.setInvStatus(line.getInvStatus());
                l.setQuantity(line.getQuantity());
                l.setIntInvstatus(line.getInvStatus().getId());
                l.setSkuCost(stvl.getSkuCost());
                l.setBatchCode(stvl.getBatchCode());
                l.setProductionDate(stvl.getProductionDate());
                l.setExpireDate(stvl.getExpireDate());
                l.setValidDate(stvl.getValidDate());
                l.setOwner(stvl.getOwner());
                if (stvl.getQuantity() > qty) {
                    l.setQuantity(qty);
                    qty = 0L;
                    stvl.setQuantity(stvl.getQuantity() - qty);
                } else if (stvl.getQuantity().equals(qty)) {
                    l.setQuantity(qty);
                    qty = 0L;
                    val.remove(i--);
                } else {
                    l.setQuantity(qty);
                    qty -= stvl.getQuantity();
                    val.remove(i--);
                }
                inLineList.add(l);
                if (qty.equals(0L)) break;
            }
        }
        wareHouseManagerExe.executeTransitInner(false, sta.getId(), inLineList, null);
        pdaOrder.setMemo("完成");
        pdaOrder.setStatus(DefaultStatus.FINISHED);
        pdaOrderDao.save(pdaOrder);
    }

    /**
     * 更新PDAOrder 状态
     * 
     * @param pdaOrderId
     * @param memo
     */
    public void updatePdaOrderStatus(Long pdaOrderId, DefaultStatus status, String memo) {
        if (pdaOrderId != null && status != null) {
            pdaOrderDao.updatePdaOrderStatus(pdaOrderId, status.getValue(), memo);
        }
    }

    @Override
    public Pagination<PdaOrderCommand> findStaForPickingByModel(int start, int pageSize, Date bt, Date bt1, Date endTime, Date endTime1, DefaultStatus status, PdaOrderType type, String code, Long id, Sort[] sorts) {
        Integer stat = null;
        String orderCode = null;
        Integer pType = null;
        if (status == null) {
            stat = null;
        } else {
            stat = status.getValue();
        }
        if (type == null) {
            pType = null;
        } else {
            pType = type.getValue();
        }
        if (StringUtils.hasText(code)) {
            orderCode = code;
        }
        return pdaOrderDao.findPdaOrderList(start, pageSize, bt, bt1, endTime, endTime1, stat, pType, orderCode, id.toString(), sorts, new BeanPropertyRowMapper<PdaOrderCommand>(PdaOrderCommand.class));
    }

    @Override
    public Pagination<PdaOrderLine> findPdaLogLine(int start, int pageSize, Long id, Sort[] sorts) {
        return pdaOrderLineDao.findPdaLogLine(start, pageSize, id, sorts, new BeanPropertyRowMapper<PdaOrderLine>(PdaOrderLine.class));
    }

    @Override
    public void saveUploadInitativeMoveInBound(UploadInitiativeMoveInboundRequest uploadInitiativeMoveInboundRequest) {
        PdaOrder order = new PdaOrder();
        order.setOrderCode(uploadInitiativeMoveInboundRequest.getUploadInitiativeMoveInboundRequestBody().getCode());
        order.setSlipCode(uploadInitiativeMoveInboundRequest.getUploadInitiativeMoveInboundRequestBody().getSlipCode());
        order.setCreateTime(new Date());
        order.setOuId(uploadInitiativeMoveInboundRequest.getUploadInitiativeMoveInboundRequestBody().getUniqCode());
        order.setStatus(DefaultStatus.CREATED);
        order.setType(PdaOrderType.InnerMove);
        order.setIsPDA(true);
        order = pdaOrderDao.save(order);
        List<Inventory> inboundList = uploadInitiativeMoveInboundRequest.getUploadInitiativeMoveInboundRequestBody().getInbound().getLibraryMovementInOutDetails();
        if (inboundList != null && inboundList.size() > 0) {
            for (int i = 0; i < inboundList.size(); i++) {
                Inventory inv = inboundList.get(i);
                PdaOrderLine pl = new PdaOrderLine();
                pl.setDirection(TransactionDirection.INBOUND);
                pl.setSkuCode(inv.getSkuBarCode());
                pl.setQty(Long.parseLong(inv.getQty()));
                pl.setLocationCode(inv.getLocation());
                pl.setInvStatus(inv.getInvStatus());
                pl.setPdaOrder(order);
                pdaOrderLineDao.save(pl);
            }
        } else {
            throw new BusinessException("");
        }
    }

    @Override
    public PdaOrder saveUploadInitativeMoveOutBound(UploadInitiativeMoveOutboundRequest uploadInitiativeMoveOutboundRequest) {
        PdaOrder order = new PdaOrder();
        try {
            order.setSlipCode(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getSlipCode());
            order.setOuId(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUniqCode());
            order.setUserName(uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getUserName());
            order.setCreateTime(new Date());
            order.setStatus(DefaultStatus.CREATED);
            order.setType(PdaOrderType.INITIATIVEMOVEOUT);
            order = pdaOrderDao.save(order);
            List<Inventory> outBoundList = uploadInitiativeMoveOutboundRequest.getUploadInitiativeMoveOutboundRequestBody().getOutbound().getLibraryMovementInOutDetails();
            if (outBoundList != null && outBoundList.size() > 0) {
                for (int i = 0; i < outBoundList.size(); i++) {
                    Inventory inv = outBoundList.get(i);
                    PdaOrderLine pl = new PdaOrderLine();
                    pl.setDirection(TransactionDirection.OUTBOUND);
                    pl.setSkuCode(inv.getSkuBarCode());
                    pl.setQty(Long.parseLong(inv.getQty()));
                    pl.setLocationCode(inv.getLocation());
                    pl.setInvStatus(inv.getInvStatus());
                    pl.setPdaOrder(order);
                    pdaOrderLineDao.save(pl);
                }
            }
        } catch (NumberFormatException e) {
            throw new BusinessException("传入数据类型有问题！");
        } catch (Exception e) {
            throw new BusinessException("执行失败，PDA上传数据未保存！");
        }
        return order;

    }

    @Override
    public void executeInitiative(PdaOrder pdaOrder) {
        Long ouId = null;
        try {
            ouId = Long.valueOf(pdaOrder.getOuId());
        } catch (Exception e) {
            throw new BusinessException("pda order warehouse info error！:" + pdaOrder.getOuId());
        }
        Warehouse wh = warehouseDao.getByOuId(ouId);
        if (wh == null) {
            throw new BusinessException("pda order warehouse info error！not found:" + pdaOrder.getOuId());
        }
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        List<PdaOrderLine> pdaLine = pdaOrderLineDao.findLineByPdaOrderId(pdaOrder.getId());
        if (pdaLine == null || pdaLine.size() < 1) {
            throw new BusinessException("pda order line info error！");
        }
        Map<String, InventoryStatus> invStatusMap = new HashMap<String, InventoryStatus>();
        // 合并PDA明细行，根据商品，库位，库存状态进行合并
        Map<String, StvLine> inMap = new HashMap<String, StvLine>();
        Long cusromerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        for (int i = 0; i < pdaLine.size(); i++) {
            if (pdaLine.get(i).getQty() == null || pdaLine.get(i).getQty() == 0) {
                continue;
            }
            StvLine sl = new StvLine();
            com.jumbo.wms.model.baseinfo.Sku sku = skuDao.getByBarcode(pdaLine.get(i).getSkuCode(), cusromerId);
            if (sku == null) {
                SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(pdaLine.get(i).getSkuCode(), customerId);
                if (addSkuCode == null) {
                    throw new BusinessException("PDA 上传的数据中条形码为" + pdaLine.get(i).getSkuCode() + "的商品不存在");
                } else {
                    sku = addSkuCode.getSku();
                }
            }
            sl.setSku(sku);
            WarehouseLocation loc = warehouseLocationDao.findLocationByCode(pdaLine.get(i).getLocationCode(), wh.getOu().getId());
            if (loc == null) {
                throw new BusinessException("PDA 上传数据中编码为" + pdaLine.get(i).getLocationCode() + "的库位不存在");
            }
            sl.setLocation(loc);
            // 库状态
            InventoryStatus status = inventoryStatusDao.getByPrimaryKey(123L);
            String invkey = pdaLine.get(i).getInvStatus();
            if (invStatusMap.containsKey(invkey)) {
                status = invStatusMap.get(invkey);
            } else {
                status = inventoryStatusDao.findByNameUnionSystem(pdaLine.get(i).getInvStatus(), wh.getOu().getParentUnit().getParentUnit().getId());
                if (status != null) {
                    invStatusMap.put(status.getName(), status);
                }
            }
            if (status == null) {
                throw new BusinessException("PDA 上传数据中库存状态" + pdaLine.get(i).getInvStatus() + "不存在");
            }
            sl.setInvStatus(status);
            sl.setQuantity(pdaLine.get(i).getQty());
            String key = sku.getId() + "_" + status.getId() + loc.getId();
            if (inMap.get(key) == null) {
                inMap.put(key, sl);
            } else {
                StvLine line = inMap.get(key);
                line.setQuantity(line.getQuantity() + pdaLine.get(i).getQty());
            }
        }
        // 查询出库明细行并根据商品id和库存状态进行分组合并
        StockTransApplication sta = staDao.findStaByCode(pdaOrder.getOrderCode());
        // 查询移库出库明细数据
        List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        Map<String, List<StvLineCommand>> tmpMap = new HashMap<String, List<StvLineCommand>>();
        for (StvLineCommand cmd : outStvLine) {
            String key = cmd.getSkuId() + "_" + cmd.getIntInvstatus();
            List<StvLineCommand> val = tmpMap.get(key);
            if (val == null) {
                val = new ArrayList<StvLineCommand>();
            }
            val.add(cmd);
            tmpMap.put(key, val);
        }
        // 根据PDA上传的入库数据和查询到的作业单的出库数据生成需要入库的作业单明细行
        List<StvLineCommand> inLineList = new ArrayList<StvLineCommand>();
        for (StvLine line : inMap.values()) {
            String key = line.getSku().getId() + "_" + line.getInvStatus().getId();
            List<StvLineCommand> val = tmpMap.get(key);
            if (val == null) {
                throw new BusinessException("[SKU:" + line.getSku().getBarCode() + " OUT is not equal to IN !]");
            }
            Long qty = line.getQuantity();
            for (int i = 0; i < val.size(); i++) {
                StvLineCommand stvl = val.get(i);
                StvLineCommand l = new StvLineCommand();
                l.setLocation(line.getLocation());
                l.setLocationId(line.getLocation().getId());
                l.setSku(line.getSku());
                l.setSkuId(line.getSku().getId());
                l.setInvStatus(line.getInvStatus());
                l.setQuantity(line.getQuantity());
                l.setIntInvstatus(line.getInvStatus().getId());
                l.setSkuCost(stvl.getSkuCost());
                l.setBatchCode(stvl.getBatchCode());
                l.setOwner(stvl.getOwner());
                if (stvl.getQuantity() > qty) {
                    l.setQuantity(qty);
                    qty = 0L;
                    stvl.setQuantity(stvl.getQuantity() - qty);
                } else if (stvl.getQuantity().equals(qty)) {
                    l.setQuantity(qty);
                    qty = 0L;
                    val.remove(i--);
                } else {
                    l.setQuantity(stvl.getQuantity());
                    qty -= stvl.getQuantity();
                    val.remove(i--);
                }
                inLineList.add(l);
                if (qty.equals(0L)) break;
            }
        }
        wareHouseManagerExe.executeTransitInner(false, sta.getId(), inLineList, null);
        pdaOrder.setMemo("完成");
        pdaOrder.setStatus(DefaultStatus.FINISHED);
        pdaOrder.setFinishTime(new Date());
        pdaOrderDao.save(pdaOrder);
    }

    @Override
    public Carton generateCartonByStaId(Long id, String index) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        sta.setStatus(StockTransApplicationStatus.PACKING);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        Carton carton = new Carton();
        carton.setSeqNo(index);
        carton.setCode(cartonDao.generateCartonCode(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).toString());
        carton.setCreateTime(new Date());
        carton.setSta(sta);
        carton.setStatus(CartonStatus.CREATED);
        cartonDao.save(carton);
        return carton;
    }

    @Override
    public void packageCartonLine(Long id, List<CartonLineCommand> lines) {
        Carton carton = cartonDao.getByPrimaryKey(id);
        if (carton == null) {
            throw new BusinessException(ErrorCode.CARTON_NOT_FOUND);
        }
        if (carton.getStatus().equals(CartonStatus.FINISH)) {
            throw new BusinessException(ErrorCode.CARTON_IS_FINISHED, new Object[] {carton.getCode()});
        }
        StockTransApplication sta = carton.getSta();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(sta.getMainWarehouse().getId());
        // 打包
        for (CartonLineCommand cmd : lines) {
            log.debug("sku : {}, qty : {}", cmd.getSkuBarcode(), cmd.getQty());
            Sku sku = skuDao.getByBarcode(cmd.getSkuBarcode(), customerId);
            if (sku == null) {
                SkuBarcode addsku = skuBarcodeDao.findByBarCode(cmd.getSkuBarcode(), customerId);
                if (addsku == null) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {cmd.getSkuBarcode()});
                } else {
                    sku = addsku.getSku();
                }
            }
            if (cmd.getQty() == null || cmd.getQty() <= 0) {
                continue;
            }
            CartonLine l = new CartonLine();
            l.setCarton(carton);
            l.setQty(cmd.getQty());
            l.setSku(sku);
            cartonLineDao.save(l);
        }
        // 更新箱执行信息
        carton.setFinishTime(new Date());
        carton.setStatus(CartonStatus.FINISH);
        // 判断是否超出计划量
        cartonLineDao.flush();
        List<CartonLineCommand> errorList = cartonLineDao.findErrorSku(sta.getId(), carton.getId(), new BeanPropertyRowMapperExt<CartonLineCommand>(CartonLineCommand.class));
        if (errorList != null && errorList.size() > 0) {
            BusinessException root = new BusinessException(ErrorCode.CARTON_SKU_ERROR);
            for (CartonLineCommand cmd : errorList) {
                Sku sku = skuDao.getByPrimaryKey(cmd.getSkuId());
                BusinessException be = new BusinessException(ErrorCode.CARTON_SKU_NOT_IN_PLAN, new Object[] {sku.getBarCode(), sku.getCode(), sku.getName(), cmd.getQty()});
                setLinkedBusinessException(root, be);
            }
            throw root;
        }
    }

    @Override
    public void createCartonAndCartonLine(PdaOrder po, StockTransApplication sta, Map<String, List<PdaOrderLine>> map) {
        for (String key : map.keySet()) {
            // 创建箱头
            Carton ct = generateCartonByStaId(sta.getId(), key);
            List<PdaOrderLine> list = map.get(key);
            List<CartonLineCommand> lines = new ArrayList<CartonLineCommand>();
            for (PdaOrderLine pol : list) {
                Sku sku = skuDao.getByCode(pol.getSkuCode());
                if (sku == null) {
                    throw new BusinessException("PDA上传明细行" + pol.getId() + "对应的商品不存在!");
                    // PDA商品不存在
                } else {
                    CartonLineCommand clc = new CartonLineCommand();
                    clc.setSkuBarcode(sku.getBarCode());
                    clc.setQty(pol.getQty());
                    lines.add(clc);
                }
            }
            // 创建CartonLine
            packageCartonLine(ct.getId(), lines);
        }
    }

    @Override
    public void partlyOutBound(PdaOrder po, StockTransApplication sta2) {
        StockTransApplication sta1 = staDao.getByPrimaryKey(sta2.getId());
        if (sta1 == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!sta1.getStatus().equals(StockTransApplicationStatus.PACKING)) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta1.getStatus()});
        }
        List<CartonLineCommand> cl = cartonLineDao.findDiffSku(sta1.getId(), new BeanPropertyRowMapperExt<CartonLineCommand>(CartonLineCommand.class));
        if (cl == null || cl.size() == 0) {
            sta1.setLastModifyTime(new Date());
            sta1.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta1.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta1.getMainWarehouse() == null ? null : sta1.getMainWarehouse().getId());
            staDao.flush();
            // 执行出库
            predefinedOutExecution(po, sta1.getId());
        } else {
            for (CartonLineCommand cmd : cl) {
                if (cmd.getQty() == null || cmd.getQty() < 0) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {"qty < 0"});
                }
            }
            // 部分出库
            StockTransVoucher outStv = stvDao.findStvCreatedByStaId(sta1.getId());
            if (outStv == null) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND);
            }
            // 创建取消STV
            StockTransVoucher stv = new StockTransVoucher();
            BigDecimal bn = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            stv.setBusinessSeqNo(bn.longValue());
            stv.setCode(stvDao.getCode(sta1.getId(), new SingleColumnRowMapper<String>(String.class)));
            stv.setCreateTime(new Date());
            // stv.setCreator(user);
            stv.setDirection(TransactionDirection.OUTBOUND);
            // stv.setOperator(user);
            stv.setOwner(sta1.getOwner());
            stv.setSta(sta1);
            stv.setLastModifyTime(new Date());
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stv.setTransactionType(outStv.getTransactionType());
            stv.setWarehouse(outStv.getWarehouse());
            stvDao.save(stv);
            stvDao.flush();
            // 调用SP 修改库存占用
            // Map<String, Object> params = new HashMap<String, Object>();
            // params.put("in_sta_id", sta.getId());
            // params.put("in_stv_id", stv.getId());
            // params.put("in_transtype_id", stv.getTransactionType().getId());
            // SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
            // SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), new
            // SqlParameter("in_stv_id", Types.NUMERIC), new SqlParameter("in_transtype_id",
            // Types.NUMERIC), s};
            // Map<String, Object> result = staDao.executeSp("sp_rm_occ_inv_for_out", sqlParameters,
            // params);
            // String errorSku = (String) result.get("error_sku_id");
            // 代码修改占用库存 占用到库位
            // 1、获取部分出库的库存
            List<InventoryCommand> invList = inventoryDao.getPartlyOutboundInventory(po.getId(), sta1.getId(), Long.parseLong(po.getOuId()), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            Map<String, Long> map = new HashMap<String, Long>();
            for (InventoryCommand ic : invList) {
                String key = ic.getSkuCode() + ic.getLocationCode();
                if (map.get(key) == null) {
                    map.put(key, ic.getChangeQty());
                    // 如果当前库存分数量小于差异量，释放当前库存份的占用，创建取消明细行，减少差异量
                    if (ic.getQuantity() <= ic.getChangeQty()) {
                        inventoryDao.updateInventoryById(ic.getId(), null);// 释放库存占用
                        stvLineDao.insertNewStvLine(stv.getId(), stv.getTransactionType().getId(), ic.getId());
                        Long changerQty = map.get(key) - ic.getQuantity();
                        map.put(key, changerQty);
                    } else {
                        // 如果当前库存分数量大于差异量，则拆分库存分，更新原始占用量 插入新的库存分，差异量变为0
                        inventoryDao.updateInventoryById(ic.getId(), map.get(key));
                        inventoryDao.insertNewInventory(ic.getId(), ic.getQuantity() - map.get(key));
                        stvLineDao.insertNewStvLine(stv.getId(), stv.getTransactionType().getId(), ic.getId());
                        map.put(key, 0L);
                    }
                } else {
                    if (map.get(key) >= 0L) {
                        // 如果当前库存分数量小于差异量，释放当前库存份的占用，创建取消明细行，减少差异量
                        if (ic.getQuantity() <= ic.getChangeQty()) {
                            inventoryDao.updateInventoryById(ic.getId(), null);// 释放库存占用
                            stvLineDao.insertNewStvLine(stv.getId(), stv.getTransactionType().getId(), ic.getId());
                            Long changerQty = map.get(key) - ic.getQuantity();
                            map.put(key, changerQty);
                        } else {
                            // 如果当前库存分数量大于差异量，则拆分库存分，更新原始占用量 插入新的库存分，差异量变为0
                            inventoryDao.updateInventoryById(ic.getId(), map.get(key));
                            inventoryDao.insertNewInventory(ic.getId(), ic.getQuantity() - map.get(key));
                            stvLineDao.insertNewStvLine(stv.getId(), stv.getTransactionType().getId(), ic.getId());
                            map.put(key, 0L);
                        }
                    }
                }
            }
            // 循环判断库存
            String errorSku = inventoryDao.selectErrorSku(sta1.getId(), stv.getId(), new SingleColumnRowMapper<String>(String.class));
            BusinessException root = null;
            if (StringUtils.hasText(errorSku)) {
                String[] skus = errorSku.split(",");
                for (String str : skus) {
                    String[] strs = str.split("/");
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
            } else {
                // 删除原始的stv明细，这时候剩余一个outStv 和stv及其明细
                // 然后根据库存占用情况创建新的outStv的明细（不占用的已经释放了）
                stvLineDao.deleteStvLineByStvId(outStv.getId());
                stvLineDao.createByStaId(sta1.getId());
                // 完成 stv
                outStv.setStatus(StockTransVoucherStatus.FINISHED);
                outStv.setLastModifyTime(new Date());
                outStv.setFinishTime(new Date());
                // outStv.setOperator(user);
                stvDao.save(stv);
                sta1.setIsNeedOccupied(false);
                // sta.setOutboundOperator(user);
                sta1.setOutboundTime(new Date());
                sta1.setInboundTime(new Date());
                sta1.setFinishTime(new Date());
                sta1.setLastModifyTime(new Date());
                sta1.setStatus(StockTransApplicationStatus.FINISHED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta1.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), null, sta1.getMainWarehouse() == null ? null : sta1.getMainWarehouse().getId());
                staDao.save(sta1);
                staDao.flush();
                staLineDao.updateCompleteQuantityByStaId(sta1.getId());
                // 出库 计算成本
                // 计算成本
                staLineDao.updateSkuCostBySta(sta1.getId(), sta1.getMainWarehouse().getParentUnit().getParentUnit().getId());
                // 记录SN日志
                snLogDao.createTransitCrossLogByStvIdSql(outStv.getId());
                // 删除sn号
                snDao.deleteSNByStvIdSql(outStv.getId());
                // 移除占用库存
                wareHouseManager.removeInventory(sta1, outStv);
                // 判断前置单据状态是否正确
                eventObserver.onEvent(new TransactionalEvent(sta1));
                // if (StockTransApplicationType.OUTBOUND_PURCHASE.equals(sta1.getType()) &&
                // StockTransApplicationStatus.FINISHED.equals(sta1.getStatus())) {
                // // 注册k3任务
                // poOutTaskRegister.registTask(outStv);
                // }
            }
        }
    }

    public void predefinedOutExecution(PdaOrder po, Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.OUTBOUND);
        if (stvList.size() != 1) {
            throw new BusinessException(ErrorCode.TRANIST_CROSS_STV_LINE_EMPTY);
        }
        Date temp = new Date();
        StockTransVoucher stv = stvList.get(0);
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        stv.setFinishTime(temp);
        stv.setLastModifyTime(new Date());
        stvDao.save(stv);
        sta.setIsNeedOccupied(false);
        sta.setOutboundTime(temp);
        sta.setInboundTime(new Date());
        sta.setFinishTime(temp);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        staDao.save(sta);
        staDao.flush();
        staLineDao.updateCompleteQuantityByStaId(sta.getId());
        // 计算成本
        staLineDao.updateSkuCostBySta(staId, sta.getMainWarehouse().getParentUnit().getParentUnit().getId());
        // 占用sn号
        snDao.updateSnByOuAndStv(stv.getId(), Long.parseLong(po.getOuId()), po.getId());
        // 记录SN日志
        snLogDao.createTransitCrossLogByStvIdSql(stv.getId());
        // 删除sn号
        snDao.deleteSNByStvIdSql(stv.getId());
        // 移除占用库存
        wareHouseManager.removeInventory(sta, stv);
        // 判断前置单据状态是否正确
        eventObserver.onEvent(new TransactionalEvent(sta));
        // if (StockTransApplicationType.OUTBOUND_PURCHASE.equals(sta.getType()) &&
        // StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
        // // 注册k3任务
        // poOutTaskRegister.registTask(stv);
        // }
    }

    @Override
    public void executeOutBound(PdaOrder po, StockTransApplication sta, Map<String, List<PdaOrderLine>> map) {
        createCartonAndCartonLine(po, sta, map);
        partlyOutBound(po, sta);
    }

    @Override
    public void executeInventoryCheck(InventoryCheck ic, Map<String, Long> importData) {
        ic = inventoryCheckDao.findByCode(ic.getCode());
        List<InventoryCheckDifferenceLine> dlist = inventoryCheckDifferenceLineDao.findByInventoryCheck(ic.getId());
        for (InventoryCheckDifferenceLine l : dlist) {
            inventoryCheckDifferenceLineDao.delete(l);
        }
        excelReadManager.margenIncentoryCheckDifLine(ic, importData);
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        inventoryCheckDao.save(ic);
    }

    @Override
    public void occupiedSn(Long id, Long id2) {
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(id, TransactionDirection.OUTBOUND);
        if (stvs == null || stvs.size() != 1) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher stv = stvs.get(0);
        snDao.updateOutBoundSnRevertByStv(stv.getId());
        List<String> sns = pdaOrderLineSnDao.findSnByPdaOrderId(id2, new SingleColumnRowMapper<String>(String.class));
        for (String sn : sns) {
            snDao.updateStatusBySn(sn, SkuSnStatus.CHECKING.getValue(), stv.getId());
        }
    }

    @Override
    public List<PdaHandOverLine> findPdaHandOverLogLine(Long id, Sort[] sorts) {
        return pdaHandOverLineDao.findPdaHandOverLogLine(id, sorts, new BeanPropertyRowMapper<PdaHandOverLine>(PdaHandOverLine.class));
    }

    @Override
    public Pagination<PdaOrder> getErrorPdaLogList(int start, int pageSize, Date date, Date date2, String orderCode, Long ouId, Sort[] sorts) {
        String code = null;
        if (StringUtils.hasText(orderCode)) {
            code = orderCode;
        }
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(DefaultStatus.CANCELED.getValue());
        List<Integer> typeList = new ArrayList<Integer>();
        typeList.add(PdaOrderType.Inbound.getValue());
        // typeList.add(PdaOrderType.INBOUND_SHELEVES.getValue());
        typeList.add(PdaOrderType.InnerMove.getValue());
        return pdaOrderDao.getErrorPdaLogList(start, pageSize, date, date2, code, statusList, typeList, ouId.toString(), sorts, new BeanPropertyRowMapper<PdaOrder>(PdaOrder.class));
    }

    @Override
    public void updatePdaOrderStatus(Long id) {
        pdaOrderDao.updateStatusById(id);
    }

    @Override
    public List<PdaOrderLine> getErrorPdaLogDetailList(Long id, Sort[] sorts) {
        return pdaOrderLineDao.getErrorPdaLogDetailList(id, sorts, new BeanPropertyRowMapper<PdaOrderLine>(PdaOrderLine.class));
    }

    @Override
    public void updateErrorPdaLineLocation(Long id, String orderCode) {
        pdaOrderLineDao.updateErrorPdaLineLocation(id, orderCode);
    }


}
