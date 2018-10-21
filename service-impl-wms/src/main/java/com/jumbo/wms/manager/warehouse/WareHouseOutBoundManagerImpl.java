package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.ShippingPointDao;
import com.jumbo.dao.automaticEquipment.ShippingPointRoleLineDao;
import com.jumbo.dao.automaticEquipment.WhPickingBatchDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.pda.PickingLineDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.BiWarehouseAddStatusDao;
import com.jumbo.dao.warehouse.CreatePickingListSqlDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.OffLineTransPackageDao;
import com.jumbo.dao.warehouse.PackageSkuCounterDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.PickingListPackageDao;
import com.jumbo.dao.warehouse.SecKillSkuCounterDao;
import com.jumbo.dao.warehouse.SecKillSkuDao;
import com.jumbo.dao.warehouse.ShopStoreInfoDao;
import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhStaPickingListLogDao;
import com.jumbo.pms.model.command.ShippingPointKeepNumber;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.expressDelivery.TransOlManagerProxy;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.model.CreatePickingListByMQ;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsBoZRequest.WcsOrder;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsBoZRequest.WcsOrder.WcsSku;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsBoZRequest.WcsOrder.WcsSku.BarCodes;
import com.jumbo.wms.model.automaticEquipment.ShippingPoint;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.WhPickingBatchCommand2;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointRoleLineCommand;
import com.jumbo.wms.model.mongodb.MongDbNoThreeDimensional;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.msg.MongoDBMessageTest;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiWarehouseAddStatusCommand;
import com.jumbo.wms.model.warehouse.CreatePickingListSql;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.ParcelSortingMode;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.PickingListSqlCommand;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.SecKillSkuCounter;
import com.jumbo.wms.model.warehouse.ShopStoreInfo;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.SpecialSkuType;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.WhAddStatusMode;
import com.jumbo.wms.model.warehouse.WhAddTypeMode;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhStaPickingListLog;
import com.jumbo.wms.model.warehouse.test.PressureTestDto;

import cn.baozun.bh.util.JSONUtil;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelWriter;
import loxia.utils.DateUtil;

/**
 * 新配货逻辑接口实现
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("warehouseOutBoundManager")
public class WareHouseOutBoundManagerImpl extends BaseManagerImpl implements WareHouseOutBoundManager {

    /**
     * 
     */
    private static final long serialVersionUID = -8364865968162146014L;

    TimeHashMap<String, List<MessageConfig>> createPickingListCache = new TimeHashMap<String, List<MessageConfig>>();


    TimeHashMap<String, List<MessageConfig>> createPickingListCache1 = new TimeHashMap<String, List<MessageConfig>>();

    protected static final Logger log = LoggerFactory.getLogger(WareHouseOutBoundManagerImpl.class);
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private OffLineTransPackageDao offLineTransPackageDao;


    @Autowired
    private UserDao userDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private SecKillSkuCounterDao secKillSkuCounterDao;
    @Autowired
    private PackageSkuCounterDao packageSkuCounterDao;
    @Autowired
    private SecKillSkuDao secKillSkuDao;
    @Autowired
    private BiWarehouseAddStatusDao biDao;
    @Autowired
    private WhStaPickingListLogDao logDao;
    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private ChannelWhRefRefDao warehouseShopRefDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private ChannelGroupManager channelGroupManager;
    @Autowired
    private ShopStoreInfoDao shopStoreInfoDao;
    @Autowired
    private PickingListPackageDao pickingListPackageDao;
    @Autowired
    private TransOlManagerProxy transOlManagerProxy;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private ShippingPointRoleLineDao shippingPointRoleLineDao;
    @Resource(name = "pickingListMode1ExportWriter")
    private ExcelWriter pickingListMode1ExportWriter;
    @Resource(name = "deliveryInfoExportWriter")
    private ExcelWriter deliveryInfoExportWriter;
    @Autowired
    private WhPickingBatchDao whPickingBatchDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private PickingLineDao pickingLineDao;
    @Autowired
    private ShippingPointDao shippingPointDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private CreatePickingListSqlDao createPickingListSqlDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private RocketMQProducerServer producerServer;

    @Value("${mq.mq.mq.wms3.create.pickinglist}")
    public String WMS3_MQ_CREATE_PICKINGLIST;

    @Autowired
    private QstaManager qstaManager;

    @Autowired
    private BaseinfoManager baseinfoManager;

    @Autowired
    private CreatePickingListManagerProxy createPickingListManagerProxy;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private WareHouseManager wareHouseManager;



    public void deleteRultName(String id) {
        createPickingListSqlDao.deleteByPrimaryKey(Long.parseLong(id));
    }

    public CreatePickingListSql findRulaNameById(String id) {
        return createPickingListSqlDao.getByPrimaryKey(Long.parseLong(id));
    }

    public List<InventoryCommand> findStaIdByLoc(Long ouId, Long locId) {
        return inventoryDao.findStaIdByLocId(ouId, locId, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
    }

    public String createPickinglistBySingOrder(Long ouId, String district, String wh_district, Long userId) {
        List<Long> locList = new ArrayList<Long>();
        List<MessageConfig> configs = new ArrayList<MessageConfig>();
        Warehouse w = baseinfoManager.findWarehouseByOuId(ouId);
        Integer num = 0;
        if (null != w && null != w.getOutboundOrderNum() && !"".equals(w.getOutboundOrderNum())) {
            num = w.getOutboundOrderNum();
        }


        List<String> channelList = biChannelDao.findChannelByOuId(ouId, new SingleColumnRowMapper<String>(String.class));
        if (null != channelList && channelList.size() > 0) {
            if (log.isInfoEnabled()) {
                log.info("channelList" + channelList.size());
            }
            Long district1 = null;
            Long wh_district1 = null;
            if (null != district && !"".equals(district)) {
                district1 = Long.parseLong(district);
            }

            if (null != wh_district && !"".equals(wh_district)) {
                wh_district1 = Long.parseLong(wh_district);
            }
            List<InventoryCommand> invList = inventoryDao.findLocBySingOrderAndOuId(ouId, district1, wh_district1, num, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            if (null != invList && invList.size() > 0) {
                for (InventoryCommand list : invList) {
                    Long skuNum = inventoryDao.findSkuCountByLocId(ouId, list.getLocationId(), new SingleColumnRowMapper<Long>(Long.class));
                    if (1 == skuNum) {
                        if (log.isInfoEnabled()) {
                            log.info("skuNum" + skuNum);
                        }
                        skuNum = inventoryDao.findOwnerCountByLocId(ouId, list.getLocationId(), new SingleColumnRowMapper<Long>(Long.class));
                        if (1 == skuNum) {
                            if (log.isInfoEnabled()) {
                                log.info("skuNum1" + skuNum);
                            }
                            configs = getCreatePickingListCache1(MqStaticEntity.WMS3_MQ_CREATE_SING_ORDER);
                            if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                if (log.isInfoEnabled()) {
                                    log.info("createPickingToMQ" + list.getLocationId());
                                }
                                createPickingListToMq(ouId, list.getLocationId().toString(), userId);
                            } else {
                                locList.add(list.getLocationId());
                            }
                        }
                    }
                }
            }
        }
        if (null != locList && locList.size() > 0) {
            List<Long> staIdList = new ArrayList<Long>();
            for (Long loc : locList) {
                List<InventoryCommand> invlist = findStaIdByLoc(ouId, loc);
                if (null != invlist && invlist.size() > 0) {
                    staIdList.clear();
                    for (InventoryCommand inv : invlist) {
                        staIdList.add(inv.getStaId());
                    }
                }
                try {
                    if (null != staIdList && staIdList.size() > 0) {
                        Long msgId = createPickingListManagerProxy.createPickingListBySta(null, staIdList, ouId, userId, PickingListCheckMode.PICKING_CHECK, null, null, null, null, null, null, null, null, null, true, true, loc, false);
                        if (null != msgId) {
                            qstaManager.createPickingListToWCS(msgId);
                        }
                    }
                } catch (Exception e) {
                    log.error("createPickinglistBySingOrder" + e.toString());
                }
            }
        }
        return null;
    }

    public String addRultNameByOuId(String postshopInputName1, String postshopInputName, Boolean isMargeWhZoon, Integer mergePickZoon, Integer mergeWhZoon, String areaList, String AeraList1, String whAreaList, String whZoneList1, String ssList,
            String shoplist, String shoplist1, String cityList, String date, String date2, String date3, String date4, StockTransApplication sta, Long categoryId, Integer transTimeType, Integer isSn, Integer isNeedInvoice, Integer isCod,
            String priorityList, Long ouId, Long userId, String modeName, String ruleCode, String ruleName) {
        String msg = null;
        PickingListSqlCommand pickingListSqlCommand = new PickingListSqlCommand();
        CreatePickingListSql createPickingListSql = new CreatePickingListSql();
        List<CreatePickingListSql> createPickingListSqlList = createPickingListSqlDao.findRuleNameByOuId(ouId, ruleName, new BeanPropertyRowMapper<CreatePickingListSql>(CreatePickingListSql.class));
        if (null != createPickingListSqlList && createPickingListSqlList.size() > 0) {
            return "error";
        }

        String lpCode = null;
        String barCode = null;
        String code1 = null;
        String code2 = null;
        String code3 = null;
        String code4 = null;
        Integer status = null;
        Integer packingType = null; // 包装类型
        Integer specialType = null;
        Integer isSpecialPackaging = null;
        Integer isMergeInt = 1; // 忽略已选拣货区域查询条件
        Integer isMargeZoon = 1; // 忽略已选仓库区域查询条件
        // Boolean isSedKill = null;
        Integer pickingType = null;
        Long packageSkuId = null;
        Long skuQty = null;
        String skus = null;
        String toLocation = null;
        List<String> shopInnerCodes = null;
        List<String> codeList = null;
        List<String> pickZoneList = null;
        List<String> whZoneList = null;
        Long pickSubType = null;
        String isPreSale = null;// 是否是预售订单
        Integer orderType2 = null;// 订单类型
        pickingListSqlCommand.setPostshopInputName(postshopInputName);
        pickingListSqlCommand.setPostshopInputName1(postshopInputName1);
        pickingListSqlCommand.setIsNeedInvoice(isNeedInvoice);
        pickingListSqlCommand.setAeraList1(AeraList1);
        pickingListSqlCommand.setIsCod(isCod);
        pickingListSqlCommand.setCategoryId(categoryId);
        pickingListSqlCommand.setIsSn(isSn);
        if (StringUtils.hasText(shoplist)) {
            if (StringUtils.hasLength(shoplist)) {
                /*
                 * shopInnerCodes = new ArrayList<String>(); String[] shopArrays =
                 * shoplist.split("\\|"); for (String s : shopArrays) { shopInnerCodes.add(s); }
                 */
                pickingListSqlCommand.setShoplist(shoplist);
            }
        }
        if (StringUtils.hasText(shoplist1)) {
            if (StringUtils.hasLength(shoplist1)) {
                /*
                 * shopInnerCodes = new ArrayList<String>(); String[] shopArrays =
                 * shoplist1.split("\\|"); for (String s : shopArrays) { List<BiChannelCommand>
                 * channelList = channelGroupManager.findAllChannelRefByGroupCode(s); for
                 * (BiChannelCommand biChannelCommand : channelList) {
                 * shopInnerCodes.add(biChannelCommand.getCode()); }
                 * 
                 * }
                 */
                pickingListSqlCommand.setShoplist1(shoplist1);
            } else {
                pickingListSqlCommand.setShoplist1(null);
            }
        } else {
            pickingListSqlCommand.setShoplist1(null);
        }
        // 新增拣货区域查询限制
        if (StringUtils.hasText(areaList)) {
            if (StringUtils.hasLength(areaList)) {
                /*
                 * pickZoneList = new ArrayList<String>(); String[] ZoneArrays =
                 * areaList.split("\\|"); for (int i = 0; i < ZoneArrays.length; i++) {
                 * pickZoneList.add(ZoneArrays[i]); }
                 */
                pickingListSqlCommand.setPickZoneList(areaList);
            } else {
                pickingListSqlCommand.setPickZoneList(null);
            }
        } else {
            pickingListSqlCommand.setPickZoneList(null);
        }

        if (StringUtils.hasText(whZoneList1)) {
            if (StringUtils.hasLength(whZoneList1)) {
                pickingListSqlCommand.setWhZoneList1(whZoneList1);
            } else {
                pickingListSqlCommand.setWhZoneList1(null);
            }
        } else {
            pickingListSqlCommand.setWhZoneList1(null);
        }
        if (null != isMargeWhZoon && isMargeWhZoon) {
            pickingListSqlCommand.setIsMargeZoon(1);
        } else {
            pickingListSqlCommand.setIsMargeZoon(null);
        }

        // 新增仓库区域查询限制
        if (StringUtils.hasText(whAreaList)) {
            if (StringUtils.hasLength(whAreaList)) {
                /*
                 * whZoneList = new ArrayList<String>(); String[] ZoneArrays =
                 * whAreaList.split("\\|"); for (int i = 0; i < ZoneArrays.length; i++) {
                 * whZoneList.add(ZoneArrays[i]); }
                 */
                pickingListSqlCommand.setWhZoneList(whAreaList);
            } else {
                pickingListSqlCommand.setWhZoneList(null);
            }
        } else {
            pickingListSqlCommand.setWhZoneList(null);
        }
        pickingListSqlCommand.setTransTimeType(transTimeType);
        pickingListSqlCommand.setMergeWhZoon(mergeWhZoon);
        if (sta != null) {
            if (sta.getOrderType2() != null) {
                orderType2 = sta.getOrderType2();
                pickingListSqlCommand.setOrderType2(orderType2);
            } else {
                pickingListSqlCommand.setOrderType2(null);
            }

            if (StringUtils.hasText(sta.getIsPreSale())) {
                isPreSale = sta.getIsPreSale();
                pickingListSqlCommand.setIsPreSale(isPreSale);
            } else {
                pickingListSqlCommand.setIsPreSale("0");
            }

            /*
             * if (StringUtils.hasText(sta.getRefSlipCode())) { refSlipCode = sta.getRefSlipCode();
             * pickingListSqlCommand.setRefSlipCode(refSlipCode); }
             */
            // 特殊处理
            if (sta.getSpecialType() != null) {
                specialType = sta.getSpecialType().getValue();
                pickingListSqlCommand.setSpecialType(specialType);
            } else {
                pickingListSqlCommand.setSpecialType(null);
            }
            // 是否合并拣货分区. 如果勾选是否合并，则忽略拣货区域条件
            if (sta.getIsMerge() != null && sta.getIsMerge()) {

                pickingListSqlCommand.setIsMergeInt(1);
            } else {
                pickingListSqlCommand.setIsMergeInt(null);
            }
            // QS
            if (sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging()) {
                isSpecialPackaging = 1;
                pickingListSqlCommand.setIsSpecialPackaging(isSpecialPackaging);
            } else {
                pickingListSqlCommand.setIsSpecialPackaging(null);
            }

            pickingType = sta.getPickingType().getValue();
            pickingListSqlCommand.setPickingType(pickingType);
            skuQty = sta.getSkuQty();
            pickingListSqlCommand.setSkuQty(skuQty);
            if (sta.getPackageSku() != null) {
                packageSkuId = sta.getPackageSku();
                pickingListSqlCommand.setPackageSkuId(packageSkuId);
            }
            if (StringUtils.hasText(sta.getSkus())) {
                skus = sta.getSkus();
                pickingListSqlCommand.setSkus(skus);
            }
            if (StringUtils.hasText(sta.getMemo())) {
                barCode = sta.getMemo();
                pickingListSqlCommand.setBarCode(barCode);
            }
            if (StringUtils.hasText(sta.getCode1())) {
                code1 = sta.getCode1();
                pickingListSqlCommand.setCode1(code1);
            }
            if (StringUtils.hasText(sta.getCode2())) {
                code2 = sta.getCode2();
                pickingListSqlCommand.setCode2(code2);
            }
            if (StringUtils.hasText(sta.getCode3())) {
                code3 = sta.getCode3();
                pickingListSqlCommand.setCode3(code3);

            }
            if (StringUtils.hasText(sta.getCode4())) {
                code4 = sta.getCode4();
                pickingListSqlCommand.setCode4(code4);
            }
            if (sta.getStatus() != null) {
                status = sta.getStatus().getValue();
                pickingListSqlCommand.setStatus(status);
            }
            if (sta.getPackingType() != null) {
                packingType = sta.getPackingType().getValue();
                pickingListSqlCommand.setPackingType(packingType);
            }
            if (StringUtils.hasText(sta.getToLocation())) {
                toLocation = sta.getToLocation();
                pickingListSqlCommand.setToLocation(toLocation);
            }
            if (sta.getStaDeliveryInfo() != null) {
                if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
                    codeList = new ArrayList<String>();
                    lpCode = sta.getStaDeliveryInfo().getLpCode();
                    String[] lpCodeS = lpCode.split(",");
                    for (int i = 0; i < lpCodeS.length; i++) {
                        codeList.add(lpCodeS[i]);
                    }
                    pickingListSqlCommand.setCodeList(codeList);
                }
            }
            if (sta.getPickSubType() != null) {
                pickSubType = sta.getPickSubType();
                pickingListSqlCommand.setPickSubType(pickSubType);
            }
        }
        pickingListSqlCommand.setsList(ssList);
        pickingListSqlCommand.setDate(date);
        pickingListSqlCommand.setDate2(date2);
        pickingListSqlCommand.setDate3(date3);
        pickingListSqlCommand.setDate4(date4);
        pickingListSqlCommand.setMergePickZoon(mergePickZoon);
        // dropDown 优先发货城市标志位
        if (null != cityList && !"".equals(cityList)) {
            createPickingListSql.setCity(cityList);
        }

        if (null != priorityList && !"".equals(priorityList)) {
            createPickingListSql.setPriority(priorityList);
        }
        createPickingListSql.setCreateTime(new Date());
        createPickingListSql.setModeName(modeName);
        createPickingListSql.setOuId(ouId);
        createPickingListSql.setUserId(userId);
        createPickingListSql.setRuleCode(ruleCode);
        createPickingListSql.setRuleName(ruleName);
        createPickingListSql.setSqlContent(JSONUtil.beanToJson(pickingListSqlCommand));
        createPickingListSqlDao.save(createPickingListSql);
        msg = "success";
        return msg;
    }

    @Override
    public Pagination<StockTransApplicationCommand> findStaForPickingByModel(Boolean isMargeWhZoon, Integer mergePickZoon, Integer mergeWhZoon, String areaList, String whAreaList, List<SkuSizeConfig> ssList, String shoplist, String shoplist1,
            List<String> cityList, int start, int pageSize, Date date, Date date2, Date date3, Date date4, StockTransApplication sta, Long categoryId, Integer transTimeType, Integer isSn, Integer isNeedInvoice, Integer isCod, Long id, Sort[] sorts,
            List<String> priorityList, String otoAll, Boolean countAreasCp) {
        String code = null;
        String lpCode = null;
        String refSlipCode = null;
        String barCode = null;
        String code1 = null;
        String code2 = null;
        String code3 = null;
        String code4 = null;
        Integer status = null;
        Integer packingType = null; // 包装类型
        Integer specialType = null;
        Integer isSpecialPackaging = null;
        Integer isMergeInt = 1; // 忽略已选拣货区域查询条件
        Integer isMargeZoon = 1; // 忽略已选仓库区域查询条件
        // Boolean isSedKill = null;
        Integer pickingType = null;
        Long packageSkuId = null;
        Long skuQty = null;
        String skus = null;
        String toLocation = null;
        List<String> shopInnerCodes = null;
        List<String> codeList = null;
        List<String> pickZoneList = null;
        List<String> whZoneList = null;
        Long pickSubType = null;
        String isPreSale = null;// 是否是预售订单
        Integer orderType2 = null;// 订单类型
        Integer isMacaoOrder = null;// 是否澳门件
        Integer isPrintMaCaoHGD = null;// 是否打印海关单
        List<String> otoList = null;// OTO店铺列表
        Integer cp = null;// 混合拣货区域（包含所有排列组合）

        if (StringUtils.hasText(shoplist)) {
            if (StringUtils.hasLength(shoplist)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist.split("\\|");
                for (String s : shopArrays) {
                    shopInnerCodes.add(s);
                }
            }
        }
        if (StringUtils.hasText(shoplist1)) {
            if (StringUtils.hasLength(shoplist1)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist1.split("\\|");
                for (String s : shopArrays) {
                    List<BiChannelCommand> channelList = channelGroupManager.findAllChannelRefByGroupCode(s);
                    for (BiChannelCommand biChannelCommand : channelList) {
                        shopInnerCodes.add(biChannelCommand.getCode());
                    }

                }
            }
        }
        // 新增拣货区域查询限制
        if (StringUtils.hasText(areaList)) {
            if (StringUtils.hasLength(areaList)) {
                pickZoneList = new ArrayList<String>();
                String[] ZoneArrays = areaList.split("\\|");
                for (int i = 0; i < ZoneArrays.length; i++) {
                    pickZoneList.add(ZoneArrays[i]);
                }
            }
        }
        // 忽略已选仓库区域查询条件
        if (isMargeWhZoon != null && isMargeWhZoon) {
            isMargeZoon = null;
        }
        // 新增仓库区域查询限制
        if (StringUtils.hasText(whAreaList)) {
            if (StringUtils.hasLength(whAreaList)) {
                whZoneList = new ArrayList<String>();
                String[] ZoneArrays = whAreaList.split("\\|");
                for (int i = 0; i < ZoneArrays.length; i++) {
                    whZoneList.add(ZoneArrays[i]);
                }
            }
        }
        if (sta != null) {
            if (sta.getOrderType2() != null) {
                orderType2 = sta.getOrderType2();
            }

            if (StringUtils.hasText(sta.getIsPreSale())) {
                isPreSale = sta.getIsPreSale();
            }
            if (StringUtils.hasText(sta.getCode())) {
                code = sta.getCode();
            }
            if (StringUtils.hasText(sta.getRefSlipCode())) {
                refSlipCode = sta.getRefSlipCode();
            }
            // 特殊处理
            if (sta.getSpecialType() != null) {
                specialType = sta.getSpecialType().getValue();
            }
            // 是否合并拣货分区. 如果勾选是否合并，则忽略拣货区域条件
            if (sta.getIsMerge() != null && sta.getIsMerge()) {
                isMergeInt = null;
            }
            // QS
            if (sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging()) {
                isSpecialPackaging = 1;
            }
            // isSpecialPackaging = sta.getIsSpecialPackaging();
            // isSedKill = sta.getIsSedKill();
            pickingType = sta.getPickingType().getValue();
            skuQty = sta.getSkuQty();
            if (sta.getPackageSku() != null) {
                packageSkuId = sta.getPackageSku();
            }
            if (StringUtils.hasText(sta.getSkus())) {
                skus = sta.getSkus();
            }
            if (StringUtils.hasText(sta.getMemo())) {
                barCode = sta.getMemo();
            }
            if (StringUtils.hasText(sta.getCode1())) {
                code1 = sta.getCode1();
            }
            if (StringUtils.hasText(sta.getCode2())) {
                code2 = sta.getCode2();
            }
            if (StringUtils.hasText(sta.getCode3())) {
                code3 = sta.getCode3();
            }
            if (StringUtils.hasText(sta.getCode4())) {
                code4 = sta.getCode4();
            }
            if (sta.getStatus() != null) {
                status = sta.getStatus().getValue();
            }
            if (sta.getPackingType() != null) {
                packingType = sta.getPackingType().getValue();
            }
            if (StringUtils.hasText(sta.getToLocation())) {
                toLocation = sta.getToLocation();
            }
            if (sta.getStaDeliveryInfo() != null) {
                if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
                    codeList = new ArrayList<String>();
                    lpCode = sta.getStaDeliveryInfo().getLpCode();
                    String[] lpCodeS = lpCode.split(",");
                    for (int i = 0; i < lpCodeS.length; i++) {
                        codeList.add(lpCodeS[i]);
                    }
                }
            }
            if (sta.getPickSubType() != null) {
                pickSubType = sta.getPickSubType();
            }
            if (sta.getIsMacaoOrder() != null) {
                isMacaoOrder = sta.getIsMacaoOrder() ? 1 : null;
            }
            if (sta.getIsPrintMaCaoHGD() != null) {
                isPrintMaCaoHGD = sta.getIsPrintMaCaoHGD() ? 1 : null;
            }
        }
        if (ssList != null) {
            List<SkuSizeConfig> sList = new ArrayList<SkuSizeConfig>();
            for (int i = 0; i < ssList.size(); i++) {
                if (!(ssList.get(i).getMaxSize().intValue() == -1 && ssList.get(i).getMinSize().intValue() == -1 && ssList.get(i).getGroupSkuQtyLimit().intValue() == -1)) {
                    sList.add(ssList.get(i));
                }
            }
            ssList = sList;
        }
        // dropDown 优先发货城市标志位
        Boolean flag = true;
        if (null != cityList && cityList.size() > 0) {
            if (cityList.contains("opposite")) {
                flag = false;
            }
        }
        Boolean msg = true;
        if (null != priorityList && priorityList.size() > 0) {
            if (priorityList.contains("opposite")) {
                msg = false;
            }
        }
        Boolean lg = false;
        if (null != priorityList && priorityList.size() > 0 && null != cityList && cityList.size() > 0) {
            lg = true;
        }
        Boolean opposite = false;
        if (flag && msg) {
            opposite = true;
        }
        if (!StringUtil.isEmpty(otoAll)) {
            otoList = new ArrayList<String>();
            String[] oto = otoAll.split(",");
            for (String s : oto) {
                otoList.add(s);
            }
        }
        if (countAreasCp != null && countAreasCp) {
            cp = 1;
            if (StringUtils.hasText(areaList)) {
                if (StringUtils.hasLength(areaList)) {
                    pickZoneList = new ArrayList<String>();
                    String[] ZoneArrays = areaList.split("\\|");
                    pickZoneList = StringUtil.getCountAreas(ZoneArrays);
                }
            }
        }
        return staDao.findStaForPickingByModel(start, pageSize, areaList, whAreaList, mergePickZoon, mergeWhZoon, isMergeInt, isMargeZoon, transTimeType, barCode, code1, code2, code3, code4, packageSkuId, ssList, isCod, isSn, skus, pickingType, skuQty,
                shopInnerCodes, pickZoneList, whZoneList, cityList, date, date2, date3, date4, code, refSlipCode, isNeedInvoice, codeList, specialType, isSpecialPackaging, categoryId, status, packingType, id, toLocation, pickSubType, flag, isPreSale,
                orderType2, isMacaoOrder, isPrintMaCaoHGD, sorts, msg, priorityList, lg, opposite, otoList, cp, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public Pagination<StaLineCommand> findPickingStaLine(int start, int pageSize, Long id, Sort[] sorts) {
        return staLineDao.findPickingStaLine(start, pageSize, id, sorts, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
    }

    /**
     * 创建配货清单 部分规则说明 KJL ADD<br/>
     * 1、lpCode 可以为空(自动创单可以混物流配货），同时创出来的单子必须是运单后置 2 中字段必须为1<br/>
     * 2、isPostpositionExpressBill<br/>
     * 3、sendCity 优先发货城市，根据选择的优先发货城市设值，可以为空<br/>
     * 4、isSn 根据传进来的查询条件设置，可以为空 为空则表示该批可能包含SN号订单，也可能包含非SN号订单<br/>
     * 5、isNeedInvoice 是否需要发票根据实际单子情况计算 如果包含有发票的单子，就为true 非空<br/>
     */
    @Override
    public PickingList createPickingList(String skusIdAndQty, List<Long> staIdList, Long warehouseId, Long userID, PickingListCheckMode checkModel, Boolean isSp, Integer isSn, Long categoryId, List<String> cityList, Boolean isTransAfter,
            List<SkuSizeConfig> ssList, Integer isCod, String isQs, Boolean isOtwoo, Boolean isSd, Boolean isClickBatch, Long locId, Boolean isOtoPicking) {
        Date WhPickingBatchDate = new Date();// 创建批次拣货时间
        Map<String, BiChannel> biChannelMap = new HashMap<String, BiChannel>();
        // 如果是手动选择的作业单，则再排序一次
        if (isSd) {
            staIdList = staDao.findStaByStaListAndLocationSort(staIdList, new SingleColumnRowMapper<Long>(Long.class));
        }
        List<StockTransApplication> stas = new ArrayList<StockTransApplication>();
        BiChannel bi = null;
        List<String> lpList = new ArrayList<String>();
        String toLocation = null; // O2O门店编码
        Long pickSubType = null; // 多件拣货模式子类型
        Integer packType = null; // 包装类型
        String isPreSale = null;// 是否是预售订单
        boolean isMode1 = false, isMode2 = false; // 拣货模式1,2
        // Boolean isSpecialPackaging = false;// O2O判断使用
        for (Long staId : staIdList) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            stas.add(sta);
            toLocation = sta.getToLocation(); // 同一批次的作业单的toLocation相同
            // isSpecialPackaging = sta.getIsSpecialPackaging();// 同一批次的作业单的isSpecialPackaging一样
            lpList.add(sta.getStaDeliveryInfo().getLpCode());
            if (sta.getPickSubType() != null && sta.getPickSubType() == 2) {
                pickSubType = 2l;
                isMode1 = true;
            } else {
                pickSubType = 1l;
                isMode2 = true;
            }
            // 如果有1单是需要包装的，则整个配货批标示为礼盒包装
            if (sta.getPackingType() != null && packType == null) {
                packType = sta.getPackingType().getValue();
            }
            //
            if ("1".equals(sta.getIsPreSale()) && isPreSale == null) {
                isPreSale = "1";
            }
        }
        // 如果一个批次里包含模式1，模式2，则优先模式1
        if (isMode1 && isMode2) {
            pickSubType = 1l;
        }
        if (stas == null || stas.size() == 0) {
            return null;
        }
        BusinessException rootExc = null;
        for (StockTransApplication sta : stas) {
            if (sta == null) {
                if (rootExc == null) {
                    rootExc = new BusinessException();
                }
                BusinessException currrentExc = new BusinessException(ErrorCode.STA_NOT_FOUND, new Object[] {""});
                rootExc.setLinkedException(currrentExc);
            } else if (!(sta.getStatus().getValue() == StockTransApplicationStatus.FAILED.getValue() || sta.getStatus().getValue() == StockTransApplicationStatus.OCCUPIED.getValue())) {
                if (rootExc == null) {
                    rootExc = new BusinessException();
                }
                BusinessException currrentExc = new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
                rootExc.setLinkedException(currrentExc);
            } else {
                if (sta.getPickingList() != null) {
                    if (rootExc == null) {
                        rootExc = new BusinessException();
                    }
                    BusinessException currrentExc = new BusinessException(ErrorCode.PICKING_LIST_IN_STA_IS_NOT_NULL, new Object[] {sta.getCode()});
                    rootExc.setLinkedException(currrentExc);
                }
            }
        }
        if (rootExc != null) {
            throw rootExc;
        }
        OperationUnit ou = operationUnitDao.getByPrimaryKey(warehouseId);
        if (ou == null) {
            throw new BusinessException(ErrorCode.OPERATION_UNIT_NOT_FOUNT, new Object[] {warehouseId});
        }
        User creator = null;
        if (userID != null) {
            creator = userDao.getByPrimaryKey(userID);
            if (creator == null) {
                throw new BusinessException(ErrorCode.USER_NOT_FOUND, new Object[] {userID});
            }
        }
        // Boolean snFlag;
        // if (isSn == null) {
        // snFlag = null;
        // } else {
        // if (isSn == 1) {
        // snFlag = true;
        // } else {
        // snFlag = false;
        // }
        // }
        Long skuSizeId = null;
        if (ssList != null && ssList.size() == 1) {
            skuSizeId = skuSizeConfigDao.getSkuSizeConfigIdByDeatil(ssList.get(0).getMaxSize(), ssList.get(0).getMinSize(), ssList.get(0).getGroupSkuQtyLimit(), new SingleColumnRowMapper<Long>(Long.class));
            if (skuSizeId == null) {
                throw new BusinessException(ErrorCode.PL_SKU_SIZE_NOT_FOUND);
            }
        }
        Boolean isSn1 = false;
        for (StockTransApplication sta : stas) {
            if (sta.getIsSn() != null && sta.getIsSn()) {
                isSn1 = true;
                break;
            }
        }
        // 判断是否后置装箱清单 是否运单后置bin.hu
        boolean is_ppp = true;// 是否后置装箱清单
        boolean is_peb = true;// 是否运单后置
        boolean is_invoice = false;// 是否需要发票
        int invoiceNum = 0;// 发票数量
        String lpCode = stas.get(0).getStaDeliveryInfo().getLpCode();
        Set<String> s = new HashSet<String>(lpList);
        if (s.size() > 1) {
            lpCode = null;
        }
        if (checkModel == PickingListCheckMode.DEFAULE || checkModel == PickingListCheckMode.PICKING_CHECK || checkModel == PickingListCheckMode.PICKING_SPECIAL || checkModel == PickingListCheckMode.PICKING_GROUP) {
            // 只有单件、多件和团购才有后面后装 // 特殊订单也可以后置了。
            // 判断是否运单后置
            if (isTransAfter != null) {
                if (isTransAfter) {
                    lpCode = null;
                }
            }
            for (StockTransApplication sta : stas) {
                if (biChannelMap.containsKey(sta.getOwner())) {
                    bi = biChannelMap.get(sta.getOwner());
                } else {
                    bi = biChannelDao.getByCode(sta.getOwner());// 获取sta对应渠道信息
                    biChannelMap.put(sta.getOwner(), bi);
                }
                ChannelWhRef cwr = warehouseShopRefDao.getChannelRef(warehouseId, bi.getId());// 通过仓库ID和渠道ID获取是否后置装箱清单是否运单后置信息
                if (!cwr.getIsPostExpressBill()) {
                    // 有一条不是运单后置整个配货清单都不是运单后置
                    is_peb = false;
                    break;
                }
                // if (sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging()) {
                // // 有一条作业单是特殊订单整个配货清单都不是运单后置
                // is_peb = false;
                // break;
                // }
                // 特殊订单 需要支持 后置面单
                // if (!sta.getIsSpecialPackaging() && (sta.getSpecialType() != null &&
                // sta.getSpecialType().equals(SpecialSkuType.NORMAL))) {
                // // 有一条作业单是特殊订单整个配货清单都不是运单后置
                // is_peb = false;
                // break;
                // }
                // 作业单物流商是否标识使用电子面单
                TransOlInterface transol = transOnLineFactory.getTransOnLine(sta.getStaDeliveryInfo().getLpCode(), sta.getMainWarehouse().getId());
                if (transol == null) {
                    // 如果非电子面单整个配货清单都不是运单后置
                    is_peb = false;
                    break;
                }
            }
            if (is_peb) {
                // 如果STA不存在发票信息
                // 判断是否后置装箱清单
                for (StockTransApplication sta : stas) {
                    if (biChannelMap.containsKey(sta.getOwner())) {
                        bi = biChannelMap.get(sta.getOwner());
                    } else {
                        bi = biChannelDao.getByCode(sta.getOwner());// 获取sta对应渠道信息
                        biChannelMap.put(sta.getOwner(), bi);
                    }
                    ChannelWhRef cwr = warehouseShopRefDao.getChannelRef(warehouseId, bi.getId());// 通过仓库ID和渠道ID获取是否后置装箱清单是否运单后置信息
                    if (!cwr.getIsPostPackingPage()) {
                        // 有一条不是后置装箱清单整个配货清单都不是后置装箱清单
                        is_ppp = false;
                        break;
                    }
                    if (sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging()) {
                        // 有一条作业单是特殊订单整个配货清单都不是后置装箱清单
                        is_ppp = false;
                        break;
                    }
                }
            } else {
                is_ppp = false;
            }
        } else {
            is_ppp = false;// 是否后置装箱清单
            is_peb = false;// 是否运单后置
        }
        // 查询批次中是否存在 需要发票的订单 dly
        for (StockTransApplication sta : stas) {
            List<StaInvoice> si = staInvoiceDao.getBySta(sta.getId());// 获取sta下所有的发票信息
            if (si.size() > 0) {
                is_invoice = true;
                invoiceNum += si.size();
                // break;
            }
        }

        BiWarehouseAddStatusCommand biStatus = getBiWhST(warehouseId, checkModel, 0, 0, staIdList.size(), null);
        // 判断作业单是否有后端判断SKU STA
        List<StockTransApplicationCommand> isbkcheck = staDao.getIsBkCheckStaByStaId(staIdList, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        PickingList pl = new PickingList();
        pl.setToLocation(toLocation);
        pl.setLpcode(lpCode);
        pl.setPackingType(packType);
        pl.setCheckMode(checkModel);
        if (isOtwoo != null) {
            pl.setIsOTwoo(isOtwoo);
        }
        if (isbkcheck.size() > 0) {
            // 设置后端判断SKU 配货单
            pl.setIsBkCheck(true);
        }
        if (isSp != null && !isSp) {
            pl.setIsSpecialPackaging(false);
        }
        if (isSp != null && isSp) {
            pl.setIsSpecialPackaging(true);
        }

        if (checkModel.equals(PickingListCheckMode.PICKING_SPECIAL)) {
            pl.setSpecialType(SpecialSkuType.NORMAL);
        }
        if ("qsAndSq".equals(isQs)) {
            pl.setSpecialType(SpecialSkuType.NORMAL);
            pl.setIsSpecialPackaging(true);
        }
        if ("notQsAndSq".equals(isQs)) {
            pl.setIsSpecialPackaging(false);
        }
        if (pickSubType == 1) {
            // 拣货模式1显示蓝色，模式2显示灰色
            pl.setSortingMode(ParcelSortingMode.SECONDARY);
        } else {
            // 拣货模式2显示蓝色，模式1显示灰色
            pl.setSortingMode(ParcelSortingMode.SINGLE);
        }
        pl.setIsBigBox(isBigBox(stas.get(0).getSkuMaxLength()));
        // pl.setIsSpecialPackaging(stas.get(0).getIsSpecialPackaging() == null
        // ? false :
        // stas.get(0).getIsSpecialPackaging());
        if (stas != null && stas.size() > 0) {
            ou = stas.get(0).getMainWarehouse();
        }
        if (biStatus != null) {
            // NEW 仓库状态，仓库类型
            pl.setWhStatus(WhAddStatusMode.valueOf(biStatus.getStatus()));
            pl.setWhType(WhAddTypeMode.valueOf(biStatus.getType()));
        }
        pl.setWarehouse(ou);
        if (userID != null) {
            pl.setCreator(creator);
        }
        pl.setExecutedTime(new Date());
        pl.setStaList(stas);
        pl.setPlanBillCount(stas.size());
        pl.setStatus(PickingListStatus.WAITING);
        pl.setIsSn(isSn1);
        pl.setIsCod(isCod);
        // 是否OTO批次
        pl.setIsOtoPicking(isOtoPicking);
        setPickingListPlanQty(pl);
        String code = null;

        // 循环获取批货清单编码，尝试50次，如成功则 停止获取
        for (int i = 0; i < 50; i++) {
            try {
                code = sequenceManager.getCode(PickingList.class.getName(), pl);
                if (code != null) {
                    break;
                }
            } catch (Exception e) {
                log.warn("try to get picking list code error");
            }
        }
        if (code == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        pl.setCode(code);
        // 订单状态与账号关联2 带仓库ID
        whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userID, warehouseId);
        if (is_invoice) {
            // 需要发票
            pl.setInvoiceNum(invoiceNum);
            pl.setIsInvoice(is_invoice);
        }
        pl.setIsPostpositionExpressBill(is_peb);
        pl.setIsPostpositionPackingPage(is_ppp);
        pl.setCategoryId(categoryId);
        pl.setCity(getCityByCityList(cityList));
        pl.setSkuSizeId(skuSizeId);
        pl.setPackageSku(skusIdAndQty); // 新增保存套装商品ID和数量
        pl.setIsPreSale(isPreSale);// 是否是预售订单
        if (null != isClickBatch && isClickBatch) {
            pl.setLocId(locId);
        }

        // O2O+QS，设置CheckMode为PICKING_O2OQS(6)、添加storeCode
        /*
         * if (!StringUtil.isEmpty(toLocation) && isSpecialPackaging) {
         * pl.setCheckMode(PickingListCheckMode.PCIKING_O2OQS); pl.setToLocation(toLocation);
         * commonCreatePickingListPackage(pl, toLocation); }
         */
        pickingListDao.save(pl);
        pickingListDao.flush();



        // 插入picklingLog表
        WhStaPickingListLog pickLog = new WhStaPickingListLog();
        pickLog.setLogTime(new Date());
        pickLog.setPickList(pl);
        if (userID != null) {
            pickLog.setUserId(userID);
        }
        // else {
        // pickLog.setUserId(1L);
        // }
        if (biStatus != null) {
            pickLog.setStatus(WhAddStatusMode.valueOf(biStatus.getStatus()));
        }
        logDao.save(pickLog);

        TransTimeType ttType = null;
        boolean isTransTimeType = true;
        Integer index = 1;
        // List<StockTransApplication> list = pl.getStaList();
        List<Long> list = staIdList;
        int staIndexId = 0;
        Warehouse se = warehouseDao.getByOuId(warehouseId);
        boolean isAutoWh = false; // 是否自动化仓
        int configNum = 5;// 小批次容量
        if (se != null && se.getIsAutoWh() != null && se.getIsAutoWh() == true) {
            isAutoWh = true;
        }
        // 小批次逻辑： 取商品分类里，最小的批次容量。如没有则取仓库配置，再则，取系统配置
        if (isAutoWh) {
            configNum = warehouseDao.findLimitBySku(staIdList, new SingleColumnRowMapper<Integer>(Integer.class));
            if (configNum <= 0) {
                // 如果仓库没配置，则取系统默认配置
                if (se.getIdx2MaxLimit() == null) {
                    // 获取二级批次配置数据
                    ChooseOption co = chooseOptionDao.findByCategoryCode("batchNum");
                    configNum = Integer.parseInt(co.getOptionKey());
                } else {
                    configNum = se.getIdx2MaxLimit(); // 小批次容量
                }
            }
        }
        Integer pickModleType = null;// 配货模式类型
        String tempWhId = "";
        int staListSize = list.size(); // 查询数据
        for (int i = 1;; i++) {
            // 添加配货批与仓储区域的关联
            WhPickingBatch bp = null; // 耳机批次表

            if (isAutoWh && checkModel == PickingListCheckMode.PICKING_CHECK) {
                bp = new WhPickingBatch();
                bp.setBarCode(pl.getCode());
                bp.setCode(pl.getCode() + "#" + i);
                bp.setStatus(DefaultStatus.CREATED);
                bp.setPickingList(pl);
                bp.setCreateTime(WhPickingBatchDate);// 批次创建时间
                bp.setSort(i);
                whPickingBatchDao.save(bp);
            }

            for (int j = 1; j <= configNum; j++) {
                StockTransApplication sta = staDao.getByPrimaryKey(list.get(staIndexId));
                staIndexId++;
                staListSize--;
                TransTimeType temp = sta.getStaDeliveryInfo().getTransTimeType();
                if (index == 1) {
                    if (temp != null) {
                        ttType = temp;
                    } else {
                        isTransTimeType = false;
                    }
                } else if (temp == null || (isTransTimeType && !ttType.equals(temp))) {
                    isTransTimeType = false;
                }
                if (sta.getGroupSta() != null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                // if (PickingListCheckMode.DEFAULE.equals(checkModel)) {
                if (list.size() <= 104) {
                    sta.setRuleCode(getRuleCode(index));
                }

                sta.setIndex(index++);
                sta.setPickingList(pl);
                // 自动化仓 单件 才绑定出库小批次by_ZDHDJ-63_fxl
                if (isAutoWh && checkModel.equals(PickingListCheckMode.PICKING_CHECK)) {
                    sta.setIdx1(i + "");
                    sta.setIdx2(j + "");
                    sta.setWhPickingBatch(bp); // 绑定二级批次
                }
                if (checkModel.equals(PickingListCheckMode.DEFAULE)) {// 多件
                    if (pickModleType == null && sta.getWhZoonList() != null && sta.getWhZoonList().contains("|")) {
                        pickModleType = 1; // 多件跨仓库区域
                    } else {
                        if (!"".equals(tempWhId) && sta.getWhZoonList() != null && !tempWhId.equals(sta.getWhZoonList())) {
                            pickModleType = 1;// 多件跨仓库区域
                        }
                        if (sta.getWhZoonList() != null) {
                            tempWhId = sta.getWhZoonList();
                        }
                    }
                }
                /*
                 * else if (isAutoWh && checkModel.equals(PickingListCheckMode.PICKING_CHECK)) {//
                 * 单件 if (pickModleType == null && sta.getWhZoonList() != null &&
                 * sta.getWhZoonList().contains("|")) { pickModleType = 2; // 单件 跨仓库区域 } else { if
                 * (!"".equals(tempWhId) && sta.getWhZoonList() != null &&
                 * !tempWhId.equals(sta.getWhZoonList())) { pickModleType = 2;// 单件 跨仓库区域 } if
                 * (sta.getWhZoonList() != null) { tempWhId = sta.getWhZoonList(); } }
                 * 
                 * }
                 */

                else if (checkModel.equals(PickingListCheckMode.PICKING_SECKILL)) {// 秒杀
                    if (pickModleType == null && sta.getWhZoonList() != null && sta.getWhZoonList().contains("|")) {
                        pickModleType = 3; // 秒杀 跨仓库区域
                    } else {
                        if (!"".equals(tempWhId) && sta.getWhZoonList() != null && !tempWhId.equals(sta.getWhZoonList())) {
                            pickModleType = 3;// 秒杀 跨仓库区域
                        }
                        if (sta.getWhZoonList() != null) {
                            tempWhId = sta.getWhZoonList();
                        }
                    }
                } else if (checkModel.equals(PickingListCheckMode.PICKING_GROUP)) {// 团购
                    if (pickModleType == null && sta.getWhZoonList() != null && sta.getWhZoonList().contains("|")) {
                        pickModleType = 4; // 团购 跨仓库区域
                    } else {
                        if (!"".equals(tempWhId) && sta.getWhZoonList() != null && !tempWhId.equals(sta.getWhZoonList())) {
                            pickModleType = 4;// 团购 跨仓库区域
                        }
                        if (sta.getWhZoonList() != null) {
                            tempWhId = sta.getWhZoonList();
                        }
                    }
                } else if (checkModel.equals(PickingListCheckMode.PICKING_PACKAGE)) {// 套装组合商品
                    if (pickModleType == null && sta.getWhZoonList() != null && sta.getWhZoonList().contains("|")) {
                        pickModleType = 5; // 套装组合商品 跨仓库区域
                    } else {
                        if (!"".equals(tempWhId) && sta.getWhZoonList() != null && !tempWhId.equals(sta.getWhZoonList())) {
                            pickModleType = 5;// 套装组合商品 跨仓库区域
                        }
                        if (sta.getWhZoonList() != null) {
                            tempWhId = sta.getWhZoonList();
                        }
                    }
                } else if (checkModel.equals(PickingListCheckMode.PICKING_SPECIAL)) {// 特殊处理
                    if (pickModleType == null && sta.getWhZoonList() != null && sta.getWhZoonList().contains("|")) {
                        pickModleType = 6; // 特殊处理 跨仓库区域
                    } else {
                        if (!"".equals(tempWhId) && sta.getWhZoonList() != null && !tempWhId.equals(sta.getWhZoonList())) {
                            pickModleType = 6;// 特殊处理 跨仓库区域
                        }
                        if (sta.getWhZoonList() != null) {
                            tempWhId = sta.getWhZoonList();
                        }
                    }
                } else if (isAutoWh && checkModel.equals(PickingListCheckMode.PCIKING_O2OQS)) {// QS
                    if (pickModleType == null && sta.getWhZoonList() != null && sta.getWhZoonList().contains("|")) {
                        pickModleType = 7; // QS 跨仓库区域
                    } else {
                        if (!"".equals(tempWhId) && sta.getWhZoonList() != null && !tempWhId.equals(sta.getWhZoonList())) {
                            pickModleType = 7;// QS 跨仓库区域
                        }
                        if (sta.getWhZoonList() != null) {
                            tempWhId = sta.getWhZoonList();
                        }
                    }
                }
                staDao.save(sta);
                if (staListSize == 0) {
                    break;
                }
            }
            if (staListSize == 0) {
                break;
            }
        }
        if (isTransTimeType) {
            pl.setTransTimeType(ttType);
        }
        pl.setPickModleType(pickModleType);
        staDao.flush();

        // 添加配货批与仓储区域的关联
        try {
            if (checkModel == PickingListCheckMode.DEFAULE || checkModel == PickingListCheckMode.PICKING_PACKAGE || checkModel == PickingListCheckMode.PICKING_SPECIAL || checkModel == PickingListCheckMode.PICKING_SECKILL
                    || checkModel == PickingListCheckMode.PICKING_GROUP) {
                whPickingBatchDao.savePlzByPickingList(pl.getId());
                pickingLineDao.savePickingLineByPickingList(pl.getId());
            } else if (isAutoWh && checkModel == PickingListCheckMode.PICKING_CHECK) {
                whPickingBatchDao.flush();
                pickingLineDao.savePickingLineBySingle(pl.getId());
            } else if (!isAutoWh) {
                WhPickingBatch pb = new WhPickingBatch();
                pb.setBarCode(pl.getCode());
                pb.setCode(pl.getCode());
                pb.setStatus(DefaultStatus.CREATED);
                pb.setSort(1);
                pb.setCreateTime(WhPickingBatchDate);// 批次创建时间
                pb.setPickingList(pl);
                whPickingBatchDao.save(pb);
                whPickingBatchDao.flush();
                pickingLineDao.savePickingLineByGeneral(pl.getId());
            }

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("PickingList Exception:", e);
            }
        }

        // 判断是否是AGV仓同步
        if (se.getIsAgv() != null && se.getIsAgv()) {
            try {
                wareHouseManager.saveCommomAgvOutBound(null, pl.getId());
            } catch (Exception e) {
                e.printStackTrace();
                log.error(pl.getId() + ":saveCommomAgvOutBound", e);
                throw new BusinessException(ErrorCode.AGV_OUTBOUND);
            }
        }
        //压测发送MQ 拣货和播种
        if( se.getIsTestWh() !=null && se.getIsTestWh()){
            for (Long staId : list) {
                StockTransApplicationCommand sta = staDao.findStaByStaIdByTest(staId,new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
                PressureTestDto test=new  PressureTestDto();
                test.setStaId(sta.getId());
                test.setStaCode(sta.getCode());
                test.setpId(sta.getPickingListId());
               // test.setpCode(sta.getPickingList().getCode());
                test.setSta(sta);
                if(sta.getPickingListId() !=null){
                    PickingListCommand pick=staDao.findPickByPidByTest(sta.getPickingListId(), new BeanPropertyRowMapperExt<PickingListCommand>(PickingListCommand.class));
                    test.setPick(pick);
                }
                List<StaLineCommand> staLines= staDao.findStaLineByStaIdByTest(staId, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                test.setStaLines(staLines);
                String reqJson = JsonUtil.writeValue(test);
                MessageCommond mes = new MessageCommond();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mes.setMsgId(sta.getId() + ":" + UUIDUtil.getUUID());
                    mes.setIsMsgBodySend(false);
                    mes.setMsgType("findStaLineByStaIdByTest_picking");
                    mes.setMsgBody(reqJson);
                    mes.setSendTime(sdf.format(date));
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_PICKING,null, mes);
                    // 保存进mongodb
                    MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                    mdbmTest.setMsgId(mes.getMsgId());
                    mdbmTest.setStaCode(sta.getCode());
                    mdbmTest.setOtherUniqueKey(sta.getCode());
                    mdbmTest.setMsgBody(reqJson);
                    mdbmTest.setMemo(MQ_WMS3_PICKING);
                    mdbmTest.setSendTime(sdf.format(date));
                    mdbmTest.setMsgType("findStaLineByStaIdByTest_picking");
                    mongoOperation.save(mdbmTest);
                    mes.setMsgId(sta.getId() + ":" + UUIDUtil.getUUID());
                    mes.setIsMsgBodySend(false);
                    mes.setMsgType("findStaLineByStaIdByTest_seeding");
                    mes.setMsgBody(reqJson);
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_SEEDING,null, mes);
                    mdbmTest.setMsgId(mes.getMsgId());
                    mdbmTest.setStaCode(sta.getCode());
                    mdbmTest.setOtherUniqueKey(sta.getCode());
                    mdbmTest.setMsgBody(reqJson);
                    mdbmTest.setMemo(MQ_WMS3_SEEDING);
                    mdbmTest.setSendTime(sdf.format(date));
                    mdbmTest.setMsgType("findStaLineByStaIdByTest_seeding");
                    mongoOperation.save(mdbmTest);
                } catch (Exception e) {
                    throw new BusinessException(0);
                }
            }
            
        }
        

        return pl;
    }

    // 创建O2O装箱实体
    public PickingListPackage commonCreatePickingListPackage(PickingList pl, String toLocation) {
        PickingListPackage plp = new PickingListPackage();
        ShopStoreInfo ssi = shopStoreInfoDao.getShopStoreInfoByCode(toLocation);
        if (ssi == null) {
            throw new BusinessException(ErrorCode.SHOP_STORE_INFO_NOT_FOUND);
        }
        plp.setAddress(ssi.getAddress());// 查看维护表的信息T_BI_SHOP_STORE
        plp.setPickingListId(pl);
        plp.setReceiver(ssi.getReceiver());
        plp.setSkuId(null);
        plp.setStatus(DefaultStatus.CREATED);// 新建
        plp.setTelephone(ssi.getTelephone());
        plp.setCity(ssi.getCity());
        plp.setProvince(ssi.getProvince());
        plp.setDistrict(ssi.getDistrict());
        plp.setTrackingNo(null);
        plp.setWeight(null);
        pickingListPackageDao.save(plp);
        return plp;
    }

    // 创建O2O装箱实体
    public PickingListPackage createPickingListPackage(Long plId, String toLocation) {
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        if (null == pl) {
            throw new BusinessException(ErrorCode.PICKING_LIST_NOT_FOUND);
        }
        return commonCreatePickingListPackage(pl, toLocation);
    }

    /**
     * 设置PickingList的 sendCity值，必须单一城市或者为空
     * 
     * @param cityList
     * @return
     */
    private String getCityByCityList(List<String> cityList) {
        if (cityList == null || cityList.size() > 1) {
            return null;
        } else {
            return cityList.get(0);
        }
    }

    /**
     * 获取仓库下对应的状态 bin.hu
     * 
     * @param ouId
     * @param checkModel
     * @return
     */
    @Override
    public BiWarehouseAddStatusCommand getBiWhST(Long ouId, PickingListCheckMode checkModel, int type, int status, Integer staIdList, Long plid) {
        BiWarehouseAddStatusCommand biTop = null;
        BiWarehouseAddStatusCommand biStatus = null;
        if (type == 0) {
            // 查询仓库下sort排序第一的状态(创建配货清单)
            biTop = biDao.getBiWHTop(ouId, new BeanPropertyRowMapper<BiWarehouseAddStatusCommand>(BiWarehouseAddStatusCommand.class));
        }
        if (type == 1) {
            // 查询仓库下sort排序第一的状态(修改配货清单状态)
            biTop = biDao.getBiWHPickStatusAboveTop(ouId, status, new BeanPropertyRowMapper<BiWarehouseAddStatusCommand>(BiWarehouseAddStatusCommand.class));
        }
        if (biTop != null) {
            if (checkModel.getValue() == PickingListCheckMode.DEFAULE.getValue() || checkModel.getValue() == PickingListCheckMode.PICKING_SPECIAL.getValue()) {
                // 如果是多件&特殊商品，那仓库状态支持分拣
                if (staIdList > 0) {
                    // 新建配货清单
                    if (staIdList > 1) {
                        // 多个作业单的配货清单，支持分拣
                        biStatus = biTop;
                    }
                    if (staIdList == 1) {
                        // 单个作业单的配货清单，不支持分拣
                        if (biTop.getStatus() == WhAddStatusMode.SEPARATION.getValue()) {
                            biStatus = biDao.getBiWHAboveTop(ouId, biTop.getStatus(), new BeanPropertyRowMapper<BiWarehouseAddStatusCommand>(BiWarehouseAddStatusCommand.class));
                        } else {
                            biStatus = biTop;
                        }
                    }
                } else {
                    // 修改配货清单状态
                    List<StockTransApplication> staList = staDao.findWhStaByPlId(plid);
                    if (staList.size() > 1) {
                        // 多个作业单的配货清单，支持分拣
                        biStatus = biTop;
                    }
                    if (staList.size() == 1) {
                        // 单个作业单的配货清单，不支持分拣
                        if (biTop.getStatus() == WhAddStatusMode.SEPARATION.getValue()) {
                            biStatus = biDao.getBiWHAboveTop(ouId, biTop.getStatus(), new BeanPropertyRowMapper<BiWarehouseAddStatusCommand>(BiWarehouseAddStatusCommand.class));
                        } else {
                            biStatus = biTop;
                        }
                    }
                }
            }
            if (checkModel.getValue() == PickingListCheckMode.PICKING_CHECK.getValue() || checkModel.getValue() == PickingListCheckMode.PICKING_SECKILL.getValue() || checkModel.getValue() == PickingListCheckMode.PICKING_GROUP.getValue()
                    || checkModel.getValue() == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                // 如果是单件&秒杀&套装&团购，那仓库状态不支持分拣
                if (biTop.getStatus() == WhAddStatusMode.SEPARATION.getValue()) {
                    // 如果仓库下排序第一的状态是待分拣，需要重新查询下status > biTop.status的数据
                    biStatus = biDao.getBiWHAboveTop(ouId, biTop.getStatus(), new BeanPropertyRowMapper<BiWarehouseAddStatusCommand>(BiWarehouseAddStatusCommand.class));
                } else {
                    biStatus = biTop;
                }
            }
            return biStatus;
        } else {
            return null;
        }
    }

    /**
     * 判断sta是否包含大件商品
     * 
     * @param skuMaxLength
     * @return
     */
    private Boolean isBigBox(BigDecimal skuMaxLength) {
        if (skuMaxLength == null) {
            skuMaxLength = new BigDecimal(0);
        }
        BigDecimal limit = new BigDecimal(chooseOptionDao.findSkuMaxLength(new BeanPropertyRowMapper<ChooseOption>(ChooseOption.class)).getOptionKey());
        if (skuMaxLength.compareTo(limit) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算pickinglist中计划单据数与计划执行产品数
     * 
     * @param pl
     */
    private void setPickingListPlanQty(PickingList pl) {
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

    /**
     * 根据查询条件查询列表
     * 
     * @param shoplist
     * @param cityList
     * @param sta
     * @param date
     * @param date2
     * @param model
     * @param categoryId
     * @param isNeedInvoice
     * @param checkModel
     * @param id
     * @return
     */
    public List<Long> findStaForPickingByModelListNew(String whZoonList, Integer mergeWhZoon, Integer isMargeWhZoon, String zoonList, String zoonId, Integer mergePickZoon, Integer isMergeInt, List<SkuSizeConfig> ssList, String shoplist, String shoplist1,
            List<String> cityList, StockTransApplication sta, Integer transTimeType, Date fromDate, Date toDate, Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice, Long ouId,
            PickingListCheckMode checkModel, Boolean isSp, String isQs, int rowNum, Integer sumCount, String otoAll, Boolean countAreasCp) {
        String code = null;
        String lpCode = null;
        String refSlipCode = null;
        String barCode = null;
        Integer status = null;
        Integer packingType = null; // 包装类型
        Integer isSpecialPackaging = null;
        Integer specialType = null;
        Integer pickingType = null;
        Long packageSkuId = null;
        Long skuQty = null;
        String skus = null;
        String toLocation = null;
        String zoneId = null;
        String whZoneId = null;
        Long pickSubType = null;
        String isPreSale = null;// 是否是预售订单
        Integer orderType2 = null;// 订单类型
        List<String> otoList = null;
        Integer cp = null;
        if ("".equals(zoonId)) {
            zoneId = null;
            whZoneId = null;
        } else {
            // 拣货区域和 仓库区域公用一个集合。 如果 拣货区域为空，那 取值仓库区域。
            if ("".equals(zoonList)) {
                whZoneId = zoonId;
            } else {
                zoneId = zoonId;
            }
        }
        List<String> codeList = null;
        if (transTimeType != null && transTimeType == 1) {
            transTimeType = null;
        }
        List<String> shopInnerCodes = null;
        if (StringUtils.hasText(shoplist)) {
            if (StringUtils.hasLength(shoplist)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist.split("\\|");
                for (String s : shopArrays) {
                    shopInnerCodes.add(s);
                }
            }
        }
        if (StringUtils.hasText(shoplist1)) {
            if (StringUtils.hasLength(shoplist1)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist1.split("\\|");
                for (String s : shopArrays) {
                    List<BiChannelCommand> channelList = channelGroupManager.findAllChannelRefByGroupCode(s);
                    for (BiChannelCommand biChannelCommand : channelList) {
                        shopInnerCodes.add(biChannelCommand.getCode());
                    }

                }
            }
        }
        // 是否特殊处理
        if (isSp != null && !isSp) {
            specialType = 1;
        }
        // 是否QS
        if (isSp != null && isSp) {
            isSpecialPackaging = 1;
        }

        // 是否QSAndSq
        if ("qsAndSq".equals(isQs)) {
            specialType = 1;
            isSpecialPackaging = 1;
        }

        // 是否QSAndSq
        if ("notQsAndSq".equals(isQs)) {
            specialType = null;
            isSpecialPackaging = null;
        }

        if (sta != null) {
            if (StringUtils.hasText(sta.getCode())) {
                code = sta.getCode();
            }
            if (StringUtils.hasText(sta.getRefSlipCode())) {
                refSlipCode = sta.getRefSlipCode();
            }

            // isSpecialPackaging = sta.getIsSpecialPackaging();
            pickingType = sta.getPickingType().getValue();
            skuQty = sta.getSkuQty();
            if (StringUtils.hasText(sta.getSkus())) {
                skus = sta.getSkus();
            }
            if (StringUtils.hasText(sta.getMemo())) {
                barCode = sta.getMemo();
            }
            if (sta.getStatus() != null) {
                status = sta.getStatus().getValue();
            }
            if (sta.getPackingType() != null) {
                packingType = sta.getPackingType().getValue();
            }
            if (sta.getPackageSku() != null) {
                packageSkuId = sta.getPackageSku();
            }
            if (StringUtils.hasText(sta.getToLocation())) {
                toLocation = sta.getToLocation();
            }
            if (StringUtils.hasText(sta.getIsPreSale())) {
                isPreSale = sta.getIsPreSale();
            }
            if (sta.getOrderType2() != null) {
                orderType2 = sta.getOrderType2();
            }
            if (sta.getStaDeliveryInfo() != null) {
                if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
                    codeList = new ArrayList<String>();
                    lpCode = sta.getStaDeliveryInfo().getLpCode();
                    String[] lpCodeS = lpCode.split(",");
                    for (int i = 0; i < lpCodeS.length; i++) {
                        codeList.add(lpCodeS[i]);
                    }
                }
            }
            // if (sta.getStaDeliveryInfo() != null) {
            // if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
            // lpCode = sta.getStaDeliveryInfo().getLpCode();
            // }
            // }
            if (sta.getPickSubType() != null) {
                pickSubType = sta.getPickSubType();
            }
        }
        if (ssList != null) {
            List<SkuSizeConfig> sList = new ArrayList<SkuSizeConfig>();
            for (int i = 0; i < ssList.size(); i++) {
                if (!(ssList.get(i).getMaxSize().intValue() == -1 && ssList.get(i).getMinSize().intValue() == -1 && ssList.get(i).getGroupSkuQtyLimit().intValue() == -1)) {
                    sList.add(ssList.get(i));
                }
            }
            ssList = sList;
        }

        // dropDown 优先发货城市标志位(opposite表示非优先发货城市)
        Boolean flag = true;
        if (null != cityList && cityList.size() > 0) {
            if (cityList.contains("opposite")) {
                flag = false;
            }
        }
        if (!StringUtil.isEmpty(otoAll)) {
            otoList = new ArrayList<String>();
            String[] oto = otoAll.split(",");
            for (String s : oto) {
                otoList.add(s);
            }
        }
        List<String> pickZoneList = null;
        if (countAreasCp != null && countAreasCp) {
            cp = 1;
            // 新增拣货区域查询限制
            if (StringUtils.hasText(zoonList)) {
                if (StringUtils.hasLength(zoonList)) {
                    pickZoneList = new ArrayList<String>();
                    String[] ZoneArrays = zoonList.split("\\|");
                    pickZoneList = StringUtil.getCountAreas(ZoneArrays);
                    // for (int i = 0; i < ZoneArrays.length; i++) {
                    // pickZoneList.add(ZoneArrays[i]);
                    // }
                }
            }
        }
        return staDao.findStaForPickingByModelListNew(pickSubType, whZoneId, whZoonList, mergeWhZoon, isMargeWhZoon, zoneId, zoonList, mergePickZoon, isMergeInt, barCode, packageSkuId, ssList, isCod, isSn, skus, pickingType, skuQty, shopInnerCodes,
                cityList, fromDate, toDate, orderCreateTime, toOrderCreateTime, code, refSlipCode, isNeedInvoice, codeList, specialType, isSpecialPackaging, categoryId, status, packingType, ouId, transTimeType, rowNum, sumCount, toLocation, flag,
                isPreSale, orderType2, otoList, cp, pickZoneList, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 查找仓库相关的团购商品
     */
    @Override
    public Pagination<Sku> queryGroupBuyingSku(int start, int pageSize, Long id, Sort[] sorts) {
        return skuDao.queryGroupBuyingSku(start, pageSize, id, sorts, new BeanPropertyRowMapper<Sku>(Sku.class));
    }

    public String findexpNameByPlid(Long plid) {
        return staDeliveryInfoDao.findexpNameByPlid(plid, new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 配货清单占用库存成功更新数据
     * 
     * @param pickingListId
     * @param operatorId
     */
    public void occupiedPickingListSuccess(Long pickingListId, Long operatorId) {
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        if (pl == null) {
            throw new BusinessException(ErrorCode.PICKING_LIST_NOT_FOUND, new Object[] {operatorId});
        }
        User operator = null;
        if (operatorId != null) {
            operator = userDao.getByPrimaryKey(operatorId);
            if (operator == null) {
                throw new BusinessException(ErrorCode.USER_NOT_FOUND, new Object[] {operatorId});
            }
        }
        pl.setStatus(PickingListStatus.PACKING);
        setPickingListPlanQty(pl);
        if (operatorId != null) {
            pl.setOperator(operator);
        }
        pl.setExecutedTime(new Date());
        pl.setPickingTime(new Date());
        // staDao.updatePgIndex(pickingListId);
    }

    /**
     * 配货清单占用库存失败更新数据
     * 
     * @param pickingListId
     * @param operatorId
     */
    public void occupiedPickingListFailed(Long pickingListId, Long operatorId) {
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        if (pl == null) {
            throw new BusinessException(ErrorCode.PICKING_LIST_NOT_FOUND, new Object[] {operatorId});
        }
        User operator = userDao.getByPrimaryKey(operatorId);
        if (operator == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, new Object[] {operatorId});
        }
        pl.setStatus(PickingListStatus.FAILED);
        setPickingListPlanQty(pl);
        pl.setOperator(operator);
        pl.setExecutedTime(new Date());
        pl.setPickingTime(new Date());
    }

    @Transactional
    public Long confirmPickingList(Long pickingListId, Long operatorId, Long ouid) {
        
        

        boolean isError = false;// 是否有失败sta
        List<Long> staList = new ArrayList<Long>(); // staList
        PickingList pl = pickingListDao.getByPrimaryKey(pickingListId);
        Boolean isSkipWeight = warehouseDao.getByOuId(ouid).getIsSkipWeight();
        List<SkuCommand> skuList = skuDao.findNoThreeDimensionalSku(pickingListId, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        if (skuList.size() > 0 && isSkipWeight == true) {
            pl.setStatus(PickingListStatus.FAILED);
            pickingListDao.save(pl);
            throw new BusinessException(ErrorCode.SKU_NO_HHW);
        } else {
            mongoOperation.remove(new Query(Criteria.where("pinkingListId").is(pickingListId)), MongDbNoThreeDimensional.class);
        }
        
        if (pl == null) {
            throw new BusinessException(ErrorCode.PICKING_LIST_NOT_FOUND, new Object[] {operatorId});
        }
        User operator = userDao.getByPrimaryKey(operatorId);
        if (operator == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, new Object[] {operatorId});
        }
        for (StockTransApplication sta : pl.getStaList()) {
            staList.add(sta.getId());
            if (StockTransApplicationStatus.FAILED.equals(sta.getStatus())) {
                // 存在配货失败
                isError = true;
                break;
            }
        }
        if (isError) {
            // 库存占用失败
            pl.setStatus(PickingListStatus.FAILED);
        } else {
            pl.setStatus(PickingListStatus.PACKING);
            List<StockTransApplication> stalist = staDao.findStaByPickingList(pickingListId, ouid);
            if (pl.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                secKillSkuDao.deleteSecSkillSkuIsSystemByOu(ouid, stalist.get(0).getSkus());
            } else {
                for (StockTransApplication sta : stalist) {
                    SecKillSkuCounter ssc = new SecKillSkuCounter();
                    ssc.setCrateTime(new Date());
                    ssc.setQty(-1L);
                    ssc.setOu(sta.getMainWarehouse());
                    ssc.setSkus(sta.getSkus());
                    secKillSkuCounterDao.save(ssc);
                }
            }
        }
        setPickingListPlanQty(pl);
        pl.setOperator(operator);
        pl.setExecutedTime(new Date());
        pl.setPickingTime(new Date());
        // 以下逻辑自动化仓匹配快递集货口,播种消息保存
        Long msgId = null;
        Warehouse se = warehouseDao.getByOuId(ouid);
        if (se != null && se.getIsAutoWh() != null && se.getIsAutoWh() == true) {
            try {
                msgId = marryStaShiipingCode(pl.getCheckMode(), pl.getCode(), staList, ouid);
            } catch (Exception e) {
                log.error(pl.getId() + ":marryStaShiiping is error---", e);
            }
        }


        


        return msgId;
    }

    /**
     * 秒杀商品计数器计算
     * 
     * @param sta
     */
    public void sedKillSkuCounterByPickingList(StockTransApplication sta) {
        // SecKillSkuCounter ssc = new SecKillSkuCounter();
        // ssc.setCrateTime(new Date());
        // ssc.setQty(-1L);
        // ssc.setOu(sta.getMainWarehouse());
        // ssc.setSkus(sta.getSkus());
        secKillSkuCounterDao.deleteSecKillCounterByStaId(sta.getId());
        if (sta.getSkuQty().equals(2L) && Long.parseLong(sta.getSkus().split(":")[0]) == 2) {
            Long sku1Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[0].split(";")[0]);
            Long sku2Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[1].split(";")[0]);
            packageSkuCounterDao.discountCounter(sku1Id, sku2Id, sta.getMainWarehouse().getId());
        }
    }

    /**
     * 查询配货清单 更具所有的可创建的作业单
     */
    public List<Long> findPickingListByAllSta(Long warehouseId) {
        return staDao.findCreatePKListAllSta(warehouseId, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public void matchingTransNoByPlId(Long plId) {
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        if (pl.getCheckMode().equals(PickingListCheckMode.PCIKING_O2OQS)) {
            // O2O+QS,以箱为基准获取运单号
            transOlManagerProxy.matchingTransNoByPackage(pl.getId());
        } else {
            // 匹配运单号通用
            transOlManagerProxy.matchingTransNo(pl.getId(), null);
        }
    }

    @Override
    public List<StockTransApplicationCommand> findStaForPickingCount(List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList, Date date, Date date2, Date date3, Date date4, StockTransApplication sta, Long categoryId,
            Integer isSn, Integer transTimeType, Integer isNeedInvoice, Integer isCod, Long id) {
        String code = null;
        String lpCode = null;
        String refSlipCode = null;
        String barCode = null;
        String code1 = null;
        String code2 = null;
        String code3 = null;
        String code4 = null;
        Integer status = null;
        Integer specialType = null;
        Integer isSpecialPackaging = null;
        // Boolean isSedKill = null;
        Integer pickingType = null;
        Long packageSkuId = null;
        Long skuQty = null;
        String skus = null;
        String toLocation = null;
        List<String> shopInnerCodes = null;
        List<String> codeList = null;
        if (StringUtils.hasText(shoplist)) {
            if (StringUtils.hasLength(shoplist)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist.split("\\|");
                for (String s : shopArrays) {
                    shopInnerCodes.add(s);
                }
            }
        }
        if (StringUtils.hasText(shoplist1)) {
            if (StringUtils.hasLength(shoplist1)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist1.split("\\|");
                for (String s : shopArrays) {
                    List<BiChannelCommand> channelList = channelGroupManager.findAllChannelRefByGroupCode(s);
                    for (BiChannelCommand biChannelCommand : channelList) {
                        shopInnerCodes.add(biChannelCommand.getCode());
                    }

                }
            }
        }
        if (sta != null) {
            if (StringUtils.hasText(sta.getCode())) {
                code = sta.getCode();
            }
            if (StringUtils.hasText(sta.getRefSlipCode())) {
                refSlipCode = sta.getRefSlipCode();
            }
            // 特殊处理
            if (sta.getSpecialType() != null) {
                specialType = sta.getSpecialType().getValue();
            }
            // QS
            if (sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging()) {
                isSpecialPackaging = 1;
            }
            // isSpecialPackaging = sta.getIsSpecialPackaging();
            // isSedKill = sta.getIsSedKill();
            // pickingType = sta.getPickingType().getValue();
            skuQty = sta.getSkuQty();
            if (sta.getPackageSku() != null) {
                packageSkuId = sta.getPackageSku();
            }
            if (StringUtils.hasText(sta.getSkus())) {
                skus = sta.getSkus();
            }
            if (StringUtils.hasText(sta.getMemo())) {
                barCode = sta.getMemo();
            }
            if (StringUtils.hasText(sta.getCode1())) {
                code1 = sta.getCode1();
            }
            if (StringUtils.hasText(sta.getCode2())) {
                code2 = sta.getCode2();
            }
            if (StringUtils.hasText(sta.getCode3())) {
                code3 = sta.getCode3();
            }
            if (StringUtils.hasText(sta.getCode4())) {
                code4 = sta.getCode4();
            }
            if (sta.getStatus() != null) {
                status = sta.getStatus().getValue();
            }
            if (StringUtils.hasText(sta.getToLocation())) {
                toLocation = sta.getToLocation();
            }
            if (sta.getStaDeliveryInfo() != null) {
                if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
                    codeList = new ArrayList<String>();
                    lpCode = sta.getStaDeliveryInfo().getLpCode();
                    String[] lpCodeS = lpCode.split(",");
                    for (int i = 0; i < lpCodeS.length; i++) {
                        codeList.add(lpCodeS[i]);
                    }
                }
            }
        }
        if (ssList != null) {
            List<SkuSizeConfig> sList = new ArrayList<SkuSizeConfig>();
            for (int i = 0; i < ssList.size(); i++) {
                if (!(ssList.get(i).getMaxSize().intValue() == -1 && ssList.get(i).getMinSize().intValue() == -1 && ssList.get(i).getGroupSkuQtyLimit().intValue() == -1)) {
                    sList.add(ssList.get(i));
                }
            }
            ssList = sList;
        }
        return staDao.findStaForPickingCount(transTimeType, barCode, code1, code2, code3, code4, packageSkuId, ssList, isCod, isSn, skus, pickingType, skuQty, shopInnerCodes, cityList, date, date2, date3, date4, code, refSlipCode, isNeedInvoice,
                codeList, specialType, isSpecialPackaging, categoryId, status, id, toLocation, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public List<Long> findStaForPickingByModelListNew1(String zoonId, Integer isMergeInt, List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList, StockTransApplication sta, Integer transTimeType, Date fromDate, Date toDate,
            Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice, Long ouId, PickingListCheckMode checkModel, Boolean isSp, String isQs, int rowNum, Integer sumCount) {
        String code = null;
        String lpCode = null;
        String refSlipCode = null;
        String barCode = null;
        Integer status = null;
        Integer isSpecialPackaging = null;
        Integer specialType = null;
        Integer pickingType = null;
        Long packageSkuId = null;
        Long skuQty = null;
        String skus = null;
        String toLocation = null;
        List<String> codeList = null;
        String zoneId = null;
        if ("".equals(zoonId)) {
            zoneId = null;
        } else {
            zoneId = zoonId;
        }
        if (transTimeType != null && transTimeType == 1) {
            transTimeType = null;
        }
        List<String> shopInnerCodes = null;
        if (StringUtils.hasText(shoplist)) {
            if (StringUtils.hasLength(shoplist)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist.split("\\|");
                for (String s : shopArrays) {
                    shopInnerCodes.add(s);
                }
            }
        }
        if (StringUtils.hasText(shoplist1)) {
            if (StringUtils.hasLength(shoplist1)) {
                shopInnerCodes = new ArrayList<String>();
                String[] shopArrays = shoplist1.split("\\|");
                for (String s : shopArrays) {
                    List<BiChannelCommand> channelList = channelGroupManager.findAllChannelRefByGroupCode(s);
                    for (BiChannelCommand biChannelCommand : channelList) {
                        shopInnerCodes.add(biChannelCommand.getCode());
                    }

                }
            }
        }
        // 是否特殊处理
        if (isSp != null && !isSp) {
            specialType = 1;
        }
        // 是否QS
        if (isSp != null && isSp) {
            isSpecialPackaging = 1;
        }

        // 是否QSAndSq
        if ("qsAndSq".equals(isQs)) {
            specialType = 1;
            isSpecialPackaging = 1;
        }

        // 是否QSAndSq
        if ("notQsAndSq".equals(isQs)) {
            specialType = null;
            isSpecialPackaging = null;
        }

        if (sta != null) {
            if (StringUtils.hasText(sta.getCode())) {
                code = sta.getCode();
            }
            if (StringUtils.hasText(sta.getRefSlipCode())) {
                refSlipCode = sta.getRefSlipCode();
            }

            // isSpecialPackaging = sta.getIsSpecialPackaging();
            // pickingType = sta.getPickingType().getValue();
            skuQty = sta.getSkuQty();
            if (StringUtils.hasText(sta.getSkus())) {
                skus = sta.getSkus();
            }
            if (StringUtils.hasText(sta.getMemo())) {
                barCode = sta.getMemo();
            }
            if (sta.getStatus() != null) {
                status = sta.getStatus().getValue();
            }
            if (sta.getPackageSku() != null) {
                packageSkuId = sta.getPackageSku();
            }
            if (StringUtils.hasText(sta.getToLocation())) {
                toLocation = sta.getToLocation();
            }
            if (sta.getStaDeliveryInfo() != null) {
                if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
                    codeList = new ArrayList<String>();
                    lpCode = sta.getStaDeliveryInfo().getLpCode();
                    String[] lpCodeS = lpCode.split(",");
                    for (int i = 0; i < lpCodeS.length; i++) {
                        codeList.add(lpCodeS[i]);
                    }
                }
            }
            // if (sta.getStaDeliveryInfo() != null) {
            // if (StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
            // lpCode = sta.getStaDeliveryInfo().getLpCode();
            // }
            // }
        }
        if (ssList != null) {
            List<SkuSizeConfig> sList = new ArrayList<SkuSizeConfig>();
            for (int i = 0; i < ssList.size(); i++) {
                if (!(ssList.get(i).getMaxSize().intValue() == -1 && ssList.get(i).getMinSize().intValue() == -1 && ssList.get(i).getGroupSkuQtyLimit().intValue() == -1)) {
                    sList.add(ssList.get(i));
                }
            }
            ssList = sList;
        }
        return staDao.findStaForPickingByModelListNew1(zoneId, isMergeInt, barCode, packageSkuId, ssList, isCod, isSn, skus, pickingType, skuQty, shopInnerCodes, cityList, fromDate, toDate, orderCreateTime, toOrderCreateTime, code, refSlipCode,
                isNeedInvoice, codeList, specialType, isSpecialPackaging, categoryId, status, ouId, transTimeType, rowNum, sumCount, toLocation, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public Pagination<StockTransApplicationCommand> findStaLsingle(int start, int pageSize, StockTransApplicationCommand sta, Integer transTimeType, Date fromDate, Date toDate, Long id, Sort[] sorts) {
        String code = null;
        String slipCode = null;
        String slipCode1 = null;
        String slipCode2 = null;
        if (StringUtils.hasText(sta.getCode())) {
            code = sta.getCode();
        }
        if (StringUtils.hasText(sta.getRefSlipCode())) {
            slipCode = sta.getRefSlipCode();
        }
        if (StringUtils.hasText(sta.getSlipCode1())) {
            slipCode1 = sta.getSlipCode1();
        }
        if (StringUtils.hasText(sta.getSlipCode2())) {
            slipCode2 = sta.getSlipCode2();
        }
        return staDao.findStaLsingle(start, pageSize, transTimeType, id, fromDate, toDate, code, slipCode, slipCode1, slipCode2, sorts, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    /**
     * 配货失败查询
     */
    @Override
    public Pagination<StockTransApplicationCommand> findPickFailedByWhId(String owner, Integer transTimeType, String lpCode, List<String> cityList, Integer isCod, Integer pickStatus, Long pickSort, Long categoryId, Integer isNeedInvoice, int start,
            int pageSize, Date date, Date date2, Date date3, Date date4, StockTransApplication sta, Long ouId, String isPreSale, Sort[] sorts) {
        String code = null;
        String slipCode = null;
        List<String> codeList = null;
        String owners = null;
        if (StringUtils.hasText(owner)) {
            owners = owner;
        }
        if (sta != null) {
            if (StringUtils.hasText(sta.getCode())) {
                code = sta.getCode();
            }
            if (StringUtils.hasText(sta.getRefSlipCode())) {
                slipCode = sta.getRefSlipCode();
            }
        }
        if (StringUtils.hasText(lpCode)) {
            codeList = new ArrayList<String>();
            String[] lpCodeS = lpCode.split(",");
            for (int i = 0; i < lpCodeS.length; i++) {
                codeList.add(lpCodeS[i]);
            }
        }
        if (isPreSale != null && "".equals(isPreSale)) {
            isPreSale = null;
        }
        return staDao.findPickFailedByWhId(start, pageSize, owners, transTimeType, codeList, cityList, isCod, pickStatus, pickSort, categoryId, isNeedInvoice, date, date2, date3, date4, code, slipCode, ouId, isPreSale, sorts,
                new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));

    }

    /**
     * 失败结果再分配
     */
    public void failedReusltAgainPick(String againSort, String againType, String owner, Integer transTimeType, String lpCode, List<String> cityList, Integer isCod, Integer pickStatus, Long pickSort, Long categoryId, Integer isNeedInvoice, Date date,
            Date date2, Date date3, Date date4, StockTransApplication sta, String isPreSale, Long ouId) {
        if ("1".endsWith(againType)) {
            // 将所有分配失败的订单添加至下次分配队列
            Integer errorCount = null;
            Warehouse house = warehouseDao.getByOuId(ouId);
            if (house != null) {
                errorCount = house.getOcpErrorLimit();
            }
            if (StringUtils.hasText(againSort)) {
                errorCount = Integer.parseInt(againSort);
            }
            staDao.failedReusltAgainPick(ouId, errorCount);
        } else {
          /*  SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            System.out.println(aDate.format(date));*/
            // 查询结果再分配
            String code = null;
            String slipCode = null;
            List<String> codeList = null;
            Integer ocpSort = 0;
            String owners = null;
            if (StringUtils.hasText(owner)) {
                owners = owner;
            }
            if (sta != null) {
                if (StringUtils.hasText(sta.getCode())) {
                    code = sta.getCode();
                }
                if (StringUtils.hasText(sta.getRefSlipCode())) {
                    slipCode = sta.getRefSlipCode();
                }
            }
            if (StringUtils.hasText(lpCode)) {
                codeList = new ArrayList<String>();
                String[] lpCodeS = lpCode.split(",");
                for (int i = 0; i < lpCodeS.length; i++) {
                    codeList.add(lpCodeS[i]);
                }
            }
            if (StringUtils.hasText(againSort)) {
                ocpSort = Integer.parseInt(againSort);
            }
            if (isPreSale != null && "".equals(isPreSale)) {
                isPreSale = null;
            }
            staDao.updatePickFailedByWhId(ocpSort, owners, transTimeType, codeList, cityList, isCod, pickStatus, pickSort, categoryId, isNeedInvoice, date, date2, date3, date4, code, slipCode, ouId, isPreSale,
                    new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        }

    }

    /**
     * 自动化-作业单集货口匹配 xiaolong.fei
     */
    public Long marryStaShiipingCode(PickingListCheckMode checkModle, String plCode, List<Long> staList, Long ouId) {
        // 播种消息实体
        com.jumbo.wms.model.automaticEquipment.MsgToWcsBoZRequest boz = new com.jumbo.wms.model.automaticEquipment.MsgToWcsBoZRequest();
        List<WcsOrder> orders = new ArrayList<WcsOrder>(); // 订单数组
        // 根据仓库获取所有集货规则，按优先级升序
        List<ShippingPointRoleLineCommand> shipList = shippingPointRoleLineDao.findShippingRoleByOuId(ouId, new BeanPropertyRowMapper<ShippingPointRoleLineCommand>(ShippingPointRoleLineCommand.class));
        for (int i = 0; i < staList.size(); i++) {
            if (log.isInfoEnabled()) {
                log.info("marryStaShiipingCode strat:{}", staList.get(i));
            }
            StockTransApplication sta = staDao.getByPrimaryKey(staList.get(i));
            StaDeliveryInfo staInfo = staDeliveryInfoDao.getByPrimaryKey(staList.get(i));
            if (sta == null || staInfo == null) {
                throw new BusinessException(ErrorCode.NOT_FIND_STA);
            }
            // 按作业单遍历集货规则
            for (ShippingPointRoleLineCommand role : shipList) {
                /** 校验物流商,不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getLpcode()) && !staInfo.getLpCode().equals(role.getLpcode())) {
                    continue;
                }
                /** 校验省,作业单信息包含匹配规则信息，不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getProvince()) && !staInfo.getProvince().contains(role.getProvince())) {
                    continue;
                }
                /** 校验市,作业单信息包含匹配规则信息，不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getCity()) && !staInfo.getCity().contains(role.getCity())) {
                    continue;
                }
                /** 校验区,作业单信息包含匹配规则信息，不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getDistrict()) && !staInfo.getDistrict().contains(role.getDistrict())) {
                    continue;
                }
                /** 校验地址,作业单信息包含匹配规则信息，不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getAddress()) && !staInfo.getAddress().contains(role.getAddress())) {
                    continue;
                }
                /** 校验时效类型,如果作业单信息时效类型为空，规则里不为空，则一条规则遍历 **/
                if (role.getTimeTypes() != null && staInfo.getTransTimeType() == null) {
                    continue;
                }
                /** 校验时效类型,如果作业单信息时效类型不等于规则的时效类型，则一条规则遍历 **/
                if (role.getTimeTypes() != null && staInfo.getTransTimeType().getValue() != role.getTimeTypes()) {
                    continue;
                }
                /** 校验店铺,不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getOwner()) && !sta.getOwner().equals(role.getOwner())) {
                    continue;
                }
                /** 校验COD,不满足继续下一条规则遍历 **/
                if (role.getIsCod() != null && ((staInfo.getIsCod() && !role.getIsCod()) || (!staInfo.getIsCod() && role.getIsCod()))) {
                    continue;
                }
                /** 校验作业类型,不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getStaType()) && Integer.parseInt(role.getStaType()) != sta.getType().getValue()) {
                    continue;
                }
                /** 校验作业单号,不满足继续下一条规则遍历 **/
                if (StringUtils.hasLength(role.getStaCode()) && !sta.getCode().equals(role.getStaCode())) {
                    continue;
                }
                // 添加集货口负载均衡逻辑
                shareShippingPointPressure(sta, role);
                // 遍历到最后，匹配到当前规则，跳出循环，进入下一个作业单匹配
                // sta.setShipmentCode(role.getPointCode());
                staDao.save(sta);
                break;
            }
            if (log.isInfoEnabled()) {
                log.info("marryStaShiipingCode end:{}", staList.get(i));
            }
            if (checkModle.equals(PickingListCheckMode.DEFAULE) || checkModle.equals(PickingListCheckMode.PICKING_SPECIAL)) {
                // 封装数据成json格式
                WcsOrder order = new WcsOrder();
                order.setOrderID(sta.getRefSlipCode());
                if (sta.getIndex() != null) {
                    order.setIndex(sta.getIndex().toString());
                }
                List<StaLine> lines = staLineDao.findByStaId(sta.getId());// 订单明细
                List<WcsSku> skus = new ArrayList<WcsSku>();// 商品明细
                for (StaLine line : lines) {
                    Sku sku = line.getSku();
                    WcsSku wcsSku = new WcsSku();
                    wcsSku.setOrderNumber(line.getId());
                    wcsSku.setSkuCode(sku.getCode());
                    wcsSku.setBarcode(sku.getBarCode());
                    wcsSku.setSkuname(sku.getName());
                    wcsSku.setCount(line.getQuantity().intValue());
                    wcsSku.setIsBatchNo(1); // 是否管理批次
                    wcsSku.setIsOg(0); // 是否管理产地
                    // 多条码
                    List<SkuBarcode> codes = skuBarcodeDao.findBySkuId(line.getSku().getId());
                    if (codes.size() > 0) {
                        List<BarCodes> codeList = new ArrayList<BarCodes>();
                        for (SkuBarcode code : codes) {
                            BarCodes barCode = new BarCodes();
                            barCode.setBarcode(code.getBarcode());
                            codeList.add(barCode);
                        }
                        wcsSku.setAddBarcodes(codeList);
                    }
                    // 是否效期
                    if (sku.getStoremode().equals(InboundStoreMode.SHELF_MANAGEMENT)) {
                        wcsSku.setIsSl(1);
                    } else {
                        wcsSku.setIsSl(0);
                    }
                    // 是否SN
                    if (sku.getIsSnSku() == null || !sku.getIsSnSku()) {
                        wcsSku.setIsSn(0);
                    } else {
                        wcsSku.setIsSn(1);
                    }
                    skus.add(wcsSku);
                }
                order.setSku(skus);
                orders.add(order);
            }
        }
        if (checkModle.equals(PickingListCheckMode.DEFAULE) || checkModle.equals(PickingListCheckMode.PICKING_SPECIAL)) {
            boz.setOrder(orders);
            boz.setWaveOrder(plCode); // 批次号
            String context = net.sf.json.JSONObject.fromObject(boz).toString();
            // 保存数据到中间表
            com.jumbo.wms.model.automaticEquipment.MsgToWcs msg = new com.jumbo.wms.model.automaticEquipment.MsgToWcs();
            msg.setContext(context);
            msg.setPickingListCode(plCode);
            msg.setCreateTime(new Date());
            msg.setErrorCount(0);
            msg.setStatus(true);
            msg.setType(com.jumbo.wms.model.automaticEquipment.WcsInterfaceType.SBoZhong);
            msgToWcsDao.save(msg);
            return msg.getId();
        } else {
            return null;
        }
    }

    /**
     * 添加集货口负载均衡规则henghui.zhang
     * 
     * @param sta
     * @param role
     */
    public void shareShippingPointPressure(StockTransApplication sta, ShippingPointRoleLineCommand role) {
        try {
            ShippingPoint shippingPoint = shippingPointDao.getPointByWcsCode(role.getPointCode());
            // 判断集货口类型
            if (org.apache.commons.lang3.StringUtils.equals(shippingPoint.getPointType(), "1")) {
                // 获取负载均衡集货口组
                List<ShippingPoint> pList = shippingPointDao.getPointByRefShippingPoint(shippingPoint.getId() + "", new BeanPropertyRowMapper<ShippingPoint>(ShippingPoint.class));
                if (pList.size() == 1) {
                    sta.setShipmentCode(((ShippingPoint) pList.get(0)).getWcsCode());
                    return;
                }
                Object[] points = pList.toArray();
                for (int i = 0; i < points.length; i++) {
                    ShippingPoint p = (ShippingPoint) points[i];
                    ShippingPointKeepNumber temp = mongoOperation.findOne(new Query(Criteria.where("id").is(p.getId())), ShippingPointKeepNumber.class, "wmsShippingPoint" + p.getId());
                    // 存储在MongoDB中的计数器对象
                    ShippingPointKeepNumber t = null;
                    if (temp == null) {
                        t = new ShippingPointKeepNumber();
                        t.setId(p.getId());
                        t.setKeepNumber(0l);
                        mongoOperation.save(t, "wmsShippingPoint" + p.getId());
                        temp = t;
                    }
                    if (temp.getKeepNumber() == null) {
                        mongoOperation.updateFirst(new Query(Criteria.where("id").is(p.getId())), Update.update("keepNumber", 0), ShippingPointKeepNumber.class, "wmsShippingPoint" + p.getId());
                    }
                    // 一轮循环过后，将所有计数器初始化
                    if (i == points.length - 1 && (temp.getKeepNumber() == p.getMaxAssumeNumber() || temp.getKeepNumber() == -1)) {
                        // flag=false;
                        for (Object p2 : points) {
                            mongoOperation.updateFirst(new Query(Criteria.where("id").is(((ShippingPoint) p2).getId())), Update.update("keepNumber", 0), ShippingPointKeepNumber.class, "wmsShippingPoint" + ((ShippingPoint) p2).getId());
                        }
                        // 一轮结束后的分配给起始集货点
                        ShippingPoint firstPoint = (ShippingPoint) points[0];
                        sta.setShipmentCode(firstPoint.getWcsCode());
                        mongoOperation.updateFirst(new Query(Criteria.where("id").is(firstPoint.getId())), Update.update("keepNumber", 1), ShippingPointKeepNumber.class, "wmsShippingPoint" + firstPoint.getId());
                        return;
                    }
                    // 判断此集货口分担作业单情况
                    if (temp.getKeepNumber() < p.getMaxAssumeNumber() && temp.getKeepNumber() != -1) {
                        // 继续往此集货口添加
                        sta.setShipmentCode(p.getWcsCode());
                        mongoOperation.updateFirst(new Query(Criteria.where("id").is(p.getId())), Update.update("keepNumber", temp.getKeepNumber() + 1), ShippingPointKeepNumber.class, "wmsShippingPoint" + p.getId());
                        break;
                    } else {
                        mongoOperation.updateFirst(new Query(Criteria.where("id").is(p.getId())), Update.update("keepNumber", -1), ShippingPointKeepNumber.class, "wmsShippingPoint" + p.getId());
                        // 移交给下一个集货口处理
                        continue;
                    }
                }
            } else {
                sta.setShipmentCode(role.getPointCode());
            }
        } catch (Exception e) {
            sta.setShipmentCode(role.getPointCode());
            log.error("集货口负载均衡异常！+wcsCode=" + role.getPointCode(), e);
        }
    }

    @Override
    public int resetMsgOutboundOrderStatus(String batchCode) {
        List<MsgOutboundOrder> outBoundList = msgOutboundOrderDao.getOutBoundListByBatchNo(Long.parseLong(batchCode));
        if (outBoundList == null) {
            return 0;
        }
        if (outBoundList.size() == 0) {
            return 0;
        }
        for (MsgOutboundOrder msgOutboundOrder : outBoundList) {
            if (org.apache.commons.lang3.StringUtils.equals(msgOutboundOrder.getStatus().getValue() + "", "2")) {
                // 清空批次号
                msgOutboundOrder.setBatchId(null);
                // 将状态设置为新建
                msgOutboundOrder.setStatus(DefaultStatus.valueOf(1));
                msgOutboundOrderDao.save(msgOutboundOrder);
                log.info("处理的msgOutboundOrder的id=" + msgOutboundOrder.getId() + ",清空的原始批次号batchId=" + batchCode + ",操作时间" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            } else {
                log.info("处理的msgOutboundOrder的id=" + msgOutboundOrder.getId() + "，其状态不为2!");
                return 2;
            }
        }
        return 1;
    }


    @Override
    public Pagination<WhPickingBatchCommand2> pickingListLogQueryDo(int start, int pageSize, WhPickingBatchCommand2 whPickingBatchCommand, Date startTime, Date startTime2, Date createTime, Date createTime2, Long userId, Long ouId, String pCode,
            String userName, Sort[] sorts) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String pCode01 = null;
        String userName01 = null;
        Date createTime01 = null;
        Date createTime02 = null;
        Date startTime01 = null;
        Date startTime02 = null;
        if (!"".equals(pCode)) {
            pCode01 = pCode;
        }
        if (!"".equals(userName)) {
            userName01 = userName;
        }
        if (createTime != null) {
            createTime01 = createTime;
        }
        if (!"".equals(createTime2)) {
            createTime02 = createTime2;
        }
        if (!"".equals(startTime)) {
            startTime01 = startTime;
        }
        if (!"".equals(startTime2)) {
            startTime02 = startTime2;
        }
        return whPickingBatchDao.getPickingListLogQueryPage(start, pageSize, ouId, startTime01, startTime02, createTime01, createTime02, pCode01, userName01, new BeanPropertyRowMapperExt<WhPickingBatchCommand2>(WhPickingBatchCommand2.class), sorts);
    }

    /**
     * 新二次分拣规则编码
     * 
     * @param index
     * @return
     */
    private String getRuleCode(int index) {
        String ruleCode;
        try {
            String[] ruleCodeList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            List<String> ruleList = new ArrayList<String>();
            ruleCode = null;
            // 生成二次分拣框号编码规则
            for (String rolecode : ruleCodeList) {
                for (int j = 1; j <= 4; j++) {
                    String s = rolecode + j;
                    ruleList.add(s);
                }
            }
            if (index <= ruleList.size() && index > 0) {
                ruleCode = ruleList.get(index - 1);
            } else {
                ruleCode = "批次箱号超过货格数";
            }
            return ruleCode;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void createPickingListToMq(Long ouId, String loc, Long userId) {
        List<MessageConfig> configs = new ArrayList<MessageConfig>();
        configs = getCreatePickingListCache(MqStaticEntity.WMS3_MQ_CREATE_SING_ORDER);
        if (configs != null && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {// 开
            List<CreatePickingListByMQ> list = new ArrayList<CreatePickingListByMQ>();
            CreatePickingListByMQ createPickingListByMQ = new CreatePickingListByMQ();
            createPickingListByMQ.setLoc(loc);
            createPickingListByMQ.setOuId(ouId);
            createPickingListByMQ.setUserId(userId);
            list.add(createPickingListByMQ);
            String msg = JsonUtil.writeValue(list);
            // 发送MQ
            MessageCommond mc = new MessageCommond();
            mc.setMsgId(loc + ouId + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
            mc.setMsgType("com.jumbo.wms.manager.warehouse.WareHouseOutBoundManagerImpl.createPickingListToMq");
            mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            mc.setMsgBody(msg);
            mc.setIsMsgBodySend(false);
            try {
                producerServer.sendDataMsgConcurrently(WMS3_MQ_CREATE_PICKINGLIST, mc);
            } catch (Exception e) {
                log.error("createPickingListToMq:" + e.toString());
            }
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mc, mdbm);
            mdbm.setMsgBody(msg);
            mdbm.setStaCode(loc);
            mdbm.setOtherUniqueKey(ouId.toString());
            mdbm.setMemo(WMS3_MQ_CREATE_PICKINGLIST);
            mongoOperation.save(mdbm);
        }
    }


    public List<MessageConfig> getCreatePickingListCache(String code) {
        if (StringUtil.isEmpty(code)) return null;
        List<MessageConfig> messageConfig = createPickingListCache.get(code); // 缓存中的数据不存在或者已过期
        if (messageConfig == null) {
            messageConfig = createPickingListRole();
        }
        return messageConfig;
    }


    public synchronized List<MessageConfig> createPickingListRole() {
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_CREATE_SING_ORDER, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        createPickingListCache.put(MqStaticEntity.WMS3_MQ_CREATE_SING_ORDER, configs, 60 * 1000);
        return configs;
    }


    public List<MessageConfig> getCreatePickingListCache1(String code) {
        if (StringUtil.isEmpty(code)) return null;
        List<MessageConfig> messageConfig = createPickingListCache1.get(code);
        // 缓存中的数据不存在或者已过期
        if (messageConfig == null) {
            messageConfig = createPickingListRole1();
        }
        return messageConfig;
    }

    public synchronized List<MessageConfig> createPickingListRole1() {
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_CREATE_SING_ORDER, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        createPickingListCache1.put(MqStaticEntity.WMS3_MQ_CREATE_SING_ORDER, configs, 60 * 1000);
        return configs;
    }

}
