package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.WhPickingBatchDao;
import com.jumbo.dao.baseinfo.TranUpgradeLogDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManagerProxy;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.model.CreatePickingListByMQ;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.baseinfo.TranUpgradeLog;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.DateUtil;

@Service("createPickingListManager")
public class CreatePickingListManagerProxyImpl extends BaseManagerImpl implements CreatePickingListManagerProxy {
    /**
     * 
     */
    private static final long serialVersionUID = 2841556229310643469L;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private TransOlManagerProxy transOlManagerProxy;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private TranUpgradeLogDao upgradeLogDao;
    @Autowired
    private StaDeliveryInfoDao deliveryInfoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WhPickingBatchDao whPickingBatchDao;

    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private RocketMQProducerServer producerServer;

    @Value("${mq.mq.mq.wms3.create.pickinglist}")
    public String WMS3_MQ_CREATE_PICKINGLIST;

    @Autowired
    private QstaManager qstaManager;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    TimeHashMap<String, List<MessageConfig>> createPickingListCache = new TimeHashMap<String, List<MessageConfig>>();


    @Override
    public Long createPickingListBySta(String skusIdAndQty, List<Long> staIdList, Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp, Integer isSn, Long categoryId, List<String> cityList, Boolean isTransAfter, List<SkuSizeConfig> ssList,
            Integer isCod, String isQs, Boolean isOtwoo, Boolean isSd, Boolean isClickBatch, Long locId, Boolean isOtoPicking) {
        Long msgId = null;
        // 首先创建并将作业单加入配货批
        PickingList pl = null;
        // String[] ruleCodeList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        // "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        // List<String> ruleList = new ArrayList<String>();
        try {
            pl = warehouseOutBoundManager.createPickingList(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, isTransAfter, ssList, isCod, isQs, isOtwoo, isSd, isClickBatch, locId, isOtoPicking);
            // 生成二次分拣框号编码规则
            // for (String rolecode : ruleCodeList) {
            // for (int j = 1; j <= 4; j++) {
            // String s = rolecode + j;
            // ruleList.add(s);
            // }
            // }
            // for (int i = 0; i < ruleList.size(); i++) {
            // String rulecode = ruleList.get(i);
            // StockTransApplication sta1 = null;
            // if (i < staIdList.size()) {
            // sta1 = staDao.getByPrimaryKey(staIdList.get(i));
            // }else{
            // break;
            // }
            // if (sta1 != null) {
            // sta1.setRuleCode(rulecode);// 作业单绑定二次分拣框号编码
            // sta1.setIndex(i+1);
            // staDao.save(sta1);
            // }
            // }
        } catch (BusinessException e) {
            throw e;
        }
        // 以下逻辑判断快递可否送达
        try {
            if (pl.getCheckMode().equals(PickingListCheckMode.PCIKING_O2OQS)) {
                // O2O+QS,以箱为基准获取运单号
                transOlManagerProxy.matchingTransNoByPackage(pl.getId());
            } else {
                // 匹配运单号通用
                transOlManagerProxy.matchingTransNo(pl.getId(), locId);
            }
        } catch (BusinessException e) {
            throw e;
        }
        try {
            wareHouseManagerProxy.occupiedInventoryByPickinglist(pl.getId(), id, ouId);
        } catch (BusinessException e) {
            throw e;
        }
        // 以下逻辑自动化仓匹配快递集货口,播种消息保存
        Warehouse se = warehouseDao.getByOuId(ouId);
        if (se != null && se.getIsAutoWh() != null && se.getIsAutoWh() == true) {
            try {
                msgId = warehouseOutBoundManager.marryStaShiipingCode(checkMode, pl.getCode(), staIdList, ouId);
            } catch (Exception e) {
                log.error(pl.getId() + ":marryStaShiiping is error---", e);
            }
        }

        return msgId;
    }

    @Override
    public List<Long> autoCreatePickingListBySta(Integer mergePickZoon, Integer mergeWhZoon, Boolean isMargeWhZoon, String whAreaList, String areaList, String skusIdAndQty, List<SkuSizeConfig> ssList, String shoplist, String shoplist1,
            List<String> cityList, StockTransApplication sta, Integer transTimeType, Integer minAutoSize, Integer limit, Integer plCount, Date date, Date date2, Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn,
            Integer isNeedInvoice, Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp, String isQs, Integer sumCount, String otoAll, Boolean isOtoPicking, Boolean countAreasCp) {
        List<Long> msgIdList = new ArrayList<Long>();
        Integer isMergeInt = 1; // 是否忽略拣货分区 1 代表否，null代表是
        Integer isMergeZoon = 1; // 是否忽略仓库分区 1 代表否，null代表是
        Integer minSize = 0; // 最少单据数
        Boolean isCp = false;// 混合拣货区域（包含所有排列组合）
        List<MessageConfig> configs = new ArrayList<MessageConfig>();
        configs = getCreatePickingListCache(MqStaticEntity.WMS3_MQ_CREATE_PICKINGLIST);
        if (minAutoSize != null && !"".equals(minSize)) {
            minSize = minAutoSize;
        }
        // 如果勾选是否忽略，则忽略拣货区域条件
        if (sta != null && sta.getIsMerge() != null && sta.getIsMerge()) {
            isMergeInt = null;
        }
        // 如果勾选是否忽略，则忽略仓库区域条件
        if (isMargeWhZoon != null && isMargeWhZoon) {
            isMergeZoon = null;
        }
        if (areaList == null) {
            areaList = "";
        }
        if (whAreaList == null) {
            whAreaList = "";
        }
        if (sta != null && sta.getIsMerge() != null && !sta.getIsMerge() && countAreasCp != null && countAreasCp) {
            // 拣货区域没有勾选 忽略已选拣货区域查询条件 && 勾选了 混合拣货区域（包含所有排列组合）
            isCp = true;
        }
        Warehouse se = warehouseDao.getByOuId(ouId);
        // 如果是自动化仓， 每批最大数量如果小于 仓库配置，则取当前数。如果大于仓库配置， 则取仓库上的配置 (多件)
        if (se != null && se.getIsAutoWh() != null && se.getIsAutoWh() == true && checkMode.equals(PickingListCheckMode.DEFAULE)) {
            Integer whMaxCount = 0;
            if (limit == null) {
                limit = 0;
            }
            if (se.getOrderCountLimit() != null) {
                whMaxCount = se.getOrderCountLimit();
            }
            if (limit > whMaxCount) {
                limit = whMaxCount;
            }
        }
        // 拣货区域和仓库区域 只会存在一个
        String array[] = null;
        if ("".equals(areaList)) {
            array = whAreaList.split("\\|");
        } else {
            array = areaList.split("\\|");
        }
        BusinessException root = new BusinessException();
        if (limit == null || limit.equals(0)) {
            // 没有每批单据多少限制，将查询出来的作业单创建到一批里面 最多查询1W单
            List<Long> staIdList = null;
            if (isCp) {
                staIdList =
                        warehouseOutBoundManager.findStaForPickingByModelListNew(whAreaList, mergeWhZoon, isMergeZoon, areaList, "", mergePickZoon, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2, orderCreateTime,
                                toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice, ouId, checkMode, isSp, isQs, 10000, sumCount, otoAll, countAreasCp);
            } else {
                staIdList =
                        warehouseOutBoundManager.findStaForPickingByModelListNew(null, null, null, null, null, null, null, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2, orderCreateTime, toOrderCreateTime, categoryId, isCod,
                                isSn, isNeedInvoice, ouId, checkMode, isSp, isQs, 10000, sumCount, otoAll, countAreasCp);
            }
            if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                try {
                    if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                        createPickingListToMq(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, null, null, false, isOtoPicking);
                    } else {
                        Long msgId = createPickingListBySta(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, null, null, false, false, null, isOtoPicking);
                        if (msgId != null) {
                            msgIdList.add(msgId);
                        }
                    }
                } catch (BusinessException e) {
                    log.error("", e);
                    setLinkedBusinessException(root, e);
                }
            }
        } else {
            // 有每批单据多少限制，但是没有批次限制，则循环作业单，根据作业单多少创建对应多少批次
            if (plCount == null || plCount == 0) {
                // String[] ruleCodeList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                // "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                // List<String> ruleList = new ArrayList<String>();
                if (isCp) {
                    List<Long> staIdList =
                            warehouseOutBoundManager.findStaForPickingByModelListNew(whAreaList, mergeWhZoon, isMergeZoon, areaList, "", mergePickZoon, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2, orderCreateTime,
                                    toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice, ouId, checkMode, isSp, isQs, 10000, sumCount, otoAll, countAreasCp);
                    if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                        List<Long> idList = new ArrayList<Long>();
                        for (int i = 0; i < staIdList.size(); i++) {
                            idList.add(staIdList.get(i));
                            if ((i + 1) % limit == 0) {
                                if (idList.size() > 0) {
                                    try {
                                        if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                            createPickingListToMq(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                        } else {
                                            Long msgId = createPickingListBySta(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                            if (msgId != null) {
                                                msgIdList.add(msgId);
                                            }
                                        }
                                    } catch (BusinessException e) {
                                        log.error("", e);
                                        setLinkedBusinessException(root, e);
                                    }
                                    idList = new ArrayList<Long>();
                                }
                            }
                        }
                        if (idList.size() > 0 && idList.size() >= minSize) {
                            try {
                                if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                    createPickingListToMq(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                } else {
                                    Long msgId = createPickingListBySta(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                    if (msgId != null) {
                                        msgIdList.add(msgId);
                                    }
                                }
                            } catch (BusinessException e) {
                                log.error("", e);
                                setLinkedBusinessException(root, e);
                            }
                        }
                    }
                } else {
                    for (String areaId : array) {
                        // by区域查询可配货作业单列表，如合并拣货区域，则忽略分区条件
                        List<Long> staIdList =
                                warehouseOutBoundManager.findStaForPickingByModelListNew(whAreaList, mergeWhZoon, isMergeZoon, areaList, areaId, mergePickZoon, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2,
                                        orderCreateTime, toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice, ouId, checkMode, isSp, isQs, 10000, sumCount, otoAll, countAreasCp);
                        // 生成二次分拣框号编码规则
                        // for (String rolecode : ruleCodeList) {
                        // for (int j = 1; j <= 4; j++) {
                        // String s = rolecode + j;
                        // ruleList.add(s);
                        // }
                        // }
                        // for (int i = 0; i < ruleList.size(); i++) {
                        // String rulecode = ruleList.get(i);
                        // StockTransApplication sta1 = null;
                        // if (i < staIdList.size()) {
                        // sta1 = staDao.getByPrimaryKey(staIdList.get(i));
                        // } else {
                        // break;
                        // }
                        // if (sta1 != null) {
                        // sta1.setRuleCode(rulecode);// 作业单绑定二次分拣框号编码
                        // sta1.setIndex(i + 1);
                        // staDao.save(sta1);
                        // }
                        // }
                        if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                            List<Long> idList = new ArrayList<Long>();
                            for (int i = 0; i < staIdList.size(); i++) {
                                idList.add(staIdList.get(i));
                                if ((i + 1) % limit == 0) {
                                    if (idList.size() > 0) {
                                        try {
                                            if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                                createPickingListToMq(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                            } else {
                                                Long msgId = createPickingListBySta(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                                if (msgId != null) {
                                                    msgIdList.add(msgId);
                                                }
                                            }

                                        } catch (BusinessException e) {
                                            log.error("", e);
                                            setLinkedBusinessException(root, e);
                                        }
                                        idList = new ArrayList<Long>();
                                    }
                                }
                            }
                            if (idList.size() > 0 && idList.size() >= minSize) {
                                try {
                                    if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                        createPickingListToMq(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                    } else {
                                        Long msgId = createPickingListBySta(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                        if (msgId != null) {
                                            msgIdList.add(msgId);
                                        }
                                    }

                                } catch (BusinessException e) {
                                    log.error("", e);
                                    setLinkedBusinessException(root, e);
                                }
                            }
                        }
                        // 如合并拣货区域，则循环一次跳出
                        if (isMergeInt == null || (mergePickZoon != null && mergePickZoon == 1)) {
                            break;
                        }
                        // 如合并仓库区域，则循环一次跳出
                        if (isMergeZoon == null || (mergeWhZoon != null && mergeWhZoon == 1)) {
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < plCount; i++) {
                    if (isCp) {
                        List<Long> staIdList =
                                warehouseOutBoundManager.findStaForPickingByModelListNew(whAreaList, mergeWhZoon, isMergeZoon, areaList, "", mergePickZoon, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2,
                                        orderCreateTime, toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice, ouId, checkMode, isSp, isQs, limit, sumCount, otoAll, countAreasCp);
                        if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                            try {
                                if (null == sumCount) {
                                    if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                        createPickingListToMq(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                    } else {
                                        Long msgId = createPickingListBySta(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                        if (msgId != null) {
                                            msgIdList.add(msgId);
                                        }
                                    }
                                } else {
                                    Long sum = 0L;
                                    List<Long> newStaIdList = new ArrayList<Long>();
                                    for (int j = 0; j < staIdList.size(); j++) {
                                        StockTransApplication st = staDao.findByQtyByStaId(staIdList.get(j), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                                        if (st.getSkuQty() > sumCount) {
                                            continue;
                                        }
                                        sum = sum + st.getSkuQty();
                                        if (sum > sumCount) {
                                            if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                                createPickingListToMq(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                            } else {
                                                Long msgId = createPickingListBySta(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                                if (msgId != null) {
                                                    msgIdList.add(msgId);
                                                }
                                            }
                                            newStaIdList.clear();
                                            sum = st.getSkuQty();
                                            newStaIdList.add(staIdList.get(j));
                                        } else {
                                            newStaIdList.add(staIdList.get(j));
                                        }
                                        if (j == staIdList.size() - 1 && sum <= sumCount) {
                                            if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                                createPickingListToMq(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                            } else {
                                                Long msgId = createPickingListBySta(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                                if (msgId != null) {
                                                    msgIdList.add(msgId);
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (BusinessException e) {
                                log.warn("creat picking list error :{}", e.getErrorCode());
                                setLinkedBusinessException(root, e);
                            }
                        }
                    } else {
                        for (String areaId : array) {
                            List<Long> staIdList =
                                    warehouseOutBoundManager.findStaForPickingByModelListNew(whAreaList, mergeWhZoon, isMergeZoon, areaList, areaId, mergePickZoon, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2,
                                            orderCreateTime, toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice, ouId, checkMode, isSp, isQs, limit, sumCount, otoAll, countAreasCp);
                            if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                                try {
                                    if (null == sumCount) {
                                        if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                            createPickingListToMq(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                        } else {
                                            Long msgId = createPickingListBySta(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                            if (msgId != null) {
                                                msgIdList.add(msgId);
                                            }
                                        }
                                    } else {
                                        Long sum = 0L;
                                        List<Long> newStaIdList = new ArrayList<Long>();
                                        for (int j = 0; j < staIdList.size(); j++) {
                                            StockTransApplication st = staDao.findByQtyByStaId(staIdList.get(j), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                                            if (st.getSkuQty() > sumCount) {
                                                continue;
                                            }
                                            sum = sum + st.getSkuQty();
                                            if (sum > sumCount) {
                                                if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                                    createPickingListToMq(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                                } else {
                                                    Long msgId = createPickingListBySta(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                                    if (msgId != null) {
                                                        msgIdList.add(msgId);
                                                    }
                                                }
                                                newStaIdList.clear();
                                                sum = st.getSkuQty();
                                                newStaIdList.add(staIdList.get(j));
                                            } else {
                                                newStaIdList.add(staIdList.get(j));
                                            }
                                            if (j == staIdList.size() - 1 && sum <= sumCount) {
                                                if (null != configs && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {
                                                    createPickingListToMq(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, isOtoPicking);
                                                } else {
                                                    Long msgId = createPickingListBySta(skusIdAndQty, newStaIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, isOtoPicking);
                                                    if (msgId != null) {
                                                        msgIdList.add(msgId);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (BusinessException e) {
                                    log.warn("creat picking list error :{}", e.getErrorCode());
                                    setLinkedBusinessException(root, e);
                                }
                            }
                            // 如合并拣货区域，则循环一次跳出
                            if (isMergeInt == null || (mergePickZoon != null && mergePickZoon == 1)) {
                                break;
                            }
                            // 如合并仓库区域，则循环一次跳出
                            if (isMergeZoon == null || (mergeWhZoon != null && mergeWhZoon == 1)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (root.getLinkedException() != null) {
            throw root;
        }
        if (msgIdList.size() > 0) {
            qstaManager.createPickingListToWCS(msgIdList);
        }
        return msgIdList;
    }


    /**
     * 配货批次分配记录 与账号关联
     */
    public void savePickNode(String jubNumber, Long pickType, String batchCode, Long ouId) {
        if (!StringUtil.isEmpty(jubNumber) && !StringUtil.isEmpty(batchCode)) {
            User user = userDao.findByLoginName(jubNumber);
            if (user != null) {
                if (pickType == 14) {
                    // 判断当前批次是否存在正在拣货的状态，如果不存在则表示前面流程节点变更有误，需要给出错误提示
                    WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(batchCode, 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                    if (ref2 == null) {
                        throw new BusinessException(ErrorCode.ERROR_INSERT_INFO5, new Object[] {batchCode});
                    }
                }
                // 判断当前批次是否存在拣货完成状态，如果存在，则给出错误提示
                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(batchCode, pickType.intValue(), new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 != null) {
                    throw new BusinessException(ErrorCode.ERROR_INSERT_INFO4, new Object[] {batchCode});
                }
                // 判断是否是配货中的批次
                PickingList obj = pickingListDao.findPickByCode(batchCode);
                if (obj != null) {
                    if (obj.getStatus() == PickingListStatus.PACKING) {
                        if (pickType == 14) {
                            // 校验是否完成了周转箱的绑定
                            Warehouse w = warehouseDao.getByOuId(ouId);
                            if (w != null && w.getIsAutoWh() != null && w.getIsAutoWh() == true && checkIsFinishBind(obj)) {
                                throw new BusinessException(ErrorCode.EXISTS_PB_NOT_BIND);
                            }
                        }
                        // 与账号关联 带仓库ID
                        whInfoTimeRefDao.insertWhInfoTime2(batchCode, WhInfoTimeRefBillType.STA_PICKING.getValue(), pickType.intValue(), user.getId(), ouId);
                        obj.setPickingStatus(pickType.intValue());
                        if (obj.getPickingUser() == null) {
                            obj.setPickingUser(user);
                        }
                        if (pickType == 13) {
                            obj.setPickingStartTime(new Date());
                        } else {
                            obj.setPickingEndTime(new Date());
                        }

                        pickingListDao.save(obj);
                    } else {
                        throw new BusinessException(ErrorCode.ERROR_INSERT_INFO2, new Object[] {batchCode});
                    }
                } else {
                    throw new BusinessException(ErrorCode.ERROR_INSERT_INFO3, new Object[] {batchCode});
                }
            } else {
                throw new BusinessException(ErrorCode.USER_JOB_NUMBER_IS_NULL, new Object[] {jubNumber});
            }
        } else {
            throw new BusinessException(ErrorCode.INFO_ERROR_NOT_SAVE);
        }
    }

    /**
     * @param obj
     * @return
     */
    private Boolean checkIsFinishBind(PickingList obj) {
        if (obj.getCheckMode().equals(PickingListCheckMode.PICKING_CHECK)) {
            return pickingListDao.findIfExistPbNotBind(obj.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
        } else {
            List<WhPickingBatch> wpbList = whPickingBatchDao.getPlzListByPickingListIdAndStatus(obj.getId(), DefaultStatus.CREATED);
            if (wpbList != null && wpbList.size() > 0) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void autoCreatePickingListByStaByNoPT(String areaList, String skusIdAndQty, List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList, StockTransApplication sta, Integer transTimeType, Integer minAutoSize,
            Integer limit, Integer plCount, Date date, Date date2, Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice, Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp,
            String isQs, Integer sumCount) {
        Integer isMergeInt = 1; // 是否合并拣货分区 1 代表否，null代表是
        Integer minSize = 0; // 最少单据数
        if (minAutoSize != null && !"".equals(minSize)) {
            minSize = minAutoSize;
        }
        // 如果勾选是否合并，则忽略拣货区域条件
        if (sta != null && sta.getIsMerge() != null && sta.getIsMerge()) {
            isMergeInt = null;
        }
        String array[] = areaList.split("\\|"); // 拣货区域数组
        BusinessException root = new BusinessException();
        if (limit == null || limit.equals(0)) {
            // 没有每批单据多少限制，将查询出来的作业单创建到一批里面 最多查询1W单
            List<Long> staIdList =
                    warehouseOutBoundManager.findStaForPickingByModelListNew1(null, null, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2, orderCreateTime, toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice, ouId, checkMode,
                            isSp, isQs, 10000, sumCount);
            if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                try {
                    createPickingListBySta(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, null, null, false, false, null, false);
                } catch (BusinessException e) {
                    log.error("", e);
                    setLinkedBusinessException(root, e);
                }
            }
        } else {
            if (plCount == null || plCount == 0) {// 有每批单据多少限制，但是没有批次限制，则循环作业单，根据作业单多少创建对应多少批次
                for (String areaId : array) {
                    // by区域查询可配货作业单列表，如合并拣货区域，则忽略分区条件
                    List<Long> staIdList =
                            warehouseOutBoundManager.findStaForPickingByModelListNew1(areaId, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2, orderCreateTime, toOrderCreateTime, categoryId, isCod, isSn, isNeedInvoice,
                                    ouId, checkMode, isSp, isQs, 10000, sumCount);
                    if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                        List<Long> idList = new ArrayList<Long>();
                        for (int i = 0; i < staIdList.size(); i++) {
                            idList.add(staIdList.get(i));
                            if ((i + 1) % limit == 0) {
                                if (idList.size() > 0) {
                                    try {
                                        createPickingListBySta(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, false);
                                    } catch (BusinessException e) {
                                        log.error("", e);
                                        setLinkedBusinessException(root, e);
                                    }
                                    idList = new ArrayList<Long>();
                                }
                            }
                        }
                        if (idList.size() > 0 && idList.size() >= minSize) {
                            try {
                                createPickingListBySta(skusIdAndQty, idList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, false);
                            } catch (BusinessException e) {
                                log.error("", e);
                                setLinkedBusinessException(root, e);
                            }
                        }
                    }
                    // 如合并拣货区域，则循环一次跳出
                    if (isMergeInt == null) {
                        break;
                    }
                }
            } else {
                for (int i = 0; i < plCount; i++) {
                    for (String areaId : array) {
                        List<Long> staIdList =
                                warehouseOutBoundManager.findStaForPickingByModelListNew1(areaId, isMergeInt, ssList, shoplist, shoplist1, cityList, sta, transTimeType, date, date2, orderCreateTime, toOrderCreateTime, categoryId, isCod, isSn,
                                        isNeedInvoice, ouId, checkMode, isSp, isQs, limit, sumCount);
                        if (staIdList != null && staIdList.size() > 0 && staIdList.size() >= minSize) {
                            try {
                                createPickingListBySta(skusIdAndQty, staIdList, ouId, id, checkMode, isSp, isSn, categoryId, cityList, null, ssList, isCod, isQs, null, false, false, null, false);
                            } catch (BusinessException e) {
                                log.error("", e);
                                setLinkedBusinessException(root, e);
                            }
                        }
                        // 如合并拣货区域，则循环一次跳出
                        if (isMergeInt == null) {
                            break;
                        }
                    }
                }
            }
        }
        if (root.getLinkedException() != null) {
            throw root;
        }
    }

    public void generStaLsingle(List<Long> staIdList, Long ouId, Long id) {
        for (Long staId : staIdList) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            // 普通达
            if (sta.getStaDeliveryInfo().getTransTimeType() == TransTimeType.ORDINARY) {
                List<TransportatorCommand> transc =
                        transportatorDao.findGenerStaLsingle(sta.getStaDeliveryInfo().getLpCode(), sta.getStaDeliveryInfo().getProvince(), sta.getStaDeliveryInfo().getCity(), sta.getStaDeliveryInfo().getDistrict(), TransTimeType.THE_NEXT_DAY.getValue(),
                                new BeanPropertyRowMapperExt<TransportatorCommand>(TransportatorCommand.class));
                if (transc.size() == 0) {
                    List<TransportatorCommand> transd =
                            transportatorDao.findGenerStaLsingle(sta.getStaDeliveryInfo().getLpCode(), sta.getStaDeliveryInfo().getProvince(), sta.getStaDeliveryInfo().getCity(), sta.getStaDeliveryInfo().getDistrict(), TransTimeType.SAME_DAY.getValue(),
                                    new BeanPropertyRowMapperExt<TransportatorCommand>(TransportatorCommand.class));
                    // 查询到不到单日达数据记录日志解锁，不作升单
                    if (transd.size() == 0) {
                        TranUpgradeLog log = new TranUpgradeLog();
                        log.setCreateTime(new Date());
                        log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                        log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                        log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                        log.settTransType(sta.getStaDeliveryInfo().getTransType());
                        log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                        log.settTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                        log.setIscancel(false);
                        log.setUserId(id);
                        log.setStaId(staId);
                        upgradeLogDao.save(log);
                        sta.setIsTransUpgrade(false);
                        sta.setIsLocked(false);
                        staDao.save(sta);
                    } else {
                        // 查询到单日达数据记录日志解锁，升单
                        TranUpgradeLog log = new TranUpgradeLog();
                        log.setCreateTime(new Date());
                        log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                        log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                        log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                        log.settTransType(sta.getStaDeliveryInfo().getTransType());
                        log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                        log.settTransTimeType(TransTimeType.SAME_DAY);
                        log.setIscancel(false);
                        log.setUserId(id);
                        log.setStaId(staId);
                        upgradeLogDao.save(log);
                        sta.getStaDeliveryInfo().setTransTimeType(TransTimeType.SAME_DAY);
                        sta.setIsLocked(false);
                        sta.setIsTransUpgrade(false);
                        deliveryInfoDao.save(sta.getStaDeliveryInfo());
                        staDao.save(sta);
                    }
                } else {
                    // 查询到次日达数据，记录日志，升单
                    TranUpgradeLog log = new TranUpgradeLog();
                    log.setCreateTime(new Date());
                    log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                    log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                    log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                    log.settTransType(sta.getStaDeliveryInfo().getTransType());
                    log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                    log.settTransTimeType(TransTimeType.THE_NEXT_DAY);
                    log.setIscancel(false);
                    log.setUserId(id);
                    log.setStaId(staId);
                    upgradeLogDao.save(log);
                    sta.getStaDeliveryInfo().setTransTimeType(TransTimeType.THE_NEXT_DAY);
                    sta.setIsLocked(false);
                    sta.setIsTransUpgrade(false);
                    deliveryInfoDao.save(sta.getStaDeliveryInfo());
                    staDao.save(sta);
                }
                // 当日达
            } else if (sta.getStaDeliveryInfo().getTransTimeType() == TransTimeType.SAME_DAY) {
                // 记录日志，解锁
                TranUpgradeLog log = new TranUpgradeLog();
                log.setCreateTime(new Date());
                log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                log.settTransType(sta.getStaDeliveryInfo().getTransType());
                log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                log.settTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                log.setIscancel(false);
                log.setUserId(id);
                log.setStaId(staId);
                upgradeLogDao.save(log);
                sta.setIsLocked(false);
                sta.setIsTransUpgrade(false);
                staDao.save(sta);
                // 次日达
            } else if (sta.getStaDeliveryInfo().getTransTimeType() == TransTimeType.THE_NEXT_DAY) {
                // 查询单日达
                List<TransportatorCommand> transd =
                        transportatorDao.findGenerStaLsingle(sta.getStaDeliveryInfo().getLpCode(), sta.getStaDeliveryInfo().getProvince(), sta.getStaDeliveryInfo().getCity(), sta.getStaDeliveryInfo().getDistrict(), TransTimeType.SAME_DAY.getValue(),
                                new BeanPropertyRowMapperExt<TransportatorCommand>(TransportatorCommand.class));
                if (transd.size() == 0) {
                    // 没有查询到记录日志，不升单
                    TranUpgradeLog log = new TranUpgradeLog();
                    log.setCreateTime(new Date());
                    log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                    log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                    log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                    log.settTransType(sta.getStaDeliveryInfo().getTransType());
                    log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                    log.settTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                    log.setIscancel(false);
                    log.setUserId(id);
                    log.setStaId(staId);
                    upgradeLogDao.save(log);
                    sta.setIsLocked(false);
                    sta.setIsTransUpgrade(false);
                } else {
                    // 查询到记录，记录日志，升单
                    TranUpgradeLog log = new TranUpgradeLog();
                    log.setCreateTime(new Date());
                    log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                    log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                    log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                    log.settTransType(sta.getStaDeliveryInfo().getTransType());
                    log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                    log.settTransTimeType(TransTimeType.SAME_DAY);
                    log.setIscancel(false);
                    log.setUserId(id);
                    log.setStaId(staId);
                    upgradeLogDao.save(log);
                    sta.getStaDeliveryInfo().setTransTimeType(TransTimeType.SAME_DAY);
                    sta.setIsLocked(false);
                    sta.setIsTransUpgrade(false);
                    deliveryInfoDao.save(sta.getStaDeliveryInfo());
                    staDao.save(sta);
                }
                // 及时达
            } else {
                // 记录日志，解锁
                TranUpgradeLog log = new TranUpgradeLog();
                log.setCreateTime(new Date());
                log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
                log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
                log.setfTransType(sta.getStaDeliveryInfo().getTransType());
                log.settTransType(sta.getStaDeliveryInfo().getTransType());
                log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                log.settTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
                log.setIscancel(false);
                log.setUserId(id);
                log.setStaId(staId);
                upgradeLogDao.save(log);
                sta.setIsLocked(false);
                sta.setIsTransUpgrade(false);
                staDao.save(sta);
            }
        }
    }

    @Override
    public void outGenerStaLsingle(List<Long> staIdList, Long ouId, Long id) {
        for (Long staId : staIdList) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            // 记录日志，解锁
            TranUpgradeLog log = new TranUpgradeLog();
            log.setCreateTime(new Date());
            log.setfLpcode(sta.getStaDeliveryInfo().getLpCode());
            log.settLpcode(sta.getStaDeliveryInfo().getLpCode());
            log.setfTransType(sta.getStaDeliveryInfo().getTransType());
            log.settTransType(sta.getStaDeliveryInfo().getTransType());
            log.setfTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
            log.settTransTimeType(sta.getStaDeliveryInfo().getTransTimeType());
            log.setIscancel(false);
            log.setUserId(id);
            log.setStaId(staId);
            upgradeLogDao.save(log);
            sta.setIsLocked(false);
            sta.setIsTransUpgrade(false);
            staDao.save(sta);
        }

    }

    public List<MessageConfig> getCreatePickingListCache(String code) {
        if (StringUtil.isEmpty(code)) return null;
        List<MessageConfig> messageConfig = createPickingListCache.get(code);
        // 缓存中的数据不存在或者已过期
        if (messageConfig == null) {
            messageConfig = createPickingListRole();
        }
        return messageConfig;
    }

    public synchronized List<MessageConfig> createPickingListRole() {
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_CREATE_PICKINGLIST, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        createPickingListCache.put(MqStaticEntity.WMS3_MQ_CREATE_PICKINGLIST, configs, 60 * 1000);
        return configs;
    }


    public void createPickingListToMq(String skusIdAndQty, List<Long> staIdList, Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp, Integer isSn, Long categoryId, List<String> cityList, Boolean isTransAfter, List<SkuSizeConfig> ssList,
            Integer isCod, String isQs, Boolean isOtwoo, boolean isSd, boolean isOtoPicking) {
        List<MessageConfig> configs = new ArrayList<MessageConfig>();
        configs = getCreatePickingListCache(MqStaticEntity.WMS3_MQ_CREATE_PICKINGLIST);
        if (configs != null && configs.size() > 0 && configs.get(0).getIsOpen() == 1) {// 开
            List<CreatePickingListByMQ> list = new ArrayList<CreatePickingListByMQ>();
            CreatePickingListByMQ createPickingListByMQ = new CreatePickingListByMQ();
            createPickingListByMQ.setSkusIdAndQty(skusIdAndQty);
            createPickingListByMQ.setStaIdList(staIdList);
            createPickingListByMQ.setOuId(ouId);
            createPickingListByMQ.setId(id);
            createPickingListByMQ.setCheckMode(checkMode);
            createPickingListByMQ.setIsSp(isSp);
            createPickingListByMQ.setCategoryId(categoryId);
            createPickingListByMQ.setSsList(ssList);
            createPickingListByMQ.setIsCod(isCod);
            createPickingListByMQ.setCityList(cityList);
            createPickingListByMQ.setIsQs(isQs);
            createPickingListByMQ.setIsOtwoo(isOtwoo);
            createPickingListByMQ.setIsTransAfter(isTransAfter);
            createPickingListByMQ.setB(isSd);
            createPickingListByMQ.setIsOtoPicking(isOtoPicking);
            list.add(createPickingListByMQ);
            String msg = JsonUtil.writeValue(list);
            // 发送MQ
            MessageCommond mc = new MessageCommond();
            mc.setMsgId(ouId + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
            mc.setMsgType("com.jumbo.wms.manager.warehouse.WareHouseOutBoundManagerImpl.createPickingListToMq");
            mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            mc.setMsgBody(msg);
            mc.setIsMsgBodySend(false);
            try {
                producerServer.sendDataMsgConcurrently(WMS3_MQ_CREATE_PICKINGLIST, mc);
            } catch (Exception e) {
                log.error("createPickingListToMq:" + ouId + id + e.toString());
            }
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mc, mdbm);
            mdbm.setMsgBody(msg);
            mdbm.setStaCode(ouId.toString());
            mdbm.setOtherUniqueKey(id.toString());
            mdbm.setMemo(WMS3_MQ_CREATE_PICKINGLIST);
            mongoOperation.save(mdbm);
        }
    }
}
