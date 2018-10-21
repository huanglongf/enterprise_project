package com.jumbo.wms.manager.outbound;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SkuDeclarationDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaSkuOriginDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuDeclarationCommand;
import com.jumbo.wms.model.baseinfo.StaSkuOrigin;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mongodb.MSRCustomCardFeedBack;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryOccupyCommand;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoStatus;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.web.commond.CheckInfoCommand;
import com.jumbo.wms.web.commond.OrderCheckCommand;
import com.jumbo.wms.web.commond.OrderCheckLineCommand;
import com.jumbo.wms.web.commond.PickingListCommand;
import com.jumbo.wms.web.commond.SkuCommand;
import com.jumbo.wms.web.commond.TransWeightOrderCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 
 * @author sjk
 * 
 */
@Transactional
@Service("outboundInfoManager")
public class OutboundInfoManagerImpl extends BaseManagerImpl implements OutboundInfoManager {

    private static final long serialVersionUID = -5071490815414973766L;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private WhContainerDao whContainerDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private HubWmsManager hubWmsManager;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SkuDao skuDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private SkuDeclarationDao skuDeclarationDao;
    @Autowired
    private StaSkuOriginDao staSkuOriginDao;

    @Override
    public PickingListCommand getOutboundCheckInfo(String code, Integer typeFlag) {
        PickingListCommand pc = null;
        List<CheckInfoCommand> list = null;
        Boolean isHaveReportMissing = null;
        // 配货单
        if (typeFlag == 1) {
            list = pickingListDao.findCheckInfoByPickingListCode(code, new BeanPropertyRowMapper<CheckInfoCommand>(CheckInfoCommand.class));
            isHaveReportMissing = pickingListDao.findIsHaveReportMissingByPickingListCode(code, new SingleColumnRowMapper<Boolean>(Boolean.class));
        }
        // 周转箱
        else if (typeFlag == 2) {
            list = pickingListDao.findCheckInfoByContainerCode(code, new BeanPropertyRowMapper<CheckInfoCommand>(CheckInfoCommand.class));
            isHaveReportMissing = pickingListDao.findIsHaveReportMissingByContainerCode(code, new SingleColumnRowMapper<Boolean>(Boolean.class));
        }
        // 作业单
        else {

            list = pickingListDao.findCheckInfoBySlipCode(code, new BeanPropertyRowMapper<CheckInfoCommand>(CheckInfoCommand.class));
            isHaveReportMissing = pickingListDao.findIsHaveReportMissingBySlipCode(code, new SingleColumnRowMapper<Boolean>(Boolean.class));
            wareHouseManager.deletePackageInfo(list.get(0).getStaCode());
        }
        pc = getCheckInfo(list, isHaveReportMissing);
        return pc;
    }

    /**
     * @param list
     * @return
     */
    private PickingListCommand getCheckInfo(List<CheckInfoCommand> list, Boolean isHaveReportMissing) {
        PickingListCommand pc = new PickingListCommand();
        pc.setIsHaveReportMissing(isHaveReportMissing);
        Map<String, OrderCheckCommand> ocM = new HashMap<String, OrderCheckCommand>();
        Map<String, OrderCheckLineCommand> oclM = new HashMap<String, OrderCheckLineCommand>();
        Map<String, Map<String, String>> skb = new HashMap<String, Map<String, String>>();// 用来存放key对应的商品的条形码集合，用map数据结果避免数据重复
        for (CheckInfoCommand cic : list) {
            if (pc.getPlId() == null) {
                pc.setPlId(cic.getPlId());
                pc.setPlCode(cic.getPlCode());
                pc.setStatus(cic.getStatus());
                pc.setIsPostpositionExpressBill(cic.getIsPostpositionExpressBill());
                pc.setOrders(new ArrayList<OrderCheckCommand>());
            }
            String staCode = cic.getStaCode();
            if (ocM.get(staCode) == null) {
                OrderCheckCommand oc = new OrderCheckCommand();
                oc.setIdx1(cic.getIdx1());
                oc.setSkuQty(cic.getSkuQty());
                oc.setIdx2(cic.getIdx2());
                oc.setIntStatus(cic.getIntStatus());
                oc.setLpcode(cic.getLpcode());
                oc.setOrderCode(cic.getOrderCode());
                oc.setOwner(cic.getOwner());
                oc.setPgIndex(cic.getPgIndex());
                oc.setSlipCode1(cic.getSlipCode1());
                oc.setStaCode(cic.getStaCode());
                oc.setStaId(cic.getStaId());
                oc.setStatus(cic.getStaStatus());
                oc.setTransNo(cic.getTransNo());
                oc.setCmCode(cic.getCmCode());
                oc.setPickingType(cic.getPickingType());
                oc.setLines(new ArrayList<OrderCheckLineCommand>());
                ocM.put(staCode, oc);
                StockTransApplication sta = staDao.getByCode(staCode);
                sta.setIsChecking(true);
            }
            Long uniqueId = cic.getUniqueId();
            String skuCode = cic.getCode();
            String skbCode = cic.getBcCode();
            if (oclM.get(staCode + "_" + uniqueId) == null) {
                OrderCheckLineCommand ocl = new OrderCheckLineCommand();
                ocl.setUniqueId(uniqueId);
                ocl.setcQty(cic.getcQty());
                ocl.setQty(cic.getQty());
                ocl.setIsGift(cic.getIsGift());// 是否是特殊处理
                SkuCommand sku = new SkuCommand();
                sku.setBarcode(cic.getBarcode());
                sku.setCode(cic.getCode());
                sku.setName(cic.getName());
                sku.setColorSize(cic.getColorSize());
                sku.setKeyProp(cic.getKeyProp());
                sku.setIsSn(cic.getIsSn());
                sku.setSupCode(cic.getSupCode());
                // 初始化每个SKU的条形码列表，默认添加主条码到列表里面
                List<String> bl = new ArrayList<String>();
                bl.add(cic.getBarcode());
                sku.setBarcodes(bl);
                // 初始化这个sku对应的条形码集合，
                Map<String, String> list1 = new HashMap<String, String>();
                list1.put(cic.getBarcode(), cic.getBarcode());
                skb.put(skuCode, list1);
                if (skbCode != null) {
                    // 如果多条码，则将当前行的多条码添加到条码列表
                    if (skb.get(skuCode).get(skbCode) == null) {
                        skb.get(skuCode).put(skbCode, skbCode);
                        // 此处用到新的List覆盖原来SKU对应的barcode List列表而不直接用addALL是为了避免重复添加
                        List<String> al = new ArrayList<String>();
                        al.addAll(skb.get(skuCode).keySet());
                        sku.setBarcodes(al);
                    }
                }
                ocl.setSku(sku);
                oclM.put(staCode + "_" + uniqueId, ocl);
            }
            // 如果明细行已经记录过但是该行有多条记录则说明该行对应的商品存在多条码，此处逻辑主要为了更新多条码商品的条形码列表
            else {
                OrderCheckLineCommand ocl = oclM.get(staCode + "_" + uniqueId);
                SkuCommand sku = ocl.getSku();
                skb.get(skuCode).put(skbCode, skbCode);
                // 此处用到新的List覆盖原来SKU对应的barcode List列表而不直接用addALL是为了避免重复添加
                List<String> al = new ArrayList<String>();
                al.addAll(skb.get(skuCode).keySet());
                sku.setBarcodes(al);
            }

        }
        for (String key : ocM.keySet()) {
            OrderCheckCommand oc = ocM.get(key);
            for (String key1 : oclM.keySet()) {
                if (key1.startsWith(key)) {
                    oc.getLines().add(oclM.get(key1));
                }
            }
            // 因为oc.getLines（）是通过Map添加进来的，所以不保证顺序，此处进行排序操作，为了界面展示需要
            Collections.sort(oc.getLines());
            pc.getOrders().add(oc);
        }
        // 因为pc.getOrders()是通过Map添加进来的，所以不保证顺序，此处进行排序操作，为了界面展示需要
        Collections.sort(pc.getOrders());
        // 如果是保税仓，需要查询商品产地
        /*
         * PickingList pl = pickingListDao.getByPrimaryKey(pc.getPlId()); Warehouse w =
         * warehouseDao.getByOuId(pl.getWarehouse().getId()); if (w.getIsBondedWarehouse() != null
         * && w.getIsBondedWarehouse()) { Map<String, Set<String>> originMap =
         * findSkuOriginByPlId(pl.getId()); if (originMap != null) { pc.setOriginMap(originMap); } }
         */

        return pc;
    }

    /**
     * 获取商品原产地
     * 
     * @return
     */
    public Map<String, Set<String>> findSkuOriginByPlId(Long plId) {
        // 如果是保税仓，需要查询商品产地
        Map<String, Set<String>> originMap = new HashMap<String, Set<String>>();
        List<SkuDeclarationCommand> sdcList = skuDeclarationDao.findSkuOriginByPlId(plId, new BeanPropertyRowMapper<SkuDeclarationCommand>(SkuDeclarationCommand.class));
        if (sdcList == null || sdcList.size() == 0) {
            return null;
        }
        for (SkuDeclarationCommand sdc : sdcList) {
            Set<String> originSet = originMap.get(sdc.getSkuCode());
            if (originSet == null) {
                originSet = new HashSet<String>();
                originMap.put(sdc.getSkuCode(), originSet);
            }
            originSet.add(sdc.getOrigin());
        }
        return originMap;
    }

    @Override
    public TransWeightOrderCommand getOrderInfo(String transNo, Boolean isSpPacking) {
        TransWeightOrderCommand cmd = packageInfoDao.findInfoByTransNo(transNo, isSpPacking, new BeanPropertyRowMapper<TransWeightOrderCommand>(TransWeightOrderCommand.class));
        return cmd;
    }

    @Override
    public Integer checkOrderCode(String orderCode, Long ouId) {
        // 返回单据类型 1：配货单 2：周转箱 3：作业单
        // 根据单号和状态，以及类型查询配货清单，要求必须是配货中或者部分出库的单件核对配货清单
        PickingList pl = pickingListDao.getCheckOrderByCode(orderCode, ouId, new BeanPropertyRowMapper<PickingList>(PickingList.class));
        if (pl != null) {
            return 1;
        }
        WhContainer wc = whContainerDao.getCheckOrderByCode(orderCode, ouId, new BeanPropertyRowMapper<WhContainer>(WhContainer.class));
        if (wc != null) {
            return 2;
        }
        StockTransApplication sta = staDao.getCheckOrderByCode(orderCode, ouId, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
        if (sta != null) {
            return 3;
        }
        return null;
    }


    @Override
    public void checkSnStatus(Long uniqueId, String sn) {
        SkuSn sn1 = skuSnDao.findIfCanUseBySn(uniqueId, sn, new BeanPropertyRowMapper<SkuSn>(SkuSn.class));
        if (sn1 == null) {
            // 如果没有可用的SN，抛出对应异常
            throw new BusinessException(ErrorCode.OUT_SN_ERROR);
        }
        // 星巴克MSR卡出库校验绑定信息
        SkuSn sn2 = skuSnDao.getByPrimaryKey(sn1.getId());
        if (sn2 != null && sn2.getSku() != null && sn2.getSku().getSpType() != null && sn2.getSku().getSpType().getValue() == 7) {
            StockTransApplication sta = staDao.getByPrimaryKey(sn2.getStaId());
            // Long staId = staLineDao.findStaId(uniqueId, new
            // SingleColumnRowMapper<Long>(Long.class));
            List<MSRCustomCardFeedBack> list = mongoOperation.find(new Query(Criteria.where("sn").is(sn2.getSn())).with(new org.springframework.data.domain.Sort(Direction.ASC, "createDate")), MSRCustomCardFeedBack.class);
            if (sta != null && list != null && !list.isEmpty() && !org.apache.commons.lang3.StringUtils.equals(sta.getCode(), list.get(0).getStaCode())) {
                // 出库SN和绑定的作业单不一致，抛出对应异常
                throw new BusinessException(ErrorCode.SN_NOT_MATCH_STA, new Object[] {sn});
            }
        }
    }

    @Override
    public void doCheckBySta(OrderCheckCommand checkOrder, Long userId, Long ouId, String isSkipWeight, String staffCode2) {
        if (checkOrder.getPickingType() == 1) {
            String sn = null;
            if (null != checkOrder.getLines() && checkOrder.getLines().size() > 0) {
                sn = checkOrder.getLines().get(0).getSns().size() > 0 ? checkOrder.getLines().get(0).getSns().get(0) : null;
            }
            wareHouseManager.checkSingleSta(checkOrder.getStaId(), checkOrder.getTransNo(), null, sn, userId);
        } else {
            List<PackageInfo> packageInfos = new ArrayList<PackageInfo>();
            PackageInfo pi = new PackageInfo();
            pi.setTrackingNo(checkOrder.getTransNo());
            packageInfos.add(pi);
            List<String> sns = new ArrayList<String>();
            for (int i = 0; i < checkOrder.getLines().size(); i++) {
                for (int j = 0; j < checkOrder.getLines().get(i).getSns().size(); j++) {
                    sns.add(checkOrder.getLines().get(i).getSns().get(j));
                }
            }
            wareHouseManager.staSortingCheck(sns, null, packageInfos, checkOrder.getStaId(), ouId, null, userId);
        }
        /**
         * 跳过称重出库逻辑
         */
      //Map<String, Object> rs = wareHouseManager.salesStaOutBoundHand(checkOrder.getStaId(),userId, false,ouId, "777777523666", null, null, true,"1");
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        StockTransApplication sta=staDao.getByPrimaryKey(checkOrder.getStaId());
        if(warehouse.getIsSkipWeight() !=null && warehouse.getIsSkipWeight() && "1".equals(isSkipWeight) && 
                StockTransApplicationStatus.CHECKED.equals(sta.getStatus())){//跳过称重出库
            List<StaAdditionalLine> saddlines = new ArrayList<StaAdditionalLine>();
            if (staffCode2 != null && !"".equals(staffCode2)) {
            Sku sku = new Sku();
                sku.setBarCode(staffCode2);
            StaAdditionalLine line = new StaAdditionalLine();
            line.setSku(sku);
                line.setQuantity(1L);
            saddlines.add(line);
            }
            Map<String, Object> rs = wareHouseManager.salesStaOutBoundHand(checkOrder.getStaId(), userId, false, ouId, checkOrder.getTransNo(), null, saddlines, true, "1");
            
        }
        
        Warehouse w = warehouseDao.getByOuId(ouId);
        if (w.getIsBondedWarehouse() != null && w.getIsBondedWarehouse() && !StringUtil.isEmpty(checkOrder.getSkuOrigin())) {
            addStaSkuOrigin(checkOrder.getSkuOrigin(), checkOrder.getStaId());

        }
    }

    /**
     * 查询当前未交接单据数
     */
    @Override
    public List<StaDeliveryInfo> getNeedHandOverOrderSummary(Long ouId, Long userId) {
        List<StaDeliveryInfo> list = staDeliveryInfoDao.findNeedHandOverOrderSummary(ouId, userId, new BeanPropertyRowMapper<StaDeliveryInfo>(StaDeliveryInfo.class));
        return list;
    }


    @Override
    public void recordWeightForTrackingNo(String transNo, BigDecimal weight, String staffCode) {
        PackageInfo pi = packageInfoDao.findByTrackingNo(transNo);
        if (pi == null || !pi.getStatus().equals(PackageInfoStatus.CREATED)) {
            throw new BusinessException(ErrorCode.NOW_WEIGHT_TRANS_ERROR);// 当前称重单号状态不对！
        }
        pi.setWeight(weight);
        pi.setLastModifyTime(new Date());
        if (staffCode != null && StringUtils.hasText(staffCode)) {
            StockTransApplication sta = staDao.getByPrimaryKey(pi.getStaDeliveryInfo().getId());
            if (sta == null || !sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                throw new BusinessException(ErrorCode.STA_NOT_EXISTS_OR_ERROR);// 关键作业单不存在或者状态不对
            }
            Long companyOuId = sta.getMainWarehouse().getParentUnit().getParentUnit().getId();
            Long customerId = warehouseDao.getByOuId(sta.getMainWarehouse().getId()).getCustomer().getId();
            Inventory inv = wareHouseManager.getAddlineSkuCache(staffCode, companyOuId, customerId);
            if (inv == null) {
                throw new BusinessException(ErrorCode.OUT_BOUND_NEED_WRAP_STUFF_NOT_FOUND, new Object[] {staffCode});
            }
            StaAdditionalLine addline = new StaAdditionalLine();
            addline.setSku(inv.getSku());
            addline.setSta(sta);
            addline.setCreateTime(new Date());
            addline.setOwner(sta.getOwner());
            addline.setLpcode(sta.getStaDeliveryInfo().getLpCode());
            addline.setTrackingNo(transNo);
            addline.setQuantity(1L);
            addline.setSkuCost(inv.getSkuCost());
            staAdditionalLineDao.save(addline);
            pi.setSku(inv.getSku());
        }
    }


    @Override
    public User findUserPrivilegeInfo(Long id) {
        return userDao.getByPrimaryKey(id);
    }


    @Override
    public void cancelStaLine(List<String> lines, Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        for (String s : lines) {
            staLineDao.updateStaLineIsCancel(true, 0L, s, id);
            StaLine staLine = staLineDao.findCancelLineByStaIdAndLineNo(id, s, new BeanPropertyRowMapper<StaLine>(StaLine.class));
            StaLine sl = staLineDao.getByPrimaryKey(staLine.getId());
            sta.setSkuQty(sta.getSkuQty() - sl.getOrderQty());
            sta.setTotalActual(sta.getTotalActual().subtract(sl.getTotalActual()));
            sta.setOrderTotalActual(sta.getTotalActual());
            sta.setIsHaveReportMissing(true);
        }
        if (sta.getSkuQty().equals(0L)) {
            Map<String, String> map = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
            hubWmsManager.cancelSalesSta(map.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
        }
    }

    @Override
    public void partlySalesOutbound(List<Long> idList, Long id, Long ouId) {
        try {
            Map<String, String> map = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
            Warehouse w = warehouseDao.getByOuId(ouId);
            if (!w.getCustomer().getCode().equals(map.get(Constants.AD_CUSTOMER_CODE))) {
                throw new BusinessException(ErrorCode.AD_NOT_AD_CUS);
            } else {
                for (Long lId : idList) {
                    Integer count = staLineDao.findResultForOperation(lId, new SingleColumnRowMapper<Integer>(Integer.class));
                    StaLine staLine = staLineDao.getByPrimaryKey(lId);
                    StockTransApplication sta = staLine.getSta();
                    if (count == 0) {
                        staLine.setQuantity(0L);
                        hubWmsManager.cancelSalesSta(map.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), false);
                    } else {
                        if (map.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner())) {
                            sta.setSkuQty(sta.getSkuQty() - staLine.getQuantity());
                            sta.setTotalActual(sta.getTotalActual().subtract(staLine.getTotalActual()));
                            sta.setOrderTotalActual(sta.getTotalActual());
                            // staLine.setIsCancel(true);
                            staLine.setQuantity(0L);
                            sta.setIsHaveReportMissing(true);
                            staLineDao.flush();
                        } else {
                            throw new BusinessException(ErrorCode.AD_PARTLY_PROHIBIT);
                        }
                    }
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.AD_PARTLY_OUTBOUND);
        }
    }

    @Override
    public void reportMissingWhenCheck(List<Long> idList, Long id, Long ouId) {
        try {
            Map<String, String> map = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
            Warehouse w = warehouseDao.getByOuId(ouId);
            if (!w.getCustomer().getCode().equals(map.get(Constants.AD_CUSTOMER_CODE))) {
                throw new BusinessException(ErrorCode.AD_NOT_AD_CUS);
            } else {
                for (Long lId : idList) {
                    StaLine staLine = staLineDao.getByPrimaryKey(lId);
                    StockTransApplication sta = staLine.getSta();
                    sta.setIsHaveReportMissing(true);
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.AD_REPORT_MISSING_ERROR);
        }
    }

    /**
     *
     */
    @Override
    public Pagination<StockTransApplicationCommand> getAllOccupiedFailureOrder(int start, int pageSize, Long ouId, Date beginTime, Date endTime, StockTransApplicationCommand sta, Sort[] sorts) {
        String owner = null;
        String slipCode = null;
        Integer intStatus = null;
        if (sta != null) {
            if (StringUtils.hasText(sta.getOwner())) {
                owner = sta.getOwner();
            }
            if (StringUtils.hasText(sta.getSlipCode())) {
                slipCode = sta.getSlipCode();
            }
            intStatus = sta.getIntStaStatus();
        }
        return staDao.getAllOccupiedFailureOrder(start, pageSize, ouId, beginTime, endTime, intStatus, owner, slipCode, sorts, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public void occupiedByIdForAD(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        Warehouse w = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        List<StaLine> ll = staLineDao.findByStaId(id);
        boolean flag = false;
        boolean haveLine = false;
        for (StaLine line : ll) {
            if (!(line.getQuantity().compareTo(0L) == 0)) {
                List<InventoryOccupyCommand> list =
                        inventoryDao.findSalesOutboundToOccupyInventoryForAd(w.getSaleOcpType() == null ? 1 : w.getSaleOcpType().getValue(), line.getId(), null, new BeanPropertyRowMapperExt<InventoryOccupyCommand>(InventoryOccupyCommand.class));
                Long sum = 0L;
                for (InventoryOccupyCommand ic : list) {
                    sum += ic.getQuantity();
                }
                if (sum.compareTo(line.getQuantity()) < 0) {
                    flag = true;
                    continue;
                } else {
                    haveLine = true;
                    Long qty = line.getQuantity();
                    for (InventoryOccupyCommand ic : list) {
                        if (qty > 0) {
                            if (ic.getQuantity().compareTo(qty) > 0) {
                                inventoryDao.occupyInvById(ic.getId(), sta.getCode(), qty);
                                wareHouseManager.saveInventoryForOccupy(ic, ic.getQuantity().longValue() - qty.longValue(), null);
                                qty = 0L;
                            } else {
                                inventoryDao.occupyInvById(ic.getId(), sta.getCode(), ic.getQuantity());
                                qty = qty - ic.getQuantity();
                            }
                        } else {
                            break;
                        }
                    }
                }

            }
        }
        if (haveLine) {
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
            BigDecimal ttid = null;
            // 销售出库占用
            ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
            if (ttid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), null, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
            stvLineDao.createByStaId(sta.getId());
        }
        if (!flag) {
            staDao.updateStaStatusByid(id, StockTransApplicationStatus.OCCUPIED.getValue());
        } else {
            staDao.updateStaStatusByid(id, StockTransApplicationStatus.FROZEN.getValue());
        }
        List<StaLine> lineList = staLineDao.findIfExistsDiffer(sta.getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
        if (lineList != null && lineList.size() > 0) {
            throw new BusinessException(ErrorCode.OCCUPATION_ERROR_OCCUR);
        }
    }

    @Override
    public void reOccupiedForAd(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        staDao.releaseInventory(sta.getCode());
        stvLineDao.deleteStvLineBySta(sta.getId());
        stvLineDao.flush();
        stvDao.deleteStvBySta(sta.getId());
        Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
        if (mp.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner())) {
            occupiedByIdForAD(id);
        } else {
            wareHouseManager.createStvByStaId(id, null, null, false);
        }
    }


    @Override
    public void cancelAdOrderById(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
        hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), false);
    }

    @Override
    public void partySendAdOrderById(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
        if (mp.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner())) {
            staLineDao.updateLineToPartySend(id, sta.getCode());
            staDao.updateStaToPartySend(id);
        } else {
            throw new BusinessException(ErrorCode.AD_PARTLY_PROHIBIT);
        }
    }

    @Override
    public List<StaLineCommand> showOccDetail(Long id, Sort[] sorts) {
        return staLineDao.findShowOccDetail(id, sorts, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
    }

    /**
     * 保税仓新增产地记录
     */
    public void addStaSkuOrigin(String skuOrigin, Long staId) {
        if(StringUtil.isEmpty(skuOrigin)) {
            return;
        }
        Map<String, Map<String, Long>> map = new HashMap<String, Map<String, Long>>();
        String[] skuOriginS=skuOrigin.split(";");
        for (String str : skuOriginS) {
            if (!StringUtil.isEmpty(str)) {
                String[] origin = str.split("\\.");
                Map<String, Long> map2 = map.get(origin[0]);
                if (map2 == null) {
                    map2 = new HashMap<String, Long>();
                    map.put(origin[0], map2);
                }
                if (map2.get(origin[1]) == null) {
                    map2.put(origin[1], 1L);
                } else {
                    map2.put(origin[1], map2.get(origin[1]) + 1L);
                }
            }
        }
        for (String s : map.keySet()) {
            Map<String, Long> m = map.get(s);
            for (String key : m.keySet()) {
                StaSkuOrigin sso = new StaSkuOrigin();
                sso.setOrigin(key);
                sso.setQty(m.get(key));
                sso.setSkuCode(s);
                sso.setStaId(staId);
                staSkuOriginDao.save(sso);
            }
        }

    }
}
