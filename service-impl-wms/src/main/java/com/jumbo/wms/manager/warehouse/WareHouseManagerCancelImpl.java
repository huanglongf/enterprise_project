package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.bizhub.manager.platform.taobao.TaobaoManager;
import com.baozun.bizhub.model.taobao.GeneralImportsCancelRequest;
import com.baozun.bizhub.model.taobao.GeneralImportsCancelResponse;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.automaticEquipment.WhPickingBatchDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.MsgOrderCancelDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.cj.CjLgOrderCodeUrlDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.CartonLineDao;
import com.jumbo.dao.warehouse.HandOverListDao;
import com.jumbo.dao.warehouse.HandOverListLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.OutBoundPackDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PdaPostLogDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SkuImperfectDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuWarehouseRefDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhStaPickingListLogDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.webservice.biaogan.clientNew2.PushExpressInfoPortTypeClientNew2;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.pda.PdaPickingManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsRequest.OQuxiaoBoZhong;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsRequest.OQuxiaoRongQi;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.hub2wms.MsgOrderCancel;
import com.jumbo.wms.model.mongodb.MongDbNoThreeDimensional;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.cj.CjLgOrderCodeUrl;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListLine;
import com.jumbo.wms.model.warehouse.HandOverListStatus;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoStatus;
import com.jumbo.wms.model.warehouse.PdaPostLog;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BaseRowMapper;

@Transactional
@Service("wareHouseManagerCancel")
public class WareHouseManagerCancelImpl extends BaseManagerImpl implements WareHouseManagerCancel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -9210064463535107109L;
    protected static final Logger logger = LoggerFactory.getLogger(WareHouseManagerCancelImpl.class);
    private EventObserver eventObserver;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WhBoxInboundManager whBoxInboundManager;
    @Autowired
    private HandOverListLineDao handOverListLineDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HandOverListDao handOverListDao;
    @Autowired
    private OutBoundPackDao outBoundPackDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private MsgOrderCancelDao msgOrderCancelDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private SkuWarehouseRefDao skuWarehouseRefDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsDao wmsOtherOutBoundInvNoticeOmsDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private SkuImperfectDao imperfectDao;
    @Autowired
    private PdaPostLogDao pdaPostLogDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private CartonLineDao cartonLineDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WhContainerDao whContainerDao;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private WhPickingBatchDao whPickingBatchDao;
    @Autowired
    private WhStaPickingListLogDao pickingListLogDao;
    @Autowired
    private TaobaoManager taobaoManager;
    @Autowired
    private CjLgOrderCodeUrlDao cjLgOrderCodeUrlDao;
    @Autowired
    private PdaPickingManager pdaPickingManager;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:WareHouseManagerImpl");
        }
    }

    @Override
    public void cancelCartonStaOfNew(Long id) {
        if (id != null) {
            StockTransApplication sta = staDao.getByPrimaryKey(id);
            Long staId = sta.getGroupSta().getId();
            List<StaLine> sl = staLineDao.findByStaId(sta.getId());
            staLineDao.deleteAll(sl);
            staDao.delete(sta);
            staDao.flush();
            // 关于单据状态，如果存在未完成子单，主单冻结，否则根据单据实际执行状态更新单据为新建或部分入库
            // 如果按箱收货作业单新建都取消，则把rootsta设置成新建状态
            List<StockTransApplicationCommand> cartonStaList = staDao.findCartonStaListByStaIdSql(staId, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
            boolean isForzen = false;
            for (StockTransApplicationCommand c : cartonStaList) {
                if (c.getIntStaStatus() == StockTransApplicationStatus.CREATED.getValue() || c.getIntStaStatus() == StockTransApplicationStatus.PARTLY_RETURNED.getValue()) {
                    isForzen = true;
                    break;
                }
            }
            if (!isForzen) {
                boolean isInbound = false;
                List<StaLineCommand> sll = whBoxInboundManager.findBoxReceiveStaLine(staId, new Sort[] {new Sort("id")});
                for (StaLineCommand sc : sll) {
                    if (sc.getCompleteQuantity() > 0) {
                        isInbound = true;
                        break;
                    }
                }
                staDao.updateStaStatusByStaIdAndStatus(staId, isInbound ? StockTransApplicationStatus.PARTLY_RETURNED.getValue() : StockTransApplicationStatus.CREATED.getValue());
            }
        }
    }

    /**
     * 取消交接单行
     */
    public Boolean cancelHandoverListLine(Long hoListLineId, Long userId, Long wId) {
        HandOverListLine line = handOverListLineDao.getByPrimaryKey(hoListLineId);
        if (line == null) {
            throw new BusinessException(ErrorCode.HAND_OVER_LIST_LINE_NOT_FOUND);
        }
        if (DefaultStatus.FINISHED.getValue() == line.getHoList().getStatus().getValue()) {
            throw new BusinessException(ErrorCode.HAND_OVER_LIST_IS_FINISHED, new Object[] {line.getHoList().getCode()});
        }
        User user = userDao.getByPrimaryKey(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 删除一条中间数据
        Long id = line.getId();
        PackageInfo p = packageInfoDao.findPgByLineId(id, new BeanPropertyRowMapper<PackageInfo>(PackageInfo.class));
        outBoundPackDao.deleteOneOutBoundPack(p.getId());
        outBoundPackDao.flush();
        // //////////
        Long handId = line.getHoList().getId();
        handOverListLineDao.deleteByPrimaryKey(line.getId());
        handOverListLineDao.flush();
        reCountHandOverList(handId);
        handOverListDao.flush();
        HandOverList hl = handOverListDao.getByPrimaryKey(handId);
        if (hl.getPackageCount() == 0) {
            pickingListDao.updatePickingListByHandOverList(handId);
            handOverListDao.deleteByPrimaryKey(handId);
            // 删除交接清单仓库列表
            handOverListDao.deleteByHoIdWareList(handId, wId);
            return false;
        }
        return true;
    }

    // 计算交货清单中包裹数与重量
    private void reCountHandOverList(Long hoListId) {
        HandOverList ho = handOverListDao.getByPrimaryKey(hoListId);
        BigDecimal weight = new BigDecimal(0);
        BigDecimal pgCount = new BigDecimal(0);

        List<PackageInfo> pgList = packageInfoDao.findPgByHoId(hoListId, new BeanPropertyRowMapper<PackageInfo>(PackageInfo.class));
        for (PackageInfo l : pgList) {
            weight = weight.add(l.getWeight());
            pgCount = pgCount.add(new BigDecimal(1));
        }
        ho.setTotalWeight(weight);
        ho.setPackageCount(pgCount.intValue());
    }

    /**
     * 取消物流交接单
     */
    public void cancelHandOverList(Long hoId, Long userId, Long wId) {
        HandOverList ho = handOverListDao.getByPrimaryKey(hoId);
        if (ho == null) {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
        }
        pickingListDao.updatePickingListByHandOverList(hoId);
        // handOverListLineDao.deleteByHandId(ho.getId());
        handOverListDao.deleteByPrimaryKey(ho.getId());
        // 删除交接清单仓库列表
        handOverListDao.deleteByHoIdWareList(ho.getId(), wId);
        // 删除用户出库包裹
        List<PackageInfo> pgList = packageInfoDao.findPgByHoId(ho.getId(), new BeanPropertyRowMapper<PackageInfo>(PackageInfo.class));
        for (PackageInfo packageInfo : pgList) {// 批量删除
            outBoundPackDao.deleteOneOutBoundPack(packageInfo.getId());
        }
        outBoundPackDao.flush();
        handOverListLineDao.deleteByHandId(ho.getId());
        handOverListLineDao.flush();
    }

    /**
     * 根据sta删除其中未完成的包裹和交接单明细
     * 
     * @param staId
     */
    private void cancelHoListLineByStaId(Long staId) {
        List<Long> handId = handOverListDao.findHandOverListByStaId(staId, PackageInfoStatus.OUTBOUND.getValue(), new SingleColumnRowMapper<Long>(Long.class));
        handOverListLineDao.deleteHandOverListLineByStaId(staId, PackageInfoStatus.OUTBOUND.getValue());
        handOverListLineDao.flush();
        // 重新计算HandOverList;
        for (Long id : handId) {
            reCountHandOverList(id);
            HandOverList handOver = handOverListDao.getByPrimaryKey(id);
            if (handOver.getBillCount() == 0) {
                handOverListDao.deleteByPrimaryKey(id);
            }
        }
    }

    public void modifyTransport(Long staId, Long userId, String lpcode, List<PackageInfo> pgList, Long wId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        User user = userDao.getByPrimaryKey(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if (sta.getStatus().getValue() != StockTransApplicationStatus.INTRANSIT.getValue()) {
            throw new BusinessException(ErrorCode.MODIFT_TRANS_STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 查询作业单包裹是否存在已经交接的；存在则不允许更改物流商，否则允许修改
        List<Long> packid = packageInfoDao.findFinishPackBySta(sta.getId(), HandOverListStatus.FINISHED.getValue(), new SingleColumnRowMapper<Long>(Long.class));
        if (!packid.isEmpty() && packid.size() > 0) {
            if (!sta.getStaDeliveryInfo().getLpCode().equals(lpcode)) {
                // 存在分包出库，不允许修改物流商
                throw new BusinessException(ErrorCode.FORBIDDEN_CHANGE);
            }
        }
        // 删除sta关联的交接明细
        cancelHoListLineByStaId(staId);
        // outBoundPackDao.deleteInfoByStaAndPackage(sta.getId(),
        // PackageInfoStatus.OUTBOUND.getValue());
        // 根据作业单获取出库包裹里的快递单号，默认删除
        Map<String, Long> trackMap = outBoundPackDao.findTrankNoByStaId(sta.getId(), new BaseRowMapper<Map<String, Long>>() {
            private Map<String, Long> result = new HashMap<String, Long>();

            public Map<String, Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
                String trackingNo = getResultFromRs(rs, "trackingNo", String.class);
                Long id = getResultFromRs(rs, "id", Long.class);
                result.put(trackingNo, id);
                return result;
            }
        });
        // 移除sta关联物流运单号,创建包裹信息
        packageInfoDao.deleteByStaIdAndStatus(sta.getId(), PackageInfoStatus.OUTBOUND.getValue());
        packageInfoDao.flush();
        String trackingNo = "";
        for (PackageInfo pg : pgList) {
            // 判断原快递单号和新增的是否一直，保留一致的，其余的删除。
            // if (trackMap != null) {
            // if (trackMap.containsKey(pg.getTrackingNo())) {
            // trackMap.remove(pg.getTrackingNo());
            // }
            // }
            if (!StringUtils.hasText(pg.getTrackingNo())) {
                throw new BusinessException(ErrorCode.PACKAGE_INFO_NO_ENOUGHT_MESSAGE);
            }
            if (pg.getWeight() == null) {
                throw new BusinessException(ErrorCode.PACKAGE_INFO_NO_ENOUGHT_MESSAGE);
            }
            if (trackingNo.equals("")) {
                trackingNo = pg.getTrackingNo();
            } else {
                trackingNo = trackingNo + "," + pg.getTrackingNo();
            }
            packageInfoDao.newPackageInfo(lpcode, pg.getTrackingNo(), pg.getWeight(), sta.getId(), true, PackageInfoStatus.OUTBOUND.getValue(), wId);
        }
        // 删除出库操作快递单号记录
        if (trackMap != null) {
            for (Long id : trackMap.values()) {
                outBoundPackDao.deleteByPrimaryKey(id);
            }
        }
        staDeliveryInfoDao.updateByPrimaryKey(sta.getId(), lpcode, pgList.get(0).getTrackingNo());
        if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
            try {
                if (!StringUtils.hasText(sta.getSystemKey())) {
                    rmi4Wms.wmsactualTrans(sta.getRefSlipCode(), lpcode, trackingNo);
                } else {
                    if (sta.getStatus().getValue() == 17) {
                        MsgOrderCancel orderCancel = new MsgOrderCancel();
                        orderCancel.setCreateTime(new Date());
                        orderCancel.setIsCanceled(true);
                        orderCancel.setMsg("物流不可达");
                        orderCancel.setSource(sta.getStaDeliveryInfo().getLpCode());
                        orderCancel.setStaCode(sta.getCode());
                        orderCancel.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
                        orderCancel.setUpdateTime(new Date());
                        orderCancel.setSystemKey(sta.getSystemKey());
                        msgOrderCancelDao.save(orderCancel);
                    }
                }
            } catch (Exception e) {
                log.error("error when connect oms to wmsCreateStaFeedback");
                log.error("", e);
                // 接口异常
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 取消调整单
     * 
     * @param invCkId
     * @param userId
     */
    public void cancelInvCheck(Long invCkId, Long userId) {
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.VMI_ADJUST_NOT_FOUND);
        }
        if (InventoryCheckStatus.CANCELED.equals(ic.getStatus()) || InventoryCheckStatus.FINISHED.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.VMI_ADJUST_HAS_FINISH, new Object[] {ic.getCode()});
        }
        User user = userDao.getByPrimaryKey(userId);
        // 取消盘点批
        ic.setStatus(InventoryCheckStatus.CANCELED);
        ic.setCancelUser(user);
        inventoryCheckDao.save(ic);
        // 取消相关调整单
        whExe.cancelAdjustingSta(ic.getId(), user);

        // 释放库存
        inventoryDao.updateOccupyByCode(ic.getCode());
        // 通知IM
        hubWmsService.insertOccupiedAndReleaseByCheck(ic.getId());

    }

    public void cancelInventoryCheck(Long invCkId, Long userId) {
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!(InventoryCheckStatus.CREATED.equals(ic.getStatus()) || InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus()))) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_FINISHED, new Object[] {ic.getCode()});
        }
        User user = userDao.getByPrimaryKey(userId);
        // 取消盘点批
        ic.setStatus(InventoryCheckStatus.CANCELED);
        ic.setCancelUser(user);
        inventoryCheckDao.save(ic);
        // 取消相关调整单
        whExe.cancelAdjustingSta(ic.getId(), user);
        // 解锁库位
        warehouseLocationDao.unLockByInvCheck(ic.getId());
    }

    public void cancelInventoryCheckManager(Long invCkId, User user) {
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!InventoryCheckStatus.CHECKWHINVENTORY.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_FINISHED, new Object[] {ic.getCode()});
        }
        // 取消盘点批
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        inventoryCheckDao.save(ic);
        // 取消调整单处理
        whExe.cancelAdjustingSta(ic.getId(), user);
    }

    public void cancelInvStatusChangeSta(Long staId, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORT_STATUS_CHANGE_CALCEL_ERROR, new Object[] {sta.getCode()});
        }
        inventoryDao.releaseInventoryByStaId(staId);
        // 取消stv
        List<StockTransVoucher> list = stvDao.findStvCreatedListByStaId(sta.getId());
        for (StockTransVoucher stv : list) {
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stv.setLastModifyTime(new Date());
            stvDao.save(stv);
        }
        // 更新sta状态，取消数量占用
        sta.setCancelTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CANCELED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        hubWmsService.insertOccupiedAndRelease(staId);
        // 取消调用OMS接口
        if (!org.apache.commons.lang3.StringUtils.equals(sta.getSystemKey(), Constants.CAINIAO_DB_SYSTEM_KEY)) {
            try {
                log.debug("Call oms order cancel interface------------------->BEGIN");
                BaseResult baseResult = rmi4Wms.cancelOperationBill(sta.getRefSlipCode(), sta.getType().getValue());
                if (baseResult.getStatus() == 0) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                }
                log.debug("Call oms order cancel interface------------------->END");
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
            }
        }
        staDao.save(sta);
    }

    public void cancelOthersStaInOutbound(Long staId, Long userId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(staId);
        User user = userDao.getByPrimaryKey(userId);
        isHaveException(sta, stv, user, userId);
        if (TransactionDirection.INBOUND.getValue() == stv.getTransactionType().getDirection().getValue()) {
            // 入库
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setLastModifyTime(new Date());
            sta.setInboundOperator(user);
            staDao.save(sta);
            snDao.deleteSNByStvIdSql(stv.getId());
        } else {
            // 出库
            sta.setCancelTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setIsNeedOccupied(false);
            sta.setOutboundOperator(user);
            staDao.save(sta);
            releaseInventoryByStaId(staId, userId, null);
            // 其他出库取消库存占用明细通知oms/pac
            WmsOtherOutBoundInvNoticeOms wto = wmsOtherOutBoundInvNoticeOmsDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
            if (wto != null) {
                wmsOtherOutBoundInvNoticeOmsDao.updateOtherOutBoundInvNoticeOmsByStaCode(sta.getCode(), 17l);
            }
        }
    }

    public void releaseInventoryByStaId(Long staId, Long userId, String slipCode) {
        StockTransApplication sta = null;
        if (staId != null) {
            sta = staDao.getByPrimaryKey(staId);
        } else {
            sta = staDao.findStaBySlipCode(slipCode);
        }
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        // sta状态检查
        if (!StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR, new Object[] {sta.getCode()});
        }
        staId = sta.getId();
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(staId);
        if (stv == null) {
            List<Inventory> invList = inventoryDao.findByOccupiedCode(sta.getCode());
            if (invList != null && invList.size() != 0) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND);
            } else {
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                return;
            }
        }
        // 是否合并单
        if (null != sta.getIsMerge() && true == sta.getIsMerge()) {// 合并单
            // 设置合单情况下，释放库存，子单状态修改
            List<StockTransApplication> list = staDao.findGroupStaList(sta.getId());
            if (list != null && list.size() > 0) {
                for (StockTransApplication bean : list) {
                    if (StockTransApplicationStatus.CANCELED.equals(bean.getStatus()) || StockTransApplicationStatus.CREATED.equals(bean.getStatus())) {
                        continue;
                    }
                    int i = inventoryDao.releaseInventoryByStaId(bean.getId());// 释放子作业单占用的库存
                    if (i == 0) {
                        throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {sta.getCode()});
                    }
                    // 取消 stv
                    StockTransVoucher stvBean = stvDao.findStvCreatedByStaId(bean.getId());
                    if (stvBean == null) {
                        throw new BusinessException(ErrorCode.STV_NOT_FOUND);
                    }
                    if (null != bean.getResetToCreate() && bean.getResetToCreate()) {
                        stvDao.delete(stvBean);
                        bean.setStatus(StockTransApplicationStatus.CREATED);
                        bean.setLastModifyTime(new Date());
                        bean.setIsNeedOccupied(true);
                        bean.setGroupSta(null);
                        bean.getStaDeliveryInfo().setTrackingNo(null);
                        whInfoTimeRefDao.insertWhInfoTime(bean.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId);
                    } else {
                        stvBean.setStatus(StockTransVoucherStatus.CANCELED);
                        stvBean.setLastModifyTime(new Date());
                        stvDao.save(stvBean);
                        // 更新sta状态，取消数量占用
                        bean.setStatus(StockTransApplicationStatus.CANCELED);
                        bean.setLastModifyTime(new Date());
                        bean.setIsNeedOccupied(false);
                        staDao.save(bean);
                        whInfoTimeRefDao.insertWhInfoTime(bean.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId);
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.CHILD_STA_NOT_FOUND, new Object[] {sta.getCode()});
            }
        } else {// 非合并单
            int i = inventoryDao.releaseInventoryByStaId(staId);
            if (i == 0) {
                throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {sta.getCode()});
            }
        }

        // 取消stv
        stv.setStatus(StockTransVoucherStatus.CANCELED);
        stv.setLastModifyTime(new Date());
        stvDao.save(stv);
        // 更新sta状态，取消数量占用
        sta.setStatus(StockTransApplicationStatus.CANCELED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setLastModifyTime(new Date());
        sta.setIsNeedOccupied(false);
        staDao.save(sta);
        // 重置sn号
        snDao.updateSNStatusByStvIdSql(stv.getId(), SkuSnStatus.USING.getValue());
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    public void cancelStv(Long stvId, Long userId) {
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);

        if (stv == null) return;
        List<SkuImperfect> imperfects = imperfectDao.findSkuImperfectStaId(stv.getSta().getId(), new BeanPropertyRowMapper<SkuImperfect>(SkuImperfect.class));
        for (SkuImperfect skuImperfect : imperfects) {
            imperfectDao.delete(skuImperfect);
        }
        if (!StockTransVoucherStatus.CREATED.equals(stv.getStatus())) {
            throw new BusinessException(ErrorCode.STV_STATUS_ERROR);
        }
        staDao.updateStaIsPDAByStv(stvId);
        snDao.deleteSNByStvIdSql(stvId);
        deleteSNByStv(stv);
        stvDao.delete(stv);
        if (stv.getIsPda() != null && stv.getIsPda()) {
            // 删除pda post日志
            pdaPostLogDao.deleteAllLogByCode(stv.getSta().getCode(), userId);
        }
    }

    /**
     * 取消STV的时候删除SN
     * 
     * @param stv
     */
    private void deleteSNByStv(StockTransVoucher stv) {
        snDao.deleteSNByStvIdSql(stv.getId());
    }

    public void cancelPickingList(Long pickingListId) {
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        if (pl == null) {
            throw new BusinessException(ErrorCode.PICKING_LIST_NOT_FOUND);
        }
        if (PickingListStatus.WAITING.equals(pl.getStatus())) {
            for (StockTransApplication sta : pl.getStaList()) {
                sta.setPickingList(null);
                staDao.save(sta);
            }
            pl.setStaList(null);
            pickingListDao.delete(pl);
        } else {
            throw new BusinessException(ErrorCode.PICKING_LIST_CANCEL_ERROR, new Object[] {pl.getCode()});
        }
    }

    /**
     * 取消装箱
     */
    @Override
    public void cancelReturnPacking(Long staId) {
        if (staId == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        if (sta.getStatus().getValue() != StockTransApplicationStatus.PACKING.getValue()) {
            throw new BusinessException(ErrorCode.CURRENT_STA_IS_NOT_PACKING, new Object[] {sta.getCode()});
        }
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        List<Carton> cartons = cartonDao.findCartonByStaId(staId);
        for (Carton ct : cartons) {
            Carton carton = cartonDao.getByPrimaryKey(ct.getId());
            if (null != carton) {
                cartonLineDao.deleteByCartonId(carton.getId());
                cartonDao.delete(carton);
            }
        }
    }

    public void cancelSalesStaBySoCode(String soCode) {
        List<StockTransApplication> stas = staDao.findBySlipCodeByType(soCode, StockTransApplicationType.OUTBOUND_SALES);
        for (StockTransApplication sta : stas) {
            if (!sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES)) {
                throw new BusinessException(ErrorCode.STA_TYPE_ERROR);
            }
            if (!(sta.getStatus().equals(StockTransApplicationStatus.CANCELED) || sta.getStatus().equals(StockTransApplicationStatus.CANCEL_UNDO))) {
                cancelSalesSta(sta.getId(), null);
            }
        }
    }

    public void cancelSalesSta(Long staId, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus())) {
            // 新建与配货失败 直接取消已处理，取消数量占用
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setIsNeedOccupied(false);
        } else if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()) || StockTransApplicationStatus.CHECKED.equals(sta.getStatus())) {
            // 配货中、已核对 取消未处理
            sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        } else {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode(), sta.getStatus()});
        }
        // 如是退货取消，则作废换货出的订单
        if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            StockTransApplication outSta = staDao.getStaByOwner(sta.getOwner(), sta.getRefSlipCode(), StockTransApplicationType.OUTBOUND_RETURN_REQUEST, StockTransApplicationStatus.CREATED);
            if (outSta != null) {
                outSta.setLastModifyTime(new Date());
                outSta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(outSta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, outSta.getMainWarehouse() == null ? null : outSta.getMainWarehouse().getId());
                outSta.setCancelTime(new Date());
                staDao.save(outSta);
            }
        }
        sta.setLastModifyTime(new Date());
        sta.setCancelTime(new Date());
        staDao.save(sta);
        // 韩国CJ 取消菜鸟的运单号
        cancelCaiNiaoTransNo(sta);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 韩国CJ 取消菜鸟的运单号
     */
    public void cancelCaiNiaoTransNo(StockTransApplication sta) {
        try {
            StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            if (di.getLpCode() != null && Transportator.CNJH.equals(di.getLpCode())) {
                CjLgOrderCodeUrl cu = cjLgOrderCodeUrlDao.getCjLgOrderCodeUrlByStaId(sta.getId());
                GeneralImportsCancelRequest r = new GeneralImportsCancelRequest();
                r.setLgorderCode(cu.getLgorderCode());
                // 请求hub的systemKey
                ChooseOption systemKeyHub = chooseOptionDao.findByCategoryCodeAndKey(Constants.CJ_SYSTEM_KEY_HUB, sta.getOwner());
                if (systemKeyHub == null) {
                    logger.error("Cj-Cancel=========>>systemKeyHub==null");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                GeneralImportsCancelResponse rp = taobaoManager.generalImportsCancel(systemKeyHub.getOptionValue(), r);
                if (rp != null) {
                    log.info("", rp.toString());
                }
            }
        } catch (Exception e) {
            log.error("取消菜鸟快递单号异常！", e);
        }
    }

    /**
     * VMI 取消STA 000 取消成功 005 不存在或者已经取消 002 取消失败 003 取消失败，可拦截
     */
    public void vmiCancelSta(Long staId) {

        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // 1.首先判断staCode 是否已经取消成功，取消成功则直接结束程序
        if (ifCancel(sta.getCode(), DefaultStatus.FINISHED.getValue()).size() > 0) {
            return;
        }
        // 2.首先判断staCode 是否已经取消失败，重新取消
        if (ifCancel(sta.getCode(), DefaultStatus.CANCELED.getValue()).size() > 0) {
            // 发送取消指令
            InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.cancelOrderRX(sta.getCode());
            // 处理返回结果
            msgOutboundOrderCancelDao.updateStaById(sta.getCode(), DefaultStatus.FINISHED.getValue());
            if ("true".equals(resp.getSuccess())) {
                // 取消sta
                cancelSalesSta(sta.getId(), null);
            }
            return;
        }
        MsgOutboundOrderCancel mc = msgOutboundOrderCancelDao.getByStaCode(sta.getCode());
        // 存在取消的则不再记录
        if (mc == null) {
            mc = new MsgOutboundOrderCancel();
        }
        // 3.首先判断staCode 首次取消，执行取消操作
        // 发送取消指令
        InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.cancelOrderRX(sta.getCode());
        Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
        mc.setUuid(id);
        mc.setStaCode(sta.getCode());
        mc.setStaId(sta.getId());
        mc.setCreateTime(new Date());
        mc.setSource(Constants.VIM_WH_SOURCE_BIAOGAN);
        // 处理返回结果
        mc.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
        msgOutboundOrderCancelDao.save(mc);
        msgOutboundOrderCancelDao.flush();
        if ("true".equals(resp.getSuccess())) {
            // 取消sta
            cancelSalesSta(sta.getId(), null);
        }
    }

    public List<MsgOutboundOrderCancel> ifCancel(String staCode, int status) {
        return msgOutboundOrderCancelDao.findVmiMsgRtnOutboundByStaCode(staCode, status, new BeanPropertyRowMapper<MsgOutboundOrderCancel>(MsgOutboundOrderCancel.class));
    }

    /**
     * 获取pickingList是否包含取消作业单 KJL
     * 
     * @param id
     * @param statusList
     * @param singleColumnRowMapper
     * @return
     */
    public Boolean findIfExistsCancelSta(Long id, List<Integer> statusList) {
        return staDao.findIfExistsCancelSta(id, statusList, new SingleColumnRowMapper<Boolean>(Boolean.class));
    }

    public void cancelSta(Long staid) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        if (sta == null) throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        if (StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            if (StockTransApplicationType.INBOUND_PURCHASE.equals(sta.getType())) {
                List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
                staLineDao.deleteAll(stalines);
                staDao.deleteByPrimaryKey(staid);
                staDao.updatePOLifeByStaRefCodeSql(sta.getRefSlipCode(), 0);
                return;
            } else if (StockTransApplicationType.GI_PUT_SHELVES.equals(sta.getType())) {
                inventoryDao.releaseInventoryByStaId(staid);
                // 取消stv
                StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
                stv.setStatus(StockTransVoucherStatus.CANCELED);
                stv.setLastModifyTime(new Date());
                stvDao.save(stv);
                // 更新sta状态，取消数量占用
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setLastModifyTime(new Date());
                sta.setFinishTime(new Date());
                staDao.save(sta);
            }
        }
    }

    public void cancelStaEasy(Long staid, String remark) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        try {
            log.debug("Call oms order cancel interface------------------->BEGIN");
            BaseResult baseResult = rmi4Wms.cancelOperationBill(sta.getRefSlipCode(), sta.getType().getValue());
            if (baseResult.getStatus() == 0) throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
            log.debug("Call oms order cancel interface------------------->END");
        } catch (Exception e) {
            throw new BusinessException("未找到远程服务，oms未知异常");
        }
        if (!sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
            throw new BusinessException(ErrorCode.STA_CRANCEL_ERROR, new Object[] {sta.getCode()});
        }
        // 更新sta状态，取消数量占用
        sta.setStatus(StockTransApplicationStatus.CANCELED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setLastModifyTime(new Date());
        sta.setCancelTime(new Date());
        sta.setMemo(remark);
        staDao.save(sta);
    }

    public void cancelTranistInner(Long staId, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()) && !StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.TRANSIT_INNER_CALCEL_ERROR, new Object[] {sta.getCode()});
        }
        if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            inventoryDao.releaseInventoryByStaId(staId);
        }
        // 取消stv
        List<StockTransVoucher> stvList = stvDao.findStvCreatedListByStaId(sta.getId());
        for (StockTransVoucher stv : stvList) {
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stv.setLastModifyTime(new Date());
            stvDao.save(stv);
        }
        // 更新sta状态，取消数量占用
        sta.setCancelTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CANCELED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        staDao.save(sta);
        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    public void cancelTransitCrossSta(Long uersId, Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.TRANSIT_CROSS_CALCEL_ERROR, new Object[] {sta.getCode()});
        }
        sta.setInboundTime(new Date());
        if (StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            // 新建直接取消
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), uersId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setCancelTime(new Date());
            sta.setLastModifyTime(new Date());
            staDao.save(sta);
        } else if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            inventoryDao.releaseInventoryByStaId(staId);
            // 取消stv
            StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stv.setLastModifyTime(new Date());
            stvDao.save(stv);
            // 更新sta状态，取消数量占用
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), uersId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setLastModifyTime(new Date());
            sta.setCancelTime(new Date());
            staDao.save(sta);
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        }
        // SN号状态恢复
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvs != null && stvs.size() != 0) {
            StockTransVoucher stv = stvs.get(0);
            snDao.updateOutBoundSnRevertByStv(stv.getId());
        }
    }

    /**
     * vmi 退仓 cancel
     */
    public void cancelVmiReturnOutBound(Long staID, Long userid) throws Exception {
        cancelVmiTransferOutBound(staID, userid);
    }

    public void cancelVmiTransferOutBound(Long staId, Long userId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(staId);
        BiChannel biChannel = biChannelDao.getByCode(sta.getOwner());
        User user = null;
        if (userId != null) {
            user = userDao.getByPrimaryKey(userId);
        }
        isHaveException(sta, stv, user, userId);
        if (TransactionDirection.OUTBOUND.getValue() == stv.getDirection().getValue()) {
            // 出库
            // staDao.save(sta);
            List<Inventory> invList = inventoryDao.findByOccupiedCode(sta.getCode());
            if (invList.size() == 0) {
                throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {sta.getCode()});
            }
            inventoryDao.releaseInventoryByStaId(staId);
            // 取消stv
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stv.setLastModifyTime(new Date());
            stvDao.save(stv);
            // 更新sta状态，取消数量占用
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setLastModifyTime(new Date());
            try {
                if (sta.getSlipCode1() != null && !"1028".equals(biChannel.getVmiCode())) {
                    log.debug("Call oms order cancel interface------------------->BEGIN");
                    BaseResult baseResult = rmi4Wms.cancelOperationBill(sta.getSlipCode1(), sta.getType().getValue());
                    if (baseResult.getStatus() == 0) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                    }
                    log.debug("Call oms order cancel interface------------------->END");
                }
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
            }
            // sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
            sta.setCancelTime(new Date());
            sta.setIsNeedOccupied(false);
            sta.setOutboundOperator(user);
            staDao.save(sta);
            /***** mongoDB库存变更添加逻辑 ******************************/
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
            // 其他出库更新中间表，传递明细给oms/pac
            WmsOtherOutBoundInvNoticeOms wto = wmsOtherOutBoundInvNoticeOmsDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
            if (wto != null) {
                wmsOtherOutBoundInvNoticeOmsDao.updateOtherOutBoundInvNoticeOmsByStaCode(sta.getCode(), 17l);
            }
        }
    }

    public String deletePickingList(Long pickingListId, Long userId, Long ouId) {
        String returnMsg = "";
        // 新逻辑 不用存储过程取消配货清单bin.hu
        List<StockTransApplication> staList = staDao.findWhStaByPlId(pickingListId);
        String cancelRemark = "";
        // 新逻辑， 取消配货清单不释放库存xiaolong.fei
        for (StockTransApplication sta : staList) {
            cancelRemark += sta.getCode() + ",";
            staDao.updateStaDeTrackByStaId(sta.getId()); // 情况快递单号
        }
        PickingList pickingList = pickingListDao.getByPrimaryKey(pickingListId);
        pickingList.setStatus(PickingListStatus.CANCEL);
        pickingList.setCancelRemark(cancelRemark);
        pickingList.setCancelUserId(userId);
        whInfoTimeRefDao.insertWhInfoTime2(pickingList.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), userId, pickingList.getWarehouse().getId());
        staDao.updateStaToPickingListByPickingListId(pickingListId);
        Warehouse se = warehouseDao.getByOuId(ouId);
        if (se != null && null != se.getIsAutoWh() && se.getIsAutoWh()) {
            // 播种取消封装数据成json格式
            MsgToWcs msg = new MsgToWcs();
            if (pickingList.getCheckMode().equals(PickingListCheckMode.DEFAULE)) {
                OQuxiaoBoZhong ss = new OQuxiaoBoZhong();
                ss.setWaveOrder(pickingList.getCode());
                String context = net.sf.json.JSONObject.fromObject(ss).toString();
                // 保存数据到中间表
                msg.setContext(context);
                msg.setPickingListCode(pickingList.getCode());
                msg.setCreateTime(new Date());
                msg.setErrorCount(0);
                msg.setStatus(true);
                msg.setType(WcsInterfaceType.OQuxiaoBoZhong);
                msgToWcsDao.save(msg);
            }
            // 货箱流向
            List<String> containerCode = whContainerDao.getWcByPickIdNoStatus(pickingListId, new SingleColumnRowMapper<String>(String.class));
            String msgIdList = "";
            if (containerCode != null && containerCode.size() > 0) {
                // 保存货箱流向取消
                for (String code : containerCode) {
                    // 保存数据到中间表
                    OQuxiaoRongQi ssz = new OQuxiaoRongQi();
                    ssz.setContainerNO(code);
                    String contexts = net.sf.json.JSONObject.fromObject(ssz).toString();
                    MsgToWcs msgs = new MsgToWcs();
                    msgs.setContext(contexts);
                    msgs.setContainerCode(code);
                    msgs.setCreateTime(new Date());
                    msgs.setErrorCount(0);
                    msgs.setStatus(true);
                    msgs.setType(WcsInterfaceType.OQuxiaoRongQi);
                    msgToWcsDao.save(msgs);
                    msgIdList += msgs.getId() + ",";
                }
            }

            String bzId = "";
            if (msg.getId() != null) {
                bzId = msg.getId().toString();
            }
            returnMsg = bzId + "_" + msgIdList;
        }
        whPickingBatchDao.updateBatchByPickId(pickingListId); // 剔除自动化箱号
        whPickingBatchDao.deleteBatchByPickId(pickingListId); // 剔除自动化二级批次
        pdaPickingManager.moveCollectionBox(pickingList.getCode(), null, true, ouId, userId);// 强制清空集货库位
        mongoOperation.remove(new Query(Criteria.where("pinkingListId").is(pickingListId)), MongDbNoThreeDimensional.class);
        return returnMsg;
    }

    public List<StockTransApplicationCommand> findSalesCancelUndoStaList(String shopId, StockTransApplication sta, Long wh_ou_id, List<Long> plists, Date createTime, Date endCreateTime, Sort[] sorts) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
        List<Integer> typeList = new ArrayList<Integer>();
        typeList.add(StockTransApplicationType.OUTBOUND_SALES.getValue());
        typeList.add(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.getValue());
        typeList.add(StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue());
        if (plists != null) {
            if (plists.size() == 0) plists = null;
        }

        return wareHouseManager.findSalesStaList(null, null, true, true, null, typeList, statusList, null, shopId, sta, null, createTime, endCreateTime, wh_ou_id, plists, false, false, false, null, sorts);
    }

    @Override
    public Pagination<StockTransApplicationCommand> findSalesCancelUndoStaListByPage(int start, int pagesize, String shopId, StockTransApplication sta, Long wh_ou_id, List<Long> plists, Date createTime, Date endCreateTime, Sort[] sorts) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
        List<Integer> typeList = new ArrayList<Integer>();
        typeList.add(StockTransApplicationType.OUTBOUND_SALES.getValue());
        typeList.add(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.getValue());
        typeList.add(StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue());
        if (plists != null) {
            if (plists.size() == 0) plists = null;
        }


        // //////////////////////////////////////////////////////////

        return wareHouseManager.findSalesStaListByPage(start, pagesize, null, null, true, true, null, typeList, statusList, null, shopId, sta, null, createTime, endCreateTime, wh_ou_id, plists, false, false, false, null, sorts);
    }

    public PickingListCommand removeFialedSalesSta(Long pickingListId) {
        List<Integer> stautsList = new ArrayList<Integer>();
        stautsList.add(StockTransApplicationStatus.FAILED.getValue());
        stautsList.add(StockTransApplicationStatus.CANCELED.getValue());// 取消已处理
        stautsList.add(StockTransApplicationStatus.CANCEL_UNDO.getValue()); // 取消未处理
        staDao.removeStaFromPickingListByStatus(stautsList, pickingListId);
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        if (pl.getStaList().size() == 0) {
            pickingListLogDao.updatePickingListLog(pl.getId(), "WareHouseManagerImpl.removeFialedSalesSta");
            pickingListDao.deleteByPrimaryKey(pl.getId());

            return null;
        } else {
            setPickingListPlanQty(pl);
            pl.setStatus(PickingListStatus.PACKING);
            PickingListCommand copypl = new PickingListCommand();
            org.springframework.beans.BeanUtils.copyProperties(pl, copypl);
            copypl.setWarehouse(null);
            copypl.setOperator(null);
            copypl.setStaList(null);
            copypl.setHandOverList(null);
            return copypl;
        }
    }

    /**
     * 计算pickinglist中计划单据数与计划执行产品数
     * 
     * @param pl
     */
    public void setPickingListPlanQty(PickingList pl) {
        List<Long> staIdList = new ArrayList<Long>();// 非取消sta列表
        for (StockTransApplication sta : pl.getStaList()) {
            if (!StockTransApplicationStatus.CANCELED.equals(sta.getStatus()) && !StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus())) {
                staIdList.add(sta.getId());
            }
        }
        List<Integer> sts = new ArrayList<Integer>();
        sts.add(StockTransApplicationStatus.CREATED.getValue());
        sts.add(StockTransApplicationStatus.OCCUPIED.getValue());
        sts.add(StockTransApplicationStatus.CHECKED.getValue());
        sts.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
        sts.add(StockTransApplicationStatus.FINISHED.getValue());
        sts.add(StockTransApplicationStatus.INTRANSIT.getValue());
        sts.add(StockTransApplicationStatus.FAILED.getValue());
        BigDecimal qty = staLineDao.findTotalSkuCountByStaList(staIdList, sts, new SingleColumnRowMapper<BigDecimal>());
        pl.setPlanSkuQty(qty == null ? 0 : qty.intValue());
        pl.setPlanBillCount(staIdList.size());
    }

    public void deletePdaPostLog(Long logid, Long userid) {
        PdaPostLog log = pdaPostLogDao.getByPrimaryKey(logid);
        User user = userDao.getByPrimaryKey(userid);
        if (!DefaultStatus.CREATED.equals(log.getStatus())) {
            throw new BusinessException(ErrorCode.PDA_POST_LOG_STATUS_ERROR, new Object[] {log.getStatus(), log.getSku().getCode(), log.getLoc().getCode(), log.getInvStatus() == null ? "" : log.getInvStatus().getName()});
        }
        log.setOperator(user);
        log.setStatus(DefaultStatus.CANCELED);
        pdaPostLogDao.save(log);
    }

    public void predefinedOutCanceled(Long staId, String memo, User creator) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()) || StockTransApplicationStatus.PACKING.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.TRANSIT_CROSS_CALCEL_ERROR, new Object[] {sta.getCode()});
        }
        sta.setInboundTime(new Date());
        if (sta.getType().getValue() != 216 && sta.getType().getValue() != 218) {
            if (StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                // 新建直接取消
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setLastModifyTime(new Date());
                sta.setMemo(memo);
                staDao.save(sta);
                if (sta.getType().getValue() != 32 && sta.getType().getValue() != 63 && sta.getType().getValue() != StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue()
                        && sta.getType().getValue() != StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {
                    try {
                        log.debug("Call oms order cancel interface------------------->BEGIN");
                        BaseResult baseResult = rmi4Wms.cancelOperationBill(sta.getSlipCode1(), sta.getType().getValue());
                        if (baseResult.getStatus() == 0) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                        }
                        log.debug("Call oms order cancel interface------------------->END");
                    } catch (BusinessException e) {
                        log.debug("", e);
                        throw e;
                    } catch (Exception e) {
                        log.debug("", e);
                        throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                    }
                }
            } else if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                inventoryDao.releaseInventoryByStaId(staId);
                // 取消stv
                StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
                stv.setStatus(StockTransVoucherStatus.CANCELED);
                stv.setLastModifyTime(new Date());
                stvDao.save(stv);
                // 更新sta状态，取消数量占用
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setLastModifyTime(new Date());
                sta.setFinishTime(new Date());
                sta.setMemo(memo);
                staDao.save(sta);
                if (sta.getType().getValue() != 32 && sta.getType().getValue() != 63 && sta.getType().getValue() != StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue()
                        && sta.getType().getValue() != StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {
                    try {
                        log.debug("Call oms order cancel interface------------------->BEGIN");
                        BaseResult baseResult = rmi4Wms.cancelOperationBill(sta.getSlipCode1(), sta.getType().getValue());
                        if (baseResult.getStatus() == 0) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                        }
                        log.debug("Call oms order cancel interface------------------->END");
                    } catch (BusinessException e) {
                        log.debug("", e);
                        throw e;
                    } catch (Exception e) {
                        log.debug("", e);
                        throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                    }
                }
            } else if (StockTransApplicationStatus.PACKING.equals(sta.getStatus())) {
                // 删除箱
                List<Carton> cartons = cartonDao.findCartonByStaId(staId);
                for (Carton ct : cartons) {
                    Carton carton = cartonDao.getByPrimaryKey(ct.getId());
                    if (null != carton) {
                        cartonLineDao.deleteByCartonId(carton.getId());
                        cartonDao.delete(carton);
                    }
                }
                inventoryDao.releaseInventoryByStaId(staId);
                // 取消stv
                StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
                stv.setStatus(StockTransVoucherStatus.CANCELED);
                stv.setLastModifyTime(new Date());
                stvDao.save(stv);
                // 更新sta状态，取消数量占用
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setLastModifyTime(new Date());
                sta.setFinishTime(new Date());
                sta.setMemo(memo);
                staDao.save(sta);
                if (sta.getType().getValue() != 32 && sta.getType().getValue() != 63 && sta.getType().getValue() != StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue()
                        && sta.getType().getValue() != StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {
                    try {
                        log.debug("Call oms order cancel interface------------------->BEGIN");
                        BaseResult baseResult = rmi4Wms.cancelOperationBill(sta.getSlipCode1(), sta.getType().getValue());
                        if (baseResult.getStatus() == 0) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                        }
                        log.debug("Call oms order cancel interface------------------->END");
                    } catch (BusinessException e) {
                        log.debug("", e);
                        throw e;
                    } catch (Exception e) {
                        log.debug("", e);
                        throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                    }
                }
            }
        } else {
            List<StockTransApplication> stalist = staDao.findBySlipCodeStatus(sta.getSlipCode1());
            for (int i = 0; i < stalist.size(); i++) {
                if (stalist.get(i).getType().getValue() == 216 || stalist.get(i).getType().getValue() == 218) {
                    inventoryDao.releaseInventoryByStaId(stalist.get(i).getId());
                    // 取消stv
                    StockTransVoucher stv = stvDao.findStvCreatedByStaId(stalist.get(i).getId());
                    stv.setStatus(StockTransVoucherStatus.CANCELED);
                    stv.setLastModifyTime(new Date());
                    stvDao.save(stv);
                    // 更新sta状态，取消数量占用
                    stalist.get(i).setStatus(StockTransApplicationStatus.CANCELED);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(stalist.get(i).getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse()
                            .getId());
                    stalist.get(i).setFinishTime(new Date());
                    stalist.get(i).setLastModifyTime(new Date());
                    stalist.get(i).setMemo(memo);
                    staDao.save(stalist.get(i));
                    if (sta.getType().getValue() != 32 && stalist.get(i).getType().getValue() != 63) {
                        try {
                            log.debug("Call oms order cancel interface------------------->BEGIN");
                            BaseResult baseResult = rmi4Wms.cancelOperationBill(stalist.get(i).getSlipCode1(), stalist.get(i).getType().getValue());
                            if (baseResult.getStatus() == 0) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
                            }
                            log.debug("Call oms order cancel interface------------------->END");
                        } catch (BusinessException e) {
                            log.debug("", e);
                            throw e;
                        } catch (Exception e) {
                            log.debug("", e);
                            throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                        }
                    }
                } else {
                    // 新建直接取消
                    stalist.get(i).setStatus(StockTransApplicationStatus.CANCELED);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(stalist.get(i).getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse()
                            .getId());
                    stalist.get(i).setLastModifyTime(new Date());
                    stalist.get(i).setMemo(memo);
                    staDao.save(stalist.get(i));
                }

            }
        }
        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }

        // SN号状态恢复
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvs != null && stvs.size() != 0) {
            StockTransVoucher stv = stvs.get(0);
            snDao.updateOutBoundSnRevertByStv(stv.getId());
        }
    }

    /**
     * 删除数据 外包仓商品关联
     */
    @Override
    public void deleteSkuWarehouseRef(Long brandId, String source, String sourcewh, Long channelId) {
        skuWarehouseRefDao.deleteSkuWarehouseRef(brandId, source, sourcewh, channelId);
    }

}
