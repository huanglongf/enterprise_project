package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.CheckingSpaceRoleDao;
import com.jumbo.dao.automaticEquipment.GoodsCollectionDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.automaticEquipment.WhContainerLogDao;
import com.jumbo.dao.automaticEquipment.WhPickingBatchDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.pda.PickingLineDao;
import com.jumbo.dao.pda.StaCartonDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.GoodsCollection;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsPickingOverRequest;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsRequest.SShouRongQi;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.automaticEquipment.WhContainerCommand;
import com.jumbo.wms.model.automaticEquipment.WhContainerLog;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.automaticEquipment.CheckingSpaceRoleCommand;
import com.jumbo.wms.model.command.automaticEquipment.WhPickingBatchCommand;
import com.jumbo.wms.model.mongodb.InboundSkuWeightCheck;
import com.jumbo.wms.model.mongodb.MongDbNoThreeDimensional;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;

import cn.baozun.bh.util.DateUtil;
import cn.baozun.bh.util.JSONUtil;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 自动化出库周转箱相关业务逻辑实现
 * 
 * @author jinlong.ke
 * @date 2016年2月25日下午3:00:32
 */
@Transactional
@Service("autoOutboundTurnboxManager")
public class AutoOutboundTurnboxManagerImpl extends BaseManagerImpl implements AutoOutboundTurnboxManager {

    /**
     * 
     */
    private static final long serialVersionUID = 8397153218307304916L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhContainerDao whContainerDao;
    @Autowired
    private WhContainerLogDao whContainerLogDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private WhPickingBatchDao whPickingBatchDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private CheckingSpaceRoleDao checkingSpaceRoleDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaCartonDao staCartonDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private PickingLineDao pickingLineDao;
    @Autowired
    private GoodsCollectionDao goodsCollectionDao;
    @Autowired
    private SkuDao skuDao;


    @Override
    public String bindBatchAndTurnbox(String slipCode, String code, Long pId, Long ouId, Long userId) {
        // 1、根据前置单据判断该单（对应的批次）是否需要绑定
        StockTransApplication sta = staDao.findStaNeedBind(slipCode, ouId, pId);
        if (sta == null) {
            // 扫描的前置单据号找不到对应的单据和批次,或者单据非配货中状态
            throw new BusinessException(ErrorCode.TURNOVERBOX_STA_NOT_EXISTS);
        }
        if (null == sta.getIdx1() || null == sta.getPickingList()) {
            // 扫描的前置单据号对应的单据还未加入配货批
            throw new BusinessException(ErrorCode.TURNOVERBOX_STA_NOT_PCIKING);
        }
        if (null != sta.getContainerCode() && !sta.getContainerCode().equals(code)) {
            // 该单已做过绑定，请查证！
            throw new BusinessException(ErrorCode.TURNOVERBOX_STA_HAS_BIND);
        }
        // 2、根据扫描的周转箱条码判断该周转箱是否可用
        WhContainer wcn = whContainerDao.getWhContainerByCode(code);
        if (null == wcn) {
            // 扫描的条码对应的周装箱不存在
            throw new BusinessException(ErrorCode.TURNOVERBOX_NOT_EXISTS);
        }
        if (null != wcn.getStatus() && wcn.getStatus().equals(DefaultStatus.EXECUTING) && wcn.getWhPickingBatch() != null) {
            // 判断周转箱上是否绑定了配货清单和二级批次，并且是否与当前作业单一致
            if (!(wcn.getPickingList() != null && wcn.getPickingList().getId().equals(sta.getPickingList().getId()) && wcn.getWhPickingBatch() != null && wcn.getWhPickingBatch().getId().equals(sta.getWhPickingBatch().getId()))) {
                // 该周转箱已绑定其他批次！
                throw new BusinessException(ErrorCode.TURNOVERBOX_BIND_OTHER_ORDER);
            } else {
                Boolean flag = updatePickingListStatusIsNecessary(pId, userId, ouId);
                return 0 + "," + flag;
            }
        }
        List<StockTransApplication> staList = staDao.getStaListByPickingListAndBatchIndex(sta.getPickingList().getId(), sta.getIdx1());
        for (StockTransApplication sta1 : staList) {
            sta1.setContainerCode(code);
        }

        wcn.setPickingList(sta.getPickingList());
        wcn.setWhPickingBatch(sta.getWhPickingBatch());
        wcn.setStatus(DefaultStatus.EXECUTING);
        staDao.flush();
        createWhContainerLog(wcn.getCode(), DefaultStatus.EXECUTING, null, TransactionDirection.OUTBOUND, sta.getPickingList().getId(), sta.getWhPickingBatch().getId(), null, userId);
        String checkCode = null;
        String roleCode = checkSpaceRole(staList, ouId, sta.getPickingList().getId());
        if ("".equals(roleCode)) {
            Warehouse w = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            if (w.getCheckingAreaCode() == null) {
                throw new BusinessException(ErrorCode.WAREHOUSE_CHECK_AREA_NOT_EXISTS);
            } else {
                checkCode = w.getCheckingAreaCode();
            }
        } else {
            checkCode = roleCode;
        }
        // 查询当前配货批是否都已绑定周转箱,如果都绑定了,则按需（根据仓库配置）自动将波次修改为拣货完成（没有拣货开始自动添加拣货开始操作）
        Boolean flag = updatePickingListStatusIsNecessary(pId, userId, ouId);
        Long id = saveMsgToWcs(wcn, checkCode, sta.getPickingList().getCode());
        return id + "," + flag;
    }

    private String checkSpaceRole(List<StockTransApplication> staList, Long ouId, Long pkId) {
        String bozCode = "";
        List<CheckingSpaceRoleCommand> roleList = checkingSpaceRoleDao.findCheckingSpaceList(ouId, 1, new BeanPropertyRowMapper<CheckingSpaceRoleCommand>(CheckingSpaceRoleCommand.class));
        for (CheckingSpaceRoleCommand role : roleList) {
            for (StockTransApplication sta : staList) {
                StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                if (role.getOwner() != null && !role.getOwner().equals(sta.getOwner())) {
                    continue;
                }
                /**
                 * 时效类型
                 */

                if (role.getTransType() != null && !((role.getTransType() == null ? -1 : role.getTransType()) == info.getTransTimeType().getValue())) {
                    continue;
                }
                /**
                 * 指定商品
                 */
                if (role.getSkuCodes() != null) {
                    boolean f = true;
                    Set<String> set = new HashSet<String>();
                    // 查询作业单明细行商品
                    String[] roleSkuCodes = role.getSkuCodes().split(",");
                    List<StaLineCommand> staLineCommandLists = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                    for (StaLineCommand staLineCommand : staLineCommandLists) {
                        set.add(staLineCommand.getCode());
                    }
                    for (String str : roleSkuCodes) {
                        if (set.contains(str)) {
                            f = false;
                            break;
                        }
                    }
                    if (f) {
                        continue;
                    }
                }
                // ////////////////////////////////

                if (role.getToLocation() != null && !role.getToLocation().equals(sta.getToLocation())) {
                    continue;
                }
                if (role.getLpcode() != null && !role.getLpcode().equals(info.getLpCode())) {
                    continue;
                }
                if (role.getCity() != null && StringUtils.hasLength(info.getCity())) {
                    // 截取市
                    String infoCity = info.getCity();
                    // if (infoCity.substring(infoCity.length() - 1, infoCity.length()).equals("市"))
                    // {
                    // infoCity = infoCity.substring(0, infoCity.length() - 1);
                    // }
                    // 非优先发货城市
                    if (role.getCity().contains("opposite")) {
                        if (role.getCity().contains(infoCity)) {
                            continue;
                        }
                    } else if (role.getCity().contains(",") && !role.getCity().contains("opposite")) {
                        // 所有优先发货城市
                        if (!role.getCity().contains(infoCity)) {
                            continue;
                        }
                    } else {
                        // 优先发货城市
                        if (!role.getCity().equals(infoCity)) {
                            continue;
                        }
                    }
                }
                // QS处理逻辑
                if (role.getIsQs() != null && (role.getSpecialType() == null || role.getSpecialType() != 1)
                        && (((sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging() && sta.getSpecialType() == null) && !role.getIsQs()) || ((sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) && role.getIsQs()))) {
                    continue;
                }
                // 特殊处理逻辑
                if (role.getSpecialType() != null
                        && (role.getIsQs() == null || !role.getIsQs())
                        && (((sta.getSpecialType() != null && sta.getSpecialType().getValue() == 1 && (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging())) && role.getSpecialType() != 1) || (sta.getSpecialType() == null && role
                                .getSpecialType() == 1))) {
                    continue;
                }
                // QS处理逻辑 && 特殊处理逻辑
                if (role.getIsQs() != null && role.getSpecialType() != null && role.getIsQs() && role.getSpecialType() == 1) {
                    if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging() || sta.getSpecialType() == null) {
                        continue;
                    }
                }

                // 是否预售
                if (role.getIsPreSale() != null) {
                    if (!role.getIsPreSale().equals(sta.getIsPreSale())) {
                        if ("0".equals(role.getIsPreSale()) && "".equals(sta.getIsPreSale())) {} else {
                            continue;
                        }
                    }
                }

                bozCode = role.getCheckingAreaCode();
                break;
            }
            if (!"".equals(bozCode)) {
                break;
            }
        }
        try {
            List<Long> staIdList = new ArrayList<Long>();
            for (StockTransApplication sta : staList) {
                // 如果作业单集货编码为空，则再次匹配
                if (!StringUtils.hasLength(sta.getShipmentCode())) {
                    staIdList.add(sta.getId());
                }
            }
            if (staIdList != null && staIdList.size() > 0) {
                PickingList t = pickingListDao.getByPrimaryKey(pkId);
                warehouseOutBoundManager.marryStaShiipingCode(t.getCheckMode(), t.getCode(), staIdList, ouId);
            }
        } catch (Exception e) {
            log.error(pkId + " marryStaShiipingCodeAgain is error.....");
        }
        return bozCode;
    }

    /**
     * 查询当前配货批是否都已绑定周转箱,如果都绑定了,则按需（根据仓库配置）自动将波次修改为拣货完成（没有拣货开始自动添加拣货开始操作）
     * 
     * @param pId
     * @return
     */
    private Boolean updatePickingListStatusIsNecessary(Long pId, Long userId, Long ouId) {
        pickingListDao.flush();
        PickingList p = pickingListDao.getByPrimaryKey(pId);
        Boolean b = pickingListDao.findIfExistPbNotBind(pId, new SingleColumnRowMapper<Boolean>(Boolean.class));
        if (!b) {
            Warehouse w = warehouseDao.getByOuId(p.getWarehouse().getId());
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {
                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(p.getCode(), 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(p.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, ouId);
                }
                ref2 = whInfoTimeRefDao.getInfoBySlipCode(p.getCode(), 14, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(p.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 14, userId, ouId);
                    // List<WhPickingBatch> pbList =
                    // whPickingBatchDao.getPlzListByPickingListId(pId);
                    // 清除mgdb缓存
                    // mongoOperation.remove(new
                    // Query(Criteria.where("barCode").in(pbList.get(0).getBarCode())),
                    // MongoDBWhPicking.class);
                }
                // }
            }
            if (p.getPickingStatus() == null) {
                p.setPickingStartTime(new Date());
            }
            if (userId != null) {
                p.setPickingUser(userDao.getByPrimaryKey(userId));
            }
            // if(p.getPickingStatus()!=14){
            p.setPickingStatus(14);
            p.setPickingEndTime(new Date());
        }
        return !b;
    }

    /**
     * @return
     */
    private Long saveMsgToWcs(WhContainer wcn, String checkCode, String pickingListCode) {
        MsgToWcsPickingOverRequest mr = new MsgToWcsPickingOverRequest();
        mr.setCategoryMode("1");
        mr.setContainerNO(wcn.getCode());
        mr.setDestinationNO(checkCode);
        MsgToWcs mw = new MsgToWcs();
        mw.setContainerCode(wcn.getCode());
        mw.setPickingListCode(pickingListCode);
        mw.setStatus(true);
        mw.setContext(JSONUtil.beanToJson(mr));
        mw.setCreateTime(new Date());
        mw.setType(WcsInterfaceType.OShouRongQi);
        mw.setErrorCount(0);
        msgToWcsDao.save(mw);
        return mw.getId();
    }

    public void createWhContainerLog(String code, DefaultStatus status, String orderCode, TransactionDirection type, Long pickListId, Long pbId, Long staId, Long userId) {
        WhContainerLog cl = new WhContainerLog();
        cl.setCode(code);
        cl.setCreateTime(new Date());
        cl.setStatus(status);
        cl.setOrderCode(orderCode);
        cl.setType(type);
        cl.setpId(pickListId);
        cl.setP2Id(pbId);
        cl.setStaId(staId);
        cl.setUserId(userId);
        whContainerLogDao.save(cl);
    }

    @Override
    public void createNewTurnboxByName(String code, Long id) {
        WhContainer wcn = whContainerDao.getWhContainerByCode(code);
        if (null != wcn) {
            throw new BusinessException(ErrorCode.TURNOVERBOX_EXISTS);
        }
        wcn = new WhContainer();
        wcn.setCode(code);
        wcn.setStatus(DefaultStatus.CREATED);
        whContainerDao.save(wcn);
    }

    @Override
    public Pagination<WhContainerCommand> getAllTurnoverBox(int start, int pageSize, String code, String plCode, Integer status, Long ouId, Sort[] sorts) {
        String cd = null;
        if (StringUtils.hasText(code)) {
            cd = code;
        }
        return whContainerDao.getAllWhContainer(start, pageSize, cd, plCode, status, ouId, sorts, new BeanPropertyRowMapper<WhContainerCommand>(WhContainerCommand.class));
    }

    @Override
    public void resetTurnoverBoxStatus(Long tId, Long userId) {
        WhContainer wc = whContainerDao.getByPrimaryKey(tId);
        if (wc.getStaId() == null) {} else {
            StockTransApplication s = staDao.getByPrimaryKey(wc.getStaId());
            if (s != null && null == s.getContainerCode()) {
                StaCarton carton = staCartonDao.getStaCartonByCode(wc.getCode(), s.getMainWarehouse().getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                if (carton != null) {
                    throw new BusinessException("该周转箱pda上架还没有完成，请进行上架审核");
                }
            }
        }
        whContainerDao.resetWhContainerStatus(tId);
        createWhContainerLog(wc.getCode(), DefaultStatus.CREATED, null, null, null, null, null, userId);
    }


    @Override
    public void resetTurnoverBoxStatusPdaShelves(Long tId, Long userId) {
        whContainerDao.resetWhContainerStatus(tId);
        WhContainer wc = whContainerDao.getByPrimaryKey(tId);
        createWhContainerLog(wc.getCode(), DefaultStatus.CREATED, null, null, null, null, null, userId);
    }

    /**
     * 强制重置周转箱状态
     * 
     * @param pId
     */
    public void resetBoxStatus(Long pId) {
        whPickingBatchDao.updateBatchByPickId(pId); // 剔除自动化箱
    }

    /**
     * 多件批次绑定
     * 
     * @param pickingListCode
     * @param barCode
     * @param ouId
     */
    public WhContainer bindManyBatchAndTurnbox(String pickingListCode, String zoonCode, String barCode, Long ouId) {
        // 1、根据前置单据判断该单（对应的批次）是否需要绑定
        PickingList pl = pickingListDao.getByCode(pickingListCode);

        WhPickingBatch pb = whPickingBatchDao.getPlzListByZoonCode(pl.getId(), zoonCode);

        // 2、根据扫描的周转箱条码判断该周转箱是否可用
        WhContainer tb = whContainerDao.getWhContainerByCode(barCode);
        if (null == tb) {
            // 扫描的条码对应的周装箱不存在
            throw new BusinessException(ErrorCode.TURNOVERBOX_CODE_NOT_EXISTS, new Object[] {barCode});
        }

        if (!tb.getStatus().equals(DefaultStatus.CREATED) && tb.getWhPickingBatch() != null) {
            // if (tb.getPickingList() != null || tb.getWhPickingBatch() != null) {
            // 该周转箱已绑定其他批次！
            throw new BusinessException(ErrorCode.TURNOVERBOX_BIND_OTHER_ORDER);
            // }
        }
        Integer bc = pl.getBoxCount();// 货箱数量
        if (bc == null) {
            bc = 0;
        }
        pl.setBoxCount(bc + 1);
        // pickingListDao.save(pl);

        tb.setPickingList(pl);
        tb.setWhPickingBatch(pb);
        tb.setStatus(DefaultStatus.EXECUTING);
        tb.setIsReceive(false);
        whContainerDao.save(tb);
        pickingListDao.flush();
        whContainerDao.flush();
        return tb;
    }

    /**
     * 根据参数获取配货配与仓储区域的关联关系
     * 
     * @param start
     * @param pageSize
     * @param m
     * @return
     */
    public Pagination<WhPickingBatchCommand> findPickingListZoneByParams(int start, int pageSize, Map<String, Object> m) {

        return whPickingBatchDao.findPickingListZoneByParams(start, pageSize, m, new BeanPropertyRowMapper<WhPickingBatchCommand>(WhPickingBatchCommand.class));
    }

    /**
     * 配货批下的周转箱设为完成 WEB版
     * 
     * @param pickingListCode
     * @param barCode
     */
    public List<Long> pickingListAndZoneOverForWeb(Long pickingListId, String zoonCode, List<String> boxCode, Long ouId, Long userId) {
        Warehouse w = warehouseDao.getByOuId(ouId);
        List<Long> msgId = null;
        if (w.getIsAutoWh() != null && w.getIsAutoWh() == true) {

            msgId = pickingListAndZoneOver(pickingListId, zoonCode, boxCode, ouId, userId);
        } else {
            msgId = notAutoPickingListAndZoneOver(pickingListId, zoonCode, boxCode, ouId, userId);
        }
        pickingLineOver(pickingListId, zoonCode);
        return msgId;
    }

    /**
     * 配货批下的周转箱设为完成
     * 
     * @param pickingListCode
     * @param barCode
     */
    public List<Long> pickingListAndZoneOver(Long pickingListId, String zoonCode, List<String> boxCode, Long ouId, Long userId) {
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        // 1.校验批次
        if (pl == null || !ouId.equals(pl.getWarehouse().getId()) || !PickingListStatus.PACKING.equals(pl.getStatus())) {
            // 此配货批不存在，或已经配货完成
            throw new BusinessException(ErrorCode.TURNOVERBOX_PL_NOT_EXISTS_END);
        }
        // 2.校验区域
        WhPickingBatch pb = whPickingBatchDao.getPlzListByZoonCode(pl.getId(), zoonCode);
        if (pb == null) {
            // 该仓库区域不存在
            throw new BusinessException(ErrorCode.TURNOVERBOX_ZONE_NOT_EXISTS);
        }
        if (!DefaultStatus.CREATED.equals(pb.getStatus())) {
            // 该区域已经捡货完成
            throw new BusinessException(ErrorCode.TURNOVERBOX_PL_END);
        }
        // 3. 校验批次是否已经达到上限
        Warehouse w = warehouseDao.getByOuId(ouId);
        boolean noBind = false;
        boolean limitB = true;// 自动化是否达到上限
        boolean b = true;
        List<Long> pickIdList = whContainerDao.findContainerPickId(w.getAutoSeedGroup(), new SingleColumnRowMapper<Long>(Long.class));
        List<WhPickingBatch> pbList = whPickingBatchDao.getPlzListByPickingListId(pickingListId);
        // 是否是没有推荐到集货区域
        for (WhPickingBatch wpb : pbList) {
            if (DefaultStatus.FINISHED.equals(wpb.getStatus())) {
                noBind = true;
            }
        }

        if (pickIdList != null && pickIdList.size() >= w.getAutoPickinglistLimit()) {// 当批次的数量超过限制的时候，如果此批次已经在集货口，那么就继续往下走，否则就抛异常
            for (Long pickId : pickIdList) {
                if (pickingListId.equals(pickId)) {
                    b = false;
                }
            }
            if (b) {

                GoodsCollection cc = goodsCollectionDao.getGoodsCollectionByPickingCode(pl.getCode());
                if (cc == null) {
                    // 人工集货是否到达上限
                    Long qty = goodsCollectionDao.countCollectionByStatus(1, ouId, new SingleColumnRowMapper<Long>(Long.class));
                    Long collectionQty = goodsCollectionDao.countCollectionByStatus(2, ouId, new SingleColumnRowMapper<Long>(Long.class));
                    Long collectionOverQty = goodsCollectionDao.countCollectionByStatus(3, ouId, new SingleColumnRowMapper<Long>(Long.class));
                    Integer limit = w.getManpowerPickinglistLimit();

                    // 是否支持跨区人工集货

                    if (qty == 0 || (limit != null && limit <= (collectionQty.intValue() + collectionOverQty.intValue())) || (w.getIsManpowerConsolidation() == true && pbList.size() <= 1)) {

                        // 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱！
                        // throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_LIMIT);
                        noBind = true;
                    }
                }
                limitB = false;
            }
        }


        // 4.区域完成
        if (noBind || (pl.getGoodsCollectionType() != null && pl.getGoodsCollectionType() == 1)) {
            pb.setStatus(DefaultStatus.FINISHED);
        } else {
            pb.setStatus(DefaultStatus.SENT);
        }
        pb.setEndTime(new Date());
        whPickingBatchDao.save(pb);

        // 5.查看是否此配合批下的所有仓库区域都已拣货完成
        boolean over = true;
        List<WhPickingBatch> wpbList = whPickingBatchDao.getPlzListByPickingListId(pickingListId);
        for (WhPickingBatch wpb : wpbList) {
            if (!DefaultStatus.SENT.equals(wpb.getStatus()) && !DefaultStatus.FINISHED.equals(wpb.getStatus())) {
                over = false;
            }
        }
        // 6.将波次修改为拣货完成
        if (over) {
            // 查询当前配货批是否都已绑定周转箱,如果都绑定了,则按需（根据仓库配置）自动将波次修改为拣货完成（没有拣货开始自动添加拣货开始操作）
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {

                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, ouId);
                }
                ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 14, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 14, userId, ouId);
                    // 清除mgdb缓存
                    // mongoOperation.remove(new
                    // Query(Criteria.where("barCode").in(pb.getBarCode())),
                    // MongoDBWhPicking.class);
                }
                // }
            }
            if (pl.getPickingStatus() == null) {
                pl.setPickingStartTime(new Date());
            }
            if (userId != null) {
                pl.setPickingUser(userDao.getByPrimaryKey(userId));
            }
            // if (pl.getPickingStatus()==null||pl.getPickingStatus() != 14) {
            pl.setPickingStatus(14);
            pl.setPickingEndTime(new Date());
        }

        for (String box : boxCode) {
            bindManyBatchAndTurnbox(pl.getCode(), zoonCode, box, ouId);

        }

        List<Long> msgIds = new ArrayList<Long>();
        
        String boCode = findCheckingCode(pl);
        if (!StringUtil.isEmpty(boCode)) {
            Long msgId = generateMsgIdByGroup(boxCode, boCode, pl.getCode());
            msgIds.add(4L);
            msgIds.add(msgId);
            return msgIds;
        }


        
        if (noBind || (pl.getGoodsCollectionType() != null && pl.getGoodsCollectionType() == 1)) {
            // 到团购复核台
            if (pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {
                if (!StringUtil.isEmpty(w.getGroupWorkbenchCode())) {
                    Long msgId = generateMsgIdByGroup(boxCode, w.getGroupWorkbenchCode(), pl.getCode());
                    msgIds.add(4L);
                    msgIds.add(msgId);
                    return msgIds;
                }
            }

            msgIds.add(-2L);
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() == 0) {
                pl.setGoodsCollectionType(1);
            }
            return msgIds;
        }

        // 7.绑定周转箱并新增wcs接口数据
        if (!limitB || (pl.getGoodsCollectionType() != null && pl.getGoodsCollectionType() == 3)) {
            msgIds = generateToWcsMsg(boxCode, pl, ouId);
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() == 0) {
                pl.setGoodsCollectionType(3);
            }
            return msgIds;
        } else {

            // 到团购复核台
            if (pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {
                if (!StringUtil.isEmpty(w.getGroupWorkbenchCode())) {
                    Long msgId = generateMsgIdByGroup(boxCode, w.getGroupWorkbenchCode(), pl.getCode());
                    msgIds.add(4L);
                    msgIds.add(msgId);
                    return msgIds;
                }
            }
            /*
             * String boCode2 = findCheckingCode(pl); if (!StringUtil.isEmpty(boCode)) { Long msgId
             * = generateMsgIdByGroup(boxCode, boCode, pl.getCode()); msgIds.add(4L);
             * msgIds.add(msgId); return msgIds; }
             */

            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() == 0) {
                pl.setGoodsCollectionType(2);
            }

            List<MsgToWcsPickingOverRequest> porList = new ArrayList<MsgToWcsPickingOverRequest>();
            for (String box : boxCode) {
                // WhContainer wc = bindManyBatchAndTurnbox(pl.getCode(), zoonCode, box, ouId);

                MsgToWcsPickingOverRequest po = new MsgToWcsPickingOverRequest();
                po.setCategoryMode("1");
                po.setContainerNO(box);
                po.setWaveOrderr(pl.getCode());
                if (PickingListCheckMode.DEFAULE.equals(pl.getCheckMode()) || PickingListCheckMode.PICKING_SPECIAL.equals(pl.getCheckMode())) {// 判断是否是多件
                    // 多件匹配播种墙逻辑
                    boCode = checkBozRole(pl.getStaList(), ouId, pl.getId());
                    if (StringUtils.hasLength(boCode)) {
                        po.setDestinationNO(boCode);
                    } else {
                        po.setDestinationNO(w.getSeedingAreaCode());
                    }
                }

                if (over) {// 设置完结标识
                    if (pl.getBoxCount() == null) {
                        pl.setBoxCount(1);
                    }
                    po.setEndingTag(pl.getBoxCount().toString());
                    po.setCount(pl.getBoxCount().toString());
                    over = false;
                }
                porList.add(po);
            }

            String context = JsonUtil.collection2jsonStr(porList);
            // 保存数据到中间表
            MsgToWcs msg = new MsgToWcs();
            msg.setContext(context);
            msg.setPickingListCode(pl.getCode());
            msg.setCreateTime(new Date());
            msg.setErrorCount(0);
            msg.setStatus(true);
            msg.setType(WcsInterfaceType.OShouRongQi);
            msgToWcsDao.save(msg);
            msgToWcsDao.flush();
            msgIds.add(4L);
            msgIds.add(msg.getId());
            return msgIds;
        }

    }

    public Long generateMsgIdByGroup(List<String> boxCode, String boCode, String plCode) {
        List<MsgToWcsPickingOverRequest> porList = new ArrayList<MsgToWcsPickingOverRequest>();
        for (String box : boxCode) {
            MsgToWcsPickingOverRequest mr = new MsgToWcsPickingOverRequest();
            mr.setCategoryMode("1");
            mr.setContainerNO(box);
            mr.setDestinationNO(boCode);
            porList.add(mr);
        }
        String context = JsonUtil.collection2jsonStr(porList);
        MsgToWcs mw = new MsgToWcs();
        mw.setPickingListCode(plCode);
        mw.setStatus(true);
        mw.setContext(context);
        mw.setCreateTime(new Date());
        mw.setType(WcsInterfaceType.OShouRongQi);
        mw.setErrorCount(0);
        msgToWcsDao.save(mw);
        return mw.getId();
    }

    /**
     * 查找已经维护好的复核工作台编码
     * 
     * @param pl
     * @return
     */
    public String findCheckingCode(PickingList pl) {
        Warehouse w = warehouseDao.getByOuId(pl.getWarehouse().getId());

        if (pl.getTransTimeType() != null) {
            if (pl.getTransTimeType() == TransTimeType.SAME_DAY) {
                if (!StringUtil.isEmpty(w.getSameDayWorkbenchCode())) {
                    return w.getSameDayWorkbenchCode();
                }
            } else if (pl.getTransTimeType() == TransTimeType.THE_NEXT_DAY) {
                if (!StringUtil.isEmpty(w.getNextDayWorkbenchCode())) {
                    return w.getNextDayWorkbenchCode();
                }
            } else if (pl.getTransTimeType() == TransTimeType.THE_NEXT_MORNING) {
                if (!StringUtil.isEmpty(w.getNextMorningWorkbenchCode())) {
                    return w.getNextMorningWorkbenchCode();
                }
            }
        }
        if (pl.getSpecialType() != null) {
            if (!StringUtil.isEmpty(w.getSpecialWorkbenchCode())) {
                return w.getSpecialWorkbenchCode();
            }
        }
        /*
         * if (pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) { if
         * (!StringUtil.isEmpty(w.getGroupWorkbenchCode())) { return w.getGroupWorkbenchCode(); } }
         */
        return null;
    }

    /**
     * 非自动化仓配货批下的周转箱设为完成
     * 
     * @param pickingListCode
     * @param barCode
     */
    public List<Long> notAutoPickingListAndZoneOver(Long pickingListId, String zoonCode, List<String> boxCode, Long ouId, Long userId) {
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        // 1.校验批次
        if (pl == null || !ouId.equals(pl.getWarehouse().getId()) || !PickingListStatus.PACKING.equals(pl.getStatus())) {
            // 此配货批不存在，或已经配货完成
            throw new BusinessException(ErrorCode.TURNOVERBOX_PL_NOT_EXISTS_END);
        }
        // 2.校验区域
        WhPickingBatch pb = whPickingBatchDao.getPlzListByZoonCode(pl.getId(), zoonCode);
        if (pb == null) {
            // 该仓库区域不存在
            throw new BusinessException(ErrorCode.TURNOVERBOX_ZONE_NOT_EXISTS);
        }
        if (!DefaultStatus.CREATED.equals(pb.getStatus())) {
            // 该区域已经捡货完成
            throw new BusinessException(ErrorCode.TURNOVERBOX_PL_END);
        }
        // 3. 校验批次是否已经达到上限
        Warehouse w = warehouseDao.getByOuId(ouId);
        boolean limitB = false;// 是否达到上限
        List<WhPickingBatch> pbList = whPickingBatchDao.getPlzListByPickingListId(pickingListId);

        GoodsCollection cc = goodsCollectionDao.getGoodsCollectionByPickingCode(pl.getCode());
        if (cc == null) {
            // 人工集货是否到达上限
            Long qty = goodsCollectionDao.countCollectionByStatus(1, ouId, new SingleColumnRowMapper<Long>(Long.class));
            Long collectionQty = goodsCollectionDao.countCollectionByStatus(2, ouId, new SingleColumnRowMapper<Long>(Long.class));
            Long collectionOverQty = goodsCollectionDao.countCollectionByStatus(3, ouId, new SingleColumnRowMapper<Long>(Long.class));
            Integer limit = w.getManpowerPickinglistLimit();

            // 是否支持跨区人工集货

            if (qty == 0 || (limit != null && limit <= (collectionQty.intValue() + collectionOverQty.intValue())) || (w.getIsManpowerConsolidation() == true && pbList.size() <= 1)) {

                // 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱！
                // throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_LIMIT);
                limitB = true;
            }
        }


        // 4.区域完成
        pb.setStatus(DefaultStatus.FINISHED);
        pb.setEndTime(new Date());
        whPickingBatchDao.save(pb);

        // 5.查看是否此配合批下的所有仓库区域都已拣货完成
        boolean over = true;
        List<WhPickingBatch> wpbList = whPickingBatchDao.getPlzListByPickingListId(pickingListId);
        for (WhPickingBatch wpb : wpbList) {
            if (!DefaultStatus.SENT.equals(wpb.getStatus()) && !DefaultStatus.FINISHED.equals(wpb.getStatus())) {
                over = false;
            }
        }
        // 6.将波次修改为拣货完成
        if (over) {
            // 查询当前配货批是否都已绑定周转箱,如果都绑定了,则按需（根据仓库配置）自动将波次修改为拣货完成（没有拣货开始自动添加拣货开始操作）
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {

                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, ouId);
                }
                ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 14, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 14, userId, ouId);
                    // 清除mgdb缓存
                    // mongoOperation.remove(new
                    // Query(Criteria.where("barCode").in(pb.getBarCode())),
                    // MongoDBWhPicking.class);
                }
                // }
            }
            if (pl.getPickingStatus() == null) {
                pl.setPickingStartTime(new Date());
            }
            if (userId != null) {
                pl.setPickingUser(userDao.getByPrimaryKey(userId));
            }
            // if (pl.getPickingStatus()==null||pl.getPickingStatus() != 14) {
            pl.setPickingStatus(14);
            pl.setPickingEndTime(new Date());
        }

        List<Long> msgIds = new ArrayList<Long>();

        // 7.绑定周转箱并新增wcs接口数据
        if (!limitB) {
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() == 0) {
                pl.setGoodsCollectionType(3);
            }
            GoodsCollection gc = null;
            gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pl.getCode());
            if (gc == null) {

                Long gcId = goodsCollectionDao.recommendCollectionCode(null, ouId, new SingleColumnRowMapper<Long>(Long.class));
                if (gcId == null) {
                    // 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱！
                    throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_LIMIT);
                }
                gc = goodsCollectionDao.getByPrimaryKey(gcId);
                gc.setPickinglist(pl);
                gc.setStartTime(null);
                gc.setStatus(2);
                goodsCollectionDao.save(gc);
            }

        } else {
            msgIds.add(-2L);
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() == 0) {
                pl.setGoodsCollectionType(1);
            }
        }

        for (String box : boxCode) {
            bindManyBatchAndTurnbox(pl.getCode(), zoonCode, box, ouId);

        }

        return msgIds;

    }

    /**
     * 获取自动化仓人工集货推荐信息
     * 
     * @param boxCode
     * @param pl
     * @param ouId
     * @return
     */
    public List<Long> generateToWcsMsg(List<String> boxCode, PickingList pl, Long ouId) {
        GoodsCollection gc = null;
        gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pl.getCode());
        if (gc == null) {
            Warehouse w = warehouseDao.getByOuId(pl.getWarehouse().getId());
            Long gcId = null;
            if (w.getIsAutoWh() != null && w.getIsAutoWh() == true) {

                gcId = goodsCollectionDao.recommendCollectionCode("true", ouId, new SingleColumnRowMapper<Long>(Long.class));
            } else {
                gcId = goodsCollectionDao.recommendCollectionCode(null, ouId, new SingleColumnRowMapper<Long>(Long.class));
            }

            if (gcId == null) {
                // 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱！
                throw new BusinessException(ErrorCode.TURNOVERBOX_COLLECTION_PICKING_LIMIT);
            }
            gc = goodsCollectionDao.getByPrimaryKey(gcId);
            gc.setPickinglist(pl);
            gc.setStartTime(null);
            gc.setStatus(2);
            goodsCollectionDao.save(gc);
        }

        List<Long> msgId = new ArrayList<Long>();
        msgId.add(1L);
        for (String box : boxCode) {
            // WhContainer wc = bindManyBatchAndTurnbox(pl.getCode(), zoonCode, box, ouId);

            SShouRongQi ss = new SShouRongQi();
            ss.setContainerNO(box); // 容器号
            ss.setDestinationNO(gc.getPopUpCode()); // 目的地位置
            String context = net.sf.json.JSONObject.fromObject(ss).toString();
            // 保存数据到中间表
            MsgToWcs msg = new MsgToWcs();
            msg.setContext(context);
            msg.setContainerCode(box);
            msg.setCreateTime(new Date());
            msg.setErrorCount(0);
            msg.setStatus(true);
            msg.setType(WcsInterfaceType.SShouRongQi);
            msgToWcsDao.save(msg);
            msgId.add(msg.getId());

        }
        return msgId;
    }

    /**
     * 在页面操作区域完成时，将明细设置为拣货完成
     * 
     * @param pickingListId
     * @param zoonCode
     */
    public void pickingLineOver(Long pickingListId, String zoonCode) {
        WhPickingBatch pb = whPickingBatchDao.getPlzListByZoonCode(pickingListId, zoonCode);
        pickingLineDao.modifyPickingLineByPbId(pb.getId());
    }

    public String pickingBatchBarCode(Long pickingListId, String zoonCode) {
        WhPickingBatch pb = whPickingBatchDao.getPlzListByZoonCode(pickingListId, zoonCode);
        if (pb != null) {
            return pb.getBarCode();
        }
        return null;
    }

    @Override
    public WhContainer getWhContainerById(Long tId) {
        WhContainer wc = whContainerDao.getByPrimaryKey(tId);
        WhContainer wc1 = new WhContainer();
        wc1.setCode(wc.getCode());
        return wc1;
    }


    @Override
    public PickingList checkBindPickingList(String pCode, Long ouId) {
        PickingList pl = pickingListDao.findIsCanCheckByPcode(pCode, ouId);
        if (pl == null) {
            throw new BusinessException(ErrorCode.PICKINGLIST_CODE_ERROR);
        } else {
            PickingList rp = new PickingList();
            rp.setCode(pl.getCode());
            rp.setId(pl.getId());
            rp.setCheckMode(pl.getCheckMode());
            return rp;
        }
    }

    /**
     * 按批次重置周转箱状态
     * 
     * @param pickId
     * @param userId
     */
    public void resetTurnoverBoxStatusByPickingList(Long pickId, Long userId) {
        List<WhContainer> wcList = whContainerDao.getWcByPickId(pickId);
        if (wcList != null) {
            for (WhContainer wc : wcList) {
                resetTurnoverBoxStatus(wc.getId(), userId);
            }

        }
    }

    /**
     * 重置周转箱状态
     * 
     * @param boxCode
     * @param userId
     */
    public String resetTurnoverBoxStatusByBoxCode(String boxCode, Long userId) {
        WhContainer wc = whContainerDao.getWhContainerByCode(boxCode);
        if (wc != null) {
            resetTurnoverBoxStatusPdaShelves(wc.getId(), userId);
        } else {
            return "无此周转箱!";
        }
        return null;
    }

    /**
     * 配货批次下订单有任意一条匹配到可用的优先级最高的播种墙推荐规则，则配货批次下所有周转箱应用该播种墙推荐规则制定的播种墙
     * 
     * @param staList
     * @param ouId
     * @return
     */
    public String checkBozRole(List<StockTransApplication> staList, Long ouId, Long pkId) {
        String bozCode = "";
        List<CheckingSpaceRoleCommand> roleList = checkingSpaceRoleDao.findCheckingSpaceList(ouId, 2, new BeanPropertyRowMapper<CheckingSpaceRoleCommand>(CheckingSpaceRoleCommand.class));
        for (CheckingSpaceRoleCommand role : roleList) {
            for (StockTransApplication sta : staList) {
                StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                if (role.getOwner() != null && !role.getOwner().equals(sta.getOwner())) {
                    continue;
                }
                if (role.getToLocation() != null && !role.getToLocation().equals(sta.getToLocation())) {
                    continue;
                }
                if (role.getLpcode() != null && !role.getLpcode().equals(info.getLpCode())) {
                    continue;
                }
                if (role.getCity() != null && StringUtils.hasLength(info.getCity())) {
                    // 截取市
                    String infoCity = info.getCity();
                    // if (infoCity.substring(infoCity.length() - 1, infoCity.length()).equals("市"))
                    // {
                    // infoCity = infoCity.substring(0, infoCity.length() - 1);
                    // }
                    // 非优先发货城市
                    if (role.getCity().contains("opposite")) {
                        if (role.getCity().contains(infoCity)) {
                            continue;
                        }
                    } else if (role.getCity().contains(",") && !role.getCity().contains("opposite")) {
                        // 所有优先发货城市
                        if (!role.getCity().contains(infoCity)) {
                            continue;
                        }
                    } else {
                        // 优先发货城市
                        if (!role.getCity().equals(infoCity)) {
                            continue;
                        }
                    }
                }
                // QS处理逻辑
                if (role.getIsQs() != null && (role.getSpecialType() == null || role.getSpecialType() != 1)
                        && (((sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging() && sta.getSpecialType() == null) && !role.getIsQs()) || ((sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) && role.getIsQs()))) {
                    continue;
                }
                // 特殊处理逻辑
                if (role.getSpecialType() != null
                        && (role.getIsQs() == null || !role.getIsQs())
                        && (((sta.getSpecialType() != null && sta.getSpecialType().getValue() == 1 && (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging())) && role.getSpecialType() != 1) || (sta.getSpecialType() == null && role
                                .getSpecialType() == 1))) {
                    continue;
                }
                // QS处理逻辑 && 特殊处理逻辑
                if (role.getIsQs() != null && role.getSpecialType() != null && role.getIsQs() && role.getSpecialType() == 1) {
                    if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging() || sta.getSpecialType() == null) {
                        continue;
                    }
                }
                bozCode = role.getCheckingAreaCode();
                break;
            }
            if (!"".equals(bozCode)) {
                break;
            }
        }
        try {
            List<Long> staIdList = new ArrayList<Long>();
            for (StockTransApplication sta : staList) {
                // 如果作业单集货编码为空，则再次匹配
                if (!StringUtils.hasLength(sta.getShipmentCode())) {
                    staIdList.add(sta.getId());
                }
            }
            if (staIdList != null && staIdList.size() > 0) {
                PickingList t = pickingListDao.getByPrimaryKey(pkId);
                warehouseOutBoundManager.marryStaShiipingCode(t.getCheckMode(), t.getCode(), staIdList, ouId);
            }
        } catch (Exception e) {
            log.error(pkId + " marryStaShiipingCodeAgain is error.....");
        }
        return bozCode;
    }

    /**
     * 获取已经占用周转箱的组织
     * 
     * @return
     */
    public List<OperationUnit> findOccupyOu() {
        return whContainerDao.findOccupyOu(new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
    }

    @Override
    public void releaseTurnBoxByPc(String plCode, Long userId) {
        PickingList p = pickingListDao.getByCode(plCode);
        if (p == null || !p.getCheckMode().equals(PickingListCheckMode.DEFAULE)) {
            throw new BusinessException(ErrorCode.RPC_ORDER_ERROR, new Object[] {plCode});
        } else {
            resetTurnoverBoxStatusByPickingList(p.getId(), userId);
        }
    }

    /**
     * 缺失体积或重量的商品是否已经维护好了
     */
    public void verifyThreeDimensionalOver() {
        // mongoOperation.findAll(InboundSkuWeightCheck.class);
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_YEAR, -15);
        List<InboundSkuWeightCheck> iswcList = mongoOperation.find(new Query(Criteria.where("createTime").lt(rightNow.getTime())), InboundSkuWeightCheck.class);

        if (iswcList == null || iswcList.size() == 0) {
            return;
        }

        for (InboundSkuWeightCheck iswc : iswcList) {
            /*
             * for(SkuCommand skuC:iswc.getSkuList()) { Sku sku =
             * skuDao.getByPrimaryKey(skuC.getId());
             * 
             * if (sku.getLength() == null || sku.getLength().compareTo(BigDecimal.ZERO) == 0 ||
             * sku.getWidth() == null || sku.getWidth().compareTo(BigDecimal.ZERO) == 0 ||
             * sku.getHeight() == null || sku.getHeight().compareTo(BigDecimal.ZERO) == 0 ||
             * sku.getGrossWeight() == null || sku.getGrossWeight().compareTo(BigDecimal.ZERO) == 0)
             * {
             * 
             * } else { mongoOperation.remove(iswc); }
             * 
             * }
             */

            List<Sku> skuList = skuDao.findSkuThreeDimensionalIsNullByStaId(iswc.getStaId(), new BeanPropertyRowMapper<Sku>(Sku.class));
            if (skuList == null) {
                mongoOperation.remove(iswc);
            }
        }

    }

    /**
     * 查询商品存在三维信息缺失的单子
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     * @throws Exception
     */
    public Pagination<StockTransApplicationCommand> findThreeDimensional(int start, int pageSize, StockTransApplicationCommand sta) throws Exception {
        Set<Long> staIdSet = findThreeDimensionalOrder(sta);
        String staCode = StringUtil.isEmpty(sta.getCode()) ? null : sta.getCode();
        String slipCode = StringUtil.isEmpty(sta.getSlipCode()) ? null : sta.getSlipCode();
        String slipCode1 = StringUtil.isEmpty(sta.getSlipCode1()) ? null : sta.getSlipCode1();
        Integer staType = StringUtil.isEmpty(sta.getStrType()) ? null : Integer.parseInt(sta.getStrType());
        String owner = StringUtil.isEmpty(sta.getOwner()) ? null : sta.getOwner();
        Integer staStatus = sta.getIntStaStatus();

        Pagination<StockTransApplicationCommand> stacList =
                staDao.findThreeDimensional(start, pageSize, staIdSet, staCode, slipCode, slipCode1, staType, owner, staStatus, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        if (stacList != null && stacList.getItems() != null && stacList.getItems().size() > 0) {
            for (StockTransApplicationCommand staCommand : stacList.getItems()) {
                InboundSkuWeightCheck isc = mongoOperation.findOne(new Query(Criteria.where("staId").in(staCommand.getId())), InboundSkuWeightCheck.class);
                staCommand.setCreateTime(isc.getCreateTime());
                if ("15".equals(staCommand.getStatusName())) {
                    staCommand.setStatusName("取消未处理");
                } else if ("17".equals(staCommand.getStatusName())) {
                    staCommand.setStatusName("取消已处理");
                } else if ("10".equals(staCommand.getStatusName())) {
                    staCommand.setStatusName("已完成");
                } else {
                    staCommand.setStatusName("未完成");
                }

            }
        }
        return stacList;
    }

    /**
     * 根据作业单查找缺失三维的商品
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @return
     */
    public Pagination<SkuCommand> findThreeDimensionalSkuInfo(int start, int pageSize, Long staId) {
        InboundSkuWeightCheck iswc = mongoOperation.findOne(new Query(Criteria.where("id").is(staId)), InboundSkuWeightCheck.class);
        Set<Long> skuIdSet = new HashSet<Long>();
        for (SkuCommand sku : iswc.getSkuList()) {
            skuIdSet.add(sku.getId());
        }

        return skuDao.findThreeDimensionalSkuInfo(start, pageSize, staId, skuIdSet, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

    public List<SkuCommand> findThreeDimensionalSkuInfoList(StockTransApplicationCommand sta) throws Exception {
        Set<Long> staIdSet = findThreeDimensionalOrder(sta);
        List<SkuCommand> skuList = skuDao.findThreeDimensionalSkuInfoList(staIdSet, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));

        return skuList;
    }

    public Set<Long> findThreeDimensionalOrder(StockTransApplicationCommand sta) throws Exception {
        Query q = new Query();
        Criteria c = Criteria.where("ouId").in(sta.getOuId());
        if (sta != null) {
            String startDate = sta.getStartCreateTime1();
            String endDateStr = sta.getEndCreateTime1();
            if (!StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDateStr)) {
                c.and("createTime").gte(DateUtil.parse(startDate, DateUtil.PATTERN_NORMAL));
            } else if (StringUtil.isEmpty(startDate) && !StringUtil.isEmpty(endDateStr)) {
                c.and("createTime").lt(DateUtil.parse(endDateStr, DateUtil.PATTERN_NORMAL));
            } else if (!StringUtil.isEmpty(startDate) && !StringUtil.isEmpty(endDateStr)) {
                c.andOperator(Criteria.where("createTime").gte(DateUtil.parse(startDate, DateUtil.PATTERN_NORMAL)), Criteria.where("createTime").lt(DateUtil.parse(endDateStr, DateUtil.PATTERN_NORMAL)));

            }

        }
        q.addCriteria(c);
        // 查询mongodb数据
        List<InboundSkuWeightCheck> iswcList = mongoOperation.find(q, InboundSkuWeightCheck.class);
        if (iswcList == null || iswcList.size() == 0) {
            return null;
        }

        Set<Long> staIdSet = new HashSet<Long>();
        for (int i = 0; i < iswcList.size(); i++) {
            staIdSet.add(iswcList.get(i).getStaId());
            /*
             * if (staIdSet.size() >= 999) { break; }
             */
        }
        return staIdSet;
    }

    @Override
    public Pagination<MongDbNoThreeDimensional> findNoThreeDimensional(int start, int pageSize, String pinkingCode, Date time1, Date time2, String store, Long ouId) throws Exception {
        Pagination<MongDbNoThreeDimensional> pagination = new Pagination<MongDbNoThreeDimensional>();
        Query q = new Query();
        Criteria c = Criteria.where("ouId").in(ouId);
        if (null != pinkingCode && !"".equals(pinkingCode)) {
            c.and("pinkingCode").is(pinkingCode);
        }
        if (null != time1 && null == time2) {
            c.and("createTime").gte(time1);
        } else if (null != time2 && null == time1) {
            c.and("createTime").lte(time2);
        } else if (null != time1 && null != time2) {
            c.andOperator(Criteria.where("createTime").gte(time1), Criteria.where("createTime").lte(time2));
        }
        q.addCriteria(c);
        List<MongDbNoThreeDimensional> list = mongoOperation.find(q, MongDbNoThreeDimensional.class);
        pagination.setItems(list);
        return pagination;
    }

    @Override
    public Pagination<SkuCommand> findNoThreeDimensionalSkuInfo(int start, int pageSize, Long plId) {
        return skuDao.findNoThreeDimensionalSkuInfo(start, pageSize, plId, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

    @Override
    public List<SkuCommand> findNoThreeDimensionalSkuInfoList(Long ouId) throws Exception {
        Query q = new Query();
        Criteria c = Criteria.where("ouId").in(ouId);
        q.addCriteria(c);
        List<MongDbNoThreeDimensional> list = mongoOperation.find(q, MongDbNoThreeDimensional.class);
        List<Long> pinkingListIdList = new ArrayList<Long>();
        for (int i = 0; i < list.size(); i++) {
            pinkingListIdList.add(list.get(i).getPinkingListId());
        }
        List<SkuCommand> skuList = skuDao.findNoThreeDimensionalSkuInfoList(pinkingListIdList, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        return skuList;
    }
}
